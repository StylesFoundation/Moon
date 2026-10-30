package com.moon.scripting.api.functions.impl;

import com.moon.Moon;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.ModuleManager;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.IndividualColorVisibilitySupplier;
import com.moon.module.impl.visual.hud.modulelist.ModuleListElementRemoveCallback;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.module.setting.impl.StringSetting;
import com.moon.scripting.ScriptModule;
import com.moon.scripting.api.functions.ModulesFunctions;
import com.moon.ui.clickgui.classic.component.CategoryPanel;
import com.moon.util.client.ChatUtil;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.luaj.vm2.LuaTable;

public class ModulesFunctionsImpl implements ModulesFunctions {
   @Override
   public void register(String name, String displayName, String description, LuaTable table) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      HUD hud = moduleManager.getModule(HUD.class);
      if (moduleManager.getModuleByName(name) != null) {
         ChatUtil.sendMessage("<red>Unable to register script module " + name + " as another module already uses this name.");
      } else {
         ScriptModule scriptModule = new ScriptModule(name, displayName, description, table);
         List settings = scriptModule.getSettings();
         ColorSetting colorSetting = ColorSetting.builder("individual-module-color")
            .defaults("Arraylist Color", -1)
            .visibleWhen(new IndividualColorVisibilitySupplier(hud))
            .onChange(new ModuleListElementRemoveCallback(hud, hud.getModuleListElement()))
            .build();
         settings.add(colorSetting);
         Moon.getInstance().getBindManager().add(scriptModule);
         moduleManager.registerModule(scriptModule);
         this.refreshScriptModuleList();
      }
   }

   @Override
   public boolean is_enabled(String name) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      Module module = moduleManager.getModuleByName(name);
      return module == null ? false : module.isEnabled();
   }

   @Override
   public void set_state(String name, boolean state) {
      Module module = Moon.getInstance().getModuleManager().getModuleByName(name);
      if (module != null) {
         Minecraft.getMinecraft().Method1015(() -> module.setEnabled(state));
      }
   }

   @Override
   public void toggle(String name) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      Module module = moduleManager.getModuleByName(name);
      if (module != null) {
         Minecraft.getMinecraft().Method1015(() -> module.toggle());
      }
   }

   @Override
   public void set_setting(String moduleName, String settingId, Object value) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      Module module = moduleManager.getModuleByName(moduleName);
      if (module != null) {
         Setting setting = module.getSetting(settingId);
         if (!(setting instanceof BooleanSetting) && !(setting instanceof StringSetting)) {
            if (setting instanceof FloatSetting) {
               if (value != null) {
                  Number number = (Number)value;
                  setting.setValue(number.floatValue());
               }
            } else if (!(setting instanceof IntegerSetting) && !(setting instanceof ColorSetting)) {
               if (setting instanceof ModeSetting) {
                  Enum modeValue = ((ModeSetting)setting).getValues()[(Integer)value];
                  Object castValue = this.safeCast(modeValue);
                  if (castValue != null) {
                     setting.setValue(this.safeCast(modeValue));
                  }
               } else if (setting instanceof EnumSetting) {
                  Enum enumValue = ((EnumSetting)setting).getValues()[(Integer)value];
                  Object castValue = this.safeCast(enumValue);
                  if (castValue != null) {
                     setting.setValue(this.safeCast(enumValue));
                  }
               } else if (setting instanceof MultipleChoiceSetting choiceSetting) {
                  Enum choiceValue = choiceSetting.getValues()[(Integer)value];
                  Object castValue = this.safeCast(choiceValue);
                  if (castValue != null) {
                     choiceSetting.toggle(this.safeCast(choiceValue));
                  }
               }
            } else if (value != null) {
               Number number = (Number)value;
               setting.setValue(number.intValue());
            }
         } else if (value != null) {
            setting.setValue(value);
         }
      }
   }

   private <T> T safeCast(Object object) {
      try {
         return (T)object;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   @Override
   public void create_boolean_setting(String moduleName, String id, String name, boolean defaultValue) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      if (moduleManager.getModuleByName(moduleName) instanceof ScriptModule scriptModule) {
         BooleanSetting setting = BooleanSetting.builder(id).defaults(name, defaultValue).build();
         scriptModule.getSettings().add(setting);
         this.refreshScriptModuleList();
      }
   }

   @Override
   public void create_integer_setting(String moduleName, String id, String name, int defaultValue, int min, int max, int increment) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      if (moduleManager.getModuleByName(moduleName) instanceof ScriptModule scriptModule) {
         IntegerSetting setting = IntegerSetting.builder(id).defaults(name, defaultValue, min, max, increment).build();
         scriptModule.getSettings().add(setting);
         this.refreshScriptModuleList();
      }
   }

   @Override
   public void create_float_setting(String moduleName, String id, String name, float defaultValue, float min, float max, float increment) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      if (moduleManager.getModuleByName(moduleName) instanceof ScriptModule scriptModule) {
         FloatSetting setting = FloatSetting.builder(id).defaults(name, defaultValue, min, max, increment).build();
         scriptModule.getSettings().add(setting);
         this.refreshScriptModuleList();
      }
   }

   @Override
   public void create_color_setting(String moduleName, String id, String name, int defaultColor) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      if (moduleManager.getModuleByName(moduleName) instanceof ScriptModule scriptModule) {
         ColorSetting setting = ColorSetting.builder(id).defaults(name, defaultColor).build();
         scriptModule.getSettings().add(setting);
         this.refreshScriptModuleList();
      }
   }

   @Override
   public void create_text_setting(String moduleName, String id, String name, String defaultValue) {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      if (moduleManager.getModuleByName(moduleName) instanceof ScriptModule scriptModule) {
         StringSetting setting = StringSetting.builder(id).defaults(name, defaultValue).build();
         scriptModule.getSettings().add(setting);
         this.refreshScriptModuleList();
      }
   }

   @Override
   public Object get_setting(String moduleName, String settingId) {
      Module module = Moon.getInstance().getModuleManager().getModuleByName(moduleName);
      return module == null ? null : module.getSetting(settingId).getValue();
   }

   private void refreshScriptModuleList() {
      ModuleManager moduleManager = Moon.getInstance().getModuleManager();
      ClickGUI clickGUI = moduleManager.getModule(ClickGUI.class);
      Minecraft.getMinecraft().addScheduledTask(() -> {
         if (clickGUI.getClassicScreen() != null) {
            List scriptModules = moduleManager.getModulesByCategory(ModuleCategory.SCRIPTS);
            CategoryPanel scriptsPanel = clickGUI.getClassicScreen().getScriptsPanel();
            scriptsPanel.getModules().clear();
            scriptsPanel.getModules().addAll(scriptModules);
            scriptsPanel.init();
         }

         if (clickGUI.getModernScreen() != null) {
            clickGUI.getModernScreen().getScriptsPanel().rebuild();
         }
      });
   }
}
