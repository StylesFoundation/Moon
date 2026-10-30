package com.moon.ui.clickgui.modern.component;

import com.moon.Moon;
import com.moon.module.impl.visual.Effects;
import net.minecraft.client.Minecraft;

public class ClassicComponent {
   protected static final Minecraft minecraft = Minecraft.getMinecraft();
   private float x;
   private float y;
   private float width;
   private float height;
   private float lastX;
   private float lastY;
   private float lastWidth;
   private float lastHeight;
   private boolean visible;
   private Effects effects;

   public ClassicComponent(float x, float y, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public void init() {
      this.initEffects();
   }

   public void tick() {
   }

   public void render(int mouseX, int mouseY) {
   }

   public void update(int mouseX, int mouseY) {
   }

   public void renderBackground(boolean dark) {
   }

   public void renderForeground(int mouseX, int mouseY) {
   }

   public void onMouseClick(int mouseX, int mouseY, int button) {
   }

   public void onMouseRelease(int mouseX, int mouseY, int button) {
   }

   public void onKeyTyped(char charTyped, int keyCode) {
   }

   protected boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   protected boolean isEffectsEnabled() {
      this.initEffects();
      return this.effects.isEnabled();
   }

   private void initEffects() {
      if (this.effects == null) {
         this.effects = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }
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

   public float getLastX() {
      return this.lastX;
   }

   public float getLastY() {
      return this.lastY;
   }

   public float getLastWidth() {
      return this.lastWidth;
   }

   public float getLastHeight() {
      return this.lastHeight;
   }

   public boolean isVisible() {
      return this.visible;
   }

   public Effects getEffects() {
      return this.effects;
   }

   public ClassicComponent setX(float value) {
      this.x = value;
      return this;
   }

   public ClassicComponent setY(float value) {
      this.y = value;
      return this;
   }

   public ClassicComponent setWidth(float value) {
      this.width = value;
      return this;
   }

   public ClassicComponent setHeight(float value) {
      this.height = value;
      return this;
   }

   public ClassicComponent setLastX(float value) {
      this.lastX = value;
      return this;
   }

   public ClassicComponent setLastY(float value) {
      this.lastY = value;
      return this;
   }

   public ClassicComponent setLastWidth(float value) {
      this.lastWidth = value;
      return this;
   }

   public ClassicComponent setLastHeight(float value) {
      this.lastHeight = value;
      return this;
   }

   public ClassicComponent setVisible(boolean value) {
      this.visible = value;
      return this;
   }

   public ClassicComponent setEffects(Effects value) {
      this.effects = value;
      return this;
   }
}
