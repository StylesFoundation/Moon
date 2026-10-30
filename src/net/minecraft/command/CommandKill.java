package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandKill extends CommandBase {
   @Override
   public String getCommandName() {
      return "kill";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.kill.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length == 0) {
         EntityPlayerMP var6 = getCommandSenderAsPlayer(iCommandSender);
         var6.onKillCommand();
         notifyOperators(iCommandSender, this, "commands.kill.successful", var6.getDisplayName());
      } else {
         Entity var7 = getEntity(iCommandSender, arr[0]);
         var7.onKillCommand();
         notifyOperators(iCommandSender, this, "commands.kill.successful", var7.getDisplayName());
      }
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames()) : null;
   }
}
