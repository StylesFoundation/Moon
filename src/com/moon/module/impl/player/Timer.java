package com.moon.module.impl.player;

import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.player.timer.BalanceTimer;
import com.moon.module.impl.player.timer.NormalTimer;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class Timer extends Module {
   private final ModeSetting<Timer.Mode> mode = ModeSetting.builder("mode", Timer.Mode.class, this).defaults("Mode", Timer.Mode.NORMAL).build();
   private final FloatSetting speed = FloatSetting.builder("speed").defaults("Speed", 1.0F, 0.1F, 10.0F, 0.05F).build();
   private final FloatSetting chargeSpeed = FloatSetting.builder("charge-speed")
      .defaults("Charge speed", 0.3F, 0.1F, 0.9F, 0.05F)
      .visibleWhen(() -> this.mode.getValue() == Timer.Mode.BALANCE)
      .build();
   private final BooleanSetting tickDelay = BooleanSetting.builder("tick-delay").defaults("Tick delay", false).build();
   private final FloatSetting ticks = FloatSetting.builder("ticks").defaults("Ticks", 1.0F, 1.0F, 40.0F, 1.0F).visibleWhen(this.tickDelay::getValue).build();
   private final BooleanSetting balanceOverlay = BooleanSetting.builder("balance-overlay")
      .defaults("Overlay", false)
      .visibleWhen(() -> this.mode.getValue() == Timer.Mode.BALANCE)
      .build();
   private final BooleanSetting colorfulBackground = BooleanSetting.builder("colorful-background")
      .defaults("Colorful background", true)
      .visibleWhen(() -> this.mode.getValue() == Timer.Mode.BALANCE && this.balanceOverlay.getValue())
      .build();

   public Timer(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   public ModeSetting<Timer.Mode> getMode() {
      return this.mode;
   }

   public FloatSetting getSpeed() {
      return this.speed;
   }

   public FloatSetting getChargeSpeed() {
      return this.chargeSpeed;
   }

   public BooleanSetting getTickDelay() {
      return this.tickDelay;
   }

   public FloatSetting getTicks() {
      return this.ticks;
   }

   public BooleanSetting getBalanceOverlay() {
      return this.balanceOverlay;
   }

   public BooleanSetting getColorfulBackground() {
      return this.colorfulBackground;
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<Timer> {
      NORMAL("Normal", new NormalTimer()),
      BALANCE("Balance", new BalanceTimer());

      private final String displayName;
      private final SubModule<Timer> subModule;

      @Override
      public String label() {
         return this.displayName;
      }

      @Override
      public SubModule<Timer> getSubModule() {
         return this.subModule;
      }

      Mode(String displayName, SubModule<Timer> subModule) {
         this.displayName = displayName;
         this.subModule = subModule;
      }

      private static Timer.Mode[] createValues() {
         return new Timer.Mode[]{NORMAL, BALANCE};
      }
   }
}
