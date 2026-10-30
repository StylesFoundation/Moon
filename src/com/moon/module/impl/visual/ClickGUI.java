package com.moon.module.impl.visual;

import com.moon.Moon;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.ui.clickgui.imgui.ImGuiClickGUIScreen;
import com.moon.ui.clickgui.modern.ModernClickGui;
import com.moon.ui.clickgui.classic.ClassicClickGui;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.animation.Easing;
import java.awt.Color;

public class ClickGUI extends Module {
   private ClassicClickGui classicScreen = null;
   private ModernClickGui modernScreen = null;
   private ImGuiClickGUIScreen imGuiScreen = null;
   private ColorScheme accentColor = ColorScheme.darkScheme(-1);
   private final EnumSetting<ClickGUI.Mode> mode = EnumSetting.builder("mode", ClickGUI.Mode.class).defaults("Mode", ClickGUI.Mode.MODERN).build();
   private final BooleanSetting openAnimation = BooleanSetting.builder("open-animation")
      .defaults("Opening Animation", true)
      .visibleWhen(() -> this.mode.getValue() == ClickGUI.Mode.DEFAULT)
      .build();
   private final EnumSetting<ClickGUI.OpenAnimationType> openAnimationType = EnumSetting.builder("open-animation-type", ClickGUI.OpenAnimationType.class)
      .defaults("Opening Type", ClickGUI.OpenAnimationType.LINEAR)
      .visibleWhen(() -> this.mode.getValue() == ClickGUI.Mode.DEFAULT && this.openAnimation.getValue() && this.openAnimation.isVisible())
      .build();
   private final BooleanSetting closeAnimation = BooleanSetting.builder("close-animation")
      .defaults("Closing Animation", true)
      .visibleWhen(() -> this.mode.getValue() == ClickGUI.Mode.DEFAULT)
      .build();
   private final EnumSetting<ClickGUI.CloseAnimationType> closeAnimationType = EnumSetting.builder("close-animation-type", ClickGUI.CloseAnimationType.class)
      .visibleWhen(() -> this.mode.getValue() == ClickGUI.Mode.DEFAULT && this.closeAnimation.getValue() && this.closeAnimation.isVisible())
      .defaults("Closing Type", ClickGUI.CloseAnimationType.IN_BACK)
      .build();
   private final EnumSetting<ClickGUI.ModuleDesignType> moduleDesignType = EnumSetting.builder("module-design-type", ClickGUI.ModuleDesignType.class)
      .visibleWhen(() -> this.mode.getValue() == ClickGUI.Mode.DEFAULT)
      .defaults("Module Design", ClickGUI.ModuleDesignType.STACKED)
      .build();
   private final BooleanSetting center = BooleanSetting.builder("center")
      .defaults("Center", false)
      .visibleWhen(() -> this.mode.getValue() == ClickGUI.Mode.MODERN)
      .build();
   private final BooleanSetting postProcessing = BooleanSetting.builder("post-processing")
      .defaults("Post processing", true)
      .visibleWhen(() -> this.mode.getValue() == ClickGUI.Mode.MODERN)
      .build();
   private final FloatSetting scale = FloatSetting.builder("scale")
      .defaults("Scale", 1.0F, 0.5F, 2.0F, 0.1F)
      .displayName("This setting only applies on re-open")
      .visibleWhen(() -> this.mode.getValue() == ClickGUI.Mode.MODERN)
      .build();
   private final ColorSetting color = ColorSetting.builder("color").defaults("Color", Color.RED.getRGB()).onChange((previous, current) -> {
      if (this.classicScreen != null) {
         this.classicScreen.refreshColors();
      }

      this.accentColor = ColorScheme.darkScheme(current);
      Moon.getInstance().getAccountManager().setThemeColor(current);
   }).build();

   public ClickGUI(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Override
   public void onEnable() {
      switch (this.mode.getValue()) {
         case MODERN:
            if (this.modernScreen == null) {
               this.modernScreen = new ModernClickGui();
               this.modernScreen.setupPanels();
            }

            mc.displayGuiScreen(this.modernScreen);
            break;
         case DEFAULT:
            if (this.classicScreen == null) {
               this.classicScreen = new ClassicClickGui(this);
               this.classicScreen.setupPanels();
            }

            mc.displayGuiScreen(this.classicScreen);
            break;
         case IMGUI:
            if (this.imGuiScreen == null) {
               this.imGuiScreen = new ImGuiClickGUIScreen();
               this.imGuiScreen.setup();
            }

            mc.displayGuiScreen(this.imGuiScreen);
            break;
      }

      this.toggle();
   }

   public ClickGUI setClassicScreen(ClassicClickGui screen) {
      this.classicScreen = screen;
      return this;
   }

   public ClickGUI setModernScreen(ModernClickGui screen) {
      this.modernScreen = screen;
      return this;
   }

   public ClickGUI setAccentColor(ColorScheme color) {
      this.accentColor = color;
      return this;
   }

   public ClassicClickGui getClassicScreen() {
      return this.classicScreen;
   }

   public ModernClickGui getModernScreen() {
      return this.modernScreen;
   }

   public ColorScheme getAccentColor() {
      return this.accentColor;
   }

   public EnumSetting<ClickGUI.Mode> getMode() {
      return this.mode;
   }

   public BooleanSetting getOpenAnimation() {
      return this.openAnimation;
   }

   public EnumSetting<ClickGUI.OpenAnimationType> getOpenAnimationType() {
      return this.openAnimationType;
   }

   public BooleanSetting getCloseAnimation() {
      return this.closeAnimation;
   }

   public EnumSetting<ClickGUI.CloseAnimationType> getCloseAnimationType() {
      return this.closeAnimationType;
   }

   public EnumSetting<ClickGUI.ModuleDesignType> getModuleDesignType() {
      return this.moduleDesignType;
   }

   public BooleanSetting getCenter() {
      return this.center;
   }

   public BooleanSetting getPostProcessing() {
      return this.postProcessing;
   }

   public FloatSetting getScale() {
      return this.scale;
   }

   public ColorSetting getColor() {
      return this.color;
   }

   public enum CloseAnimationType implements LabelEnum {
      LINEAR("Linear", Easing.LINEAR),
      IN_BACK("In Back", Easing.EASE_IN_BACK);

      private final String label;
      private final Easing easing;

      @Override
      public String label() {
         return this.label;
      }

      public Easing getEasing() {
         return this.easing;
      }

      CloseAnimationType(String label, Easing easing) {
         this.label = label;
         this.easing = easing;
      }
   }

   public enum Mode implements LabelEnum {
      DEFAULT("Default"),
      MODERN("Modern"),
      IMGUI("ImGui");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Mode(String label) {
         this.label = label;
      }
   }

   public enum ModuleDesignType implements LabelEnum {
      ROUNDED("Rounded"),
      STACKED("Stacked");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      ModuleDesignType(String label) {
         this.label = label;
      }
   }

   public enum OpenAnimationType implements LabelEnum {
      LINEAR("Linear", Easing.LINEAR),
      OUT_BACK("Out Back", Easing.EASE_OUT_BACK);

      private final String label;
      private final Easing easing;

      @Override
      public String label() {
         return this.label;
      }

      public Easing getEasing() {
         return this.easing;
      }

      OpenAnimationType(String label, Easing easing) {
         this.label = label;
         this.easing = easing;
      }
   }
}
