package net.minecraft.command;

import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandTitle extends CommandBase {
   private static final Logger LOGGER = LogManager.getLogger();

   @Override
   public String getCommandName() {
      return "title";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.title.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 2) {
         throw new WrongUsageException("commands.title.usage");
      }

      if (arr.length < 3) {
         if ("title".equals(arr[1]) || "subtitle".equals(arr[1])) {
            throw new WrongUsageException("commands.title.usage.title");
         }

         if ("times".equals(arr[1])) {
            throw new WrongUsageException("commands.title.usage.times");
         }
      }

      EntityPlayerMP var6 = getPlayer(iCommandSender, arr[0]);
      S45PacketTitle.Type var7 = S45PacketTitle.Type.byName(arr[1]);
      if (var7 == S45PacketTitle.Type.CLEAR || var7 == S45PacketTitle.Type.RESET) {
         if (arr.length != 2) {
            throw new WrongUsageException("commands.title.usage");
         }

         S45PacketTitle var14 = new S45PacketTitle(var7, null);
         var6.playerNetServerHandler.sendPacket(var14);
         notifyOperators(iCommandSender, this, "commands.title.success");
      } else if (var7 == S45PacketTitle.Type.TIMES) {
         if (arr.length != 5) {
            throw new WrongUsageException("commands.title.usage");
         }

         int var8 = parseInt(arr[2]);
         int var9 = parseInt(arr[3]);
         int var10 = parseInt(arr[4]);
         S45PacketTitle var11 = new S45PacketTitle(var8, var9, var10);
         var6.playerNetServerHandler.sendPacket(var11);
         notifyOperators(iCommandSender, this, "commands.title.success");
      } else {
         if (arr.length < 3) {
            throw new WrongUsageException("commands.title.usage");
         }

         String var13 = buildString(arr, 2);

         IChatComponent var15;
         try {
            var15 = IChatComponent.Serializer.jsonToComponent(var13);
         } catch (JsonParseException var12) {
            Throwable var17 = ExceptionUtils.getRootCause(var12);
            throw new SyntaxErrorException("commands.tellraw.jsonException", var17 == null ? "" : var17.getMessage());
         }

         S45PacketTitle var16 = new S45PacketTitle(var7, ChatComponentProcessor.processComponent(iCommandSender, var15, var6));
         var6.playerNetServerHandler.sendPacket(var16);
         notifyOperators(iCommandSender, this, "commands.title.success");
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames())
         : (arr.length == 2 ? getListOfStringsMatchingLastWord(arr, S45PacketTitle.Type.getNames()) : null);
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
