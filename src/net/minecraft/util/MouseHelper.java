package net.minecraft.util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class MouseHelper {
   public int deltaX;
   public int deltaY;

   public void grabMouseCursor() {
      Mouse.setGrabbed(true);
      this.deltaX = 0;
      this.deltaY = 0;
   }

   public void ungrabMouseCursor() {
      Mouse.setGrabbed(false);
      Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
   }

   public void Method893() {
      int var4 = Mouse.getDX();
      int var5 = Mouse.getDY();
      if (!Mouse.isIgnoreNextMove()) {
         this.deltaX = var4;
         this.deltaY = var5;
      }

      if (var4 != 0 || var5 != 0) {
         Mouse.setIgnoreNextMove(false);
      }
   }
}
