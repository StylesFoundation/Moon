package net.minecraft.command.server;

import java.util.List;
import java.util.regex.Matcher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandPardonIp extends CommandBase {
   @Override
   public String getCommandName() {
      return "pardon-ip";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
      return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(iCommandSender);
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.unbanip.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length == 1 && arr[0].length() > 1) {
         Matcher var6 = CommandBanIp.field_147211_a.matcher(arr[0]);
         if (var6.matches()) {
            MinecraftServer.getServer().getConfigurationManager().getBannedIPs().removeEntry(arr[0]);
            notifyOperators(iCommandSender, this, "commands.unbanip.success", arr[0]);
         } else {
            throw new SyntaxErrorException("commands.unbanip.invalid");
         }
      } else {
         throw new WrongUsageException("commands.unbanip.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getConfigurationManager().getBannedIPs().getKeys()) : null;
   }
}
