package net.minecraft.client.settings;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.src.Config;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.optifine.ClearWater;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.RandomEntities;
import net.optifine.shaders.Shaders;
import net.optifine.util.KeyUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GameSettings {
   private static final Logger logger = LogManager.getLogger();
   private static final Gson gson = new Gson();
   private static final ParameterizedType typeListString = new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class};
      }

      @Override
      public Type getRawType() {
         return List.class;
      }

      @Override
      public Type getOwnerType() {
         return null;
      }
   };
   private static final String[] GUISCALES = new String[]{
      "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"
   };
   private static final String[] PARTICLES = new String[]{"options.particles.all", "options.particles.decreased", "options.particles.minimal"};
   private static final String[] AMBIENT_OCCLUSIONS = new String[]{"options.ao.off", "options.ao.min", "options.ao.max"};
   private static final String[] STREAM_COMPRESSIONS = new String[]{
      "options.stream.compression.low", "options.stream.compression.medium", "options.stream.compression.high"
   };
   private static final String[] STREAM_CHAT_MODES = new String[]{
      "options.stream.chat.enabled.streaming", "options.stream.chat.enabled.always", "options.stream.chat.enabled.never"
   };
   private static final String[] STREAM_CHAT_FILTER_MODES = new String[]{
      "options.stream.chat.userFilter.all", "options.stream.chat.userFilter.subs", "options.stream.chat.userFilter.mods"
   };
   private static final String[] STREAM_MIC_MODES = new String[]{"options.stream.mic_toggle.mute", "options.stream.mic_toggle.talk"};
   private static final String[] CLOUDS_TYPES = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
   public float mouseSensitivity = 0.5F;
   public boolean invertMouse;
   public int renderDistanceChunks = -1;
   public boolean viewBobbing = true;
   public boolean anaglyph;
   public boolean fboEnable = true;
   public int limitFramerate = 120;
   public int clouds = 2;
   public boolean fancyGraphics = true;
   public int ambientOcclusion = 2;
   public List<String> resourcePacks = Lists.newArrayList();
   public List<String> incompatibleResourcePacks = Lists.newArrayList();
   public EntityPlayer.EnumChatVisibility chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
   public boolean chatColours = true;
   public boolean chatLinks = true;
   public boolean chatLinksPrompt = true;
   public float chatOpacity = 1.0F;
   public boolean snooperEnabled = true;
   public boolean fullScreen;
   public boolean enableVsync = true;
   public boolean useVbo = false;
   public boolean allowBlockAlternatives = true;
   public boolean reducedDebugInfo = false;
   public boolean hideServerAddress;
   public boolean advancedItemTooltips;
   public boolean pauseOnLostFocus = true;
   private final Set<EnumPlayerModelParts> setModelParts = Sets.newHashSet(EnumPlayerModelParts.values());
   public boolean touchscreen;
   public int overrideWidth;
   public int overrideHeight;
   public boolean heldItemTooltips = true;
   public float chatScale = 1.0F;
   public float chatWidth = 1.0F;
   public float chatHeightUnfocused = 0.44366196F;
   public float chatHeightFocused = 1.0F;
   public boolean showInventoryAchievementHint = true;
   public int mipmapLevels = 4;
   private final Map<SoundCategory, Float> mapSoundLevels = Maps.newEnumMap(SoundCategory.class);
   public float streamBytesPerPixel = 0.5F;
   public float streamMicVolume = 1.0F;
   public float streamGameVolume = 1.0F;
   public float streamKbps = 0.5412844F;
   public float streamFps = 0.31690142F;
   public int streamCompression = 1;
   public boolean streamSendMetadata = true;
   public String streamPreferredServer = "";
   public int streamChatEnabled = 0;
   public int streamChatUserFilter = 0;
   public int streamMicToggleBehavior = 0;
   public boolean useNativeTransport = true;
   public boolean entityShadows = true;
   public boolean realmsNotifications = true;
   public KeyBinding keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
   public KeyBinding keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
   public KeyBinding keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
   public KeyBinding keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
   public KeyBinding keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
   public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
   public KeyBinding keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.movement");
   public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
   public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
   public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.gameplay");
   public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
   public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
   public KeyBinding keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
   public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
   public KeyBinding keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
   public KeyBinding keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
   public KeyBinding keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
   public KeyBinding keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
   public KeyBinding keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
   public KeyBinding keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
   public KeyBinding keyBindStreamStartStop = new KeyBinding("key.streamStartStop", 64, "key.categories.stream");
   public KeyBinding keyBindStreamPauseUnpause = new KeyBinding("key.streamPauseUnpause", 65, "key.categories.stream");
   public KeyBinding keyBindStreamCommercials = new KeyBinding("key.streamCommercial", 0, "key.categories.stream");
   public KeyBinding keyBindStreamToggleMic = new KeyBinding("key.streamToggleMic", 0, "key.categories.stream");
   public KeyBinding[] keyBindsHotbar = new KeyBinding[]{
      new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"),
      new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"),
      new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"),
      new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"),
      new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"),
      new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"),
      new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"),
      new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"),
      new KeyBinding("key.hotbar.9", 10, "key.categories.inventory")
   };
   public KeyBinding[] keyBindings;
   protected Minecraft mc;
   private File optionsFile;
   public EnumDifficulty difficulty;
   public boolean hideGUI;
   public int thirdPersonView;
   public boolean showDebugInfo;
   public boolean showDebugProfilerChart;
   public boolean showLagometer;
   public String lastServer;
   public boolean smoothCamera;
   public boolean debugCamEnable;
   public float fovSetting;
   public float gammaSetting;
   public float saturation;
   public int guiScale;
   public int particleSetting;
   public String language;
   public boolean forceUnicodeFont;
   public int ofFogType = 1;
   public float ofFogStart = 0.8F;
   public int ofMipmapType = 0;
   public boolean ofOcclusionFancy = false;
   public boolean ofSmoothFps = false;
   public boolean ofSmoothWorld = Config.isSingleProcessor();
   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
   public boolean ofRenderRegions = false;
   public boolean ofSmartAnimations = false;
   public float ofAoLevel = 1.0F;
   public int ofAaLevel = 0;
   public int ofAfLevel = 1;
   public int ofClouds = 0;
   public float ofCloudsHeight = 0.0F;
   public int ofTrees = 0;
   public int ofRain = 0;
   public int ofDroppedItems = 0;
   public int ofBetterGrass = 3;
   public int ofAutoSaveTicks = 4000;
   public boolean ofLagometer = false;
   public boolean ofProfiler = false;
   public boolean ofShowFps = false;
   public boolean ofWeather = true;
   public boolean ofSky = true;
   public boolean ofStars = true;
   public boolean ofSunMoon = true;
   public int ofVignette = 0;
   public int ofChunkUpdates = 1;
   public boolean ofChunkUpdatesDynamic = false;
   public int ofTime = 0;
   public boolean ofClearWater = false;
   public boolean ofBetterSnow = false;
   public String ofFullscreenMode = "Default";
   public boolean ofSwampColors = true;
   public boolean ofRandomEntities = true;
   public boolean ofSmoothBiomes = true;
   public boolean ofCustomFonts = true;
   public boolean ofCustomColors = true;
   public boolean ofCustomSky = true;
   public boolean ofShowCapes = true;
   public int ofConnectedTextures = 2;
   public boolean ofCustomItems = true;
   public boolean ofNaturalTextures = false;
   public boolean ofEmissiveTextures = true;
   public boolean ofFastMath = false;
   public int ofTranslucentBlocks = 0;
   public boolean ofDynamicFov = true;
   public boolean ofAlternateBlocks = true;
   public int ofDynamicLights = 3;
   public boolean ofCustomEntityModels = true;
   public boolean ofCustomGuis = true;
   public boolean ofShowGlErrors = true;
   public int ofScreenshotSize = 1;
   public int ofAnimatedWater = 0;
   public int ofAnimatedLava = 0;
   public boolean ofAnimatedFire = true;
   public boolean ofAnimatedPortal = true;
   public boolean ofAnimatedRedstone = true;
   public boolean ofAnimatedExplosion = true;
   public boolean ofAnimatedFlame = true;
   public boolean ofAnimatedSmoke = true;
   public boolean ofVoidParticles = true;
   public boolean ofWaterParticles = true;
   public boolean ofRainSplash = true;
   public boolean ofPortalParticles = true;
   public boolean ofPotionParticles = true;
   public boolean ofFireworkParticles = true;
   public boolean ofDrippingWaterLava = true;
   public boolean ofAnimatedTerrain = true;
   public boolean ofAnimatedTextures = true;
   private static final int[] OF_TREES_VALUES = new int[]{0, 1, 4, 2};
   private static final int[] OF_DYNAMIC_LIGHTS = new int[]{3, 1, 2};
   private static final String[] KEYS_DYNAMIC_LIGHTS = new String[]{"options.off", "options.graphics.fast", "options.graphics.fancy"};
   public KeyBinding ofKeyBindZoom;
   private File optionsFileOF;

   public GameSettings(Minecraft minecraft, File file) {
      this.keyBindings = (KeyBinding[])ArrayUtils.addAll(
         new KeyBinding[]{
            this.keyBindAttack,
            this.keyBindUseItem,
            this.keyBindForward,
            this.keyBindLeft,
            this.keyBindBack,
            this.keyBindRight,
            this.keyBindJump,
            this.keyBindSneak,
            this.keyBindSprint,
            this.keyBindDrop,
            this.keyBindInventory,
            this.keyBindChat,
            this.keyBindPlayerList,
            this.keyBindPickBlock,
            this.keyBindCommand,
            this.keyBindScreenshot,
            this.keyBindTogglePerspective,
            this.keyBindSmoothCamera,
            this.keyBindStreamStartStop,
            this.keyBindStreamPauseUnpause,
            this.keyBindStreamCommercials,
            this.keyBindStreamToggleMic,
            this.keyBindFullscreen,
            this.keyBindSpectatorOutlines
         },
         this.keyBindsHotbar
      );
      this.difficulty = EnumDifficulty.NORMAL;
      this.lastServer = "";
      this.fovSetting = 70.0F;
      this.language = "en_US";
      this.forceUnicodeFont = false;
      this.mc = minecraft;
      this.optionsFile = new File(file, "options.txt");
      if (minecraft.isJava64bit() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
         GameSettings.Options.RENDER_DISTANCE.setValueMax(32.0F);
         long var6 = 1000000L;
         if (Runtime.getRuntime().maxMemory() >= 1500000000L) {
            GameSettings.Options.RENDER_DISTANCE.setValueMax(48.0F);
         }

         if (Runtime.getRuntime().maxMemory() >= 2500000000L) {
            GameSettings.Options.RENDER_DISTANCE.setValueMax(64.0F);
         }
      } else {
         GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
      }

      this.renderDistanceChunks = minecraft.isJava64bit() ? 12 : 8;
      this.optionsFileOF = new File(file, "optionsof.txt");
      this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
      this.ofKeyBindZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
      this.keyBindings = (KeyBinding[])ArrayUtils.add(this.keyBindings, this.ofKeyBindZoom);
      KeyUtils.fixKeyConflicts(this.keyBindings, new KeyBinding[]{this.ofKeyBindZoom});
      this.renderDistanceChunks = 8;
      this.loadOptions();
      Config.Method246(this);
   }

   public GameSettings() {
      this.keyBindings = (KeyBinding[])ArrayUtils.addAll(
         new KeyBinding[]{
            this.keyBindAttack,
            this.keyBindUseItem,
            this.keyBindForward,
            this.keyBindLeft,
            this.keyBindBack,
            this.keyBindRight,
            this.keyBindJump,
            this.keyBindSneak,
            this.keyBindSprint,
            this.keyBindDrop,
            this.keyBindInventory,
            this.keyBindChat,
            this.keyBindPlayerList,
            this.keyBindPickBlock,
            this.keyBindCommand,
            this.keyBindScreenshot,
            this.keyBindTogglePerspective,
            this.keyBindSmoothCamera,
            this.keyBindStreamStartStop,
            this.keyBindStreamPauseUnpause,
            this.keyBindStreamCommercials,
            this.keyBindStreamToggleMic,
            this.keyBindFullscreen,
            this.keyBindSpectatorOutlines
         },
         this.keyBindsHotbar
      );
      this.difficulty = EnumDifficulty.NORMAL;
      this.lastServer = "";
      this.fovSetting = 70.0F;
      this.language = "en_US";
      this.forceUnicodeFont = false;
   }

   public static String getKeyDisplayString(int integer) {
      return integer < 0
         ? I18n.format("key.mouseButton", integer + 101)
         : (integer < 256 ? Keyboard.getKeyName(integer) : String.format("%c", (char)(integer - 256)).toUpperCase());
   }

   public static boolean isKeyDown(KeyBinding keyBinding) {
      return keyBinding.getKeyCode() != 0
         && (keyBinding.getKeyCode() < 0 ? Mouse.isButtonDown(keyBinding.getKeyCode() + 100) : Keyboard.isKeyDown(keyBinding.getKeyCode()));
   }

   public void setOptionKeyBinding(KeyBinding keyBinding, int integer) {
      keyBinding.setKeyCode(integer);
      this.saveOptions();
   }

   public void setOptionFloatValue(GameSettings.Options options, float float2) {
      this.setOptionFloatValueOF(options, float2);
      if (options == GameSettings.Options.SENSITIVITY) {
         this.mouseSensitivity = float2;
      }

      if (options == GameSettings.Options.FOV) {
         this.fovSetting = float2;
      }

      if (options == GameSettings.Options.GAMMA) {
         this.gammaSetting = float2;
      }

      if (options == GameSettings.Options.FRAMERATE_LIMIT) {
         this.limitFramerate = (int)float2;
         this.enableVsync = false;
         if (this.limitFramerate <= 0) {
            this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
            this.enableVsync = true;
         }

         this.updateVSync();
      }

      if (options == GameSettings.Options.CHAT_OPACITY) {
         this.chatOpacity = float2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (options == GameSettings.Options.CHAT_HEIGHT_FOCUSED) {
         this.chatHeightFocused = float2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (options == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED) {
         this.chatHeightUnfocused = float2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (options == GameSettings.Options.CHAT_WIDTH) {
         this.chatWidth = float2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (options == GameSettings.Options.CHAT_SCALE) {
         this.chatScale = float2;
         this.mc.ingameGUI.getChatGUI().refreshChat();
      }

      if (options == GameSettings.Options.MIPMAP_LEVELS) {
         int var6 = this.mipmapLevels;
         this.mipmapLevels = (int)float2;
         if (var6 != float2) {
            this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
            this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
            this.mc.getTextureMapBlocks().setBlurMipmapDirect(false, this.mipmapLevels > 0);
            this.mc.scheduleResourcesRefresh();
         }
      }

      if (options == GameSettings.Options.BLOCK_ALTERNATIVES) {
         this.allowBlockAlternatives = !this.allowBlockAlternatives;
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.RENDER_DISTANCE) {
         this.renderDistanceChunks = (int)float2;
         this.mc.renderGlobal.setDisplayListEntitiesDirty();
      }

      if (options == GameSettings.Options.STREAM_BYTES_PER_PIXEL) {
         this.streamBytesPerPixel = float2;
      }

      if (options == GameSettings.Options.STREAM_KBPS) {
         this.streamKbps = float2;
      }

      if (options == GameSettings.Options.STREAM_FPS) {
         this.streamFps = float2;
      }
   }

   public void setOptionValue(GameSettings.Options options, int integer) {
      this.setOptionValueOF(options, integer);
      if (options == GameSettings.Options.INVERT_MOUSE) {
         this.invertMouse = !this.invertMouse;
      }

      if (options == GameSettings.Options.GUI_SCALE) {
         this.guiScale += integer;
         if (GuiScreen.isShiftKeyDown()) {
            this.guiScale = 0;
         }

         DisplayMode var6 = Config.getLargestDisplayMode();
         int var7 = var6.getWidth() / 320;
         int var8 = var6.getHeight() / 240;
         int var9 = Math.min(var7, var8);
         if (this.guiScale < 0) {
            this.guiScale = var9 - 1;
         }

         if (this.mc.isUnicode() && this.guiScale % 2 != 0) {
            this.guiScale += integer;
         }

         if (this.guiScale < 0 || this.guiScale >= var9) {
            this.guiScale = 0;
         }
      }

      if (options == GameSettings.Options.PARTICLES) {
         this.particleSetting = (this.particleSetting + integer) % 3;
      }

      if (options == GameSettings.Options.VIEW_BOBBING) {
         this.viewBobbing = !this.viewBobbing;
      }

      if (options == GameSettings.Options.RENDER_CLOUDS) {
         this.clouds = (this.clouds + integer) % 3;
      }

      if (options == GameSettings.Options.FORCE_UNICODE_FONT) {
         this.forceUnicodeFont = !this.forceUnicodeFont;
         this.mc.fontRendererObj.setUnicodeFlag(this.mc.getLanguageManager().isCurrentLocaleUnicode() || this.forceUnicodeFont);
      }

      if (options == GameSettings.Options.FBO_ENABLE) {
         this.fboEnable = !this.fboEnable;
      }

      if (options == GameSettings.Options.ANAGLYPH) {
         if (!this.anaglyph && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.an.shaders1"), Lang.get("of.message.an.shaders2"));
            return;
         }

         this.anaglyph = !this.anaglyph;
         this.mc.refreshResources();
      }

      if (options == GameSettings.Options.GRAPHICS) {
         this.fancyGraphics = !this.fancyGraphics;
         this.updateRenderClouds();
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.AMBIENT_OCCLUSION) {
         this.ambientOcclusion = (this.ambientOcclusion + integer) % 3;
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.CHAT_VISIBILITY) {
         this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + integer) % 3);
      }

      if (options == GameSettings.Options.STREAM_COMPRESSION) {
         this.streamCompression = (this.streamCompression + integer) % 3;
      }

      if (options == GameSettings.Options.STREAM_SEND_METADATA) {
         this.streamSendMetadata = !this.streamSendMetadata;
      }

      if (options == GameSettings.Options.STREAM_CHAT_ENABLED) {
         this.streamChatEnabled = (this.streamChatEnabled + integer) % 3;
      }

      if (options == GameSettings.Options.STREAM_CHAT_USER_FILTER) {
         this.streamChatUserFilter = (this.streamChatUserFilter + integer) % 3;
      }

      if (options == GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
         this.streamMicToggleBehavior = (this.streamMicToggleBehavior + integer) % 2;
      }

      if (options == GameSettings.Options.CHAT_COLOR) {
         this.chatColours = !this.chatColours;
      }

      if (options == GameSettings.Options.CHAT_LINKS) {
         this.chatLinks = !this.chatLinks;
      }

      if (options == GameSettings.Options.CHAT_LINKS_PROMPT) {
         this.chatLinksPrompt = !this.chatLinksPrompt;
      }

      if (options == GameSettings.Options.SNOOPER_ENABLED) {
         this.snooperEnabled = !this.snooperEnabled;
      }

      if (options == GameSettings.Options.TOUCHSCREEN) {
         this.touchscreen = !this.touchscreen;
      }

      if (options == GameSettings.Options.USE_FULLSCREEN) {
         this.fullScreen = !this.fullScreen;
         if (this.mc.isFullScreen() != this.fullScreen) {
            this.mc.toggleFullscreen();
         }
      }

      if (options == GameSettings.Options.ENABLE_VSYNC) {
         this.enableVsync = !this.enableVsync;
         Display.setVSyncEnabled(this.enableVsync);
      }

      if (options == GameSettings.Options.USE_VBO) {
         this.useVbo = !this.useVbo;
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.BLOCK_ALTERNATIVES) {
         this.allowBlockAlternatives = !this.allowBlockAlternatives;
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.REDUCED_DEBUG_INFO) {
         this.reducedDebugInfo = !this.reducedDebugInfo;
      }

      if (options == GameSettings.Options.ENTITY_SHADOWS) {
         this.entityShadows = !this.entityShadows;
      }

      if (options == GameSettings.Options.REALMS_NOTIFICATIONS) {
         this.realmsNotifications = !this.realmsNotifications;
      }

      this.saveOptions();
   }

   public float getOptionFloatValue(GameSettings.Options options) {
      float var5 = this.getOptionFloatValueOF(options);
      return var5 != Float.MAX_VALUE
         ? var5
         : (
            options == GameSettings.Options.FOV
               ? this.fovSetting
               : (
                  options == GameSettings.Options.GAMMA
                     ? this.gammaSetting
                     : (
                        options == GameSettings.Options.SATURATION
                           ? this.saturation
                           : (
                              options == GameSettings.Options.SENSITIVITY
                                 ? this.mouseSensitivity
                                 : (
                                    options == GameSettings.Options.CHAT_OPACITY
                                       ? this.chatOpacity
                                       : (
                                          options == GameSettings.Options.CHAT_HEIGHT_FOCUSED
                                             ? this.chatHeightFocused
                                             : (
                                                options == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED
                                                   ? this.chatHeightUnfocused
                                                   : (
                                                      options == GameSettings.Options.CHAT_SCALE
                                                         ? this.chatScale
                                                         : (
                                                            options == GameSettings.Options.CHAT_WIDTH
                                                               ? this.chatWidth
                                                               : (
                                                                  options == GameSettings.Options.FRAMERATE_LIMIT
                                                                     ? this.limitFramerate
                                                                     : (
                                                                        options == GameSettings.Options.MIPMAP_LEVELS
                                                                           ? this.mipmapLevels
                                                                           : (
                                                                              options == GameSettings.Options.RENDER_DISTANCE
                                                                                 ? this.renderDistanceChunks
                                                                                 : (
                                                                                    options == GameSettings.Options.STREAM_BYTES_PER_PIXEL
                                                                                       ? this.streamBytesPerPixel
                                                                                       : (
                                                                                          options == GameSettings.Options.STREAM_VOLUME_MIC
                                                                                             ? this.streamMicVolume
                                                                                             : (
                                                                                                options == GameSettings.Options.STREAM_VOLUME_SYSTEM
                                                                                                   ? this.streamGameVolume
                                                                                                   : (
                                                                                                      options == GameSettings.Options.STREAM_KBPS
                                                                                                         ? this.streamKbps
                                                                                                         : (
                                                                                                            options == GameSettings.Options.STREAM_FPS
                                                                                                               ? this.streamFps
                                                                                                               : 0.0F
                                                                                                         )
                                                                                                   )
                                                                                             )
                                                                                       )
                                                                                 )
                                                                           )
                                                                     )
                                                               )
                                                         )
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               )
         );
   }

   public boolean getOptionOrdinalValue(GameSettings.Options options) {
      switch (options) {
         case INVERT_MOUSE:
            return this.invertMouse;
         case VIEW_BOBBING:
            return this.viewBobbing;
         case ANAGLYPH:
            return this.anaglyph;
         case FBO_ENABLE:
            return this.fboEnable;
         case CHAT_COLOR:
            return this.chatColours;
         case CHAT_LINKS:
            return this.chatLinks;
         case CHAT_LINKS_PROMPT:
            return this.chatLinksPrompt;
         case SNOOPER_ENABLED:
            return this.snooperEnabled;
         case USE_FULLSCREEN:
            return this.fullScreen;
         case ENABLE_VSYNC:
            return this.enableVsync;
         case USE_VBO:
            return this.useVbo;
         case TOUCHSCREEN:
            return this.touchscreen;
         case STREAM_SEND_METADATA:
            return this.streamSendMetadata;
         case FORCE_UNICODE_FONT:
            return this.forceUnicodeFont;
         case BLOCK_ALTERNATIVES:
            return this.allowBlockAlternatives;
         case REDUCED_DEBUG_INFO:
            return this.reducedDebugInfo;
         case ENTITY_SHADOWS:
            return this.entityShadows;
         case REALMS_NOTIFICATIONS:
            return this.realmsNotifications;
         default:
            return false;
      }
   }

   private static String getTranslation(String[] arr, int integer) {
      if (integer < 0 || integer >= arr.length) {
         integer = 0;
      }

      return I18n.format(arr[integer]);
   }

   public String getKeyBinding(GameSettings.Options options) {
      String var5 = this.getKeyBindingOF(options);
      if (var5 != null) {
         return var5;
      }

      String var6 = I18n.format(options.getEnumString()) + ": ";
      if (!options.getEnumFloat()) {
         if (options.getEnumBoolean()) {
            boolean var9 = this.getOptionOrdinalValue(options);
            return var9 ? var6 + I18n.format("options.on") : var6 + I18n.format("options.off");
         } else if (options == GameSettings.Options.GUI_SCALE) {
            return this.guiScale >= GUISCALES.length ? var6 + this.guiScale + "x" : var6 + getTranslation(GUISCALES, this.guiScale);
         } else if (options == GameSettings.Options.CHAT_VISIBILITY) {
            return var6 + I18n.format(this.chatVisibility.getResourceKey());
         } else if (options == GameSettings.Options.PARTICLES) {
            return var6 + getTranslation(PARTICLES, this.particleSetting);
         } else if (options == GameSettings.Options.AMBIENT_OCCLUSION) {
            return var6 + getTranslation(AMBIENT_OCCLUSIONS, this.ambientOcclusion);
         } else if (options == GameSettings.Options.STREAM_COMPRESSION) {
            return var6 + getTranslation(STREAM_COMPRESSIONS, this.streamCompression);
         } else if (options == GameSettings.Options.STREAM_CHAT_ENABLED) {
            return var6 + getTranslation(STREAM_CHAT_MODES, this.streamChatEnabled);
         } else if (options == GameSettings.Options.STREAM_CHAT_USER_FILTER) {
            return var6 + getTranslation(STREAM_CHAT_FILTER_MODES, this.streamChatUserFilter);
         } else if (options == GameSettings.Options.STREAM_MIC_TOGGLE_BEHAVIOR) {
            return var6 + getTranslation(STREAM_MIC_MODES, this.streamMicToggleBehavior);
         } else if (options == GameSettings.Options.RENDER_CLOUDS) {
            return var6 + getTranslation(CLOUDS_TYPES, this.clouds);
         } else if (options == GameSettings.Options.GRAPHICS) {
            return this.fancyGraphics ? var6 + I18n.format("options.graphics.fancy") : var6 + I18n.format("options.graphics.fast");
         } else {
            return var6;
         }
      } else {
         float var7 = this.getOptionFloatValue(options);
         float var8 = options.normalizeValue(var7);
         return options == GameSettings.Options.MIPMAP_LEVELS && var7 >= 4.0
            ? var6 + Lang.get("of.general.max")
            : (
               options == GameSettings.Options.SENSITIVITY
                  ? (
                     var8 == 0.0F
                        ? var6 + I18n.format("options.sensitivity.min")
                        : (var8 == 1.0F ? var6 + I18n.format("options.sensitivity.max") : var6 + (int)(var8 * 200.0F) + "%")
                  )
                  : (
                     options == GameSettings.Options.FOV
                        ? (var7 == 70.0F ? var6 + I18n.format("options.fov.min") : (var7 == 110.0F ? var6 + I18n.format("options.fov.max") : var6 + (int)var7))
                        : (
                           options == GameSettings.Options.FRAMERATE_LIMIT
                              ? (var7 == options.valueMax ? var6 + I18n.format("options.framerateLimit.max") : var6 + (int)var7 + " fps")
                              : (
                                 options == GameSettings.Options.RENDER_CLOUDS
                                    ? (var7 == options.valueMin ? var6 + I18n.format("options.cloudHeight.min") : var6 + ((int)var7 + 128))
                                    : (
                                       options == GameSettings.Options.GAMMA
                                          ? (
                                             var8 == 0.0F
                                                ? var6 + I18n.format("options.gamma.min")
                                                : (var8 == 1.0F ? var6 + I18n.format("options.gamma.max") : var6 + "+" + (int)(var8 * 100.0F) + "%")
                                          )
                                          : (
                                             options == GameSettings.Options.SATURATION
                                                ? var6 + (int)(var8 * 400.0F) + "%"
                                                : (
                                                   options == GameSettings.Options.CHAT_OPACITY
                                                      ? var6 + (int)(var8 * 90.0F + 10.0F) + "%"
                                                      : (
                                                         options == GameSettings.Options.CHAT_HEIGHT_UNFOCUSED
                                                            ? var6 + GuiNewChat.calculateChatboxHeight(var8) + "px"
                                                            : (
                                                               options == GameSettings.Options.CHAT_HEIGHT_FOCUSED
                                                                  ? var6 + GuiNewChat.calculateChatboxHeight(var8) + "px"
                                                                  : (
                                                                     options == GameSettings.Options.CHAT_WIDTH
                                                                        ? var6 + GuiNewChat.calculateChatboxWidth(var8) + "px"
                                                                        : (
                                                                           options == GameSettings.Options.RENDER_DISTANCE
                                                                              ? var6 + (int)var7 + " chunks"
                                                                              : (
                                                                                 options == GameSettings.Options.MIPMAP_LEVELS
                                                                                    ? (var7 == 0.0F ? var6 + I18n.format("options.off") : var6 + (int)var7)
                                                                                    : (
                                                                                       options == GameSettings.Options.STREAM_FPS
                                                                                          ? var6 + " fps"
                                                                                          : (
                                                                                             options == GameSettings.Options.STREAM_KBPS
                                                                                                ? var6 + " Kbps"
                                                                                                : (
                                                                                                   options == GameSettings.Options.STREAM_BYTES_PER_PIXEL
                                                                                                      ? var6
                                                                                                      : (
                                                                                                         var8 == 0.0F
                                                                                                            ? var6 + I18n.format("options.off")
                                                                                                            : var6 + (int)(var8 * 100.0F) + "%"
                                                                                                      )
                                                                                                )
                                                                                          )
                                                                                    )
                                                                              )
                                                                        )
                                                                  )
                                                            )
                                                      )
                                                )
                                          )
                                    )
                              )
                        )
                  )
            );
      }
   }

   // $VF: Could not verify finally blocks. A semaphore variable has been added to preserve control flow.
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void loadOptions() {
      FileInputStream var4 = null;
      boolean var16 = false /* VF: Semaphore variable */;

      label1120: {
         label1119: {
            label1118: {
               try {
                  var16 = true;
                  if (!this.optionsFile.exists()) {
                     var16 = false;
                     break label1120;
                  }

                  BufferedReader var5 = new BufferedReader(new InputStreamReader(var4 = new FileInputStream(this.optionsFile)));
                  this.mapSoundLevels.clear();

                  String var6;
                  while ((var6 = var5.readLine()) != null) {
                     try {
                        String[] var7 = var6.split(":");
                        if (var7[0].equals("mouseSensitivity")) {
                           this.mouseSensitivity = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("fov")) {
                           this.fovSetting = this.parseFloat(var7[1]) * 40.0F + 70.0F;
                        }

                        if (var7[0].equals("gamma")) {
                           this.gammaSetting = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("saturation")) {
                           this.saturation = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("invertYMouse")) {
                           this.invertMouse = var7[1].equals("true");
                        }

                        if (var7[0].equals("renderDistance")) {
                           this.renderDistanceChunks = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("guiScale")) {
                           this.guiScale = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("particles")) {
                           this.particleSetting = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("bobView")) {
                           this.viewBobbing = var7[1].equals("true");
                        }

                        if (var7[0].equals("anaglyph3d")) {
                           this.anaglyph = var7[1].equals("true");
                        }

                        if (var7[0].equals("maxFps")) {
                           this.limitFramerate = Integer.parseInt(var7[1]);
                           if (this.enableVsync) {
                              this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
                           }

                           if (this.limitFramerate <= 0) {
                              this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
                           }
                        }

                        if (var7[0].equals("fboEnable")) {
                           this.fboEnable = var7[1].equals("true");
                        }

                        if (var7[0].equals("difficulty")) {
                           this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(var7[1]));
                        }

                        if (var7[0].equals("fancyGraphics")) {
                           this.fancyGraphics = var7[1].equals("true");
                           this.updateRenderClouds();
                        }

                        if (var7[0].equals("ao")) {
                           if (var7[1].equals("true")) {
                              this.ambientOcclusion = 2;
                           } else if (var7[1].equals("false")) {
                              this.ambientOcclusion = 0;
                           } else {
                              this.ambientOcclusion = Integer.parseInt(var7[1]);
                           }
                        }

                        if (var7[0].equals("renderClouds")) {
                           if (var7[1].equals("true")) {
                              this.clouds = 2;
                           } else if (var7[1].equals("false")) {
                              this.clouds = 0;
                           } else if (var7[1].equals("fast")) {
                              this.clouds = 1;
                           }
                        }

                        if (var7[0].equals("resourcePacks")) {
                           this.resourcePacks = (List<String>)gson.fromJson(var6.substring(var6.indexOf(58) + 1), typeListString);
                           if (this.resourcePacks == null) {
                              this.resourcePacks = Lists.newArrayList();
                           }
                        }

                        if (var7[0].equals("incompatibleResourcePacks")) {
                           this.incompatibleResourcePacks = (List<String>)gson.fromJson(var6.substring(var6.indexOf(58) + 1), typeListString);
                           if (this.incompatibleResourcePacks == null) {
                              this.incompatibleResourcePacks = Lists.newArrayList();
                           }
                        }

                        if (var7[0].equals("lastServer") && var7.length >= 2) {
                           this.lastServer = var6.substring(var6.indexOf(58) + 1);
                        }

                        if (var7[0].equals("lang") && var7.length >= 2) {
                           this.language = var7[1];
                        }

                        if (var7[0].equals("chatVisibility")) {
                           this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(var7[1]));
                        }

                        if (var7[0].equals("chatColors")) {
                           this.chatColours = var7[1].equals("true");
                        }

                        if (var7[0].equals("chatLinks")) {
                           this.chatLinks = var7[1].equals("true");
                        }

                        if (var7[0].equals("chatLinksPrompt")) {
                           this.chatLinksPrompt = var7[1].equals("true");
                        }

                        if (var7[0].equals("chatOpacity")) {
                           this.chatOpacity = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("snooperEnabled")) {
                           this.snooperEnabled = var7[1].equals("true");
                        }

                        if (var7[0].equals("fullscreen")) {
                           this.fullScreen = var7[1].equals("true");
                        }

                        if (var7[0].equals("enableVsync")) {
                           this.enableVsync = var7[1].equals("true");
                           if (this.enableVsync) {
                              this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
                           }

                           this.updateVSync();
                        }

                        if (var7[0].equals("useVbo")) {
                           this.useVbo = var7[1].equals("true");
                        }

                        if (var7[0].equals("hideServerAddress")) {
                           this.hideServerAddress = var7[1].equals("true");
                        }

                        if (var7[0].equals("advancedItemTooltips")) {
                           this.advancedItemTooltips = var7[1].equals("true");
                        }

                        if (var7[0].equals("pauseOnLostFocus")) {
                           this.pauseOnLostFocus = var7[1].equals("true");
                        }

                        if (var7[0].equals("touchscreen")) {
                           this.touchscreen = var7[1].equals("true");
                        }

                        if (var7[0].equals("overrideHeight")) {
                           this.overrideHeight = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("overrideWidth")) {
                           this.overrideWidth = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("heldItemTooltips")) {
                           this.heldItemTooltips = var7[1].equals("true");
                        }

                        if (var7[0].equals("chatHeightFocused")) {
                           this.chatHeightFocused = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("chatHeightUnfocused")) {
                           this.chatHeightUnfocused = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("chatScale")) {
                           this.chatScale = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("chatWidth")) {
                           this.chatWidth = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("showInventoryAchievementHint")) {
                           this.showInventoryAchievementHint = var7[1].equals("true");
                        }

                        if (var7[0].equals("mipmapLevels")) {
                           this.mipmapLevels = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("streamBytesPerPixel")) {
                           this.streamBytesPerPixel = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("streamMicVolume")) {
                           this.streamMicVolume = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("streamSystemVolume")) {
                           this.streamGameVolume = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("streamKbps")) {
                           this.streamKbps = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("streamFps")) {
                           this.streamFps = this.parseFloat(var7[1]);
                        }

                        if (var7[0].equals("streamCompression")) {
                           this.streamCompression = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("streamSendMetadata")) {
                           this.streamSendMetadata = var7[1].equals("true");
                        }

                        if (var7[0].equals("streamPreferredServer") && var7.length >= 2) {
                           this.streamPreferredServer = var6.substring(var6.indexOf(58) + 1);
                        }

                        if (var7[0].equals("streamChatEnabled")) {
                           this.streamChatEnabled = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("streamChatUserFilter")) {
                           this.streamChatUserFilter = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("streamMicToggleBehavior")) {
                           this.streamMicToggleBehavior = Integer.parseInt(var7[1]);
                        }

                        if (var7[0].equals("forceUnicodeFont")) {
                           this.forceUnicodeFont = var7[1].equals("true");
                        }

                        if (var7[0].equals("allowBlockAlternatives")) {
                           this.allowBlockAlternatives = var7[1].equals("true");
                        }

                        if (var7[0].equals("reducedDebugInfo")) {
                           this.reducedDebugInfo = var7[1].equals("true");
                        }

                        if (var7[0].equals("useNativeTransport")) {
                           this.useNativeTransport = var7[1].equals("true");
                        }

                        if (var7[0].equals("entityShadows")) {
                           this.entityShadows = var7[1].equals("true");
                        }

                        if (var7[0].equals("realmsNotifications")) {
                           this.realmsNotifications = var7[1].equals("true");
                        }

                        for (KeyBinding var11 : this.keyBindings) {
                           if (var7[0].equals("key_" + var11.getKeyDescription())) {
                              var11.setKeyCode(Integer.parseInt(var7[1]));
                           }
                        }

                        for (SoundCategory var26 : SoundCategory.values()) {
                           if (var7[0].equals("soundCategory_" + var26.getCategoryName())) {
                              this.mapSoundLevels.put(var26, this.parseFloat(var7[1]));
                           }
                        }

                        for (EnumPlayerModelParts var27 : EnumPlayerModelParts.values()) {
                           if (var7[0].equals("modelPart_" + var27.getPartName())) {
                              this.setModelPartEnabled(var27, var7[1].equals("true"));
                           }
                        }
                     } catch (Exception var17) {
                        logger.warn("Skipping bad option: " + var6);
                        var17.printStackTrace();
                     }
                  }

                  KeyBinding.resetKeyBindingArrayAndHash();
                  var5.close();
                  var16 = false;
                  break label1118;
               } catch (Exception var18) {
                  logger.error("Failed to load options", var18);
                  var16 = false;
               } finally {
                  if (var16) {
                     IOUtils.closeQuietly(var4);
                  }
               }

               IOUtils.closeQuietly(var4);
               break label1119;
            }

            IOUtils.closeQuietly(var4);
         }

         this.loadOfOptions();
         return;
      }

      IOUtils.closeQuietly((InputStream)null);
   }

   private float parseFloat(String string) {
      return string.equals("true") ? 1.0F : (string.equals("false") ? 0.0F : Float.parseFloat(string));
   }

   public void saveOptions() {
      try {
         PrintWriter var4 = new PrintWriter(new FileWriter(this.optionsFile));
         var4.println("invertYMouse:" + this.invertMouse);
         var4.println("mouseSensitivity:" + this.mouseSensitivity);
         var4.println("fov:" + (this.fovSetting - 70.0F) / 40.0F);
         var4.println("gamma:" + this.gammaSetting);
         var4.println("saturation:" + this.saturation);
         var4.println("renderDistance:" + this.renderDistanceChunks);
         var4.println("guiScale:" + this.guiScale);
         var4.println("particles:" + this.particleSetting);
         var4.println("bobView:" + this.viewBobbing);
         var4.println("anaglyph3d:" + this.anaglyph);
         var4.println("maxFps:" + this.limitFramerate);
         var4.println("fboEnable:" + this.fboEnable);
         var4.println("difficulty:" + this.difficulty.getDifficultyId());
         var4.println("fancyGraphics:" + this.fancyGraphics);
         var4.println("ao:" + this.ambientOcclusion);
         switch (this.clouds) {
            case 0:
               var4.println("renderClouds:false");
               break;
            case 1:
               var4.println("renderClouds:fast");
               break;
            case 2:
               var4.println("renderClouds:true");
         }

         var4.println("resourcePacks:" + gson.toJson(this.resourcePacks));
         var4.println("incompatibleResourcePacks:" + gson.toJson(this.incompatibleResourcePacks));
         var4.println("lastServer:" + this.lastServer);
         var4.println("lang:" + this.language);
         var4.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
         var4.println("chatColors:" + this.chatColours);
         var4.println("chatLinks:" + this.chatLinks);
         var4.println("chatLinksPrompt:" + this.chatLinksPrompt);
         var4.println("chatOpacity:" + this.chatOpacity);
         var4.println("snooperEnabled:" + this.snooperEnabled);
         var4.println("fullscreen:" + this.fullScreen);
         var4.println("enableVsync:" + this.enableVsync);
         var4.println("useVbo:" + this.useVbo);
         var4.println("hideServerAddress:" + this.hideServerAddress);
         var4.println("advancedItemTooltips:" + this.advancedItemTooltips);
         var4.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
         var4.println("touchscreen:" + this.touchscreen);
         var4.println("overrideWidth:" + this.overrideWidth);
         var4.println("overrideHeight:" + this.overrideHeight);
         var4.println("heldItemTooltips:" + this.heldItemTooltips);
         var4.println("chatHeightFocused:" + this.chatHeightFocused);
         var4.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
         var4.println("chatScale:" + this.chatScale);
         var4.println("chatWidth:" + this.chatWidth);
         var4.println("showInventoryAchievementHint:" + this.showInventoryAchievementHint);
         var4.println("mipmapLevels:" + this.mipmapLevels);
         var4.println("streamBytesPerPixel:" + this.streamBytesPerPixel);
         var4.println("streamMicVolume:" + this.streamMicVolume);
         var4.println("streamSystemVolume:" + this.streamGameVolume);
         var4.println("streamKbps:" + this.streamKbps);
         var4.println("streamFps:" + this.streamFps);
         var4.println("streamCompression:" + this.streamCompression);
         var4.println("streamSendMetadata:" + this.streamSendMetadata);
         var4.println("streamPreferredServer:" + this.streamPreferredServer);
         var4.println("streamChatEnabled:" + this.streamChatEnabled);
         var4.println("streamChatUserFilter:" + this.streamChatUserFilter);
         var4.println("streamMicToggleBehavior:" + this.streamMicToggleBehavior);
         var4.println("forceUnicodeFont:" + this.forceUnicodeFont);
         var4.println("allowBlockAlternatives:" + this.allowBlockAlternatives);
         var4.println("reducedDebugInfo:" + this.reducedDebugInfo);
         var4.println("useNativeTransport:" + this.useNativeTransport);
         var4.println("entityShadows:" + this.entityShadows);
         var4.println("realmsNotifications:" + this.realmsNotifications);

         for (KeyBinding var8 : this.keyBindings) {
            var4.println("key_" + var8.getKeyDescription() + ":" + var8.getKeyCode());
         }

         for (SoundCategory var16 : SoundCategory.values()) {
            var4.println("soundCategory_" + var16.getCategoryName() + ":" + this.getSoundLevel(var16));
         }

         for (EnumPlayerModelParts var17 : EnumPlayerModelParts.values()) {
            var4.println("modelPart_" + var17.getPartName() + ":" + this.setModelParts.contains(var17));
         }

         var4.close();
      } catch (Exception var9) {
         logger.error("Failed to save options", var9);
      }

      this.saveOfOptions();
      this.sendSettingsToServer();
   }

   public float getSoundLevel(SoundCategory soundCategory) {
      return this.mapSoundLevels.getOrDefault(soundCategory, 1.0F);
   }

   public void setSoundLevel(SoundCategory soundCategory, float float2) {
      this.mc.getSoundHandler().setSoundLevel(soundCategory, float2);
      this.mapSoundLevels.put(soundCategory, float2);
   }

   public void sendSettingsToServer() {
      if (this.mc.thePlayer != null) {
         int var4 = 0;

         for (EnumPlayerModelParts var6 : this.setModelParts) {
            var4 |= var6.getPartMask();
         }

         this.mc
            .thePlayer
            .sendQueue
            .Method754(new C15PacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, var4));
      }
   }

   public Set<EnumPlayerModelParts> getModelParts() {
      return ImmutableSet.copyOf(this.setModelParts);
   }

   public void setModelPartEnabled(EnumPlayerModelParts enumPlayerModelParts, boolean boolean2) {
      if (boolean2) {
         this.setModelParts.add(enumPlayerModelParts);
      } else {
         this.setModelParts.remove(enumPlayerModelParts);
      }

      this.sendSettingsToServer();
   }

   public void switchModelPartEnabled(EnumPlayerModelParts enumPlayerModelParts) {
      if (!this.getModelParts().contains(enumPlayerModelParts)) {
         this.setModelParts.add(enumPlayerModelParts);
      } else {
         this.setModelParts.remove(enumPlayerModelParts);
      }

      this.sendSettingsToServer();
   }

   public int shouldRenderClouds() {
      return this.renderDistanceChunks >= 4 ? this.clouds : 0;
   }

   public boolean isUsingNativeTransport() {
      return this.useNativeTransport;
   }

   private void setOptionFloatValueOF(GameSettings.Options options, float float2) {
      if (options == GameSettings.Options.CLOUD_HEIGHT) {
         this.ofCloudsHeight = float2;
         this.mc.renderGlobal.resetClouds();
      }

      if (options == GameSettings.Options.AO_LEVEL) {
         this.ofAoLevel = float2;
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.AA_LEVEL) {
         int var6 = (int)float2;
         if (var6 > 0 && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
            return;
         }

         int[] var7 = new int[]{0, 2, 4, 6, 8, 12, 16};
         this.ofAaLevel = 0;

         for (int var11 : var7) {
            if (var6 >= var11) {
               this.ofAaLevel = var11;
            }
         }

         this.ofAaLevel = Config.Method255(this.ofAaLevel, 0, 16);
      }

      if (options == GameSettings.Options.AF_LEVEL) {
         int var12 = (int)float2;
         if (var12 > 1 && Config.isShaders()) {
            Config.showGuiMessage(Lang.get("of.message.af.shaders1"), Lang.get("of.message.af.shaders2"));
            return;
         }

         this.ofAfLevel = 1;

         while (this.ofAfLevel * 2 <= var12) {
            this.ofAfLevel *= 2;
         }

         this.ofAfLevel = Config.Method255(this.ofAfLevel, 1, 16);
         this.mc.refreshResources();
      }

      if (options == GameSettings.Options.MIPMAP_TYPE) {
         int var13 = (int)float2;
         this.ofMipmapType = Config.Method255(var13, 0, 3);
         this.mc.refreshResources();
      }

      if (options == GameSettings.Options.FULLSCREEN_MODE) {
         int var14 = (int)float2 - 1;
         String[] var15 = Config.Method272();
         if (var14 < 0 || var14 >= var15.length) {
            this.ofFullscreenMode = "Default";
            return;
         }

         this.ofFullscreenMode = var15[var14];
      }
   }

   private float getOptionFloatValueOF(GameSettings.Options options) {
      if (options == GameSettings.Options.CLOUD_HEIGHT) {
         return this.ofCloudsHeight;
      }

      if (options == GameSettings.Options.AO_LEVEL) {
         return this.ofAoLevel;
      }

      if (options == GameSettings.Options.AA_LEVEL) {
         return this.ofAaLevel;
      }

      if (options == GameSettings.Options.AF_LEVEL) {
         return this.ofAfLevel;
      }

      if (options == GameSettings.Options.MIPMAP_TYPE) {
         return this.ofMipmapType;
      }

      if (options != GameSettings.Options.FRAMERATE_LIMIT) {
         if (options == GameSettings.Options.FULLSCREEN_MODE) {
            if (this.ofFullscreenMode.equals("Default")) {
               return 0.0F;
            }

            int var6 = Arrays.asList(Config.Method272()).indexOf(this.ofFullscreenMode);
            return var6 < 0 ? 0.0F : var6 + 1;
         } else {
            return Float.MAX_VALUE;
         }
      } else {
         return this.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync ? 0.0F : this.limitFramerate;
      }
   }

   private void setOptionValueOF(GameSettings.Options options, int integer) {
      if (options == GameSettings.Options.FOG_FANCY) {
         switch (this.ofFogType) {
            case 1:
               this.ofFogType = 2;
               if (!Config.isFancyFogAvailable()) {
                  this.ofFogType = 3;
               }
               break;
            case 2:
               this.ofFogType = 3;
               break;
            default:
               this.ofFogType = 1;
         }
      }

      if (options == GameSettings.Options.FOG_START) {
         this.ofFogStart += 0.2F;
         if (this.ofFogStart > 0.81F) {
            this.ofFogStart = 0.2F;
         }
      }

      if (options == GameSettings.Options.SMOOTH_FPS) {
         this.ofSmoothFps = !this.ofSmoothFps;
      }

      if (options == GameSettings.Options.SMOOTH_WORLD) {
         this.ofSmoothWorld = !this.ofSmoothWorld;
         Config.updateThreadPriorities();
      }

      if (options == GameSettings.Options.CLOUDS) {
         this.ofClouds++;
         if (this.ofClouds > 3) {
            this.ofClouds = 0;
         }

         this.updateRenderClouds();
         this.mc.renderGlobal.resetClouds();
      }

      if (options == GameSettings.Options.TREES) {
         this.ofTrees = nextValue(this.ofTrees, OF_TREES_VALUES);
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.DROPPED_ITEMS) {
         this.ofDroppedItems++;
         if (this.ofDroppedItems > 2) {
            this.ofDroppedItems = 0;
         }
      }

      if (options == GameSettings.Options.RAIN) {
         this.ofRain++;
         if (this.ofRain > 3) {
            this.ofRain = 0;
         }
      }

      if (options == GameSettings.Options.ANIMATED_WATER) {
         this.ofAnimatedWater++;
         if (this.ofAnimatedWater == 1) {
            this.ofAnimatedWater++;
         }

         if (this.ofAnimatedWater > 2) {
            this.ofAnimatedWater = 0;
         }
      }

      if (options == GameSettings.Options.ANIMATED_LAVA) {
         this.ofAnimatedLava++;
         if (this.ofAnimatedLava == 1) {
            this.ofAnimatedLava++;
         }

         if (this.ofAnimatedLava > 2) {
            this.ofAnimatedLava = 0;
         }
      }

      if (options == GameSettings.Options.ANIMATED_FIRE) {
         this.ofAnimatedFire = !this.ofAnimatedFire;
      }

      if (options == GameSettings.Options.ANIMATED_PORTAL) {
         this.ofAnimatedPortal = !this.ofAnimatedPortal;
      }

      if (options == GameSettings.Options.ANIMATED_REDSTONE) {
         this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
      }

      if (options == GameSettings.Options.ANIMATED_EXPLOSION) {
         this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
      }

      if (options == GameSettings.Options.ANIMATED_FLAME) {
         this.ofAnimatedFlame = !this.ofAnimatedFlame;
      }

      if (options == GameSettings.Options.ANIMATED_SMOKE) {
         this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
      }

      if (options == GameSettings.Options.VOID_PARTICLES) {
         this.ofVoidParticles = !this.ofVoidParticles;
      }

      if (options == GameSettings.Options.WATER_PARTICLES) {
         this.ofWaterParticles = !this.ofWaterParticles;
      }

      if (options == GameSettings.Options.PORTAL_PARTICLES) {
         this.ofPortalParticles = !this.ofPortalParticles;
      }

      if (options == GameSettings.Options.POTION_PARTICLES) {
         this.ofPotionParticles = !this.ofPotionParticles;
      }

      if (options == GameSettings.Options.FIREWORK_PARTICLES) {
         this.ofFireworkParticles = !this.ofFireworkParticles;
      }

      if (options == GameSettings.Options.DRIPPING_WATER_LAVA) {
         this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
      }

      if (options == GameSettings.Options.ANIMATED_TERRAIN) {
         this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
      }

      if (options == GameSettings.Options.ANIMATED_TEXTURES) {
         this.ofAnimatedTextures = !this.ofAnimatedTextures;
      }

      if (options == GameSettings.Options.RAIN_SPLASH) {
         this.ofRainSplash = !this.ofRainSplash;
      }

      if (options == GameSettings.Options.LAGOMETER) {
         this.ofLagometer = !this.ofLagometer;
      }

      if (options == GameSettings.Options.SHOW_FPS) {
         this.ofShowFps = !this.ofShowFps;
      }

      if (options == GameSettings.Options.AUTOSAVE_TICKS) {
         short var6 = 900;
         this.ofAutoSaveTicks = Math.max(this.ofAutoSaveTicks / var6 * var6, var6);
         this.ofAutoSaveTicks *= 2;
         if (this.ofAutoSaveTicks > 28800) {
            this.ofAutoSaveTicks = var6;
         }
      }

      if (options == GameSettings.Options.BETTER_GRASS) {
         this.ofBetterGrass++;
         if (this.ofBetterGrass > 3) {
            this.ofBetterGrass = 1;
         }

         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.CONNECTED_TEXTURES) {
         this.ofConnectedTextures++;
         if (this.ofConnectedTextures > 3) {
            this.ofConnectedTextures = 1;
         }

         if (this.ofConnectedTextures == 2) {
            this.mc.renderGlobal.loadRenderers();
         } else {
            this.mc.refreshResources();
         }
      }

      if (options == GameSettings.Options.WEATHER) {
         this.ofWeather = !this.ofWeather;
      }

      if (options == GameSettings.Options.SKY) {
         this.ofSky = !this.ofSky;
      }

      if (options == GameSettings.Options.STARS) {
         this.ofStars = !this.ofStars;
      }

      if (options == GameSettings.Options.SUN_MOON) {
         this.ofSunMoon = !this.ofSunMoon;
      }

      if (options == GameSettings.Options.VIGNETTE) {
         this.ofVignette++;
         if (this.ofVignette > 2) {
            this.ofVignette = 0;
         }
      }

      if (options == GameSettings.Options.CHUNK_UPDATES) {
         this.ofChunkUpdates++;
         if (this.ofChunkUpdates > 5) {
            this.ofChunkUpdates = 1;
         }
      }

      if (options == GameSettings.Options.CHUNK_UPDATES_DYNAMIC) {
         this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
      }

      if (options == GameSettings.Options.TIME) {
         this.ofTime++;
         if (this.ofTime > 2) {
            this.ofTime = 0;
         }
      }

      if (options == GameSettings.Options.CLEAR_WATER) {
         this.ofClearWater = !this.ofClearWater;
         this.updateWaterOpacity();
      }

      if (options == GameSettings.Options.PROFILER) {
         this.ofProfiler = !this.ofProfiler;
      }

      if (options == GameSettings.Options.BETTER_SNOW) {
         this.ofBetterSnow = !this.ofBetterSnow;
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.SWAMP_COLORS) {
         this.ofSwampColors = !this.ofSwampColors;
         CustomColors.updateUseDefaultGrassFoliageColors();
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.RANDOM_ENTITIES) {
         this.ofRandomEntities = !this.ofRandomEntities;
         RandomEntities.update();
      }

      if (options == GameSettings.Options.SMOOTH_BIOMES) {
         this.ofSmoothBiomes = !this.ofSmoothBiomes;
         CustomColors.updateUseDefaultGrassFoliageColors();
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.CUSTOM_FONTS) {
         this.ofCustomFonts = !this.ofCustomFonts;
         this.mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
         this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
      }

      if (options == GameSettings.Options.CUSTOM_COLORS) {
         this.ofCustomColors = !this.ofCustomColors;
         CustomColors.update();
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.CUSTOM_ITEMS) {
         this.ofCustomItems = !this.ofCustomItems;
         this.mc.refreshResources();
      }

      if (options == GameSettings.Options.CUSTOM_SKY) {
         this.ofCustomSky = !this.ofCustomSky;
         CustomSky.update();
      }

      if (options == GameSettings.Options.SHOW_CAPES) {
         this.ofShowCapes = !this.ofShowCapes;
      }

      if (options == GameSettings.Options.NATURAL_TEXTURES) {
         this.ofNaturalTextures = !this.ofNaturalTextures;
         NaturalTextures.update();
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.EMISSIVE_TEXTURES) {
         this.ofEmissiveTextures = !this.ofEmissiveTextures;
         this.mc.refreshResources();
      }

      if (options == GameSettings.Options.FAST_MATH) {
         this.ofFastMath = !this.ofFastMath;
         MathHelper.fastMath = this.ofFastMath;
      }

      if (options == GameSettings.Options.TRANSLUCENT_BLOCKS) {
         if (this.ofTranslucentBlocks == 0) {
            this.ofTranslucentBlocks = 1;
         } else if (this.ofTranslucentBlocks == 1) {
            this.ofTranslucentBlocks = 2;
         } else if (this.ofTranslucentBlocks == 2) {
            this.ofTranslucentBlocks = 0;
         } else {
            this.ofTranslucentBlocks = 0;
         }

         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.LAZY_CHUNK_LOADING) {
         this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
      }

      if (options == GameSettings.Options.RENDER_REGIONS) {
         this.ofRenderRegions = !this.ofRenderRegions;
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.SMART_ANIMATIONS) {
         this.ofSmartAnimations = !this.ofSmartAnimations;
         this.mc.renderGlobal.loadRenderers();
      }

      if (options == GameSettings.Options.DYNAMIC_FOV) {
         this.ofDynamicFov = !this.ofDynamicFov;
      }

      if (options == GameSettings.Options.ALTERNATE_BLOCKS) {
         this.ofAlternateBlocks = !this.ofAlternateBlocks;
         this.mc.refreshResources();
      }

      if (options == GameSettings.Options.DYNAMIC_LIGHTS) {
         this.ofDynamicLights = nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
         DynamicLights.removeLights(this.mc.renderGlobal);
      }

      if (options == GameSettings.Options.SCREENSHOT_SIZE) {
         this.ofScreenshotSize++;
         if (this.ofScreenshotSize > 4) {
            this.ofScreenshotSize = 1;
         }

         if (!OpenGlHelper.isFramebufferEnabled()) {
            this.ofScreenshotSize = 1;
         }
      }

      if (options == GameSettings.Options.CUSTOM_ENTITY_MODELS) {
         this.ofCustomEntityModels = !this.ofCustomEntityModels;
         this.mc.refreshResources();
      }

      if (options == GameSettings.Options.CUSTOM_GUIS) {
         this.ofCustomGuis = !this.ofCustomGuis;
         CustomGuis.update();
      }

      if (options == GameSettings.Options.SHOW_GL_ERRORS) {
         this.ofShowGlErrors = !this.ofShowGlErrors;
      }

      if (options == GameSettings.Options.HELD_ITEM_TOOLTIPS) {
         this.heldItemTooltips = !this.heldItemTooltips;
      }

      if (options == GameSettings.Options.ADVANCED_TOOLTIPS) {
         this.advancedItemTooltips = !this.advancedItemTooltips;
      }
   }

   private String getKeyBindingOF(GameSettings.Options options) {
      String var5 = I18n.format(options.getEnumString()) + ": ";
      if (var5 == null) {
         var5 = options.getEnumString();
      }

      if (options == GameSettings.Options.RENDER_DISTANCE) {
         int var14 = (int)this.getOptionFloatValue(options);
         String var7 = I18n.format("options.renderDistance.tiny");
         byte var8 = 2;
         if (var14 >= 4) {
            var7 = I18n.format("options.renderDistance.short");
            var8 = 4;
         }

         if (var14 >= 8) {
            var7 = I18n.format("options.renderDistance.normal");
            var8 = 8;
         }

         if (var14 >= 16) {
            var7 = I18n.format("options.renderDistance.far");
            var8 = 16;
         }

         if (var14 >= 32) {
            var7 = Lang.get("of.options.renderDistance.extreme");
            var8 = 32;
         }

         if (var14 >= 48) {
            var7 = Lang.get("of.options.renderDistance.insane");
            var8 = 48;
         }

         if (var14 >= 64) {
            var7 = Lang.get("of.options.renderDistance.ludicrous");
            var8 = 64;
         }

         int var9 = this.renderDistanceChunks - var8;
         String var10 = var7;
         if (var9 > 0) {
            var10 = var7 + "+";
         }

         return var5 + var14 + " " + var10;
      } else if (options == GameSettings.Options.FOG_FANCY) {
         switch (this.ofFogType) {
            case 1:
               return var5 + Lang.getFast();
            case 2:
               return var5 + Lang.getFancy();
            default:
               return var5 + Lang.getOff();
         }
      } else {
         if (options == GameSettings.Options.FOG_START) {
            return var5 + this.ofFogStart;
         }

         if (options == GameSettings.Options.MIPMAP_TYPE) {
            switch (this.ofMipmapType) {
               case 0:
                  return var5 + Lang.get("of.options.mipmap.nearest");
               case 1:
                  return var5 + Lang.get("of.options.mipmap.linear");
               case 2:
                  return var5 + Lang.get("of.options.mipmap.bilinear");
               case 3:
                  return var5 + Lang.get("of.options.mipmap.trilinear");
               default:
                  return var5 + "of.options.mipmap.nearest";
            }
         } else {
            if (options == GameSettings.Options.SMOOTH_FPS) {
               return this.ofSmoothFps ? var5 + Lang.getOn() : var5 + Lang.getOff();
            }

            if (options == GameSettings.Options.SMOOTH_WORLD) {
               return this.ofSmoothWorld ? var5 + Lang.getOn() : var5 + Lang.getOff();
            }

            if (options == GameSettings.Options.CLOUDS) {
               switch (this.ofClouds) {
                  case 1:
                     return var5 + Lang.getFast();
                  case 2:
                     return var5 + Lang.getFancy();
                  case 3:
                     return var5 + Lang.getOff();
                  default:
                     return var5 + Lang.getDefault();
               }
            } else if (options == GameSettings.Options.TREES) {
               switch (this.ofTrees) {
                  case 1:
                     return var5 + Lang.getFast();
                  case 2:
                     return var5 + Lang.getFancy();
                  case 3:
                  default:
                     return var5 + Lang.getDefault();
                  case 4:
                     return var5 + Lang.get("of.general.smart");
               }
            } else if (options == GameSettings.Options.DROPPED_ITEMS) {
               switch (this.ofDroppedItems) {
                  case 1:
                     return var5 + Lang.getFast();
                  case 2:
                     return var5 + Lang.getFancy();
                  default:
                     return var5 + Lang.getDefault();
               }
            } else if (options == GameSettings.Options.RAIN) {
               switch (this.ofRain) {
                  case 1:
                     return var5 + Lang.getFast();
                  case 2:
                     return var5 + Lang.getFancy();
                  case 3:
                     return var5 + Lang.getOff();
                  default:
                     return var5 + Lang.getDefault();
               }
            } else if (options == GameSettings.Options.ANIMATED_WATER) {
               switch (this.ofAnimatedWater) {
                  case 1:
                     return var5 + Lang.get("of.options.animation.dynamic");
                  case 2:
                     return var5 + Lang.getOff();
                  default:
                     return var5 + Lang.getOn();
               }
            } else if (options == GameSettings.Options.ANIMATED_LAVA) {
               switch (this.ofAnimatedLava) {
                  case 1:
                     return var5 + Lang.get("of.options.animation.dynamic");
                  case 2:
                     return var5 + Lang.getOff();
                  default:
                     return var5 + Lang.getOn();
               }
            } else {
               if (options == GameSettings.Options.ANIMATED_FIRE) {
                  return this.ofAnimatedFire ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.ANIMATED_PORTAL) {
                  return this.ofAnimatedPortal ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.ANIMATED_REDSTONE) {
                  return this.ofAnimatedRedstone ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.ANIMATED_EXPLOSION) {
                  return this.ofAnimatedExplosion ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.ANIMATED_FLAME) {
                  return this.ofAnimatedFlame ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.ANIMATED_SMOKE) {
                  return this.ofAnimatedSmoke ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.VOID_PARTICLES) {
                  return this.ofVoidParticles ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.WATER_PARTICLES) {
                  return this.ofWaterParticles ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.PORTAL_PARTICLES) {
                  return this.ofPortalParticles ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.POTION_PARTICLES) {
                  return this.ofPotionParticles ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.FIREWORK_PARTICLES) {
                  return this.ofFireworkParticles ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.DRIPPING_WATER_LAVA) {
                  return this.ofDrippingWaterLava ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.ANIMATED_TERRAIN) {
                  return this.ofAnimatedTerrain ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.ANIMATED_TEXTURES) {
                  return this.ofAnimatedTextures ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.RAIN_SPLASH) {
                  return this.ofRainSplash ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.LAGOMETER) {
                  return this.ofLagometer ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.SHOW_FPS) {
                  return this.ofShowFps ? var5 + Lang.getOn() : var5 + Lang.getOff();
               }

               if (options == GameSettings.Options.AUTOSAVE_TICKS) {
                  short var13 = 900;
                  return this.ofAutoSaveTicks <= var13
                     ? var5 + Lang.get("of.options.save.45s")
                     : (
                        this.ofAutoSaveTicks <= 1800
                           ? var5 + Lang.get("of.options.save.90s")
                           : (
                              this.ofAutoSaveTicks <= 3600
                                 ? var5 + Lang.get("of.options.save.3min")
                                 : (
                                    this.ofAutoSaveTicks <= 7200
                                       ? var5 + Lang.get("of.options.save.6min")
                                       : (this.ofAutoSaveTicks <= 14400 ? var5 + Lang.get("of.options.save.12min") : var5 + Lang.get("of.options.save.24min"))
                                 )
                           )
                     );
               }

               if (options == GameSettings.Options.BETTER_GRASS) {
                  switch (this.ofBetterGrass) {
                     case 1:
                        return var5 + Lang.getFast();
                     case 2:
                        return var5 + Lang.getFancy();
                     default:
                        return var5 + Lang.getOff();
                  }
               } else if (options == GameSettings.Options.CONNECTED_TEXTURES) {
                  switch (this.ofConnectedTextures) {
                     case 1:
                        return var5 + Lang.getFast();
                     case 2:
                        return var5 + Lang.getFancy();
                     default:
                        return var5 + Lang.getOff();
                  }
               } else {
                  if (options == GameSettings.Options.WEATHER) {
                     return this.ofWeather ? var5 + Lang.getOn() : var5 + Lang.getOff();
                  }

                  if (options == GameSettings.Options.SKY) {
                     return this.ofSky ? var5 + Lang.getOn() : var5 + Lang.getOff();
                  }

                  if (options == GameSettings.Options.STARS) {
                     return this.ofStars ? var5 + Lang.getOn() : var5 + Lang.getOff();
                  }

                  if (options == GameSettings.Options.SUN_MOON) {
                     return this.ofSunMoon ? var5 + Lang.getOn() : var5 + Lang.getOff();
                  }

                  if (options == GameSettings.Options.VIGNETTE) {
                     switch (this.ofVignette) {
                        case 1:
                           return var5 + Lang.getFast();
                        case 2:
                           return var5 + Lang.getFancy();
                        default:
                           return var5 + Lang.getDefault();
                     }
                  } else {
                     if (options == GameSettings.Options.CHUNK_UPDATES) {
                        return var5 + this.ofChunkUpdates;
                     }

                     if (options == GameSettings.Options.CHUNK_UPDATES_DYNAMIC) {
                        return this.ofChunkUpdatesDynamic ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     }

                     if (options == GameSettings.Options.TIME) {
                        return this.ofTime == 1
                           ? var5 + Lang.get("of.options.time.dayOnly")
                           : (this.ofTime == 2 ? var5 + Lang.get("of.options.time.nightOnly") : var5 + Lang.getDefault());
                     }

                     if (options == GameSettings.Options.CLEAR_WATER) {
                        return this.ofClearWater ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     }

                     if (options == GameSettings.Options.AA_LEVEL) {
                        String var12 = "";
                        if (this.ofAaLevel != Config.getAntialiasingLevel()) {
                           var12 = " (" + Lang.get("of.general.restart") + ")";
                        }

                        return this.ofAaLevel == 0 ? var5 + Lang.getOff() + var12 : var5 + this.ofAaLevel + var12;
                     } else if (options == GameSettings.Options.AF_LEVEL) {
                        return this.ofAfLevel == 1 ? var5 + Lang.getOff() : var5 + this.ofAfLevel;
                     } else if (options == GameSettings.Options.PROFILER) {
                        return this.ofProfiler ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.BETTER_SNOW) {
                        return this.ofBetterSnow ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.SWAMP_COLORS) {
                        return this.ofSwampColors ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.RANDOM_ENTITIES) {
                        return this.ofRandomEntities ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.SMOOTH_BIOMES) {
                        return this.ofSmoothBiomes ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.CUSTOM_FONTS) {
                        return this.ofCustomFonts ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.CUSTOM_COLORS) {
                        return this.ofCustomColors ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.CUSTOM_SKY) {
                        return this.ofCustomSky ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.SHOW_CAPES) {
                        return this.ofShowCapes ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.CUSTOM_ITEMS) {
                        return this.ofCustomItems ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.NATURAL_TEXTURES) {
                        return this.ofNaturalTextures ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.EMISSIVE_TEXTURES) {
                        return this.ofEmissiveTextures ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.FAST_MATH) {
                        return this.ofFastMath ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.TRANSLUCENT_BLOCKS) {
                        return this.ofTranslucentBlocks == 1
                           ? var5 + Lang.getFast()
                           : (this.ofTranslucentBlocks == 2 ? var5 + Lang.getFancy() : var5 + Lang.getDefault());
                     } else if (options == GameSettings.Options.LAZY_CHUNK_LOADING) {
                        return this.ofLazyChunkLoading ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.RENDER_REGIONS) {
                        return this.ofRenderRegions ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.SMART_ANIMATIONS) {
                        return this.ofSmartAnimations ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.DYNAMIC_FOV) {
                        return this.ofDynamicFov ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.ALTERNATE_BLOCKS) {
                        return this.ofAlternateBlocks ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.DYNAMIC_LIGHTS) {
                        int var11 = indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
                        return var5 + getTranslation(KEYS_DYNAMIC_LIGHTS, var11);
                     } else if (options == GameSettings.Options.SCREENSHOT_SIZE) {
                        return this.ofScreenshotSize <= 1 ? var5 + Lang.getDefault() : var5 + this.ofScreenshotSize + "x";
                     } else if (options == GameSettings.Options.CUSTOM_ENTITY_MODELS) {
                        return this.ofCustomEntityModels ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.CUSTOM_GUIS) {
                        return this.ofCustomGuis ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.SHOW_GL_ERRORS) {
                        return this.ofShowGlErrors ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.FULLSCREEN_MODE) {
                        return this.ofFullscreenMode.equals("Default") ? var5 + Lang.getDefault() : var5 + this.ofFullscreenMode;
                     } else if (options == GameSettings.Options.HELD_ITEM_TOOLTIPS) {
                        return this.heldItemTooltips ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.ADVANCED_TOOLTIPS) {
                        return this.advancedItemTooltips ? var5 + Lang.getOn() : var5 + Lang.getOff();
                     } else if (options == GameSettings.Options.FRAMERATE_LIMIT) {
                        float var6 = this.getOptionFloatValue(options);
                        return var6 == 0.0F
                           ? var5 + Lang.get("of.options.framerateLimit.vsync")
                           : (var6 == options.valueMax ? var5 + I18n.format("options.framerateLimit.max") : var5 + (int)var6 + " fps");
                     } else {
                        return null;
                     }
                  }
               }
            }
         }
      }
   }

   public void loadOfOptions() {
      try {
         File var4 = this.optionsFileOF;
         if (!var4.exists()) {
            var4 = this.optionsFile;
         }

         if (!var4.exists()) {
            return;
         }

         BufferedReader var5 = new BufferedReader(new InputStreamReader(new FileInputStream(var4), StandardCharsets.UTF_8));

         String var6;
         while ((var6 = var5.readLine()) != null) {
            try {
               String[] var7 = var6.split(":");
               if (var7[0].equals("ofRenderDistanceChunks") && var7.length >= 2) {
                  this.renderDistanceChunks = Integer.parseInt(var7[1]);
                  this.renderDistanceChunks = Config.Method255(this.renderDistanceChunks, 2, 1024);
               }

               if (var7[0].equals("ofFogType") && var7.length >= 2) {
                  this.ofFogType = Integer.parseInt(var7[1]);
                  this.ofFogType = Config.Method255(this.ofFogType, 1, 3);
               }

               if (var7[0].equals("ofFogStart") && var7.length >= 2) {
                  this.ofFogStart = Float.parseFloat(var7[1]);
                  if (this.ofFogStart < 0.2F) {
                     this.ofFogStart = 0.2F;
                  }

                  if (this.ofFogStart > 0.81F) {
                     this.ofFogStart = 0.8F;
                  }
               }

               if (var7[0].equals("ofMipmapType") && var7.length >= 2) {
                  this.ofMipmapType = Integer.parseInt(var7[1]);
                  this.ofMipmapType = Config.Method255(this.ofMipmapType, 0, 3);
               }

               if (var7[0].equals("ofOcclusionFancy") && var7.length >= 2) {
                  this.ofOcclusionFancy = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofSmoothFps") && var7.length >= 2) {
                  this.ofSmoothFps = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofSmoothWorld") && var7.length >= 2) {
                  this.ofSmoothWorld = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAoLevel") && var7.length >= 2) {
                  this.ofAoLevel = Float.parseFloat(var7[1]);
                  this.ofAoLevel = Config.Method256(this.ofAoLevel, 0.0F, 1.0F);
               }

               if (var7[0].equals("ofClouds") && var7.length >= 2) {
                  this.ofClouds = Integer.parseInt(var7[1]);
                  this.ofClouds = Config.Method255(this.ofClouds, 0, 3);
                  this.updateRenderClouds();
               }

               if (var7[0].equals("ofCloudsHeight") && var7.length >= 2) {
                  this.ofCloudsHeight = Float.parseFloat(var7[1]);
                  this.ofCloudsHeight = Config.Method256(this.ofCloudsHeight, 0.0F, 1.0F);
               }

               if (var7[0].equals("ofTrees") && var7.length >= 2) {
                  this.ofTrees = Integer.parseInt(var7[1]);
                  this.ofTrees = limit(this.ofTrees, OF_TREES_VALUES);
               }

               if (var7[0].equals("ofDroppedItems") && var7.length >= 2) {
                  this.ofDroppedItems = Integer.parseInt(var7[1]);
                  this.ofDroppedItems = Config.Method255(this.ofDroppedItems, 0, 2);
               }

               if (var7[0].equals("ofRain") && var7.length >= 2) {
                  this.ofRain = Integer.parseInt(var7[1]);
                  this.ofRain = Config.Method255(this.ofRain, 0, 3);
               }

               if (var7[0].equals("ofAnimatedWater") && var7.length >= 2) {
                  this.ofAnimatedWater = Integer.parseInt(var7[1]);
                  this.ofAnimatedWater = Config.Method255(this.ofAnimatedWater, 0, 2);
               }

               if (var7[0].equals("ofAnimatedLava") && var7.length >= 2) {
                  this.ofAnimatedLava = Integer.parseInt(var7[1]);
                  this.ofAnimatedLava = Config.Method255(this.ofAnimatedLava, 0, 2);
               }

               if (var7[0].equals("ofAnimatedFire") && var7.length >= 2) {
                  this.ofAnimatedFire = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAnimatedPortal") && var7.length >= 2) {
                  this.ofAnimatedPortal = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAnimatedRedstone") && var7.length >= 2) {
                  this.ofAnimatedRedstone = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAnimatedExplosion") && var7.length >= 2) {
                  this.ofAnimatedExplosion = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAnimatedFlame") && var7.length >= 2) {
                  this.ofAnimatedFlame = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAnimatedSmoke") && var7.length >= 2) {
                  this.ofAnimatedSmoke = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofVoidParticles") && var7.length >= 2) {
                  this.ofVoidParticles = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofWaterParticles") && var7.length >= 2) {
                  this.ofWaterParticles = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofPortalParticles") && var7.length >= 2) {
                  this.ofPortalParticles = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofPotionParticles") && var7.length >= 2) {
                  this.ofPotionParticles = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofFireworkParticles") && var7.length >= 2) {
                  this.ofFireworkParticles = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofDrippingWaterLava") && var7.length >= 2) {
                  this.ofDrippingWaterLava = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAnimatedTerrain") && var7.length >= 2) {
                  this.ofAnimatedTerrain = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAnimatedTextures") && var7.length >= 2) {
                  this.ofAnimatedTextures = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofRainSplash") && var7.length >= 2) {
                  this.ofRainSplash = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofLagometer") && var7.length >= 2) {
                  this.ofLagometer = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofShowFps") && var7.length >= 2) {
                  this.ofShowFps = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAutoSaveTicks") && var7.length >= 2) {
                  this.ofAutoSaveTicks = Integer.parseInt(var7[1]);
                  this.ofAutoSaveTicks = Config.Method255(this.ofAutoSaveTicks, 40, 40000);
               }

               if (var7[0].equals("ofBetterGrass") && var7.length >= 2) {
                  this.ofBetterGrass = Integer.parseInt(var7[1]);
                  this.ofBetterGrass = Config.Method255(this.ofBetterGrass, 1, 3);
               }

               if (var7[0].equals("ofConnectedTextures") && var7.length >= 2) {
                  this.ofConnectedTextures = Integer.parseInt(var7[1]);
                  this.ofConnectedTextures = Config.Method255(this.ofConnectedTextures, 1, 3);
               }

               if (var7[0].equals("ofWeather") && var7.length >= 2) {
                  this.ofWeather = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofSky") && var7.length >= 2) {
                  this.ofSky = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofStars") && var7.length >= 2) {
                  this.ofStars = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofSunMoon") && var7.length >= 2) {
                  this.ofSunMoon = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofVignette") && var7.length >= 2) {
                  this.ofVignette = Integer.parseInt(var7[1]);
                  this.ofVignette = Config.Method255(this.ofVignette, 0, 2);
               }

               if (var7[0].equals("ofChunkUpdates") && var7.length >= 2) {
                  this.ofChunkUpdates = Integer.parseInt(var7[1]);
                  this.ofChunkUpdates = Config.Method255(this.ofChunkUpdates, 1, 5);
               }

               if (var7[0].equals("ofChunkUpdatesDynamic") && var7.length >= 2) {
                  this.ofChunkUpdatesDynamic = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofTime") && var7.length >= 2) {
                  this.ofTime = Integer.parseInt(var7[1]);
                  this.ofTime = Config.Method255(this.ofTime, 0, 2);
               }

               if (var7[0].equals("ofClearWater") && var7.length >= 2) {
                  this.ofClearWater = Boolean.parseBoolean(var7[1]);
                  this.updateWaterOpacity();
               }

               if (var7[0].equals("ofAaLevel") && var7.length >= 2) {
                  this.ofAaLevel = Integer.parseInt(var7[1]);
                  this.ofAaLevel = Config.Method255(this.ofAaLevel, 0, 16);
               }

               if (var7[0].equals("ofAfLevel") && var7.length >= 2) {
                  this.ofAfLevel = Integer.parseInt(var7[1]);
                  this.ofAfLevel = Config.Method255(this.ofAfLevel, 1, 16);
               }

               if (var7[0].equals("ofProfiler") && var7.length >= 2) {
                  this.ofProfiler = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofBetterSnow") && var7.length >= 2) {
                  this.ofBetterSnow = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofSwampColors") && var7.length >= 2) {
                  this.ofSwampColors = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofRandomEntities") && var7.length >= 2) {
                  this.ofRandomEntities = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofSmoothBiomes") && var7.length >= 2) {
                  this.ofSmoothBiomes = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofCustomFonts") && var7.length >= 2) {
                  this.ofCustomFonts = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofCustomColors") && var7.length >= 2) {
                  this.ofCustomColors = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofCustomItems") && var7.length >= 2) {
                  this.ofCustomItems = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofCustomSky") && var7.length >= 2) {
                  this.ofCustomSky = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofShowCapes") && var7.length >= 2) {
                  this.ofShowCapes = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofNaturalTextures") && var7.length >= 2) {
                  this.ofNaturalTextures = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofEmissiveTextures") && var7.length >= 2) {
                  this.ofEmissiveTextures = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofLazyChunkLoading") && var7.length >= 2) {
                  this.ofLazyChunkLoading = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofRenderRegions") && var7.length >= 2) {
                  this.ofRenderRegions = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofSmartAnimations") && var7.length >= 2) {
                  this.ofSmartAnimations = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofDynamicFov") && var7.length >= 2) {
                  this.ofDynamicFov = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofAlternateBlocks") && var7.length >= 2) {
                  this.ofAlternateBlocks = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofDynamicLights") && var7.length >= 2) {
                  this.ofDynamicLights = Integer.parseInt(var7[1]);
                  this.ofDynamicLights = limit(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
               }

               if (var7[0].equals("ofScreenshotSize") && var7.length >= 2) {
                  this.ofScreenshotSize = Integer.parseInt(var7[1]);
                  this.ofScreenshotSize = Config.Method255(this.ofScreenshotSize, 1, 4);
               }

               if (var7[0].equals("ofCustomEntityModels") && var7.length >= 2) {
                  this.ofCustomEntityModels = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofCustomGuis") && var7.length >= 2) {
                  this.ofCustomGuis = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofShowGlErrors") && var7.length >= 2) {
                  this.ofShowGlErrors = Boolean.parseBoolean(var7[1]);
               }

               if (var7[0].equals("ofFullscreenMode") && var7.length >= 2) {
                  this.ofFullscreenMode = var7[1];
               }

               if (var7[0].equals("ofFastMath") && var7.length >= 2) {
                  this.ofFastMath = Boolean.parseBoolean(var7[1]);
                  MathHelper.fastMath = this.ofFastMath;
               }

               if (var7[0].equals("ofTranslucentBlocks") && var7.length >= 2) {
                  this.ofTranslucentBlocks = Integer.parseInt(var7[1]);
                  this.ofTranslucentBlocks = Config.Method255(this.ofTranslucentBlocks, 0, 2);
               }

               if (var7[0].equals("key_" + this.ofKeyBindZoom.getKeyDescription())) {
                  this.ofKeyBindZoom.setKeyCode(Integer.parseInt(var7[1]));
               }
            } catch (Exception var8) {
               Config.Method251("Skipping bad option: " + var6);
               var8.printStackTrace();
            }
         }

         KeyUtils.fixKeyConflicts(this.keyBindings, new KeyBinding[]{this.ofKeyBindZoom});
         KeyBinding.resetKeyBindingArrayAndHash();
         var5.close();
      } catch (Exception var9) {
         Config.Method252("Failed to load options");
         var9.printStackTrace();
      }
   }

   public void saveOfOptions() {
      try {
         PrintWriter var1 = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFileOF), StandardCharsets.UTF_8));
         var1.println("ofFogType:" + this.ofFogType);
         var1.println("ofFogStart:" + this.ofFogStart);
         var1.println("ofMipmapType:" + this.ofMipmapType);
         var1.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
         var1.println("ofSmoothFps:" + this.ofSmoothFps);
         var1.println("ofSmoothWorld:" + this.ofSmoothWorld);
         var1.println("ofAoLevel:" + this.ofAoLevel);
         var1.println("ofClouds:" + this.ofClouds);
         var1.println("ofCloudsHeight:" + this.ofCloudsHeight);
         var1.println("ofTrees:" + this.ofTrees);
         var1.println("ofDroppedItems:" + this.ofDroppedItems);
         var1.println("ofRain:" + this.ofRain);
         var1.println("ofAnimatedWater:" + this.ofAnimatedWater);
         var1.println("ofAnimatedLava:" + this.ofAnimatedLava);
         var1.println("ofAnimatedFire:" + this.ofAnimatedFire);
         var1.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
         var1.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
         var1.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
         var1.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
         var1.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
         var1.println("ofVoidParticles:" + this.ofVoidParticles);
         var1.println("ofWaterParticles:" + this.ofWaterParticles);
         var1.println("ofPortalParticles:" + this.ofPortalParticles);
         var1.println("ofPotionParticles:" + this.ofPotionParticles);
         var1.println("ofFireworkParticles:" + this.ofFireworkParticles);
         var1.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
         var1.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
         var1.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
         var1.println("ofRainSplash:" + this.ofRainSplash);
         var1.println("ofLagometer:" + this.ofLagometer);
         var1.println("ofShowFps:" + this.ofShowFps);
         var1.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
         var1.println("ofBetterGrass:" + this.ofBetterGrass);
         var1.println("ofConnectedTextures:" + this.ofConnectedTextures);
         var1.println("ofWeather:" + this.ofWeather);
         var1.println("ofSky:" + this.ofSky);
         var1.println("ofStars:" + this.ofStars);
         var1.println("ofSunMoon:" + this.ofSunMoon);
         var1.println("ofVignette:" + this.ofVignette);
         var1.println("ofChunkUpdates:" + this.ofChunkUpdates);
         var1.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
         var1.println("ofTime:" + this.ofTime);
         var1.println("ofClearWater:" + this.ofClearWater);
         var1.println("ofAaLevel:" + this.ofAaLevel);
         var1.println("ofAfLevel:" + this.ofAfLevel);
         var1.println("ofProfiler:" + this.ofProfiler);
         var1.println("ofBetterSnow:" + this.ofBetterSnow);
         var1.println("ofSwampColors:" + this.ofSwampColors);
         var1.println("ofRandomEntities:" + this.ofRandomEntities);
         var1.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
         var1.println("ofCustomFonts:" + this.ofCustomFonts);
         var1.println("ofCustomColors:" + this.ofCustomColors);
         var1.println("ofCustomItems:" + this.ofCustomItems);
         var1.println("ofCustomSky:" + this.ofCustomSky);
         var1.println("ofShowCapes:" + this.ofShowCapes);
         var1.println("ofNaturalTextures:" + this.ofNaturalTextures);
         var1.println("ofEmissiveTextures:" + this.ofEmissiveTextures);
         var1.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
         var1.println("ofRenderRegions:" + this.ofRenderRegions);
         var1.println("ofSmartAnimations:" + this.ofSmartAnimations);
         var1.println("ofDynamicFov:" + this.ofDynamicFov);
         var1.println("ofAlternateBlocks:" + this.ofAlternateBlocks);
         var1.println("ofDynamicLights:" + this.ofDynamicLights);
         var1.println("ofScreenshotSize:" + this.ofScreenshotSize);
         var1.println("ofCustomEntityModels:" + this.ofCustomEntityModels);
         var1.println("ofCustomGuis:" + this.ofCustomGuis);
         var1.println("ofShowGlErrors:" + this.ofShowGlErrors);
         var1.println("ofFullscreenMode:" + this.ofFullscreenMode);
         var1.println("ofFastMath:" + this.ofFastMath);
         var1.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
         var1.println("key_" + this.ofKeyBindZoom.getKeyDescription() + ":" + this.ofKeyBindZoom.getKeyCode());
         var1.close();
      } catch (Exception var2) {
         Config.Method252("Failed to save options");
         var2.printStackTrace();
      }
   }

   private void updateRenderClouds() {
      switch (this.ofClouds) {
         case 1:
            this.clouds = 1;
            break;
         case 2:
            this.clouds = 2;
            break;
         case 3:
            this.clouds = 0;
            break;
         default:
            if (this.fancyGraphics) {
               this.clouds = 2;
            } else {
               this.clouds = 1;
            }
      }
   }

   public void resetSettings() {
      this.renderDistanceChunks = 8;
      this.viewBobbing = true;
      this.anaglyph = false;
      this.limitFramerate = (int)GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
      this.enableVsync = false;
      this.updateVSync();
      this.mipmapLevels = 4;
      this.fancyGraphics = true;
      this.ambientOcclusion = 2;
      this.clouds = 2;
      this.fovSetting = 70.0F;
      this.gammaSetting = 0.0F;
      this.guiScale = 0;
      this.particleSetting = 0;
      this.heldItemTooltips = true;
      this.useVbo = false;
      this.forceUnicodeFont = false;
      this.ofFogType = 1;
      this.ofFogStart = 0.8F;
      this.ofMipmapType = 0;
      this.ofOcclusionFancy = false;
      this.ofSmartAnimations = false;
      this.ofSmoothFps = false;
      Config.updateAvailableProcessors();
      this.ofSmoothWorld = Config.isSingleProcessor();
      this.ofLazyChunkLoading = false;
      this.ofRenderRegions = false;
      this.ofFastMath = false;
      this.ofTranslucentBlocks = 0;
      this.ofDynamicFov = true;
      this.ofAlternateBlocks = true;
      this.ofDynamicLights = 3;
      this.ofScreenshotSize = 1;
      this.ofCustomEntityModels = true;
      this.ofCustomGuis = true;
      this.ofShowGlErrors = true;
      this.ofAoLevel = 1.0F;
      this.ofAaLevel = 0;
      this.ofAfLevel = 1;
      this.ofClouds = 0;
      this.ofCloudsHeight = 0.0F;
      this.ofTrees = 0;
      this.ofRain = 0;
      this.ofBetterGrass = 3;
      this.ofAutoSaveTicks = 4000;
      this.ofLagometer = false;
      this.ofShowFps = false;
      this.ofProfiler = false;
      this.ofWeather = true;
      this.ofSky = true;
      this.ofStars = true;
      this.ofSunMoon = true;
      this.ofVignette = 0;
      this.ofChunkUpdates = 1;
      this.ofChunkUpdatesDynamic = false;
      this.ofTime = 0;
      this.ofClearWater = false;
      this.ofBetterSnow = false;
      this.ofFullscreenMode = "Default";
      this.ofSwampColors = true;
      this.ofRandomEntities = true;
      this.ofSmoothBiomes = true;
      this.ofCustomFonts = true;
      this.ofCustomColors = true;
      this.ofCustomItems = true;
      this.ofCustomSky = true;
      this.ofShowCapes = true;
      this.ofConnectedTextures = 2;
      this.ofNaturalTextures = false;
      this.ofEmissiveTextures = true;
      this.ofAnimatedWater = 0;
      this.ofAnimatedLava = 0;
      this.ofAnimatedFire = true;
      this.ofAnimatedPortal = true;
      this.ofAnimatedRedstone = true;
      this.ofAnimatedExplosion = true;
      this.ofAnimatedFlame = true;
      this.ofAnimatedSmoke = true;
      this.ofVoidParticles = true;
      this.ofWaterParticles = true;
      this.ofRainSplash = true;
      this.ofPortalParticles = true;
      this.ofPotionParticles = true;
      this.ofFireworkParticles = true;
      this.ofDrippingWaterLava = true;
      this.ofAnimatedTerrain = true;
      this.ofAnimatedTextures = true;
      Shaders.setShaderPack("OFF");
      Shaders.configAntialiasingLevel = 0;
      Shaders.uninit();
      Shaders.storeConfig();
      this.updateWaterOpacity();
      this.mc.refreshResources();
      this.saveOptions();
   }

   public void updateVSync() {
      Display.setVSyncEnabled(this.enableVsync);
   }

   private void updateWaterOpacity() {
      if (Config.isIntegratedServerRunning()) {
         Config.waterOpacityChanged = true;
      }

      ClearWater.updateWaterOpacity(this, this.mc.theWorld);
   }

   public void setAllAnimations(boolean boolean1) {
      int var5 = boolean1 ? 0 : 2;
      this.ofAnimatedWater = var5;
      this.ofAnimatedLava = var5;
      this.ofAnimatedFire = boolean1;
      this.ofAnimatedPortal = boolean1;
      this.ofAnimatedRedstone = boolean1;
      this.ofAnimatedExplosion = boolean1;
      this.ofAnimatedFlame = boolean1;
      this.ofAnimatedSmoke = boolean1;
      this.ofVoidParticles = boolean1;
      this.ofWaterParticles = boolean1;
      this.ofRainSplash = boolean1;
      this.ofPortalParticles = boolean1;
      this.ofPotionParticles = boolean1;
      this.ofFireworkParticles = boolean1;
      this.particleSetting = boolean1 ? 0 : 2;
      this.ofDrippingWaterLava = boolean1;
      this.ofAnimatedTerrain = boolean1;
      this.ofAnimatedTextures = boolean1;
   }

   private static int nextValue(int integer, int[] arr) {
      int var5 = indexOf(integer, arr);
      if (var5 < 0) {
         return arr[0];
      }

      if (++var5 >= arr.length) {
         var5 = 0;
      }

      return arr[var5];
   }

   private static int limit(int integer, int[] arr) {
      int var5 = indexOf(integer, arr);
      return var5 < 0 ? arr[0] : integer;
   }

   private static int indexOf(int integer, int[] arr) {
      for (int var5 = 0; var5 < arr.length; var5++) {
         if (arr[var5] == integer) {
            return var5;
         }
      }

      return -1;
   }

   public enum Options {
      INVERT_MOUSE("options.invertMouse", false, true),
      SENSITIVITY("options.sensitivity", true, false),
      FOV("options.fov", true, false, 30.0F, 110.0F, 1.0F),
      GAMMA("options.gamma", true, false),
      SATURATION("options.saturation", true, false),
      RENDER_DISTANCE("options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),
      VIEW_BOBBING("options.viewBobbing", false, true),
      ANAGLYPH("options.anaglyph", false, true),
      FRAMERATE_LIMIT("options.framerateLimit", true, false, 0.0F, 260.0F, 5.0F),
      FBO_ENABLE("options.fboEnable", false, true),
      RENDER_CLOUDS("options.renderClouds", false, false),
      GRAPHICS("options.graphics", false, false),
      AMBIENT_OCCLUSION("options.ao", false, false),
      GUI_SCALE("options.guiScale", false, false),
      PARTICLES("options.particles", false, false),
      CHAT_VISIBILITY("options.chat.visibility", false, false),
      CHAT_COLOR("options.chat.color", false, true),
      CHAT_LINKS("options.chat.links", false, true),
      CHAT_OPACITY("options.chat.opacity", true, false),
      CHAT_LINKS_PROMPT("options.chat.links.prompt", false, true),
      SNOOPER_ENABLED("options.snooper", false, true),
      USE_FULLSCREEN("options.fullscreen", false, true),
      ENABLE_VSYNC("options.vsync", false, true),
      USE_VBO("options.vbo", false, true),
      TOUCHSCREEN("options.touchscreen", false, true),
      CHAT_SCALE("options.chat.scale", true, false),
      CHAT_WIDTH("options.chat.width", true, false),
      CHAT_HEIGHT_FOCUSED("options.chat.height.focused", true, false),
      CHAT_HEIGHT_UNFOCUSED("options.chat.height.unfocused", true, false),
      MIPMAP_LEVELS("options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),
      FORCE_UNICODE_FONT("options.forceUnicodeFont", false, true),
      STREAM_BYTES_PER_PIXEL("options.stream.bytesPerPixel", true, false),
      STREAM_VOLUME_MIC("options.stream.micVolumne", true, false),
      STREAM_VOLUME_SYSTEM("options.stream.systemVolume", true, false),
      STREAM_KBPS("options.stream.kbps", true, false),
      STREAM_FPS("options.stream.fps", true, false),
      STREAM_COMPRESSION("options.stream.compression", false, false),
      STREAM_SEND_METADATA("options.stream.sendMetadata", false, true),
      STREAM_CHAT_ENABLED("options.stream.chat.enabled", false, false),
      STREAM_CHAT_USER_FILTER("options.stream.chat.userFilter", false, false),
      STREAM_MIC_TOGGLE_BEHAVIOR("options.stream.micToggleBehavior", false, false),
      BLOCK_ALTERNATIVES("options.blockAlternatives", false, true),
      REDUCED_DEBUG_INFO("options.reducedDebugInfo", false, true),
      ENTITY_SHADOWS("options.entityShadows", false, true),
      REALMS_NOTIFICATIONS("options.realmsNotifications", false, true),
      FOG_FANCY("of.options.FOG_FANCY", false, false),
      FOG_START("of.options.FOG_START", false, false),
      MIPMAP_TYPE("of.options.MIPMAP_TYPE", true, false, 0.0F, 3.0F, 1.0F),
      SMOOTH_FPS("of.options.SMOOTH_FPS", false, false),
      CLOUDS("of.options.CLOUDS", false, false),
      CLOUD_HEIGHT("of.options.CLOUD_HEIGHT", true, false),
      TREES("of.options.TREES", false, false),
      RAIN("of.options.RAIN", false, false),
      ANIMATED_WATER("of.options.ANIMATED_WATER", false, false),
      ANIMATED_LAVA("of.options.ANIMATED_LAVA", false, false),
      ANIMATED_FIRE("of.options.ANIMATED_FIRE", false, false),
      ANIMATED_PORTAL("of.options.ANIMATED_PORTAL", false, false),
      AO_LEVEL("of.options.AO_LEVEL", true, false),
      LAGOMETER("of.options.LAGOMETER", false, false),
      SHOW_FPS("of.options.SHOW_FPS", false, false),
      AUTOSAVE_TICKS("of.options.AUTOSAVE_TICKS", false, false),
      BETTER_GRASS("of.options.BETTER_GRASS", false, false),
      ANIMATED_REDSTONE("of.options.ANIMATED_REDSTONE", false, false),
      ANIMATED_EXPLOSION("of.options.ANIMATED_EXPLOSION", false, false),
      ANIMATED_FLAME("of.options.ANIMATED_FLAME", false, false),
      ANIMATED_SMOKE("of.options.ANIMATED_SMOKE", false, false),
      WEATHER("of.options.WEATHER", false, false),
      SKY("of.options.SKY", false, false),
      STARS("of.options.STARS", false, false),
      SUN_MOON("of.options.SUN_MOON", false, false),
      VIGNETTE("of.options.VIGNETTE", false, false),
      CHUNK_UPDATES("of.options.CHUNK_UPDATES", false, false),
      CHUNK_UPDATES_DYNAMIC("of.options.CHUNK_UPDATES_DYNAMIC", false, false),
      TIME("of.options.TIME", false, false),
      CLEAR_WATER("of.options.CLEAR_WATER", false, false),
      SMOOTH_WORLD("of.options.SMOOTH_WORLD", false, false),
      VOID_PARTICLES("of.options.VOID_PARTICLES", false, false),
      WATER_PARTICLES("of.options.WATER_PARTICLES", false, false),
      RAIN_SPLASH("of.options.RAIN_SPLASH", false, false),
      PORTAL_PARTICLES("of.options.PORTAL_PARTICLES", false, false),
      POTION_PARTICLES("of.options.POTION_PARTICLES", false, false),
      FIREWORK_PARTICLES("of.options.FIREWORK_PARTICLES", false, false),
      PROFILER("of.options.PROFILER", false, false),
      DRIPPING_WATER_LAVA("of.options.DRIPPING_WATER_LAVA", false, false),
      BETTER_SNOW("of.options.BETTER_SNOW", false, false),
      FULLSCREEN_MODE("of.options.FULLSCREEN_MODE", true, false, 0.0F, Config.getDisplayModes().length, 1.0F),
      ANIMATED_TERRAIN("of.options.ANIMATED_TERRAIN", false, false),
      SWAMP_COLORS("of.options.SWAMP_COLORS", false, false),
      RANDOM_ENTITIES("of.options.RANDOM_ENTITIES", false, false),
      SMOOTH_BIOMES("of.options.SMOOTH_BIOMES", false, false),
      CUSTOM_FONTS("of.options.CUSTOM_FONTS", false, false),
      CUSTOM_COLORS("of.options.CUSTOM_COLORS", false, false),
      SHOW_CAPES("of.options.SHOW_CAPES", false, false),
      CONNECTED_TEXTURES("of.options.CONNECTED_TEXTURES", false, false),
      CUSTOM_ITEMS("of.options.CUSTOM_ITEMS", false, false),
      AA_LEVEL("of.options.AA_LEVEL", true, false, 0.0F, 16.0F, 1.0F),
      AF_LEVEL("of.options.AF_LEVEL", true, false, 1.0F, 16.0F, 1.0F),
      ANIMATED_TEXTURES("of.options.ANIMATED_TEXTURES", false, false),
      NATURAL_TEXTURES("of.options.NATURAL_TEXTURES", false, false),
      EMISSIVE_TEXTURES("of.options.EMISSIVE_TEXTURES", false, false),
      HELD_ITEM_TOOLTIPS("of.options.HELD_ITEM_TOOLTIPS", false, false),
      DROPPED_ITEMS("of.options.DROPPED_ITEMS", false, false),
      LAZY_CHUNK_LOADING("of.options.LAZY_CHUNK_LOADING", false, false),
      CUSTOM_SKY("of.options.CUSTOM_SKY", false, false),
      FAST_MATH("of.options.FAST_MATH", false, false),
      TRANSLUCENT_BLOCKS("of.options.TRANSLUCENT_BLOCKS", false, false),
      DYNAMIC_FOV("of.options.DYNAMIC_FOV", false, false),
      DYNAMIC_LIGHTS("of.options.DYNAMIC_LIGHTS", false, false),
      ALTERNATE_BLOCKS("of.options.ALTERNATE_BLOCKS", false, false),
      CUSTOM_ENTITY_MODELS("of.options.CUSTOM_ENTITY_MODELS", false, false),
      ADVANCED_TOOLTIPS("of.options.ADVANCED_TOOLTIPS", false, false),
      SCREENSHOT_SIZE("of.options.SCREENSHOT_SIZE", false, false),
      CUSTOM_GUIS("of.options.CUSTOM_GUIS", false, false),
      RENDER_REGIONS("of.options.RENDER_REGIONS", false, false),
      SHOW_GL_ERRORS("of.options.SHOW_GL_ERRORS", false, false),
      SMART_ANIMATIONS("of.options.SMART_ANIMATIONS", false, false);

      private final boolean enumFloat;
      private final boolean enumBoolean;
      private final String enumString;
      private final float valueStep;
      private final float valueMin;
      private float valueMax;

      public static GameSettings.Options getEnumOptions(int integer) {
         for (GameSettings.Options var7 : values()) {
            if (var7.returnEnumOrdinal() == integer) {
               return var7;
            }
         }

         return null;
      }

      Options(String string3, boolean boolean4, boolean boolean5) {
         this(string3, boolean4, boolean5, 0.0F, 1.0F, 0.0F);
      }

      Options(String string3, boolean boolean4, boolean boolean5, float float6, float float7, float float8) {
         this.enumString = string3;
         this.enumFloat = boolean4;
         this.enumBoolean = boolean5;
         this.valueMin = float6;
         this.valueMax = float7;
         this.valueStep = float8;
      }

      public boolean getEnumFloat() {
         return this.enumFloat;
      }

      public boolean getEnumBoolean() {
         return this.enumBoolean;
      }

      public int returnEnumOrdinal() {
         return this.ordinal();
      }

      public String getEnumString() {
         return this.enumString;
      }

      public float getValueMax() {
         return this.valueMax;
      }

      public void setValueMax(float float1) {
         this.valueMax = float1;
      }

      public float normalizeValue(float float1) {
         return MathHelper.clamp_float((this.snapToStepClamp(float1) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
      }

      public float denormalizeValue(float float1) {
         return this.snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp_float(float1, 0.0F, 1.0F));
      }

      public float snapToStepClamp(float float1) {
         return MathHelper.clamp_float(this.snapToStep(float1), this.valueMin, this.valueMax);
      }

      private float snapToStep(float float1) {
         if (this.valueStep > 0.0F) {
            float1 = this.valueStep * Math.round(float1 / this.valueStep);
         }

         return float1;
      }

      private static GameSettings.Options[] Method969() {
         return new GameSettings.Options[]{
            INVERT_MOUSE,
            SENSITIVITY,
            FOV,
            GAMMA,
            SATURATION,
            RENDER_DISTANCE,
            VIEW_BOBBING,
            ANAGLYPH,
            FRAMERATE_LIMIT,
            FBO_ENABLE,
            RENDER_CLOUDS,
            GRAPHICS,
            AMBIENT_OCCLUSION,
            GUI_SCALE,
            PARTICLES,
            CHAT_VISIBILITY,
            CHAT_COLOR,
            CHAT_LINKS,
            CHAT_OPACITY,
            CHAT_LINKS_PROMPT,
            SNOOPER_ENABLED,
            USE_FULLSCREEN,
            ENABLE_VSYNC,
            USE_VBO,
            TOUCHSCREEN,
            CHAT_SCALE,
            CHAT_WIDTH,
            CHAT_HEIGHT_FOCUSED,
            CHAT_HEIGHT_UNFOCUSED,
            MIPMAP_LEVELS,
            FORCE_UNICODE_FONT,
            STREAM_BYTES_PER_PIXEL,
            STREAM_VOLUME_MIC,
            STREAM_VOLUME_SYSTEM,
            STREAM_KBPS,
            STREAM_FPS,
            STREAM_COMPRESSION,
            STREAM_SEND_METADATA,
            STREAM_CHAT_ENABLED,
            STREAM_CHAT_USER_FILTER,
            STREAM_MIC_TOGGLE_BEHAVIOR,
            BLOCK_ALTERNATIVES,
            REDUCED_DEBUG_INFO,
            ENTITY_SHADOWS,
            REALMS_NOTIFICATIONS,
            FOG_FANCY,
            FOG_START,
            MIPMAP_TYPE,
            SMOOTH_FPS,
            CLOUDS,
            CLOUD_HEIGHT,
            TREES,
            RAIN,
            ANIMATED_WATER,
            ANIMATED_LAVA,
            ANIMATED_FIRE,
            ANIMATED_PORTAL,
            AO_LEVEL,
            LAGOMETER,
            SHOW_FPS,
            AUTOSAVE_TICKS,
            BETTER_GRASS,
            ANIMATED_REDSTONE,
            ANIMATED_EXPLOSION,
            ANIMATED_FLAME,
            ANIMATED_SMOKE,
            WEATHER,
            SKY,
            STARS,
            SUN_MOON,
            VIGNETTE,
            CHUNK_UPDATES,
            CHUNK_UPDATES_DYNAMIC,
            TIME,
            CLEAR_WATER,
            SMOOTH_WORLD,
            VOID_PARTICLES,
            WATER_PARTICLES,
            RAIN_SPLASH,
            PORTAL_PARTICLES,
            POTION_PARTICLES,
            FIREWORK_PARTICLES,
            PROFILER,
            DRIPPING_WATER_LAVA,
            BETTER_SNOW,
            FULLSCREEN_MODE,
            ANIMATED_TERRAIN,
            SWAMP_COLORS,
            RANDOM_ENTITIES,
            SMOOTH_BIOMES,
            CUSTOM_FONTS,
            CUSTOM_COLORS,
            SHOW_CAPES,
            CONNECTED_TEXTURES,
            CUSTOM_ITEMS,
            AA_LEVEL,
            AF_LEVEL,
            ANIMATED_TEXTURES,
            NATURAL_TEXTURES,
            EMISSIVE_TEXTURES,
            HELD_ITEM_TOOLTIPS,
            DROPPED_ITEMS,
            LAZY_CHUNK_LOADING,
            CUSTOM_SKY,
            FAST_MATH,
            TRANSLUCENT_BLOCKS,
            DYNAMIC_FOV,
            DYNAMIC_LIGHTS,
            ALTERNATE_BLOCKS,
            CUSTOM_ENTITY_MODELS,
            ADVANCED_TOOLTIPS,
            SCREENSHOT_SIZE,
            CUSTOM_GUIS,
            RENDER_REGIONS,
            SHOW_GL_ERRORS,
            SMART_ANIMATIONS
         };
      }
   }
}
