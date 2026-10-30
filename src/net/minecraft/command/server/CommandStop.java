package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStop extends CommandBase {
   @Override
   public String getCommandName() {
      return "stop";
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.stop.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (MinecraftServer.getServer().worldServers != null) {
         notifyOperators(iCommandSender, this, "commands.stop.start");
      }

      MinecraftServer.getServer().initiateShutdown();
   }
}
