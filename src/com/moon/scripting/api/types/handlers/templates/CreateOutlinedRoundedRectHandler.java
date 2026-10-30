package com.moon.scripting.api.types.handlers.templates;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.types.templates.OutlinedRoundedRectTemplate;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class CreateOutlinedRoundedRectHandler extends VarArgFunction {
   public final ScriptLoader scriptLoader;

   public CreateOutlinedRoundedRectHandler(ScriptLoader scriptLoader) {
      this.scriptLoader = scriptLoader;
   }

   @Override
   public Varargs invoke(Varargs args) {
      LuaValue xVal = args.arg(1);
      LuaValue yVal = args.arg(2);
      LuaValue widthVal = args.arg(3);
      LuaValue heightVal = args.arg(4);
      LuaValue radiusVal = args.arg(5);
      LuaValue thicknessVal = args.arg(6);
      LuaValue colorVal = args.arg(7);
      if (!xVal.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + xVal.typename());
      } else if (!yVal.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + yVal.typename());
      } else if (!widthVal.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + widthVal.typename());
      } else if (!heightVal.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + heightVal.typename());
      } else if (!radiusVal.isnumber()) {
         return LuaValue.error("Expected type number at 4, got " + radiusVal.typename());
      } else if (!thicknessVal.isnumber()) {
         return LuaValue.error("Expected type number at 5, got " + thicknessVal.typename());
      } else {
         return !colorVal.isnumber()
            ? LuaValue.error("Expected type number at 6, got " + colorVal.typename())
            : CoerceJavaToLua.coerce(
               new OutlinedRoundedRectTemplate(
                  xVal.tofloat(),
                  yVal.tofloat(),
                  widthVal.tofloat(),
                  heightVal.tofloat(),
                  radiusVal.tofloat(),
                  thicknessVal.tofloat(),
                  colorVal.toint()
               )
            );
      }
   }
}
