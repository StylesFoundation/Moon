package com.moon.ui.clickgui.imgui;

import com.moon.module.LabelEnum;
import com.moon.module.setting.impl.ModeSetting;
import imgui.ImGui;
import imgui.type.ImInt;
import java.util.ArrayList;

public class ImGuiModeWidget implements ImGuiWidget {
   private final ModeSetting setting;
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
         int ordinal = ((Enum)this.setting.getValue()).ordinal();
         this.currentIndex.set(ordinal);
         ImGui.pushID(this.id);
         ImGui.combo(this.setting.getDisplayName(), this.currentIndex, this.options);
         if (ImGui.isItemEdited()) {
            this.setting.setValue(this.setting.getValues()[this.currentIndex.get()]);
         }

         ImGui.popID();
      }
   }

   public ImGuiModeWidget(ModeSetting setting) {
      this.setting = setting;
   }
}
