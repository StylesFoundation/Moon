package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class S34PacketMaps implements Packet<INetHandlerPlayClient> {
   private int mapId;
   private byte mapScale;
   private Vec4b[] mapVisiblePlayersVec4b;
   private int mapMinX;
   private int mapMinY;
   private int mapMaxX;
   private int mapMaxY;
   private byte[] mapDataBytes;

   public S34PacketMaps() {
   }

   public S34PacketMaps(int integer1, byte byte2, Collection<Vec4b> collection, byte[] arr, int integer5, int integer6, int integer7, int integer8) {
      this.mapId = integer1;
      this.mapScale = byte2;
      this.mapVisiblePlayersVec4b = collection.toArray(new Vec4b[0]);
      this.mapMinX = integer5;
      this.mapMinY = integer6;
      this.mapMaxX = integer7;
      this.mapMaxY = integer8;
      this.mapDataBytes = new byte[integer7 * integer8];

      for (int var12 = 0; var12 < integer7; var12++) {
         for (int var13 = 0; var13 < integer8; var13++) {
            this.mapDataBytes[var12 + var13 * integer7] = arr[integer5 + var12 + (integer6 + var13) * 128];
         }
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.mapId = buffer.readVarIntFromBuffer();
      this.mapScale = buffer.readByte();
      this.mapVisiblePlayersVec4b = new Vec4b[buffer.readVarIntFromBuffer()];

      for (int var5 = 0; var5 < this.mapVisiblePlayersVec4b.length; var5++) {
         short var6 = buffer.readByte();
         this.mapVisiblePlayersVec4b[var5] = new Vec4b((byte)(var6 >> 4 & 15), buffer.readByte(), buffer.readByte(), (byte)(var6 & 15));
      }

      this.mapMaxX = buffer.readUnsignedByte();
      if (this.mapMaxX > 0) {
         this.mapMaxY = buffer.readUnsignedByte();
         this.mapMinX = buffer.readUnsignedByte();
         this.mapMinY = buffer.readUnsignedByte();
         this.mapDataBytes = buffer.readByteArray();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.mapId);
      buffer.writeByte(this.mapScale);
      buffer.writeVarIntToBuffer(this.mapVisiblePlayersVec4b.length);

      for (Vec4b var8 : this.mapVisiblePlayersVec4b) {
         buffer.writeByte((var8.Method972() & 15) << 4 | var8.Method975() & 15);
         buffer.writeByte(var8.Method973());
         buffer.writeByte(var8.Method974());
      }

      buffer.writeByte(this.mapMaxX);
      if (this.mapMaxX > 0) {
         buffer.writeByte(this.mapMaxY);
         buffer.writeByte(this.mapMinX);
         buffer.writeByte(this.mapMinY);
         buffer.writeByteArray(this.mapDataBytes);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleMaps(this);
   }

   public int getMapId() {
      return this.mapId;
   }

   public void setMapdataTo(MapData mapData) {
      mapData.scale = this.mapScale;
      mapData.mapDecorations.clear();

      for (int var5 = 0; var5 < this.mapVisiblePlayersVec4b.length; var5++) {
         Vec4b var6 = this.mapVisiblePlayersVec4b[var5];
         mapData.mapDecorations.put("icon-" + var5, var6);
      }

      for (int var7 = 0; var7 < this.mapMaxX; var7++) {
         for (int var8 = 0; var8 < this.mapMaxY; var8++) {
            mapData.colors[this.mapMinX + var7 + (this.mapMinY + var8) * 128] = this.mapDataBytes[var7 + var8 * this.mapMaxX];
         }
      }
   }
}
