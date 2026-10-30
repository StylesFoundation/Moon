package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentFishingSpeed extends Enchantment {
   protected EnchantmentFishingSpeed(int integer1, ResourceLocation resourceLocation, int integer3, EnumEnchantmentType enumEnchantmentType) {
      super(integer1, resourceLocation, integer3, enumEnchantmentType);
      this.setName("fishingSpeed");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 15 + (integer - 1) * 9;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return super.getMinEnchantability(integer) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }
}
