package com.moon.scripting.api.types.templates;

import com.moon.render.command.RenderCommand;
import com.moon.render.command.impl.shape.OutlineRoundedRectangleCommand;

public class OutlinedRoundedRectTemplateConverter extends RenderTemplateConverter<OutlinedRoundedRectTemplate> {
   public RenderCommand convert(OutlinedRoundedRectTemplate outlinedRoundedRectTemplate) {
      return OutlineRoundedRectangleCommand.of(
         outlinedRoundedRectTemplate.x,
         outlinedRoundedRectTemplate.y,
         outlinedRoundedRectTemplate.width,
         outlinedRoundedRectTemplate.height,
         outlinedRoundedRectTemplate.radius,
         outlinedRoundedRectTemplate.thickness,
         outlinedRoundedRectTemplate.color
      );
   }
}
