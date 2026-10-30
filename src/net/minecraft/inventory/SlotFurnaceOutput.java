package net.minecraft.inventory;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.MathHelper;

public class SlotFurnaceOutput extends Slot {
   private final EntityPlayer thePlayer;
   private int field_75228_b;

   public SlotFurnaceOutput(EntityPlayer entityPlayer, IInventory iInventory, int integer3, int integer4, int integer5) {
      super(iInventory, integer3, integer4, integer5);
      this.thePlayer = entityPlayer;
   }

   @Override
   public boolean isItemValid(ItemStack itemStack) {
      return false;
   }

   @Override
   public ItemStack decrStackSize(int integer) {
      if (this.getHasStack()) {
         this.field_75228_b = this.field_75228_b + Math.min(integer, this.getStack().stackSize);
      }

      return super.decrStackSize(integer);
   }

   @Override
   public void onPickupFromSlot(EntityPlayer entityPlayer, ItemStack itemStack) {
      this.onCrafting(itemStack);
      super.onPickupFromSlot(entityPlayer, itemStack);
   }

   @Override
   protected void onCrafting(ItemStack itemStack, int integer) {
      this.field_75228_b += integer;
      this.onCrafting(itemStack);
   }

   @Override
   protected void onCrafting(ItemStack itemStack) {
      itemStack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.field_75228_b);
      if (!this.thePlayer.worldObj.isRemote) {
         int var5 = this.field_75228_b;
         float var6 = FurnaceRecipes.instance().getSmeltingExperience(itemStack);
         if (var6 == 0.0F) {
            var5 = 0;
         } else if (var6 < 1.0F) {
            int var7 = MathHelper.floor_float(var5 * var6);
            if (var7 < MathHelper.ceiling_float_int(var5 * var6) && Math.random() < var5 * var6 - var7) {
               var7++;
            }

            var5 = var7;
         }

         while (var5 > 0) {
            int var8 = EntityXPOrb.getXPSplit(var5);
            var5 -= var8;
            this.thePlayer
               .worldObj
               .spawnEntityInWorld(new EntityXPOrb(this.thePlayer.worldObj, this.thePlayer.posX, this.thePlayer.posY + 0.5, this.thePlayer.posZ + 0.5, var8));
         }
      }

      this.field_75228_b = 0;
      if (itemStack.getItem() == Items.iron_ingot) {
         this.thePlayer.triggerAchievement(AchievementList.acquireIron);
      }

      if (itemStack.getItem() == Items.cooked_fish) {
         this.thePlayer.triggerAchievement(AchievementList.cookFish);
      }
   }
}
