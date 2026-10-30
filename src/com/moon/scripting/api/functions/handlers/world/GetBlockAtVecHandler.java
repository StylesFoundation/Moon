package com.moon.scripting.api.functions.handlers.world;

import com.moon.scripting.api.functions.WorldFunctionContainer;
import com.moon.scripting.api.types.Vec3d;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetBlockAtVecHandler extends OneArgFunction {
   final WorldFunctionContainer container;

   public GetBlockAtVecHandler(WorldFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue vecValue) {
      if (!vecValue.isuserdata(Vec3d.class)) {
         return LuaValue.error("Expected type number at 0, got " + vecValue.typename());
      }

      Vec3d vec = (Vec3d)vecValue.touserdata(Vec3d.class);
      return LuaConverterRegistry.toLuaValue(this.container.implementation.get_block_at_vec(vec));
   }
}
