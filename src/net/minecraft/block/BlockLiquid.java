package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public abstract class BlockLiquid extends Block {
   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);

   protected BlockLiquid(Material material) {
      super(material);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, 0));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.setTickRandomly(true);
   }

   @Override
   public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return this.blockMaterial != Material.lava;
   }

   @Override
   public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int integer) {
      return this.blockMaterial == Material.water ? BiomeColorHelper.getWaterColorAtPos(iBlockAccess, blockPos) : 16777215;
   }

   public static float getLiquidHeightPercent(int integer) {
      if (integer >= 8) {
         integer = 0;
      }

      return (integer + 1) / 9.0F;
   }

   protected int getLevel(IBlockAccess iBlockAccess, BlockPos blockPos) {
      return iBlockAccess.getBlockState(blockPos).getBlock().getMaterial() == this.blockMaterial ? iBlockAccess.getBlockState(blockPos).getValue(LEVEL) : -1;
   }

   protected int getEffectiveFlowDecay(IBlockAccess iBlockAccess, BlockPos blockPos) {
      int var6 = this.getLevel(iBlockAccess, blockPos);
      return var6 >= 8 ? 0 : var6;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean canCollideCheck(IBlockState iBlockState, boolean boolean2) {
      return boolean2 && iBlockState.getValue(LEVEL) == 0;
   }

   @Override
   public boolean isBlockSolid(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      Material var7 = iBlockAccess.getBlockState(blockPos).getBlock().getMaterial();
      return var7 != this.blockMaterial && (enumFacing == EnumFacing.UP || var7 != Material.ice && super.isBlockSolid(iBlockAccess, blockPos, enumFacing));
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return iBlockAccess.getBlockState(blockPos).getBlock().getMaterial() != this.blockMaterial
         && (enumFacing == EnumFacing.UP || super.shouldSideBeRendered(iBlockAccess, blockPos, enumFacing));
   }

   public boolean shouldRenderSides(IBlockAccess iBlockAccess, BlockPos blockPos) {
      for (int var6 = -1; var6 <= 1; var6++) {
         for (int var7 = -1; var7 <= 1; var7++) {
            Block var9 = iBlockAccess.getBlockState(blockPos.add(var6, 0, var7)).getBlock();
            Material var10 = var9.getMaterial();
            if (var10 != this.blockMaterial && !var9.isFullBlock()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos blockPos, IBlockState iBlockState) {
      return null;
   }

   @Override
   public int getRenderType() {
      return 1;
   }

   @Override
   public Item getItemDropped(IBlockState iBlockState, Random random, int integer) {
      return null;
   }

   @Override
   public int quantityDropped(Random random) {
      return 0;
   }

   protected Vec3 getFlowVector(IBlockAccess iBlockAccess, BlockPos blockPos) {
      Vec3 var6 = new Vec3(0.0, 0.0, 0.0);
      int var7 = this.getEffectiveFlowDecay(iBlockAccess, blockPos);

      for (EnumFacing var9 : EnumFacing.Plane.HORIZONTAL) {
         BlockPos var10 = blockPos.offset(var9);
         int var11 = this.getEffectiveFlowDecay(iBlockAccess, var10);
         if (var11 < 0) {
            if (!iBlockAccess.getBlockState(var10).getBlock().getMaterial().blocksMovement()) {
               var11 = this.getEffectiveFlowDecay(iBlockAccess, var10.down());
               if (var11 >= 0) {
                  int var12 = var11 - (var7 - 8);
                  var6 = var6.addVector(
                     (var10.getX() - blockPos.getX()) * var12, (var10.getY() - blockPos.getY()) * var12, (var10.getZ() - blockPos.getZ()) * var12
                  );
               }
            }
         } else if (var11 >= 0) {
            int var17 = var11 - var7;
            var6 = var6.addVector((var10.getX() - blockPos.getX()) * var17, (var10.getY() - blockPos.getY()) * var17, (var10.getZ() - blockPos.getZ()) * var17);
         }
      }

      if (iBlockAccess.getBlockState(blockPos).getValue(LEVEL) >= 8) {
         for (EnumFacing var14 : EnumFacing.Plane.HORIZONTAL) {
            BlockPos var15 = blockPos.offset(var14);
            if (this.isBlockSolid(iBlockAccess, var15, var14) || this.isBlockSolid(iBlockAccess, var15.up(), var14)) {
               var6 = var6.normalize().addVector(0.0, -6.0, 0.0);
               break;
            }
         }
      }

      return var6.normalize();
   }

   @Override
   public Vec3 modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3 vec3) {
      return vec3.add(this.getFlowVector(world, blockPos));
   }

   @Override
   public int tickRate(World world) {
      return this.blockMaterial == Material.water ? 5 : (this.blockMaterial == Material.lava ? (world.provider.getHasNoSky() ? 10 : 30) : 0);
   }

   @Override
   public int getMixedBrightnessForBlock(IBlockAccess iBlockAccess, BlockPos blockPos) {
      int var5 = iBlockAccess.getCombinedLight(blockPos, 0);
      int var6 = iBlockAccess.getCombinedLight(blockPos.up(), 0);
      int var7 = var5 & 0xFF;
      int var8 = var6 & 0xFF;
      int var9 = var5 >> 16 & 0xFF;
      int var10 = var6 >> 16 & 0xFF;
      return Math.max(var7, var8) | Math.max(var9, var10) << 16;
   }

   @Override
   public EnumWorldBlockLayer getBlockLayer() {
      return this.blockMaterial == Material.water ? EnumWorldBlockLayer.TRANSLUCENT : EnumWorldBlockLayer.SOLID;
   }

   @Override
   public void randomDisplayTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      double var8 = blockPos.getX();
      double var10 = blockPos.getY();
      double var12 = blockPos.getZ();
      if (this.blockMaterial == Material.water) {
         int var14 = iBlockState.getValue(LEVEL);
         if (var14 > 0 && var14 < 8) {
            if (random.nextInt(64) == 0) {
               world.playSound(var8 + 0.5, var10 + 0.5, var12 + 0.5, "liquid.water", random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
         } else if (random.nextInt(10) == 0) {
            world.spawnParticle(EnumParticleTypes.SUSPENDED, var8 + random.nextFloat(), var10 + random.nextFloat(), var12 + random.nextFloat(), 0.0, 0.0, 0.0);
         }
      }

      if (this.blockMaterial == Material.lava
         && world.getBlockState(blockPos.up()).getBlock().getMaterial() == Material.air
         && !world.getBlockState(blockPos.up()).getBlock().isOpaqueCube()) {
         if (random.nextInt(100) == 0) {
            double var21 = var8 + random.nextFloat();
            double var16 = var10 + this.maxY;
            double var18 = var12 + random.nextFloat();
            world.spawnParticle(EnumParticleTypes.LAVA, var21, var16, var18, 0.0, 0.0, 0.0);
            world.playSound(var21, var16, var18, "liquid.lavapop", 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
         }

         if (random.nextInt(200) == 0) {
            world.playSound(var8, var10, var12, "liquid.lava", 0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
         }
      }

      if (random.nextInt(10) == 0 && World.doesBlockHaveSolidTopSurface(world, blockPos.down())) {
         Material var22 = world.getBlockState(blockPos.down(2)).getBlock().getMaterial();
         if (!var22.blocksMovement() && !var22.isLiquid()) {
            double var15 = var8 + random.nextFloat();
            double var17 = var10 - 1.05;
            double var19 = var12 + random.nextFloat();
            if (this.blockMaterial == Material.water) {
               world.spawnParticle(EnumParticleTypes.DRIP_WATER, var15, var17, var19, 0.0, 0.0, 0.0);
            } else {
               world.spawnParticle(EnumParticleTypes.DRIP_LAVA, var15, var17, var19, 0.0, 0.0, 0.0);
            }
         }
      }
   }

   public static double getFlowDirection(IBlockAccess iBlockAccess, BlockPos blockPos, Material material) {
      Vec3 var6 = getFlowingBlock(material).getFlowVector(iBlockAccess, blockPos);
      return var6.xCoord == 0.0 && var6.zCoord == 0.0 ? -1000.0 : MathHelper.atan2(var6.zCoord, var6.xCoord) - (Math.PI / 2);
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      this.checkForMixing(world, blockPos, iBlockState);
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      this.checkForMixing(world, blockPos, iBlockState);
   }

   public boolean checkForMixing(World world, BlockPos blockPos, IBlockState iBlockState) {
      if (this.blockMaterial == Material.lava) {
         boolean var7 = false;

         for (EnumFacing var11 : EnumFacing.values()) {
            if (var11 != EnumFacing.DOWN && world.getBlockState(blockPos.offset(var11)).getBlock().getMaterial() == Material.water) {
               var7 = true;
               break;
            }
         }

         if (var7) {
            Integer var12 = iBlockState.getValue(LEVEL);
            if (var12 == 0) {
               world.setBlockState(blockPos, Blocks.obsidian.getDefaultState());
               this.triggerMixEffects(world, blockPos);
               return true;
            }

            if (var12 <= 4) {
               world.setBlockState(blockPos, Blocks.cobblestone.getDefaultState());
               this.triggerMixEffects(world, blockPos);
               return true;
            }
         }
      }

      return false;
   }

   protected void triggerMixEffects(World world, BlockPos blockPos) {
      double var6 = blockPos.getX();
      double var8 = blockPos.getY();
      double var10 = blockPos.getZ();
      world.playSoundEffect(var6 + 0.5, var8 + 0.5, var10 + 0.5, "random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

      for (int var12 = 0; var12 < 8; var12++) {
         world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6 + Math.random(), var8 + 1.2, var10 + Math.random(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   public IBlockState getStateFromMeta(int integer) {
      return this.getDefaultState().withProperty(LEVEL, integer);
   }

   @Override
   public int getMetaFromState(IBlockState iBlockState) {
      return iBlockState.getValue(LEVEL);
   }

   @Override
   protected BlockState createBlockState() {
      return new BlockState(this, LEVEL);
   }

   public static BlockDynamicLiquid getFlowingBlock(Material material) {
      if (material == Material.water) {
         return Blocks.flowing_water;
      } else if (material == Material.lava) {
         return Blocks.flowing_lava;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }

   public static BlockStaticLiquid getStaticBlock(Material material) {
      if (material == Material.water) {
         return Blocks.water;
      } else if (material == Material.lava) {
         return Blocks.lava;
      } else {
         throw new IllegalArgumentException("Invalid material");
      }
   }
}
