package com.moon.ui.clickgui.classic.component;

import com.moon.Moon;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.theme.ColorScheme;

public class Button {
   private final float x;
   private final float y;
   private final float width;
   private final float height;
   private String text;
   private boolean disabled;
   private ColorScheme colorScheme;

   public void render(int mouseX, int mouseY) {
      FontRenderer font = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
      boolean hovered = this.isMouseOver(mouseX, mouseY, this.x, this.y, this.width, this.height);
      RenderTask rectTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      rectTask.addCommand(
         RoundedRectangleCommand.of(
            this.x,
            this.y,
            this.width,
            this.height,
            this.height / 2.0F - 1.0F,
            !hovered && !this.disabled ? this.colorScheme.getPrimary() : new RenderColor(this.colorScheme.getPrimary()).withAlphaFraction(0.6F).getRGB()
         )
      );
      rectTask.render();
      RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(font).build();
      font.draw(
         textTask,
         this.text,
         8.25F,
         this.x + this.width / 2.0F - font.getTextWidth(this.text, 8.25F) / 2.0F,
         this.y + this.height / 2.0F - font.getLineHeight(8.25F) / 2.0F + 0.5F,
         this.disabled ? new RenderColor(this.colorScheme.getOnPrimary()).withAlphaFraction(0.6F).getRGB() : this.colorScheme.getOnPrimary(),
         false
      );
      textTask.render();
   }

   public boolean isClicked(int mouseX, int mouseY, int button) {
      return this.isMouseOver(mouseX, mouseY, this.x, this.y, this.width, this.height) && button == 0;
   }

   protected boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   public Button(float x, float y, float width, float height, String text, boolean disabled, ColorScheme colorScheme) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.text = text;
      this.disabled = disabled;
      this.colorScheme = colorScheme;
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

   public Button setText(String text) {
      this.text = text;
      return this;
   }

   public Button setDisabled(boolean value) {
      this.disabled = value;
      return this;
   }

   public Button setColorScheme(ColorScheme colorScheme) {
      this.colorScheme = colorScheme;
      return this;
   }
}
