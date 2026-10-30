package com.moon.scripting.api.functions.handlers.modules;

import com.moon.scripting.api.functions.ModulesFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class IsEnabledHandler extends OneArgFunction {
   final ModulesFunctionContainer container;

   public IsEnabledHandler(ModulesFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue arg) {
      if (!arg.isstring()) {
         return LuaValue.error("Expected type string at 0, got " + arg.typename());
      }

      String moduleName = arg.tojstring();
      return LuaValue.valueOf(this.container.implementation.is_enabled(moduleName));
   }
}
