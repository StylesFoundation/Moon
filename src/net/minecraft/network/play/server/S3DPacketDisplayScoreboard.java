package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScoreObjective;

public class S3DPacketDisplayScoreboard implements Packet<INetHandlerPlayClient> {
   private int position;
   private String scoreName;

   public S3DPacketDisplayScoreboard() {
   }

   public S3DPacketDisplayScoreboard(int integer, ScoreObjective scoreObjective) {
      this.position = integer;
      if (scoreObjective == null) {
         this.scoreName = "";
      } else {
         this.scoreName = scoreObjective.getName();
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.position = buffer.readByte();
      this.scoreName = buffer.readStringFromBuffer(16);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.position);
      buffer.writeString(this.scoreName);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleDisplayScoreboard(this);
   }

   public int func_149371_c() {
      return this.position;
   }

   public String func_149370_d() {
      return this.scoreName;
   }
}
