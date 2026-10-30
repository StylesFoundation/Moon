package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class ServerListEntryLanScan implements GuiListExtended.IGuiListEntry {
   private final Minecraft mc = Minecraft.getMinecraft();

   @Override
   public void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8) {
      int var11 = integer3 + integer5 / 2 - this.mc.fontRendererObj.FONT_HEIGHT / 2;
      this.mc
         .fontRendererObj
         .drawString(
            I18n.format("lanServer.scanning"),
            this.mc.currentScreen.width / 2 - this.mc.fontRendererObj.getStringWidth(I18n.format("lanServer.scanning")) / 2,
            var11,
            16777215
         );

      String var12 = switch ((int)(Minecraft.getSystemTime() / 300L % 4L)) {
         default -> "O o o";
         case 1, 3 -> "o O o";
         case 2 -> "o o O";
      };
      this.mc
         .fontRendererObj
         .drawString(
            var12, this.mc.currentScreen.width / 2 - this.mc.fontRendererObj.getStringWidth(var12) / 2, var11 + this.mc.fontRendererObj.FONT_HEIGHT, 8421504
         );
   }

   @Override
   public void setSelected(int integer1, int integer2, int integer3) {
   }

   @Override
   public boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      return false;
   }

   @Override
   public void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
   }
}
