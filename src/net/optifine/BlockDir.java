package net.optifine;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public enum BlockDir {
   DOWN(EnumFacing.DOWN),
   UP(EnumFacing.UP),
   NORTH(EnumFacing.NORTH),
   SOUTH(EnumFacing.SOUTH),
   WEST(EnumFacing.WEST),
   EAST(EnumFacing.EAST),
   NORTH_WEST(EnumFacing.NORTH, EnumFacing.WEST),
   NORTH_EAST(EnumFacing.NORTH, EnumFacing.EAST),
   SOUTH_WEST(EnumFacing.SOUTH, EnumFacing.WEST),
   SOUTH_EAST(EnumFacing.SOUTH, EnumFacing.EAST),
   DOWN_NORTH(EnumFacing.DOWN, EnumFacing.NORTH),
   DOWN_SOUTH(EnumFacing.DOWN, EnumFacing.SOUTH),
   UP_NORTH(EnumFacing.UP, EnumFacing.NORTH),
   UP_SOUTH(EnumFacing.UP, EnumFacing.SOUTH),
   DOWN_WEST(EnumFacing.DOWN, EnumFacing.WEST),
   DOWN_EAST(EnumFacing.DOWN, EnumFacing.EAST),
   UP_WEST(EnumFacing.UP, EnumFacing.WEST),
   UP_EAST(EnumFacing.UP, EnumFacing.EAST);

   private final EnumFacing facing1;
   private EnumFacing facing2;

   BlockDir(EnumFacing enumFacing) {
      this.facing1 = enumFacing;
   }

   BlockDir(EnumFacing enumFacing3, EnumFacing enumFacing4) {
      this.facing1 = enumFacing3;
      this.facing2 = enumFacing4;
   }

   public EnumFacing getFacing1() {
      return this.facing1;
   }

   public EnumFacing getFacing2() {
      return this.facing2;
   }

   BlockPos offset(BlockPos blockPos) {
      blockPos = blockPos.offset(this.facing1, 1);
      if (this.facing2 != null) {
         blockPos = blockPos.offset(this.facing2, 1);
      }

      return blockPos;
   }

   public int Method946() {
      int var3 = this.facing1.getFrontOffsetX();
      if (this.facing2 != null) {
         var3 += this.facing2.getFrontOffsetX();
      }

      return var3;
   }

   public int Method947() {
      int var3 = this.facing1.getFrontOffsetY();
      if (this.facing2 != null) {
         var3 += this.facing2.getFrontOffsetY();
      }

      return var3;
   }

   public int Method948() {
      int var3 = this.facing1.getFrontOffsetZ();
      if (this.facing2 != null) {
         var3 += this.facing2.getFrontOffsetZ();
      }

      return var3;
   }

   public boolean isDouble() {
      return this.facing2 != null;
   }
}
