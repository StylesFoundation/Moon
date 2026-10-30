package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandWhitelist extends CommandBase {
   @Override
   public String getCommandName() {
      return "whitelist";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.whitelist.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 1) {
         throw new WrongUsageException("commands.whitelist.usage");
      }

      MinecraftServer var6 = MinecraftServer.getServer();
      if (arr[0].equals("on")) {
         var6.getConfigurationManager().setWhiteListEnabled(true);
         notifyOperators(iCommandSender, this, "commands.whitelist.enabled");
      } else if (arr[0].equals("off")) {
         var6.getConfigurationManager().setWhiteListEnabled(false);
         notifyOperators(iCommandSender, this, "commands.whitelist.disabled");
      } else if (arr[0].equals("list")) {
         iCommandSender.addChatMessage(
            new ChatComponentTranslation(
               "commands.whitelist.list",
               var6.getConfigurationManager().getWhitelistedPlayerNames().length,
               var6.getConfigurationManager().getAvailablePlayerDat().length
            )
         );
         String[] var7 = var6.getConfigurationManager().getWhitelistedPlayerNames();
         iCommandSender.addChatMessage(new ChatComponentText(joinNiceString(var7)));
      } else if (arr[0].equals("add")) {
         if (arr.length < 2) {
            throw new WrongUsageException("commands.whitelist.add.usage");
         }

         GameProfile var8 = var6.getPlayerProfileCache().getGameProfileForUsername(arr[1]);
         if (var8 == null) {
            throw new CommandException("commands.whitelist.add.failed", arr[1]);
         }

         var6.getConfigurationManager().addWhitelistedPlayer(var8);
         notifyOperators(iCommandSender, this, "commands.whitelist.add.success", arr[1]);
      } else if (arr[0].equals("remove")) {
         if (arr.length < 2) {
            throw new WrongUsageException("commands.whitelist.remove.usage");
         }

         GameProfile var9 = var6.getConfigurationManager().getWhitelistedPlayers().getBannedProfile(arr[1]);
         if (var9 == null) {
            throw new CommandException("commands.whitelist.remove.failed", arr[1]);
         }

         var6.getConfigurationManager().removePlayerFromWhitelist(var9);
         notifyOperators(iCommandSender, this, "commands.whitelist.remove.success", arr[1]);
      } else if (arr[0].equals("reload")) {
         var6.getConfigurationManager().loadWhiteList();
         notifyOperators(iCommandSender, this, "commands.whitelist.reloaded");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      if (arr.length == 1) {
         return getListOfStringsMatchingLastWord(arr, "on", "off", "list", "add", "remove", "reload");
      }

      if (arr.length == 2) {
         if (arr[0].equals("remove")) {
            return getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getConfigurationManager().getWhitelistedPlayerNames());
         }

         if (arr[0].equals("add")) {
            return getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getPlayerProfileCache().getUsernames());
         }
      }

      return null;
   }
}
