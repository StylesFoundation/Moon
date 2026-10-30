package com.moon.scripting.converter;

import com.moon.scripting.api.types.Block;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class BlockConverter extends LuaConverter<Block> {
   public LuaValue convert(Block block) {
      if (block == null) {
         return LuaValue.NIL;
      }

      LuaTable table = new LuaTable();
      table.set("name", LuaValue.valueOf(block.name));
      table.set("display_name", LuaValue.valueOf(block.display_name));
      table.set("full_block", LuaValue.valueOf(block.full_block));
      table.set("translucent", LuaValue.valueOf(block.translucent));
      table.set("block_hardness", LuaValue.valueOf(block.block_hardness));
      table.set("relative_hardness", LuaValue.valueOf(block.relative_hardness));
      table.set("slipperiness", LuaValue.valueOf(block.slipperiness));
      return table;
   }
}
