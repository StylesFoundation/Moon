package net.optifine.gui;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class TooltipManager {
   private final GuiScreen guiScreen;
   private final TooltipProvider tooltipProvider;
   private int lastMouseX = 0;
   private int lastMouseY = 0;
   private long mouseStillTime = 0L;

   public TooltipManager(GuiScreen guiScreen, TooltipProvider tooltipProvider) {
      this.guiScreen = guiScreen;
      this.tooltipProvider = tooltipProvider;
   }

   public void drawTooltips(int integer1, int integer2, List list) {
      if (Math.abs(integer1 - this.lastMouseX) <= 5 && Math.abs(integer2 - this.lastMouseY) <= 5) {
         short var7 = 700;
         if (System.currentTimeMillis() >= this.mouseStillTime + 700L) {
            GuiButton var8 = GuiScreenOF.getSelectedButton(integer1, integer2, list);
            if (var8 != null) {
               Rectangle var9 = this.tooltipProvider.getTooltipBounds(this.guiScreen, integer1, integer2);
               String[] var10 = this.tooltipProvider.getTooltipLines(var8, var9.width);
               if (var10 != null) {
                  if (var10.length > 8) {
                     var10 = Arrays.copyOf(var10, 8);
                     var10[var10.length - 1] = var10[var10.length - 1] + " ...";
                  }

                  if (this.tooltipProvider.isRenderBorder()) {
                     int var11 = -528449408;
                     this.drawRectBorder(var9.x, var9.y, var9.x + var9.width, var9.y + var9.height, var11);
                  }

                  Gui.drawRect(var9.x, var9.y, var9.x + var9.width, var9.y + var9.height, -536870912);

                  for (int var15 = 0; var15 < var10.length; var15++) {
                     String var12 = var10[var15];
                     int var13 = 14540253;
                     if (var12.endsWith("!")) {
                        var13 = 16719904;
                     }

                     FontRenderer var14 = Minecraft.getMinecraft().fontRendererObj;
                     var14.drawStringWithShadow(var12, var9.x + 5, var9.y + 5 + var15 * 11, var13);
                  }
               }
            }
         }
      } else {
         this.lastMouseX = integer1;
         this.lastMouseY = integer2;
         this.mouseStillTime = System.currentTimeMillis();
      }
   }

   private void drawRectBorder(int integer1, int integer2, int integer3, int integer4, int integer5) {
      Gui.drawRect(integer1, integer2 - 1, integer3, integer2, integer5);
      Gui.drawRect(integer1, integer4, integer3, integer4 + 1, integer5);
      Gui.drawRect(integer1 - 1, integer2, integer1, integer4, integer5);
      Gui.drawRect(integer3, integer2, integer3 + 1, integer4, integer5);
   }
}
