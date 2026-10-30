package net.minecraft.block;

import net.minecraft.util.BlockPos;

public class BlockEventData {
   private final BlockPos position;
   private final Block blockType;
   private final int eventID;
   private final int eventParameter;

   public BlockEventData(BlockPos blockPos, Block block, int integer3, int integer4) {
      this.position = blockPos;
      this.eventID = integer3;
      this.eventParameter = integer4;
      this.blockType = block;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public int getEventID() {
      return this.eventID;
   }

   public int getEventParameter() {
      return this.eventParameter;
   }

   public Block getBlock() {
      return this.blockType;
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof BlockEventData var5)
         ? false
         : this.position.equals(var5.position)
            && this.eventID == var5.eventID
            && this.eventParameter == var5.eventParameter
            && this.blockType == var5.blockType;
   }

   @Override
   public String toString() {
      return "TE(" + this.position + ")," + this.eventID + "," + this.eventParameter + "," + this.blockType;
   }
}
