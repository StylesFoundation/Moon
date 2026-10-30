package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase.NBTPrimitive {
   private byte data;

   NBTTagByte() {
   }

   public NBTTagByte(byte byte1) {
      this.data = byte1;
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
      dataOutput.writeByte(this.data);
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(72L);
      this.data = dataInput.readByte();
   }

   @Override
   public byte getId() {
      return 1;
   }

   @Override
   public String toString() {
      return this.data + "b";
   }

   @Override
   public NBTBase copy() {
      return new NBTTagByte(this.data);
   }

   @Override
   public boolean equals(Object object) {
      if (super.equals(object)) {
         NBTTagByte var5 = (NBTTagByte)object;
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
      return this.data;
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
