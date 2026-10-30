package net.minecraft.client;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.moon.Moon;
import com.moon.events.impl.input.InputEvent;
import com.moon.events.impl.input.KeyPressEvent;
import com.moon.events.impl.input.MouseEvent;
import com.moon.events.impl.lifecycle.LoopEvent;
import com.moon.events.impl.lifecycle.TickEvent;
import com.moon.events.impl.player.AttackEvent;
import com.moon.events.impl.player.AttackInputEvent;
import com.moon.events.impl.player.InteractEvent;
import com.moon.events.impl.player.SlotChangeEvent;
import com.moon.events.impl.player.UseInputEvent;
import com.moon.events.impl.render.RenderAchievementEvent;
import com.moon.events.impl.render.ResizeEvent;
import com.moon.module.impl.combat.AutoClicker;
import com.moon.ui.login.LoginScreen;
import com.moon.viamcp.ViaMCP;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiControls;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMemoryErrorScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.main.GameConfiguration;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.FoliageColorReloadListener;
import net.minecraft.client.resources.GrassColorReloadListener;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.client.resources.ResourceIndex;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
import net.minecraft.client.resources.data.FontMetadataSection;
import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Bootstrap;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.profiler.IPlayerUsage;
import net.minecraft.profiler.PlayerUsageSnooper;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MinecraftError;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ScreenShotHelper;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;
import net.minecraft.util.Util;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.storage.AnvilSaveConverter;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.OpenGLException;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.IOUtil;
import org.lwjgl.util.glu.GLU;

public class Minecraft implements IThreadListener, IPlayerUsage {
   public static final boolean isRunningOnMac = Util.getOSType() == Util.EnumOS.OSX;
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation locationMojangPng = new ResourceLocation("textures/gui/title/mojang.png");
   private static final List<DisplayMode> macDisplayModes = Lists.newArrayList(new DisplayMode[]{new DisplayMode(2560, 1600), new DisplayMode(2880, 1800)});
   public static byte[] memoryReserve = new byte[10485760];
   private static Minecraft theMinecraft;
   private static int debugFPS;
   public final File mcDataDir;
   public final FrameTimer frameTimer = new FrameTimer();
   public final Profiler mcProfiler = new Profiler();
   public final DefaultResourcePack mcDefaultResourcePack;
   public final Timer timer = new Timer(20.0F);
   private final File fileResourcepacks;
   private final PropertyMap twitchDetails;
   private final PropertyMap profileProperties;
   private final boolean field_175613_B = true;
   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
   private final int tempDisplayWidth;
   private final int tempDisplayHeight;
   private final File fileAssets;
   private final String launchedVersion;
   private final Proxy proxy;
   private final boolean jvm64bit;
   private final boolean isDemo;
   private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
   private final List<IResourcePack> defaultResourcePacks = Lists.newArrayList();
   private final MinecraftSessionService sessionService;
   private final Queue<FutureTask<?>> scheduledTasks = Queues.newArrayDeque();
   private final long field_175615_aJ = 0L;
   private final Thread mcThread = Thread.currentThread();
   private final Queue<Runnable> E = new ArrayDeque<>();
   public PlayerControllerMP playerController;
   public int displayWidth;
   public int displayHeight;
   public WorldClient theWorld;
   public RenderGlobal renderGlobal;
   public EntityPlayerSP thePlayer;
   public Entity pointedEntity;
   public EffectRenderer effectRenderer;
   public FontRenderer fontRendererObj;
   public FontRenderer standardGalacticFontRenderer;
   public GuiScreen currentScreen;
   public LoadingScreenRenderer loadingScreen;
   public EntityRenderer entityRenderer;
   public GuiAchievement guiAchievement;
   public GuiIngame ingameGUI;
   public boolean skipRenderWorld;
   public MovingObjectPosition objectMouseOver;
   public GameSettings gameSettings;
   public MouseHelper mouseHelper;
   public boolean inGameHasFocus;
   public String debug = "";
   public boolean aa = false;
   public boolean field_175614_C = false;
   public boolean field_175611_D = false;
   public boolean renderChunksMany = true;
   long systemTime = getSystemTime();
   long startNanoTime = System.nanoTime();
   volatile boolean running = true;
   long debugUpdateTime = getSystemTime();
   int fpsCounter;
   long prevFrameTime = -1L;
   private Session session;
   private ServerData currentServerData;
   private TextureManager renderEngine;
   private boolean fullscreen;
   private boolean hasCrashed;
   private CrashReport crashReporter;
   private boolean connectedToRealms = false;
   private RenderManager renderManager;
   private RenderItem renderItem;
   private ItemRenderer itemRenderer;
   private Entity renderViewEntity;
   private boolean isGamePaused;
   private AutoClicker aw;
   public int leftClickCounter;
   private IntegratedServer theIntegratedServer;
   private ISaveFormat saveLoader;
   public int rightClickDelayTimer;
   private String serverName;
   private int serverPort;
   private int joinPlayerCounter;
   private NetworkManager myNetworkManager;
   private boolean integratedServerIsRunning;
   private long debugCrashKeyPressTime = -1L;
   private IReloadableResourceManager mcResourceManager;
   private ResourcePackRepository mcResourcePackRepository;
   private LanguageManager mcLanguageManager;
   private Framebuffer framebufferMc;
   private TextureMap textureMapBlocks;
   private SoundHandler mcSoundHandler;
   private MusicTicker mcMusicTicker;
   private ResourceLocation mojangLogo;
   private SkinManager skinManager;
   private ModelManager modelManager;
   private BlockRendererDispatcher blockRenderDispatcher;
   private String debugProfilerName = "root";
   private Thread aU = null;

   public Minecraft(GameConfiguration gameConfiguration) {
      theMinecraft = this;
      this.mcDataDir = gameConfiguration.folderInfo.mcDataDir;
      this.fileAssets = gameConfiguration.folderInfo.assetsDir;
      this.fileResourcepacks = gameConfiguration.folderInfo.resourcePacksDir;
      this.launchedVersion = gameConfiguration.gameInfo.version;
      this.twitchDetails = gameConfiguration.userInfo.userProperties;
      this.profileProperties = gameConfiguration.userInfo.profileProperties;
      this.mcDefaultResourcePack = new DefaultResourcePack(
         new ResourceIndex(gameConfiguration.folderInfo.assetsDir, gameConfiguration.folderInfo.assetIndex).getResourceMap()
      );
      this.proxy = gameConfiguration.userInfo.proxy == null ? Proxy.NO_PROXY : gameConfiguration.userInfo.proxy;
      this.sessionService = new YggdrasilAuthenticationService(gameConfiguration.userInfo.proxy, UUID.randomUUID().toString()).createMinecraftSessionService();
      this.session = gameConfiguration.userInfo.session;
      logger.info("Setting user: " + this.session.getUsername());
      logger.info("(Session ID is " + this.session.getUsername() + ")");
      this.isDemo = gameConfiguration.gameInfo.isDemo;
      this.displayWidth = gameConfiguration.displayInfo.width > 0 ? gameConfiguration.displayInfo.width : 1;
      this.displayHeight = gameConfiguration.displayInfo.height > 0 ? gameConfiguration.displayInfo.height : 1;
      this.tempDisplayWidth = gameConfiguration.displayInfo.width;
      this.tempDisplayHeight = gameConfiguration.displayInfo.height;
      this.fullscreen = gameConfiguration.displayInfo.fullscreen;
      this.jvm64bit = isJvm64bit();
      this.theIntegratedServer = new IntegratedServer(this);
      if (gameConfiguration.serverInfo.serverName != null) {
         this.serverName = gameConfiguration.serverInfo.serverName;
         this.serverPort = gameConfiguration.serverInfo.serverPort;
      }

      ImageIO.setUseCache(false);
      Bootstrap.register();
   }

   private static boolean isJvm64bit() {
      String[] var3 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
      String[] var4 = var3;
      int var5 = var4.length;

      for (int var6 = 0; var6 < var5; var6++) {
         String var8 = System.getProperty(var4[var6]);
         if (var8 != null && var8.contains("64")) {
            return true;
         }
      }

      return false;
   }

   public static boolean isGuiEnabled() {
      return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
   }

   public static boolean isFancyGraphicsEnabled() {
      return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
   }

   public static boolean isAmbientOcclusionEnabled() {
      return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0;
   }

   public static Minecraft getMinecraft() {
      return theMinecraft;
   }

   public static int getGLMaximumTextureSize() {
      for (short var3 = 16384; var3 > 0; var3 >>= 1) {
         GL11.glTexImage2D(GL11.GL_PROXY_TEXTURE_2D, 0, GL11.GL_RGBA, var3, var3, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)null);
         int var4 = GL11.glGetTexLevelParameteri(GL11.GL_PROXY_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
         if (var4 != 0) {
            return var3;
         }
      }

      return -1;
   }

   public static void stopIntegratedServer() {
      if (theMinecraft != null) {
         IntegratedServer var3 = theMinecraft.getIntegratedServer();
         if (var3 != null) {
            var3.stopServer();
         }
      }
   }

   public static long getSystemTime() {
      return System.currentTimeMillis();
   }

   public static int getDebugFPS() {
      return debugFPS;
   }

   public static Map<String, String> getSessionInfo() {
      HashMap var0 = Maps.newHashMap();
      var0.put("X-Minecraft-Username", getMinecraft().getSession().getUsername());
      var0.put("X-Minecraft-UUID", getMinecraft().getSession().getPlayerID());
      var0.put("X-Minecraft-Version", "1.8.9");
      return var0;
   }

