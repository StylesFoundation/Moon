package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class S21PacketChunkData implements Packet<INetHandlerPlayClient> {
   private int chunkX;
   private int chunkZ;
   private S21PacketChunkData.Extracted extractedData;
   private boolean field_149279_g;

   public S21PacketChunkData() {
   }

   public S21PacketChunkData(Chunk chunk, boolean boolean2, int integer) {
      this.chunkX = chunk.xPosition;
      this.chunkZ = chunk.zPosition;
      this.field_149279_g = boolean2;
      this.extractedData = getExtractedData(chunk, boolean2, !chunk.getWorld().provider.getHasNoSky(), integer);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.chunkX = buffer.readInt();
      this.chunkZ = buffer.readInt();
      this.field_149279_g = buffer.readBoolean();
      this.extractedData = new S21PacketChunkData.Extracted();
      this.extractedData.dataSize = buffer.readShort();
      this.extractedData.data = buffer.readByteArray();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeInt(this.chunkX);
      buffer.writeInt(this.chunkZ);
      buffer.writeBoolean(this.field_149279_g);
      buffer.writeShort((short)(this.extractedData.dataSize & 65535));
      buffer.writeByteArray(this.extractedData.data);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleChunkData(this);
   }

   public byte[] getExtractedDataBytes() {
      return this.extractedData.data;
   }

   protected static int func_180737_a(int integer, boolean boolean2, boolean boolean3) {
      int var6 = integer * 2 * 16 * 16 * 16;
      int var7 = integer * 16 * 16 * 16 / 2;
      int var8 = boolean2 ? integer * 16 * 16 * 16 / 2 : 0;
      int var9 = boolean3 ? 256 : 0;
      return var6 + var7 + var8 + var9;
   }

   public static S21PacketChunkData.Extracted getExtractedData(Chunk chunk, boolean boolean2, boolean boolean3, int integer) {
      ExtendedBlockStorage[] var7 = chunk.getBlockStorageArray();
      S21PacketChunkData.Extracted var8 = new S21PacketChunkData.Extracted();
      ArrayList var9 = Lists.newArrayList();

      for (int var10 = 0; var10 < var7.length; var10++) {
         ExtendedBlockStorage var11 = var7[var10];
         if (var11 != null && (!boolean2 || !var11.isEmpty()) && (integer & 1 << var10) != 0) {
            var8.dataSize |= 1 << var10;
            var9.add(var11);
         }
      }

      var8.data = new byte[func_180737_a(Integer.bitCount(var8.dataSize), boolean3, boolean2)];
      int var18 = 0;
      Iterator var20 = var9.iterator();

      while (var20.hasNext()) {
         for (char var17 : ((ExtendedBlockStorage)var20.next()).getData()) {
            var8.data[var18++] = (byte)(var17 & 0xFF);
            var8.data[var18++] = (byte)(var17 >> '\b' & 0xFF);
         }
      }

      var20 = var9.iterator();

      while (var20.hasNext()) {
         var18 = func_179757_a(((ExtendedBlockStorage)var20.next()).getBlocklightArray().getData(), var8.data, var18);
      }

      if (boolean3) {
         var20 = var9.iterator();

         while (var20.hasNext()) {
            var18 = func_179757_a(((ExtendedBlockStorage)var20.next()).getSkylightArray().getData(), var8.data, var18);
         }
      }

      if (boolean2) {
         func_179757_a(chunk.getBiomeArray(), var8.data, var18);
      }

      return var8;
   }

   private static int func_179757_a(byte[] arr1, byte[] arr2, int integer) {
      System.arraycopy(arr1, 0, arr2, integer, arr1.length);
      return integer + arr1.length;
   }

   public int getChunkX() {
      return this.chunkX;
   }

   public int getChunkZ() {
      return this.chunkZ;
   }

   public int getExtractedSize() {
      return this.extractedData.dataSize;
   }

   public boolean func_149274_i() {
      return this.field_149279_g;
   }

   public static class Extracted {
      public byte[] data;
      public int dataSize;
   }
}
