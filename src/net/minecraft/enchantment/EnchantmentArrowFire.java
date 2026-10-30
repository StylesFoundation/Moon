package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowFire extends Enchantment {
   public EnchantmentArrowFire(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.BOW);
      this.setName("arrowFire");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 20;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return 50;
   }

   @Override
   public int getMaxLevel() {
      return 1;
   }
}
