package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandTrigger extends CommandBase {
   @Override
   public String getCommandName() {
      return "trigger";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 0;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.trigger.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 3) {
         throw new WrongUsageException("commands.trigger.usage");
      } else {
         EntityPlayerMP var6;
         if (iCommandSender instanceof EntityPlayerMP) {
            var6 = (EntityPlayerMP)iCommandSender;
         } else {
            Entity var12 = iCommandSender.getCommandSenderEntity();
            if (!(var12 instanceof EntityPlayerMP)) {
               throw new CommandException("commands.trigger.invalidPlayer");
            }
            var6 = (EntityPlayerMP)var12;
         }

         Scoreboard var11 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
         ScoreObjective var8 = var11.getObjective(arr[0]);
         if (var8 != null && var8.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
            int var9 = parseInt(arr[2]);
            if (!var11.entityHasObjective(var6.getName(), var8)) {
               throw new CommandException("commands.trigger.invalidObjective", arr[0]);
            }

            Score var10 = var11.Method281(var6.getName(), var8);
            if (var10.isLocked()) {
               throw new CommandException("commands.trigger.disabled", arr[0]);
            }

            if ("set".equals(arr[1])) {
               var10.setScorePoints(var9);
            } else {
               if (!"add".equals(arr[1])) {
                  throw new CommandException("commands.trigger.invalidMode", arr[1]);
               }

               var10.increseScore(var9);
            }

            var10.setLocked(true);
            if (var6.theItemInWorldManager.isCreative()) {
               notifyOperators(iCommandSender, this, "commands.trigger.success", arr[0], arr[1], arr[2]);
            }
         } else {
            throw new CommandException("commands.trigger.invalidObjective", arr[0]);
         }
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      if (arr.length == 1) {
         Scoreboard var7 = MinecraftServer.getServer().worldServerForDimension(0).getScoreboard();
         ArrayList<String> var8 = Lists.newArrayList();

         for (ScoreObjective var10 : var7.getScoreObjectives()) {
            if (var10.getCriteria() == IScoreObjectiveCriteria.TRIGGER) {
               var8.add(var10.getName());
            }
         }

         return getListOfStringsMatchingLastWord(arr, var8.toArray(new String[0]));
      } else {
         return arr.length == 2 ? getListOfStringsMatchingLastWord(arr, "add", "set") : null;
      }
   }
}
