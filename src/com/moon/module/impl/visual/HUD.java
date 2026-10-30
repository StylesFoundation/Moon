package com.moon.module.impl.visual;

import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.module.CategoryHideEvent;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.RenderFrameEvent;
import com.moon.events.impl.render.ResizeEvent;
import com.moon.events.impl.render.ScaleEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.events.EventBus;
import com.moon.module.LabelEnum;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.visual.hud.base.ColorMode;
import com.moon.module.impl.visual.hud.base.DraggableComponentProperties;
import com.moon.module.impl.visual.hud.base.DraggableHudElement;
import com.moon.module.impl.visual.hud.base.HudElement;
import com.moon.module.impl.visual.hud.info.InformationElement;
import com.moon.module.impl.visual.hud.info.PotionStatusElement;
import com.moon.module.impl.visual.hud.info.WatermarkElement;
import com.moon.module.impl.visual.hud.modulelist.ModuleListElement;
import com.moon.module.impl.visual.hud.pitevent.PitEventTrackerElement;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.EnumSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.module.setting.impl.MultipleChoiceSetting;
import com.moon.module.setting.impl.StringSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.renderer.GlStateManager;

public class HUD extends Module {
   private boolean rebuildRequested = true;
   protected final BooleanSetting watermarkEnabled = BooleanSetting.builder("wm-enabled").defaults("Enabled", true).build();
   protected final EnumSetting<HUD.WatermarkMode> watermarkMode = EnumSetting.builder("wm-mode", HUD.WatermarkMode.class)
      .defaults("Mode", HUD.WatermarkMode.LOGO)
      .visibleWhen(this.watermarkEnabled::getValue)
      .build();
   protected final StringSetting clientName = StringSetting.builder("wm-clientname")
      .defaults("Client Name", "Moon")
      .visibleWhen(() -> this.watermarkEnabled.getValue() && this.watermarkMode.getValue() == HUD.WatermarkMode.TEXT)
      .build();
   protected final BooleanSetting watermarkCustomFont = BooleanSetting.builder("wm-custom-font")
      .defaults("Custom Font", true)
      .visibleWhen(() -> this.watermarkEnabled.getValue() && this.watermarkMode.getValue() == HUD.WatermarkMode.TEXT)
      .build();
   protected final BooleanSetting watermarkColorful = BooleanSetting.builder("wm-colorful")
      .defaults("Colorful background", false)
      .visibleWhen(() -> this.watermarkEnabled.getValue() && this.watermarkMode.getValue() == HUD.WatermarkMode.INFORMATIVE)
      .build();
   protected final BooleanSetting watermarkLowAlphaText = BooleanSetting.builder("wm-low-alpha-text")
      .defaults("Low alpha text", false)
      .visibleWhen(() -> this.watermarkEnabled.getValue() && this.watermarkMode.getValue() == HUD.WatermarkMode.INFORMATIVE)
      .build();
   protected final EnumSetting<ColorMode> watermarkColorMode = EnumSetting.builder("wm-color", ColorMode.class)
      .defaults("Color Mode", ColorMode.SYNC)
      .visibleWhen(this.watermarkEnabled::getValue)
      .build();
   protected final ColorSetting watermarkColorOne = ColorSetting.builder("wm-color-one")
      .defaults("Color", -1)
      .visibleWhen(() -> this.watermarkEnabled.getValue() && this.watermarkColorMode.getValue() != ColorMode.SYNC)
      .build();
   protected final ColorSetting watermarkColorTwo = ColorSetting.builder("wm-color-two")
      .defaults("Second Color", -1)
      .visibleWhen(() -> this.watermarkEnabled.getValue() && this.watermarkColorMode.getValue() == ColorMode.GRADIENT)
      .build();
   protected final GroupSetting watermarkSettings = GroupSetting.builder()
      .defaults(
         "Watermark Settings",
         this.watermarkEnabled,
         this.watermarkMode,
         this.clientName,
         this.watermarkCustomFont,
         this.watermarkColorful,
         this.watermarkLowAlphaText,
         this.watermarkColorMode,
         this.watermarkColorOne,
         this.watermarkColorTwo
      )
      .build();
   protected final BooleanSetting potionsEnabled = BooleanSetting.builder("pot-enabled").defaults("Enabled", true).build();
   protected final BooleanSetting potionsCustomFont = BooleanSetting.builder("pot-custom-font")
      .defaults("Custom Font", true)
      .visibleWhen(this.potionsEnabled::getValue)
      .build();
   protected final BooleanSetting potionsShowIcons = BooleanSetting.builder("pot-show-icons")
      .defaults("Show Icons", true)
      .visibleWhen(this.potionsEnabled::getValue)
      .build();
   protected final GroupSetting potionSettings = GroupSetting.builder()
      .defaults("Potion Settings", this.potionsEnabled, this.potionsCustomFont, this.potionsShowIcons)
      .build();
   protected final BooleanSetting showFps = BooleanSetting.builder("info-fps-enabled").defaults("Show FPS", true).build();
   protected final BooleanSetting showBps = BooleanSetting.builder("info-bps-enabled").defaults("Show BPS", true).build();
   protected final BooleanSetting showBuild = BooleanSetting.builder("info-build-enabled").defaults("Show Build", true).build();
   protected final BooleanSetting infoCustomFont = BooleanSetting.builder("info-custom-font").defaults("Custom Font", true).build();
   protected final EnumSetting<ColorMode> infoPrefixColor = EnumSetting.builder("info-prefix-color", ColorMode.class)
      .defaults("Prefix Color", ColorMode.SYNC)
      .build();
   protected final ColorSetting infoPrefixColorOne = ColorSetting.builder("info-prefix-col-one")
      .defaults("Color", -1)
      .visibleWhen(() -> this.infoPrefixColor.getValue() != ColorMode.SYNC)
      .build();
   protected final ColorSetting infoPrefixColorTwo = ColorSetting.builder("info-prefix-col-two")
      .defaults("Second Color", -1)
      .visibleWhen(() -> this.infoPrefixColor.getValue() == ColorMode.GRADIENT)
      .build();
   protected final GroupSetting informationSettings = GroupSetting.builder()
      .defaults(
         "Information Settings",
         this.showFps,
         this.showBps,
         this.showBuild,
         this.infoCustomFont,
         this.infoPrefixColor,
         this.infoPrefixColorOne,
         this.infoPrefixColorTwo
      )
      .build();
   protected final BooleanSetting pitEventTracker = BooleanSetting.builder("pit-event-tracker").defaults("Enabled", false).build();
   protected final BooleanSetting pitHideTitle = BooleanSetting.builder("pit-event-tracker-hide-title").defaults("Hide title", false).build();
   protected final BooleanSetting pitPadding = BooleanSetting.builder("pit-event-tracker-padding").defaults("Padding between time", true).build();
   protected final BooleanSetting pitOnlyMajor = BooleanSetting.builder("pit-event-tracker-major")
      .defaults("Only Major", false)
      .visibleWhen(this.pitEventTracker::getValue)
      .build();
   protected final IntegerSetting pitEventAmount = IntegerSetting.builder("pit-event-tracker-amount")
      .defaults("Event Amount", 10, 5, 20, 1)
      .visibleWhen(this.pitEventTracker::getValue)
      .build();
   protected final GroupSetting pitEventTrackerSettings = GroupSetting.builder()
      .defaults("Pit Event Tracker", this.pitEventTracker, this.pitHideTitle, this.pitPadding, this.pitOnlyMajor, this.pitEventAmount)
      .build();
   protected final GroupSetting draggableSettings = GroupSetting.builder().defaults("Draggable Settings", this.pitEventTrackerSettings).build();
   protected final BooleanSetting arrayListEnabled = BooleanSetting.builder("al-enabled").defaults("Enabled", true).build();
   protected final MultipleChoiceSetting<ModuleCategory> shownCategories = MultipleChoiceSetting.builder("shown-categories", ModuleCategory.class)
      .defaults("Shown categories", ModuleCategory.values())
      .onChange((previous, current) -> {
         for (ModuleCategory category : previous.keySet()) {
            boolean previousState = (Boolean)previous.get(category);
            boolean currentState = (Boolean)current.get(category);
            if (currentState != previousState) {
               Moon.getInstance().getEventBus().publish(new CategoryHideEvent(category, currentState));
               break;
            }
         }
      })
      .build();
   protected final BooleanSetting alCustomFont = BooleanSetting.builder("al-custom-font")
      .defaults("Custom Font", true)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final BooleanSetting alTextShadow = BooleanSetting.builder("al-text-shadow")
      .defaults("Shadow", true)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final EnumSetting<HUD.SuffixType> suffixType = EnumSetting.builder("al-suffix-type", HUD.SuffixType.class)
      .defaults("Suffix Type", HUD.SuffixType.SPACE)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final EnumSetting<HUD.NameFormatting> nameFormatting = EnumSetting.builder("al-module-name-formatting", HUD.NameFormatting.class)
      .defaults("Name Formatting", HUD.NameFormatting.NORMAL)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final EnumSetting<HUD.TextColorMode> textColor = EnumSetting.builder("al-text-color", HUD.TextColorMode.class)
      .defaults("Text Color", HUD.TextColorMode.GRADIENT)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final ColorSetting alColorOne = ColorSetting.builder("al-color-one")
      .defaults("Color", -10295554)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final ColorSetting alColorTwo = ColorSetting.builder("al-color-two")
      .defaults("Second Color", -7458050)
      .visibleWhen(() -> this.textColor.getValue() == HUD.TextColorMode.GRADIENT && this.arrayListEnabled.getValue())
      .build();
   protected final FloatSetting alFadeOffset = FloatSetting.builder("al-fade-offset")
      .defaults("Fade offset", 0.8F, 0.1F, 1.5F, 0.1F)
      .visibleWhen(() -> this.textColor.getValue() == HUD.TextColorMode.PULSING || this.textColor.getValue() == HUD.TextColorMode.GRADIENT)
      .build();
   protected final FloatSetting alFontSize = FloatSetting.builder("al-font-size")
      .defaults("Font size", 9.0F, 5.0F, 14.0F, 0.25F)
      .visibleWhen(this.alCustomFont::getValue)
      .build();
   protected final GroupSetting textSettings = GroupSetting.builder()
      .defaults(
         "Text Settings",
         this.alCustomFont,
         this.alTextShadow,
         this.suffixType,
         this.nameFormatting,
         this.textColor,
         this.alColorOne,
         this.alColorTwo,
         this.alFadeOffset,
         this.alFontSize
      )
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final BooleanSetting alBackground = BooleanSetting.builder("al-background")
      .defaults("Enabled", true)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final IntegerSetting backgroundOpacity = IntegerSetting.builder("al-background-opacity")
      .defaults("Background Opacity", 80, 0, 255, 1)
      .visibleWhen(() -> this.alBackground.getValue() && this.arrayListEnabled.getValue())
      .build();
   protected final BooleanSetting backgroundColoredGlow = BooleanSetting.builder("al-background-colored-glow")
      .defaults("Text Colored Glow", false)
      .visibleWhen(() -> this.alBackground.getValue() && this.arrayListEnabled.getValue())
      .build();
   protected final ColorSetting backgroundColor = ColorSetting.builder("al-background-color")
      .defaults("Background Color", -16777216)
      .visibleWhen(() -> this.alBackground.getValue() && this.arrayListEnabled.getValue())
      .build();
   protected final BooleanSetting alRounded = BooleanSetting.builder("al-rounded")
      .defaults("Rounded", false)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final IntegerSetting roundingFactor = IntegerSetting.builder("al-rounding-factor")
      .defaults("Rounding Factor", 3, 1, 6, 1)
      .visibleWhen(() -> this.alRounded.getValue() && this.arrayListEnabled.getValue())
      .build();
   protected final IntegerSetting alOffset = IntegerSetting.builder("al-offset")
      .defaults("Offset", 0, 0, 10, 1)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final GroupSetting backgroundSettings = GroupSetting.builder()
      .defaults(
         "Background Settings",
         this.alBackground,
         this.backgroundColoredGlow,
         this.backgroundColor,
         this.backgroundOpacity,
         this.alRounded,
         this.roundingFactor
      )
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final BooleanSetting alSidebar = BooleanSetting.builder("al-sidebar")
      .defaults("Enabled", true)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final EnumSetting<HUD.SidebarType> sidebarType = EnumSetting.builder("al-sidebar-type", HUD.SidebarType.class)
      .defaults("Sidebar Type", HUD.SidebarType.RIGHT)
      .visibleWhen(() -> this.alSidebar.getValue() && this.arrayListEnabled.getValue())
      .build();
   protected final EnumSetting<HUD.SidebarColorMode> sidebarColor = EnumSetting.builder("al-sidebar-color", HUD.SidebarColorMode.class)
      .defaults("Sidebar Color", HUD.SidebarColorMode.SYNC)
      .visibleWhen(() -> this.alSidebar.getValue() && this.arrayListEnabled.getValue())
      .build();
   protected final ColorSetting sidebarColorOne = ColorSetting.builder("al-color-one-sb")
      .defaults("Color", -1)
      .visibleWhen(() -> this.alSidebar.getValue() && this.sidebarColor.getValue() != HUD.SidebarColorMode.SYNC && this.arrayListEnabled.getValue())
      .build();
   protected final ColorSetting sidebarColorTwo = ColorSetting.builder("al-color-two-sb")
      .defaults("Second Color", -1)
      .visibleWhen(() -> this.alSidebar.getValue() && this.sidebarColor.getValue() == HUD.SidebarColorMode.GRADIENT && this.arrayListEnabled.getValue())
      .build();
   protected final FloatSetting sidebarFadeOffset = FloatSetting.builder("al-fade-offset-sb")
      .defaults("Fade offset", 0.8F, 0.1F, 1.5F, 0.1F)
      .visibleWhen(() -> this.sidebarColor.getValue() == HUD.SidebarColorMode.GRADIENT || this.sidebarColor.getValue() == HUD.SidebarColorMode.PULSING)
      .build();
   protected final GroupSetting sidebarSettings = GroupSetting.builder()
      .defaults("Sidebar Settings", this.alSidebar, this.sidebarType, this.sidebarColor, this.sidebarColorOne, this.sidebarColorTwo, this.sidebarFadeOffset)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final IntegerSetting alSpacing = IntegerSetting.builder("al-spacing")
      .defaults("Vertical Spacing", 3, 1, 10, 1)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   protected final FloatSetting alHorizontalSpacing = FloatSetting.builder("al-hor-spacing")
      .defaults("Horizontal Spacing", 3.0F, 1.0F, 5.0F, 0.5F)
      .visibleWhen(this.arrayListEnabled::getValue)
      .build();
   private final GroupSetting moduleListSettings = GroupSetting.builder()
      .defaults(
         "Module List",
         this.arrayListEnabled,
         this.shownCategories,
         this.textSettings,
         this.backgroundSettings,
         this.sidebarSettings,
         this.alSpacing,
         this.alHorizontalSpacing,
         this.alOffset
      )
      .build();
   private final EnumSetting<HUD.Font> customFont = EnumSetting.builder("custom-font-type", HUD.Font.class).defaults("Custom Font", HUD.Font.ROBOTO).build();
   private final FloatSetting scale = FloatSetting.builder("scale")
      .defaults("Scale", 1.0F, 0.5F, 4.0F, 0.1F)
      .onChange((previous, current) -> this.rebuildRequested = true)
      .build();
   private RenderTask rectTask;
   private RenderTask backgroundTask;
   private RenderTask roundedTask;
   private RenderTask textTask;
   private RenderTask titleTask;
   private RenderTask sidebarTask;
   private RenderTask iconTask;
   private RenderTask outlineTask;
   public final List<DraggableHudElement> draggableElements = new ArrayList<>();
   public final List<HudElement> hudElements = new ArrayList<>();
   private ModuleListElement moduleListElement;

