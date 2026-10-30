package com.moon.ui.clickgui.imgui;

import com.moon.module.setting.impl.IntegerSetting;
import imgui.ImGui;

public class ImGuiIntegerWidget implements ImGuiWidget {
   private final IntegerSetting setting;
   private final int[] value = new int[1];
   private long id;

   @Override
   public void setup() {
      this.value[0] = this.setting.getValue();
      this.id = Math.round(Math.random() * 1.0E9);
   }

   @Override
   public void render() {
      if (this.setting.isVisible()) {
         ImGui.pushID(this.id);
         this.value[0] = this.setting.getValue();
         ImGui.sliderInt(this.setting.getDisplayName(), this.value, this.setting.getMin(), this.setting.getMax());
         if (ImGui.isItemEdited()) {
            this.setting.setRawValueClamped(this.value[0]);
         }

         ImGui.popID();
      }
   }

   public ImGuiIntegerWidget(IntegerSetting setting) {
      this.setting = setting;
   }
}
