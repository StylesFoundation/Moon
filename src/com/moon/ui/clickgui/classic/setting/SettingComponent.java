package com.moon.ui.clickgui.classic.setting;

import com.moon.Moon;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.clickgui.AnimatedValue;
import com.moon.ui.theme.ColorScheme;

public class SettingComponent {
   protected FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
   protected ClickGUI clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
   private final AnimatedValue yAnimation = new AnimatedValue();
   protected final AnimatedValue expandAnimation = new AnimatedValue();
   protected float x;
   protected float y;
   protected float width;
   protected float height;
   protected float targetHeight;
   protected float padding = 3.0F;
   protected float clipX;
   protected float clipY;
   protected float clipWidth;
   protected float clipHeight;
   protected ColorScheme colorScheme;
   private boolean active;

   public void init() {
      this.initReferences();
      this.colorScheme = ColorScheme.darkScheme(this.clickGui.getColor().getValue());
      this.active = this.isActive();
      this.targetHeight = this.height;
      this.expandAnimation.set(this.isActive() ? 1.0F : 0.0F);
      this.yAnimation.set(this.y);
   }

   public void updateColorScheme() {
      this.colorScheme = ColorScheme.darkScheme(this.clickGui.getColor().getValue());
   }

   public void render(int mouseX, int mouseY) {
      this.yAnimation.animateTo(this.y, 0.3F);
      if (this.shouldAnimate()) {
         this.expandAnimation.animateTo(this.isActive() ? 1.0F : 0.0F, 0.3F);
         this.height = this.targetHeight * this.expandAnimation.get();
      }
   }

   public void renderOverlay(int mouseX, int mouseY) {
   }

   public boolean onMouseClick(int mouseX, int mouseY, int button) {
      return false;
   }

   public void onMouseRelease(int mouseX, int mouseY, int button) {
   }

   public void onKeyTyped(char charTyped, int keyCode) {
   }

   protected boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   public float getAnimatedY() {
      return this.yAnimation.get();
   }

   public boolean isActive() {
      return false;
   }

   public boolean shouldAnimate() {
      return true;
   }

   protected void initReferences() {
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

   public AnimatedValue getYAnimation() {
      return this.yAnimation;
   }

   public AnimatedValue getExpandAnimation() {
      return this.expandAnimation;
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

   public float getTargetHeight() {
      return this.targetHeight;
   }

   public float getPadding() {
      return this.padding;
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

   public ColorScheme getColorScheme() {
      return this.colorScheme;
   }

   public boolean isActiveCached() {
      return this.active;
   }

   public SettingComponent setFontRenderer(FontRenderer fontRenderer) {
      this.fontRenderer = fontRenderer;
      return this;
   }

   public SettingComponent setClickGui(ClickGUI clickGui) {
      this.clickGui = clickGui;
      return this;
   }

   public SettingComponent setX(float x) {
      this.x = x;
      return this;
   }

   public SettingComponent setY(float y) {
      this.y = y;
      return this;
   }

   public SettingComponent setWidth(float width) {
      this.width = width;
      return this;
   }

   public SettingComponent setHeight(float height) {
      this.height = height;
      return this;
   }

   public SettingComponent setTargetHeight(float targetHeight) {
      this.targetHeight = targetHeight;
      return this;
   }

   public SettingComponent setClipX(float clipX) {
      this.clipX = clipX;
      return this;
   }

   public SettingComponent setClipY(float clipY) {
      this.clipY = clipY;
      return this;
   }

   public SettingComponent setClipWidth(float clipWidth) {
      this.clipWidth = clipWidth;
      return this;
   }

   public SettingComponent setClipHeight(float clipHeight) {
      this.clipHeight = clipHeight;
      return this;
   }

   public SettingComponent setColorScheme(ColorScheme colorScheme) {
      this.colorScheme = colorScheme;
      return this;
   }

   public SettingComponent setActive(boolean active) {
      this.active = active;
      return this;
   }

   public SettingComponent setPadding(float padding) {
      this.padding = padding;
      return this;
   }

   public static int getDefaultWidth() {
      return 116;
   }
}
