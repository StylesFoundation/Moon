package net.minecraft.entity.player;

import com.moon.Moon;
import com.moon.events.impl.player.HeldItemEvent;
import com.moon.events.impl.player.SlotChangeEvent;
import com.moon.events.impl.render.RenderHeldItemEvent;
import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ReportedException;

public class InventoryPlayer implements IInventory {
   public ItemStack[] mainInventory = new ItemStack[36];
   public ItemStack[] armorInventory = new ItemStack[4];
   public int currentItem;
   public EntityPlayer player;
   private ItemStack itemStack;
   public boolean inventoryChanged;

   public InventoryPlayer(EntityPlayer entityPlayer) {
      this.player = entityPlayer;
   }

   public ItemStack getCurrentItem() {
      return this.currentItem < 9 && this.currentItem >= 0 ? this.mainInventory[this.currentItem] : null;
   }

   public ItemStack Method726() {
      HeldItemEvent var4 = new HeldItemEvent(this.currentItem);
      Moon.getInstance().getEventBus().publish(var4);
      return var4.getSlot() < 9 && var4.getSlot() >= 0 ? this.mainInventory[var4.getSlot()] : null;
   }

   public static int getHotbarSize() {
      return 9;
   }

   private int getInventorySlotContainItem(Item item) {
      for (int var5 = 0; var5 < this.mainInventory.length; var5++) {
         if (this.mainInventory[var5] != null && this.mainInventory[var5].getItem() == item) {
            return var5;
         }
      }

      return -1;
   }

   private int getInventorySlotContainItemAndDamage(Item item, int integer) {
      for (int var6 = 0; var6 < this.mainInventory.length; var6++) {
         if (this.mainInventory[var6] != null && this.mainInventory[var6].getItem() == item && this.mainInventory[var6].getMetadata() == integer) {
            return var6;
         }
      }

      return -1;
   }

   private int storeItemStack(ItemStack itemStack) {
      for (int var5 = 0; var5 < this.mainInventory.length; var5++) {
         if (this.mainInventory[var5] != null
            && this.mainInventory[var5].getItem() == itemStack.getItem()
            && this.mainInventory[var5].isStackable()
            && this.mainInventory[var5].stackSize < this.mainInventory[var5].getMaxStackSize()
            && this.mainInventory[var5].stackSize < this.getInventoryStackLimit()
            && (!this.mainInventory[var5].getHasSubtypes() || this.mainInventory[var5].getMetadata() == itemStack.getMetadata())
            && ItemStack.areItemStackTagsEqual(this.mainInventory[var5], itemStack)) {
            return var5;
         }
      }

      return -1;
   }

   public int getFirstEmptyStack() {
      for (int var4 = 0; var4 < this.mainInventory.length; var4++) {
         if (this.mainInventory[var4] == null) {
            return var4;
         }
      }

      return -1;
   }

   public void setCurrentItem(Item item, int integer, boolean boolean3, boolean boolean4) {
      ItemStack var8 = this.getCurrentItem();
      int var9 = boolean3 ? this.getInventorySlotContainItemAndDamage(item, integer) : this.getInventorySlotContainItem(item);
      if (var9 >= 0 && var9 < 9) {
         this.currentItem = var9;
      } else if (boolean4 && item != null) {
         int var10 = this.getFirstEmptyStack();
         if (var10 >= 0 && var10 < 9) {
            this.currentItem = var10;
         }

         if (var8 == null || !var8.isItemEnchantable() || this.getInventorySlotContainItemAndDamage(var8.getItem(), var8.getItemDamage()) != this.currentItem) {
            int var11 = this.getInventorySlotContainItemAndDamage(item, integer);
            int var12;
            if (var11 >= 0) {
               var12 = this.mainInventory[var11].stackSize;
               this.mainInventory[var11] = this.mainInventory[this.currentItem];
            } else {
               var12 = 1;
            }

            this.mainInventory[this.currentItem] = new ItemStack(item, var12, integer);
         }
      }
   }

   public void Method727(int integer) {
      if (integer > 0) {
         integer = 1;
      }

      if (integer < 0) {
         integer = -1;
      }

      RenderHeldItemEvent var5 = new RenderHeldItemEvent(this.currentItem);
      Moon.getInstance().getEventBus().publish(var5);
      int var6 = var5.getSlot() - integer;

      while (var6 < 0) {
         var6 += 9;
      }

      while (var6 >= 9) {
         var6 -= 9;
      }

      SlotChangeEvent var7 = new SlotChangeEvent(var6);
      Moon.getInstance().getEventBus().publish(var7);
      if (!var7.isCancelled()) {
         this.currentItem = var7.getSlot();
      }
   }

