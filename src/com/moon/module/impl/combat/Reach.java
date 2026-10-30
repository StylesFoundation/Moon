package com.moon.module.impl.combat;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.player.PickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.util.math.MathUtil;

public class Reach extends Module {
   private final FloatSetting minReach = FloatSetting.builder("min-reach").defaults("Min reach", 3.0F, 3.0F, 6.0F, 0.05F).build();
   private final FloatSetting maxReach = FloatSetting.builder("max-reach").defaults("Max reach", 3.0F, 3.0F, 6.0F, 0.05F).onChange((oldValue, newValue) -> {
      if (newValue < this.minReach.getValue()) {
         this.minReach.setValue(newValue - this.minReach.getInc());
      }
   }).build();
   public final BooleanSetting targetHurtTimeSync = BooleanSetting.builder("target-hurt-time-sync").defaults("Target hurt-time sync", false).build();

   public Reach(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.maxReach.setValue(Math.max(this.maxReach.getValue(), this.minReach.getValue()));
   }

   @Subscribe
   public void onPick(PickEvent event) {
      if (!this.targetHurtTimeSync.getValue() || event.getEntity() == null || event.getEntity().hurtResistantTime < 12) {
         event.setRange(MathUtil.random(this.minReach.getValue(), this.maxReach.getValue()));
      }
   }
}
