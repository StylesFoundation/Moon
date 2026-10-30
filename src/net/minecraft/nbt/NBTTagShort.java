package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase.NBTPrimitive {
   private short data;

   public NBTTagShort() {
   }

   public NBTTagShort(short short1) {
      this.data = short1;
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
      dataOutput.writeShort(this.data);
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(80L);
      this.data = dataInput.readShort();
   }

   @Override
   public byte getId() {
      return 2;
   }

   @Override
   public String toString() {
      return this.data + "s";
   }

   @Override
   public NBTBase copy() {
      return new NBTTagShort(this.data);
   }

   @Override
   public boolean equals(Object object) {
      if (super.equals(object)) {
         NBTTagShort var5 = (NBTTagShort)object;
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
      return this.data;
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
