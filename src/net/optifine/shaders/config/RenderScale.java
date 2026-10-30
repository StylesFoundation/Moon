package net.optifine.shaders.config;

public class RenderScale {
   private float scale = 1.0F;
   private float offsetX = 0.0F;
   private float offsetY = 0.0F;

   public RenderScale(float float1, float float2, float float3) {
      this.scale = float1;
      this.offsetX = float2;
      this.offsetY = float3;
   }

   public float getScale() {
      return this.scale;
   }

   public float getOffsetX() {
      return this.offsetX;
   }

   public float getOffsetY() {
      return this.offsetY;
   }

   @Override
   public String toString() {
      return this.scale + ", " + this.offsetX + ", " + this.offsetY;
   }
}
