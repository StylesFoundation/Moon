package com.moon.ui.clickgui.imgui;

import com.moon.module.LabelEnum;
import com.moon.module.setting.impl.EnumSetting;
import imgui.ImGui;
import imgui.type.ImInt;
import java.util.ArrayList;

public class ImGuiEnumWidget implements ImGuiWidget {
   private final EnumSetting setting;
   private final ImInt currentIndex = new ImInt();
   private String[] options;
   private long id;

   @Override
   public void setup() {
      ArrayList<String> labels = new ArrayList<>();

      for (Enum value : this.setting.getValues()) {
         if (value instanceof LabelEnum labelEnum) {
            labels.add(labelEnum.label());
         }
      }

      this.options = labels.toArray(new String[0]);
      this.id = Math.round(Math.random() * 1.0E9);
   }

   @Override
   public void render() {
      if (this.setting.isVisible()) {
         ImGui.pushID(this.id);
         int ordinal = ((Enum)this.setting.getValue()).ordinal();
         this.currentIndex.set(ordinal);
         ImGui.combo(this.setting.getDisplayName(), this.currentIndex, this.options);
         if (ImGui.isItemEdited()) {
            this.setting.setValue(this.setting.getValues()[this.currentIndex.get()]);
         }

         ImGui.popID();
      }
   }

   public ImGuiEnumWidget(EnumSetting setting) {
      this.setting = setting;
   }
}
