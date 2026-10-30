package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDragonEgg extends Block {
   public BlockDragonEgg() {
      super(Material.dragonEgg, MapColor.blackColor);
      this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 1.0F, 0.9375F);
   }

   @Override
   public void onBlockAdded(World world, BlockPos blockPos, IBlockState iBlockState) {
      world.scheduleUpdate(blockPos, this, this.tickRate(world));
   }

   @Override
   public void onNeighborBlockChange(World world, BlockPos blockPos, IBlockState iBlockState, Block block) {
      world.scheduleUpdate(blockPos, this, this.tickRate(world));
   }

   @Override
   public void updateTick(World world, BlockPos blockPos, IBlockState iBlockState, Random random) {
      this.checkFall(world, blockPos);
   }

   private void checkFall(World world, BlockPos blockPos) {
      if (BlockFalling.canFallInto(world, blockPos.down()) && blockPos.getY() >= 0) {
         byte var6 = 32;
         if (!BlockFalling.fallInstantly && world.Method326(blockPos.add(-32, -32, -32), blockPos.add(var6, var6, var6))) {
            world.spawnEntityInWorld(new EntityFallingBlock(world, blockPos.getX() + 0.5F, blockPos.getY(), blockPos.getZ() + 0.5F, this.getDefaultState()));
         } else {
            world.setBlockToAir(blockPos);
            BlockPos var7 = blockPos;

            while (BlockFalling.canFallInto(world, var7) && var7.getY() > 0) {
               var7 = var7.down();
            }

            if (var7.getY() > 0) {
               world.setBlockState(var7, this.getDefaultState(), 2);
            }
         }
      }
   }

   @Override
   public boolean onBlockActivated(
      World world, BlockPos blockPos, IBlockState iBlockState, EntityPlayer entityPlayer, EnumFacing enumFacing, float float6, float float7, float float8
   ) {
      this.teleport(world, blockPos);
      return true;
   }

   @Override
   public void onBlockClicked(World world, BlockPos blockPos, EntityPlayer entityPlayer) {
      this.teleport(world, blockPos);
   }

   private void teleport(World world, BlockPos blockPos) {
      IBlockState var6 = world.getBlockState(blockPos);
      if (var6.getBlock() == this) {
         for (int var7 = 0; var7 < 1000; var7++) {
            BlockPos var8 = blockPos.add(
               world.rand.nextInt(16) - world.rand.nextInt(16), world.rand.nextInt(8) - world.rand.nextInt(8), world.rand.nextInt(16) - world.rand.nextInt(16)
            );
            if (world.getBlockState(var8).getBlock().blockMaterial == Material.air) {
               if (world.isRemote) {
                  for (int var9 = 0; var9 < 128; var9++) {
                     double var10 = world.rand.nextDouble();
                     float var12 = (world.rand.nextFloat() - 0.5F) * 0.2F;
                     float var13 = (world.rand.nextFloat() - 0.5F) * 0.2F;
                     float var14 = (world.rand.nextFloat() - 0.5F) * 0.2F;
                     double var15 = var8.getX() + (blockPos.getX() - var8.getX()) * var10 + (world.rand.nextDouble() - 0.5) + 0.5;
                     double var17 = var8.getY() + (blockPos.getY() - var8.getY()) * var10 + world.rand.nextDouble() - 0.5;
                     double var19 = var8.getZ() + (blockPos.getZ() - var8.getZ()) * var10 + (world.rand.nextDouble() - 0.5) + 0.5;
                     world.spawnParticle(EnumParticleTypes.PORTAL, var15, var17, var19, var12, var13, var14);
                  }
               } else {
                  world.setBlockState(var8, var6, 2);
                  world.setBlockToAir(blockPos);
               }

               return;
            }
         }
      }
   }

   @Override
   public int tickRate(World world) {
      return 5;
   }

   @Override
   public boolean isOpaqueCube() {
      return false;
   }

   @Override
   public boolean isFullCube() {
      return false;
   }

   @Override
   public boolean shouldSideBeRendered(IBlockAccess iBlockAccess, BlockPos blockPos, EnumFacing enumFacing) {
      return true;
   }

   @Override
   public Item getItem(World world, BlockPos blockPos) {
      return null;
   }
}
