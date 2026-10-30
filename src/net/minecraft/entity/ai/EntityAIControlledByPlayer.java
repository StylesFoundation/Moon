package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class EntityAIControlledByPlayer extends EntityAIBase {
   private final EntityLiving thisEntity;
   private final float maxSpeed;
   private float currentSpeed;
   private boolean speedBoosted;
   private int speedBoostTime;
   private int maxSpeedBoostTime;

   public EntityAIControlledByPlayer(EntityLiving entityLiving, float float2) {
      this.thisEntity = entityLiving;
      this.maxSpeed = float2;
      this.setMutexBits(7);
   }

   @Override
   public void startExecuting() {
      this.currentSpeed = 0.0F;
   }

   @Override
   public void resetTask() {
      this.speedBoosted = false;
      this.currentSpeed = 0.0F;
   }

   @Override
   public boolean shouldExecute() {
      return this.thisEntity.isEntityAlive()
         && this.thisEntity.riddenByEntity != null
         && this.thisEntity.riddenByEntity instanceof EntityPlayer
         && (this.speedBoosted || this.thisEntity.canBeSteered());
   }

   @Override
   public void updateTask() {
      EntityPlayer var4 = (EntityPlayer)this.thisEntity.riddenByEntity;
      EntityCreature var5 = (EntityCreature)this.thisEntity;
      float var6 = MathHelper.wrapAngleTo180_float(var4.rotationYaw - this.thisEntity.rotationYaw) * 0.5F;
      if (var6 > 5.0F) {
         var6 = 5.0F;
      }

      if (var6 < -5.0F) {
         var6 = -5.0F;
      }

      this.thisEntity.rotationYaw = MathHelper.wrapAngleTo180_float(this.thisEntity.rotationYaw + var6);
      if (this.currentSpeed < this.maxSpeed) {
         this.currentSpeed = this.currentSpeed + (this.maxSpeed - this.currentSpeed) * 0.01F;
      }

      if (this.currentSpeed > this.maxSpeed) {
         this.currentSpeed = this.maxSpeed;
      }

      int var7 = MathHelper.floor_double(this.thisEntity.posX);
      int var8 = MathHelper.floor_double(this.thisEntity.posY);
      int var9 = MathHelper.floor_double(this.thisEntity.posZ);
      float var10 = this.currentSpeed;
      if (this.speedBoosted) {
         if (this.speedBoostTime++ > this.maxSpeedBoostTime) {
            this.speedBoosted = false;
         }

         var10 += var10 * 1.15F * MathHelper.sin((float)this.speedBoostTime / this.maxSpeedBoostTime * (float) Math.PI);
      }

      float var11 = 0.91F;
      if (this.thisEntity.onGround) {
         var11 = this.thisEntity
               .worldObj
               .getBlockState(new BlockPos(MathHelper.floor_float(var7), MathHelper.floor_float(var8) - 1, MathHelper.floor_float(var9)))
               .getBlock()
               .slipperiness
            * 0.91F;
      }

      float var12 = 0.16277136F / (var11 * var11 * var11);
      float var13 = MathHelper.sin(var5.rotationYaw * (float) Math.PI / 180.0F);
      float var14 = MathHelper.cos(var5.rotationYaw * (float) Math.PI / 180.0F);
      float var15 = var5.getAIMoveSpeed() * var12;
      float var16 = Math.max(var10, 1.0F);
      var16 = var15 / var16;
      float var17 = var10 * var16;
      float var18 = -(var17 * var13);
      float var19 = var17 * var14;
      if (MathHelper.abs(var18) > MathHelper.abs(var19)) {
         if (var18 < 0.0F) {
            var18 -= this.thisEntity.width / 2.0F;
         }

         if (var18 > 0.0F) {
            var18 += this.thisEntity.width / 2.0F;
         }

         var19 = 0.0F;
      } else {
         var18 = 0.0F;
         if (var19 < 0.0F) {
            var19 -= this.thisEntity.width / 2.0F;
         }

         if (var19 > 0.0F) {
            var19 += this.thisEntity.width / 2.0F;
         }
      }

      int var20 = MathHelper.floor_double(this.thisEntity.posX + var18);
      int var21 = MathHelper.floor_double(this.thisEntity.posZ + var19);
      int var22 = MathHelper.floor_float(this.thisEntity.width + 1.0F);
      int var23 = MathHelper.floor_float(this.thisEntity.height + var4.height + 1.0F);
      int var24 = MathHelper.floor_float(this.thisEntity.width + 1.0F);
      if (var7 != var20 || var9 != var21) {
         Block var25 = this.thisEntity.worldObj.getBlockState(new BlockPos(var7, var8, var9)).getBlock();
         boolean var26 = !this.isStairOrSlab(var25)
            && (
               var25.getMaterial() != Material.air
                  || !this.isStairOrSlab(this.thisEntity.worldObj.getBlockState(new BlockPos(var7, var8 - 1, var9)).getBlock())
            );
         if (var26
            && 0 == WalkNodeProcessor.func_176170_a(this.thisEntity.worldObj, this.thisEntity, var20, var8, var21, var22, var23, var24, false, false, true)
            && 1 == WalkNodeProcessor.func_176170_a(this.thisEntity.worldObj, this.thisEntity, var7, var8 + 1, var9, var22, var23, var24, false, false, true)
            && 1 == WalkNodeProcessor.func_176170_a(this.thisEntity.worldObj, this.thisEntity, var20, var8 + 1, var21, var22, var23, var24, false, false, true)
            )
          {
            var5.getJumpHelper().setJumping();
         }
      }

      if (!var4.capabilities.isCreativeMode && this.currentSpeed >= this.maxSpeed * 0.5F && this.thisEntity.getRNG().nextFloat() < 0.006F && !this.speedBoosted
         )
       {
         ItemStack var28 = var4.getHeldItem();
         if (var28 != null && var28.getItem() == Items.carrot_on_a_stick) {
            var28.damageItem(1, var4);
            if (var28.stackSize == 0) {
               ItemStack var29 = new ItemStack(Items.fishing_rod);
               var29.setTagCompound(var28.getTagCompound());
               var4.inventory.mainInventory[var4.inventory.currentItem] = var29;
            }
         }
      }

      this.thisEntity.moveEntityWithHeading(0.0F, var10);
   }

   private boolean isStairOrSlab(Block block) {
      return block instanceof BlockStairs || block instanceof BlockSlab;
   }

   public boolean isSpeedBoosted() {
      return this.speedBoosted;
   }

   public void boostSpeed() {
      this.speedBoosted = true;
      this.speedBoostTime = 0;
      this.maxSpeedBoostTime = this.thisEntity.getRNG().nextInt(841) + 140;
   }

   public boolean isControlledByPlayer() {
      return !this.isSpeedBoosted() && this.currentSpeed > this.maxSpeed * 0.3F;
   }
}
