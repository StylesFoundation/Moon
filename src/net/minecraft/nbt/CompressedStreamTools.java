package net.minecraft.nbt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;

public class CompressedStreamTools {
   public static NBTTagCompound readCompressed(InputStream inputStream) throws IOException {
      DataInputStream var1 = new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream)));

      NBTTagCompound var2;
      try {
         var2 = read(var1, NBTSizeTracker.INFINITE);
      } finally {
         var1.close();
      }

      return var2;
   }

   public static void writeCompressed(NBTTagCompound nBTTagCompound, OutputStream outputStream) throws IOException {
      try (DataOutputStream var2 = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)))) {
         write(nBTTagCompound, var2);
      }
   }

   public static void safeWrite(NBTTagCompound nBTTagCompound, File file) throws IOException {
      File var5 = new File(file.getAbsolutePath() + "_tmp");
      if (var5.exists()) {
         var5.delete();
      }

      write(nBTTagCompound, var5);
      if (file.exists()) {
         file.delete();
      }

      if (file.exists()) {
         throw new IOException("Failed to delete " + file);
      }

      var5.renameTo(file);
   }

   public static void write(NBTTagCompound nBTTagCompound, File file) throws IOException {
      try (DataOutputStream var2 = new DataOutputStream(new FileOutputStream(file))) {
         write(nBTTagCompound, var2);
      }
   }

   public static NBTTagCompound read(File file) throws IOException {
      if (!file.exists()) {
         return null;
      }

      DataInputStream var3 = new DataInputStream(new FileInputStream(file));

      NBTTagCompound var4;
      try {
         var4 = read(var3, NBTSizeTracker.INFINITE);
      } finally {
         var3.close();
      }

      return var4;
   }

   public static NBTTagCompound read(DataInputStream dataInputStream) throws IOException {
      return read(dataInputStream, NBTSizeTracker.INFINITE);
   }

   public static NBTTagCompound read(DataInput dataInput, NBTSizeTracker nBTSizeTracker) throws IOException {
      NBTBase var5 = func_152455_a(dataInput, 0, nBTSizeTracker);
      if (var5 instanceof NBTTagCompound) {
         return (NBTTagCompound)var5;
      } else {
         throw new IOException("Root tag must be a named compound tag");
      }
   }

   public static void write(NBTTagCompound nBTTagCompound, DataOutput dataOutput) throws IOException {
      writeTag(nBTTagCompound, dataOutput);
   }

   private static void writeTag(NBTBase nBTBase, DataOutput dataOutput) throws IOException {
      dataOutput.writeByte(nBTBase.getId());
      if (nBTBase.getId() != 0) {
         dataOutput.writeUTF("");
         nBTBase.write(dataOutput);
      }
   }

   private static NBTBase func_152455_a(DataInput dataInput, int integer, NBTSizeTracker nBTSizeTracker) throws IOException {
      byte var6 = dataInput.readByte();
      if (var6 == 0) {
         return new NBTTagEnd();
      }

      dataInput.readUTF();
      NBTBase var7 = NBTBase.createNewByType(var6);

      try {
         var7.read(dataInput, integer, nBTSizeTracker);
         return var7;
      } catch (IOException var11) {
         CrashReport var9 = CrashReport.makeCrashReport(var11, "Loading NBT data");
         CrashReportCategory var10 = var9.makeCategory("NBT Tag");
         var10.addCrashSection("Tag name", "[UNNAMED TAG]");
         var10.addCrashSection("Tag type", var6);
         throw new ReportedException(var9);
      }
   }
}
