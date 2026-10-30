package net.optifine.gui;

import java.awt.Rectangle;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.gui.GuiButtonDownloadShaders;
import net.optifine.shaders.gui.GuiButtonEnumShaderOption;

public class TooltipProviderEnumShaderOptions implements TooltipProvider {
   @Override
   public Rectangle getTooltipBounds(GuiScreen guiScreen, int integer2, int integer3) {
      int var7 = guiScreen.width - 450;
      byte var8 = 35;
      if (var7 < 10) {
         var7 = 10;
      }

      if (integer3 <= 129) {
         var8 += 100;
      }

      int var9 = var7 + 150 + 150;
      int var10 = var8 + 84 + 10;
      return new Rectangle(var7, var8, var9 - var7, var10 - var8);
   }

   @Override
   public boolean isRenderBorder() {
      return true;
   }

   @Override
   public String[] getTooltipLines(GuiButton guiButton, int integer) {
      if (guiButton instanceof GuiButtonDownloadShaders) {
         return TooltipProviderOptions.getTooltipLines("of.options.shaders.DOWNLOAD");
      } else if (guiButton instanceof GuiButtonEnumShaderOption var6) {
         EnumShaderOption var7 = var6.getEnumShaderOption();
         return this.getTooltipLines(var7);
      } else {
         return null;
      }
   }

   private String[] getTooltipLines(EnumShaderOption enumShaderOption) {
      return TooltipProviderOptions.getTooltipLines(enumShaderOption.getResourceKey());
   }
}
