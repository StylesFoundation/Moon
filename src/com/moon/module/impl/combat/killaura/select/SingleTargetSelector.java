package com.moon.module.impl.combat.killaura.select;

import com.moon.Moon;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.combat.killaura.TargetSelector;
import com.moon.storage.TargetStorage;
import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class SingleTargetSelector extends TargetSelector {
   public SingleTargetSelector(KillAura killAura) {
      super(killAura);
   }

   @Override
   public EntityLivingBase selectTarget(List<EntityLivingBase> targets) {
      TargetStorage targetStorage = Moon.getInstance().getTargetStorage();
      boolean hasPriorityTarget = targets.stream().anyMatch(entity -> entity instanceof EntityPlayer player && targetStorage.isTarget(player));
      return hasPriorityTarget
         ? (EntityLivingBase)targets.stream()
            .filter(entity -> entity instanceof EntityPlayer player && targetStorage.isTarget(player))
            .min(this.killAura.getSortType().getValue().getSort().comparator())
            .orElse(null)
         : (EntityLivingBase)targets.stream()
            .sorted(Comparator.comparingInt(entity -> entity instanceof EntityPlayer player && targetStorage.isTarget(player) ? 1 : 0))
            .min(this.killAura.getSortType().getValue().getSort().comparator())
            .orElse(null);
   }
}
