package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.border.WorldBorder;

public class CommandWorldBorder extends CommandBase {
   @Override
   public String getCommandName() {
      return "worldborder";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.worldborder.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 1) {
         throw new WrongUsageException("commands.worldborder.usage");
      }

      WorldBorder var6 = this.getWorldBorder();
      if (arr[0].equals("set")) {
         if (arr.length != 2 && arr.length != 3) {
            throw new WrongUsageException("commands.worldborder.set.usage");
         }

         double var7 = var6.getTargetSize();
         double var9 = parseDouble(arr[1], 1.0, 6.0E7);
         long var11 = arr.length > 2 ? parseLong(arr[2], 0L, 9223372036854775L) * 1000L : 0L;
         if (var11 > 0L) {
            var6.setTransition(var7, var9, var11);
            if (var7 > var9) {
               notifyOperators(
                  iCommandSender,
                  this,
                  "commands.worldborder.setSlowly.shrink.success",
                  String.format("%.1f", var9),
                  String.format("%.1f", var7),
                  Long.toString(var11 / 1000L)
               );
            } else {
               notifyOperators(
                  iCommandSender,
                  this,
                  "commands.worldborder.setSlowly.grow.success",
                  String.format("%.1f", var9),
                  String.format("%.1f", var7),
                  Long.toString(var11 / 1000L)
               );
            }
         } else {
            var6.setTransition(var9);
            notifyOperators(iCommandSender, this, "commands.worldborder.set.success", String.format("%.1f", var9), String.format("%.1f", var7));
         }
      } else if (arr[0].equals("add")) {
         if (arr.length != 2 && arr.length != 3) {
            throw new WrongUsageException("commands.worldborder.add.usage");
         }

         double var13 = var6.getDiameter();
         double var21 = var13 + parseDouble(arr[1], -var13, 6.0E7 - var13);
         long var24 = var6.getTimeUntilTarget() + (arr.length > 2 ? parseLong(arr[2], 0L, 9223372036854775L) * 1000L : 0L);
         if (var24 > 0L) {
            var6.setTransition(var13, var21, var24);
            if (var13 > var21) {
               notifyOperators(
                  iCommandSender,
                  this,
                  "commands.worldborder.setSlowly.shrink.success",
                  String.format("%.1f", var21),
                  String.format("%.1f", var13),
                  Long.toString(var24 / 1000L)
               );
            } else {
               notifyOperators(
                  iCommandSender,
                  this,
                  "commands.worldborder.setSlowly.grow.success",
                  String.format("%.1f", var21),
                  String.format("%.1f", var13),
                  Long.toString(var24 / 1000L)
               );
            }
         } else {
            var6.setTransition(var21);
            notifyOperators(iCommandSender, this, "commands.worldborder.set.success", String.format("%.1f", var21), String.format("%.1f", var13));
         }
      } else if (arr[0].equals("center")) {
         if (arr.length != 3) {
            throw new WrongUsageException("commands.worldborder.center.usage");
         }

         BlockPos var14 = iCommandSender.getPosition();
         double var8 = parseDouble(var14.getX() + 0.5, arr[1], true);
         double var10 = parseDouble(var14.getZ() + 0.5, arr[2], true);
         var6.setCenter(var8, var10);
         notifyOperators(iCommandSender, this, "commands.worldborder.center.success", var8, var10);
      } else if (arr[0].equals("damage")) {
         if (arr.length < 2) {
            throw new WrongUsageException("commands.worldborder.damage.usage");
         }

         if (arr[1].equals("buffer")) {
            if (arr.length != 3) {
               throw new WrongUsageException("commands.worldborder.damage.buffer.usage");
            }

            double var15 = parseDouble(arr[2], 0.0);
            double var22 = var6.getDamageBuffer();
            var6.setDamageBuffer(var15);
            notifyOperators(iCommandSender, this, "commands.worldborder.damage.buffer.success", String.format("%.1f", var15), String.format("%.1f", var22));
         } else if (arr[1].equals("amount")) {
            if (arr.length != 3) {
               throw new WrongUsageException("commands.worldborder.damage.amount.usage");
            }

            double var16 = parseDouble(arr[2], 0.0);
            double var23 = var6.getDamageAmount();
            var6.setDamageAmount(var16);
            notifyOperators(iCommandSender, this, "commands.worldborder.damage.amount.success", String.format("%.2f", var16), String.format("%.2f", var23));
         }
      } else if (arr[0].equals("warning")) {
         if (arr.length < 2) {
            throw new WrongUsageException("commands.worldborder.warning.usage");
         }

         int var17 = parseInt(arr[2], 0);
         if (arr[1].equals("time")) {
            if (arr.length != 3) {
               throw new WrongUsageException("commands.worldborder.warning.time.usage");
            }

            int var19 = var6.getWarningTime();
            var6.setWarningTime(var17);
            notifyOperators(iCommandSender, this, "commands.worldborder.warning.time.success", var17, var19);
         } else if (arr[1].equals("distance")) {
            if (arr.length != 3) {
               throw new WrongUsageException("commands.worldborder.warning.distance.usage");
            }

            int var20 = var6.getWarningDistance();
            var6.setWarningDistance(var17);
            notifyOperators(iCommandSender, this, "commands.worldborder.warning.distance.success", var17, var20);
         }
      } else {
         if (!arr[0].equals("get")) {
            throw new WrongUsageException("commands.worldborder.usage");
         }

         double var18 = var6.getDiameter();
         iCommandSender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, MathHelper.floor_double(var18 + 0.5));
         iCommandSender.addChatMessage(new ChatComponentTranslation("commands.worldborder.get.success", String.format("%.0f", var18)));
      }
   }

   protected WorldBorder getWorldBorder() {
      return MinecraftServer.getServer().worldServers[0].getWorldBorder();
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, "set", "center", "damage", "warning", "add", "get")
         : (
            arr.length == 2 && arr[0].equals("damage")
               ? getListOfStringsMatchingLastWord(arr, "buffer", "amount")
               : (
                  arr.length >= 2 && arr.length <= 3 && arr[0].equals("center")
                     ? func_181043_b(arr, 1, blockPos)
                     : (arr.length == 2 && arr[0].equals("warning") ? getListOfStringsMatchingLastWord(arr, "time", "distance") : null)
               )
         );
   }
}
