package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentFireAspect extends Enchantment {
   protected EnchantmentFireAspect(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.WEAPON);
      this.setName("fire");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 10 + 20 * (integer - 1);
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
