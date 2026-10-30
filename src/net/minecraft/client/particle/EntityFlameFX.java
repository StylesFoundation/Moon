package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX extends EntityFX {
   private final float flameScale;

   protected EntityFlameFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
      this.motionX = this.motionX * 0.01F + double5;
      this.motionY = this.motionY * 0.01F + double6;
      this.motionZ = this.motionZ * 0.01F + double7;
      this.posX = this.posX + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
      this.posY = this.posY + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
      this.posZ = this.posZ + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F;
      this.flameScale = this.particleScale;
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
      this.noClip = true;
      this.setParticleTextureIndex(48);
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var9 = (this.particleAge + float3) / this.particleMaxAge;
      this.particleScale = this.flameScale * (1.0F - var9 * var9 * 0.5F);
      super.renderParticle(worldRenderer, entity, float3, float4, float5, float6, float7, float8);
   }

   @Override
   public int getBrightnessForRender(float float1) {
      float var4 = MathHelper.clamp_float((this.particleAge + float1) / this.particleMaxAge, 0.0F, 1.0F);
      int var5 = super.getBrightnessForRender(float1);
      int var6 = var5 & 0xFF;
      int var7 = var5 >> 16 & 0xFF;
      var6 += (int)(var4 * 15.0F * 16.0F);
      if (var6 > 240) {
         var6 = 240;
      }

      return var6 | var7 << 16;
   }

   @Override
   public float getBrightness(float float1) {
      float var2 = MathHelper.clamp_float((this.particleAge + float1) / this.particleMaxAge, 0.0F, 1.0F);
      float var3 = super.getBrightness(float1);
      return var3 * var2 + (1.0F - var2);
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.96F;
      this.motionY *= 0.96F;
      this.motionZ *= 0.96F;
      if (this.onGround) {
         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
      }
   }

   @Override
   public boolean Method239() {
      return true;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityFlameFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
