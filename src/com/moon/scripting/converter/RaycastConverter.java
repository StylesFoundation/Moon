package com.moon.scripting.converter;

import com.moon.scripting.api.types.RayCastResult;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class RaycastConverter extends LuaConverter<RayCastResult> {
   public LuaValue convert(RayCastResult rayCastResult) {
      if (rayCastResult == null) {
         return LuaValue.NIL;
      }

      LuaTable table = new LuaTable();
      table.set("hit_type", CoerceJavaToLua.coerce(rayCastResult.hit_type));
      table.set("side_hit", CoerceJavaToLua.coerce(rayCastResult.side_hit));
      table.set("pos", CoerceJavaToLua.coerce(rayCastResult.pos));
      table.set("hit_vec", CoerceJavaToLua.coerce(rayCastResult.hit_vec));
      table.set("hit_entity_id", rayCastResult.hit_entity_id);
      return table;
   }
}
