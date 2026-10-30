package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S32PacketConfirmTransaction implements Packet<INetHandlerPlayClient> {
   private int windowId;
   private short actionNumber;
   private boolean field_148893_c;

   public S32PacketConfirmTransaction() {
   }

   public S32PacketConfirmTransaction(int integer, short short2, boolean boolean3) {
      this.windowId = integer;
      this.actionNumber = short2;
      this.field_148893_c = boolean3;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleConfirmTransaction(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readUnsignedByte();
      this.actionNumber = buffer.readShort();
      this.field_148893_c = buffer.readBoolean();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
      buffer.writeShort(this.actionNumber);
      buffer.writeBoolean(this.field_148893_c);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public short getActionNumber() {
      return this.actionNumber;
   }

   public boolean func_148888_e() {
      return this.field_148893_c;
   }

   public int Method417() {
      return this.windowId;
   }

   public short Method418() {
      return this.actionNumber;
   }

   public boolean Method419() {
      return this.field_148893_c;
   }

   public S32PacketConfirmTransaction Method420(int integer) {
      this.windowId = integer;
      return this;
   }

   public S32PacketConfirmTransaction Method421(short short1) {
      this.actionNumber = short1;
      return this;
   }

   public S32PacketConfirmTransaction Method422(boolean boolean1) {
      this.field_148893_c = boolean1;
      return this;
   }
}
