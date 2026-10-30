package com.moon.scripting.converter;

import com.moon.scripting.api.types.Potion;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class PotionConverter extends LuaConverter<Potion> {
   public LuaValue convert(Potion potion) {
      if (potion == null) {
         return LuaValue.NIL;
      }

      LuaTable table = new LuaTable();
      table.set("id", LuaValue.valueOf(potion.id));
      table.set("name", LuaValue.valueOf(potion.name));
      table.set("duration", LuaValue.valueOf(potion.duration));
      table.set("amplifier", LuaValue.valueOf(potion.amplifier));
      table.set("bad_effect", LuaValue.valueOf(potion.bad_effect));
      table.set("color", LuaValue.valueOf(potion.color));
      return table;
   }
}
