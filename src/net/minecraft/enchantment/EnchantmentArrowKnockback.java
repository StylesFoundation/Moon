package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowKnockback extends Enchantment {
   public EnchantmentArrowKnockback(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.BOW);
      this.setName("arrowKnockback");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 12 + (integer - 1) * 20;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return this.getMinEnchantability(integer) + 25;
   }

   @Override
   public int getMaxLevel() {
      return 2;
   }
}
