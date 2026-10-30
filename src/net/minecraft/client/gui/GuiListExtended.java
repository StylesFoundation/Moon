package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;

public abstract class GuiListExtended extends GuiSlot {
   public GuiListExtended(Minecraft minecraft, int integer2, int integer3, int integer4, int integer5, int integer6) {
      super(minecraft, integer2, integer3, integer4, integer5, integer6);
   }

   @Override
   protected void elementClicked(int index, boolean doubleClick, int mouseX, int mouseY) {
   }

   @Override
   protected boolean isSelected(int index) {
      return false;
   }

   @Override
   protected void drawBackground() {
   }

   @Override
   protected void drawSlot(int index, int left, int top, int right, int bottom, int slotHeight) {
      this.getListEntry(index)
         .drawEntry(index, left, top, this.getListWidth(), right, bottom, slotHeight, this.getSlotIndexFromScreenCoords(bottom, slotHeight) == index);
   }

   @Override
   protected void func_178040_a(int integer1, int integer2, int integer3) {
      this.getListEntry(integer1).setSelected(integer1, integer2, integer3);
   }

   public boolean mouseClicked(int integer1, int integer2, int integer3) {
      if (this.isMouseYWithinSlotBounds(integer2)) {
         int var4 = this.getSlotIndexFromScreenCoords(integer1, integer2);
         if (var4 >= 0) {
            int var5 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
            int var6 = this.top + 4 - this.getAmountScrolled() + var4 * this.slotHeight + this.headerPadding;
            int var7 = integer1 - var5;
            int var8 = integer2 - var6;
            if (this.getListEntry(var4).mousePressed(var4, integer1, integer2, integer3, var7, var8)) {
               this.setEnabled(false);
               return true;
            }
         }
      }

      return false;
   }

   public boolean mouseReleased(int integer1, int integer2, int integer3) {
      for (int var4 = 0; var4 < this.getSize(); var4++) {
         int var5 = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
         int var6 = this.top + 4 - this.getAmountScrolled() + var4 * this.slotHeight + this.headerPadding;
         int var7 = integer1 - var5;
         int var8 = integer2 - var6;
         this.getListEntry(var4).mouseReleased(var4, integer1, integer2, integer3, var7, var8);
      }

      this.setEnabled(true);
      return false;
   }

   public abstract GuiListExtended.IGuiListEntry getListEntry(int integer);

   public interface IGuiListEntry {
      void setSelected(int integer1, int integer2, int integer3);

      void drawEntry(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, boolean boolean8);

      boolean mousePressed(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6);

      void mouseReleased(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6);
   }
}
