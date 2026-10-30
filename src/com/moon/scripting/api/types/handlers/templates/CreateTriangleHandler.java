package com.moon.scripting.api.types.handlers.templates;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.types.templates.TriangleTemplate;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class CreateTriangleHandler extends VarArgFunction {
   public final ScriptLoader scriptLoader;

   public CreateTriangleHandler(ScriptLoader scriptLoader) {
      this.scriptLoader = scriptLoader;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue topX = varargs.arg(1);
      LuaValue topY = varargs.arg(2);
      LuaValue leftX = varargs.arg(3);
      LuaValue leftY = varargs.arg(4);
      LuaValue rightX = varargs.arg(5);
      LuaValue rightY = varargs.arg(6);
      LuaValue color = varargs.arg(7);
      if (!topX.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + topX.typename());
      } else if (!topY.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + topY.typename());
      } else if (!leftX.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + leftX.typename());
      } else if (!leftY.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + leftY.typename());
      } else if (!rightX.isnumber()) {
         return LuaValue.error("Expected type number at 4, got " + rightX.typename());
      } else if (!rightY.isnumber()) {
         return LuaValue.error("Expected type number at 5, got " + rightY.typename());
      } else {
         return !color.isnumber()
            ? LuaValue.error("Expected type number at 6, got " + color.typename())
            : CoerceJavaToLua.coerce(
               new TriangleTemplate(topX.tofloat(), topY.tofloat(), leftX.tofloat(), leftY.tofloat(), rightX.tofloat(), rightY.tofloat(), color.toint())
            );
      }
   }
}
