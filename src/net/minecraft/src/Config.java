package net.minecraft.src;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.minecraft.client.LoadingScreenRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.DefaultResourcePack;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.ResourcePackRepository;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.optifine.DynamicLights;
import net.optifine.GlErrors;
import net.optifine.VersionCheckThread;
import net.optifine.config.GlVersion;
import net.optifine.gui.GuiMessage;
import net.optifine.shaders.Shaders;
import net.optifine.util.DisplayModeComparator;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.TimedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GL20;

public class Config {
   private static String build = null;
   private static String newRelease = null;
   private static boolean c = false;
   public static String d = null;
   public static String e = null;
   public static String f = null;
   public static String[] openGlExtensions = null;
   public static GlVersion glVersion = null;
   public static GlVersion glslVersion = null;
   public static int minecraftVersionInt = -1;
   public static boolean fancyFogAvailable = false;
   public static boolean occlusionAvailable = false;
   private static GameSettings gameSettings = null;
   private static final Minecraft minecraft = Minecraft.getMinecraft();
   private static boolean initialized = false;
   private static Thread minecraftThread = null;
   private static DisplayMode desktopDisplayMode = null;
   private static DisplayMode[] displayModes = null;
   private static int availableProcessors = 0;
   public static boolean zoomMode = false;
   public static boolean zoomSmoothCamera = false;
   private static int texturePackClouds = 0;
   public static boolean waterOpacityChanged = false;
   private static boolean fullscreenModeChecked = false;
   private static boolean desktopModeChecked = false;
   private static DefaultResourcePack defaultResourcePackLazy = null;
   public static final Float DEF_ALPHA_FUNC_LEVEL = 0.1F;
   private static final Logger LOGGER = LogManager.getLogger();
   public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");
   private static String mcDebugLast = null;
   private static int fpsMinLast = 0;
   public static float renderPartialTicks;

   public static String getVersion() {
      return "OptiFine_1.8.9_HD_U_M5";
   }

   public static String getVersionDebug() {
      StringBuilder var2 = new StringBuilder(32);
      if (isDynamicLights()) {
         var2.append("DL: ");
         var2.append(DynamicLights.getCount());
         var2.append(", ");
      }

      var2.append("OptiFine_1.8.9_HD_U_M5");
      String var3 = Shaders.getShaderPackName();
      if (var3 != null) {
         var2.append(", ");
         var2.append(var3);
      }

      return var2.toString();
   }

   public static void Method246(GameSettings settings) {
      if (gameSettings == null) {
         gameSettings = settings;
         desktopDisplayMode = Display.getDesktopDisplayMode();
         updateAvailableProcessors();
      }
   }

   public static void initDisplay() {
      checkInitialized();
      checkDisplayMode();
      minecraftThread = Thread.currentThread();
      updateThreadPriorities();
      Shaders.Method448(Minecraft.getMinecraft());
   }

   public static void checkInitialized() {
      if (!initialized && Display.isCreated()) {
         initialized = true;
         Method247();
         startVersionCheckThread();
      }
   }

   private static void Method247() {
      log("");
      log(getVersion());
      log("Build: " + getBuild());
      log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
      log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
      log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
      log("LWJGL: " + Sys.getVersion());
      d = GL11.glGetString(GL11.GL_VERSION);
      e = GL11.glGetString(GL11.GL_RENDERER);
      f = GL11.glGetString(GL11.GL_VENDOR);
      log("OpenGL: " + e + ", version " + d + ", " + f);
      log("OpenGL Version: " + Method248());
      if (!GLContext.getCapabilities().OpenGL12) {
         log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
      }

      fancyFogAvailable = GLContext.getCapabilities().GL_NV_fog_distance;
      if (!fancyFogAvailable) {
         log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
      }

      occlusionAvailable = GLContext.getCapabilities().GL_ARB_occlusion_query;
      if (!occlusionAvailable) {
         log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
      }
   }

   public static String getBuild() {
      if (build == null) {
         try {
            InputStream var0 = Config.class.getResourceAsStream("/buildof.txt");
            if (var0 == null) {
               return null;
            }

            build = Method273(var0)[0];
         } catch (Exception var1) {
            Method252(var1.getClass().getName() + ": " + var1.getMessage());
            build = "";
         }
      }

      return build;
   }

   public static boolean isFancyFogAvailable() {
      return fancyFogAvailable;
   }

   public static boolean isOcclusionAvailable() {
      return occlusionAvailable;
   }

   public static int getMinecraftVersionInt() {
      if (minecraftVersionInt < 0) {
         String[] var2 = tokenize("1.8.9", ".");
         int var3 = 0;
         if (var2.length > 0) {
            var3 = 0 + 10000 * parseInt(var2[0], 0);
         }

         if (var2.length > 1) {
            var3 += 100 * parseInt(var2[1], 0);
         }

         if (var2.length > 2) {
            var3 += parseInt(var2[2], 0);
         }

         minecraftVersionInt = var3;
      }

      return minecraftVersionInt;
   }

   public static String Method248() {
      GlVersion var0 = getGlVersion();
      return var0.getMajor() + "." + var0.getMinor() + "." + var0.getRelease();
   }

