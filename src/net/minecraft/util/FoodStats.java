package net.minecraft.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;

public class FoodStats {
   private int foodLevel = 20;
   private float foodSaturationLevel = 5.0F;
   private float foodExhaustionLevel;
   private int foodTimer;
   private int prevFoodLevel = 20;

   public void addStats(int integer, float float2) {
      this.foodLevel = Math.min(integer + this.foodLevel, 20);
      this.foodSaturationLevel = Math.min(this.foodSaturationLevel + integer * float2 * 2.0F, this.foodLevel);
   }

   public void addStats(ItemFood itemFood, ItemStack itemStack) {
      this.addStats(itemFood.getHealAmount(itemStack), itemFood.getSaturationModifier(itemStack));
   }

   public void onUpdate(EntityPlayer entityPlayer) {
      EnumDifficulty var5 = entityPlayer.worldObj.getDifficulty();
      this.prevFoodLevel = this.foodLevel;
      if (this.foodExhaustionLevel > 4.0F) {
         this.foodExhaustionLevel -= 4.0F;
         if (this.foodSaturationLevel > 0.0F) {
            this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
         } else if (var5 != EnumDifficulty.PEACEFUL) {
            this.foodLevel = Math.max(this.foodLevel - 1, 0);
         }
      }

      if (entityPlayer.worldObj.getGameRules().getBoolean("naturalRegeneration") && this.foodLevel >= 18 && entityPlayer.shouldHeal()) {
         this.foodTimer++;
         if (this.foodTimer >= 80) {
            entityPlayer.heal(1.0F);
            this.addExhaustion(3.0F);
            this.foodTimer = 0;
         }
      } else if (this.foodLevel <= 0) {
         this.foodTimer++;
         if (this.foodTimer >= 80) {
            if (entityPlayer.getHealth() > 10.0F || var5 == EnumDifficulty.HARD || entityPlayer.getHealth() > 1.0F && var5 == EnumDifficulty.NORMAL) {
               entityPlayer.attackEntityFrom(DamageSource.starve, 1.0F);
            }

            this.foodTimer = 0;
         }
      } else {
         this.foodTimer = 0;
      }
   }

   public void readNBT(NBTTagCompound nBTTagCompound) {
      if (nBTTagCompound.hasKey("foodLevel", 99)) {
         this.foodLevel = nBTTagCompound.getInteger("foodLevel");
         this.foodTimer = nBTTagCompound.getInteger("foodTickTimer");
         this.foodSaturationLevel = nBTTagCompound.getFloat("foodSaturationLevel");
         this.foodExhaustionLevel = nBTTagCompound.getFloat("foodExhaustionLevel");
      }
   }

   public void writeNBT(NBTTagCompound nBTTagCompound) {
      nBTTagCompound.setInteger("foodLevel", this.foodLevel);
      nBTTagCompound.setInteger("foodTickTimer", this.foodTimer);
      nBTTagCompound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
      nBTTagCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
   }

   public int getFoodLevel() {
      return this.foodLevel;
   }

   public int getPrevFoodLevel() {
      return this.prevFoodLevel;
   }

   public boolean needFood() {
      return this.foodLevel < 20;
   }

   public void addExhaustion(float float1) {
      this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + float1, 40.0F);
   }

   public float getSaturationLevel() {
      return this.foodSaturationLevel;
   }

   public void setFoodLevel(int integer) {
      this.foodLevel = integer;
   }

   public void setFoodSaturationLevel(float float1) {
      this.foodSaturationLevel = float1;
   }
}
