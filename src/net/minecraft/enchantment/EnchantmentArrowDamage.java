package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowDamage extends Enchantment {
   public EnchantmentArrowDamage(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.BOW);
      this.setName("arrowDamage");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 1 + (integer - 1) * 10;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return this.getMinEnchantability(integer) + 15;
   }

   @Override
   public int getMaxLevel() {
      return 5;
   }
}
