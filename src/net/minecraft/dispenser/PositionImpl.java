package net.minecraft.dispenser;

public class PositionImpl implements IPosition {
   protected final double x;
   protected final double y;
   protected final double z;

   public PositionImpl(double double1, double double2, double double3) {
      this.x = double1;
      this.y = double2;
      this.z = double3;
   }

   @Override
   public double getX() {
      return this.x;
   }

   @Override
   public double getY() {
      return this.y;
   }

   @Override
   public double getZ() {
      return this.z;
   }
}
