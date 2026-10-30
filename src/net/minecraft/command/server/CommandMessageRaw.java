package net.minecraft.command.server;

import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class CommandMessageRaw extends CommandBase {
   @Override
   public String getCommandName() {
      return "tellraw";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.tellraw.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 2) {
         throw new WrongUsageException("commands.tellraw.usage");
      }

      EntityPlayerMP var5 = getPlayer(iCommandSender, arr[0]);
      String var6 = buildString(arr, 1);

      try {
         IChatComponent var7 = IChatComponent.Serializer.jsonToComponent(var6);
         var5.addChatMessage(ChatComponentProcessor.processComponent(iCommandSender, var7, var5));
      } catch (JsonParseException var9) {
         Throwable var8 = ExceptionUtils.getRootCause(var9);
         throw new SyntaxErrorException("commands.tellraw.jsonException", var8 == null ? "" : var8.getMessage());
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1 ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames()) : null;
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
