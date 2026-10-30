package net.minecraft.world.chunk.storage;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.NibbleArray;

public class ExtendedBlockStorage {
   private final int yBase;
   private int blockRefCount;
   private int tickRefCount;
   private char[] data;
   private NibbleArray blocklightArray;
   private NibbleArray skylightArray;

   public ExtendedBlockStorage(int integer, boolean boolean2) {
      this.yBase = integer;
      this.data = new char[4096];
      this.blocklightArray = new NibbleArray();
      if (boolean2) {
         this.skylightArray = new NibbleArray();
      }
   }

   public IBlockState get(int integer1, int integer2, int integer3) {
      IBlockState var7 = Block.BLOCK_STATE_IDS.getByValue(this.data[integer2 << 8 | integer3 << 4 | integer1]);
      return var7 != null ? var7 : Blocks.air.getDefaultState();
   }

   public void set(int integer1, int integer2, int integer3, IBlockState iBlockState) {
      IBlockState var8 = this.get(integer1, integer2, integer3);
      Block var9 = var8.getBlock();
      Block var10 = iBlockState.getBlock();
      if (var9 != Blocks.air) {
         this.blockRefCount--;
         if (var9.getTickRandomly()) {
            this.tickRefCount--;
         }
      }

      if (var10 != Blocks.air) {
         this.blockRefCount++;
         if (var10.getTickRandomly()) {
            this.tickRefCount++;
         }
      }

      this.data[integer2 << 8 | integer3 << 4 | integer1] = (char)Block.BLOCK_STATE_IDS.get(iBlockState);
   }

   public Block getBlockByExtId(int integer1, int integer2, int integer3) {
      return this.get(integer1, integer2, integer3).getBlock();
   }

   public int getExtBlockMetadata(int integer1, int integer2, int integer3) {
      IBlockState var4 = this.get(integer1, integer2, integer3);
      return var4.getBlock().getMetaFromState(var4);
   }

   public boolean isEmpty() {
      return this.blockRefCount == 0;
   }

   public boolean getNeedsRandomTick() {
      return this.tickRefCount > 0;
   }

   public int getYLocation() {
      return this.yBase;
   }

   public void setExtSkylightValue(int integer1, int integer2, int integer3, int integer4) {
      this.skylightArray.set(integer1, integer2, integer3, integer4);
   }

   public int getExtSkylightValue(int integer1, int integer2, int integer3) {
      return this.skylightArray.get(integer1, integer2, integer3);
   }

   public void setExtBlocklightValue(int integer1, int integer2, int integer3, int integer4) {
      this.blocklightArray.set(integer1, integer2, integer3, integer4);
   }

   public int getExtBlocklightValue(int integer1, int integer2, int integer3) {
      return this.blocklightArray.get(integer1, integer2, integer3);
   }

   public void removeInvalidBlocks() {
      Blocks.air.getDefaultState();
      int var5 = 0;
      int var6 = 0;

      for (int var7 = 0; var7 < 16; var7++) {
         for (int var8 = 0; var8 < 16; var8++) {
            for (int var9 = 0; var9 < 16; var9++) {
               Block var10 = this.getBlockByExtId(var9, var7, var8);
               if (var10 != Blocks.air) {
                  var5++;
                  if (var10.getTickRandomly()) {
                     var6++;
                  }
               }
            }
         }
      }

      this.blockRefCount = var5;
      this.tickRefCount = var6;
   }

   public char[] getData() {
      return this.data;
   }

   public void setData(char[] arr) {
      this.data = arr;
   }

   public NibbleArray getBlocklightArray() {
      return this.blocklightArray;
   }

   public NibbleArray getSkylightArray() {
      return this.skylightArray;
   }

   public void setBlocklightArray(NibbleArray nibbleArray) {
      this.blocklightArray = nibbleArray;
   }

   public void setSkylightArray(NibbleArray nibbleArray) {
      this.skylightArray = nibbleArray;
   }

   public int getBlockRefCount() {
      return this.blockRefCount;
   }
}
