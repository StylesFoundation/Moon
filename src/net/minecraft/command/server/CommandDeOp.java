package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandDeOp extends CommandBase {
   @Override
   public String getCommandName() {
      return "deop";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.deop.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length == 1 && arr[0].length() > 0) {
         MinecraftServer var6 = MinecraftServer.getServer();
         GameProfile var7 = var6.getConfigurationManager().getOppedPlayers().getGameProfileFromName(arr[0]);
         if (var7 == null) {
            throw new CommandException("commands.deop.failed", arr[0]);
         }

         var6.getConfigurationManager().removeOp(var7);
         notifyOperators(iCommandSender, this, "commands.deop.success", arr[0]);
      } else {
         throw new WrongUsageException("commands.deop.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getConfigurationManager().getOppedPlayerNames()) : null;
   }
}
