package net.minecraft.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityRainFX extends EntityFX {
   protected EntityRainFX(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.motionX *= 0.3F;
      this.motionY = Math.random() * 0.2F + 0.1F;
      this.motionZ *= 0.3F;
      this.particleRed = 1.0F;
      this.particleGreen = 1.0F;
      this.particleBlue = 1.0F;
      this.setParticleTextureIndex(19 + this.rand.nextInt(4));
      this.setSize(0.01F, 0.01F);
      this.particleGravity = 0.06F;
      this.particleMaxAge = (int)(8.0 / (Math.random() * 0.8 + 0.2));
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
      if (this.particleMaxAge-- <= 0) {
         this.setDead();
      }

      if (this.onGround) {
         if (Math.random() < 0.5) {
            this.setDead();
         }

         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
      }

      BlockPos var1 = new BlockPos(this);
      IBlockState var2 = this.worldObj.getBlockState(var1);
      Block var3 = var2.getBlock();
      var3.setBlockBoundsBasedOnState(this.worldObj, var1);
      Material var4 = var2.getBlock().getMaterial();
      if (var4.isLiquid() || var4.isSolid()) {
         double var5;
         if (var2.getBlock() instanceof BlockLiquid) {
            var5 = 1.0F - BlockLiquid.getLiquidHeightPercent(var2.getValue(BlockLiquid.LEVEL));
         } else {
            var5 = var3.getBlockBoundsMaxY();
         }

         double var7 = MathHelper.floor_double(this.posY) + var5;
         if (this.posY < var7) {
            this.setDead();
         }
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityRainFX(world, double3, double4, double5);
      }
   }
}
