package net.minecraft.command;

import java.util.List;
import java.util.Map;
import net.minecraft.util.BlockPos;

public interface ICommandManager {
   int executeCommand(ICommandSender iCommandSender, String string);

   List<String> getTabCompletionOptions(ICommandSender iCommandSender, String string, BlockPos blockPos);

   List<ICommand> getPossibleCommands(ICommandSender iCommandSender);

   Map<String, ICommand> getCommands();
}
