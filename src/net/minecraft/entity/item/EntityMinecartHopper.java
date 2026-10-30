package net.minecraft.entity.item;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.realms.Tezzelator;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {
   private boolean isBlocked = true;
   private int transferTicker = -1;
   private final BlockPos field_174900_c = BlockPos.ORIGIN;

   public EntityMinecartHopper(World world) {
      super(world);
   }

   public EntityMinecartHopper(World world, double double2, double double3, double double4) {
      super(world, double2, double3, double4);
   }

   @Override
   public EntityMinecart.EnumMinecartType getMinecartType() {
      return EntityMinecart.EnumMinecartType.HOPPER;
   }

   @Override
   public IBlockState getDefaultDisplayTile() {
      return Blocks.hopper.getDefaultState();
   }

   @Override
   public int getDefaultDisplayTileOffset() {
      return 1;
   }

   @Override
   public int getSizeInventory() {
      return 5;
   }

   @Override
   public boolean interactFirst(EntityPlayer entityPlayer) {
      if (!this.worldObj.isRemote) {
         entityPlayer.displayGUIChest(this);
      }

      return true;
   }

   @Override
   public void onActivatorRailPass(int integer1, int integer2, int integer3, boolean boolean4) {
      boolean var8 = !boolean4;
      if (var8 != this.getBlocked()) {
         this.setBlocked(var8);
      }
   }

   public boolean getBlocked() {
      return this.isBlocked;
   }

   public void setBlocked(boolean boolean1) {
      this.isBlocked = boolean1;
   }

   @Override
   public World getWorld() {
      return this.worldObj;
   }

   @Override
   public double getXPos() {
      return this.posX;
   }

   @Override
   public double getYPos() {
      return this.posY + 0.5;
   }

   @Override
   public double getZPos() {
      return this.posZ;
   }

   @Override
   public void onUpdate() {
      super.onUpdate();
      if (!this.worldObj.isRemote && this.isEntityAlive() && this.getBlocked()) {
         BlockPos var4 = new BlockPos(this);
         if (var4.equals(this.field_174900_c)) {
            this.transferTicker--;
         } else {
            this.setTransferTicker(0);
         }

         if (!this.canTransfer()) {
            this.setTransferTicker(0);
            if (this.func_96112_aD()) {
               this.setTransferTicker(4);
               this.markDirty();
            }
         }
      }
   }

   public boolean func_96112_aD() {
      if (TileEntityHopper.captureDroppedItems(this)) {
         return true;
      }

      List var4 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.getEntityBoundingBox().expand(0.25, 0.0, 0.25), Tezzelator.a);
      if (var4.size() > 0) {
         TileEntityHopper.putDropInInventoryAllSlots(this, (EntityItem)var4.get(0));
      }

      return false;
   }

   @Override
   public void killMinecart(DamageSource damageSource) {
      super.killMinecart(damageSource);
      if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         this.dropItemWithOffset(Item.getItemFromBlock(Blocks.hopper), 1, 0.0F);
      }
   }

   @Override
   protected void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setInteger("TransferCooldown", this.transferTicker);
   }

   @Override
   protected void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.transferTicker = nBTTagCompound.getInteger("TransferCooldown");
   }

   public void setTransferTicker(int integer) {
      this.transferTicker = integer;
   }

   public boolean canTransfer() {
      return this.transferTicker > 0;
   }

   @Override
   public String getGuiID() {
      return "minecraft:hopper";
   }

   @Override
   public Container createContainer(InventoryPlayer inventoryPlayer, EntityPlayer entityPlayer) {
      return new ContainerHopper(inventoryPlayer, this, entityPlayer);
   }
}
