package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.util.BlockPos;

public class CommandBanPlayer extends CommandBase {
   @Override
   public String getCommandName() {
      return "ban";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.ban.usage";
   }

   @Override
   public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
      return MinecraftServer.getServer().getConfigurationManager().getBannedPlayers().isLanServer() && super.canCommandSenderUseCommand(iCommandSender);
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length >= 1 && arr[0].length() > 0) {
         MinecraftServer var6 = MinecraftServer.getServer();
         GameProfile var7 = var6.getPlayerProfileCache().getGameProfileForUsername(arr[0]);
         if (var7 == null) {
            throw new CommandException("commands.ban.failed", arr[0]);
         }

         String var8 = null;
         if (arr.length >= 2) {
            var8 = getChatComponentFromNthArg(iCommandSender, arr, 1).getUnformattedText();
         }

         UserListBansEntry var9 = new UserListBansEntry(var7, null, iCommandSender.getName(), null, var8);
         var6.getConfigurationManager().getBannedPlayers().addEntry(var9);
         EntityPlayerMP var10 = var6.getConfigurationManager().getPlayerByUsername(arr[0]);
         if (var10 != null) {
            var10.playerNetServerHandler.kickPlayerFromServer("You are banned from this server.");
         }

         notifyOperators(iCommandSender, this, "commands.ban.success", arr[0]);
      } else {
         throw new WrongUsageException("commands.ban.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length >= 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames()) : null;
   }
}
