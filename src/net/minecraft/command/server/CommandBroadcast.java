package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandBroadcast extends CommandBase {
   @Override
   public String getCommandName() {
      return "say";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 1;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.say.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length > 0 && arr[0].length() > 0) {
         IChatComponent var6 = getChatComponentFromNthArg(iCommandSender, arr, 0, true);
         MinecraftServer.getServer()
            .getConfigurationManager()
            .sendChatMsg(new ChatComponentTranslation("chat.type.announcement", iCommandSender.getDisplayName(), var6));
      } else {
         throw new WrongUsageException("commands.say.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length >= 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames()) : null;
   }
}
