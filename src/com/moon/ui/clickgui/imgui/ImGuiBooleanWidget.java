package com.moon.ui.clickgui.imgui;

import com.moon.module.setting.impl.BooleanSetting;
import imgui.ImGui;

public class ImGuiBooleanWidget implements ImGuiWidget {
   private final BooleanSetting setting;
   private long id;

   @Override
   public void setup() {
      this.id = Math.round(Math.random() * 1.0E9);
   }

   @Override
   public void render() {
      if (this.setting.isVisible()) {
         ImGui.pushID(this.id);
         ImGui.checkbox(this.setting.getDisplayName(), this.setting.getValue());
         if (ImGui.isItemClicked()) {
            this.setting.setValue(!this.setting.getValue());
         }

         ImGui.popID();
      }
   }

   public ImGuiBooleanWidget(BooleanSetting setting) {
      this.setting = setting;
   }
}
