package net.minecraft.world.chunk.storage;

import com.google.common.collect.Lists;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import net.minecraft.server.MinecraftServer;

public class RegionFile {
   private static final byte[] emptySector = new byte[4096];
   private final File fileName;
   private RandomAccessFile dataFile;
   private final int[] offsets = new int[1024];
   private final int[] chunkTimestamps = new int[1024];
   private List<Boolean> sectorFree;
   private int sizeDelta;
   private long lastModified;

   public RegionFile(File file) {
      this.fileName = file;
      this.sizeDelta = 0;

      try {
         if (file.exists()) {
            this.lastModified = file.lastModified();
         }

         this.dataFile = new RandomAccessFile(file, "rw");
         if (this.dataFile.length() < 4096L) {
            for (int var5 = 0; var5 < 1024; var5++) {
               this.dataFile.writeInt(0);
            }

            for (int var10 = 0; var10 < 1024; var10++) {
               this.dataFile.writeInt(0);
            }

            this.sizeDelta += 8192;
         }

         if ((this.dataFile.length() & 4095L) != 0L) {
            for (int var11 = 0; var11 < (this.dataFile.length() & 4095L); var11++) {
               this.dataFile.write(0);
            }
         }

         int var12 = (int)this.dataFile.length() / 4096;
         this.sectorFree = Lists.newArrayListWithCapacity(var12);

         for (int var6 = 0; var6 < var12; var6++) {
            this.sectorFree.add(Boolean.TRUE);
         }

         this.sectorFree.set(0, Boolean.FALSE);
         this.sectorFree.set(1, Boolean.FALSE);
         this.dataFile.seek(0L);

         for (int var13 = 0; var13 < 1024; var13++) {
            int var7 = this.dataFile.readInt();
            this.offsets[var13] = var7;
            if (var7 != 0 && (var7 >> 8) + (var7 & 0xFF) <= this.sectorFree.size()) {
               for (int var8 = 0; var8 < (var7 & 0xFF); var8++) {
                  this.sectorFree.set((var7 >> 8) + var8, Boolean.FALSE);
               }
            }
         }

         for (int var14 = 0; var14 < 1024; var14++) {
            int var15 = this.dataFile.readInt();
            this.chunkTimestamps[var14] = var15;
         }
      } catch (IOException var9) {
         var9.printStackTrace();
      }
   }

   public synchronized DataInputStream getChunkDataInputStream(int integer1, int integer2) {
      if (this.outOfBounds(integer1, integer2)) {
         return null;
      }

      try {
         int var6 = this.getOffset(integer1, integer2);
         if (var6 == 0) {
            return null;
         } else {
            int var7 = var6 >> 8;
            int var8 = var6 & 0xFF;
            if (var7 + var8 > this.sectorFree.size()) {
               return null;
            } else {
               this.dataFile.seek(var7 * 4096L);
               int var9 = this.dataFile.readInt();
               if (var9 > 4096 * var8) {
                  return null;
               } else if (var9 <= 0) {
                  return null;
               } else {
                  byte var10 = this.dataFile.readByte();
                  if (var10 == 1) {
                     byte[] var13 = new byte[var9 - 1];
                     this.dataFile.read(var13);
                     return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(var13))));
                  } else if (var10 == 2) {
                     byte[] var11 = new byte[var9 - 1];
                     this.dataFile.read(var11);
                     return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(var11))));
                  } else {
                     return null;
                  }
               }
            }
         }
      } catch (IOException var12) {
         return null;
      }
   }

   public DataOutputStream getChunkDataOutputStream(int integer1, int integer2) {
      return this.outOfBounds(integer1, integer2) ? null : new DataOutputStream(new DeflaterOutputStream(new RegionFile.ChunkBuffer(integer1, integer2)));
   }

   protected synchronized void write(int integer1, int integer2, byte[] arr, int integer4) {
      try {
         int var8 = this.getOffset(integer1, integer2);
         int var9 = var8 >> 8;
         int var10 = var8 & 0xFF;
         int var11 = (integer4 + 5) / 4096 + 1;
         if (var11 >= 256) {
            return;
         }

         if (var9 != 0 && var10 == var11) {
            this.write(var9, arr, integer4);
         } else {
            for (int var12 = 0; var12 < var10; var12++) {
               this.sectorFree.set(var9 + var12, Boolean.TRUE);
            }

            int var18 = this.sectorFree.indexOf(Boolean.TRUE);
            int var13 = 0;
            if (var18 != -1) {
               for (int var14 = var18; var14 < this.sectorFree.size(); var14++) {
                  if (var13 != 0) {
                     if (this.sectorFree.get(var14)) {
                        var13++;
                     } else {
                        var13 = 0;
                     }
                  } else if (this.sectorFree.get(var14)) {
                     var18 = var14;
                     var13 = 1;
                  }

                  if (var13 >= var11) {
                     break;
                  }
               }
            }

            if (var13 >= var11) {
               var9 = var18;
               this.setOffset(integer1, integer2, var18 << 8 | var11);

               for (int var20 = 0; var20 < var11; var20++) {
                  this.sectorFree.set(var9 + var20, Boolean.FALSE);
               }

               this.write(var9, arr, integer4);
            } else {
               this.dataFile.seek(this.dataFile.length());
               var9 = this.sectorFree.size();

               for (int var19 = 0; var19 < var11; var19++) {
                  this.dataFile.write(emptySector);
                  this.sectorFree.add(Boolean.FALSE);
               }

               this.sizeDelta += 4096 * var11;
               this.write(var9, arr, integer4);
               this.setOffset(integer1, integer2, var9 << 8 | var11);
            }
         }

         this.setChunkTimestamp(integer1, integer2, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
      } catch (IOException var15) {
         var15.printStackTrace();
      }
   }

   private void write(int integer1, byte[] arr, int integer3) throws IOException {
      this.dataFile.seek(integer1 * 4096L);
      this.dataFile.writeInt(integer3 + 1);
      this.dataFile.writeByte(2);
      this.dataFile.write(arr, 0, integer3);
   }

   private boolean outOfBounds(int integer1, int integer2) {
      return integer1 < 0 || integer1 >= 32 || integer2 < 0 || integer2 >= 32;
   }

   private int getOffset(int integer1, int integer2) {
      return this.offsets[integer1 + integer2 * 32];
   }

   public boolean isChunkSaved(int integer1, int integer2) {
      return this.getOffset(integer1, integer2) != 0;
   }

   private void setOffset(int integer1, int integer2, int integer3) throws IOException {
      this.offsets[integer1 + integer2 * 32] = integer3;
      this.dataFile.seek((integer1 + integer2 * 32L) * 4L);
      this.dataFile.writeInt(integer3);
   }

   private void setChunkTimestamp(int integer1, int integer2, int integer3) throws IOException {
      this.chunkTimestamps[integer1 + integer2 * 32] = integer3;
      this.dataFile.seek(4096L + (integer1 + integer2 * 32L) * 4L);
      this.dataFile.writeInt(integer3);
   }

   public void close() throws IOException {
      if (this.dataFile != null) {
         this.dataFile.close();
      }
   }

   class ChunkBuffer extends ByteArrayOutputStream {
      private final int chunkX;
      private final int chunkZ;

      public ChunkBuffer(int integer2, int integer3) {
         super(8096);
         this.chunkX = integer2;
         this.chunkZ = integer3;
      }

      @Override
      public void close() throws IOException {
         RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
      }
   }
}
