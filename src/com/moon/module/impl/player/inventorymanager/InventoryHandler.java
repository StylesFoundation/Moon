package com.moon.module.impl.player.inventorymanager;

import com.moon.module.impl.player.InventoryManager;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class InventoryHandler {
   private final InventoryManager module;
   private final Minecraft mc;

   public InventoryHandler(InventoryManager module) {
      this.module = module;
      this.mc = Minecraft.getMinecraft();
   }

   public int getPotion(int potionId) {
      for (int slot = 0; slot <= 35; slot++) {
         ItemStack[] mainInventory = this.mc.thePlayer.inventory.mainInventory;
         if (mainInventory[slot] != null) {
            ItemStack stack = mainInventory[slot];
            if (stack.getItem() instanceof ItemPotion itemPotion && itemPotion.getEffects(stack) != null) {
               for (PotionEffect effect : itemPotion.getEffects(stack)) {
                  if (effect.getPotionID() == potionId) {
                     return slot;
                  }
               }
            }
         }
      }

      return -1;
   }

   private int getBestTool(Predicate<ItemTool> predicate, int toolSlotOffset) {
      double bestValue = Double.NaN;
      byte bestSlot = -1;

      for (Slot slot : this.getSlots()) {
         ItemStack stack = slot.getStack();
         if (stack != null) {
            Item item = stack.getItem();
            if (item instanceof ItemTool tool && predicate.test((ItemTool)item)) {
               Item.ToolMaterial material = tool.getToolMaterial();
               double value = material.getHarvestLevel() + this.getEnchantmentValue(stack);
               int targetSlot = 36 + this.module.toolsSlot.getValue() + toolSlotOffset;
               if (Double.isNaN(bestValue) || value > bestValue || value == bestValue && slot.slotNumber == targetSlot) {
                  bestSlot = (byte)(slot.slotNumber != targetSlot ? slot.slotNumber : -1);
                  bestValue = value;
               }
            }
         }
      }

      return bestSlot;
   }

   public int getPickaxe() {
      return this.getBestTool(tool -> tool instanceof ItemPickaxe, 0);
   }

   public int getAxe() {
      return this.getBestTool(tool -> tool instanceof ItemAxe, 1);
   }

   public int getShovel() {
      return this.getBestTool(tool -> tool instanceof ItemSpade, 2);
   }

   public int getBlock() {
      int maxStackSize = 0;
      byte bestSlot = -1;

      for (Slot slot : this.getSlots()) {
         ItemStack stack = slot.getStack();
         if (stack != null && stack.getItem() instanceof ItemBlock && maxStackSize <= stack.stackSize) {
            bestSlot = (byte)(slot.slotNumber != 44 ? slot.slotNumber : -1);
            maxStackSize = stack.stackSize;
         }
      }

      return bestSlot;
   }

   public int getBow() {
      double bestValue = Double.NaN;
      byte bestSlot = -1;

      for (Slot slot : this.getSlots()) {
         ItemStack stack = slot.getStack();
         if (stack != null && stack.getItem() instanceof ItemBow) {
            double value = this.getEnchantmentValue(stack);
            byte targetSlot = 43;
            if (Double.isNaN(bestValue) || value > bestValue || value == bestValue && slot.slotNumber == targetSlot) {
               bestSlot = (byte)(slot.slotNumber != targetSlot ? slot.slotNumber : -1);
               bestValue = value;
            }
         }
      }

      return bestSlot;
   }

   public int getSword() {
      double bestDamage = Double.NaN;
      byte bestSlot = -1;

      for (Slot slot : this.getSlots()) {
         ItemStack stack = slot.getStack();
         if (stack != null) {
            Item item = stack.getItem();
            if (item instanceof ItemSword) {
               double damage = ((ItemSword)item).getDamageVsEntity() + this.getEnchantmentValue(stack);
               if (Double.isNaN(bestDamage) || damage > bestDamage || damage == bestDamage && slot.slotNumber == 36) {
                  bestSlot = (byte)(slot.slotNumber != 36 ? slot.slotNumber : -1);
                  bestDamage = damage;
               }
            }
         }
      }

      return bestSlot;
   }

   public int getArmor(int armorType) {
      double bestValue = Double.NaN;
      byte bestSlot = -1;

      for (Slot slot : this.getSlots()) {
         ItemStack stack = slot.getStack();
         if (stack != null && stack.getItem() instanceof ItemArmor armor && armor.armorType == armorType) {
            double armorValue = this.getArmorValue(stack);
            if (Double.isNaN(bestValue) || !(armorValue <= bestValue) || armorValue == bestValue && slot.slotNumber == armorType + 5) {
               bestSlot = (byte)(slot.slotNumber != armorType + 5 ? slot.slotNumber : -1);
               bestValue = armorValue;
            }
         }
      }

      return bestSlot;
   }

   private double getArmorValue(ItemStack stack) {
      if (!(stack.getItem() instanceof ItemArmor)) {
         return -1.0;
      }

      float reduction = ((ItemArmor)stack.getItem()).damageReduceAmount;
      Map enchantments = EnchantmentHelper.getEnchantments(stack);
      if (enchantments.containsKey(Enchantment.protection.effectId)) {
         int level = (Integer)enchantments.get(Enchantment.protection.effectId);
         reduction += Enchantment.protection.calcModifierDamage(level, DamageSource.generic);
      }

      return reduction;
   }

   public int getSoup() {
      for (int slot = 0; slot <= 35; slot++) {
         if (this.mc.thePlayer != null && this.mc.theWorld != null && this.mc.thePlayer.inventory.mainInventory[slot] != null) {
            Item item = this.mc.thePlayer.inventory.mainInventory[slot].getItem();
            if (item instanceof ItemSoup) {
               return slot;
            }
         }
      }

      return -1;
   }

   private int getBlockCount() {
      int count = 0;

      for (int i = 0; i < 45; i++) {
         if (this.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack stack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = stack.getItem();
            if (stack.getItem() instanceof ItemBlock && ((ItemBlock)item).getBlock().isFullBlock()) {
               count += stack.stackSize;
            }
         }
      }

      return count;
   }

   public int getTrashSlot() {
      for (Slot slot : this.getSlots()) {
         if (this.shouldDrop(slot)) {
            return slot.slotNumber;
         }
      }

      return -1;
   }

   public boolean shouldDrop(Slot slot) {
      ItemStack stack = slot.getStack();
      if (stack == null) {
         return false;
      }

      if (this.module.ignoreCustomNames.getValue() && stack.hasDisplayName() && !(stack.getItem() instanceof ItemPotion)) {
         return false;
      }

      Item item = stack.getItem();
      if (item instanceof ItemSword) {
         int bestSword = this.getSword();
         return slot.slotNumber != bestSword && (bestSword != -1 || slot.slotNumber != 36);
      }

      if (this.module.keepTools.getValue()) {
         if (item instanceof ItemPickaxe) {
            int bestPickaxe = this.getPickaxe();
            int pickaxeSlot = 36 + this.module.toolsSlot.getValue();
            return slot.slotNumber != bestPickaxe && (bestPickaxe != -1 || slot.slotNumber != pickaxeSlot);
         }

         if (item instanceof ItemAxe) {
            int bestAxe = this.getAxe();
            int axeSlot = 36 + this.module.toolsSlot.getValue() + 1;
            return slot.slotNumber != bestAxe && (bestAxe != -1 || slot.slotNumber != axeSlot);
         }

         if (item instanceof ItemSpade) {
            int bestShovel = this.getShovel();
            int shovelSlot = 36 + this.module.toolsSlot.getValue() + 2;
            return slot.slotNumber != bestShovel && (bestShovel != -1 || slot.slotNumber != shovelSlot);
         }
      }

      if (this.module.keepBows.getValue() && item instanceof ItemBow) {
         int bestBow = this.getBow();
         byte bowSlot = 43;
         return slot.slotNumber != bestBow && (bestBow != -1 || slot.slotNumber != bowSlot);
      } else if (item instanceof ItemArmor armor) {
         int bestArmor = this.getArmor(armor.armorType);
         return slot.slotNumber != bestArmor && (bestArmor != -1 || slot.slotNumber != armor.armorType + 5);
      } else if (!(item instanceof ItemPotion potion)) {
         if (item instanceof ItemBlock && ((ItemBlock)item).getBlock().isFullBlock()) {
            int blockCount = this.getBlockCount();
            return blockCount > this.module.blockLimit.getValue();
         }

         if (!(item instanceof ItemFood) && !(item instanceof ItemExpBottle)) {
            switch (Item.getIdFromItem(item)) {
               case 262:
               case 263:
               case 264:
               case 265:
               case 266:
               case 280:
               case 325:
               case 326:
               case 327:
               case 368:
               case 369:
               case 370:
               case 378:
               case 388:
               case 399:
                  return false;
               default:
                  return true;
            }
         } else {
            return false;
         }
      } else {
         for (PotionEffect effect : potion.getEffects(stack)) {
            if (effect != null && (Potion.potionTypes[effect.getPotionID()].isBadEffect() || effect.getPotionID() == 12)) {
               return true;
            }
         }

         return false;
      }
   }

   private ArrayList<Slot> getSlots() {
      ArrayList<Slot> slots = new ArrayList<>(this.mc.thePlayer.inventoryContainer.inventorySlots);
      if (this.mc.thePlayer.openContainer != null) {
         slots.removeIf(slot -> this.mc.thePlayer.openContainer.inventorySlots.contains(slot));
         slots.addAll(this.mc.thePlayer.openContainer.inventorySlots);
      }

      return slots;
   }

   private double getEnchantmentValue(ItemStack stack) {
      if (stack != null && stack.isItemEnchanted()) {
         NBTTagList tagList = stack.getEnchantmentTagList();
         if (tagList == null) {
            return 0.0;
         }

         double totalValue = 0.0;

         for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = tagList.getCompoundTagAt(i);
            short lvl = tag.getShort("lvl");
            switch (tag.getShort("id")) {
               case 0:
                  totalValue += lvl;
                  break;
               case 16:
                  totalValue += 1.25 * lvl;
                  break;
               case 20:
                  totalValue += 0.5 * lvl;
                  break;
               default:
                  totalValue += 0.2 * lvl;
            }
         }

         return totalValue;
      } else {
         return 0.0;
      }
   }

   public void swapWithHotbar(int slot, int hotbarSlot) {
      this.swapWithHotbar(this.mc.thePlayer.inventoryContainer.windowId, slot, hotbarSlot);
   }

   public void shiftClick(int slot) {
      this.shiftClick(this.mc.thePlayer.inventoryContainer.windowId, slot);
   }

   public void drop(int slot, boolean allItems) {
      this.drop(this.mc.thePlayer.inventoryContainer.windowId, slot, allItems);
   }

   public void drop(int windowId, int slot, boolean allItems) {
      this.spoofOpenInventory();
      this.mc.playerController.windowClick(windowId, slot, allItems ? 1 : 0, 4, this.mc.thePlayer);
      this.spoofCloseInventory();
   }

   public void shiftClick(int windowId, int slot) {
      this.spoofOpenInventory();
      this.mc.playerController.windowClick(windowId, slot, 0, 1, this.mc.thePlayer);
      this.spoofCloseInventory();
   }

   public void swapWithHotbar(int windowId, int slot, int hotbarSlot) {
      this.spoofOpenInventory();
      this.mc.playerController.windowClick(windowId, hotbarSlot, slot, 2, this.mc.thePlayer);
      this.spoofCloseInventory();
   }

   private void spoofOpenInventory() {
      if (this.module.requiredInventoryState.getValue() == InventoryManager.Mode.SPOOF) {
         this.mc.thePlayer.sendQueue.Method754(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
      }
   }

   private void spoofCloseInventory() {
      if (this.module.requiredInventoryState.getValue() == InventoryManager.Mode.SPOOF) {
         this.mc.thePlayer.sendQueue.Method754(new C0DPacketCloseWindow(this.mc.thePlayer.inventoryContainer.windowId));
      }
   }
}
