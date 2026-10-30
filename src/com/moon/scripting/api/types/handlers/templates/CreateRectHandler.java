package com.moon.scripting.api.types.handlers.templates;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.types.templates.RectTemplate;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class CreateRectHandler extends VarArgFunction {
   public final ScriptLoader scriptLoader;

   public CreateRectHandler(ScriptLoader scriptLoader) {
      this.scriptLoader = scriptLoader;
   }

   @Override
   public Varargs invoke(Varargs args) {
      LuaValue xVal = args.arg(1);
      LuaValue yVal = args.arg(2);
      LuaValue widthVal = args.arg(3);
      LuaValue heightVal = args.arg(4);
      LuaValue colorVal = args.arg(5);
      if (!xVal.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + xVal.typename());
      } else if (!yVal.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + yVal.typename());
      } else if (!widthVal.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + widthVal.typename());
      } else if (!heightVal.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + heightVal.typename());
      } else {
         return !colorVal.isnumber()
            ? LuaValue.error("Expected type number at 4, got " + colorVal.typename())
            : CoerceJavaToLua.coerce(new RectTemplate(xVal.tofloat(), yVal.tofloat(), widthVal.tofloat(), heightVal.tofloat(), colorVal.toint()));
      }
   }
}
