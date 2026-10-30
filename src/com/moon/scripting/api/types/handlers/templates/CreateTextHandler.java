package com.moon.scripting.api.types.handlers.templates;

import com.moon.scripting.ScriptLoader;
import com.moon.scripting.api.enums.CustomFontType;
import com.moon.scripting.api.types.templates.TextTemplate;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class CreateTextHandler extends VarArgFunction {
   public final ScriptLoader scriptLoader;

   public CreateTextHandler(ScriptLoader scriptLoader) {
      this.scriptLoader = scriptLoader;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue fontType = varargs.arg(1);
      LuaValue text = varargs.arg(2);
      LuaValue size = varargs.arg(3);
      LuaValue x = varargs.arg(4);
      LuaValue y = varargs.arg(5);
      LuaValue color = varargs.arg(6);
      LuaValue shadow = varargs.arg(7);
      if (fontType.isnil()) {
         return LuaValue.error("Expected type customfonttype at 0, got " + fontType.typename());
      }

      if (CoerceLuaToJava.coerce(fontType, Object.class) instanceof CustomFontType customFontType) {
         if (!text.isstring()) {
            return LuaValue.error("Expected type string at 1, got " + text.typename());
         } else if (!size.isstring()) {
            return LuaValue.error("Expected type number at 1, got " + size.typename());
         } else if (!x.isnumber()) {
            return LuaValue.error("Expected type number at 3, got " + x.typename());
         } else if (!y.isnumber()) {
            return LuaValue.error("Expected type number at 4, got " + y.typename());
         } else if (!color.isnumber()) {
            return LuaValue.error("Expected type number at 5, got " + color.typename());
         } else {
            return !shadow.isboolean()
               ? LuaValue.error("Expected type boolean at 6, got " + shadow.typename())
               : CoerceJavaToLua.coerce(
                  new TextTemplate(customFontType, text.tojstring(), size.tofloat(), x.tofloat(), y.tofloat(), color.toint(), shadow.toboolean())
               );
         }
      } else {
         return LuaValue.error("Expected type customfonttype at 0, got " + fontType.typename());
      }
   }
}
