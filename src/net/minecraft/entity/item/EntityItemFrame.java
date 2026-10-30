package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class EntityItemFrame extends EntityHanging {
   private float itemDropChance = 1.0F;

   public EntityItemFrame(World world) {
      super(world);
   }

   public EntityItemFrame(World world, BlockPos blockPos, EnumFacing enumFacing) {
      super(world, blockPos);
      this.updateFacingWithBoundingBox(enumFacing);
   }

   @Override
   protected void entityInit() {
      this.getDataWatcher().addObjectByDataType(8, 5);
      this.getDataWatcher().addObject(9, (byte)0);
   }

   @Override
   public float getCollisionBorderSize() {
      return 0.0F;
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      if (!damageSource.isExplosion() && this.getDisplayedItem() != null) {
         if (!this.worldObj.isRemote) {
            this.dropItemOrSelf(damageSource.getEntity(), false);
            this.setDisplayedItem(null);
         }

         return true;
      } else {
         return super.attackEntityFrom(damageSource, float2);
      }
   }

   @Override
   public int getWidthPixels() {
      return 12;
   }

   @Override
   public int getHeightPixels() {
      return 12;
   }

   @Override
   public boolean isInRangeToRenderDist(double double1) {
      double var6 = 1024.0 * this.renderDistanceWeight;
      return double1 < var6 * var6;
   }

   @Override
   public void onBroken(Entity entity) {
      this.dropItemOrSelf(entity, true);
   }

   public void dropItemOrSelf(Entity entity, boolean boolean2) {
      if (this.worldObj.getGameRules().getBoolean("doEntityDrops")) {
         ItemStack var6 = this.getDisplayedItem();
         if (entity instanceof EntityPlayer var7 && var7.capabilities.isCreativeMode) {
            this.Method242(var6);
            return;
         }

         if (boolean2) {
            this.entityDropItem(new ItemStack(Items.item_frame), 0.0F);
         }

         if (var6 != null && this.rand.nextFloat() < this.itemDropChance) {
            var6 = var6.copy();
            this.Method242(var6);
            this.entityDropItem(var6, 0.0F);
         }
      }
   }

   private void Method242(ItemStack itemStack) {
      if (itemStack != null) {
         if (itemStack.getItem() == Items.filled_map) {
            ((ItemMap)itemStack.getItem()).getMapData(itemStack, this.worldObj).mapDecorations.remove("frame-" + this.getEntityId());
         }

         itemStack.setItemFrame(null);
      }
   }

   public ItemStack getDisplayedItem() {
      return this.getDataWatcher().getWatchableObjectItemStack(8);
   }

   public void setDisplayedItem(ItemStack itemStack) {
      this.setDisplayedItemWithUpdate(itemStack, true);
   }

   private void setDisplayedItemWithUpdate(ItemStack itemStack, boolean boolean2) {
      if (itemStack != null) {
         itemStack = itemStack.copy();
         itemStack.stackSize = 1;
         itemStack.setItemFrame(this);
      }

      this.getDataWatcher().updateObject(8, itemStack);
      this.getDataWatcher().setObjectWatched(8);
      if (boolean2 && this.hangingPosition != null) {
         this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
      }
   }

   public int getRotation() {
      return this.getDataWatcher().getWatchableObjectByte(9);
   }

   public void setItemRotation(int integer) {
      this.func_174865_a(integer, true);
   }

   private void func_174865_a(int integer, boolean boolean2) {
      this.getDataWatcher().updateObject(9, (byte)(integer % 8));
      if (boolean2 && this.hangingPosition != null) {
         this.worldObj.updateComparatorOutputLevel(this.hangingPosition, Blocks.air);
      }
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      if (this.getDisplayedItem() != null) {
         nBTTagCompound.setTag("Item", this.getDisplayedItem().writeToNBT(new NBTTagCompound()));
         nBTTagCompound.setByte("ItemRotation", (byte)this.getRotation());
         nBTTagCompound.setFloat("ItemDropChance", this.itemDropChance);
      }

      super.writeEntityToNBT(nBTTagCompound);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      NBTTagCompound var5 = nBTTagCompound.getCompoundTag("Item");
      if (var5 != null && !var5.hasNoTags()) {
         this.setDisplayedItemWithUpdate(ItemStack.loadItemStackFromNBT(var5), false);
         this.func_174865_a(nBTTagCompound.getByte("ItemRotation"), false);
         if (nBTTagCompound.hasKey("ItemDropChance", 99)) {
            this.itemDropChance = nBTTagCompound.getFloat("ItemDropChance");
         }

         if (nBTTagCompound.hasKey("Direction")) {
            this.func_174865_a(this.getRotation() * 2, false);
         }
      }

      super.readEntityFromNBT(nBTTagCompound);
   }

   @Override
   public boolean interactFirst(EntityPlayer entityPlayer) {
      if (this.getDisplayedItem() == null) {
         ItemStack var5 = entityPlayer.getHeldItem();
         if (var5 != null && !this.worldObj.isRemote) {
            this.setDisplayedItem(var5);
            if (!entityPlayer.capabilities.isCreativeMode && --var5.stackSize <= 0) {
               entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
         }
      } else if (!this.worldObj.isRemote) {
         this.setItemRotation(this.getRotation() + 1);
      }

      return true;
   }

   public int func_174866_q() {
      return this.getDisplayedItem() == null ? 0 : this.getRotation() % 8 + 1;
   }
}
