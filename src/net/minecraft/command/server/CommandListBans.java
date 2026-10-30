package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListBans extends CommandBase {
   @Override
   public String getCommandName() {
      return "banlist";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
      return (
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer()
               || MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer()
         )
         && super.canCommandSenderUseCommand(iCommandSender);
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.banlist.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length >= 1 && arr[0].equalsIgnoreCase("ips")) {
         iCommandSender.addChatMessage(
            new ChatComponentTranslation("commands.banlist.ips", MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys().length)
         );
         iCommandSender.addChatMessage(new ChatComponentText(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys())));
      } else {
         iCommandSender.addChatMessage(
            new ChatComponentTranslation("commands.banlist.players", MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys().length)
         );
         iCommandSender.addChatMessage(
            new ChatComponentText(joinNiceString(MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().getKeys()))
         );
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, "players", "ips") : null;
   }
}
