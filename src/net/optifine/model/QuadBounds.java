package net.optifine.model;

import net.minecraft.util.EnumFacing;

public class QuadBounds {
   private float minX = Float.MAX_VALUE;
   private float minY = Float.MAX_VALUE;
   private float minZ = Float.MAX_VALUE;
   private float maxX = -Float.MAX_VALUE;
   private float maxY = -Float.MAX_VALUE;
   private float maxZ = -Float.MAX_VALUE;

   public QuadBounds(int[] arr) {
      int var5 = arr.length / 4;

      for (int var6 = 0; var6 < 4; var6++) {
         int var7 = var6 * var5;
         float var8 = Float.intBitsToFloat(arr[var7]);
         float var9 = Float.intBitsToFloat(arr[var7 + 1]);
         float var10 = Float.intBitsToFloat(arr[var7 + 2]);
         if (this.minX > var8) {
            this.minX = var8;
         }

         if (this.minY > var9) {
            this.minY = var9;
         }

         if (this.minZ > var10) {
            this.minZ = var10;
         }

         if (this.maxX < var8) {
            this.maxX = var8;
         }

         if (this.maxY < var9) {
            this.maxY = var9;
         }

         if (this.maxZ < var10) {
            this.maxZ = var10;
         }
      }
   }

   public float Method703() {
      return this.minX;
   }

   public float Method704() {
      return this.minY;
   }

   public float Method705() {
      return this.minZ;
   }

   public float Method706() {
      return this.maxX;
   }

   public float Method707() {
      return this.maxY;
   }

   public float Method708() {
      return this.maxZ;
   }

   public boolean Method709(EnumFacing enumFacing) {
      float var5;
      float var6;
      float var7;
      switch (enumFacing) {
         case DOWN:
            var5 = this.Method704();
            var6 = this.Method707();
            var7 = 0.0F;
            break;
         case UP:
            var5 = this.Method704();
            var6 = this.Method707();
            var7 = 1.0F;
            break;
         case NORTH:
            var5 = this.Method705();
            var6 = this.Method708();
            var7 = 0.0F;
            break;
         case SOUTH:
            var5 = this.Method705();
            var6 = this.Method708();
            var7 = 1.0F;
            break;
         case WEST:
            var5 = this.Method703();
            var6 = this.Method706();
            var7 = 0.0F;
            break;
         case EAST:
            var5 = this.Method703();
            var6 = this.Method706();
            var7 = 1.0F;
            break;
         default:
            return false;
      }

      return var5 == var7 && var6 == var7;
   }

   public boolean Method710(EnumFacing enumFacing) {
      float var5;
      float var6;
      float var7;
      float var8;
      switch (enumFacing) {
         case DOWN:
         case UP:
            var5 = this.Method703();
            var6 = this.Method706();
            var7 = this.Method705();
            var8 = this.Method708();
            break;
         case NORTH:
         case SOUTH:
            var5 = this.Method703();
            var6 = this.Method706();
            var7 = this.Method704();
            var8 = this.Method707();
            break;
         case WEST:
         case EAST:
            var5 = this.Method704();
            var6 = this.Method707();
            var7 = this.Method705();
            var8 = this.Method708();
            break;
         default:
            return false;
      }

      return var5 == 0.0F && var6 == 1.0F && var7 == 0.0F && var8 == 1.0F;
   }
}