   public int clearMatchingItems(Item item, int integer2, int integer3, NBTTagCompound nBTTagCompound) {
      int var8 = 0;

      for (int var9 = 0; var9 < this.mainInventory.length; var9++) {
         ItemStack var10 = this.mainInventory[var9];
         if (var10 != null
            && (item == null || var10.getItem() == item)
            && (integer2 <= -1 || var10.getMetadata() == integer2)
            && (nBTTagCompound == null || NBTUtil.func_181123_a(nBTTagCompound, var10.getTagCompound(), true))) {
            int var11 = integer3 <= 0 ? var10.stackSize : Math.min(integer3 - var8, var10.stackSize);
            var8 += var11;
            if (integer3 != 0) {
               this.mainInventory[var9].stackSize -= var11;
               if (this.mainInventory[var9].stackSize == 0) {
                  this.mainInventory[var9] = null;
               }

               if (integer3 > 0 && var8 >= integer3) {
                  return var8;
               }
            }
         }
      }

      for (int var12 = 0; var12 < this.armorInventory.length; var12++) {
         ItemStack var14 = this.armorInventory[var12];
         if (var14 != null
            && (item == null || var14.getItem() == item)
            && (integer2 <= -1 || var14.getMetadata() == integer2)
            && (nBTTagCompound == null || NBTUtil.func_181123_a(nBTTagCompound, var14.getTagCompound(), false))) {
            int var15 = integer3 <= 0 ? var14.stackSize : Math.min(integer3 - var8, var14.stackSize);
            var8 += var15;
            if (integer3 != 0) {
               this.armorInventory[var12].stackSize -= var15;
               if (this.armorInventory[var12].stackSize == 0) {
                  this.armorInventory[var12] = null;
               }

               if (integer3 > 0 && var8 >= integer3) {
                  return var8;
               }
            }
         }
      }

      if (this.itemStack != null) {
         if (item != null && this.itemStack.getItem() != item) {
            return var8;
         }

         if (integer2 > -1 && this.itemStack.getMetadata() != integer2) {
            return var8;
         }

         if (nBTTagCompound != null && !NBTUtil.func_181123_a(nBTTagCompound, this.itemStack.getTagCompound(), false)) {
            return var8;
         }

         int var13 = integer3 <= 0 ? this.itemStack.stackSize : Math.min(integer3 - var8, this.itemStack.stackSize);
         var8 += var13;
         if (integer3 != 0) {
            this.itemStack.stackSize -= var13;
            if (this.itemStack.stackSize == 0) {
               this.itemStack = null;
            }

            if (integer3 > 0 && var8 >= integer3) {
               return var8;
            }
         }
      }

      return var8;
   }

   private int storePartialItemStack(ItemStack itemStack) {
      Item var5 = itemStack.getItem();
      int var6 = itemStack.stackSize;
      int var7 = this.storeItemStack(itemStack);
      if (var7 < 0) {
         var7 = this.getFirstEmptyStack();
      }

      if (var7 >= 0) {
         if (this.mainInventory[var7] == null) {
            this.mainInventory[var7] = new ItemStack(var5, 0, itemStack.getMetadata());
            if (itemStack.hasTagCompound()) {
               this.mainInventory[var7].setTagCompound((NBTTagCompound)itemStack.getTagCompound().copy());
            }
         }

         int var8 = Math.min(var6, this.mainInventory[var7].getMaxStackSize() - this.mainInventory[var7].stackSize);
         if (var8 > this.getInventoryStackLimit() - this.mainInventory[var7].stackSize) {
            var8 = this.getInventoryStackLimit() - this.mainInventory[var7].stackSize;
         }

         if (var8 != 0) {
            var6 -= var8;
            this.mainInventory[var7].stackSize += var8;
            this.mainInventory[var7].animationsToGo = 5;
         }
      }

      return var6;
   }

   public void decrementAnimations() {
      for (int var4 = 0; var4 < this.mainInventory.length; var4++) {
         if (this.mainInventory[var4] != null) {
            this.mainInventory[var4].updateAnimation(this.player.worldObj, this.player, var4, this.currentItem == var4);
         }
      }
   }

