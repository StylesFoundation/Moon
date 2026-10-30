package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collection;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

public class S3EPacketTeams implements Packet<INetHandlerPlayClient> {
   private String name = "";
   private String displayName = "";
   private String prefix = "";
   private String suffix = "";
   private String nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
   private int color = -1;
   private final Collection<String> players = Lists.newArrayList();
   private int action;
   private int friendlyFlags;

   public S3EPacketTeams() {
   }

   public S3EPacketTeams(ScorePlayerTeam scorePlayerTeam, int integer) {
      this.name = scorePlayerTeam.getRegisteredName();
      this.action = integer;
      if (integer == 0 || integer == 2) {
         this.displayName = scorePlayerTeam.getTeamName();
         this.prefix = scorePlayerTeam.getColorPrefix();
         this.suffix = scorePlayerTeam.getColorSuffix();
         this.friendlyFlags = scorePlayerTeam.func_98299_i();
         this.nameTagVisibility = scorePlayerTeam.getNameTagVisibility().internalName;
         this.color = scorePlayerTeam.getChatFormat().getColorIndex();
      }

      if (integer == 0) {
         this.players.addAll(scorePlayerTeam.getMembershipCollection());
      }
   }

   public S3EPacketTeams(ScorePlayerTeam scorePlayerTeam, Collection<String> collection, int integer) {
      if (integer != 3 && integer != 4) {
         throw new IllegalArgumentException("Method must be join or leave for player constructor");
      }

      if (collection != null && !collection.isEmpty()) {
         this.action = integer;
         this.name = scorePlayerTeam.getRegisteredName();
         this.players.addAll(collection);
      } else {
         throw new IllegalArgumentException("Players cannot be null/empty");
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.name = buffer.readStringFromBuffer(16);
      this.action = buffer.readByte();
      if (this.action == 0 || this.action == 2) {
         this.displayName = buffer.readStringFromBuffer(32);
         this.prefix = buffer.readStringFromBuffer(16);
         this.suffix = buffer.readStringFromBuffer(16);
         this.friendlyFlags = buffer.readByte();
         this.nameTagVisibility = buffer.readStringFromBuffer(32);
         this.color = buffer.readByte();
      }

      if (this.action == 0 || this.action == 3 || this.action == 4) {
         int var5 = buffer.readVarIntFromBuffer();

         for (int var6 = 0; var6 < var5; var6++) {
            this.players.add(buffer.readStringFromBuffer(40));
         }
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.name);
      buffer.writeByte(this.action);
      if (this.action == 0 || this.action == 2) {
         buffer.writeString(this.displayName);
         buffer.writeString(this.prefix);
         buffer.writeString(this.suffix);
         buffer.writeByte(this.friendlyFlags);
         buffer.writeString(this.nameTagVisibility);
         buffer.writeByte(this.color);
      }

      if (this.action == 0 || this.action == 3 || this.action == 4) {
         buffer.writeVarIntToBuffer(this.players.size());

         for (String var6 : this.players) {
            buffer.writeString(var6);
         }
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleTeams(this);
   }

   public String getName() {
      return this.name;
   }

   public String getDisplayName() {
      return this.displayName;
   }

   public String getPrefix() {
      return this.prefix;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public Collection<String> getPlayers() {
      return this.players;
   }

   public int getAction() {
      return this.action;
   }

   public int getFriendlyFlags() {
      return this.friendlyFlags;
   }

   public int getColor() {
      return this.color;
   }

   public String getNameTagVisibility() {
      return this.nameTagVisibility;
   }
}
