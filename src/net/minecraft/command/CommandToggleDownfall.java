package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandToggleDownfall extends CommandBase {
   @Override
   public String getCommandName() {
      return "toggledownfall";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.downfall.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      this.toggleDownfall();
      notifyOperators(iCommandSender, this, "commands.downfall.success");
   }

   protected void toggleDownfall() {
      WorldInfo var4 = MinecraftServer.getServer().worldServers[0].getWorldInfo();
      var4.setRaining(!var4.isRaining());
   }
}
