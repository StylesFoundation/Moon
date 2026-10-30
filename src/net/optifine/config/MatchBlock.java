package net.optifine.config;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.src.Config;

public class MatchBlock {
   private int blockId = -1;
   private int[] metadatas = null;

   public MatchBlock(int integer) {
      this.blockId = integer;
   }

   public MatchBlock(int integer1, int integer2) {
      this.blockId = integer1;
      if (integer2 >= 0 && integer2 <= 15) {
         this.metadatas = new int[]{integer2};
      }
   }

   public MatchBlock(int integer, int[] arr) {
      this.blockId = integer;
      this.metadatas = arr;
   }

   public int getBlockId() {
      return this.blockId;
   }

   public int[] getMetadatas() {
      return this.metadatas;
   }

   public boolean matches(BlockStateBase blockStateBase) {
      return blockStateBase.getBlockId() == this.blockId && Matches.metadata(blockStateBase.getMetadata(), this.metadatas);
   }

   public boolean matches(int integer1, int integer2) {
      return integer1 == this.blockId && Matches.metadata(integer2, this.metadatas);
   }

   public void addMetadata(int integer) {
      if (this.metadatas != null && integer >= 0 && integer <= 15) {
         for (int var8 : this.metadatas) {
            if (var8 == integer) {
               return;
            }
         }

         this.metadatas = Config.addIntToArray(this.metadatas, integer);
      }
   }

   @Override
   public String toString() {
      return this.blockId + ":" + Config.arrayToString(this.metadatas);
   }
}
