package com.moon.scripting.api.functions.handlers.modules;

import com.moon.scripting.api.functions.ModulesFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class CreateColorSettingHandler extends VarArgFunction {
   final ModulesFunctionContainer container;

   public CreateColorSettingHandler(ModulesFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue moduleName = varargs.arg(1);
      LuaValue settingId = varargs.arg(2);
      LuaValue settingName = varargs.arg(3);
      LuaValue defaultValue = varargs.arg(4);
      if (moduleName.isnil() || !moduleName.isstring()) {
         return LuaValue.error("Expected type string at 0, got " + moduleName.typename());
      } else if (settingId.isnil() || !settingId.isstring()) {
         return LuaValue.error("Expected type string at 1, got " + settingId.typename());
      } else if (settingName.isnil() || !settingName.isstring()) {
         return LuaValue.error("Expected type string at 2, got " + settingName.typename());
      } else if (!defaultValue.isnil() && defaultValue.isnumber()) {
         this.container.implementation.create_color_setting(moduleName.tojstring(), settingId.tojstring(), settingName.tojstring(), defaultValue.toint());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 3, got " + defaultValue.typename());
      }
   }
}
