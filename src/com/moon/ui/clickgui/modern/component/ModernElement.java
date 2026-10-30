package com.moon.ui.clickgui.modern.component;

import com.moon.Moon;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.ui.clickgui.modern.render.ClickGuiRenderContext;
import net.minecraft.client.Minecraft;

public class ModernElement {
   protected static final Minecraft minecraft = Minecraft.getMinecraft();
   private float x;
   private float y;
   private float width;
   private float height;
   private ClickGUI clickGui;

   public ModernElement(float x, float y, float width, float height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }

   public void init() {
      this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
   }

   public void tick() {
   }

   public void setVisible(boolean value) {
   }

   public void render(int mouseX, int mouseY) {
   }

   public void update(int mouseX, int mouseY) {
   }

   public void renderForeground(int mouseX, int mouseY) {
   }

   public void onMouseClick(int mouseX, int mouseY, int button) {
   }

   public void onMouseRelease(int mouseX, int mouseY, int button) {
   }

   public void onKeyTyped(char charTyped, int keyCode) {
   }

   protected boolean isAnimating() {
      return ClickGuiRenderContext.animating;
   }

   protected boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
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

   public ClickGUI getClickGui() {
      return this.clickGui;
   }

   public ModernElement setX(float value) {
      this.x = value;
      return this;
   }

   public ModernElement setY(float value) {
      this.y = value;
      return this;
   }

   public ModernElement setWidth(float value) {
      this.width = value;
      return this;
   }

   public ModernElement setHeight(float value) {
      this.height = value;
      return this;
   }

   public ModernElement setClickGui(ClickGUI value) {
      this.clickGui = value;
      return this;
   }
}
