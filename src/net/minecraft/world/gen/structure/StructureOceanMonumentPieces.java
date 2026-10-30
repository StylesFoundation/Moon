package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class StructureOceanMonumentPieces {
   public static void registerOceanMonumentPieces() {
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.MonumentBuilding.class, "OMB");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.MonumentCoreRoom.class, "OMCR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleXRoom.class, "OMDXR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleXYRoom.class, "OMDXYR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleYRoom.class, "OMDYR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleYZRoom.class, "OMDYZR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.DoubleZRoom.class, "OMDZR");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.EntryRoom.class, "OMEntry");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.Penthouse.class, "OMPenthouse");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.SimpleRoom.class, "OMSimple");
      MapGenStructureIO.registerStructureComponent(StructureOceanMonumentPieces.SimpleTopRoom.class, "OMSimpleT");
   }

   public static class DoubleXRoom extends StructureOceanMonumentPieces.Piece {
      public DoubleXRoom() {
      }

      public DoubleXRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         super(1, enumFacing, roomDefinition, 2, 1, 1);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         StructureOceanMonumentPieces.RoomDefinition var7 = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var8 = this.field_175830_k;
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(world, structureBoundingBox, 8, 0, var7.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(world, structureBoundingBox, 0, 0, var8.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (var8.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 4, 1, 7, 4, 6, field_175828_a);
         }

         if (var7.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 8, 4, 1, 14, 4, 6, field_175828_a);
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 15, 3, 0, 15, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 15, 3, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 7, 14, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 7, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 15, 2, 0, 15, 2, 7, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 15, 2, 0, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 14, 2, 7, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 15, 1, 0, 15, 1, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 15, 1, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 10, 1, 4, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 2, 0, 9, 2, 3, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 3, 0, 10, 3, 4, field_175826_b, field_175826_b, false);
         this.setBlockState(world, field_175825_e, 6, 2, 3, structureBoundingBox);
         this.setBlockState(world, field_175825_e, 9, 2, 3, structureBoundingBox);
         if (var8.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
         }

         if (var8.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, false);
         }

         if (var8.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, false);
         }

         if (var7.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 11, 1, 0, 12, 2, 0, false);
         }

         if (var7.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 11, 1, 7, 12, 2, 7, false);
         }

         if (var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 15, 1, 3, 15, 2, 4, false);
         }

         return true;
      }
   }

   public static class DoubleXYRoom extends StructureOceanMonumentPieces.Piece {
      public DoubleXYRoom() {
      }

      public DoubleXYRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         super(1, enumFacing, roomDefinition, 2, 2, 1);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         StructureOceanMonumentPieces.RoomDefinition var7 = this.field_175830_k.field_175965_b[EnumFacing.EAST.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var8 = this.field_175830_k;
         StructureOceanMonumentPieces.RoomDefinition var9 = var8.field_175965_b[EnumFacing.UP.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var10 = var7.field_175965_b[EnumFacing.UP.getIndex()];
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(world, structureBoundingBox, 8, 0, var7.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(world, structureBoundingBox, 0, 0, var8.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (var9.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 8, 1, 7, 8, 6, field_175828_a);
         }

         if (var10.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 8, 8, 1, 14, 8, 6, field_175828_a);
         }

         for (int var11 = 1; var11 <= 7; var11++) {
            IBlockState var12 = field_175826_b;
            if (var11 == 2 || var11 == 6) {
               var12 = field_175828_a;
            }

            this.fillWithBlocks(world, structureBoundingBox, 0, var11, 0, 0, var11, 7, var12, var12, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, var11, 0, 15, var11, 7, var12, var12, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, var11, 0, 15, var11, 0, var12, var12, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, var11, 7, 14, var11, 7, var12, var12, false);
         }

         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 3, 2, 7, 4, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 2, 4, 7, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 5, 4, 7, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 13, 1, 3, 13, 7, 4, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 11, 1, 2, 12, 7, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 11, 1, 5, 12, 7, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 3, 5, 3, 4, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 1, 3, 10, 3, 4, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 7, 2, 10, 7, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 5, 2, 5, 7, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 5, 2, 10, 7, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 5, 5, 5, 7, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 5, 5, 10, 7, 5, field_175826_b, field_175826_b, false);
         this.setBlockState(world, field_175826_b, 6, 6, 2, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 9, 6, 2, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 6, 6, 5, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 9, 6, 5, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 5, 4, 3, 6, 4, 4, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 4, 3, 10, 4, 4, field_175826_b, field_175826_b, false);
         this.setBlockState(world, field_175825_e, 5, 4, 2, structureBoundingBox);
         this.setBlockState(world, field_175825_e, 5, 4, 5, structureBoundingBox);
         this.setBlockState(world, field_175825_e, 10, 4, 2, structureBoundingBox);
         this.setBlockState(world, field_175825_e, 10, 4, 5, structureBoundingBox);
         if (var8.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
         }

         if (var8.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, false);
         }

         if (var8.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, false);
         }

         if (var7.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 11, 1, 0, 12, 2, 0, false);
         }

         if (var7.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 11, 1, 7, 12, 2, 7, false);
         }

         if (var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 15, 1, 3, 15, 2, 4, false);
         }

         if (var9.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 5, 0, 4, 6, 0, false);
         }

         if (var9.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 5, 7, 4, 6, 7, false);
         }

         if (var9.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 5, 3, 0, 6, 4, false);
         }

         if (var10.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 11, 5, 0, 12, 6, 0, false);
         }

         if (var10.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 11, 5, 7, 12, 6, 7, false);
         }

         if (var10.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 15, 5, 3, 15, 6, 4, false);
         }

         return true;
      }
   }

   public static class DoubleYRoom extends StructureOceanMonumentPieces.Piece {
      public DoubleYRoom() {
      }

      public DoubleYRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         super(1, enumFacing, roomDefinition, 1, 2, 1);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(world, structureBoundingBox, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         StructureOceanMonumentPieces.RoomDefinition var7 = this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()];
         if (var7.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 8, 1, 6, 8, 6, field_175828_a);
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 4, 0, 0, 4, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 4, 0, 7, 4, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 4, 0, 6, 4, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 4, 7, 6, 4, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 4, 1, 2, 4, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 4, 2, 1, 4, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 4, 1, 5, 4, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 4, 2, 6, 4, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 4, 5, 2, 4, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 4, 5, 1, 4, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 4, 5, 5, 4, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 4, 5, 6, 4, 5, field_175826_b, field_175826_b, false);
         StructureOceanMonumentPieces.RoomDefinition var8 = this.field_175830_k;

         for (byte var9 = 1; var9 <= 5; var9 += 4) {
            if (var8.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 2, var9, 0, 2, var9 + 2, 0, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 5, var9, 0, 5, var9 + 2, 0, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 3, var9 + 2, 0, 4, var9 + 2, 0, field_175826_b, field_175826_b, false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, 0, var9, 0, 7, var9 + 2, 0, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, var9 + 1, 0, 7, var9 + 1, 0, field_175828_a, field_175828_a, false);
            }

            byte var10 = 7;
            if (var8.field_175966_c[EnumFacing.NORTH.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 2, var9, var10, 2, var9 + 2, var10, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 5, var9, var10, 5, var9 + 2, var10, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 3, var9 + 2, var10, 4, var9 + 2, var10, field_175826_b, field_175826_b, false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, 0, var9, var10, 7, var9 + 2, var10, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, var9 + 1, var10, 7, var9 + 1, var10, field_175828_a, field_175828_a, false);
            }

            if (var8.field_175966_c[EnumFacing.WEST.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 0, var9, 2, 0, var9 + 2, 2, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, var9, 5, 0, var9 + 2, 5, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, var9 + 2, 3, 0, var9 + 2, 4, field_175826_b, field_175826_b, false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, 0, var9, 0, 0, var9 + 2, 7, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, var9 + 1, 0, 0, var9 + 1, 7, field_175828_a, field_175828_a, false);
            }

            byte var11 = 7;
            if (var8.field_175966_c[EnumFacing.EAST.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, var11, var9, 2, var11, var9 + 2, 2, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, var11, var9, 5, var11, var9 + 2, 5, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, var11, var9 + 2, 3, var11, var9 + 2, 4, field_175826_b, field_175826_b, false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, var11, var9, 0, var11, var9 + 2, 7, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, var11, var9 + 1, 0, var11, var9 + 1, 7, field_175828_a, field_175828_a, false);
            }

            var8 = var7;
         }

         return true;
      }
   }

   public static class DoubleYZRoom extends StructureOceanMonumentPieces.Piece {
      public DoubleYZRoom() {
      }

      public DoubleYZRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         super(1, enumFacing, roomDefinition, 1, 2, 2);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         StructureOceanMonumentPieces.RoomDefinition var7 = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var8 = this.field_175830_k;
         StructureOceanMonumentPieces.RoomDefinition var9 = var7.field_175965_b[EnumFacing.UP.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var10 = var8.field_175965_b[EnumFacing.UP.getIndex()];
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(world, structureBoundingBox, 0, 8, var7.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(world, structureBoundingBox, 0, 0, var8.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (var10.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 8, 1, 6, 8, 7, field_175828_a);
         }

         if (var9.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 8, 8, 6, 8, 14, field_175828_a);
         }

         for (int var11 = 1; var11 <= 7; var11++) {
            IBlockState var12 = field_175826_b;
            if (var11 == 2 || var11 == 6) {
               var12 = field_175828_a;
            }

            this.fillWithBlocks(world, structureBoundingBox, 0, var11, 0, 0, var11, 15, var12, var12, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, var11, 0, 7, var11, 15, var12, var12, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, var11, 0, 6, var11, 0, var12, var12, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, var11, 15, 6, var11, 15, var12, var12, false);
         }

         for (int var13 = 1; var13 <= 7; var13++) {
            IBlockState var14 = field_175827_c;
            if (var13 == 2 || var13 == 6) {
               var14 = field_175825_e;
            }

            this.fillWithBlocks(world, structureBoundingBox, 3, var13, 7, 4, var13, 8, var14, var14, false);
         }

         if (var8.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
         }

         if (var8.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 7, 1, 3, 7, 2, 4, false);
         }

         if (var8.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, false);
         }

         if (var7.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 15, 4, 2, 15, false);
         }

         if (var7.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 1, 11, 0, 2, 12, false);
         }

         if (var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 7, 1, 11, 7, 2, 12, false);
         }

         if (var10.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 5, 0, 4, 6, 0, false);
         }

         if (var10.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 7, 5, 3, 7, 6, 4, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 4, 2, 6, 4, 5, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 2, 6, 3, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 5, 6, 3, 5, field_175826_b, field_175826_b, false);
         }

         if (var10.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 5, 3, 0, 6, 4, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 2, 2, 4, 5, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 2, 1, 3, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 5, 1, 3, 5, field_175826_b, field_175826_b, false);
         }

         if (var9.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 5, 15, 4, 6, 15, false);
         }

         if (var9.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 5, 11, 0, 6, 12, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 4, 10, 2, 4, 13, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 10, 1, 3, 10, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 13, 1, 3, 13, field_175826_b, field_175826_b, false);
         }

         if (var9.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 7, 5, 11, 7, 6, 12, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 4, 10, 6, 4, 13, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 10, 6, 3, 10, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 13, 6, 3, 13, field_175826_b, field_175826_b, false);
         }

         return true;
      }
   }

   public static class DoubleZRoom extends StructureOceanMonumentPieces.Piece {
      public DoubleZRoom() {
      }

      public DoubleZRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         super(1, enumFacing, roomDefinition, 1, 1, 2);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         StructureOceanMonumentPieces.RoomDefinition var7 = this.field_175830_k.field_175965_b[EnumFacing.NORTH.getIndex()];
         StructureOceanMonumentPieces.RoomDefinition var8 = this.field_175830_k;
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(world, structureBoundingBox, 0, 8, var7.field_175966_c[EnumFacing.DOWN.getIndex()]);
            this.func_175821_a(world, structureBoundingBox, 0, 0, var8.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (var8.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 4, 1, 6, 4, 7, field_175828_a);
         }

         if (var7.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 4, 8, 6, 4, 14, field_175828_a);
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 0, 3, 15, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 3, 0, 7, 3, 15, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 7, 3, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 15, 6, 3, 15, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 15, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 15, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 7, 2, 0, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 15, 6, 2, 15, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 15, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 1, 15, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 7, 1, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 15, 6, 1, 15, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 1, 1, 1, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 1, 1, 6, 1, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 1, 1, 3, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 3, 1, 6, 3, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 13, 1, 1, 14, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 1, 13, 6, 1, 14, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 13, 1, 3, 14, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 3, 13, 6, 3, 14, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 6, 2, 3, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 6, 5, 3, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 9, 2, 3, 9, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 9, 5, 3, 9, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 2, 6, 4, 2, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 2, 9, 4, 2, 9, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 2, 7, 2, 2, 8, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 2, 7, 5, 2, 8, field_175826_b, field_175826_b, false);
         this.setBlockState(world, field_175825_e, 2, 2, 5, structureBoundingBox);
         this.setBlockState(world, field_175825_e, 5, 2, 5, structureBoundingBox);
         this.setBlockState(world, field_175825_e, 2, 2, 10, structureBoundingBox);
         this.setBlockState(world, field_175825_e, 5, 2, 10, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 2, 3, 5, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 5, 3, 5, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 2, 3, 10, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 5, 3, 10, structureBoundingBox);
         if (var8.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
         }

         if (var8.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 7, 1, 3, 7, 2, 4, false);
         }

         if (var8.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, false);
         }

         if (var7.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 15, 4, 2, 15, false);
         }

         if (var7.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 1, 11, 0, 2, 12, false);
         }

         if (var7.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 7, 1, 11, 7, 2, 12, false);
         }

         return true;
      }
   }

   public static class EntryRoom extends StructureOceanMonumentPieces.Piece {
      public EntryRoom() {
      }

      public EntryRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         super(1, enumFacing, roomDefinition, 1, 1, 1);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 2, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 1, 2, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 2, 0, 7, 2, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 2, 3, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
         if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, false);
         }

         if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 1, 2, 4, false);
         }

         if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 6, 1, 3, 7, 2, 4, false);
         }

         return true;
      }
   }

   static class FitSimpleRoomHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private FitSimpleRoomHelper() {
      }

      @Override
      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         return true;
      }

      @Override
      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         roomDefinition.field_175963_d = true;
         return new StructureOceanMonumentPieces.SimpleRoom(enumFacing, roomDefinition, random);
      }
   }

   static class FitSimpleRoomTopHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private FitSimpleRoomTopHelper() {
      }

      @Override
      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         return !roomDefinition.field_175966_c[EnumFacing.WEST.getIndex()]
            && !roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()]
            && !roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()]
            && !roomDefinition.field_175966_c[EnumFacing.SOUTH.getIndex()]
            && !roomDefinition.field_175966_c[EnumFacing.UP.getIndex()];
      }

      @Override
      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         roomDefinition.field_175963_d = true;
         return new StructureOceanMonumentPieces.SimpleTopRoom(enumFacing, roomDefinition, random);
      }
   }

   public static class MonumentBuilding extends StructureOceanMonumentPieces.Piece {
      private StructureOceanMonumentPieces.RoomDefinition field_175845_o;
      private StructureOceanMonumentPieces.RoomDefinition field_175844_p;
      private final List<StructureOceanMonumentPieces.Piece> field_175843_q = Lists.newArrayList();

      public MonumentBuilding() {
      }

      public MonumentBuilding(Random random, int integer2, int integer3, EnumFacing enumFacing) {
         super(0);
         this.coordBaseMode = enumFacing;
         this.boundingBox = new StructureBoundingBox(integer2, 39, integer3, integer2 + 58 - 1, 61, integer3 + 58 - 1);
         List<StructureOceanMonumentPieces.RoomDefinition> var8 = this.func_175836_a(random);
         this.field_175845_o.field_175963_d = true;
         this.field_175843_q.add(new StructureOceanMonumentPieces.EntryRoom(this.coordBaseMode, this.field_175845_o));
         this.field_175843_q.add(new StructureOceanMonumentPieces.MonumentCoreRoom(this.coordBaseMode, this.field_175844_p, random));
         List<StructureOceanMonumentPieces.MonumentRoomFitHelper> var9 = Lists.newArrayList();
         var9.add(new StructureOceanMonumentPieces.XYDoubleRoomFitHelper());
         var9.add(new StructureOceanMonumentPieces.YZDoubleRoomFitHelper());
         var9.add(new StructureOceanMonumentPieces.ZDoubleRoomFitHelper());
         var9.add(new StructureOceanMonumentPieces.XDoubleRoomFitHelper());
         var9.add(new StructureOceanMonumentPieces.YDoubleRoomFitHelper());
         var9.add(new StructureOceanMonumentPieces.FitSimpleRoomTopHelper());
         var9.add(new StructureOceanMonumentPieces.FitSimpleRoomHelper());

         for (StructureOceanMonumentPieces.RoomDefinition var11 : var8) {
            if (!var11.field_175963_d && !var11.func_175961_b()) {
               for (StructureOceanMonumentPieces.MonumentRoomFitHelper var13 : var9) {
                  if (var13.func_175969_a(var11)) {
                     this.field_175843_q.add(var13.func_175968_a(this.coordBaseMode, var11, random));
                     break;
                  }
               }
            }
         }

         int var17 = this.boundingBox.minY;
         int var18 = this.getXWithOffset(9, 22);
         int var19 = this.getZWithOffset(9, 22);

         for (StructureOceanMonumentPieces.Piece var14 : this.field_175843_q) {
            var14.getBoundingBox().offset(var18, var17, var19);
         }

         StructureBoundingBox var21 = StructureBoundingBox.func_175899_a(
            this.getXWithOffset(1, 1),
            this.getYWithOffset(1),
            this.getZWithOffset(1, 1),
            this.getXWithOffset(23, 21),
            this.getYWithOffset(8),
            this.getZWithOffset(23, 21)
         );
         StructureBoundingBox var22 = StructureBoundingBox.func_175899_a(
            this.getXWithOffset(34, 1),
            this.getYWithOffset(1),
            this.getZWithOffset(34, 1),
            this.getXWithOffset(56, 21),
            this.getYWithOffset(8),
            this.getZWithOffset(56, 21)
         );
         StructureBoundingBox var15 = StructureBoundingBox.func_175899_a(
            this.getXWithOffset(22, 22),
            this.getYWithOffset(13),
            this.getZWithOffset(22, 22),
            this.getXWithOffset(35, 35),
            this.getYWithOffset(17),
            this.getZWithOffset(35, 35)
         );
         int var16 = random.nextInt();
         this.field_175843_q.add(new StructureOceanMonumentPieces.WingRoom(this.coordBaseMode, var21, var16++));
         this.field_175843_q.add(new StructureOceanMonumentPieces.WingRoom(this.coordBaseMode, var22, var16++));
         this.field_175843_q.add(new StructureOceanMonumentPieces.Penthouse(this.coordBaseMode, var15));
      }

      private List<StructureOceanMonumentPieces.RoomDefinition> func_175836_a(Random random) {
         StructureOceanMonumentPieces.RoomDefinition[] var5 = new StructureOceanMonumentPieces.RoomDefinition[75];

         for (int var6 = 0; var6 < 5; var6++) {
            for (int var7 = 0; var7 < 4; var7++) {
               int var9 = func_175820_a(var6, 0, var7);
               var5[var9] = new StructureOceanMonumentPieces.RoomDefinition(var9);
            }
         }

         for (int var18 = 0; var18 < 5; var18++) {
            for (int var22 = 0; var22 < 4; var22++) {
               int var27 = func_175820_a(var18, 1, var22);
               var5[var27] = new StructureOceanMonumentPieces.RoomDefinition(var27);
            }
         }

         for (int var19 = 1; var19 < 4; var19++) {
            for (int var23 = 0; var23 < 2; var23++) {
               int var28 = func_175820_a(var19, 2, var23);
               var5[var28] = new StructureOceanMonumentPieces.RoomDefinition(var28);
            }
         }

         this.field_175845_o = var5[field_175823_g];

         for (int var20 = 0; var20 < 5; var20++) {
            for (int var24 = 0; var24 < 5; var24++) {
               for (int var8 = 0; var8 < 3; var8++) {
                  int var29 = func_175820_a(var20, var8, var24);
                  if (var5[var29] != null) {
                     for (EnumFacing var13 : EnumFacing.values()) {
                        int var14 = var20 + var13.getFrontOffsetX();
                        int var15 = var8 + var13.getFrontOffsetY();
                        int var16 = var24 + var13.getFrontOffsetZ();
                        if (var14 >= 0 && var14 < 5 && var16 >= 0 && var16 < 5 && var15 >= 0 && var15 < 3) {
                           int var17 = func_175820_a(var14, var15, var16);
                           if (var5[var17] != null) {
                              if (var16 != var24) {
                                 var5[var29].func_175957_a(var13.getOpposite(), var5[var17]);
                              } else {
                                 var5[var29].func_175957_a(var13, var5[var17]);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         StructureOceanMonumentPieces.RoomDefinition var21;
         var5[field_175831_h].func_175957_a(EnumFacing.UP, var21 = new StructureOceanMonumentPieces.RoomDefinition(1003));
         StructureOceanMonumentPieces.RoomDefinition var25;
         var5[field_175832_i].func_175957_a(EnumFacing.SOUTH, var25 = new StructureOceanMonumentPieces.RoomDefinition(1001));
         StructureOceanMonumentPieces.RoomDefinition var26;
         var5[field_175829_j].func_175957_a(EnumFacing.SOUTH, var26 = new StructureOceanMonumentPieces.RoomDefinition(1002));
         var21.field_175963_d = true;
         var25.field_175963_d = true;
         var26.field_175963_d = true;
         this.field_175845_o.field_175964_e = true;
         this.field_175844_p = var5[func_175820_a(random.nextInt(4), 0, 2)];
         this.field_175844_p.field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         this.field_175844_p.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         List<StructureOceanMonumentPieces.RoomDefinition> var30 = Lists.newArrayList();

         for (StructureOceanMonumentPieces.RoomDefinition var37 : var5) {
            if (var37 != null) {
               var37.func_175958_a();
               var30.add(var37);
            }
         }

         var21.func_175958_a();
         Collections.shuffle(var30, random);
         int var32 = 1;

         for (StructureOceanMonumentPieces.RoomDefinition var36 : var30) {
            int var38 = 0;
            int var39 = 0;

            while (var38 < 2 && var39 < 5) {
               var39++;
               int var40 = random.nextInt(6);
               if (var36.field_175966_c[var40]) {
                  int var41 = EnumFacing.getFront(var40).getOpposite().getIndex();
                  var36.field_175966_c[var40] = false;
                  var36.field_175965_b[var40].field_175966_c[var41] = false;
                  if (var36.func_175959_a(var32++) && var36.field_175965_b[var40].func_175959_a(var32++)) {
                     var38++;
                  } else {
                     var36.field_175966_c[var40] = true;
                     var36.field_175965_b[var40].field_175966_c[var41] = true;
                  }
               }
            }
         }

         var30.add(var21);
         var30.add(var25);
         var30.add(var26);
         return var30;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         int var7 = Math.max(world.getSeaLevel(), 64) - this.boundingBox.minY;
         this.func_181655_a(world, structureBoundingBox, 0, 0, 0, 58, var7, 58, false);
         this.func_175840_a(false, 0, world, random, structureBoundingBox);
         this.func_175840_a(true, 33, world, random, structureBoundingBox);
         this.func_175839_b(world, random, structureBoundingBox);
         this.func_175837_c(world, random, structureBoundingBox);
         this.func_175841_d(world, random, structureBoundingBox);
         this.func_175835_e(world, random, structureBoundingBox);
         this.func_175842_f(world, random, structureBoundingBox);
         this.func_175838_g(world, random, structureBoundingBox);

         for (int var8 = 0; var8 < 7; var8++) {
            int var9 = 0;

            while (var9 < 7) {
               if (var9 == 0 && var8 == 3) {
                  var9 = 6;
               }

               int var10 = var8 * 9;
               int var11 = var9 * 9;

               for (int var12 = 0; var12 < 4; var12++) {
                  for (int var13 = 0; var13 < 4; var13++) {
                     this.setBlockState(world, field_175826_b, var10 + var12, 0, var11 + var13, structureBoundingBox);
                     this.replaceAirAndLiquidDownwards(world, field_175826_b, var10 + var12, -1, var11 + var13, structureBoundingBox);
                  }
               }

               if (var8 != 0 && var8 != 6) {
                  var9 += 6;
               } else {
                  var9++;
               }
            }
         }

         for (int var14 = 0; var14 < 5; var14++) {
            this.func_181655_a(world, structureBoundingBox, -1 - var14, var14 * 2, -1 - var14, -1 - var14, 23, 58 + var14, false);
            this.func_181655_a(world, structureBoundingBox, 58 + var14, var14 * 2, -1 - var14, 58 + var14, 23, 58 + var14, false);
            this.func_181655_a(world, structureBoundingBox, -var14, var14 * 2, -1 - var14, 57 + var14, 23, -1 - var14, false);
            this.func_181655_a(world, structureBoundingBox, -var14, var14 * 2, 58 + var14, 57 + var14, 23, 58 + var14, false);
         }

         for (StructureOceanMonumentPieces.Piece var16 : this.field_175843_q) {
            if (var16.getBoundingBox().intersectsWith(structureBoundingBox)) {
               var16.addComponentParts(world, random, structureBoundingBox);
            }
         }

         return true;
      }

      private void func_175840_a(boolean boolean1, int integer, World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.func_175818_a(structureBoundingBox, integer, 0, integer + 23, 20)) {
            this.fillWithBlocks(world, structureBoundingBox, integer, 0, 0, integer + 24, 0, 20, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, integer, 1, 0, integer + 24, 10, 20, false);

            for (int var10 = 0; var10 < 4; var10++) {
               this.fillWithBlocks(
                  world, structureBoundingBox, integer + var10, var10 + 1, var10, integer + var10, var10 + 1, 20, field_175826_b, field_175826_b, false
               );
               this.fillWithBlocks(
                  world,
                  structureBoundingBox,
                  integer + var10 + 7,
                  var10 + 5,
                  var10 + 7,
                  integer + var10 + 7,
                  var10 + 5,
                  20,
                  field_175826_b,
                  field_175826_b,
                  false
               );
               this.fillWithBlocks(
                  world,
                  structureBoundingBox,
                  integer + 17 - var10,
                  var10 + 5,
                  var10 + 7,
                  integer + 17 - var10,
                  var10 + 5,
                  20,
                  field_175826_b,
                  field_175826_b,
                  false
               );
               this.fillWithBlocks(
                  world,
                  structureBoundingBox,
                  integer + 24 - var10,
                  var10 + 1,
                  var10,
                  integer + 24 - var10,
                  var10 + 1,
                  20,
                  field_175826_b,
                  field_175826_b,
                  false
               );
               this.fillWithBlocks(
                  world,
                  structureBoundingBox,
                  integer + var10 + 1,
                  var10 + 1,
                  var10,
                  integer + 23 - var10,
                  var10 + 1,
                  var10,
                  field_175826_b,
                  field_175826_b,
                  false
               );
               this.fillWithBlocks(
                  world,
                  structureBoundingBox,
                  integer + var10 + 8,
                  var10 + 5,
                  var10 + 7,
                  integer + 16 - var10,
                  var10 + 5,
                  var10 + 7,
                  field_175826_b,
                  field_175826_b,
                  false
               );
            }

            this.fillWithBlocks(world, structureBoundingBox, integer + 4, 4, 4, integer + 6, 4, 20, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, integer + 7, 4, 4, integer + 17, 4, 6, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, integer + 18, 4, 4, integer + 20, 4, 20, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, integer + 11, 8, 11, integer + 13, 8, 20, field_175828_a, field_175828_a, false);
            this.setBlockState(world, field_175824_d, integer + 12, 9, 12, structureBoundingBox);
            this.setBlockState(world, field_175824_d, integer + 12, 9, 15, structureBoundingBox);
            this.setBlockState(world, field_175824_d, integer + 12, 9, 18, structureBoundingBox);
            int var14 = boolean1 ? integer + 19 : integer + 5;
            int var11 = boolean1 ? integer + 5 : integer + 19;

            for (byte var12 = 20; var12 >= 5; var12 -= 3) {
               this.setBlockState(world, field_175824_d, var14, 5, var12, structureBoundingBox);
            }

            for (byte var15 = 19; var15 >= 7; var15 -= 3) {
               this.setBlockState(world, field_175824_d, var11, 5, var15, structureBoundingBox);
            }

            for (int var16 = 0; var16 < 4; var16++) {
               int var13 = boolean1 ? integer + (24 - (17 - var16 * 3)) : integer + 17 - var16 * 3;
               this.setBlockState(world, field_175824_d, var13, 5, 5, structureBoundingBox);
            }

            this.setBlockState(world, field_175824_d, var11, 5, 5, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, integer + 11, 1, 12, integer + 13, 7, 12, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, integer + 12, 1, 11, integer + 12, 7, 13, field_175828_a, field_175828_a, false);
         }
      }

      private void func_175839_b(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.func_175818_a(structureBoundingBox, 22, 5, 35, 17)) {
            this.func_181655_a(world, structureBoundingBox, 25, 0, 0, 32, 8, 20, false);

            for (int var7 = 0; var7 < 4; var7++) {
               this.fillWithBlocks(world, structureBoundingBox, 24, 2, 5 + var7 * 4, 24, 4, 5 + var7 * 4, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 22, 4, 5 + var7 * 4, 23, 4, 5 + var7 * 4, field_175826_b, field_175826_b, false);
               this.setBlockState(world, field_175826_b, 25, 5, 5 + var7 * 4, structureBoundingBox);
               this.setBlockState(world, field_175826_b, 26, 6, 5 + var7 * 4, structureBoundingBox);
               this.setBlockState(world, field_175825_e, 26, 5, 5 + var7 * 4, structureBoundingBox);
               this.fillWithBlocks(world, structureBoundingBox, 33, 2, 5 + var7 * 4, 33, 4, 5 + var7 * 4, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 34, 4, 5 + var7 * 4, 35, 4, 5 + var7 * 4, field_175826_b, field_175826_b, false);
               this.setBlockState(world, field_175826_b, 32, 5, 5 + var7 * 4, structureBoundingBox);
               this.setBlockState(world, field_175826_b, 31, 6, 5 + var7 * 4, structureBoundingBox);
               this.setBlockState(world, field_175825_e, 31, 5, 5 + var7 * 4, structureBoundingBox);
               this.fillWithBlocks(world, structureBoundingBox, 27, 6, 5 + var7 * 4, 30, 6, 5 + var7 * 4, field_175828_a, field_175828_a, false);
            }
         }
      }

      private void func_175837_c(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.func_175818_a(structureBoundingBox, 15, 20, 42, 21)) {
            this.fillWithBlocks(world, structureBoundingBox, 15, 0, 21, 42, 0, 21, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 26, 1, 21, 31, 3, 21, false);
            this.fillWithBlocks(world, structureBoundingBox, 21, 12, 21, 36, 12, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 17, 11, 21, 40, 11, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 16, 10, 21, 41, 10, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 7, 21, 42, 9, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 16, 6, 21, 41, 6, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 17, 5, 21, 40, 5, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 21, 4, 21, 36, 4, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 22, 3, 21, 26, 3, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 31, 3, 21, 35, 3, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 23, 2, 21, 25, 2, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 32, 2, 21, 34, 2, 21, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 28, 4, 20, 29, 4, 21, field_175826_b, field_175826_b, false);
            this.setBlockState(world, field_175826_b, 27, 3, 21, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 30, 3, 21, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 26, 2, 21, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 31, 2, 21, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 25, 1, 21, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 32, 1, 21, structureBoundingBox);

            for (int var7 = 0; var7 < 7; var7++) {
               this.setBlockState(world, field_175827_c, 28 - var7, 6 + var7, 21, structureBoundingBox);
               this.setBlockState(world, field_175827_c, 29 + var7, 6 + var7, 21, structureBoundingBox);
            }

            for (int var8 = 0; var8 < 4; var8++) {
               this.setBlockState(world, field_175827_c, 28 - var8, 9 + var8, 21, structureBoundingBox);
               this.setBlockState(world, field_175827_c, 29 + var8, 9 + var8, 21, structureBoundingBox);
            }

            this.setBlockState(world, field_175827_c, 28, 12, 21, structureBoundingBox);
            this.setBlockState(world, field_175827_c, 29, 12, 21, structureBoundingBox);

            for (int var9 = 0; var9 < 3; var9++) {
               this.setBlockState(world, field_175827_c, 22 - var9 * 2, 8, 21, structureBoundingBox);
               this.setBlockState(world, field_175827_c, 22 - var9 * 2, 9, 21, structureBoundingBox);
               this.setBlockState(world, field_175827_c, 35 + var9 * 2, 8, 21, structureBoundingBox);
               this.setBlockState(world, field_175827_c, 35 + var9 * 2, 9, 21, structureBoundingBox);
            }

            this.func_181655_a(world, structureBoundingBox, 15, 13, 21, 42, 15, 21, false);
            this.func_181655_a(world, structureBoundingBox, 15, 1, 21, 15, 6, 21, false);
            this.func_181655_a(world, structureBoundingBox, 16, 1, 21, 16, 5, 21, false);
            this.func_181655_a(world, structureBoundingBox, 17, 1, 21, 20, 4, 21, false);
            this.func_181655_a(world, structureBoundingBox, 21, 1, 21, 21, 3, 21, false);
            this.func_181655_a(world, structureBoundingBox, 22, 1, 21, 22, 2, 21, false);
            this.func_181655_a(world, structureBoundingBox, 23, 1, 21, 24, 1, 21, false);
            this.func_181655_a(world, structureBoundingBox, 42, 1, 21, 42, 6, 21, false);
            this.func_181655_a(world, structureBoundingBox, 41, 1, 21, 41, 5, 21, false);
            this.func_181655_a(world, structureBoundingBox, 37, 1, 21, 40, 4, 21, false);
            this.func_181655_a(world, structureBoundingBox, 36, 1, 21, 36, 3, 21, false);
            this.func_181655_a(world, structureBoundingBox, 33, 1, 21, 34, 1, 21, false);
            this.func_181655_a(world, structureBoundingBox, 35, 1, 21, 35, 2, 21, false);
         }
      }

      private void func_175841_d(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.func_175818_a(structureBoundingBox, 21, 21, 36, 36)) {
            this.fillWithBlocks(world, structureBoundingBox, 21, 0, 22, 36, 0, 36, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 21, 1, 22, 36, 23, 36, false);

            for (int var7 = 0; var7 < 4; var7++) {
               this.fillWithBlocks(
                  world, structureBoundingBox, 21 + var7, 13 + var7, 21 + var7, 36 - var7, 13 + var7, 21 + var7, field_175826_b, field_175826_b, false
               );
               this.fillWithBlocks(
                  world, structureBoundingBox, 21 + var7, 13 + var7, 36 - var7, 36 - var7, 13 + var7, 36 - var7, field_175826_b, field_175826_b, false
               );
               this.fillWithBlocks(
                  world, structureBoundingBox, 21 + var7, 13 + var7, 22 + var7, 21 + var7, 13 + var7, 35 - var7, field_175826_b, field_175826_b, false
               );
               this.fillWithBlocks(
                  world, structureBoundingBox, 36 - var7, 13 + var7, 22 + var7, 36 - var7, 13 + var7, 35 - var7, field_175826_b, field_175826_b, false
               );
            }

            this.fillWithBlocks(world, structureBoundingBox, 25, 16, 25, 32, 16, 32, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 25, 17, 25, 25, 19, 25, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 32, 17, 25, 32, 19, 25, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 25, 17, 32, 25, 19, 32, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 32, 17, 32, 32, 19, 32, field_175826_b, field_175826_b, false);
            this.setBlockState(world, field_175826_b, 26, 20, 26, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 27, 21, 27, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 27, 20, 27, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 26, 20, 31, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 27, 21, 30, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 27, 20, 30, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 31, 20, 31, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 30, 21, 30, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 30, 20, 30, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 31, 20, 26, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 30, 21, 27, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 30, 20, 27, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 28, 21, 27, 29, 21, 27, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 27, 21, 28, 27, 21, 29, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 28, 21, 30, 29, 21, 30, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 30, 21, 28, 30, 21, 29, field_175828_a, field_175828_a, false);
         }
      }

      private void func_175835_e(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.func_175818_a(structureBoundingBox, 0, 21, 6, 58)) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 0, 21, 6, 0, 57, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 0, 1, 21, 6, 7, 57, false);
            this.fillWithBlocks(world, structureBoundingBox, 4, 4, 21, 6, 4, 53, field_175828_a, field_175828_a, false);

            for (int var7 = 0; var7 < 4; var7++) {
               this.fillWithBlocks(world, structureBoundingBox, var7, var7 + 1, 21, var7, var7 + 1, 57 - var7, field_175826_b, field_175826_b, false);
            }

            for (byte var8 = 23; var8 < 53; var8 += 3) {
               this.setBlockState(world, field_175824_d, 5, 5, var8, structureBoundingBox);
            }

            this.setBlockState(world, field_175824_d, 5, 5, 52, structureBoundingBox);

            for (int var9 = 0; var9 < 4; var9++) {
               this.fillWithBlocks(world, structureBoundingBox, var9, var9 + 1, 21, var9, var9 + 1, 57 - var9, field_175826_b, field_175826_b, false);
            }

            this.fillWithBlocks(world, structureBoundingBox, 4, 1, 52, 6, 3, 52, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 51, 5, 3, 53, field_175828_a, field_175828_a, false);
         }

         if (this.func_175818_a(structureBoundingBox, 51, 21, 58, 58)) {
            this.fillWithBlocks(world, structureBoundingBox, 51, 0, 21, 57, 0, 57, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 51, 1, 21, 57, 7, 57, false);
            this.fillWithBlocks(world, structureBoundingBox, 51, 4, 21, 53, 4, 53, field_175828_a, field_175828_a, false);

            for (int var10 = 0; var10 < 4; var10++) {
               this.fillWithBlocks(
                  world, structureBoundingBox, 57 - var10, var10 + 1, 21, 57 - var10, var10 + 1, 57 - var10, field_175826_b, field_175826_b, false
               );
            }

            for (byte var11 = 23; var11 < 53; var11 += 3) {
               this.setBlockState(world, field_175824_d, 52, 5, var11, structureBoundingBox);
            }

            this.setBlockState(world, field_175824_d, 52, 5, 52, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 51, 1, 52, 53, 3, 52, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 52, 1, 51, 52, 3, 53, field_175828_a, field_175828_a, false);
         }

         if (this.func_175818_a(structureBoundingBox, 0, 51, 57, 57)) {
            this.fillWithBlocks(world, structureBoundingBox, 7, 0, 51, 50, 0, 57, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 7, 1, 51, 50, 10, 57, false);

            for (int var12 = 0; var12 < 4; var12++) {
               this.fillWithBlocks(
                  world, structureBoundingBox, var12 + 1, var12 + 1, 57 - var12, 56 - var12, var12 + 1, 57 - var12, field_175826_b, field_175826_b, false
               );
            }
         }
      }

      private void func_175842_f(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.func_175818_a(structureBoundingBox, 7, 21, 13, 50)) {
            this.fillWithBlocks(world, structureBoundingBox, 7, 0, 21, 13, 0, 50, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 7, 1, 21, 13, 10, 50, false);
            this.fillWithBlocks(world, structureBoundingBox, 11, 8, 21, 13, 8, 53, field_175828_a, field_175828_a, false);

            for (int var7 = 0; var7 < 4; var7++) {
               this.fillWithBlocks(world, structureBoundingBox, var7 + 7, var7 + 5, 21, var7 + 7, var7 + 5, 54, field_175826_b, field_175826_b, false);
            }

            for (byte var8 = 21; var8 <= 45; var8 += 3) {
               this.setBlockState(world, field_175824_d, 12, 9, var8, structureBoundingBox);
            }
         }

         if (this.func_175818_a(structureBoundingBox, 44, 21, 50, 54)) {
            this.fillWithBlocks(world, structureBoundingBox, 44, 0, 21, 50, 0, 50, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 44, 1, 21, 50, 10, 50, false);
            this.fillWithBlocks(world, structureBoundingBox, 44, 8, 21, 46, 8, 53, field_175828_a, field_175828_a, false);

            for (int var9 = 0; var9 < 4; var9++) {
               this.fillWithBlocks(world, structureBoundingBox, 50 - var9, var9 + 5, 21, 50 - var9, var9 + 5, 54, field_175826_b, field_175826_b, false);
            }

            for (byte var10 = 21; var10 <= 45; var10 += 3) {
               this.setBlockState(world, field_175824_d, 45, 9, var10, structureBoundingBox);
            }
         }

         if (this.func_175818_a(structureBoundingBox, 8, 44, 49, 54)) {
            this.fillWithBlocks(world, structureBoundingBox, 14, 0, 44, 43, 0, 50, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 14, 1, 44, 43, 10, 50, false);

            for (byte var11 = 12; var11 <= 45; var11 += 3) {
               this.setBlockState(world, field_175824_d, var11, 9, 45, structureBoundingBox);
               this.setBlockState(world, field_175824_d, var11, 9, 52, structureBoundingBox);
               if (var11 == 12 || var11 == 18 || var11 == 24 || var11 == 33 || var11 == 39 || var11 == 45) {
                  this.setBlockState(world, field_175824_d, var11, 9, 47, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 9, 50, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 10, 45, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 10, 46, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 10, 51, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 10, 52, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 11, 47, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 11, 50, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 12, 48, structureBoundingBox);
                  this.setBlockState(world, field_175824_d, var11, 12, 49, structureBoundingBox);
               }
            }

            for (int var12 = 0; var12 < 3; var12++) {
               this.fillWithBlocks(world, structureBoundingBox, 8 + var12, 5 + var12, 54, 49 - var12, 5 + var12, 54, field_175828_a, field_175828_a, false);
            }

            this.fillWithBlocks(world, structureBoundingBox, 11, 8, 54, 46, 8, 54, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 14, 8, 44, 43, 8, 53, field_175828_a, field_175828_a, false);
         }
      }

      private void func_175838_g(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.func_175818_a(structureBoundingBox, 14, 21, 20, 43)) {
            this.fillWithBlocks(world, structureBoundingBox, 14, 0, 21, 20, 0, 43, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 14, 1, 22, 20, 14, 43, false);
            this.fillWithBlocks(world, structureBoundingBox, 18, 12, 22, 20, 12, 39, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 18, 12, 21, 20, 12, 21, field_175826_b, field_175826_b, false);

            for (int var7 = 0; var7 < 4; var7++) {
               this.fillWithBlocks(world, structureBoundingBox, var7 + 14, var7 + 9, 21, var7 + 14, var7 + 9, 43 - var7, field_175826_b, field_175826_b, false);
            }

            for (byte var8 = 23; var8 <= 39; var8 += 3) {
               this.setBlockState(world, field_175824_d, 19, 13, var8, structureBoundingBox);
            }
         }

         if (this.func_175818_a(structureBoundingBox, 37, 21, 43, 43)) {
            this.fillWithBlocks(world, structureBoundingBox, 37, 0, 21, 43, 0, 43, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 37, 1, 22, 43, 14, 43, false);
            this.fillWithBlocks(world, structureBoundingBox, 37, 12, 22, 39, 12, 39, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 37, 12, 21, 39, 12, 21, field_175826_b, field_175826_b, false);

            for (int var9 = 0; var9 < 4; var9++) {
               this.fillWithBlocks(world, structureBoundingBox, 43 - var9, var9 + 9, 21, 43 - var9, var9 + 9, 43 - var9, field_175826_b, field_175826_b, false);
            }

            for (byte var10 = 23; var10 <= 39; var10 += 3) {
               this.setBlockState(world, field_175824_d, 38, 13, var10, structureBoundingBox);
            }
         }

         if (this.func_175818_a(structureBoundingBox, 15, 37, 42, 43)) {
            this.fillWithBlocks(world, structureBoundingBox, 21, 0, 37, 36, 0, 43, field_175828_a, field_175828_a, false);
            this.func_181655_a(world, structureBoundingBox, 21, 1, 37, 36, 14, 43, false);
            this.fillWithBlocks(world, structureBoundingBox, 21, 12, 37, 36, 12, 39, field_175828_a, field_175828_a, false);

            for (int var11 = 0; var11 < 4; var11++) {
               this.fillWithBlocks(
                  world, structureBoundingBox, 15 + var11, var11 + 9, 43 - var11, 42 - var11, var11 + 9, 43 - var11, field_175826_b, field_175826_b, false
               );
            }

            for (byte var12 = 21; var12 <= 36; var12 += 3) {
               this.setBlockState(world, field_175824_d, var12, 13, 38, structureBoundingBox);
            }
         }
      }
   }

   public static class MonumentCoreRoom extends StructureOceanMonumentPieces.Piece {
      public MonumentCoreRoom() {
      }

      public MonumentCoreRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         super(1, enumFacing, roomDefinition, 2, 2, 2);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         this.func_175819_a(world, structureBoundingBox, 1, 8, 0, 14, 8, 14, field_175828_a);
         byte var7 = 7;
         IBlockState var8 = field_175826_b;
         this.fillWithBlocks(world, structureBoundingBox, 0, var7, 0, 0, var7, 15, var8, var8, false);
         this.fillWithBlocks(world, structureBoundingBox, 15, var7, 0, 15, var7, 15, var8, var8, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, var7, 0, 15, var7, 0, var8, var8, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, var7, 15, 14, var7, 15, var8, var8, false);

         for (int var10 = 1; var10 <= 6; var10++) {
            var8 = field_175826_b;
            if (var10 == 2 || var10 == 6) {
               var8 = field_175828_a;
            }

            for (byte var9 = 0; var9 <= 15; var9 += 15) {
               this.fillWithBlocks(world, structureBoundingBox, var9, var10, 0, var9, var10, 1, var8, var8, false);
               this.fillWithBlocks(world, structureBoundingBox, var9, var10, 6, var9, var10, 9, var8, var8, false);
               this.fillWithBlocks(world, structureBoundingBox, var9, var10, 14, var9, var10, 15, var8, var8, false);
            }

            this.fillWithBlocks(world, structureBoundingBox, 1, var10, 0, 1, var10, 0, var8, var8, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, var10, 0, 9, var10, 0, var8, var8, false);
            this.fillWithBlocks(world, structureBoundingBox, 14, var10, 0, 14, var10, 0, var8, var8, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, var10, 15, 14, var10, 15, var8, var8, false);
         }

         this.fillWithBlocks(world, structureBoundingBox, 6, 3, 6, 9, 6, 9, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 4, 7, 8, 5, 8, Blocks.gold_block.getDefaultState(), Blocks.gold_block.getDefaultState(), false);

         for (byte var11 = 3; var11 <= 6; var11 += 3) {
            for (byte var13 = 6; var13 <= 9; var13 += 3) {
               this.setBlockState(world, field_175825_e, var13, var11, 6, structureBoundingBox);
               this.setBlockState(world, field_175825_e, var13, var11, 9, structureBoundingBox);
            }
         }

         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 6, 5, 2, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 1, 9, 5, 2, 9, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 1, 6, 10, 2, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 1, 9, 10, 2, 9, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 1, 5, 6, 2, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 1, 5, 9, 2, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 1, 10, 6, 2, 10, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 1, 10, 9, 2, 10, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 2, 5, 5, 6, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 2, 10, 5, 6, 10, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 2, 5, 10, 6, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 2, 10, 10, 6, 10, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 7, 1, 5, 7, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 7, 1, 10, 7, 6, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 5, 7, 9, 5, 7, 14, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 7, 9, 10, 7, 14, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 7, 5, 6, 7, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 7, 10, 6, 7, 10, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 7, 5, 14, 7, 5, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 7, 10, 14, 7, 10, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 2, 2, 1, 3, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 2, 3, 1, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 13, 1, 2, 13, 1, 3, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 12, 1, 2, 12, 1, 2, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, 1, 12, 2, 1, 13, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 13, 3, 1, 13, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 13, 1, 12, 13, 1, 13, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 12, 1, 13, 12, 1, 13, field_175826_b, field_175826_b, false);
         return true;
      }
   }

   interface MonumentRoomFitHelper {
      boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition roomDefinition);

      StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random);
   }

   public static class Penthouse extends StructureOceanMonumentPieces.Piece {
      public Penthouse() {
      }

      public Penthouse(EnumFacing enumFacing, StructureBoundingBox structureBoundingBox) {
         super(enumFacing, structureBoundingBox);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         this.fillWithBlocks(world, structureBoundingBox, 2, -1, 2, 11, -1, 11, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, -1, 0, 1, -1, 11, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 12, -1, 0, 13, -1, 11, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, -1, 0, 11, -1, 1, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 2, -1, 12, 11, -1, 13, field_175828_a, field_175828_a, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 0, 0, 0, 0, 13, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 13, 0, 0, 13, 0, 13, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 0, 12, 0, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 0, 13, 12, 0, 13, field_175826_b, field_175826_b, false);

         for (byte var7 = 2; var7 <= 11; var7 += 3) {
            this.setBlockState(world, field_175825_e, 0, 0, var7, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 13, 0, var7, structureBoundingBox);
            this.setBlockState(world, field_175825_e, var7, 0, 0, structureBoundingBox);
         }

         this.fillWithBlocks(world, structureBoundingBox, 2, 0, 3, 4, 0, 9, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 9, 0, 3, 11, 0, 9, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 4, 0, 9, 9, 0, 11, field_175826_b, field_175826_b, false);
         this.setBlockState(world, field_175826_b, 5, 0, 8, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 8, 0, 8, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 10, 0, 10, structureBoundingBox);
         this.setBlockState(world, field_175826_b, 3, 0, 10, structureBoundingBox);
         this.fillWithBlocks(world, structureBoundingBox, 3, 0, 3, 3, 0, 7, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 10, 0, 3, 10, 0, 7, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, 0, 10, 7, 0, 10, field_175827_c, field_175827_c, false);
         byte var10 = 3;

         for (int var8 = 0; var8 < 2; var8++) {
            for (byte var9 = 2; var9 <= 8; var9 += 3) {
               this.fillWithBlocks(world, structureBoundingBox, var10, 0, var9, var10, 2, var9, field_175826_b, field_175826_b, false);
            }

            var10 = 10;
         }

         this.fillWithBlocks(world, structureBoundingBox, 5, 0, 10, 5, 2, 10, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 8, 0, 10, 8, 2, 10, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 6, -1, 7, 7, -1, 8, field_175827_c, field_175827_c, false);
         this.func_181655_a(world, structureBoundingBox, 6, -1, 3, 7, -1, 4, false);
         this.func_175817_a(world, structureBoundingBox, 6, 1, 6);
         return true;
      }
   }

   public abstract static class Piece extends StructureComponent {
      protected static final IBlockState field_175828_a = Blocks.prismarine.getStateFromMeta(BlockPrismarine.ROUGH_META);
      protected static final IBlockState field_175826_b = Blocks.prismarine.getStateFromMeta(BlockPrismarine.BRICKS_META);
      protected static final IBlockState field_175827_c = Blocks.prismarine.getStateFromMeta(BlockPrismarine.DARK_META);
      protected static final IBlockState field_175824_d = field_175826_b;
      protected static final IBlockState field_175825_e = Blocks.sea_lantern.getDefaultState();
      protected static final IBlockState field_175822_f = Blocks.water.getDefaultState();
      protected static final int field_175823_g = func_175820_a(2, 0, 0);
      protected static final int field_175831_h = func_175820_a(2, 2, 0);
      protected static final int field_175832_i = func_175820_a(0, 1, 0);
      protected static final int field_175829_j = func_175820_a(4, 1, 0);
      protected StructureOceanMonumentPieces.RoomDefinition field_175830_k;

      protected static final int func_175820_a(int integer1, int integer2, int integer3) {
         return integer2 * 25 + integer3 * 5 + integer1;
      }

      public Piece() {
         super(0);
      }

      public Piece(int integer) {
         super(integer);
      }

      public Piece(EnumFacing enumFacing, StructureBoundingBox structureBoundingBox) {
         super(1);
         this.coordBaseMode = enumFacing;
         this.boundingBox = structureBoundingBox;
      }

      protected Piece(int integer1, EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, int integer4, int integer5, int integer6) {
         super(integer1);
         this.coordBaseMode = enumFacing;
         this.field_175830_k = roomDefinition;
         int var10 = roomDefinition.field_175967_a;
         int var11 = var10 % 5;
         int var12 = var10 / 5 % 5;
         int var13 = var10 / 25;
         if (enumFacing != EnumFacing.NORTH && enumFacing != EnumFacing.SOUTH) {
            this.boundingBox = new StructureBoundingBox(0, 0, 0, integer6 * 8 - 1, integer5 * 4 - 1, integer4 * 8 - 1);
         } else {
            this.boundingBox = new StructureBoundingBox(0, 0, 0, integer4 * 8 - 1, integer5 * 4 - 1, integer6 * 8 - 1);
         }

         switch (enumFacing) {
            case NORTH:
               this.boundingBox.offset(var11 * 8, var13 * 4, -(var12 + integer6) * 8 + 1);
               break;
            case SOUTH:
               this.boundingBox.offset(var11 * 8, var13 * 4, var12 * 8);
               break;
            case WEST:
               this.boundingBox.offset(-(var12 + integer6) * 8 + 1, var13 * 4, var11 * 8);
               break;
            default:
               this.boundingBox.offset(var12 * 8, var13 * 4, var11 * 8);
         }
      }

      @Override
      protected void writeStructureToNBT(NBTTagCompound nBTTagCompound) {
      }

      @Override
      protected void readStructureFromNBT(NBTTagCompound nBTTagCompound) {
      }

      protected void func_181655_a(
         World world,
         StructureBoundingBox structureBoundingBox,
         int integer3,
         int integer4,
         int integer5,
         int integer6,
         int integer7,
         int integer8,
         boolean boolean9
      ) {
         for (int var13 = integer4; var13 <= integer7; var13++) {
            for (int var14 = integer3; var14 <= integer6; var14++) {
               for (int var15 = integer5; var15 <= integer8; var15++) {
                  if (!boolean9 || this.getBlockStateFromPos(world, var14, var13, var15, structureBoundingBox).getBlock().getMaterial() != Material.air) {
                     if (this.getYWithOffset(var13) >= world.getSeaLevel()) {
                        this.setBlockState(world, Blocks.air.getDefaultState(), var14, var13, var15, structureBoundingBox);
                     } else {
                        this.setBlockState(world, field_175822_f, var14, var13, var15, structureBoundingBox);
                     }
                  }
               }
            }
         }
      }

      protected void func_175821_a(World world, StructureBoundingBox structureBoundingBox, int integer3, int integer4, boolean boolean5) {
         if (boolean5) {
            this.fillWithBlocks(world, structureBoundingBox, integer3, 0, integer4, integer3 + 2, 0, integer4 + 8 - 1, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(
               world, structureBoundingBox, integer3 + 5, 0, integer4, integer3 + 8 - 1, 0, integer4 + 8 - 1, field_175828_a, field_175828_a, false
            );
            this.fillWithBlocks(world, structureBoundingBox, integer3 + 3, 0, integer4, integer3 + 4, 0, integer4 + 2, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(
               world, structureBoundingBox, integer3 + 3, 0, integer4 + 5, integer3 + 4, 0, integer4 + 8 - 1, field_175828_a, field_175828_a, false
            );
            this.fillWithBlocks(
               world, structureBoundingBox, integer3 + 3, 0, integer4 + 2, integer3 + 4, 0, integer4 + 2, field_175826_b, field_175826_b, false
            );
            this.fillWithBlocks(
               world, structureBoundingBox, integer3 + 3, 0, integer4 + 5, integer3 + 4, 0, integer4 + 5, field_175826_b, field_175826_b, false
            );
            this.fillWithBlocks(
               world, structureBoundingBox, integer3 + 2, 0, integer4 + 3, integer3 + 2, 0, integer4 + 4, field_175826_b, field_175826_b, false
            );
            this.fillWithBlocks(
               world, structureBoundingBox, integer3 + 5, 0, integer4 + 3, integer3 + 5, 0, integer4 + 4, field_175826_b, field_175826_b, false
            );
         } else {
            this.fillWithBlocks(
               world, structureBoundingBox, integer3, 0, integer4, integer3 + 8 - 1, 0, integer4 + 8 - 1, field_175828_a, field_175828_a, false
            );
         }
      }

      protected void func_175819_a(
         World world,
         StructureBoundingBox structureBoundingBox,
         int integer3,
         int integer4,
         int integer5,
         int integer6,
         int integer7,
         int integer8,
         IBlockState iBlockState
      ) {
         for (int var13 = integer4; var13 <= integer7; var13++) {
            for (int var14 = integer3; var14 <= integer6; var14++) {
               for (int var15 = integer5; var15 <= integer8; var15++) {
                  if (this.getBlockStateFromPos(world, var14, var13, var15, structureBoundingBox) == field_175822_f) {
                     this.setBlockState(world, iBlockState, var14, var13, var15, structureBoundingBox);
                  }
               }
            }
         }
      }

      protected boolean func_175818_a(StructureBoundingBox structureBoundingBox, int integer2, int integer3, int integer4, int integer5) {
         int var6 = this.getXWithOffset(integer2, integer3);
         int var7 = this.getZWithOffset(integer2, integer3);
         int var8 = this.getXWithOffset(integer4, integer5);
         int var9 = this.getZWithOffset(integer4, integer5);
         return structureBoundingBox.intersectsWith(Math.min(var6, var8), Math.min(var7, var9), Math.max(var6, var8), Math.max(var7, var9));
      }

      protected boolean func_175817_a(World world, StructureBoundingBox structureBoundingBox, int integer3, int integer4, int integer5) {
         int var9 = this.getXWithOffset(integer3, integer5);
         int var10 = this.getYWithOffset(integer4);
         int var11 = this.getZWithOffset(integer3, integer5);
         if (structureBoundingBox.isVecInside(new BlockPos(var9, var10, var11))) {
            EntityGuardian var12 = new EntityGuardian(world);
            var12.setElder(true);
            var12.heal(var12.getMaxHealth());
            var12.setLocationAndAngles(var9 + 0.5, var10, var11 + 0.5, 0.0F, 0.0F);
            var12.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(var12)), null);
            world.spawnEntityInWorld(var12);
            return true;
         } else {
            return false;
         }
      }
   }

   static class RoomDefinition {
      int field_175967_a;
      StructureOceanMonumentPieces.RoomDefinition[] field_175965_b = new StructureOceanMonumentPieces.RoomDefinition[6];
      boolean[] field_175966_c = new boolean[6];
      boolean field_175963_d;
      boolean field_175964_e;
      int field_175962_f;

      public RoomDefinition(int integer) {
         this.field_175967_a = integer;
      }

      public void func_175957_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         this.field_175965_b[enumFacing.getIndex()] = roomDefinition;
         roomDefinition.field_175965_b[enumFacing.getOpposite().getIndex()] = this;
      }

      public void func_175958_a() {
         for (int var4 = 0; var4 < 6; var4++) {
            this.field_175966_c[var4] = this.field_175965_b[var4] != null;
         }
      }

      public boolean func_175959_a(int integer) {
         if (this.field_175964_e) {
            return true;
         }

         this.field_175962_f = integer;

         for (int var5 = 0; var5 < 6; var5++) {
            if (this.field_175965_b[var5] != null
               && this.field_175966_c[var5]
               && this.field_175965_b[var5].field_175962_f != integer
               && this.field_175965_b[var5].func_175959_a(integer)) {
               return true;
            }
         }

         return false;
      }

      public boolean func_175961_b() {
         return this.field_175967_a >= 75;
      }

      public int func_175960_c() {
         int var4 = 0;

         for (int var5 = 0; var5 < 6; var5++) {
            if (this.field_175966_c[var5]) {
               var4++;
            }
         }

         return var4;
      }
   }

   public static class SimpleRoom extends StructureOceanMonumentPieces.Piece {
      private int field_175833_o;

      public SimpleRoom() {
      }

      public SimpleRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         super(1, enumFacing, roomDefinition, 1, 1, 1);
         this.field_175833_o = random.nextInt(3);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(world, structureBoundingBox, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 4, 1, 6, 4, 6, field_175828_a);
         }

         boolean var7 = this.field_175833_o != 0
            && random.nextBoolean()
            && !this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]
            && !this.field_175830_k.field_175966_c[EnumFacing.UP.getIndex()]
            && this.field_175830_k.func_175960_c() > 1;
         if (this.field_175833_o == 0) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 2, 1, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 2, 3, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 2, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 2, 2, 0, field_175828_a, field_175828_a, false);
            this.setBlockState(world, field_175825_e, 1, 2, 1, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 0, 7, 1, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 3, 0, 7, 3, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 2, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 2, 0, 6, 2, 0, field_175828_a, field_175828_a, false);
            this.setBlockState(world, field_175825_e, 6, 2, 1, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 5, 2, 1, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 5, 2, 3, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 5, 0, 2, 7, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 2, 2, 7, field_175828_a, field_175828_a, false);
            this.setBlockState(world, field_175825_e, 1, 2, 6, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 5, 7, 1, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 3, 5, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 2, 5, 7, 2, 7, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 2, 7, 6, 2, 7, field_175828_a, field_175828_a, false);
            this.setBlockState(world, field_175825_e, 6, 2, 6, structureBoundingBox);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 3, 3, 0, 4, 3, 0, field_175826_b, field_175826_b, false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, 3, 3, 0, 4, 3, 1, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 3, 2, 0, 4, 2, 0, field_175828_a, field_175828_a, false);
               this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 4, 1, 1, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 3, 3, 7, 4, 3, 7, field_175826_b, field_175826_b, false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, 3, 3, 6, 4, 3, 7, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 3, 2, 7, 4, 2, 7, field_175828_a, field_175828_a, false);
               this.fillWithBlocks(world, structureBoundingBox, 3, 1, 6, 4, 1, 7, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 0, 3, 3, 0, 3, 4, field_175826_b, field_175826_b, false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, 0, 3, 3, 1, 3, 4, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, 2, 3, 0, 2, 4, field_175828_a, field_175828_a, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, 1, 3, 1, 1, 4, field_175826_b, field_175826_b, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 7, 3, 3, 7, 3, 4, field_175826_b, field_175826_b, false);
            } else {
               this.fillWithBlocks(world, structureBoundingBox, 6, 3, 3, 7, 3, 4, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 7, 2, 3, 7, 2, 4, field_175828_a, field_175828_a, false);
               this.fillWithBlocks(world, structureBoundingBox, 6, 1, 3, 7, 1, 4, field_175826_b, field_175826_b, false);
            }
         } else if (this.field_175833_o == 1) {
            this.fillWithBlocks(world, structureBoundingBox, 2, 1, 2, 2, 3, 2, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 2, 1, 5, 2, 3, 5, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 5, 5, 3, 5, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 5, 1, 2, 5, 3, 2, field_175826_b, field_175826_b, false);
            this.setBlockState(world, field_175825_e, 2, 2, 2, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 2, 2, 5, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 5, 2, 5, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 5, 2, 2, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 1, 3, 0, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 3, 1, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 7, 1, 3, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 6, 0, 3, 6, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 7, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 6, 7, 3, 6, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 1, 0, 7, 3, 0, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 1, 7, 3, 1, field_175826_b, field_175826_b, false);
            this.setBlockState(world, field_175828_a, 1, 2, 0, structureBoundingBox);
            this.setBlockState(world, field_175828_a, 0, 2, 1, structureBoundingBox);
            this.setBlockState(world, field_175828_a, 1, 2, 7, structureBoundingBox);
            this.setBlockState(world, field_175828_a, 0, 2, 6, structureBoundingBox);
            this.setBlockState(world, field_175828_a, 6, 2, 7, structureBoundingBox);
            this.setBlockState(world, field_175828_a, 7, 2, 6, structureBoundingBox);
            this.setBlockState(world, field_175828_a, 6, 2, 0, structureBoundingBox);
            this.setBlockState(world, field_175828_a, 7, 2, 1, structureBoundingBox);
            if (!this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 6, 2, 0, field_175828_a, field_175828_a, false);
               this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 6, 2, 7, field_175828_a, field_175828_a, false);
               this.fillWithBlocks(world, structureBoundingBox, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 0, 3, 1, 0, 3, 6, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, 2, 1, 0, 2, 6, field_175828_a, field_175828_a, false);
               this.fillWithBlocks(world, structureBoundingBox, 0, 1, 1, 0, 1, 6, field_175826_b, field_175826_b, false);
            }

            if (!this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
               this.fillWithBlocks(world, structureBoundingBox, 7, 3, 1, 7, 3, 6, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, 7, 2, 1, 7, 2, 6, field_175828_a, field_175828_a, false);
               this.fillWithBlocks(world, structureBoundingBox, 7, 1, 1, 7, 1, 6, field_175826_b, field_175826_b, false);
            }
         } else if (this.field_175833_o == 2) {
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 7, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 7, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 6, 2, 0, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 6, 2, 7, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 3, 7, 2, 4, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, field_175827_c, field_175827_c, false);
            if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
               this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.NORTH.getIndex()]) {
               this.func_181655_a(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.WEST.getIndex()]) {
               this.func_181655_a(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, false);
            }

            if (this.field_175830_k.field_175966_c[EnumFacing.EAST.getIndex()]) {
               this.func_181655_a(world, structureBoundingBox, 7, 1, 3, 7, 2, 4, false);
            }
         }

         if (var7) {
            this.fillWithBlocks(world, structureBoundingBox, 3, 1, 3, 4, 1, 4, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 2, 3, 4, 2, 4, field_175828_a, field_175828_a, false);
            this.fillWithBlocks(world, structureBoundingBox, 3, 3, 3, 4, 3, 4, field_175826_b, field_175826_b, false);
         }

         return true;
      }
   }

   public static class SimpleTopRoom extends StructureOceanMonumentPieces.Piece {
      public SimpleTopRoom() {
      }

      public SimpleTopRoom(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         super(1, enumFacing, roomDefinition, 1, 1, 1);
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_175830_k.field_175967_a / 25 > 0) {
            this.func_175821_a(world, structureBoundingBox, 0, 0, this.field_175830_k.field_175966_c[EnumFacing.DOWN.getIndex()]);
         }

         if (this.field_175830_k.field_175965_b[EnumFacing.UP.getIndex()] == null) {
            this.func_175819_a(world, structureBoundingBox, 1, 4, 1, 6, 4, 6, field_175828_a);
         }

         for (int var7 = 1; var7 <= 6; var7++) {
            for (int var8 = 1; var8 <= 6; var8++) {
               if (random.nextInt(3) != 0) {
                  int var9 = 2 + (random.nextInt(4) == 0 ? 0 : 1);
                  this.fillWithBlocks(
                     world, structureBoundingBox, var7, var9, var8, var7, 3, var8, Blocks.sponge.getStateFromMeta(1), Blocks.sponge.getStateFromMeta(1), false
                  );
               }
            }
         }

         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 0, 0, 1, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 1, 0, 7, 1, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 0, 6, 1, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 1, 7, 6, 1, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 2, 0, 0, 2, 7, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 2, 0, 7, 2, 7, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 0, 6, 2, 0, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 2, 7, 6, 2, 7, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 3, 0, 0, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 3, 0, 7, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 0, 6, 3, 0, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 1, 3, 7, 6, 3, 7, field_175826_b, field_175826_b, false);
         this.fillWithBlocks(world, structureBoundingBox, 0, 1, 3, 0, 2, 4, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 7, 1, 3, 7, 2, 4, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, field_175827_c, field_175827_c, false);
         this.fillWithBlocks(world, structureBoundingBox, 3, 1, 7, 4, 2, 7, field_175827_c, field_175827_c, false);
         if (this.field_175830_k.field_175966_c[EnumFacing.SOUTH.getIndex()]) {
            this.func_181655_a(world, structureBoundingBox, 3, 1, 0, 4, 2, 0, false);
         }

         return true;
      }
   }

   public static class WingRoom extends StructureOceanMonumentPieces.Piece {
      private int field_175834_o;

      public WingRoom() {
      }

      public WingRoom(EnumFacing enumFacing, StructureBoundingBox structureBoundingBox, int integer) {
         super(enumFacing, structureBoundingBox);
         this.field_175834_o = integer & 1;
      }

      @Override
      public boolean addComponentParts(World world, Random random, StructureBoundingBox structureBoundingBox) {
         if (this.field_175834_o == 0) {
            for (int var7 = 0; var7 < 4; var7++) {
               this.fillWithBlocks(world, structureBoundingBox, 10 - var7, 3 - var7, 20 - var7, 12 + var7, 3 - var7, 20, field_175826_b, field_175826_b, false);
            }

            this.fillWithBlocks(world, structureBoundingBox, 7, 0, 6, 15, 0, 16, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 6, 0, 6, 6, 3, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 16, 0, 6, 16, 3, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 7, 7, 1, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 1, 7, 15, 1, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 7, 1, 6, 9, 3, 6, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 13, 1, 6, 15, 3, 6, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 1, 7, 9, 1, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 13, 1, 7, 14, 1, 7, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 9, 0, 5, 13, 0, 5, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 10, 0, 7, 12, 0, 7, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 8, 0, 10, 8, 0, 12, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 14, 0, 10, 14, 0, 12, field_175827_c, field_175827_c, false);

            for (byte var11 = 18; var11 >= 7; var11 -= 3) {
               this.setBlockState(world, field_175825_e, 6, 3, var11, structureBoundingBox);
               this.setBlockState(world, field_175825_e, 16, 3, var11, structureBoundingBox);
            }

            this.setBlockState(world, field_175825_e, 10, 0, 10, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 12, 0, 10, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 10, 0, 12, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 12, 0, 12, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 8, 3, 6, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 14, 3, 6, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 4, 2, 4, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 4, 1, 4, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 4, 0, 4, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 18, 2, 4, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 18, 1, 4, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 18, 0, 4, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 4, 2, 18, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 4, 1, 18, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 4, 0, 18, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 18, 2, 18, structureBoundingBox);
            this.setBlockState(world, field_175825_e, 18, 1, 18, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 18, 0, 18, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 9, 7, 20, structureBoundingBox);
            this.setBlockState(world, field_175826_b, 13, 7, 20, structureBoundingBox);
            this.fillWithBlocks(world, structureBoundingBox, 6, 0, 21, 7, 4, 21, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 15, 0, 21, 16, 4, 21, field_175826_b, field_175826_b, false);
            this.func_175817_a(world, structureBoundingBox, 11, 2, 16);
         } else if (this.field_175834_o == 1) {
            this.fillWithBlocks(world, structureBoundingBox, 9, 3, 18, 13, 3, 20, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 9, 0, 18, 9, 2, 18, field_175826_b, field_175826_b, false);
            this.fillWithBlocks(world, structureBoundingBox, 13, 0, 18, 13, 2, 18, field_175826_b, field_175826_b, false);
            byte var12 = 9;
            byte var8 = 20;

            for (int var10 = 0; var10 < 2; var10++) {
               this.setBlockState(world, field_175826_b, var12, 6, var8, structureBoundingBox);
               this.setBlockState(world, field_175825_e, var12, 5, var8, structureBoundingBox);
               this.setBlockState(world, field_175826_b, var12, 4, var8, structureBoundingBox);
               var12 = 13;
            }

            this.fillWithBlocks(world, structureBoundingBox, 7, 3, 7, 15, 3, 14, field_175826_b, field_175826_b, false);
            var12 = 10;

            for (int var15 = 0; var15 < 2; var15++) {
               this.fillWithBlocks(world, structureBoundingBox, var12, 0, 10, var12, 6, 10, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, var12, 0, 12, var12, 6, 12, field_175826_b, field_175826_b, false);
               this.setBlockState(world, field_175825_e, var12, 0, 10, structureBoundingBox);
               this.setBlockState(world, field_175825_e, var12, 0, 12, structureBoundingBox);
               this.setBlockState(world, field_175825_e, var12, 4, 10, structureBoundingBox);
               this.setBlockState(world, field_175825_e, var12, 4, 12, structureBoundingBox);
               var12 = 12;
            }

            var12 = 8;

            for (int var16 = 0; var16 < 2; var16++) {
               this.fillWithBlocks(world, structureBoundingBox, var12, 0, 7, var12, 2, 7, field_175826_b, field_175826_b, false);
               this.fillWithBlocks(world, structureBoundingBox, var12, 0, 14, var12, 2, 14, field_175826_b, field_175826_b, false);
               var12 = 14;
            }

            this.fillWithBlocks(world, structureBoundingBox, 8, 3, 8, 8, 3, 13, field_175827_c, field_175827_c, false);
            this.fillWithBlocks(world, structureBoundingBox, 14, 3, 8, 14, 3, 13, field_175827_c, field_175827_c, false);
            this.func_175817_a(world, structureBoundingBox, 11, 5, 13);
         }

         return true;
      }
   }

   static class XDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private XDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         return roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d;
      }

      @Override
      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         roomDefinition.field_175963_d = true;
         roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleXRoom(enumFacing, roomDefinition, random);
      }
   }

   static class XYDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private XYDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         if (roomDefinition.field_175966_c[EnumFacing.EAST.getIndex()]
            && !roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d
            && roomDefinition.field_175966_c[EnumFacing.UP.getIndex()]
            && !roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
            StructureOceanMonumentPieces.RoomDefinition var5 = roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()];
            return var5.field_175966_c[EnumFacing.UP.getIndex()] && !var5.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
         } else {
            return false;
         }
      }

      @Override
      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         roomDefinition.field_175963_d = true;
         roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175963_d = true;
         roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         roomDefinition.field_175965_b[EnumFacing.EAST.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleXYRoom(enumFacing, roomDefinition, random);
      }
   }

   static class YDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private YDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         return roomDefinition.field_175966_c[EnumFacing.UP.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
      }

      @Override
      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         roomDefinition.field_175963_d = true;
         roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleYRoom(enumFacing, roomDefinition, random);
      }
   }

   static class YZDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private YZDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         if (roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()]
            && !roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d
            && roomDefinition.field_175966_c[EnumFacing.UP.getIndex()]
            && !roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d) {
            StructureOceanMonumentPieces.RoomDefinition var5 = roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()];
            return var5.field_175966_c[EnumFacing.UP.getIndex()] && !var5.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d;
         } else {
            return false;
         }
      }

      @Override
      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         roomDefinition.field_175963_d = true;
         roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
         roomDefinition.field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175965_b[EnumFacing.UP.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleYZRoom(enumFacing, roomDefinition, random);
      }
   }

   static class ZDoubleRoomFitHelper implements StructureOceanMonumentPieces.MonumentRoomFitHelper {
      private ZDoubleRoomFitHelper() {
      }

      @Override
      public boolean func_175969_a(StructureOceanMonumentPieces.RoomDefinition roomDefinition) {
         return roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] && !roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d;
      }

      @Override
      public StructureOceanMonumentPieces.Piece func_175968_a(EnumFacing enumFacing, StructureOceanMonumentPieces.RoomDefinition roomDefinition, Random random) {
         StructureOceanMonumentPieces.RoomDefinition var7 = roomDefinition;
         if (!roomDefinition.field_175966_c[EnumFacing.NORTH.getIndex()] || roomDefinition.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d) {
            var7 = roomDefinition.field_175965_b[EnumFacing.SOUTH.getIndex()];
         }

         var7.field_175963_d = true;
         var7.field_175965_b[EnumFacing.NORTH.getIndex()].field_175963_d = true;
         return new StructureOceanMonumentPieces.DoubleZRoom(enumFacing, var7, random);
      }
   }
}
