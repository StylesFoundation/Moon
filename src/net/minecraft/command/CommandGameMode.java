package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

public class CommandGameMode extends CommandBase {
   @Override
   public String getCommandName() {
      return "gamemode";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.gamemode.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length == 0) {
         throw new WrongUsageException("commands.gamemode.usage");
      }

      WorldSettings.GameType var6 = this.getGameModeFromCommand(iCommandSender, arr[0]);
      EntityPlayerMP var7 = arr.length >= 2 ? getPlayer(iCommandSender, arr[1]) : getCommandSenderAsPlayer(iCommandSender);
      var7.setGameType(var6);
      var7.fallDistance = 0.0F;
      if (iCommandSender.getEntityWorld().getGameRules().getBoolean("sendCommandFeedback")) {
         var7.addChatMessage(new ChatComponentTranslation("gameMode.changed"));
      }

      ChatComponentTranslation var8 = new ChatComponentTranslation("gameMode." + var6.getName());
      if (var7 != iCommandSender) {
         notifyOperators(iCommandSender, this, 1, "commands.gamemode.success.other", var7.getName(), var8);
      } else {
         notifyOperators(iCommandSender, this, 1, "commands.gamemode.success.self", var8);
      }
   }

   protected WorldSettings.GameType getGameModeFromCommand(ICommandSender iCommandSender, String string) throws CommandException {
      return string.equalsIgnoreCase(WorldSettings.GameType.SURVIVAL.getName()) || string.equalsIgnoreCase("s")
         ? WorldSettings.GameType.SURVIVAL
         : (
            string.equalsIgnoreCase(WorldSettings.GameType.CREATIVE.getName()) || string.equalsIgnoreCase("c")
               ? WorldSettings.GameType.CREATIVE
               : (
                  string.equalsIgnoreCase(WorldSettings.GameType.ADVENTURE.getName()) || string.equalsIgnoreCase("a")
                     ? WorldSettings.GameType.ADVENTURE
                     : (
                        !string.equalsIgnoreCase(WorldSettings.GameType.SPECTATOR.getName()) && !string.equalsIgnoreCase("sp")
                           ? WorldSettings.getGameTypeById(parseInt(string, 0, WorldSettings.GameType.values().length - 2))
                           : WorldSettings.GameType.SPECTATOR
                     )
               )
         );
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, "survival", "creative", "adventure", "spectator")
         : (arr.length == 2 ? getListOfStringsMatchingLastWord(arr, this.getListOfPlayerUsernames()) : null);
   }

   protected String[] getListOfPlayerUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 1;
   }
}
