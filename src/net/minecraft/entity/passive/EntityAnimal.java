package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public abstract class EntityAnimal extends EntityAgeable implements IAnimals {
   protected Block spawnableBlock = Blocks.grass;
   private int inLove;
   private EntityPlayer playerInLove;

   public EntityAnimal(World world) {
      super(world);
   }

   @Override
   protected void updateAITasks() {
      if (this.getGrowingAge() != 0) {
         this.inLove = 0;
      }

      super.updateAITasks();
   }

   @Override
   public void onLivingUpdate() {
      super.onLivingUpdate();
      if (this.getGrowingAge() != 0) {
         this.inLove = 0;
      }

      if (this.inLove > 0) {
         this.inLove--;
         if (this.inLove % 10 == 0) {
            double var4 = this.rand.nextGaussian() * 0.02;
            double var6 = this.rand.nextGaussian() * 0.02;
            double var8 = this.rand.nextGaussian() * 0.02;
            this.worldObj
               .spawnParticle(
                  EnumParticleTypes.HEART,
                  this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                  this.posY + 0.5 + this.rand.nextFloat() * this.height,
                  this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                  var4,
                  var6,
                  var8
               );
         }
      }
   }

   @Override
   public boolean attackEntityFrom(DamageSource damageSource, float float2) {
      if (this.isEntityInvulnerable(damageSource)) {
         return false;
      }

      this.inLove = 0;
      return super.attackEntityFrom(damageSource, float2);
   }

   @Override
   public float getBlockPathWeight(BlockPos blockPos) {
      return this.worldObj.getBlockState(blockPos.down()).getBlock() == Blocks.grass ? 10.0F : this.worldObj.getLightBrightness(blockPos) - 0.5F;
   }

   @Override
   public void writeEntityToNBT(NBTTagCompound nBTTagCompound) {
      super.writeEntityToNBT(nBTTagCompound);
      nBTTagCompound.setInteger("InLove", this.inLove);
   }

   @Override
   public void readEntityFromNBT(NBTTagCompound nBTTagCompound) {
      super.readEntityFromNBT(nBTTagCompound);
      this.inLove = nBTTagCompound.getInteger("InLove");
   }

   @Override
   public boolean getCanSpawnHere() {
      int var4 = MathHelper.floor_double(this.posX);
      int var5 = MathHelper.floor_double(this.getEntityBoundingBox().minY);
      int var6 = MathHelper.floor_double(this.posZ);
      BlockPos var7 = new BlockPos(var4, var5, var6);
      return this.worldObj.getBlockState(var7.down()).getBlock() == this.spawnableBlock && this.worldObj.getLight(var7) > 8 && super.getCanSpawnHere();
   }

   @Override
   public int getTalkInterval() {
      return 120;
   }

   @Override
   protected boolean canDespawn() {
      return false;
   }

   @Override
   protected int getExperiencePoints(EntityPlayer entityPlayer) {
      return 1 + this.worldObj.rand.nextInt(3);
   }

   public boolean isBreedingItem(ItemStack itemStack) {
      return itemStack != null && itemStack.getItem() == Items.wheat;
   }

   @Override
   public boolean interact(EntityPlayer entityPlayer) {
      ItemStack var5 = entityPlayer.inventory.getCurrentItem();
      if (var5 != null) {
         if (this.isBreedingItem(var5) && this.getGrowingAge() == 0 && this.inLove <= 0) {
            this.consumeItemFromStack(entityPlayer, var5);
            this.setInLove(entityPlayer);
            return true;
         }

         if (this.isChild() && this.isBreedingItem(var5)) {
            this.consumeItemFromStack(entityPlayer, var5);
            this.func_175501_a((int)(-this.getGrowingAge() / 20 * 0.1F), true);
            return true;
         }
      }

      return super.interact(entityPlayer);
   }

   protected void consumeItemFromStack(EntityPlayer entityPlayer, ItemStack itemStack) {
      if (!entityPlayer.capabilities.isCreativeMode) {
         itemStack.stackSize--;
         if (itemStack.stackSize <= 0) {
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
         }
      }
   }

   public void setInLove(EntityPlayer entityPlayer) {
      this.inLove = 600;
      this.playerInLove = entityPlayer;
      this.worldObj.setEntityState(this, (byte)18);
   }

   public EntityPlayer getPlayerInLove() {
      return this.playerInLove;
   }

   public boolean isInLove() {
      return this.inLove > 0;
   }

   public void resetInLove() {
      this.inLove = 0;
   }

   public boolean canMateWith(EntityAnimal entityAnimal) {
      return entityAnimal != this && entityAnimal.getClass() == this.getClass() && this.isInLove() && entityAnimal.isInLove();
   }

   @Override
   public void handleStatusUpdate(byte byte1) {
      if (byte1 == 18) {
         for (int var5 = 0; var5 < 7; var5++) {
            double var6 = this.rand.nextGaussian() * 0.02;
            double var8 = this.rand.nextGaussian() * 0.02;
            double var10 = this.rand.nextGaussian() * 0.02;
            this.worldObj
               .spawnParticle(
                  EnumParticleTypes.HEART,
                  this.posX + this.rand.nextFloat() * this.width * 2.0F - this.width,
                  this.posY + 0.5 + this.rand.nextFloat() * this.height,
                  this.posZ + this.rand.nextFloat() * this.width * 2.0F - this.width,
                  var6,
                  var8,
                  var10
               );
         }
      } else {
         super.handleStatusUpdate(byte1);
      }
   }
}
