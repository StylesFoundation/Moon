package com.moon.module.impl.movement;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.movement.antivoid.impl.PacketAntiVoid;
import com.moon.module.impl.movement.antivoid.impl.PositionAntiVoid;
import com.moon.module.impl.movement.antivoid.impl.WatchdogAntiVoid;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class AntiVoid extends Module {
   private ModeSetting<AntiVoid.Mode> mode = ModeSetting.builder("mode", AntiVoid.Mode.class, this).defaults("Mode", AntiVoid.Mode.POSITION).build();
   private FloatSetting fallDistance = FloatSetting.builder("fall-distance").defaults("Fall distance", 5.0F, 3.0F, 20.0F, 0.5F).build();

   public AntiVoid(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.setSuffix(this.mode.getValue().label());
   }

   public ModeSetting<AntiVoid.Mode> getMode() {
      return this.mode;
   }

   public FloatSetting getFallDistance() {
      return this.fallDistance;
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<AntiVoid> {
      POSITION("Position", new PositionAntiVoid()),
      PACKET("Packet", new PacketAntiVoid()),
      WATCHDOG("Watchdog", new WatchdogAntiVoid());

      private final String label;
      private final SubModule<AntiVoid> subModule;

      @Override
      public String label() {
         return this.label;
      }

      @Override
      public SubModule<AntiVoid> getSubModule() {
         return this.subModule;
      }

      Mode(String label, SubModule<AntiVoid> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }
}
