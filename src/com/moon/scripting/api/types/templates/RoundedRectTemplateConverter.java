package com.moon.scripting.api.types.templates;

import com.moon.render.command.RenderCommand;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;

public class RoundedRectTemplateConverter extends RenderTemplateConverter<RoundedRectTemplate> {
   public RenderCommand convert(RoundedRectTemplate roundedRectTemplate) {
      return RoundedRectangleCommand.of(
         roundedRectTemplate.x,
         roundedRectTemplate.y,
         roundedRectTemplate.width,
         roundedRectTemplate.height,
         roundedRectTemplate.radius,
         roundedRectTemplate.color
      );
   }
}
