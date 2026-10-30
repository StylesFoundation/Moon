package com.moon.util.game;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;

public class BlockUtil {
   private final Minecraft mc = Minecraft.getMinecraft();
   private final List<Vector3d> offsets = new ArrayList<>();
   private BlockPos blockPos;
   private EnumFacing facing;

   public BlockUtil() {
      for (int x = -5; x <= 5; x++) {
         for (int y = 0; y > -3; y--) {
            for (int z = -5; z <= 5; z++) {
               this.offsets.add(new Vector3d(x, y, z));
            }
         }
      }
   }

   public void findNearestBlock(BlockPos pos, double range) {
      BlockPos nearestPos = null;
      double minDistance = 2.147483647E9;

      for (Vector3d offset : this.offsets) {
         BlockPos currentPos = pos.add(offset.x, offset.y, offset.z);
         Vec3 centerVec = new Vec3(currentPos.getX() + 0.5F, currentPos.getY() + 0.5F, currentPos.getZ() + 0.5F);
         double distance = this.mc.thePlayer.getPositionVector().distanceTo(centerVec);
         Block block = this.mc.theWorld.getBlockState(currentPos).getBlock();
         if (this.isValidBlock(block, currentPos) && distance <= range && distance < minDistance) {
            nearestPos = currentPos;
            minDistance = distance;
         }
      }

      if (nearestPos != null) {
         EnumFacing facing = EnumFacing.getFacingFromVector(pos.getX() - nearestPos.getX(), pos.getY() - nearestPos.getY(), pos.getZ() - nearestPos.getZ());
         if (facing != null) {
            this.blockPos = nearestPos;
            this.facing = facing;
            return;
         }
      }

      this.blockPos = null;
      this.facing = null;
   }

   private boolean isValidBlock(Block block, BlockPos pos) {
      if (!(block instanceof BlockContainer) && !(block instanceof BlockFalling)) {
         return block.getMaterial().blocksMovement();
      }

      if (!(block instanceof BlockSand)) {
         return false;
      }

      Block belowBlock = this.mc.theWorld.getBlockState(pos.down(1)).getBlock();
      return belowBlock.getMaterial() != Material.air && !(belowBlock instanceof BlockFalling);
   }

   public Minecraft getMinecraft() {
      return this.mc;
   }

   public List<Vector3d> getOffsets() {
      return this.offsets;
   }

   public BlockPos getBlockPos() {
      return this.blockPos;
   }

   public EnumFacing getFacing() {
      return this.facing;
   }
}