   private static GlVersion Method249() {
      return GLContext.getCapabilities().OpenGL44
         ? new GlVersion(4, 4)
         : (
            GLContext.getCapabilities().OpenGL43
               ? new GlVersion(4, 3)
               : (
                  GLContext.getCapabilities().OpenGL42
                     ? new GlVersion(4, 2)
                     : (
                        GLContext.getCapabilities().OpenGL41
                           ? new GlVersion(4, 1)
                           : (
                              GLContext.getCapabilities().OpenGL40
                                 ? new GlVersion(4, 0)
                                 : (
                                    GLContext.getCapabilities().OpenGL33
                                       ? new GlVersion(3, 3)
                                       : (
                                          GLContext.getCapabilities().OpenGL32
                                             ? new GlVersion(3, 2)
                                             : (
                                                GLContext.getCapabilities().OpenGL31
                                                   ? new GlVersion(3, 1)
                                                   : (
                                                      GLContext.getCapabilities().OpenGL30
                                                         ? new GlVersion(3, 0)
                                                         : (
                                                            GLContext.getCapabilities().OpenGL21
                                                               ? new GlVersion(2, 1)
                                                               : (
                                                                  GLContext.getCapabilities().OpenGL20
                                                                     ? new GlVersion(2, 0)
                                                                     : (
                                                                        GLContext.getCapabilities().OpenGL15
                                                                           ? new GlVersion(1, 5)
                                                                           : (
                                                                              GLContext.getCapabilities().OpenGL14
                                                                                 ? new GlVersion(1, 4)
                                                                                 : (
                                                                                    GLContext.getCapabilities().OpenGL13
                                                                                       ? new GlVersion(1, 3)
                                                                                       : (
                                                                                          GLContext.getCapabilities().OpenGL12
                                                                                             ? new GlVersion(1, 2)
                                                                                             : (
                                                                                                GLContext.getCapabilities().OpenGL11
                                                                                                   ? new GlVersion(1, 1)
                                                                                                   : new GlVersion(1, 0)
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

   public static GlVersion getGlVersion() {
      if (glVersion == null) {
         String var2 = GL11.glGetString(GL11.GL_VERSION);
         glVersion = parseGlVersion(var2, null);
         if (glVersion == null) {
            glVersion = Method249();
         }

         if (glVersion == null) {
            glVersion = new GlVersion(1, 0);
         }
      }

      return glVersion;
   }

   public static GlVersion getGlslVersion() {
      if (glslVersion == null) {
         String var2 = GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION);
         glslVersion = parseGlVersion(var2, null);
         if (glslVersion == null) {
            glslVersion = new GlVersion(1, 10);
         }
      }

      return glslVersion;
   }

   public static GlVersion parseGlVersion(String string, GlVersion glVersion) {
      try {
         if (string == null) {
            return glVersion;
         }

         Matcher var3 = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?").matcher(string);
         if (!var3.matches()) {
            return glVersion;
         }

         int var4 = Integer.parseInt(var3.group(1));
         int var5 = Integer.parseInt(var3.group(2));
         int var6 = var3.group(4) != null ? Integer.parseInt(var3.group(4)) : 0;
         String var7 = var3.group(5);
         return new GlVersion(var4, var5, var6, var7);
      } catch (Exception var8) {
         var8.printStackTrace();
         return glVersion;
      }
   }

   public static String[] getOpenGlExtensions() {
      if (openGlExtensions == null) {
         openGlExtensions = detectOpenGlExtensions();
      }

      return openGlExtensions;
   }

   private static String[] detectOpenGlExtensions() {
      try {
         if (getGlVersion().getMajor() >= 3) {
            int var3 = GL11.glGetInteger(GL30.GL_NUM_EXTENSIONS);
            if (var3 > 0) {
               String[] var4 = new String[var3];

               for (int var5 = 0; var5 < var3; var5++) {
                  var4[var5] = GL30.glGetStringi(GL11.GL_EXTENSIONS, var5);
               }

               return var4;
            }
         }
      } catch (Exception var7) {
         var7.printStackTrace();
      }

      try {
         return GL11.glGetString(GL11.GL_EXTENSIONS).split(" ");
      } catch (Exception var6) {
         var6.printStackTrace();
         return new String[0];
      }
   }

   public static void updateThreadPriorities() {
      updateAvailableProcessors();
      if (isSingleProcessor()) {
         if (isSmoothWorld()) {
            minecraftThread.setPriority(10);
            setThreadPriority("Server thread", 1);
         } else {
            minecraftThread.setPriority(5);
            setThreadPriority("Server thread", 5);
         }
      } else {
         minecraftThread.setPriority(10);
         setThreadPriority("Server thread", 5);
      }
   }

   private static void setThreadPriority(String string, int integer) {
      try {
         ThreadGroup var4 = Thread.currentThread().getThreadGroup();
         if (var4 == null) {
            return;
         }

         Thread[] var6 = new Thread[(var4.activeCount() + 10) * 2];
         var4.enumerate(var6, false);

         for (Thread var10 : var6) {
            if (var10 != null && var10.getName().startsWith(string)) {
               var10.setPriority(integer);
            }
         }
      } catch (Throwable var11) {
         Method252(var11.getClass().getName() + ": " + var11.getMessage());
      }
   }

   public static boolean isMinecraftThread() {
      return Thread.currentThread() == minecraftThread;
   }

   private static void startVersionCheckThread() {
      new VersionCheckThread().start();
   }

   public static boolean isMipmaps() {
      return gameSettings.mipmapLevels > 0;
   }

   public static int getMipmapLevels() {
      return gameSettings.mipmapLevels;
   }

   public static int getMipmapType() {
      switch (gameSettings.ofMipmapType) {
         case 2:
            if (isMultiTexture()) {
               return 9985;
            }

            return 9986;
         case 3:
            if (isMultiTexture()) {
               return 9987;
            }

            return 9986;
         default:
            return 9986;
      }
   }

   public static boolean isUseAlphaFunc() {
      float var0 = getAlphaFuncLevel();
      return var0 > DEF_ALPHA_FUNC_LEVEL + 1.0E-5F;
   }

   public static float getAlphaFuncLevel() {
      return DEF_ALPHA_FUNC_LEVEL;
   }

   public static boolean isFogFancy() {
      return isFancyFogAvailable() && gameSettings.ofFogType == 2;
   }

   public static boolean isFogFast() {
      return gameSettings.ofFogType == 1;
   }

   public static boolean isFogOff() {
      return gameSettings.ofFogType == 3;
   }

   public static boolean isFogOn() {
      return gameSettings.ofFogType != 3;
   }

   public static float getFogStart() {
      return gameSettings.ofFogStart;
   }

   public static void Method250(String string) {
      if (logDetail) {
         LOGGER.info("[OptiFine] " + string);
      }
   }

   public static void Method251(String string) {
      LOGGER.info("[OptiFine] " + string);
   }

   public static void Method252(String string) {
      LOGGER.warn("[OptiFine] " + string);
   }

   public static void Method253(String string) {
      LOGGER.error("[OptiFine] " + string);
   }

   public static void log(String string) {
      Method251(string);
   }

   public static int getUpdatesPerFrame() {
      return gameSettings.ofChunkUpdates;
   }

   public static boolean Method254() {
      return gameSettings.ofChunkUpdatesDynamic;
   }

   public static boolean isRainFancy() {
      return gameSettings.ofRain == 0 ? gameSettings.fancyGraphics : gameSettings.ofRain == 2;
   }

   public static boolean isRainOff() {
      return gameSettings.ofRain == 3;
   }

   public static boolean isCloudsFancy() {
      return gameSettings.ofClouds != 0
         ? gameSettings.ofClouds == 2
         : (
            isShaders() && !Shaders.shaderPackClouds.isDefault()
               ? Shaders.shaderPackClouds.isFancy()
               : (texturePackClouds != 0 ? texturePackClouds == 2 : gameSettings.fancyGraphics)
         );
   }

   public static boolean isCloudsOff() {
      return gameSettings.ofClouds != 0
         ? gameSettings.ofClouds == 3
         : (isShaders() && !Shaders.shaderPackClouds.isDefault() ? Shaders.shaderPackClouds.isOff() : texturePackClouds == 3);
   }

   public static void updateTexturePackClouds() {
      texturePackClouds = 0;
      IResourceManager var2 = getResourceManager();
      if (var2 != null) {
         try {
            InputStream var3 = var2.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
            if (var3 == null) {
               return;
            }

            PropertiesOrdered var4 = new PropertiesOrdered();
            var4.load(var3);
            var3.close();
            String var5 = var4.getProperty("clouds");
            if (var5 == null) {
               return;
            }

            Method251("Texture pack clouds: " + var5);
            var5 = var5.toLowerCase();
            if (var5.equals("fast")) {
               texturePackClouds = 1;
            }

            if (var5.equals("fancy")) {
               texturePackClouds = 2;
            }

            if (var5.equals("off")) {
               texturePackClouds = 3;
            }
         } catch (Exception var6) {
         }
      }
   }

   public static ModelManager getModelManager() {
      return minecraft.getRenderItem().modelManager;
   }

   public static boolean isTreesFancy() {
      return gameSettings.ofTrees == 0 ? gameSettings.fancyGraphics : gameSettings.ofTrees != 1;
   }

   public static boolean isTreesSmart() {
      return gameSettings.ofTrees == 4;
   }

   public static boolean isCullFacesLeaves() {
      return gameSettings.ofTrees == 0 ? !gameSettings.fancyGraphics : gameSettings.ofTrees == 4;
   }

   public static boolean isDroppedItemsFancy() {
      return gameSettings.ofDroppedItems == 0 ? gameSettings.fancyGraphics : gameSettings.ofDroppedItems == 2;
   }

   public static int Method255(int integer1, int integer2, int integer3) {
      return integer1 < integer2 ? integer2 : Math.min(integer1, integer3);
   }

   public static float Method256(float float1, float float2, float float3) {
      return float1 < float2 ? float2 : Math.min(float1, float3);
   }

   public static double Method257(double double1, double double2, double double3) {
      return double1 < double2 ? double2 : Math.min(double1, double3);
   }

   public static float Method258(float float1) {
      return float1 < 0.0F ? 0.0F : Math.min(float1, 1.0F);
   }

   public static boolean Method259() {
      return gameSettings.ofAnimatedWater != 2;
   }

   public static boolean Method260() {
      return gameSettings.ofAnimatedWater == 1;
   }

   public static boolean Method261() {
      return gameSettings.ofAnimatedPortal;
   }

   public static boolean Method262() {
      return gameSettings.ofAnimatedLava != 2;
   }

   public static boolean Method263() {
      return gameSettings.ofAnimatedLava == 1;
   }

   public static boolean Method264() {
      return gameSettings.ofAnimatedFire;
   }

   public static boolean isAnimatedRedstone() {
      return gameSettings.ofAnimatedRedstone;
   }

   public static boolean isAnimatedExplosion() {
      return gameSettings.ofAnimatedExplosion;
   }

   public static boolean isAnimatedFlame() {
      return gameSettings.ofAnimatedFlame;
   }

   public static boolean isAnimatedSmoke() {
      return gameSettings.ofAnimatedSmoke;
   }

   public static boolean isVoidParticles() {
      return gameSettings.ofVoidParticles;
   }

   public static boolean isWaterParticles() {
      return gameSettings.ofWaterParticles;
   }

   public static boolean isRainSplash() {
      return gameSettings.ofRainSplash;
   }

   public static boolean isPortalParticles() {
      return gameSettings.ofPortalParticles;
   }

   public static boolean isPotionParticles() {
      return gameSettings.ofPotionParticles;
   }

   public static boolean isFireworkParticles() {
      return gameSettings.ofFireworkParticles;
   }

   public static float getAmbientOcclusionLevel() {
      return isShaders() && Shaders.aoLevel >= 0.0F ? Shaders.aoLevel : gameSettings.ofAoLevel;
   }

   public static String listToString(List list) {
      return listToString(list, ", ");
   }

   public static String listToString(List list, String string) {
      if (list == null) {
         return "";
      }

      StringBuilder var2 = new StringBuilder(list.size() * 5);

      for (int var3 = 0; var3 < list.size(); var3++) {
         Object var4 = list.get(var3);
         if (var3 > 0) {
            var2.append(string);
         }

         var2.append(var4);
      }

      return var2.toString();
   }

   public static String arrayToString(Object[] arr) {
      return Method265(arr, ", ");
   }

   public static String Method265(Object[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var2 = new StringBuilder(arr.length * 5);

      for (int var3 = 0; var3 < arr.length; var3++) {
         Object var4 = arr[var3];
         if (var3 > 0) {
            var2.append(string);
         }

         var2.append(var4);
      }

      return var2.toString();
   }

   public static String arrayToString(int[] arr) {
      return Method266(arr, ", ");
   }

   public static String Method266(int[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var2 = new StringBuilder(arr.length * 5);

      for (int var3 = 0; var3 < arr.length; var3++) {
         int var4 = arr[var3];
         if (var3 > 0) {
            var2.append(string);
         }

         var2.append(var4);
      }

      return var2.toString();
   }

   public static String arrayToString(float[] arr) {
      return Method267(arr, ", ");
   }

   public static String Method267(float[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var2 = new StringBuilder(arr.length * 5);

      for (int var3 = 0; var3 < arr.length; var3++) {
         float var4 = arr[var3];
         if (var3 > 0) {
            var2.append(string);
         }

         var2.append(var4);
      }

      return var2.toString();
   }

   public static Minecraft getMinecraft() {
      return minecraft;
   }

   public static TextureManager getTextureManager() {
      return minecraft.getTextureManager();
   }

   public static IResourceManager getResourceManager() {
      return minecraft.getResourceManager();
   }

   public static InputStream getResourceStream(ResourceLocation resourceLocation) throws IOException {
      return getResourceStream(minecraft.getResourceManager(), resourceLocation);
   }

   public static InputStream getResourceStream(IResourceManager iResourceManager, ResourceLocation resourceLocation) throws IOException {
      IResource var2 = iResourceManager.getResource(resourceLocation);
      return var2 == null ? null : var2.getInputStream();
   }

   public static IResource getResource(ResourceLocation resourceLocation) throws IOException {
      return minecraft.getResourceManager().getResource(resourceLocation);
   }

   public static boolean hasResource(ResourceLocation resourceLocation) {
      if (resourceLocation == null) {
         return false;
      }

      IResourcePack var1 = getDefiningResourcePack(resourceLocation);
      return var1 != null;
   }

   public static boolean hasResource(IResourceManager iResourceManager, ResourceLocation resourceLocation) {
      try {
         IResource var2 = iResourceManager.getResource(resourceLocation);
         return var2 != null;
      } catch (IOException var3) {
         return false;
      }
   }

   public static IResourcePack[] getResourcePacks() {
      ResourcePackRepository var0 = minecraft.getResourcePackRepository();
      List<ResourcePackRepository.Entry> var1 = var0.getRepositoryEntries();
      List<IResourcePack> var2 = new ArrayList<>();

      for (ResourcePackRepository.Entry var4 : var1) {
         var2.add(var4.getResourcePack());
      }

      if (var0.getResourcePackInstance() != null) {
         var2.add(var0.getResourcePackInstance());
      }

      return var2.toArray(new IResourcePack[0]);
   }

   public static String getResourcePackNames() {
      if (minecraft.getResourcePackRepository() == null) {
         return "";
      }

      IResourcePack[] var0 = getResourcePacks();
      if (var0.length == 0) {
         return getDefaultResourcePack().getPackName();
      }

      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = var0[var2].getPackName();
      }

      return arrayToString(var1);
   }

   public static DefaultResourcePack getDefaultResourcePack() {
      if (defaultResourcePackLazy == null) {
         Minecraft var0 = Minecraft.getMinecraft();
         defaultResourcePackLazy = var0.mcDefaultResourcePack;
         if (defaultResourcePackLazy == null) {
            ResourcePackRepository var1 = var0.getResourcePackRepository();
            if (var1 != null) {
               defaultResourcePackLazy = (DefaultResourcePack)var1.rprDefaultResourcePack;
            }
         }
      }

      return defaultResourcePackLazy;
   }

   public static boolean isFromDefaultResourcePack(ResourceLocation resourceLocation) {
      IResourcePack var1 = getDefiningResourcePack(resourceLocation);
      return var1 == getDefaultResourcePack();
   }

   public static IResourcePack getDefiningResourcePack(ResourceLocation resourceLocation) {
      ResourcePackRepository var1 = minecraft.getResourcePackRepository();
      IResourcePack var2 = var1.getResourcePackInstance();
      if (var2 != null && var2.resourceExists(resourceLocation)) {
         return var2;
      }

      List var3 = var1.repositoryEntries;

      for (int var4 = var3.size() - 1; var4 >= 0; var4--) {
         IResourcePack var6 = ((ResourcePackRepository.Entry)var3.get(var4)).getResourcePack();
         if (var6.resourceExists(resourceLocation)) {
            return var6;
         }
      }

      return getDefaultResourcePack().resourceExists(resourceLocation) ? getDefaultResourcePack() : null;
   }

   public static RenderGlobal getRenderGlobal() {
      return minecraft.renderGlobal;
   }

   public static boolean isBetterGrass() {
      return gameSettings.ofBetterGrass != 3;
   }

   public static boolean isBetterGrassFancy() {
      return gameSettings.ofBetterGrass == 2;
   }

   public static boolean Method268() {
      return gameSettings.ofWeather;
   }

   public static boolean isSkyEnabled() {
      return gameSettings.ofSky;
   }

   public static boolean isSunMoonEnabled() {
      return gameSettings.ofSunMoon;
   }

   public static boolean isSunTexture() {
      return isSunMoonEnabled() && (!isShaders() || Shaders.isSun());
   }

   public static boolean isMoonTexture() {
      return isSunMoonEnabled() && (!isShaders() || Shaders.isMoon());
   }

   public static boolean isVignetteEnabled() {
      return (!isShaders() || Shaders.isVignette()) && (gameSettings.ofVignette == 0 ? gameSettings.fancyGraphics : gameSettings.ofVignette == 2);
   }

   public static boolean isStarsEnabled() {
      return gameSettings.ofStars;
   }

   public static void sleep(long long1) {
      try {
         Thread.sleep(long1);
      } catch (InterruptedException var2) {
         var2.printStackTrace();
      }
   }

   public static boolean Method269() {
      return gameSettings.ofTime == 1;
   }

   public static boolean Method270() {
      return gameSettings.ofTime == 0;
   }

   public static boolean Method271() {
      return gameSettings.ofTime == 2;
   }

   public static boolean isClearWater() {
      return gameSettings.ofClearWater;
   }

   public static int getAnisotropicFilterLevel() {
      GameSettings settings = getGameSettings();
      return settings != null ? settings.ofAfLevel : 1;
   }

   public static boolean isAnisotropicFiltering() {
      return getAnisotropicFilterLevel() > 1;
   }

   public static int getAntialiasingLevel() {
      return 0;
   }

   public static boolean isAntialiasing() {
      return getAntialiasingLevel() > 0;
   }

   public static boolean isAntialiasingConfigured() {
      return getGameSettings().ofAaLevel > 0;
   }

   public static boolean isMultiTexture() {
      return getAnisotropicFilterLevel() > 1 || getAntialiasingLevel() > 0;
   }

   public static boolean between(int integer1, int integer2, int integer3) {
      return integer1 >= integer2 && integer1 <= integer3;
   }

   public static boolean between(float float1, float float2, float float3) {
      return float1 >= float2 && float1 <= float3;
   }

   public static boolean isDrippingWaterLava() {
      return gameSettings.ofDrippingWaterLava;
   }

   public static boolean isBetterSnow() {
      return gameSettings.ofBetterSnow;
   }

   public static Dimension getFullscreenDimension() {
      if (desktopDisplayMode == null) {
         return null;
      }

      if (gameSettings == null) {
         return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
      }

      String var0 = gameSettings.ofFullscreenMode;
      if (var0.equals("Default")) {
         return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
      }

      String[] var1 = tokenize(var0, " x");
      return var1.length < 2
         ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight())
         : new Dimension(parseInt(var1[0], -1), parseInt(var1[1], -1));
   }

   public static int parseInt(String string, int integer) {
      try {
         return string == null ? integer : Integer.parseInt(string.trim());
      } catch (NumberFormatException var2) {
         return integer;
      }
   }

   public static float parseFloat(String string, float float2) {
      try {
         return string == null ? float2 : Float.parseFloat(string.trim());
      } catch (NumberFormatException var2) {
         return float2;
      }
   }

   public static boolean parseBoolean(String string, boolean boolean2) {
      try {
         return string == null ? boolean2 : Boolean.parseBoolean(string.trim());
      } catch (NumberFormatException var2) {
         return boolean2;
      }
   }

   public static Boolean parseBoolean(String string, Boolean boolean2) {
      try {
         if (string == null) {
            return boolean2;
         }

         string = string.trim().toLowerCase();
         return string.equals("true") ? Boolean.TRUE : (string.equals("false") ? Boolean.FALSE : boolean2);
      } catch (NumberFormatException var2) {
         return boolean2;
      }
   }

   public static String[] tokenize(String string1, String string2) {
      StringTokenizer var2 = new StringTokenizer(string1, string2);
      List<String> var3 = new ArrayList<>();

      while (var2.hasMoreTokens()) {
         String var4 = var2.nextToken();
         var3.add(var4);
      }

      return var3.toArray(new String[0]);
   }

   public static DisplayMode getDesktopDisplayMode() {
      return desktopDisplayMode;
   }

   public static DisplayMode[] getDisplayModes() {
      if (displayModes == null) {
         try {
            DisplayMode[] var0 = Display.getAvailableDisplayModes();
            Set<Dimension> var1 = getDisplayModeDimensions(var0);
            List<DisplayMode> var2 = new ArrayList<>();

            for (Dimension var4 : var1) {
               DisplayMode var6 = getDisplayMode(getDisplayModes(var0, var4), desktopDisplayMode);
               if (var6 != null) {
                  var2.add(var6);
               }
            }

            DisplayMode[] var8 = var2.toArray(new DisplayMode[0]);
            Arrays.sort(var8, new DisplayModeComparator());
            return var8;
         } catch (Exception var7) {
            var7.printStackTrace();
            displayModes = new DisplayMode[]{desktopDisplayMode};
         }
      }

      return displayModes;
   }

   public static DisplayMode getLargestDisplayMode() {
      DisplayMode[] var0 = getDisplayModes();
      if (var0 != null && var0.length >= 1) {
         DisplayMode var1 = var0[var0.length - 1];
         return desktopDisplayMode.getWidth() > var1.getWidth()
            ? desktopDisplayMode
            : (desktopDisplayMode.getWidth() == var1.getWidth() && desktopDisplayMode.getHeight() > var1.getHeight() ? desktopDisplayMode : var1);
      } else {
         return desktopDisplayMode;
      }
   }

   private static Set<Dimension> getDisplayModeDimensions(DisplayMode[] arr) {
      HashSet var1 = new HashSet();

      for (DisplayMode var5 : arr) {
         Dimension var6 = new Dimension(var5.getWidth(), var5.getHeight());
         var1.add(var6);
      }

      return var1;
   }

   private static DisplayMode[] getDisplayModes(DisplayMode[] arr, Dimension dimension) {
      List<DisplayMode> var2 = new ArrayList<>();

      for (DisplayMode var6 : arr) {
         if (var6.getWidth() == dimension.getWidth() && var6.getHeight() == dimension.getHeight()) {
            var2.add(var6);
         }
      }

      return var2.toArray(new DisplayMode[0]);
   }

   private static DisplayMode getDisplayMode(DisplayMode[] arr, DisplayMode displayMode) {
      if (displayMode != null) {
         for (DisplayMode var5 : arr) {
            if (var5.getBitsPerPixel() == displayMode.getBitsPerPixel() && var5.getFrequency() == displayMode.getFrequency()) {
               return var5;
            }
         }
      }

      if (arr.length == 0) {
         return null;
      }

      Arrays.sort(arr, new DisplayModeComparator());
      return arr[arr.length - 1];
   }

   public static String[] Method272() {
      DisplayMode[] var0 = getDisplayModes();
      String[] var1 = new String[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         DisplayMode var3 = var0[var2];
         String var4 = var3.getWidth() + "x" + var3.getHeight();
         var1[var2] = var4;
      }

      return var1;
   }

   public static DisplayMode getDisplayMode(Dimension dimension) throws LWJGLException {
      for (DisplayMode var5 : getDisplayModes()) {
         if (var5.getWidth() == dimension.width && var5.getHeight() == dimension.height) {
            return var5;
         }
      }

      return desktopDisplayMode;
   }

   public static boolean ab() {
      return gameSettings.ofAnimatedTerrain;
   }

   public static boolean isAnimatedTextures() {
      return gameSettings.ofAnimatedTextures;
   }

   public static boolean isSwampColors() {
      return gameSettings.ofSwampColors;
   }

   public static boolean isRandomEntities() {
      return gameSettings.ofRandomEntities;
   }

   public static void checkGlError(String string) {
      int var3 = GlStateManager.glGetError();
      if (var3 != 0 && GlErrors.isEnabled(var3)) {
         String var4 = getGlErrorString(var3);
         String var5 = String.format("OpenGL error: %s (%s), at: %s", var3, var4, string);
         Method253(var5);
         if (isShowGlErrors() && TimedEvent.isActive("ShowGlError", 10000L)) {
            String var6 = I18n.format("of.message.openglError", var3, var4);
            minecraft.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(var6));
         }
      }
   }

   public static boolean isSmoothBiomes() {
      return gameSettings.ofSmoothBiomes;
   }

   public static boolean isCustomColors() {
      return gameSettings.ofCustomColors;
   }

   public static boolean isCustomSky() {
      return gameSettings.ofCustomSky;
   }

   public static boolean isCustomFonts() {
      return gameSettings.ofCustomFonts;
   }

   public static boolean isShowCapes() {
      return gameSettings.ofShowCapes;
   }

   public static boolean isConnectedTextures() {
      return gameSettings.ofConnectedTextures != 3;
   }

   public static boolean isNaturalTextures() {
      return gameSettings.ofNaturalTextures;
   }

   public static boolean isEmissiveTextures() {
      return gameSettings.ofEmissiveTextures;
   }

   public static boolean isConnectedTexturesFancy() {
      return gameSettings.ofConnectedTextures == 2;
   }

   public static boolean ao() {
      return gameSettings.ofTranslucentBlocks == 0 ? gameSettings.fancyGraphics : gameSettings.ofTranslucentBlocks == 2;
   }

   public static boolean isShaders() {
      return Shaders.shaderPackLoaded;
   }

   public static String[] readLines(File file) throws IOException {
      return Method273(new FileInputStream(file));
   }

   public static String[] Method273(InputStream inputStream) throws IOException {
      List<String> var1 = new ArrayList<>();
      InputStreamReader var2 = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);
      BufferedReader var3 = new BufferedReader(var2);

      while (true) {
         String var4 = var3.readLine();
         if (var4 == null) {
            return var1.toArray(new String[0]);
         }

         var1.add(var4);
      }
   }

   public static String readFile(File file) throws IOException {
      return readInputStream(new FileInputStream(file), "ASCII");
   }

   public static String readInputStream(InputStream inputStream) throws IOException {
      return readInputStream(inputStream, "ASCII");
   }

   public static String readInputStream(InputStream inputStream, String string) throws IOException {
      InputStreamReader var2 = new InputStreamReader(inputStream, string);
      BufferedReader var3 = new BufferedReader(var2);
      StringBuilder var4 = new StringBuilder();

      while (true) {
         String var5 = var3.readLine();
         if (var5 == null) {
            return var4.toString();
         }

         var4.append(var5);
         var4.append("\n");
      }
   }

   public static byte[] readAll(InputStream inputStream) throws IOException {
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      byte[] var4 = new byte[1024];

      while (true) {
         int var5 = inputStream.read(var4);
         if (var5 < 0) {
            inputStream.close();
            return var3.toByteArray();
         }

         var3.write(var4, 0, var5);
      }
   }

   public static GameSettings getGameSettings() {
      if (gameSettings == null) {
         Minecraft mc = Minecraft.getMinecraft();
         if (mc != null) {
            gameSettings = mc.gameSettings;
         }
      }
      return gameSettings;
   }

   public static int compareRelease(String string1, String string2) {
      String[] var2 = splitRelease(string1);
      String[] var3 = splitRelease(string2);
      String var4 = var2[0];
      String var5 = var3[0];
      if (!var4.equals(var5)) {
         return var4.compareTo(var5);
      }

      int var6 = parseInt(var2[1], -1);
      int var7 = parseInt(var3[1], -1);
      if (var6 != var7) {
         return var6 - var7;
      }

      String var8 = var2[2];
      String var9 = var3[2];
      if (!var8.equals(var9)) {
         if (var8.isEmpty()) {
            return 1;
         }

         if (var9.isEmpty()) {
            return -1;
         }
      }

      return var8.compareTo(var9);
   }

   private static String[] splitRelease(String string) {
      if (string != null && string.length() > 0) {
         Matcher var2 = Pattern.compile("([A-Z])([0-9]+)(.*)").matcher(string);
         if (!var2.matches()) {
            return new String[]{"", "", ""};
         }

         String var3 = normalize(var2.group(1));
         String var4 = normalize(var2.group(2));
         String var5 = normalize(var2.group(3));
         return new String[]{var3, var4, var5};
      } else {
         return new String[]{"", "", ""};
      }
   }

   public static int intHash(int integer) {
      integer = integer ^ 61 ^ integer >> 16;
      integer += integer << 3;
      integer = (integer ^ integer >> 4) * 668265261;
      return integer ^ integer >> 15;
   }

   public static int getRandom(BlockPos blockPos, int integer) {
      return intHash(intHash(intHash(intHash(integer + 37) + blockPos.getX()) + blockPos.getZ()) + blockPos.getY());
   }

   public static int getAvailableProcessors() {
      return availableProcessors;
   }

   public static void updateAvailableProcessors() {
      availableProcessors = Runtime.getRuntime().availableProcessors();
   }

   public static boolean isSingleProcessor() {
      return getAvailableProcessors() <= 1;
   }

   public static boolean isSmoothWorld() {
      return gameSettings.ofSmoothWorld;
   }

   public static boolean as() {
      return gameSettings.ofLazyChunkLoading;
   }

   public static boolean isDynamicFov() {
      return gameSettings.ofDynamicFov;
   }

   public static boolean au() {
      return gameSettings.ofAlternateBlocks;
   }

   public static int getChunkViewDistance() {
      return gameSettings == null ? 10 : gameSettings.renderDistanceChunks;
   }

   public static boolean equals(Object object1, Object object2) {
      return Objects.equals(object1, object2);
   }

   public static boolean equalsOne(Object object, Object[] arr) {
      if (arr != null) {
         for (Object var5 : arr) {
            if (equals(object, var5)) {
               return true;
            }
         }
      }

      return false;
   }

   public static boolean equalsOne(int integer, int[] arr) {
      for (int var5 : arr) {
         if (var5 == integer) {
            return true;
         }
      }

      return false;
   }

   public static boolean isSameOne(Object object, Object[] arr) {
      if (arr != null) {
         for (Object var5 : arr) {
            if (object == var5) {
               return true;
            }
         }
      }

      return false;
   }

   public static String normalize(String string) {
      return string == null ? "" : string;
   }

   private static ByteBuffer Method274(InputStream inputStream) throws IOException {
      BufferedImage var3 = ImageIO.read(inputStream);
      int[] var4 = var3.getRGB(0, 0, var3.getWidth(), var3.getHeight(), null, 0, var3.getWidth());
      ByteBuffer var5 = ByteBuffer.allocate(4 * var4.length);

      for (int var9 : var4) {
         var5.putInt(var9 << 8 | var9 >> 24 & 0xFF);
      }

      var5.flip();
      return var5;
   }

   public static void checkDisplayMode() {
      try {
         if (minecraft.isFullScreen()) {
            if (fullscreenModeChecked) {
               return;
            }

            fullscreenModeChecked = true;
            desktopModeChecked = false;
            DisplayMode var0 = Display.getDisplayMode();
            Dimension var1 = getFullscreenDimension();
            if (var1 == null) {
               return;
            }

            if (var0.getWidth() == var1.width && var0.getHeight() == var1.height) {
               return;
            }

            DisplayMode var2 = getDisplayMode(var1);
            if (var2 == null) {
               return;
            }

            Display.setDisplayMode(var2);
            minecraft.displayWidth = Display.getDisplayMode().getWidth();
            minecraft.displayHeight = Display.getDisplayMode().getHeight();
            if (minecraft.displayWidth <= 0) {
               minecraft.displayWidth = 1;
            }

            if (minecraft.displayHeight <= 0) {
               minecraft.displayHeight = 1;
            }

            if (minecraft.currentScreen != null) {
               ScaledResolution var3 = new ScaledResolution(minecraft);
               int var4 = var3.Method237();
               int var5 = var3.Method238();
               minecraft.currentScreen.setWorldAndResolution(minecraft, var4, var5);
            }

            updateFramebufferSize();
            Display.setFullscreen(true);
            minecraft.gameSettings.updateVSync();
            GlStateManager.enableTexture2D();
         } else {
            if (desktopModeChecked) {
               return;
            }

            desktopModeChecked = true;
            fullscreenModeChecked = false;
            minecraft.gameSettings.updateVSync();
            Display.update();
            GlStateManager.enableTexture2D();
            Display.setResizable(false);
            Display.setResizable(true);
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         gameSettings.ofFullscreenMode = "Default";
         gameSettings.saveOfOptions();
      }
   }

   public static void updateFramebufferSize() {
      minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
      if (minecraft.entityRenderer != null) {
         minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
      }

      minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
   }

   public static Object[] addObjectToArray(Object[] arr, Object object) {
      if (arr == null) {
         throw new NullPointerException("The given array is NULL");
      }

      int var2 = arr.length;
      int var3 = var2 + 1;
      Object[] var4 = (Object[])Array.newInstance(arr.getClass().getComponentType(), var3);
      System.arraycopy(arr, 0, var4, 0, var2);
      var4[var2] = object;
      return var4;
   }

   public static Object[] addObjectToArray(Object[] arr, Object object, int integer) {
      ArrayList var3 = new ArrayList<>(Arrays.asList(arr));
      var3.add(integer, object);
      Object[] var4 = (Object[])Array.newInstance(arr.getClass().getComponentType(), var3.size());
      return var3.toArray(var4);
   }

   public static Object[] addObjectsToArray(Object[] arr1, Object[] arr2) {
      if (arr1 == null) {
         throw new NullPointerException("The given array is NULL");
      }

      if (arr2.length == 0) {
         return arr1;
      }

      int var2 = arr1.length;
      int var3 = var2 + arr2.length;
      Object[] var4 = (Object[])Array.newInstance(arr1.getClass().getComponentType(), var3);
      System.arraycopy(arr1, 0, var4, 0, var2);
      System.arraycopy(arr2, 0, var4, var2, arr2.length);
      return var4;
   }

   public static Object[] removeObjectFromArray(Object[] arr, Object object) {
      ArrayList var2 = new ArrayList<>(Arrays.asList(arr));
      var2.remove(object);
      return Method275(var2, arr.getClass().getComponentType());
   }

   public static Object[] Method275(Collection collection, Class class2) {
      if (collection == null) {
         return null;
      }

      if (class2 == null) {
         return null;
      }

      if (class2.isPrimitive()) {
         throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + class2);
      }

      Object[] var2 = (Object[])Array.newInstance(class2, collection.size());
      return collection.toArray(var2);
   }

   public static boolean isCustomItems() {
      return gameSettings.ofCustomItems;
   }

   public static void drawFps() {
      int var2 = Minecraft.getDebugFPS();
      String var3 = getUpdates(minecraft.debug);
      int var4 = minecraft.renderGlobal.getCountActiveRenderers();
      int var5 = minecraft.renderGlobal.getCountEntitiesRendered();
      int var6 = minecraft.renderGlobal.getCountTileEntitiesRendered();
      String var7 = var2 + "/" + getFpsMin() + " fps, C: " + var4 + ", E: " + var5 + "+" + var6 + ", U: " + var3;
      minecraft.fontRendererObj.drawString(var7, 2.0F, 2.0F, -2039584);
   }

   public static int getFpsMin() {
      if (minecraft.debug != mcDebugLast) {
         mcDebugLast = minecraft.debug;
         FrameTimer var0 = minecraft.getFrameTimer();
         long[] var1 = var0.getFrames();
         int var2 = var0.getIndex();
         int var3 = var0.getLastIndex();
         if (var2 != var3) {
            int var4 = Minecraft.getDebugFPS();
            if (var4 <= 0) {
               var4 = 1;
            }

            long var5 = (long)(1.0 / var4 * 1.0E9);
            long var7 = 0L;

            for (int var9 = MathHelper.normalizeAngle(var2 - 1, var1.length);
               var9 != var3 && var7 < 1.0E9;
               var9 = MathHelper.normalizeAngle(var9 - 1, var1.length)
            ) {
               long var10 = var1[var9];
               if (var10 > var5) {
                  var5 = var10;
               }

               var7 += var10;
            }

            double var12 = var5 / 1.0E9;
            fpsMinLast = (int)(1.0 / var12);
         }
      }

      return fpsMinLast;
   }

   private static String getUpdates(String string) {
      int var3 = string.indexOf(40);
      if (var3 < 0) {
         return "";
      }

      int var4 = string.indexOf(32, var3);
      return var4 < 0 ? "" : string.substring(var3 + 1, var4);
   }

   public static int getBitsOs() {
      String var2 = System.getenv("ProgramFiles(X86)");
      return var2 != null ? 64 : 32;
   }

   public static int getBitsJre() {
      String[] var3 = new String[]{"sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch"};
      int var4 = var3.length;

      for (int var5 = 0; var5 < var4; var5++) {
         String var7 = System.getProperty(var3[var5]);
         if (var7 != null && var7.contains("64")) {
            return 64;
         }
      }

      return 32;
   }

   public static void showGuiMessage(String string1, String string2) {
      GuiMessage var2 = new GuiMessage(minecraft.currentScreen, string1, string2);
      minecraft.displayGuiScreen(var2);
   }

   public static int[] addIntToArray(int[] arr, int integer) {
      return addIntsToArray(arr, new int[]{integer});
   }

   public static int[] addIntsToArray(int[] arr1, int[] arr2) {
      if (arr1 != null && arr2 != null) {
         int var2 = arr1.length;
         int[] var4 = new int[var2 + arr2.length];
         System.arraycopy(arr1, 0, var4, 0, var2);
         System.arraycopy(arr2, 0, var4, var2, arr2.length);
         return var4;
      } else {
         throw new NullPointerException("The given array is NULL");
      }
   }

   public static DynamicTexture getMojangLogoTexture(DynamicTexture dynamicTexture) {
      try {
         InputStream var2 = getResourceStream(new ResourceLocation("textures/gui/title/mojang.png"));
         if (var2 == null) {
            return dynamicTexture;
         }

         BufferedImage var3 = ImageIO.read(var2);
         return var3 == null ? dynamicTexture : new DynamicTexture(var3);
      } catch (Exception var4) {
         Method252(var4.getClass().getName() + ": " + var4.getMessage());
         return dynamicTexture;
      }
   }

   public static void Method276(File file, String string) throws IOException {
      FileOutputStream var2 = new FileOutputStream(file);
      byte[] var3 = string.getBytes(StandardCharsets.US_ASCII);
      var2.write(var3);
      var2.close();
   }

   public static TextureMap getTextureMap() {
      return getMinecraft().getTextureMapBlocks();
   }

   public static boolean isDynamicLights() {
      return gameSettings.ofDynamicLights != 3;
   }

   public static boolean isDynamicLightsFast() {
      return gameSettings.ofDynamicLights == 1;
   }

   public static boolean isDynamicHandLight() {
      return isDynamicLights() && (!isShaders() || Shaders.isDynamicHandLight());
   }

   public static boolean isCustomEntityModels() {
      return gameSettings.ofCustomEntityModels;
   }

   public static boolean isCustomGuis() {
      return gameSettings.ofCustomGuis;
   }

   public static int getScreenshotSize() {
      return gameSettings.ofScreenshotSize;
   }

   public static int[] toPrimitive(Integer[] arr) {
      if (arr == null) {
         return null;
      }

      if (arr.length == 0) {
         return new int[0];
      }

      int[] var1 = new int[arr.length];

      for (int var2 = 0; var2 < var1.length; var2++) {
         var1[var2] = arr[var2];
      }

      return var1;
   }

   public static boolean isRenderRegions() {
      return gameSettings.ofRenderRegions;
   }

   public static boolean isVbo() {
      return OpenGlHelper.useVbo();
   }

   public static boolean aF() {
      return gameSettings.ofSmoothFps;
   }

   public static boolean Method277(URI uRI) {
      try {
         Desktop.getDesktop().browse(uRI);
         return true;
      } catch (Exception var2) {
         Method252("Error opening link: " + uRI);
         Method252(var2.getClass().getName() + ": " + var2.getMessage());
         return false;
      }
   }

   public static boolean isShowGlErrors() {
      return gameSettings.ofShowGlErrors;
   }

   public static String Method278(boolean[] arr, String string) {
      if (arr == null) {
         return "";
      }

      StringBuilder var2 = new StringBuilder(arr.length * 5);

      for (int var3 = 0; var3 < arr.length; var3++) {
         boolean var4 = arr[var3];
         if (var3 > 0) {
            var2.append(string);
         }

         var2.append(var4);
      }

      return var2.toString();
   }

   public static boolean isIntegratedServerRunning() {
      return minecraft.getIntegratedServer() != null && minecraft.isIntegratedServerRunning();
   }

   public static IntBuffer createDirectIntBuffer(int integer) {
      return GLAllocation.createDirectByteBuffer(integer << 2).asIntBuffer();
   }

   public static String getGlErrorString(int integer) {
      switch (integer) {
         case 0:
            return "No error";
         case 1280:
            return "Invalid enum";
         case 1281:
            return "Invalid value";
         case 1282:
            return "Invalid operation";
         case 1283:
            return "Stack overflow";
         case 1284:
            return "Stack underflow";
         case 1285:
            return "Out of memory";
         case 1286:
            return "Invalid framebuffer operation";
         default:
            return "Unknown";
      }
   }

   public static boolean isTrue(Boolean boolean1) {
      return boolean1 != null && boolean1;
   }

   public static boolean isQuadsToTriangles() {
      return isShaders() && !Shaders.Method459();
   }

   public static void checkNull(Object object, String string) throws NullPointerException {
      if (object == null) {
         throw new NullPointerException(string);
      }
   }

   public static String getNewRelease() {
      return newRelease;
   }

   public static void setNewRelease(String string) {
      newRelease = string;
   }

   public static boolean isNotify64BitJava() {
      return c;
   }

   public static void setNotify64BitJava(boolean b) {
      c = b;
   }

   static {
      newRelease = null;
      c = false;
   }
}
