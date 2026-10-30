package com.moon.module.impl.visual;

import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import java.awt.Color;

public class StorageESP extends Module {
   private final BooleanSetting twoD = BooleanSetting.builder("two-d").defaults("2D", true).build();
   private final BooleanSetting boxEnabled = BooleanSetting.builder("two-d-box-enabled").defaults("Enabled", true).build();
   private final BooleanSetting boxCornered = BooleanSetting.builder("two-d-box-cornered")
      .defaults("Cornered", false)
      .visibleWhen(this.boxEnabled::getValue)
      .build();
   private final BooleanSetting boxCorneredRelative = BooleanSetting.builder("two-d-box-cornered-relative")
      .defaults("Relative Corners", true)
      .visibleWhen(() -> this.boxEnabled.getValue() && this.boxCornered.getValue())
      .build();
   private final FloatSetting cornerFactor = FloatSetting.builder("two-d-box-cornered-corner-factor")
      .defaults("Corner Factor", 2.0F, 2.0F, 4.0F, 0.1F)
      .visibleWhen(() -> this.boxEnabled.getValue() && this.boxCornered.getValue())
      .build();
   private final BooleanSetting boxInfill = BooleanSetting.builder("two-d-box-infill").defaults("Infill", false).visibleWhen(this.boxEnabled::getValue).build();
   private final ColorSetting normalColor = ColorSetting.builder("two-d-box-norm-color")
      .defaults("Normal Color", -1)
      .visibleWhen(this.boxEnabled::getValue)
      .build();
   private final ColorSetting redstoneColor = ColorSetting.builder("two-d-box-red-color")
      .defaults("Redstone Color", Color.RED.getRGB())
      .visibleWhen(this.boxEnabled::getValue)
      .build();
   private final ColorSetting enderColor = ColorSetting.builder("two-d-box-end-color")
      .defaults("Ender Color", Color.PINK.getRGB())
      .visibleWhen(this.boxEnabled::getValue)
      .build();
   private final GroupSetting boxSettings = GroupSetting.builder()
      .defaults(
         "Box Settings",
         this.boxEnabled,
         this.boxCornered,
         this.boxCorneredRelative,
         this.cornerFactor,
         this.boxInfill,
         this.normalColor,
         this.redstoneColor,
         this.enderColor
      )
      .build();
   private final BooleanSetting glow = BooleanSetting.builder("glow")
      .defaults("Glow", false)
      .description("Glow properties have to be configured inside ESP")
      .build();
   private final ColorSetting glowNormalColor = ColorSetting.builder("glow-norm-color").defaults("Normal Color", Color.BLUE.getRGB()).build();
   private final ColorSetting glowRedstoneColor = ColorSetting.builder("glow-red-color").defaults("Redstone Color", Color.RED.getRGB()).build();
   private final ColorSetting glowEnderColor = ColorSetting.builder("glow-end-color").defaults("Ender Color", Color.PINK.getRGB()).build();
   private final BooleanSetting outline = BooleanSetting.builder("outline")
      .defaults("Outline", false)
      .description("Outline properties have to be configured inside ESP")
      .build();
   private final GroupSetting glowColorSettings = GroupSetting.builder()
      .defaults("Color Settings", this.glowNormalColor, this.glowRedstoneColor, this.glowEnderColor)
      .build();
   private final BooleanSetting box3d = BooleanSetting.builder("box").defaults("Box", false).build();
   private final BooleanSetting box3dOutline = BooleanSetting.builder("box-outline").defaults("Outline", true).build();
   private final FloatSetting box3dOpacity = FloatSetting.builder("box-opacity").defaults("Opacity", 0.3F, 0.01F, 1.0F, 0.01F).build();
   private final GroupSetting box3dSettings = GroupSetting.builder().defaults("Box Settings", this.box3dOutline, this.box3dOpacity).build();
   private final ColorSetting box3dNormalColor = ColorSetting.builder("box-norm-color").defaults("Normal Color", Color.BLUE.getRGB()).build();
   private final ColorSetting box3dRedstoneColor = ColorSetting.builder("box-red-color").defaults("Redstone Color", Color.RED.getRGB()).build();
   private final ColorSetting box3dEnderColor = ColorSetting.builder("box-end-color").defaults("Ender Color", Color.PINK.getRGB()).build();
   private final GroupSetting box3dColorSettings = GroupSetting.builder()
      .defaults("Color Settings", this.box3dNormalColor, this.box3dRedstoneColor, this.box3dEnderColor)
      .build();

   public StorageESP(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   public BooleanSetting getTwoD() {
      return this.twoD;
   }

   public BooleanSetting getBoxEnabled() {
      return this.boxEnabled;
   }

   public BooleanSetting getBoxCornered() {
      return this.boxCornered;
   }

   public BooleanSetting getBoxCorneredRelative() {
      return this.boxCorneredRelative;
   }

   public FloatSetting getCornerFactor() {
      return this.cornerFactor;
   }

   public BooleanSetting getBoxInfill() {
      return this.boxInfill;
   }

   public ColorSetting getNormalColor() {
      return this.normalColor;
   }

   public ColorSetting getRedstoneColor() {
      return this.redstoneColor;
   }

   public ColorSetting getEnderColor() {
      return this.enderColor;
   }

   public GroupSetting getBoxSettings() {
      return this.boxSettings;
   }

   public BooleanSetting getGlow() {
      return this.glow;
   }

   public ColorSetting getGlowNormalColor() {
      return this.glowNormalColor;
   }

   public ColorSetting getGlowRedstoneColor() {
      return this.glowRedstoneColor;
   }

   public ColorSetting getGlowEnderColor() {
      return this.glowEnderColor;
   }

   public BooleanSetting getOutline() {
      return this.outline;
   }

   public GroupSetting getGlowColorSettings() {
      return this.glowColorSettings;
   }

   public BooleanSetting getBox3d() {
      return this.box3d;
   }

   public BooleanSetting getBox3dOutline() {
      return this.box3dOutline;
   }

   public FloatSetting getBox3dOpacity() {
      return this.box3dOpacity;
   }

   public GroupSetting getBox3dSettings() {
      return this.box3dSettings;
   }

   public ColorSetting getBox3dNormalColor() {
      return this.box3dNormalColor;
   }

   public ColorSetting getBox3dRedstoneColor() {
      return this.box3dRedstoneColor;
   }

   public ColorSetting getBox3dEnderColor() {
      return this.box3dEnderColor;
   }

   public GroupSetting getBox3dColorSettings() {
      return this.box3dColorSettings;
   }
}
