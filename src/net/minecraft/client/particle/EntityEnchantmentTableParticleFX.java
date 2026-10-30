package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityEnchantmentTableParticleFX extends EntityFX {
   private final float field_70565_a;
   private final double coordX;
   private final double coordY;
   private final double coordZ;

   protected EntityEnchantmentTableParticleFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
      this.motionX = double5;
      this.motionY = double6;
      this.motionZ = double7;
      this.coordX = double2;
      this.coordY = double3;
      this.coordZ = double4;
      this.posX = this.prevPosX = double2 + double5;
      this.posY = this.prevPosY = double3 + double6;
      this.posZ = this.prevPosZ = double4 + double7;
      float var16 = this.rand.nextFloat() * 0.6F + 0.4F;
      this.field_70565_a = this.particleScale = this.rand.nextFloat() * 0.5F + 0.2F;
      this.particleRed = this.particleGreen = this.particleBlue = var16;
      this.particleGreen *= 0.9F;
      this.particleRed *= 0.9F;
      this.particleMaxAge = (int)(Math.random() * 10.0) + 30;
      this.noClip = true;
      this.setParticleTextureIndex((int)(Math.random() * 26.0 + 1.0 + 224.0));
   }

   @Override
   public int getBrightnessForRender(float float1) {
      int var4 = super.getBrightnessForRender(float1);
      float var5 = (float)this.particleAge / this.particleMaxAge;
      var5 *= var5;
      var5 *= var5;
      int var6 = var4 & 0xFF;
      int var7 = var4 >> 16 & 0xFF;
      var7 += (int)(var5 * 15.0F * 16.0F);
      if (var7 > 240) {
         var7 = 240;
      }

      return var6 | var7 << 16;
   }

   @Override
   public float getBrightness(float float1) {
      float var2 = super.getBrightness(float1);
      float var3 = (float)this.particleAge / this.particleMaxAge;
      var3 *= var3;
      var3 *= var3;
      return var2 * (1.0F - var3) + var3;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      float var1 = (float)this.particleAge / this.particleMaxAge;
      var1 = 1.0F - var1;
      float var2 = 1.0F - var1;
      var2 *= var2;
      var2 *= var2;
      this.posX = this.coordX + this.motionX * var1;
      this.posY = this.coordY + this.motionY * var1 - var2 * 1.2F;
      this.posZ = this.coordZ + this.motionZ * var1;
      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }
   }

   public static class EnchantmentTable implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityEnchantmentTableParticleFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
