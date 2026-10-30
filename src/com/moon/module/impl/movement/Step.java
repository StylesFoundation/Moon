package com.moon.module.impl.movement;

import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.movement.step.WatchdogStep;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class Step extends Module {
   public final ModeSetting<Step.Mode> mode = ModeSetting.builder("mode", Step.Mode.class, this).defaults("Mode", Step.Mode.WATCHDOG).build();

   public Step(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   enum Mode implements LabelEnum, SubModuleRegistry<Step> {
      WATCHDOG("Watchdog", new WatchdogStep());

      private final String label;
      private final SubModule<Step> subModule;

      @Override
      public String label() {
         return this.label;
      }

      @Override
      public SubModule<Step> getSubModule() {
         return this.subModule;
      }

      Mode(String label, SubModule<Step> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }
}
