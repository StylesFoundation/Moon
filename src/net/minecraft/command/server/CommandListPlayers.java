package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListPlayers extends CommandBase {
   @Override
   public String getCommandName() {
      return "list";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 0;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.players.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      int var5 = MinecraftServer.getServer().getCurrentPlayerCount();
      iCommandSender.addChatMessage(new ChatComponentTranslation("commands.players.list", var5, MinecraftServer.getServer().getMaxPlayers()));
      iCommandSender.addChatMessage(
         new ChatComponentText(MinecraftServer.getServer().getConfigurationManager().func_181058_b(arr.length > 0 && "uuids".equalsIgnoreCase(arr[0])))
      );
      iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var5);
   }
}
