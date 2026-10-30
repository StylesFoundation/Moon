package com.moon.module.impl.player;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.player.nofall.BlinkNoFall;
import com.moon.module.impl.player.nofall.CollisionNoFall;
import com.moon.module.impl.player.nofall.FlagNoFall;
import com.moon.module.impl.player.nofall.VanillaNoFall;
import com.moon.module.impl.player.nofall.VerusNoFall;
import com.moon.module.impl.player.nofall.WatchdogNoFall;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class NoFall extends Module {
   private final ModeSetting<NoFall.Mode> mode = ModeSetting.builder("mode", NoFall.Mode.class, this).defaults("Mode", NoFall.Mode.VANILLA).build();

   public NoFall(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.setSuffix(this.mode.getValue().label());
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<NoFall> {
      VANILLA("Vanilla", new VanillaNoFall()),
      FLAG("Flag", new FlagNoFall()),
      VERUS("Verus", new VerusNoFall()),
      BLINK("Blink", new BlinkNoFall()),
      COLLISION("Collision", new CollisionNoFall()),
      WATCHDOG("Watchdog", new WatchdogNoFall());

      final String displayName;
      final SubModule<NoFall> subModule;

      @Override
      public SubModule<NoFall> getSubModule() {
         return this.subModule;
      }

      @Override
      public String label() {
         return this.displayName;
      }

      Mode(String displayName, SubModule<NoFall> subModule) {
         this.displayName = displayName;
         this.subModule = subModule;
      }

      private static NoFall.Mode[] createValues() {
         return new NoFall.Mode[]{VANILLA, FLAG, VERUS, BLINK, COLLISION, WATCHDOG};
      }
   }
}
