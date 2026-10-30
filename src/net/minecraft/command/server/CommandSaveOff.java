package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOff extends CommandBase {
   @Override
   public String getCommandName() {
      return "save-off";
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.save-off.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      MinecraftServer var6 = MinecraftServer.getServer();
      boolean var7 = false;

      for (int var8 = 0; var8 < var6.worldServers.length; var8++) {
         if (var6.worldServers[var8] != null) {
            WorldServer var9 = var6.worldServers[var8];
            if (!var9.disableLevelSaving) {
               var9.disableLevelSaving = true;
               var7 = true;
            }
         }
      }

      if (var7) {
         notifyOperators(iCommandSender, this, "commands.save.disabled");
      } else {
         throw new CommandException("commands.save-off.alreadyOff");
      }
   }
}
