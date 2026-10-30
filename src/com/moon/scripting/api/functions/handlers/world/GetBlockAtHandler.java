package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;

public class GetBlockAtHandler extends ThreeArgFunction {
   final WorldFunctionContainer container;

   public GetBlockAtHandler(WorldFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue xValue, LuaValue yValue, LuaValue zValue) {
      if (!xValue.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + xValue.typename());
      }

      if (!yValue.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + yValue.typename());
      }

      if (!zValue.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + zValue.typename());
      }

      double x = xValue.todouble();
      double y = yValue.todouble();
      double z = zValue.todouble();
      return LuaConverterRegistry.toLuaValue(this.container.implementation.get_block_at(x, y, z));
   }
}
