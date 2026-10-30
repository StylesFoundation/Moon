package com.moon.module.impl.combat.killaura.select;

import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.combat.killaura.TargetSelector;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class DynamicTargetSelector extends TargetSelector {
   private EntityLivingBase currentTarget;

   public DynamicTargetSelector(KillAura killAura) {
      super(killAura);
   }

   @Override
   public void onEnable() {
      this.currentTarget = null;
   }

   @Override
   public EntityLivingBase selectTarget(List<EntityLivingBase> targets) {
      List<EntityLivingBase> attackableTargets = targets.stream().filter(entity -> entity.hurtResistantTime <= 16).toList();
      boolean needsNewTarget = this.currentTarget == null || this.currentTarget.isDead || this.currentTarget.hurtResistantTime > 16;
      if (this.currentTarget != null
         && (this.currentTarget.isDead || this.currentTarget.getDistanceToEntity(mc.thePlayer) > this.killAura.getAimReach().getValue())) {
         this.currentTarget = null;
      }

      if (!attackableTargets.isEmpty() && needsNewTarget) {
         this.currentTarget = attackableTargets.stream().min(Comparator.comparingDouble(this::getYawDifference)).orElse(null);
      }

      return this.currentTarget;
   }

   private double getYawDifference(EntityLivingBase entity) {
      Vec3 diff = entity.getPositionVector()
         .addVector(0.0, entity.getEyeHeight() / 2.0F, 0.0)
         .subtract(mc.thePlayer.getPositionVector().addVector(0.0, mc.thePlayer.getEyeHeight(), 0.0));
      double yawDiff = Math.abs(mc.thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(diff.zCoord, diff.xCoord)) - 90.0)) % 360.0;
      return yawDiff > 180.0 ? 360.0 - yawDiff : yawDiff;
   }
}
