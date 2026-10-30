package net.minecraft.world.gen.feature;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class WorldGeneratorBonusChest extends WorldGenerator {
   private final List<WeightedRandomChestContent> chestItems;
   private final int itemsToGenerateInBonusChest;

   public WorldGeneratorBonusChest(List<WeightedRandomChestContent> list, int integer) {
      this.chestItems = list;
      this.itemsToGenerateInBonusChest = integer;
   }

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      Block var7;
      while (((var7 = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.air || var7.getMaterial() == Material.leaves) && blockPos.getY() > 1) {
         blockPos = blockPos.down();
      }

      if (blockPos.getY() >= 1) {
         blockPos = blockPos.up();

         for (int var8 = 0; var8 < 4; var8++) {
            BlockPos var9 = blockPos.add(random.nextInt(4) - random.nextInt(4), random.nextInt(3) - random.nextInt(3), random.nextInt(4) - random.nextInt(4));
            if (world.isAirBlock(var9) && World.doesBlockHaveSolidTopSurface(world, var9.down())) {
               world.setBlockState(var9, Blocks.chest.getDefaultState(), 2);
               TileEntity var10 = world.getTileEntity(var9);
               if (var10 instanceof TileEntityChest) {
                  WeightedRandomChestContent.generateChestContents(random, this.chestItems, (TileEntityChest)var10, this.itemsToGenerateInBonusChest);
               }

               BlockPos var11 = var9.east();
               BlockPos var12 = var9.west();
               BlockPos var13 = var9.north();
               BlockPos var14 = var9.south();
               if (world.isAirBlock(var12) && World.doesBlockHaveSolidTopSurface(world, var12.down())) {
                  world.setBlockState(var12, Blocks.torch.getDefaultState(), 2);
               }

               if (world.isAirBlock(var11) && World.doesBlockHaveSolidTopSurface(world, var11.down())) {
                  world.setBlockState(var11, Blocks.torch.getDefaultState(), 2);
               }

               if (world.isAirBlock(var13) && World.doesBlockHaveSolidTopSurface(world, var13.down())) {
                  world.setBlockState(var13, Blocks.torch.getDefaultState(), 2);
               }

               if (world.isAirBlock(var14) && World.doesBlockHaveSolidTopSurface(world, var14.down())) {
                  world.setBlockState(var14, Blocks.torch.getDefaultState(), 2);
               }

               return true;
            }
         }
      }

      return false;
   }
}
