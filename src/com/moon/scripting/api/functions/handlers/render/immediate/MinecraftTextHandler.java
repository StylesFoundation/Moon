package com.moon.scripting.api.functions.handlers.render.immediate;

import com.moon.scripting.api.functions.render.ImmediateRenderFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class MinecraftTextHandler extends VarArgFunction {
   final ImmediateRenderFunctionContainer container;

   public MinecraftTextHandler(ImmediateRenderFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue textVal = varargs.arg(1);
      LuaValue xVal = varargs.arg(2);
      LuaValue yVal = varargs.arg(3);
      LuaValue colorVal = varargs.arg(4);
      LuaValue shadowVal = varargs.arg(5);
      if (!textVal.isstring()) {
         return LuaValue.error("Expected type string at 0, got " + textVal.typename());
      }

      if (!xVal.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + xVal.typename());
      }

      if (!yVal.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + yVal.typename());
      }

      if (!colorVal.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + colorVal.typename());
      }

      if (!shadowVal.isboolean()) {
         return LuaValue.error("Expected type boolean at 4, got " + shadowVal.typename());
      }

      this.container.implementation.minecraft_text(textVal.tojstring(), xVal.tofloat(), yVal.tofloat(), colorVal.toint(), shadowVal.toboolean());
      return NIL;
   }
}
