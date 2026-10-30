package net.minecraft.tileentity;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockHopper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.realms.Tezzelator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityHopper extends TileEntityLockable implements IHopper, ITickable {
   private ItemStack[] inventory = new ItemStack[5];
   private String customName;
   private int transferCooldown = -1;

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      NBTTagList var5 = nBTTagCompound.getTagList("Items", 10);
      this.inventory = new ItemStack[this.getSizeInventory()];
      if (nBTTagCompound.hasKey("CustomName", 8)) {
         this.customName = nBTTagCompound.getString("CustomName");
      }

      this.transferCooldown = nBTTagCompound.getInteger("TransferCooldown");

      for (int var6 = 0; var6 < var5.tagCount(); var6++) {
         NBTTagCompound var7 = var5.getCompoundTagAt(var6);
         byte var8 = var7.getByte("Slot");
         if (var8 >= 0 && var8 < this.inventory.length) {
            this.inventory[var8] = ItemStack.loadItemStackFromNBT(var7);
         }
      }
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      NBTTagList var5 = new NBTTagList();

      for (int var6 = 0; var6 < this.inventory.length; var6++) {
         if (this.inventory[var6] != null) {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setByte("Slot", (byte)var6);
            this.inventory[var6].writeToNBT(var7);
            var5.appendTag(var7);
         }
      }

      nBTTagCompound.setTag("Items", var5);
      nBTTagCompound.setInteger("TransferCooldown", this.transferCooldown);
      if (this.hasCustomName()) {
         nBTTagCompound.setString("CustomName", this.customName);
      }
   }

   @Override
   public void markDirty() {
      super.markDirty();
   }

   @Override
   public int getSizeInventory() {
      return this.inventory.length;
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return this.inventory[integer];
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.inventory[integer1] != null) {
         if (this.inventory[integer1].stackSize <= integer2) {
            ItemStack var7 = this.inventory[integer1];
            this.inventory[integer1] = null;
            return var7;
         }

         ItemStack var6 = this.inventory[integer1].splitStack(integer2);
         if (this.inventory[integer1].stackSize == 0) {
            this.inventory[integer1] = null;
         }

         return var6;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      if (this.inventory[integer] != null) {
         ItemStack var5 = this.inventory[integer];
         this.inventory[integer] = null;
         return var5;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      this.inventory[integer] = itemStack;
      if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
         itemStack.stackSize = this.getInventoryStackLimit();
      }
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.customName : "container.hopper";
   }

   @Override
   public boolean hasCustomName() {
      return this.customName != null && this.customName.length() > 0;
   }

   public void setCustomName(String string) {
      this.customName = string;
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   @Override
   public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
      return this.worldObj.getTileEntity(this.pos) == this
         && entityPlayer.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
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

   @Override
   public void update() {
      if (this.worldObj != null && !this.worldObj.isRemote) {
         this.transferCooldown--;
         if (!this.isOnTransferCooldown()) {
            this.setTransferCooldown(0);
            this.updateHopper();
         }
      }
   }

   public boolean updateHopper() {
      if (this.worldObj != null && !this.worldObj.isRemote && !this.isOnTransferCooldown() && BlockHopper.isEnabled(this.getBlockMetadata())) {
         boolean var4 = false;
         if (!this.isEmpty()) {
            var4 = this.transferItemsOut();
         }

         if (!this.isFull()) {
            var4 = captureDroppedItems(this) || var4;
         }

         if (var4) {
            this.setTransferCooldown(8);
            this.markDirty();
            return true;
         }
      }

      return false;
   }

   private boolean isEmpty() {
      for (ItemStack var7 : this.inventory) {
         if (var7 != null) {
            return false;
         }
      }

      return true;
   }

   private boolean isFull() {
      for (ItemStack var7 : this.inventory) {
         if (var7 == null || var7.stackSize != var7.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   private boolean transferItemsOut() {
      IInventory var4 = this.getInventoryForHopperTransfer();
      if (var4 == null) {
         return false;
      }

      EnumFacing var5 = BlockHopper.getFacing(this.getBlockMetadata()).getOpposite();
      if (this.isInventoryFull(var4, var5)) {
         return false;
      }

      for (int var6 = 0; var6 < this.getSizeInventory(); var6++) {
         if (this.getStackInSlot(var6) != null) {
            ItemStack var7 = this.getStackInSlot(var6).copy();
            ItemStack var8 = putStackInInventoryAllSlots(var4, this.decrStackSize(var6, 1), var5);
            if (var8 == null || var8.stackSize == 0) {
               var4.markDirty();
               return true;
            }

            this.setInventorySlotContents(var6, var7);
         }
      }

      return false;
   }

   private boolean isInventoryFull(IInventory iInventory, EnumFacing enumFacing) {
      if (iInventory instanceof ISidedInventory var6) {
         for (int var11 : var6.getSlotsForFace(enumFacing)) {
            ItemStack var12 = var6.getStackInSlot(var11);
            if (var12 == null || var12.stackSize != var12.getMaxStackSize()) {
               return false;
            }
         }
      } else {
         int var7 = iInventory.getSizeInventory();

         for (int var13 = 0; var13 < var7; var13++) {
            ItemStack var14 = iInventory.getStackInSlot(var13);
            if (var14 == null || var14.stackSize != var14.getMaxStackSize()) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean isInventoryEmpty(IInventory iInventory, EnumFacing enumFacing) {
      if (iInventory instanceof ISidedInventory var5) {
         for (int var10 : var5.getSlotsForFace(enumFacing)) {
            if (var5.getStackInSlot(var10) != null) {
               return false;
            }
         }
      } else {
         int var6 = iInventory.getSizeInventory();

         for (int var11 = 0; var11 < var6; var11++) {
            if (iInventory.getStackInSlot(var11) != null) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean captureDroppedItems(IHopper iHopper) {
      IInventory var4 = getHopperInventory(iHopper);
      if (var4 != null) {
         EnumFacing var5 = EnumFacing.DOWN;
         if (isInventoryEmpty(var4, var5)) {
            return false;
         }

         if (var4 instanceof ISidedInventory var6) {
            for (int var11 : var6.getSlotsForFace(var5)) {
               if (pullItemFromSlot(iHopper, var4, var11, var5)) {
                  return true;
               }
            }
         } else {
            int var7 = var4.getSizeInventory();

            for (int var14 = 0; var14 < var7; var14++) {
               if (pullItemFromSlot(iHopper, var4, var14, var5)) {
                  return true;
               }
            }
         }
      } else {
         for (EntityItem var13 : func_181556_a(iHopper.getWorld(), iHopper.getXPos(), iHopper.getYPos() + 1.0, iHopper.getZPos())) {
            if (putDropInInventoryAllSlots(iHopper, var13)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean pullItemFromSlot(IHopper iHopper, IInventory iInventory, int integer, EnumFacing enumFacing) {
      ItemStack var7 = iInventory.getStackInSlot(integer);
      if (var7 != null && canExtractItemFromSlot(iInventory, var7, integer, enumFacing)) {
         ItemStack var8 = var7.copy();
         ItemStack var9 = putStackInInventoryAllSlots(iHopper, iInventory.decrStackSize(integer, 1), null);
         if (var9 == null || var9.stackSize == 0) {
            iInventory.markDirty();
            return true;
         }

         iInventory.setInventorySlotContents(integer, var8);
      }

      return false;
   }

   public static boolean putDropInInventoryAllSlots(IInventory iInventory, EntityItem entityItem) {
      boolean var5 = false;
      if (entityItem == null) {
         return false;
      }

      ItemStack var6 = entityItem.Method464().copy();
      ItemStack var7 = putStackInInventoryAllSlots(iInventory, var6, null);
      if (var7 != null && var7.stackSize != 0) {
         entityItem.setEntityItemStack(var7);
      } else {
         var5 = true;
         entityItem.setDead();
      }

      return var5;
   }

   public static ItemStack putStackInInventoryAllSlots(IInventory iInventory, ItemStack itemStack, EnumFacing enumFacing) {
      if (iInventory instanceof ISidedInventory var6 && enumFacing != null) {
         int[] var9 = var6.getSlotsForFace(enumFacing);

         for (int var10 = 0; var10 < var9.length && itemStack != null && itemStack.stackSize > 0; var10++) {
            itemStack = insertStack(iInventory, itemStack, var9[var10], enumFacing);
         }
      } else {
         int var7 = iInventory.getSizeInventory();

         for (int var8 = 0; var8 < var7 && itemStack != null && itemStack.stackSize > 0; var8++) {
            itemStack = insertStack(iInventory, itemStack, var8, enumFacing);
         }
      }

      if (itemStack != null && itemStack.stackSize == 0) {
         itemStack = null;
      }

      return itemStack;
   }

   private static boolean canInsertItemInSlot(IInventory iInventory, ItemStack itemStack, int integer, EnumFacing enumFacing) {
      return iInventory.isItemValidForSlot(integer, itemStack)
         && (!(iInventory instanceof ISidedInventory) || ((ISidedInventory)iInventory).canInsertItem(integer, itemStack, enumFacing));
   }

   private static boolean canExtractItemFromSlot(IInventory iInventory, ItemStack itemStack, int integer, EnumFacing enumFacing) {
      return !(iInventory instanceof ISidedInventory) || ((ISidedInventory)iInventory).canExtractItem(integer, itemStack, enumFacing);
   }

   private static ItemStack insertStack(IInventory iInventory, ItemStack itemStack, int integer, EnumFacing enumFacing) {
      ItemStack var7 = iInventory.getStackInSlot(integer);
      if (canInsertItemInSlot(iInventory, itemStack, integer, enumFacing)) {
         boolean var8 = false;
         if (var7 == null) {
            iInventory.setInventorySlotContents(integer, itemStack);
            itemStack = null;
            var8 = true;
         } else if (canCombine(var7, itemStack)) {
            int var9 = itemStack.getMaxStackSize() - var7.stackSize;
            int var10 = Math.min(itemStack.stackSize, var9);
            itemStack.stackSize -= var10;
            var7.stackSize += var10;
            var8 = var10 > 0;
         }

         if (var8) {
            if (iInventory instanceof TileEntityHopper var11) {
               if (var11.mayTransfer()) {
                  var11.setTransferCooldown(8);
               }

               iInventory.markDirty();
            }

            iInventory.markDirty();
         }
      }

      return itemStack;
   }

   private IInventory getInventoryForHopperTransfer() {
      EnumFacing var1 = BlockHopper.getFacing(this.getBlockMetadata());
      return getInventoryAtPosition(
         this.getWorld(), this.pos.getX() + var1.getFrontOffsetX(), this.pos.getY() + var1.getFrontOffsetY(), this.pos.getZ() + var1.getFrontOffsetZ()
      );
   }

   public static IInventory getHopperInventory(IHopper iHopper) {
      return getInventoryAtPosition(iHopper.getWorld(), iHopper.getXPos(), iHopper.getYPos() + 1.0, iHopper.getZPos());
   }

   public static List<EntityItem> func_181556_a(World world, double double2, double double3, double double4) {
      return world.getEntitiesWithinAABB(
         EntityItem.class, new AxisAlignedBB(double2 - 0.5, double3 - 0.5, double4 - 0.5, double2 + 0.5, double3 + 0.5, double4 + 0.5), Tezzelator.a
      );
   }

   public static IInventory getInventoryAtPosition(World world, double double2, double double3, double double4) {
      IInventory var10 = null;
      int var11 = MathHelper.floor_double(double2);
      int var12 = MathHelper.floor_double(double3);
      int var13 = MathHelper.floor_double(double4);
      BlockPos var14 = new BlockPos(var11, var12, var13);
      Block var15 = world.getBlockState(var14).getBlock();
      if (var15.hasTileEntity()) {
         TileEntity var16 = world.getTileEntity(var14);
         if (var16 instanceof IInventory) {
            var10 = (IInventory)var16;
            if (var10 instanceof TileEntityChest && var15 instanceof BlockChest) {
               var10 = ((BlockChest)var15).getLockableContainer(world, var14);
            }
         }
      }

      if (var10 == null) {
         List var17 = world.getEntitiesInAABBexcluding(
            null, new AxisAlignedBB(double2 - 0.5, double3 - 0.5, double4 - 0.5, double2 + 0.5, double3 + 0.5, double4 + 0.5), Tezzelator.c
         );
         if (var17.size() > 0) {
            var10 = (IInventory)var17.get(world.rand.nextInt(var17.size()));
         }
      }

      return var10;
   }

   private static boolean canCombine(ItemStack itemStack1, ItemStack itemStack2) {
      return itemStack1.getItem() == itemStack2.getItem()
         && itemStack1.getMetadata() == itemStack2.getMetadata()
         && itemStack1.stackSize <= itemStack1.getMaxStackSize()
         && ItemStack.areItemStackTagsEqual(itemStack1, itemStack2);
   }

   @Override
   public double getXPos() {
      return this.pos.getX() + 0.5;
   }

   @Override
   public double getYPos() {
      return this.pos.getY() + 0.5;
   }

   @Override
   public double getZPos() {
      return this.pos.getZ() + 0.5;
   }

   public void setTransferCooldown(int integer) {
      this.transferCooldown = integer;
   }

   public boolean isOnTransferCooldown() {
      return this.transferCooldown > 0;
   }

   public boolean mayTransfer() {
      return this.transferCooldown <= 1;
   }

   @Override
   public String getGuiID() {
      return "minecraft:hopper";
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      return new ContainerHopper(inventoryPlayer, this, entityPlayer);
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
      Arrays.fill(this.inventory, null);
   }
}
