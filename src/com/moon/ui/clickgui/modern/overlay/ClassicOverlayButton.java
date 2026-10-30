package com.moon.ui.clickgui.modern.overlay;

import com.moon.Moon;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.RenderTask;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import java.util.function.Consumer;

public class ClassicOverlayButton {
   public static final ClassicOverlayButton CLOSE = new ClassicOverlayButton(
      "Close",
      ClassicOverlayButtonStyle.ERROR,
      ignored -> Moon.getInstance().getModuleManager().<ClickGUI>getModule(ClickGUI.class).getModernScreen().setOverlay(null)
   );
   private final FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM);
   private final String text;
   private final ClassicOverlayButtonStyle style;
   private final Consumer<Void> callback;
   public float x;
   public float y;
   public float width;
   public float height;

   public void measure() {
      this.width = this.fontRenderer.getTextWidth(this.text, 8.0F) + 6.0F;
      this.height = this.fontRenderer.getLineHeight(8.0F) + 6.0F;
   }

   public void render(RenderTask backgroundRenderTask, RenderTask textRenderTask, ClickGUI clickGui) {
      int backgroundColor = switch (this.style) {
         case ERROR -> -1096636;
         case SUCCESS -> -14498466;
         case DEFAULT -> clickGui.getColor().getValue();
      };
      backgroundRenderTask.addCommand(RoundedRectangleCommand.of(this.x, this.y, this.width, this.height, 6.0F, backgroundColor));
      this.fontRenderer.draw(textRenderTask, this.text, 8.0F, this.x + 3.0F, this.y + 3.5F, -1);
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

   public ClassicOverlayButtonStyle getStyle() {
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

   public ClassicOverlayButton(String text, ClassicOverlayButtonStyle style, Consumer<Void> callback) {
      this.text = text;
      this.style = style;
      this.callback = callback;
   }

   public ClassicOverlayButton setX(float x) {
      this.x = x;
      return this;
   }

   public ClassicOverlayButton setY(float y) {
      this.y = y;
      return this;
   }

   public ClassicOverlayButton setWidth(float width) {
      this.width = width;
      return this;
   }

   public ClassicOverlayButton setHeight(float height) {
      this.height = height;
      return this;
   }
}
