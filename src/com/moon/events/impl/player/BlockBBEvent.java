package com.moon.events.impl.player;

import com.moon.events.Event;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class BlockBBEvent extends Event {
   private Block block;
   private BlockPos.MutableBlockPos blockPos;
   private List<AxisAlignedBB> boundingBox;

   public Block getBlock() {
      return this.block;
   }

   public BlockPos.MutableBlockPos getBlockPos() {
      return this.blockPos;
   }

   public List<AxisAlignedBB> getBoundingBox() {
      return this.boundingBox;
   }

   public BlockBBEvent setBlock(Block block) {
      this.block = block;
      return this;
   }

   public BlockBBEvent setBlockPos(BlockPos.MutableBlockPos blockPos) {
      this.blockPos = blockPos;
      return this;
   }

   public BlockBBEvent setBoundingBox(List<AxisAlignedBB> boundingBox) {
      this.boundingBox = boundingBox;
      return this;
   }

   public BlockBBEvent(Block block, BlockPos.MutableBlockPos blockPos, List<AxisAlignedBB> boundingBox) {
      this.block = block;
      this.blockPos = blockPos;
      this.boundingBox = boundingBox;
   }
}
