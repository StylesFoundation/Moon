package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentKnockback extends Enchantment {
   protected EnchantmentKnockback(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.WEAPON);
      this.setName("knockback");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 5 + 20 * (integer - 1);
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return super.getMinEnchantability(integer) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 2;
   }
}
