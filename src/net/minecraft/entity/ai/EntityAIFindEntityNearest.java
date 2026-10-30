package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearest extends EntityAIBase {
   private static final Logger LOGGER = LogManager.getLogger();
   private final EntityLiving mob;
   private final Predicate<EntityLivingBase> field_179443_c;
   private final EntityAINearestAttackableTarget.Sorter field_179440_d;
   private EntityLivingBase target;
   private final Class<? extends EntityLivingBase> field_179439_f;

   public EntityAIFindEntityNearest(EntityLiving entityLiving, Class<? extends EntityLivingBase> class2) {
      this.mob = entityLiving;
      this.field_179439_f = class2;
      if (entityLiving instanceof EntityCreature) {
         LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.field_179443_c = entityLivingBase -> {
         double var5 = this.getFollowRange();
         if (entityLivingBase.isSneaking()) {
            var5 *= 0.8F;
         }

         return !entityLivingBase.isInvisible()
            && !(entityLivingBase.getDistanceToEntity(this.mob) > var5)
            && EntityAITarget.isSuitableTarget(this.mob, entityLivingBase, false, true);
      };
      this.field_179440_d = new EntityAINearestAttackableTarget.Sorter(entityLiving);
   }

   @Override
   public boolean shouldExecute() {
      double var4 = this.getFollowRange();
      List var6 = this.mob.worldObj.getEntitiesWithinAABB(this.field_179439_f, this.mob.getEntityBoundingBox().expand(var4, 4.0, var4), this.field_179443_c);
      var6.sort(this.field_179440_d);
      if (var6.isEmpty()) {
         return false;
      }

      this.target = (EntityLivingBase)var6.get(0);
      return true;
   }

   @Override
   public boolean continueExecuting() {
      EntityLivingBase var4 = this.mob.getAttackTarget();
      if (var4 == null) {
         return false;
      }

      if (!var4.isEntityAlive()) {
         return false;
      }

      double var5 = this.getFollowRange();
      return !(this.mob.getDistanceSqToEntity(var4) > var5 * var5)
         && (!(var4 instanceof EntityPlayerMP) || !((EntityPlayerMP)var4).theItemInWorldManager.isCreative());
   }

   @Override
   public void startExecuting() {
      this.mob.setAttackTarget(this.target);
      super.startExecuting();
   }

   @Override
   public void resetTask() {
      this.mob.setAttackTarget(null);
      super.startExecuting();
   }

   protected double getFollowRange() {
      IAttributeInstance var4 = this.mob.getEntityAttribute(SharedMonsterAttributes.followRange);
      return var4 == null ? 16.0 : var4.getAttributeValue();
   }
}
