package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.GameRules;

public class CommandGameRule extends CommandBase {
   @Override
   public String getCommandName() {
      return "gamerule";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.gamerule.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      GameRules var6 = this.getGameRules();
      String var7 = arr.length > 0 ? arr[0] : "";
      String var8 = arr.length > 1 ? buildString(arr, 1) : "";
      switch (arr.length) {
         case 0:
            iCommandSender.addChatMessage(new ChatComponentText(joinNiceString(var6.getRules())));
            break;
         case 1:
            if (!var6.hasRule(var7)) {
               throw new CommandException("commands.gamerule.norule", var7);
            }

            String var9 = var6.getString(var7);
            iCommandSender.addChatMessage(new ChatComponentText(var7).appendText(" = ").appendText(var9));
            iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, var6.getInt(var7));
            break;
         default:
            if (var6.areSameType(var7, GameRules.ValueType.BOOLEAN_VALUE) && !"true".equals(var8) && !"false".equals(var8)) {
               throw new CommandException("commands.generic.boolean.invalid", var8);
            }

            var6.setOrCreateGameRule(var7, var8);
            func_175773_a(var6, var7);
            notifyOperators(iCommandSender, this, "commands.gamerule.success");
      }
   }

   public static void func_175773_a(GameRules gameRules, String string) {
      if ("reducedDebugInfo".equals(string)) {
         byte var5 = (byte)(gameRules.getBoolean(string) ? 22 : 23);

         for (EntityPlayerMP var7 : MinecraftServer.getServer().getConfigurationManager().getPlayerList()) {
            var7.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(var7, var5));
         }
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      if (arr.length == 1) {
         return getListOfStringsMatchingLastWord(arr, this.getGameRules().getRules());
      }

      if (arr.length == 2) {
         GameRules var7 = this.getGameRules();
         if (var7.areSameType(arr[0], GameRules.ValueType.BOOLEAN_VALUE)) {
            return getListOfStringsMatchingLastWord(arr, "true", "false");
         }
      }

      return null;
   }

   private GameRules getGameRules() {
      return MinecraftServer.getServer().worldServerForDimension(0).getGameRules();
   }
}
