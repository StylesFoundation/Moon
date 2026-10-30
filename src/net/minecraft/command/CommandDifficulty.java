package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class CommandDifficulty extends CommandBase {
   @Override
   public String getCommandName() {
      return "difficulty";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.difficulty.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length == 0) {
         throw new WrongUsageException("commands.difficulty.usage");
      }

      EnumDifficulty var5 = this.getDifficultyFromCommand(arr[0]);
      MinecraftServer.getServer().setDifficultyForAllWorlds(var5);
      notifyOperators(iCommandSender, this, "commands.difficulty.success", new ChatComponentTranslation(var5.getDifficultyResourceKey()));
   }

   protected EnumDifficulty getDifficultyFromCommand(String string) throws CommandException {
      return string.equalsIgnoreCase("peaceful") || string.equalsIgnoreCase("p")
         ? EnumDifficulty.PEACEFUL
         : (
            string.equalsIgnoreCase("easy") || string.equalsIgnoreCase("e")
               ? EnumDifficulty.EASY
               : (
                  !string.equalsIgnoreCase("normal") && !string.equalsIgnoreCase("n")
                     ? (
                        !string.equalsIgnoreCase("hard") && !string.equalsIgnoreCase("h")
                           ? EnumDifficulty.getDifficultyEnum(parseInt(string, 0, 3))
                           : EnumDifficulty.HARD
                     )
                     : EnumDifficulty.NORMAL
               )
         );
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, "peaceful", "easy", "normal", "hard") : null;
   }
}
