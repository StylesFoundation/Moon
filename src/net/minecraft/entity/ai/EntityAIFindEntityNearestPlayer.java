package net.minecraft.entity.ai;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase {
   private static final Logger LOGGER = LogManager.getLogger();
   private final EntityLiving entityLiving;
   private final Predicate<Entity> predicate;
   private final EntityAINearestAttackableTarget.Sorter sorter;
   private EntityLivingBase entityTarget;

   public EntityAIFindEntityNearestPlayer(EntityLiving entityLiving) {
      this.entityLiving = entityLiving;
      if (entityLiving instanceof EntityCreature) {
         LOGGER.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
      }

      this.predicate = entity -> {
         if (!(entity instanceof EntityPlayer)) {
            return false;
         }

         if (((EntityPlayer)entity).capabilities.disableDamage) {
            return false;
         }

         double var5 = this.maxTargetRange();
         if (entity.isSneaking()) {
            var5 *= 0.8F;
         }

         if (entity.isInvisible()) {
            float var7 = ((EntityPlayer)entity).getArmorVisibility();
            if (var7 < 0.1F) {
               var7 = 0.1F;
            }

            var5 *= 0.7F * var7;
         }

         return !(entity.getDistanceToEntity(this.entityLiving) > var5)
            && EntityAITarget.isSuitableTarget(this.entityLiving, (EntityLivingBase)entity, false, true);
      };
      this.sorter = new EntityAINearestAttackableTarget.Sorter(entityLiving);
   }

   @Override
   public boolean shouldExecute() {
      double var4 = this.maxTargetRange();
      List var6 = this.entityLiving
         .worldObj
         .getEntitiesWithinAABB(EntityPlayer.class, this.entityLiving.getEntityBoundingBox().expand(var4, 4.0, var4), this.predicate);
      var6.sort(this.sorter);
      if (var6.isEmpty()) {
         return false;
      }

      this.entityTarget = (EntityLivingBase)var6.get(0);
      return true;
   }

   @Override
   public boolean continueExecuting() {
      EntityLivingBase var4 = this.entityLiving.getAttackTarget();
      if (var4 == null) {
         return false;
      }

      if (!var4.isEntityAlive()) {
         return false;
      }

      if (var4 instanceof EntityPlayer && ((EntityPlayer)var4).capabilities.disableDamage) {
         return false;
      }

      Team var5 = this.entityLiving.getTeam();
      Team var6 = var4.getTeam();
      if (var5 != null && var6 == var5) {
         return false;
      }

      double var7 = this.maxTargetRange();
      return !(this.entityLiving.getDistanceSqToEntity(var4) > var7 * var7)
         && (!(var4 instanceof EntityPlayerMP) || !((EntityPlayerMP)var4).theItemInWorldManager.isCreative());
   }

   @Override
   public void startExecuting() {
      this.entityLiving.setAttackTarget(this.entityTarget);
      super.startExecuting();
   }

   @Override
   public void resetTask() {
      this.entityLiving.setAttackTarget(null);
      super.startExecuting();
   }

   protected double maxTargetRange() {
      IAttributeInstance var4 = this.entityLiving.getEntityAttribute(SharedMonsterAttributes.followRange);
      return var4 == null ? 16.0 : var4.getAttributeValue();
   }
}
