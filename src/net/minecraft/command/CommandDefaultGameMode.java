package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

public class CommandDefaultGameMode extends CommandGameMode {
   @Override
   public String getCommandName() {
      return "defaultgamemode";
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.defaultgamemode.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length == 0) {
         throw new WrongUsageException("commands.defaultgamemode.usage");
      }

      WorldSettings.GameType var5 = this.getGameModeFromCommand(iCommandSender, arr[0]);
      this.setGameType(var5);
      notifyOperators(iCommandSender, this, "commands.defaultgamemode.success", new ChatComponentTranslation("gameMode." + var5.getName()));
   }

   protected void setGameType(WorldSettings.GameType gameType) {
      MinecraftServer var5 = MinecraftServer.getServer();
      var5.setGameType(gameType);
      if (var5.getForceGamemode()) {
         for (EntityPlayerMP var7 : MinecraftServer.getServer().getConfigurationManager().getPlayerList()) {
            var7.setGameType(gameType);
            var7.fallDistance = 0.0F;
         }
      }
   }
}
