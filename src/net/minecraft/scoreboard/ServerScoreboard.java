package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.server.MinecraftServer;

public class ServerScoreboard extends Scoreboard {
   private final MinecraftServer scoreboardMCServer;
   private final Set<ScoreObjective> field_96553_b = Sets.newHashSet();
   private ScoreboardSaveData scoreboardSaveData;

   public ServerScoreboard(MinecraftServer minecraftServer) {
      this.scoreboardMCServer = minecraftServer;
   }

   @Override
   public void func_96536_a(Score score) {
      super.func_96536_a(score);
      if (this.field_96553_b.contains(score.getObjective())) {
         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(score));
      }

      this.markSaveDataDirty();
   }

   @Override
   public void func_96516_a(String string) {
      super.func_96516_a(string);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(string));
      this.markSaveDataDirty();
   }

   @Override
   public void func_178820_a(String string, ScoreObjective scoreObjective) {
      super.func_178820_a(string, scoreObjective);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3CPacketUpdateScore(string, scoreObjective));
      this.markSaveDataDirty();
   }

   @Override
   public void setObjectiveInDisplaySlot(int integer, ScoreObjective scoreObjective) {
      ScoreObjective var6 = this.getObjectiveInDisplaySlot(integer);
      super.setObjectiveInDisplaySlot(integer, scoreObjective);
      if (var6 != scoreObjective && var6 != null) {
         if (this.func_96552_h(var6) > 0) {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(integer, scoreObjective));
         } else {
            this.sendDisplaySlotRemovalPackets(var6);
         }
      }

      if (scoreObjective != null) {
         if (this.field_96553_b.contains(scoreObjective)) {
            this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3DPacketDisplayScoreboard(integer, scoreObjective));
         } else {
            this.func_96549_e(scoreObjective);
         }
      }

      this.markSaveDataDirty();
   }

   @Override
   public boolean addPlayerToTeam(String string1, String string2) {
      if (super.addPlayerToTeam(string1, string2)) {
         ScorePlayerTeam var6 = this.getTeam(string2);
         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(var6, List.of(string1), 3));
         this.markSaveDataDirty();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void removePlayerFromTeam(String string, ScorePlayerTeam scorePlayerTeam) {
      super.removePlayerFromTeam(string, scorePlayerTeam);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, Collections.singletonList(string), 4));
      this.markSaveDataDirty();
   }

   @Override
   public void onScoreObjectiveAdded(ScoreObjective scoreObjective) {
      super.onScoreObjectiveAdded(scoreObjective);
      this.markSaveDataDirty();
   }

   @Override
   public void onObjectiveDisplayNameChanged(ScoreObjective scoreObjective) {
      super.onObjectiveDisplayNameChanged(scoreObjective);
      if (this.field_96553_b.contains(scoreObjective)) {
         this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3BPacketScoreboardObjective(scoreObjective, 2));
      }

      this.markSaveDataDirty();
   }

   @Override
   public void onScoreObjectiveRemoved(ScoreObjective scoreObjective) {
      super.onScoreObjectiveRemoved(scoreObjective);
      if (this.field_96553_b.contains(scoreObjective)) {
         this.sendDisplaySlotRemovalPackets(scoreObjective);
      }

      this.markSaveDataDirty();
   }

   @Override
   public void Method282(ScorePlayerTeam scorePlayerTeam) {
      super.Method282(scorePlayerTeam);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 0));
      this.markSaveDataDirty();
   }

   @Override
   public void sendTeamUpdate(ScorePlayerTeam scorePlayerTeam) {
      super.sendTeamUpdate(scorePlayerTeam);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 2));
      this.markSaveDataDirty();
   }

   @Override
   public void Method283(ScorePlayerTeam scorePlayerTeam) {
      super.Method283(scorePlayerTeam);
      this.scoreboardMCServer.getConfigurationManager().sendPacketToAllPlayers(new S3EPacketTeams(scorePlayerTeam, 1));
      this.markSaveDataDirty();
   }

   public void func_96547_a(ScoreboardSaveData scoreboardSaveData) {
      this.scoreboardSaveData = scoreboardSaveData;
   }

   protected void markSaveDataDirty() {
      if (this.scoreboardSaveData != null) {
         this.scoreboardSaveData.markDirty();
      }
   }

   public List<Packet> func_96550_d(ScoreObjective scoreObjective) {
      ArrayList var5 = Lists.newArrayList();
      var5.add(new S3BPacketScoreboardObjective(scoreObjective, 0));

      for (int var6 = 0; var6 < 19; var6++) {
         if (this.getObjectiveInDisplaySlot(var6) == scoreObjective) {
            var5.add(new S3DPacketDisplayScoreboard(var6, scoreObjective));
         }
      }

      for (Score var7 : this.getSortedScores(scoreObjective)) {
         var5.add(new S3CPacketUpdateScore(var7));
      }

      return var5;
   }

   public void func_96549_e(ScoreObjective scoreObjective) {
      List<Packet> var5 = this.func_96550_d(scoreObjective);

      for (EntityPlayerMP var7 : this.scoreboardMCServer.getConfigurationManager().getPlayerList()) {
         for (Packet var9 : var5) {
            var7.playerNetServerHandler.sendPacket(var9);
         }
      }

      this.field_96553_b.add(scoreObjective);
   }

   public List<Packet> func_96548_f(ScoreObjective scoreObjective) {
      ArrayList var5 = Lists.newArrayList();
      var5.add(new S3BPacketScoreboardObjective(scoreObjective, 1));

      for (int var6 = 0; var6 < 19; var6++) {
         if (this.getObjectiveInDisplaySlot(var6) == scoreObjective) {
            var5.add(new S3DPacketDisplayScoreboard(var6, scoreObjective));
         }
      }

      return var5;
   }

   public void sendDisplaySlotRemovalPackets(ScoreObjective scoreObjective) {
      List<Packet> var5 = this.func_96548_f(scoreObjective);

      for (EntityPlayerMP var7 : this.scoreboardMCServer.getConfigurationManager().getPlayerList()) {
         for (Packet var9 : var5) {
            var7.playerNetServerHandler.sendPacket(var9);
         }
      }

      this.field_96553_b.remove(scoreObjective);
   }

   public int func_96552_h(ScoreObjective scoreObjective) {
      int var5 = 0;

      for (int var6 = 0; var6 < 19; var6++) {
         if (this.getObjectiveInDisplaySlot(var6) == scoreObjective) {
            var5++;
         }
      }

      return var5;
   }
}
