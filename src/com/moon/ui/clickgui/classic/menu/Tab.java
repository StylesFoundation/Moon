package com.moon.ui.clickgui.classic.menu;

import com.moon.render.state.impl.StencilClipState;
import com.moon.ui.theme.ColorScheme;

public class Tab {
   private final String name;
   private final int index;
   private float x;
   private float y;
   private float width;
   private float height;
   private float clipX;
   private float clipY;
   private float clipWidth;
   private float clipHeight;

   public void render(ColorScheme colorScheme, int mouseX, int mouseY) {
   }

   public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
   }

   protected StencilClipState getClipState() {
      return StencilClipState.of(this.clipX, this.clipY, this.clipWidth, this.clipHeight);
   }

   public String getName() {
      return this.name;
   }

   public int getIndex() {
      return this.index;
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

   public float getClipX() {
      return this.clipX;
   }

   public float getClipY() {
      return this.clipY;
   }

   public float getClipWidth() {
      return this.clipWidth;
   }

   public float getClipHeight() {
      return this.clipHeight;
   }

   public Tab(String name, int index) {
      this.name = name;
      this.index = index;
   }

   public Tab setX(float value) {
      this.x = value;
      return this;
   }

   public Tab setY(float value) {
      this.y = value;
      return this;
   }

   public Tab setWidth(float value) {
      this.width = value;
      return this;
   }

   public Tab setHeight(float value) {
      this.height = value;
      return this;
   }

   public Tab setClipX(float value) {
      this.clipX = value;
      return this;
   }

   public Tab setClipY(float value) {
      this.clipY = value;
      return this;
   }

   public Tab setClipWidth(float value) {
      this.clipWidth = value;
      return this;
   }

   public Tab setClipHeight(float value) {
      this.clipHeight = value;
      return this;
   }
}
