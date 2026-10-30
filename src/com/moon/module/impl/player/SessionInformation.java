package com.moon.module.impl.player;

import com.moon.Moon;
import com.moon.account.Account;
import com.moon.account.SessionServerStats;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.network.ConnectServerEvent;
import com.moon.events.impl.network.PacketEvent;
import com.moon.events.impl.render.EffectRenderEvent;
import com.moon.events.impl.render.OverlayRenderEvent;
import com.moon.events.impl.render.ResizeEvent;
import com.moon.events.impl.render.ScaleEvent;
import com.moon.events.priority.Priority;
import com.moon.events.subscription.Subscribe;
import com.moon.handlers.NotificationHandler;
import com.moon.handlers.ScaleHandler;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.impl.player.sessioninformation.KillDeathDetector;
import com.moon.module.impl.player.sessioninformation.ServerFavicon;
import com.moon.module.impl.player.sessioninformation.WrappedSessionMeta;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.module.impl.visual.Effects;
import com.moon.module.impl.visual.HUD;
import com.moon.module.impl.visual.hud.base.ColorMode;
import com.moon.module.setting.impl.BooleanSetting;
import com.moon.module.setting.impl.ButtonSetting;
import com.moon.module.setting.impl.ColorSetting;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.module.setting.impl.ModularListSetting;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.render.RenderTask;
import com.moon.render.RenderTransformState;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.shape.RoundedRectangleCommand;
import com.moon.render.command.impl.texture.RoundedTextureCommand;
import com.moon.render.font.manager.FontType;
import com.moon.render.font.msdf.MsdfFontRenderer;
import com.moon.render.font.msdf.MsdfGlyphRenderer;
import com.moon.ui.theme.ColorScheme;
import com.moon.util.client.PreciseTicker;
import com.moon.util.render.GradientUtil;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ServerListEntryNormal;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class SessionInformation extends Module {
   private final Map<String, KillDeathDetector> killDeathDetectors = new HashMap<>();
   private final ModularListSetting serverListSetting = ModularListSetting.builder("servers")
      .defaults("Detection types", "hypixel.net")
      .defaultValue("Make sure this equals the server you are on!")
      .onEntriesChanged(server -> this.ensureSessionInformationFolder())
      .build();
   private final BooleanSetting autoDetectServerSetting = BooleanSetting.builder("auto-detect-server").defaults("Auto detect server", true).build();
   private final FloatSetting renderScaleSetting = FloatSetting.builder("render-scale").defaults("Render scale", 1.0F, 0.25F, 4.0F, 0.1F).build();
   protected final BooleanSetting colorfulBackgroundSetting = BooleanSetting.builder("colorful").defaults("Colorful background", false).build();
   protected final BooleanSetting lowAlphaTextSetting = BooleanSetting.builder("low-alpha-text").defaults("Low alpha text", false).build();
   private final BooleanSetting syncWithModuleListSetting = BooleanSetting.builder("sync-with-module-list").defaults("Sync with module-list", true).build();
   private final ColorSetting colorSetting = ColorSetting.builder("color").defaults("Color", Color.RED.getRGB()).build();
   private final ButtonSetting openServerDirectoryButton = ButtonSetting.builder("open-servers")
      .defaults("Open server directory")
      .onClick(
         (ignored1, ignored2) -> {
            File folder = this.ensureSessionInformationFolder();

            try {
               Desktop.getDesktop().open(folder);
            } catch (IOException e) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Session Information")
                        .message("Failed to open session information directory")
                        .type(NotificationType.ERROR)
                        .duration(2000L)
                        .build()
                  );
            }
         }
      )
      .build();
   private final ButtonSetting reloadDetectionsButton = ButtonSetting.builder("reload-detections")
      .defaults("Reload detections")
      .onClick(
         (ignored1, ignored2) -> {
            this.killDeathDetectors.forEach((server, detector) -> detector.reload());
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Session Information")
                     .message("Reloaded all detection entries")
                     .type(NotificationType.SUCCESS)
                     .duration(2000L)
                     .build()
               );
         }
      )
      .build();
   private final FloatSetting xPositionSetting = FloatSetting.builder("x").defaults("X", 0.0F, 0.0F, 100000.0F, 1.0F).visibleWhen(() -> false).build();
   private final FloatSetting yPositionSetting = FloatSetting.builder("y").defaults("Y", 0.0F, 0.0F, 100000.0F, 1.0F).visibleWhen(() -> false).build();
   private final ExecutorService saveExecutor = Executors.newSingleThreadExecutor();
   private final Map<ServerData, ServerFavicon> serverFavicons = new HashMap<>();
   private RenderTask backgroundRenderTask;
   private RenderTask titleRenderTask;
   private RenderTask iconRenderTask;
   private RenderTask serverIconRenderTask;
   private RenderTask outlineRenderTask;
   private final PreciseTicker ticker = new PreciseTicker();
   private ClickGUI clickGuiModule;
   private Effects effectsModule;
   private HUD hudModule;
   private float dragOffsetX;
   private float dragOffsetY;
   private boolean dragging;
   private int kills;
   private int deaths;
   private int pendingKills;
   private int pendingDeaths;
   private long lastUpdateTime;
   private long playTime;
   private long currentSessionPlayTime;
   private String formattedPlayTime = "0s";
   private boolean needsRebuild = true;
   private int lastColor = -2147483647;
   private float panelWidth;
   private float panelHeight;
   private ColorScheme colorScheme = ColorScheme.darkScheme(-1);
   private final List<WrappedSessionMeta> sessionMetaEntries = List.of(
      WrappedSessionMeta.builder().setIcon("\uf889").setMessage(() -> this.kills + " kills").setOffset(2.0F).build(),
      WrappedSessionMeta.builder().setIcon("\uf89a").setMessage(() -> this.deaths + " deaths").setOffset(2.0F).build(),
      WrappedSessionMeta.builder().setIcon("\ue8b5").setMessage(() -> this.formattedPlayTime).setOffset(2.0F).build()
   );

   public SessionInformation(String name, String displayName, String description, ModuleCategory category) {
      super(name, displayName, description, category);
      this.ensureSessionInformationFolder();
      new Thread(() -> {
         while (true) {
            this.ticker.runAt(Display.getDesktopDisplayMode().getFrequency());
            this.needsRebuild = true;
         }
      }, "Moon Notification Ticker").start();
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onOverlayRender(OverlayRenderEvent event) {
      boolean defaultIcon = true;
      ResourceLocation iconLocation = null;
      if (mc.getCurrentServerData() != null) {
         ServerFavicon serverFavicon = this.serverFavicons.computeIfAbsent(mc.getCurrentServerData(), ServerFavicon::new);
         if (serverFavicon.hasIcon()) {
            iconLocation = serverFavicon.getIconLocation();
            defaultIcon = false;
         }
      }

      if (defaultIcon) {
         iconLocation = ServerListEntryNormal.UNKNOWN_SERVER;
      }

      if (event.getSequence() == OverlayRenderEvent.Sequence.POST) {
         Tuple<Integer, Integer> mousePos = this.getMousePosition();
         int mouseX = mousePos.getFirst();
         int mouseY = mousePos.getSecond();
         boolean mouseOver = this.isMouseOver(
            mouseX,
            mouseY,
            this.xPositionSetting.getValue(),
            this.yPositionSetting.getValue(),
            this.panelWidth * this.renderScaleSetting.getValue(),
            this.panelHeight * this.renderScaleSetting.getValue()
         );
         if (!Mouse.isButtonDown(0)) {
            this.dragging = false;
         }

         if ((mouseOver || this.dragging) && Mouse.isButtonDown(0) && mc.currentScreen instanceof GuiChat) {
            if (!this.dragging) {
               this.dragOffsetX = this.xPositionSetting.getValue() - mouseX;
               this.dragOffsetY = this.yPositionSetting.getValue() - mouseY;
               this.dragging = true;
            }

            this.xPositionSetting.setValue(mouseX + this.dragOffsetX);
            this.yPositionSetting.setValue(mouseY + this.dragOffsetY);
            this.effectsModule.getKawaseRenderer().setNeedsRender(true);
            this.effectsModule.getGaussianRenderer().requestRender();
            this.needsRebuild = true;
         }

         if (this.backgroundRenderTask != null && this.titleRenderTask != null && this.iconRenderTask != null && this.serverIconRenderTask != null) {
            RenderTransformState.setScale(this.renderScaleSetting.getValue(), this.renderScaleSetting.getValue(), 1.0F);
            GlStateManager.enableBlend();
            this.backgroundRenderTask.render();
            this.titleRenderTask.render();
            this.iconRenderTask.render();
            mc.getTextureManager().bindTexture(iconLocation);
            this.serverIconRenderTask.render();
            GlStateManager.bindTexture(0);
            RenderTransformState.resetScale();
         }

         if (this.needsRebuild) {
            MsdfFontRenderer symbolFontRenderer = (MsdfFontRenderer)Moon.getInstance().getFontManager().getFont(FontType.MATERIAL_SYMBOLS_OUTLINED_REGULAR);
            if (this.clickGuiModule == null) {
               this.clickGuiModule = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
            }

            if (this.effectsModule == null) {
               this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
            }

            float scale = this.renderScaleSetting.getValue();
            float panelX = this.xPositionSetting.getValue() * 1.0F / scale;
            float panelY = this.yPositionSetting.getValue() * 1.0F / scale;
            float serverIconX = panelX + 6.0F;
            float serverIconY = panelY + 6.0F;
            float titleWidth = NotificationHandler.textFont.getTextWidth("§lSession Information", 7.75F);
            this.panelWidth = titleWidth + 12.0F + 4.0F + 16.566668F;
            this.panelHeight = 84.26667F;
            if (this.backgroundRenderTask != null) {
               this.backgroundRenderTask.getState().releaseGpuResources();
            }

            if (this.titleRenderTask != null) {
               this.titleRenderTask.getState().releaseGpuResources();
            }

            if (this.iconRenderTask != null) {
               this.iconRenderTask.getState().releaseGpuResources();
            }

            if (this.serverIconRenderTask != null) {
               this.serverIconRenderTask.getState().releaseGpuResources();
            }

            if (this.outlineRenderTask != null) {
               this.outlineRenderTask.getState().releaseGpuResources();
            }

            this.backgroundRenderTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
            this.outlineRenderTask = RenderTask.builder().withRenderType(RenderType.ROUNDED_RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
            this.iconRenderTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(symbolFontRenderer).withGpuBuffers().retainGpuBuffer().build();
            this.titleRenderTask = RenderTask.builder()
               .withRenderType(RenderType.MSDF)
               .withGpuBuffers()
               .retainGpuBuffer()
               .withFontRenderer(NotificationHandler.textFont)
               .build();
            this.serverIconRenderTask = RenderTask.builder().withRenderType(RenderType.TEXTURE_ROUND).withGpuBuffers().retainGpuBuffer().build();
            this.backgroundRenderTask
               .addCommand(
                  RoundedRectangleCommand.of(
                     panelX,
                     panelY,
                     this.panelWidth,
                     this.panelHeight,
                     8.0F,
                     new RenderColor(Color.BLACK.getRGB())
                        .withAlphaFraction(this.effectsModule.isEnabled() && this.effectsModule.getBlur().getValue() ? 0.275F : 0.4F)
                        .getRGB()
                  )
               );
            this.outlineRenderTask
               .addCommand(
                  RoundedRectangleCommand.of(panelX - 0.45F, panelY - 0.45F, this.panelWidth + 0.9F, this.panelHeight + 0.9F, 8.5F, Color.BLACK.getRGB())
               );
            NotificationHandler.textFont
               .drawInternal(
                  this.titleRenderTask,
                  "Session Information",
                  7.75F,
                  panelX + 6.0F + 16.566668F + 4.0F,
                  panelY + 6.0F + 8.283334F - NotificationHandler.textFont.getLineHeight(7.75F) / 2.0F + 0.5F,
                  -1,
                  true,
                  false,
                  0.0F
               );
            this.serverIconRenderTask
               .addCommand(RoundedTextureCommand.of(serverIconX, serverIconY, serverIconX + 16.566668F, serverIconY + 16.566668F, 0.0F, 0.0F, 1.0F, 1.0F, 4.5F, -1));
            float entryY = serverIconY + 16.566668F + 2.0F;

            for (WrappedSessionMeta sessionMeta : this.sessionMetaEntries) {
               String message = sessionMeta.getMessage().get();
               String icon = sessionMeta.getIcon();
               float offset = sessionMeta.getOffset();
               MsdfGlyphRenderer glyph = symbolFontRenderer.getFont().getProvider().getGlyph(icon.charAt(0));
               float advance = glyph.getAdvance() * 9.0F;
               float planeHeight = glyph.getPlaneHeight() * 9.0F;
               NotificationHandler.textFont.draw(this.titleRenderTask, message, 6.25F, panelX + 6.0F + 16.566668F + 4.0F, entryY + 5.5F, -1);
               symbolFontRenderer.draw(this.iconRenderTask, icon, 9.0F, panelX + 6.0F + 8.283334F - advance / 2.0F, entryY + 8.283334F - planeHeight / 4.0F - offset, -2236963);
               this.backgroundRenderTask.addCommand(RoundedRectangleCommand.of(panelX + 6.0F, entryY, 16.566668F, 16.566668F, 4.5F, 1073741824));
               entryY += 18.566668F;
            }

            this.needsRebuild = false;
         }
      }
   }

   @Subscribe(priority = Priority.LOWEST)
   public void onEffectRender(EffectRenderEvent event) {
      if (this.outlineRenderTask != null) {
         RenderTransformState.setScale(this.renderScaleSetting.getValue(), this.renderScaleSetting.getValue(), 1.0F);
         this.outlineRenderTask.render();
         RenderTransformState.resetScale();
      }
   }

   @Subscribe
   public void onConnectServer(ConnectServerEvent event) {
      this.kills = 0;
      this.deaths = 0;
      this.currentSessionPlayTime = 0L;
      this.playTime = 0L;
   }

   @Subscribe
   public void onResize(ResizeEvent event) {
      this.needsRebuild = true;
   }

   @Subscribe
   public void onScale(ScaleEvent event) {
      this.needsRebuild = true;
   }

   @Subscribe
   public void onTick(TickEvent event) {
      ServerData serverData = mc.getCurrentServerData();
      if (this.autoDetectServerSetting.getValue()) {
         for (String serverEntry : this.serverListSetting.getEntries()) {
            if (serverData != null && serverData.serverIP.contains(serverEntry)) {
               this.serverListSetting.setValue(serverEntry);
               break;
            }
         }
      }

      if (System.currentTimeMillis() - this.lastUpdateTime >= 1000L && mc.thePlayer != null) {
         this.currentSessionPlayTime += 1000L;
         Account account = Moon.getInstance().getCurrentAccount();
         if (account != null && serverData != null) {
            Map<String, SessionServerStats> sessions = account.getSession().getSessions();
            SessionServerStats stats = sessions.computeIfAbsent(serverData.serverIP, server -> new SessionServerStats());
            this.kills = stats.getKills();
            this.deaths = stats.getDeaths();
            this.playTime = stats.getPlayTime();
            String currentServer = this.serverListSetting.getValue();
            if (serverData.serverIP.contains(currentServer)) {
               this.kills = this.kills + this.pendingKills;
               this.deaths = this.deaths + this.pendingDeaths;
               this.playTime = this.playTime + this.currentSessionPlayTime;
            }

            stats.setKills(this.kills);
            stats.setDeaths(this.deaths);
            stats.setPlayTime(this.playTime);
            this.pendingKills = 0;
            this.pendingDeaths = 0;
            this.currentSessionPlayTime = 0L;
            this.saveExecutor.submit(() -> Moon.getInstance().getAccountStorage().save(Moon.getInstance().getAccountManager().getAccounts()));
         } else if (serverData != null) {
            this.kills = this.kills + this.pendingKills;
            this.deaths = this.deaths + this.pendingDeaths;
            this.playTime = this.playTime + this.currentSessionPlayTime;
            this.pendingKills = 0;
            this.pendingDeaths = 0;
            this.currentSessionPlayTime = 0L;
         }

         LocalDateTime playDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.playTime), ZoneId.systemDefault());
         LocalDateTime epochDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
         String timeFormatted = "";
         long years = ChronoUnit.YEARS.between(epochDateTime, playDateTime);
         long months = ChronoUnit.MONTHS.between(epochDateTime, playDateTime) - years * 12L;
         long days = ChronoUnit.DAYS.between(epochDateTime, playDateTime) - years * 365L - months * 30L;
         long hours = ChronoUnit.HOURS.between(epochDateTime, playDateTime) - days * 24L - months * 720L - years * 8760L;
         long minutes = ChronoUnit.MINUTES.between(epochDateTime, playDateTime) - hours * 60L - days * 1440L - months * 43200L - years * 525600L;
         long seconds = ChronoUnit.SECONDS.between(epochDateTime, playDateTime) - minutes * 60L - hours * 3600L - days * 86400L - months * 2592000L - years * 31536000L;
         if (years > 0L) {
            timeFormatted = "" + years + "y ";
         }

         if (months > 0L) {
            timeFormatted = timeFormatted + months + "mo ";
         }

         if (days > 0L) {
            timeFormatted = timeFormatted + days + "d ";
         }

         if (hours > 0L) {
            timeFormatted = timeFormatted + hours + "h ";
         }

         if (minutes > 0L) {
            timeFormatted = timeFormatted + minutes + "m ";
         }

         if (seconds > 0L) {
            timeFormatted = timeFormatted + seconds + "s";
         }

         if (timeFormatted.isEmpty()) {
            timeFormatted = "0s";
         }

         this.formattedPlayTime = timeFormatted;
         this.lastUpdateTime = System.currentTimeMillis();
      }
   }

   @Subscribe
   public void onPacket(PacketEvent event) {
      if (event.getPacket() instanceof S02PacketChat chatPacket && (chatPacket.getType() == 0 || chatPacket.getType() == 1)) {
         this.processChatMessage(chatPacket.getChatComponent().getUnformattedText());
      }
   }

   private void processChatMessage(String message) {
      String currentServer = this.serverListSetting.getValue();
      KillDeathDetector detector = this.killDeathDetectors.get(currentServer);
      if (detector != null && mc.thePlayer != null) {
         String playerName = mc.thePlayer.getName();
         boolean killDetected = detector.getKillPatterns()
            .stream()
            .filter(pattern -> !pattern.isEmpty())
            .map(pattern -> pattern.replaceAll("%p", playerName))
            .anyMatch(message::contains);
         boolean deathDetected = detector.getDeathPatterns()
            .stream()
            .filter(pattern -> !pattern.isEmpty())
            .map(pattern -> pattern.replaceAll("%p", playerName))
            .anyMatch(message::contains);
         if (killDetected) {
            this.pendingKills++;
         }

         if (deathDetected) {
            this.pendingDeaths++;
         }
      }
   }

   private File ensureSessionInformationFolder() {
      File rootDir = Moon.getInstance().getRootDirectory();
      File sessionDir = new File(rootDir, "session");
      if (!sessionDir.exists()) {
         sessionDir.mkdirs();
      }

      for (String serverEntry : this.serverListSetting.getEntries()) {
         File serverDir = new File(sessionDir, serverEntry);
         if (!serverDir.exists()) {
            serverDir.mkdirs();
         }

         File killsFile = new File(serverDir, "kills.txt");
         File deathsFile = new File(serverDir, "deaths.txt");
         boolean isHypixel = serverEntry.contains("hypixel.net");
         if (isHypixel) {
            try {
               List<String> deathTemplates = List.of(
                  "%p was", "%p fell into", "%p lost a", "%p drank", "%p be shot", "%p be snowballed", "%p be sent to", "%p be killed", "%p be shot"
               );
               List<String> killTemplates = List.of("by %p.", "by %p. FINAL KILL!");
               if (!killsFile.exists()) {
                  Files.write(
                     killsFile.toPath(),
                     String.join(System.lineSeparator(), killTemplates).getBytes(),
                     StandardOpenOption.CREATE,
                     StandardOpenOption.WRITE,
                     StandardOpenOption.TRUNCATE_EXISTING
                  );
               }

               if (!deathsFile.exists()) {
                  Files.write(
                     deathsFile.toPath(),
                     String.join(System.lineSeparator(), deathTemplates).getBytes(),
                     StandardOpenOption.CREATE,
                     StandardOpenOption.WRITE,
                     StandardOpenOption.TRUNCATE_EXISTING
                  );
               }
            } catch (IOException e) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Session Information")
                        .message("Failed to create kills and deaths templates for " + serverEntry)
                        .type(NotificationType.ERROR)
                        .duration(2000L)
                        .build()
                  );
            }
         } else {
            try {
               if (!killsFile.exists()) {
                  killsFile.createNewFile();
               }

               if (!deathsFile.exists()) {
                  deathsFile.createNewFile();
               }
            } catch (IOException e) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Session Information")
                        .message("Failed to create kills and deaths templates for " + serverEntry)
                        .type(NotificationType.ERROR)
                        .duration(2000L)
                        .build()
                  );
            }
         }

         KillDeathDetector detector = this.killDeathDetectors.computeIfAbsent(serverEntry, server -> new KillDeathDetector());
         detector.loadKillPatterns(killsFile);
         detector.loadDeathPatterns(deathsFile);
      }

      return sessionDir;
   }

   private boolean isMouseOver(int mouseX, int mouseY, float x, float y, float width, float height) {
      return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
   }

   private int getColor() {
      this.initializeModules();
      int color = this.syncWithModuleListSetting.getValue() ? this.hudModule.getAlColorOne().getValue() : this.colorSetting.getValue();
      if (color != this.lastColor) {
         this.colorScheme = ColorScheme.darkScheme(color);
         this.lastColor = color;
      }

      return this.syncWithModuleListSetting.getValue()
         ? this.getAnimatedColor(0.0F, this.hudModule, ColorMode.SYNC, this.hudModule.getAlColorOne().getValue(), this.hudModule.getAlColorTwo().getValue())
         : this.colorSetting.getValue();
   }

   private void initializeModules() {
      if (this.hudModule == null || this.effectsModule == null) {
         this.hudModule = Moon.getInstance().getModuleManager().getModule(HUD.class);
         this.effectsModule = Moon.getInstance().getModuleManager().getModule(Effects.class);
      }
   }

   private Tuple<Integer, Integer> getMousePosition() {
      int mouseX = Mouse.getX();
      int mouseY = Display.getHeight() - Mouse.getY();
      float scaleFactor = ScaleHandler.getScaledResolution().getScaleFactor();
      mouseX = (int)(mouseX / scaleFactor);
      mouseY = (int)(mouseY / scaleFactor);
      return new Tuple<>(mouseX, mouseY);
   }

   private int getAnimatedColor(float offset, HUD hudModule, ColorMode colorMode, int primaryColor, int secondaryColor) {
      return this.getAnimatedColor(offset, 4500.0, hudModule, colorMode, primaryColor, secondaryColor);
   }

   private int getAnimatedColor(float offset, double duration, HUD hudModule, ColorMode colorMode, int primaryColor, int secondaryColor) {
      int modeIndex = colorMode.ordinal();
      if (colorMode == ColorMode.SYNC) {
         primaryColor = hudModule.getAlColorOne().getValue();
         secondaryColor = hudModule.getAlColorTwo().getValue();
         modeIndex = hudModule.getTextColor().getValue().ordinal() + 1;
      }

      long currentTime = System.currentTimeMillis();
      int color;
      switch (modeIndex) {
         case 2: {
            long cycleDuration = (long)(1.5 * duration);
            int[] colors = new int[]{primaryColor, secondaryColor, primaryColor, secondaryColor, primaryColor, secondaryColor, primaryColor};
            color = GradientUtil.getColorAt(colors, (float)((currentTime + (int)offset * -100L) % cycleDuration) / ((float)cycleDuration / 2.0F));
            break;
         }
         case 3: {
            long cycleDuration = (long)(1.5 * duration);
            int darkerColor = new Color(primaryColor).darker().darker().getRGB();
            int[] colors = new int[]{primaryColor, darkerColor, primaryColor, darkerColor, primaryColor, darkerColor, primaryColor};
            color = GradientUtil.getColorAt(colors, (float)((currentTime + (int)offset * -100L) % cycleDuration) / ((float)cycleDuration / 2.0F));
            break;
         }
         default:
            return primaryColor;
      }

      return color;
   }
}
