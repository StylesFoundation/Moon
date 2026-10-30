package com.moon.module.impl.visual;

import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.render.BossBarRenderEvent;
import com.moon.events.impl.render.CrosshairRenderEvent;
import com.moon.events.impl.render.HotbarRenderEvent;
import com.moon.events.impl.render.HurtCameraEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.RenderAchievementEvent;
import com.moon.events.impl.render.RenderEntityEvent;
import com.moon.events.impl.render.ResizeEvent;
import com.moon.events.impl.render.ScaleEvent;
import com.moon.events.impl.render.TileEntityRenderEvent;
import com.moon.events.impl.render.TitleRenderEvent;
import com.moon.events.impl.render.VanillaHudRenderEvent;
import com.moon.events.impl.render.VignetteRenderEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.GroupSetting;
import com.moon.module.setting.impl.IntegerSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.texture.TextureCommand;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;

public class Camera extends Module {
   private static final ResourceLocation WIDGETS_TEXTURE = new ResourceLocation("textures/gui/widgets.png");
   public final BooleanSetting limitHudFps = BooleanSetting.builder("limit-hud-fps")
      .defaults("Limit Hud FPS", true)
      .description("Improves performance but breaks vignette")
      .build();
   public final BooleanSetting batchRenderingEntity = BooleanSetting.builder("batch-rendering-entity").defaults("Entity Batching", false).build();
   public final BooleanSetting entityCulling = BooleanSetting.builder("entity-culling").defaults("Entity Culling", false).build();
   public final BooleanSetting particleCulling = BooleanSetting.builder("particle-culling").defaults("Particle Culling", false).build();
   public final BooleanSetting disableArmorStands = BooleanSetting.builder("disable-armor-stands").defaults("Disable Armor Stands", true).build();
   public final BooleanSetting disableParticleLighting = BooleanSetting.builder("disable-particle-lighting")
      .defaults("Disable Particle Lighting", true)
      .build();
   public final GroupSetting optimizationSettings = GroupSetting.builder()
      .defaults("Optimizations", this.entityCulling, this.particleCulling, this.disableArmorStands, this.disableParticleLighting)
      .build();
   public final IntegerSetting maxEntityDistance = IntegerSetting.builder("max-entity-distance").defaults("Max entity distance", 64, 8, 1024, 1).build();
   public final IntegerSetting maxTileEntityDistance = IntegerSetting.builder("max-tile-entity-distance")
      .defaults("Max tile entity distance", 64, 8, 1024, 1)
      .build();
   public final GroupSetting distanceSettings = GroupSetting.builder()
      .defaults("Distance Limiting", this.maxEntityDistance, this.maxTileEntityDistance)
      .build();
   private final BooleanSetting noHurtCam = BooleanSetting.builder("no-hurt-cam").defaults("No Hurt Camera", false).build();
   private final BooleanSetting hideBossBar = BooleanSetting.builder("hide-boss-bar").defaults("Hide Boss Bar", false).build();
   private final BooleanSetting hideTitle = BooleanSetting.builder("hide-title").defaults("Hide Title", false).build();
   private final BooleanSetting hideAchievements = BooleanSetting.builder("hide-achievements").defaults("Hide Achievements", false).build();
   private final BooleanSetting hideScoreIndex = BooleanSetting.builder("hide-score-index").defaults("Hide Score Index", false).build();
   public final BooleanSetting removeSpeedEffect = BooleanSetting.builder("remove-speed-effect").defaults("Remove Speed FoV", false).build();
   public final GroupSetting hideSettings = GroupSetting.builder()
      .defaults("Hide", this.noHurtCam, this.hideBossBar, this.hideTitle, this.hideAchievements, this.hideScoreIndex, this.removeSpeedEffect)
      .build();
   private final Framebuffer hudFramebuffer = new Framebuffer(1, 1, true);
   private boolean renderToFramebuffer = true;
   private boolean rebuildHotbar = true;
   private RenderTask hudTextureTask;
   private RenderTask hotbarTextureTask;
   private int lastHotbarSlot;

   public Camera(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
   }

   @Subscribe
   public void onScale(ScaleEvent event) {
      this.rebuildHotbar = true;
   }

