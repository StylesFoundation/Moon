package net.minecraft.client.particle;

import net.minecraft.world.World;

public class EntityFishWakeFX extends EntityFX {
   protected EntityFishWakeFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.motionX *= 0.3F;
      this.motionY = Math.random() * 0.2F + 0.1F;
      this.motionZ *= 0.3F;
      this.particleRed = 1.0F;
      this.particleGreen = 1.0F;
      this.particleBlue = 1.0F;
      this.setParticleTextureIndex(19);
      this.setSize(0.01F, 0.01F);
      this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.particleGravity = 0.0F;
      this.motionX = double5;
      this.motionY = double6;
      this.motionZ = double7;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY = this.motionY - this.particleGravity;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.98F;
      this.motionY *= 0.98F;
      this.motionZ *= 0.98F;
      int var3 = 60 - this.particleMaxAge;
      float var4 = var3 * 0.001F;
      this.setSize(var4, var4);
      this.setParticleTextureIndex(19 + var3 % 4);
      if (this.particleMaxAge-- <= 0) {
         this.setDead();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityFishWakeFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
