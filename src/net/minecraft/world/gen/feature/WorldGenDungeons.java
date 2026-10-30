package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenDungeons extends WorldGenerator {
   private static final Logger field_175918_a = LogManager.getLogger();
   private static final String[] SPAWNERTYPES = new String[]{"Skeleton", "Zombie", "Zombie", "Spider"};
   private static final List<WeightedRandomChestContent> CHESTCONTENT = Lists.newArrayList(
      new WeightedRandomChestContent[]{
         new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.string, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 4),
         new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 4),
         new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2),
         new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
      }
   );

   @Override
   public boolean generate(World world, Random random, BlockPos blockPos) {
      int var8 = random.nextInt(2) + 2;
      int var9 = -var8 - 1;
      int var10 = var8 + 1;
      int var13 = random.nextInt(2) + 2;
      int var14 = -var13 - 1;
      int var15 = var13 + 1;
      int var16 = 0;

      for (int var17 = var9; var17 <= var10; var17++) {
         for (int var18 = -1; var18 <= 4; var18++) {
            for (int var19 = var14; var19 <= var15; var19++) {
               BlockPos var20 = blockPos.add(var17, var18, var19);
               boolean var22 = world.getBlockState(var20).getBlock().getMaterial().isSolid();
               if (var18 == -1 && !var22) {
                  return false;
               }

               if (var18 == 4 && !var22) {
                  return false;
               }

               if ((var17 == var9 || var17 == var10 || var19 == var14 || var19 == var15)
                  && var18 == 0
                  && world.isAirBlock(var20)
                  && world.isAirBlock(var20.up())) {
                  var16++;
               }
            }
         }
      }

      if (var16 >= 1 && var16 <= 5) {
         for (int var26 = var9; var26 <= var10; var26++) {
            for (int var29 = 3; var29 >= -1; var29--) {
               for (int var31 = var14; var31 <= var15; var31++) {
                  BlockPos var33 = blockPos.add(var26, var29, var31);
                  if (var26 != var9 && var29 != -1 && var31 != var14 && var26 != var10 && var29 != 4 && var31 != var15) {
                     if (world.getBlockState(var33).getBlock() != Blocks.chest) {
                        world.setBlockToAir(var33);
                     }
                  } else if (var33.getY() >= 0 && !world.getBlockState(var33.down()).getBlock().getMaterial().isSolid()) {
                     world.setBlockToAir(var33);
                  } else if (world.getBlockState(var33).getBlock().getMaterial().isSolid() && world.getBlockState(var33).getBlock() != Blocks.chest) {
                     if (var29 == -1 && random.nextInt(4) != 0) {
                        world.setBlockState(var33, Blocks.mossy_cobblestone.getDefaultState(), 2);
                     } else {
                        world.setBlockState(var33, Blocks.cobblestone.getDefaultState(), 2);
                     }
                  }
               }
            }
         }

         for (int var27 = 0; var27 < 2; var27++) {
            for (int var30 = 0; var30 < 3; var30++) {
               int var32 = blockPos.getX() + random.nextInt(var8 * 2 + 1) - var8;
               int var34 = blockPos.getY();
               int var21 = blockPos.getZ() + random.nextInt(var13 * 2 + 1) - var13;
               BlockPos var35 = new BlockPos(var32, var34, var21);
               if (world.isAirBlock(var35)) {
                  int var23 = 0;

                  for (EnumFacing var25 : EnumFacing.Plane.HORIZONTAL) {
                     if (world.getBlockState(var35.offset(var25)).getBlock().getMaterial().isSolid()) {
                        var23++;
                     }
                  }

                  if (var23 == 1) {
                     world.setBlockState(var35, Blocks.chest.correctFacing(world, var35, Blocks.chest.getDefaultState()), 2);
                     List var36 = WeightedRandomChestContent.func_177629_a(CHESTCONTENT, Items.enchanted_book.getRandom(random));
                     TileEntity var37 = world.getTileEntity(var35);
                     if (var37 instanceof TileEntityChest) {
                        WeightedRandomChestContent.generateChestContents(random, var36, (TileEntityChest)var37, 8);
                     }
                     break;
                  }
               }
            }
         }

         world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), 2);
         TileEntity var28 = world.getTileEntity(blockPos);
         if (var28 instanceof TileEntityMobSpawner) {
            ((TileEntityMobSpawner)var28).getSpawnerBaseLogic().setEntityName(this.pickMobSpawner(random));
         } else {
            field_175918_a.error("Failed to fetch mob spawner entity at (" + blockPos.getX() + ", " + blockPos.getY() + ", " + blockPos.getZ() + ")");
         }

         return true;
      } else {
         return false;
      }
   }

   private String pickMobSpawner(Random random) {
      return SPAWNERTYPES[random.nextInt(SPAWNERTYPES.length)];
   }
}
