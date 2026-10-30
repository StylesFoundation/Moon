package com.moon.ui.clickgui.classic.overlay;

import com.moon.ui.theme.ColorScheme;

public class OverlayContent {
   protected float x;
   protected float y;
   protected float width;
   protected float height;
   protected ColorScheme colorScheme;

   public void init() {
   }

   public void render(int mouseX, int mouseY) {
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
   }

   public void mouseReleased(int mouseX, int mouseY, int button) {
   }

   public void charTyped(char charTyped, int keyCode) {
   }

   public OverlayContent setX(float x) {
      this.x = x;
      return this;
   }

   public OverlayContent setY(float y) {
      this.y = y;
      return this;
   }

   public OverlayContent setWidth(float width) {
      this.width = width;
      return this;
   }

   public OverlayContent setHeight(float height) {
      this.height = height;
      return this;
   }

   public OverlayContent setColorScheme(ColorScheme colorScheme) {
      this.colorScheme = colorScheme;
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

   public ColorScheme getColorScheme() {
      return this.colorScheme;
   }

   public static int getDefaultHeight() {
      return 95;
   }

   static {
      if (getDefaultHeight() == 0) {
      }
   }
}
