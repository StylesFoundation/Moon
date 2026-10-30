package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "vec3i", path = "types/vec3i")
@Description("A three-dimensional integer based vector representation")
public class Vec3i {
   public int x;
   public int y;
   public int z;

   public Vec3i(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3i() {
   }

   @Override
   public String toString() {
      return "Vec3i(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
   }
}
