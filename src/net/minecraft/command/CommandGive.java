package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandGive extends CommandBase {
   @Override
   public String getCommandName() {
      return "give";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.give.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 2) {
         throw new WrongUsageException("commands.give.usage");
      }

      EntityPlayerMP var6 = getPlayer(iCommandSender, arr[0]);
      Item var7 = getItemByText(iCommandSender, arr[1]);
      int var8 = arr.length >= 3 ? parseInt(arr[2], 1, 64) : 1;
      int var9 = arr.length >= 4 ? parseInt(arr[3]) : 0;
      ItemStack var10 = new ItemStack(var7, var8, var9);
      if (arr.length >= 5) {
         String var11 = getChatComponentFromNthArg(iCommandSender, arr, 4).getUnformattedText();

         try {
            var10.setTagCompound(JsonToNBT.getTagFromJson(var11));
         } catch (NBTException var13) {
            throw new CommandException("commands.give.tagError", var13.getMessage());
         }
      }

      boolean var14 = var6.inventory.addItemStackToInventory(var10);
      if (var14) {
         var6.worldObj.playSoundAtEntity(var6, "random.pop", 0.2F, ((var6.getRNG().nextFloat() - var6.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
         var6.inventoryContainer.detectAndSendChanges();
      }

      if (var14 && var10.stackSize <= 0) {
         var10.stackSize = 1;
         iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, var8);
         EntityItem var15 = var6.dropPlayerItemWithRandomChoice(var10, false);
         if (var15 != null) {
            var15.func_174870_v();
         }
      } else {
         iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, var8 - var10.stackSize);
         EntityItem var12 = var6.dropPlayerItemWithRandomChoice(var10, false);
         if (var12 != null) {
            var12.setNoPickupDelay();
            var12.setOwner(var6.getName());
         }
      }

      notifyOperators(iCommandSender, this, "commands.give.success", var10.getChatComponent(), var8, var6.getName());
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, this.getPlayers())
         : (arr.length == 2 ? getListOfStringsMatchingLastWord(arr, Item.itemRegistry.getKeys()) : null);
   }

   protected String[] getPlayers() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return integer == 0;
   }
}
