package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

public class EntityAIWatchClosest2 extends EntityAIWatchClosest {
   public EntityAIWatchClosest2(EntityLiving entityLiving, Class<? extends Entity> class2, float float3, float float4) {
      super(entityLiving, class2, float3, float4);
      this.setMutexBits(3);
   }
}
