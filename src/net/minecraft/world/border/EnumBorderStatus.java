package net.minecraft.world.border;

public enum EnumBorderStatus {
   GROWING(4259712),
   SHRINKING(16724016),
   STATIONARY(2138367);

   private final int id;

   EnumBorderStatus(int integer3) {
      this.id = integer3;
   }

   public int getID() {
      return this.id;
   }
}
