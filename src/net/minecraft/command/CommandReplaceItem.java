package net.minecraft.command;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandReplaceItem extends CommandBase {
   private static final Map<String, Integer> SHORTCUTS = Maps.newHashMap();

   @Override
   public String getCommandName() {
      return "replaceitem";
   }

   @Override
   public int getRequiredPermissionLevel() {
      return 2;
   }

   @Override
   public String getCommandUsage(ICommandSender iCommandSender) {
      return "commands.replaceitem.usage";
   }

   @Override
   public void processCommand(ICommandSender iCommandSender, String[] arr) throws CommandException {
      if (arr.length < 1) {
         throw new WrongUsageException("commands.replaceitem.usage");
      }

      boolean var6;
      if (arr[0].equals("entity")) {
         var6 = false;
      } else {
         if (!arr[0].equals("block")) {
            throw new WrongUsageException("commands.replaceitem.usage");
         }

         var6 = true;
      }

      int var7;
      if (var6) {
         if (arr.length < 6) {
            throw new WrongUsageException("commands.replaceitem.block.usage");
         }

         var7 = 4;
      } else {
         if (arr.length < 4) {
            throw new WrongUsageException("commands.replaceitem.entity.usage");
         }

         var7 = 2;
      }

      int var8 = this.getSlotForShortcut(arr[var7++]);

      Item var9;
      try {
         var9 = getItemByText(iCommandSender, arr[var7]);
      } catch (NumberInvalidException var18) {
         if (Block.getBlockFromName(arr[var7]) != Blocks.air) {
            throw var18;
         }

         var9 = null;
      }

      var7++;
      int var10 = arr.length > var7 ? parseInt(arr[var7++], 1, 64) : 1;
      int var11 = arr.length > var7 ? parseInt(arr[var7++]) : 0;
      ItemStack var12 = new ItemStack(var9, var10, var11);
      if (arr.length > var7) {
         String var13 = getChatComponentFromNthArg(iCommandSender, arr, var7).getUnformattedText();

         try {
            var12.setTagCompound(JsonToNBT.getTagFromJson(var13));
         } catch (NBTException var17) {
            throw new CommandException("commands.replaceitem.tagError", var17.getMessage());
         }
      }

      if (var12.getItem() == null) {
         var12 = null;
      }

      if (var6) {
         iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
         BlockPos var21 = parseBlockPos(iCommandSender, arr, 1, false);
         World var14 = iCommandSender.getEntityWorld();
         if (!(var14.getTileEntity(var21) instanceof IInventory var16)) {
            throw new CommandException("commands.replaceitem.noContainer", var21.getX(), var21.getY(), var21.getZ());
         }

         if (var8 >= 0 && var8 < var16.getSizeInventory()) {
            var16.setInventorySlotContents(var8, var12);
         }
      } else {
         Entity var22 = getEntity(iCommandSender, arr[1]);
         iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, 0);
         if (var22 instanceof EntityPlayer) {
            ((EntityPlayer)var22).inventoryContainer.detectAndSendChanges();
         }

         if (!var22.replaceItemInInventory(var8, var12)) {
            throw new CommandException("commands.replaceitem.failed", var8, var10, var12 == null ? "Air" : var12.getChatComponent());
         }

         if (var22 instanceof EntityPlayer) {
            ((EntityPlayer)var22).inventoryContainer.detectAndSendChanges();
         }
      }

      iCommandSender.setCommandStat(CommandResultStats.Type.AFFECTED_ITEMS, var10);
      notifyOperators(iCommandSender, this, "commands.replaceitem.success", var8, var10, var12 == null ? "Air" : var12.getChatComponent());
   }

   private int getSlotForShortcut(String string) throws CommandException {
      if (!SHORTCUTS.containsKey(string)) {
         throw new CommandException("commands.generic.parameter.invalid", string);
      } else {
         return SHORTCUTS.get(string);
      }
   }

   @Override
   public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] arr, BlockPos blockPos) {
      return arr.length == 1
         ? getListOfStringsMatchingLastWord(arr, "entity", "block")
         : (
            arr.length == 2 && arr[0].equals("entity")
               ? getListOfStringsMatchingLastWord(arr, this.getUsernames())
               : (
                  arr.length >= 2 && arr.length <= 4 && arr[0].equals("block")
                     ? func_175771_a(arr, 1, blockPos)
                     : (
                        (arr.length != 3 || !arr[0].equals("entity")) && (arr.length != 5 || !arr[0].equals("block"))
                           ? (
                              arr.length == 4 && arr[0].equals("entity") || arr.length == 6 && arr[0].equals("block")
                                 ? getListOfStringsMatchingLastWord(arr, Item.itemRegistry.getKeys())
                                 : null
                           )
                           : getListOfStringsMatchingLastWord(arr, SHORTCUTS.keySet())
                     )
               )
         );
   }

   protected String[] getUsernames() {
      return MinecraftServer.getServer().getAllUsernames();
   }

   @Override
   public boolean isUsernameIndex(String[] arr, int integer) {
      return arr.length > 0 && arr[0].equals("entity") && integer == 1;
   }

   static {
      for (int var13 = 0; var13 < 54; var13++) {
         SHORTCUTS.put("slot.container." + var13, var13);
      }

      for (int var14 = 0; var14 < 9; var14++) {
         SHORTCUTS.put("slot.hotbar." + var14, var14);
      }

      for (int var15 = 0; var15 < 27; var15++) {
         SHORTCUTS.put("slot.inventory." + var15, 9 + var15);
      }

      for (int var16 = 0; var16 < 27; var16++) {
         SHORTCUTS.put("slot.enderchest." + var16, 200 + var16);
      }

      for (int var17 = 0; var17 < 8; var17++) {
         SHORTCUTS.put("slot.villager." + var17, 300 + var17);
      }

      for (int var18 = 0; var18 < 15; var18++) {
         SHORTCUTS.put("slot.horse." + var18, 500 + var18);
      }

      SHORTCUTS.put("slot.weapon", 99);
      SHORTCUTS.put("slot.armor.head", 103);
      SHORTCUTS.put("slot.armor.chest", 102);
      SHORTCUTS.put("slot.armor.legs", 101);
      SHORTCUTS.put("slot.armor.feet", 100);
      SHORTCUTS.put("slot.horse.saddle", 400);
      SHORTCUTS.put("slot.horse.armor", 401);
      SHORTCUTS.put("slot.horse.chest", 499);
   }
}
