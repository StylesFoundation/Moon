package net.minecraft.block.state;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockPistonStructureHelper {
   private final World world;
   private final BlockPos pistonPos;
   private final BlockPos blockToMove;
   private final EnumFacing moveDirection;
   private final List<BlockPos> toMove = Lists.newArrayList();
   private final List<BlockPos> toDestroy = Lists.newArrayList();

   public BlockPistonStructureHelper(World world, BlockPos blockPos, EnumFacing enumFacing, boolean boolean4) {
      this.world = world;
      this.pistonPos = blockPos;
      if (boolean4) {
         this.moveDirection = enumFacing;
         this.blockToMove = blockPos.offset(enumFacing);
      } else {
         this.moveDirection = enumFacing.getOpposite();
         this.blockToMove = blockPos.offset(enumFacing, 2);
      }
   }

   public boolean canMove() {
      this.toMove.clear();
      this.toDestroy.clear();
      Block var4 = this.world.getBlockState(this.blockToMove).getBlock();
      if (!BlockPistonBase.canPush(var4, this.world, this.blockToMove, this.moveDirection, false)) {
         if (var4.getMobilityFlag() != 1) {
            return false;
         }

         this.toDestroy.add(this.blockToMove);
         return true;
      } else {
         if (!this.func_177251_a(this.blockToMove)) {
            return false;
         }

         for (BlockPos var6 : this.toMove) {
            if (this.world.getBlockState(var6).getBlock() == Blocks.slime_block && !this.func_177250_b(var6)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean func_177251_a(BlockPos blockPos) {
      Block var5 = this.world.getBlockState(blockPos).getBlock();
      if (var5.getMaterial() == Material.air) {
         return true;
      }

      if (!BlockPistonBase.canPush(var5, this.world, blockPos, this.moveDirection, false)) {
         return true;
      }

      if (blockPos.equals(this.pistonPos)) {
         return true;
      }

      if (this.toMove.contains(blockPos)) {
         return true;
      }

      int var6 = 1;
      if (var6 + this.toMove.size() > 12) {
         return false;
      }

      while (var5 == Blocks.slime_block) {
         BlockPos var7 = blockPos.offset(this.moveDirection.getOpposite(), var6);
         var5 = this.world.getBlockState(var7).getBlock();
         if (var5.getMaterial() == Material.air || !BlockPistonBase.canPush(var5, this.world, var7, this.moveDirection, false) || var7.equals(this.pistonPos)) {
            break;
         }

         if (++var6 + this.toMove.size() > 12) {
            return false;
         }
      }

      int var14 = 0;

      for (int var8 = var6 - 1; var8 >= 0; var8--) {
         this.toMove.add(blockPos.offset(this.moveDirection.getOpposite(), var8));
         var14++;
      }

      int var15 = 1;

      while (true) {
         BlockPos var9 = blockPos.offset(this.moveDirection, var15);
         int var10 = this.toMove.indexOf(var9);
         if (var10 > -1) {
            this.func_177255_a(var14, var10);

            for (int var11 = 0; var11 <= var10 + var14; var11++) {
               BlockPos var12 = this.toMove.get(var11);
               if (this.world.getBlockState(var12).getBlock() == Blocks.slime_block && !this.func_177250_b(var12)) {
                  return false;
               }
            }

            return true;
         }

         var5 = this.world.getBlockState(var9).getBlock();
         if (var5.getMaterial() == Material.air) {
            return true;
         }

         if (!BlockPistonBase.canPush(var5, this.world, var9, this.moveDirection, true) || var9.equals(this.pistonPos)) {
            return false;
         }

         if (var5.getMobilityFlag() == 1) {
            this.toDestroy.add(var9);
            return true;
         }

         if (this.toMove.size() >= 12) {
            return false;
         }

         this.toMove.add(var9);
         var14++;
         var15++;
      }
   }

   private void func_177255_a(int integer1, int integer2) {
      ArrayList var3 = Lists.newArrayList();
      ArrayList var4 = Lists.newArrayList();
      ArrayList var5 = Lists.newArrayList();
      var3.addAll(this.toMove.subList(0, integer2));
      var4.addAll(this.toMove.subList(this.toMove.size() - integer1, this.toMove.size()));
      var5.addAll(this.toMove.subList(integer2, this.toMove.size() - integer1));
      this.toMove.clear();
      this.toMove.addAll(var3);
      this.toMove.addAll(var4);
      this.toMove.addAll(var5);
   }

   private boolean func_177250_b(BlockPos blockPos) {
      for (EnumFacing var8 : EnumFacing.values()) {
         if (var8.getAxis() != this.moveDirection.getAxis() && !this.func_177251_a(blockPos.offset(var8))) {
            return false;
         }
      }

      return true;
   }

   public List<BlockPos> getBlocksToMove() {
      return this.toMove;
   }

   public List<BlockPos> getBlocksToDestroy() {
      return this.toDestroy;
   }
}
