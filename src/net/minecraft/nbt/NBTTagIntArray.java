package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
   private int[] intArray;

   NBTTagIntArray() {
   }

   public NBTTagIntArray(int[] arr) {
      this.intArray = arr;
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
      dataOutput.writeInt(this.intArray.length);

      for (int var8 : this.intArray) {
         dataOutput.writeInt(var8);
      }
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(192L);
      int var7 = dataInput.readInt();
      nBTSizeTracker.read(32L * var7);
      this.intArray = new int[var7];

      for (int var8 = 0; var8 < var7; var8++) {
         this.intArray[var8] = dataInput.readInt();
      }
   }

   @Override
   public byte getId() {
      return 11;
   }

   @Override
   public String toString() {
      StringBuilder var4 = new StringBuilder("[");

      for (int var8 : this.intArray) {
         var4.append(var8).append(",");
      }

      return var4 + "]";
   }

   @Override
   public NBTBase copy() {
      int[] var1 = new int[this.intArray.length];
      System.arraycopy(this.intArray, 0, var1, 0, this.intArray.length);
      return new NBTTagIntArray(var1);
   }

   @Override
   public boolean equals(Object object) {
      return super.equals(object) && Arrays.equals(this.intArray, ((NBTTagIntArray)object).intArray);
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.intArray);
   }

   public int[] getIntArray() {
      return this.intArray;
   }
}
