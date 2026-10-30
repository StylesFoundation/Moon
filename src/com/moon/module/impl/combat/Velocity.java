package com.moon.module.impl.combat;

import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.combat.velocity.DelayedVelocity;
import com.moon.module.impl.combat.velocity.GrimVelocity;
import com.moon.module.impl.combat.velocity.JumpVelocity;
import com.moon.module.impl.combat.velocity.NormalVelocity;
import com.moon.module.impl.combat.velocity.WatchdogVelocity;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class Velocity extends Module {
   private final ModeSetting<Velocity.Mode> mode = ModeSetting.builder("mode", Velocity.Mode.class, this).defaults("Mode", Velocity.Mode.NORMAL).build();
   public final IntegerSetting horizontalModifier = IntegerSetting.builder("horizontal-mod")
      .defaults("Horizontal modifier", 50, 0, 100, 1)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.NORMAL)
      .build();
   public final IntegerSetting verticalModifier = IntegerSetting.builder("vertical-mod")
      .defaults("Vertical modifier", 50, 0, 100, 1)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.NORMAL)
      .build();
   public final IntegerSetting verticalDelay = IntegerSetting.builder("vertical-delay")
      .defaults("Delay", 0, 0, 10, 1)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.NORMAL)
      .build();
   public final IntegerSetting modifierDuration = IntegerSetting.builder("modifier-duration")
      .defaults("Modifier duration", 0, 1, 10, 1)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.NORMAL)
      .build();
   public final BooleanSetting reverseHorizontal = BooleanSetting.builder("reverse-horizontal")
      .defaults("Reverse horizontal", false)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.NORMAL)
      .build();
   public final BooleanSetting reverseVertical = BooleanSetting.builder("reverse-vertical")
      .defaults("Reverse vertical", false)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.NORMAL)
      .build();
   public final BooleanSetting allowCancel = BooleanSetting.builder("allow-cancel")
      .defaults("Allow cancel", false)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.NORMAL)
      .build();
   public final BooleanSetting stackVelocities = BooleanSetting.builder("delayed-stack-velocities")
      .defaults("Stack velocities", true)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.DELAYED)
      .build();
   public final IntegerSetting timeoutTicks = IntegerSetting.builder("delayed-timeout-ticks")
      .defaults("Time-out", 12, 1, 60, 1)
      .visibleWhen(() -> this.mode.getValue() == Velocity.Mode.DELAYED)
      .build();
   public final BooleanSetting modifyExplosions = BooleanSetting.builder("modify-explosions").defaults("Modify explosions", true).build();

   public Velocity(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<Velocity> {
      NORMAL("Normal", new NormalVelocity()),
      DELAYED("Delayed", new DelayedVelocity()),
      JUMP("Jump", new JumpVelocity()),
      GRIM("Grim (2.0)", new GrimVelocity()),
      WATCHDOG("Watchdog", new WatchdogVelocity());

      final String label;
      final SubModule<Velocity> subModule;

      @Override
      public SubModule<Velocity> getSubModule() {
         return this.subModule;
      }

      @Override
      public String label() {
         return this.label;
      }

      Mode(String label, SubModule<Velocity> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }
}
