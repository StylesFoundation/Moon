package com.moon.module.impl.visual.hud.base;

import com.moon.module.impl.visual.HUD;
import com.moon.render.RenderTask;
import com.moon.util.render.GradientUtil;
import java.awt.Color;
import net.minecraft.client.Minecraft;

public interface HudElement {
   Minecraft minecraft = Minecraft.getMinecraft();

   void setRectangleTask(RenderTask task);

   void setRoundedTask(RenderTask task);

   void setBackgroundTask(RenderTask task);

   void setTextTask(RenderTask task);

   void renderPost();

   default int computeColor(float offset, HUD hud, ColorMode colorMode, int colorOne, int colorTwo) {
      return this.computeColor(offset, 4500.0, hud, colorMode, colorOne, colorTwo);
   }

   default int computeColor(float offset, double period, HUD hud, ColorMode colorMode, int colorOne, int colorTwo) {
      int modeIndex = colorMode.ordinal();
      if (colorMode == ColorMode.SYNC) {
         colorOne = hud.getAlColorOne().getValue();
         colorTwo = hud.getAlColorTwo().getValue();
         modeIndex = hud.getTextColor().getValue().ordinal() + 1;
      }

      long currentTime = System.currentTimeMillis();
      int resultColor;
      switch (modeIndex) {
         case 2: {
            long cycleTime = (long)(1.5 * period);
            int[] colors = new int[]{colorOne, colorTwo, colorOne, colorTwo, colorOne, colorTwo, colorOne};
            resultColor = GradientUtil.getColorAt(colors, (float)((currentTime + (int)offset * -100L) % cycleTime) / ((float)cycleTime / 2.0F));
            break;
         }
         case 3: {
            long cycleTime = (long)(1.5 * period);
            int darkerColor = new Color(colorOne).darker().darker().getRGB();
            int[] colors = new int[]{colorOne, darkerColor, colorOne, darkerColor, colorOne, darkerColor, colorOne};
            resultColor = GradientUtil.getColorAt(colors, (float)((currentTime + (int)offset * -100L) % cycleTime) / ((float)cycleTime / 2.0F));
            break;
         }
         default:
            return colorOne;
      }

      return resultColor;
   }
}
