package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;

public class EntityAITargetNonTamed<T extends EntityLivingBase> extends EntityAINearestAttackableTarget {
   private final EntityTameable theTameable;

   public EntityAITargetNonTamed(EntityTameable entityTameable, Class<T> class2, boolean boolean3, Predicate<? super T> predicate) {
      super(entityTameable, class2, 10, boolean3, false, predicate);
      this.theTameable = entityTameable;
   }

   @Override
   public boolean shouldExecute() {
      return !this.theTameable.isTamed() && super.shouldExecute();
   }
}
