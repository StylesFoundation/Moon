package net.minecraft.client.particle;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDropParticleFX extends EntityFX {
   private final Material materialType;
   private int bobTimer;

   protected EntityDropParticleFX(World world, double double2, double double3, double double4, Material material) {
      super(world, double2, double3, double4, 0.0, 0.0, 0.0);
      this.motionX = this.motionY = this.motionZ = 0.0;
      if (material == Material.water) {
         this.particleRed = 0.0F;
         this.particleGreen = 0.0F;
         this.particleBlue = 1.0F;
      } else {
         this.particleRed = 1.0F;
         this.particleGreen = 0.0F;
         this.particleBlue = 0.0F;
      }

      this.setParticleTextureIndex(113);
      this.setSize(0.01F, 0.01F);
      this.particleGravity = 0.06F;
      this.materialType = material;
      this.bobTimer = 40;
      this.particleMaxAge = (int)(64.0 / (Math.random() * 0.8 + 0.2));
      this.motionX = this.motionY = this.motionZ = 0.0;
   }

   @Override
   public int getBrightnessForRender(float float1) {
      return this.materialType == Material.water ? super.getBrightnessForRender(float1) : 257;
   }

   @Override
   public float getBrightness(float float1) {
      return this.materialType == Material.water ? super.getBrightness(float1) : 1.0F;
   }

   @Override
   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.materialType == Material.water) {
         this.particleRed = 0.2F;
         this.particleGreen = 0.3F;
         this.particleBlue = 1.0F;
      } else {
         this.particleRed = 1.0F;
         this.particleGreen = 16.0F / (40 - this.bobTimer + 16);
         this.particleBlue = 4.0F / (40 - this.bobTimer + 8);
      }

      this.motionY = this.motionY - this.particleGravity;
      if (this.bobTimer-- > 0) {
         this.motionX *= 0.02;
         this.motionY *= 0.02;
         this.motionZ *= 0.02;
         this.setParticleTextureIndex(113);
      } else {
         this.setParticleTextureIndex(112);
      }

      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.98F;
      this.motionY *= 0.98F;
      this.motionZ *= 0.98F;
      if (this.particleMaxAge-- <= 0) {
         this.setDead();
      }

      if (this.onGround) {
         if (this.materialType == Material.water) {
            this.setDead();
            this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
         } else {
            this.setParticleTextureIndex(114);
         }

         this.motionX *= 0.7F;
         this.motionZ *= 0.7F;
      }

      BlockPos var3 = new BlockPos(this);
      IBlockState var4 = this.worldObj.getBlockState(var3);
      Material var5 = var4.getBlock().getMaterial();
      if (var5.isLiquid() || var5.isSolid()) {
         double var6 = 0.0;
         if (var4.getBlock() instanceof BlockLiquid) {
            var6 = BlockLiquid.getLiquidHeightPercent(var4.getValue(BlockLiquid.LEVEL));
         }

         double var8 = MathHelper.floor_double(this.posY) + 1 - var6;
         if (this.posY < var8) {
            this.setDead();
         }
      }
   }

   public static class LavaFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityDropParticleFX(world, double3, double4, double5, Material.lava);
      }
   }

   public static class WaterFactory implements IParticleFactory {
      @Override
      public EntityFX getEntityFX(
         int integer, World world, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
      ) {
         return new EntityDropParticleFX(world, double3, double4, double5, Material.water);
      }
   }
}
