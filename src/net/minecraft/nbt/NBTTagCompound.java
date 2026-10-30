package net.minecraft.nbt;

import com.google.common.collect.Maps;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

public class NBTTagCompound extends NBTBase {
   private final Map<String, NBTBase> tagMap = Maps.newHashMap();

   @Override
   void write(DataOutput dataOutput) throws IOException {
      for (String var6 : this.tagMap.keySet()) {
         NBTBase var7 = this.tagMap.get(var6);
         writeEntry(var6, var7, dataOutput);
      }

      dataOutput.writeByte(0);
   }

   @Override
   void read(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      nBTSizeTracker.read(384L);
      if (integer > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      }

      this.tagMap.clear();

      byte var7;
      while ((var7 = readType(dataInput, nBTSizeTracker)) != 0) {
         String var8 = readKey(dataInput, nBTSizeTracker);
         nBTSizeTracker.read(224L + 16L * var8.length());
         NBTBase var9 = readNBT(var7, var8, dataInput, integer + 1, nBTSizeTracker);
         if (this.tagMap.put(var8, var9) != null) {
            nBTSizeTracker.read(288L);
         }
      }
   }

   public Set<String> getKeySet() {
      return this.tagMap.keySet();
   }

   @Override
   public byte getId() {
      return 10;
   }

   public void setTag(String string, NBTBase nBTBase) {
      this.tagMap.put(string, nBTBase);
   }

   public void setByte(String string, byte byte2) {
      this.tagMap.put(string, new NBTTagByte(byte2));
   }

   public void setShort(String string, short short2) {
      this.tagMap.put(string, new NBTTagShort(short2));
   }

   public void setInteger(String string, int integer) {
      this.tagMap.put(string, new NBTTagInt(integer));
   }

   public void setLong(String string, long long2) {
      this.tagMap.put(string, new NBTTagLong(long2));
   }

   public void setFloat(String string, float float2) {
      this.tagMap.put(string, new NBTTagFloat(float2));
   }

   public void setDouble(String string, double double2) {
      this.tagMap.put(string, new NBTTagDouble(double2));
   }

   public void setString(String string1, String string2) {
      this.tagMap.put(string1, new NBTTagString(string2));
   }

   public void setByteArray(String string, byte[] arr) {
      this.tagMap.put(string, new NBTTagByteArray(arr));
   }

   public void setIntArray(String string, int[] arr) {
      this.tagMap.put(string, new NBTTagIntArray(arr));
   }

   public void setBoolean(String string, boolean boolean2) {
      this.setByte(string, (byte)(boolean2 ? 1 : 0));
   }

   public NBTBase getTag(String string) {
      return this.tagMap.get(string);
   }

   public byte getTagId(String string) {
      NBTBase var5 = this.tagMap.get(string);
      return var5 != null ? var5.getId() : 0;
   }

   public boolean hasKey(String string) {
      return this.tagMap.containsKey(string);
   }

   public boolean hasKey(String string, int integer) {
      byte var6 = this.getTagId(string);
      if (var6 == integer) {
         return true;
      }

      if (integer != 99) {
         if (var6 > 0) {
         }

         return false;
      } else {
         return var6 == 1 || var6 == 2 || var6 == 3 || var6 == 4 || var6 == 5 || var6 == 6;
      }
   }

   public byte getByte(String string) {
      try {
         return !this.hasKey(string, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(string)).getByte();
      } catch (ClassCastException var2) {
         return 0;
      }
   }

   public short getShort(String string) {
      try {
         return !this.hasKey(string, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(string)).getShort();
      } catch (ClassCastException var2) {
         return 0;
      }
   }

   public int getInteger(String string) {
      try {
         return !this.hasKey(string, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(string)).getInt();
      } catch (ClassCastException var2) {
         return 0;
      }
   }

   public long getLong(String string) {
      try {
         return !this.hasKey(string, 99) ? 0L : ((NBTBase.NBTPrimitive)this.tagMap.get(string)).getLong();
      } catch (ClassCastException var2) {
         return 0L;
      }
   }

   public float getFloat(String string) {
      try {
         return !this.hasKey(string, 99) ? 0.0F : ((NBTBase.NBTPrimitive)this.tagMap.get(string)).getFloat();
      } catch (ClassCastException var2) {
         return 0.0F;
      }
   }

   public double getDouble(String string) {
      try {
         return !this.hasKey(string, 99) ? 0.0 : ((NBTBase.NBTPrimitive)this.tagMap.get(string)).getDouble();
      } catch (ClassCastException var2) {
         return 0.0;
      }
   }

   public String getString(String string) {
      try {
         return !this.hasKey(string, 8) ? "" : this.tagMap.get(string).getString();
      } catch (ClassCastException var2) {
         return "";
      }
   }

