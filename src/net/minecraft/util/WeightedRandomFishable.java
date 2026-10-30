package net.minecraft.util;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class WeightedRandomFishable extends WeightedRandom.Item {
   private final ItemStack returnStack;
   private float maxDamagePercent;
   private boolean enchantable;

   public WeightedRandomFishable(ItemStack itemStack, int integer) {
      super(integer);
      this.returnStack = itemStack;
   }

   public ItemStack getItemStack(Random random) {
      ItemStack var5 = this.returnStack.copy();
      if (this.maxDamagePercent > 0.0F) {
         int var6 = (int)(this.maxDamagePercent * this.returnStack.getMaxDamage());
         int var7 = var5.getMaxDamage() - random.nextInt(random.nextInt(var6) + 1);
         if (var7 > var6) {
            var7 = var6;
         }

         if (var7 < 1) {
            var7 = 1;
         }

         var5.setItemDamage(var7);
      }

      if (this.enchantable) {
         EnchantmentHelper.addRandomEnchantment(random, var5, 30);
      }

      return var5;
   }

   public WeightedRandomFishable setMaxDamagePercent(float float1) {
      this.maxDamagePercent = float1;
      return this;
   }

   public WeightedRandomFishable setEnchantable() {
      this.enchantable = true;
      return this;
   }
}
