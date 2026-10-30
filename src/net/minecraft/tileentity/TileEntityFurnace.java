package net.minecraft.tileentity;

import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.MathHelper;

public class TileEntityFurnace extends TileEntityLockable implements ITickable, ISidedInventory {
   private static final int[] slotsTop = new int[]{0};
   private static final int[] slotsBottom = new int[]{2, 1};
   private static final int[] slotsSides = new int[]{1};
   private ItemStack[] furnaceItemStacks = new ItemStack[3];
   private int furnaceBurnTime;
   private int currentItemBurnTime;
   private int cookTime;
   private int totalCookTime;
   public String furnaceCustomName;

   @Override
   public int getSizeInventory() {
      return this.furnaceItemStacks.length;
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return this.furnaceItemStacks[integer];
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.furnaceItemStacks[integer1] != null) {
         if (this.furnaceItemStacks[integer1].stackSize <= integer2) {
            ItemStack var7 = this.furnaceItemStacks[integer1];
            this.furnaceItemStacks[integer1] = null;
            return var7;
         }

         ItemStack var6 = this.furnaceItemStacks[integer1].splitStack(integer2);
         if (this.furnaceItemStacks[integer1].stackSize == 0) {
            this.furnaceItemStacks[integer1] = null;
         }

         return var6;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      if (this.furnaceItemStacks[integer] != null) {
         ItemStack var5 = this.furnaceItemStacks[integer];
         this.furnaceItemStacks[integer] = null;
         return var5;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      boolean var6 = itemStack != null
         && itemStack.isItemEqual(this.furnaceItemStacks[integer])
         && ItemStack.areItemStackTagsEqual(itemStack, this.furnaceItemStacks[integer]);
      this.furnaceItemStacks[integer] = itemStack;
      if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
         itemStack.stackSize = this.getInventoryStackLimit();
      }

      if (integer == 0 && !var6) {
         this.totalCookTime = this.getCookTime(itemStack);
         this.cookTime = 0;
         this.markDirty();
      }
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.furnaceCustomName : "container.furnace";
   }

   @Override
   public boolean hasCustomName() {
      return this.furnaceCustomName != null && this.furnaceCustomName.length() > 0;
   }

   public void setCustomInventoryName(String string) {
      this.furnaceCustomName = string;
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      NBTTagList var5 = nBTTagCompound.getTagList("Items", 10);
      this.furnaceItemStacks = new ItemStack[this.getSizeInventory()];

      for (int var6 = 0; var6 < var5.tagCount(); var6++) {
         NBTTagCompound var7 = var5.getCompoundTagAt(var6);
         byte var8 = var7.getByte("Slot");
         if (var8 >= 0 && var8 < this.furnaceItemStacks.length) {
            this.furnaceItemStacks[var8] = ItemStack.loadItemStackFromNBT(var7);
         }
      }

      this.furnaceBurnTime = nBTTagCompound.getShort("BurnTime");
      this.cookTime = nBTTagCompound.getShort("CookTime");
      this.totalCookTime = nBTTagCompound.getShort("CookTimeTotal");
      this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
      if (nBTTagCompound.hasKey("CustomName", 8)) {
         this.furnaceCustomName = nBTTagCompound.getString("CustomName");
      }
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      nBTTagCompound.setShort("BurnTime", (short)this.furnaceBurnTime);
      nBTTagCompound.setShort("CookTime", (short)this.cookTime);
      nBTTagCompound.setShort("CookTimeTotal", (short)this.totalCookTime);
      NBTTagList var5 = new NBTTagList();

      for (int var6 = 0; var6 < this.furnaceItemStacks.length; var6++) {
         if (this.furnaceItemStacks[var6] != null) {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setByte("Slot", (byte)var6);
            this.furnaceItemStacks[var6].writeToNBT(var7);
            var5.appendTag(var7);
         }
      }

      nBTTagCompound.setTag("Items", var5);
      if (this.hasCustomName()) {
         nBTTagCompound.setString("CustomName", this.furnaceCustomName);
      }
   }

   @Override
   public int getInventoryStackLimit() {
      return 64;
   }

   public boolean isBurning() {
      return this.furnaceBurnTime > 0;
   }

   public static boolean isBurning(IInventory iInventory) {
      return iInventory.getField(0) > 0;
   }

   @Override
   public void update() {
      boolean var4 = this.isBurning();
      boolean var5 = false;
      if (this.isBurning()) {
         this.furnaceBurnTime--;
      }

      if (!this.worldObj.isRemote) {
         if (this.isBurning() || this.furnaceItemStacks[1] != null && this.furnaceItemStacks[0] != null) {
            if (!this.isBurning() && this.canSmelt()) {
               this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.furnaceItemStacks[1]);
               if (this.isBurning()) {
                  var5 = true;
                  if (this.furnaceItemStacks[1] != null) {
                     this.furnaceItemStacks[1].stackSize--;
                     if (this.furnaceItemStacks[1].stackSize == 0) {
                        Item var6 = this.furnaceItemStacks[1].getItem().getContainerItem();
                        this.furnaceItemStacks[1] = var6 != null ? new ItemStack(var6) : null;
                     }
                  }
               }
            }

            if (this.isBurning() && this.canSmelt()) {
               this.cookTime++;
               if (this.cookTime == this.totalCookTime) {
                  this.cookTime = 0;
                  this.totalCookTime = this.getCookTime(this.furnaceItemStacks[0]);
                  this.smeltItem();
                  var5 = true;
               }
            } else {
               this.cookTime = 0;
            }
         } else if (!this.isBurning() && this.cookTime > 0) {
            this.cookTime = MathHelper.clamp_int(this.cookTime - 2, 0, this.totalCookTime);
         }

         if (var4 != this.isBurning()) {
            var5 = true;
            BlockFurnace.setState(this.isBurning(), this.worldObj, this.pos);
         }
      }

