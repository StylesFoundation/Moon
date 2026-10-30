package com.moon.scripting.api.types.templates;

import com.moon.render.command.RenderCommand;
import com.moon.render.command.impl.shape.RectangleCommand;

public class RectTemplateConverter extends RenderTemplateConverter<RectTemplate> {
   public RenderCommand convert(RectTemplate rectTemplate) {
      return RectangleCommand.of(rectTemplate.x, rectTemplate.y, rectTemplate.width, rectTemplate.height, rectTemplate.color);
   }
}
