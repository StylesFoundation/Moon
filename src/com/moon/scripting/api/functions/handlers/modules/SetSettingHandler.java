package com.moon.scripting.api.functions.handlers.modules;

import com.moon.scripting.api.functions.ModulesFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class SetSettingHandler extends ThreeArgFunction {
   final ModulesFunctionContainer container;

   public SetSettingHandler(ModulesFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue module, LuaValue settingId, LuaValue value) {
      if (module.isnil() || !module.isstring()) {
         return LuaValue.error("Expected type string at 0, got " + module.typename());
      }

      if (settingId.isnil() || !settingId.isstring()) {
         return LuaValue.error("Expected type string at 1, got " + settingId.typename());
      }

      if (value.isnil()) {
         return LuaValue.error("Expected type any at 2, got " + value.typename());
      }

      String moduleName = module.tojstring();
      String id = settingId.tojstring();
      Object settingValue = CoerceLuaToJava.coerce(value, Object.class);
      this.container.implementation.set_setting(moduleName, id, settingValue);
      return LuaValue.NIL;
   }
}
