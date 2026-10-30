package net.optifine;

import net.minecraft.src.Config;
import net.minecraft.util.Vec3;

public class CustomColorFader {
   private Vec3 color = null;
   private long timeUpdate = System.currentTimeMillis();

   public Vec3 getColor(double double1, double double2, double double3) {
      if (this.color == null) {
         this.color = new Vec3(double1, double2, double3);
         return this.color;
      }

      long var10 = System.currentTimeMillis();
      long var12 = var10 - this.timeUpdate;
      if (var12 != 0L) {
         this.timeUpdate = var10;
         if (!(Math.abs(double1 - this.color.xCoord) < 0.004)
            || !(Math.abs(double2 - this.color.yCoord) < 0.004)
            || !(Math.abs(double3 - this.color.zCoord) < 0.004)) {
            double var14 = Config.Method257(var12 * 0.001, 0.0, 1.0);
            double var16 = double1 - this.color.xCoord;
            double var18 = double2 - this.color.yCoord;
            double var20 = double3 - this.color.zCoord;
            double var22 = this.color.xCoord + var16 * var14;
            double var24 = this.color.yCoord + var18 * var14;
            double var26 = this.color.zCoord + var20 * var14;
            this.color = new Vec3(var22, var24, var26);
         }
      }

      return this.color;
   }
}
