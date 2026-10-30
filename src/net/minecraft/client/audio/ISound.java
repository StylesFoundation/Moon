package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public interface ISound {
   ResourceLocation getSoundLocation();

   boolean canRepeat();

   int getRepeatDelay();

   float getVolume();

   float getPitch();

   float getXPosF();

   float getYPosF();

   float getZPosF();

   ISound.AttenuationType getAttenuationType();

   enum AttenuationType {
      NONE(0),
      LINEAR(2);

      private final int type;

      AttenuationType(int integer3) {
         this.type = integer3;
      }

      public int getTypeInt() {
         return this.type;
      }

      private static ISound.AttenuationType[] Method353() {
         return new ISound.AttenuationType[]{NONE, LINEAR};
      }
   }
}
