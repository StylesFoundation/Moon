package net.minecraft.world;

import net.minecraft.util.MathHelper;

public class DifficultyInstance {
   private final EnumDifficulty worldDifficulty;
   private final float additionalDifficulty;

   public DifficultyInstance(EnumDifficulty enumDifficulty, long long2, long long3, float float4) {
      this.worldDifficulty = enumDifficulty;
      this.additionalDifficulty = this.calculateAdditionalDifficulty(enumDifficulty, long2, long3, float4);
   }

   public float getAdditionalDifficulty() {
      return this.additionalDifficulty;
   }

   public float getClampedAdditionalDifficulty() {
      return this.additionalDifficulty < 2.0F ? 0.0F : (this.additionalDifficulty > 4.0F ? 1.0F : (this.additionalDifficulty - 2.0F) / 2.0F);
   }

   private float calculateAdditionalDifficulty(EnumDifficulty enumDifficulty, long long2, long long3, float float4) {
      if (enumDifficulty == EnumDifficulty.PEACEFUL) {
         return 0.0F;
      }

      boolean var10 = enumDifficulty == EnumDifficulty.HARD;
      float var12 = MathHelper.clamp_float(((float)long2 - 72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
      float var11 = 0.75F + var12;
      float var13 = 0.0F
         + MathHelper.clamp_float((float)long3 / 3600000.0F, 0.0F, 1.0F) * (var10 ? 1.0F : 0.75F)
         + MathHelper.clamp_float(float4 * 0.25F, 0.0F, var12);
      if (enumDifficulty == EnumDifficulty.EASY) {
         var13 *= 0.5F;
      }

      var11 += var13;
      return enumDifficulty.getDifficultyId() * var11;
   }
}
