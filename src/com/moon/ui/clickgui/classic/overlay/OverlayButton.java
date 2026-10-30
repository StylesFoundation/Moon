package com.moon.ui.clickgui.classic.overlay;

import com.moon.Moon;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.RenderTask;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.ui.theme.ColorScheme;
import java.util.function.Consumer;

public class OverlayButton {
   public static final OverlayButton CLOSE = new OverlayButton(
      "Close",
      OverlayButtonStyle.ERROR,
      ignored -> Moon.getInstance().getModuleManager().<ClickGUI>getModule(ClickGUI.class).getClassicScreen().setOverlay(null)
   );
   private final FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
   private final String text;
   private final OverlayButtonStyle style;
   private final Consumer<Void> callback;
   public float x;
   public float y;
   public float width;
   public float height;

   public void measure() {
      this.width = this.fontRenderer.getTextWidth(this.text, 8.0F) + 6.0F;
      this.height = this.fontRenderer.getLineHeight(8.0F) + 6.0F;
   }

   public void render(RenderTask backgroundTask, RenderTask textTask, ColorScheme colorScheme) {
      int backgroundColor = switch (this.style) {
         case ERROR -> colorScheme.getError();
         case SUCCESS -> colorScheme.getSecondary();
         case NORMAL -> colorScheme.getPrimary();
      };

      int textColor = switch (this.style) {
         case ERROR -> colorScheme.getOnError();
         case SUCCESS -> colorScheme.getOnSecondary();
         case NORMAL -> colorScheme.getOnPrimary();
      };
      backgroundTask.addCommand(RoundedRectangleCommand.of(this.x, this.y, this.width, this.height, 3.0F, backgroundColor));
      this.fontRenderer.draw(textTask, this.text, 8.0F, this.x + 3.0F, this.y + 3.5F, textColor);
   }

   public boolean isHovered(float mouseX, float mouseY) {
      return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
   }

   public FontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   public String getText() {
      return this.text;
   }

   public OverlayButtonStyle getStyle() {
      return this.style;
   }

   public Consumer<Void> getCallback() {
      return this.callback;
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

   public OverlayButton(String text, OverlayButtonStyle style, Consumer<Void> callback) {
      this.text = text;
      this.style = style;
      this.callback = callback;
   }

   public OverlayButton setX(float x) {
      this.x = x;
      return this;
   }

   public OverlayButton setY(float y) {
      this.y = y;
      return this;
   }

   public OverlayButton setWidth(float width) {
      this.width = width;
      return this;
   }

   public OverlayButton setHeight(float height) {
      this.height = height;
      return this;
   }
}
