package com.moon.module.impl.visual;

import com.moon.Moon;
import com.moon.events.impl.render.RenderEntityNameTagEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BezierSetting;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.render.font.impl.MinecraftFontRenderer;
import com.moon.util.render.FrustumUtil;
import java.awt.Color;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.util.vector.Vector4f;

public class ESP extends Module {
   private final MinecraftFontRenderer fontRenderer = Moon.getInstance().getBitFontManager().getDefaultRenderer();
   private final MultipleChoiceSetting<ESP.Targets> targets = MultipleChoiceSetting.builder("targets", ESP.Targets.class)
      .defaults("Targets", ESP.Targets.PLAYER)
      .build();
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
   private final ColorSetting boxColor = ColorSetting.builder("two-d-box-color").defaults("Color", -1).visibleWhen(this.boxEnabled::getValue).build();
   private final GroupSetting boxSettings = GroupSetting.builder()
      .defaults("Box Settings", this.boxEnabled, this.boxCornered, this.boxCorneredRelative, this.cornerFactor, this.boxInfill, this.boxColor)
      .build();
   private final BooleanSetting nameEnabled = BooleanSetting.builder("two-d-name-enabled").defaults("Enabled", true).build();
   private final BooleanSetting nameCustomColor = BooleanSetting.builder("two-d-name-custom-color")
      .defaults("Custom Color", false)
      .visibleWhen(this.nameEnabled::getValue)
      .build();
   private final ColorSetting nameColor = ColorSetting.builder("two-d-name-color")
      .defaults("Color", -1)
      .visibleWhen(() -> this.nameEnabled.getValue() && this.nameCustomColor.getValue())
      .build();
   private final GroupSetting nameSettings = GroupSetting.builder().defaults("Name Settings", this.nameEnabled, this.nameCustomColor, this.nameColor).build();
   private final BooleanSetting healthEnabled = BooleanSetting.builder("two-d-health-enabled").defaults("Enabled", true).build();
   private final BooleanSetting showHealth = BooleanSetting.builder("two-d-show-health")
      .defaults("Show health", true)
      .visibleWhen(this.healthEnabled::getValue)
      .build();
   private final EnumSetting<ESP.HealthColorType> healthColorType = EnumSetting.builder("two-d-health-color-type", ESP.HealthColorType.class)
      .defaults("Color Type", ESP.HealthColorType.NORMAL)
      .visibleWhen(this.healthEnabled::getValue)
      .build();
   private final ColorSetting healthColorOne = ColorSetting.builder("two-d-health-color-one")
      .defaults("Color", -1)
      .visibleWhen(() -> this.healthColorType.getValue() != ESP.HealthColorType.NORMAL && this.healthEnabled.getValue())
      .build();
   private final ColorSetting healthColorTwo = ColorSetting.builder("two-d-health-color-two")
      .defaults("Second Color", Color.RED.getRGB())
      .visibleWhen(() -> this.healthColorType.getValue() == ESP.HealthColorType.GRADIENT && this.healthEnabled.getValue())
      .build();
   private final GroupSetting healthSettings = GroupSetting.builder()
      .defaults("Health Settings", this.healthEnabled, this.showHealth, this.healthColorType, this.healthColorOne, this.healthColorTwo)
      .build();
   private final BooleanSetting armorEnabled = BooleanSetting.builder("two-d-armor-enabled").defaults("Enabled", true).build();
   private final EnumSetting<ESP.ArmorColorType> armorColorType = EnumSetting.builder("two-d-armor-color-type", ESP.ArmorColorType.class)
      .defaults("Color Type", ESP.ArmorColorType.STATIC)
      .visibleWhen(this.armorEnabled::getValue)
      .build();
   private final ColorSetting armorColorOne = ColorSetting.builder("two-d-armor-color-one")
      .defaults("Color", -1)
      .visibleWhen(this.armorEnabled::getValue)
      .build();
   private final ColorSetting armorColorTwo = ColorSetting.builder("two-d-armor-color-two")
      .defaults("Second Color", Color.RED.getRGB())
      .visibleWhen(() -> this.armorColorType.getValue() == ESP.ArmorColorType.GRADIENT && this.armorEnabled.getValue())
      .build();
   private final GroupSetting armorSettings = GroupSetting.builder()
      .defaults("Armor Settings", this.armorEnabled, this.armorColorType, this.armorColorOne, this.armorColorTwo)
      .visibleWhen(this.twoD::getValue)
      .build();
   private final BooleanSetting itemEnabled = BooleanSetting.builder("two-d-item-enabled").defaults("Enabled", true).build();
   private final BooleanSetting itemTexture = BooleanSetting.builder("two-d-item-tex")
      .defaults("Texture", false)
      .visibleWhen(this.itemEnabled::getValue)
      .build();
   private final BooleanSetting itemName = BooleanSetting.builder("two-d-item-name").defaults("Name", true).visibleWhen(this.itemEnabled::getValue).build();
   private final ColorSetting itemColor = ColorSetting.builder("two-d-item-color")
      .defaults("Color", -1)
      .visibleWhen(() -> this.itemTexture.getValue() && this.itemEnabled.getValue())
      .build();
   private final GroupSetting itemSettings = GroupSetting.builder()
      .defaults("Item Settings", this.itemEnabled, this.itemTexture, this.itemName, this.itemColor)
      .build();
   private final BooleanSetting indicatorEnabled = BooleanSetting.builder("two-d-indicator-enabled").defaults("Enabled", false).build();
   private final MultipleChoiceSetting<ESP.IndicatorType> indicatorTypes = MultipleChoiceSetting.builder("two-d-indicator-types", ESP.IndicatorType.class)
      .defaults("Types", ESP.IndicatorType.BLOCK)
      .visibleWhen(this.indicatorEnabled::getValue)
      .build();
   private final GroupSetting indicatorSettings = GroupSetting.builder().defaults("Indicator Settings", this.indicatorEnabled, this.indicatorTypes).build();
   private final BooleanSetting glow = BooleanSetting.builder("glow").defaults("Glow", false).build();
   private final IntegerSetting glowOutlineIntensity = IntegerSetting.builder("glow-outline-intensity").defaults("Outline Radius", 16, 0, 100, 1).build();
   private final BezierSetting glowAlphaCurve = BezierSetting.builder("glow-alpha-bezier")
      .defaults("Alpha Curve", 1.0F, 0.3F)
      .controlPoints(new float[]{0.25F, 1.0F}, new float[]{0.6F, 1.0F})
      .build();
   private final BooleanSetting glowBlur = BooleanSetting.builder("glow-blur").defaults("Blur outline", true).build();
   private final IntegerSetting glowIterations = IntegerSetting.builder("glow-iterations")
      .defaults("Iterations", 2, 1, 5, 1)
      .visibleWhen(this.glowBlur::getValue)
      .build();
   private final FloatSetting glowRadius = FloatSetting.builder("glow-radius")
      .defaults("Offset", 3.0F, 1.0F, 10.0F, 0.1F)
      .visibleWhen(this.glowBlur::getValue)
      .build();
   private final FloatSetting glowAlpha = FloatSetting.builder("glow-alpha")
      .defaults("Exposure", 1.0F, 1.0F, 3.0F, 0.1F)
      .visibleWhen(this.glowBlur::getValue)
      .build();
   private final BooleanSetting glowCutInside = BooleanSetting.builder("glow-cut-inside")
      .defaults("Cut Entity", false)
      .visibleWhen(this.glowBlur::getValue)
      .build();
   private final GroupSetting intensitySettings = GroupSetting.builder()
      .defaults(
         "Intensity Settings",
         this.glowOutlineIntensity,
         this.glowAlphaCurve,
         this.glowBlur,
         this.glowIterations,
         this.glowRadius,
         this.glowAlpha,
         this.glowCutInside
      )
      .build();
   private final ColorSetting playerColor = ColorSetting.builder("glow-player-color")
      .defaults("Player Color", -161275)
      .visibleWhen(() -> this.targets.hasChecked(ESP.Targets.PLAYER))
      .build();
   private final ColorSetting animalColor = ColorSetting.builder("glow-animal-color")
      .defaults("Animal Color", -15335949)
      .visibleWhen(() -> this.targets.hasChecked(ESP.Targets.ANIMAL))
      .build();
   private final ColorSetting mobColor = ColorSetting.builder("glow-mobs-color")
      .defaults("Mobs Color", -13697734)
      .visibleWhen(() -> this.targets.hasChecked(ESP.Targets.MOB))
      .build();
   private final ColorSetting hurtTimeColor = ColorSetting.builder("glow-hurttime-color").defaults("Hurt Time Color", -175277).build();
   private final BooleanSetting outline = BooleanSetting.builder("outline").defaults("Outline", false).build();
   private final IntegerSetting outlineIntensity = IntegerSetting.builder("outline-outline-intensity").defaults("Outline Radius", 1, 0, 100, 1).build();
   private final FloatSetting outlineBrightness = FloatSetting.builder("outline-brightness").defaults("Outline Brightness", 0.0F, 0.0F, 1.0F, 0.05F).build();
   private final FloatSetting outlineContrast = FloatSetting.builder("outline-contrast").defaults("Outline Contrast", 1.0F, 0.0F, 5.0F, 0.05F).build();
   private final FloatSetting outlineSaturation = FloatSetting.builder("outline-saturation").defaults("Outline Saturation", 1.0F, 0.0F, 5.0F, 0.05F).build();
   private final GroupSetting outlineSettings = GroupSetting.builder()
      .defaults("Outline Settings", this.outlineIntensity, this.outlineBrightness, this.outlineContrast, this.outlineSaturation)
      .build();
   private final BooleanSetting box3d = BooleanSetting.builder("box").defaults("Box", false).build();
   private final GroupSetting glowColorSettings = GroupSetting.builder()
      .defaults("Color Settings", this.playerColor, this.animalColor, this.mobColor, this.hurtTimeColor)
      .build();
   private final BooleanSetting markTarget = BooleanSetting.builder("box-mark-target").defaults("Mark target", true).build();
   private final BooleanSetting box3dOutline = BooleanSetting.builder("box-outline").defaults("Outline", true).build();
   private final FloatSetting box3dOpacity = FloatSetting.builder("box-opacity").defaults("Opacity", 0.3F, 0.01F, 1.0F, 0.01F).build();
   private final FloatSetting box3dWidthExpansion = FloatSetting.builder("box-width-expansion").defaults("Width expansion", 0.0F, 0.0F, 1.0F, 0.01F).build();
   private final FloatSetting box3dHeightExpansion = FloatSetting.builder("box-height-expansion").defaults("Height expansion", 0.0F, 0.0F, 1.0F, 0.01F).build();
   private final GroupSetting box3dSettings = GroupSetting.builder()
      .defaults("Box Settings", this.markTarget, this.box3dOutline, this.box3dOpacity, this.box3dWidthExpansion, this.box3dHeightExpansion)
      .build();
   private final ColorSetting box3dNormalColor = ColorSetting.builder("box-normal-color").defaults("Normal color", Color.BLUE.getRGB()).build();
   private final ColorSetting box3dTargetColor = ColorSetting.builder("box-target-color")
      .defaults("Target color", Color.RED.getRGB())
      .visibleWhen(this.markTarget::getValue)
      .build();
   private final GroupSetting box3dColorSettings = GroupSetting.builder().defaults("Color Settings", this.box3dNormalColor, this.box3dTargetColor).build();

