package com.moon.ui.clickgui.imgui;

import com.moon.module.Module;
import com.moon.module.setting.Setting;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.module.setting.impl.StringSetting;
import imgui.ImGui;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ImGuiModuleWidget implements ImGuiWidget {
   private final Module module;
   private final List<ImGuiWidget> settingWidgets = new ArrayList<>();

   @Override
   public void setup() {
      for (Setting setting : this.module.getSettings()) {
         if (!setting.isGrouped()) {
            if (setting instanceof GroupSetting groupSetting) {
               this.settingWidgets.add(new ImGuiGroupWidget(groupSetting));
            } else if (setting instanceof BooleanSetting booleanSetting) {
               this.settingWidgets.add(new ImGuiBooleanWidget(booleanSetting));
            } else if (setting instanceof IntegerSetting integerSetting) {
               this.settingWidgets.add(new ImGuiIntegerWidget(integerSetting));
            } else if (setting instanceof FloatSetting floatSetting) {
               this.settingWidgets.add(new ImGuiFloatWidget(floatSetting));
            } else if (setting instanceof ColorSetting colorSetting) {
               this.settingWidgets.add(new ImGuiColorWidget(colorSetting));
            } else if (setting instanceof EnumSetting enumSetting) {
               this.settingWidgets.add(new ImGuiEnumWidget(enumSetting));
            } else if (setting instanceof ModeSetting modeSetting) {
               this.settingWidgets.add(new ImGuiModeWidget(modeSetting));
            } else if (setting instanceof MultipleChoiceSetting multipleChoiceSetting) {
               this.settingWidgets.add(new ImGuiMultipleChoiceWidget(multipleChoiceSetting));
            } else if (setting instanceof StringSetting stringSetting) {
               this.settingWidgets.add(new ImGuiStringWidget(stringSetting));
            }
         }
      }

      Iterator iterator = this.settingWidgets.iterator();

      while (iterator.hasNext()) {
         ((ImGuiWidget)iterator.next()).setup();
      }
   }

   @Override
   public void render() {
      if (ImGui.collapsingHeader(this.module.getName())) {
         ImGui.checkbox("Enabled", this.module.isEnabled());
         if (ImGui.isItemClicked()) {
            this.module.toggle();
         }

         Iterator iterator = this.settingWidgets.iterator();

         while (iterator.hasNext()) {
            ((ImGuiWidget)iterator.next()).render();
         }
      }
   }

   public ImGuiModuleWidget(Module module) {
      this.module = module;
   }
}
