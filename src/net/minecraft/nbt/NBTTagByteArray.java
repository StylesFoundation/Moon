package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {
   private byte[] data;

   NBTTagByteArray() {
   }

   public NBTTagByteArray(byte[] arr) {
      this.data = arr;
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
      dataOutput.writeInt(this.data.length);
      dataOutput.write(this.data);
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(192L);
      int var6 = dataInput.readInt();
      nBTSizeTracker.read(8L * var6);
      this.data = new byte[var6];
      dataInput.readFully(this.data);
   }

   @Override
   public byte getId() {
      return 7;
   }

   @Override
   public String toString() {
      return "[" + this.data.length + " bytes]";
   }

   @Override
   public NBTBase copy() {
      byte[] var1 = new byte[this.data.length];
      System.arraycopy(this.data, 0, var1, 0, this.data.length);
      return new NBTTagByteArray(var1);
   }

   @Override
   public boolean equals(Object object) {
      return super.equals(object) && Arrays.equals(this.data, ((NBTTagByteArray)object).data);
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.data);
   }

   public byte[] getByteArray() {
      return this.data;
   }
}
