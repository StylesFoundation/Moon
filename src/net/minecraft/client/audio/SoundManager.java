package net.minecraft.client.audio;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import io.netty.util.internal.ThreadLocalRandom;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import moom.ON;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.Source;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

public class SoundManager {
   private static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
   private static final Logger logger = LogManager.getLogger();
   private final SoundHandler sndHandler;
   private final GameSettings options;
   private SoundManager.SoundSystemStarterThread sndSystem;
   private boolean loaded;
   private int playTime = 0;
   private final Map<String, ISound> playingSounds = HashBiMap.create();
   private final Map<ISound, String> invPlayingSounds = ((BiMap)this.playingSounds).inverse();
   private final Map<ISound, SoundPoolEntry> playingSoundPoolEntries = Maps.newHashMap();
   private final Multimap<SoundCategory, String> categorySounds = HashMultimap.create();
   private final List<ITickableSound> tickableSounds = Lists.newArrayList();
   private final Map<ISound, Integer> delayedSounds = Maps.newHashMap();
   private final Map<String, Integer> playingSoundsStopTime = Maps.newHashMap();

   public SoundManager(SoundHandler soundHandler, GameSettings gameSettings) {
      this.sndHandler = soundHandler;
      this.options = gameSettings;

      try {
         SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
         SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
      } catch (SoundSystemException var4) {
         logger.error(LOG_MARKER, "Error linking with the LibraryJavaSound plug-in", var4);
      }
   }

   public void reloadSoundSystem() {
      this.unloadSoundSystem();
      this.loadSoundSystem();
   }

   private synchronized void loadSoundSystem() {
      if (!this.loaded) {
         try {
            new Thread(() -> {
               SoundSystemConfig.setLogger(new ON(this));
               this.sndSystem = new SoundManager.SoundSystemStarterThread();
               this.loaded = true;
               this.sndSystem.setMasterVolume(this.options.getSoundLevel(SoundCategory.MASTER));
               logger.info(LOG_MARKER, "Sound engine started");
            }, "Sound Library Loader").start();
         } catch (RuntimeException var5) {
            logger.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", var5);
            this.options.setSoundLevel(SoundCategory.MASTER, 0.0F);
            this.options.saveOptions();
         }
      }
   }

   private float getSoundCategoryVolume(SoundCategory soundCategory) {
      return soundCategory != null && soundCategory != SoundCategory.MASTER ? this.options.getSoundLevel(soundCategory) : 1.0F;
   }

   public void setSoundCategoryVolume(SoundCategory soundCategory, float float2) {
      if (this.loaded) {
         if (soundCategory == SoundCategory.MASTER) {
            this.sndSystem.setMasterVolume(float2);
         } else {
            for (String var7 : this.categorySounds.get(soundCategory)) {
               ISound var8 = this.playingSounds.get(var7);
               float var9 = this.getNormalizedVolume(var8, this.playingSoundPoolEntries.get(var8), soundCategory);
               if (var9 <= 0.0F) {
                  this.stopSound(var8);
               } else {
                  this.sndSystem.setVolume(var7, var9);
               }
            }
         }
      }
   }

   public void unloadSoundSystem() {
      if (this.loaded) {
         this.stopAllSounds();
         this.sndSystem.cleanup();
         this.loaded = false;
      }
   }

   public void stopAllSounds() {
      if (this.loaded) {
         for (String var5 : this.playingSounds.keySet()) {
            this.sndSystem.stop(var5);
         }

         this.playingSounds.clear();
         this.delayedSounds.clear();
         this.tickableSounds.clear();
         this.categorySounds.clear();
         this.playingSoundPoolEntries.clear();
         this.playingSoundsStopTime.clear();
      }
   }

