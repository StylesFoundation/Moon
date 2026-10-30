package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase.NBTPrimitive {
   private long data;

   NBTTagLong() {
   }

   public NBTTagLong(long long1) {
      this.data = long1;
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
      dataOutput.writeLong(this.data);
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(128L);
      this.data = dataInput.readLong();
   }

   @Override
   public byte getId() {
      return 4;
   }

   @Override
   public String toString() {
      return this.data + "L";
   }

   @Override
   public NBTBase copy() {
      return new NBTTagLong(this.data);
   }

   @Override
   public boolean equals(Object object) {
      if (super.equals(object)) {
         NBTTagLong var5 = (NBTTagLong)object;
         return this.data == var5.data;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
   }

   @Override
   public long getLong() {
      return this.data;
   }

   @Override
   public int getInt() {
      return (int)this.data;
   }

   @Override
   public short getShort() {
      return (short)(this.data & 65535L);
   }

   @Override
   public byte getByte() {
      return (byte)(this.data & 255L);
   }

   @Override
   public double getDouble() {
      return this.data;
   }

   @Override
   public float getFloat() {
      return (float)this.data;
   }
}
