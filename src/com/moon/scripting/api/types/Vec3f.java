package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "vec3f", path = "types/vec3f")
@Description("A three-dimensional float based vector representation")
public class Vec3f {
   public float x;
   public float y;
   public float z;

   public Vec3f(float x, float y, float z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3f() {
   }

   @Override
   public String toString() {
      return "Vec3f(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
   }
}
