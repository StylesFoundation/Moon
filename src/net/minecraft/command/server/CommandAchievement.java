package net.minecraft.command.server;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;

public class CommandAchievement extends CommandBase {
   @Override
   public String getCommandName() {
      return "achievement";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.achievement.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 2) {
         throw new WrongUsageException("commands.achievement.usage");
      }

      StatBase var6 = StatList.getOneShotStat(arr[1]);
      if (var6 == null && !arr[1].equals("*")) {
         throw new CommandException("commands.achievement.unknownAchievement", arr[1]);
      }

      EntityPlayerMP var7 = arr.length >= 3 ? getPlayer(iCommandSender, arr[2]) : getCommandSenderAsPlayer(iCommandSender);
      boolean var8 = arr[0].equalsIgnoreCase("give");
      boolean var9 = arr[0].equalsIgnoreCase("take");
      if (var8 || var9) {
         if (var6 == null) {
            if (var8) {
               for (Achievement var11 : AchievementList.achievementList) {
                  var7.triggerAchievement(var11);
               }

               notifyOperators(iCommandSender, this, "commands.achievement.give.success.all", var7.getName());
            } else if (var9) {
               for (Achievement var19 : Lists.reverse(AchievementList.achievementList)) {
                  var7.func_175145_a(var19);
               }

               notifyOperators(iCommandSender, this, "commands.achievement.take.success.all", var7.getName());
            }
         } else {
            if (var6 instanceof Achievement var18) {
               if (var8) {
                  if (var7.getStatFile().hasAchievementUnlocked(var18)) {
                     throw new CommandException("commands.achievement.alreadyHave", var7.getName(), var6.createChatComponent());
                  }

                  ArrayList<Achievement> var21 = Lists.newArrayList();

                  while (var18.parentAchievement != null && !var7.getStatFile().hasAchievementUnlocked(var18.parentAchievement)) {
                     var21.add(var18.parentAchievement);
                     var18 = var18.parentAchievement;
                  }

                  for (Achievement var24 : Lists.reverse(var21)) {
                     var7.triggerAchievement(var24);
                  }
               } else if (var9) {
                  if (!var7.getStatFile().hasAchievementUnlocked(var18)) {
                     throw new CommandException("commands.achievement.dontHave", var7.getName(), var6.createChatComponent());
                  }

                  ArrayList<Achievement> var20 = Lists.newArrayList(
                     Iterators.filter(
                        AchievementList.achievementList.iterator(),
                        achievement -> var7.getStatFile().hasAchievementUnlocked(achievement) && achievement != var6
                     )
                  );
                  ArrayList<Achievement> var12 = Lists.newArrayList(var20);

                  for (Achievement var14 : var20) {
                     Achievement var15 = var14;
                     boolean var16 = false;

                     while (var15 != null) {
                        if (var15 == var6) {
                           var16 = true;
                        }

                        var15 = var15.parentAchievement;
                     }

                     if (!var16) {
                        for (Achievement var26 = var14; var26 != null; var26 = var26.parentAchievement) {
                           var12.remove(var14);
                        }
                     }
                  }

                  for (Achievement var25 : var12) {
                     var7.func_175145_a(var25);
                  }
               }
            }

            if (var8) {
               var7.triggerAchievement(var6);
               notifyOperators(iCommandSender, this, "commands.achievement.give.success.one", var7.getName(), var6.createChatComponent());
            } else if (var9) {
               var7.func_175145_a(var6);
               notifyOperators(iCommandSender, this, "commands.achievement.take.success.one", var6.createChatComponent(), var7.getName());
            }
         }
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      if (arr.length == 1) {
         return getListOfStringsMatchingLastWord(arr, "give", "take");
      }

      if (arr.length != 2) {
         return arr.length == 3 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames()) : null;
      }

      ArrayList var7 = Lists.newArrayList();

      for (StatBase var9 : StatList.allStats) {
         var7.add(var9.statId);
      }

      return getListOfStringsMatchingLastWord(arr, var7);
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 2;
   }
}
