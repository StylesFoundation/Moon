package net.minecraft.command;

import java.util.List;
import java.util.Random;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.storage.WorldInfo;

public class CommandWeather extends CommandBase {
   @Override
   public String getCommandName() {
      return "weather";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.weather.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length >= 1 && arr.length <= 2) {
         int var6 = (300 + new Random().nextInt(600)) * 20;
         if (arr.length == 2) {
            var6 = parseInt(arr[1], 1, 1000000) * 20;
         }

         WorldInfo var8 = MinecraftServer.getServer().worldServers[0].getWorldInfo();
         if ("clear".equalsIgnoreCase(arr[0])) {
            var8.setCleanWeatherTime(var6);
            var8.setRainTime(0);
            var8.setThunderTime(0);
            var8.setRaining(false);
            var8.setThundering(false);
            notifyOperators(iCommandSender, this, "commands.weather.clear");
         } else if ("rain".equalsIgnoreCase(arr[0])) {
            var8.setCleanWeatherTime(0);
            var8.setRainTime(var6);
            var8.setThunderTime(var6);
            var8.setRaining(true);
            var8.setThundering(false);
            notifyOperators(iCommandSender, this, "commands.weather.rain");
         } else {
            if (!"thunder".equalsIgnoreCase(arr[0])) {
               throw new WrongUsageException("commands.weather.usage");
            }

            var8.setCleanWeatherTime(0);
            var8.setRainTime(var6);
            var8.setThunderTime(var6);
            var8.setRaining(true);
            var8.setThundering(true);
            notifyOperators(iCommandSender, this, "commands.weather.thunder");
         }
      } else {
         throw new WrongUsageException("commands.weather.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, "clear", "rain", "thunder") : null;
   }
}
