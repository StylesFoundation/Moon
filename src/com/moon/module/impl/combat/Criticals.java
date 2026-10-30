package com.moon.module.impl.combat;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.combat.criticals.VerusCriticals;
import com.moon.module.impl.combat.criticals.VulcanCriticals;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class Criticals extends Module {
   private final ModeSetting<Criticals.Mode> mode = ModeSetting.builder("mode", Criticals.Mode.class, this).defaults("Mode", Criticals.Mode.VULCAN).build();
   private final IntegerSetting vulcanDelay = IntegerSetting.builder("vulcan-delay").defaults("Delay", 100, 0, 500, 50).build();

   public Criticals(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.setSuffix(this.mode.getValue().label());
   }

   public ModeSetting<Criticals.Mode> getMode() {
      return this.mode;
   }

   public IntegerSetting getDelay() {
      return this.vulcanDelay;
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<Criticals> {
      VULCAN("Vulcan", new VulcanCriticals()),
      VERUS("Verus", new VerusCriticals());

      private final String label;
      private final SubModule<Criticals> subModule;

      @Override
      public String label() {
         return this.label;
      }

      @Override
      public SubModule<Criticals> getSubModule() {
         return this.subModule;
      }

      Mode(String label, SubModule<Criticals> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }
}
