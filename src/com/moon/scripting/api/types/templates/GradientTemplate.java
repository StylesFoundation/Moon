package com.moon.scripting.api.types.templates;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "template/gradient", path = "types/template/gradient")
@Description("Contains information about a gradient to be drawn")
public class GradientTemplate implements RenderTemplate {
   public final float x;
   public final float y;
   public final float width;
   public final float height;
   public final int from;
   public final int to;
   public final boolean vertical;

   public GradientTemplate(float x, float y, float width, float height, int from, int to, boolean vertical) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.from = from;
      this.to = to;
      this.vertical = vertical;
   }

   @Override
   public String toString() {
      return "GradientTemplate(x="
         + this.x
         + ", y="
         + this.y
         + ", width="
         + this.width
         + ", height="
         + this.height
         + ", from="
         + this.from
         + ", to="
         + this.to
         + ", vertical="
         + this.vertical
         + ")";
   }
}
