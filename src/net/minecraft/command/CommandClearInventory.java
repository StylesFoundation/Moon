package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandClearInventory extends CommandBase {
   @Override
   public String getCommandName() {
      return "clear";
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.clear.usage";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      EntityPlayerMP var6 = arr.length == 0 ? getCommandSenderAsPlayer(iCommandSender) : getPlayer(iCommandSender, arr[0]);
      Item var7 = arr.length >= 2 ? getItemByText(iCommandSender, arr[1]) : null;
      int var8 = arr.length >= 3 ? parseInt(arr[2], -1) : -1;
      int var9 = arr.length >= 4 ? parseInt(arr[3], -1) : -1;
      NBTTagCompound var10 = null;
      if (arr.length >= 5) {
         try {
            var10 = JsonToNBT.getTagFromJson(buildString(arr, 4));
         } catch (NBTException var12) {
            throw new CommandException("commands.clear.tagError", var12.getMessage());
         }
      }

      if (arr.length >= 2 && var7 == null) {
         throw new CommandException("commands.clear.failure", var6.getName());
      }

      int var11 = var6.inventory.clearMatchingItems(var7, var8, var9, var10);
      var6.inventoryContainer.detectAndSendChanges();
      if (!var6.capabilities.isCreativeMode) {
         var6.updateHeldItem();
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, var11);
      if (var11 == 0) {
         throw new CommandException("commands.clear.failure", var6.getName());
      }

      if (var9 == 0) {
         iCommandSender.addChatMessage(new ChatComponentTranslation("commands.clear.testing", var6.getName(), var11));
      } else {
         notifyOperators(iCommandSender, this, "commands.clear.success", var6.getName(), var11);
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, this.func_147209_d())
         : (arr.length == 2 ? getListOfStringsMatchingLastWord(arr, Item.itemRegistry.getKeys()) : null);
   }

   protected String[] func_147209_d() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
