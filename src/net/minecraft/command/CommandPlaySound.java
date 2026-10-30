package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;

public class CommandPlaySound extends CommandBase {
   @Override
   public String getCommandName() {
      return "playsound";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.playsound.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 2) {
         throw new WrongUsageException(this.getCommandUsage(iCommandSender));
      }

      int var6 = 0;
      String var7 = arr[var6++];
      EntityPlayerMP var8 = getPlayer(iCommandSender, arr[var6++]);
      Vec3 var9 = iCommandSender.getPositionVector();
      double var10 = var9.xCoord;
      if (arr.length > var6) {
         var10 = parseDouble(var10, arr[var6++], true);
      }

      double var12 = var9.yCoord;
      if (arr.length > var6) {
         var12 = parseDouble(var12, arr[var6++], 0, 0, false);
      }

      double var14 = var9.zCoord;
      if (arr.length > var6) {
         var14 = parseDouble(var14, arr[var6++], true);
      }

      double var16 = 1.0;
      if (arr.length > var6) {
         var16 = parseDouble(arr[var6++], 0.0, Float.MAX_VALUE);
      }

      double var18 = 1.0;
      if (arr.length > var6) {
         var18 = parseDouble(arr[var6++], 0.0, 2.0);
      }

      double var20 = 0.0;
      if (arr.length > var6) {
         var20 = parseDouble(arr[var6], 0.0, 1.0);
      }

      double var22 = var16 > 1.0 ? var16 * 16.0 : 16.0;
      double var24 = var8.getDistance(var10, var12, var14);
      if (var24 > var22) {
         if (var20 <= 0.0) {
            throw new CommandException("commands.playsound.playerTooFar", var8.getName());
         }

         double var26 = var10 - var8.posX;
         double var28 = var12 - var8.posY;
         double var30 = var14 - var8.posZ;
         double var32 = Math.sqrt(var26 * var26 + var28 * var28 + var30 * var30);
         if (var32 > 0.0) {
            var10 = var8.posX + var26 / var32 * 2.0;
            var12 = var8.posY + var28 / var32 * 2.0;
            var14 = var8.posZ + var30 / var32 * 2.0;
         }

         var16 = var20;
      }

      var8.playerNetServerHandler.sendPacket(new S29PacketSoundEffect(var7, var10, var12, var14, (float)var16, (float)var18));
      notifyOperators(iCommandSender, this, "commands.playsound.success", var7, var8.getName());
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 2
         ? getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames())
         : (arr.length > 2 && arr.length <= 5 ? func_175771_a(arr, 2, blockPos) : null);
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 1;
   }
}
