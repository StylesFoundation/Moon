package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityAuraFX extends EntityFX {
   protected EntityAuraFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
      float var14 = this.rand.nextFloat() * 0.1F + 0.2F;
      this.particleRed = var14;
      this.particleGreen = var14;
      this.particleBlue = var14;
      this.setParticleTextureIndex(0);
      this.setSize(0.02F, 0.02F);
      this.particleScale = this.particleScale * (this.rand.nextFloat() * 0.6F + 0.5F);
      this.motionX *= 0.02F;
      this.motionY *= 0.02F;
      this.motionZ *= 0.02F;
      this.particleMaxAge = (int)(20.0 / (Math.random() * 0.8 + 0.2));
      this.noClip = true;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.99;
      this.motionY *= 0.99;
      this.motionZ *= 0.99;
      if (this.particleMaxAge-- <= 0) {
         this.setDead();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityAuraFX(world, double3, double4, double5, double6, double7, double8);
      }
   }

   public static class HappyVillagerFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         EntityAuraFX var18 = new EntityAuraFX(world, double3, double4, double5, double6, double7, double8);
         var18.setParticleTextureIndex(82);
         var18.setRBGColorF(1.0F, 1.0F, 1.0F);
         return var18;
      }
   }
}
