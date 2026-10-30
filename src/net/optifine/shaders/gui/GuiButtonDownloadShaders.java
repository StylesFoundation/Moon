package net.optifine.shaders.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiButtonDownloadShaders extends GuiButton {
   public GuiButtonDownloadShaders(int integer1, int integer2, int integer3) {
      super(integer1, integer2, integer3, 22, 20, "");
   }

   @Override
   public void drawButton(Minecraft minecraft, int integer2, int integer3) {
      if (this.visible) {
         super.drawButton(minecraft, integer2, integer3);
         ResourceLocation var7 = new ResourceLocation("optifine/textures/icons.png");
         minecraft.getTextureManager().bindTexture(var7);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.drawTexturedModalRect(this.xPosition + 3, this.yPosition + 2, 0, 0, 16, 16);
      }
   }
}
