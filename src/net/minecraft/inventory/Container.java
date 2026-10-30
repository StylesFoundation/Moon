package net.minecraft.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public abstract class Container {
   public List<ItemStack> inventoryItemStacks = Lists.newArrayList();
   public List<Slot> inventorySlots = Lists.newArrayList();
   public int windowId;
   private short transactionID;
   private int dragMode = -1;
   private int dragEvent;
   private final Set<Slot> dragSlots = Sets.newHashSet();
   protected List<ICrafting> crafters = Lists.newArrayList();
   private final Set<EntityPlayer> playerList = Sets.newHashSet();

   protected Slot addSlotToContainer(Slot slot) {
      slot.slotNumber = this.inventorySlots.size();
      this.inventorySlots.add(slot);
      this.inventoryItemStacks.add(null);
      return slot;
   }

   public void onCraftGuiOpened(ICrafting iCrafting) {
      if (this.crafters.contains(iCrafting)) {
         throw new IllegalArgumentException("Listener already listening");
      }

      this.crafters.add(iCrafting);
      iCrafting.updateCraftingInventory(this, this.getInventory());
      this.detectAndSendChanges();
   }

   public void removeCraftingFromCrafters(ICrafting iCrafting) {
      this.crafters.remove(iCrafting);
   }

   public List<ItemStack> getInventory() {
      ArrayList var4 = Lists.newArrayList();

      for (Slot var6 : this.inventorySlots) {
         var4.add(var6.getStack());
      }

      return var4;
   }

   public void detectAndSendChanges() {
      for (int var4 = 0; var4 < this.inventorySlots.size(); var4++) {
         ItemStack var5 = this.inventorySlots.get(var4).getStack();
         ItemStack var6 = this.inventoryItemStacks.get(var4);
         if (!ItemStack.areItemStacksEqual(var6, var5)) {
            var6 = var5 == null ? null : var5.copy();
            this.inventoryItemStacks.set(var4, var6);

            for (ICrafting var8 : this.crafters) {
               var8.sendSlotContents(this, var4, var6);
            }
         }
      }
   }

   public boolean enchantItem(EntityPlayer entityPlayer, int integer) {
      return false;
   }

   public Slot getSlotFromInventory(IInventory iInventory, int integer) {
      for (Slot var7 : this.inventorySlots) {
         if (var7.isHere(iInventory, integer)) {
            return var7;
         }
      }

      return null;
   }

   public Slot getSlot(int integer) {
      return this.inventorySlots.get(integer);
   }

   public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int integer) {
      Slot var6 = this.inventorySlots.get(integer);
      return var6 != null ? var6.getStack() : null;
   }

   public ItemStack slotClick(int integer1, int integer2, int integer3, EntityPlayer entityPlayer) {
      ItemStack var8 = null;
      InventoryPlayer var9 = entityPlayer.inventory;
      if (integer3 == 5) {
         int var10 = this.dragEvent;
         this.dragEvent = getDragEvent(integer2);
         if ((var10 != 1 || this.dragEvent != 2) && var10 != this.dragEvent) {
            this.resetDrag();
         } else if (var9.getItemStack() == null) {
            this.resetDrag();
         } else if (this.dragEvent == 0) {
            this.dragMode = extractDragMode(integer2);
            if (isValidDragMode(this.dragMode, entityPlayer)) {
               this.dragEvent = 1;
               this.dragSlots.clear();
            } else {
               this.resetDrag();
            }
         } else if (this.dragEvent == 1) {
            Slot var11 = this.inventorySlots.get(integer1);
            if (var11 != null
               && canAddItemToSlot(var11, var9.getItemStack(), true)
               && var11.isItemValid(var9.getItemStack())
               && var9.getItemStack().stackSize > this.dragSlots.size()
               && this.canDragIntoSlot(var11)) {
               this.dragSlots.add(var11);
            }
         } else if (this.dragEvent == 2) {
            if (!this.dragSlots.isEmpty()) {
               ItemStack var25 = var9.getItemStack().copy();
               int var12 = var9.getItemStack().stackSize;

               for (Slot var14 : this.dragSlots) {
                  if (var14 != null
                     && canAddItemToSlot(var14, var9.getItemStack(), true)
                     && var14.isItemValid(var9.getItemStack())
                     && var9.getItemStack().stackSize >= this.dragSlots.size()
                     && this.canDragIntoSlot(var14)) {
                     ItemStack var15 = var25.copy();
                     int var16 = var14.getHasStack() ? var14.getStack().stackSize : 0;
                     computeStackSize(this.dragSlots, this.dragMode, var15, var16);
                     if (var15.stackSize > var15.getMaxStackSize()) {
                        var15.stackSize = var15.getMaxStackSize();
                     }

                     if (var15.stackSize > var14.getItemStackLimit(var15)) {
                        var15.stackSize = var14.getItemStackLimit(var15);
                     }

                     var12 -= var15.stackSize - var16;
                     var14.putStack(var15);
                  }
               }

               var25.stackSize = var12;
               if (var25.stackSize <= 0) {
                  var25 = null;
               }

               var9.setItemStack(var25);
            }

            this.resetDrag();
         } else {
            this.resetDrag();
         }
      } else if (this.dragEvent != 0) {
         this.resetDrag();
      } else if ((integer3 == 0 || integer3 == 1) && (integer2 == 0 || integer2 == 1)) {
         if (integer1 == -999) {
            if (var9.getItemStack() != null) {
               if (integer2 == 0) {
                  entityPlayer.dropPlayerItemWithRandomChoice(var9.getItemStack(), true);
                  var9.setItemStack(null);
               }

               if (integer2 == 1) {
                  entityPlayer.dropPlayerItemWithRandomChoice(var9.getItemStack().splitStack(1), true);
                  if (var9.getItemStack().stackSize == 0) {
                     var9.setItemStack(null);
                  }
               }
            }
         } else if (integer3 == 1) {
            if (integer1 < 0) {
               return null;
            }

            Slot var23 = this.inventorySlots.get(integer1);
            if (var23 != null && var23.canTakeStack(entityPlayer)) {
               ItemStack var30 = this.transferStackInSlot(entityPlayer, integer1);
               if (var30 != null) {
                  Item var34 = var30.getItem();
                  var8 = var30.copy();
                  if (var23.getStack() != null && var23.getStack().getItem() == var34) {
                     this.retrySlotClick(integer1, integer2, true, entityPlayer);
                  }
               }
            }
         } else {
            if (integer1 < 0) {
               return null;
            }

            Slot var24 = this.inventorySlots.get(integer1);
            if (var24 != null) {
               ItemStack var31 = var24.getStack();
               ItemStack var35 = var9.getItemStack();
               if (var31 != null) {
                  var8 = var31.copy();
               }

               if (var31 == null) {
                  if (var35 != null && var24.isItemValid(var35)) {
                     int var41 = integer2 == 0 ? var35.stackSize : 1;
                     if (var41 > var24.getItemStackLimit(var35)) {
                        var41 = var24.getItemStackLimit(var35);
                     }

                     if (var35.stackSize >= var41) {
                        var24.putStack(var35.splitStack(var41));
                     }

                     if (var35.stackSize == 0) {
                        var9.setItemStack(null);
                     }
                  }
               } else if (var24.canTakeStack(entityPlayer)) {
                  if (var35 == null) {
                     int var40 = integer2 == 0 ? var31.stackSize : (var31.stackSize + 1) / 2;
                     ItemStack var44 = var24.decrStackSize(var40);
                     var9.setItemStack(var44);
                     if (var31.stackSize == 0) {
                        var24.putStack(null);
                     }

                     var24.onPickupFromSlot(entityPlayer, var9.getItemStack());
                  } else if (var24.isItemValid(var35)) {
                     if (var31.getItem() == var35.getItem() && var31.getMetadata() == var35.getMetadata() && ItemStack.areItemStackTagsEqual(var31, var35)) {
                        int var39 = integer2 == 0 ? var35.stackSize : 1;
                        if (var39 > var24.getItemStackLimit(var35) - var31.stackSize) {
                           var39 = var24.getItemStackLimit(var35) - var31.stackSize;
                        }

                        if (var39 > var35.getMaxStackSize() - var31.stackSize) {
                           var39 = var35.getMaxStackSize() - var31.stackSize;
                        }

                        var35.splitStack(var39);
                        if (var35.stackSize == 0) {
                           var9.setItemStack(null);
                        }

                        var31.stackSize += var39;
                     } else if (var35.stackSize <= var24.getItemStackLimit(var35)) {
                        var24.putStack(var35);
                        var9.setItemStack(var31);
                     }
                  } else if (var31.getItem() == var35.getItem()
                     && var35.getMaxStackSize() > 1
                     && (!var31.getHasSubtypes() || var31.getMetadata() == var35.getMetadata())
                     && ItemStack.areItemStackTagsEqual(var31, var35)) {
                     int var38 = var31.stackSize;
                     if (var38 > 0 && var38 + var35.stackSize <= var35.getMaxStackSize()) {
                        var35.stackSize += var38;
                        if (var24.decrStackSize(var38).stackSize == 0) {
                           var24.putStack(null);
                        }

                        var24.onPickupFromSlot(entityPlayer, var9.getItemStack());
                     }
                  }
               }

               var24.onSlotChanged();
            }
         }
      } else if (integer3 == 2 && integer2 >= 0 && integer2 < 9) {
         Slot var22 = this.inventorySlots.get(integer1);
         if (var22.canTakeStack(entityPlayer)) {
            ItemStack var29 = var9.getStackInSlot(integer2);
            boolean var33 = var29 == null || var22.inventory == var9 && var22.isItemValid(var29);
            int var37 = -1;
            if (!var33) {
               var37 = var9.getFirstEmptyStack();
               var33 |= var37 > -1;
            }

            if (var22.getHasStack() && var33) {
               ItemStack var43 = var22.getStack();
               var9.setInventorySlotContents(integer2, var43.copy());
               if ((var22.inventory != var9 || !var22.isItemValid(var29)) && var29 != null) {
                  if (var37 > -1) {
                     var9.addItemStackToInventory(var29);
                     var22.decrStackSize(var43.stackSize);
                     var22.putStack(null);
                     var22.onPickupFromSlot(entityPlayer, var43);
                  }
               } else {
                  var22.decrStackSize(var43.stackSize);
                  var22.putStack(var29);
                  var22.onPickupFromSlot(entityPlayer, var43);
               }
            } else if (!var22.getHasStack() && var29 != null && var22.isItemValid(var29)) {
               var9.setInventorySlotContents(integer2, null);
               var22.putStack(var29);
            }
         }
      } else if (integer3 == 3 && entityPlayer.capabilities.isCreativeMode && var9.getItemStack() == null && integer1 >= 0) {
         Slot var21 = this.inventorySlots.get(integer1);
         if (var21 != null && var21.getHasStack()) {
            ItemStack var28 = var21.getStack().copy();
            var28.stackSize = var28.getMaxStackSize();
            var9.setItemStack(var28);
         }
      } else if (integer3 == 4 && var9.getItemStack() == null && integer1 >= 0) {
         Slot var20 = this.inventorySlots.get(integer1);
         if (var20 != null && var20.getHasStack() && var20.canTakeStack(entityPlayer)) {
            ItemStack var27 = var20.decrStackSize(integer2 == 0 ? 1 : var20.getStack().stackSize);
            var20.onPickupFromSlot(entityPlayer, var27);
            entityPlayer.dropPlayerItemWithRandomChoice(var27, true);
         }
      } else if (integer3 == 6 && integer1 >= 0) {
         Slot var19 = this.inventorySlots.get(integer1);
         ItemStack var26 = var9.getItemStack();
         if (var26 != null && (var19 == null || !var19.getHasStack() || !var19.canTakeStack(entityPlayer))) {
            int var32 = integer2 == 0 ? 0 : this.inventorySlots.size() - 1;
            int var36 = integer2 == 0 ? 1 : -1;

            for (int var42 = 0; var42 < 2; var42++) {
               for (int var45 = var32; var45 >= 0 && var45 < this.inventorySlots.size() && var26.stackSize < var26.getMaxStackSize(); var45 += var36) {
                  Slot var46 = this.inventorySlots.get(var45);
                  if (var46.getHasStack()
                     && canAddItemToSlot(var46, var26, true)
                     && var46.canTakeStack(entityPlayer)
                     && this.canMergeSlot(var26, var46)
                     && (var42 != 0 || var46.getStack().stackSize != var46.getStack().getMaxStackSize())) {
                     int var17 = Math.min(var26.getMaxStackSize() - var26.stackSize, var46.getStack().stackSize);
                     ItemStack var18 = var46.decrStackSize(var17);
                     var26.stackSize += var17;
                     if (var18.stackSize <= 0) {
                        var46.putStack(null);
                     }

                     var46.onPickupFromSlot(entityPlayer, var18);
                  }
               }
            }
         }

         this.detectAndSendChanges();
      }

      return var8;
   }

   public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
      return true;
   }

   protected void retrySlotClick(int integer1, int integer2, boolean boolean3, EntityPlayer entityPlayer) {
      this.slotClick(integer1, integer2, 1, entityPlayer);
   }

   public void onContainerClosed(EntityPlayer entityPlayer) {
      InventoryPlayer var5 = entityPlayer.inventory;
      if (var5.getItemStack() != null) {
         entityPlayer.dropPlayerItemWithRandomChoice(var5.getItemStack(), false);
         var5.setItemStack(null);
      }
   }

   public void onCraftMatrixChanged(IInventory iInventory) {
      this.detectAndSendChanges();
   }

   public void putStackInSlot(int integer, ItemStack itemStack) {
      this.getSlot(integer).putStack(itemStack);
   }

   public void putStacksInSlots(ItemStack[] arr) {
      for (int var5 = 0; var5 < arr.length; var5++) {
         this.getSlot(var5).putStack(arr[var5]);
      }
   }

   public void updateProgressBar(int integer1, int integer2) {
   }

   public short getNextTransactionID(InventoryPlayer inventoryPlayer) {
      this.transactionID++;
      return this.transactionID;
   }

   public boolean getCanCraft(EntityPlayer entityPlayer) {
      return !this.playerList.contains(entityPlayer);
   }

   public void setCanCraft(EntityPlayer entityPlayer, boolean boolean2) {
      if (boolean2) {
         this.playerList.remove(entityPlayer);
      } else {
         this.playerList.add(entityPlayer);
      }
   }

   public abstract boolean canInteractWith(EntityPlayer entityPlayer);

   protected boolean mergeItemStack(ItemStack itemStack, int integer2, int integer3, boolean boolean4) {
      boolean var8 = false;
      int var9 = integer2;
      if (boolean4) {
         var9 = integer3 - 1;
      }

      if (itemStack.isStackable()) {
         while (itemStack.stackSize > 0 && (!boolean4 && var9 < integer3 || boolean4 && var9 >= integer2)) {
            Slot var10 = this.inventorySlots.get(var9);
            ItemStack var11 = var10.getStack();
            if (var11 != null
               && var11.getItem() == itemStack.getItem()
               && (!itemStack.getHasSubtypes() || itemStack.getMetadata() == var11.getMetadata())
               && ItemStack.areItemStackTagsEqual(itemStack, var11)) {
               int var12 = var11.stackSize + itemStack.stackSize;
               if (var12 <= itemStack.getMaxStackSize()) {
                  itemStack.stackSize = 0;
                  var11.stackSize = var12;
                  var10.onSlotChanged();
                  var8 = true;
               } else if (var11.stackSize < itemStack.getMaxStackSize()) {
                  itemStack.stackSize = itemStack.stackSize - (itemStack.getMaxStackSize() - var11.stackSize);
                  var11.stackSize = itemStack.getMaxStackSize();
                  var10.onSlotChanged();
                  var8 = true;
               }
            }

            if (boolean4) {
               var9--;
            } else {
               var9++;
            }
         }
      }

      if (itemStack.stackSize > 0) {
         if (boolean4) {
            var9 = integer3 - 1;
         } else {
            var9 = integer2;
         }

         while (!boolean4 && var9 < integer3 || boolean4 && var9 >= integer2) {
            Slot var14 = this.inventorySlots.get(var9);
            ItemStack var15 = var14.getStack();
            if (var15 == null) {
               var14.putStack(itemStack.copy());
               var14.onSlotChanged();
               itemStack.stackSize = 0;
               var8 = true;
               break;
            }

            if (boolean4) {
               var9--;
            } else {
               var9++;
            }
         }
      }

      return var8;
   }

   public static int extractDragMode(int integer) {
      return integer >> 2 & 3;
   }

   public static int getDragEvent(int integer) {
      return integer & 3;
   }

   public static int func_94534_d(int integer1, int integer2) {
      return integer1 & 3 | (integer2 & 3) << 2;
   }

   public static boolean isValidDragMode(int integer, EntityPlayer entityPlayer) {
      return integer == 0 || integer == 1 || integer == 2 && entityPlayer.capabilities.isCreativeMode;
   }

   protected void resetDrag() {
      this.dragEvent = 0;
      this.dragSlots.clear();
   }

   public static boolean canAddItemToSlot(Slot slot, ItemStack itemStack, boolean boolean3) {
      boolean var6 = slot == null || !slot.getHasStack();
      if (slot != null
         && slot.getHasStack()
         && itemStack != null
         && itemStack.isItemEqual(slot.getStack())
         && ItemStack.areItemStackTagsEqual(slot.getStack(), itemStack)) {
         int var10002 = boolean3 ? 0 : itemStack.stackSize;
         var6 |= slot.getStack().stackSize + var10002 <= itemStack.getMaxStackSize();
      }

      return var6;
   }

   public static void computeStackSize(Set<Slot> set, int integer2, ItemStack itemStack, int integer4) {
      switch (integer2) {
         case 0:
            itemStack.stackSize = MathHelper.floor_float((float)itemStack.stackSize / set.size());
            break;
         case 1:
            itemStack.stackSize = 1;
            break;
         case 2:
            itemStack.stackSize = itemStack.getItem().getItemStackLimit();
      }

      itemStack.stackSize += integer4;
   }

   public boolean canDragIntoSlot(Slot slot) {
      return true;
   }

   public static int calcRedstone(TileEntity tileEntity) {
      return tileEntity instanceof IInventory ? calcRedstoneFromInventory((IInventory)tileEntity) : 0;
   }

   public static int calcRedstoneFromInventory(IInventory iInventory) {
      if (iInventory == null) {
         return 0;
      }

      int var4 = 0;
      float var5 = 0.0F;

      for (int var6 = 0; var6 < iInventory.getSizeInventory(); var6++) {
         ItemStack var7 = iInventory.getStackInSlot(var6);
         if (var7 != null) {
            var5 += (float)var7.stackSize / Math.min(iInventory.getInventoryStackLimit(), var7.getMaxStackSize());
            var4++;
         }
      }

      var5 /= iInventory.getSizeInventory();
      return MathHelper.floor_float(var5 * 14.0F) + (var4 > 0 ? 1 : 0);
   }
}
