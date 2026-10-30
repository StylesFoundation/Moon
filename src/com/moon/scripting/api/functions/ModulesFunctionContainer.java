package com.moon.scripting.api.functions;

import com.moon.scripting.api.functions.handlers.modules.CreateBooleanSettingHandler;
import com.moon.scripting.api.functions.handlers.modules.CreateColorSettingHandler;
import com.moon.scripting.api.functions.handlers.modules.CreateFloatSettingHandler;
import com.moon.scripting.api.functions.handlers.modules.CreateIntegerSettingHandler;
import com.moon.scripting.api.functions.handlers.modules.CreateTextSettingHandler;
import com.moon.scripting.api.functions.handlers.modules.GetSettingHandler;
import com.moon.scripting.api.functions.handlers.modules.IsEnabledHandler;
import com.moon.scripting.api.functions.handlers.modules.RegisterHandler;
import com.moon.scripting.api.functions.handlers.modules.SetSettingHandler;
import com.moon.scripting.api.functions.handlers.modules.SetStateHandler;
import com.moon.scripting.api.functions.handlers.modules.ToggleHandler;
import com.moon.scripting.api.functions.impl.ModulesFunctionsImpl;
import org.luaj.vm2.LuaTable;

public class ModulesFunctionContainer extends FunctionContainer {
   public final ModulesFunctionsImpl implementation = new ModulesFunctionsImpl();

   public ModulesFunctionContainer() {
      super("modules");
   }

   @Override
   public void registerFunctions(LuaTable table) {
      table.set("register", new RegisterHandler(this));
      table.set("is_enabled", new IsEnabledHandler(this));
      table.set("set_state", new SetStateHandler(this));
      table.set("toggle", new ToggleHandler(this));
      table.set("set_setting", new SetSettingHandler(this));
      table.set("create_boolean_setting", new CreateBooleanSettingHandler(this));
      table.set("create_integer_setting", new CreateIntegerSettingHandler(this));
      table.set("create_float_setting", new CreateFloatSettingHandler(this));
      table.set("create_color_setting", new CreateColorSettingHandler(this));
      table.set("create_text_setting", new CreateTextSettingHandler(this));
      table.set("get_setting", new GetSettingHandler(this));
   }
}
