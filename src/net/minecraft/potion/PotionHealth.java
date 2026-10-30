package net.minecraft.potion;

import net.minecraft.util.ResourceLocation;

public class PotionHealth extends Potion {
   public PotionHealth(int integer1, ResourceLocation resourceLocation, boolean boolean3, int integer4) {
      super(integer1, resourceLocation, boolean3, integer4);
   }

   @Override
   public boolean isInstant() {
      return true;
   }

   @Override
   public boolean isReady(int integer1, int integer2) {
      return integer1 >= 1;
   }
}
