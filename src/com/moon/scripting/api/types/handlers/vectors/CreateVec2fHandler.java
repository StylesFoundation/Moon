package com.moon.scripting.api.types.handlers.vectors;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.types.Vec2f;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class CreateVec2fHandler extends VarArgFunction {
   public final ScriptLoader scriptLoader;

   public CreateVec2fHandler(ScriptLoader scriptLoader) {
      this.scriptLoader = scriptLoader;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue x = varargs.arg(1);
      LuaValue y = varargs.arg(2);
      return CoerceJavaToLua.coerce(new Vec2f(x.tofloat(), y.tofloat()));
   }
}
