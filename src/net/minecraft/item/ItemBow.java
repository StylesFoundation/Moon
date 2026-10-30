package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBow extends Item {
   public static final String[] bowPullIconNameArray = new String[]{"pulling_0", "pulling_1", "pulling_2"};

   public ItemBow() {
      this.maxStackSize = 1;
      this.setMaxDamage(384);
      this.setCreativeTab(CreativeTabs.tabCombat);
   }

   @Override
   public void onPlayerStoppedUsing(ItemStack itemStack, World world, EntityPlayer entityPlayer, int integer) {
      boolean var8 = entityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) > 0;
      if (var8 || entityPlayer.inventory.hasItem(Items.arrow)) {
         float var10 = (this.getMaxItemUseDuration(itemStack) - integer) / 20.0F;
         var10 = (var10 * var10 + var10 * 2.0F) / 3.0F;
         if (var10 < 0.1) {
            return;
         }

         if (var10 > 1.0F) {
            var10 = 1.0F;
         }

         EntityArrow var11 = new EntityArrow(world, entityPlayer, var10 * 2.0F);
         if (var10 == 1.0F) {
            var11.setIsCritical(true);
         }

         int var12 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
         if (var12 > 0) {
            var11.setDamage(var11.getDamage() + var12 * 0.5 + 0.5);
         }

         int var13 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
         if (var13 > 0) {
            var11.setKnockbackStrength(var13);
         }

         if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0) {
            var11.setFire(100);
         }

         itemStack.damageItem(1, entityPlayer);
         world.playSoundAtEntity(entityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var10 * 0.5F);
         if (var8) {
            var11.canBePickedUp = 2;
         } else {
            entityPlayer.inventory.consumeInventoryItem(Items.arrow);
         }

         entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
         if (!world.isRemote) {
            world.spawnEntityInWorld(var11);
         }
      }
   }

   @Override
   public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      return itemStack;
   }

   @Override
   public int getMaxItemUseDuration(ItemStack itemStack) {
      return 72000;
   }

   @Override
   public EnumAction getItemUseAction(ItemStack itemStack) {
      return EnumAction.BOW;
   }

   @Override
   public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
      if (entityPlayer.capabilities.isCreativeMode || entityPlayer.inventory.hasItem(Items.arrow)) {
         entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
      }

      return itemStack;
   }

   public static float Method567(int integer) {
      float var4 = integer / 20.0F;
      var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;
      if (var4 > 1.0F) {
         var4 = 1.0F;
      }

      return var4;
   }

   @Override
   public int getItemEnchantability() {
      return 1;
   }
}
