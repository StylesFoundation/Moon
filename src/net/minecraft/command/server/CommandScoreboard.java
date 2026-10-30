package net.minecraft.command.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandScoreboard extends CommandBase {
   @Override
   public String getCommandName() {
      return "scoreboard";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.scoreboard.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (!this.func_175780_b(iCommandSender, arr)) {
         if (arr.length < 1) {
            throw new WrongUsageException("commands.scoreboard.usage");
         }

         if (arr[0].equalsIgnoreCase("objectives")) {
            if (arr.length == 1) {
               throw new WrongUsageException("commands.scoreboard.objectives.usage");
            }

            if (arr[1].equalsIgnoreCase("list")) {
               this.listObjectives(iCommandSender);
            } else if (arr[1].equalsIgnoreCase("add")) {
               if (arr.length < 4) {
                  throw new WrongUsageException("commands.scoreboard.objectives.add.usage");
               }

               this.addObjective(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("remove")) {
               if (arr.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.objectives.remove.usage");
               }

               this.removeObjective(iCommandSender, arr[2]);
            } else {
               if (!arr[1].equalsIgnoreCase("setdisplay")) {
                  throw new WrongUsageException("commands.scoreboard.objectives.usage");
               }

               if (arr.length != 3 && arr.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.objectives.setdisplay.usage");
               }

               this.setObjectiveDisplay(iCommandSender, arr, 2);
            }
         } else if (arr[0].equalsIgnoreCase("players")) {
            if (arr.length == 1) {
               throw new WrongUsageException("commands.scoreboard.players.usage");
            }

            if (arr[1].equalsIgnoreCase("list")) {
               if (arr.length > 3) {
                  throw new WrongUsageException("commands.scoreboard.players.list.usage");
               }

               this.listPlayers(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("add")) {
               if (arr.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.add.usage");
               }

               this.setPlayer(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("remove")) {
               if (arr.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.remove.usage");
               }

               this.setPlayer(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("set")) {
               if (arr.length < 5) {
                  throw new WrongUsageException("commands.scoreboard.players.set.usage");
               }

               this.setPlayer(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("reset")) {
               if (arr.length != 3 && arr.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.players.reset.usage");
               }

               this.resetPlayers(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("enable")) {
               if (arr.length != 4) {
                  throw new WrongUsageException("commands.scoreboard.players.enable.usage");
               }

               this.func_175779_n(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("test")) {
               if (arr.length != 5 && arr.length != 6) {
                  throw new WrongUsageException("commands.scoreboard.players.test.usage");
               }

               this.func_175781_o(iCommandSender, arr, 2);
            } else {
               if (!arr[1].equalsIgnoreCase("operation")) {
                  throw new WrongUsageException("commands.scoreboard.players.usage");
               }

               if (arr.length != 7) {
                  throw new WrongUsageException("commands.scoreboard.players.operation.usage");
               }

               this.func_175778_p(iCommandSender, arr, 2);
            }
         } else {
            if (!arr[0].equalsIgnoreCase("teams")) {
               throw new WrongUsageException("commands.scoreboard.usage");
            }

            if (arr.length == 1) {
               throw new WrongUsageException("commands.scoreboard.teams.usage");
            }

            if (arr[1].equalsIgnoreCase("list")) {
               if (arr.length > 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.list.usage");
               }

               this.listTeams(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("add")) {
               if (arr.length < 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.add.usage");
               }

               this.addTeam(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("remove")) {
               if (arr.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.remove.usage");
               }

               this.removeTeam(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("empty")) {
               if (arr.length != 3) {
                  throw new WrongUsageException("commands.scoreboard.teams.empty.usage");
               }

               this.emptyTeam(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("join")) {
               if (arr.length < 4 && (arr.length != 3 || !(iCommandSender instanceof EntityPlayer))) {
                  throw new WrongUsageException("commands.scoreboard.teams.join.usage");
               }

               this.joinTeam(iCommandSender, arr, 2);
            } else if (arr[1].equalsIgnoreCase("leave")) {
               if (arr.length < 3 && !(iCommandSender instanceof EntityPlayer)) {
                  throw new WrongUsageException("commands.scoreboard.teams.leave.usage");
               }

               this.leaveTeam(iCommandSender, arr, 2);
            } else {
               if (!arr[1].equalsIgnoreCase("option")) {
                  throw new WrongUsageException("commands.scoreboard.teams.usage");
               }

               if (arr.length != 4 && arr.length != 5) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.usage");
               }

               this.setTeamOption(iCommandSender, arr, 2);
            }
         }
      }
   }

   private boolean func_175780_b(ICommandSender iCommandSender, String[] arr) throws CommandException {
      int var6 = -1;

      for (int var7 = 0; var7 < arr.length; var7++) {
         if (this.isUsernameIndex(arr, var7) && "*".equals(arr[var7])) {
            if (var6 >= 0) {
               throw new CommandException("commands.scoreboard.noMultiWildcard");
            }

            var6 = var7;
         }
      }

      if (var6 < 0) {
         return false;
      }

      ArrayList<String> var15 = Lists.newArrayList(this.getScoreboard().getObjectiveNames());
      String var8 = arr[var6];
      ArrayList<String> var9 = Lists.newArrayList();

      for (String var11 : var15) {
         arr[var6] = var11;

         try {
            this.processCommand(iCommandSender, arr);
            var9.add(var11);
         } catch (CommandException var14) {
            ChatComponentTranslation var13 = new ChatComponentTranslation(var14.getMessage(), var14.getErrorObjects());
            var13.getChatStyle().setColor(EnumChatFormatting.RED);
            iCommandSender.addChatMessage(var13);
         }
      }

      arr[var6] = var8;
      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, var9.size());
      if (var9.size() == 0) {
         throw new WrongUsageException("commands.scoreboard.allMatchesFailed");
      } else {
         return true;
      }
   }

   protected Scoreboard getScoreboard() {
      return MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
   }

   protected ScoreObjective getObjective(String string, boolean boolean2) throws CommandException {
      ScoreObjective var7 = this.getScoreboard().getObjective(string);
      if (var7 == null) {
         throw new CommandException("commands.scoreboard.objectiveNotFound", string);
      } else if (boolean2 && var7.getCriteria().isReadOnly()) {
         throw new CommandException("commands.scoreboard.objectiveReadOnly", string);
      } else {
         return var7;
      }
   }

   protected ScorePlayerTeam getTeam(String string) throws CommandException {
      Scoreboard var5 = this.getScoreboard();
      ScorePlayerTeam var6 = var5.getTeam(string);
      if (var6 == null) {
         throw new CommandException("commands.scoreboard.teamNotFound", string);
      } else {
         return var6;
      }
   }

   protected void addObjective(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      String var7 = arr[integer++];
      String var8 = arr[integer++];
      Scoreboard var9 = this.getScoreboard();
      IScoreObjectiveCriteria var10 = IScoreObjectiveCriteria.INSTANCES.get(var8);
      if (var10 == null) {
         throw new WrongUsageException("commands.scoreboard.objectives.add.wrongType", var8);
      }

      if (var9.getObjective(var7) != null) {
         throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", var7);
      }

      if (var7.length() > 16) {
         throw new SyntaxErrorException("commands.scoreboard.objectives.add.tooLong", var7, 16);
      }

      if (var7.length() == 0) {
         throw new WrongUsageException("commands.scoreboard.objectives.add.usage");
      }

      if (arr.length > integer) {
         String var11 = getChatComponentFromNthArg(iCommandSender, arr, integer).getUnformattedText();
         if (var11.length() > 32) {
            throw new SyntaxErrorException("commands.scoreboard.objectives.add.displayTooLong", var11, 32);
         }

         if (var11.length() > 0) {
            var9.Method280(var7, var10).setDisplayName(var11);
         } else {
            var9.Method280(var7, var10);
         }
      } else {
         var9.Method280(var7, var10);
      }

      notifyOperators(iCommandSender, this, "commands.scoreboard.objectives.add.success", var7);
   }

   protected void addTeam(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      String var7 = arr[integer++];
      Scoreboard var8 = this.getScoreboard();
      if (var8.getTeam(var7) != null) {
         throw new CommandException("commands.scoreboard.teams.add.alreadyExists", var7);
      }

      if (var7.length() > 16) {
         throw new SyntaxErrorException("commands.scoreboard.teams.add.tooLong", var7, 16);
      }

      if (var7.length() == 0) {
         throw new WrongUsageException("commands.scoreboard.teams.add.usage");
      }

      if (arr.length > integer) {
         String var9 = getChatComponentFromNthArg(iCommandSender, arr, integer).getUnformattedText();
         if (var9.length() > 32) {
            throw new SyntaxErrorException("commands.scoreboard.teams.add.displayTooLong", var9, 32);
         }

         if (var9.length() > 0) {
            var8.createTeam(var7).setTeamName(var9);
         } else {
            var8.createTeam(var7);
         }
      } else {
         var8.createTeam(var7);
      }

      notifyOperators(iCommandSender, this, "commands.scoreboard.teams.add.success", var7);
   }

   protected void setTeamOption(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      ScorePlayerTeam var7 = this.getTeam(arr[integer++]);
      if (var7 != null) {
         String var8 = arr[integer++].toLowerCase();
         if (!var8.equalsIgnoreCase("color")
            && !var8.equalsIgnoreCase("friendlyfire")
            && !var8.equalsIgnoreCase("seeFriendlyInvisibles")
            && !var8.equalsIgnoreCase("nametagVisibility")
            && !var8.equalsIgnoreCase("deathMessageVisibility")) {
            throw new WrongUsageException("commands.scoreboard.teams.option.usage");
         }

         if (arr.length == 4) {
            if (var8.equalsIgnoreCase("color")) {
               throw new WrongUsageException(
                  "commands.scoreboard.teams.option.noValue", var8, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false))
               );
            }

            if (!var8.equalsIgnoreCase("friendlyfire") && !var8.equalsIgnoreCase("seeFriendlyInvisibles")) {
               if (!var8.equalsIgnoreCase("nametagVisibility") && !var8.equalsIgnoreCase("deathMessageVisibility")) {
                  throw new WrongUsageException("commands.scoreboard.teams.option.usage");
               }

               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var8, joinNiceString(Team.EnumVisible.func_178825_a()));
            }

            throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var8, joinNiceStringFromCollection(Arrays.asList("true", "false")));
         }

         String var9 = arr[integer];
         if (var8.equalsIgnoreCase("color")) {
            EnumChatFormatting var10 = EnumChatFormatting.getValueByName(var9);
            if (var10 == null || var10.isFancyStyling()) {
               throw new WrongUsageException(
                  "commands.scoreboard.teams.option.noValue", var8, joinNiceStringFromCollection(EnumChatFormatting.getValidValues(true, false))
               );
            }

            var7.setChatFormat(var10);
            var7.setNamePrefix(var10.toString());
            var7.setNameSuffix(EnumChatFormatting.RESET.toString());
         } else if (var8.equalsIgnoreCase("friendlyfire")) {
            if (!var9.equalsIgnoreCase("true") && !var9.equalsIgnoreCase("false")) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var8, joinNiceStringFromCollection(Arrays.asList("true", "false")));
            }

            var7.setAllowFriendlyFire(var9.equalsIgnoreCase("true"));
         } else if (var8.equalsIgnoreCase("seeFriendlyInvisibles")) {
            if (!var9.equalsIgnoreCase("true") && !var9.equalsIgnoreCase("false")) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var8, joinNiceStringFromCollection(Arrays.asList("true", "false")));
            }

            var7.setSeeFriendlyInvisiblesEnabled(var9.equalsIgnoreCase("true"));
         } else if (var8.equalsIgnoreCase("nametagVisibility")) {
            Team.EnumVisible var13 = Team.EnumVisible.func_178824_a(var9);
            if (var13 == null) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var8, joinNiceString(Team.EnumVisible.func_178825_a()));
            }

            var7.setNameTagVisibility(var13);
         } else if (var8.equalsIgnoreCase("deathMessageVisibility")) {
            Team.EnumVisible var14 = Team.EnumVisible.func_178824_a(var9);
            if (var14 == null) {
               throw new WrongUsageException("commands.scoreboard.teams.option.noValue", var8, joinNiceString(Team.EnumVisible.func_178825_a()));
            }

            var7.setDeathMessageVisibility(var14);
         }

         notifyOperators(iCommandSender, this, "commands.scoreboard.teams.option.success", var8, var7.getRegisteredName(), var9);
      }
   }

   protected void removeTeam(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      ScorePlayerTeam var8 = this.getTeam(arr[integer]);
      if (var8 != null) {
         var7.removeTeam(var8);
         notifyOperators(iCommandSender, this, "commands.scoreboard.teams.remove.success", var8.getRegisteredName());
      }
   }

   protected void listTeams(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      if (arr.length > integer) {
         ScorePlayerTeam var8 = this.getTeam(arr[integer]);
         if (var8 == null) {
            return;
         }

         Collection var9 = var8.getMembershipCollection();
         iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var9.size());
         if (var9.size() == 0) {
            throw new CommandException("commands.scoreboard.teams.list.player.empty", var8.getRegisteredName());
         }

         ChatComponentTranslation var10 = new ChatComponentTranslation("commands.scoreboard.teams.list.player.count", var9.size(), var8.getRegisteredName());
         var10.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         iCommandSender.addChatMessage(var10);
         iCommandSender.addChatMessage(new ChatComponentText(joinNiceString(var9.toArray())));
      } else {
         Collection<ScorePlayerTeam> var12 = var7.getTeams();
         iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var12.size());
         if (var12.size() == 0) {
            throw new CommandException("commands.scoreboard.teams.list.empty");
         }

         ChatComponentTranslation var13 = new ChatComponentTranslation("commands.scoreboard.teams.list.count", var12.size());
         var13.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         iCommandSender.addChatMessage(var13);

         for (ScorePlayerTeam var11 : var12) {
            iCommandSender.addChatMessage(
               new ChatComponentTranslation(
                  "commands.scoreboard.teams.list.entry", var11.getRegisteredName(), var11.getTeamName(), var11.getMembershipCollection().size()
               )
            );
         }
      }
   }

   protected void joinTeam(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      String var8 = arr[integer++];
      HashSet var9 = Sets.newHashSet();
      HashSet var10 = Sets.newHashSet();
      if (iCommandSender instanceof EntityPlayer && integer == arr.length) {
         String var16 = getCommandSenderAsPlayer(iCommandSender).getName();
         if (var7.addPlayerToTeam(var16, var8)) {
            var9.add(var16);
         } else {
            var10.add(var16);
         }
      } else {
         while (integer < arr.length) {
            String var11 = arr[integer++];
            if (var11.startsWith("@")) {
               for (Entity var13 : func_175763_c(iCommandSender, var11)) {
                  String var14 = getEntityName(iCommandSender, var13.getUniqueID().toString());
                  if (var7.addPlayerToTeam(var14, var8)) {
                     var9.add(var14);
                  } else {
                     var10.add(var14);
                  }
               }
            } else {
               String var12 = getEntityName(iCommandSender, var11);
               if (var7.addPlayerToTeam(var12, var8)) {
                  var9.add(var12);
               } else {
                  var10.add(var12);
               }
            }
         }
      }

      if (!var9.isEmpty()) {
         iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, var9.size());
         notifyOperators(iCommandSender, this, "commands.scoreboard.teams.join.success", var9.size(), var8, joinNiceString(var9.toArray(new String[0])));
      }

      if (!var10.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.join.failure", var10.size(), var8, joinNiceString(var10.toArray(new String[0])));
      }
   }

   protected void leaveTeam(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      HashSet var8 = Sets.newHashSet();
      HashSet var9 = Sets.newHashSet();
      if (iCommandSender instanceof EntityPlayer && integer == arr.length) {
         String var14 = getCommandSenderAsPlayer(iCommandSender).getName();
         if (var7.removePlayerFromTeams(var14)) {
            var8.add(var14);
         } else {
            var9.add(var14);
         }
      } else {
         while (integer < arr.length) {
            String var10 = arr[integer++];
            if (var10.startsWith("@")) {
               for (Entity var12 : func_175763_c(iCommandSender, var10)) {
                  String var13 = getEntityName(iCommandSender, var12.getUniqueID().toString());
                  if (var7.removePlayerFromTeams(var13)) {
                     var8.add(var13);
                  } else {
                     var9.add(var13);
                  }
               }
            } else {
               String var11 = getEntityName(iCommandSender, var10);
               if (var7.removePlayerFromTeams(var11)) {
                  var8.add(var11);
               } else {
                  var9.add(var11);
               }
            }
         }
      }

      if (!var8.isEmpty()) {
         iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, var8.size());
         notifyOperators(iCommandSender, this, "commands.scoreboard.teams.leave.success", var8.size(), joinNiceString(var8.toArray(new String[0])));
      }

      if (!var9.isEmpty()) {
         throw new CommandException("commands.scoreboard.teams.leave.failure", var9.size(), joinNiceString(var9.toArray(new String[0])));
      }
   }

   protected void emptyTeam(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      ScorePlayerTeam var8 = this.getTeam(arr[integer]);
      if (var8 != null) {
         ArrayList<String> var9 = Lists.newArrayList(var8.getMembershipCollection());
         iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ENTITIES, var9.size());
         if (var9.isEmpty()) {
            throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", var8.getRegisteredName());
         }

         for (String var11 : var9) {
            var7.removePlayerFromTeam(var11, var8);
         }

         notifyOperators(iCommandSender, this, "commands.scoreboard.teams.empty.success", var9.size(), var8.getRegisteredName());
      }
   }

   protected void removeObjective(ICommandSender iCommandSender, String string) throws CommandException {
      Scoreboard var3 = this.getScoreboard();
      ScoreObjective var4 = this.getObjective(string, false);
      var3.removeObjective(var4);
      notifyOperators(iCommandSender, this, "commands.scoreboard.objectives.remove.success", string);
   }

   protected void listObjectives(ICommandSender iCommandSender) throws CommandException {
      Collection<ScoreObjective> var6 = this.getScoreboard().getScoreObjectives();
      if (var6.size() == 0) {
         throw new CommandException("commands.scoreboard.objectives.list.empty");
      }

      ChatComponentTranslation var7 = new ChatComponentTranslation("commands.scoreboard.objectives.list.count", var6.size());
      var7.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
      iCommandSender.addChatMessage(var7);

      for (ScoreObjective var9 : var6) {
         iCommandSender.addChatMessage(
            new ChatComponentTranslation("commands.scoreboard.objectives.list.entry", var9.getName(), var9.getDisplayName(), var9.getCriteria().getName())
         );
      }
   }

   protected void setObjectiveDisplay(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      String var8 = arr[integer++];
      int var9 = Scoreboard.getObjectiveDisplaySlotNumber(var8);
      ScoreObjective var10 = null;
      if (arr.length == 4) {
         var10 = this.getObjective(arr[integer], false);
      }

      if (var9 < 0) {
         throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", var8);
      }

      var7.setObjectiveInDisplaySlot(var9, var10);
      if (var10 != null) {
         notifyOperators(
            iCommandSender, this, "commands.scoreboard.objectives.setdisplay.successSet", Scoreboard.getObjectiveDisplaySlot(var9), var10.getName()
         );
      } else {
         notifyOperators(iCommandSender, this, "commands.scoreboard.objectives.setdisplay.successCleared", Scoreboard.getObjectiveDisplaySlot(var9));
      }
   }

   protected void listPlayers(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      if (arr.length > integer) {
         String var8 = getEntityName(iCommandSender, arr[integer]);
         Map<ScoreObjective, Score> var9 = var7.getObjectivesForEntity(var8);
         iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var9.size());
         if (var9.size() == 0) {
            throw new CommandException("commands.scoreboard.players.list.player.empty", var8);
         }

         ChatComponentTranslation var10 = new ChatComponentTranslation("commands.scoreboard.players.list.player.count", var9.size(), var8);
         var10.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         iCommandSender.addChatMessage(var10);

         for (Score var12 : var9.values()) {
            iCommandSender.addChatMessage(
               new ChatComponentTranslation(
                  "commands.scoreboard.players.list.player.entry",
                  var12.getScorePoints(),
                  var12.getObjective().getDisplayName(),
                  var12.getObjective().getName()
               )
            );
         }
      } else {
         Collection var13 = var7.getObjectiveNames();
         iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var13.size());
         if (var13.size() == 0) {
            throw new CommandException("commands.scoreboard.players.list.empty");
         }

         ChatComponentTranslation var14 = new ChatComponentTranslation("commands.scoreboard.players.list.count", var13.size());
         var14.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
         iCommandSender.addChatMessage(var14);
         iCommandSender.addChatMessage(new ChatComponentText(joinNiceString(var13.toArray())));
      }
   }

   protected void setPlayer(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      String var7 = arr[integer - 1];
      int var8 = integer;
      String var9 = getEntityName(iCommandSender, arr[integer++]);
      if (var9.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", var9, 40);
      }

      ScoreObjective var10 = this.getObjective(arr[integer++], true);
      int var11 = var7.equalsIgnoreCase("set") ? parseInt(arr[integer++]) : parseInt(arr[integer++], 0);
      if (arr.length > integer) {
         Entity var12 = getEntity(iCommandSender, arr[var8]);

         try {
            NBTTagCompound var13 = JsonToNBT.getTagFromJson(buildString(arr, integer));
            NBTTagCompound var14 = new NBTTagCompound();
            var12.writeToNBT(var14);
            if (!NBTUtil.func_181123_a(var13, var14, true)) {
               throw new CommandException("commands.scoreboard.players.set.tagMismatch", var9);
            }
         } catch (NBTException var15) {
            throw new CommandException("commands.scoreboard.players.set.tagError", var15.getMessage());
         }
      }

      Scoreboard var19 = this.getScoreboard();
      Score var20 = var19.Method281(var9, var10);
      if (var7.equalsIgnoreCase("set")) {
         var20.setScorePoints(var11);
      } else if (var7.equalsIgnoreCase("add")) {
         var20.increseScore(var11);
      } else {
         var20.decreaseScore(var11);
      }

      notifyOperators(iCommandSender, this, "commands.scoreboard.players.set.success", var10.getName(), var9, var20.getScorePoints());
   }

   protected void resetPlayers(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      String var8 = getEntityName(iCommandSender, arr[integer++]);
      if (arr.length > integer) {
         ScoreObjective var9 = this.getObjective(arr[integer++], false);
         var7.removeObjectiveFromEntity(var8, var9);
         notifyOperators(iCommandSender, this, "commands.scoreboard.players.resetscore.success", var9.getName(), var8);
      } else {
         var7.removeObjectiveFromEntity(var8, null);
         notifyOperators(iCommandSender, this, "commands.scoreboard.players.reset.success", var8);
      }
   }

   protected void func_175779_n(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var6 = this.getScoreboard();
      String var7 = getPlayerName(iCommandSender, arr[integer++]);
      if (var7.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", var7, 40);
      }

      ScoreObjective var8 = this.getObjective(arr[integer], false);
      if (var8.getCriteria() != IScoreObjectiveCriteria.TRIGGER) {
         throw new CommandException("commands.scoreboard.players.enable.noTrigger", var8.getName());
      }

      var6.Method281(var7, var8).setLocked(false);
      notifyOperators(iCommandSender, this, "commands.scoreboard.players.enable.success", var8.getName(), var7);
   }

   protected void func_175781_o(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      String var8 = getEntityName(iCommandSender, arr[integer++]);
      if (var8.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", var8, 40);
      }

      ScoreObjective var9 = this.getObjective(arr[integer++], false);
      if (!var7.entityHasObjective(var8, var9)) {
         throw new CommandException("commands.scoreboard.players.test.notFound", var9.getName(), var8);
      }

      int var10 = arr[integer].equals("*") ? Integer.MIN_VALUE : parseInt(arr[integer]);
      integer++;
      int var11 = integer < arr.length && !arr[integer].equals("*") ? parseInt(arr[integer], var10) : Integer.MAX_VALUE;
      Score var12 = var7.Method281(var8, var9);
      if (var12.getScorePoints() >= var10 && var12.getScorePoints() <= var11) {
         notifyOperators(iCommandSender, this, "commands.scoreboard.players.test.success", var12.getScorePoints(), var10, var11);
      } else {
         throw new CommandException("commands.scoreboard.players.test.failed", var12.getScorePoints(), var10, var11);
      }
   }

   protected void func_175778_p(ICommandSender iCommandSender, String[] arr, int integer) throws CommandException {
      Scoreboard var7 = this.getScoreboard();
      String var8 = getEntityName(iCommandSender, arr[integer++]);
      ScoreObjective var9 = this.getObjective(arr[integer++], true);
      String var10 = arr[integer++];
      String var11 = getEntityName(iCommandSender, arr[integer++]);
      ScoreObjective var12 = this.getObjective(arr[integer], false);
      if (var8.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", var8, 40);
      }

      if (var11.length() > 40) {
         throw new SyntaxErrorException("commands.scoreboard.players.name.tooLong", var11, 40);
      }

      Score var13 = var7.Method281(var8, var9);
      if (!var7.entityHasObjective(var11, var12)) {
         throw new CommandException("commands.scoreboard.players.operation.notFound", var12.getName(), var11);
      }

      Score var14 = var7.Method281(var11, var12);
      if (var10.equals("+=")) {
         var13.setScorePoints(var13.getScorePoints() + var14.getScorePoints());
      } else if (var10.equals("-=")) {
         var13.setScorePoints(var13.getScorePoints() - var14.getScorePoints());
      } else if (var10.equals("*=")) {
         var13.setScorePoints(var13.getScorePoints() * var14.getScorePoints());
      } else if (var10.equals("/=")) {
         if (var14.getScorePoints() != 0) {
            var13.setScorePoints(var13.getScorePoints() / var14.getScorePoints());
         }
      } else if (var10.equals("%=")) {
         if (var14.getScorePoints() != 0) {
            var13.setScorePoints(var13.getScorePoints() % var14.getScorePoints());
         }
      } else if (var10.equals("=")) {
         var13.setScorePoints(var14.getScorePoints());
      } else if (var10.equals("<")) {
         var13.setScorePoints(Math.min(var13.getScorePoints(), var14.getScorePoints()));
      } else if (var10.equals(">")) {
         var13.setScorePoints(Math.max(var13.getScorePoints(), var14.getScorePoints()));
      } else {
         if (!var10.equals("><")) {
            throw new CommandException("commands.scoreboard.players.operation.invalidOperation", var10);
         }

         int var15 = var13.getScorePoints();
         var13.setScorePoints(var14.getScorePoints());
         var14.setScorePoints(var15);
      }

      notifyOperators(iCommandSender, this, "commands.scoreboard.players.operation.success");
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      if (arr.length == 1) {
         return getListOfStringsMatchingLastWord(arr, "objectives", "players", "teams");
      }

      if (arr[0].equalsIgnoreCase("objectives")) {
         if (arr.length == 2) {
            return getListOfStringsMatchingLastWord(arr, "list", "add", "remove", "setdisplay");
         }

         if (arr[1].equalsIgnoreCase("add")) {
            if (arr.length == 4) {
               Set var7 = IScoreObjectiveCriteria.INSTANCES.keySet();
               return getListOfStringsMatchingLastWord(arr, var7);
            }
         } else if (arr[1].equalsIgnoreCase("remove")) {
            if (arr.length == 3) {
               return getListOfStringsMatchingLastWord(arr, this.func_147184_a(false));
            }
         } else if (arr[1].equalsIgnoreCase("setdisplay")) {
            if (arr.length == 3) {
               return getListOfStringsMatchingLastWord(arr, Scoreboard.getDisplaySlotStrings());
            }

            if (arr.length == 4) {
               return getListOfStringsMatchingLastWord(arr, this.func_147184_a(false));
            }
         }
      } else if (arr[0].equalsIgnoreCase("players")) {
         if (arr.length == 2) {
            return getListOfStringsMatchingLastWord(arr, "set", "add", "remove", "reset", "list", "enable", "test", "operation");
         }

         if (!arr[1].equalsIgnoreCase("set") && !arr[1].equalsIgnoreCase("add") && !arr[1].equalsIgnoreCase("remove") && !arr[1].equalsIgnoreCase("reset")) {
            if (arr[1].equalsIgnoreCase("enable")) {
               if (arr.length == 3) {
                  return getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames());
               }

               if (arr.length == 4) {
                  return getListOfStringsMatchingLastWord(arr, this.func_175782_e());
               }
            } else if (!arr[1].equalsIgnoreCase("list") && !arr[1].equalsIgnoreCase("test")) {
               if (arr[1].equalsIgnoreCase("operation")) {
                  if (arr.length == 3) {
                     return getListOfStringsMatchingLastWord(arr, this.getScoreboard().getObjectiveNames());
                  }

                  if (arr.length == 4) {
                     return getListOfStringsMatchingLastWord(arr, this.func_147184_a(true));
                  }

                  if (arr.length == 5) {
                     return getListOfStringsMatchingLastWord(arr, "+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><");
                  }

                  if (arr.length == 6) {
                     return getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames());
                  }

                  if (arr.length == 7) {
                     return getListOfStringsMatchingLastWord(arr, this.func_147184_a(false));
                  }
               }
            } else {
               if (arr.length == 3) {
                  return getListOfStringsMatchingLastWord(arr, this.getScoreboard().getObjectiveNames());
               }

               if (arr.length == 4 && arr[1].equalsIgnoreCase("test")) {
                  return getListOfStringsMatchingLastWord(arr, this.func_147184_a(false));
               }
            }
         } else {
            if (arr.length == 3) {
               return getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames());
            }

            if (arr.length == 4) {
               return getListOfStringsMatchingLastWord(arr, this.func_147184_a(true));
            }
         }
      } else if (arr[0].equalsIgnoreCase("teams")) {
         if (arr.length == 2) {
            return getListOfStringsMatchingLastWord(arr, "add", "remove", "join", "leave", "empty", "list", "option");
         }

         if (arr[1].equalsIgnoreCase("join")) {
            if (arr.length == 3) {
               return getListOfStringsMatchingLastWord(arr, this.getScoreboard().getTeamNames());
            }

            if (arr.length >= 4) {
               return getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames());
            }
         } else {
            if (arr[1].equalsIgnoreCase("leave")) {
               return getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames());
            }

            if (!arr[1].equalsIgnoreCase("empty") && !arr[1].equalsIgnoreCase("list") && !arr[1].equalsIgnoreCase("remove")) {
               if (arr[1].equalsIgnoreCase("option")) {
                  if (arr.length == 3) {
                     return getListOfStringsMatchingLastWord(arr, this.getScoreboard().getTeamNames());
                  }

                  if (arr.length == 4) {
                     return getListOfStringsMatchingLastWord(
                        arr, "color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility"
                     );
                  }

                  if (arr.length == 5) {
                     if (arr[3].equalsIgnoreCase("color")) {
                        return getListOfStringsMatchingLastWord(arr, EnumChatFormatting.getValidValues(true, false));
                     }

                     if (arr[3].equalsIgnoreCase("nametagVisibility") || arr[3].equalsIgnoreCase("deathMessageVisibility")) {
                        return getListOfStringsMatchingLastWord(arr, Team.EnumVisible.func_178825_a());
                     }

                     if (arr[3].equalsIgnoreCase("friendlyfire") || arr[3].equalsIgnoreCase("seeFriendlyInvisibles")) {
                        return getListOfStringsMatchingLastWord(arr, "true", "false");
                     }
                  }
               }
            } else if (arr.length == 3) {
               return getListOfStringsMatchingLastWord(arr, this.getScoreboard().getTeamNames());
            }
         }
      }

      return null;
   }

   protected List<String> func_147184_a(boolean boolean1) {
      Collection<ScoreObjective> var5 = this.getScoreboard().getScoreObjectives();
      ArrayList<String> var6 = Lists.newArrayList();

      for (ScoreObjective var8 : var5) {
         if (!boolean1 || !var8.getCriteria().isReadOnly()) {
            var6.add(var8.getName());
         }
      }

      return var6;
   }

   protected List<String> func_175782_e() {
      Collection<ScoreObjective> var4 = this.getScoreboard().getScoreObjectives();
      ArrayList<String> var5 = Lists.newArrayList();

      for (ScoreObjective var7 : var4) {
         if (var7.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
            var5.add(var7.getName());
         }
      }

      return var5;
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return !arr[0].equalsIgnoreCase("players")
         ? arr[0].equalsIgnoreCase("teams") && integer == 2
         : (arr.length > 1 && arr[1].equalsIgnoreCase("operation") ? integer == 2 || integer == 5 : integer == 2);
   }
}
