package com.moon.scripting.api.types.templates;

import com.moon.scripting.api.enums.CustomFontType;
import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "template/text", path = "types/template/text")
@Description("Contains information about a text to be drawn")
public class TextTemplate implements RenderTemplate {
   public final CustomFontType font_type;
   public final String text;
   public final float size;
   public final float x;
   public final float y;
   public final int color;
   public final boolean shadow;

   public TextTemplate(CustomFontType font_type, String text, float size, float x, float y, int color, boolean shadow) {
      this.font_type = font_type;
      this.text = text;
      this.size = size;
      this.x = x;
      this.y = y;
      this.color = color;
      this.shadow = shadow;
   }

   @Override
   public String toString() {
      return "TextTemplate(font_type="
         + this.font_type
         + ", text="
         + this.text
         + ", size="
         + this.size
         + ", x="
         + this.x
         + ", y="
         + this.y
         + ", color="
         + this.color
         + ", shadow="
         + this.shadow
         + ")";
   }
}