   public HUD(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
      this.moduleListElement = new ModuleListElement();
      this.hudElements.add(new WatermarkElement());
      this.hudElements.add(this.moduleListElement);
      this.hudElements.add(new InformationElement());
      this.hudElements.add(new PotionStatusElement());
      this.draggableElements.add(new PitEventTrackerElement("pit_event_tracker", this));
   }

   @Override
   public void onEnable() {
      super.onEnable();
      EventBus eventBus = Moon.getInstance().getEventBus();
      if (eventBus != null) {
         for (HudElement el : this.hudElements) {
            eventBus.subscribe(el);
         }
         for (DraggableHudElement el : this.draggableElements) {
            eventBus.subscribe(el);
         }
      }
      if (this.moduleListElement != null) {
         this.moduleListElement.reloadModules();
      }
      this.setRebuildRequested(true);
   }

   @Override
   public void onDisable() {
      super.onDisable();
      EventBus eventBus = Moon.getInstance().getEventBus();
      if (eventBus != null) {
         for (HudElement el : this.hudElements) {
            eventBus.unsubscribe(el);
         }
         for (DraggableHudElement el : this.draggableElements) {
            eventBus.unsubscribe(el);
         }
      }
   }

   @Subscribe
   public void onTick(TickEvent event) {
      this.setRebuildRequested(true);
   }

