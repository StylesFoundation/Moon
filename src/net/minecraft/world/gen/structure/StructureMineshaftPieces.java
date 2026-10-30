package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureMineshaftPieces {
   private static final List<WeightedRandomChestContent> CHEST_CONTENT_WEIGHT_LIST = Lists.newArrayList(
      new WeightedRandomChestContent[]{
         new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
         new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
         new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
         new WeightedRandomChestContent(Items.dye, EnumDyeColor.BLUE.getDyeDamage(), 4, 9, 5),
         new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3),
         new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10),
         new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1),
         new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1),
         new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10),
         new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10),
         new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
         new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1)
      }
   );

   public static void registerStructurePieces() {
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Corridor.class, "MSCorridor");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Cross.class, "MSCrossing");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Room.class, "MSRoom");
      MapGenStructureIO.registerStructureComponent(StructureMineshaftPieces.Stairs.class, "MSStairs");
   }

   private static StructureComponent func_175892_a(
      List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
   ) {
      int var10 = random.nextInt(100);
      if (var10 >= 80) {
         StructureBoundingBox var11 = StructureMineshaftPieces.Cross.func_175813_a(list, random, integer3, integer4, integer5, enumFacing);
         if (var11 != null) {
            return new StructureMineshaftPieces.Cross(integer7, random, var11, enumFacing);
         }
      } else if (var10 >= 70) {
         StructureBoundingBox var12 = StructureMineshaftPieces.Stairs.func_175812_a(list, random, integer3, integer4, integer5, enumFacing);
         if (var12 != null) {
            return new StructureMineshaftPieces.Stairs(integer7, random, var12, enumFacing);
         }
      } else {
         StructureBoundingBox var13 = StructureMineshaftPieces.Corridor.func_175814_a(list, random, integer3, integer4, integer5, enumFacing);
         if (var13 != null) {
            return new StructureMineshaftPieces.Corridor(integer7, random, var13, enumFacing);
         }
      }

      return null;
   }

   private static StructureComponent func_175890_b(
      StructureComponent structureComponent,
      List<StructureComponent> list,
      Random random,
      int integer4,
      int integer5,
      int integer6,
      EnumFacing enumFacing,
      int integer8
   ) {
      if (integer8 > 8) {
         return null;
      }

      if (Math.abs(integer4 - structureComponent.getBoundingBox().minX) <= 80 && Math.abs(integer6 - structureComponent.getBoundingBox().minZ) <= 80) {
         StructureComponent var11 = func_175892_a(list, random, integer4, integer5, integer6, enumFacing, integer8 + 1);
         if (var11 != null) {
            list.add(var11);
            var11.buildComponent(structureComponent, list, random);
         }

         return var11;
      } else {
         return null;
      }
   }

   public static class Corridor extends StructureComponent {
      private boolean hasRails;
      private boolean hasSpiders;
      private boolean spawnerPlaced;
      private int sectionCount;

      public Corridor() {
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         nBTTagCompound.setBoolean("hr", this.hasRails);
         nBTTagCompound.setBoolean("sc", this.hasSpiders);
         nBTTagCompound.setBoolean("hps", this.spawnerPlaced);
         nBTTagCompound.setInteger("Num", this.sectionCount);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         this.hasRails = nBTTagCompound.getBoolean("hr");
         this.hasSpiders = nBTTagCompound.getBoolean("sc");
         this.spawnerPlaced = nBTTagCompound.getBoolean("hps");
         this.sectionCount = nBTTagCompound.getInteger("Num");
      }

      public Corridor(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
         this.hasRails = random.nextInt(3) == 0;
         this.hasSpiders = !this.hasRails && random.nextInt(23) == 0;
         if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
            this.sectionCount = structureBoundingBox.getXSize() / 5;
         } else {
            this.sectionCount = structureBoundingBox.getZSize() / 5;
         }
      }

      // $VF: Unable to simplify switch on enum
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      public static StructureBoundingBox func_175814_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing
      ) {
         StructureBoundingBox var9 = new StructureBoundingBox(integer3, integer4, integer5, integer3, integer4 + 2, integer5);

         int var10;
         for (var10 = random.nextInt(3) + 2; var10 > 0; var10--) {
            int var11 = var10 * 5;
            switch (enumFacing) {
               case NORTH:
                  var9.maxX = integer3 + 2;
                  var9.minZ = integer5 - (var11 - 1);
                  break;
               case SOUTH:
                  var9.maxX = integer3 + 2;
                  var9.maxZ = integer5 + (var11 - 1);
                  break;
               case WEST:
                  var9.minX = integer3 - (var11 - 1);
                  var9.maxZ = integer5 + 2;
                  break;
               case EAST:
                  var9.maxX = integer3 + (var11 - 1);
                  var9.maxZ = integer5 + 2;
            }

            if (StructureComponent.findIntersecting(list, var9) == null) {
               break;
            }
         }

         return var10 > 0 ? var9 : null;
      }

      // $VF: Unable to simplify switch on enum
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         int var7 = this.getComponentType();
         int var8 = random.nextInt(4);
         if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case NORTH:
                  if (var8 <= 1) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ - 1,
                        this.coordBaseMode,
                        var7
                     );
                  } else if (var8 == 2) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ,
                        EnumFacing.WEST,
                        var7
                     );
                  } else {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ,
                        EnumFacing.EAST,
                        var7
                     );
                  }
                  break;
               case SOUTH:
                  if (var8 <= 1) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ + 1,
                        this.coordBaseMode,
                        var7
                     );
                  } else if (var8 == 2) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ - 3,
                        EnumFacing.WEST,
                        var7
                     );
                  } else {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ - 3,
                        EnumFacing.EAST,
                        var7
                     );
                  }
                  break;
               case WEST:
                  if (var8 <= 1) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX - 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ,
                        this.coordBaseMode,
                        var7
                     );
                  } else if (var8 == 2) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ - 1,
                        EnumFacing.NORTH,
                        var7
                     );
                  } else {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.minX,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ + 1,
                        EnumFacing.SOUTH,
                        var7
                     );
                  }
                  break;
               case EAST:
                  if (var8 <= 1) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX + 1,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ,
                        this.coordBaseMode,
                        var7
                     );
                  } else if (var8 == 2) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX - 3,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.minZ - 1,
                        EnumFacing.NORTH,
                        var7
                     );
                  } else {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent,
                        list,
                        random,
                        this.boundingBox.maxX - 3,
                        this.boundingBox.minY - 1 + random.nextInt(3),
                        this.boundingBox.maxZ + 1,
                        EnumFacing.SOUTH,
                        var7
                     );
                  }
            }
         }

         if (var7 < 8) {
            if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.SOUTH) {
               for (int var11 = this.boundingBox.minX + 3; var11 + 3 <= this.boundingBox.maxX; var11 += 5) {
                  int var12 = random.nextInt(5);
                  if (var12 == 0) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent, list, random, var11, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var7 + 1
                     );
                  } else if (var12 == 1) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent, list, random, var11, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var7 + 1
                     );
                  }
               }
            } else {
               for (int var9 = this.boundingBox.minZ + 3; var9 + 3 <= this.boundingBox.maxZ; var9 += 5) {
                  int var10 = random.nextInt(5);
                  if (var10 == 0) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, var9, EnumFacing.WEST, var7 + 1
                     );
                  } else if (var10 == 1) {
                     StructureMineshaftPieces.func_175890_b(
                        structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, var9, EnumFacing.EAST, var7 + 1
                     );
                  }
               }
            }
         }
      }

      @Override
      protected boolean generateChestContents(
         World world,
         StructureBoundingBox structureBoundingBox,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         List<WeightedRandomChestContent> list,
         int integer8
      ) {
         BlockPos var12 = new BlockPos(this.getXWithOffset(integer4, integer6), this.getYWithOffset(integer5), this.getZWithOffset(integer4, integer6));
         if (structureBoundingBox.isVecInside(var12) && world.getBlockState(var12).getBlock().getMaterial() == Material.air) {
            int var13 = random.nextBoolean() ? 1 : 0;
            world.setBlockState(var12, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, var13)), 2);
            EntityMinecartChest var14 = new EntityMinecartChest(world, var12.getX() + 0.5F, var12.getY() + 0.5F, var12.getZ() + 0.5F);
            WeightedRandomChestContent.generateChestContents(random, list, var14, integer8);
            world.spawnEntityInWorld(var14);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         int var11 = this.sectionCount * 5 - 1;
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 2, 1, var11, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.func_175805_a(world, structureBoundingBox, random, 0.8F, 0, 2, 0, 2, 2, var11, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         if (this.hasSpiders) {
            this.func_175805_a(
               world, structureBoundingBox, random, 0.6F, 0, 0, 0, 2, 1, var11, Blocks.web.getDefaultState(), Blocks.air.getDefaultState(), false
            );
         }

         for (int var12 = 0; var12 < this.sectionCount; var12++) {
            int var13 = 2 + var12 * 5;
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, var13, 0, 1, var13, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 0, var13, 2, 1, var13, Blocks.oak_fence.getDefaultState(), Blocks.air.getDefaultState(), false);
            if (random.nextInt(4) == 0) {
               this.fillWithBlocks(world, structureBoundingBox, 0, 2, var13, 0, 2, var13, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
               this.fillWithBlocks(world, structureBoundingBox, 2, 2, var13, 2, 2, var13, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, 0, 2, var13, 2, 2, var13, Blocks.planks.getDefaultState(), Blocks.air.getDefaultState(), false);
            }

            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1F, 0, 2, var13 - 1, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1F, 2, 2, var13 - 1, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1F, 0, 2, var13 + 1, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1F, 2, 2, var13 + 1, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05F, 0, 2, var13 - 2, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05F, 2, 2, var13 - 2, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05F, 0, 2, var13 + 2, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05F, 2, 2, var13 + 2, Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05F, 1, 2, var13 - 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
            this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.05F, 1, 2, var13 + 1, Blocks.torch.getStateFromMeta(EnumFacing.UP.getIndex()));
            if (random.nextInt(100) == 0) {
               this.generateChestContents(
                  world,
                  structureBoundingBox,
                  random,
                  2,
                  0,
                  var13 - 1,
                  WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST, Items.enchanted_book.getRandom(random)),
                  3 + random.nextInt(4)
               );
            }

            if (random.nextInt(100) == 0) {
               this.generateChestContents(
                  world,
                  structureBoundingBox,
                  random,
                  0,
                  0,
                  var13 + 1,
                  WeightedRandomChestContent.func_177629_a(StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST, Items.enchanted_book.getRandom(random)),
                  3 + random.nextInt(4)
               );
            }

            if (this.hasSpiders && !this.spawnerPlaced) {
               int var14 = this.getYWithOffset(0);
               int var15 = var13 - 1 + random.nextInt(3);
               int var16 = this.getXWithOffset(1, var15);
               var15 = this.getZWithOffset(1, var15);
               BlockPos var17 = new BlockPos(var16, var14, var15);
               if (structureBoundingBox.isVecInside(var17)) {
                  this.spawnerPlaced = true;
                  world.setBlockState(var17, Blocks.mob_spawner.getDefaultState(), 2);
                  TileEntity var18 = world.getTileEntity(var17);
                  if (var18 instanceof TileEntityMobSpawner) {
                     ((TileEntityMobSpawner)var18).getSpawnerBaseLogic().setEntityName("CaveSpider");
                  }
               }
            }
         }

         for (int var19 = 0; var19 <= 2; var19++) {
            for (int var21 = 0; var21 <= var11; var21++) {
               IBlockState var24 = this.getBlockStateFromPos(world, var19, -1, var21, structureBoundingBox);
               if (var24.getBlock().getMaterial() == Material.air) {
                  this.setBlockState(world, Blocks.planks.getDefaultState(), var19, -1, var21, structureBoundingBox);
               }
            }
         }

         if (this.hasRails) {
            for (int var20 = 0; var20 <= var11; var20++) {
               IBlockState var22 = this.getBlockStateFromPos(world, 1, -1, var20, structureBoundingBox);
               if (var22.getBlock().getMaterial() != Material.air && var22.getBlock().isFullBlock()) {
                  this.randomlyPlaceBlock(
                     world, structureBoundingBox, random, 0.7F, 1, 0, var20, Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, 0))
                  );
               }
            }
         }

         return true;
      }
   }

   public static class Cross extends StructureComponent {
      private EnumFacing corridorDirection;
      private boolean isMultipleFloors;

      public Cross() {
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         nBTTagCompound.setBoolean("tf", this.isMultipleFloors);
         nBTTagCompound.setInteger("D", this.corridorDirection.getHorizontalIndex());
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         this.isMultipleFloors = nBTTagCompound.getBoolean("tf");
         this.corridorDirection = EnumFacing.getHorizontal(nBTTagCompound.getInteger("D"));
      }

      public Cross(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.corridorDirection = enumFacing;
         this.boundingBox = structureBoundingBox;
         this.isMultipleFloors = structureBoundingBox.getYSize() > 3;
      }

      public static StructureBoundingBox func_175813_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing
      ) {
         StructureBoundingBox var9 = new StructureBoundingBox(integer3, integer4, integer5, integer3, integer4 + 2, integer5);
         if (random.nextInt(4) == 0) {
            var9.maxY += 4;
         }

         switch (enumFacing) {
            case NORTH:
               var9.minX = integer3 - 1;
               var9.maxX = integer3 + 3;
               var9.minZ = integer5 - 4;
               break;
            case SOUTH:
               var9.minX = integer3 - 1;
               var9.maxX = integer3 + 3;
               var9.maxZ = integer5 + 4;
               break;
            case WEST:
               var9.minX = integer3 - 4;
               var9.minZ = integer5 - 1;
               var9.maxZ = integer5 + 3;
               break;
            case EAST:
               var9.maxX = integer3 + 4;
               var9.minZ = integer5 - 1;
               var9.maxZ = integer5 + 3;
         }

         return StructureComponent.findIntersecting(list, var9) != null ? null : var9;
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         int var7 = this.getComponentType();
         switch (this.corridorDirection) {
            case NORTH:
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var7
               );
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var7
               );
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var7
               );
               break;
            case SOUTH:
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var7
               );
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var7
               );
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var7
               );
               break;
            case WEST:
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var7
               );
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var7
               );
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, var7
               );
               break;
            case EAST:
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var7
               );
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var7
               );
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, var7
               );
         }

         if (this.isMultipleFloors) {
            if (random.nextBoolean()) {
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1, EnumFacing.NORTH, var7
               );
            }

            if (random.nextBoolean()) {
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.WEST, var7
               );
            }

            if (random.nextBoolean()) {
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1, EnumFacing.EAST, var7
               );
            }

            if (random.nextBoolean()) {
               StructureMineshaftPieces.func_175890_b(
                  structureComponent, list, random, this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var7
               );
            }
         }
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         if (this.isMultipleFloors) {
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               this.boundingBox.minX + 1,
               this.boundingBox.minY,
               this.boundingBox.minZ,
               this.boundingBox.maxX - 1,
               this.boundingBox.minY + 3 - 1,
               this.boundingBox.maxZ,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               this.boundingBox.minX,
               this.boundingBox.minY,
               this.boundingBox.minZ + 1,
               this.boundingBox.maxX,
               this.boundingBox.minY + 3 - 1,
               this.boundingBox.maxZ - 1,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               this.boundingBox.minX + 1,
               this.boundingBox.maxY - 2,
               this.boundingBox.minZ,
               this.boundingBox.maxX - 1,
               this.boundingBox.maxY,
               this.boundingBox.maxZ,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               this.boundingBox.minX,
               this.boundingBox.maxY - 2,
               this.boundingBox.minZ + 1,
               this.boundingBox.maxX,
               this.boundingBox.maxY,
               this.boundingBox.maxZ - 1,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               this.boundingBox.minX + 1,
               this.boundingBox.minY + 3,
               this.boundingBox.minZ + 1,
               this.boundingBox.maxX - 1,
               this.boundingBox.minY + 3,
               this.boundingBox.maxZ - 1,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
         } else {
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               this.boundingBox.minX + 1,
               this.boundingBox.minY,
               this.boundingBox.minZ,
               this.boundingBox.maxX - 1,
               this.boundingBox.maxY,
               this.boundingBox.maxZ,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               this.boundingBox.minX,
               this.boundingBox.minY,
               this.boundingBox.minZ + 1,
               this.boundingBox.maxX,
               this.boundingBox.maxY,
               this.boundingBox.maxZ - 1,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
         }

         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.boundingBox.minX + 1,
            this.boundingBox.minY,
            this.boundingBox.minZ + 1,
            this.boundingBox.minX + 1,
            this.boundingBox.maxY,
            this.boundingBox.minZ + 1,
            Blocks.planks.getDefaultState(),
            Blocks.air.getDefaultState(),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.boundingBox.minX + 1,
            this.boundingBox.minY,
            this.boundingBox.maxZ - 1,
            this.boundingBox.minX + 1,
            this.boundingBox.maxY,
            this.boundingBox.maxZ - 1,
            Blocks.planks.getDefaultState(),
            Blocks.air.getDefaultState(),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.boundingBox.maxX - 1,
            this.boundingBox.minY,
            this.boundingBox.minZ + 1,
            this.boundingBox.maxX - 1,
            this.boundingBox.maxY,
            this.boundingBox.minZ + 1,
            Blocks.planks.getDefaultState(),
            Blocks.air.getDefaultState(),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.boundingBox.maxX - 1,
            this.boundingBox.minY,
            this.boundingBox.maxZ - 1,
            this.boundingBox.maxX - 1,
            this.boundingBox.maxY,
            this.boundingBox.maxZ - 1,
            Blocks.planks.getDefaultState(),
            Blocks.air.getDefaultState(),
            false
         );

         for (int var7 = this.boundingBox.minX; var7 <= this.boundingBox.maxX; var7++) {
            for (int var8 = this.boundingBox.minZ; var8 <= this.boundingBox.maxZ; var8++) {
               if (this.getBlockStateFromPos(world, var7, this.boundingBox.minY - 1, var8, structureBoundingBox).getBlock().getMaterial() == Material.air) {
                  this.setBlockState(world, Blocks.planks.getDefaultState(), var7, this.boundingBox.minY - 1, var8, structureBoundingBox);
               }
            }
         }

         return true;
      }
   }

   public static class Room extends StructureComponent {
      private final List<StructureBoundingBox> roomsLinkedToTheRoom = Lists.newLinkedList();

      public Room() {
      }

      public Room(int integer1, Random random, int integer3, int integer4) {
         super(integer1);
         this.boundingBox = new StructureBoundingBox(
            integer3, 50, integer4, integer3 + 7 + random.nextInt(6), 54 + random.nextInt(6), integer4 + 7 + random.nextInt(6)
         );
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         int var7 = this.getComponentType();
         int var8 = this.boundingBox.getYSize() - 3 - 1;
         if (var8 <= 0) {
            var8 = 1;
         }

         int var9 = 0;

         while (var9 < this.boundingBox.getXSize()) {
            var9 += random.nextInt(this.boundingBox.getXSize());
            if (var9 + 3 > this.boundingBox.getXSize()) {
               break;
            }

            StructureComponent var10 = StructureMineshaftPieces.func_175890_b(
               structureComponent,
               list,
               random,
               this.boundingBox.minX + var9,
               this.boundingBox.minY + random.nextInt(var8) + 1,
               this.boundingBox.minZ - 1,
               EnumFacing.NORTH,
               var7
            );
            if (var10 != null) {
               StructureBoundingBox var11 = var10.getBoundingBox();
               this.roomsLinkedToTheRoom
                  .add(new StructureBoundingBox(var11.minX, var11.minY, this.boundingBox.minZ, var11.maxX, var11.maxY, this.boundingBox.minZ + 1));
            }

            var9 += 4;
         }

         var9 = 0;

         while (var9 < this.boundingBox.getXSize()) {
            var9 += random.nextInt(this.boundingBox.getXSize());
            if (var9 + 3 > this.boundingBox.getXSize()) {
               break;
            }

            StructureComponent var19 = StructureMineshaftPieces.func_175890_b(
               structureComponent,
               list,
               random,
               this.boundingBox.minX + var9,
               this.boundingBox.minY + random.nextInt(var8) + 1,
               this.boundingBox.maxZ + 1,
               EnumFacing.SOUTH,
               var7
            );
            if (var19 != null) {
               StructureBoundingBox var22 = var19.getBoundingBox();
               this.roomsLinkedToTheRoom
                  .add(new StructureBoundingBox(var22.minX, var22.minY, this.boundingBox.maxZ - 1, var22.maxX, var22.maxY, this.boundingBox.maxZ));
            }

            var9 += 4;
         }

         var9 = 0;

         while (var9 < this.boundingBox.getZSize()) {
            var9 += random.nextInt(this.boundingBox.getZSize());
            if (var9 + 3 > this.boundingBox.getZSize()) {
               break;
            }

            StructureComponent var20 = StructureMineshaftPieces.func_175890_b(
               structureComponent,
               list,
               random,
               this.boundingBox.minX - 1,
               this.boundingBox.minY + random.nextInt(var8) + 1,
               this.boundingBox.minZ + var9,
               EnumFacing.WEST,
               var7
            );
            if (var20 != null) {
               StructureBoundingBox var23 = var20.getBoundingBox();
               this.roomsLinkedToTheRoom
                  .add(new StructureBoundingBox(this.boundingBox.minX, var23.minY, var23.minZ, this.boundingBox.minX + 1, var23.maxY, var23.maxZ));
            }

            var9 += 4;
         }

         var9 = 0;

         while (var9 < this.boundingBox.getZSize()) {
            var9 += random.nextInt(this.boundingBox.getZSize());
            if (var9 + 3 > this.boundingBox.getZSize()) {
               break;
            }

            StructureComponent var21 = StructureMineshaftPieces.func_175890_b(
               structureComponent,
               list,
               random,
               this.boundingBox.maxX + 1,
               this.boundingBox.minY + random.nextInt(var8) + 1,
               this.boundingBox.minZ + var9,
               EnumFacing.EAST,
               var7
            );
            if (var21 != null) {
               StructureBoundingBox var24 = var21.getBoundingBox();
               this.roomsLinkedToTheRoom
                  .add(new StructureBoundingBox(this.boundingBox.maxX - 1, var24.minY, var24.minZ, this.boundingBox.maxX, var24.maxY, var24.maxZ));
            }

            var9 += 4;
         }
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.boundingBox.minX,
            this.boundingBox.minY,
            this.boundingBox.minZ,
            this.boundingBox.maxX,
            this.boundingBox.minY,
            this.boundingBox.maxZ,
            Blocks.dirt.getDefaultState(),
            Blocks.air.getDefaultState(),
            true
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.boundingBox.minX,
            this.boundingBox.minY + 1,
            this.boundingBox.minZ,
            this.boundingBox.maxX,
            Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY),
            this.boundingBox.maxZ,
            Blocks.air.getDefaultState(),
            Blocks.air.getDefaultState(),
            false
         );

         for (StructureBoundingBox var8 : this.roomsLinkedToTheRoom) {
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               var8.minX,
               var8.maxY - 2,
               var8.minZ,
               var8.maxX,
               var8.maxY,
               var8.maxZ,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
         }

         this.randomlyRareFillWithBlocks(
            world,
            structureBoundingBox,
            this.boundingBox.minX,
            this.boundingBox.minY + 4,
            this.boundingBox.minZ,
            this.boundingBox.maxX,
            this.boundingBox.maxY,
            this.boundingBox.maxZ,
            Blocks.air.getDefaultState(),
            false
         );
         return true;
      }

      @Override
      public void func_181138_a(int integer1, int integer2, int integer3) {
         super.func_181138_a(integer1, integer2, integer3);
         Iterator var7 = this.roomsLinkedToTheRoom.iterator();

         while (var7.hasNext()) {
            ((StructureBoundingBox)var7.next()).offset(integer1, integer2, integer3);
         }
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         NBTTagList var5 = new NBTTagList();

         for (StructureBoundingBox var7 : this.roomsLinkedToTheRoom) {
            var5.appendTag(var7.toNBTTagIntArray());
         }

         nBTTagCompound.setTag("Entrances", var5);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         NBTTagList var5 = nBTTagCompound.getTagList("Entrances", 11);

         for (int var6 = 0; var6 < var5.tagCount(); var6++) {
            this.roomsLinkedToTheRoom.add(new StructureBoundingBox(var5.getIntArrayAt(var6)));
         }
      }
   }

   public static class Stairs extends StructureComponent {
      public Stairs() {
      }

      public Stairs(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
      }

      public static StructureBoundingBox func_175812_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing
      ) {
         StructureBoundingBox var9 = new StructureBoundingBox(integer3, integer4 - 5, integer5, integer3, integer4 + 2, integer5);
         switch (enumFacing) {
            case NORTH:
               var9.maxX = integer3 + 2;
               var9.minZ = integer5 - 8;
               break;
            case SOUTH:
               var9.maxX = integer3 + 2;
               var9.maxZ = integer5 + 8;
               break;
            case WEST:
               var9.minX = integer3 - 8;
               var9.maxZ = integer5 + 2;
               break;
            case EAST:
               var9.maxX = integer3 + 8;
               var9.maxZ = integer5 + 2;
         }

         return StructureComponent.findIntersecting(list, var9) != null ? null : var9;
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         int var7 = this.getComponentType();
         if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case NORTH:
                  StructureMineshaftPieces.func_175890_b(
                     structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, var7
                  );
                  break;
               case SOUTH:
                  StructureMineshaftPieces.func_175890_b(
                     structureComponent, list, random, this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, var7
                  );
                  break;
               case WEST:
                  StructureMineshaftPieces.func_175890_b(
                     structureComponent, list, random, this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST, var7
                  );
                  break;
               case EAST:
                  StructureMineshaftPieces.func_175890_b(
                     structureComponent, list, random, this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST, var7
                  );
            }
         }
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);

         for (int var7 = 0; var7 < 5; var7++) {
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               0,
               5 - var7 - (var7 < 4 ? 1 : 0),
               2 + var7,
               2,
               7 - var7,
               2 + var7,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
         }

         return true;
      }
   }
}
