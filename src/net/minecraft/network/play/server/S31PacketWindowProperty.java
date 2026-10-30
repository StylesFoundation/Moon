package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S31PacketWindowProperty implements Packet<INetHandlerPlayClient> {
   private int windowId;
   private int varIndex;
   private int varValue;

   public S31PacketWindowProperty() {
   }

   public S31PacketWindowProperty(int integer1, int integer2, int integer3) {
      this.windowId = integer1;
      this.varIndex = integer2;
      this.varValue = integer3;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleWindowProperty(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readUnsignedByte();
      this.varIndex = buffer.readShort();
      this.varValue = buffer.readShort();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
      buffer.writeShort(this.varIndex);
      buffer.writeShort(this.varValue);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public int getVarIndex() {
      return this.varIndex;
   }

   public int getVarValue() {
      return this.varValue;
   }
}
