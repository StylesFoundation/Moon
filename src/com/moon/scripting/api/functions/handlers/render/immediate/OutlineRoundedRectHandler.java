package com.moon.scripting.api.functions.handlers.render.immediate;

import com.moon.scripting.api.functions.render.ImmediateRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class OutlineRoundedRectHandler extends VarArgFunction {
   final ImmediateRenderFunctionContainer container;

   public OutlineRoundedRectHandler(ImmediateRenderFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue xVal = varargs.arg(1);
      LuaValue yVal = varargs.arg(2);
      LuaValue widthVal = varargs.arg(3);
      LuaValue heightVal = varargs.arg(4);
      LuaValue radiusVal = varargs.arg(5);
      LuaValue thicknessVal = varargs.arg(6);
      LuaValue colorVal = varargs.arg(7);
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

      if (!radiusVal.isnumber()) {
         return LuaValue.error("Expected type number at 4, got " + radiusVal.typename());
      }

      if (!thicknessVal.isnumber()) {
         return LuaValue.error("Expected type number at 5, got " + thicknessVal.typename());
      }

      if (!colorVal.isnumber()) {
         return LuaValue.error("Expected type number at 6, got " + colorVal.typename());
      }

      this.container
         .implementation
         .outline_rounded_rect(xVal.tofloat(), yVal.tofloat(), widthVal.tofloat(), heightVal.tofloat(), radiusVal.tofloat(), thicknessVal.tofloat(), colorVal.toint());
      return NIL;
   }
}
