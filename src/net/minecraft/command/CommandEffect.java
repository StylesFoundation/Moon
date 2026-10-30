package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandEffect extends CommandBase {
   @Override
   public String getCommandName() {
      return "effect";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.effect.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 2) {
         throw new WrongUsageException("commands.effect.usage");
      }

      EntityLivingBase var6 = getEntity(iCommandSender, arr[0], EntityLivingBase.class);
      if (arr[1].equals("clear")) {
         if (var6.getActivePotionEffects().isEmpty()) {
            throw new CommandException("commands.effect.failure.notActive.all", var6.getName());
         }

         var6.clearActivePotions();
         notifyOperators(iCommandSender, this, "commands.effect.success.removed.all", var6.getName());
      } else {
         int var7;
         try {
            var7 = parseInt(arr[1], 1);
         } catch (NumberInvalidException var14) {
            Potion var9 = Potion.getPotionFromResourceLocation(arr[1]);
            if (var9 == null) {
               throw var14;
            }

            var7 = var9.id;
         }

         int var8 = 600;
         int var15 = 30;
         int var10 = 0;
         if (var7 < 0 || var7 >= Potion.potionTypes.length || Potion.potionTypes[var7] == null) {
            throw new NumberInvalidException("commands.effect.notFound", var7);
         }

         Potion var11 = Potion.potionTypes[var7];
         if (arr.length >= 3) {
            var15 = parseInt(arr[2], 0, 1000000);
            if (var11.isInstant()) {
               var8 = var15;
            } else {
               var8 = var15 * 20;
            }
         } else if (var11.isInstant()) {
            var8 = 1;
         }

         if (arr.length >= 4) {
            var10 = parseInt(arr[3], 0, 255);
         }

         boolean var12 = arr.length < 5 || !"true".equalsIgnoreCase(arr[4]);
         if (var15 > 0) {
            PotionEffect var13 = new PotionEffect(var7, var8, var10, false, var12);
            var6.addPotionEffect(var13);
            notifyOperators(
               iCommandSender, this, "commands.effect.success", new ChatComponentTranslation(var13.getEffectName()), var7, var10, var6.getName(), var15
            );
         } else {
            if (!var6.isPotionActive(var7)) {
               throw new CommandException("commands.effect.failure.notActive", new ChatComponentTranslation(var11.getName()), var6.getName());
            }

            var6.removePotionEffect(var7);
            notifyOperators(iCommandSender, this, "commands.effect.success.removed", new ChatComponentTranslation(var11.getName()), var6.getName());
         }
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, this.getAllUsernames())
         : (
            arr.length == 2
               ? getListOfStringsMatchingLastWord(arr, Potion.getPotionLocations())
               : (arr.length == 5 ? getListOfStringsMatchingLastWord(arr, "true", "false") : null)
         );
   }

   protected String[] getAllUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
