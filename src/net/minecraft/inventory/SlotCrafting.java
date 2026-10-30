package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.stats.AchievementList;

public class SlotCrafting extends Slot {
   private final InventoryCrafting craftMatrix;
   private final EntityPlayer thePlayer;
   private int amountCrafted;

   public SlotCrafting(EntityPlayer entityPlayer, InventoryCrafting inventoryCrafting, IInventory iInventory, int integer4, int integer5, int integer6) {
      super(iInventory, integer4, integer5, integer6);
      this.thePlayer = entityPlayer;
      this.craftMatrix = inventoryCrafting;
   }

   @Override
   public boolean isItemValid(ItemStack itemStack) {
      return false;
   }

   @Override
   public ItemStack decrStackSize(int integer) {
      if (this.getHasStack()) {
         this.amountCrafted = this.amountCrafted + Math.min(integer, this.getStack().stackSize);
      }

      return super.decrStackSize(integer);
   }

   @Override
   protected void onCrafting(ItemStack itemStack, int integer) {
      this.amountCrafted += integer;
      this.onCrafting(itemStack);
   }

   @Override
   protected void onCrafting(ItemStack itemStack) {
      if (this.amountCrafted > 0) {
         itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
      }

      this.amountCrafted = 0;
      if (itemStack.getItem() == Item.getItemFromBlock(Blocks.crafting_table)) {
         this.thePlayer.triggerAchievement(AchievementList.buildWorkBench);
      }

      if (itemStack.getItem() instanceof ItemPickaxe) {
         this.thePlayer.triggerAchievement(AchievementList.buildPickaxe);
      }

      if (itemStack.getItem() == Item.getItemFromBlock(Blocks.furnace)) {
         this.thePlayer.triggerAchievement(AchievementList.buildFurnace);
      }

      if (itemStack.getItem() instanceof ItemHoe) {
         this.thePlayer.triggerAchievement(AchievementList.buildHoe);
      }

      if (itemStack.getItem() == Items.bread) {
         this.thePlayer.triggerAchievement(AchievementList.makeBread);
      }

      if (itemStack.getItem() == Items.cake) {
         this.thePlayer.triggerAchievement(AchievementList.bakeCake);
      }

      if (itemStack.getItem() instanceof ItemPickaxe && ((ItemPickaxe)itemStack.getItem()).getToolMaterial() != Item.ToolMaterial.WOOD) {
         this.thePlayer.triggerAchievement(AchievementList.buildBetterPickaxe);
      }

      if (itemStack.getItem() instanceof ItemSword) {
         this.thePlayer.triggerAchievement(AchievementList.buildSword);
      }

      if (itemStack.getItem() == Item.getItemFromBlock(Blocks.enchanting_table)) {
         this.thePlayer.triggerAchievement(AchievementList.enchantments);
      }

      if (itemStack.getItem() == Item.getItemFromBlock(Blocks.bookshelf)) {
         this.thePlayer.triggerAchievement(AchievementList.bookcase);
      }

      if (itemStack.getItem() == Items.golden_apple && itemStack.getMetadata() == 1) {
         this.thePlayer.triggerAchievement(AchievementList.overpowered);
      }
   }

   @Override
   public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
      this.onCrafting(itemStack);
      ItemStack[] var6 = CraftingManager.getInstance().func_180303_b(this.craftMatrix, entityPlayer.worldObj);

      for (int var7 = 0; var7 < var6.length; var7++) {
         ItemStack var8 = this.craftMatrix.getStackInSlot(var7);
         ItemStack var9 = var6[var7];
         if (var8 != null) {
            this.craftMatrix.decrStackSize(var7, 1);
         }

         if (var9 != null) {
            if (this.craftMatrix.getStackInSlot(var7) == null) {
               this.craftMatrix.setInventorySlotContents(var7, var9);
            } else if (!this.thePlayer.inventory.addItemStackToInventory(var9)) {
               this.thePlayer.dropPlayerItemWithRandomChoice(var9, false);
            }
         }
      }
   }
}
