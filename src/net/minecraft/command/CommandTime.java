package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandTime extends CommandBase {
   @Override
   public String getCommandName() {
      return "time";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.time.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length > 1) {
         if (arr[0].equals("set")) {
            int var9;
            if (arr[1].equals("day")) {
               var9 = 1000;
            } else if (arr[1].equals("night")) {
               var9 = 13000;
            } else {
               var9 = parseInt(arr[1], 0);
            }

            this.setTime(iCommandSender, var9);
            notifyOperators(iCommandSender, this, "commands.time.set", var9);
            return;
         }

         if (arr[0].equals("add")) {
            int var8 = parseInt(arr[1], 0);
            this.addTime(iCommandSender, var8);
            notifyOperators(iCommandSender, this, "commands.time.added", var8);
            return;
         }

         if (arr[0].equals("query")) {
            if (arr[1].equals("daytime")) {
               int var7 = (int)(iCommandSender.getEntityWorld().getWorldTime() % 2147483647L);
               iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var7);
               notifyOperators(iCommandSender, this, "commands.time.query", var7);
               return;
            }

            if (arr[1].equals("gametime")) {
               int var6 = (int)(iCommandSender.getEntityWorld().getTotalWorldTime() % 2147483647L);
               iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var6);
               notifyOperators(iCommandSender, this, "commands.time.query", var6);
               return;
            }
         }
      }

      throw new WrongUsageException("commands.time.usage");
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, "set", "add", "query")
         : (
            arr.length == 2 && arr[0].equals("set")
               ? getListOfStringsMatchingLastWord(arr, "day", "night")
               : (arr.length == 2 && arr[0].equals("query") ? getListOfStringsMatchingLastWord(arr, "daytime", "gametime") : null)
         );
   }

   protected void setTime(ICommandSender iCommandSender, int integer) {
      for (int var6 = 0; var6 < MinecraftServer.getServer().worldServers.length; var6++) {
         MinecraftServer.getServer().worldServers[var6].setWorldTime(integer);
      }
   }

   protected void addTime(ICommandSender iCommandSender, int integer) {
      for (int var6 = 0; var6 < MinecraftServer.getServer().worldServers.length; var6++) {
         WorldServer var7 = MinecraftServer.getServer().worldServers[var6];
         var7.setWorldTime(var7.getWorldTime() + integer);
      }
   }
}