   public void run() {
      this.running = true;
      this.aU = Thread.currentThread();

      try {
         this.startGame();
      } catch (Throwable var12) {
         var12.printStackTrace();
         CrashReport var3 = CrashReport.makeCrashReport(var12, "Initializing game");
         var3.makeCategory("Initialization");
         this.displayCrashReport(this.addGraphicsAndWorldToCrashReport(var3));
         return;
      }

      try {
         while (this.running) {
            if (this.hasCrashed && this.crashReporter != null) {
               this.displayCrashReport(this.crashReporter);
            } else {
               try {
                  this.runGameLoop();
               } catch (OutOfMemoryError var11) {
                  this.freeMemory();
                  this.displayGuiScreen(new GuiMemoryErrorScreen());
               }
            }
         }

         return;
      } catch (MinecraftError var13) {
         var13.printStackTrace();
      } catch (ReportedException var14) {
         this.addGraphicsAndWorldToCrashReport(var14.getCrashReport());
         this.freeMemory();
         logger.fatal("Reported exception thrown!", var14);
         this.displayCrashReport(var14.getCrashReport());
         var14.printStackTrace();
      } catch (Throwable var15) {
         CrashReport var17 = this.addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var15));
         this.freeMemory();
         logger.fatal("Unreported exception thrown!", var15);
         this.displayCrashReport(var17);
         var15.printStackTrace();
      } finally {
         this.shutdownMinecraftApplet();
      }
   }

   private synchronized void startGame() throws LWJGLException, IOException {
      this.gameSettings = new GameSettings(this, this.mcDataDir);
      this.defaultResourcePacks.add(this.mcDefaultResourcePack);
      this.startTimerHackThread();
      if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
         this.displayWidth = this.gameSettings.overrideWidth;
         this.displayHeight = this.gameSettings.overrideHeight;
      }

      logger.info("LWJGL Version: " + Sys.getVersion());
      this.setInitialDisplayMode();
      this.createDisplay();
      this.setWindowIcon();
      logger.info("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
      logger.info("Device: " + GL11.glGetString(GL11.GL_RENDERER));
      OpenGlHelper.initializeTextures();
      this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
      this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.registerMetadataSerializers();
      this.mcResourcePackRepository = new ResourcePackRepository(
         this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings
      );
      this.mcResourceManager = new SimpleReloadableResourceManager(this.metadataSerializer_);
      this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
      this.mcResourceManager.registerReloadListener(this.mcLanguageManager);
      this.refreshResources();
      this.renderEngine = new TextureManager(this.mcResourceManager);
      this.mcResourceManager.registerReloadListener(this.renderEngine);
      this.drawSplashScreen(this.renderEngine);
      this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
      this.saveLoader = new AnvilSaveConverter(new File(this.mcDataDir, "saves"));
      this.mcSoundHandler = new SoundHandler(this.mcResourceManager, this.gameSettings);
      this.mcResourceManager.registerReloadListener(this.mcSoundHandler);
      this.mcMusicTicker = new MusicTicker(this);
      this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
      if (this.gameSettings.language != null) {
         this.fontRendererObj.setUnicodeFlag(this.isUnicode());
         this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
      }

      this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
      this.mcResourceManager.registerReloadListener(this.fontRendererObj);
      this.mcResourceManager.registerReloadListener(this.standardGalacticFontRenderer);
      this.mcResourceManager.registerReloadListener(new GrassColorReloadListener());
      this.mcResourceManager.registerReloadListener(new FoliageColorReloadListener());
      AchievementList.openInventory.setStatStringFormatter(string -> {
         try {
            return String.format(string, GameSettings.getKeyDisplayString(this.gameSettings.keyBindInventory.getKeyCode()));
         } catch (Exception var2) {
            return "Error: " + var2.getLocalizedMessage();
         }
      });
      this.mouseHelper = new MouseHelper();
      this.checkGLError("Pre startup");
      GlStateManager.enableTexture2D();
      GlStateManager.shadeModel(7425);
      GlStateManager.clearDepth(1.0);
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(515);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.cullFace(1029);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.matrixMode(5888);
      this.checkGLError("Startup");
      this.textureMapBlocks = new TextureMap("textures");
      this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
      this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, this.textureMapBlocks);
      this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
      this.textureMapBlocks.setBlurMipmapDirect(false, this.gameSettings.mipmapLevels > 0);
      this.modelManager = new ModelManager(this.textureMapBlocks);
      this.mcResourceManager.registerReloadListener(this.modelManager);
      this.renderItem = new RenderItem(this.renderEngine, this.modelManager);
      this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
      this.itemRenderer = new ItemRenderer(this);
      this.mcResourceManager.registerReloadListener(this.renderItem);
      this.entityRenderer = new EntityRenderer(this, this.mcResourceManager);
      this.mcResourceManager.registerReloadListener(this.entityRenderer);
      this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings);
      this.mcResourceManager.registerReloadListener(this.blockRenderDispatcher);
      this.renderGlobal = new RenderGlobal(this);
      this.mcResourceManager.registerReloadListener(this.renderGlobal);
      this.guiAchievement = new GuiAchievement(this);
      GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
      this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);
      this.checkGLError("Post startup");
      this.ingameGUI = new GuiIngame(this);
      this.displayGuiScreen(new LoginScreen());
      this.renderEngine.deleteTexture(this.mojangLogo);
      this.mojangLogo = null;
      this.loadingScreen = new LoadingScreenRenderer(this);
      if (this.gameSettings.fullScreen && !this.fullscreen) {
         this.toggleFullscreen();
      }

      try {
         Display.setVSyncEnabled(this.gameSettings.enableVsync);
      } catch (OpenGLException var1) {
         this.gameSettings.enableVsync = false;
         this.gameSettings.saveOptions();
      }

      this.renderGlobal.makeEntityOutlineShader();
   }

   private void registerMetadataSerializers() {
      this.metadataSerializer_.registerMetadataSectionType(new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new FontMetadataSectionSerializer(), FontMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new PackMetadataSectionSerializer(), PackMetadataSection.class);
      this.metadataSerializer_.registerMetadataSectionType(new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
   }

   private void createDisplay() throws LWJGLException {
      Mouse.runnableConsumer = new Consumer<Runnable>() {
         public void accept(Runnable runnable) {
            Minecraft.this.Method1015(runnable);
         }
      };
      Display.setTitle("Moon Client (BLEEDING EDGE)");
      Display.setResizable(true);

      try {
         Display.create(new PixelFormat().withDepthBits(24));
         Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
      } catch (LWJGLException var6) {
         logger.error("Couldn't set pixel format", var6);

         try {
            Thread.sleep(1000L);
         } catch (InterruptedException var5) {
         }

         if (this.fullscreen) {
            this.updateDisplayMode();
         }

         Display.create();
      }
   }

   private void setInitialDisplayMode() throws LWJGLException {
      if (this.fullscreen) {
         Display.setFullscreen(true);
         DisplayMode var4 = Display.getDisplayMode();
         this.displayWidth = Math.max(1, var4.getWidth());
         this.displayHeight = Math.max(1, var4.getHeight());
      } else {
         Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
      }
   }

   public void setWindowIcon() {
      Util.EnumOS var4 = Util.getOSType();
      if (var4 != Util.EnumOS.OSX) {
         ByteBuffer var5;
         ByteBuffer var6;
         try {
            var5 = IOUtil.ioResourceToByteBuffer("icon16x16.png", 2048);
            var6 = IOUtil.ioResourceToByteBuffer("icon32x32.png", 4096);
         } catch (Exception var14) {
            throw new RuntimeException(var14);
         }

         IntBuffer var7 = MemoryUtil.memAllocInt(1);
         IntBuffer var8 = MemoryUtil.memAllocInt(1);
         IntBuffer var9 = MemoryUtil.memAllocInt(1);

         try (GLFWImage.Buffer var10 = GLFWImage.malloc(2)) {
            ByteBuffer var11 = STBImage.stbi_load_from_memory(var5, var7, var8, var9, 4);
            var10.position(0).width(var7.get(0)).height(var8.get(0)).pixels(var11);
            ByteBuffer var12 = STBImage.stbi_load_from_memory(var6, var7, var8, var9, 4);
            var10.position(1).width(var7.get(0)).height(var8.get(0)).pixels(var12);
            var10.position(0);
            GLFW.glfwSetWindowIcon(Display.getWindow(), var10);
            STBImage.stbi_image_free(var12);
            STBImage.stbi_image_free(var11);
         }
      }
   }

   public Framebuffer getFramebuffer() {
      return this.framebufferMc;
   }

   public String getVersion() {
      return this.launchedVersion;
   }

   private void startTimerHackThread() {
      Thread var1 = new Thread("Timer hack thread") {
         @Override
         public void run() {
            while (Minecraft.this.running) {
               try {
                  Thread.sleep(2147483647L);
               } catch (InterruptedException var1) {
               }
            }
         }
      };
      var1.setDaemon(true);
      var1.start();
   }

   public void crashed(CrashReport crashReport) {
      this.hasCrashed = true;
      this.crashReporter = crashReport;
   }

   public void displayCrashReport(CrashReport crashReport) {
      File var5 = new File(getMinecraft().mcDataDir, "crash-reports");
      File var6 = new File(var5, "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-client.txt");
      Bootstrap.printToSYSOUT(crashReport.getCompleteReport());
      if (crashReport.getFile() != null) {
         Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReport.getFile());
         System.exit(-1);
      } else if (crashReport.saveToFile(var6)) {
         Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + var6.getAbsolutePath());
         System.exit(-1);
      } else {
         Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
         System.exit(-2);
      }
   }

   public boolean isUnicode() {
      return this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont;
   }

   public void refreshResources() {
      ArrayList var4 = Lists.newArrayList(this.defaultResourcePacks);

      for (ResourcePackRepository.Entry var6 : this.mcResourcePackRepository.getRepositoryEntries()) {
         var4.add(var6.getResourcePack());
      }

      if (this.mcResourcePackRepository.getResourcePackInstance() != null) {
         var4.add(this.mcResourcePackRepository.getResourcePackInstance());
      }

      try {
         this.mcResourceManager.reloadResources(var4);
      } catch (RuntimeException var7) {
         logger.info("Caught error stitching, removing all assigned resourcepacks", var7);
         var4.clear();
         var4.addAll(this.defaultResourcePacks);
         this.mcResourcePackRepository.setRepositories(Collections.emptyList());
         this.mcResourceManager.reloadResources(var4);
         this.gameSettings.resourcePacks.clear();
         this.gameSettings.incompatibleResourcePacks.clear();
         this.gameSettings.saveOptions();
      }

      this.mcLanguageManager.parseLanguageMetadata(var4);
      if (this.renderGlobal != null) {
         this.renderGlobal.loadRenderers();
      }
   }

   private ByteBuffer readImageToBuffer(InputStream inputStream) throws IOException {
      BufferedImage var5 = ImageIO.read(inputStream);
      int[] var6 = var5.getRGB(0, 0, var5.getWidth(), var5.getHeight(), null, 0, var5.getWidth());
      ByteBuffer var7 = ByteBuffer.allocate(4 * var6.length);

      for (int var11 : var6) {
         var7.putInt(var11 << 8 | var11 >> 24 & 0xFF);
      }

      var7.flip();
      return var7;
   }

   private void updateDisplayMode() throws LWJGLException {
      HashSet<DisplayMode> var4 = Sets.newHashSet();
      Collections.addAll(var4, Display.getAvailableDisplayModes());
      DisplayMode var5 = Display.getDesktopDisplayMode();
      if (!var4.contains(var5) && Util.getOSType() == Util.EnumOS.OSX) {
         for (DisplayMode var7 : macDisplayModes) {
            boolean var8 = true;

            for (DisplayMode var10 : var4) {
               if (var10.getBitsPerPixel() == 32 && var10.getWidth() == var7.getWidth() && var10.getHeight() == var7.getHeight()) {
                  var8 = false;
                  break;
               }
            }

            if (!var8) {
               for (DisplayMode var12 : var4) {
                  if (var12.getBitsPerPixel() == 32 && var12.getWidth() == var7.getWidth() / 2 && var12.getHeight() == var7.getHeight() / 2) {
                     var5 = var12;
                     break;
                  }
               }
            }
         }
      }

      Display.setDisplayMode(var5);
      this.displayWidth = var5.getWidth();
      this.displayHeight = var5.getHeight();
   }

   private void drawSplashScreen(TextureManager textureManager) throws LWJGLException {
      ScaledResolution var4 = new ScaledResolution(this);
      int var5 = var4.getScaleFactor();
      Framebuffer var6 = new Framebuffer(var4.Method237() * var5, var4.Method238() * var5, true);
      var6.bindFramebuffer(false);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0, var4.Method237(), var4.Method238(), 0.0, 1000.0, 3000.0);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      GlStateManager.disableDepth();
      GlStateManager.enableTexture2D();
      InputStream var7 = null;

      try {
         var7 = this.mcDefaultResourcePack.getInputStream(locationMojangPng);
         this.mojangLogo = textureManager.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(var7)));
         textureManager.bindTexture(this.mojangLogo);
      } catch (IOException var14) {
         logger.error("Unable to load logo: " + locationMojangPng, var14);
      } finally {
         IOUtils.closeQuietly(var7);
      }

      Tessellator var8 = Tessellator.getInstance();
      WorldRenderer var9 = var8.getWorldRenderer();
      var9.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var9.pos(0.0, this.displayHeight, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
      var9.pos(this.displayWidth, this.displayHeight, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
      var9.pos(this.displayWidth, 0.0, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
      var9.pos(0.0, 0.0, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
      var8.draw();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      short var10 = 256;
      short var11 = 256;
      this.draw((var4.Method237() - var10) / 2, (var4.Method238() - var11) / 2, 0, 0, var10, var11, 255, 255, 255, 255);
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      var6.unbindFramebuffer();
      var6.framebufferRender(var4.Method237() * var5, var4.Method238() * var5);
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      this.updateDisplay();
   }

   public void draw(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6, int integer7, int integer8, int integer9, int integer10) {
      WorldRenderer var15 = Tessellator.getInstance().getWorldRenderer();
      var15.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
      var15.pos(integer1, integer2 + integer6, 0.0)
         .tex(integer3 * 0.00390625F, (integer4 + integer6) * 0.00390625F)
         .color(integer7, integer8, integer9, integer10)
         .endVertex();
      var15.pos(integer1 + integer5, integer2 + integer6, 0.0)
         .tex((integer3 + integer5) * 0.00390625F, (integer4 + integer6) * 0.00390625F)
         .color(integer7, integer8, integer9, integer10)
         .endVertex();
      var15.pos(integer1 + integer5, integer2, 0.0)
         .tex((integer3 + integer5) * 0.00390625F, integer4 * 0.00390625F)
         .color(integer7, integer8, integer9, integer10)
         .endVertex();
      var15.pos(integer1, integer2, 0.0).tex(integer3 * 0.00390625F, integer4 * 0.00390625F).color(integer7, integer8, integer9, integer10).endVertex();
      Tessellator.getInstance().draw();
   }

   public ISaveFormat getSaveLoader() {
      return this.saveLoader;
   }

   public void displayGuiScreen(GuiScreen guiScreen) {
      if (this.currentScreen != null) {
         this.currentScreen.onGuiClosed();
      }

      if (guiScreen == null && this.theWorld == null) {
         guiScreen = new GuiMainMenu();
      } else if (guiScreen == null && this.thePlayer.getHealth() <= 0.0F) {
         guiScreen = new GuiGameOver();
      }

      if (guiScreen instanceof GuiMainMenu) {
         this.gameSettings.showDebugInfo = false;
         this.ingameGUI.getChatGUI().clearChatMessages();
      }

      this.currentScreen = guiScreen;
      if (guiScreen != null) {
         this.setIngameNotInFocus();
         ScaledResolution var5 = new ScaledResolution(this);
         int var6 = var5.Method237();
         int var7 = var5.Method238();
         guiScreen.setWorldAndResolution(this, var6, var7);
         this.skipRenderWorld = false;
      } else {
         this.mcSoundHandler.resumeSounds();
         this.setIngameFocus();
      }
   }

   public void checkGLError(String string) {
      Objects.requireNonNull(this);
      int var5 = GL11.glGetError();
      if (var5 != 0) {
         String var6 = GLU.gluErrorString(var5);
         logger.error("########## GL ERROR ##########");
         logger.error("@ " + string);
         logger.error(var5 + ": " + var6);
      }
   }

   public void shutdownMinecraftApplet() {
      try {
         logger.info("Stopping!");

         try {
            this.loadWorld(null);
         } catch (Throwable var6) {
         }

         this.mcSoundHandler.unloadSounds();
         Moon.getInstance().shutdown();
      } finally {
         Display.destroy();
         if (!this.hasCrashed) {
            System.exit(0);
         }
      }
   }

   private void runGameLoop() throws IOException {
      Moon.getInstance().getEventBus().publish(new LoopEvent(LoopEvent.Sequence.PRE));
      long var4 = System.nanoTime();
      this.mcProfiler.startSection("root");
      if (Display.isCreated() && Display.isCloseRequested()) {
         this.shutdown();
      }

      if (this.isGamePaused && this.theWorld != null) {
         float var6 = this.timer.renderPartialTicks;
         this.timer.updateTimer();
         this.timer.renderPartialTicks = var6;
      } else {
         this.timer.updateTimer();
      }

      this.mcProfiler.startSection("scheduledExecutables");
      synchronized (this.scheduledTasks) {
         while (!this.scheduledTasks.isEmpty()) {
            Util.runTask(this.scheduledTasks.poll(), logger);
         }
      }

      this.mcProfiler.endSection();
      long var17 = System.nanoTime();
      this.mcProfiler.startSection("tick");
      int var8 = this.timer.elapsedTicks;

      for (int var10 = 0; var10 < var8; var10++) {
         this.runTick();
      }

      this.mcProfiler.endStartSection("preRenderErrors");
      long var18 = System.nanoTime() - var17;
      this.checkGLError("Pre render");
      this.mcProfiler.endStartSection("sound");
      this.mcSoundHandler.setListener(this.thePlayer, this.timer.renderPartialTicks);
      this.mcProfiler.endSection();
      this.mcProfiler.startSection("render");
      GlStateManager.pushMatrix();
      GlStateManager.clear(16640);
      this.framebufferMc.bindFramebuffer(true);
      this.mcProfiler.startSection("display");
      GlStateManager.enableTexture2D();
      if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
         this.gameSettings.thirdPersonView = 0;
      }

      this.mcProfiler.endSection();
      if (!this.skipRenderWorld) {
         this.mcProfiler.endStartSection("gameRenderer");
         this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks, var4);
         this.mcProfiler.endSection();
      }

      this.mcProfiler.endSection();
      if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
         if (!this.mcProfiler.profilingEnabled) {
            this.mcProfiler.clearProfiling();
         }

         this.mcProfiler.profilingEnabled = true;
         this.displayDebugInfo(var18);
      } else {
         this.mcProfiler.profilingEnabled = false;
         this.prevFrameTime = System.nanoTime();
      }

      RenderAchievementEvent var12 = new RenderAchievementEvent();
      Moon.getInstance().getEventBus().publish(var12);
      if (!var12.isCancelled()) {
         this.guiAchievement.updateAchievementWindow();
      }

      this.framebufferMc.unbindFramebuffer();
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
      GlStateManager.popMatrix();
      this.mcProfiler.startSection("root");
      this.updateDisplay();
      Thread.yield();
      this.mcProfiler.startSection("stream");
      this.mcProfiler.startSection("update");
      this.mcProfiler.endStartSection("submit");
      this.mcProfiler.endSection();
      this.mcProfiler.endSection();
      this.checkGLError("Post render");
      this.fpsCounter++;
      this.isGamePaused = this.isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic();
      long var13 = System.nanoTime();
      this.frameTimer.addFrame(var13 - this.startNanoTime);
      this.startNanoTime = var13;

      while (getSystemTime() >= this.debugUpdateTime + 1000L) {
         debugFPS = this.fpsCounter;
         this.debug = String.format(
            "%d fps (%d chunk update%s) T: %s%s%s%s%s",
            debugFPS,
            RenderChunk.renderChunksUpdated,
            RenderChunk.renderChunksUpdated != 1 ? "s" : "",
            this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax() ? "inf" : this.gameSettings.limitFramerate,
            this.gameSettings.enableVsync ? " vsync" : "",
            this.gameSettings.fancyGraphics ? "" : " fast",
            this.gameSettings.clouds == 0 ? "" : (this.gameSettings.clouds == 1 ? " fast-clouds" : " fancy-clouds"),
            OpenGlHelper.useVbo() ? " vbo" : ""
         );
         RenderChunk.renderChunksUpdated = 0;
         this.debugUpdateTime += 1000L;
         this.fpsCounter = 0;
         this.usageSnooper.addMemoryStatsToSnooper();
         if (!this.usageSnooper.isSnooperRunning()) {
            this.usageSnooper.startSnooper();
         }
      }

      while (!this.E.isEmpty()) {
         this.E.poll().run();
      }

      if (this.isFramerateLimitBelowMax()) {
         this.mcProfiler.startSection("fpslimit_wait");
         Display.sync(this.getLimitFramerate());
         this.mcProfiler.endSection();
      }

      this.mcProfiler.endSection();
      Moon.getInstance().getEventBus().publish(new LoopEvent(LoopEvent.Sequence.POST));
   }

   public void updateDisplay() {
      this.mcProfiler.startSection("display_update");
      Display.update();
      this.mcProfiler.endSection();
      this.checkWindowResize();
   }

   protected void checkWindowResize() {
      if (!this.fullscreen && Display.wasResized()) {
         int var4 = this.displayWidth;
         int var5 = this.displayHeight;
         this.displayWidth = Display.getWidth();
         this.displayHeight = Display.getHeight();
         if (this.displayWidth != var4 || this.displayHeight != var5) {
            if (this.displayWidth <= 0) {
               this.displayWidth = 1;
            }

            if (this.displayHeight <= 0) {
               this.displayHeight = 1;
            }

            this.resize(this.displayWidth, this.displayHeight);
         }
      }
   }

   public int getLimitFramerate() {
      return this.theWorld == null && this.currentScreen != null ? 144 : this.gameSettings.limitFramerate;
   }

   public boolean isFramerateLimitBelowMax() {
      return this.getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax();
   }

   public void freeMemory() {
      try {
         memoryReserve = new byte[0];
         this.renderGlobal.deleteAllDisplayLists();
      } catch (Throwable var2) {
      }

      try {
         this.loadWorld(null);
      } catch (Throwable var1) {
      }
   }

   private void updateDebugProfilerName(int integer) {
      List var5 = this.mcProfiler.getProfilingData(this.debugProfilerName);
      if (var5 != null && !var5.isEmpty()) {
         Profiler.Result var6 = (Profiler.Result)var5.remove(0);
         if (integer == 0) {
            if (var6.field_76331_c.length() > 0) {
               int var7 = this.debugProfilerName.lastIndexOf(".");
               if (var7 >= 0) {
                  this.debugProfilerName = this.debugProfilerName.substring(0, var7);
               }
            }
         } else {
            integer--;
            if (integer < var5.size() && !((Profiler.Result)var5.get(integer)).field_76331_c.equals("unspecified")) {
               if (this.debugProfilerName.length() > 0) {
                  this.debugProfilerName = this.debugProfilerName + ".";
               }

               this.debugProfilerName = this.debugProfilerName + ((Profiler.Result)var5.get(integer)).field_76331_c;
            }
         }
      }
   }

   private void displayDebugInfo(long long1) {
      if (this.mcProfiler.profilingEnabled) {
         List<Profiler.Result> var6 = this.mcProfiler.getProfilingData(this.debugProfilerName);
         Profiler.Result var7 = (Profiler.Result)var6.remove(0);
         GlStateManager.clear(256);
         GlStateManager.matrixMode(5889);
         GlStateManager.enableColorMaterial();
         GlStateManager.loadIdentity();
         GlStateManager.ortho(0.0, this.displayWidth, this.displayHeight, 0.0, 1000.0, 3000.0);
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
         GL11.glLineWidth(1.0F);
         GlStateManager.disableTexture2D();
         Tessellator var8 = Tessellator.getInstance();
         WorldRenderer var9 = var8.getWorldRenderer();
         short var10 = 160;
         int var11 = this.displayWidth - var10 - 10;
         int var12 = this.displayHeight - 320;
         GlStateManager.enableBlend();
         var9.begin(7, DefaultVertexFormats.POSITION_COLOR);
         var9.pos(var11 - 176.0F, var12 - 96.0F - 16.0F, 0.0).color(200, 0, 0, 0).endVertex();
         var9.pos(var11 - 176.0F, var12 + 320, 0.0).color(200, 0, 0, 0).endVertex();
         var9.pos(var11 + 176.0F, var12 + 320, 0.0).color(200, 0, 0, 0).endVertex();
         var9.pos(var11 + 176.0F, var12 - 96.0F - 16.0F, 0.0).color(200, 0, 0, 0).endVertex();
         var8.draw();
         GlStateManager.disableBlend();
         double var13 = 0.0;

         for (Profiler.Result var16 : var6) {
            int var17 = MathHelper.floor_double(var16.field_76332_a / 4.0) + 1;
            var9.begin(6, DefaultVertexFormats.POSITION_COLOR);
            int var18 = var16.getColor();
            int var19 = var18 >> 16 & 0xFF;
            int var20 = var18 >> 8 & 0xFF;
            int var21 = var18 & 0xFF;
            var9.pos(var11, var12, 0.0).color(var19, var20, var21, 255).endVertex();

            for (int var22 = var17; var22 >= 0; var22--) {
               float var23 = (float)((var13 + var16.field_76332_a * var22 / var17) * Math.PI * 2.0 / 100.0);
               float var24 = MathHelper.sin(var23) * 160.0F;
               float var25 = MathHelper.cos(var23) * 160.0F * 0.5F;
               var9.pos(var11 + var24, var12 - var25, 0.0).color(var19, var20, var21, 255).endVertex();
            }

            var8.draw();
            var9.begin(5, DefaultVertexFormats.POSITION_COLOR);

            for (int var38 = var17; var38 >= 0; var38--) {
               float var39 = (float)((var13 + var16.field_76332_a * var38 / var17) * Math.PI * 2.0 / 100.0);
               float var40 = MathHelper.sin(var39) * 160.0F;
               float var41 = MathHelper.cos(var39) * 160.0F * 0.5F;
               var9.pos(var11 + var40, var12 - var41, 0.0).color(var19 >> 1, var20 >> 1, var21 >> 1, 255).endVertex();
               var9.pos(var11 + var40, var12 - var41 + 10.0F, 0.0).color(var19 >> 1, var20 >> 1, var21 >> 1, 255).endVertex();
            }

            var8.draw();
            var13 += var16.field_76332_a;
         }

         DecimalFormat var26 = new DecimalFormat("##0.00");
         GlStateManager.enableTexture2D();
         String var27 = "";
         if (!var7.field_76331_c.equals("unspecified")) {
            var27 = "" + "[0] ";
         }

         if (var7.field_76331_c.length() == 0) {
            var27 = var27 + "ROOT ";
         } else {
            var27 = var27 + var7.field_76331_c + " ";
         }

         int var30 = 16777215;
         this.fontRendererObj.drawStringWithShadow(var27, var11 - var10, var12 - 80 - 16, var30);
         this.fontRendererObj
            .drawStringWithShadow(
               var27 = var26.format(var7.field_76330_b) + "%", var11 + var10 - this.fontRendererObj.getStringWidth(var27), var12 - 80 - 16, var30
            );

         for (int var31 = 0; var31 < var6.size(); var31++) {
            Profiler.Result var32 = (Profiler.Result)var6.get(var31);
            String var33 = "";
            if (var32.field_76331_c.equals("unspecified")) {
               var33 = "" + "[?] ";
            } else {
               var33 = var33 + "[" + (var31 + 1) + "] ";
            }

            var33 = var33 + var32.field_76331_c;
            this.fontRendererObj.drawStringWithShadow(var33, var11 - var10, var12 + 80 + var31 * 8 + 20, var32.getColor());
            this.fontRendererObj
               .drawStringWithShadow(
                  var33 = var26.format(var32.field_76332_a) + "%",
                  var11 + var10 - 50 - this.fontRendererObj.getStringWidth(var33),
                  var12 + 80 + var31 * 8 + 20,
                  var32.getColor()
               );
            this.fontRendererObj
               .drawStringWithShadow(
                  var33 = var26.format(var32.field_76330_b) + "%",
                  var11 + var10 - this.fontRendererObj.getStringWidth(var33),
                  var12 + 80 + var31 * 8 + 20,
                  var32.getColor()
               );
         }
      }
   }

   public void shutdown() {
      this.running = false;
   }

   public void setIngameFocus() {
      if (Display.isActive() && !this.inGameHasFocus) {
         this.inGameHasFocus = true;
         this.mouseHelper.grabMouseCursor();
         this.displayGuiScreen(null);
         this.leftClickCounter = 10000;
      }
   }

   public void setIngameNotInFocus() {
      if (this.inGameHasFocus) {
         KeyBinding.unPressAllKeys();
         this.inGameHasFocus = false;
         this.mouseHelper.ungrabMouseCursor();
      }
   }

   public void displayInGameMenu() {
      if (this.currentScreen == null) {
         this.displayGuiScreen(new GuiIngameMenu());
         if (this.isSingleplayer() && !this.theIntegratedServer.getPublic()) {
            this.mcSoundHandler.pauseSounds();
         }
      }
   }

   private void sendClickBlockToController(boolean boolean1) {
      if (!boolean1) {
         this.leftClickCounter = 0;
      }

      if (this.leftClickCounter <= 0 && !this.thePlayer.isUsingItem()) {
         if (boolean1 && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var5 = this.objectMouseOver.getBlockPos();
            if (this.theWorld.getBlockState(var5).getBlock().getMaterial() != Material.air
               && this.playerController.onPlayerDamageBlock(var5, this.objectMouseOver.sideHit)) {
               this.effectRenderer.addBlockHitEffects(var5, this.objectMouseOver.sideHit);
               this.thePlayer.swingItem();
            }
         } else {
            this.playerController.resetBlockRemoving();
         }
      }
   }

   public void Method1013(Packet packet) {
      try {
         NetworkManager var5 = this.getNetHandler().getNetworkManager();
         INetHandler var6 = var5.getNetHandler();
         if (var5.Method714().isOpen()) {
            try {
               packet.processPacket(var6);
            } catch (ThreadQuickExitException var7) {
            } catch (Exception var8) {
               var8.printStackTrace();
            }
         }
      } catch (Exception var9) {
         var9.printStackTrace();
      }
   }

   public void clickMouse() {
      if (this.aw == null) {
         this.aw = Moon.getInstance().getModuleManager().getModule(AutoClicker.class);
      }

      boolean var4 = this.aw != null && this.aw.isEnabled() && this.aw.removeAirSwingDelay.getValue();
      if (this.leftClickCounter <= 0 || var4) {
         if (this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            InteractEvent var5 = new InteractEvent(this.objectMouseOver.entityHit);
            Moon.getInstance().getEventBus().publish(var5);
            if (var5.isCancelled()) {
               return;
            }
         }

         if (ViaMCP.getInstance().getVersion() == 47) {
            this.thePlayer.swingItem();
         }

         if (this.objectMouseOver == null) {
            logger.error("Null returned as 'hitResult', this shouldn't happen!");
            if (this.playerController.isNotCreative()) {
               this.leftClickCounter = 10;
            }
         } else {
            switch (this.objectMouseOver.typeOfHit) {
               case ENTITY:
                  this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
                  break;
               case BLOCK:
                  BlockPos var6 = this.objectMouseOver.getBlockPos();
                  if (this.theWorld.getBlockState(var6).getBlock().getMaterial() != Material.air) {
                     this.playerController.clickBlock(var6, this.objectMouseOver.sideHit);
                     break;
                  }
               case MISS:
               default:
                  if (this.playerController.isNotCreative()) {
                     this.leftClickCounter = 10;
                  }
            }
         }

         if (ViaMCP.getInstance().getVersion() > 47) {
            this.thePlayer.swingItem();
         }
      }
   }

   public void rightClickMouse() {
      if (!this.playerController.getIsHittingBlock()) {
         this.rightClickDelayTimer = 4;
         boolean var4 = true;
         ItemStack var5 = this.thePlayer.inventory.getCurrentItem();
         if (this.objectMouseOver == null) {
            logger.warn("Null returned as 'hitResult', this shouldn't happen!");
         } else {
            switch (this.objectMouseOver.typeOfHit) {
               case ENTITY:
                  if (this.playerController.isPlayerRightClickingOnEntity(this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
                     var4 = false;
                  } else if (this.playerController.interactWithEntitySendPacket(this.thePlayer, this.objectMouseOver.entityHit)) {
                     var4 = false;
                  }
                  break;
               case BLOCK:
                  BlockPos var6 = this.objectMouseOver.getBlockPos();
                  if (this.theWorld.getBlockState(var6).getBlock().getMaterial() != Material.air) {
                     int var7 = var5 != null ? var5.stackSize : 0;
                     if (this.playerController
                        .onPlayerRightClick(this.thePlayer, this.theWorld, var5, var6, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
                        var4 = false;
                        this.thePlayer.swingItem();
                     }

                     if (var5 == null) {
                        return;
                     }

                     if (var5.stackSize == 0) {
                        this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
                     } else if (var5.stackSize != var7 || this.playerController.isInCreativeMode()) {
                        this.entityRenderer.itemRenderer.Method809();
                     }
                  }
            }
         }

         if (var4) {
            ItemStack var8 = this.thePlayer.inventory.getCurrentItem();
            if (var8 != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, var8)) {
               this.entityRenderer.itemRenderer.Method810();
            }
         }
      }
   }

   public void toggleFullscreen() {
      try {
         this.fullscreen = !this.fullscreen;
         this.gameSettings.fullScreen = this.fullscreen;
         if (this.fullscreen) {
            this.updateDisplayMode();
            this.displayWidth = Display.getDisplayMode().getWidth();
            this.displayHeight = Display.getDisplayMode().getHeight();
         } else {
            Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
            this.displayWidth = this.tempDisplayWidth;
            this.displayHeight = this.tempDisplayHeight;
         }

         if (this.displayWidth <= 0) {
            this.displayWidth = 1;
         }

         if (this.displayHeight <= 0) {
            this.displayHeight = 1;
         }

         if (this.currentScreen != null) {
            this.resize(this.displayWidth, this.displayHeight);
         } else {
            this.updateFramebufferSize();
         }

         Display.setFullscreen(this.fullscreen);
         Display.setVSyncEnabled(this.gameSettings.enableVsync);
         Moon.getInstance().getEventBus().publish(new ResizeEvent());
         this.updateDisplay();
      } catch (Exception var5) {
         logger.error("Couldn't toggle fullscreen", var5);
      }
   }

   private void resize(int integer1, int integer2) {
      this.displayWidth = Math.max(1, integer1);
      this.displayHeight = Math.max(1, integer2);
      Moon.getInstance().getEventBus().publish(new ResizeEvent());
      if (this.currentScreen != null) {
         ScaledResolution var6 = new ScaledResolution(this);
         this.currentScreen.onResize(this, var6.Method237(), var6.Method238());
      }

      if (this.loadingScreen == null) {
         this.loadingScreen = new LoadingScreenRenderer(this);
      }

      this.updateFramebufferSize();
   }

   private void updateFramebufferSize() {
      this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
      if (this.entityRenderer != null) {
         this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
      }
   }

   public MusicTicker getMusicTicker() {
      return this.mcMusicTicker;
   }

   public void runTick() throws IOException {
      TickEvent var4 = new TickEvent(TickEvent.Sequence.PRE);
      Moon.getInstance().getEventBus().publish(var4);
      boolean var5 = var4.getTickPassed() > 0;
      if (this.rightClickDelayTimer > 0) {
         this.rightClickDelayTimer--;
      }


      this.mcProfiler.startSection("gui");
      if (!this.isGamePaused) {
         this.ingameGUI.updateTick();
      }

      this.mcProfiler.endSection();
      this.entityRenderer.getMouseOver(1.0F);
      this.mcProfiler.startSection("gameMode");
      if (!this.isGamePaused && this.theWorld != null) {
         this.playerController.updateController();
      }

      this.mcProfiler.endStartSection("textures");
      if (!this.isGamePaused) {
         this.renderEngine.tick();
      }

      if (this.currentScreen == null && this.thePlayer != null) {
         if (this.thePlayer.getHealth() <= 0.0F) {
            this.displayGuiScreen(null);
         } else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
            this.displayGuiScreen(new GuiSleepMP());
         }
      } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
         this.displayGuiScreen(null);
      }

      if (this.currentScreen != null) {
         this.leftClickCounter = 10000;
      }

      if (this.currentScreen != null) {
         try {
            this.currentScreen.handleInput();
         } catch (Throwable var15) {
            CrashReport var7 = CrashReport.makeCrashReport(var15, "Updating screen events");
            var7.makeCategory("Affected screen").addCrashSectionCallable("Screen name", () -> this.currentScreen.getClass().getCanonicalName());
            throw new ReportedException(var7);
         }

         if (this.currentScreen != null) {
            try {
               this.currentScreen.onUpdateScreen();
            } catch (Throwable var14) {
               CrashReport var17 = CrashReport.makeCrashReport(var14, "Ticking screen");
               var17.makeCategory("Affected screen").addCrashSectionCallable("Screen name", () -> this.currentScreen.getClass().getCanonicalName());
               throw new ReportedException(var17);
            }
         }
      }

      if (this.currentScreen == null || this.currentScreen.allowUserInput) {
         if (this.gameSettings.keyBindUseItem.isPressed()) {
            int var19 = this.gameSettings.keyBindUseItem.getKeyCode();
            int var25 = GLFW.glfwGetMouseButton(Display.getWindow(), var19);
            if (var25 == 0) {
               KeyBinding.setKeyBindState(var19, false);
            }
         }

         if (this.gameSettings.keyBindAttack.isPressed()) {
            int var20 = this.gameSettings.keyBindAttack.getKeyCode() + 100;
            int var26 = GLFW.glfwGetMouseButton(Display.getWindow(), var20);
            if (var26 == 0) {
               KeyBinding.setKeyBindState(var20, false);
            }
         }

         this.mcProfiler.endStartSection("mouse");

         while (Mouse.next()) {
            int var21 = Mouse.getEventButton();
            boolean var27 = Mouse.getEventButtonState();
            boolean var9 = Mouse.getEventButtonState();
            if (var21 != -1) {
               KeyBinding.setKeyBindState(var21 - 100, var27);
               MouseEvent var10 = new MouseEvent(var21, var9);
               Moon.getInstance().getEventBus().publish(var10);
            }

            if (var9) {
               if (this.thePlayer.isSpectator() && var21 == 2) {
                  this.ingameGUI.getSpectatorGui().func_175261_b();
               } else {
                  KeyBinding.onTick(var21 - 100);
               }
            }

            long var37 = getSystemTime() - this.systemTime;
            if (var37 <= 200L) {
               int var12 = Mouse.getEventDWheel();
               if (var12 != 0) {
                  if (this.thePlayer.isSpectator()) {
                     var12 = var12 < 0 ? -1 : 1;
                     if (this.ingameGUI.getSpectatorGui().func_175262_a()) {
                        this.ingameGUI.getSpectatorGui().func_175259_b(-var12);
                     } else {
                        float var13 = MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + var12 * 0.005F, 0.0F, 0.2F);
                        this.thePlayer.capabilities.setFlySpeed(var13);
                     }
                  } else {
                     this.thePlayer.inventory.Method727(var12);
                  }
               }

               if (this.currentScreen == null) {
                  if (!this.inGameHasFocus && Mouse.getEventButtonState()) {
                     this.setIngameFocus();
                  }
               } else if (this.currentScreen != null) {
                  this.currentScreen.handleMouseInput();
               }
            }
         }

         if (this.leftClickCounter > 0) {
            this.leftClickCounter--;
         }

         this.mcProfiler.endStartSection("keyboard");

         while (Keyboard.next()) {
            int var22 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() + 256 : Keyboard.getEventKey();
            KeyBinding.setKeyBindState(var22, Keyboard.getEventKeyState());
            boolean var28 = Keyboard.getEventKeyState();
            KeyPressEvent var32 = new KeyPressEvent(var22, var28);
            Moon.getInstance().getEventBus().publish(var32);
            if (var28) {
               KeyBinding.onTick(var22);
            }

            if (this.debugCrashKeyPressTime > 0L) {
               if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
                  throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
               }

               if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
                  this.debugCrashKeyPressTime = -1L;
               }
            } else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
               this.debugCrashKeyPressTime = getSystemTime();
            }

            this.dispatchKeypresses();
            if (Keyboard.getEventKeyState()) {
               if (var22 == 62 && this.entityRenderer != null) {
                  this.entityRenderer.switchUseShader();
               }

               if (this.currentScreen != null) {
                  this.currentScreen.handleKeyboardInput();
               } else {
                  if (var22 == 1) {
                     this.displayInGameMenu();
                  }

                  if (var22 == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null) {
                     this.ingameGUI.getChatGUI().clearChatMessages();
                  }

                  if (var22 == 31 && Keyboard.isKeyDown(61)) {
                     this.refreshResources();
                  }

                  if (var22 == 20 && Keyboard.isKeyDown(61)) {
                     this.refreshResources();
                  }

                  if (var22 == 33 && Keyboard.isKeyDown(61)) {
                     this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, GuiScreen.isShiftKeyDown() ? -1 : 1);
                  }

                  if (var22 == 30 && Keyboard.isKeyDown(61)) {
                     this.renderGlobal.loadRenderers();
                  }

                  if (var22 == 35 && Keyboard.isKeyDown(61)) {
                     this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
                     this.gameSettings.saveOptions();
                  }

                  if (var22 == 48 && Keyboard.isKeyDown(61)) {
                     this.renderManager.setDebugBoundingBox(!this.renderManager.isDebugBoundingBox());
                  }

                  if (var22 == 25 && Keyboard.isKeyDown(61)) {
                     this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
                     this.gameSettings.saveOptions();
                  }

                  if (var22 == 59) {
                     this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
                  }

                  if (var22 == 61) {
                     this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
                     this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
                     this.gameSettings.showLagometer = GuiScreen.isAltKeyDown();
                  }

                  if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
                     this.gameSettings.thirdPersonView++;
                     if (this.gameSettings.thirdPersonView > 2) {
                        this.gameSettings.thirdPersonView = 0;
                     }

                     if (this.gameSettings.thirdPersonView == 0) {
                        this.entityRenderer.loadEntityShader(this.getRenderViewEntity());
                     } else if (this.gameSettings.thirdPersonView == 1) {
                        this.entityRenderer.loadEntityShader(null);
                     }

                     this.renderGlobal.setDisplayListEntitiesDirty();
                  }

                  if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
                     this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
                  }
               }

               if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart) {
                  if (var22 == 11) {
                     this.updateDebugProfilerName(0);
                  }

                  for (int var38 = 0; var38 < 9; var38++) {
                     if (var22 == 2 + var38) {
                        this.updateDebugProfilerName(var38 + 1);
                     }
                  }
               }
            }
         }

         for (int var23 = 0; var23 < 9; var23++) {
            if (this.gameSettings.keyBindsHotbar[var23].isPressed()) {
               if (this.thePlayer.isSpectator()) {
                  this.ingameGUI.getSpectatorGui().func_175260_a(var23);
               } else {
                  SlotChangeEvent var29 = new SlotChangeEvent(var23);
                  Moon.getInstance().getEventBus().publish(var29);
                  if (!var29.isCancelled()) {
                     this.thePlayer.inventory.currentItem = var29.getSlot();
                  }
               }
            }
         }

         boolean var24 = this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN;

         while (this.gameSettings.keyBindInventory.isPressed()) {
            if (this.playerController.isRidingHorse()) {
               this.thePlayer.sendHorseInventory();
            } else {
               this.getNetHandler().Method754(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
               this.displayGuiScreen(new GuiInventory(this.thePlayer));
            }
         }

         while (this.gameSettings.keyBindDrop.isPressed()) {
            if (!this.thePlayer.isSpectator()) {
               this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
            }
         }

         while (this.gameSettings.keyBindChat.isPressed() && var24) {
            this.displayGuiScreen(new GuiChat());
         }

         if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && var24) {
            this.displayGuiScreen(new GuiChat("/"));
         }

         InputEvent var30 = new InputEvent();
         Moon.getInstance().getEventBus().publish(var30);
         if (this.thePlayer.isUsingItem()) {
            if (!this.gameSettings.keyBindUseItem.isKeyDown()) {
               this.playerController.onStoppedUsingItem(this.thePlayer);
            }

            while (this.gameSettings.keyBindAttack.isPressed()) {
               AttackInputEvent var35 = new AttackInputEvent();
               Moon.getInstance().getEventBus().publish(var35);
               if (var35.isCancelled()) {
                  AttackEvent var39 = new AttackEvent();
                  Moon.getInstance().getEventBus().publish(var39);
                  if (!var39.isCancelled()) {
                     this.clickMouse();
                  }
               }
            }

            while (this.gameSettings.keyBindUseItem.isPressed()) {
            }

            while (this.gameSettings.keyBindPickBlock.isPressed()) {
            }
         } else {
            while (this.gameSettings.keyBindAttack.isPressed() || var4.decay()) {
               AttackEvent var33 = new AttackEvent();
               Moon.getInstance().getEventBus().publish(var33);
               if (!var33.isCancelled()) {
                  this.clickMouse();
               }
            }

            while (this.gameSettings.keyBindUseItem.isPressed()) {
               UseInputEvent var34 = new UseInputEvent();
               Moon.getInstance().getEventBus().publish(var34);
               if (!var34.isCancelled()) {
                  this.rightClickMouse();
               }
            }

            while (this.gameSettings.keyBindPickBlock.isPressed()) {
               this.middleClickMouse();
            }
         }

         if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem()) {
            this.rightClickMouse();
         }

         this.sendClickBlockToController(this.currentScreen == null && (this.gameSettings.keyBindAttack.isKeyDown() || var5) && this.inGameHasFocus);
      }

      if (this.theWorld != null) {
         if (this.thePlayer != null) {
            this.joinPlayerCounter++;
            if (this.joinPlayerCounter == 30) {
               this.joinPlayerCounter = 0;
               this.theWorld.joinEntityInSurroundings(this.thePlayer);
            }
         }

         this.mcProfiler.endStartSection("gameRenderer");
         if (!this.isGamePaused) {
            this.entityRenderer.updateRenderer();
         }

         this.mcProfiler.endStartSection("levelRenderer");
         if (!this.isGamePaused) {
            this.renderGlobal.updateClouds();
         }

         this.mcProfiler.endStartSection("level");
         if (!this.isGamePaused) {
            if (this.theWorld.getLastLightningBolt() > 0) {
               this.theWorld.setLastLightningBolt(this.theWorld.getLastLightningBolt() - 1);
            }

            this.theWorld.updateEntities();
         }
      } else if (this.entityRenderer.isShaderActive()) {
         this.entityRenderer.stopUseShader();
      }

      if (!this.isGamePaused) {
         this.mcMusicTicker.update();
         this.mcSoundHandler.update();
      }

      if (this.theWorld != null) {
         if (!this.isGamePaused) {
            this.theWorld.setAllowedSpawnTypes(this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL, true);

            try {
               this.theWorld.tick();
            } catch (Throwable var16) {
               CrashReport var31 = CrashReport.makeCrashReport(var16, "Exception in world tick");
               if (this.theWorld == null) {
                  CrashReportCategory var36 = var31.makeCategory("Affected level");
                  var36.addCrashSection("Problem", "Level is null!");
               } else {
                  this.theWorld.addWorldInfoToCrashReport(var31);
               }

               throw new ReportedException(var31);
            }
         }

         this.mcProfiler.endStartSection("animateTick");
         if (!this.isGamePaused && this.theWorld != null) {
            this.theWorld
               .doVoidFogParticles(
                  MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ)
               );
         }

         this.mcProfiler.endStartSection("particles");
         if (!this.isGamePaused) {
            this.effectRenderer.updateEffects();
         }
      } else if (this.myNetworkManager != null) {
         this.mcProfiler.endStartSection("pendingConnection");
         this.myNetworkManager.processReceivedPackets();
      }

      this.mcProfiler.endSection();
      this.systemTime = getSystemTime();
      Moon.getInstance().getEventBus().publish(new TickEvent(TickEvent.Sequence.POST));
   }

   public void launchIntegratedServer(String string1, String string2, WorldSettings worldSettings) {
      this.loadWorld(null);
      ISaveHandler var7 = this.saveLoader.getSaveLoader(string1, false);
      WorldInfo var8 = var7.loadWorldInfo();
      if (var8 == null && worldSettings != null) {
         var8 = new WorldInfo(worldSettings, string1);
         var7.saveWorldInfo(var8);
      }

      if (worldSettings == null) {
         worldSettings = new WorldSettings(var8);
      }

      try {
         this.theIntegratedServer = new IntegratedServer(this, string1, string2, worldSettings);
         this.theIntegratedServer.startServerThread();
         this.integratedServerIsRunning = true;
      } catch (Throwable var13) {
         CrashReport var10 = CrashReport.makeCrashReport(var13, "Starting integrated server");
         CrashReportCategory var11 = var10.makeCategory("Starting integrated server");
         var11.addCrashSection("Level ID", string1);
         var11.addCrashSection("Level Name", string2);
         throw new ReportedException(var10);
      }

      this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel"));

      while (!this.theIntegratedServer.serverIsInRunLoop()) {
         String var9 = this.theIntegratedServer.getUserMessage();
         if (var9 != null) {
            this.loadingScreen.displayLoadingString(I18n.format(var9));
         } else {
            this.loadingScreen.displayLoadingString("");
         }

         try {
            Thread.sleep(200L);
         } catch (InterruptedException var12) {
         }
      }

      this.displayGuiScreen(null);
      SocketAddress var14 = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
      NetworkManager var15 = NetworkManager.provideLocalClient(var14);
      var15.setNetHandler(new NetHandlerLoginClient(var15, this, null));
      var15.sendPacket(new C00Handshake(47, var14.toString(), 0, EnumConnectionState.LOGIN));
      var15.sendPacket(new C00PacketLoginStart(this.getSession().getProfile()));
      this.myNetworkManager = var15;
   }

   public void loadWorld(WorldClient worldClient) {
      this.loadWorld(worldClient, "");
   }

   public void loadWorld(WorldClient worldClient, String string) {
      if (worldClient == null) {
         NetHandlerPlayClient var6 = this.getNetHandler();
         if (var6 != null) {
            var6.cleanup();
         }

         if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
            this.theIntegratedServer.initiateShutdown();
            this.theIntegratedServer.setStaticInstance();
         }

         this.theIntegratedServer = null;
         this.guiAchievement.clearAchievements();
         this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
      }

      this.renderViewEntity = null;
      this.myNetworkManager = null;
      if (this.loadingScreen != null) {
         this.loadingScreen.resetProgressAndMessage(string);
         this.loadingScreen.displayLoadingString("");
      }

      if (worldClient == null && this.theWorld != null) {
         this.mcResourcePackRepository.clearResourcePack();
         this.ingameGUI.resetPlayersOverlayFooterHeader();
         this.setServerData(null);
         this.integratedServerIsRunning = false;
      }

      this.mcSoundHandler.stopSounds();
      this.theWorld = worldClient;
      if (worldClient != null) {
         if (this.renderGlobal != null) {
            this.renderGlobal.setWorldAndLoadRenderers(worldClient);
         }

         if (this.effectRenderer != null) {
            this.effectRenderer.clearEffects(worldClient);
         }

         if (this.thePlayer == null) {
            this.thePlayer = this.playerController.func_178892_a(worldClient, new StatFileWriter());
            this.playerController.flipPlayer(this.thePlayer);
         }

         this.thePlayer.preparePlayerToSpawn();
         worldClient.spawnEntityInWorld(this.thePlayer);
         this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
         this.playerController.setPlayerCapabilities(this.thePlayer);
         this.renderViewEntity = this.thePlayer;
      } else {
         this.saveLoader.flushCache();
         this.thePlayer = null;
      }

      this.systemTime = 0L;
   }

   public void setDimensionAndSpawnPlayer(int integer) {
      this.theWorld.setInitialSpawnLocation();
      this.theWorld.removeAllEntities();
      int var5 = 0;
      String var6 = null;
      if (this.thePlayer != null) {
         var5 = this.thePlayer.getEntityId();
         this.theWorld.removeEntity(this.thePlayer);
         var6 = this.thePlayer.getClientBrand();
      }

      this.renderViewEntity = null;
      EntityPlayerSP var7 = this.thePlayer;
      this.thePlayer = this.playerController.func_178892_a(this.theWorld, this.thePlayer == null ? new StatFileWriter() : this.thePlayer.getStatFileWriter());
      this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(var7.getDataWatcher().getAllWatched());
      this.thePlayer.dimension = integer;
      this.renderViewEntity = this.thePlayer;
      this.thePlayer.preparePlayerToSpawn();
      this.thePlayer.setClientBrand(var6);
      this.theWorld.spawnEntityInWorld(this.thePlayer);
      this.playerController.flipPlayer(this.thePlayer);
      this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
      this.thePlayer.setEntityId(var5);
      this.playerController.setPlayerCapabilities(this.thePlayer);
      this.thePlayer.setReducedDebug(var7.hasReducedDebug());
      if (this.currentScreen instanceof GuiGameOver) {
         this.displayGuiScreen(null);
      }
   }

   public final boolean isDemo() {
      return this.isDemo;
   }

   public NetHandlerPlayClient getNetHandler() {
      return this.thePlayer != null ? this.thePlayer.sendQueue : null;
   }

   private void middleClickMouse() {
      if (this.objectMouseOver != null) {
         boolean var4 = this.thePlayer.capabilities.isCreativeMode;
         int var5 = 0;
         boolean var6 = false;
         TileEntity var7 = null;
         Item var8;
         if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var9 = this.objectMouseOver.getBlockPos();
            Block var10 = this.theWorld.getBlockState(var9).getBlock();
            if (var10.getMaterial() == Material.air) {
               return;
            }

            var8 = var10.getItem(this.theWorld, var9);
            if (var8 == null) {
               return;
            }

            if (var4 && GuiScreen.isCtrlKeyDown()) {
               var7 = this.theWorld.getTileEntity(var9);
            }

            var5 = (var8 instanceof ItemBlock && !var10.isFlowerPot() ? Block.getBlockFromItem(var8) : var10).getDamageValue(this.theWorld, var9);
            var6 = var8.getHasSubtypes();
         } else {
            if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !var4) {
               return;
            }

            if (this.objectMouseOver.entityHit instanceof EntityPainting) {
               var8 = Items.painting;
            } else if (this.objectMouseOver.entityHit instanceof EntityLeashKnot) {
               var8 = Items.lead;
            } else {
               Entity var11 = this.objectMouseOver.entityHit;
               if (var11 instanceof EntityItemFrame) {
                  ItemStack var16 = ((EntityItemFrame)var11).getDisplayedItem();
                  if (var16 == null) {
                     var8 = Items.item_frame;
                  } else {
                     var8 = var16.getItem();
                     var5 = var16.getMetadata();
                     var6 = true;
                  }
               } else if (this.objectMouseOver.entityHit instanceof EntityMinecart var13) {
                  switch (var13.getMinecartType()) {
                     case FURNACE :
                        var8 = Items.furnace_minecart;
                        break;
                     case CHEST :
                        var8 = Items.chest_minecart;
                        break;
                     case TNT :
                        var8 = Items.tnt_minecart;
                        break;
                     case HOPPER :
                        var8 = Items.hopper_minecart;
                        break;
                     case COMMAND_BLOCK :
                        var8 = Items.command_block_minecart;
                        break;
                     default:
                        var8 = Items.minecart;
                  }
               } else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
                  var8 = Items.boat;
               } else if (this.objectMouseOver.entityHit instanceof EntityArmorStand) {
                  var8 = Items.armor_stand;
               } else {
                  var8 = Items.spawn_egg;
                  var5 = EntityList.getEntityID(this.objectMouseOver.entityHit);
                  var6 = true;
                  if (!EntityList.entityEggs.containsKey(var5)) {
                     return;
                  }
               }
            }
         }

         InventoryPlayer var12 = this.thePlayer.inventory;
         if (var7 == null) {
            var12.setCurrentItem(var8, var5, var6, var4);
         } else {
            ItemStack var14 = this.pickBlockWithNBT(var8, var5, var7);
            var12.setInventorySlotContents(var12.currentItem, var14);
         }

         if (var4) {
            int var15 = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + var12.currentItem;
            this.playerController.sendSlotPacket(var12.getStackInSlot(var12.currentItem), var15);
         }
      }
   }

   private ItemStack pickBlockWithNBT(Item item, int integer, TileEntity tileEntity) {
      ItemStack var7 = new ItemStack(item, 1, integer);
      NBTTagCompound var8 = new NBTTagCompound();
      tileEntity.writeToNBT(var8);
      if (item == Items.skull && var8.hasKey("Owner")) {
         NBTTagCompound var11 = var8.getCompoundTag("Owner");
         NBTTagCompound var12 = new NBTTagCompound();
         var12.setTag("SkullOwner", var11);
         var7.setTagCompound(var12);
         return var7;
      } else {
         var7.setTagInfo("BlockEntityTag", var8);
         NBTTagCompound var9 = new NBTTagCompound();
         NBTTagList var10 = new NBTTagList();
         var10.appendTag(new NBTTagString("(+NBT)"));
         var9.setTag("Lore", var10);
         var7.setTagInfo("display", var9);
         return var7;
      }
   }

   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport crashReport) {
      crashReport.getCategory().addCrashSectionCallable("Launched Version", () -> this.launchedVersion);
      crashReport.getCategory().addCrashSectionCallable("LWJGL", Sys::getVersion);
      crashReport.getCategory()
         .addCrashSectionCallable("OpenGL", () -> GL11.glGetString(GL11.GL_RENDERER) + " GL version " + GL11.glGetString(GL11.GL_VERSION) + ", " + GL11.glGetString(GL11.GL_VENDOR));
      crashReport.getCategory().addCrashSectionCallable("GL Caps", OpenGlHelper::getLogText);
      crashReport.getCategory().addCrashSectionCallable("Using VBOs", () -> this.gameSettings.useVbo ? "Yes" : "No");
      crashReport.getCategory()
         .addCrashSectionCallable(
            "Is Modded",
            () -> {
               String var3 = ClientBrandRetriever.getClientModName();
               return !var3.equals("vanilla")
                  ? "Definitely; Client brand changed to '" + var3 + "'"
                  : (
                     Minecraft.class.getSigners() == null
                        ? "Very likely; Jar signature invalidated"
                        : "Probably not. Jar signature remains and client brand is untouched."
                  );
            }
         );
      crashReport.getCategory().addCrashSectionCallable("Type", () -> "Client (map_client.txt)");
      crashReport.getCategory().addCrashSectionCallable("Resource Packs", () -> {
         StringBuilder var4 = new StringBuilder();

         for (String var6 : this.gameSettings.resourcePacks) {
            if (var4.length() > 0) {
               var4.append(", ");
            }

            var4.append(var6);
            if (this.gameSettings.incompatibleResourcePacks.contains(var6)) {
               var4.append(" (incompatible)");
            }
         }

         return var4.toString();
      });
      crashReport.getCategory().addCrashSectionCallable("Current Language", () -> this.mcLanguageManager.getCurrentLanguage().toString());
      crashReport.getCategory()
         .addCrashSectionCallable("Profiler Position", () -> this.mcProfiler.profilingEnabled ? this.mcProfiler.getNameOfLastSection() : "N/A (disabled)");
      crashReport.getCategory().addCrashSectionCallable("CPU", OpenGlHelper::getCpu);
      if (this.theWorld != null) {
         this.theWorld.addWorldInfoToCrashReport(crashReport);
      }

      return crashReport;
   }

   public ListenableFuture<Object> scheduleResourcesRefresh() {
      return this.addScheduledTask(this::refreshResources);
   }

   @Override
   public void addServerStatsToSnooper(PlayerUsageSnooper playerUsageSnooper) {
      playerUsageSnooper.addClientStat("fps", debugFPS);
      playerUsageSnooper.addClientStat("vsync_enabled", this.gameSettings.enableVsync);
      playerUsageSnooper.addClientStat("display_frequency", Display.getDisplayMode().getFrequency());
      playerUsageSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
      playerUsageSnooper.addClientStat("run_time", (MinecraftServer.getCurrentTimeMillis() - playerUsageSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L);
      playerUsageSnooper.addClientStat("current_action", this.getCurrentAction());
      String var5 = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN ? "little" : "big";
      playerUsageSnooper.addClientStat("endianness", var5);
      playerUsageSnooper.addClientStat("resource_packs", this.mcResourcePackRepository.getRepositoryEntries().size());
      int var6 = 0;

      for (ResourcePackRepository.Entry var8 : this.mcResourcePackRepository.getRepositoryEntries()) {
         playerUsageSnooper.addClientStat("resource_pack[" + var6++ + "]", var8.getResourcePackName());
      }

      if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
         playerUsageSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
      }
   }

   private String getCurrentAction() {
      return this.theIntegratedServer != null
         ? (this.theIntegratedServer.getPublic() ? "hosting_lan" : "singleplayer")
         : (this.currentServerData != null ? (this.currentServerData.isOnLAN() ? "playing_lan" : "multiplayer") : "out_of_game");
   }

   @Override
   public void addServerTypeToSnooper(PlayerUsageSnooper playerUsageSnooper) {
      playerUsageSnooper.addStatToSnooper("opengl_version", GL11.glGetString(GL11.GL_VERSION));
      playerUsageSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(GL11.GL_VENDOR));
      playerUsageSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
      playerUsageSnooper.addStatToSnooper("launched_version", this.launchedVersion);
      ContextCapabilities var5 = GLContext.getCapabilities();
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", var5.GL_ARB_arrays_of_arrays);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", var5.GL_ARB_base_instance);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", var5.GL_ARB_blend_func_extended);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", var5.GL_ARB_clear_buffer_object);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", var5.GL_ARB_color_buffer_float);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", var5.GL_ARB_compatibility);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", var5.GL_ARB_compressed_texture_pixel_storage);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", var5.GL_ARB_compute_shader);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", var5.GL_ARB_copy_buffer);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", var5.GL_ARB_copy_image);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", var5.GL_ARB_depth_buffer_float);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", var5.GL_ARB_compute_shader);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", var5.GL_ARB_copy_buffer);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", var5.GL_ARB_copy_image);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", var5.GL_ARB_depth_buffer_float);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", var5.GL_ARB_depth_clamp);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", var5.GL_ARB_depth_texture);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", var5.GL_ARB_draw_buffers);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", var5.GL_ARB_draw_buffers_blend);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", var5.GL_ARB_draw_elements_base_vertex);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", var5.GL_ARB_draw_indirect);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", var5.GL_ARB_draw_instanced);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", var5.GL_ARB_explicit_attrib_location);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", var5.GL_ARB_explicit_uniform_location);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", var5.GL_ARB_fragment_layer_viewport);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", var5.GL_ARB_fragment_program);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", var5.GL_ARB_fragment_shader);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", var5.GL_ARB_fragment_program_shadow);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", var5.GL_ARB_framebuffer_object);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", var5.GL_ARB_framebuffer_sRGB);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", var5.GL_ARB_geometry_shader4);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", var5.GL_ARB_gpu_shader5);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", var5.GL_ARB_half_float_pixel);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", var5.GL_ARB_half_float_vertex);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", var5.GL_ARB_instanced_arrays);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", var5.GL_ARB_map_buffer_alignment);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", var5.GL_ARB_map_buffer_range);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_multisample]", var5.GL_ARB_multisample);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", var5.GL_ARB_multitexture);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", var5.GL_ARB_occlusion_query2);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", var5.GL_ARB_pixel_buffer_object);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", var5.GL_ARB_seamless_cube_map);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", var5.GL_ARB_shader_objects);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", var5.GL_ARB_shader_stencil_export);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", var5.GL_ARB_shader_texture_lod);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shadow]", var5.GL_ARB_shadow);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", var5.GL_ARB_shadow_ambient);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", var5.GL_ARB_stencil_texturing);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_sync]", var5.GL_ARB_sync);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", var5.GL_ARB_tessellation_shader);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", var5.GL_ARB_texture_border_clamp);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", var5.GL_ARB_texture_buffer_object);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", var5.GL_ARB_texture_cube_map);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", var5.GL_ARB_texture_cube_map_array);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", var5.GL_ARB_texture_non_power_of_two);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", var5.GL_ARB_uniform_buffer_object);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", var5.GL_ARB_vertex_blend);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", var5.GL_ARB_vertex_buffer_object);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", var5.GL_ARB_vertex_program);
      playerUsageSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", var5.GL_ARB_vertex_shader);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", var5.GL_EXT_bindable_uniform);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", var5.GL_EXT_blend_equation_separate);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", var5.GL_EXT_blend_func_separate);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", var5.GL_EXT_blend_minmax);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", var5.GL_EXT_blend_subtract);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", var5.GL_EXT_draw_instanced);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", var5.GL_EXT_framebuffer_multisample);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", var5.GL_EXT_framebuffer_object);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", var5.GL_EXT_framebuffer_sRGB);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", var5.GL_EXT_geometry_shader4);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", var5.GL_EXT_gpu_program_parameters);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", var5.GL_EXT_gpu_shader4);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", var5.GL_EXT_multi_draw_arrays);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", var5.GL_EXT_packed_depth_stencil);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", var5.GL_EXT_paletted_texture);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", var5.GL_EXT_rescale_normal);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", var5.GL_EXT_separate_shader_objects);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", var5.GL_EXT_shader_image_load_store);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", var5.GL_EXT_shadow_funcs);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", var5.GL_EXT_shared_texture_palette);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", var5.GL_EXT_stencil_clear_tag);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", var5.GL_EXT_stencil_two_side);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", var5.GL_EXT_stencil_wrap);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", var5.GL_EXT_texture_3d);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", var5.GL_EXT_texture_array);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", var5.GL_EXT_texture_buffer_object);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", var5.GL_EXT_texture_integer);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", var5.GL_EXT_texture_lod_bias);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", var5.GL_EXT_texture_sRGB);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", var5.GL_EXT_vertex_shader);
      playerUsageSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", var5.GL_EXT_vertex_weighting);
      playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", GL11.glGetInteger(GL20.GL_MAX_VERTEX_UNIFORM_COMPONENTS));
      GL11.glGetError();
      playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", GL11.glGetInteger(GL20.GL_MAX_FRAGMENT_UNIFORM_COMPONENTS));
      GL11.glGetError();
      playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", GL11.glGetInteger(GL20.GL_MAX_VERTEX_ATTRIBS));
      GL11.glGetError();
      playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", GL11.glGetInteger(GL20.GL_MAX_VERTEX_TEXTURE_IMAGE_UNITS));
      GL11.glGetError();
      playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(GL20.GL_MAX_TEXTURE_IMAGE_UNITS));
      GL11.glGetError();
      playerUsageSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", GL11.glGetInteger(GL30.GL_MAX_ARRAY_TEXTURE_LAYERS));
      GL11.glGetError();
      playerUsageSnooper.addStatToSnooper("gl_max_texture_size", getGLMaximumTextureSize());
   }

   @Override
   public boolean isSnooperEnabled() {
      return this.gameSettings.snooperEnabled;
   }

   public void setServerData(ServerData serverData) {
      this.currentServerData = serverData;
   }

   public ServerData getCurrentServerData() {
      return this.currentServerData;
   }

   public boolean isIntegratedServerRunning() {
      return this.integratedServerIsRunning;
   }

   public boolean isSingleplayer() {
      return this.integratedServerIsRunning && this.theIntegratedServer != null;
   }

   public IntegratedServer getIntegratedServer() {
      return this.theIntegratedServer;
   }

   public PlayerUsageSnooper getPlayerUsageSnooper() {
      return this.usageSnooper;
   }

   public boolean isFullScreen() {
      return this.fullscreen;
   }

   public Session getSession() {
      return this.session;
   }

   public PropertyMap getTwitchDetails() {
      return this.twitchDetails;
   }

   public PropertyMap getProfileProperties() {
      if (this.profileProperties.isEmpty()) {
         GameProfile var4 = this.getSessionService().fillProfileProperties(this.session.getProfile(), false);
         this.profileProperties.putAll(var4.getProperties());
      }

      return this.profileProperties;
   }

   public Proxy getProxy() {
      return this.proxy;
   }

   public TextureManager getTextureManager() {
      return this.renderEngine;
   }

   public IResourceManager getResourceManager() {
      return this.mcResourceManager;
   }

   public ResourcePackRepository getResourcePackRepository() {
      return this.mcResourcePackRepository;
   }

   public LanguageManager getLanguageManager() {
      return this.mcLanguageManager;
   }

   public TextureMap getTextureMapBlocks() {
      return this.textureMapBlocks;
   }

   public boolean isJava64bit() {
      return this.jvm64bit;
   }

   public boolean isGamePaused() {
      return this.isGamePaused;
   }

   public SoundHandler getSoundHandler() {
      return this.mcSoundHandler;
   }

   public MusicTicker.MusicType getAmbientMusicType() {
      return this.thePlayer != null
         ? (
            this.thePlayer.worldObj.provider instanceof WorldProviderHell
               ? MusicTicker.MusicType.NETHER
               : (
                  this.thePlayer.worldObj.provider instanceof WorldProviderEnd
                     ? (BossStatus.bossName != null && BossStatus.statusBarTime > 0 ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END)
                     : (
                        this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying
                           ? MusicTicker.MusicType.CREATIVE
                           : MusicTicker.MusicType.GAME
                     )
               )
         )
         : MusicTicker.MusicType.MENU;
   }

   public void dispatchKeypresses() {
      int var4 = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
      if (var4 != 0
         && !Keyboard.isRepeatEvent()
         && (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)
         && Keyboard.getEventKeyState()) {
         if (var4 == this.gameSettings.keyBindFullscreen.getKeyCode()) {
            this.toggleFullscreen();
         } else if (var4 == this.gameSettings.keyBindScreenshot.getKeyCode()) {
            this.ingameGUI
               .getChatGUI()
               .printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
         }
      }
   }

   public MinecraftSessionService getSessionService() {
      return this.sessionService;
   }

   public SkinManager getSkinManager() {
      return this.skinManager;
   }

   public Entity getRenderViewEntity() {
      return this.renderViewEntity;
   }

   public void setRenderViewEntity(Entity entity) {
      this.renderViewEntity = entity;
      this.entityRenderer.loadEntityShader(entity);
   }

   public <V> ListenableFuture<V> addScheduledTask(Callable<V> callable) {
      Validate.notNull(callable);
      if (!this.isCallingFromMinecraftThread()) {
         ListenableFutureTask var5 = ListenableFutureTask.create(callable);
         synchronized (this.scheduledTasks) {
            this.scheduledTasks.add(var5);
            return var5;
         }
      } else {
         try {
            return Futures.immediateFuture(callable.call());
         } catch (Exception var9) {
            return Futures.immediateFailedFuture(var9);
         }
      }
   }

   public boolean Method1014() {
      return this.aU == Thread.currentThread();
   }

   @Override
   public ListenableFuture<Object> addScheduledTask(Runnable runnable) {
      Validate.notNull(runnable);
      return this.addScheduledTask(Executors.callable(runnable));
   }

   @Override
   public boolean isCallingFromMinecraftThread() {
      return Thread.currentThread() == this.mcThread;
   }

   public BlockRendererDispatcher getBlockRendererDispatcher() {
      return this.blockRenderDispatcher;
   }

   public RenderManager getRenderManager() {
      return this.renderManager;
   }

   public RenderItem getRenderItem() {
      return this.renderItem;
   }

   public ItemRenderer getItemRenderer() {
      return this.itemRenderer;
   }

   public FrameTimer getFrameTimer() {
      return this.frameTimer;
   }

   public boolean isConnectedToRealms() {
      return this.connectedToRealms;
   }

   public void setConnectedToRealms(boolean boolean1) {
      this.connectedToRealms = boolean1;
   }

   public void Method1015(Runnable runnable) {
      this.E.add(runnable);
   }

   public Queue<Runnable> getTwitchStream() {
      return this.E;
   }

   public Minecraft setSession(Session session) {
      this.session = session;
      return this;
   }

   public Session Method1016() {
      return this.session;
   }
}
