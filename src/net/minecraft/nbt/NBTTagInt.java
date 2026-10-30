package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase.NBTPrimitive {
   private int data;

   NBTTagInt() {
   }

   public NBTTagInt(int integer) {
      this.data = integer;
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
      dataOutput.writeInt(this.data);
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(96L);
      this.data = dataInput.readInt();
   }

   @Override
   public byte getId() {
      return 3;
   }

   @Override
   public String toString() {
      return this.data + "";
   }

   @Override
   public NBTBase copy() {
      return new NBTTagInt(this.data);
   }

   @Override
   public boolean equals(Object object) {
      if (super.equals(object)) {
         NBTTagInt var5 = (NBTTagInt)object;
         return this.data == var5.data;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ this.data;
   }

   @Override
   public long getLong() {
      return this.data;
   }

   @Override
   public int getInt() {
      return this.data;
   }

   @Override
   public short getShort() {
      return (short)(this.data & 65535);
   }

   @Override
   public byte getByte() {
      return (byte)(this.data & 0xFF);
   }

   @Override
   public double getDouble() {
      return this.data;
   }

   @Override
   public float getFloat() {
      return this.data;
   }
}
