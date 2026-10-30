package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.ScoreObjective;

public class S3BPacketScoreboardObjective implements Packet<INetHandlerPlayClient> {
   private String objectiveName;
   private String objectiveValue;
   private IScoreObjectiveCriteria.EnumRenderType type;
   private int field_149342_c;

   public S3BPacketScoreboardObjective() {
   }

   public S3BPacketScoreboardObjective(ScoreObjective scoreObjective, int integer) {
      this.objectiveName = scoreObjective.getName();
      this.objectiveValue = scoreObjective.getDisplayName();
      this.type = scoreObjective.getCriteria().getRenderType();
      this.field_149342_c = integer;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.objectiveName = buffer.readStringFromBuffer(16);
      this.field_149342_c = buffer.readByte();
      if (this.field_149342_c == 0 || this.field_149342_c == 2) {
         this.objectiveValue = buffer.readStringFromBuffer(32);
         this.type = IScoreObjectiveCriteria.EnumRenderType.func_178795_a(buffer.readStringFromBuffer(16));
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.objectiveName);
      buffer.writeByte(this.field_149342_c);
      if (this.field_149342_c == 0 || this.field_149342_c == 2) {
         buffer.writeString(this.objectiveValue);
         buffer.writeString(this.type.func_178796_a());
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleScoreboardObjective(this);
   }

   public String func_149339_c() {
      return this.objectiveName;
   }

   public String func_149337_d() {
      return this.objectiveValue;
   }

   public int func_149338_e() {
      return this.field_149342_c;
   }

   public IScoreObjectiveCriteria.EnumRenderType func_179817_d() {
      return this.type;
   }
}
