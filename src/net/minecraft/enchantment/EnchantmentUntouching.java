package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentUntouching extends Enchantment {
   protected EnchantmentUntouching(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.DIGGER);
      this.setName("untouching");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 15;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return super.getMinEnchantability(integer) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 1;
   }

   @Override
   public boolean canApplyTogether(Enchantment enchantment) {
      return super.canApplyTogether(enchantment) && enchantment.effectId != fortune.effectId;
   }

   @Override
   public boolean canApply(ItemStack itemStack) {
      return itemStack.getItem() == Items.shears || super.canApply(itemStack);
   }
}
