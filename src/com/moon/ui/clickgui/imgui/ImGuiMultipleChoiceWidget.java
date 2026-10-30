package com.moon.ui.clickgui.imgui;

import com.moon.module.LabelEnum;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import imgui.ImGui;
import imgui.type.ImInt;
import java.util.ArrayList;

public class ImGuiMultipleChoiceWidget implements ImGuiWidget {
   private final MultipleChoiceSetting setting;
   private final ImInt currentIndex = new ImInt();
   private String[] options;
   private long id;

   @Override
   public void setup() {
      ArrayList<String> optionLabels = new ArrayList<>();

      for (Enum enumValue : this.setting.getValues()) {
         if (enumValue instanceof LabelEnum labelEnum) {
            optionLabels.add(labelEnum.label());
         }
      }

      this.options = optionLabels.toArray(new String[0]);
      this.id = Math.round(Math.random() * 1.0E9);
   }

   @Override
   public void render() {
      if (this.setting.isVisible()) {
         ImGui.pushID(this.id);
         if (ImGui.beginListBox(this.setting.getDisplayName(), ImGui.calcItemWidth(), 85.0F)) {
            for (int i = 0; i < this.options.length; i++) {
               String option = this.options[i];
               Enum enumValue = this.setting.getValues()[i];
               boolean checked = this.setting.hasChecked(enumValue);
               if (ImGui.selectable(option, checked)) {
                  this.setting.toggle(enumValue);
               }
            }

            ImGui.endListBox();
         }

         ImGui.popID();
      }
   }

   public ImGuiMultipleChoiceWidget(MultipleChoiceSetting setting) {
      this.setting = setting;
   }
}
