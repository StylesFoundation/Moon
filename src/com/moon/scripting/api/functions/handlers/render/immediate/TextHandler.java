package com.moon.scripting.api.functions.handlers.render.immediate;

import com.moon.scripting.api.enums.CustomFontType;
import com.moon.scripting.api.functions.render.ImmediateRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class TextHandler extends VarArgFunction {
   final ImmediateRenderFunctionContainer container;

   public TextHandler(ImmediateRenderFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue fontTypeVal = varargs.arg(1);
      LuaValue textVal = varargs.arg(2);
      LuaValue sizeVal = varargs.arg(3);
      LuaValue xVal = varargs.arg(4);
      LuaValue yVal = varargs.arg(5);
      LuaValue colorVal = varargs.arg(6);
      LuaValue shadowVal = varargs.arg(7);
      if (fontTypeVal.isnil()) {
         return LuaValue.error("Expected type customfonttype at 0, got " + fontTypeVal.typename());
      }

      if (CoerceLuaToJava.coerce(fontTypeVal, Object.class) instanceof CustomFontType customFontType) {
         if (!textVal.isstring()) {
            return LuaValue.error("Expected type string at 1, got " + textVal.typename());
         }

         if (!sizeVal.isstring()) {
            return LuaValue.error("Expected type number at 1, got " + sizeVal.typename());
         }

         if (!xVal.isnumber()) {
            return LuaValue.error("Expected type number at 3, got " + xVal.typename());
         }

         if (!yVal.isnumber()) {
            return LuaValue.error("Expected type number at 4, got " + yVal.typename());
         }

         if (!colorVal.isnumber()) {
            return LuaValue.error("Expected type number at 5, got " + colorVal.typename());
         }

         if (!shadowVal.isboolean()) {
            return LuaValue.error("Expected type boolean at 6, got " + shadowVal.typename());
         }

         this.container.implementation.text(customFontType, textVal.tojstring(), sizeVal.tofloat(), xVal.tofloat(), yVal.tofloat(), colorVal.toint(), shadowVal.toboolean());
         return NIL;
      } else {
         return LuaValue.error("Expected type customfonttype at 0, got " + fontTypeVal.typename());
      }
   }
}
