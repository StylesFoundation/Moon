package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenBlockBlob extends WorldGenerator {
   private final Block field_150545_a;
   private final int field_150544_b;

   public WorldGenBlockBlob(Block block, int integer) {
      super(false);
      this.field_150545_a = block;
      this.field_150544_b = integer;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      while (true) {
         label50: {
            if (blockPos.getY() > 3) {
               if (world.isAirBlock(blockPos.down())) {
                  break label50;
               }

               Block var7 = world.getBlockState(blockPos.down()).getBlock();
               if (var7 != Blocks.grass && var7 != Blocks.dirt && var7 != Blocks.stone) {
                  break label50;
               }
            }

            if (blockPos.getY() <= 3) {
               return false;
            }

            int var15 = this.field_150544_b;

            for (int var8 = 0; var15 >= 0 && var8 < 3; var8++) {
               int var9 = var15 + random.nextInt(2);
               int var10 = var15 + random.nextInt(2);
               int var11 = var15 + random.nextInt(2);
               float var12 = (var9 + var10 + var11) * 0.333F + 0.5F;

               for (BlockPos var14 : BlockPos.getAllInBox(blockPos.add(-var9, -var10, -var11), blockPos.add(var9, var10, var11))) {
                  if (var14.distanceSq(blockPos) <= var12 * var12) {
                     world.setBlockState(var14, this.field_150545_a.getDefaultState(), 4);
                  }
               }

               blockPos = blockPos.add(-(var15 + 1) + random.nextInt(2 + var15 * 2), -random.nextInt(2), -(var15 + 1) + random.nextInt(2 + var15 * 2));
            }

            return true;
         }

         blockPos = blockPos.down();
      }
   }
}
