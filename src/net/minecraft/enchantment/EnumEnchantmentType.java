package net.minecraft.enchantment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public enum EnumEnchantmentType {
   ALL,
   ARMOR,
   ARMOR_FEET,
   ARMOR_LEGS,
   ARMOR_TORSO,
   ARMOR_HEAD,
   WEAPON,
   DIGGER,
   FISHING_ROD,
   BREAKABLE,
   BOW;

   public boolean canEnchantItem(Item item) {
      if (this == ALL) {
         return true;
      }

      if (this == BREAKABLE && item.isDamageable()) {
         return true;
      }

      if (item instanceof ItemArmor) {
         if (this == ARMOR) {
            return true;
         }

         ItemArmor var5 = (ItemArmor)item;
         return var5.armorType == 0
            ? this == ARMOR_HEAD
            : (var5.armorType == 2 ? this == ARMOR_LEGS : (var5.armorType == 1 ? this == ARMOR_TORSO : var5.armorType == 3 && this == ARMOR_FEET));
      } else {
         return item instanceof ItemSword
            ? this == WEAPON
            : (item instanceof ItemTool ? this == DIGGER : (item instanceof ItemBow ? this == BOW : item instanceof ItemFishingRod && this == FISHING_ROD));
      }
   }
}
