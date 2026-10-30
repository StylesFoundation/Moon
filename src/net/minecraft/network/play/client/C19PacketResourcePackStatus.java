package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C19PacketResourcePackStatus implements Packet<INetHandlerPlayServer> {
   private String hash;
   private C19PacketResourcePackStatus.Action status;

   public C19PacketResourcePackStatus() {
   }

   public C19PacketResourcePackStatus(String string, C19PacketResourcePackStatus.Action action) {
      if (string.length() > 40) {
         string = string.substring(0, 40);
      }

      this.hash = string;
      this.status = action;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.hash = buffer.readStringFromBuffer(40);
      this.status = buffer.readEnumValue(C19PacketResourcePackStatus.Action.class);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.hash);
      buffer.writeEnumValue(this.status);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.handleResourcePackStatus(this);
   }

   public String Method888() {
      return this.hash;
   }

   public C19PacketResourcePackStatus.Action Method889() {
      return this.status;
   }

   public C19PacketResourcePackStatus Method890(String string) {
      this.hash = string;
      return this;
   }

   public C19PacketResourcePackStatus Method891(C19PacketResourcePackStatus.Action action) {
      this.status = action;
      return this;
   }

   public enum Action {
      SUCCESSFULLY_LOADED,
      DECLINED,
      FAILED_DOWNLOAD,
      ACCEPTED;

      private static C19PacketResourcePackStatus.Action[] Method463() {
         return new C19PacketResourcePackStatus.Action[]{SUCCESSFULLY_LOADED, DECLINED, FAILED_DOWNLOAD, ACCEPTED};
      }
   }
}
