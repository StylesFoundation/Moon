package com.moon.scripting.converter;

import com.moon.scripting.api.types.Enchantment;
import com.moon.scripting.api.types.Item;
import java.util.ArrayList;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class ItemConverter extends LuaConverter<Item> {
   public LuaValue convert(Item item) {
      if (item == null) {
         return LuaValue.NIL;
      }

      LuaTable table = new LuaTable();
      table.set("name", item.name);
      table.set("display_name", item.display_name);
      table.set("max_stack_size", item.max_stack_size);
      table.set("stack_size", item.stack_size);
      table.set("max_damage", item.max_damage);
      table.set("damage", item.damage);
      ArrayList<LuaValue> enchantmentList = new ArrayList<>();
      if (item.enchantments != null) {
         Enchantment[] enchantments = item.enchantments;
         int length = enchantments.length;

         for (int i = 0; i < length; i++) {
            LuaValue enchantmentValue = LuaConverterRegistry.toLuaValue(enchantments[i]);
            enchantmentList.add(enchantmentValue);
         }
      }

      table.set("enchantments", LuaTable.listOf(enchantmentList.toArray(new LuaValue[0])));
      return table;
   }
}
