package net.optifine.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;

public class GuiScreenOF extends GuiScreen {
   protected void actionPerformedRightClick(GuiButton guiButton) throws IOException {
   }

   @Override
   protected void onMouseClicked(int mouseX, int mouseY, int button) throws IOException {
      super.onMouseClicked(mouseX, mouseY, button);
      if (button == 1) {
         GuiButton var7 = getSelectedButton(mouseX, mouseY, this.m);
         if (var7 != null && var7.enabled) {
            var7.playPressSound(this.mc.getSoundHandler());
            this.actionPerformedRightClick(var7);
         }
      }
   }

   public static GuiButton getSelectedButton(int integer1, int integer2, List<GuiButton> list) {
      for (GuiButton var7 : list) {
         if (var7.visible) {
            int var8 = GuiVideoSettings.getButtonWidth(var7);
            int var9 = GuiVideoSettings.getButtonHeight(var7);
            if (integer1 >= var7.xPosition && integer2 >= var7.yPosition && integer1 < var7.xPosition + var8 && integer2 < var7.yPosition + var9) {
               return var7;
            }
         }
      }

      return null;
   }
}
