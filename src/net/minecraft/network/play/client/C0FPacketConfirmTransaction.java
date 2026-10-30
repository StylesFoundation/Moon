package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0FPacketConfirmTransaction implements Packet<INetHandlerPlayServer> {
   private int windowId;
   private short uid;
   private boolean accepted;

   public C0FPacketConfirmTransaction() {
   }

   public C0FPacketConfirmTransaction(int integer, short short2, boolean boolean3) {
      this.windowId = integer;
      this.uid = short2;
      this.accepted = boolean3;
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processConfirmTransaction(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readByte();
      this.uid = buffer.readShort();
      this.accepted = buffer.readByte() != 0;
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
      buffer.writeShort(this.uid);
      buffer.writeByte(this.accepted ? 1 : 0);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public short getUid() {
      return this.uid;
   }

   public C0FPacketConfirmTransaction Method465(int integer) {
      this.windowId = integer;
      return this;
   }

   public C0FPacketConfirmTransaction Method466(short short1) {
      this.uid = short1;
      return this;
   }

   public C0FPacketConfirmTransaction Method467(boolean boolean1) {
      this.accepted = boolean1;
      return this;
   }

   public boolean Method468() {
      return this.accepted;
   }
}
