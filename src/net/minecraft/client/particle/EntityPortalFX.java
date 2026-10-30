package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPortalFX extends EntityFX {
   private final float portalParticleScale;
   private final double portalPosX;
   private final double portalPosY;
   private final double portalPosZ;

   protected EntityPortalFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
      this.motionX = double5;
      this.motionY = double6;
      this.motionZ = double7;
      this.portalPosX = this.posX = double2;
      this.portalPosY = this.posY = double3;
      this.portalPosZ = this.posZ = double4;
      float var16 = this.rand.nextFloat() * 0.6F + 0.4F;
      this.portalParticleScale = this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
      this.particleRed = this.particleGreen = this.particleBlue = var16;
      this.particleGreen *= 0.3F;
      this.particleRed *= 0.9F;
      this.particleMaxAge = (int)(Math.random() * 10.0) + 40;
      this.noClip = true;
      this.setParticleTextureIndex((int)(Math.random() * 8.0));
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var9 = (this.particleAge + float3) / this.particleMaxAge;
      var9 = 1.0F - var9;
      var9 *= var9;
      var9 = 1.0F - var9;
      this.particleScale = this.portalParticleScale * var9;
      super.renderParticle(worldRenderer, entity, float3, float4, float5, float6, float7, float8);
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
      var3 = var3 * var3 * var3 * var3;
      return var2 * (1.0F - var3) + var3;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      float var1 = (float)this.particleAge / this.particleMaxAge;
      var1 = -var1 + var1 * var1 * 2.0F;
      var1 = 1.0F - var1;
      this.posX = this.portalPosX + this.motionX * var1;
      this.posY = this.portalPosY + this.motionY * var1 + (1.0F - var1);
      this.posZ = this.portalPosZ + this.motionZ * var1;
      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
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
         return new EntityPortalFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
