package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityLavaFX extends EntityFX {
   private final float lavaParticleScale;

   protected EntityLavaFX(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.motionX *= 0.8F;
      this.motionY *= 0.8F;
      this.motionZ *= 0.8F;
      this.motionY = this.rand.nextFloat() * 0.4F + 0.05F;
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.particleScale = this.particleScale * (this.rand.nextFloat() * 2.0F + 0.2F);
      this.lavaParticleScale = this.particleScale;
      this.particleMaxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      this.noClip = false;
      this.setParticleTextureIndex(49);
   }

   @Override
   public int getBrightnessForRender(float float1) {
      MathHelper.clamp_float((this.particleAge + float1) / this.particleMaxAge, 0.0F, 1.0F);
      int var5 = super.getBrightnessForRender(float1);
      short var6 = 240;
      int var7 = var5 >> 16 & 0xFF;
      return var6 | var7 << 16;
   }

   @Override
   public float getBrightness(float float1) {
      return 1.0F;
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var9 = (this.particleAge + float3) / this.particleMaxAge;
      this.particleScale = this.lavaParticleScale * (1.0F - var9 * var9);
      super.renderParticle(worldRenderer, entity, float3, float4, float5, float6, float7, float8);
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      float var1 = (float)this.particleAge / this.particleMaxAge;
      if (this.rand.nextFloat() > var1) {
         this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ);
      }

      this.motionY -= 0.03;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.999F;
      this.motionY *= 0.999F;
      this.motionZ *= 0.999F;
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
         return new EntityLavaFX(world, double3, double4, double5);
      }
   }
}
