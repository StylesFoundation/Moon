package com.moon.ui.clickgui.modern.overlay;

import com.moon.ui.clickgui.modern.ModernClickGui;

public class ClassicOverlayContent {
   protected float x;
   protected float y;
   protected float width;
   protected float height;
   protected ModernClickGui screen;

   public void init() {
   }

   public void render(int mouseX, int mouseY) {
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
   }

   public void mouseReleased(int mouseX, int mouseY, int button) {
   }

   public void keyTyped(char charTyped, int keyCode) {
   }

   public ClassicOverlayContent setX(float x) {
      this.x = x;
      return this;
   }

   public ClassicOverlayContent setY(float y) {
      this.y = y;
      return this;
   }

   public ClassicOverlayContent setWidth(float width) {
      this.width = width;
      return this;
   }

   public ClassicOverlayContent setHeight(float height) {
      this.height = height;
      return this;
   }

   public ClassicOverlayContent setScreen(ModernClickGui screen) {
      this.screen = screen;
      return this;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }

   public ModernClickGui getScreen() {
      return this.screen;
   }

   public static int getEmptyValue() {
      return 0;
   }
}
