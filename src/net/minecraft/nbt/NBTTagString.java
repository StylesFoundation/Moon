package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {
   private String data;

   public NBTTagString() {
      this.data = "";
   }

   public NBTTagString(String string) {
      this.data = string;
      if (string == null) {
         throw new IllegalArgumentException("Empty string not allowed");
      }
   }

   @Override
   void write(DataOutput dataOutput) throws IOException {
      dataOutput.writeUTF(this.data);
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(288L);
      this.data = dataInput.readUTF();
      nBTSizeTracker.read(16L * this.data.length());
   }

   @Override
   public byte getId() {
      return 8;
   }

   @Override
   public String toString() {
      return "\"" + this.data.replace("\"", "\\\"") + "\"";
   }

   @Override
   public NBTBase copy() {
      return new NBTTagString(this.data);
   }

   @Override
   public boolean hasNoTags() {
      return this.data.isEmpty();
   }

   @Override
   public boolean equals(Object object) {
      if (!super.equals(object)) {
         return false;
      }

      NBTTagString var5 = (NBTTagString)object;
      return this.data == null && var5.data == null || this.data != null && this.data.equals(var5.data);
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ this.data.hashCode();
   }

   @Override
   public String getString() {
      return this.data;
   }
}
