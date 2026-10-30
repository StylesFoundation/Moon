package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler implements IResourceManagerReloadListener, ITickable {
   private static final Logger logger = LogManager.getLogger();
   private static final Gson GSON = new GsonBuilder().registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
   private static final ParameterizedType TYPE = new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class, SoundList.class};
      }

      @Override
      public Type getRawType() {
         return Map.class;
      }

      @Override
      public Type getOwnerType() {
         return null;
      }
   };
   public static final SoundPoolEntry missing_sound = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0, 0.0, false);
   private final SoundRegistry sndRegistry = new SoundRegistry();
   private final SoundManager sndManager;
   private final IResourceManager mcResourceManager;

   public SoundHandler(IResourceManager iResourceManager, GameSettings gameSettings) {
      this.mcResourceManager = iResourceManager;
      this.sndManager = new SoundManager(this, gameSettings);
   }

   @Override
   public void onResourceManagerReload(IResourceManager iResourceManager) {
      this.sndManager.reloadSoundSystem();
      this.sndRegistry.clearMap();

      for (String var6 : iResourceManager.getResourceDomains()) {
         try {
            for (IResource var8 : iResourceManager.getAllResources(new ResourceLocation(var6, "sounds.json"))) {
               try {
                  for (Entry var11 : this.getSoundMap(var8.getInputStream()).entrySet()) {
                     this.loadSoundResource(new ResourceLocation(var6, (String)var11.getKey()), (SoundList)var11.getValue());
                  }
               } catch (RuntimeException var12) {
                  logger.warn("Invalid sounds.json", var12);
               }
            }
         } catch (IOException var13) {
         }
      }
   }

   protected Map<String, SoundList> getSoundMap(InputStream inputStream) {
      Map var2;
      try {
         var2 = (Map)GSON.fromJson(new InputStreamReader(inputStream), TYPE);
      } finally {
         IOUtils.closeQuietly(inputStream);
      }

      return var2;
   }

   private void loadSoundResource(ResourceLocation resourceLocation, SoundList soundList) {
      boolean var6 = !this.sndRegistry.containsKey(resourceLocation);
      SoundEventAccessorComposite var7;
      if (!var6 && !soundList.canReplaceExisting()) {
         var7 = this.sndRegistry.getObject(resourceLocation);
      } else {
         if (!var6) {
            logger.debug("Replaced sound event location {}", new Object[]{resourceLocation});
         }

         var7 = new SoundEventAccessorComposite(resourceLocation, 1.0, 1.0, soundList.getSoundCategory());
         this.sndRegistry.registerSound(var7);
      }

      for (final SoundList.SoundEntry var9 : soundList.getSoundList()) {
         String var10 = var9.getSoundEntryName();
         ResourceLocation var11 = new ResourceLocation(var10);
         final String var12 = var10.contains(":") ? var11.getResourceDomain() : resourceLocation.getResourceDomain();
         ISoundEventAccessor var13;
         switch (var9.getSoundEntryType()) {
            case FILE:
               ResourceLocation var14 = new ResourceLocation(var12, "sounds/" + var11.getResourcePath() + ".ogg");
               InputStream var15 = null;

               try {
                  var15 = this.mcResourceManager.getResource(var14).getInputStream();
               } catch (FileNotFoundException var21) {
                  logger.warn("File {} does not exist, cannot add it to event {}", var14, resourceLocation);
                  continue;
               } catch (IOException var22) {
                  logger.warn("Could not load sound file " + var14 + ", cannot add it to event " + resourceLocation, var22);
                  continue;
               } finally {
                  IOUtils.closeQuietly(var15);
               }

               var13 = new SoundEventAccessor(
                  new SoundPoolEntry(var14, var9.getSoundEntryPitch(), var9.getSoundEntryVolume(), var9.isStreaming()), var9.getSoundEntryWeight()
               );
               break;
            case SOUND_EVENT:
               var13 = new ISoundEventAccessor<SoundPoolEntry>() {
                  final ResourceLocation field_148726_a = new ResourceLocation(var12, var9.getSoundEntryName());

                  @Override
                  public int getWeight() {
                     SoundEventAccessorComposite var4 = SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
                     return var4 == null ? 0 : var4.getWeight();
                  }

                  public SoundPoolEntry cloneEntry() {
                     SoundEventAccessorComposite var4 = SoundHandler.this.sndRegistry.getObject(this.field_148726_a);
                     return var4 == null ? SoundHandler.missing_sound : var4.cloneEntry();
                  }
               };
               break;
            default:
               throw new IllegalStateException("IN YOU FACE");
         }

         var7.addSoundToEventPool(var13);
      }
   }

   public SoundEventAccessorComposite getSound(ResourceLocation resourceLocation) {
      return this.sndRegistry.getObject(resourceLocation);
   }

   public void playSound(ISound iSound) {
      this.sndManager.playSound(iSound);
   }

   public void playDelayedSound(ISound iSound, int integer) {
      this.sndManager.playDelayedSound(iSound, integer);
   }

   public void setListener(EntityPlayer entityPlayer, float float2) {
      this.sndManager.setListener(entityPlayer, float2);
   }

   public void pauseSounds() {
      this.sndManager.pauseAllSounds();
   }

   public void stopSounds() {
      this.sndManager.stopAllSounds();
   }

   public void unloadSounds() {
      this.sndManager.unloadSoundSystem();
   }

   @Override
   public void update() {
      this.sndManager.updateAllSounds();
   }

   public void resumeSounds() {
      this.sndManager.resumeAllSounds();
   }

   public void setSoundLevel(SoundCategory soundCategory, float float2) {
      if (soundCategory == SoundCategory.MASTER && float2 <= 0.0F) {
         this.stopSounds();
      }

      this.sndManager.setSoundCategoryVolume(soundCategory, float2);
   }

   public void stopSound(ISound iSound) {
      this.sndManager.stopSound(iSound);
   }

   public SoundEventAccessorComposite getRandomSoundFromCategories(SoundCategory... arr) {
      ArrayList var5 = Lists.newArrayList();

      for (ResourceLocation var7 : this.sndRegistry.getKeys()) {
         SoundEventAccessorComposite var8 = this.sndRegistry.getObject(var7);
         if (ArrayUtils.contains(arr, var8.getSoundCategory())) {
            var5.add(var8);
         }
      }

      return var5.isEmpty() ? null : (SoundEventAccessorComposite)var5.get(new Random().nextInt(var5.size()));
   }

   public boolean isSoundPlaying(ISound iSound) {
      return this.sndManager.isSoundPlaying(iSound);
   }
}
