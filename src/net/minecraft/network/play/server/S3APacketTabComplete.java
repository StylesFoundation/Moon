package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S3APacketTabComplete implements Packet<INetHandlerPlayClient> {
   private String[] matches;

   public S3APacketTabComplete() {
   }

   public S3APacketTabComplete(String[] arr) {
      this.matches = arr;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.matches = new String[buffer.readVarIntFromBuffer()];

      for (int var5 = 0; var5 < this.matches.length; var5++) {
         this.matches[var5] = buffer.readStringFromBuffer(32767);
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.matches.length);

      for (String var8 : this.matches) {
         buffer.writeString(var8);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleTabComplete(this);
   }

   public String[] func_149630_c() {
      return this.matches;
   }
}