   @Subscribe
   public void onResize(ResizeEvent event) {
      this.rebuildHotbar = true;
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onRenderEntity(RenderEntityEvent event) {
      if (!mc.getRenderManager().Method595()) {
         if (event.getEntity().aC && this.entityCulling.getValue()) {
            event.setCancelled(true);
         } else {
            Entity entity = event.getEntity();
            if (entity.getDistanceSqToEntity(mc.thePlayer) > this.maxEntityDistance.getValue() * this.maxEntityDistance.getValue()) {
               event.setCancelled(true);
            }

            if (entity instanceof EntityArmorStand && this.disableArmorStands.getValue()) {
               event.setCancelled(true);
            }
         }
      }
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onTileEntityRender(TileEntityRenderEvent event) {
      if (!mc.getRenderManager().Method595()) {
         if (event.getTileEntity().i && this.entityCulling.getValue()) {
            event.setCancelled(true);
         } else {
            TileEntity tileEntity = event.getTileEntity();
            EntityPlayerSP player = mc.thePlayer;
            BlockPos pos = tileEntity.getPos();
            boolean notZeroPos = pos.getX() != 0 && pos.getY() != 0 && pos.getZ() != 0;
            if (tileEntity.getDistanceSq(player.posX, player.posY, player.posZ) > this.maxTileEntityDistance.getValue() * this.maxTileEntityDistance.getValue() && notZeroPos) {
               event.setCancelled(true);
            }
         }
      }
   }

   @Subscribe
   public void onTitleRender(TitleRenderEvent event) {
      if (this.hideTitle.getValue()) {
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onBossBarRender(BossBarRenderEvent event) {
      if (this.hideBossBar.getValue()) {
         event.setCancelled(true);
      }
   }

   @Subscribe(priority = Priority.LOW)
   public void onCrosshairRender(CrosshairRenderEvent event) {
      if (this.limitHudFps.getValue()) {
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onHurtCamera(HurtCameraEvent event) {
      if (this.noHurtCam.getValue()) {
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onRenderAchievement(RenderAchievementEvent event) {
      if (this.hideAchievements.getValue()) {
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onHotbarRender(HotbarRenderEvent event) {
      if (this.limitHudFps.getValue()) {
         event.setCancelled(true);
      }
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onOverlayRender(OverlayRenderEvent event) {
      if (event.getSequence() == OverlayRenderEvent.Sequence.POST) {
         this.renderCachedHud();
      }
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onVanillaHudRender(VanillaHudRenderEvent event) {
      this.renderHudToFramebuffer(event);
   }

   @Subscribe(priority = Priority.HIGHEST)
   public void onTick(TickEvent event) {
      if (event.getSequence() == TickEvent.Sequence.POST) {
         this.renderToFramebuffer = true;
      }
   }

   @Subscribe
   public void onVignetteRender(VignetteRenderEvent event) {
      if (this.limitHudFps.getValue()) {
         event.setCancelled(true);
      }
   }

   private static void drawTexturedRect(int x, int y, int u, int v, int width, int height) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
      worldRenderer.pos(x, y + height, 100.0).tex(u * 0.00390625F, (v + height) * 0.00390625F).endVertex();
      worldRenderer.pos(x + width, y + height, 100.0).tex((u + width) * 0.00390625F, (v + height) * 0.00390625F).endVertex();
      worldRenderer.pos(x + width, y, 100.0).tex((u + width) * 0.00390625F, v * 0.00390625F).endVertex();
      worldRenderer.pos(x, y, 100.0).tex(u * 0.00390625F, v * 0.00390625F).endVertex();
      tessellator.draw();
   }

   private void renderHudToFramebuffer(VanillaHudRenderEvent event) {
      if (this.limitHudFps.getValue()) {
         int displayWidth = mc.displayWidth;
         int displayHeight = mc.displayHeight;
         if (this.renderToFramebuffer) {
            this.hudFramebuffer.useDepth = true;
            if (this.hudFramebuffer.Method371(displayWidth, displayHeight)) {
               this.hudFramebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
               this.hudFramebuffer.setFramebufferFilter(9728);
            }

            this.hudFramebuffer.framebufferClear();
            this.hudFramebuffer.bindFramebuffer(true);
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
         } else {
            event.setCancelled(true);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.enableAlpha();
         }
      }
   }

   private void renderCachedHud() {
      if (this.limitHudFps.getValue()) {
         ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
         if (this.renderToFramebuffer) {
            mc.getFramebuffer().bindFramebuffer(true);
            this.renderToFramebuffer = false;
         }

         GlStateManager.enableBlend();
         GlStateManager.enableAlpha();
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.blendFunc(770, 771);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         if (this.rebuildHotbar || mc.thePlayer.inventory.currentItem != this.lastHotbarSlot) {
            if (this.hudTextureTask != null) {
               this.hudTextureTask.getState().releaseGpuResources();
            }

            if (this.hotbarTextureTask != null) {
               this.hotbarTextureTask.getState().releaseGpuResources();
            }

            this.hudTextureTask = RenderTask.builder().withRenderType(RenderType.TEXTURE_COL).withGpuBuffers().retainGpuBuffer().build();
            this.hotbarTextureTask = RenderTask.builder().withRenderType(RenderType.TEXTURE_COL).withGpuBuffers().retainGpuBuffer().build();
            this.hudTextureTask
               .addCommand(TextureCommand.of(0.0F, 0.0F, (float)scaledResolution.getScaledWidth_double(), (float)scaledResolution.getScaledHeight_double(), 0.0F, 1.0F, 1.0F, 0.0F, -1));
            int centerX = scaledResolution.Method237() / 2;
            this.hotbarTextureTask
               .addCommand(TextureCommand.of(centerX - 91, scaledResolution.Method238() - 22, centerX + 91, scaledResolution.Method238(), 0.0F, 0.0F, 0.7109375F, 0.0859375F, -1));
            int hotbarSlotX = centerX - 91 - 1 + mc.thePlayer.inventory.currentItem * 20;
            this.hotbarTextureTask
               .addCommand(TextureCommand.of(hotbarSlotX, scaledResolution.Method238() - 22 - 1, hotbarSlotX + 24, scaledResolution.Method238() - 1, 0.0F, 0.0859375F, 0.09375F, 0.171875F, -1));
            this.rebuildHotbar = false;
            this.lastHotbarSlot = mc.thePlayer.inventory.currentItem;
         }

         mc.getTextureManager().bindTexture(WIDGETS_TEXTURE);
         GlStateManager.enableDepth();
         this.hotbarTextureTask.render();
         GlStateManager.disableDepth();
         this.hudFramebuffer.bindFramebufferTexture();
         this.hudTextureTask.render();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         ScaledResolution crosshairResolution = ScaleHandler.getScaledResolution();
         if (mc.ingameGUI.showCrosshair()) {
            int scaledWidth = crosshairResolution.Method237();
            int scaledHeight = crosshairResolution.Method238();
            mc.getTextureManager().bindTexture(Gui.icons);
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            drawTexturedRect(scaledWidth / 2 - 7, scaledHeight / 2 - 7, 0, 0, 16, 16);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         }

         mc.ingameGUI.renderVignette(mc.thePlayer.getBrightness(mc.timer.renderPartialTicks), ScaleHandler.getScaledResolution());
      }
   }

   public BooleanSetting getLimitHudFps() {
      return this.limitHudFps;
   }

   public BooleanSetting getBatchRenderingEntity() {
      return this.batchRenderingEntity;
   }

   public BooleanSetting getEntityCulling() {
      return this.entityCulling;
   }

   public BooleanSetting getParticleCulling() {
      return this.particleCulling;
   }

   public BooleanSetting getDisableArmorStands() {
      return this.disableArmorStands;
   }

   public BooleanSetting getDisableParticleLighting() {
      return this.disableParticleLighting;
   }

   public GroupSetting getOptimizationSettings() {
      return this.optimizationSettings;
   }

   public IntegerSetting getMaxEntityDistance() {
      return this.maxEntityDistance;
   }

   public IntegerSetting getMaxTileEntityDistance() {
      return this.maxTileEntityDistance;
   }

   public GroupSetting getDistanceSettings() {
      return this.distanceSettings;
   }

   public BooleanSetting getNoHurtCam() {
      return this.noHurtCam;
   }

   public BooleanSetting getHideBossBar() {
      return this.hideBossBar;
   }

   public BooleanSetting getHideTitle() {
      return this.hideTitle;
   }

   public BooleanSetting getHideAchievements() {
      return this.hideAchievements;
   }

   public BooleanSetting getHideScoreIndex() {
      return this.hideScoreIndex;
   }

   public BooleanSetting getRemoveSpeedEffect() {
      return this.removeSpeedEffect;
   }

   public GroupSetting getHideSettings() {
      return this.hideSettings;
   }

   public Framebuffer getHudFramebuffer() {
      return this.hudFramebuffer;
   }

   public boolean isRenderToFramebuffer() {
      return this.renderToFramebuffer;
   }

   public boolean isRebuildHotbar() {
      return this.rebuildHotbar;
   }

   public RenderTask getHudTextureTask() {
      return this.hudTextureTask;
   }

   public RenderTask getHotbarTextureTask() {
      return this.hotbarTextureTask;
   }

   public int getLastHotbarSlot() {
      return this.lastHotbarSlot;
   }
}
