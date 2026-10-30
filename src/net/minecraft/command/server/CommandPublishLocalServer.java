package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings;

public class CommandPublishLocalServer extends CommandBase {
   @Override
   public String getCommandName() {
      return "publish";
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.publish.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      String var6 = MinecraftServer.getServer().shareToLAN(WorldSettings.GameType.SURVIVAL, false);
      if (var6 != null) {
         notifyOperators(iCommandSender, this, "commands.publish.started", var6);
      } else {
         notifyOperators(iCommandSender, this, "commands.publish.failed");
      }
   }
}
