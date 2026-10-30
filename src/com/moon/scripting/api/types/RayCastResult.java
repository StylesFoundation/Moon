package com.moon.scripting.api.types;

import com.moon.scripting.api.enums.Facing;
import com.moon.scripting.api.enums.HitType;
import com.moon.scripting.documentation.annotation.DataClassContainer;
import com.moon.scripting.documentation.annotation.Description;

@DataClassContainer(value = "raycastresult", path = "types/raycastresult")
@Description("The result returned by ray-cast functions. You cannot instantiate this type yourself!")
public class RayCastResult {
   public HitType hit_type;
   public Facing side_hit;
   public Vec3i pos;
   public Vec3d hit_vec;
   public int hit_entity_id;

   public RayCastResult(HitType hit_type, Facing side_hit, Vec3i pos, Vec3d hit_vec, int hit_entity_id) {
      this.hit_type = hit_type;
      this.side_hit = side_hit;
      this.pos = pos;
      this.hit_vec = hit_vec;
      this.hit_entity_id = hit_entity_id;
   }

   public RayCastResult() {
   }

   @Override
   public String toString() {
      return "RayCastResult(hit_type="
         + this.hit_type
         + ", side_hit="
         + this.side_hit
         + ", pos="
         + this.pos
         + ", hit_vec="
         + this.hit_vec
         + ", hit_entity_id="
         + this.hit_entity_id
         + ")";
   }
}
