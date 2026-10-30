package com.moon.scripting.api.functions.handlers.modules;

import com.moon.scripting.api.functions.ModulesFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class CreateIntegerSettingHandler extends VarArgFunction {
   final ModulesFunctionContainer container;

   public CreateIntegerSettingHandler(ModulesFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue moduleName = varargs.arg(1);
      LuaValue settingId = varargs.arg(2);
      LuaValue settingName = varargs.arg(3);
      LuaValue defaultValue = varargs.arg(4);
      LuaValue minValue = varargs.arg(5);
      LuaValue maxValue = varargs.arg(6);
      LuaValue increment = varargs.arg(7);
      if (moduleName.isnil() || !moduleName.isstring()) {
         return LuaValue.error("Expected type string at 0, got " + moduleName.typename());
      } else if (settingId.isnil() || !settingId.isstring()) {
         return LuaValue.error("Expected type string at 1, got " + settingId.typename());
      } else if (settingName.isnil() || !settingName.isstring()) {
         return LuaValue.error("Expected type string at 2, got " + settingName.typename());
      } else if (defaultValue.isnil() || !defaultValue.isnumber()) {
         return LuaValue.error("Expected type number at 3, got " + defaultValue.typename());
      } else if (minValue.isnil() || !minValue.isnumber()) {
         return LuaValue.error("Expected type number at 4, got " + minValue.typename());
      } else if (maxValue.isnil() || !maxValue.isnumber()) {
         return LuaValue.error("Expected type number at 5, got " + maxValue.typename());
      } else if (!increment.isnil() && increment.isnumber()) {
         this.container
            .implementation
            .create_integer_setting(moduleName.tojstring(), settingId.tojstring(), settingName.tojstring(), defaultValue.toint(), minValue.toint(), maxValue.toint(), increment.toint());
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 6, got " + increment.typename());
      }
   }
}