   public ESP(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onRenderEntityNameTag(RenderEntityNameTagEvent event) {
      Entity entity = event.getEntity();
      Vector4f screenBounds = entity.Method940();
      if (screenBounds != null && this.isValidTarget(entity) && FrustumUtil.isVisible(entity)) {
         if (this.twoD.getValue() && this.nameEnabled.getValue()) {
            event.setCancelled(true);
         }
      }
   }

   public boolean isValidTarget(Entity entity) {
      if (entity == mc.thePlayer) {
         return mc.gameSettings.thirdPersonView != 0;
      } else if (entity.getEntityId() == -84 || entity.getEntityId() == -42) {
         return false;
      } else if (entity.isInvisible() && !this.targets.hasChecked(ESP.Targets.INVISIBLE)) {
         return false;
      } else if (entity instanceof EntityMob && this.targets.hasChecked(ESP.Targets.MOB)) {
         return true;
      } else {
         return (entity instanceof EntityAnimal || entity instanceof EntityVillager || entity instanceof EntitySquid)
               && this.targets.hasChecked(ESP.Targets.ANIMAL)
            ? true
            : entity instanceof EntityPlayer && this.targets.hasChecked(ESP.Targets.PLAYER);
      }
   }

   public MinecraftFontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   public MultipleChoiceSetting<ESP.Targets> getTargets() {
      return this.targets;
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

   public ColorSetting getBoxColor() {
      return this.boxColor;
   }

   public GroupSetting getBoxSettings() {
      return this.boxSettings;
   }

   public BooleanSetting getNameEnabled() {
      return this.nameEnabled;
   }

   public BooleanSetting getNameCustomColor() {
      return this.nameCustomColor;
   }

   public ColorSetting getNameColor() {
      return this.nameColor;
   }

   public GroupSetting getNameSettings() {
      return this.nameSettings;
   }

   public BooleanSetting getHealthEnabled() {
      return this.healthEnabled;
   }

   public BooleanSetting getShowHealth() {
      return this.showHealth;
   }

   public EnumSetting<ESP.HealthColorType> getHealthColorType() {
      return this.healthColorType;
   }

   public ColorSetting getHealthColorOne() {
      return this.healthColorOne;
   }

   public ColorSetting getHealthColorTwo() {
      return this.healthColorTwo;
   }

   public GroupSetting getHealthSettings() {
      return this.healthSettings;
   }

   public BooleanSetting getArmorEnabled() {
      return this.armorEnabled;
   }

   public EnumSetting<ESP.ArmorColorType> getArmorColorType() {
      return this.armorColorType;
   }

   public ColorSetting getArmorColorOne() {
      return this.armorColorOne;
   }

   public ColorSetting getArmorColorTwo() {
      return this.armorColorTwo;
   }

   public GroupSetting getArmorSettings() {
      return this.armorSettings;
   }

   public BooleanSetting getItemEnabled() {
      return this.itemEnabled;
   }

   public BooleanSetting getItemTexture() {
      return this.itemTexture;
   }

   public BooleanSetting getItemName() {
      return this.itemName;
   }

   public ColorSetting getItemColor() {
      return this.itemColor;
   }

   public GroupSetting getItemSettings() {
      return this.itemSettings;
   }

   public BooleanSetting getIndicatorEnabled() {
      return this.indicatorEnabled;
   }

   public MultipleChoiceSetting<ESP.IndicatorType> getIndicatorTypes() {
      return this.indicatorTypes;
   }

   public GroupSetting getIndicatorSettings() {
      return this.indicatorSettings;
   }

   public BooleanSetting getGlow() {
      return this.glow;
   }

   public IntegerSetting getGlowOutlineIntensity() {
      return this.glowOutlineIntensity;
   }

   public BezierSetting getGlowAlphaCurve() {
      return this.glowAlphaCurve;
   }

   public BooleanSetting getGlowBlur() {
      return this.glowBlur;
   }

   public IntegerSetting getGlowIterations() {
      return this.glowIterations;
   }

   public FloatSetting getGlowRadius() {
      return this.glowRadius;
   }

   public FloatSetting getGlowAlpha() {
      return this.glowAlpha;
   }

   public BooleanSetting getGlowCutInside() {
      return this.glowCutInside;
   }

   public GroupSetting getIntensitySettings() {
      return this.intensitySettings;
   }

   public ColorSetting getPlayerColor() {
      return this.playerColor;
   }

   public ColorSetting getAnimalColor() {
      return this.animalColor;
   }

   public ColorSetting getMobColor() {
      return this.mobColor;
   }

   public ColorSetting getHurtTimeColor() {
      return this.hurtTimeColor;
   }

   public BooleanSetting getOutline() {
      return this.outline;
   }

   public IntegerSetting getOutlineIntensity() {
      return this.outlineIntensity;
   }

   public FloatSetting getOutlineBrightness() {
      return this.outlineBrightness;
   }

   public FloatSetting getOutlineContrast() {
      return this.outlineContrast;
   }

   public FloatSetting getOutlineSaturation() {
      return this.outlineSaturation;
   }

   public GroupSetting getOutlineSettings() {
      return this.outlineSettings;
   }

   public BooleanSetting getBox3d() {
      return this.box3d;
   }

   public GroupSetting getGlowColorSettings() {
      return this.glowColorSettings;
   }

   public BooleanSetting getMarkTarget() {
      return this.markTarget;
   }

   public BooleanSetting getBox3dOutline() {
      return this.box3dOutline;
   }

   public FloatSetting getBox3dOpacity() {
      return this.box3dOpacity;
   }

   public FloatSetting getBox3dWidthExpansion() {
      return this.box3dWidthExpansion;
   }

   public FloatSetting getBox3dHeightExpansion() {
      return this.box3dHeightExpansion;
   }

   public GroupSetting getBox3dSettings() {
      return this.box3dSettings;
   }

   public ColorSetting getBox3dNormalColor() {
      return this.box3dNormalColor;
   }

   public ColorSetting getBox3dTargetColor() {
      return this.box3dTargetColor;
   }

   public GroupSetting getBox3dColorSettings() {
      return this.box3dColorSettings;
   }

   public enum ArmorColorType implements LabelEnum {
      STATIC("Static"),
      GRADIENT("Gradient");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      ArmorColorType(String label) {
         this.label = label;
      }
   }

   public enum HealthColorType implements LabelEnum {
      NORMAL("Normal"),
      GRADIENT("Gradient"),
      STATIC("Static");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      HealthColorType(String label) {
         this.label = label;
      }
   }

   public enum IndicatorType implements LabelEnum {
      BLOCK("Block"),
      SNEAK("Sneak"),
      LOW_HP("Low HP");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      IndicatorType(String label) {
         this.label = label;
      }
   }

   public enum Targets implements LabelEnum {
      PLAYER("Players"),
      ANIMAL("Animals"),
      MOB("Mobs"),
      INVISIBLE("Invisibles");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      Targets(String label) {
         this.label = label;
      }
   }
}
