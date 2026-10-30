package com.moon.scripting.api.types.templates;

import com.moon.render.command.RenderCommand;
import java.util.HashMap;
import java.util.Map;

public class RenderTemplateConverterRegistry {
   private static final Map<Class<?>, RenderTemplateConverter<?>> CONVERTERS = new HashMap<>();

   public static RenderCommand convert(RenderTemplate renderTemplate) {
      if (renderTemplate == null) {
         return null;
      }

      RenderTemplateConverter converter = CONVERTERS.get(renderTemplate.getClass());
      return converter == null ? null : converter.convert(Method1017(renderTemplate));
   }

   private static <T> T Method1017(Object object) {
      try {
         return (T)object;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   static {
      CONVERTERS.put(GradientTemplate.class, new GradientTemplateConverter());
      CONVERTERS.put(RectTemplate.class, new RectTemplateConverter());
      CONVERTERS.put(RoundedRectTemplate.class, new RoundedRectTemplateConverter());
      CONVERTERS.put(OutlinedRoundedRectTemplate.class, new OutlinedRoundedRectTemplateConverter());
      CONVERTERS.put(TriangleTemplate.class, new TriangleTemplateConverter());
   }
}
