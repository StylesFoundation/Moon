package com.moon.scripting.api.functions.impl;

import com.moon.scripting.api.functions.InventoryFunctions;
import com.moon.scripting.api.types.Enchantment;
import com.moon.scripting.api.types.Item;
import com.moon.scripting.api.types.Potion;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiHopper;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiBeacon;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiDispenser;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

public class InventoryFunctionsImpl implements InventoryFunctions {
   private static final Minecraft mc = Minecraft.getMinecraft();

   @Override
   public void drop(int slotId, boolean stack) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotId, stack ? 1 : 0, 4, mc.thePlayer);
   }

   @Override
   public void shift(int slotId) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotId, 0, 1, mc.thePlayer);
   }

   @Override
   public void swap(int slotId, int hotbarSlot) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slotId, hotbarSlot, 2, mc.thePlayer);
   }

   @Override
   public void window_click(int windowId, int slotId, int mouseButton, int mode) {
      mc.playerController.windowClick(windowId, slotId, mouseButton, mode, mc.thePlayer);
   }

   @Override
   public Item get_item_in_window(int windowId, int slotId) {
      if (mc.thePlayer == null) {
         return null;
      }

      if (windowId == mc.thePlayer.inventoryContainer.windowId) {
         return this.get_item(slotId);
      }

      if (windowId == this.upper_container_id()) {
         if (!(mc.currentScreen instanceof GuiChest guiChest)) {
            return null;
         } else {
            Container container = guiChest.inventorySlots;
            if (container == null) {
               return null;
            }

            ItemStack stack = container.inventoryItemStacks.get(slotId);
            if (stack == null) {
               return null;
            }

            net.minecraft.item.Item mcItem = stack.getItem();
            Item item = new Item();
            item.name = mcItem.getUnlocalizedName();
            item.display_name = stack.getDisplayName();
            item.max_stack_size = mcItem.getItemStackLimit();
            item.max_damage = mcItem.getMaxDamage();
            item.stack_size = stack.stackSize;
            item.damage = stack.getItemDamage();
            item.potions = new Potion[0];
            item.solid = false;
            if (stack.getItem() != null) {
               if (stack.getItem() instanceof ItemBlock itemBlock) {
                  item.solid = itemBlock.getBlock().getMaterial().isSolid();
               }

               if (stack.getItem() instanceof ItemPotion itemPotion) {
                  List<PotionEffect> effects = itemPotion.getEffects(stack);
                  ArrayList<Potion> potionList = new ArrayList<>();

                  for (PotionEffect effect : effects) {
                     net.minecraft.potion.Potion potionType = net.minecraft.potion.Potion.potionTypes[effect.getPotionID()];
                     Potion potion = new Potion();
                     potion.id = effect.getPotionID();
                     potion.amplifier = effect.getAmplifier();
                     potion.duration = effect.getDuration();
                     potion.color = potionType.getLiquidColor();
                     potion.name = potionType.getName();
                     potion.bad_effect = potionType.isBadEffect();
                     potionList.add(potion);
                  }

                  item.potions = potionList.toArray(new Potion[0]);
               }
            }

            NBTTagList enchantmentTagList = stack.getEnchantmentTagList();
            if (enchantmentTagList != null) {
               int count = enchantmentTagList.tagCount();
               Enchantment[] enchantments = new Enchantment[count];

               for (int i = 0; i < count; i++) {
                  Enchantment enchantment = new Enchantment();
                  NBTTagCompound compoundTag = enchantmentTagList.getCompoundTagAt(i);
                  short id = compoundTag.getShort("id");
                  short lvl = compoundTag.getShort("lvl");
                  enchantment.id = id;
                  enchantment.level = lvl;
                  enchantments[i] = enchantment;
               }

               item.enchantments = enchantments;
            }

            return item;
         }
      } else {
         return this.get_item(slotId);
      }
   }

   @Override
   public Item get_item(int slotId) {
      if (mc.thePlayer != null && mc.thePlayer.inventory != null) {
         ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slotId);
         if (stack == null) {
            return null;
         }

         net.minecraft.item.Item mcItem = stack.getItem();
         Item item = new Item();
         item.name = mcItem.getUnlocalizedName();
         item.display_name = stack.getDisplayName();
         item.max_stack_size = mcItem.getItemStackLimit();
         item.max_damage = mcItem.getMaxDamage();
         item.stack_size = stack.stackSize;
         item.damage = stack.getItemDamage();
         item.potions = new Potion[0];
         item.solid = false;
         if (stack.getItem() != null) {
            if (stack.getItem() instanceof ItemBlock itemBlock) {
               item.solid = itemBlock.getBlock().getMaterial().isSolid();
            }

            if (stack.getItem() instanceof ItemPotion itemPotion) {
               List<PotionEffect> effects = itemPotion.getEffects(stack);
               ArrayList<Potion> potionList = new ArrayList<>();

               for (PotionEffect effect : effects) {
                  net.minecraft.potion.Potion potionType = net.minecraft.potion.Potion.potionTypes[effect.getPotionID()];
                  Potion potion = new Potion();
                  potion.id = effect.getPotionID();
                  potion.amplifier = effect.getAmplifier();
                  potion.duration = effect.getDuration();
                  potion.color = potionType.getLiquidColor();
                  potion.name = potionType.getName();
                  potion.bad_effect = potionType.isBadEffect();
                  potionList.add(potion);
               }

               item.potions = potionList.toArray(new Potion[0]);
            }
         }

         NBTTagList enchantmentTagList = stack.getEnchantmentTagList();
         if (enchantmentTagList != null) {
            int count = enchantmentTagList.tagCount();
            Enchantment[] enchantments = new Enchantment[count];

            for (int i = 0; i < count; i++) {
               Enchantment enchantment = new Enchantment();
               NBTTagCompound compoundTag = enchantmentTagList.getCompoundTagAt(i);
               short id = compoundTag.getShort("id");
               short lvl = compoundTag.getShort("lvl");
               enchantment.id = id;
               enchantment.level = lvl;
               enchantments[i] = enchantment;
            }

            item.enchantments = enchantments;
         }

         return item;
      } else {
         return null;
      }
   }

   @Override
   public double get_damage_reduction(int slotId) {
      if (mc.thePlayer != null && mc.thePlayer.inventory != null) {
         ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slotId);
         if (stack == null) {
            return 0.0;
         }

         if (!(stack.getItem() instanceof ItemArmor)) {
            return -1.0;
         }

         float damageReduceAmount = ((ItemArmor)stack.getItem()).damageReduceAmount;
         Map enchantments = EnchantmentHelper.getEnchantments(stack);
         if (enchantments.containsKey(net.minecraft.enchantment.Enchantment.protection.effectId)) {
            int protectionLevel = (Integer)enchantments.get(net.minecraft.enchantment.Enchantment.protection.effectId);
            damageReduceAmount += net.minecraft.enchantment.Enchantment.protection.calcModifierDamage(protectionLevel, DamageSource.generic);
         }

         return damageReduceAmount;
      } else {
         return 0.0;
      }
   }

   @Override
   public double get_damage_amount(int slotId) {
      if (mc.thePlayer != null && mc.thePlayer.inventory != null) {
         ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slotId);
         if (stack == null) {
            return 0.0;
         } else {
            return stack.getItem() instanceof ItemTool itemTool ? itemTool.getToolMaterial().getDamageVsEntity() + this.getEnchantmentBonus(stack) : 0.0;
         }
      } else {
         return 0.0;
      }
   }

   @Override
   public double get_harvest_level(int slotId) {
      if (mc.thePlayer != null && mc.thePlayer.inventory != null) {
         ItemStack stack = mc.thePlayer.inventory.getStackInSlot(slotId);
         if (stack == null) {
            return 0.0;
         } else {
            return stack.getItem() instanceof ItemTool itemTool ? itemTool.getToolMaterial().getHarvestLevel() + this.getEnchantmentBonus(stack) : 0.0;
         }
      } else {
         return 0.0;
      }
   }

   @Override
   public int upper_container_id() {
      return mc.currentScreen instanceof GuiChest guiChest ? guiChest.inventorySlots.windowId : 0;
   }

   @Override
   public int lower_container_id() {
      return this.player_inventory_id();
   }

   @Override
   public int player_inventory_id() {
      return mc.thePlayer != null && mc.thePlayer.inventoryContainer != null ? mc.thePlayer.inventoryContainer.windowId : 0;
   }

   @Override
   public String current_inventory() {
      GuiScreen screen = mc.currentScreen;
      if (screen == null) {
         return null;
      }

      if (screen instanceof GuiChest) {
         return "chest";
      }

      if (screen instanceof GuiBeacon) {
         return "beacon";
      }

      if (screen instanceof GuiMerchant) {
         return "merchant";
      }

      if (screen instanceof GuiHopper) {
         return "hopper";
      }

      if (screen instanceof GuiFurnace) {
         return "furnace";
      }

      if (screen instanceof GuiDispenser) {
         return "dispenser";
      }

      if (screen instanceof GuiCrafting) {
         return "crafting";
      }

      if (screen instanceof GuiContainer) {
         Container container = ((GuiContainer)screen).inventorySlots;
         if (container instanceof ContainerRepair) {
            return "anvil";
         }

         if (container instanceof ContainerPlayer) {
            return "inventory";
         }
      }

      return null;
   }

   private double getEnchantmentBonus(ItemStack itemStack) {
      if (itemStack != null && itemStack.isItemEnchanted()) {
         NBTTagList tagList = itemStack.getEnchantmentTagList();
         if (tagList == null) {
            return 0.0;
         }

         double bonus = 0.0;

         for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound compoundTag = tagList.getCompoundTagAt(i);
            short lvl = compoundTag.getShort("lvl");
            switch (compoundTag.getShort("id")) {
               case 0:
                  bonus += lvl;
                  break;
               case 16:
                  bonus += 1.25 * lvl;
                  break;
               case 20:
                  bonus += 0.5 * lvl;
                  break;
               default:
                  bonus += 0.2 * lvl;
            }
         }

         return bonus;
      } else {
         return 0.0;
      }
   }
}
