package com.moon.ui.clickgui.modern.tooltip;

import com.moon.Moon;
import com.moon.handlers.ScaleHandler;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Tooltip {
   private final String title;
   private ClickGUI clickGui;

   public void render(int mouseX, int mouseY) {
      if (this.clickGui == null) {
         this.clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
      }

      if (this.isVisible()) {
         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
         float width = fontRenderer.getTextWidth(this.title, 7.25F) + 7.0F;
         float height = fontRenderer.getTextHeight(this.title, 7.25F) + 5.0F;
         RenderTask backgroundTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
         RenderTask outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
         RenderTask textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
         boolean overflowRight = mouseX + 8.0F + width > scaledResolution.Method237();
         float x = overflowRight ? mouseX - 8.0F - width : mouseX + 8.0F;
         backgroundTask.addCommand(RoundedRectangleCommand.of(x, mouseY - 2, width, height, 4.0F, -869191375));
         outlineTask.addCommand(
            OutlineRoundedRectangleCommand.of(x - 1.0F, mouseY - 3.0F, width + 2.0F, height + 2.0F, 4.0F, 0.5F, this.clickGui.getColor().getValue())
         );
         fontRenderer.draw(textTask, this.title, 7.25F, x + 3.5F, mouseY - 1.5F + 2.5F, -1);
         backgroundTask.render();
         outlineTask.render();
         textTask.render();
      }
   }

   public abstract boolean isVisible();

   public Tooltip(String title) {
      this.title = title;
   }
}
