package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCrit2FX extends EntityFX {
   float field_174839_a;

   protected EntityCrit2FX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      this(world, double2, double3, double4, double5, double6, double7, 1.0F);
   }

   protected EntityCrit2FX(World world, double double2, double double3, double double4, double double5, double double6, double double7, float float8) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.motionX *= 0.1F;
      this.motionY *= 0.1F;
      this.motionZ *= 0.1F;
      this.motionX += double5 * 0.4;
      this.motionY += double6 * 0.4;
      this.motionZ += double7 * 0.4;
      this.particleRed = this.particleGreen = this.particleBlue = (float)(Math.random() * 0.3F + 0.6F);
      this.particleScale *= 0.75F;
      this.particleScale *= float8;
      this.field_174839_a = this.particleScale;
      this.particleMaxAge = (int)(6.0 / (Math.random() * 0.8 + 0.6));
      this.particleMaxAge = (int)(this.particleMaxAge * float8);
      this.noClip = false;
      this.setParticleTextureIndex(65);
      this.onUpdate();
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      float var9 = MathHelper.clamp_float((this.particleAge + float3) / this.particleMaxAge * 32.0F, 0.0F, 1.0F);
      this.particleScale = this.field_174839_a * var9;
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
      this.particleGreen = (float)(this.particleGreen * 0.96);
      this.particleBlue = (float)(this.particleBlue * 0.9);
      this.motionX *= 0.7F;
      this.motionY *= 0.7F;
      this.motionZ *= 0.7F;
      this.motionY -= 0.02F;
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
         return new EntityCrit2FX(world, double3, double4, double5, double6, double7, double8);
      }
   }

   public static class MagicFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         EntityCrit2FX var16 = new EntityCrit2FX(world, double3, double4, double5, double6, double7, double8);
         var16.setRBGColorF(var16.getRedColorF() * 0.3F, var16.getGreenColorF() * 0.8F, var16.getBlueColorF());
         var16.nextTextureIndexX();
         return var16;
      }
   }
}
