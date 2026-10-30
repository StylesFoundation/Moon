package net.optifine.config;

import net.minecraft.enchantment.Enchantment;

public class ParserEnchantmentId implements IParserInt {
   @Override
   public int parse(String string, int integer) {
      Enchantment var6 = Enchantment.getEnchantmentByLocation(string);
      return var6 == null ? integer : var6.effectId;
   }
}
