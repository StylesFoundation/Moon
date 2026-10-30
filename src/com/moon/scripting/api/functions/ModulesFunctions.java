package com.moon.scripting.api.functions;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.ExampleCode;
import com.moon.scripting.documentation.annotation.Parameter;
import org.luaj.vm2.LuaTable;

@com.moon.scripting.documentation.annotation.FunctionContainer(value = "modules", path = "functions/modules")
@Description("Contains functions to create modules and settings for them")
public interface ModulesFunctions {
   @Description("Used to register new modules in the client")
   @ExampleCode(
      "local module = {\n    on_enable = function()\n        client:print('The module has been enabled!')\n    end\n}\n-- Register\nmodules:register(\"YourModule\", \"Your Module\", module)"
   )
   void register(
      @Parameter("name") @Description("The name of the module") String name,
      @Parameter("display_name") @Description("The display name of the module, used in arraylist and clickgui") String displayName,
      @Parameter("description") @Description("The description of the module, shown in the clickgui") String description,
      @Parameter("table") @Description("The module table containing all event hooks, you can find all available events [here](../tables/events.md)") LuaTable table
   );

   @Description("Returns whether a module is enabled or not")
   boolean is_enabled(@Parameter("name") @Description("The name of the module") String name);

   @Description("Sets the enabled state of a module")
   void set_state(
      @Parameter("name") @Description("The name of the module to update the state of") String name,
      @Parameter("state") @Description("The new module state") boolean state
   );

   @Description("Toggles a module")
   void toggle(@Parameter("name") @Description("The name of the module to toggle") String name);

   @Description(
      "Sets the value of a setting<br>Some settings have to be treated differently:<br>- Modes / Enums: Please use the number index of the setting (starts from 0)<br>Multiple Choice: Please use the number index, you can only toggle ONE at a time, please be aware of that (starts from 0)"
   )
   void set_setting(
      @Parameter("name") @Description("The name of the module which contains the setting you want to change") String module,
      @Parameter("setting_id") @Description(
   "The id of the setting you want to change, if you don't know what it is just save a config and take a look at that"
) String settingId,
      @Parameter("value") @Description("The new value<br>BEWARE: Observe the formats described in the methods description") Object value
   );

   @Description("Used to create a boolean setting for a scripting module")
   void create_boolean_setting(
      @Parameter("module") @Description("The module the setting will be added to") String module,
      @Parameter("id") @Description("The id of the setting (used for configs)") String id,
      @Parameter("name") @Description("The name of the setting") String name,
      @Parameter("default") @Description("The default value of the setting") boolean defaultValue
   );

   @Description("Used to create a integer setting for a scripting module")
   void create_integer_setting(
      @Parameter("module") @Description("The module the setting will be added to") String module,
      @Parameter("id") @Description("The id of the setting (used for configs)") String id,
      @Parameter("name") @Description("The name of the setting") String name,
      @Parameter("default") @Description("The default value of the setting (ideally in-between of min and max)") int defaultValue,
      @Parameter("min") @Description("The minimum value allowed") int min,
      @Parameter("max") @Description("The maximum value allowed") int max,
      @Parameter("increment") @Description("The increment amount") int increment
   );

   @Description("Used to create a float setting for a scripting module")
   void create_float_setting(
      @Parameter("module") @Description("The module the setting will be added to") String module,
      @Parameter("id") @Description("The id of the setting (used for configs)") String id,
      @Parameter("name") @Description("The name of the setting") String name,
      @Parameter("default") @Description("The default value of the setting (ideally in-between of min and max)") float defaultValue,
      @Parameter("min") @Description("The minimum value allowed") float min,
      @Parameter("max") @Description("The maximum value allowed") float max,
      @Parameter("increment") @Description("The increment amount") float increment
   );

   @Description("Used to create a color setting for a scripting module")
   void create_color_setting(
      @Parameter("module") @Description("The module the setting will be added to") String module,
      @Parameter("id") @Description("The id of the setting (used for configs)") String id,
      @Parameter("name") @Description("The name of the setting") String name,
      @Parameter("default") @Description("The default value of the setting") int defaultValue
   );

   @Description("Used to create a text setting for a scripting module")
   void create_text_setting(
      @Parameter("module") @Description("The module the setting will be added to") String module,
      @Parameter("id") @Description("The id of the setting (used for configs)") String id,
      @Parameter("name") @Description("The name of the setting") String name,
      @Parameter("default") @Description("The default value of the setting") String defaultValue
   );

   @Description("Returns the value of a setting")
   Object get_setting(
      @Parameter("module") @Description("The module which contains the setting") String module,
      @Parameter("id") @Description("The id of the setting") String id
   );
}
