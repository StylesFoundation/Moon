package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.chunk.Chunk;

public class S26PacketMapChunkBulk implements Packet<INetHandlerPlayClient> {
   private int[] xPositions;
   private int[] zPositions;
   private S21PacketChunkData.Extracted[] chunksData;
   private boolean isOverworld;

   public S26PacketMapChunkBulk() {
   }

   public S26PacketMapChunkBulk(List<Chunk> list) {
      int var5 = list.size();
      this.xPositions = new int[var5];
      this.zPositions = new int[var5];
      this.chunksData = new S21PacketChunkData.Extracted[var5];
      this.isOverworld = !((Chunk)list.get(0)).getWorld().provider.getHasNoSky();

      for (int var6 = 0; var6 < var5; var6++) {
         Chunk var7 = (Chunk)list.get(var6);
         S21PacketChunkData.Extracted var8 = S21PacketChunkData.getExtractedData(var7, true, this.isOverworld, 65535);
         this.xPositions[var6] = var7.xPosition;
         this.zPositions[var6] = var7.zPosition;
         this.chunksData[var6] = var8;
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.isOverworld = buffer.readBoolean();
      int var5 = buffer.readVarIntFromBuffer();
      this.xPositions = new int[var5];
      this.zPositions = new int[var5];
      this.chunksData = new S21PacketChunkData.Extracted[var5];

      for (int var6 = 0; var6 < var5; var6++) {
         this.xPositions[var6] = buffer.readInt();
         this.zPositions[var6] = buffer.readInt();
         this.chunksData[var6] = new S21PacketChunkData.Extracted();
         this.chunksData[var6].dataSize = buffer.readShort() & '\uffff';
         this.chunksData[var6].data = new byte[S21PacketChunkData.func_180737_a(Integer.bitCount(this.chunksData[var6].dataSize), this.isOverworld, true)];
      }

      for (int var7 = 0; var7 < var5; var7++) {
         buffer.readBytes(this.chunksData[var7].data);
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBoolean(this.isOverworld);
      buffer.writeVarIntToBuffer(this.chunksData.length);

      for (int var5 = 0; var5 < this.xPositions.length; var5++) {
         buffer.writeInt(this.xPositions[var5]);
         buffer.writeInt(this.zPositions[var5]);
         buffer.writeShort((short)(this.chunksData[var5].dataSize & 65535));
      }

      for (int var6 = 0; var6 < this.xPositions.length; var6++) {
         buffer.writeBytes(this.chunksData[var6].data);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleMapChunkBulk(this);
   }

   public int getChunkX(int integer) {
      return this.xPositions[integer];
   }

   public int getChunkZ(int integer) {
      return this.zPositions[integer];
   }

   public int getChunkCount() {
      return this.xPositions.length;
   }

   public byte[] getChunkBytes(int integer) {
      return this.chunksData[integer].data;
   }

   public int getChunkSize(int integer) {
      return this.chunksData[integer].dataSize;
   }
}
