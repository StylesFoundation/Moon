package com.moon.scripting.api.types;

import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "vec3d", path = "types/vec3d")
@Description("A three-dimensional double based vector representation")
public class Vec3d {
   public double x;
   public double y;
   public double z;

   public Vec3d(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public Vec3d() {
   }

   @Override
   public String toString() {
      return "Vec3d(x=" + this.x + ", y=" + this.y + ", z=" + this.z + ")";
   }
}
