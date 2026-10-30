package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDurability extends Enchantment {
   protected EnchantmentDurability(int integer1, ResourceLocation resourceLocation, int integer3) {
      super(integer1, resourceLocation, integer3, EnumEnchantmentType.BREAKABLE);
      this.setName("durability");
   }

   @Override
   public int getMinEnchantability(int integer) {
      return 5 + (integer - 1) * 8;
   }

   @Override
   public int getMaxEnchantability(int integer) {
      return super.getMinEnchantability(integer) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }

   @Override
   public boolean canApply(ItemStack itemStack) {
      return itemStack.isItemStackDamageable() || super.canApply(itemStack);
   }

   public static boolean negateDamage(ItemStack itemStack, int integer, Random random) {
      return (!(itemStack.getItem() instanceof ItemArmor) || !(random.nextFloat() < 0.6F)) && random.nextInt(integer + 1) > 0;
   }
}
