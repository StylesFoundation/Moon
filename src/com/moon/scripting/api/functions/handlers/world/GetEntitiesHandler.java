package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import java.util.Arrays;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetEntitiesHandler extends ZeroArgFunction {
   final WorldFunctionContainer container;

   public GetEntitiesHandler(WorldFunctionContainer jW) {
      this.container = jW;
   }

   @Override
   public LuaValue call() {
      return LuaValue.listOf(Arrays.stream(this.container.implementation.get_entities()).mapToObj(LuaValue::valueOf).toArray(LuaValue[]::new));
   }
}
