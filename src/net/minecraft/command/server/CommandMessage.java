package net.minecraft.command.server;

import java.util.Arrays;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CommandMessage extends CommandBase {
   @Override
   public List<String> getCommandAliases() {
      return Arrays.asList("w", "msg");
   }

   @Override
   public String getCommandName() {
      return "tell";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 0;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.message.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 2) {
         throw new WrongUsageException("commands.message.usage");
      }

      EntityPlayerMP var6 = getPlayer(iCommandSender, arr[0]);
      if (var6 == iCommandSender) {
         throw new PlayerNotFoundException("commands.message.sameTarget");
      }

      IChatComponent var7 = getChatComponentFromNthArg(iCommandSender, arr, 1, !(iCommandSender instanceof EntityPlayer));
      ChatComponentTranslation var8 = new ChatComponentTranslation("commands.message.display.incoming", iCommandSender.getDisplayName(), var7.createCopy());
      ChatComponentTranslation var9 = new ChatComponentTranslation("commands.message.display.outgoing", var6.getDisplayName(), var7.createCopy());
      var8.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.TRUE);
      var9.getChatStyle().setColor(EnumChatFormatting.GRAY).setItalic(Boolean.TRUE);
      var6.addChatMessage(var8);
      iCommandSender.addChatMessage(var9);
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames());
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
