package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;

public class S3CPacketUpdateScore implements Packet<INetHandlerPlayClient> {
   private String name = "";
   private String objective = "";
   private int value;
   private S3CPacketUpdateScore.Action action;

   public S3CPacketUpdateScore() {
   }

   public S3CPacketUpdateScore(Score score) {
      this.name = score.getPlayerName();
      this.objective = score.getObjective().getName();
      this.value = score.getScorePoints();
      this.action = S3CPacketUpdateScore.Action.CHANGE;
   }

   public S3CPacketUpdateScore(String string) {
      this.name = string;
      this.objective = "";
      this.value = 0;
      this.action = S3CPacketUpdateScore.Action.REMOVE;
   }

   public S3CPacketUpdateScore(String string, ScoreObjective scoreObjective) {
      this.name = string;
      this.objective = scoreObjective.getName();
      this.value = 0;
      this.action = S3CPacketUpdateScore.Action.REMOVE;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.name = buffer.readStringFromBuffer(40);
      this.action = buffer.readEnumValue(S3CPacketUpdateScore.Action.class);
      this.objective = buffer.readStringFromBuffer(16);
      if (this.action != S3CPacketUpdateScore.Action.REMOVE) {
         this.value = buffer.readVarIntFromBuffer();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.name);
      buffer.writeEnumValue(this.action);
      buffer.writeString(this.objective);
      if (this.action != S3CPacketUpdateScore.Action.REMOVE) {
         buffer.writeVarIntToBuffer(this.value);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleUpdateScore(this);
   }

   public String getPlayerName() {
      return this.name;
   }

   public String getObjectiveName() {
      return this.objective;
   }

   public int getScoreValue() {
      return this.value;
   }

   public S3CPacketUpdateScore.Action getScoreAction() {
      return this.action;
   }

   public enum Action {
      CHANGE,
      REMOVE;

      private static S3CPacketUpdateScore.Action[] Method602() {
         return new S3CPacketUpdateScore.Action[]{CHANGE, REMOVE};
      }
   }
}
