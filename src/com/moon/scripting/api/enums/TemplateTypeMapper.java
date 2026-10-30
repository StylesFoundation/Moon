package com.moon.scripting.api.enums;

import com.moon.render.RenderType;

public class TemplateTypeMapper {
   public static RenderType toRenderType(TemplateType templateType) {
      switch (templateType) {
         case RECT:
            return RenderType.RECTANGLE;
         case TEXT:
            return RenderType.MSDF;
         case GRADIENT:
            return RenderType.GRADIENT;
         case TRIANGLE:
            return RenderType.TRIANGLE;
         case ROUNDED_RECT:
            return RenderType.ROUNDED_RECTANGLE;
         case OUTLINED_ROUNDED_RECT:
            return RenderType.OUTLINE_ROUNDED_RECTANGLE;
         default:
            return RenderType.RECTANGLE;
      }
   }
}
