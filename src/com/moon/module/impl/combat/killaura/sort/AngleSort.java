package com.moon.module.impl.combat.killaura.sort;

import com.moon.module.impl.combat.killaura.Sort;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class AngleSort implements Sort {
   @Override
   public Comparator<Entity> comparator() {
      return Comparator.comparingDouble(entity -> this.getAngleDifference((EntityLivingBase)entity));
   }

   private double getAngleDifference(EntityLivingBase target) {
      Vec3 diff = target.getPositionVector()
         .addVector(0.0, target.getEyeHeight() / 2.0F, 0.0)
         .subtract(client.thePlayer.getPositionVector().addVector(0.0, client.thePlayer.getEyeHeight(), 0.0));
      double yawDiff = Math.abs(client.thePlayer.rotationYaw - (Math.toDegrees(Math.atan2(diff.zCoord, diff.xCoord)) - 90.0)) % 360.0;
      return yawDiff > 180.0 ? 360.0 - yawDiff : yawDiff;
   }
}
