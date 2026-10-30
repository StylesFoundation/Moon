package com.moon.scripting.api.functions.handlers.render.immediate;

import com.moon.scripting.api.functions.render.ImmediateRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class GradientRectHandler extends VarArgFunction {
   final ImmediateRenderFunctionContainer container;

   public GradientRectHandler(ImmediateRenderFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue xVal = varargs.arg(1);
      LuaValue yVal = varargs.arg(2);
      LuaValue widthVal = varargs.arg(3);
      LuaValue heightVal = varargs.arg(4);
      LuaValue fromColorVal = varargs.arg(5);
      LuaValue toColorVal = varargs.arg(6);
      LuaValue verticalVal = varargs.arg(7);
      if (!xVal.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + xVal.typename());
      }

      if (!yVal.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + yVal.typename());
      }

      if (!widthVal.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + widthVal.typename());
      }

      if (!heightVal.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + heightVal.typename());
      }

      if (!fromColorVal.isnumber()) {
         return LuaValue.error("Expected type number at 4, got " + fromColorVal.typename());
      }

      if (!toColorVal.isnumber()) {
         return LuaValue.error("Expected type number at 5, got " + toColorVal.typename());
      }

      if (!verticalVal.isboolean()) {
         return LuaValue.error("Expected type boolean at 6, got " + verticalVal.typename());
      }

      this.container
         .implementation
         .gradient_rect(xVal.tofloat(), yVal.tofloat(), widthVal.tofloat(), heightVal.tofloat(), fromColorVal.toint(), toColorVal.toint(), verticalVal.toboolean());
      return NIL;
   }
}
