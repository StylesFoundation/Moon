package com.moon.module.impl.visual;

import com.moon.Moon;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.render.HudRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.ScreenShaderEvent;
import com.moon.events.impl.render.ShaderEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.render.shader.blur.impl.GaussianBlurRenderer;
import com.moon.render.shader.blur.impl.KawaseBlurRenderer;
import com.moon.ui.clickgui.modern.ModernClickGui;
import com.moon.ui.clickgui.classic.ClassicClickGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;

public class Effects extends Module {
   private Boolean fogAllowed = null;
   private final Framebuffer kawaseFramebuffer = new Framebuffer(1, 1, false);
   private final Framebuffer gaussianFramebuffer = new Framebuffer(1, 1, false);
   private final Framebuffer[] blurFramebuffers = new Framebuffer[6];
   private final KawaseBlurRenderer kawaseRenderer;
   private final BooleanSetting blur;
   private final BooleanSetting fogBlur;
   private final FloatSetting fogBlurDistance;
   private final IntegerSetting blurIterations;
   private final IntegerSetting blurOffset;
   private final GroupSetting blurSettings;
   private final GaussianBlurRenderer gaussianRenderer;
   private final BooleanSetting shadow;
   private final IntegerSetting shadowIterations;
   private final IntegerSetting shadowOffset;
   private final FloatSetting shadowAlpha;
   private final GroupSetting shadowSettings;

   public Effects(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);

      for (int i = 0; i < this.blurFramebuffers.length; i++) {
         this.blurFramebuffers[i] = new Framebuffer(1, 1, false);
      }

      this.kawaseRenderer = new KawaseBlurRenderer(this.kawaseFramebuffer, this.blurFramebuffers, 3, 3);
      this.blur = BooleanSetting.builder("blur").defaults("Blur", true).build();
      this.fogBlur = BooleanSetting.builder("fog-blur").defaults("Blur fog", false).visibleWhen(() -> false).build();
      this.fogBlurDistance = FloatSetting.builder("fog-blur-distance").defaults("Fog distance", 0.75F, 0.0F, 1.0F, 0.01F).visibleWhen(() -> false).build();
      this.blurIterations = IntegerSetting.builder("blur-iterations")
         .defaults("Iterations", 3, 1, 5, 1)
         .visibleWhen(this.blur::getValue)
         .onChange((previous, current) -> this.kawaseRenderer.setIterations(current))
         .build();
      this.blurOffset = IntegerSetting.builder("blur-offset")
         .defaults("Offset", 3, 0, 10, 1)
         .visibleWhen(this.blur::getValue)
         .onChange((previous, current) -> this.kawaseRenderer.setOffset(current))
         .build();
      this.blurSettings = GroupSetting.builder().defaults("Blur Settings", this.blurIterations, this.blurOffset).visibleWhen(this.blur::getValue).build();
      this.gaussianRenderer = new GaussianBlurRenderer(this.gaussianFramebuffer, this.blurFramebuffers, 3, 4);
      this.shadow = BooleanSetting.builder("shadow").defaults("Shadow", true).build();
      this.shadowIterations = IntegerSetting.builder("shadow-iterations")
         .defaults("Iterations", 3, 1, 5, 1)
         .visibleWhen(this.shadow::getValue)
         .onChange((previous, current) -> this.gaussianRenderer.setIterations(current))
         .build();
      this.shadowOffset = IntegerSetting.builder("shadow-offset")
         .defaults("Offset", 4, 0, 10, 1)
         .visibleWhen(this.shadow::getValue)
         .onChange((previous, current) -> this.gaussianRenderer.setOffset(current.intValue()))
         .build();
      this.shadowAlpha = FloatSetting.builder("shadow-alpha")
         .defaults("Alpha", 1.0F, 0.1F, 1.0F, 0.05F)
         .visibleWhen(this.shadow::getValue)
         .onChange((previous, current) -> this.gaussianRenderer.setAlpha(current))
         .build();
      this.shadowSettings = GroupSetting.builder()
         .defaults("Shadow Settings", this.shadowIterations, this.shadowOffset, this.shadowAlpha)
         .visibleWhen(this.shadow::getValue)
         .build();
   }

   @Subscribe(priority = Priority.HIGH)
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.PRE) {
         this.gaussianRenderer.requestRender();
      }
   }

   @Subscribe
   public void onHudRender(HudRenderEvent event) {
      if (this.blur.getValue()) {
         this.kawaseRenderer.prepare();
      }
   }

   @Subscribe(priority = Priority.HIGH)
   public void onOverlayRender(OverlayRenderEvent event) {
      if (event.getSequence() == OverlayRenderEvent.Sequence.PRE) {
         if (this.fogAllowed == null) {
            this.fogAllowed = System.getProperty("UltraSecretFogging") != null;
         }

         if (!this.fogAllowed) {
            this.fogBlur.setValue(false);
         } else {
            this.fogBlur.visibleWhen(this.blur::getValue);
            this.fogBlurDistance.visibleWhen(() -> this.blur.getValue() && this.fogBlur.getValue());
         }

         ScreenShaderEvent screenShaderEvent = new ScreenShaderEvent();
         GuiScreen currentScreen = mc.currentScreen;
         if (currentScreen instanceof ClassicClickGui) {
            ((ClassicClickGui)currentScreen).updateBlur();
         } else if (mc.currentScreen instanceof ModernClickGui classicClickGUI) {
            classicClickGUI.onRender2D(screenShaderEvent);
         }

         Moon.getInstance().getEventBus().publish(screenShaderEvent);
         GlStateManager.disableLighting();
         GlStateManager.enableBlend();
         GlStateManager.disableDepth();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         if (this.blur.getValue()) {
            this.kawaseRenderer.render();
         } else {
            Moon.getInstance().getEventBus().publish(new ShaderEvent());
         }

         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.disableDepth();
         if (this.shadow.getValue()) {
            this.gaussianRenderer.render();
         }

         GlStateManager.enableDepth();
      }
   }

   public Boolean isFogAllowed() {
      return this.fogAllowed;
   }

   public Framebuffer getKawaseFramebuffer() {
      return this.kawaseFramebuffer;
   }

   public Framebuffer getGaussianFramebuffer() {
      return this.gaussianFramebuffer;
   }

   public Framebuffer[] getBlurFramebuffers() {
      return this.blurFramebuffers;
   }

   public BooleanSetting getFogBlur() {
      return this.fogBlur;
   }

   public FloatSetting getFogBlurDistance() {
      return this.fogBlurDistance;
   }

   public IntegerSetting getBlurIterations() {
      return this.blurIterations;
   }

   public IntegerSetting getBlurOffset() {
      return this.blurOffset;
   }

   public GroupSetting getBlurSettings() {
      return this.blurSettings;
   }

   public IntegerSetting getShadowIterations() {
      return this.shadowIterations;
   }

   public IntegerSetting getShadowOffset() {
      return this.shadowOffset;
   }

   public FloatSetting getShadowAlpha() {
      return this.shadowAlpha;
   }

   public GroupSetting getShadowSettings() {
      return this.shadowSettings;
   }

   public KawaseBlurRenderer getKawaseRenderer() {
      return this.kawaseRenderer;
   }

   public BooleanSetting getBlur() {
      return this.blur;
   }

   public GaussianBlurRenderer getGaussianRenderer() {
      return this.gaussianRenderer;
   }

   public BooleanSetting getShadow() {
      return this.shadow;
   }
}
