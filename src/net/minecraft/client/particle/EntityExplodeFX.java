package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityExplodeFX extends EntityFX {
   protected EntityExplodeFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
      this.motionX = double5 + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.motionY = double6 + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.motionZ = double7 + (Math.random() * 2.0 - 1.0) * 0.05F;
      this.particleRed = this.particleGreen = this.particleBlue = this.rand.nextFloat() * 0.3F + 0.7F;
      this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F;
      this.particleMaxAge = (int)(16.0 / (this.rand.nextFloat() * 0.8 + 0.2)) + 2;
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
      this.motionY += 0.004;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.9F;
      this.motionY *= 0.9F;
      this.motionZ *= 0.9F;
      if (this.onGround) {
         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityExplodeFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
