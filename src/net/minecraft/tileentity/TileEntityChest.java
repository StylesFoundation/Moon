package net.minecraft.tileentity;

import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityChest extends TileEntityLockable implements ITickable, IInventory {
   private ItemStack[] chestContents = new ItemStack[27];
   public boolean adjacentChestChecked;
   public TileEntityChest adjacentChestZNeg;
   public TileEntityChest adjacentChestXPos;
   public TileEntityChest adjacentChestXNeg;
   public TileEntityChest adjacentChestZPos;
   public float lidAngle;
   public float prevLidAngle;
   public int numPlayersUsing;
   private int ticksSinceSync;
   private int cachedChestType;
   private String customName;

   public TileEntityChest() {
      this.cachedChestType = -1;
   }

   public TileEntityChest(int integer) {
      this.cachedChestType = integer;
   }

   @Override
   public int getSizeInventory() {
      return 27;
   }

   @Override
   public ItemStack getStackInSlot(int integer) {
      return this.chestContents[integer];
   }

   @Override
   public ItemStack decrStackSize(int integer1, int integer2) {
      if (this.chestContents[integer1] != null) {
         if (this.chestContents[integer1].stackSize <= integer2) {
            ItemStack var7 = this.chestContents[integer1];
            this.chestContents[integer1] = null;
            this.markDirty();
            return var7;
         }

         ItemStack var6 = this.chestContents[integer1].splitStack(integer2);
         if (this.chestContents[integer1].stackSize == 0) {
            this.chestContents[integer1] = null;
         }

         this.markDirty();
         return var6;
      } else {
         return null;
      }
   }

   @Override
   public ItemStack removeStackFromSlot(int integer) {
      if (this.chestContents[integer] != null) {
         ItemStack var5 = this.chestContents[integer];
         this.chestContents[integer] = null;
         return var5;
      } else {
         return null;
      }
   }

   @Override
   public void setInventorySlotContents(int integer, ItemStack itemStack) {
      this.chestContents[integer] = itemStack;
      if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
         itemStack.stackSize = this.getInventoryStackLimit();
      }

      this.markDirty();
   }

   @Override
   public String getName() {
      return this.hasCustomName() ? this.customName : "container.chest";
   }

   @Override
   public boolean hasCustomName() {
      return this.customName != null && this.customName.length() > 0;
   }

   public void setCustomName(String string) {
      this.customName = string;
   }

   @Override
   public void readFromNBT(NBTTagCompound nBTTagCompound) {
      super.readFromNBT(nBTTagCompound);
      NBTTagList var5 = nBTTagCompound.getTagList("Items", 10);
      this.chestContents = new ItemStack[this.getSizeInventory()];
      if (nBTTagCompound.hasKey("CustomName", 8)) {
         this.customName = nBTTagCompound.getString("CustomName");
      }

      for (int var6 = 0; var6 < var5.tagCount(); var6++) {
         NBTTagCompound var7 = var5.getCompoundTagAt(var6);
         int var8 = var7.getByte("Slot") & 255;
         if (var8 >= 0 && var8 < this.chestContents.length) {
            this.chestContents[var8] = ItemStack.loadItemStackFromNBT(var7);
         }
      }
   }

   @Override
   public void writeToNBT(NBTTagCompound nBTTagCompound) {
      super.writeToNBT(nBTTagCompound);
      NBTTagList var5 = new NBTTagList();

      for (int var6 = 0; var6 < this.chestContents.length; var6++) {
         if (this.chestContents[var6] != null) {
            NBTTagCompound var7 = new NBTTagCompound();
            var7.setByte("Slot", (byte)var6);
            this.chestContents[var6].writeToNBT(var7);
            var5.appendTag(var7);
         }
      }

      nBTTagCompound.setTag("Items", var5);
      if (this.hasCustomName()) {
         nBTTagCompound.setString("CustomName", this.customName);
      }
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
   public void updateContainingBlockInfo() {
      super.updateContainingBlockInfo();
      this.adjacentChestChecked = false;
   }

   private void func_174910_a(TileEntityChest tileEntityChest, EnumFacing enumFacing) {
      if (tileEntityChest.isInvalid()) {
         this.adjacentChestChecked = false;
      } else if (this.adjacentChestChecked) {
         switch (enumFacing) {
            case NORTH:
               if (this.adjacentChestZNeg != tileEntityChest) {
                  this.adjacentChestChecked = false;
               }
               break;
            case SOUTH:
               if (this.adjacentChestZPos != tileEntityChest) {
                  this.adjacentChestChecked = false;
               }
               break;
            case EAST:
               if (this.adjacentChestXPos != tileEntityChest) {
                  this.adjacentChestChecked = false;
               }
               break;
            case WEST:
               if (this.adjacentChestXNeg != tileEntityChest) {
                  this.adjacentChestChecked = false;
               }
         }
      }
   }

   public void checkForAdjacentChests() {
      if (!this.adjacentChestChecked) {
         this.adjacentChestChecked = true;
         this.adjacentChestXNeg = this.getAdjacentChest(EnumFacing.WEST);
         this.adjacentChestXPos = this.getAdjacentChest(EnumFacing.EAST);
         this.adjacentChestZNeg = this.getAdjacentChest(EnumFacing.NORTH);
         this.adjacentChestZPos = this.getAdjacentChest(EnumFacing.SOUTH);
      }
   }

   protected TileEntityChest getAdjacentChest(EnumFacing enumFacing) {
      BlockPos var5 = this.pos.offset(enumFacing);
      if (this.isChestAt(var5) && this.worldObj.getTileEntity(var5) instanceof TileEntityChest var7) {
         var7.func_174910_a(this, enumFacing.getOpposite());
         return var7;
      } else {
         return null;
      }
   }

   private boolean isChestAt(BlockPos blockPos) {
      if (this.worldObj == null) {
         return false;
      }

      Block var5 = this.worldObj.getBlockState(blockPos).getBlock();
      return var5 instanceof BlockChest && ((BlockChest)var5).chestType == this.getChestType();
   }

   @Override
   public void update() {
      this.checkForAdjacentChests();
      int var4 = this.pos.getX();
      int var5 = this.pos.getY();
      int var6 = this.pos.getZ();
      this.ticksSinceSync++;
      if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + var4 + var5 + var6) % 200 == 0) {
         this.numPlayersUsing = 0;

         for (EntityPlayer var9 : this.worldObj
            .getEntitiesWithinAABB(
               EntityPlayer.class, new AxisAlignedBB(var4 - 5.0F, var5 - 5.0F, var6 - 5.0F, var4 + 1 + 5.0F, var5 + 1 + 5.0F, var6 + 1 + 5.0F)
            )) {
            if (var9.openContainer instanceof ContainerChest) {
               IInventory var10 = ((ContainerChest)var9.openContainer).getLowerChestInventory();
               if (var10 == this || var10 instanceof InventoryLargeChest && ((InventoryLargeChest)var10).isPartOfLargeChest(this)) {
                  this.numPlayersUsing++;
               }
            }
         }
      }

      this.prevLidAngle = this.lidAngle;
      float var7 = 0.1F;
      if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
         double var14 = var4 + 0.5;
         double var17 = var6 + 0.5;
         if (this.adjacentChestZPos != null) {
            var17 += 0.5;
         }

         if (this.adjacentChestXPos != null) {
            var14 += 0.5;
         }

         this.worldObj.playSoundEffect(var14, var5 + 0.5, var17, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
      }

      if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
         float var15 = this.lidAngle;
         if (this.numPlayersUsing > 0) {
            this.lidAngle += var7;
         } else {
            this.lidAngle -= var7;
         }

         if (this.lidAngle > 1.0F) {
            this.lidAngle = 1.0F;
         }

         float var16 = 0.5F;
         if (this.lidAngle < var16 && var15 >= var16 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double var18 = var4 + 0.5;
            double var12 = var6 + 0.5;
            if (this.adjacentChestZPos != null) {
               var12 += 0.5;
            }

            if (this.adjacentChestXPos != null) {
               var18 += 0.5;
            }

            this.worldObj.playSoundEffect(var18, var5 + 0.5, var12, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
         }

         if (this.lidAngle < 0.0F) {
            this.lidAngle = 0.0F;
         }
      }
   }

   @Override
   public boolean receiveClientEvent(int integer1, int integer2) {
      if (integer1 == 1) {
         this.numPlayersUsing = integer2;
         return true;
      } else {
         return super.receiveClientEvent(integer1, integer2);
      }
   }

   @Override
   public void openInventory(EntityPlayer entityPlayer) {
      if (!entityPlayer.isSpectator()) {
         if (this.numPlayersUsing < 0) {
            this.numPlayersUsing = 0;
         }

         this.numPlayersUsing++;
         this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
         this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
         this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
      }
   }

   @Override
   public void closeInventory(EntityPlayer entityPlayer) {
      if (!entityPlayer.isSpectator() && this.getBlockType() instanceof BlockChest) {
         this.numPlayersUsing--;
         this.worldObj.addBlockEvent(this.pos, this.getBlockType(), 1, this.numPlayersUsing);
         this.worldObj.notifyNeighborsOfStateChange(this.pos, this.getBlockType());
         this.worldObj.notifyNeighborsOfStateChange(this.pos.down(), this.getBlockType());
      }
   }

   @Override
   public boolean isItemValidForSlot(int integer, ItemStack itemStack) {
      return true;
   }

   @Override
   public void invalidate() {
      super.invalidate();
      this.updateContainingBlockInfo();
      this.checkForAdjacentChests();
   }

   public int getChestType() {
      if (this.cachedChestType == -1) {
         if (this.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
            return 0;
         }

         this.cachedChestType = ((BlockChest)this.getBlockType()).chestType;
      }

      return this.cachedChestType;
   }

   @Override
   public String getGuiID() {
      return "minecraft:chest";
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      return new ContainerChest(inventoryPlayer, this, entityPlayer);
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
      Arrays.fill(this.chestContents, null);
   }
}
