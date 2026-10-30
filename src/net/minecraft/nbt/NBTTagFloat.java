package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.MathHelper;

public class NBTTagFloat extends NBTBase.NBTPrimitive {
   private float data;

   NBTTagFloat() {
   }

   public NBTTagFloat(float float1) {
      this.data = float1;
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
      dataOutput.writeFloat(this.data);
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(96L);
      this.data = dataInput.readFloat();
   }

   @Override
   public byte getId() {
      return 5;
   }

   @Override
   public String toString() {
      return this.data + "f";
   }

   @Override
   public NBTBase copy() {
      return new NBTTagFloat(this.data);
   }

   @Override
   public boolean equals(Object object) {
      if (super.equals(object)) {
         NBTTagFloat var5 = (NBTTagFloat)object;
         return this.data == var5.data;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ Float.floatToIntBits(this.data);
   }

   @Override
   public long getLong() {
      return (long)this.data;
   }

   @Override
   public int getInt() {
      return MathHelper.floor_float(this.data);
   }

   @Override
   public short getShort() {
      return (short)(MathHelper.floor_float(this.data) & 65535);
   }

   @Override
   public byte getByte() {
      return (byte)(MathHelper.floor_float(this.data) & 0xFF);
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
