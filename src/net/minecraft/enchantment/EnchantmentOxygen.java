package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentOxygen extends Enchantment {
   public EnchantmentOxygen(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.ARMOR_HEAD);
      this.setName("oxygen");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 10 * integer;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return this.getMinEnchantability(integer) + 30;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }
}
