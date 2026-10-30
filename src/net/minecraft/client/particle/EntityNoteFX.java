package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityNoteFX extends EntityFX {
   float noteParticleScale;

   protected EntityNoteFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      this(world, double2, double3, double4, double5, double6, double7, 2.0F);
   }

   protected EntityNoteFX(World world, double double2, double double3, double double4, double double5, double double6, double double7, float float8) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.motionX *= 0.01F;
      this.motionY *= 0.01F;
      this.motionZ *= 0.01F;
      this.motionY += 0.2;
      this.particleRed = MathHelper.sin(((float)double5 + 0.0F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
      this.particleGreen = MathHelper.sin(((float)double5 + 0.33333334F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
      this.particleBlue = MathHelper.sin(((float)double5 + 0.6666667F) * (float) Math.PI * 2.0F) * 0.65F + 0.35F;
      this.particleScale *= 0.75F;
      this.particleScale *= float8;
      this.noteParticleScale = this.particleScale;
      this.particleMaxAge = 6;
      this.noClip = false;
      this.setParticleTextureIndex(64);
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var9 = MathHelper.clamp_float((this.particleAge + float3) / this.particleMaxAge * 32.0F, 0.0F, 1.0F);
      this.particleScale = this.noteParticleScale * var9;
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

      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      if (this.posY == this.prevPosY) {
         this.motionX *= 1.1;
         this.motionZ *= 1.1;
      }

      this.motionX *= 0.66F;
      this.motionY *= 0.66F;
      this.motionZ *= 0.66F;
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
         return new EntityNoteFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
