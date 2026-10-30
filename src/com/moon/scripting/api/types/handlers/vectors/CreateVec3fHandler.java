package com.moon.scripting.api.types.handlers.vectors;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.types.Vec3f;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class CreateVec3fHandler extends VarArgFunction {
   public final ScriptLoader scriptLoader;

   public CreateVec3fHandler(ScriptLoader scriptLoader) {
      this.scriptLoader = scriptLoader;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue x = varargs.arg(1);
      LuaValue y = varargs.arg(2);
      LuaValue z = varargs.arg(3);
      return CoerceJavaToLua.coerce(new Vec3f(x.tofloat(), y.tofloat(), z.tofloat()));
   }
}
