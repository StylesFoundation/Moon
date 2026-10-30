package net.minecraft.nbt;

import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagList extends NBTBase {
   private static final Logger LOGGER = LogManager.getLogger();
   private List<NBTBase> tagList = Lists.newArrayList();
   private byte tagType = 0;

   @Override
   void write(DataOutput dataOutput) throws IOException {
      if (!this.tagList.isEmpty()) {
         this.tagType = this.tagList.get(0).getId();
      } else {
         this.tagType = 0;
      }

      dataOutput.writeByte(this.tagType);
      dataOutput.writeInt(this.tagList.size());
      Iterator var5 = this.tagList.iterator();

      while (var5.hasNext()) {
         ((NBTBase)var5.next()).write(dataOutput);
      }
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(296L);
      if (integer > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      }

      this.tagType = dataInput.readByte();
      int var7 = dataInput.readInt();
      if (this.tagType == 0 && var7 > 0) {
         throw new RuntimeException("Missing type on ListTag");
      }

      nBTSizeTracker.read(32L * var7);
      this.tagList = Lists.newArrayListWithCapacity(var7);

      for (int var8 = 0; var8 < var7; var8++) {
         NBTBase var9 = NBTBase.createNewByType(this.tagType);
         var9.read(dataInput, integer + 1, nBTSizeTracker);
         this.tagList.add(var9);
      }
   }

   @Override
   public byte getId() {
      return 9;
   }

   @Override
   public String toString() {
      StringBuilder var4 = new StringBuilder("[");

      for (int var5 = 0; var5 < this.tagList.size(); var5++) {
         if (var5 != 0) {
            var4.append(',');
         }

         var4.append(var5).append(':').append(this.tagList.get(var5));
      }

      return var4.append(']').toString();
   }

   public void appendTag(NBTBase nBTBase) {
      if (nBTBase.getId() == 0) {
         LOGGER.warn("Invalid TagEnd added to ListTag");
      } else {
         if (this.tagType == 0) {
            this.tagType = nBTBase.getId();
         } else if (this.tagType != nBTBase.getId()) {
            LOGGER.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.tagList.add(nBTBase);
      }
   }

   public void set(int integer, NBTBase nBTBase) {
      if (nBTBase.getId() == 0) {
         LOGGER.warn("Invalid TagEnd added to ListTag");
      } else if (integer >= 0 && integer < this.tagList.size()) {
         if (this.tagType == 0) {
            this.tagType = nBTBase.getId();
         } else if (this.tagType != nBTBase.getId()) {
            LOGGER.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.tagList.set(integer, nBTBase);
      } else {
         LOGGER.warn("index out of bounds to set tag in tag list");
      }
   }

   public NBTBase removeTag(int integer) {
      return this.tagList.remove(integer);
   }

   @Override
   public boolean hasNoTags() {
      return this.tagList.isEmpty();
   }

   public NBTTagCompound getCompoundTagAt(int integer) {
      if (integer >= 0 && integer < this.tagList.size()) {
         NBTBase var5 = this.tagList.get(integer);
         return var5.getId() == 10 ? (NBTTagCompound)var5 : new NBTTagCompound();
      } else {
         return new NBTTagCompound();
      }
   }

   public int[] getIntArrayAt(int integer) {
      if (integer >= 0 && integer < this.tagList.size()) {
         NBTBase var5 = this.tagList.get(integer);
         return var5.getId() == 11 ? ((NBTTagIntArray)var5).getIntArray() : new int[0];
      } else {
         return new int[0];
      }
   }

   public double getDoubleAt(int integer) {
      if (integer >= 0 && integer < this.tagList.size()) {
         NBTBase var5 = this.tagList.get(integer);
         return var5.getId() == 6 ? ((NBTTagDouble)var5).getDouble() : 0.0;
      } else {
         return 0.0;
      }
   }

   public float getFloatAt(int integer) {
      if (integer >= 0 && integer < this.tagList.size()) {
         NBTBase var5 = this.tagList.get(integer);
         return var5.getId() == 5 ? ((NBTTagFloat)var5).getFloat() : 0.0F;
      } else {
         return 0.0F;
      }
   }

   public String getStringTagAt(int integer) {
      if (integer >= 0 && integer < this.tagList.size()) {
         NBTBase var5 = this.tagList.get(integer);
         return var5.getId() == 8 ? var5.getString() : var5.toString();
      } else {
         return "";
      }
   }

   public NBTBase get(int integer) {
      return integer >= 0 && integer < this.tagList.size() ? this.tagList.get(integer) : new NBTTagEnd();
   }

   public int tagCount() {
      return this.tagList.size();
   }

   @Override
   public NBTBase copy() {
      NBTTagList var4 = new NBTTagList();
      var4.tagType = this.tagType;
      Iterator var5 = this.tagList.iterator();

      while (var5.hasNext()) {
         NBTBase var7 = ((NBTBase)var5.next()).copy();
         var4.tagList.add(var7);
      }

      return var4;
   }

   @Override
   public boolean equals(Object object) {
      if (super.equals(object)) {
         NBTTagList var5 = (NBTTagList)object;
         if (this.tagType == var5.tagType) {
            return this.tagList.equals(var5.tagList);
         }
      }

      return false;
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ this.tagList.hashCode();
   }

   public int getTagType() {
      return this.tagType;
   }
}
