package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import java.util.Arrays;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class GetPlayersHandler extends ZeroArgFunction {
   final WorldFunctionContainer container;

   public GetPlayersHandler(WorldFunctionContainer jW) {
      this.container = jW;
   }

   @Override
   public LuaValue call() {
      return LuaValue.listOf(Arrays.stream(this.container.implementation.get_players()).mapToObj(LuaValue::valueOf).toArray(LuaValue[]::new));
   }
}
