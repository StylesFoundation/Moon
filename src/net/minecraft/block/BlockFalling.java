package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockFalling extends Block {
   public static boolean fallInstantly;

   public BlockFalling() {
      super(Material.sand);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public BlockFalling(Material material) {
      super(material);
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
      if (!world.isRemote) {
         this.checkFallable(world, blockPos);
      }
   }

   private void checkFallable(World world, BlockPos blockPos) {
      if (canFallInto(world, blockPos.down()) && blockPos.getY() >= 0) {
         byte var6 = 32;
         if (fallInstantly || !world.Method326(blockPos.add(-32, -32, -32), blockPos.add(var6, var6, var6))) {
            world.setBlockToAir(blockPos);
            BlockPos var8 = blockPos.down();

            while (canFallInto(world, var8) && var8.getY() > 0) {
               var8 = var8.down();
            }

            if (var8.getY() > 0) {
               world.setBlockState(var8.up(), this.getDefaultState());
            }
         } else if (!world.isRemote) {
            EntityFallingBlock var7 = new EntityFallingBlock(
               world, blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, world.getBlockState(blockPos)
            );
            this.onStartFalling(var7);
            world.spawnEntityInWorld(var7);
         }
      }
   }

   protected void onStartFalling(EntityFallingBlock entityFallingBlock) {
   }

   @Override
   public int tickRate(World world) {
      return 2;
   }

   public static boolean canFallInto(World world, BlockPos blockPos) {
      Block var5 = world.getBlockState(blockPos).getBlock();
      Material var6 = var5.blockMaterial;
      return var5 == Blocks.fire || var6 == Material.air || var6 == Material.water || var6 == Material.lava;
   }

   public void onEndFalling(World world, BlockPos blockPos) {
   }
}
