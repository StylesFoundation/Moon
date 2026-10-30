package com.moon.scripting.api.types.handlers.templates;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.types.templates.RoundedRectTemplate;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class CreateRoundedRectHandler extends VarArgFunction {
   public final ScriptLoader scriptLoader;

   public CreateRoundedRectHandler(ScriptLoader scriptLoader) {
      this.scriptLoader = scriptLoader;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue x = varargs.arg(1);
      LuaValue y = varargs.arg(2);
      LuaValue width = varargs.arg(3);
      LuaValue height = varargs.arg(4);
      LuaValue radius = varargs.arg(5);
      LuaValue color = varargs.arg(6);
      if (!x.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + x.typename());
      } else if (!y.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + y.typename());
      } else if (!width.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + width.typename());
      } else if (!height.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + height.typename());
      } else if (!radius.isnumber()) {
         return LuaValue.error("Expected type number at 4, got " + radius.typename());
      } else {
         return !color.isnumber()
            ? LuaValue.error("Expected type number at 5, got " + color.typename())
            : CoerceJavaToLua.coerce(new RoundedRectTemplate(x.tofloat(), y.tofloat(), width.tofloat(), height.tofloat(), radius.tofloat(), color.toint()));
      }
   }
}
