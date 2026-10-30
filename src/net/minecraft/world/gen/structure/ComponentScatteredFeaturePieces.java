package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class ComponentScatteredFeaturePieces {
   public static void registerScatteredFeaturePieces() {
      MapGenStructureIO.registerStructureComponent(ComponentScatteredFeaturePieces.DesertPyramid.class, "TeDP");
      MapGenStructureIO.registerStructureComponent(ComponentScatteredFeaturePieces.JunglePyramid.class, "TeJP");
      MapGenStructureIO.registerStructureComponent(ComponentScatteredFeaturePieces.SwampHut.class, "TeSH");
   }

   public static class DesertPyramid extends ComponentScatteredFeaturePieces.Feature {
      private final boolean[] hasPlacedChest = new boolean[4];
      private static final List<WeightedRandomChestContent> itemsToGenerateInTemple = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15),
            new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2),
            new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20),
            new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16),
            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
         }
      );

      public DesertPyramid() {
      }

      public DesertPyramid(Random random, int integer2, int integer3) {
         super(random, integer2, 64, integer3, 21, 15, 21);
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("hasPlacedChest0", this.hasPlacedChest[0]);
         nBTTagCompound.setBoolean("hasPlacedChest1", this.hasPlacedChest[1]);
         nBTTagCompound.setBoolean("hasPlacedChest2", this.hasPlacedChest[2]);
         nBTTagCompound.setBoolean("hasPlacedChest3", this.hasPlacedChest[3]);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.hasPlacedChest[0] = nBTTagCompound.getBoolean("hasPlacedChest0");
         this.hasPlacedChest[1] = nBTTagCompound.getBoolean("hasPlacedChest1");
         this.hasPlacedChest[2] = nBTTagCompound.getBoolean("hasPlacedChest2");
         this.hasPlacedChest[3] = nBTTagCompound.getBoolean("hasPlacedChest3");
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            0,
            -4,
            0,
            this.scatteredFeatureSizeX - 1,
            0,
            this.scatteredFeatureSizeZ - 1,
            Blocks.sandstone.getDefaultState(),
            Blocks.sandstone.getDefaultState(),
            false
         );

         for (int var7 = 1; var7 <= 9; var7++) {
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               var7,
               var7,
               var7,
               this.scatteredFeatureSizeX - 1 - var7,
               var7,
               this.scatteredFeatureSizeZ - 1 - var7,
               Blocks.sandstone.getDefaultState(),
               Blocks.sandstone.getDefaultState(),
               false
            );
            this.fillWithBlocks(
               world,
               structureBoundingBox,
               var7 + 1,
               var7,
               var7 + 1,
               this.scatteredFeatureSizeX - 2 - var7,
               var7,
               this.scatteredFeatureSizeZ - 2 - var7,
               Blocks.air.getDefaultState(),
               Blocks.air.getDefaultState(),
               false
            );
         }

         for (int var17 = 0; var17 < this.scatteredFeatureSizeX; var17++) {
            for (int var8 = 0; var8 < this.scatteredFeatureSizeZ; var8++) {
               byte var9 = -5;
               this.replaceAirAndLiquidDownwards(world, Blocks.sandstone.getDefaultState(), var17, var9, var8, structureBoundingBox);
            }
         }

         int var18 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 3);
         int var19 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 2);
         int var20 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 0);
         int var10 = this.getMetadataWithOffset(Blocks.sandstone_stairs, 1);
         int var11 = ~EnumDyeColor.ORANGE.getDyeDamage() & 15;
         int var12 = ~EnumDyeColor.BLUE.getDyeDamage() & 15;
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 9, 4, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 10, 1, 3, 10, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var18), 2, 10, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var19), 2, 10, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var20), 0, 10, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var10), 4, 10, 2, structureBoundingBox);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 5,
            0,
            0,
            this.scatteredFeatureSizeX - 1,
            9,
            4,
            Blocks.sandstone.getDefaultState(),
            Blocks.air.getDefaultState(),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 4,
            10,
            1,
            this.scatteredFeatureSizeX - 2,
            10,
            3,
            Blocks.sandstone.getDefaultState(),
            Blocks.sandstone.getDefaultState(),
            false
         );
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var18), this.scatteredFeatureSizeX - 3, 10, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var19), this.scatteredFeatureSizeX - 3, 10, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var20), this.scatteredFeatureSizeX - 5, 10, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var10), this.scatteredFeatureSizeX - 1, 10, 2, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 8, 0, 0, 12, 4, 4, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 1, 0, 11, 3, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 9, 1, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 9, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 9, 3, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 10, 3, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 11, 3, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 11, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 11, 1, 1, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 8, 3, 3, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 1, 2, 8, 2, 2, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 12, 1, 1, 16, 3, 3, Blocks.sandstone.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 12, 1, 2, 16, 2, 2, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            5,
            4,
            5,
            this.scatteredFeatureSizeX - 6,
            4,
            this.scatteredFeatureSizeZ - 6,
            Blocks.sandstone.getDefaultState(),
            Blocks.sandstone.getDefaultState(),
            false
         );
         this.fillWithBlocks(world, structureBoundingBox, 9, 4, 9, 11, 4, 11, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            8,
            1,
            8,
            8,
            3,
            8,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            12,
            1,
            8,
            12,
            3,
            8,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            8,
            1,
            12,
            8,
            3,
            12,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            12,
            1,
            12,
            12,
            3,
            12,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 5, 4, 4, 11, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 5,
            1,
            5,
            this.scatteredFeatureSizeX - 2,
            4,
            11,
            Blocks.sandstone.getDefaultState(),
            Blocks.sandstone.getDefaultState(),
            false
         );
         this.fillWithBlocks(world, structureBoundingBox, 6, 7, 9, 6, 7, 11, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 7,
            7,
            9,
            this.scatteredFeatureSizeX - 7,
            7,
            11,
            Blocks.sandstone.getDefaultState(),
            Blocks.sandstone.getDefaultState(),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            5,
            5,
            9,
            5,
            7,
            11,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 6,
            5,
            9,
            this.scatteredFeatureSizeX - 6,
            7,
            11,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.setBlockState(world, Blocks.air.getDefaultState(), 5, 5, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 5, 6, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 6, 6, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - 6, 5, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - 6, 6, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), this.scatteredFeatureSizeX - 7, 6, 10, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 2, 4, 4, 2, 6, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 3,
            4,
            4,
            this.scatteredFeatureSizeX - 3,
            6,
            4,
            Blocks.air.getDefaultState(),
            Blocks.air.getDefaultState(),
            false
         );
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var18), 2, 4, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var18), 2, 3, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var18), this.scatteredFeatureSizeX - 3, 4, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var18), this.scatteredFeatureSizeX - 3, 3, 4, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 3, 2, 2, 3, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 3,
            1,
            3,
            this.scatteredFeatureSizeX - 2,
            2,
            3,
            Blocks.sandstone.getDefaultState(),
            Blocks.sandstone.getDefaultState(),
            false
         );
         this.setBlockState(world, Blocks.sandstone_stairs.getDefaultState(), 1, 1, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getDefaultState(), this.scatteredFeatureSizeX - 2, 1, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SAND.getMetadata()), 1, 2, 2, structureBoundingBox);
         this.setBlockState(
            world, Blocks.stone_slab.getStateFromMeta(BlockStoneSlab.EnumType.SAND.getMetadata()), this.scatteredFeatureSizeX - 2, 2, 2, structureBoundingBox
         );
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var10), 2, 1, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone_stairs.getStateFromMeta(var20), this.scatteredFeatureSizeX - 3, 1, 2, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 4, 3, 5, 4, 3, 18, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 5,
            3,
            5,
            this.scatteredFeatureSizeX - 5,
            3,
            17,
            Blocks.sandstone.getDefaultState(),
            Blocks.sandstone.getDefaultState(),
            false
         );
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 5, 4, 2, 16, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            this.scatteredFeatureSizeX - 6,
            1,
            5,
            this.scatteredFeatureSizeX - 5,
            2,
            16,
            Blocks.air.getDefaultState(),
            Blocks.air.getDefaultState(),
            false
         );

         for (byte var13 = 5; var13 <= 17; var13 += 2) {
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 4, 1, var13, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 4, 2, var13, structureBoundingBox);
            this.setBlockState(
               world,
               Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
               this.scatteredFeatureSizeX - 5,
               1,
               var13,
               structureBoundingBox
            );
            this.setBlockState(
               world,
               Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()),
               this.scatteredFeatureSizeX - 5,
               2,
               var13,
               structureBoundingBox
            );
         }

         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 10, 0, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 10, 0, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 9, 0, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 11, 0, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 8, 0, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 12, 0, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 7, 0, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 13, 0, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 9, 0, 11, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 11, 0, 11, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 10, 0, 12, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 10, 0, 13, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var12), 10, 0, 10, structureBoundingBox);

         for (int var21 = 0; var21 <= this.scatteredFeatureSizeX - 1; var21 += this.scatteredFeatureSizeX - 1) {
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 2, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 2, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 2, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 3, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 3, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 3, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 4, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), var21, 4, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 4, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 5, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 5, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 5, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 6, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), var21, 6, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 6, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 7, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 7, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var21, 7, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 8, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 8, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var21, 8, 3, structureBoundingBox);
         }

         for (int var22 = 2; var22 <= this.scatteredFeatureSizeX - 3; var22 += this.scatteredFeatureSizeX - 3 - 2) {
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22 - 1, 2, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22, 2, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22 + 1, 2, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22 - 1, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22 + 1, 3, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22 - 1, 4, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), var22, 4, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22 + 1, 4, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22 - 1, 5, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22, 5, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22 + 1, 5, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22 - 1, 6, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), var22, 6, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22 + 1, 6, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22 - 1, 7, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22, 7, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), var22 + 1, 7, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22 - 1, 8, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22, 8, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), var22 + 1, 8, 0, structureBoundingBox);
         }

         this.fillWithBlocks(
            world,
            structureBoundingBox,
            8,
            4,
            0,
            12,
            6,
            0,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.setBlockState(world, Blocks.air.getDefaultState(), 8, 6, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 12, 6, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 9, 5, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 10, 5, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.stained_hardened_clay.getStateFromMeta(var11), 11, 5, 0, structureBoundingBox);
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            8,
            -14,
            8,
            12,
            -11,
            12,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            8,
            -10,
            8,
            12,
            -10,
            12,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            8,
            -9,
            8,
            12,
            -9,
            12,
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()),
            false
         );
         this.fillWithBlocks(world, structureBoundingBox, 8, -8, 8, 12, -1, 12, Blocks.sandstone.getDefaultState(), Blocks.sandstone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 9, -11, 9, 11, -1, 11, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.setBlockState(world, Blocks.stone_pressure_plate.getDefaultState(), 10, -11, 10, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 9, -13, 9, 11, -13, 11, Blocks.tnt.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.setBlockState(world, Blocks.air.getDefaultState(), 8, -11, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 8, -10, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 7, -10, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 7, -11, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 12, -11, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 12, -10, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 13, -10, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 13, -11, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 10, -11, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 10, -10, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 10, -10, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 10, -11, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 10, -11, 12, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 10, -10, 12, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.CHISELED.getMetadata()), 10, -10, 13, structureBoundingBox);
         this.setBlockState(world, Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata()), 10, -11, 13, structureBoundingBox);

         for (EnumFacing var14 : EnumFacing.Plane.HORIZONTAL) {
            if (!this.hasPlacedChest[var14.getHorizontalIndex()]) {
               int var15 = var14.getFrontOffsetX() * 2;
               int var16 = var14.getFrontOffsetZ() * 2;
               this.hasPlacedChest[var14.getHorizontalIndex()] = this.generateChestContents(
                  world,
                  structureBoundingBox,
                  random,
                  10 + var15,
                  -11,
                  10 + var16,
                  WeightedRandomChestContent.func_177629_a(itemsToGenerateInTemple, Items.enchanted_book.getRandom(random)),
                  2 + random.nextInt(5)
               );
            }
         }

         return true;
      }
   }

   abstract static class Feature extends StructureComponent {
      protected int scatteredFeatureSizeX;
      protected int scatteredFeatureSizeY;
      protected int scatteredFeatureSizeZ;
      protected int field_74936_d = -1;

      public Feature() {
      }

      protected Feature(Random random, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7) {
         super(0);
         this.scatteredFeatureSizeX = integer5;
         this.scatteredFeatureSizeY = integer6;
         this.scatteredFeatureSizeZ = integer7;
         this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
         switch (this.coordBaseMode) {
            case NORTH:
            case SOUTH:
               this.boundingBox = new StructureBoundingBox(
                  integer2, integer3, integer4, integer2 + integer5 - 1, integer3 + integer6 - 1, integer4 + integer7 - 1
               );
               break;
            default:
               this.boundingBox = new StructureBoundingBox(
                  integer2, integer3, integer4, integer2 + integer7 - 1, integer3 + integer6 - 1, integer4 + integer5 - 1
               );
         }
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         nBTTagCompound.setInteger("Width", this.scatteredFeatureSizeX);
         nBTTagCompound.setInteger("Height", this.scatteredFeatureSizeY);
         nBTTagCompound.setInteger("Depth", this.scatteredFeatureSizeZ);
         nBTTagCompound.setInteger("HPos", this.field_74936_d);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         this.scatteredFeatureSizeX = nBTTagCompound.getInteger("Width");
         this.scatteredFeatureSizeY = nBTTagCompound.getInteger("Height");
         this.scatteredFeatureSizeZ = nBTTagCompound.getInteger("Depth");
         this.field_74936_d = nBTTagCompound.getInteger("HPos");
      }

      protected boolean func_74935_a(World world, StructureBoundingBox structureBoundingBox, int integer) {
         if (this.field_74936_d >= 0) {
            return true;
         }

         int var7 = 0;
         int var8 = 0;
         BlockPos.MutableBlockPos var9 = new BlockPos.MutableBlockPos();

         for (int var10 = this.boundingBox.minZ; var10 <= this.boundingBox.maxZ; var10++) {
            for (int var11 = this.boundingBox.minX; var11 <= this.boundingBox.maxX; var11++) {
               var9.set(var11, 64, var10);
               if (structureBoundingBox.isVecInside(var9)) {
                  var7 += Math.max(world.getTopSolidOrLiquidBlock(var9).getY(), world.provider.getAverageGroundLevel());
                  var8++;
               }
            }
         }

         if (var8 == 0) {
            return false;
         }

         this.field_74936_d = var7 / var8;
         this.boundingBox.offset(0, this.field_74936_d - this.boundingBox.minY + integer, 0);
         return true;
      }
   }

   public static class JunglePyramid extends ComponentScatteredFeaturePieces.Feature {
      private boolean placedMainChest;
      private boolean placedHiddenChest;
      private boolean placedTrap1;
      private boolean placedTrap2;
      private static final List<WeightedRandomChestContent> field_175816_i = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.gold_ingot, 0, 2, 7, 15),
            new WeightedRandomChestContent(Items.emerald, 0, 1, 3, 2),
            new WeightedRandomChestContent(Items.bone, 0, 4, 6, 20),
            new WeightedRandomChestContent(Items.rotten_flesh, 0, 3, 7, 16),
            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
         }
      );
      private static final List<WeightedRandomChestContent> field_175815_j = Lists.newArrayList(
         new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.arrow, 0, 2, 7, 30)}
      );
      private static final ComponentScatteredFeaturePieces.JunglePyramid.Stones junglePyramidsRandomScatteredStones = new ComponentScatteredFeaturePieces.JunglePyramid.Stones();

      public JunglePyramid() {
      }

      public JunglePyramid(Random random, int integer2, int integer3) {
         super(random, integer2, 64, integer3, 12, 10, 15);
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("placedMainChest", this.placedMainChest);
         nBTTagCompound.setBoolean("placedHiddenChest", this.placedHiddenChest);
         nBTTagCompound.setBoolean("placedTrap1", this.placedTrap1);
         nBTTagCompound.setBoolean("placedTrap2", this.placedTrap2);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.placedMainChest = nBTTagCompound.getBoolean("placedMainChest");
         this.placedHiddenChest = nBTTagCompound.getBoolean("placedHiddenChest");
         this.placedTrap1 = nBTTagCompound.getBoolean("placedTrap1");
         this.placedTrap2 = nBTTagCompound.getBoolean("placedTrap2");
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (!this.func_74935_a(world, structureBoundingBox, 0)) {
            return false;
         }

         int var7 = this.getMetadataWithOffset(Blocks.stone_stairs, 3);
         int var8 = this.getMetadataWithOffset(Blocks.stone_stairs, 2);
         int var9 = this.getMetadataWithOffset(Blocks.stone_stairs, 0);
         int var10 = this.getMetadataWithOffset(Blocks.stone_stairs, 1);
         this.fillWithRandomizedBlocks(
            world,
            structureBoundingBox,
            0,
            -4,
            0,
            this.scatteredFeatureSizeX - 1,
            0,
            this.scatteredFeatureSizeZ - 1,
            false,
            random,
            junglePyramidsRandomScatteredStones
         );
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 1, 2, 9, 2, 2, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 1, 12, 9, 2, 12, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 1, 3, 2, 2, 11, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, 1, 3, 9, 2, 11, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 3, 1, 10, 6, 1, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 3, 13, 10, 6, 13, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, 3, 2, 1, 6, 12, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 10, 3, 2, 10, 6, 12, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 3, 2, 9, 3, 12, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 6, 2, 9, 6, 12, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 3, 7, 3, 8, 7, 11, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 8, 4, 7, 8, 10, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithAir(world, structureBoundingBox, 3, 1, 3, 8, 2, 11);
         this.fillWithAir(world, structureBoundingBox, 4, 3, 6, 7, 3, 9);
         this.fillWithAir(world, structureBoundingBox, 2, 4, 2, 9, 5, 12);
         this.fillWithAir(world, structureBoundingBox, 4, 6, 5, 7, 6, 9);
         this.fillWithAir(world, structureBoundingBox, 5, 7, 6, 6, 7, 8);
         this.fillWithAir(world, structureBoundingBox, 5, 1, 2, 6, 2, 2);
         this.fillWithAir(world, structureBoundingBox, 5, 2, 12, 6, 2, 12);
         this.fillWithAir(world, structureBoundingBox, 5, 5, 1, 6, 5, 1);
         this.fillWithAir(world, structureBoundingBox, 5, 5, 13, 6, 5, 13);
         this.setBlockState(world, Blocks.air.getDefaultState(), 1, 5, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 10, 5, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 1, 5, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 10, 5, 9, structureBoundingBox);

         for (byte var11 = 0; var11 <= 14; var11 += 14) {
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 4, var11, 2, 5, var11, false, random, junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 4, var11, 4, 5, var11, false, random, junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 7, 4, var11, 7, 5, var11, false, random, junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, 4, var11, 9, 5, var11, false, random, junglePyramidsRandomScatteredStones);
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 5, 6, 0, 6, 6, 0, false, random, junglePyramidsRandomScatteredStones);

         for (byte var13 = 0; var13 <= 11; var13 += 11) {
            for (byte var12 = 2; var12 <= 12; var12 += 2) {
               this.fillWithRandomizedBlocks(world, structureBoundingBox, var13, 4, var12, var13, 5, var12, false, random, junglePyramidsRandomScatteredStones);
            }

            this.fillWithRandomizedBlocks(world, structureBoundingBox, var13, 6, 5, var13, 6, 5, false, random, junglePyramidsRandomScatteredStones);
            this.fillWithRandomizedBlocks(world, structureBoundingBox, var13, 6, 9, var13, 6, 9, false, random, junglePyramidsRandomScatteredStones);
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 7, 2, 2, 9, 2, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, 7, 2, 9, 9, 2, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, 7, 12, 2, 9, 12, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, 7, 12, 9, 9, 12, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 9, 4, 4, 9, 4, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 7, 9, 4, 7, 9, 4, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 9, 10, 4, 9, 10, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 7, 9, 10, 7, 9, 10, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 5, 9, 7, 6, 9, 7, false, random, junglePyramidsRandomScatteredStones);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 5, 9, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 6, 9, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var8), 5, 9, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var8), 6, 9, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 4, 0, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 5, 0, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 6, 0, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 7, 0, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 4, 1, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 4, 2, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 4, 3, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 7, 1, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 7, 2, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var7), 7, 3, 10, structureBoundingBox);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 9, 4, 1, 9, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 7, 1, 9, 7, 1, 9, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 4, 1, 10, 7, 2, 10, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 5, 4, 5, 6, 4, 5, false, random, junglePyramidsRandomScatteredStones);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var9), 4, 4, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var10), 7, 4, 5, structureBoundingBox);

         for (int var14 = 0; var14 < 4; var14++) {
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var8), 5, -var14, 6 + var14, structureBoundingBox);
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(var8), 6, -var14, 6 + var14, structureBoundingBox);
            this.fillWithAir(world, structureBoundingBox, 5, -var14, 7 + var14, 6, -var14, 9 + var14);
         }

         this.fillWithAir(world, structureBoundingBox, 1, -3, 12, 10, -1, 13);
         this.fillWithAir(world, structureBoundingBox, 1, -3, 1, 3, -1, 13);
         this.fillWithAir(world, structureBoundingBox, 1, -3, 1, 9, -1, 5);

         for (byte var15 = 1; var15 <= 13; var15 += 2) {
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, -3, var15, 1, -2, var15, false, random, junglePyramidsRandomScatteredStones);
         }

         for (byte var16 = 2; var16 <= 12; var16 += 2) {
            this.fillWithRandomizedBlocks(world, structureBoundingBox, 1, -1, var16, 3, -1, var16, false, random, junglePyramidsRandomScatteredStones);
         }

         this.fillWithRandomizedBlocks(world, structureBoundingBox, 2, -2, 1, 5, -2, 1, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 7, -2, 1, 9, -2, 1, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 6, -3, 1, 6, -3, 1, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 6, -1, 1, 6, -1, 1, false, random, junglePyramidsRandomScatteredStones);
         this.setBlockState(
            world,
            Blocks.tripwire_hook
               .getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.EAST.getHorizontalIndex()))
               .withProperty(BlockTripWireHook.ATTACHED, Boolean.TRUE),
            1,
            -3,
            8,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.tripwire_hook
               .getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.WEST.getHorizontalIndex()))
               .withProperty(BlockTripWireHook.ATTACHED, Boolean.TRUE),
            4,
            -3,
            8,
            structureBoundingBox
         );
         this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, Boolean.TRUE), 2, -3, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, Boolean.TRUE), 3, -3, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 5, -3, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 5, -3, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 5, -3, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 5, -3, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 5, -3, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 5, -3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 5, -3, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 4, -3, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 3, -3, 1, structureBoundingBox);
         if (!this.placedTrap1) {
            this.placedTrap1 = this.generateDispenserContents(world, structureBoundingBox, random, 3, -2, 1, EnumFacing.NORTH.getIndex(), field_175815_j, 2);
         }

         this.setBlockState(world, Blocks.vine.getStateFromMeta(15), 3, -2, 2, structureBoundingBox);
         this.setBlockState(
            world,
            Blocks.tripwire_hook
               .getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.NORTH.getHorizontalIndex()))
               .withProperty(BlockTripWireHook.ATTACHED, Boolean.TRUE),
            7,
            -3,
            1,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.tripwire_hook
               .getStateFromMeta(this.getMetadataWithOffset(Blocks.tripwire_hook, EnumFacing.SOUTH.getHorizontalIndex()))
               .withProperty(BlockTripWireHook.ATTACHED, Boolean.TRUE),
            7,
            -3,
            5,
            structureBoundingBox
         );
         this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, Boolean.TRUE), 7, -3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, Boolean.TRUE), 7, -3, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.tripwire.getDefaultState().withProperty(BlockTripWire.ATTACHED, Boolean.TRUE), 7, -3, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 8, -3, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 9, -3, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 9, -3, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 9, -3, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 9, -2, 4, structureBoundingBox);
         if (!this.placedTrap2) {
            this.placedTrap2 = this.generateDispenserContents(world, structureBoundingBox, random, 9, -2, 3, EnumFacing.WEST.getIndex(), field_175815_j, 2);
         }

         this.setBlockState(world, Blocks.vine.getStateFromMeta(15), 8, -1, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.vine.getStateFromMeta(15), 8, -2, 3, structureBoundingBox);
         if (!this.placedMainChest) {
            this.placedMainChest = this.generateChestContents(
               world,
               structureBoundingBox,
               random,
               8,
               -3,
               3,
               WeightedRandomChestContent.func_177629_a(field_175816_i, Items.enchanted_book.getRandom(random)),
               2 + random.nextInt(5)
            );
         }

         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 9, -3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 8, -3, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 4, -3, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 5, -2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 5, -1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 6, -3, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 7, -2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 7, -1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 8, -3, 5, structureBoundingBox);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 9, -1, 1, 9, -1, 5, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithAir(world, structureBoundingBox, 8, -3, 8, 10, -1, 10);
         this.setBlockState(world, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 8, -2, 11, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 9, -2, 11, structureBoundingBox);
         this.setBlockState(world, Blocks.stonebrick.getStateFromMeta(BlockStoneBrick.CHISELED_META), 10, -2, 11, structureBoundingBox);
         this.setBlockState(
            world,
            Blocks.lever
               .getStateFromMeta(BlockLever.getMetadataForFacing(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))),
            8,
            -2,
            12,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.lever
               .getStateFromMeta(BlockLever.getMetadataForFacing(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))),
            9,
            -2,
            12,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.lever
               .getStateFromMeta(BlockLever.getMetadataForFacing(EnumFacing.getFront(this.getMetadataWithOffset(Blocks.lever, EnumFacing.NORTH.getIndex())))),
            10,
            -2,
            12,
            structureBoundingBox
         );
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 8, -3, 8, 8, -3, 10, false, random, junglePyramidsRandomScatteredStones);
         this.fillWithRandomizedBlocks(world, structureBoundingBox, 10, -3, 8, 10, -3, 10, false, random, junglePyramidsRandomScatteredStones);
         this.setBlockState(world, Blocks.mossy_cobblestone.getDefaultState(), 10, -2, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 8, -2, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 8, -2, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.redstone_wire.getDefaultState(), 10, -1, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.sticky_piston.getStateFromMeta(EnumFacing.UP.getIndex()), 9, -2, 8, structureBoundingBox);
         this.setBlockState(
            world,
            Blocks.sticky_piston.getStateFromMeta(this.getMetadataWithOffset(Blocks.sticky_piston, EnumFacing.WEST.getIndex())),
            10,
            -2,
            8,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.sticky_piston.getStateFromMeta(this.getMetadataWithOffset(Blocks.sticky_piston, EnumFacing.WEST.getIndex())),
            10,
            -1,
            8,
            structureBoundingBox
         );
         this.setBlockState(
            world,
            Blocks.unpowered_repeater.getStateFromMeta(this.getMetadataWithOffset(Blocks.unpowered_repeater, EnumFacing.NORTH.getHorizontalIndex())),
            10,
            -2,
            10,
            structureBoundingBox
         );
         if (!this.placedHiddenChest) {
            this.placedHiddenChest = this.generateChestContents(
               world,
               structureBoundingBox,
               random,
               9,
               -3,
               10,
               WeightedRandomChestContent.func_177629_a(field_175816_i, Items.enchanted_book.getRandom(random)),
               2 + random.nextInt(5)
            );
         }

         return true;
      }

      static class Stones extends StructureComponent.BlockSelector {
         private Stones() {
         }

         @Override
         public void selectBlocks(Random random, int integer2, int integer3, int integer4, boolean boolean5) {
            if (random.nextFloat() < 0.4F) {
               this.blockstate = Blocks.cobblestone.getDefaultState();
            } else {
               this.blockstate = Blocks.mossy_cobblestone.getDefaultState();
            }
         }
      }
   }

   public static class SwampHut extends ComponentScatteredFeaturePieces.Feature {
      private boolean hasWitch;

      public SwampHut() {
      }

      public SwampHut(Random random, int integer2, int integer3) {
         super(random, integer2, 64, integer3, 7, 7, 9);
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("Witch", this.hasWitch);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.hasWitch = nBTTagCompound.getBoolean("Witch");
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (!this.func_74935_a(world, structureBoundingBox, 0)) {
            return false;
         }

         this.fillWithBlocks(
            world,
            structureBoundingBox,
            1,
            1,
            1,
            5,
            1,
            7,
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            1,
            4,
            2,
            5,
            4,
            7,
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            2,
            1,
            0,
            4,
            1,
            0,
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            2,
            2,
            2,
            3,
            3,
            2,
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            1,
            2,
            3,
            1,
            3,
            6,
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            5,
            2,
            3,
            5,
            3,
            6,
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            false
         );
         this.fillWithBlocks(
            world,
            structureBoundingBox,
            2,
            2,
            7,
            4,
            3,
            7,
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            Blocks.planks.getStateFromMeta(BlockPlanks.EnumType.SPRUCE.getMetadata()),
            false
         );
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 2, 1, 3, 2, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 0, 2, 5, 3, 2, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 7, 1, 3, 7, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 0, 7, 5, 3, 7, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 3, 3, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 1, 3, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 5, 3, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 5, 3, 5, structureBoundingBox);
         this.setBlockState(
            world,
            Blocks.flower_pot.getDefaultState().withProperty(BlockFlowerPot.CONTENTS, BlockFlowerPot.EnumFlowerType.MUSHROOM_RED),
            1,
            3,
            5,
            structureBoundingBox
         );
         this.setBlockState(world, Blocks.crafting_table.getDefaultState(), 3, 2, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.cauldron.getDefaultState(), 4, 2, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 5, 2, 1, structureBoundingBox);
         int var7 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         int var8 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
         int var9 = this.getMetadataWithOffset(Blocks.oak_stairs, 0);
         int var10 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
         this.fillWithBlocks(
            world, structureBoundingBox, 0, 4, 1, 6, 4, 1, Blocks.spruce_stairs.getStateFromMeta(var7), Blocks.spruce_stairs.getStateFromMeta(var7), false
         );
         this.fillWithBlocks(
            world, structureBoundingBox, 0, 4, 2, 0, 4, 7, Blocks.spruce_stairs.getStateFromMeta(var9), Blocks.spruce_stairs.getStateFromMeta(var9), false
         );
         this.fillWithBlocks(
            world, structureBoundingBox, 6, 4, 2, 6, 4, 7, Blocks.spruce_stairs.getStateFromMeta(var8), Blocks.spruce_stairs.getStateFromMeta(var8), false
         );
         this.fillWithBlocks(
            world, structureBoundingBox, 0, 4, 8, 6, 4, 8, Blocks.spruce_stairs.getStateFromMeta(var10), Blocks.spruce_stairs.getStateFromMeta(var10), false
         );

         for (byte var11 = 2; var11 <= 7; var11 += 5) {
            for (byte var12 = 1; var12 <= 5; var12 += 4) {
               this.replaceAirAndLiquidDownwards(world, Blocks.log.getDefaultState(), var12, -1, var11, structureBoundingBox);
            }
         }

         if (!this.hasWitch) {
            int var15 = this.getXWithOffset(2, 5);
            int var16 = this.getYWithOffset(2);
            int var13 = this.getZWithOffset(2, 5);
            if (structureBoundingBox.isVecInside(new BlockPos(var15, var16, var13))) {
               this.hasWitch = true;
               EntityWitch var14 = new EntityWitch(world);
               var14.setLocationAndAngles(var15 + 0.5, var16, var13 + 0.5, 0.0F, 0.0F);
               var14.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(var15, var16, var13)), null);
               world.spawnEntityInWorld(var14);
            }
         }

         return true;
      }
   }
}
