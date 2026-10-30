package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "template/outlined_rounded_rect", path = "types/template/outlined_rounded_rect")
@Description("Contains information about an outlined rounded rect to be drawn")
public class OutlinedRoundedRectTemplate implements RenderTemplate {
   public final float x;
   public final float y;
   public final float width;
   public final float height;
   public final float radius;
   public final float thickness;
   public final int color;

   public OutlinedRoundedRectTemplate(float x, float y, float width, float height, float radius, float thickness, int color) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.radius = radius;
      this.thickness = thickness;
      this.color = color;
   }

   @Override
   public String toString() {
      return "OutlinedRoundedRectTemplate(x="
         + this.x
         + ", y="
         + this.y
         + ", width="
         + this.width
         + ", height="
         + this.height
         + ", radius="
         + this.radius
         + ", thickness="
         + this.thickness
         + ", color="
         + this.color
         + ")";
   }
}
