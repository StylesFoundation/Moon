package net.minecraft.command.server;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class CommandBanIp extends CommandBase {
   public static final Pattern field_147211_a = Pattern.compile(
      "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
   );

   @Override
   public String getCommandName() {
      return "ban-ip";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 3;
   }

   @Override
   public boolean canCommandSenderUseCommand(ICommandSender iCommandSender) {
      return MinecraftServer.getServer().getConfigurationManager().getBannedIPs().isLanServer() && super.canCommandSenderUseCommand(iCommandSender);
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.banip.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length >= 1 && arr[0].length() > 1) {
         IChatComponent var6 = arr.length >= 2 ? getChatComponentFromNthArg(iCommandSender, arr, 1) : null;
         Matcher var7 = field_147211_a.matcher(arr[0]);
         if (var7.matches()) {
            this.func_147210_a(iCommandSender, arr[0], var6 == null ? null : var6.getUnformattedText());
         } else {
            EntityPlayerMP var8 = MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(arr[0]);
            if (var8 == null) {
               throw new PlayerNotFoundException("commands.banip.invalid");
            }

            this.func_147210_a(iCommandSender, var8.getPlayerIP(), var6 == null ? null : var6.getUnformattedText());
         }
      } else {
         throw new WrongUsageException("commands.banip.usage");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames()) : null;
   }

   protected void func_147210_a(ICommandSender iCommandSender, String string2, String string3) {
      IPBanEntry var7 = new IPBanEntry(string2, null, iCommandSender.getName(), null, string3);
      MinecraftServer.getServer().getConfigurationManager().getBannedIPs().addEntry(var7);
      List<EntityPlayerMP> var8 = MinecraftServer.getServer().getConfigurationManager().getPlayersMatchingAddress(string2);
      String[] var9 = new String[var8.size()];
      int var10 = 0;

      for (EntityPlayerMP var12 : var8) {
         var12.playerNetServerHandler.kickPlayerFromServer("You have been IP banned.");
         var9[var10++] = var12.getName();
      }

      if (var8.isEmpty()) {
         notifyOperators(iCommandSender, this, "commands.banip.success", string2);
      } else {
         notifyOperators(iCommandSender, this, "commands.banip.success.players", string2, joinNiceString(var9));
      }
   }
}
