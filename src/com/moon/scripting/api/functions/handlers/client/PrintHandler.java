package com.moon.scripting.api.functions.handlers.client;

import com.moon.scripting.api.functions.ClientFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class PrintHandler extends OneArgFunction {
   final ClientFunctionContainer container;

   public PrintHandler(ClientFunctionContainer jP) {
      this.container = jP;
   }

   @Override
   public LuaValue call(LuaValue luaValue) {
      this.container.implementation.print(luaValueToString(luaValue));
      return LuaValue.NIL;
   }

   private static String luaValueToString(LuaValue value) {
      return value.isnil() ? "nil" : value.tojstring();
   }
}
