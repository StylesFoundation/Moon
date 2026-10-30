package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class GuiButtonLanguage extends GuiButton {
   public GuiButtonLanguage(int integer1, int integer2, int integer3) {
      super(integer1, integer2, integer3, 20, 20, "");
   }

   @Override
   public void drawButton(Minecraft minecraft, int integer2, int integer3) {
      if (this.visible) {
         minecraft.getTextureManager().bindTexture(GuiButton.buttonTextures);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         boolean var6 = integer2 >= this.xPosition
            && integer3 >= this.yPosition
            && integer2 < this.xPosition + this.width
            && integer3 < this.yPosition + this.h;
         int var7 = 106;
         if (var6) {
            var7 += this.h;
         }

         this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, var7, this.width, this.h);
      }
   }
}
