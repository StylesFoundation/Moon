package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityEnderChest;

public class InventoryEnderChest extends InventoryBasic {
   private TileEntityEnderChest associatedChest;

   public InventoryEnderChest() {
      super("container.enderchest", false, 27);
   }

   public void setChestTileEntity(TileEntityEnderChest tileEntityEnderChest) {
      this.associatedChest = tileEntityEnderChest;
   }

   public void loadInventoryFromNBT(NBTTagList nBTTagList) {
      for (int var5 = 0; var5 < this.getSizeInventory(); var5++) {
         this.setInventorySlotContents(var5, null);
      }

      for (int var8 = 0; var8 < nBTTagList.tagCount(); var8++) {
         NBTTagCompound var6 = nBTTagList.getCompoundTagAt(var8);
         int var7 = var6.getByte("Slot") & 255;
         if (var7 >= 0 && var7 < this.getSizeInventory()) {
            this.setInventorySlotContents(var7, ItemStack.loadItemStackFromNBT(var6));
         }
      }
   }

   public NBTTagList saveInventoryToNBT() {
      NBTTagList var4 = new NBTTagList();

      for (int var5 = 0; var5 < this.getSizeInventory(); var5++) {
         ItemStack var6 = this.getStackInSlot(var5);
         if (var6 != null) {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setByte("Slot", (byte)var5);
            var6.writeToNBT(var7);
            var4.appendTag(var7);
         }
      }

      return var4;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
      return (this.associatedChest == null || this.associatedChest.canBeUsed(entityPlayer)) && super.isUseableByPlayer(entityPlayer);
   }

   @Override
   public void openInventory(EntityPlayer entityPlayer) {
      if (this.associatedChest != null) {
         this.associatedChest.openChest();
      }

      super.openInventory(entityPlayer);
   }

   @Override
   public void closeInventory(EntityPlayer entityPlayer) {
      if (this.associatedChest != null) {
         this.associatedChest.closeChest();
      }

      super.closeInventory(entityPlayer);
      this.associatedChest = null;
   }
}
