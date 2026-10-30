package com.moon.scripting.api.types.templates;

import com.moon.render.command.RenderCommand;
import com.moon.render.command.impl.shape.TriangleCommand;

public class TriangleTemplateConverter extends RenderTemplateConverter<TriangleTemplate> {
   public RenderCommand convert(TriangleTemplate triangleTemplate) {
      return TriangleCommand.of(
         triangleTemplate.top_x,
         triangleTemplate.top_y,
         triangleTemplate.left_x,
         triangleTemplate.left_x,
         triangleTemplate.right_x,
         triangleTemplate.right_y,
         triangleTemplate.color
      );
   }
}
