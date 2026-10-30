package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityReddustFX extends EntityFX {
   float reddustParticleScale;

   protected EntityReddustFX(World world, double double2, double double3, double double4, float float5, float float6, float float7) {
      this(world, double2, double3, double4, 1.0F, float5, float6, float7);
   }

   protected EntityReddustFX(World world, double double2, double double3, double double4, float float5, float float6, float float7, float float8) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.motionX *= 0.1F;
      this.motionY *= 0.1F;
      this.motionZ *= 0.1F;
      if (float6 == 0.0F) {
         float6 = 1.0F;
      }

      float var12 = (float)Math.random() * 0.4F + 0.6F;
      this.particleRed = ((float)(Math.random() * 0.2F) + 0.8F) * float6 * var12;
      this.particleGreen = ((float)(Math.random() * 0.2F) + 0.8F) * float7 * var12;
      this.particleBlue = ((float)(Math.random() * 0.2F) + 0.8F) * float8 * var12;
      this.particleScale *= 0.75F;
      this.particleScale *= float5;
      this.reddustParticleScale = this.particleScale;
      this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.particleMaxAge = (int)(this.particleMaxAge * float5);
      this.noClip = false;
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var9 = MathHelper.clamp_float((this.particleAge + float3) / this.particleMaxAge * 32.0F, 0.0F, 1.0F);
      this.particleScale = this.reddustParticleScale * var9;
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

      this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      if (this.posY == this.prevPosY) {
         this.motionX *= 1.1;
         this.motionZ *= 1.1;
      }

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
         return new EntityReddustFX(world, double3, double4, double5, (float)double6, (float)double7, (float)double8);
      }
   }
}
