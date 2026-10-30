package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;

@DataClassContainer(value = "vec2f", path = "types/vec2f")
@Description("A two-dimensional float based vector representation")
public class Vec2f {
   public float x;
   public float y;

   public Vec2f(float x, float y) {
      this.x = x;
      this.y = y;
   }

   public Vec2f() {
   }

   @Description("Adds the given parameters to the vector values")
   public void add(@Parameter("x") @Description("The value added to x") float x, @Parameter("y") @Description("The value added to y") float y) {
      this.x += x;
      this.y += y;
   }

   @Override
   public String toString() {
      return "Vec2f(x=" + this.x + ", y=" + this.y + ")";
   }
}
