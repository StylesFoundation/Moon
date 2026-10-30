package com.moon.ui.clickgui.imgui;

import com.moon.module.setting.impl.FloatSetting;
import imgui.ImGui;

public class ImGuiFloatWidget implements ImGuiWidget {
   private final FloatSetting setting;
   private final float[] value = new float[1];
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
         ImGui.sliderFloat(this.setting.getDisplayName(), this.value, this.setting.getMin(), this.setting.getMax());
         if (ImGui.isItemEdited()) {
            this.setting.setValue(this.value[0]);
         }

         ImGui.popID();
      }
   }

   public ImGuiFloatWidget(FloatSetting setting) {
      this.setting = setting;
   }
}
