package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ChunkPrimer {
   private final short[] data = new short[65536];
   private final IBlockState defaultState = Blocks.air.getDefaultState();

   public IBlockState getBlockState(int integer1, int integer2, int integer3) {
      int var6 = integer1 << 12 | integer3 << 8 | integer2;
      return this.getBlockState(var6);
   }

   public IBlockState getBlockState(int integer) {
      if (integer >= 0 && integer < this.data.length) {
         IBlockState var5 = Block.BLOCK_STATE_IDS.getByValue(this.data[integer]);
         return var5 != null ? var5 : this.defaultState;
      } else {
         throw new IndexOutOfBoundsException("The coordinate is out of range");
      }
   }

   public void setBlockState(int integer1, int integer2, int integer3, IBlockState iBlockState) {
      int var7 = integer1 << 12 | integer3 << 8 | integer2;
      this.setBlockState(var7, iBlockState);
   }

   public void setBlockState(int integer, IBlockState iBlockState) {
      if (integer >= 0 && integer < this.data.length) {
         this.data[integer] = (short)Block.BLOCK_STATE_IDS.get(iBlockState);
      } else {
         throw new IndexOutOfBoundsException("The coordinate is out of range");
      }
   }
}
