package com.moon.scripting.api.functions.handlers.modules;

import com.moon.scripting.api.functions.ModulesFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class SetStateHandler extends TwoArgFunction {
   final ModulesFunctionContainer container;

   public SetStateHandler(ModulesFunctionContainer jT) {
      this.container = jT;
   }

   @Override
   public LuaValue call(LuaValue luaValue1, LuaValue luaValue2) {
      if (luaValue1.isnil() || !luaValue1.isstring()) {
         return LuaValue.error("Expected type string at 0, got " + luaValue1.typename());
      } else if (!luaValue2.isnil() && luaValue2.isboolean()) {
         this.container.implementation.set_state(luaValue1.tojstring(), luaValue2.toboolean());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type boolean at 1, got " + luaValue2.typename());
      }
   }
}
