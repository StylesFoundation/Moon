package com.moon.scripting.api.functions.handlers.modules;

import com.moon.scripting.api.functions.ModulesFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class GetSettingHandler extends VarArgFunction {
   final ModulesFunctionContainer container;

   public GetSettingHandler(ModulesFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue moduleName = varargs.arg(1);
      LuaValue settingId = varargs.arg(2);
      if (moduleName.isnil() || !moduleName.isstring()) {
         return LuaValue.error("Expected type string at 0, got " + moduleName.typename());
      } else {
         return !settingId.isnil() && settingId.isstring()
            ? CoerceJavaToLua.coerce(this.container.implementation.get_setting(moduleName.tojstring(), settingId.tojstring()))
            : LuaValue.error("Expected type string at 1, got " + settingId.typename());
      }
   }
}
