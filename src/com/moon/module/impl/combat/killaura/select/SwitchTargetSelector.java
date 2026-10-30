package com.moon.module.impl.combat.killaura.select;

import com.moon.Moon;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.combat.killaura.TargetSelector;
import com.moon.storage.TargetStorage;
import com.moon.util.client.Stopwatch;
import java.util.List;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class SwitchTargetSelector extends TargetSelector {
   private final Stopwatch stopwatch = new Stopwatch();
   private int index;

   public SwitchTargetSelector(KillAura killAura) {
      super(killAura);
   }

   @Override
   public EntityLivingBase selectTarget(List<EntityLivingBase> targets) {
      TargetStorage targetStorage = Moon.getInstance().getTargetStorage();
      boolean hasPriorityTarget = targets.stream().anyMatch(entity -> entity instanceof EntityPlayer player && targetStorage.isTarget(player));
      List<EntityLivingBase> filteredTargets = targets;
      if (hasPriorityTarget) {
         filteredTargets = filteredTargets.stream().filter(entity -> entity instanceof EntityPlayer player && targetStorage.isTarget(player)).toList();
      }

      if (this.stopwatch.hasElapsedAndReset(this.killAura.getSwitchDelay().getValue().intValue()) && !filteredTargets.isEmpty()) {
         this.index++;
         if (this.index > filteredTargets.size() - 1) {
            this.index = 0;
         }
      }

      return filteredTargets.isEmpty() ? null : filteredTargets.get(Math.min(this.index, filteredTargets.size() - 1));
   }
}
