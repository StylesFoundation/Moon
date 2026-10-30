package com.moon.ui.clickgui.imgui;

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
import java.util.List;

public class ImGuiGroupWidget implements ImGuiWidget {
   private final GroupSetting setting;
   private final List<ImGuiWidget> children = new ArrayList<>();
   private long id;

   @Override
   public void setup() {
      for (Setting childSetting : this.setting.getValue()) {
         if (childSetting instanceof GroupSetting groupSetting) {
            this.children.add(new ImGuiGroupWidget(groupSetting));
         } else if (childSetting instanceof BooleanSetting booleanSetting) {
            this.children.add(new ImGuiBooleanWidget(booleanSetting));
         } else if (childSetting instanceof IntegerSetting integerSetting) {
            this.children.add(new ImGuiIntegerWidget(integerSetting));
         } else if (childSetting instanceof FloatSetting floatSetting) {
            this.children.add(new ImGuiFloatWidget(floatSetting));
         } else if (childSetting instanceof ColorSetting colorSetting) {
            this.children.add(new ImGuiColorWidget(colorSetting));
         } else if (childSetting instanceof EnumSetting enumSetting) {
            this.children.add(new ImGuiEnumWidget(enumSetting));
         } else if (childSetting instanceof ModeSetting modeSetting) {
            this.children.add(new ImGuiModeWidget(modeSetting));
         } else if (childSetting instanceof MultipleChoiceSetting multipleChoiceSetting) {
            this.children.add(new ImGuiMultipleChoiceWidget(multipleChoiceSetting));
         } else if (childSetting instanceof StringSetting stringSetting) {
            this.children.add(new ImGuiStringWidget(stringSetting));
         }
      }

      for (ImGuiWidget widget : this.children) {
         widget.setup();
      }

      this.id = Math.round(Math.random() * 1.0E9);
   }

   @Override
   public void render() {
      if (this.setting.isVisible()) {
         if (ImGui.treeNode(this.setting.getDisplayName())) {
            for (ImGuiWidget widget : this.children) {
               widget.render();
            }

            ImGui.treePop();
         }
      }
   }

   public ImGuiGroupWidget(GroupSetting setting) {
      this.setting = setting;
   }
}
