package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenSpikes extends WorldGenerator {
   private final Block baseBlockRequired;

   public WorldGenSpikes(Block block) {
      this.baseBlockRequired = block;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      if (world.isAirBlock(blockPos) && world.getBlockState(blockPos.down()).getBlock() == this.baseBlockRequired) {
         int var7 = random.nextInt(32) + 6;
         int var8 = random.nextInt(4) + 1;
         BlockPos.MutableBlockPos var9 = new BlockPos.MutableBlockPos();

         for (int var10 = blockPos.getX() - var8; var10 <= blockPos.getX() + var8; var10++) {
            for (int var11 = blockPos.getZ() - var8; var11 <= blockPos.getZ() + var8; var11++) {
               int var12 = var10 - blockPos.getX();
               int var13 = var11 - blockPos.getZ();
               if (var12 * var12 + var13 * var13 <= var8 * var8 + 1
                  && world.getBlockState(var9.set(var10, blockPos.getY() - 1, var11)).getBlock() != this.baseBlockRequired) {
                  return false;
               }
            }
         }

         for (int var15 = blockPos.getY(); var15 < blockPos.getY() + var7 && var15 < 256; var15++) {
            for (int var17 = blockPos.getX() - var8; var17 <= blockPos.getX() + var8; var17++) {
               for (int var18 = blockPos.getZ() - var8; var18 <= blockPos.getZ() + var8; var18++) {
                  int var19 = var17 - blockPos.getX();
                  int var14 = var18 - blockPos.getZ();
                  if (var19 * var19 + var14 * var14 <= var8 * var8 + 1) {
                     world.setBlockState(new BlockPos(var17, var15, var18), Blocks.obsidian.getDefaultState(), 2);
                  }
               }
            }
         }

         EntityEnderCrystal var16 = new EntityEnderCrystal(world);
         var16.setLocationAndAngles(blockPos.getX() + 0.5F, blockPos.getY() + var7, blockPos.getZ() + 0.5F, random.nextFloat() * 360.0F, 0.0F);
         world.spawnEntityInWorld(var16);
         world.setBlockState(blockPos.up(var7), Blocks.bedrock.getDefaultState(), 2);
         return true;
      } else {
         return false;
      }
   }
}
