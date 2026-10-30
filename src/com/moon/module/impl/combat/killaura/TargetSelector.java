package com.moon.module.impl.combat.killaura;

import com.moon.module.impl.combat.KillAura;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

public abstract class TargetSelector {
   protected static final Minecraft mc = Minecraft.getMinecraft();
   protected final KillAura killAura;

   public void onEnable() {
   }

   public void onDisable() {
   }

   public abstract EntityLivingBase selectTarget(List<EntityLivingBase> targets);

   public TargetSelector(KillAura killAura) {
      this.killAura = killAura;
   }
}
