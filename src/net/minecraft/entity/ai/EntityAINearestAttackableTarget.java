package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.realms.Tezzelator;

public class EntityAINearestAttackableTarget<T extends EntityLivingBase> extends EntityAITarget {
   protected final Class<T> targetClass;
   private final int targetChance;
   protected final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
   protected Predicate<? super T> targetEntitySelector;
   protected EntityLivingBase targetEntity;

   public EntityAINearestAttackableTarget(EntityCreature entityCreature, Class<T> class2, boolean boolean3) {
      this(entityCreature, class2, boolean3, false);
   }

   public EntityAINearestAttackableTarget(EntityCreature entityCreature, Class<T> class2, boolean boolean3, boolean boolean4) {
      this(entityCreature, class2, 10, boolean3, boolean4, null);
   }

   public EntityAINearestAttackableTarget(
      EntityCreature entityCreature, Class<T> class2, int integer, boolean boolean4, boolean boolean5, Predicate<? super T> predicate
   ) {
      super(entityCreature, boolean4, boolean5);
      this.targetClass = class2;
      this.targetChance = integer;
      this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(entityCreature);
      this.setMutexBits(1);
      this.targetEntitySelector = entityLivingBase -> {
         if (predicate != null && !predicate.apply((T)entityLivingBase)) {
            return false;
         }

         if (entityLivingBase instanceof EntityPlayer) {
            double var6 = this.getTargetDistance();
            if (entityLivingBase.isSneaking()) {
               var6 *= 0.8F;
            }

            if (entityLivingBase.isInvisible()) {
               float var8 = ((EntityPlayer)entityLivingBase).getArmorVisibility();
               if (var8 < 0.1F) {
                  var8 = 0.1F;
               }

               var6 *= 0.7F * var8;
            }

            if (entityLivingBase.getDistanceToEntity(this.taskOwner) > var6) {
               return false;
            }
         }

         return this.isSuitableTarget(entityLivingBase, false);
      };
   }

   @Override
   public boolean shouldExecute() {
      if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
         return false;
      }

      double var4 = this.getTargetDistance();
      List var6 = this.taskOwner
         .worldObj
         .getEntitiesWithinAABB(
            this.targetClass, this.taskOwner.getEntityBoundingBox().expand(var4, 4.0, var4), Predicates.and(this.targetEntitySelector, Tezzelator.d)
         );
      var6.sort(this.theNearestAttackableTargetSorter);
      if (var6.isEmpty()) {
         return false;
      }

      this.targetEntity = (EntityLivingBase)var6.get(0);
      return true;
   }

   @Override
   public void startExecuting() {
      this.taskOwner.setAttackTarget(this.targetEntity);
      super.startExecuting();
   }

   public static class Sorter implements Comparator<Entity> {
      private final Entity theEntity;

      public Sorter(Entity entity) {
         this.theEntity = entity;
      }

      public int compare(Entity entity1, Entity entity2) {
         double var3 = this.theEntity.getDistanceSqToEntity(entity1);
         double var5 = this.theEntity.getDistanceSqToEntity(entity2);
         return Double.compare(var3, var5);
      }
   }
}
