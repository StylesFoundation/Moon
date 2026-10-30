package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.util.ResourceLocation;

public class SoundEventAccessorComposite implements ISoundEventAccessor<SoundPoolEntry> {
   private final List<ISoundEventAccessor<SoundPoolEntry>> soundPool = Lists.newArrayList();
   private final Random rnd = new Random();
   private final ResourceLocation soundLocation;
   private final SoundCategory category;
   private final double eventPitch;
   private final double eventVolume;

   public SoundEventAccessorComposite(ResourceLocation resourceLocation, double double2, double double3, SoundCategory soundCategory) {
      this.soundLocation = resourceLocation;
      this.eventVolume = double3;
      this.eventPitch = double2;
      this.category = soundCategory;
   }

   @Override
   public int getWeight() {
      int var4 = 0;

      for (ISoundEventAccessor var6 : this.soundPool) {
         var4 += var6.getWeight();
      }

      return var4;
   }

   public SoundPoolEntry cloneEntry() {
      int var4 = this.getWeight();
      if (!this.soundPool.isEmpty() && var4 != 0) {
         int var5 = this.rnd.nextInt(var4);

         for (ISoundEventAccessor var7 : this.soundPool) {
            var5 -= var7.getWeight();
            if (var5 < 0) {
               SoundPoolEntry var8 = (SoundPoolEntry)var7.cloneEntry();
               var8.setPitch(var8.getPitch() * this.eventPitch);
               var8.setVolume(var8.getVolume() * this.eventVolume);
               return var8;
            }
         }
      }

      return SoundHandler.missing_sound;
   }

   public void addSoundToEventPool(ISoundEventAccessor<SoundPoolEntry> iSoundEventAccessor) {
      this.soundPool.add(iSoundEventAccessor);
   }

   public ResourceLocation getSoundEventLocation() {
      return this.soundLocation;
   }

   public SoundCategory getSoundCategory() {
      return this.category;
   }
}
