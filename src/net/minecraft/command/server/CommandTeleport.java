package net.minecraft.command.server;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class CommandTeleport extends CommandBase {
   @Override
   public String getCommandName() {
      return "tp";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.tp.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 1) {
         throw new WrongUsageException("commands.tp.usage");
      }

      byte var6 = 0;
      Entity var7;
      if (arr.length != 2 && arr.length != 4 && arr.length != 6) {
         var7 = getCommandSenderAsPlayer(iCommandSender);
      } else {
         var7 = getEntity(iCommandSender, arr[0]);
         var6 = 1;
      }

      if (arr.length != 1 && arr.length != 2) {
         if (arr.length < var6 + 3) {
            throw new WrongUsageException("commands.tp.usage");
         }

         if (var7.worldObj != null) {
            int var17 = var6 + 1;
            CommandBase.CoordinateArg var9 = parseCoordinate(var7.posX, arr[var6], true);
            CommandBase.CoordinateArg var10 = parseCoordinate(var7.posY, arr[var17++], 0, 0, false);
            CommandBase.CoordinateArg var11 = parseCoordinate(var7.posZ, arr[var17++], true);
            CommandBase.CoordinateArg var12 = parseCoordinate(var7.rotationYaw, arr.length > var17 ? arr[var17++] : "~", false);
            CommandBase.CoordinateArg var13 = parseCoordinate(var7.rotationPitch, arr.length > var17 ? arr[var17] : "~", false);
            if (var7 instanceof EntityPlayerMP) {
               EnumSet var20 = EnumSet.noneOf(S08PacketPlayerPosLook.EnumFlags.class);
               if (var9.func_179630_c()) {
                  var20.add(S08PacketPlayerPosLook.EnumFlags.X);
               }

               if (var10.func_179630_c()) {
                  var20.add(S08PacketPlayerPosLook.EnumFlags.Y);
               }

               if (var11.func_179630_c()) {
                  var20.add(S08PacketPlayerPosLook.EnumFlags.Z);
               }

               if (var13.func_179630_c()) {
                  var20.add(S08PacketPlayerPosLook.EnumFlags.X_ROT);
               }

               if (var12.func_179630_c()) {
                  var20.add(S08PacketPlayerPosLook.EnumFlags.Y_ROT);
               }

               float var21 = (float)var12.func_179629_b();
               if (!var12.func_179630_c()) {
                  var21 = MathHelper.wrapAngleTo180_float(var21);
               }

               float var16 = (float)var13.func_179629_b();
               if (!var13.func_179630_c()) {
                  var16 = MathHelper.wrapAngleTo180_float(var16);
               }

               if (var16 > 90.0F || var16 < -90.0F) {
                  var16 = MathHelper.wrapAngleTo180_float(180.0F - var16);
                  var21 = MathHelper.wrapAngleTo180_float(var21 + 180.0F);
               }

               var7.mountEntity(null);
               ((EntityPlayerMP)var7)
                  .playerNetServerHandler
                  .setPlayerLocation(var9.func_179629_b(), var10.func_179629_b(), var11.func_179629_b(), var21, var16, var20);
               var7.setRotationYawHead(var21);
            } else {
               float var14 = (float)MathHelper.wrapAngleTo180_double(var12.func_179628_a());
               float var15 = (float)MathHelper.wrapAngleTo180_double(var13.func_179628_a());
               if (var15 > 90.0F || var15 < -90.0F) {
                  var15 = MathHelper.wrapAngleTo180_float(180.0F - var15);
                  var14 = MathHelper.wrapAngleTo180_float(var14 + 180.0F);
               }

               var7.setLocationAndAngles(var9.func_179628_a(), var10.func_179628_a(), var11.func_179628_a(), var14, var15);
               var7.setRotationYawHead(var14);
            }

            notifyOperators(
               iCommandSender, this, "commands.tp.success.coordinates", var7.getName(), var9.func_179628_a(), var10.func_179628_a(), var11.func_179628_a()
            );
         }
      } else {
         Entity var8 = getEntity(iCommandSender, arr[arr.length - 1]);
         if (var8.worldObj != var7.worldObj) {
            throw new CommandException("commands.tp.notSameDimension");
         }

         var7.mountEntity(null);
         if (var7 instanceof EntityPlayerMP) {
            ((EntityPlayerMP)var7).playerNetServerHandler.setPlayerLocation(var8.posX, var8.posY, var8.posZ, var8.rotationYaw, var8.rotationPitch);
         } else {
            var7.setLocationAndAngles(var8.posX, var8.posY, var8.posZ, var8.rotationYaw, var8.rotationPitch);
         }

         notifyOperators(iCommandSender, this, "commands.tp.success", var7.getName(), var8.getName());
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length != 1 && arr.length != 2 ? null : getListOfStringsMatchingLastWord(arr, MinecraftServer.getServer().getAllUsernames());
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
