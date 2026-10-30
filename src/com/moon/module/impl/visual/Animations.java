package com.moon.module.impl.visual;

import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.IntegerSetting;

public class Animations extends Module {
   private final EnumSetting<Animations.Mode> mode = EnumSetting.builder("mode", Animations.Mode.class).defaults("Mode", Animations.Mode.V1_7).build();
   private final BooleanSetting slowAnimationDown = BooleanSetting.builder("slow-animation-down").defaults("Slow animation down", true).build();
   private final IntegerSetting slowDownStrength = IntegerSetting.builder("slow-down-strength")
      .defaults("Slow down strength", 5, 1, 15, 1)
      .visibleWhen(this.slowAnimationDown::getValue)
      .build();
   private final BooleanSetting alwaysTranslate = BooleanSetting.builder("always-translate").defaults("Always translate", false).build();
   private final FloatSetting xPos = FloatSetting.builder("x-pos").defaults("X Position", 0.0F, -1.5F, 1.0F, 0.025F).build();
   private final FloatSetting yPos = FloatSetting.builder("y-pos").defaults("Y Position", 0.0F, -0.5F, 1.0F, 0.025F).build();
   private final FloatSetting scale = FloatSetting.builder("scale").defaults("Scale", 1.0F, 0.1F, 3.0F, 0.025F).build();

   public Animations(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   public EnumSetting<Animations.Mode> getMode() {
      return this.mode;
   }

   public BooleanSetting getSlowAnimationDown() {
      return this.slowAnimationDown;
   }

   public IntegerSetting getSlowDownStrength() {
      return this.slowDownStrength;
   }

   public BooleanSetting getAlwaysTranslate() {
      return this.alwaysTranslate;
   }

   public FloatSetting getXPos() {
      return this.xPos;
   }

   public FloatSetting getYPos() {
      return this.yPos;
   }

   public FloatSetting getScale() {
      return this.scale;
   }

   public enum Mode implements LabelEnum {
      V1_8("1.8"),
      V1_7("1.7"),
      SLIDE("Slide"),
      XIV("XIV"),
      EXHIBITION("Exhibition"),
      OLD_EXHIBITION("Old Exhibition"),
      WINGY("Wingy");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Mode(String label) {
         this.label = label;
      }
   }
}
