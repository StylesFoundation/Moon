package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenVines extends WorldGenerator {
   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      while (blockPos.getY() < 128) {
         if (world.isAirBlock(blockPos)) {
            for (EnumFacing var10 : EnumFacing.Plane.HORIZONTAL.facings()) {
               if (Blocks.vine.canPlaceBlockOnSide(world, blockPos, var10)) {
                  IBlockState var11 = Blocks.vine
                     .getDefaultState()
                     .withProperty(BlockVine.NORTH, var10 == EnumFacing.NORTH)
                     .withProperty(BlockVine.EAST, var10 == EnumFacing.EAST)
                     .withProperty(BlockVine.SOUTH, var10 == EnumFacing.SOUTH)
                     .withProperty(BlockVine.WEST, var10 == EnumFacing.WEST);
                  world.setBlockState(blockPos, var11, 2);
                  break;
               }
            }
         } else {
            blockPos = blockPos.add(random.nextInt(4) - random.nextInt(4), 0, random.nextInt(4) - random.nextInt(4));
         }

         blockPos = blockPos.up();
      }

      return true;
   }
}
