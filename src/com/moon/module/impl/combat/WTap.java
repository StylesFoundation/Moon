package com.moon.module.impl.combat;

import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.combat.wtap.BlatantWTap;
import com.moon.module.impl.combat.wtap.LegitWTap;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class WTap extends Module {
   private final ModeSetting<WTap.Mode> mode = ModeSetting.builder("mode", WTap.Mode.class, this).defaults("Mode", WTap.Mode.BLATANT).build();
   private final BooleanSetting doublePacket = BooleanSetting.builder("double-packet")
      .defaults("Double Packet", false)
      .visibleWhen(() -> this.mode.getValue() == WTap.Mode.BLATANT)
      .build();
   private final EnumSetting<WTap.Timing> tapTiming = EnumSetting.builder("tap-timing", WTap.Timing.class)
      .defaults("Tap timing", WTap.Timing.HURT_TIME)
      .visibleWhen(() -> this.mode.getValue() == WTap.Mode.LEGIT)
      .build();
   private final IntegerSetting tickDelay = IntegerSetting.builder("tick-delay")
      .defaults("Tick delay", 9, 0, 10, 1)
      .visibleWhen(() -> this.mode.getValue() == WTap.Mode.LEGIT)
      .build();
   private final BooleanSetting randomizeDelay = BooleanSetting.builder("randomize-delay")
      .defaults("Randomize tick delay", false)
      .visibleWhen(() -> this.mode.getValue() == WTap.Mode.LEGIT)
      .build();
   private final IntegerSetting randomizeAmount = IntegerSetting.builder("randomize-amount")
      .defaults("Randomize amount", 1, 0, 5, 1)
      .visibleWhen(() -> this.mode.getValue() == WTap.Mode.LEGIT && this.randomizeDelay.getValue())
      .build();

   public WTap(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   public ModeSetting<WTap.Mode> getMode() {
      return this.mode;
   }

   public BooleanSetting getDoublePacket() {
      return this.doublePacket;
   }

   public EnumSetting<WTap.Timing> getTapTiming() {
      return this.tapTiming;
   }

   public IntegerSetting getTickDelay() {
      return this.tickDelay;
   }

   public BooleanSetting getRandomizeDelay() {
      return this.randomizeDelay;
   }

   public IntegerSetting getRandomizeAmount() {
      return this.randomizeAmount;
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<WTap> {
      BLATANT("Blatant", new BlatantWTap()),
      LEGIT("Legit", new LegitWTap());

      private final String label;
      private final SubModule<WTap> subModule;

      @Override
      public String label() {
         return this.label;
      }

      @Override
      public SubModule<WTap> getSubModule() {
         return this.subModule;
      }

      Mode(String label, SubModule<WTap> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }

   public enum Timing implements LabelEnum {
      HURT_TIME("Hurt-time"),
      PAST_ATTACK("Past attack");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Timing(String label) {
         this.label = label;
      }
   }
}
