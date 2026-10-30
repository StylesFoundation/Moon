package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySpellParticleFX extends EntityFX {
   private static final Random RANDOM = new Random();
   private int baseSpellTextureIndex = 128;

   protected EntitySpellParticleFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, 0.5 - RANDOM.nextDouble(), double6, 0.5 - RANDOM.nextDouble());
      this.motionY *= 0.2F;
      if (double5 == 0.0 && double7 == 0.0) {
         this.motionX *= 0.1F;
         this.motionZ *= 0.1F;
      }

      this.particleScale *= 0.75F;
      this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.noClip = false;
   }

   @Override
   public void renderParticle(WorldRenderer worldRenderer, Entity entity, float float3, float float4, float float5, float float6, float float7, float float8) {
      MathHelper.clamp_float((this.particleAge + float3) / this.particleMaxAge * 32.0F, 0.0F, 1.0F);
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

      this.setParticleTextureIndex(this.baseSpellTextureIndex + (7 - this.particleAge * 8 / this.particleMaxAge));
      this.motionY += 0.004;
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

   public void setBaseSpellTextureIndex(int integer) {
      this.baseSpellTextureIndex = integer;
   }

   @Override
   public boolean Method239() {
      return true;
   }

   public static class AmbientMobFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         EntitySpellParticleFX var16 = new EntitySpellParticleFX(world, double3, double4, double5, double6, double7, double8);
         var16.setAlphaF(0.15F);
         var16.setRBGColorF((float)double6, (float)double7, (float)double8);
         return var16;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntitySpellParticleFX(world, double3, double4, double5, double6, double7, double8);
      }
   }

   public static class InstantFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         EntitySpellParticleFX var18 = new EntitySpellParticleFX(world, double3, double4, double5, double6, double7, double8);
         var18.setBaseSpellTextureIndex(144);
         return var18;
      }
   }

   public static class MobFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         EntitySpellParticleFX var16 = new EntitySpellParticleFX(world, double3, double4, double5, double6, double7, double8);
         var16.setRBGColorF((float)double6, (float)double7, (float)double8);
         return var16;
      }
   }

   public static class WitchFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         EntitySpellParticleFX var18 = new EntitySpellParticleFX(world, double3, double4, double5, double6, double7, double8);
         var18.setBaseSpellTextureIndex(144);
         float var19 = world.rand.nextFloat() * 0.5F + 0.35F;
         var18.setRBGColorF(var19, 0.0F * var19, var19);
         return var18;
      }
   }
}