   public void updateAllSounds() {
      this.playTime++;

      for (ITickableSound var5 : this.tickableSounds) {
         var5.update();
         if (var5.isDonePlaying()) {
            this.stopSound(var5);
         } else {
            String var6 = this.invPlayingSounds.get(var5);
            this.sndSystem
               .setVolume(
                  var6,
                  this.getNormalizedVolume(var5, this.playingSoundPoolEntries.get(var5), this.sndHandler.getSound(var5.getSoundLocation()).getSoundCategory())
               );
            this.sndSystem.setPitch(var6, this.getNormalizedPitch(var5, this.playingSoundPoolEntries.get(var5)));
            this.sndSystem.setPosition(var6, var5.getXPosF(), var5.getYPosF(), var5.getZPosF());
         }
      }

      Iterator var11 = this.playingSounds.entrySet().iterator();

      while (var11.hasNext()) {
         Entry var12 = (Entry)var11.next();
         String var14 = (String)var12.getKey();
         ISound var7 = (ISound)var12.getValue();
         if (!this.sndSystem.playing(var14)) {
            int var8 = this.playingSoundsStopTime.get(var14);
            if (var8 <= this.playTime) {
               int var9 = var7.getRepeatDelay();
               if (var7.canRepeat() && var9 > 0) {
                  this.delayedSounds.put(var7, this.playTime + var9);
               }

               var11.remove();
               logger.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", new Object[]{var14});
               this.sndSystem.removeSource(var14);
               this.playingSoundsStopTime.remove(var14);
               this.playingSoundPoolEntries.remove(var7);

               try {
                  this.categorySounds.remove(this.sndHandler.getSound(var7.getSoundLocation()).getSoundCategory(), var14);
               } catch (RuntimeException var10) {
               }

               if (var7 instanceof ITickableSound) {
                  this.tickableSounds.remove(var7);
               }
            }
         }
      }

      Iterator var13 = this.delayedSounds.entrySet().iterator();

      while (var13.hasNext()) {
         Entry var15 = (Entry)var13.next();
         if (this.playTime >= (Integer)var15.getValue()) {
            ISound var16 = (ISound)var15.getKey();
            if (var16 instanceof ITickableSound) {
               ((ITickableSound)var16).update();
            }

            this.playSound(var16);
            var13.remove();
         }
      }
   }

   public boolean isSoundPlaying(ISound iSound) {
      if (!this.loaded) {
         return false;
      }

      String var5 = this.invPlayingSounds.get(iSound);
      return var5 != null
         && (this.sndSystem.playing(var5) || this.playingSoundsStopTime.containsKey(var5) && this.playingSoundsStopTime.get(var5) <= this.playTime);
   }

   public void stopSound(ISound iSound) {
      if (this.loaded) {
         String var5 = this.invPlayingSounds.get(iSound);
         if (var5 != null) {
            this.sndSystem.stop(var5);
         }
      }
   }

   public void playSound(ISound iSound) {
      if (this.loaded) {
         if (this.sndSystem.getMasterVolume() <= 0.0F) {
            logger.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", new Object[]{iSound.getSoundLocation()});
         } else {
            SoundEventAccessorComposite var5 = this.sndHandler.getSound(iSound.getSoundLocation());
            if (var5 == null) {
               logger.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", new Object[]{iSound.getSoundLocation()});
            } else {
               SoundPoolEntry var6 = var5.cloneEntry();
               if (var6 == SoundHandler.missing_sound) {
                  logger.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", new Object[]{var5.getSoundEventLocation()});
               } else {
                  float var7 = iSound.getVolume();
                  float var8 = 16.0F;
                  if (var7 > 1.0F) {
                     var8 = 16.0F * var7;
                  }

                  SoundCategory var9 = var5.getSoundCategory();
                  float var10 = this.getNormalizedVolume(iSound, var6, var9);
                  double var11 = this.getNormalizedPitch(iSound, var6);
                  ResourceLocation var13 = var6.getSoundPoolEntryLocation();
                  if (var10 == 0.0F) {
                     logger.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", new Object[]{var13});
                  } else {
                     boolean var14 = iSound.canRepeat() && iSound.getRepeatDelay() == 0;
                     String var15 = MathHelper.getRandomUuid(ThreadLocalRandom.current()).toString();
                     if (var6.isStreamingSound()) {
                        this.sndSystem
                           .newStreamingSource(
                              false,
                              var15,
                              getURLForSoundResource(var13),
                              var13.toString(),
                              var14,
                              iSound.getXPosF(),
                              iSound.getYPosF(),
                              iSound.getZPosF(),
                              iSound.getAttenuationType().getTypeInt(),
                              var8
                           );
                     } else {
                        this.sndSystem
                           .newSource(
                              false,
                              var15,
                              getURLForSoundResource(var13),
                              var13.toString(),
                              var14,
                              iSound.getXPosF(),
                              iSound.getYPosF(),
                              iSound.getZPosF(),
                              iSound.getAttenuationType().getTypeInt(),
                              var8
                           );
                     }

                     logger.debug(
                        LOG_MARKER, "Playing sound {} for event {} as channel {}", var6.getSoundPoolEntryLocation(), var5.getSoundEventLocation(), var15
                     );
                     this.sndSystem.setPitch(var15, (float)var11);
                     this.sndSystem.setVolume(var15, var10);
                     this.sndSystem.play(var15);
                     this.playingSoundsStopTime.put(var15, this.playTime + 20);
                     this.playingSounds.put(var15, iSound);
                     this.playingSoundPoolEntries.put(iSound, var6);
                     if (var9 != SoundCategory.MASTER) {
                        this.categorySounds.put(var9, var15);
                     }

                     if (iSound instanceof ITickableSound) {
                        this.tickableSounds.add((ITickableSound)iSound);
                     }
                  }
               }
            }
         }
      }
   }

