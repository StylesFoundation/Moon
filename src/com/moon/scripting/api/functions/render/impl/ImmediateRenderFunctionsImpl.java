package com.moon.scripting.api.functions.render.impl;

import com.moon.Moon;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.GradientCommand;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;
import com.moon.render.command.impl.shape.RectangleCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.shape.TriangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.scripting.api.enums.CustomFontType;
import com.moon.scripting.api.enums.CustomFontTypeMapper;
import com.moon.scripting.api.functions.render.ImmediateRenderFunctions;
import net.minecraft.client.Minecraft;

public class ImmediateRenderFunctionsImpl implements ImmediateRenderFunctions {
   @Override
   public void rect(float x, float y, float width, float height, int color) {
      RenderTask task = RenderTask.builder().withRenderType(RenderType.RECTANGLE).build();
      task.addCommand(RectangleCommand.of(x, y, width, height, color));
      task.render();
   }

   @Override
   public void gradient_rect(float x, float y, float width, float height, int from, int to, boolean vertical) {
      RenderTask task = RenderTask.builder().withRenderType(RenderType.GRADIENT).build();
      task.addCommand(GradientCommand.of(x, y, width, height, from, to, vertical));
      task.render();
   }

   @Override
   public void rounded_rect(float x, float y, float width, float height, float radius, int color) {
      RenderTask task = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).build();
      task.addCommand(RoundedRectangleCommand.of(x, y, width, height, radius, color));
      task.render();
   }

   @Override
   public void outline_rounded_rect(float x, float y, float width, float height, float radius, float thickness, int color) {
      RenderTask task = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).build();
      task.addCommand(OutlineRoundedRectangleCommand.of(x, y, width, height, radius, thickness, color));
      task.render();
   }

   @Override
   public void triangle(float topX, float topY, float leftX, float leftY, float rightX, float rightY, int color) {
      RenderTask task = RenderTask.builder().withRenderType(RenderType.TRIANGLE).build();
      task.addCommand(TriangleCommand.of(topX, topY, leftX, leftY, rightX, rightY, color));
      task.render();
   }

   @Override
   public void minecraft_text(String text, float x, float y, int color, boolean shadow) {
      if (shadow) {
         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x, y, color);
      } else {
         Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color);
      }
   }

   @Override
   public void text(CustomFontType customFontType, String text, float size, float x, float y, int color, boolean shadow) {
      FontType fontType = CustomFontTypeMapper.toFontType(customFontType);
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(fontType);
      RenderTask task = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).build();
      if (shadow) {
         fontRenderer.drawWithShadow(task, text, size, x, y, color);
      } else {
         fontRenderer.draw(task, text, size, x, y, color);
      }

      task.render();
   }
}
