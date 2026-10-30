package net.optifine.gui;

import java.awt.Rectangle;
import java.util.ArrayList;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.optifine.Lang;

public class TooltipProviderOptions implements TooltipProvider {
   @Override
   public Rectangle getTooltipBounds(GuiScreen guiScreen, int integer2, int integer3) {
      int var7 = guiScreen.width / 2 - 150;
      int var8 = guiScreen.height / 6 - 7;
      if (integer3 <= var8 + 98) {
         var8 += 105;
      }

      int var9 = var7 + 150 + 150;
      int var10 = var8 + 84 + 10;
      return new Rectangle(var7, var8, var9 - var7, var10 - var8);
   }

   @Override
   public boolean isRenderBorder() {
      return false;
   }

   @Override
   public String[] getTooltipLines(GuiButton guiButton, int integer) {
      return guiButton instanceof IOptionControl var6 ? getTooltipLines(var6.getOption().getEnumString()) : null;
   }

   public static String[] getTooltipLines(String string) {
      ArrayList<String> var4 = new ArrayList<String>();

      for (int var5 = 0; var5 < 10; var5++) {
         String var7 = Lang.get(string + ".tooltip." + (var5 + 1), null);
         if (var7 == null) {
            break;
         }

         var4.add(var7);
      }

      return var4.size() == 0 ? null : var4.toArray(new String[0]);
   }
}
