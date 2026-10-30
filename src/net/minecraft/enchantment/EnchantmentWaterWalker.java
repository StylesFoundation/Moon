package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWalker extends Enchantment {
   public EnchantmentWaterWalker(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.ARMOR_FEET);
      this.setName("waterWalker");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return integer * 10;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return this.getMinEnchantability(integer) + 15;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }
}
