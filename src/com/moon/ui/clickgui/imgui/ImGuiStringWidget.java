package com.moon.ui.clickgui.imgui;

import com.moon.module.setting.impl.StringSetting;
import imgui.ImGui;
import imgui.type.ImString;

public class ImGuiStringWidget implements ImGuiWidget {
   private final StringSetting setting;
   private ImString text = new ImString(512);
   private long id;

   @Override
   public void setup() {
      this.text.set(this.setting.getValue());
      this.id = Math.round(Math.random() * 1.0E9);
   }

   @Override
   public void render() {
      if (this.setting.isVisible()) {
         ImGui.pushID(this.id);
         this.text.set(this.setting.getValue());
         ImGui.inputText(this.setting.getDisplayName(), this.text);
         if (ImGui.isItemEdited()) {
            this.setting.setValue(this.text.get());
         }

         ImGui.popID();
      }
   }

   public ImGuiStringWidget(StringSetting setting) {
      this.setting = setting;
   }
}
