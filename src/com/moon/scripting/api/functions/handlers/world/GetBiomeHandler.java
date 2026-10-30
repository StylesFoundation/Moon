package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetBiomeHandler extends ZeroArgFunction {
   final WorldFunctionContainer container;

   public GetBiomeHandler(WorldFunctionContainer jW) {
      this.container = jW;
   }

   @Override
   public LuaValue call() {
      return LuaValue.valueOf(this.container.implementation.get_biome());
   }
}
