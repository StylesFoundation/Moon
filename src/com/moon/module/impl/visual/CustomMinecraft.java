package com.moon.module.impl.visual;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.moon.Moon;
import com.moon.events.impl.render.ChatRenderEvent;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.ScoreboardRenderEvent;
import com.moon.events.impl.render.ScreenShaderEvent;
import com.moon.events.impl.render.ResizeEvent;
import com.moon.events.impl.render.ScaleEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ModularListSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.util.client.PreciseTicker;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class CustomMinecraft extends Module {
   private final BooleanSetting replaceIps = BooleanSetting.builder("replace-ips")
      .defaults("Replace ips", false)
      .description("Replaces ips in scoreboards with defined texts.")
      .build();
   private final ModularListSetting detectionStrings = ModularListSetting.builder("detection-strings")
      .defaults("Detection strings", "hypixel.net")
      .defaultValue("What string to replace in the scoreboard")
      .visibleWhen(this.replaceIps::getValue)
      .build();
   private final BooleanSetting scoreboard = BooleanSetting.builder("scoreboard").defaults("Scoreboard", true).build();
   public final BooleanSetting chat = BooleanSetting.builder("chat").defaults("Chat", true).onChange((previous, current) -> {
      if (mc.ingameGUI != null) {
         mc.ingameGUI.getChatGUI().refreshChat();
      }
   }).build();
   private Camera cameraModule;
   private Effects effectsModule;
   private boolean needsRebuild;
   private RenderTask textTask;
   private RenderTask backgroundTask;
   private RenderTask blurTask;
   private ScoreObjective currentObjective;
   private int lastChatLineCount;
   private int chatLineCount;
   private float lastScoreboardX;
   private float lastScoreboardY;
   private float scoreboardX;
   private float scoreboardY;

   public CustomMinecraft(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
      PreciseTicker ticker = new PreciseTicker();
      new Thread(() -> {
         while (true) {
            ticker.runAt(20);
            this.needsRebuild = true;
         }
      }).start();
   }

   @Override
   public void onDisable() {
      this.refreshChat();
   }

   @Override
   public void onEnable() {
      this.refreshChat();
   }

   @Subscribe
   public void onResize(ResizeEvent event) {
      this.needsRebuild = true;
   }

   @Subscribe
   public void onScale(ScaleEvent event) {
      this.needsRebuild = true;
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onOverlayRender(OverlayRenderEvent event) {
      if (this.cameraModule == null) {
         this.cameraModule = Moon.getInstance().getModuleManager().getModule(Camera.class);
      }

      if (event.getSequence() == OverlayRenderEvent.Sequence.PRE) {
         if (this.needsRebuild) {
            FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
            if (this.textTask != null) {
               this.textTask.getState().releaseGpuResources();
            }

            if (this.backgroundTask != null) {
               this.backgroundTask.getState().releaseGpuResources();
            }

            if (this.blurTask != null) {
               this.blurTask.getState().releaseGpuResources();
            }

            this.textTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).retainGpuBuffer().withGpuBuffers().build();
            this.backgroundTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).retainGpuBuffer().withGpuBuffers().build();
            this.blurTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).retainGpuBuffer().withGpuBuffers().build();
            if (this.chat.getValue()) {
               this.rebuildChat(mc.ingameGUI.getUpdateCounter());
            }

            if (this.scoreboard.getValue() && this.currentObjective != null) {
               this.rebuildScoreboard(this.currentObjective);
            }

            this.needsRebuild = false;
         }
      } else if (event.getSequence() == OverlayRenderEvent.Sequence.POST && this.hasBuiltTasks()) {
         this.renderOverlay();
      }
   }

   @Subscribe(priority = Priority.LOW)
   public void onEffectRender(EffectRenderEvent event) {
      if (this.blurTask != null && (this.chat.getValue() || this.scoreboard.getValue())) {
         this.blurTask.render();
      }
   }

   @Subscribe
   public void onScreenShader(ScreenShaderEvent event) {
      if (this.lastChatLineCount != this.chatLineCount) {
         if (this.effectsModule == null) {
            this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
         }

         this.effectsModule.getGaussianRenderer().requestRender();
         this.effectsModule.getKawaseRenderer().setNeedsRender(true);
         this.lastChatLineCount = this.chatLineCount;
      }

      if (this.lastScoreboardX != this.scoreboardX || this.lastScoreboardY != this.scoreboardY) {
         if (this.effectsModule == null) {
            this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
         }

         this.effectsModule.getGaussianRenderer().requestRender();
         this.effectsModule.getKawaseRenderer().setNeedsRender(true);
         this.lastScoreboardX = this.scoreboardX;
         this.lastScoreboardY = this.scoreboardY;
      }
   }

   @Subscribe
   public void onScoreboardRender(ScoreboardRenderEvent event) {
      if (this.scoreboard.getValue()) {
         if (this.cameraModule == null) {
            this.cameraModule = Moon.getInstance().getModuleManager().getModule(Camera.class);
         }

         this.currentObjective = event.getObjective();
         event.setCancelled(true);
      }
   }

   @Subscribe
   public void onChatRender(ChatRenderEvent event) {
      if (this.chat.getValue()) {
         event.setCancelled(true);
      }
   }

   private void rebuildChat(int updateCounter) {
      GuiNewChat chatGUI = mc.ingameGUI.getChatGUI();
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      if (mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
         int lineCount = chatGUI.getLineCount();
         boolean isChatOpen = false;
         int renderedLineCount = 0;
         int chatLinesSize = chatGUI.Method496().size();
         float chatOpacity = mc.gameSettings.chatOpacity * 0.9F + 0.1F;
         int chatWidth = MathHelper.ceiling_float_int(chatGUI.getChatWidth());
         if (chatLinesSize > 0) {
            if (chatGUI.getChatOpen()) {
               isChatOpen = true;
            }

            for (int i = 0; i + chatGUI.Method497() < chatGUI.Method496().size() && i < lineCount; i++) {
               ChatLine chatLine = chatGUI.Method496().get(i + chatGUI.Method497());
               if (chatLine != null) {
                  int timeSinceUpdate = updateCounter - chatLine.getUpdatedCounter();
                  if (timeSinceUpdate < 200 || isChatOpen) {
                     double percent = timeSinceUpdate / 200.0;
                     percent = MathHelper.clamp_double((1.0 - percent) * 10.0, 0.0, 1.0);
                     percent *= percent;
                     int alpha = (int)(255.0 * percent);
                     if (isChatOpen) {
                        alpha = 255;
                     }

                     alpha = (int)(alpha * chatOpacity);
                     renderedLineCount++;
                     if (alpha > 3) {
                        float lineYOffset = -i * fontRenderer.getLineHeight(9.0F);
                        String formattedText = chatLine.getChatComponent().getFormattedText();
                        float y = scaledResolution.Method238() + (lineYOffset - 8.0F - 27.0F);
                        fontRenderer.drawWithShadow(this.textTask, formattedText, 9.0F, 5.0F, y, -1);
                     }
                  }
               }
            }

            if (renderedLineCount > 0) {
               float height = renderedLineCount * fontRenderer.getLineHeight(9.0F) + 5.0F;
               float y = scaledResolution.Method238() - 27 - height + 4.5F;
               this.backgroundTask.addCommand(RoundedRectangleCommand.of(2.0F, y, chatWidth + 3, height, 4.0F, Integer.MIN_VALUE));
               this.blurTask.addCommand(RoundedRectangleCommand.of(2.0F, y, chatWidth + 3, height, 4.0F, Color.BLACK.getRGB()));
            }
         }

         this.chatLineCount = renderedLineCount;
      }
   }

   private void rebuildScoreboard(ScoreObjective objective) {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.INTER_MEDIUM);
      ScaledResolution scaledResolution = ScaleHandler.getScaledResolution();
      Scoreboard scoreboard = objective.getScoreboard();
      Collection<Score> scores = scoreboard.getSortedScores(objective);
      List<Score> filteredScores = Lists.newArrayList(
         scores.stream().filter(score -> score.getPlayerName() != null && !score.getPlayerName().startsWith("#")).collect(Collectors.toList())
      );
      if (filteredScores.size() > 15) {
         scores = Lists.newArrayList(Iterables.skip(filteredScores, scores.size() - 15));
      } else {
         scores = filteredScores;
      }

      float maxWidth = fontRenderer.getTextWidth(objective.getDisplayName(), 9.0F);

      for (Score score : scores) {
         ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
         String playerName = ScorePlayerTeam.formatPlayerName(team, score.getPlayerName());
         EnumChatFormatting formatting = EnumChatFormatting.RED;
         int scorePoints = score.getScorePoints();
         String scoreText = playerName + ": " + formatting + scorePoints;
         maxWidth = Math.max(maxWidth, fontRenderer.getTextWidth(scoreText, 9.0F));
      }

      float lineHeight = fontRenderer.getLineHeight(9.0F);
      int totalHeight = (int)Math.ceil(scores.size() * lineHeight);
      int startY = (int)Math.ceil(scaledResolution.Method238() / 2.0F + totalHeight / 3.0F + 100.0F);
      int startX = (int)Math.ceil(scaledResolution.Method237() - maxWidth - 3.0F - 6.0F);
      int scoreIndex = 0;

      for (Score score : scores) {
         scoreIndex++;
         ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
         String playerName = ScorePlayerTeam.formatPlayerName(team, score.getPlayerName());
         String pointsText = "" + EnumChatFormatting.RED + score.getScorePoints();
         int y = (int)Math.ceil(startY - scoreIndex * lineHeight);
         int rightX = (int)Math.ceil(scaledResolution.Method237() - 3 + 2 - 6.0F);
         if (this.replaceIps.getValue()
            && playerName.replaceAll("§[0-9]", "").replaceAll("§[a-z]", "").replaceAll("[^\\x00-\\x7F]+", "").equals(this.detectionStrings.getValue())) {
            playerName = "§9§lmoonclient.xyz";
         }

         fontRenderer.draw(this.textTask, playerName, 9.0F, startX, y, -1, true);
         if (!this.cameraModule.isEnabled() || !this.cameraModule.getHideScoreIndex().getValue()) {
            fontRenderer.draw(this.textTask, pointsText, 9.0F, rightX - fontRenderer.getTextWidth(pointsText, 9.0F), y, -1, true);
         }

         if (scoreIndex == scores.size()) {
            String displayName = objective.getDisplayName();
            if (this.replaceIps.getValue()
               && displayName.replaceAll("§[0-9]", "").replaceAll("§[a-z]", "").replaceAll("[^\\x00-\\x7F]+", "").equals(this.detectionStrings.getValue())) {
               displayName = "§9§lmoonclient.xyz";
            }

            fontRenderer.draw(this.textTask, displayName, 9.0F, startX + maxWidth / 2.0F - fontRenderer.getTextWidth(displayName, 9.0F) / 2.0F, y - lineHeight, -1, true);
         }
      }

      float padding = 3.0F;
      this.scoreboardX = startX - padding;
      this.scoreboardY = startY - scoreIndex * lineHeight - lineHeight - padding;
      float width = scaledResolution.Method237() - 3 + 2 - this.scoreboardX - 6.0F + padding;
      float height = (scores.size() + 1) * lineHeight + 6.0F;
      this.backgroundTask.addCommand(RoundedRectangleCommand.of(this.scoreboardX, this.scoreboardY, width, height, 4.0F, Integer.MIN_VALUE));
      this.blurTask.addCommand(RoundedRectangleCommand.of(this.scoreboardX, this.scoreboardY, width, height, 4.0F, Color.BLACK.getRGB()));
   }

   private void renderOverlay() {
      GlStateManager.enableBlend();
      this.backgroundTask.render();
      this.textTask.render();
      GlStateManager.disableBlend();
   }

   private boolean hasBuiltTasks() {
      return this.textTask != null && this.backgroundTask != null;
   }

   private void refreshChat() {
      if (mc.ingameGUI != null) {
         mc.ingameGUI.getChatGUI().refreshChat();
      }
   }

   public BooleanSetting getReplaceIps() {
      return this.replaceIps;
   }

   public ModularListSetting getDetectionStrings() {
      return this.detectionStrings;
   }

   public BooleanSetting getScoreboard() {
      return this.scoreboard;
   }

   public BooleanSetting getChat() {
      return this.chat;
   }

   public Camera getCameraModule() {
      return this.cameraModule;
   }

   public Effects getEffectsModule() {
      return this.effectsModule;
   }

   public boolean isNeedsRebuild() {
      return this.needsRebuild;
   }

   public RenderTask getTextTask() {
      return this.textTask;
   }

   public RenderTask getBackgroundTask() {
      return this.backgroundTask;
   }

   public RenderTask getBlurTask() {
      return this.blurTask;
   }

   public ScoreObjective getCurrentObjective() {
      return this.currentObjective;
   }

   public int getLastChatLineCount() {
      return this.lastChatLineCount;
   }

   public int getChatLineCount() {
      return this.chatLineCount;
   }

   public float getLastScoreboardX() {
      return this.lastScoreboardX;
   }

   public float getLastScoreboardY() {
      return this.lastScoreboardY;
   }

   public float getScoreboardX() {
      return this.scoreboardX;
   }

   public float getScoreboardY() {
      return this.scoreboardY;
   }
}
