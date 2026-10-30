package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandServerKick extends CommandBase {
   @Override
   public String getCommandName() {
      return "kick";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.kick.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length > 0 && arr[0].length() > 1) {
         EntityPlayerMP var6 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(arr[0]);
         String var7 = "Kicked by an operator.";
         boolean var8 = false;
         if (var6 == null) {
            throw new PlayerNotFoundException();
         }

         if (arr.length >= 2) {
            var7 = getChatComponentFromNthArg(iCommandSender, arr, 1).getUnformattedText();
            var8 = true;
         }

         var6.playerNetServerHandler.kickPlayerFromServer(var7);
         if (var8) {
            notifyOperators(iCommandSender, this, "commands.kick.success.reason", var6.getName(), var7);
         } else {
            notifyOperators(iCommandSender, this, "commands.kick.success", var6.getName());
         }
      } else {
         throw new WrongUsageException("commands.kick.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length >= 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames()) : null;
   }
}