   private float getNormalizedPitch(ISound iSound, SoundPoolEntry soundPoolEntry) {
      return (float)MathHelper.clamp_double(iSound.getPitch() * soundPoolEntry.getPitch(), 0.5, 2.0);
   }

   private float getNormalizedVolume(ISound iSound, SoundPoolEntry soundPoolEntry, SoundCategory soundCategory) {
      return (float)MathHelper.clamp_double(iSound.getVolume() * soundPoolEntry.getVolume(), 0.0, 1.0) * this.getSoundCategoryVolume(soundCategory);
   }

   public void pauseAllSounds() {
      for (String var5 : this.playingSounds.keySet()) {
         logger.debug(LOG_MARKER, "Pausing channel {}", new Object[]{var5});
         this.sndSystem.pause(var5);
      }
   }

   public void resumeAllSounds() {
      for (String var5 : this.playingSounds.keySet()) {
         logger.debug(LOG_MARKER, "Resuming channel {}", new Object[]{var5});
         this.sndSystem.play(var5);
      }
   }

   public void playDelayedSound(ISound iSound, int integer) {
      this.delayedSounds.put(iSound, this.playTime + integer);
   }

   private static URL getURLForSoundResource(ResourceLocation resourceLocation) {
      String var1 = String.format("%s:%s:%s", "mcsounddomain", resourceLocation.getResourceDomain(), resourceLocation.getResourcePath());
      URLStreamHandler var2 = new URLStreamHandler() {
         @Override
         protected URLConnection openConnection(URL uRL) {
            return new URLConnection(uRL) {
               @Override
               public void connect() throws IOException {
               }

               @Override
               public InputStream getInputStream() throws IOException {
                  return Minecraft.getMinecraft().getResourceManager().getResource(resourceLocation).getInputStream();
               }
            };
         }
      };

      try {
         return new URL(null, var1, var2);
      } catch (MalformedURLException var3) {
         throw new Error("TODO: Sanely handle url exception! :D");
      }
   }

   public void setListener(EntityPlayer entityPlayer, float float2) {
      if (this.loaded && entityPlayer != null) {
         float var6 = entityPlayer.prevRotationPitch + (entityPlayer.rotationPitch - entityPlayer.prevRotationPitch) * float2;
         float var7 = entityPlayer.prevRotationYaw + (entityPlayer.rotationYaw - entityPlayer.prevRotationYaw) * float2;
         double var8 = entityPlayer.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * float2;
         double var10 = entityPlayer.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * float2 + entityPlayer.getEyeHeight();
         double var12 = entityPlayer.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * float2;
         float var14 = MathHelper.cos((var7 + 90.0F) * (float) (Math.PI / 180.0));
         float var15 = MathHelper.sin((var7 + 90.0F) * (float) (Math.PI / 180.0));
         float var16 = MathHelper.cos(-var6 * (float) (Math.PI / 180.0));
         float var17 = MathHelper.sin(-var6 * (float) (Math.PI / 180.0));
         float var18 = MathHelper.cos((-var6 + 90.0F) * (float) (Math.PI / 180.0));
         float var19 = MathHelper.sin((-var6 + 90.0F) * (float) (Math.PI / 180.0));
         float var20 = var14 * var16;
         float var21 = var15 * var16;
         float var22 = var14 * var18;
         float var23 = var15 * var18;
         this.sndSystem.setListenerPosition((float)var8, (float)var10, (float)var12);
         this.sndSystem.setListenerOrientation(var20, var17, var21, var22, var19, var23);
      }
   }

   static class SoundSystemStarterThread extends SoundSystem {
      private SoundSystemStarterThread() {
      }

      @Override
      public boolean playing(String string) {
         synchronized (SoundSystemConfig.THREAD_SYNC) {
            if (this.soundLibrary == null) {
               return false;
            }

            Source var6 = this.soundLibrary.getSources().get(string);
            return var6 != null && (var6.playing() || var6.paused() || var6.preLoad);
         }
      }
   }
}
