package net.minecraft.client.gui;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.moon.Moon;
import com.moon.events.impl.render.BossBarRenderEvent;
import com.moon.events.impl.render.ChatRenderEvent;
import com.moon.events.impl.render.CrosshairRenderEvent;
import com.moon.events.impl.render.HotbarRenderEvent;
import com.moon.events.impl.render.HudRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.ScoreboardRenderEvent;
import com.moon.events.impl.render.ShaderEvent;
import com.moon.events.impl.render.TitleRenderEvent;
import com.moon.events.impl.render.VanillaHudRenderEvent;
import com.moon.events.impl.render.VignetteRenderEvent;
import com.moon.module.impl.visual.Camera;
import com.moon.module.impl.visual.CustomMinecraft;
import com.moon.module.impl.visual.Effects;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.world.border.WorldBorder;
import net.optifine.CustomColors;

public class GuiIngame extends Gui {
   private static final ResourceLocation vignetteTexPath = new ResourceLocation("textures/misc/vignette.png");
   private static final ResourceLocation widgetsTexPath = new ResourceLocation("textures/gui/widgets.png");
   private static final ResourceLocation pumpkinBlurTexPath = new ResourceLocation("textures/misc/pumpkinblur.png");
   private final Random rand = new Random();
   private final Minecraft mc;
   private final RenderItem itemRenderer;
   private final GuiNewChat persistantChatGUI;
   private final GuiOverlayDebug overlayDebug;
   private final GuiSpectator spectatorGui;
   private final GuiPlayerTabOverlay overlayPlayerList;
   public float prevVignetteBrightness = 1.0F;
   private int updateCounter;
   private String recordPlaying = "";
   private int recordPlayingUpFor;
   private boolean recordIsPlaying;
   private int remainingHighlightTicks;
   private ItemStack highlightingItemStack;
   private int titlesTimer;
   private String displayedTitle = "";
   private String displayedSubTitle = "";
   private int titleFadeIn;
   private int titleDisplayTime;
   private int titleFadeOut;
   private int playerHealth = 0;
   private int lastPlayerHealth = 0;
   private long lastSystemTime = 0L;
   private long healthUpdateCounter = 0L;
   private Camera streamIndicator;
   private Effects H;
   private CustomMinecraft I;

   public GuiIngame(Minecraft minecraft) {
      this.mc = minecraft;
      this.itemRenderer = minecraft.getRenderItem();
      this.overlayDebug = new GuiOverlayDebug(minecraft);
      this.spectatorGui = new GuiSpectator(minecraft);
      this.persistantChatGUI = new GuiNewChat(minecraft);
      this.overlayPlayerList = new GuiPlayerTabOverlay(minecraft, this);
      this.setDefaultTitlesTimes();
   }

   public void setDefaultTitlesTimes() {
      this.titleFadeIn = 10;
      this.titleDisplayTime = 70;
      this.titleFadeOut = 20;
   }