   public byte[] getByteArray(String string) {
      try {
         return !this.hasKey(string, 7) ? new byte[0] : ((NBTTagByteArray)this.tagMap.get(string)).getByteArray();
      } catch (ClassCastException var6) {
         throw new ReportedException(this.createCrashReport(string, 7, var6));
      }
   }

   public int[] getIntArray(String string) {
      try {
         return !this.hasKey(string, 11) ? new int[0] : ((NBTTagIntArray)this.tagMap.get(string)).getIntArray();
      } catch (ClassCastException var6) {
         throw new ReportedException(this.createCrashReport(string, 11, var6));
      }
   }

   public NBTTagCompound getCompoundTag(String string) {
      try {
         return !this.hasKey(string, 10) ? new NBTTagCompound() : (NBTTagCompound)this.tagMap.get(string);
      } catch (ClassCastException var6) {
         throw new ReportedException(this.createCrashReport(string, 10, var6));
      }
   }

   public NBTTagList getTagList(String string, int integer) {
      try {
         if (this.getTagId(string) != 9) {
            return new NBTTagList();
         }

         NBTTagList var6 = (NBTTagList)this.tagMap.get(string);
         return var6.tagCount() > 0 && var6.getTagType() != integer ? new NBTTagList() : var6;
      } catch (ClassCastException var7) {
         throw new ReportedException(this.createCrashReport(string, 9, var7));
      }
   }

   public boolean getBoolean(String string) {
      return this.getByte(string) != 0;
   }

   public void removeTag(String string) {
      this.tagMap.remove(string);
   }

   @Override
   public String toString() {
      StringBuilder var4 = new StringBuilder("{");

      for (Entry var6 : this.tagMap.entrySet()) {
         if (var4.length() != 1) {
            var4.append(',');
         }

         var4.append((String)var6.getKey()).append(':').append(var6.getValue());
      }

      return var4.append('}').toString();
   }

   @Override
   public boolean hasNoTags() {
      return this.tagMap.isEmpty();
   }

   private CrashReport createCrashReport(String string, int integer, ClassCastException classCastException) {
      CrashReport var4 = CrashReport.makeCrashReport(classCastException, "Reading NBT data");
      CrashReportCategory var5 = var4.makeCategoryDepth("Corrupt NBT tag", 1);
      var5.addCrashSectionCallable("Tag type found", () -> NBTBase.NBT_TYPES[this.tagMap.get(string).getId()]);
      var5.addCrashSectionCallable("Tag type expected", () -> NBTBase.NBT_TYPES[integer]);
      var5.addCrashSection("Tag name", string);
      return var4;
   }

   @Override
   public NBTBase copy() {
      NBTTagCompound var3 = new NBTTagCompound();

      for (String var5 : this.tagMap.keySet()) {
         var3.setTag(var5, this.tagMap.get(var5).copy());
      }

      return var3;
   }

   @Override
   public boolean equals(Object object) {
      if (super.equals(object)) {
         NBTTagCompound var5 = (NBTTagCompound)object;
         return this.tagMap.entrySet().equals(var5.tagMap.entrySet());
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return super.hashCode() ^ this.tagMap.hashCode();
   }

   private static void writeEntry(String string, NBTBase nBTBase, DataOutput dataOutput) throws IOException {
      dataOutput.writeByte(nBTBase.getId());
      if (nBTBase.getId() != 0) {
         dataOutput.writeUTF(string);
         nBTBase.write(dataOutput);
      }
   }

   private static byte readType(DataInput dataInput, NBTSizeTracker nBTSizeTracker) throws IOException {
      return dataInput.readByte();
   }

   private static String readKey(DataInput dataInput, NBTSizeTracker nBTSizeTracker) throws IOException {
      return dataInput.readUTF();
   }

   static NBTBase readNBT(byte byte1, String string, DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      NBTBase var5 = NBTBase.createNewByType(byte1);

      try {
         var5.read(dataInput, integer, nBTSizeTracker);
         return var5;
      } catch (IOException var9) {
         CrashReport var7 = CrashReport.makeCrashReport(var9, "Loading NBT data");
         CrashReportCategory var8 = var7.makeCategory("NBT Tag");
         var8.addCrashSection("Tag name", string);
         var8.addCrashSection("Tag type", byte1);
         throw new ReportedException(var7);
      }
   }

   public void merge(NBTTagCompound nBTTagCompound) {
      for (String var6 : nBTTagCompound.tagMap.keySet()) {
         NBTBase var7 = nBTTagCompound.tagMap.get(var6);
         if (var7.getId() == 10) {
            if (this.hasKey(var6, 10)) {
               NBTTagCompound var8 = this.getCompoundTag(var6);
               var8.merge((NBTTagCompound)var7);
            } else {
               this.setTag(var6, var7.copy());
            }
         } else {
            this.setTag(var6, var7.copy());
         }
      }
   }
}
