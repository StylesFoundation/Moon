package net.minecraft.client.particle;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityBubbleFX extends EntityFX {
   protected EntityBubbleFX(World world, double double2, double double3, double double4, double double5, double double6, double double7) {
      super(world, double2, double3, double4, double5, double6, double7);
      this.particleRed = 1.0F;
      this.particleGreen = 1.0F;
      this.particleBlue = 1.0F;
      this.setParticleTextureIndex(32);
      this.setSize(0.02F, 0.02F);
      this.particleScale = this.particleScale * (this.rand.nextFloat() * 0.6F + 0.2F);
      this.motionX = double5 * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.motionY = double6 * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.motionZ = double7 * 0.2F + (Math.random() * 2.0 - 1.0) * 0.02F;
      this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY += 0.002;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.85F;
      this.motionY *= 0.85F;
      this.motionZ *= 0.85F;
      if (this.worldObj.getBlockState(new BlockPos(this)).getBlock().getMaterial() != Material.water) {
         this.setDead();
      }

      if (this.particleMaxAge-- <= 0) {
         this.setDead();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityBubbleFX(world, double3, double4, double5, double6, double7, double8);
      }
   }
}
