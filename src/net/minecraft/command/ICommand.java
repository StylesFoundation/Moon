package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;

public interface ICommand extends Comparable<ICommand> {
   String getCommandName();

   String getCommandUsage(ICommandSender iCommandSender);

   List<String> getCommandAliases();

   void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException;

   boolean canCommandSenderUseCommand(ICommandSender iCommandSender);

   List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos);

   boolean isUsernameIndex(String[] arr, int integer);
}
