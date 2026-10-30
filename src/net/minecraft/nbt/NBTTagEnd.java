package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd extends NBTBase {
   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(64L);
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
   }

   @Override
   public byte getId() {
      return 0;
   }

   @Override
   public String toString() {
      return "END";
   }

   @Override
   public NBTBase copy() {
      return new NBTTagEnd();
   }
}
