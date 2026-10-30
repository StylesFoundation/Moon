package com.moon.scripting.api.types.handlers.vectors;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.types.Vec3i;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class EmptyVec3iHandler extends ZeroArgFunction {
   public final ScriptLoader scriptLoader;

   public EmptyVec3iHandler(ScriptLoader jJ) {
      this.scriptLoader = jJ;
   }

   @Override
   public LuaValue call() {
      return CoerceJavaToLua.coerce(new Vec3i());
   }
}
