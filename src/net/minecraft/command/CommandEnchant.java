package net.minecraft.command;

import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandEnchant extends CommandBase {
   @Override
   public String getCommandName() {
      return "enchant";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.enchant.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 2) {
         throw new WrongUsageException("commands.enchant.usage");
      }

      EntityPlayerMP var6 = getPlayer(iCommandSender, arr[0]);
      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);

      int var7;
      try {
         var7 = parseInt(arr[1], 0);
      } catch (NumberInvalidException var15) {
         Enchantment var9 = Enchantment.getEnchantmentByLocation(arr[1]);
         if (var9 == null) {
            throw var15;
         }

         var7 = var9.effectId;
      }

      int var8 = 1;
      ItemStack var16 = var6.getCurrentEquippedItem();
      if (var16 == null) {
         throw new CommandException("commands.enchant.noItem");
      }

      Enchantment var10 = Enchantment.getEnchantmentById(var7);
      if (var10 == null) {
         throw new NumberInvalidException("commands.enchant.notFound", var7);
      }

      if (!var10.canApply(var16)) {
         throw new CommandException("commands.enchant.cantEnchant");
      }

      if (arr.length >= 3) {
         var8 = parseInt(arr[2], var10.getMinLevel(), var10.getMaxLevel());
      }

      if (var16.hasTagCompound()) {
         NBTTagList var11 = var16.getEnchantmentTagList();
         if (var11 != null) {
            for (int var12 = 0; var12 < var11.tagCount(); var12++) {
               short var13 = var11.getCompoundTagAt(var12).getShort("id");
               if (Enchantment.getEnchantmentById(var13) != null) {
                  Enchantment var14 = Enchantment.getEnchantmentById(var13);
                  if (!var14.canApplyTogether(var10)) {
                     throw new CommandException(
                        "commands.enchant.cantCombine", var10.Method532(var8), var14.Method532(var11.getCompoundTagAt(var12).getShort("lvl"))
                     );
                  }
               }
            }
         }
      }

      var16.addEnchantment(var10, var8);
      notifyOperators(iCommandSender, this, "commands.enchant.success");
      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 1);
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, this.getListOfPlayers())
         : (arr.length == 2 ? getListOfStringsMatchingLastWord(arr, Enchantment.func_181077_c()) : null);
   }

   protected String[] getListOfPlayers() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