   @Subscribe
   public void onResize(ResizeEvent event) {
      this.setRebuildRequested(true);
   }

   @Subscribe
   public void onScale(ScaleEvent event) {
      this.setRebuildRequested(true);
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onRenderFrame(RenderFrameEvent event) {
      FontRenderer fontRenderer = this.customFont.getValue().getFontSupplier().get();
      if (this.rebuildRequested) {
         this.rebuildRequested = false;
         if (this.rectTask != null) {
            this.rectTask.getState().releaseGpuResources();
         }

         if (this.backgroundTask != null) {
            this.backgroundTask.getState().releaseGpuResources();
         }

         if (this.roundedTask != null) {
            this.roundedTask.getState().releaseGpuResources();
         }

         if (this.textTask != null) {
            this.textTask.getState().releaseGpuResources();
         }

         if (this.sidebarTask != null) {
            this.sidebarTask.getState().releaseGpuResources();
         }

         if (this.titleTask != null) {
            this.titleTask.getState().releaseGpuResources();
         }

         if (this.iconTask != null) {
            this.iconTask.getState().releaseGpuResources();
         }

         if (this.outlineTask != null) {
            this.outlineTask.getState().releaseGpuResources();
         }

         this.rectTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
         this.roundedTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
         this.textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withGpuBuffers().retainGpuBuffer().withFontRenderer(fontRenderer).build();
         this.iconTask = RenderTask.builder()
            .withRenderType(RenderType.MSDF)
            .withGpuBuffers()
            .retainGpuBuffer()
            .withFontRenderer(Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_ICONS))
            .build();
         this.backgroundTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
         this.sidebarTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
         this.titleTask = RenderTask.builder().withRenderType(RenderType.MSDF).withGpuBuffers().retainGpuBuffer().withFontRenderer(fontRenderer).build();
         this.outlineTask = RenderTask.builder().withRenderType(RenderType.OUTLINE_ROUNDED_RECTANGLE).withGpuBuffers().retainGpuBuffer().build();

         for (HudElement hudElement : this.hudElements) {
            hudElement.setRectangleTask(this.rectTask);
            hudElement.setRoundedTask(this.roundedTask);
            hudElement.setBackgroundTask(this.backgroundTask);
            hudElement.setTextTask(this.textTask);
         }

         for (DraggableHudElement draggableElement : this.draggableElements) {
            if (draggableElement.isVisible()) {
               DraggableComponentProperties titleProperties = draggableElement.getTitle();
               if (!titleProperties.isTitleDisabled()) {
                  float titleWidth = fontRenderer.getTextWidth(titleProperties.getTitle(), 12.0F);
                  fontRenderer.drawWithShadow(this.titleTask, titleProperties.getTitle(), 12.0F, draggableElement.getX() + draggableElement.getWidth() / 2.0F - titleWidth / 2.0F, draggableElement.getY() + 4.0F, -1);
                  draggableElement.setWidth(Math.max(draggableElement.getWidth(), titleWidth + 8.0F));
               }

               draggableElement.setHeight(Math.max(draggableElement.getHeight(), titleProperties.isTitleDisabled() ? 4.0F : 8.0F + fontRenderer.getLineHeight(12.0F)));
               if (!titleProperties.isBackgroundDisabled()) {
                  this.roundedTask
                     .addCommand(
                        RoundedRectangleCommand.of(
                           draggableElement.getX() - 1.0F, draggableElement.getY() - 1.0F, draggableElement.getWidth() + 2.0F, draggableElement.getHeight() + 2.0F, 6.0F, Integer.MIN_VALUE
                        )
                     );
                  this.backgroundTask
                     .addCommand(
                        RoundedRectangleCommand.of(
                           draggableElement.getX() - 1.0F, draggableElement.getY() - 1.0F, draggableElement.getWidth() + 2.0F, draggableElement.getHeight() + 2.0F, 6.0F, Color.BLACK.getRGB()
                        )
                     );
               }

               draggableElement.setContentX(draggableElement.getX() + 4.0F);
               draggableElement.setContentY(draggableElement.getY() + (titleProperties.isTitleDisabled() ? 4.0F : 8.0F + fontRenderer.getLineHeight(12.0F)));
               draggableElement.setRectangleTask(this.rectTask);
               draggableElement.setBackgroundTask(this.backgroundTask);
               draggableElement.setRoundedTask(this.roundedTask);
               draggableElement.setTextTask(this.titleTask);
            }
         }
      }
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onOverlayRender(OverlayRenderEvent event) {
      if (!mc.gameSettings.showDebugInfo) {
         if (event.getSequence() != OverlayRenderEvent.Sequence.PRE && event.getSequence() == OverlayRenderEvent.Sequence.POST) {
            if (this.rectTask == null) {
               return;
            }
            mc.getFramebuffer().bindFramebuffer(true);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderTransformState.setScale(this.scale.getValue(), this.scale.getValue(), 1.0F);
            this.rectTask.render();
            this.roundedTask.render();
            this.outlineTask.render();
            this.sidebarTask.render();
            this.titleTask.render();
            this.textTask.render();
            this.iconTask.render();
            RenderTransformState.resetScale();
            GlStateManager.enableTexture2D();
            GlStateManager.enableBlend();
            GlStateManager.scale(this.scale.getValue(), this.scale.getValue(), 1.0F);

            for (HudElement hudElement : this.hudElements) {
               hudElement.renderPost();
            }

            for (DraggableHudElement draggableElement : this.draggableElements) {
               if (draggableElement.isVisible()) {
                  draggableElement.renderPost();
               }
            }

            GlStateManager.scale(1.0F / this.scale.getValue(), 1.0F / this.scale.getValue(), 1.0F);
            GlStateManager.enableDepth();
         }
      }
   }

   @Subscribe
   public void onEffectRender(EffectRenderEvent event) {
      if (!mc.gameSettings.showDebugInfo) {
         if (this.backgroundTask != null) {
            RenderTransformState.setScale(this.scale.getValue(), this.scale.getValue(), 1.0F);
            this.backgroundTask.render();
            RenderTransformState.resetScale();
         }
      }
   }

   public void clearModuleList(ModuleListElement element) {
      element.getModuleColors().clear();
   }

   public BooleanSetting getWatermarkEnabled() {
      return this.watermarkEnabled;
   }

   public EnumSetting<HUD.WatermarkMode> getWatermarkMode() {
      return this.watermarkMode;
   }

   public StringSetting getClientName() {
      return this.clientName;
   }

   public BooleanSetting getWatermarkCustomFont() {
      return this.watermarkCustomFont;
   }

   public BooleanSetting getWatermarkColorful() {
      return this.watermarkColorful;
   }

   public BooleanSetting getWatermarkLowAlphaText() {
      return this.watermarkLowAlphaText;
   }

   public EnumSetting<ColorMode> getWatermarkColorMode() {
      return this.watermarkColorMode;
   }

   public ColorSetting getWatermarkColorOne() {
      return this.watermarkColorOne;
   }

   public ColorSetting getWatermarkColorTwo() {
      return this.watermarkColorTwo;
   }

   public GroupSetting getWatermarkSettings() {
      return this.watermarkSettings;
   }

   public BooleanSetting getPotionsEnabled() {
      return this.potionsEnabled;
   }

   public BooleanSetting getPotionsCustomFont() {
      return this.potionsCustomFont;
   }

   public BooleanSetting getPotionsShowIcons() {
      return this.potionsShowIcons;
   }

   public GroupSetting getPotionSettings() {
      return this.potionSettings;
   }

   public BooleanSetting getShowFps() {
      return this.showFps;
   }

   public BooleanSetting getShowBps() {
      return this.showBps;
   }

   public BooleanSetting getShowBuild() {
      return this.showBuild;
   }

   public BooleanSetting getInfoCustomFont() {
      return this.infoCustomFont;
   }

   public EnumSetting<ColorMode> getInfoPrefixColor() {
      return this.infoPrefixColor;
   }

   public ColorSetting getInfoPrefixColorOne() {
      return this.infoPrefixColorOne;
   }

   public ColorSetting getInfoPrefixColorTwo() {
      return this.infoPrefixColorTwo;
   }

   public GroupSetting getInformationSettings() {
      return this.informationSettings;
   }

   public BooleanSetting getPitEventTracker() {
      return this.pitEventTracker;
   }

   public BooleanSetting getPitHideTitle() {
      return this.pitHideTitle;
   }

   public BooleanSetting getPitPadding() {
      return this.pitPadding;
   }

   public BooleanSetting getPitOnlyMajor() {
      return this.pitOnlyMajor;
   }

   public IntegerSetting getPitEventAmount() {
      return this.pitEventAmount;
   }

   public GroupSetting getPitEventTrackerSettings() {
      return this.pitEventTrackerSettings;
   }

   public GroupSetting getDraggableSettings() {
      return this.draggableSettings;
   }

   public BooleanSetting getArrayListEnabled() {
      return this.arrayListEnabled;
   }

   public MultipleChoiceSetting<ModuleCategory> getShownCategories() {
      return this.shownCategories;
   }

   public BooleanSetting getAlCustomFont() {
      return this.alCustomFont;
   }

   public BooleanSetting getAlTextShadow() {
      return this.alTextShadow;
   }

   public EnumSetting<HUD.SuffixType> getSuffixType() {
      return this.suffixType;
   }

   public EnumSetting<HUD.NameFormatting> getNameFormatting() {
      return this.nameFormatting;
   }

   public EnumSetting<HUD.TextColorMode> getTextColor() {
      return this.textColor;
   }

   public ColorSetting getAlColorOne() {
      return this.alColorOne;
   }

   public ColorSetting getAlColorTwo() {
      return this.alColorTwo;
   }

   public FloatSetting getAlFadeOffset() {
      return this.alFadeOffset;
   }

   public FloatSetting getAlFontSize() {
      return this.alFontSize;
   }

   public GroupSetting getTextSettings() {
      return this.textSettings;
   }

   public BooleanSetting getAlBackground() {
      return this.alBackground;
   }

   public IntegerSetting getBackgroundOpacity() {
      return this.backgroundOpacity;
   }

   public BooleanSetting getBackgroundColoredGlow() {
      return this.backgroundColoredGlow;
   }

   public ColorSetting getBackgroundColor() {
      return this.backgroundColor;
   }

   public BooleanSetting getAlRounded() {
      return this.alRounded;
   }

   public IntegerSetting getRoundingFactor() {
      return this.roundingFactor;
   }

   public IntegerSetting getAlOffset() {
      return this.alOffset;
   }

   public GroupSetting getBackgroundSettings() {
      return this.backgroundSettings;
   }

   public BooleanSetting getAlSidebar() {
      return this.alSidebar;
   }

   public EnumSetting<HUD.SidebarType> getSidebarType() {
      return this.sidebarType;
   }

   public EnumSetting<HUD.SidebarColorMode> getSidebarColor() {
      return this.sidebarColor;
   }

   public ColorSetting getSidebarColorOne() {
      return this.sidebarColorOne;
   }

   public ColorSetting getSidebarColorTwo() {
      return this.sidebarColorTwo;
   }

   public FloatSetting getSidebarFadeOffset() {
      return this.sidebarFadeOffset;
   }

   public GroupSetting getSidebarSettings() {
      return this.sidebarSettings;
   }

   public IntegerSetting getAlSpacing() {
      return this.alSpacing;
   }

   public FloatSetting getAlHorizontalSpacing() {
      return this.alHorizontalSpacing;
   }

   public GroupSetting getModuleListSettings() {
      return this.moduleListSettings;
   }

   public EnumSetting<HUD.Font> getCustomFont() {
      return this.customFont;
   }

   public FloatSetting getScale() {
      return this.scale;
   }

   public RenderTask getRectTask() {
      return this.rectTask;
   }

   public RenderTask getBackgroundTask() {
      return this.backgroundTask;
   }

   public RenderTask getRoundedTask() {
      return this.roundedTask;
   }

   public RenderTask getTextTask() {
      return this.textTask;
   }

   public RenderTask getTitleTask() {
      return this.titleTask;
   }

   public RenderTask getSidebarTask() {
      return this.sidebarTask;
   }

   public RenderTask getIconTask() {
      return this.iconTask;
   }

   public RenderTask getOutlineTask() {
      return this.outlineTask;
   }

   public List<DraggableHudElement> getDraggableElements() {
      return this.draggableElements;
   }

   public List<HudElement> getHudElements() {
      return this.hudElements;
   }

   public ModuleListElement getModuleListElement() {
      return this.moduleListElement;
   }

   public HUD setRebuildRequested(boolean rebuildRequested) {
      this.rebuildRequested = rebuildRequested;
      return this;
   }

   public boolean isRebuildRequested() {
      return this.rebuildRequested;
   }

   public HUD setModuleListElement(ModuleListElement moduleListElement) {
      this.moduleListElement = moduleListElement;
      return this;
   }

   public enum Font implements LabelEnum {
      ROBOTO("Roboto", () -> Moon.getInstance().getFontManager().getFont(FontType.ROBOTO_MEDIUM)),
      PRODUCT_SANS("Product Sans", () -> Moon.getInstance().getFontManager().getFont(FontType.PRODUCT_SANS)),
      SF_UI("San Francisco", () -> Moon.getInstance().getFontManager().getFont(FontType.SF_UI)),
      VOLTE("Volte", () -> Moon.getInstance().getFontManager().getFont(FontType.VOLTE_SEMI_BOLD));

      private final String label;
      private final Supplier<FontRenderer> fontSupplier;

      @Override
      public String label() {
         return this.label;
      }

      public Supplier<FontRenderer> getFontSupplier() {
         return this.fontSupplier;
      }

      Font(String label, Supplier<FontRenderer> fontSupplier) {
         this.label = label;
         this.fontSupplier = fontSupplier;
      }
   }

   public enum NameFormatting implements LabelEnum {
      NORMAL("Normal", input -> input),
      LOWER("Lower", String::toLowerCase);

      private final String label;
      private final Function<String, String> formatter;

      @Override
      public String label() {
         return this.label;
      }

      public Function<String, String> getFormatter() {
         return this.formatter;
      }

      NameFormatting(String label, Function<String, String> formatter) {
         this.label = label;
         this.formatter = formatter;
      }
   }

   public enum SidebarColorMode implements LabelEnum {
      SYNC("Sync"),
      STATIC("Static"),
      GRADIENT("Gradient"),
      PULSING("Pulsing");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      SidebarColorMode(String label) {
         this.label = label;
      }
   }

   public enum SidebarType implements LabelEnum {
      RIGHT("Right"),
      LEFT("Left"),
      OUTLINE("Outline");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      SidebarType(String label) {
         this.label = label;
      }
   }

   public enum SuffixType implements LabelEnum {
      SPACE("Space"),
      DASH("Dash (-)"),
      BRACES("Braces ()"),
      NONE("None");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      SuffixType(String label) {
         this.label = label;
      }
   }

   public enum TextColorMode implements LabelEnum {
      STATIC("Static"),
      GRADIENT("Gradient"),
      PULSING("Pulsing"),
      INDIVIDUAL("Individual"),
      MOON("Moon"),
      ASTOLFO("Astolfo");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      TextColorMode(String label) {
         this.label = label;
      }
   }

   public enum WatermarkMode implements LabelEnum {
      TEXT("Text"),
      LOGO("Logo"),
      INFORMATIVE("Informative");

      private final String label;

      @Override
      public String label() {
         return this.label;
      }

      WatermarkMode(String label) {
         this.label = label;
      }
   }
}
