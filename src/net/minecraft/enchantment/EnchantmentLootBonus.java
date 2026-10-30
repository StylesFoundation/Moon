package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentLootBonus extends Enchantment {
   protected EnchantmentLootBonus(int integer1, ResourceLocation resourceLocation, int integer3, EnumEnchantmentType enumEnchantmentType) {
      super(integer1, resourceLocation, integer3, enumEnchantmentType);
      if (enumEnchantmentType == EnumEnchantmentType.DIGGER) {
         this.setName("lootBonusDigger");
      } else if (enumEnchantmentType == EnumEnchantmentType.FISHING_ROD) {
         this.setName("lootBonusFishing");
      } else {
         this.setName("lootBonus");
      }
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

   @Override
   public boolean canApplyTogether(Enchantment enchantment) {
      return super.canApplyTogether(enchantment) && enchantment.effectId != silkTouch.effectId;
   }
}
