package net.minecraft.util;

public class MouseFilter {
   private float field_76336_a;
   private float field_76334_b;
   private float field_76335_c;

   public float smooth(float float1, float float2) {
      this.field_76336_a += float1;
      float1 = (this.field_76336_a - this.field_76334_b) * float2;
      this.field_76335_c = this.field_76335_c + (float1 - this.field_76335_c) * 0.5F;
      if (float1 > 0.0F && float1 > this.field_76335_c || float1 < 0.0F && float1 < this.field_76335_c) {
         float1 = this.field_76335_c;
      }

      this.field_76334_b += float1;
      return float1;
   }

   public void reset() {
      this.field_76336_a = 0.0F;
      this.field_76334_b = 0.0F;
      this.field_76335_c = 0.0F;
   }
}
