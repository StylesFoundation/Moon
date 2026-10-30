package com.moon.scripting.converter;

import com.moon.scripting.api.types.Enchantment;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class EnchantmentConverter extends LuaConverter<Enchantment> {
   public LuaValue convert(Enchantment enchantment) {
      LuaTable table = new LuaTable();
      table.set("id", enchantment.id);
      table.set("level", enchantment.level);
      return table;
   }
}
