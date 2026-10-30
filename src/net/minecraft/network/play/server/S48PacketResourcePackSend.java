package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S48PacketResourcePackSend implements Packet<INetHandlerPlayClient> {
   private String url;
   private String hash;

   public S48PacketResourcePackSend() {
   }

   public S48PacketResourcePackSend(String string1, String string2) {
      this.url = string1;
      this.hash = string2;
      if (string2.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + string2.length() + ")");
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.url = buffer.readStringFromBuffer(32767);
      this.hash = buffer.readStringFromBuffer(40);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.url);
      buffer.writeString(this.hash);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleResourcePack(this);
   }

   public String getURL() {
      return this.url;
   }

   public String getHash() {
      return this.hash;
   }

   public S48PacketResourcePackSend Method950(String string) {
      this.url = string;
      return this;
   }

   public S48PacketResourcePackSend Method951(String string) {
      this.hash = string;
      return this;
   }
}
