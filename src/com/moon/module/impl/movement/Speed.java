package com.moon.module.impl.movement;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.movement.speed.LegitSpeed;
import com.moon.module.impl.movement.speed.NCPSpeed;
import com.moon.module.impl.movement.speed.StrafeSpeed;
import com.moon.module.impl.movement.speed.UniversoSpeed;
import com.moon.module.impl.movement.speed.VanillaSpeed;
import com.moon.module.impl.movement.speed.VerusSpeed;
import com.moon.module.impl.movement.speed.VulcanSpeed;
import com.moon.module.impl.movement.speed.WatchdogSpeed;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class Speed extends Module {
   private final ModeSetting<Speed.Mode> mode = ModeSetting.builder("mode", Speed.Mode.class, this).defaults("Mode", Speed.Mode.VANILLA).build();
   private final BooleanSetting lowerJumpMotion = BooleanSetting.builder("lower-jump-motion")
      .defaults("Lower Jump Motion", false)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.NCP)
      .build();
   public final BooleanSetting wdDamageBoost = BooleanSetting.builder("wd-damage-boost")
      .defaults("Damage Boost", false)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.WATCHDOG)
      .build();
   public final FloatSetting wdDamageBoostAmount = FloatSetting.builder("wd-damage-boost-amount")
      .defaults("Damage Boost Amount", 2.5F, 1.0F, 3.0F, 0.25F)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.WATCHDOG && this.wdDamageBoost.getValue())
      .build();
   private final EnumSetting<Speed.VerusMode> verusMode = EnumSetting.builder("verus-mode", Speed.VerusMode.class)
      .defaults("Verus Mode", Speed.VerusMode.HOP)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.VERUS)
      .build();
   private final BooleanSetting strafeFast = BooleanSetting.builder("strafe-fast")
      .defaults("Fast", true)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.STRAFE)
      .build();
   private final BooleanSetting hurtTimeBoost = BooleanSetting.builder("hurt-time-boost")
      .defaults("Hurt Time Boost", false)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.STRAFE && !this.strafeFast.getValue())
      .build();
   private final FloatSetting hurtTimeBoostAmount = FloatSetting.builder("hurt-time-boost-amount")
      .defaults("Hurt Time Boost Amount", 1.0F, 1.0F, 10.0F, 0.25F)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.STRAFE && this.hurtTimeBoost.getValue() && !this.strafeFast.getValue())
      .build();
   public final BooleanSetting universoDamageBoost = BooleanSetting.builder("universo-damage-boost")
      .defaults("Damage Boost", false)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.UNIVERSO)
      .build();
   public final FloatSetting universoDamageBoostAmount = FloatSetting.builder("universo-damage-boost-amount")
      .defaults("Damage Boost Amount", 2.5F, 1.0F, 3.0F, 0.25F)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.UNIVERSO && this.universoDamageBoost.getValue())
      .build();
   public final BooleanSetting vanillaJump = BooleanSetting.builder("vanilla-jump")
      .defaults("Jump", true)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.VANILLA)
      .build();
   public final FloatSetting vanillaSpeed = FloatSetting.builder("vanilla-speed")
      .defaults("Speed", 0.5F, 0.1F, 10.0F, 0.1F)
      .visibleWhen(() -> this.mode.getValue() == Speed.Mode.VANILLA)
      .build();

   public Speed(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.setSuffix(this.mode.getValue().label());
   }

   public ModeSetting<Speed.Mode> getMode() {
      return this.mode;
   }

   public BooleanSetting getLowerJumpMotion() {
      return this.lowerJumpMotion;
   }

   public BooleanSetting getWdDamageBoost() {
      return this.wdDamageBoost;
   }

   public FloatSetting getWdDamageBoostAmount() {
      return this.wdDamageBoostAmount;
   }

   public EnumSetting<Speed.VerusMode> getVerusMode() {
      return this.verusMode;
   }

   public BooleanSetting getStrafeFast() {
      return this.strafeFast;
   }

   public BooleanSetting getHurtTimeBoost() {
      return this.hurtTimeBoost;
   }

   public FloatSetting getHurtTimeBoostAmount() {
      return this.hurtTimeBoostAmount;
   }

   public BooleanSetting getUniversoDamageBoost() {
      return this.universoDamageBoost;
   }

   public FloatSetting getUniversoDamageBoostAmount() {
      return this.universoDamageBoostAmount;
   }

   public BooleanSetting getVanillaJump() {
      return this.vanillaJump;
   }

   public FloatSetting getVanillaSpeed() {
      return this.vanillaSpeed;
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<Speed> {
      VANILLA("Vanilla", new VanillaSpeed()),
      NCP("NCP", new NCPSpeed()),
      WATCHDOG("Watchdog", new WatchdogSpeed()),
      LEGIT("Legit", new LegitSpeed()),
      VULCAN("Vulcan", new VulcanSpeed()),
      VERUS("Verus", new VerusSpeed()),
      STRAFE("Strafe", new StrafeSpeed()),
      UNIVERSO("Universo", new UniversoSpeed());

      final String label;
      final SubModule<Speed> subModule;

      @Override
      public SubModule<Speed> getSubModule() {
         return this.subModule;
      }

      @Override
      public String label() {
         return this.label;
      }

      Mode(String label, SubModule<Speed> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }

   public enum VerusMode implements LabelEnum {
      GROUND("Ground"),
      HOP("Hop");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      VerusMode(String label) {
         this.label = label;
      }
   }
}
