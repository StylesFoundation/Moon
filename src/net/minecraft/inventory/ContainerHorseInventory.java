package net.minecraft.inventory;

import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ContainerHorseInventory extends Container {
   private final IInventory horseInventory;
   private final EntityHorse theHorse;

   public ContainerHorseInventory(IInventory iInventory1, IInventory iInventory2, EntityHorse entityHorse, EntityPlayer entityPlayer) {
      this.horseInventory = iInventory2;
      this.theHorse = entityHorse;
      iInventory2.openInventory(entityPlayer);
      byte var9 = -18;
      this.addSlotToContainer(new Slot(iInventory2, 0, 8, 18) {
         @Override
         public boolean isItemValid(ItemStack itemStack) {
            return super.isItemValid(itemStack) && itemStack.getItem() == Items.saddle && !this.getHasStack();
         }
      });
      this.addSlotToContainer(new Slot(iInventory2, 1, 8, 36) {
         @Override
         public boolean isItemValid(ItemStack itemStack) {
            return super.isItemValid(itemStack) && entityHorse.canWearArmor() && EntityHorse.isArmorItem(itemStack.getItem());
         }

         @Override
         public boolean canBeHovered() {
            return entityHorse.canWearArmor();
         }
      });
      if (entityHorse.isChested()) {
         for (int var10 = 0; var10 < 3; var10++) {
            for (int var11 = 0; var11 < 5; var11++) {
               this.addSlotToContainer(new Slot(iInventory2, 2 + var11 + var10 * 5, 80 + var11 * 18, 18 + var10 * 18));
            }
         }
      }

      for (int var12 = 0; var12 < 3; var12++) {
         for (int var14 = 0; var14 < 9; var14++) {
            this.addSlotToContainer(new Slot(iInventory1, var14 + var12 * 9 + 9, 8 + var14 * 18, 102 + var12 * 18 + var9));
         }
      }

      for (int var13 = 0; var13 < 9; var13++) {
         this.addSlotToContainer(new Slot(iInventory1, var13, 8 + var13 * 18, 142));
      }
   }

   @Override
   public boolean canInteractWith(EntityPlayer entityPlayer) {
      return this.horseInventory.isUseableByPlayer(entityPlayer) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity(entityPlayer) < 8.0F;
   }

   @Override
   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      ItemStack var6 = null;
      Slot var7 = this.inventorySlots.get(integer);
      if (var7 != null && var7.getHasStack()) {
         ItemStack var8 = var7.getStack();
         var6 = var8.copy();
         if (integer < this.horseInventory.getSizeInventory()) {
            if (!this.mergeItemStack(var8, this.horseInventory.getSizeInventory(), this.inventorySlots.size(), true)) {
               return null;
            }
         } else if (this.getSlot(1).isItemValid(var8) && !this.getSlot(1).getHasStack()) {
            if (!this.mergeItemStack(var8, 1, 2, false)) {
               return null;
            }
         } else if (this.getSlot(0).isItemValid(var8)) {
            if (!this.mergeItemStack(var8, 0, 1, false)) {
               return null;
            }
         } else if (this.horseInventory.getSizeInventory() <= 2 || !this.mergeItemStack(var8, 2, this.horseInventory.getSizeInventory(), false)) {
            return null;
         }

         if (var8.stackSize == 0) {
            var7.putStack(null);
         } else {
            var7.onSlotChanged();
         }
      }

      return var6;
   }

   @Override
   public void onContainerClosed(EntityPlayer entityPlayer) {
      super.onContainerClosed(entityPlayer);
      this.horseInventory.closeInventory(entityPlayer);
   }
}