   public boolean consumeInventoryItem(Item item) {
      int var5 = this.getInventorySlotContainItem(item);
      if (var5 < 0) {
         return false;
      }

      if (--this.mainInventory[var5].stackSize <= 0) {
         this.mainInventory[var5] = null;
      }

      return true;
   }

   public boolean hasItem(Item item) {
      int var5 = this.getInventorySlotContainItem(item);
      return var5 >= 0;
   }

   public boolean addItemStackToInventory(ItemStack itemStack) {
      if (itemStack != null && itemStack.stackSize != 0 && itemStack.getItem() != null) {
         try {
            if (itemStack.isItemDamaged()) {
               int var9 = this.getFirstEmptyStack();
               if (var9 >= 0) {
                  this.mainInventory[var9] = ItemStack.copyItemStack(itemStack);
                  this.mainInventory[var9].animationsToGo = 5;
                  itemStack.stackSize = 0;
                  return true;
               } else if (this.player.capabilities.isCreativeMode) {
                  itemStack.stackSize = 0;
                  return true;
               } else {
                  return false;
               }
            } else {
               int var5;
               do {
                  var5 = itemStack.stackSize;
                  itemStack.stackSize = this.storePartialItemStack(itemStack);
               } while (itemStack.stackSize > 0 && itemStack.stackSize < var5);

               if (itemStack.stackSize == var5 && this.player.capabilities.isCreativeMode) {
                  itemStack.stackSize = 0;
                  return true;
               } else {
                  return itemStack.stackSize < var5;
               }
            }
         } catch (Throwable var8) {
            CrashReport var6 = CrashReport.makeCrashReport(var8, "Adding item to inventory");
            CrashReportCategory var7 = var6.makeCategory("Item being added");
            var7.addCrashSection("Item ID", Item.getIdFromItem(itemStack.getItem()));
            var7.addCrashSection("Item data", itemStack.getMetadata());
            var7.addCrashSectionCallable("Item name", itemStack::getDisplayName);
            throw new ReportedException(var6);
         }
      } else {
         return false;
      }
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      ItemStack[] var6 = this.mainInventory;
      if (integer1 < 0) {
         return null;
      }

      if (integer1 >= this.mainInventory.length) {
         var6 = this.armorInventory;
         integer1 -= this.mainInventory.length;
      }

      if (var6[integer1] != null) {
         if (var6[integer1].stackSize <= integer2) {
            ItemStack var8 = var6[integer1];
            var6[integer1] = null;
            return var8;
         }

         ItemStack var7 = var6[integer1].splitStack(integer2);
         if (var6[integer1].stackSize == 0) {
            var6[integer1] = null;
         }

         return var7;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      ItemStack[] var5 = this.mainInventory;
      if (integer >= this.mainInventory.length) {
         var5 = this.armorInventory;
         integer -= this.mainInventory.length;
      }

      if (var5[integer] != null) {
         ItemStack var6 = var5[integer];
         var5[integer] = null;
         return var6;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      ItemStack[] var6 = this.mainInventory;
      if (integer >= var6.length) {
         integer -= var6.length;
         var6 = this.armorInventory;
      }

      var6[integer] = itemStack;
   }

   public float getStrVsBlock(Block block, int integer) {
      float var5 = 1.0F;
      if (integer < 0) {
         return 1.0F;
      }

      if (this.mainInventory[integer] != null) {
         var5 = 1.0F * this.mainInventory[integer].getStrVsBlock(block);
      }

      return var5;
   }

   public NBTTagList writeToNBT(NBTTagList nBTTagList) {
      for (int var5 = 0; var5 < this.mainInventory.length; var5++) {
         if (this.mainInventory[var5] != null) {
            NBTTagCompound var6 = new NBTTagCompound();
            var6.setByte("Slot", (byte)var5);
            this.mainInventory[var5].writeToNBT(var6);
            nBTTagList.appendTag(var6);
         }
      }

      for (int var7 = 0; var7 < this.armorInventory.length; var7++) {
         if (this.armorInventory[var7] != null) {
            NBTTagCompound var8 = new NBTTagCompound();
            var8.setByte("Slot", (byte)(var7 + 100));
            this.armorInventory[var7].writeToNBT(var8);
            nBTTagList.appendTag(var8);
         }
      }

      return nBTTagList;
   }

   public void readFromNBT(NBTTagList nBTTagList) {
      this.mainInventory = new ItemStack[36];
      this.armorInventory = new ItemStack[4];

      for (int var5 = 0; var5 < nBTTagList.tagCount(); var5++) {
         NBTTagCompound var6 = nBTTagList.getCompoundTagAt(var5);
         int var7 = var6.getByte("Slot") & 255;
         ItemStack var8 = ItemStack.loadItemStackFromNBT(var6);
         if (var8 != null) {
            if (var7 >= 0 && var7 < this.mainInventory.length) {
               this.mainInventory[var7] = var8;
            }

            if (var7 >= 100 && var7 < this.armorInventory.length + 100) {
               this.armorInventory[var7 - 100] = var8;
            }
         }
      }
   }

   @Override
   public int getSizeInventory() {
      return this.mainInventory.length + 4;
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      if (integer < 0) {
         return null;
      }

      ItemStack[] var5 = this.mainInventory;
      if (integer >= var5.length) {
         integer -= var5.length;
         var5 = this.armorInventory;
      }

      return var5[integer];
   }

   @Override
   public String getName() {
      return "container.inventory";
   }

   @Override
   public boolean hasCustomName() {
      return false;
   }

   @Override
   public IChatComponent getDisplayName() {
      return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean Method728(Block block, int integer) {
      if (block.getMaterial().isToolNotRequired()) {
         return true;
      }

      ItemStack var6 = this.getStackInSlot(integer);
      return var6 != null && var6.canHarvestBlock(block);
   }

   public ItemStack armorItemInSlot(int integer) {
      return this.armorInventory[integer];
   }

   public int getTotalArmorValue() {
      int var4 = 0;

      for (ItemStack var8 : this.armorInventory) {
         if (var8 != null && var8.getItem() instanceof ItemArmor) {
            int var9 = ((ItemArmor)var8.getItem()).damageReduceAmount;
            var4 += var9;
         }
      }

      return var4;
   }

   public void damageArmor(float float1) {
      float1 /= 4.0F;
      if (float1 < 1.0F) {
         float1 = 1.0F;
      }

      for (int var5 = 0; var5 < this.armorInventory.length; var5++) {
         if (this.armorInventory[var5] != null && this.armorInventory[var5].getItem() instanceof ItemArmor) {
            this.armorInventory[var5].damageItem((int)float1, this.player);
            if (this.armorInventory[var5].stackSize == 0) {
               this.armorInventory[var5] = null;
            }
         }
      }
   }

   public void dropAllItems() {
      for (int var4 = 0; var4 < this.mainInventory.length; var4++) {
         if (this.mainInventory[var4] != null) {
            this.player.dropItem(this.mainInventory[var4], true, false);
            this.mainInventory[var4] = null;
         }
      }

      for (int var5 = 0; var5 < this.armorInventory.length; var5++) {
         if (this.armorInventory[var5] != null) {
            this.player.dropItem(this.armorInventory[var5], true, false);
            this.armorInventory[var5] = null;
         }
      }
   }

   @Override
   public void markDirty() {
      this.inventoryChanged = true;
   }

   public void setItemStack(ItemStack itemStack) {
      this.itemStack = itemStack;
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
      return !this.player.isDead && entityPlayer.getDistanceSqToEntity(this.player) <= 64.0;
   }

   public boolean hasItemStack(ItemStack itemStack) {
      for (ItemStack var8 : this.armorInventory) {
         if (var8 != null && var8.isItemEqual(itemStack)) {
            return true;
         }
      }

      for (ItemStack var12 : this.mainInventory) {
         if (var12 != null && var12.isItemEqual(itemStack)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void openInventory(EntityPlayer entityPlayer) {
   }

   @Override
   public void closeInventory(EntityPlayer entityPlayer) {
   }

   @Override
   public boolean isItemValidForSlot(int integer, ItemStack itemStack) {
      return true;
   }

   public void copyInventory(InventoryPlayer inventoryPlayer) {
      for (int var5 = 0; var5 < this.mainInventory.length; var5++) {
         this.mainInventory[var5] = ItemStack.copyItemStack(inventoryPlayer.mainInventory[var5]);
      }

      for (int var6 = 0; var6 < this.armorInventory.length; var6++) {
         this.armorInventory[var6] = ItemStack.copyItemStack(inventoryPlayer.armorInventory[var6]);
      }

      this.currentItem = inventoryPlayer.currentItem;
   }

   @Override
   public int getField(int integer) {
      return 0;
   }

   @Override
   public void setField(int integer1, int integer2) {
   }

   @Override
   public int getFieldCount() {
      return 0;
   }

   @Override
   public void clear() {
      Arrays.fill(this.mainInventory, null);
      Arrays.fill(this.armorInventory, null);
   }
}