      if (var5) {
         this.markDirty();
      }
   }

   public int getCookTime(ItemStack itemStack) {
      return 200;
   }

   private boolean canSmelt() {
      if (this.furnaceItemStacks[0] == null) {
         return false;
      }

      ItemStack var4 = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
      return var4 != null
         && (
            this.furnaceItemStacks[2] == null
               || this.furnaceItemStacks[2].isItemEqual(var4)
                  && (
                     this.furnaceItemStacks[2].stackSize < this.getInventoryStackLimit()
                           && this.furnaceItemStacks[2].stackSize < this.furnaceItemStacks[2].getMaxStackSize()
                        || this.furnaceItemStacks[2].stackSize < var4.getMaxStackSize()
                  )
         );
   }

   public void smeltItem() {
      if (this.canSmelt()) {
         ItemStack var4 = FurnaceRecipes.instance().getSmeltingResult(this.furnaceItemStacks[0]);
         if (this.furnaceItemStacks[2] == null) {
            this.furnaceItemStacks[2] = var4.copy();
         } else if (this.furnaceItemStacks[2].getItem() == var4.getItem()) {
            this.furnaceItemStacks[2].stackSize++;
         }

         if (this.furnaceItemStacks[0].getItem() == Item.getItemFromBlock(Blocks.sponge)
            && this.furnaceItemStacks[0].getMetadata() == 1
            && this.furnaceItemStacks[1] != null
            && this.furnaceItemStacks[1].getItem() == Items.bucket) {
            this.furnaceItemStacks[1] = new ItemStack(Items.water_bucket);
         }

         this.furnaceItemStacks[0].stackSize--;
         if (this.furnaceItemStacks[0].stackSize <= 0) {
            this.furnaceItemStacks[0] = null;
         }
      }
   }

   public static int getItemBurnTime(ItemStack itemStack) {
      if (itemStack == null) {
         return 0;
      }

      Item var4 = itemStack.getItem();
      if (var4 instanceof ItemBlock && Block.getBlockFromItem(var4) != Blocks.air) {
         Block var5 = Block.getBlockFromItem(var4);
         if (var5 == Blocks.wooden_slab) {
            return 150;
         }

         if (var5.getMaterial() == Material.wood) {
            return 300;
         }

         if (var5 == Blocks.coal_block) {
            return 16000;
         }
      }

      return var4 instanceof ItemTool && ((ItemTool)var4).getToolMaterialName().equals("WOOD")
         ? 200
         : (
            var4 instanceof ItemSword && ((ItemSword)var4).getToolMaterialName().equals("WOOD")
               ? 200
               : (
                  var4 instanceof ItemHoe && ((ItemHoe)var4).getMaterialName().equals("WOOD")
                     ? 200
                     : (
                        var4 == Items.stick
                           ? 100
                           : (
                              var4 == Items.coal
                                 ? 1600
                                 : (
                                    var4 == Items.lava_bucket
                                       ? 20000
                                       : (var4 == Item.getItemFromBlock(Blocks.sapling) ? 100 : (var4 == Items.blaze_rod ? 2400 : 0))
                                 )
                           )
                     )
               )
         );
   }

   public static boolean isItemFuel(ItemStack itemStack) {
      return getItemBurnTime(itemStack) > 0;
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
      return integer != 2 && (integer != 1 || isItemFuel(itemStack) || SlotFurnaceFuel.isBucket(itemStack));
   }

   @Override
   public int[] getSlotsForFace(EnumFacing enumFacing) {
      return enumFacing == EnumFacing.DOWN ? slotsBottom : (enumFacing == EnumFacing.UP ? slotsTop : slotsSides);
   }

   @Override
   public boolean canInsertItem(int integer, ItemStack itemStack, EnumFacing enumFacing) {
      return this.isItemValidForSlot(integer, itemStack);
   }

   @Override
   public boolean canExtractItem(int integer, ItemStack itemStack, EnumFacing enumFacing) {
      if (enumFacing == EnumFacing.DOWN && integer == 1) {
         Item var7 = itemStack.getItem();
         return var7 == Items.water_bucket || var7 == Items.bucket;
      } else {
         return true;
      }
   }

   @Override
   public String getGuiID() {
      return "minecraft:furnace";
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      return new ContainerFurnace(inventoryPlayer, this);
   }

   @Override
   public int getField(int integer) {
      switch (integer) {
         case 0:
            return this.furnaceBurnTime;
         case 1:
            return this.currentItemBurnTime;
         case 2:
            return this.cookTime;
         case 3:
            return this.totalCookTime;
         default:
            return 0;
      }
   }

   @Override
   public void setField(int integer1, int integer2) {
      switch (integer1) {
         case 0:
            this.furnaceBurnTime = integer2;
            break;
         case 1:
            this.currentItemBurnTime = integer2;
            break;
         case 2:
            this.cookTime = integer2;
            break;
         case 3:
            this.totalCookTime = integer2;
      }
   }

   @Override
   public int getFieldCount() {
      return 4;
   }

   @Override
   public void clear() {
      Arrays.fill(this.furnaceItemStacks, null);
   }
}
