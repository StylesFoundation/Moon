package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWorker extends Enchantment {
   public EnchantmentWaterWorker(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.ARMOR_HEAD);
      this.setName("waterWorker");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 1;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return this.getMinEnchantability(integer) + 40;
   }

   @Override
   public int getMaxLevel() {
      return 1;
   }
}
