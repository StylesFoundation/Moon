package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout extends CommandBase {
   @Override
   public String getCommandName() {
      return "setidletimeout";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.setidletimeout.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length != 1) {
         throw new WrongUsageException("commands.setidletimeout.usage");
      }

      int var6 = parseInt(arr[0], 0);
      MinecraftServer.getServer().setPlayerIdleTimeout(var6);
      notifyOperators(iCommandSender, this, "commands.setidletimeout.success", var6);
   }
}
