package com.moon.scripting.api.types.handlers.templates;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.types.templates.GradientTemplate;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class CreateGradientHandler extends VarArgFunction {
   public final ScriptLoader scriptLoader;

   public CreateGradientHandler(ScriptLoader scriptLoader) {
      this.scriptLoader = scriptLoader;
   }

   @Override
   public Varargs invoke(Varargs args) {
      LuaValue xVal = args.arg(1);
      LuaValue yVal = args.arg(2);
      LuaValue widthVal = args.arg(3);
      LuaValue heightVal = args.arg(4);
      LuaValue fromVal = args.arg(5);
      LuaValue toVal = args.arg(6);
      LuaValue verticalVal = args.arg(7);
      if (!xVal.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + xVal.typename());
      } else if (!yVal.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + yVal.typename());
      } else if (!widthVal.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + widthVal.typename());
      } else if (!heightVal.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + heightVal.typename());
      } else if (!fromVal.isnumber()) {
         return LuaValue.error("Expected type number at 4, got " + fromVal.typename());
      } else if (!toVal.isnumber()) {
         return LuaValue.error("Expected type number at 5, got " + toVal.typename());
      } else {
         return !verticalVal.isboolean()
            ? LuaValue.error("Expected type boolean at 6, got " + verticalVal.typename())
            : CoerceJavaToLua.coerce(
               new GradientTemplate(xVal.tofloat(), yVal.tofloat(), widthVal.tofloat(), heightVal.tofloat(), fromVal.toint(), toVal.toint(), verticalVal.toboolean())
            );
      }
   }
}
