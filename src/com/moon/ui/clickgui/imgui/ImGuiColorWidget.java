package com.moon.ui.clickgui.imgui;

import com.moon.module.setting.impl.ColorSetting;
import imgui.ImGui;
import java.awt.Color;

public class ImGuiColorWidget implements ImGuiWidget {
   private final ColorSetting setting;
   private final float[] color = new float[4];
   private long id;

   @Override
   public void setup() {
      Color currentColor = new Color(this.setting.getValue());
      this.color[0] = currentColor.getRed() / 255.0F;
      this.color[1] = currentColor.getGreen() / 255.0F;
      this.color[2] = currentColor.getBlue() / 255.0F;
      this.color[3] = currentColor.getAlpha() / 255.0F;
      this.id = Math.round(Math.random() * 1.0E9);
   }

   @Override
   public void render() {
      if (this.setting.isVisible()) {
         ImGui.pushID(this.id);
         Color currentColor = new Color(this.setting.getValue());
         this.color[0] = currentColor.getRed() / 255.0F;
         this.color[1] = currentColor.getGreen() / 255.0F;
         this.color[2] = currentColor.getBlue() / 255.0F;
         this.color[3] = currentColor.getAlpha() / 255.0F;
         ImGui.colorEdit4(this.setting.getDisplayName(), this.color, 2);
         if (ImGui.isItemEdited()) {
            this.setting.setValue(new Color(this.color[0], this.color[1], this.color[2], this.color[3]).getRGB());
         }

         ImGui.popID();
      }
   }

   public ImGuiColorWidget(ColorSetting setting) {
      this.setting = setting;
   }
}
