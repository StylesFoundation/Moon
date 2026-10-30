package net.minecraft.util;

public enum EnumWorldBlockLayer {
   SOLID("Solid"),
   CUTOUT_MIPPED("Mipped Cutout"),
   CUTOUT("Cutout"),
   TRANSLUCENT("Translucent");

   private final String layerName;

   EnumWorldBlockLayer(String string3) {
      this.layerName = string3;
   }

   @Override
   public String toString() {
      return this.layerName;
   }
}
