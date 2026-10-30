package net.minecraft.command.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandOp extends CommandBase {
   @Override
   public String getCommandName() {
      return "op";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.op.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length == 1 && arr[0].length() > 0) {
         MinecraftServer var6 = MinecraftServer.getServer();
         GameProfile var7 = var6.getPlayerProfileCache().getGameProfileForUsername(arr[0]);
         if (var7 == null) {
            throw new CommandException("commands.op.failed", arr[0]);
         }

         var6.getConfigurationManager().addOp(var7);
         notifyOperators(iCommandSender, this, "commands.op.success", arr[0]);
      } else {
         throw new WrongUsageException("commands.op.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      if (arr.length == 1) {
         String var7 = arr[arr.length - 1];
         ArrayList var8 = Lists.newArrayList();

         for (GameProfile var12 : MinecraftServer.getServer().getGameProfiles()) {
            if (!MinecraftServer.getServer().getConfigurationManager().canSendCommands(var12) && doesStringStartWith(var7, var12.getName())) {
               var8.add(var12.getName());
            }
         }

         return var8;
      } else {
         return null;
      }
   }
}
