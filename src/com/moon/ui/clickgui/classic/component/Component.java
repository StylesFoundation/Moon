package com.moon.ui.clickgui.classic.component;

import com.moon.Moon;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.theme.ColorScheme;

public class Component {
   protected FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_SEMI_BOLD);
   protected ClickGUI clickGui;
   protected float x;
   protected float y;
   protected float width;
   protected float height;
   private boolean dragging;
   private float dragOffsetX;
   private float dragOffsetY;
   protected ColorScheme colorScheme;

   public void init() {
      this.ensureClickGuiReference();
      this.colorScheme = ColorScheme.darkScheme(this.clickGui.getColor().getValue());
   }

   public void updateColorScheme() {
      this.colorScheme = ColorScheme.darkScheme(this.clickGui.getColor().getValue());
   }

   public void render(int mouseX, int mouseY) {
      if (this.dragging) {
         this.x = this.dragOffsetX + mouseX;
         this.y = this.dragOffsetY + mouseY;
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
   }

   public void mouseReleased(int mouseX, int mouseY, int button) {
   }

   public void keyTyped(char charTyped, int keyCode) {
   }

   protected boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   public void onEffectRender(EffectRenderEvent event, float partialTicks) {
   }

   public void update() {
   }

   protected void ensureClickGuiReference() {
      if (this.clickGui == null) {
         this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
         this.colorScheme = ColorScheme.darkScheme(this.clickGui.getColor().getValue());
      }
   }

   public FontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   public ClickGUI getClickGui() {
      return this.clickGui;
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

   public boolean isDragging() {
      return this.dragging;
   }

   public float getDragOffsetX() {
      return this.dragOffsetX;
   }

   public float getDragOffsetY() {
      return this.dragOffsetY;
   }

   public ColorScheme getColorScheme() {
      return this.colorScheme;
   }

   public Component setFontRenderer(FontRenderer fontRenderer) {
      this.fontRenderer = fontRenderer;
      return this;
   }

   public Component setClickGui(ClickGUI clickGui) {
      this.clickGui = clickGui;
      return this;
   }

   public Component setX(float value) {
      this.x = value;
      return this;
   }

   public Component setY(float value) {
      this.y = value;
      return this;
   }

   public Component setWidth(float value) {
      this.width = value;
      return this;
   }

   public Component setHeight(float value) {
      this.height = value;
      return this;
   }

   public Component setDragging(boolean value) {
      this.dragging = value;
      return this;
   }

   public Component setDragOffsetX(float value) {
      this.dragOffsetX = value;
      return this;
   }

   public Component setDragOffsetY(float value) {
      this.dragOffsetY = value;
      return this;
   }

   public Component setColorScheme(ColorScheme colorScheme) {
      this.colorScheme = colorScheme;
      return this;
   }

   public static boolean alwaysTrue() {
      return true;
   }

   static {
      if (!alwaysTrue()) {
      }
   }
}
