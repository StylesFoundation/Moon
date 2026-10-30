package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class Scoreboard {
   private final Map<String, ScoreObjective> scoreObjectives = Maps.newHashMap();
   private final Map<IScoreObjectiveCriteria, List<ScoreObjective>> scoreObjectiveCriterias = Maps.newHashMap();
   private final Map<String, Map<ScoreObjective, Score>> entitiesScoreObjectives = Maps.newHashMap();
   private final ScoreObjective[] objectiveDisplaySlots = new ScoreObjective[19];
   private final Map<String, ScorePlayerTeam> teams = Maps.newHashMap();
   private final Map<String, ScorePlayerTeam> teamMemberships = Maps.newHashMap();
   private static String[] field_178823_g = null;

   public ScoreObjective getObjective(String string) {
      return this.scoreObjectives.get(string);
   }

   public ScoreObjective Method280(String string, IScoreObjectiveCriteria iScoreObjectiveCriteria) {
      if (string.length() > 16) {
         throw new IllegalArgumentException("The objective name '" + string + "' is too long!");
      }

      ScoreObjective var6 = this.getObjective(string);
      if (var6 != null) {
         throw new IllegalArgumentException("An objective with the name '" + string + "' already exists!");
      }

      var6 = new ScoreObjective(this, string, iScoreObjectiveCriteria);
      List<ScoreObjective> var7 = this.scoreObjectiveCriterias.computeIfAbsent(iScoreObjectiveCriteria, k -> Lists.newArrayList());
      var7.add(var6);
      this.scoreObjectives.put(string, var6);
      this.onScoreObjectiveAdded(var6);
      return var6;
   }

   public Collection<ScoreObjective> getObjectivesFromCriteria(IScoreObjectiveCriteria iScoreObjectiveCriteria) {
      Collection var5 = this.scoreObjectiveCriterias.get(iScoreObjectiveCriteria);
      return var5 == null ? Lists.newArrayList() : Lists.newArrayList(var5);
   }

   public boolean entityHasObjective(String string, ScoreObjective scoreObjective) {
      Map var6 = this.entitiesScoreObjectives.get(string);
      if (var6 == null) {
         return false;
      }

      Score var7 = (Score)var6.get(scoreObjective);
      return var7 != null;
   }

   public Score Method281(String string, ScoreObjective scoreObjective) {
      if (string.length() > 40) {
         throw new IllegalArgumentException("The player name '" + string + "' is too long!");
      }

      Map<ScoreObjective, Score> var6 = this.entitiesScoreObjectives.computeIfAbsent(string, k -> Maps.newHashMap());
      Score var7 = (Score)var6.get(scoreObjective);
      if (var7 == null) {
         var7 = new Score(this, scoreObjective, string);
         var6.put(scoreObjective, var7);
      }

      return var7;
   }

   public Collection<Score> getSortedScores(ScoreObjective scoreObjective) {
      ArrayList var5 = Lists.newArrayList();
      Iterator var6 = this.entitiesScoreObjectives.values().iterator();

      while (var6.hasNext()) {
         Score var8 = (Score)((Map)var6.next()).get(scoreObjective);
         if (var8 != null) {
            var5.add(var8);
         }
      }

      var5.sort(Score.a);
      return var5;
   }

   public Collection<ScoreObjective> getScoreObjectives() {
      return this.scoreObjectives.values();
   }

   public Collection<String> getObjectiveNames() {
      return this.entitiesScoreObjectives.keySet();
   }

   public void removeObjectiveFromEntity(String string, ScoreObjective scoreObjective) {
      if (scoreObjective == null) {
         Map var6 = this.entitiesScoreObjectives.remove(string);
         if (var6 != null) {
            this.func_96516_a(string);
         }
      } else {
         Map var9 = this.entitiesScoreObjectives.get(string);
         if (var9 != null) {
            Score var7 = (Score)var9.remove(scoreObjective);
            if (var9.size() < 1) {
               Map var8 = this.entitiesScoreObjectives.remove(string);
               if (var8 != null) {
                  this.func_96516_a(string);
               }
            } else if (var7 != null) {
               this.func_178820_a(string, scoreObjective);
            }
         }
      }
   }

   public Collection<Score> getScores() {
      Collection<Map<ScoreObjective, Score>> var4 = this.entitiesScoreObjectives.values();
      ArrayList var5 = Lists.newArrayList();

      for (Map<ScoreObjective, Score> var7 : var4) {
         var5.addAll(var7.values());
      }

      return var5;
   }

   public Map<ScoreObjective, Score> getObjectivesForEntity(String string) {
      Map var5 = this.entitiesScoreObjectives.get(string);
      if (var5 == null) {
         var5 = Maps.newHashMap();
      }

      return var5;
   }

   public void removeObjective(ScoreObjective scoreObjective) {
      this.scoreObjectives.remove(scoreObjective.getName());

      for (int var5 = 0; var5 < 19; var5++) {
         if (this.getObjectiveInDisplaySlot(var5) == scoreObjective) {
            this.setObjectiveInDisplaySlot(var5, null);
         }
      }

      List var8 = this.scoreObjectiveCriterias.get(scoreObjective.getCriteria());
      if (var8 != null) {
         var8.remove(scoreObjective);
      }

      for (Map var7 : this.entitiesScoreObjectives.values()) {
         var7.remove(scoreObjective);
      }

      this.onScoreObjectiveRemoved(scoreObjective);
   }

   public void setObjectiveInDisplaySlot(int integer, ScoreObjective scoreObjective) {
      this.objectiveDisplaySlots[integer] = scoreObjective;
   }

   public ScoreObjective getObjectiveInDisplaySlot(int integer) {
      return this.objectiveDisplaySlots[integer];
   }

   public ScorePlayerTeam getTeam(String string) {
      return this.teams.get(string);
   }

   public ScorePlayerTeam createTeam(String string) {
      if (string.length() > 16) {
         throw new IllegalArgumentException("The team name '" + string + "' is too long!");
      }

      ScorePlayerTeam var5 = this.getTeam(string);
      if (var5 != null) {
         throw new IllegalArgumentException("A team with the name '" + string + "' already exists!");
      }

      var5 = new ScorePlayerTeam(this, string);
      this.teams.put(string, var5);
      this.Method282(var5);
      return var5;
   }

   public void removeTeam(ScorePlayerTeam scorePlayerTeam) {
      this.teams.remove(scorePlayerTeam.getRegisteredName());

      for (String var6 : scorePlayerTeam.getMembershipCollection()) {
         this.teamMemberships.remove(var6);
      }

      this.Method283(scorePlayerTeam);
   }

   public boolean addPlayerToTeam(String string1, String string2) {
      if (string1.length() > 40) {
         throw new IllegalArgumentException("The player name '" + string1 + "' is too long!");
      }

      if (!this.teams.containsKey(string2)) {
         return false;
      }

      ScorePlayerTeam var6 = this.getTeam(string2);
      if (this.getPlayersTeam(string1) != null) {
         this.removePlayerFromTeams(string1);
      }

      this.teamMemberships.put(string1, var6);
      var6.getMembershipCollection().add(string1);
      return true;
   }

   public boolean removePlayerFromTeams(String string) {
      ScorePlayerTeam var5 = this.getPlayersTeam(string);
      if (var5 != null) {
         this.removePlayerFromTeam(string, var5);
         return true;
      } else {
         return false;
      }
   }

   public void removePlayerFromTeam(String string, ScorePlayerTeam scorePlayerTeam) {
      if (this.getPlayersTeam(string) != scorePlayerTeam) {
         throw new IllegalStateException(
            "Player is either on another team or not on any team. Cannot remove from team '" + scorePlayerTeam.getRegisteredName() + "'."
         );
      }

      this.teamMemberships.remove(string);
      scorePlayerTeam.getMembershipCollection().remove(string);
   }

   public Collection<String> getTeamNames() {
      return this.teams.keySet();
   }

   public Collection<ScorePlayerTeam> getTeams() {
      return this.teams.values();
   }

   public ScorePlayerTeam getPlayersTeam(String string) {
      return this.teamMemberships.get(string);
   }

   public void onScoreObjectiveAdded(ScoreObjective scoreObjective) {
   }

   public void onObjectiveDisplayNameChanged(ScoreObjective scoreObjective) {
   }

   public void onScoreObjectiveRemoved(ScoreObjective scoreObjective) {
   }

   public void func_96536_a(Score score) {
   }

   public void func_96516_a(String string) {
   }

   public void func_178820_a(String string, ScoreObjective scoreObjective) {
   }

   public void Method282(ScorePlayerTeam scorePlayerTeam) {
   }

   public void sendTeamUpdate(ScorePlayerTeam scorePlayerTeam) {
   }

   public void Method283(ScorePlayerTeam scorePlayerTeam) {
   }

   public static String getObjectiveDisplaySlot(int integer) {
      switch (integer) {
         case 0:
            return "list";
         case 1:
            return "sidebar";
         case 2:
            return "belowName";
         default:
            if (integer >= 3 && integer <= 18) {
               EnumChatFormatting var4 = EnumChatFormatting.func_175744_a(integer - 3);
               if (var4 != null && var4 != EnumChatFormatting.RESET) {
                  return "sidebar.team." + var4.getFriendlyName();
               }
            }

            return null;
      }
   }

   public static int getObjectiveDisplaySlotNumber(String string) {
      if (string.equalsIgnoreCase("list")) {
         return 0;
      }

      if (string.equalsIgnoreCase("sidebar")) {
         return 1;
      }

      if (string.equalsIgnoreCase("belowName")) {
         return 2;
      }

      if (string.startsWith("sidebar.team.")) {
         EnumChatFormatting var5 = EnumChatFormatting.getValueByName(string.substring(13));
         if (var5 != null && var5.getColorIndex() >= 0) {
            return var5.getColorIndex() + 3;
         }
      }

      return -1;
   }

   public static String[] getDisplaySlotStrings() {
      if (field_178823_g == null) {
         field_178823_g = new String[19];

         for (int var3 = 0; var3 < 19; var3++) {
            field_178823_g[var3] = getObjectiveDisplaySlot(var3);
         }
      }

      return field_178823_g;
   }

   public void func_181140_a(Entity entity) {
      if (entity != null && !(entity instanceof EntityPlayer) && !entity.isEntityAlive()) {
         String var5 = entity.getUniqueID().toString();
         this.removeObjectiveFromEntity(var5, null);
         this.removePlayerFromTeams(var5);
      }
   }
}
