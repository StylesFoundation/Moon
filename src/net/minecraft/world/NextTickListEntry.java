package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

public class NextTickListEntry implements Comparable<NextTickListEntry> {
   private static long nextTickEntryID;
   private final Block block;
   public final BlockPos position;
   public long scheduledTime;
   public int priority;
   private final long tickEntryID;

   public NextTickListEntry(BlockPos blockPos, Block block) {
      this.tickEntryID = nextTickEntryID++;
      this.position = blockPos;
      this.block = block;
   }

   @Override
   public boolean equals(Object object) {
      return !(object instanceof NextTickListEntry var5) ? false : this.position.equals(var5.position) && Block.isEqualTo(this.block, var5.block);
   }

   @Override
   public int hashCode() {
      return this.position.hashCode();
   }

   public NextTickListEntry setScheduledTime(long long1) {
      this.scheduledTime = long1;
      return this;
   }

   public void setPriority(int integer) {
      this.priority = integer;
   }

   public int compareTo(NextTickListEntry nextTickListEntry) {
      return this.scheduledTime < nextTickListEntry.scheduledTime
         ? -1
         : (
            this.scheduledTime > nextTickListEntry.scheduledTime
               ? 1
               : (
                  this.priority != nextTickListEntry.priority
                     ? this.priority - nextTickListEntry.priority
                     : Long.compare(this.tickEntryID, nextTickListEntry.tickEntryID)
               )
         );
   }

   @Override
   public String toString() {
      return Block.getIdFromBlock(this.block) + ": " + this.position + ", " + this.scheduledTime + ", " + this.priority + ", " + this.tickEntryID;
   }

   public Block getBlock() {
      return this.block;
   }
}
