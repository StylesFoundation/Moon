package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandXP extends CommandBase {
   @Override
   public String getCommandName() {
      return "xp";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.xp.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length == 0) {
         throw new WrongUsageException("commands.xp.usage");
      }

      String var6 = arr[0];
      boolean var7 = var6.endsWith("l") || var6.endsWith("L");
      if (var7 && var6.length() > 1) {
         var6 = var6.substring(0, var6.length() - 1);
      }

      int var8 = parseInt(var6);
      boolean var9 = var8 < 0;
      if (var9) {
         var8 *= -1;
      }

      EntityPlayerMP var10 = arr.length > 1 ? getPlayer(iCommandSender, arr[1]) : getCommandSenderAsPlayer(iCommandSender);
      if (var7) {
         iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var10.experienceLevel);
         if (var9) {
            var10.addExperienceLevel(-var8);
            notifyOperators(iCommandSender, this, "commands.xp.success.negative.levels", var8, var10.getName());
         } else {
            var10.addExperienceLevel(var8);
            notifyOperators(iCommandSender, this, "commands.xp.success.levels", var8, var10.getName());
         }
      } else {
         iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var10.experienceTotal);
         if (var9) {
            throw new CommandException("commands.xp.failure.widthdrawXp");
         }

         var10.addExperience(var8);
         notifyOperators(iCommandSender, this, "commands.xp.success", var8, var10.getName());
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 2 ? getListOfStringsMatchingLastWord(arr, this.getAllUsernames()) : null;
   }

   protected String[] getAllUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 1;
   }
}
