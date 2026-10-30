package com.moon.module.impl.movement;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.movement.longjump.VanillaLongJump;
import com.moon.module.impl.movement.longjump.VulcanLongJump;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.ModeSetting;
import com.moon.module.sub.SubModule;
import com.moon.module.sub.SubModuleRegistry;

public class LongJump extends Module {
   private final ModeSetting<LongJump.Mode> mode = ModeSetting.builder("mode", LongJump.Mode.class, this).defaults("Mode", LongJump.Mode.VULCAN).build();
   private final FloatSetting vanillaHeight = FloatSetting.builder("vanilla-height")
      .defaults("Height", 0.42F, 0.1F, 5.0F, 0.01F)
      .visibleWhen(() -> this.mode.getValue() == LongJump.Mode.VANILLA)
      .build();
   private final FloatSetting vanillaSpeed = FloatSetting.builder("vanilla-speed")
      .defaults("Speed", 1.0F, 0.1F, 10.0F, 0.1F)
      .visibleWhen(() -> this.mode.getValue() == LongJump.Mode.VANILLA)
      .build();

   public LongJump(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.setSuffix(this.mode.getValue().label());
   }

   public ModeSetting<LongJump.Mode> getMode() {
      return this.mode;
   }

   public FloatSetting getVanillaHeight() {
      return this.vanillaHeight;
   }

   public FloatSetting getVanillaSpeed() {
      return this.vanillaSpeed;
   }

   public enum Mode implements LabelEnum, SubModuleRegistry<LongJump> {
      VANILLA("Vanilla", new VanillaLongJump()),
      VULCAN("Vulcan", new VulcanLongJump());

      private final String label;
      private final SubModule<LongJump> subModule;

      @Override
      public String label() {
         return this.label;
      }

      @Override
      public SubModule<LongJump> getSubModule() {
         return this.subModule;
      }

      Mode(String label, SubModule<LongJump> subModule) {
         this.label = label;
         this.subModule = subModule;
      }
   }
}
