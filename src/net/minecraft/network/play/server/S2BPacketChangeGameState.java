package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S2BPacketChangeGameState implements Packet<INetHandlerPlayClient> {
   public static final String[] MESSAGE_NAMES = new String[]{"tile.bed.notValid"};
   private int state;
   private float field_149141_c;

   public S2BPacketChangeGameState() {
   }

   public S2BPacketChangeGameState(int integer, float float2) {
      this.state = integer;
      this.field_149141_c = float2;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.state = buffer.readUnsignedByte();
      this.field_149141_c = buffer.readFloat();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.state);
      buffer.writeFloat(this.field_149141_c);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleChangeGameState(this);
   }

   public int getGameState() {
      return this.state;
   }

   public float func_149137_d() {
      return this.field_149141_c;
   }
}
