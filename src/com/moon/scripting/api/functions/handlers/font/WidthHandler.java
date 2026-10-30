package com.moon.scripting.api.functions.handlers.font;

import com.moon.scripting.api.enums.CustomFontType;
import com.moon.scripting.api.functions.FontFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class WidthHandler extends ThreeArgFunction {
   final FontFunctionContainer container;

   public WidthHandler(FontFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue fontArg, LuaValue sizeArg, LuaValue textArg) {
      if (fontArg.isnil()) {
         return LuaValue.error("Expected type customfonttype at 0, got " + fontArg.typename());
      }

      if (CoerceLuaToJava.coerce(fontArg, Object.class) instanceof CustomFontType fontType) {
         if (!sizeArg.isnumber()) {
            return LuaValue.error("Expected type number at 1, got " + sizeArg.typename());
         } else {
            return !textArg.isstring()
               ? LuaValue.error("Expected type string at 2, got " + textArg.typename())
               : LuaValue.valueOf(this.container.implementation.width(fontType, sizeArg.tofloat(), textArg.tojstring()));
         }
      } else {
         return LuaValue.error("Expected type customfonttype at 0, got " + fontArg.typename());
      }
   }
}
