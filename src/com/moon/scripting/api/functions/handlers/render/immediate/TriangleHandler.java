package com.moon.scripting.api.functions.handlers.render.immediate;

import com.moon.scripting.api.functions.render.ImmediateRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class TriangleHandler extends VarArgFunction {
   final ImmediateRenderFunctionContainer container;

   public TriangleHandler(ImmediateRenderFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue topXVal = varargs.arg(1);
      LuaValue topYVal = varargs.arg(2);
      LuaValue leftXVal = varargs.arg(3);
      LuaValue leftYVal = varargs.arg(4);
      LuaValue rightXVal = varargs.arg(5);
      LuaValue rightYVal = varargs.arg(6);
      LuaValue colorVal = varargs.arg(7);
      if (!topXVal.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + topXVal.typename());
      }

      if (!topYVal.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + topYVal.typename());
      }

      if (!leftXVal.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + leftXVal.typename());
      }

      if (!leftYVal.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + leftYVal.typename());
      }

      if (!rightXVal.isnumber()) {
         return LuaValue.error("Expected type number at 4, got " + rightXVal.typename());
      }

      if (!rightYVal.isnumber()) {
         return LuaValue.error("Expected type number at 5, got " + rightYVal.typename());
      }

      if (!colorVal.isnumber()) {
         return LuaValue.error("Expected type number at 6, got " + colorVal.typename());
      }

      this.container.implementation.triangle(topXVal.tofloat(), topYVal.tofloat(), leftXVal.tofloat(), leftYVal.tofloat(), rightXVal.tofloat(), rightYVal.tofloat(), colorVal.toint());
      return NIL;
   }
}
