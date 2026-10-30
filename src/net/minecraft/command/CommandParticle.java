package net.minecraft.command;

import java.util.List;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;

public class CommandParticle extends CommandBase {
   @Override
   public String getCommandName() {
      return "particle";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.particle.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 8) {
         throw new WrongUsageException("commands.particle.usage");
      }

      boolean var6 = false;
      EnumParticleTypes var7 = null;

      for (EnumParticleTypes var11 : EnumParticleTypes.values()) {
         if (var11.hasArguments()) {
            if (arr[0].startsWith(var11.getParticleName())) {
               var6 = true;
               var7 = var11;
               break;
            }
         } else if (arr[0].equals(var11.getParticleName())) {
            var6 = true;
            var7 = var11;
            break;
         }
      }

      if (!var6) {
         throw new CommandException("commands.particle.notFound", arr[0]);
      }

      String var32 = arr[0];
      Vec3 var33 = iCommandSender.getPositionVector();
      double var34 = (float)parseDouble(var33.xCoord, arr[1], true);
      double var12 = (float)parseDouble(var33.yCoord, arr[2], true);
      double var14 = (float)parseDouble(var33.zCoord, arr[3], true);
      double var16 = (float)parseDouble(arr[4]);
      double var18 = (float)parseDouble(arr[5]);
      double var20 = (float)parseDouble(arr[6]);
      double var22 = (float)parseDouble(arr[7]);
      int var24 = 0;
      if (arr.length > 8) {
         var24 = parseInt(arr[8], 0);
      }

      boolean var25 = arr.length > 9 && "force".equals(arr[9]);
      if (iCommandSender.getEntityWorld() instanceof WorldServer var27) {
         int[] var28 = new int[var7.getArgumentCount()];
         if (var7.hasArguments()) {
            String[] var29 = arr[0].split("_", 3);

            for (int var30 = 1; var30 < var29.length; var30++) {
               try {
                  var28[var30 - 1] = Integer.parseInt(var29[var30]);
               } catch (NumberFormatException var31) {
                  throw new CommandException("commands.particle.notFound", arr[0]);
               }
            }
         }

         var27.spawnParticle(var7, var25, var34, var12, var14, var24, var16, var18, var20, var22, var28);
         notifyOperators(iCommandSender, this, "commands.particle.success", var32, Math.max(var24, 1));
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, EnumParticleTypes.getParticleNames())
         : (
            arr.length > 1 && arr.length <= 4
               ? func_175771_a(arr, 1, blockPos)
               : (arr.length == 10 ? getListOfStringsMatchingLastWord(arr, "normal", "force") : null)
         );
   }
}
