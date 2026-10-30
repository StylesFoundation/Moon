package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase {
   @Override
   public String getCommandName() {
      return "save-all";
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.save.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      MinecraftServer var6 = MinecraftServer.getServer();
      iCommandSender.addChatMessage(new ChatComponentTranslation("commands.save.start"));
      if (var6.getConfigurationManager() != null) {
         var6.getConfigurationManager().saveAllPlayerData();
      }

      try {
         for (int var7 = 0; var7 < var6.worldServers.length; var7++) {
            if (var6.worldServers[var7] != null) {
               WorldServer var8 = var6.worldServers[var7];
               boolean var9 = var8.disableLevelSaving;
               var8.disableLevelSaving = false;
               var8.saveAllChunks(true, null);
               var8.disableLevelSaving = var9;
            }
         }

         if (arr.length > 0 && "flush".equals(arr[0])) {
            iCommandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushStart"));

            for (int var11 = 0; var11 < var6.worldServers.length; var11++) {
               if (var6.worldServers[var11] != null) {
                  WorldServer var12 = var6.worldServers[var11];
                  boolean var13 = var12.disableLevelSaving;
                  var12.disableLevelSaving = false;
                  var12.saveChunkData();
                  var12.disableLevelSaving = var13;
               }
            }

            iCommandSender.addChatMessage(new ChatComponentTranslation("commands.save.flushEnd"));
         }
      } catch (MinecraftException var10) {
         notifyOperators(iCommandSender, this, "commands.save.failed", var10.getMessage());
         return;
      }

      notifyOperators(iCommandSender, this, "commands.save.success");
   }
}
