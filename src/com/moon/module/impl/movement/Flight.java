package com.moon.module.impl.movement;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.movement.flight.BlockDropFlight;
import com.moon.module.impl.movement.flight.CollisionFlight;
import com.moon.module.impl.movement.flight.FuncraftFlight;
import com.moon.module.impl.movement.flight.NegativeFlight;
import com.moon.module.impl.movement.flight.VanillaFlight;
import com.moon.module.impl.movement.flight.VelocityFlight;
import com.moon.module.impl.movement.flight.VerusFlight;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class Flight extends Module {
   private final ModeSetting<Flight.Mode> mode = ModeSetting.builder("mode", Flight.Mode.class, this).defaults("Mode", Flight.Mode.VANILLA).build();
   public final FloatSetting vanillaVerticalSpeed = FloatSetting.builder("vanilla-vertical-speed")
      .defaults("Vertical Speed", 1.0F, 0.0F, 5.0F, 0.1F)
      .visibleWhen(() -> this.mode.getValue() == Flight.Mode.VANILLA)
      .build();
   public final FloatSetting vanillaHorizontalSpeed = FloatSetting.builder("vanilla-horizontal-speed")
      .defaults("Horizontal Speed", 1.0F, 0.0F, 5.0F, 0.1F)
      .visibleWhen(() -> this.mode.getValue() == Flight.Mode.VANILLA)
      .build();
   public final FloatSetting funcraftSpeed = FloatSetting.builder("funcraft-speed")
      .defaults("Speed", 2.0F, 0.0F, 10.0F, 0.1F)
      .visibleWhen(() -> this.mode.getValue() == Flight.Mode.FUN_CRAFT)
      .build();
   public final BooleanSetting verusDelayTransactions = BooleanSetting.builder("verus-delay-transactions")
      .defaults("Delay transactions", true)
      .visibleWhen(() -> this.mode.getValue() == Flight.Mode.VERUS)
      .build();
   public final IntegerSetting verusDelayTicks = IntegerSetting.builder("verus-delay-ticks")
      .defaults("Transaction delay", 15, 0, 50, 1)
      .visibleWhen(() -> this.mode.getValue() == Flight.Mode.VERUS && this.verusDelayTransactions.getValue())
      .build();
   public final BooleanSetting verusWaitForDelay = BooleanSetting.builder("verus-wait-for-delay")
      .defaults("Wait for delay", true)
      .visibleWhen(() -> this.mode.getValue() == Flight.Mode.VERUS && this.verusDelayTransactions.getValue())
      .build();

   public Flight(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.setSuffix(this.mode.getValue().label());
   }

   public ModeSetting<Flight.Mode> getMode() {
      return this.mode;
   }

   public FloatSetting getVanillaVerticalSpeed() {
      return this.vanillaVerticalSpeed;
   }

   public FloatSetting getVanillaHorizontalSpeed() {
      return this.vanillaHorizontalSpeed;
   }

   public FloatSetting getFuncraftSpeed() {
      return this.funcraftSpeed;
   }

   public BooleanSetting getVerusDelayTransactions() {
      return this.verusDelayTransactions;
   }

   public IntegerSetting getVerusDelayTicks() {
      return this.verusDelayTicks;
   }

   public BooleanSetting getVerusWaitForDelay() {
      return this.verusWaitForDelay;
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<Flight> {
      VANILLA("Vanilla", new VanillaFlight()),
      VELOCITY("Velocity", new VelocityFlight()),
      FUN_CRAFT("FunCraft", new FuncraftFlight()),
      BLOCK_DROP("BlockDrop", new BlockDropFlight()),
      NEGATIVITY("Negativity", new NegativeFlight()),
      COLLISION("Collision", new CollisionFlight()),
      VERUS("Verus", new VerusFlight());

      final String label;
      final SubModule<Flight> subModule;

      @Override
      public SubModule<Flight> getSubModule() {
         return this.subModule;
      }

      @Override
      public String label() {
         return this.label;
      }

      Mode(String label, SubModule<Flight> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }
}
