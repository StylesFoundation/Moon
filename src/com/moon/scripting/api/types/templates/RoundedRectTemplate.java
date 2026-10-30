package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "template/rounded_rect", path = "types/template/rounded_rect")
@Description("Contains information about a rounded_rect to be drawn")
public class RoundedRectTemplate implements RenderTemplate {
   public final float x;
   public final float y;
   public final float width;
   public final float height;
   public final float radius;
   public final int color;

   public RoundedRectTemplate(float x, float y, float width, float height, float radius, int color) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.radius = radius;
      this.color = color;
   }

   @Override
   public String toString() {
      return "RoundedRectTemplate(x="
         + this.x
         + ", y="
         + this.y
         + ", width="
         + this.width
         + ", height="
         + this.height
         + ", radius="
         + this.radius
         + ", color="
         + this.color
         + ")";
   }
}