   public void renderGameOverlay(float float1) {
      ScaledResolution var4 = new ScaledResolution(this.mc);
      int var5 = var4.Method237();
      int var6 = var4.Method238();
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0, var4.getScaledWidth_double(), var4.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
      Moon.getInstance().getEventBus().publish(new HudRenderEvent());
      GlStateManager.clear(256);
      OverlayRenderEvent var7 = new OverlayRenderEvent(OverlayRenderEvent.Sequence.PRE);
      Moon.getInstance().getEventBus().publish(var7);
      if (this.H == null) {
         this.H = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }

      if (!this.H.isEnabled()) {
         Moon.getInstance().getEventBus().publish(new ShaderEvent());
      }

      GlStateManager.enableBlend();
      VanillaHudRenderEvent var8 = new VanillaHudRenderEvent();
      Moon.getInstance().getEventBus().publish(var8);
      if (!var7.isCancelled() && !var8.isCancelled()) {
         if (this.streamIndicator == null) {
            this.streamIndicator = Moon.getInstance().getModuleManager().getModule(Camera.class);
         }

         VignetteRenderEvent var9 = new VignetteRenderEvent();
         Moon.getInstance().getEventBus().publish(var9);
         if (!var9.isCancelled()) {
            if (Config.isVignetteEnabled()) {
               this.renderVignette(this.mc.thePlayer.getBrightness(float1), var4);
            } else {
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            }
         } else {
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         }

         ItemStack var10 = this.mc.thePlayer.inventory.armorItemInSlot(3);
         if (this.mc.gameSettings.thirdPersonView == 0 && var10 != null && var10.getItem() == Item.getItemFromBlock(Blocks.pumpkin)) {
            this.renderPumpkinOverlay(var4);
         }

         if (!this.mc.thePlayer.isPotionActive(Potion.confusion)) {
            float var11 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * float1;
            if (var11 > 0.0F) {
               this.renderPortal(var11, var4);
            }
         }

         if (this.mc.playerController.isSpectator()) {
            this.spectatorGui.renderTooltip(var4, float1);
         } else {
            this.renderTooltip(var4, float1);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(icons);
         GlStateManager.enableBlend();
         CrosshairRenderEvent var20 = new CrosshairRenderEvent();
         Moon.getInstance().getEventBus().publish(var20);
         if (this.showCrosshair() && !var20.isCancelled()) {
            GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
            GlStateManager.enableAlpha();
            this.drawTexturedModalRect(var5 / 2 - 7, var6 / 2 - 7, 0, 0, 16, 16);
         }

         GlStateManager.enableAlpha();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         BossBarRenderEvent var12 = new BossBarRenderEvent();
         Moon.getInstance().getEventBus().publish(var12);
         if (!var12.isCancelled()) {
            this.mc.mcProfiler.startSection("bossHealth");
            this.renderBossHealth();
            this.mc.mcProfiler.endSection();
         }

         if (this.mc.playerController.shouldDrawHUD()) {
            this.renderPlayerStats(var4);
         }

         GlStateManager.disableBlend();
         if (this.mc.thePlayer.getSleepTimer() > 0) {
            this.mc.mcProfiler.startSection("sleep");
            GlStateManager.disableDepth();
            GlStateManager.disableAlpha();
            int var13 = this.mc.thePlayer.getSleepTimer();
            float var14 = var13 / 100.0F;
            if (var14 > 1.0F) {
               var14 = 1.0F - (var13 - 100) / 10.0F;
            }

            int var15 = (int)(220.0F * var14) << 24 | 1052704;
            drawRect(0, 0, var5, var6, var15);
            GlStateManager.enableAlpha();
            GlStateManager.enableDepth();
            this.mc.mcProfiler.endSection();
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         int var21 = var5 / 2 - 91;
         if (this.mc.thePlayer.isRidingHorse()) {
            this.renderHorseJumpBar(var4, var21);
         } else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
            this.renderExpBar(var4, var21);
         }

         if (this.mc.gameSettings.heldItemTooltips && !this.mc.playerController.isSpectator()) {
            this.renderSelectedItem(var4);
         } else if (this.mc.thePlayer.isSpectator()) {
            this.spectatorGui.renderSelectedItem(var4);
         }

         if (this.mc.isDemo()) {
            this.renderDemo(var4);
         }

         if (this.mc.gameSettings.showDebugInfo) {
            this.overlayDebug.renderDebugInfo(var4);
         }

         if (this.recordPlayingUpFor > 0) {
            this.mc.mcProfiler.startSection("overlayMessage");
            float var22 = this.recordPlayingUpFor - float1;
            int var24 = (int)(var22 * 255.0F / 20.0F);
            if (var24 > 255) {
               var24 = 255;
            }

            if (var24 > 8) {
               GlStateManager.pushMatrix();
               GlStateManager.translate(var5 / 2, var6 - 68, 0.0F);
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               int var16 = 16777215;
               if (this.recordIsPlaying) {
                  var16 = MathHelper.hsvToRGB(var22 / 50.0F, 0.7F, 0.6F) & 16777215;
               }

               this.getFontRenderer()
                  .drawString(this.recordPlaying, -this.getFontRenderer().getStringWidth(this.recordPlaying) / 2, -4.0F, var16 + (var24 << 24 & 0xFF000000));
               GlStateManager.disableBlend();
               GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
         }

         TitleRenderEvent var23 = new TitleRenderEvent();
         Moon.getInstance().getEventBus().publish(var23);
         if (!var23.isCancelled() && this.titlesTimer > 0) {
            this.mc.mcProfiler.startSection("titleAndSubtitle");
            float var25 = this.titlesTimer - float1;
            int var27 = 255;
            if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
               var27 = (int)((this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut - var25) * 255.0F / this.titleFadeIn);
            }

            if (this.titlesTimer <= this.titleFadeOut) {
               var27 = (int)(var25 * 255.0F / this.titleFadeOut);
            }

            var27 = MathHelper.clamp_int(var27, 0, 255);
            if (var27 > 8) {
               GlStateManager.pushMatrix();
               GlStateManager.translate(var5 / 2, var6 / 2, 0.0F);
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               GlStateManager.pushMatrix();
               GlStateManager.scale(4.0F, 4.0F, 4.0F);
               int var17 = var27 << 24 & 0xFF000000;
               this.getFontRenderer()
                  .drawString(this.displayedTitle, -this.getFontRenderer().getStringWidth(this.displayedTitle) / 2, -10.0F, 16777215 | var17, true);
               GlStateManager.popMatrix();
               GlStateManager.pushMatrix();
               GlStateManager.scale(2.0F, 2.0F, 2.0F);
               this.getFontRenderer()
                  .drawString(this.displayedSubTitle, -this.getFontRenderer().getStringWidth(this.displayedSubTitle) / 2, 5.0F, 16777215 | var17, true);
               GlStateManager.popMatrix();
               GlStateManager.disableBlend();
               GlStateManager.popMatrix();
            }

            this.mc.mcProfiler.endSection();
         }

         Scoreboard var26 = this.mc.theWorld.getScoreboard();
         ScoreObjective var29 = null;
         ScorePlayerTeam var30 = var26.getPlayersTeam(this.mc.thePlayer.getName());
         if (var30 != null) {
            EnumChatFormatting var18 = var30.getChatFormat();
            if (var18 != null) {
               int var19 = var18.getColorIndex();
               if (var19 >= 0) {
                  var29 = var26.getObjectiveInDisplaySlot(3 + var19);
               }
            }
         }

         ScoreObjective var31 = var29 != null ? var29 : var26.getObjectiveInDisplaySlot(1);
         if (var31 != null) {
            this.renderScoreboard(var31, var4);
         } else {
            ScoreboardRenderEvent var33 = new ScoreboardRenderEvent(null);
            Moon.getInstance().getEventBus().publish(var33);
         }

         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.disableAlpha();
         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, var6 - 48, 0.0F);
         this.mc.mcProfiler.startSection("chat");
         ChatRenderEvent var34 = new ChatRenderEvent();
         Moon.getInstance().getEventBus().publish(var34);
         if (!var34.isCancelled()) {
            this.persistantChatGUI.drawChat(this.updateCounter);
         }

         this.mc.mcProfiler.endSection();
         GlStateManager.popMatrix();
         var31 = var26.getObjectiveInDisplaySlot(0);
         if (!this.mc.gameSettings.keyBindPlayerList.isKeyDown()
            || this.mc.isIntegratedServerRunning() && this.mc.thePlayer.sendQueue.getPlayerInfoMap().size() <= 1 && var31 == null) {
            this.overlayPlayerList.updatePlayerList(false);
         } else {
            this.overlayPlayerList.updatePlayerList(true);
            this.overlayPlayerList.renderPlayerlist(var5, var26, var31);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableLighting();
         GlStateManager.enableAlpha();
      }

      Moon.getInstance().getEventBus().publish(new OverlayRenderEvent(OverlayRenderEvent.Sequence.POST));
   }

   protected void renderTooltip(ScaledResolution scaledResolution, float float2) {
      if (this.mc.getRenderViewEntity() instanceof EntityPlayer var5) {
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         HotbarRenderEvent var10 = new HotbarRenderEvent();
         Moon.getInstance().getEventBus().publish(var10);
         if (!var10.isCancelled()) {
            this.mc.getTextureManager().bindTexture(widgetsTexPath);
            int var7 = scaledResolution.Method237() / 2;
            float var8 = this.zLevel;
            this.zLevel = -90.0F;
            this.drawTexturedModalRect(var7 - 91, scaledResolution.Method238() - 22, 0, 0, 182, 22);
            this.drawTexturedModalRect(var7 - 91 - 1 + var5.inventory.currentItem * 20, scaledResolution.Method238() - 22 - 1, 0, 22, 24, 22);
            this.zLevel = var8;
         }

         GlStateManager.enableRescaleNormal();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.enableGUIStandardItemLighting();

         for (int var11 = 0; var11 < 9; var11++) {
            int var12 = scaledResolution.Method237() / 2 - 90 + var11 * 20 + 2;
            int var9 = scaledResolution.Method238() - 16 - 3;
            this.renderHotbarItem(var11, var12, var9, float2, var5);
         }

         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableBlend();
      }
   }

   public void renderHorseJumpBar(ScaledResolution scaledResolution, int integer) {
      this.mc.mcProfiler.startSection("jumpBar");
      this.mc.getTextureManager().bindTexture(Gui.icons);
      float var5 = this.mc.thePlayer.getHorseJumpPower();
      short var6 = 182;
      int var7 = (int)(var5 * 183.0F);
      int var8 = scaledResolution.Method238() - 32 + 3;
      this.drawTexturedModalRect(integer, var8, 0, 84, var6, 5);
      if (var7 > 0) {
         this.drawTexturedModalRect(integer, var8, 0, 89, var7, 5);
      }

      this.mc.mcProfiler.endSection();
   }

   public void renderExpBar(ScaledResolution scaledResolution, int integer) {
      this.mc.mcProfiler.startSection("expBar");
      this.mc.getTextureManager().bindTexture(Gui.icons);
      if (this.mc.thePlayer.xpBarCap() > 0) {
         short var6 = 182;
         int var7 = (int)(this.mc.thePlayer.experience * 183.0F);
         int var8 = scaledResolution.Method238() - 32 + 3;
         this.drawTexturedModalRect(integer, var8, 0, 64, var6, 5);
         if (var7 > 0) {
            this.drawTexturedModalRect(integer, var8, 0, 69, var7, 5);
         }
      }

      this.mc.mcProfiler.endSection();
      if (this.mc.thePlayer.experienceLevel > 0) {
         this.mc.mcProfiler.startSection("expLevel");
         int var10 = 8453920;
         if (Config.isCustomColors()) {
            var10 = CustomColors.getExpBarTextColor(var10);
         }

         String var11 = this.mc.thePlayer.experienceLevel + "";
         int var12 = (scaledResolution.Method237() - this.getFontRenderer().getStringWidth(var11)) / 2;
         int var9 = scaledResolution.Method238() - 31 - 4;
         this.getFontRenderer().drawString(var11, var12 + 1, var9, 0);
         this.getFontRenderer().drawString(var11, var12 - 1, var9, 0);
         this.getFontRenderer().drawString(var11, var12, var9 + 1, 0);
         this.getFontRenderer().drawString(var11, var12, var9 - 1, 0);
         this.getFontRenderer().drawString(var11, var12, var9, var10);
         this.mc.mcProfiler.endSection();
      }
   }

   public void renderSelectedItem(ScaledResolution scaledResolution) {
      this.mc.mcProfiler.startSection("selectedItemName");
      if (this.remainingHighlightTicks > 0 && this.highlightingItemStack != null) {
         String var4 = this.highlightingItemStack.getDisplayName();
         if (this.highlightingItemStack.hasDisplayName()) {
            var4 = EnumChatFormatting.ITALIC + var4;
         }

         int var5 = (scaledResolution.Method237() - this.getFontRenderer().getStringWidth(var4)) / 2;
         int var6 = scaledResolution.Method238() - 59;
         if (!this.mc.playerController.shouldDrawHUD()) {
            var6 += 14;
         }

         int var7 = (int)(this.remainingHighlightTicks * 256.0F / 10.0F);
         if (var7 > 255) {
            var7 = 255;
         }

         if (var7 > 0) {
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.getFontRenderer().drawStringWithShadow(var4, var5, var6, 16777215 + (var7 << 24));
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
         }
      }

      this.mc.mcProfiler.endSection();
   }

   public void renderDemo(ScaledResolution scaledResolution) {
      this.mc.mcProfiler.startSection("demo");
      String var4;
      if (this.mc.theWorld.getTotalWorldTime() >= 120500L) {
         var4 = I18n.format("demo.demoExpired");
      } else {
         var4 = I18n.format("demo.remainingTime", StringUtils.Method604((int)(120500L - this.mc.theWorld.getTotalWorldTime())));
      }

      int var5 = this.getFontRenderer().getStringWidth(var4);
      this.getFontRenderer().drawStringWithShadow(var4, scaledResolution.Method237() - var5 - 10, 5.0F, 16777215);
      this.mc.mcProfiler.endSection();
   }

   public boolean showCrosshair() {
      if (this.mc.gameSettings.showDebugInfo && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo) {
         return false;
      }

      if (this.mc.playerController.isSpectator()) {
         if (this.mc.pointedEntity != null) {
            return true;
         } else if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var1 = this.mc.objectMouseOver.getBlockPos();
            return this.mc.theWorld.getTileEntity(var1) instanceof IInventory;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   private void renderScoreboard(ScoreObjective scoreObjective, ScaledResolution scaledResolution) {
      ScoreboardRenderEvent var5 = new ScoreboardRenderEvent(scoreObjective);
      Moon.getInstance().getEventBus().publish(var5);
      if (!var5.isCancelled()) {
         if (this.I == null) {
            this.I = Moon.getInstance().getModuleManager().getModule(CustomMinecraft.class);
         }

         Scoreboard var6 = scoreObjective.getScoreboard();
         Collection<Score> var7 = var6.getSortedScores(scoreObjective);
         List<Score> var8 = Lists.newArrayList(
            var7.stream().filter(score -> score.getPlayerName() != null && !score.getPlayerName().startsWith("#")).collect(Collectors.toList())
         );
         if (var8.size() > 15) {
            var7 = Lists.newArrayList(Iterables.skip(var8, var7.size() - 15));
         } else {
            var7 = var8;
         }

         int var9 = this.getFontRenderer().getStringWidth(scoreObjective.getDisplayName());

         for (Score var11 : var7) {
            ScorePlayerTeam var12 = var6.getPlayersTeam(var11.getPlayerName());
            String var13 = ScorePlayerTeam.formatPlayerName(var12, var11.getPlayerName()) + ": " + EnumChatFormatting.RED + var11.getScorePoints();
            var9 = Math.max(var9, this.getFontRenderer().getStringWidth(var13));
         }

         int var24 = var7.size() * this.getFontRenderer().FONT_HEIGHT;
         int var25 = scaledResolution.Method238() / 2 + var24 / 3;
         int var26 = scaledResolution.Method237() - var9 - 3;
         int var14 = 0;

         for (Score var16 : var7) {
            var14++;
            ScorePlayerTeam var17 = var6.getPlayersTeam(var16.getPlayerName());
            String var18 = ScorePlayerTeam.formatPlayerName(var17, var16.getPlayerName());
            String var19 = "" + EnumChatFormatting.RED + var16.getScorePoints();
            int var20 = var25 - var14 * this.getFontRenderer().FONT_HEIGHT;
            int var21 = scaledResolution.Method237() - 3 + 2;
            if (this.I.isEnabled() && this.I.getReplaceIps().getValue()) {
               String var22 = this.I.getDetectionStrings().getValue();
               if (var18.replaceAll("§[0-9]", "").replaceAll("§[a-z]", "").replaceAll("[^\\x00-\\x7F]+", "").equals(var22)) {
                  var18 = "§9§lmoonclient.xyz";
               }
            }

            drawRect(var26 - 2, var20, var21, var20 + this.getFontRenderer().FONT_HEIGHT, 1342177280);
            this.getFontRenderer().drawString(var18, var26, var20, -1);
            if (!this.streamIndicator.isEnabled() || !this.streamIndicator.getHideScoreIndex().getValue()) {
               this.getFontRenderer().drawString(var19, var21 - this.getFontRenderer().getStringWidth(var19), var20, -1);
            }

            if (var14 == var7.size()) {
               String var27 = scoreObjective.getDisplayName();
               drawRect(var26 - 2, var20 - this.getFontRenderer().FONT_HEIGHT - 1, var21, var20 - 1, 1610612736);
               drawRect(var26 - 2, var20 - 1, var21, var20, 1342177280);
               if (this.I.isEnabled()
                  && this.I.getReplaceIps().getValue()
                  && var27.replaceAll("§[0-9]", "").replaceAll("§[a-z]", "").replaceAll("[^\\x00-\\x7F]+", "").equals(this.I.getDetectionStrings().getValue())) {
                  var27 = "§9§lmoonclient.xyz";
               }

               this.getFontRenderer()
                  .drawString(var27, var26 + var9 / 2 - this.getFontRenderer().getStringWidth(var27) / 2, var20 - this.getFontRenderer().FONT_HEIGHT, -1);
            }
         }
      }
   }

   private void renderPlayerStats(ScaledResolution scaledResolution) {
      if (this.mc.getRenderViewEntity() instanceof EntityPlayer var4) {
         int var36 = MathHelper.ceiling_float_int(var4.getHealth());
         boolean var6 = this.healthUpdateCounter > this.updateCounter && (this.healthUpdateCounter - this.updateCounter) / 3L % 2L == 1L;
         if (var36 < this.playerHealth && var4.hurtResistantTime > 0) {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.healthUpdateCounter = this.updateCounter + 20;
         } else if (var36 > this.playerHealth && var4.hurtResistantTime > 0) {
            this.lastSystemTime = Minecraft.getSystemTime();
            this.healthUpdateCounter = this.updateCounter + 10;
         }

         if (Minecraft.getSystemTime() - this.lastSystemTime > 1000L) {
            this.playerHealth = var36;
            this.lastPlayerHealth = var36;
            this.lastSystemTime = Minecraft.getSystemTime();
         }

         this.playerHealth = var36;
         int var7 = this.lastPlayerHealth;
         this.rand.setSeed(this.updateCounter * 312871L);
         FoodStats var9 = var4.getFoodStats();
         int var10 = var9.getFoodLevel();
         int var11 = var9.getPrevFoodLevel();
         IAttributeInstance var12 = var4.getEntityAttribute(SharedMonsterAttributes.maxHealth);
         int var13 = scaledResolution.Method237() / 2 - 91;
         int var14 = scaledResolution.Method237() / 2 + 91;
         int var15 = scaledResolution.Method238() - 39;
         float var16 = (float)var12.getAttributeValue();
         float var17 = var4.getAbsorptionAmount();
         int var18 = MathHelper.ceiling_float_int((var16 + var17) / 2.0F / 10.0F);
         int var19 = Math.max(10 - (var18 - 2), 3);
         int var20 = var15 - (var18 - 1) * var19 - 10;
         float var21 = var17;
         int var22 = var4.getTotalArmorValue();
         int var23 = -1;
         if (var4.isPotionActive(Potion.regeneration)) {
            var23 = this.updateCounter % MathHelper.ceiling_float_int(var16 + 5.0F);
         }

         this.mc.mcProfiler.startSection("armor");

         for (int var24 = 0; var24 < 10; var24++) {
            if (var22 > 0) {
               int var25 = var13 + var24 * 8;
               if (var24 * 2 + 1 < var22) {
                  this.drawTexturedModalRect(var25, var20, 34, 9, 9, 9);
               }

               if (var24 * 2 + 1 == var22) {
                  this.drawTexturedModalRect(var25, var20, 25, 9, 9, 9);
               }

               if (var24 * 2 + 1 > var22) {
                  this.drawTexturedModalRect(var25, var20, 16, 9, 9, 9);
               }
            }
         }

         this.mc.mcProfiler.endStartSection("health");

         for (int var37 = MathHelper.ceiling_float_int((var16 + var17) / 2.0F) - 1; var37 >= 0; var37--) {
            byte var39 = 16;
            if (var4.isPotionActive(Potion.poison)) {
               var39 += 36;
            } else if (var4.isPotionActive(Potion.wither)) {
               var39 += 72;
            }

            byte var26 = 0;
            if (var6) {
               var26 = 1;
            }

            int var27 = MathHelper.ceiling_float_int((var37 + 1) / 10.0F) - 1;
            int var28 = var13 + var37 % 10 * 8;
            int var29 = var15 - var27 * var19;
            if (var36 <= 4) {
               var29 += this.rand.nextInt(2);
            }

            if (var37 == var23) {
               var29 -= 2;
            }

            byte var30 = 0;
            if (var4.worldObj.getWorldInfo().isHardcoreModeEnabled()) {
               var30 = 5;
            }

            this.drawTexturedModalRect(var28, var29, 16 + var26 * 9, 9 * var30, 9, 9);
            if (var6) {
               if (var37 * 2 + 1 < var7) {
                  this.drawTexturedModalRect(var28, var29, var39 + 54, 9 * var30, 9, 9);
               }

               if (var37 * 2 + 1 == var7) {
                  this.drawTexturedModalRect(var28, var29, var39 + 63, 9 * var30, 9, 9);
               }
            }

            if (var21 <= 0.0F) {
               if (var37 * 2 + 1 < var36) {
                  this.drawTexturedModalRect(var28, var29, var39 + 36, 9 * var30, 9, 9);
               }

               if (var37 * 2 + 1 == var36) {
                  this.drawTexturedModalRect(var28, var29, var39 + 45, 9 * var30, 9, 9);
               }
            } else {
               if (var21 == var17 && var17 % 2.0F == 1.0F) {
                  this.drawTexturedModalRect(var28, var29, var39 + 153, 9 * var30, 9, 9);
               } else {
                  this.drawTexturedModalRect(var28, var29, var39 + 144, 9 * var30, 9, 9);
               }

               var21 -= 2.0F;
            }
         }

         Entity var38 = var4.ridingEntity;
         if (var38 == null) {
            this.mc.mcProfiler.endStartSection("food");

            for (int var43 = 0; var43 < 10; var43++) {
               int var45 = var15;
               byte var48 = 16;
               byte var51 = 0;
               if (var4.isPotionActive(Potion.hunger)) {
                  var48 += 36;
                  var51 = 13;
               }

               if (var4.getFoodStats().getSaturationLevel() <= 0.0F && this.updateCounter % (var10 * 3 + 1) == 0) {
                  var45 = var15 + (this.rand.nextInt(3) - 1);
               }

               if (false) {
                  var51 = 1;
               }

               int var53 = var14 - var43 * 8 - 9;
               this.drawTexturedModalRect(var53, var45, 16 + var51 * 9, 27, 9, 9);
               if (false) {
                  if (var43 * 2 + 1 < var11) {
                     this.drawTexturedModalRect(var53, var45, var48 + 54, 27, 9, 9);
                  }

                  if (var43 * 2 + 1 == var11) {
                     this.drawTexturedModalRect(var53, var45, var48 + 63, 27, 9, 9);
                  }
               }

               if (var43 * 2 + 1 < var10) {
                  this.drawTexturedModalRect(var53, var45, var48 + 36, 27, 9, 9);
               }

               if (var43 * 2 + 1 == var10) {
                  this.drawTexturedModalRect(var53, var45, var48 + 45, 27, 9, 9);
               }
            }
         } else if (var38 instanceof EntityLivingBase var40) {
            this.mc.mcProfiler.endStartSection("mountHealth");
            int var42 = (int)Math.ceil(var40.getHealth());
            int var47 = (int)(var40.getMaxHealth() + 0.5F) / 2;
            if (var47 > 30) {
               var47 = 30;
            }

            int var50 = var15;

            for (byte var52 = 0; var47 > 0; var52 += 20) {
               int var31 = Math.min(var47, 10);
               var47 -= var31;

               for (int var32 = 0; var32 < var31; var32++) {
                  byte var33 = 52;
                  byte var34 = 0;
                  if (false) {
                     var34 = 1;
                  }

                  int var35 = var14 - var32 * 8 - 9;
                  this.drawTexturedModalRect(var35, var50, var33 + var34 * 9, 9, 9, 9);
                  if (var32 * 2 + 1 + var52 < var42) {
                     this.drawTexturedModalRect(var35, var50, 88, 9, 9, 9);
                  }

                  if (var32 * 2 + 1 + var52 == var42) {
                     this.drawTexturedModalRect(var35, var50, 97, 9, 9, 9);
                  }
               }

               var50 -= 10;
            }
         }

         this.mc.mcProfiler.endStartSection("air");
         if (var4.isInsideOfMaterial(Material.water)) {
            int var41 = this.mc.thePlayer.getAir();
            int var44 = MathHelper.ceiling_double_int((var41 - 2) * 10.0 / 300.0);
            int var46 = MathHelper.ceiling_double_int(var41 * 10.0 / 300.0) - var44;

            for (int var49 = 0; var49 < var44 + var46; var49++) {
               if (var49 < var44) {
                  this.drawTexturedModalRect(var14 - var49 * 8 - 9, var20, 16, 18, 9, 9);
               } else {
                  this.drawTexturedModalRect(var14 - var49 * 8 - 9, var20, 25, 18, 9, 9);
               }
            }
         }

         this.mc.mcProfiler.endSection();
      }
   }

   private void renderBossHealth() {
      if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
         BossStatus.statusBarTime--;
         int var5 = new ScaledResolution(this.mc).Method237();
         short var6 = 182;
         int var7 = var5 / 2 - 91;
         int var8 = (int)(BossStatus.healthScale * 183.0F);
         byte var9 = 12;
         this.drawTexturedModalRect(var7, var9, 0, 74, var6, 5);
         this.drawTexturedModalRect(var7, var9, 0, 74, var6, 5);
         if (var8 > 0) {
            this.drawTexturedModalRect(var7, var9, 0, 79, var8, 5);
         }

         String var10 = BossStatus.bossName;
         this.getFontRenderer().drawStringWithShadow(var10, var5 / 2 - this.getFontRenderer().getStringWidth(var10) / 2, 2.0F, 16777215);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.mc.getTextureManager().bindTexture(icons);
      }
   }

   private void renderPumpkinOverlay(ScaledResolution scaledResolution) {
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableAlpha();
      this.mc.getTextureManager().bindTexture(pumpkinBlurTexPath);
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      var5.begin(7, DefaultVertexFormats.POSITION_TEX);
      var5.pos(0.0, scaledResolution.Method238(), -90.0).tex(0.0, 1.0).endVertex();
      var5.pos(scaledResolution.Method237(), scaledResolution.Method238(), -90.0).tex(1.0, 1.0).endVertex();
      var5.pos(scaledResolution.Method237(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
      var5.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
      var4.draw();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public void renderVignette(float float1, ScaledResolution scaledResolution) {
      if (!Config.isVignetteEnabled()) {
         GlStateManager.enableDepth();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      } else {
         float1 = MathHelper.clamp_float(1.0F - float1, 0.0F, 1.0F);
         WorldBorder var5 = this.mc.theWorld.getWorldBorder();
         float var6 = (float)var5.getClosestDistance(this.mc.thePlayer);
         double var7 = Math.min(var5.getResizeSpeed() * var5.getWarningTime() * 1000.0, Math.abs(var5.getTargetSize() - var5.getDiameter()));
         double var9 = Math.max(var5.getWarningDistance(), var7);
         if (var6 < var9) {
            var6 = 1.0F - (float)(var6 / var9);
         } else {
            var6 = 0.0F;
         }

         this.prevVignetteBrightness = (float)(this.prevVignetteBrightness + (float1 - this.prevVignetteBrightness) * 0.01);
         GlStateManager.disableDepth();
         GlStateManager.depthMask(false);
         GlStateManager.tryBlendFuncSeparate(0, 769, 1, 0);
         if (var6 > 0.0F) {
            GlStateManager.color(0.0F, var6, var6, 1.0F);
         } else {
            GlStateManager.color(this.prevVignetteBrightness, this.prevVignetteBrightness, this.prevVignetteBrightness, 1.0F);
         }

         this.mc.getTextureManager().bindTexture(vignetteTexPath);
         Tessellator var11 = Tessellator.getInstance();
         WorldRenderer var12 = var11.getWorldRenderer();
         var12.begin(7, DefaultVertexFormats.POSITION_TEX);
         var12.pos(0.0, scaledResolution.Method238(), -90.0).tex(0.0, 1.0).endVertex();
         var12.pos(scaledResolution.Method237(), scaledResolution.Method238(), -90.0).tex(1.0, 1.0).endVertex();
         var12.pos(scaledResolution.Method237(), 0.0, -90.0).tex(1.0, 0.0).endVertex();
         var12.pos(0.0, 0.0, -90.0).tex(0.0, 0.0).endVertex();
         var11.draw();
         GlStateManager.depthMask(true);
         GlStateManager.enableDepth();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      }
   }

   private void renderPortal(float float1, ScaledResolution scaledResolution) {
      if (float1 < 1.0F) {
         float1 *= float1;
         float1 = float1 * float1 * 0.8F + 0.2F;
      }

      GlStateManager.disableAlpha();
      GlStateManager.disableDepth();
      GlStateManager.depthMask(false);
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(1.0F, 1.0F, 1.0F, float1);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      TextureAtlasSprite var5 = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.portal.getDefaultState());
      float var6 = var5.getMinU();
      float var7 = var5.getMinV();
      float var8 = var5.getMaxU();
      float var9 = var5.getMaxV();
      Tessellator var10 = Tessellator.getInstance();
      WorldRenderer var11 = var10.getWorldRenderer();
      var11.begin(7, DefaultVertexFormats.POSITION_TEX);
      var11.pos(0.0, scaledResolution.Method238(), -90.0).tex(var6, var9).endVertex();
      var11.pos(scaledResolution.Method237(), scaledResolution.Method238(), -90.0).tex(var8, var9).endVertex();
      var11.pos(scaledResolution.Method237(), 0.0, -90.0).tex(var8, var7).endVertex();
      var11.pos(0.0, 0.0, -90.0).tex(var6, var7).endVertex();
      var10.draw();
      GlStateManager.depthMask(true);
      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderHotbarItem(int integer1, int integer2, int integer3, float float4, EntityPlayer entityPlayer) {
      ItemStack var8 = entityPlayer.inventory.mainInventory[integer1];
      if (var8 != null) {
         float var9 = var8.animationsToGo - float4;
         if (var9 > 0.0F) {
            GlStateManager.pushMatrix();
            float var10 = 1.0F + var9 / 5.0F;
            GlStateManager.translate(integer2 + 8, integer3 + 12, 0.0F);
            GlStateManager.scale(1.0F / var10, (var10 + 1.0F) / 2.0F, 1.0F);
            GlStateManager.translate(-(integer2 + 8), -(integer3 + 12), 0.0F);
         }

         this.itemRenderer.renderItemAndEffectIntoGUI(var8, integer2, integer3);
         if (var9 > 0.0F) {
            GlStateManager.popMatrix();
         }

         this.itemRenderer.renderItemOverlays(this.mc.fontRendererObj, var8, integer2, integer3);
      }
   }

   public void updateTick() {
      if (this.recordPlayingUpFor > 0) {
         this.recordPlayingUpFor--;
      }

      if (this.titlesTimer > 0) {
         this.titlesTimer--;
         if (this.titlesTimer <= 0) {
            this.displayedTitle = "";
            this.displayedSubTitle = "";
         }
      }

      this.updateCounter++;
      if (this.mc.thePlayer != null) {
         ItemStack var3 = this.mc.thePlayer.inventory.getCurrentItem();
         if (var3 == null) {
            this.remainingHighlightTicks = 0;
         } else if (this.highlightingItemStack != null
            && var3.getItem() == this.highlightingItemStack.getItem()
            && ItemStack.areItemStackTagsEqual(var3, this.highlightingItemStack)
            && (var3.isItemStackDamageable() || var3.getMetadata() == this.highlightingItemStack.getMetadata())) {
            if (this.remainingHighlightTicks > 0) {
               this.remainingHighlightTicks--;
            }
         } else {
            this.remainingHighlightTicks = 40;
         }

         this.highlightingItemStack = var3;
      }
   }

   public void setRecordPlayingMessage(String string) {
      this.setRecordPlaying(I18n.format("record.nowPlaying", string), true);
   }

   public void setRecordPlaying(String string, boolean boolean2) {
      this.recordPlaying = string;
      this.recordPlayingUpFor = 60;
      this.recordIsPlaying = boolean2;
   }

   public void displayTitle(String string1, String string2, int integer3, int integer4, int integer5) {
      if (string1 == null && string2 == null && integer3 < 0 && integer4 < 0 && integer5 < 0) {
         this.displayedTitle = "";
         this.displayedSubTitle = "";
         this.titlesTimer = 0;
      } else if (string1 != null) {
         this.displayedTitle = string1;
         this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
      } else if (string2 != null) {
         this.displayedSubTitle = string2;
      } else {
         if (integer3 >= 0) {
            this.titleFadeIn = integer3;
         }

         if (integer4 >= 0) {
            this.titleDisplayTime = integer4;
         }

         if (integer5 >= 0) {
            this.titleFadeOut = integer5;
         }

         if (this.titlesTimer > 0) {
            this.titlesTimer = this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut;
         }
      }
   }

   public void setRecordPlaying(IChatComponent iChatComponent, boolean boolean2) {
      this.setRecordPlaying(iChatComponent.getUnformattedText(), boolean2);
   }

   public GuiNewChat getChatGUI() {
      return this.persistantChatGUI;
   }

   public int getUpdateCounter() {
      return this.updateCounter;
   }

   public FontRenderer getFontRenderer() {
      return this.mc.fontRendererObj;
   }

   public GuiSpectator getSpectatorGui() {
      return this.spectatorGui;
   }

   public GuiPlayerTabOverlay getTabList() {
      return this.overlayPlayerList;
   }

   public void resetPlayersOverlayFooterHeader() {
      this.overlayPlayerList.resetFooterHeader();
   }
}
