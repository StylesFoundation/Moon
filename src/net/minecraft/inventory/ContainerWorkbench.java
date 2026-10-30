package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerWorkbench extends Container {
   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
   public IInventory craftResult = new InventoryCraftResult();
   private final World worldObj;
   private final BlockPos pos;

   public ContainerWorkbench(InventoryPlayer inventoryPlayer, World world, BlockPos blockPos) {
      this.worldObj = world;
      this.pos = blockPos;
      this.addSlotToContainer(new SlotCrafting(inventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));

      for (int var7 = 0; var7 < 3; var7++) {
         for (int var8 = 0; var8 < 3; var8++) {
            this.addSlotToContainer(new Slot(this.craftMatrix, var8 + var7 * 3, 30 + var8 * 18, 17 + var7 * 18));
         }
      }

      for (int var9 = 0; var9 < 3; var9++) {
         for (int var11 = 0; var11 < 9; var11++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, var11 + var9 * 9 + 9, 8 + var11 * 18, 84 + var9 * 18));
         }
      }

      for (int var10 = 0; var10 < 9; var10++) {
         this.addSlotToContainer(new Slot(inventoryPlayer, var10, 8 + var10 * 18, 142));
      }

      this.onCraftMatrixChanged(this.craftMatrix);
   }

   @Override
   public void onCraftMatrixChanged(IInventory iInventory) {
      this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
   }

   @Override
   public void onContainerClosed(EntityPlayer entityPlayer) {
      super.onContainerClosed(entityPlayer);
      if (!this.worldObj.isRemote) {
         for (int var5 = 0; var5 < 9; var5++) {
            ItemStack var6 = this.craftMatrix.removeStackFromSlot(var5);
            if (var6 != null) {
               entityPlayer.dropPlayerItemWithRandomChoice(var6, false);
            }
         }
      }
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.worldObj.getBlockState(this.pos).getBlock() == Blocks.crafting_table
         && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = null;
      Slot var7 = this.inventorySlots.get(integer);
      if (var7 != null && var7.getHasStack()) {
         ItemStack var8 = var7.getStack();
         var6 = var8.copy();
         if (integer == 0) {
            if (!this.mergeItemStack(var8, 10, 46, true)) {
               return null;
            }

            var7.onSlotChange(var8, var6);
         } else if (integer >= 10 && integer < 37) {
            if (!this.mergeItemStack(var8, 37, 46, false)) {
               return null;
            }
         } else if (integer >= 37 && integer < 46) {
            if (!this.mergeItemStack(var8, 10, 37, false)) {
               return null;
            }
         } else if (!this.mergeItemStack(var8, 10, 46, false)) {
            return null;
         }

         if (var8.stackSize == 0) {
            var7.putStack(null);
         } else {
            var7.onSlotChanged();
         }

         if (var8.stackSize == var6.stackSize) {
            return null;
         }

         var7.onPickupFromSlot(entityPlayer, var8);
      }

      return var6;
   }

   @Override
   public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
      return slot.inventory != this.craftResult && super.canMergeSlot(itemStack, slot);
   }
}
