package net.minecraft.command;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class CommandHelp extends CommandBase {
   @Override
   public String getCommandName() {
      return "help";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 0;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.help.usage";
   }

   @Override
   public List<String> getCommandAliases() {
      return List.of("?");
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      List var6 = this.getSortedPossibleCommands(iCommandSender);
      int var8 = (var6.size() - 1) / 7;

      int var9;
      try {
         var9 = arr.length == 0 ? 0 : parseInt(arr[0], 1, var8 + 1) - 1;
      } catch (NumberInvalidException var15) {
         ICommand var12 = this.getCommands().get(arr[0]);
         if (var12 != null) {
            throw new WrongUsageException(var12.getCommandUsage(iCommandSender));
         }

         if (MathHelper.parseIntWithDefault(arr[0], -1) != -1) {
            throw var15;
         }

         throw new CommandNotFoundException();
      }

      int var10 = Math.min((var9 + 1) * 7, var6.size());
      ChatComponentTranslation var11 = new ChatComponentTranslation("commands.help.header", var9 + 1, var8 + 1);
      var11.getChatStyle().setColor(EnumChatFormatting.DARK_GREEN);
      iCommandSender.addChatMessage(var11);

      for (int var16 = var9 * 7; var16 < var10; var16++) {
         ICommand var13 = (ICommand)var6.get(var16);
         ChatComponentTranslation var14 = new ChatComponentTranslation(var13.getCommandUsage(iCommandSender));
         var14.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + var13.getCommandName() + " "));
         iCommandSender.addChatMessage(var14);
      }

      if (var9 == 0 && iCommandSender instanceof EntityPlayer) {
         ChatComponentTranslation var17 = new ChatComponentTranslation("commands.help.footer");
         var17.getChatStyle().setColor(EnumChatFormatting.GREEN);
         iCommandSender.addChatMessage(var17);
      }
   }

   protected List<ICommand> getSortedPossibleCommands(ICommandSender iCommandSender) {
      List<ICommand> var2 = MinecraftServer.getServer().getCommandManager().getPossibleCommands(iCommandSender);
      Collections.sort(var2);
      return var2;
   }

   protected Map<String, ICommand> getCommands() {
      return MinecraftServer.getServer().getCommandManager().getCommands();
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      if (arr.length == 1) {
         Set<String> var6 = this.getCommands().keySet();
         return getListOfStringsMatchingLastWord(arr, var6.toArray(new String[0]));
      } else {
         return null;
      }
   }
}
