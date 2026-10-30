package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class GuiListButton extends GuiButton {
   private boolean field_175216_o;
   private final String localizationStr;
   private final GuiPageButtonList.GuiResponder guiResponder;

   public GuiListButton(GuiPageButtonList.GuiResponder guiResponder, int integer2, int integer3, int integer4, String string, boolean boolean6) {
      super(integer2, integer3, integer4, 150, 20, "");
      this.localizationStr = string;
      this.field_175216_o = boolean6;
      this.displayString = this.buildDisplayString();
      this.guiResponder = guiResponder;
   }

   private String buildDisplayString() {
      return I18n.format(this.localizationStr) + ": " + (this.field_175216_o ? I18n.format("gui.yes") : I18n.format("gui.no"));
   }

   public void func_175212_b(boolean boolean1) {
      this.field_175216_o = boolean1;
      this.displayString = this.buildDisplayString();
      this.guiResponder.func_175321_a(this.l, boolean1);
   }

   @Override
   public boolean mousePressed(Minecraft minecraft, int integer2, int integer3) {
      if (super.mousePressed(minecraft, integer2, integer3)) {
         this.field_175216_o = !this.field_175216_o;
         this.displayString = this.buildDisplayString();
         this.guiResponder.func_175321_a(this.l, this.field_175216_o);
         return true;
      } else {
         return false;
      }
   }
}
