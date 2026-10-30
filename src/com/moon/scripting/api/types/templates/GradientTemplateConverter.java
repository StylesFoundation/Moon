package com.moon.scripting.api.types.templates;

import com.moon.render.command.RenderCommand;
import com.moon.render.command.impl.shape.GradientCommand;

public class GradientTemplateConverter extends RenderTemplateConverter<GradientTemplate> {
   public RenderCommand convert(GradientTemplate gradientTemplate) {
      return GradientCommand.of(
         gradientTemplate.x,
         gradientTemplate.y,
         gradientTemplate.width,
         gradientTemplate.height,
         gradientTemplate.from,
         gradientTemplate.to,
         gradientTemplate.vertical
      );
   }
}
