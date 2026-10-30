package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureStrongholdPieces {
   private static final StructureStrongholdPieces.PieceWeight[] pieceWeightArray = new StructureStrongholdPieces.PieceWeight[]{
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Straight.class, 40, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Prison.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.LeftTurn.class, 20, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RightTurn.class, 20, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RoomCrossing.class, 10, 6),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.StairsStraight.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Stairs.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Crossing.class, 5, 4),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.ChestCorridor.class, 5, 4),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Library.class, 10, 2) {
         @Override
         public boolean canSpawnMoreStructuresOfType(int integer) {
            return super.canSpawnMoreStructuresOfType(integer) && integer > 4;
         }
      },
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.PortalRoom.class, 20, 1) {
         @Override
         public boolean canSpawnMoreStructuresOfType(int integer) {
            return super.canSpawnMoreStructuresOfType(integer) && integer > 5;
         }
      }
   };
   private static List<StructureStrongholdPieces.PieceWeight> structurePieceList;
   private static Class<? extends StructureStrongholdPieces.Stronghold> strongComponentType;
   static int totalWeight;
   private static final StructureStrongholdPieces.Stones strongholdStones = new StructureStrongholdPieces.Stones();

   public static void registerStrongholdPieces() {
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.ChestCorridor.class, "SHCC");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Corridor.class, "SHFC");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Crossing.class, "SH5C");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.LeftTurn.class, "SHLT");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Library.class, "SHLi");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.PortalRoom.class, "SHPR");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Prison.class, "SHPH");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.RightTurn.class, "SHRT");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.RoomCrossing.class, "SHRC");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Stairs.class, "SHSD");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Stairs2.class, "SHStart");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.Straight.class, "SHS");
      MapGenStructureIO.registerStructureComponent(StructureStrongholdPieces.StairsStraight.class, "SHSSD");
   }

   public static void prepareStructurePieces() {
      structurePieceList = Lists.newArrayList();

      for (StructureStrongholdPieces.PieceWeight var6 : pieceWeightArray) {
         var6.instancesSpawned = 0;
         structurePieceList.add(var6);
      }

      strongComponentType = null;
   }

   private static boolean canAddStructurePieces() {
      boolean var3 = false;
      totalWeight = 0;

      for (StructureStrongholdPieces.PieceWeight var5 : structurePieceList) {
         if (var5.instancesLimit > 0 && var5.instancesSpawned < var5.instancesLimit) {
            var3 = true;
         }

         totalWeight = totalWeight + var5.pieceWeight;
      }

      return var3;
   }

   private static StructureStrongholdPieces.Stronghold func_175954_a(
      Class<? extends StructureStrongholdPieces.Stronghold> class1,
      List<StructureComponent> list,
      Random random,
      int integer4,
      int integer5,
      int integer6,
      EnumFacing enumFacing,
      int integer8
   ) {
      StructureStrongholdPieces.Stronghold var11 = null;
      if (class1 == StructureStrongholdPieces.Straight.class) {
         var11 = StructureStrongholdPieces.Straight.func_175862_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.Prison.class) {
         var11 = StructureStrongholdPieces.Prison.func_175860_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.LeftTurn.class) {
         var11 = StructureStrongholdPieces.LeftTurn.func_175867_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.RightTurn.class) {
         var11 = StructureStrongholdPieces.RightTurn.func_175867_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.RoomCrossing.class) {
         var11 = StructureStrongholdPieces.RoomCrossing.func_175859_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.StairsStraight.class) {
         var11 = StructureStrongholdPieces.StairsStraight.func_175861_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.Stairs.class) {
         var11 = StructureStrongholdPieces.Stairs.func_175863_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.Crossing.class) {
         var11 = StructureStrongholdPieces.Crossing.func_175866_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.ChestCorridor.class) {
         var11 = StructureStrongholdPieces.ChestCorridor.func_175868_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.Library.class) {
         var11 = StructureStrongholdPieces.Library.func_175864_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      } else if (class1 == StructureStrongholdPieces.PortalRoom.class) {
         var11 = StructureStrongholdPieces.PortalRoom.func_175865_a(list, random, integer4, integer5, integer6, enumFacing, integer8);
      }

      return var11;
   }

   private static StructureStrongholdPieces.Stronghold func_175955_b(
      StructureStrongholdPieces.Stairs2 stairs2,
      List<StructureComponent> list,
      Random random,
      int integer4,
      int integer5,
      int integer6,
      EnumFacing enumFacing,
      int integer8
   ) {
      if (!canAddStructurePieces()) {
         return null;
      }

      if (strongComponentType != null) {
         StructureStrongholdPieces.Stronghold var11 = func_175954_a(strongComponentType, list, random, integer4, integer5, integer6, enumFacing, integer8);
         strongComponentType = null;
         if (var11 != null) {
            return var11;
         }
      }

      int var16 = 0;

      while (var16 < 5) {
         var16++;
         int var12 = random.nextInt(totalWeight);

         for (StructureStrongholdPieces.PieceWeight var14 : structurePieceList) {
            var12 -= var14.pieceWeight;
            if (var12 < 0) {
               if (!var14.canSpawnMoreStructuresOfType(integer8) || var14 == stairs2.strongholdPieceWeight) {
                  break;
               }

               StructureStrongholdPieces.Stronghold var15 = func_175954_a(var14.pieceClass, list, random, integer4, integer5, integer6, enumFacing, integer8);
               if (var15 != null) {
                  var14.instancesSpawned++;
                  stairs2.strongholdPieceWeight = var14;
                  if (!var14.canSpawnMoreStructures()) {
                     structurePieceList.remove(var14);
                  }

                  return var15;
               }
            }
         }
      }

      StructureBoundingBox var17 = StructureStrongholdPieces.Corridor.func_175869_a(list, random, integer4, integer5, integer6, enumFacing);
      return var17 != null && var17.minY > 1 ? new StructureStrongholdPieces.Corridor(integer8, random, var17, enumFacing) : null;
   }

   private static StructureComponent func_175953_c(
      StructureStrongholdPieces.Stairs2 stairs2,
      List<StructureComponent> list,
      Random random,
      int integer4,
      int integer5,
      int integer6,
      EnumFacing enumFacing,
      int integer8
   ) {
      if (integer8 > 50) {
         return null;
      }

      if (Math.abs(integer4 - stairs2.getBoundingBox().minX) <= 112 && Math.abs(integer6 - stairs2.getBoundingBox().minZ) <= 112) {
         StructureStrongholdPieces.Stronghold var11 = func_175955_b(stairs2, list, random, integer4, integer5, integer6, enumFacing, integer8 + 1);
         if (var11 != null) {
            list.add(var11);
            stairs2.field_75026_c.add(var11);
         }

         return var11;
      } else {
         return null;
      }
   }

   public static class ChestCorridor extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> strongholdChestContents = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10),
            new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
            new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
            new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
         }
      );
      private boolean hasMadeChest;

      public ChestCorridor() {
      }

      public ChestCorridor(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("Chest", this.hasMadeChest);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.hasMadeChest = nBTTagCompound.getBoolean("Chest");
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
      }

      public static StructureStrongholdPieces.ChestCorridor func_175868_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -1, 0, 5, 5, 7, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.ChestCorridor(integer7, random, var9, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 4, 6, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
         this.placeDoor(world, random, structureBoundingBox, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 2, 3, 1, 4, Blocks.stonebrick.getDefaultState(), Blocks.stonebrick.getDefaultState(), false);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 3, 2, 4, structureBoundingBox);

         for (int var7 = 2; var7 <= 4; var7++) {
            this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SMOOTHBRICK.getMetadata()), 2, 1, var7, structureBoundingBox);
         }

         if (!this.hasMadeChest && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(3, 3), this.getYWithOffset(2), this.getZWithOffset(3, 3)))
            )
          {
            this.hasMadeChest = true;
            this.generateChestContents(
               world,
               structureBoundingBox,
               random,
               3,
               2,
               3,
               WeightedRandomChestContent.func_177629_a(strongholdChestContents, Items.enchanted_book.getRandom(random)),
               2 + random.nextInt(2)
            );
         }

         return true;
      }
   }

   public static class Corridor extends StructureStrongholdPieces.Stronghold {
      private int field_74993_a;

      public Corridor() {
      }

      public Corridor(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
         this.field_74993_a = enumFacing != EnumFacing.NORTH && enumFacing != EnumFacing.SOUTH
            ? structureBoundingBox.getXSize()
            : structureBoundingBox.getZSize();
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setInteger("Steps", this.field_74993_a);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.field_74993_a = nBTTagCompound.getInteger("Steps");
      }

      public static StructureBoundingBox func_175869_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -1, 0, 5, 5, 4, enumFacing);
         StructureComponent var11 = StructureComponent.findIntersecting(list, var10);
         if (var11 != null && var11.getBoundingBox().minY == var10.minY) {
            for (int var12 = 3; var12 >= 1; var12--) {
               var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -1, 0, 5, 5, var12 - 1, enumFacing);
               if (!var11.getBoundingBox().intersectsWith(var10)) {
                  return StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -1, 0, 5, 5, var12, enumFacing);
               }
            }
         }

         return null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         for (int var7 = 0; var7 < this.field_74993_a; var7++) {
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0, 0, var7, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 0, var7, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 0, var7, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 0, var7, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 4, 0, var7, structureBoundingBox);

            for (int var8 = 1; var8 <= 3; var8++) {
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0, var8, var7, structureBoundingBox);
               this.setBlockState(world, Blocks.air.getDefaultState(), 1, var8, var7, structureBoundingBox);
               this.setBlockState(world, Blocks.air.getDefaultState(), 2, var8, var7, structureBoundingBox);
               this.setBlockState(world, Blocks.air.getDefaultState(), 3, var8, var7, structureBoundingBox);
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 4, var8, var7, structureBoundingBox);
            }

            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 0, 4, var7, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 4, var7, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 4, var7, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 4, var7, structureBoundingBox);
            this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 4, 4, var7, structureBoundingBox);
         }

         return true;
      }
   }

   public static class Crossing extends StructureStrongholdPieces.Stronghold {
      private boolean field_74996_b;
      private boolean field_74997_c;
      private boolean field_74995_d;
      private boolean field_74999_h;

      public Crossing() {
      }

      public Crossing(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
         this.field_74996_b = random.nextBoolean();
         this.field_74997_c = random.nextBoolean();
         this.field_74995_d = random.nextBoolean();
         this.field_74999_h = random.nextInt(3) > 0;
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("leftLow", this.field_74996_b);
         nBTTagCompound.setBoolean("leftHigh", this.field_74997_c);
         nBTTagCompound.setBoolean("rightLow", this.field_74995_d);
         nBTTagCompound.setBoolean("rightHigh", this.field_74999_h);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.field_74996_b = nBTTagCompound.getBoolean("leftLow");
         this.field_74997_c = nBTTagCompound.getBoolean("leftHigh");
         this.field_74995_d = nBTTagCompound.getBoolean("rightLow");
         this.field_74999_h = nBTTagCompound.getBoolean("rightHigh");
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         byte var7 = 3;
         byte var8 = 5;
         if (this.coordBaseMode == EnumFacing.WEST || this.coordBaseMode == EnumFacing.NORTH) {
            var7 = 5;
            var8 = 3;
         }

         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 5, 1);
         if (this.field_74996_b) {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)structureComponent, list, random, var7, 1);
         }

         if (this.field_74997_c) {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)structureComponent, list, random, var8, 7);
         }

         if (this.field_74995_d) {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)structureComponent, list, random, var7, 1);
         }

         if (this.field_74999_h) {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)structureComponent, list, random, var8, 7);
         }
      }

      public static StructureStrongholdPieces.Crossing func_175866_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -4, -3, 0, 10, 9, 11, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.Crossing(integer7, random, var9, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 9, 8, 10, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 4, 3, 0);
         if (this.field_74996_b) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 1, 0, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         if (this.field_74995_d) {
            this.fillWithBlocks(world, structureBoundingBox, 9, 3, 1, 9, 5, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         if (this.field_74997_c) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 5, 7, 0, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         if (this.field_74999_h) {
            this.fillWithBlocks(world, structureBoundingBox, 9, 5, 7, 9, 7, 9, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 10, 7, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 2, 1, 8, 2, 6, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 5, 4, 4, 9, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 8, 1, 5, 8, 4, 9, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 4, 7, 3, 4, 9, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 3, 5, 3, 3, 6, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 4, 3, 3, 4, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 4, 6, 3, 4, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 5, 1, 7, 7, 1, 8, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 9, 7, 1, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 2, 7, 7, 2, 7, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 5, 7, 4, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 5, 7, 8, 5, 9, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(
            world, structureBoundingBox, 5, 5, 7, 7, 5, 9, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false
         );
         this.setBlockState(world, Blocks.torch.getDefaultState(), 6, 5, 6, structureBoundingBox);
         return true;
      }
   }

   public static class LeftTurn extends StructureStrongholdPieces.Stronghold {
      public LeftTurn() {
      }

      public LeftTurn(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
         } else {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
         }
      }

      public static StructureStrongholdPieces.LeftTurn func_175867_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -1, 0, 5, 5, 5, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.LeftTurn(integer7, random, var9, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 4, 4, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
         if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         } else {
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         return true;
      }
   }

   public static class Library extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> strongholdLibraryChestContents = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.book, 0, 1, 3, 20),
            new WeightedRandomChestContent(Items.paper, 0, 2, 7, 20),
            new WeightedRandomChestContent(Items.map, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.compass, 0, 1, 1, 1)
         }
      );
      private boolean isLargeRoom;

      public Library() {
      }

      public Library(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
         this.isLargeRoom = structureBoundingBox.getYSize() > 6;
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("Tall", this.isLargeRoom);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.isLargeRoom = nBTTagCompound.getBoolean("Tall");
      }

      public static StructureStrongholdPieces.Library func_175864_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -4, -1, 0, 14, 11, 15, enumFacing);
         if (!canStrongholdGoDeeper(var10) || StructureComponent.findIntersecting(list, var10) != null) {
            var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -4, -1, 0, 14, 6, 15, enumFacing);
            if (!canStrongholdGoDeeper(var10) || StructureComponent.findIntersecting(list, var10) != null) {
               return null;
            }
         }

         return new StructureStrongholdPieces.Library(integer7, random, var10, enumFacing);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         byte var7 = 11;
         if (!this.isLargeRoom) {
            var7 = 6;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 13, var7 - 1, 14, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 4, 1, 0);
         this.func_175805_a(world, structureBoundingBox, random, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.web.getDefaultState(), Blocks.web.getDefaultState(), false);

         for (int var10 = 1; var10 <= 13; var10++) {
            if ((var10 - 1) % 4 == 0) {
               this.fillWithBlocks(
                  world, structureBoundingBox, 1, 1, var10, 1, 4, var10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
               );
               this.fillWithBlocks(
                  world, structureBoundingBox, 12, 1, var10, 12, 4, var10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
               );
               this.setBlockState(world, Blocks.torch.getDefaultState(), 2, 3, var10, structureBoundingBox);
               this.setBlockState(world, Blocks.torch.getDefaultState(), 11, 3, var10, structureBoundingBox);
               if (this.isLargeRoom) {
                  this.fillWithBlocks(
                     world, structureBoundingBox, 1, 6, var10, 1, 9, var10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
                  );
                  this.fillWithBlocks(
                     world, structureBoundingBox, 12, 6, var10, 12, 9, var10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false
                  );
               }
            } else {
               this.fillWithBlocks(
                  world, structureBoundingBox, 1, 1, var10, 1, 4, var10, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
               );
               this.fillWithBlocks(
                  world, structureBoundingBox, 12, 1, var10, 12, 4, var10, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
               );
               if (this.isLargeRoom) {
                  this.fillWithBlocks(
                     world, structureBoundingBox, 1, 6, var10, 1, 9, var10, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
                  );
                  this.fillWithBlocks(
                     world, structureBoundingBox, 12, 6, var10, 12, 9, var10, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
                  );
               }
            }
         }

         for (byte var13 = 3; var13 < 12; var13 += 2) {
            this.fillWithBlocks(
               world, structureBoundingBox, 3, 1, var13, 4, 3, var13, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
            );
            this.fillWithBlocks(
               world, structureBoundingBox, 6, 1, var13, 7, 3, var13, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
            );
            this.fillWithBlocks(
               world, structureBoundingBox, 9, 1, var13, 10, 3, var13, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false
            );
         }

         if (this.isLargeRoom) {
            this.fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 3, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 10, 5, 1, 12, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 5, 1, 9, 5, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 5, 12, 9, 5, 13, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 9, 5, 11, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 5, 11, structureBoundingBox);
            this.setBlockState(world, Blocks.planks.getDefaultState(), 9, 5, 10, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 3, 6, 2, 3, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 10, 6, 2, 10, 6, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 6, 2, 9, 6, 2, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 6, 12, 8, 6, 12, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 9, 6, 11, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 8, 6, 11, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 9, 6, 10, structureBoundingBox);
            int var14 = this.getMetadataWithOffset(Blocks.ladder, 3);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var14), 10, 1, 13, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var14), 10, 2, 13, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var14), 10, 3, 13, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var14), 10, 4, 13, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var14), 10, 5, 13, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var14), 10, 6, 13, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var14), 10, 7, 13, structureBoundingBox);
            byte var11 = 7;
            byte var12 = 7;
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 6, 9, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), var11, 9, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 6, 8, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), var11, 8, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 6, 7, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), var11, 7, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 5, 7, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 8, 7, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 6, 7, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 6, 7, 8, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), var11, 7, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), var11, 7, 8, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState(), 5, 8, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState(), 8, 8, var12, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState(), 6, 8, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState(), 6, 8, 8, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState(), var11, 8, 6, structureBoundingBox);
            this.setBlockState(world, Blocks.torch.getDefaultState(), var11, 8, 8, structureBoundingBox);
         }

         this.generateChestContents(
            world,
            structureBoundingBox,
            random,
            3,
            3,
            5,
            WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, Items.enchanted_book.getRandom(random, 1, 5, 2)),
            1 + random.nextInt(4)
         );
         if (this.isLargeRoom) {
            this.setBlockState(world, Blocks.air.getDefaultState(), 12, 9, 1, structureBoundingBox);
            this.generateChestContents(
               world,
               structureBoundingBox,
               random,
               12,
               8,
               1,
               WeightedRandomChestContent.func_177629_a(strongholdLibraryChestContents, Items.enchanted_book.getRandom(random, 1, 5, 2)),
               1 + random.nextInt(4)
            );
         }

         return true;
      }
   }

   static class PieceWeight {
      public Class<? extends StructureStrongholdPieces.Stronghold> pieceClass;
      public final int pieceWeight;
      public int instancesSpawned;
      public int instancesLimit;

      public PieceWeight(Class<? extends StructureStrongholdPieces.Stronghold> class1, int integer2, int integer3) {
         this.pieceClass = class1;
         this.pieceWeight = integer2;
         this.instancesLimit = integer3;
      }

      public boolean canSpawnMoreStructuresOfType(int integer) {
         return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
      }

      public boolean canSpawnMoreStructures() {
         return this.instancesLimit == 0 || this.instancesSpawned < this.instancesLimit;
      }
   }

   public static class PortalRoom extends StructureStrongholdPieces.Stronghold {
      private boolean hasSpawner;

      public PortalRoom() {
      }

      public PortalRoom(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("Mob", this.hasSpawner);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.hasSpawner = nBTTagCompound.getBoolean("Mob");
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         if (structureComponent != null) {
            ((StructureStrongholdPieces.Stairs2)structureComponent).strongholdPortalRoom = this;
         }
      }

      public static StructureStrongholdPieces.PortalRoom func_175865_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -4, -1, 0, 11, 8, 16, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.PortalRoom(integer7, random, var9, enumFacing)
            : null;
      }

      // $VF: Unable to simplify switch on enum
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 10, 7, 15, false, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, StructureStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
         int var7 = 6;
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, var7, 1, 1, var7, 14, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, var7, 1, 9, var7, 14, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, var7, 1, 8, var7, 2, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, var7, 14, 8, var7, 14, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 1, 1, 2, 1, 4, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 8, 1, 1, 9, 1, 4, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 1, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 1, 1, 9, 1, 3, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 3, 1, 8, 7, 1, 12, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithBlocks(
            world, structureBoundingBox, 4, 1, 9, 6, 1, 11, Blocks.flowing_lava.getDefaultState(), Blocks.flowing_lava.getDefaultState(), false
         );

         for (byte var8 = 3; var8 < 14; var8 += 2) {
            this.fillWithBlocks(
               world, structureBoundingBox, 0, 3, var8, 0, 4, var8, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false
            );
            this.fillWithBlocks(
               world, structureBoundingBox, 10, 3, var8, 10, 4, var8, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false
            );
         }

         for (byte var16 = 2; var16 < 9; var16 += 2) {
            this.fillWithBlocks(
               world, structureBoundingBox, var16, 3, 15, var16, 4, 15, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false
            );
         }

         int var17 = this.getMetadataWithOffset(Blocks.stone_brick_stairs, 3);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 5, 6, 1, 7, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 2, 6, 6, 2, 7, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 3, 7, 6, 3, 7, false, random, StructureStrongholdPieces.strongholdStones);

         for (int var9 = 4; var9 <= 6; var9++) {
            this.setBlockState(world, Blocks.stone_brick_stairs.getStateFromMeta(var17), var9, 1, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_brick_stairs.getStateFromMeta(var17), var9, 2, 5, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_brick_stairs.getStateFromMeta(var17), var9, 3, 6, structureBoundingBox);
         }

         int var18 = EnumFacing.NORTH.getHorizontalIndex();
         int var10 = EnumFacing.SOUTH.getHorizontalIndex();
         int var11 = EnumFacing.EAST.getHorizontalIndex();
         int var12 = EnumFacing.WEST.getHorizontalIndex();
         if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case SOUTH:
                  var18 = EnumFacing.SOUTH.getHorizontalIndex();
                  var10 = EnumFacing.NORTH.getHorizontalIndex();
                  break;
               case WEST:
                  var18 = EnumFacing.WEST.getHorizontalIndex();
                  var10 = EnumFacing.EAST.getHorizontalIndex();
                  var11 = EnumFacing.SOUTH.getHorizontalIndex();
                  var12 = EnumFacing.NORTH.getHorizontalIndex();
                  break;
               case EAST:
                  var18 = EnumFacing.EAST.getHorizontalIndex();
                  var10 = EnumFacing.WEST.getHorizontalIndex();
                  var11 = EnumFacing.SOUTH.getHorizontalIndex();
                  var12 = EnumFacing.NORTH.getHorizontalIndex();
            }
         }

         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var18).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            4,
            3,
            8,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var18).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            5,
            3,
            8,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var18).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            6,
            3,
            8,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var10).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            4,
            3,
            12,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var10).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            5,
            3,
            12,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var10).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            6,
            3,
            12,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var11).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            3,
            3,
            9,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var11).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            3,
            3,
            10,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var11).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            3,
            3,
            11,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var12).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            7,
            3,
            9,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var12).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            7,
            3,
            10,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.end_portal_frame.getStateFromMeta(var12).withProperty(BlockEndPortalFrame.EYE, random.nextFloat() > 0.9F),
            7,
            3,
            11,
            structureBoundingBox
         );
         if (!this.hasSpawner) {
            var7 = this.getYWithOffset(3);
            BlockPos var13 = new BlockPos(this.getXWithOffset(5, 6), var7, this.getZWithOffset(5, 6));
            if (structureBoundingBox.isVecInside(var13)) {
               this.hasSpawner = true;
               world.setBlockState(var13, Blocks.mob_spawner.getDefaultState(), 2);
               TileEntity var14 = world.getTileEntity(var13);
               if (var14 instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)var14).getSpawnerBaseLogic().setEntityName("Silverfish");
               }
            }
         }

         return true;
      }
   }

   public static class Prison extends StructureStrongholdPieces.Stronghold {
      public Prison() {
      }

      public Prison(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
      }

      public static StructureStrongholdPieces.Prison func_175860_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -1, 0, 9, 5, 11, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.Prison(integer7, random, var9, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 8, 4, 10, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 10, 3, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 1, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 3, 4, 3, 3, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 7, 4, 3, 7, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 9, 4, 3, 9, false, random, StructureStrongholdPieces.strongholdStones);
         this.fillWithBlocks(world, structureBoundingBox, 4, 1, 4, 4, 3, 6, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 5, 7, 3, 5, Blocks.iron_bars.getDefaultState(), Blocks.iron_bars.getDefaultState(), false);
         this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 4, 3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 4, 3, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3)), 4, 1, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.iron_door.getStateFromMeta(this.getMetadataWithOffset(Blocks.iron_door, 3) + 8), 4, 2, 8, structureBoundingBox);
         return true;
      }
   }

   public static class RightTurn extends StructureStrongholdPieces.LeftTurn {
      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
         } else {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
         }
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 4, 4, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
         if (this.coordBaseMode != EnumFacing.NORTH && this.coordBaseMode != EnumFacing.EAST) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         } else {
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         return true;
      }
   }

   public static class RoomCrossing extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> strongholdRoomCrossingChestContents = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
            new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
            new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10),
            new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1)
         }
      );
      protected int roomType;

      public RoomCrossing() {
      }

      public RoomCrossing(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
         this.roomType = random.nextInt(5);
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setInteger("Type", this.roomType);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.roomType = nBTTagCompound.getInteger("Type");
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 4, 1);
         this.getNextComponentX((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 4);
         this.getNextComponentZ((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 4);
      }

      public static StructureStrongholdPieces.RoomCrossing func_175859_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -4, -1, 0, 11, 7, 11, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.RoomCrossing(integer7, random, var9, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 10, 6, 10, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 4, 1, 0);
         this.fillWithBlocks(world, structureBoundingBox, 4, 1, 10, 6, 3, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 4, 0, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 1, 4, 10, 3, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         switch (this.roomType) {
            case 0:
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.torch.getDefaultState(), 4, 3, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.torch.getDefaultState(), 6, 3, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.torch.getDefaultState(), 5, 3, 4, structureBoundingBox);
               this.setBlockState(world, Blocks.torch.getDefaultState(), 5, 3, 6, structureBoundingBox);
               this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 4, 1, 4, structureBoundingBox);
               this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 4, 1, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 4, 1, 6, structureBoundingBox);
               this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 6, 1, 4, structureBoundingBox);
               this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 6, 1, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 6, 1, 6, structureBoundingBox);
               this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 5, 1, 4, structureBoundingBox);
               this.setBlockState(world, Blocks.stone_slab.getDefaultState(), 5, 1, 6, structureBoundingBox);
               break;
            case 1:
               for (int var11 = 0; var11 < 5; var11++) {
                  this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 1, 3 + var11, structureBoundingBox);
                  this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 7, 1, 3 + var11, structureBoundingBox);
                  this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3 + var11, 1, 3, structureBoundingBox);
                  this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3 + var11, 1, 7, structureBoundingBox);
               }

               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.flowing_water.getDefaultState(), 5, 4, 5, structureBoundingBox);
               break;
            case 2:
               for (int var7 = 1; var7 <= 9; var7++) {
                  this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 1, 3, var7, structureBoundingBox);
                  this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 9, 3, var7, structureBoundingBox);
               }

               for (int var8 = 1; var8 <= 9; var8++) {
                  this.setBlockState(world, Blocks.cobblestone.getDefaultState(), var8, 3, 1, structureBoundingBox);
                  this.setBlockState(world, Blocks.cobblestone.getDefaultState(), var8, 3, 9, structureBoundingBox);
               }

               this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 5, 1, 4, structureBoundingBox);
               this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 5, 1, 6, structureBoundingBox);
               this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 5, 3, 4, structureBoundingBox);
               this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 5, 3, 6, structureBoundingBox);
               this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 1, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 6, 1, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 3, 5, structureBoundingBox);
               this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 6, 3, 5, structureBoundingBox);

               for (int var9 = 1; var9 <= 3; var9++) {
                  this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, var9, 4, structureBoundingBox);
                  this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 6, var9, 4, structureBoundingBox);
                  this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, var9, 6, structureBoundingBox);
                  this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 6, var9, 6, structureBoundingBox);
               }

               this.setBlockState(world, Blocks.torch.getDefaultState(), 5, 3, 5, structureBoundingBox);

               for (int var10 = 2; var10 <= 8; var10++) {
                  this.setBlockState(world, Blocks.planks.getDefaultState(), 2, 3, var10, structureBoundingBox);
                  this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 3, var10, structureBoundingBox);
                  if (var10 <= 3 || var10 >= 7) {
                     this.setBlockState(world, Blocks.planks.getDefaultState(), 4, 3, var10, structureBoundingBox);
                     this.setBlockState(world, Blocks.planks.getDefaultState(), 5, 3, var10, structureBoundingBox);
                     this.setBlockState(world, Blocks.planks.getDefaultState(), 6, 3, var10, structureBoundingBox);
                  }

                  this.setBlockState(world, Blocks.planks.getDefaultState(), 7, 3, var10, structureBoundingBox);
                  this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 3, var10, structureBoundingBox);
               }

               this.setBlockState(
                  world, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 1, 3, structureBoundingBox
               );
               this.setBlockState(
                  world, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 2, 3, structureBoundingBox
               );
               this.setBlockState(
                  world, Blocks.ladder.getStateFromMeta(this.getMetadataWithOffset(Blocks.ladder, EnumFacing.WEST.getIndex())), 9, 3, 3, structureBoundingBox
               );
               this.generateChestContents(
                  world,
                  structureBoundingBox,
                  random,
                  3,
                  4,
                  8,
                  WeightedRandomChestContent.func_177629_a(strongholdRoomCrossingChestContents, Items.enchanted_book.getRandom(random)),
                  1 + random.nextInt(4)
               );
         }

         return true;
      }
   }

   public static class Stairs extends StructureStrongholdPieces.Stronghold {
      private boolean field_75024_a;

      public Stairs() {
      }

      public Stairs(int integer1, Random random, int integer3, int integer4) {
         super(integer1);
         this.field_75024_a = true;
         this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
         this.field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;
         this.boundingBox = new StructureBoundingBox(integer3, 64, integer4, integer3 + 5 - 1, 74, integer4 + 5 - 1);
      }

      public Stairs(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.field_75024_a = false;
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("Source", this.field_75024_a);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.field_75024_a = nBTTagCompound.getBoolean("Source");
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         if (this.field_75024_a) {
            StructureStrongholdPieces.strongComponentType = StructureStrongholdPieces.Crossing.class;
         }

         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
      }

      public static StructureStrongholdPieces.Stairs func_175863_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -7, 0, 5, 11, 5, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.Stairs(integer7, random, var9, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 10, 4, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 7, 0);
         this.placeDoor(world, random, structureBoundingBox, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 6, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 5, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 6, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 5, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 4, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 5, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 4, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 3, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 4, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 3, 3, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 1, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 1, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.STONE.getMetadata()), 1, 1, 3, structureBoundingBox);
         return true;
      }
   }

   public static class Stairs2 extends StructureStrongholdPieces.Stairs {
      public StructureStrongholdPieces.PieceWeight strongholdPieceWeight;
      public StructureStrongholdPieces.PortalRoom strongholdPortalRoom;
      public List<StructureComponent> field_75026_c = Lists.newArrayList();

      public Stairs2() {
      }

      public Stairs2(int integer1, Random random, int integer3, int integer4) {
         super(0, random, integer3, integer4);
      }

      @Override
      public BlockPos getBoundingBoxCenter() {
         return this.strongholdPortalRoom != null ? this.strongholdPortalRoom.getBoundingBoxCenter() : super.getBoundingBoxCenter();
      }
   }

   public static class StairsStraight extends StructureStrongholdPieces.Stronghold {
      public StairsStraight() {
      }

      public StairsStraight(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
      }

      public static StructureStrongholdPieces.StairsStraight func_175861_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -7, 0, 5, 11, 8, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.StairsStraight(integer7, random, var9, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 10, 7, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 7, 0);
         this.placeDoor(world, random, structureBoundingBox, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
         int var7 = this.getMetadataWithOffset(Blocks.stone_stairs, 2);

         for (int var8 = 0; var8 < 6; var8++) {
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 1, 6 - var8, 1 + var8, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 2, 6 - var8, 1 + var8, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 3, 6 - var8, 1 + var8, structureBoundingBox);
            if (var8 < 5) {
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 1, 5 - var8, 1 + var8, structureBoundingBox);
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 2, 5 - var8, 1 + var8, structureBoundingBox);
               this.setBlockState(world, Blocks.stonebrick.getDefaultState(), 3, 5 - var8, 1 + var8, structureBoundingBox);
            }
         }

         return true;
      }
   }

   static class Stones extends StructureComponent.BlockSelector {
      private Stones() {
      }

      @Override
      public void selectBlocks(Random random, int integer2, int integer3, int integer4, boolean boolean5) {
         if (boolean5) {
            float var9 = random.nextFloat();
            if (var9 < 0.2F) {
               this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CRACKED_META);
            } else if (var9 < 0.5F) {
               this.blockstate = Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.MOSSY_META);
            } else if (var9 < 0.55F) {
               this.blockstate = Blocks.monster_egg.getStateFromMeta(BlockSilverfish.EnumType.STONEBRICK.getMetadata());
            } else {
               this.blockstate = Blocks.stonebrick.getDefaultState();
            }
         } else {
            this.blockstate = Blocks.air.getDefaultState();
         }
      }
   }

   public static class Straight extends StructureStrongholdPieces.Stronghold {
      private boolean expandsX;
      private boolean expandsZ;

      public Straight() {
      }

      public Straight(int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(integer);
         this.coordBaseMode = enumFacing;
         this.field_143013_d = this.getRandomDoor(random);
         this.boundingBox = structureBoundingBox;
         this.expandsX = random.nextInt(2) == 0;
         this.expandsZ = random.nextInt(2) == 0;
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("Left", this.expandsX);
         nBTTagCompound.setBoolean("Right", this.expandsZ);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.expandsX = nBTTagCompound.getBoolean("Left");
         this.expandsZ = nBTTagCompound.getBoolean("Right");
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         this.getNextComponentNormal((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 1);
         if (this.expandsX) {
            this.getNextComponentX((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 2);
         }

         if (this.expandsZ) {
            this.getNextComponentZ((StructureStrongholdPieces.Stairs2)structureComponent, list, random, 1, 2);
         }
      }

      public static StructureStrongholdPieces.Straight func_175862_a(
         List<StructureComponent> list, Random random, int integer3, int integer4, int integer5, EnumFacing enumFacing, int integer7
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer3, integer4, integer5, -1, -1, 0, 5, 5, 7, enumFacing);
         return canStrongholdGoDeeper(var9) && StructureComponent.findIntersecting(list, var9) == null
            ? new StructureStrongholdPieces.Straight(integer7, random, var9, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 0, 0, 0, 4, 4, 6, true, random, StructureStrongholdPieces.strongholdStones);
         this.placeDoor(world, random, structureBoundingBox, this.field_143013_d, 1, 1, 0);
         this.placeDoor(world, random, structureBoundingBox, StructureStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
         this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1F, 1, 2, 1, Blocks.torch.getDefaultState());
         this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1F, 3, 2, 1, Blocks.torch.getDefaultState());
         this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1F, 1, 2, 5, Blocks.torch.getDefaultState());
         this.randomlyPlaceBlock(world, structureBoundingBox, random, 0.1F, 3, 2, 5, Blocks.torch.getDefaultState());
         if (this.expandsX) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 2, 0, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         if (this.expandsZ) {
            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 2, 4, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         }

         return true;
      }
   }

   abstract static class Stronghold extends StructureComponent {
      protected StructureStrongholdPieces.Stronghold.Door field_143013_d = StructureStrongholdPieces.Stronghold.Door.OPENING;

      public Stronghold() {
      }

      protected Stronghold(int integer) {
         super(integer);
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         nBTTagCompound.setString("EntryDoor", this.field_143013_d.name());
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         this.field_143013_d = StructureStrongholdPieces.Stronghold.Door.valueOf(nBTTagCompound.getString("EntryDoor"));
      }

      protected void placeDoor(World worldIn, Random p_74990_2_, StructureBoundingBox p_74990_3_, StructureStrongholdPieces.Stronghold.Door p_74990_4_, int p_74990_5_, int p_74990_6_, int p_74990_7_)
      {
         switch (p_74990_4_)
         {
            case OPENING:
            default:
               this.fillWithBlocks(worldIn, p_74990_3_, p_74990_5_, p_74990_6_, p_74990_7_, p_74990_5_ + 3 - 1, p_74990_6_ + 3 - 1, p_74990_7_, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
               break;

            case WOOD_DOOR:
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.oak_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.oak_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               break;

            case GRATES:
               this.setBlockState(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.air.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_bars.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
               break;

            case IRON_DOOR:
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 1, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 2, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stonebrick.getDefaultState(), p_74990_5_ + 2, p_74990_6_, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_door.getDefaultState(), p_74990_5_ + 1, p_74990_6_, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.iron_door.getStateFromMeta(8), p_74990_5_ + 1, p_74990_6_ + 1, p_74990_7_, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 4)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ + 1, p_74990_3_);
               this.setBlockState(worldIn, Blocks.stone_button.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_button, 3)), p_74990_5_ + 2, p_74990_6_ + 1, p_74990_7_ - 1, p_74990_3_);
         }
      }


      protected StructureStrongholdPieces.Stronghold.Door getRandomDoor(Random random) {
         int var4 = random.nextInt(5);
         switch (var4) {
            case 0:
            case 1:
            default:
               return StructureStrongholdPieces.Stronghold.Door.OPENING;
            case 2:
               return StructureStrongholdPieces.Stronghold.Door.WOOD_DOOR;
            case 3:
               return StructureStrongholdPieces.Stronghold.Door.GRATES;
            case 4:
               return StructureStrongholdPieces.Stronghold.Door.IRON_DOOR;
         }
      }

      protected StructureComponent getNextComponentNormal(
         StructureStrongholdPieces.Stairs2 stairs2, List<StructureComponent> list, Random random, int integer4, int integer5
      ) {
         if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case SOUTH:
                  return StructureStrongholdPieces.func_175953_c(
                     stairs2,
                     list,
                     random,
                     this.boundingBox.minX + integer4,
                     this.boundingBox.minY + integer5,
                     this.boundingBox.maxZ + 1,
                     this.coordBaseMode,
                     this.getComponentType()
                  );
               case WEST:
                  return StructureStrongholdPieces.func_175953_c(
                     stairs2,
                     list,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY + integer5,
                     this.boundingBox.minZ + integer4,
                     this.coordBaseMode,
                     this.getComponentType()
                  );
               case EAST:
                  return StructureStrongholdPieces.func_175953_c(
                     stairs2,
                     list,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY + integer5,
                     this.boundingBox.minZ + integer4,
                     this.coordBaseMode,
                     this.getComponentType()
                  );
               case NORTH:
                  return StructureStrongholdPieces.func_175953_c(
                     stairs2,
                     list,
                     random,
                     this.boundingBox.minX + integer4,
                     this.boundingBox.minY + integer5,
                     this.boundingBox.minZ - 1,
                     this.coordBaseMode,
                     this.getComponentType()
                  );
            }
         }

         return null;
      }

      protected StructureComponent getNextComponentX(
         StructureStrongholdPieces.Stairs2 stairs2, List<StructureComponent> list, Random random, int integer4, int integer5
      ) {
         if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case SOUTH:
               case NORTH:
                  return StructureStrongholdPieces.func_175953_c(
                     stairs2,
                     list,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY + integer4,
                     this.boundingBox.minZ + integer5,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
               case WEST:
               case EAST:
                  return StructureStrongholdPieces.func_175953_c(
                     stairs2,
                     list,
                     random,
                     this.boundingBox.minX + integer5,
                     this.boundingBox.minY + integer4,
                     this.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
            }
         }

         return null;
      }

      protected StructureComponent getNextComponentZ(
         StructureStrongholdPieces.Stairs2 stairs2, List<StructureComponent> list, Random random, int integer4, int integer5
      ) {
         if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case SOUTH:
               case NORTH:
                  return StructureStrongholdPieces.func_175953_c(
                     stairs2,
                     list,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY + integer4,
                     this.boundingBox.minZ + integer5,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
               case WEST:
               case EAST:
                  return StructureStrongholdPieces.func_175953_c(
                     stairs2,
                     list,
                     random,
                     this.boundingBox.minX + integer5,
                     this.boundingBox.minY + integer4,
                     this.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
            }
         }

         return null;
      }

      protected static boolean canStrongholdGoDeeper(StructureBoundingBox structureBoundingBox) {
         return structureBoundingBox != null && structureBoundingBox.minY > 10;
      }

      public enum Door {
         OPENING,
         WOOD_DOOR,
         GRATES,
         IRON_DOOR;

         private static StructureStrongholdPieces.Stronghold.Door[] Method905() {
            return new StructureStrongholdPieces.Stronghold.Door[]{OPENING, WOOD_DOOR, GRATES, IRON_DOOR};
         }
      }
   }
}
