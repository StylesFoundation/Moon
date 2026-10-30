package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class StructureVillagePieces {
   public static void registerVillagePieces() {
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House1.class, "ViBH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Field1.class, "ViDF");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Field2.class, "ViF");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Torch.class, "ViL");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Hall.class, "ViPH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House4Garden.class, "ViSH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.WoodHut.class, "ViSmH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Church.class, "ViST");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House2.class, "ViS");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Start.class, "ViStart");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Path.class, "ViSR");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.House3.class, "ViTRH");
      MapGenStructureIO.registerStructureComponent(StructureVillagePieces.Well.class, "ViW");
   }

   public static List<StructureVillagePieces.PieceWeight> Method412(Random random, int integer) {
      List<StructureVillagePieces.PieceWeight> var4 = Lists.newArrayList();
      var4.add(
         new StructureVillagePieces.PieceWeight(
            StructureVillagePieces.House4Garden.class, 4, MathHelper.getRandomIntegerInRange(random, 2 + integer, 4 + integer * 2)
         )
      );
      var4.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.Church.class, 20, MathHelper.getRandomIntegerInRange(random, integer, 1 + integer))
      );
      var4.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.House1.class, 20, MathHelper.getRandomIntegerInRange(random, integer, 2 + integer))
      );
      var4.add(
         new StructureVillagePieces.PieceWeight(
            StructureVillagePieces.WoodHut.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + integer, 5 + integer * 3)
         )
      );
      var4.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Hall.class, 15, MathHelper.getRandomIntegerInRange(random, integer, 2 + integer)));
      var4.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field1.class, 3, MathHelper.getRandomIntegerInRange(random, 1 + integer, 4 + integer))
      );
      var4.add(
         new StructureVillagePieces.PieceWeight(
            StructureVillagePieces.Field2.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + integer, 4 + integer * 2)
         )
      );
      var4.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House2.class, 15, MathHelper.getRandomIntegerInRange(random, 0, 1 + integer)));
      var4.add(
         new StructureVillagePieces.PieceWeight(StructureVillagePieces.House3.class, 8, MathHelper.getRandomIntegerInRange(random, integer, 3 + integer * 2))
      );
      var4.removeIf(pieceWeight -> pieceWeight.villagePiecesLimit == 0);
      return var4;
   }

   private static int func_75079_a(List<StructureVillagePieces.PieceWeight> list) {
      boolean var4 = false;
      int var5 = 0;

      for (StructureVillagePieces.PieceWeight var7 : list) {
         if (var7.villagePiecesLimit > 0 && var7.villagePiecesSpawned < var7.villagePiecesLimit) {
            var4 = true;
         }

         var5 += var7.villagePieceWeight;
      }

      return var4 ? var5 : -1;
   }

   private static StructureVillagePieces.Village func_176065_a(
      StructureVillagePieces.Start start,
      StructureVillagePieces.PieceWeight pieceWeight,
      List<StructureComponent> list,
      Random random,
      int integer5,
      int integer6,
      int integer7,
      EnumFacing enumFacing,
      int integer9
   ) {
      Class var12 = pieceWeight.villagePieceClass;
      StructureVillagePieces.Village var13 = null;
      if (var12 == StructureVillagePieces.House4Garden.class) {
         var13 = StructureVillagePieces.House4Garden.func_175858_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      } else if (var12 == StructureVillagePieces.Church.class) {
         var13 = StructureVillagePieces.Church.func_175854_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      } else if (var12 == StructureVillagePieces.House1.class) {
         var13 = StructureVillagePieces.House1.func_175850_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      } else if (var12 == StructureVillagePieces.WoodHut.class) {
         var13 = StructureVillagePieces.WoodHut.func_175853_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      } else if (var12 == StructureVillagePieces.Hall.class) {
         var13 = StructureVillagePieces.Hall.func_175857_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      } else if (var12 == StructureVillagePieces.Field1.class) {
         var13 = StructureVillagePieces.Field1.func_175851_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      } else if (var12 == StructureVillagePieces.Field2.class) {
         var13 = StructureVillagePieces.Field2.func_175852_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      } else if (var12 == StructureVillagePieces.House2.class) {
         var13 = StructureVillagePieces.House2.func_175855_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      } else if (var12 == StructureVillagePieces.House3.class) {
         var13 = StructureVillagePieces.House3.func_175849_a(start, list, random, integer5, integer6, integer7, enumFacing, integer9);
      }

      return var13;
   }

   private static StructureVillagePieces.Village func_176067_c(
      StructureVillagePieces.Start start,
      List<StructureComponent> list,
      Random random,
      int integer4,
      int integer5,
      int integer6,
      EnumFacing enumFacing,
      int integer8
   ) {
      int var11 = func_75079_a(start.structureVillageWeightedPieceList);
      if (var11 <= 0) {
         return null;
      }

      int var12 = 0;

      while (var12 < 5) {
         var12++;
         int var13 = random.nextInt(var11);

         for (StructureVillagePieces.PieceWeight var15 : start.structureVillageWeightedPieceList) {
            var13 -= var15.villagePieceWeight;
            if (var13 < 0) {
               if (!var15.canSpawnMoreVillagePiecesOfType(integer8)
                  || var15 == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1) {
                  break;
               }

               StructureVillagePieces.Village var16 = func_176065_a(start, var15, list, random, integer4, integer5, integer6, enumFacing, integer8);
               if (var16 != null) {
                  var15.villagePiecesSpawned++;
                  start.structVillagePieceWeight = var15;
                  if (!var15.canSpawnMoreVillagePieces()) {
                     start.structureVillageWeightedPieceList.remove(var15);
                  }

                  return var16;
               }
            }
         }
      }

      StructureBoundingBox var17 = StructureVillagePieces.Torch.func_175856_a(start, list, random, integer4, integer5, integer6, enumFacing);
      return var17 != null ? new StructureVillagePieces.Torch(start, integer8, random, var17, enumFacing) : null;
   }

   private static StructureComponent Method413(
      StructureVillagePieces.Start start,
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

      if (Math.abs(integer4 - start.getBoundingBox().minX) <= 112 && Math.abs(integer6 - start.getBoundingBox().minZ) <= 112) {
         StructureVillagePieces.Village var11 = func_176067_c(start, list, random, integer4, integer5, integer6, enumFacing, integer8 + 1);
         if (var11 != null) {
            int var12 = (var11.boundingBox.minX + var11.boundingBox.maxX) / 2;
            int var13 = (var11.boundingBox.minZ + var11.boundingBox.maxZ) / 2;
            int var14 = var11.boundingBox.maxX - var11.boundingBox.minX;
            int var15 = var11.boundingBox.maxZ - var11.boundingBox.minZ;
            int var16 = Math.max(var14, var15);
            if (start.getWorldChunkManager().areBiomesViable(var12, var13, var16 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
               list.add(var11);
               start.field_74932_i.add(var11);
               return var11;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static StructureComponent Method414(
      StructureVillagePieces.Start start,
      List<StructureComponent> list,
      Random random,
      int integer4,
      int integer5,
      int integer6,
      EnumFacing enumFacing,
      int integer8
   ) {
      if (integer8 > 3 + start.terrainType) {
         return null;
      }

      if (Math.abs(integer4 - start.getBoundingBox().minX) <= 112 && Math.abs(integer6 - start.getBoundingBox().minZ) <= 112) {
         StructureBoundingBox var11 = StructureVillagePieces.Path.func_175848_a(start, list, random, integer4, integer5, integer6, enumFacing);
         if (var11 != null && var11.minY > 10) {
            StructureVillagePieces.Path var12 = new StructureVillagePieces.Path(start, integer8, random, var11, enumFacing);
            int var13 = (var12.boundingBox.minX + var12.boundingBox.maxX) / 2;
            int var14 = (var12.boundingBox.minZ + var12.boundingBox.maxZ) / 2;
            int var15 = var12.boundingBox.maxX - var12.boundingBox.minX;
            int var16 = var12.boundingBox.maxZ - var12.boundingBox.minZ;
            int var17 = Math.max(var15, var16);
            if (start.getWorldChunkManager().areBiomesViable(var13, var14, var17 / 2 + 4, MapGenVillage.villageSpawnBiomes)) {
               list.add(var12);
               start.field_74930_j.add(var12);
               return var12;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static class Church extends StructureVillagePieces.Village {
      public Church() {
      }

      public Church(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.Church func_175854_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 5, 12, 9, enumFacing);
         return canVillageGoDeeper(var10) && StructureComponent.findIntersecting(list, var10) == null
            ? new StructureVillagePieces.Church(start, integer8, random, var10, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 12 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 3, 7, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 3, 9, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 3, 0, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 3, 10, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 10, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 4, 0, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 0, 4, 4, 4, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 8, 3, 4, 8, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 5, 4, 3, 10, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 5, 5, 3, 5, 7, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 9, 0, 4, 9, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 11, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 11, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 2, 11, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 2, 11, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 1, 1, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 1, 1, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 2, 1, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 3, 1, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 3, 1, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 1, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 3, 1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 1)), 1, 2, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 0)), 3, 2, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 6, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 7, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 6, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 7, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 6, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 7, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 6, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 7, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 3, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 3, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 3, 8, structureBoundingBox);
         this.setBlockState(
            world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 2, 4, 7, structureBoundingBox
         );
         this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateY()), 1, 4, 6, structureBoundingBox);
         this.setBlockState(
            world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), 3, 4, 6, structureBoundingBox
         );
         this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 4, 5, structureBoundingBox);
         int var7 = this.getMetadataWithOffset(Blocks.ladder, 4);

         for (int var8 = 1; var8 <= 9; var8++) {
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var7), 3, var8, 3, structureBoundingBox);
         }

         this.setBlockState(world, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBox);
         this.placeDoorCurrentPosition(world, structureBoundingBox, random, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
         if (this.getBlockStateFromPos(world, 2, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(world, 2, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBox);
         }

         for (int var10 = 0; var10 < 9; var10++) {
            for (int var9 = 0; var9 < 5; var9++) {
               this.clearCurrentPositionBlocksUpwards(world, var9, 12, var10, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var9, -1, var10, structureBoundingBox);
            }
         }

         this.spawnVillagers(world, structureBoundingBox, 2, 1, 2, 1);
         return true;
      }

      @Override
      protected int func_180779_c(int integer1, int integer2) {
         return 2;
      }
   }

   public static class Field1 extends StructureVillagePieces.Village {
      private Block cropTypeA;
      private Block cropTypeB;
      private Block cropTypeC;
      private Block cropTypeD;

      public Field1() {
      }

      public Field1(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
         this.cropTypeA = this.func_151559_a(random);
         this.cropTypeB = this.func_151559_a(random);
         this.cropTypeC = this.func_151559_a(random);
         this.cropTypeD = this.func_151559_a(random);
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
         nBTTagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
         nBTTagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
         nBTTagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.cropTypeA = Block.getBlockById(nBTTagCompound.getInteger("CA"));
         this.cropTypeB = Block.getBlockById(nBTTagCompound.getInteger("CB"));
         this.cropTypeC = Block.getBlockById(nBTTagCompound.getInteger("CC"));
         this.cropTypeD = Block.getBlockById(nBTTagCompound.getInteger("CD"));
      }

      private Block func_151559_a(Random random) {
         switch (random.nextInt(5)) {
            case 0:
               return Blocks.carrots;
            case 1:
               return Blocks.potatoes;
            default:
               return Blocks.wheat;
         }
      }

      public static StructureVillagePieces.Field1 func_175851_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 13, 4, 9, enumFacing);
         return canVillageGoDeeper(var10) && StructureComponent.findIntersecting(list, var10) == null
            ? new StructureVillagePieces.Field1(start, integer8, random, var10, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 12, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 0, 1, 8, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 0, 1, 11, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 12, 0, 0, 12, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 11, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 8, 11, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 0, 1, 9, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);

         for (int var7 = 1; var7 <= 7; var7++) {
            this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 1, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 2, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 4, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 5, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 7, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeC.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 8, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 10, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeD.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 11, 1, var7, structureBoundingBox);
         }

         for (int var9 = 0; var9 < 9; var9++) {
            for (int var8 = 0; var8 < 13; var8++) {
               this.clearCurrentPositionBlocksUpwards(world, var8, 4, var9, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.dirt.getDefaultState(), var8, -1, var9, structureBoundingBox);
            }
         }

         return true;
      }
   }

   public static class Field2 extends StructureVillagePieces.Village {
      private Block cropTypeA;
      private Block cropTypeB;

      public Field2() {
      }

      public Field2(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
         this.cropTypeA = this.func_151560_a(random);
         this.cropTypeB = this.func_151560_a(random);
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
         nBTTagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.cropTypeA = Block.getBlockById(nBTTagCompound.getInteger("CA"));
         this.cropTypeB = Block.getBlockById(nBTTagCompound.getInteger("CB"));
      }

      private Block func_151560_a(Random random) {
         switch (random.nextInt(5)) {
            case 0:
               return Blocks.carrots;
            case 1:
               return Blocks.potatoes;
            default:
               return Blocks.wheat;
         }
      }

      public static StructureVillagePieces.Field2 func_175852_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 7, 4, 9, enumFacing);
         return canVillageGoDeeper(var10) && StructureComponent.findIntersecting(list, var10) == null
            ? new StructureVillagePieces.Field2(start, integer8, random, var10, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 6, 4, 8, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 0, 1, 5, 0, 7, Blocks.farmland.getDefaultState(), Blocks.farmland.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 0, 0, 6, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 5, 0, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 8, 5, 0, 8, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 0, 1, 3, 0, 7, Blocks.water.getDefaultState(), Blocks.water.getDefaultState(), false);

         for (int var7 = 1; var7 <= 7; var7++) {
            this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 1, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeA.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 2, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 4, 1, var7, structureBoundingBox);
            this.setBlockState(world, this.cropTypeB.getStateFromMeta(MathHelper.getRandomIntegerInRange(random, 2, 7)), 5, 1, var7, structureBoundingBox);
         }

         for (int var9 = 0; var9 < 9; var9++) {
            for (int var8 = 0; var8 < 7; var8++) {
               this.clearCurrentPositionBlocksUpwards(world, var8, 4, var9, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.dirt.getDefaultState(), var8, -1, var9, structureBoundingBox);
            }
         }

         return true;
      }
   }

   public static class Hall extends StructureVillagePieces.Village {
      public Hall() {
      }

      public Hall(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.Hall func_175857_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 9, 7, 11, enumFacing);
         return canVillageGoDeeper(var10) && StructureComponent.findIntersecting(list, var10) == null
            ? new StructureVillagePieces.Hall(start, integer8, random, var10, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 0, 6, 8, 0, 10, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 6, 0, 6, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 2, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 1, 6, 8, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 10, 7, 1, 10, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 0, 0, 8, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 5, 7, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 5, 7, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 4, 4, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBox);
         int var7 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         int var8 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

         for (int var9 = -1; var9 <= 2; var9++) {
            for (int var10 = 0; var10 <= 8; var10++) {
               this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var7), var10, 4 + var9, var9, structureBoundingBox);
               this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var8), var10, 4 + var9, 5 - var9, structureBoundingBox);
            }
         }

         this.setBlockState(world, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 1, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 3, structureBoundingBox);
         this.fillWithBlocks(
            world, structureBoundingBox, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab.getDefaultState(), Blocks.double_stone_slab.getDefaultState(), false
         );
         this.setBlockState(world, Blocks.double_stone_slab.getDefaultState(), 6, 1, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.double_stone_slab.getDefaultState(), 6, 1, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, structureBoundingBox);
         this.placeDoorCurrentPosition(world, structureBoundingBox, random, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
         if (this.getBlockStateFromPos(world, 2, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(world, 2, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBox);
         }

         this.setBlockState(world, Blocks.air.getDefaultState(), 6, 1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 6, 2, 5, structureBoundingBox);
         this.setBlockState(
            world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 6, 3, 4, structureBoundingBox
         );
         this.placeDoorCurrentPosition(world, structureBoundingBox, random, 6, 1, 5, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));

         for (int var11 = 0; var11 < 5; var11++) {
            for (int var12 = 0; var12 < 9; var12++) {
               this.clearCurrentPositionBlocksUpwards(world, var12, 7, var11, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var12, -1, var11, structureBoundingBox);
            }
         }

         this.spawnVillagers(world, structureBoundingBox, 4, 1, 2, 2);
         return true;
      }

      @Override
      protected int func_180779_c(int integer1, int integer2) {
         return integer1 == 0 ? 4 : super.func_180779_c(integer1, integer2);
      }
   }

   public static class House1 extends StructureVillagePieces.Village {
      public House1() {
      }

      public House1(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.House1 func_175850_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 9, 9, 6, enumFacing);
         return canVillageGoDeeper(var10) && StructureComponent.findIntersecting(list, var10) == null
            ? new StructureVillagePieces.House1(start, integer8, random, var10, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 9 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 7, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 8, 0, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 8, 5, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 6, 1, 8, 6, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 7, 2, 8, 7, 3, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         int var7 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         int var8 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

         for (int var9 = -1; var9 <= 2; var9++) {
            for (int var10 = 0; var10 <= 8; var10++) {
               this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var7), var10, 6 + var9, var9, structureBoundingBox);
               this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var8), var10, 6 + var9, 5 - var9, structureBoundingBox);
            }
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 5, 8, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 1, 0, 8, 1, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 0, 7, 1, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 2, 5, 0, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 2, 5, 8, 4, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 2, 0, 8, 4, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 2, 1, 0, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 5, 7, 4, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 2, 1, 8, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 7, 4, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 6, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 3, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 5, 3, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 6, 3, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 3, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 3, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 3, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 5, 2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 6, 2, 5, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 1, 4, 1, 7, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 4, 4, 7, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 4, 7, 3, 4, Blocks.bookshelf.getDefaultState(), Blocks.bookshelf.getDefaultState(), false);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 7, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 0)), 7, 1, 3, structureBoundingBox);
         int var12 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var12), 6, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var12), 5, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var12), 4, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var12), 3, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 6, 1, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), 6, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 1, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), 4, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.crafting_table.getDefaultState(), 7, 1, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBox);
         this.placeDoorCurrentPosition(world, structureBoundingBox, random, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
         if (this.getBlockStateFromPos(world, 1, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(world, 1, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBox);
         }

         for (int var13 = 0; var13 < 6; var13++) {
            for (int var11 = 0; var11 < 9; var11++) {
               this.clearCurrentPositionBlocksUpwards(world, var11, 9, var13, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var11, -1, var13, structureBoundingBox);
            }
         }

         this.spawnVillagers(world, structureBoundingBox, 2, 1, 2, 1);
         return true;
      }

      @Override
      protected int func_180779_c(int integer1, int integer2) {
         return 1;
      }
   }

   public static class House2 extends StructureVillagePieces.Village {
      private static final List<WeightedRandomChestContent> villageBlacksmithChestContents = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
            new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5),
            new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5),
            new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5),
            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
         }
      );
      private boolean hasMadeChest;

      public House2() {
      }

      public House2(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.House2 func_175855_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 10, 6, 7, enumFacing);
         return canVillageGoDeeper(var10) && StructureComponent.findIntersecting(list, var10) == null
            ? new StructureVillagePieces.House2(start, integer8, random, var10, enumFacing)
            : null;
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
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 9, 4, 6, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 9, 0, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 5, 0, 9, 5, 6, Blocks.stone_slab.getDefaultState(), Blocks.stone_slab.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 8, 5, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 3, 4, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 6, 0, 4, 6, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 3, 1, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 2, 3, 3, 2, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 1, 3, 5, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 6, 5, 3, 6, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 5, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 1, 0, 9, 3, 0, Blocks.oak_fence.getDefaultState(), Blocks.oak_fence.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 1, 4, 9, 4, 6, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.setBlockState(world, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 9, 2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.iron_bars.getDefaultState(), 9, 2, 4, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 7, 2, 4, 8, 2, 5, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 6, 1, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.furnace.getDefaultState(), 6, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.furnace.getDefaultState(), 6, 3, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 2, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 2, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), 2, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 3)), 2, 1, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.oak_stairs, 1)), 1, 1, 4, structureBoundingBox);
         if (!this.hasMadeChest && structureBoundingBox.isVecInside(new BlockPos(this.getXWithOffset(5, 5), this.getYWithOffset(1), this.getZWithOffset(5, 5)))
            )
          {
            this.hasMadeChest = true;
            this.generateChestContents(world, structureBoundingBox, random, 5, 1, 5, villageBlacksmithChestContents, 3 + random.nextInt(6));
         }

         for (int var7 = 6; var7 <= 8; var7++) {
            if (this.getBlockStateFromPos(world, var7, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air
               && this.getBlockStateFromPos(world, var7, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
               this.setBlockState(
                  world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), var7, 0, -1, structureBoundingBox
               );
            }
         }

         for (int var9 = 0; var9 < 7; var9++) {
            for (int var8 = 0; var8 < 10; var8++) {
               this.clearCurrentPositionBlocksUpwards(world, var8, 6, var9, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var8, -1, var9, structureBoundingBox);
            }
         }

         this.spawnVillagers(world, structureBoundingBox, 7, 1, 1, 1);
         return true;
      }

      @Override
      protected int func_180779_c(int integer1, int integer2) {
         return 3;
      }
   }

   public static class House3 extends StructureVillagePieces.Village {
      public House3() {
      }

      public House3(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      public static StructureVillagePieces.House3 func_175849_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 9, 7, 12, enumFacing);
         return canVillageGoDeeper(var10) && StructureComponent.findIntersecting(list, var10) == null
            ? new StructureVillagePieces.House3(start, integer8, random, var10, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 7 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 7, 4, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 8, 4, 10, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 0, 5, 8, 0, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 7, 0, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 3, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 0, 0, 8, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 7, 2, 0, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 5, 2, 1, 5, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 0, 6, 2, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 0, 10, 7, 3, 10, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 7, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 5, 2, 3, 5, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 4, 1, 8, 4, 1, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 4, 4, 3, 4, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 5, 2, 8, 5, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 0, 4, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 4, 4, structureBoundingBox);
         int var7 = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
         int var8 = this.getMetadataWithOffset(Blocks.oak_stairs, 2);

         for (int var9 = -1; var9 <= 2; var9++) {
            for (int var10 = 0; var10 <= 8; var10++) {
               this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var7), var10, 4 + var9, var9, structureBoundingBox);
               if ((var9 > -1 || var10 <= 1) && (var9 > 0 || var10 <= 3) && (var9 > 1 || var10 != 5)) {
                  this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var8), var10, 4 + var9, 5 - var9, structureBoundingBox);
               }
            }
         }

         this.fillWithBlocks(world, structureBoundingBox, 3, 4, 5, 3, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 4, 2, 7, 4, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 5, 4, 4, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 5, 4, 6, 5, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 6, 3, 5, 6, 10, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         int var13 = this.getMetadataWithOffset(Blocks.oak_stairs, 0);

         for (int var14 = 4; var14 >= 1; var14--) {
            this.setBlockState(world, Blocks.planks.getDefaultState(), var14, 2 + var14, 7 - var14, structureBoundingBox);

            for (int var11 = 8 - var14; var11 <= 10; var11++) {
               this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var13), var14, 2 + var14, var11, structureBoundingBox);
            }
         }

         int var15 = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 6, 6, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 7, 5, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var15), 6, 6, 4, structureBoundingBox);

         for (int var16 = 6; var16 <= 8; var16++) {
            for (int var12 = 5; var12 <= 10; var12++) {
               this.setBlockState(world, Blocks.oak_stairs.getStateFromMeta(var15), var16, 12 - var16, var12, structureBoundingBox);
            }
         }

         this.setBlockState(world, Blocks.log.getDefaultState(), 0, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 0, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 4, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 5, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 6, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 2, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 8, 2, 5, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 2, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 8, 2, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 8, 2, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 2, 2, 6, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 2, 7, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 2, 8, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 2, 2, 9, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 4, 4, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 5, 4, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 6, 4, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 5, 5, 10, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 2, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 2, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, structureBoundingBox);
         this.placeDoorCurrentPosition(world, structureBoundingBox, random, 2, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, -1, 3, 2, -1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         if (this.getBlockStateFromPos(world, 2, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(world, 2, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBox);
         }

         for (int var17 = 0; var17 < 5; var17++) {
            for (int var19 = 0; var19 < 9; var19++) {
               this.clearCurrentPositionBlocksUpwards(world, var19, 7, var17, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var19, -1, var17, structureBoundingBox);
            }
         }

         for (int var18 = 5; var18 < 11; var18++) {
            for (int var20 = 2; var20 < 9; var20++) {
               this.clearCurrentPositionBlocksUpwards(world, var20, 7, var18, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var20, -1, var18, structureBoundingBox);
            }
         }

         this.spawnVillagers(world, structureBoundingBox, 4, 1, 2, 2);
         return true;
      }
   }

   public static class House4Garden extends StructureVillagePieces.Village {
      private boolean isRoofAccessible;

      public House4Garden() {
      }

      public House4Garden(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
         this.isRoofAccessible = random.nextBoolean();
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setBoolean("Terrace", this.isRoofAccessible);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.isRoofAccessible = nBTTagCompound.getBoolean("Terrace");
      }

      public static StructureVillagePieces.House4Garden func_175858_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 5, 6, 5, enumFacing);
         return StructureComponent.findIntersecting(list, var10) != null
            ? null
            : new StructureVillagePieces.House4Garden(start, integer8, random, var10, enumFacing);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 4, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 4, 4, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 4, 1, 3, 4, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 3, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 3, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 0, 3, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 1, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.cobblestone.getDefaultState(), 4, 3, 4, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 4, 3, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 2, 2, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 3, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 2, 3, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 3, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 1, 0, structureBoundingBox);
         if (this.getBlockStateFromPos(world, 2, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(world, 2, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 2, 0, -1, structureBoundingBox);
         }

         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 3, 3, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         if (this.isRoofAccessible) {
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 5, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 5, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 3, 5, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 0, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 5, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 2, 5, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 3, 5, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 4, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 5, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 1, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 2, structureBoundingBox);
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 0, 5, 3, structureBoundingBox);
         }

         if (this.isRoofAccessible) {
            int var7 = this.getMetadataWithOffset(Blocks.ladder, 3);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var7), 3, 1, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var7), 3, 2, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var7), 3, 3, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.ladder.getStateFromMeta(var7), 3, 4, 3, structureBoundingBox);
         }

         this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 2, 3, 1, structureBoundingBox);

         for (int var9 = 0; var9 < 5; var9++) {
            for (int var8 = 0; var8 < 5; var8++) {
               this.clearCurrentPositionBlocksUpwards(world, var8, 6, var9, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var8, -1, var9, structureBoundingBox);
            }
         }

         this.spawnVillagers(world, structureBoundingBox, 1, 1, 2, 1);
         return true;
      }
   }

   public static class Path extends StructureVillagePieces.Road {
      private int length;

      public Path() {
      }

      public Path(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
         this.length = Math.max(structureBoundingBox.getXSize(), structureBoundingBox.getZSize());
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setInteger("Length", this.length);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.length = nBTTagCompound.getInteger("Length");
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         boolean var7 = false;

         for (int var8 = random.nextInt(5); var8 < this.length - 8; var8 += 2 + random.nextInt(5)) {
            StructureComponent var9 = this.Method675((StructureVillagePieces.Start)structureComponent, list, random, 0, var8);
            if (var9 != null) {
               var8 += Math.max(var9.boundingBox.getXSize(), var9.boundingBox.getZSize());
               var7 = true;
            }
         }

         for (int var10 = random.nextInt(5); var10 < this.length - 8; var10 += 2 + random.nextInt(5)) {
            StructureComponent var11 = this.Method676((StructureVillagePieces.Start)structureComponent, list, random, 0, var10);
            if (var11 != null) {
               var10 += Math.max(var11.boundingBox.getXSize(), var11.boundingBox.getZSize());
               var7 = true;
            }
         }

         if (var7 && random.nextInt(3) > 0 && this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case NORTH:
                  StructureVillagePieces.Method414(
                     (StructureVillagePieces.Start)structureComponent,
                     list,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY,
                     this.boundingBox.minZ,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
                  break;
               case SOUTH:
                  StructureVillagePieces.Method414(
                     (StructureVillagePieces.Start)structureComponent,
                     list,
                     random,
                     this.boundingBox.minX - 1,
                     this.boundingBox.minY,
                     this.boundingBox.maxZ - 2,
                     EnumFacing.WEST,
                     this.getComponentType()
                  );
                  break;
               case WEST:
                  StructureVillagePieces.Method414(
                     (StructureVillagePieces.Start)structureComponent,
                     list,
                     random,
                     this.boundingBox.minX,
                     this.boundingBox.minY,
                     this.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
                  break;
               case EAST:
                  StructureVillagePieces.Method414(
                     (StructureVillagePieces.Start)structureComponent,
                     list,
                     random,
                     this.boundingBox.maxX - 2,
                     this.boundingBox.minY,
                     this.boundingBox.minZ - 1,
                     EnumFacing.NORTH,
                     this.getComponentType()
                  );
            }
         }

         if (var7 && random.nextInt(3) > 0 && this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case NORTH:
                  StructureVillagePieces.Method414(
                     (StructureVillagePieces.Start)structureComponent,
                     list,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY,
                     this.boundingBox.minZ,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
                  break;
               case SOUTH:
                  StructureVillagePieces.Method414(
                     (StructureVillagePieces.Start)structureComponent,
                     list,
                     random,
                     this.boundingBox.maxX + 1,
                     this.boundingBox.minY,
                     this.boundingBox.maxZ - 2,
                     EnumFacing.EAST,
                     this.getComponentType()
                  );
                  break;
               case WEST:
                  StructureVillagePieces.Method414(
                     (StructureVillagePieces.Start)structureComponent,
                     list,
                     random,
                     this.boundingBox.minX,
                     this.boundingBox.minY,
                     this.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
                  break;
               case EAST:
                  StructureVillagePieces.Method414(
                     (StructureVillagePieces.Start)structureComponent,
                     list,
                     random,
                     this.boundingBox.maxX - 2,
                     this.boundingBox.minY,
                     this.boundingBox.maxZ + 1,
                     EnumFacing.SOUTH,
                     this.getComponentType()
                  );
            }
         }
      }

      public static StructureBoundingBox func_175848_a(
         StructureVillagePieces.Start start, List<StructureComponent> list, Random random, int integer4, int integer5, int integer6, EnumFacing enumFacing
      ) {
         for (int var10 = 7 * MathHelper.getRandomIntegerInRange(random, 3, 5); var10 >= 7; var10 -= 7) {
            StructureBoundingBox var11 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 3, 3, var10, enumFacing);
            if (StructureComponent.findIntersecting(list, var11) == null) {
               return var11;
            }
         }

         return null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         IBlockState var7 = this.func_175847_a(Blocks.gravel.getDefaultState());
         IBlockState var8 = this.func_175847_a(Blocks.cobblestone.getDefaultState());

         for (int var9 = this.boundingBox.minX; var9 <= this.boundingBox.maxX; var9++) {
            for (int var10 = this.boundingBox.minZ; var10 <= this.boundingBox.maxZ; var10++) {
               BlockPos var11 = new BlockPos(var9, 64, var10);
               if (structureBoundingBox.isVecInside(var11)) {
                  var11 = world.getTopSolidOrLiquidBlock(var11).down();
                  world.setBlockState(var11, var7, 2);
                  world.setBlockState(var11.down(), var8, 2);
               }
            }
         }

         return true;
      }
   }

   public static class PieceWeight {
      public Class<? extends StructureVillagePieces.Village> villagePieceClass;
      public final int villagePieceWeight;
      public int villagePiecesSpawned;
      public int villagePiecesLimit;

      public PieceWeight(Class<? extends StructureVillagePieces.Village> class1, int integer2, int integer3) {
         this.villagePieceClass = class1;
         this.villagePieceWeight = integer2;
         this.villagePiecesLimit = integer3;
      }

      public boolean canSpawnMoreVillagePiecesOfType(int integer) {
         return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
      }

      public boolean canSpawnMoreVillagePieces() {
         return this.villagePiecesLimit == 0 || this.villagePiecesSpawned < this.villagePiecesLimit;
      }
   }

   public abstract static class Road extends StructureVillagePieces.Village {
      public Road() {
      }

      protected Road(StructureVillagePieces.Start start, int integer) {
         super(start, integer);
      }
   }

   public static class Start extends StructureVillagePieces.Well {
      public WorldChunkManager worldChunkMngr;
      public boolean inDesert;
      public int terrainType;
      public StructureVillagePieces.PieceWeight structVillagePieceWeight;
      public List<StructureVillagePieces.PieceWeight> structureVillageWeightedPieceList;
      public List<StructureComponent> field_74932_i = Lists.newArrayList();
      public List<StructureComponent> field_74930_j = Lists.newArrayList();

      public Start() {
      }

      public Start(
         WorldChunkManager worldChunkManager,
         int integer2,
         Random random,
         int integer4,
         int integer5,
         List<StructureVillagePieces.PieceWeight> list,
         int integer7
      ) {
         super(null, 0, random, integer4, integer5);
         this.worldChunkMngr = worldChunkManager;
         this.structureVillageWeightedPieceList = list;
         this.terrainType = integer7;
         BiomeGenBase var11 = worldChunkManager.getBiomeGenerator(new BlockPos(integer4, 0, integer5), BiomeGenBase.field_180279_ad);
         this.inDesert = var11 == BiomeGenBase.desert || var11 == BiomeGenBase.desertHills;
         this.func_175846_a(this.inDesert);
      }

      public WorldChunkManager getWorldChunkManager() {
         return this.worldChunkMngr;
      }
   }

   public static class Torch extends StructureVillagePieces.Village {
      public Torch() {
      }

      public Torch(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      public static StructureBoundingBox func_175856_a(
         StructureVillagePieces.Start start, List<StructureComponent> list, Random random, int integer4, int integer5, int integer6, EnumFacing enumFacing
      ) {
         StructureBoundingBox var9 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 3, 4, 2, enumFacing);
         return StructureComponent.findIntersecting(list, var9) != null ? null : var9;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 4 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 2, 3, 1, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 0, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 2, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBox);
         boolean var7 = this.coordBaseMode == EnumFacing.EAST || this.coordBaseMode == EnumFacing.NORTH;
         this.setBlockState(
            world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateY()), var7 ? 2 : 0, 3, 0, structureBoundingBox
         );
         this.setBlockState(world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode), 1, 3, 1, structureBoundingBox);
         this.setBlockState(
            world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.rotateYCCW()), var7 ? 0 : 2, 3, 0, structureBoundingBox
         );
         this.setBlockState(
            world, Blocks.torch.getDefaultState().withProperty(BlockTorch.FACING, this.coordBaseMode.getOpposite()), 1, 3, -1, structureBoundingBox
         );
         return true;
      }
   }

   abstract static class Village extends StructureComponent {
      protected int field_143015_k = -1;
      private int villagersSpawned;
      private boolean isDesertVillage;

      public Village() {
      }

      protected Village(StructureVillagePieces.Start start, int integer) {
         super(integer);
         if (start != null) {
            this.isDesertVillage = start.inDesert;
         }
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         nBTTagCompound.setInteger("HPos", this.field_143015_k);
         nBTTagCompound.setInteger("VCount", this.villagersSpawned);
         nBTTagCompound.setBoolean("Desert", this.isDesertVillage);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         this.field_143015_k = nBTTagCompound.getInteger("HPos");
         this.villagersSpawned = nBTTagCompound.getInteger("VCount");
         this.isDesertVillage = nBTTagCompound.getBoolean("Desert");
      }

      protected StructureComponent Method675(StructureVillagePieces.Start start, List<StructureComponent> list, Random random, int integer4, int integer5) {
         if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case NORTH:
               case SOUTH:
                  return StructureVillagePieces.Method413(
                     start,
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
                  return StructureVillagePieces.Method413(
                     start,
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

      protected StructureComponent Method676(StructureVillagePieces.Start start, List<StructureComponent> list, Random random, int integer4, int integer5) {
         if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
               case NORTH:
               case SOUTH:
                  return StructureVillagePieces.Method413(
                     start,
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
                  return StructureVillagePieces.Method413(
                     start,
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

      protected int getAverageGroundLevel(World world, StructureBoundingBox structureBoundingBox) {
         int var6 = 0;
         int var7 = 0;
         BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

         for (int var9 = this.boundingBox.minZ; var9 <= this.boundingBox.maxZ; var9++) {
            for (int var10 = this.boundingBox.minX; var10 <= this.boundingBox.maxX; var10++) {
               var8.set(var10, 64, var9);
               if (structureBoundingBox.isVecInside(var8)) {
                  var6 += Math.max(world.getTopSolidOrLiquidBlock(var8).getY(), world.provider.getAverageGroundLevel());
                  var7++;
               }
            }
         }

         return var7 == 0 ? -1 : var6 / var7;
      }

      protected static boolean canVillageGoDeeper(StructureBoundingBox structureBoundingBox) {
         return structureBoundingBox != null && structureBoundingBox.minY > 10;
      }

      protected void spawnVillagers(World world, StructureBoundingBox structureBoundingBox, int integer3, int integer4, int integer5, int integer6) {
         if (this.villagersSpawned < integer6) {
            for (int var10 = this.villagersSpawned; var10 < integer6; var10++) {
               int var11 = this.getXWithOffset(integer3 + var10, integer5);
               int var12 = this.getYWithOffset(integer4);
               int var13 = this.getZWithOffset(integer3 + var10, integer5);
               if (!structureBoundingBox.isVecInside(new BlockPos(var11, var12, var13))) {
                  break;
               }

               this.villagersSpawned++;
               EntityVillager var14 = new EntityVillager(world);
               var14.setLocationAndAngles(var11 + 0.5, var12, var13 + 0.5, 0.0F, 0.0F);
               var14.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(var14)), null);
               var14.setProfession(this.func_180779_c(var10, var14.getProfession()));
               world.spawnEntityInWorld(var14);
            }
         }
      }

      protected int func_180779_c(int integer1, int integer2) {
         return integer2;
      }

      protected IBlockState func_175847_a(IBlockState iBlockState) {
         if (this.isDesertVillage) {
            if (iBlockState.getBlock() == Blocks.log || iBlockState.getBlock() == Blocks.log2) {
               return Blocks.sandstone.getDefaultState();
            }

            if (iBlockState.getBlock() == Blocks.cobblestone) {
               return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.DEFAULT.getMetadata());
            }

            if (iBlockState.getBlock() == Blocks.planks) {
               return Blocks.sandstone.getStateFromMeta(BlockSandStone.EnumType.SMOOTH.getMetadata());
            }

            if (iBlockState.getBlock() == Blocks.oak_stairs) {
               return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, iBlockState.getValue(BlockStairs.FACING));
            }

            if (iBlockState.getBlock() == Blocks.stone_stairs) {
               return Blocks.sandstone_stairs.getDefaultState().withProperty(BlockStairs.FACING, iBlockState.getValue(BlockStairs.FACING));
            }

            if (iBlockState.getBlock() == Blocks.gravel) {
               return Blocks.sandstone.getDefaultState();
            }
         }

         return iBlockState;
      }

      @Override
      protected void setBlockState(World world, IBlockState iBlockState, int integer3, int integer4, int integer5, StructureBoundingBox structureBoundingBox) {
         IBlockState var7 = this.func_175847_a(iBlockState);
         super.setBlockState(world, var7, integer3, integer4, integer5, structureBoundingBox);
      }

      @Override
      protected void fillWithBlocks(
         World world,
         StructureBoundingBox structureBoundingBox,
         int integer3,
         int integer4,
         int integer5,
         int integer6,
         int integer7,
         int integer8,
         IBlockState iBlockState9,
         IBlockState iBlockState10,
         boolean boolean11
      ) {
         IBlockState var12 = this.func_175847_a(iBlockState9);
         IBlockState var13 = this.func_175847_a(iBlockState10);
         super.fillWithBlocks(world, structureBoundingBox, integer3, integer4, integer5, integer6, integer7, integer8, var12, var13, boolean11);
      }

      @Override
      protected void replaceAirAndLiquidDownwards(
         World world, IBlockState iBlockState, int integer3, int integer4, int integer5, StructureBoundingBox structureBoundingBox
      ) {
         IBlockState var7 = this.func_175847_a(iBlockState);
         super.replaceAirAndLiquidDownwards(world, var7, integer3, integer4, integer5, structureBoundingBox);
      }

      protected void func_175846_a(boolean boolean1) {
         this.isDesertVillage = boolean1;
      }
   }

   public static class Well extends StructureVillagePieces.Village {
      public Well() {
      }

      public Well(StructureVillagePieces.Start start, int integer2, Random random, int integer4, int integer5) {
         super(start, integer2);
         this.coordBaseMode = EnumFacing.Plane.HORIZONTAL.random(random);
         this.boundingBox = new StructureBoundingBox(integer4, 64, integer5, integer4 + 6 - 1, 78, integer5 + 6 - 1);
      }

      @Override
      public void buildComponent(StructureComponent structureComponent, List<StructureComponent> list, Random random) {
         StructureVillagePieces.Method414(
            (StructureVillagePieces.Start)structureComponent,
            list,
            random,
            this.boundingBox.minX - 1,
            this.boundingBox.maxY - 4,
            this.boundingBox.minZ + 1,
            EnumFacing.WEST,
            this.getComponentType()
         );
         StructureVillagePieces.Method414(
            (StructureVillagePieces.Start)structureComponent,
            list,
            random,
            this.boundingBox.maxX + 1,
            this.boundingBox.maxY - 4,
            this.boundingBox.minZ + 1,
            EnumFacing.EAST,
            this.getComponentType()
         );
         StructureVillagePieces.Method414(
            (StructureVillagePieces.Start)structureComponent,
            list,
            random,
            this.boundingBox.minX + 1,
            this.boundingBox.maxY - 4,
            this.boundingBox.minZ - 1,
            EnumFacing.NORTH,
            this.getComponentType()
         );
         StructureVillagePieces.Method414(
            (StructureVillagePieces.Start)structureComponent,
            list,
            random,
            this.boundingBox.minX + 1,
            this.boundingBox.maxY - 4,
            this.boundingBox.maxZ + 1,
            EnumFacing.SOUTH,
            this.getComponentType()
         );
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
         }

         this.fillWithBlocks(
            world, structureBoundingBox, 1, 0, 1, 4, 12, 4, Blocks.cobblestone.getDefaultState(), Blocks.flowing_water.getDefaultState(), false
         );
         this.setBlockState(world, Blocks.air.getDefaultState(), 2, 12, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 3, 12, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 2, 12, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 3, 12, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 13, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 14, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 13, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 14, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 13, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 1, 14, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 13, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.oak_fence.getDefaultState(), 4, 14, 4, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 1, 15, 1, 4, 15, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);

         for (int var7 = 0; var7 <= 5; var7++) {
            for (int var8 = 0; var8 <= 5; var8++) {
               if (var8 == 0 || var8 == 5 || var7 == 0 || var7 == 5) {
                  this.setBlockState(world, Blocks.gravel.getDefaultState(), var8, 11, var7, structureBoundingBox);
                  this.clearCurrentPositionBlocksUpwards(world, var8, 12, var7, structureBoundingBox);
               }
            }
         }

         return true;
      }
   }

   public static class WoodHut extends StructureVillagePieces.Village {
      private boolean isTallHouse;
      private int tablePosition;

      public WoodHut() {
      }

      public WoodHut(StructureVillagePieces.Start start, int integer, Random random, StructureBoundingBox structureBoundingBox, EnumFacing enumFacing) {
         super(start, integer);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
         this.isTallHouse = random.nextBoolean();
         this.tablePosition = random.nextInt(3);
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
         super.writeStructureToNBT(nBTTagCompound);
         nBTTagCompound.setInteger("T", this.tablePosition);
         nBTTagCompound.setBoolean("C", this.isTallHouse);
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
         super.readStructureFromNBT(nBTTagCompound);
         this.tablePosition = nBTTagCompound.getInteger("T");
         this.isTallHouse = nBTTagCompound.getBoolean("C");
      }

      public static StructureVillagePieces.WoodHut func_175853_a(
         StructureVillagePieces.Start start,
         List<StructureComponent> list,
         Random random,
         int integer4,
         int integer5,
         int integer6,
         EnumFacing enumFacing,
         int integer8
      ) {
         StructureBoundingBox var10 = StructureBoundingBox.getComponentToAddBoundingBox(integer4, integer5, integer6, 0, 0, 0, 4, 6, 5, enumFacing);
         return canVillageGoDeeper(var10) && StructureComponent.findIntersecting(list, var10) == null
            ? new StructureVillagePieces.WoodHut(start, integer8, random, var10, enumFacing)
            : null;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_143015_k < 0) {
            this.field_143015_k = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.field_143015_k < 0) {
               return true;
            }

            this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 6 - 1, 0);
         }

         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 3, 5, 4, Blocks.air.getDefaultState(), Blocks.air.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 3, 0, 4, Blocks.cobblestone.getDefaultState(), Blocks.cobblestone.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 1, 2, 0, 3, Blocks.dirt.getDefaultState(), Blocks.dirt.getDefaultState(), false);
         if (this.isTallHouse) {
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 1, 2, 4, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         } else {
            this.fillWithBlocks(world, structureBoundingBox, 1, 5, 1, 2, 5, 3, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         }

         this.setBlockState(world, Blocks.log.getDefaultState(), 1, 4, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 2, 4, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 1, 4, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 2, 4, 4, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 3, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 1, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 3, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 3, 3, 0, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 4, 0, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 4, 3, 3, 4, Blocks.log.getDefaultState(), Blocks.log.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 1, 3, 3, 3, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 4, 2, 3, 4, Blocks.planks.getDefaultState(), Blocks.planks.getDefaultState(), false);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
         this.setBlockState(world, Blocks.glass_pane.getDefaultState(), 3, 2, 2, structureBoundingBox);
         if (this.tablePosition > 0) {
            this.setBlockState(world, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, structureBoundingBox);
            this.setBlockState(world, Blocks.wooden_pressure_plate.getDefaultState(), this.tablePosition, 2, 3, structureBoundingBox);
         }

         this.setBlockState(world, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBox);
         this.setBlockState(world, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBox);
         this.placeDoorCurrentPosition(world, structureBoundingBox, random, 1, 1, 0, EnumFacing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
         if (this.getBlockStateFromPos(world, 1, 0, -1, structureBoundingBox).getBlock().getMaterial() == Material.air
            && this.getBlockStateFromPos(world, 1, -1, -1, structureBoundingBox).getBlock().getMaterial() != Material.air) {
            this.setBlockState(world, Blocks.stone_stairs.getStateFromMeta(this.getMetadataWithOffset(Blocks.stone_stairs, 3)), 1, 0, -1, structureBoundingBox);
         }

         for (int var7 = 0; var7 < 5; var7++) {
            for (int var8 = 0; var8 < 4; var8++) {
               this.clearCurrentPositionBlocksUpwards(world, var8, 6, var7, structureBoundingBox);
               this.replaceAirAndLiquidDownwards(world, Blocks.cobblestone.getDefaultState(), var8, -1, var7, structureBoundingBox);
            }
         }

         this.spawnVillagers(world, structureBoundingBox, 1, 1, 2, 1);
         return true;
      }
   }
}
