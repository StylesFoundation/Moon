package net.optifine.shaders;

import com.google.common.base.Charsets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomColors;
import net.optifine.GlErrors;
import net.optifine.Lang;
import net.optifine.config.ConnectedParser;
import net.optifine.expr.IExpressionBool;
import net.optifine.render.GlAlphaState;
import net.optifine.render.GlBlendState;
import net.optifine.shaders.config.EnumShaderOption;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.shaders.config.MacroState;
import net.optifine.shaders.config.PropertyDefaultFastFancyOff;
import net.optifine.shaders.config.PropertyDefaultTrueFalse;
import net.optifine.shaders.config.RenderScale;
import net.optifine.shaders.config.ScreenShaderOptions;
import net.optifine.shaders.config.ShaderLine;
import net.optifine.shaders.config.ShaderOption;
import net.optifine.shaders.config.ShaderOptionProfile;
import net.optifine.shaders.config.ShaderOptionRest;
import net.optifine.shaders.config.ShaderPackParser;
import net.optifine.shaders.config.ShaderParser;
import net.optifine.shaders.config.ShaderProfile;
import net.optifine.shaders.uniform.CustomUniforms;
import net.optifine.shaders.uniform.ShaderUniform1f;
import net.optifine.shaders.uniform.ShaderUniform1i;
import net.optifine.shaders.uniform.ShaderUniform2i;
import net.optifine.shaders.uniform.ShaderUniform3f;
import net.optifine.shaders.uniform.ShaderUniform4f;
import net.optifine.shaders.uniform.ShaderUniform4i;
import net.optifine.shaders.uniform.ShaderUniformM4;
import net.optifine.shaders.uniform.ShaderUniforms;
import net.optifine.shaders.uniform.Smoother;
import net.optifine.texture.InternalFormat;
import net.optifine.texture.PixelFormat;
import net.optifine.texture.PixelType;
import net.optifine.texture.TextureType;
import net.optifine.util.EntityUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;
import net.optifine.util.TimedEvent;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBGeometryShader4;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL32;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector4f;

public class Shaders {
   static Minecraft mc;
   static EntityRenderer entityRenderer;
   public static boolean isInitializedOnce = false;
   public static boolean isShaderPackInitialized = false;
   public static ContextCapabilities capabilities;
   public static String glVersionString;
   public static String glVendorString;
   public static String glRendererString;
   public static boolean hasGlGenMipmap = false;
   public static int countResetDisplayLists = 0;
   private static int renderDisplayWidth = 0;
   private static int renderDisplayHeight = 0;
   public static int renderWidth = 0;
   public static int renderHeight = 0;
   public static boolean isRenderingWorld = false;
   public static boolean isRenderingSky = false;
   public static boolean isCompositeRendered = false;
   public static boolean isRenderingDfb = false;
   public static boolean isShadowPass = false;
   public static boolean isEntitiesGlowing = false;
   public static boolean isSleeping;
   private static boolean isRenderingFirstPersonHand;
   private static boolean isHandRenderedMain;
   private static boolean isHandRenderedOff;
   private static boolean skipRenderHandMain;
   private static boolean skipRenderHandOff;
   public static boolean renderItemKeepDepthMask = false;
   public static boolean itemToRenderMainTranslucent = false;
   public static boolean itemToRenderOffTranslucent = false;
   static float[] sunPosition = new float[4];
   static float[] moonPosition = new float[4];
   static float[] shadowLightPosition = new float[4];
   static float[] upPosition = new float[4];
   static float[] shadowLightPositionVector = new float[4];
   static float[] upPosModelView = new float[]{0.0F, 100.0F, 0.0F, 0.0F};
   static float[] sunPosModelView = new float[]{0.0F, 100.0F, 0.0F, 0.0F};
   static float[] moonPosModelView = new float[]{0.0F, -100.0F, 0.0F, 0.0F};
   private static final float[] tempMat = new float[16];
   static float clearColorR;
   static float clearColorG;
   static float clearColorB;
   static float skyColorR;
   static float skyColorG;
   static float skyColorB;
   static long worldTime = 0L;
   static long lastWorldTime = 0L;
   static long diffWorldTime = 0L;
   static float celestialAngle = 0.0F;
   static float sunAngle = 0.0F;
   static float shadowAngle = 0.0F;
   static int moonPhase = 0;
   static long systemTime = 0L;
   static long lastSystemTime = 0L;
   static long diffSystemTime = 0L;
   static int frameCounter = 0;
   static float frameTime = 0.0F;
   static float frameTimeCounter = 0.0F;
   static int systemTimeInt32 = 0;
   static float rainStrength = 0.0F;
   static float wetness = 0.0F;
   public static float wetnessHalfLife = 600.0F;
   public static float drynessHalfLife = 200.0F;
   public static float eyeBrightnessHalflife = 10.0F;
   static boolean usewetness = false;
   static int isEyeInWater = 0;
   static int eyeBrightness = 0;
   static float eyeBrightnessFadeX = 0.0F;
   static float eyeBrightnessFadeY = 0.0F;
   static float eyePosY = 0.0F;
   static float centerDepth = 0.0F;
   static float centerDepthSmooth = 0.0F;
   static float centerDepthSmoothHalflife = 1.0F;
   static boolean centerDepthSmoothEnabled = false;
   static int superSamplingLevel = 1;
   static float nightVision = 0.0F;
   static float blindness = 0.0F;
   static boolean lightmapEnabled = false;
   static boolean fogEnabled = true;
   public static int entityAttrib = 10;
   public static int midTexCoordAttrib = 11;
   public static int tangentAttrib = 12;
   public static boolean useEntityAttrib = false;
   public static boolean useMidTexCoordAttrib = false;
   public static boolean useTangentAttrib = false;
   public static boolean progUseEntityAttrib = false;
   public static boolean progUseMidTexCoordAttrib = false;
   public static boolean progUseTangentAttrib = false;
   private static boolean progArbGeometryShader4 = false;
   private static int progMaxVerticesOut = 3;
   private static boolean hasGeometryShaders = false;
   public static int atlasSizeX = 0;
   public static int atlasSizeY = 0;
   private static final ShaderUniforms shaderUniforms = new ShaderUniforms();
   public static ShaderUniform4f uniform_entityColor = shaderUniforms.make4f("entityColor");
   public static ShaderUniform1i uniform_entityId = shaderUniforms.make1i("entityId");
   public static ShaderUniform1i uniform_blockEntityId = shaderUniforms.make1i("blockEntityId");
   public static ShaderUniform1i uniform_texture = shaderUniforms.make1i("texture");
   public static ShaderUniform1i uniform_lightmap = shaderUniforms.make1i("lightmap");
   public static ShaderUniform1i uniform_normals = shaderUniforms.make1i("normals");
   public static ShaderUniform1i uniform_specular = shaderUniforms.make1i("specular");
   public static ShaderUniform1i uniform_shadow = shaderUniforms.make1i("shadow");
   public static ShaderUniform1i uniform_watershadow = shaderUniforms.make1i("watershadow");
   public static ShaderUniform1i uniform_shadowtex0 = shaderUniforms.make1i("shadowtex0");
   public static ShaderUniform1i uniform_shadowtex1 = shaderUniforms.make1i("shadowtex1");
   public static ShaderUniform1i uniform_depthtex0 = shaderUniforms.make1i("depthtex0");
   public static ShaderUniform1i uniform_depthtex1 = shaderUniforms.make1i("depthtex1");
   public static ShaderUniform1i uniform_shadowcolor = shaderUniforms.make1i("shadowcolor");
   public static ShaderUniform1i uniform_shadowcolor0 = shaderUniforms.make1i("shadowcolor0");
   public static ShaderUniform1i uniform_shadowcolor1 = shaderUniforms.make1i("shadowcolor1");
   public static ShaderUniform1i uniform_noisetex = shaderUniforms.make1i("noisetex");
   public static ShaderUniform1i uniform_gcolor = shaderUniforms.make1i("gcolor");
   public static ShaderUniform1i uniform_gdepth = shaderUniforms.make1i("gdepth");
   public static ShaderUniform1i uniform_gnormal = shaderUniforms.make1i("gnormal");
   public static ShaderUniform1i uniform_composite = shaderUniforms.make1i("composite");
   public static ShaderUniform1i uniform_gaux1 = shaderUniforms.make1i("gaux1");
   public static ShaderUniform1i uniform_gaux2 = shaderUniforms.make1i("gaux2");
   public static ShaderUniform1i uniform_gaux3 = shaderUniforms.make1i("gaux3");
   public static ShaderUniform1i uniform_gaux4 = shaderUniforms.make1i("gaux4");
   public static ShaderUniform1i uniform_colortex0 = shaderUniforms.make1i("colortex0");
   public static ShaderUniform1i uniform_colortex1 = shaderUniforms.make1i("colortex1");
   public static ShaderUniform1i uniform_colortex2 = shaderUniforms.make1i("colortex2");
   public static ShaderUniform1i uniform_colortex3 = shaderUniforms.make1i("colortex3");
   public static ShaderUniform1i uniform_colortex4 = shaderUniforms.make1i("colortex4");
   public static ShaderUniform1i uniform_colortex5 = shaderUniforms.make1i("colortex5");
   public static ShaderUniform1i uniform_colortex6 = shaderUniforms.make1i("colortex6");
   public static ShaderUniform1i uniform_colortex7 = shaderUniforms.make1i("colortex7");
   public static ShaderUniform1i uniform_gdepthtex = shaderUniforms.make1i("gdepthtex");
   public static ShaderUniform1i uniform_depthtex2 = shaderUniforms.make1i("depthtex2");
   public static ShaderUniform1i uniform_tex = shaderUniforms.make1i("tex");
   public static ShaderUniform1i uniform_heldItemId = shaderUniforms.make1i("heldItemId");
   public static ShaderUniform1i uniform_heldBlockLightValue = shaderUniforms.make1i("heldBlockLightValue");
   public static ShaderUniform1i uniform_heldItemId2 = shaderUniforms.make1i("heldItemId2");
   public static ShaderUniform1i uniform_heldBlockLightValue2 = shaderUniforms.make1i("heldBlockLightValue2");
   public static ShaderUniform1i uniform_fogMode = shaderUniforms.make1i("fogMode");
   public static ShaderUniform1f uniform_fogDensity = shaderUniforms.make1f("fogDensity");
   public static ShaderUniform3f uniform_fogColor = shaderUniforms.make3f("fogColor");
   public static ShaderUniform3f uniform_skyColor = shaderUniforms.make3f("skyColor");
   public static ShaderUniform1i uniform_worldTime = shaderUniforms.make1i("worldTime");
   public static ShaderUniform1i uniform_worldDay = shaderUniforms.make1i("worldDay");
   public static ShaderUniform1i uniform_moonPhase = shaderUniforms.make1i("moonPhase");
   public static ShaderUniform1i uniform_frameCounter = shaderUniforms.make1i("frameCounter");
   public static ShaderUniform1f uniform_frameTime = shaderUniforms.make1f("frameTime");
   public static ShaderUniform1f uniform_frameTimeCounter = shaderUniforms.make1f("frameTimeCounter");
   public static ShaderUniform1f uniform_sunAngle = shaderUniforms.make1f("sunAngle");
   public static ShaderUniform1f uniform_shadowAngle = shaderUniforms.make1f("shadowAngle");
   public static ShaderUniform1f uniform_rainStrength = shaderUniforms.make1f("rainStrength");
   public static ShaderUniform1f uniform_aspectRatio = shaderUniforms.make1f("aspectRatio");
   public static ShaderUniform1f uniform_viewWidth = shaderUniforms.make1f("viewWidth");
   public static ShaderUniform1f uniform_viewHeight = shaderUniforms.make1f("viewHeight");
   public static ShaderUniform1f uniform_near = shaderUniforms.make1f("near");
   public static ShaderUniform1f uniform_far = shaderUniforms.make1f("far");
   public static ShaderUniform3f uniform_sunPosition = shaderUniforms.make3f("sunPosition");
   public static ShaderUniform3f uniform_moonPosition = shaderUniforms.make3f("moonPosition");
   public static ShaderUniform3f uniform_shadowLightPosition = shaderUniforms.make3f("shadowLightPosition");
   public static ShaderUniform3f uniform_upPosition = shaderUniforms.make3f("upPosition");
   public static ShaderUniform3f uniform_previousCameraPosition = shaderUniforms.make3f("previousCameraPosition");
   public static ShaderUniform3f uniform_cameraPosition = shaderUniforms.make3f("cameraPosition");
   public static ShaderUniformM4 uniform_gbufferModelView = shaderUniforms.makeM4("gbufferModelView");
   public static ShaderUniformM4 uniform_gbufferModelViewInverse = shaderUniforms.makeM4("gbufferModelViewInverse");
   public static ShaderUniformM4 uniform_gbufferPreviousProjection = shaderUniforms.makeM4("gbufferPreviousProjection");
   public static ShaderUniformM4 uniform_gbufferProjection = shaderUniforms.makeM4("gbufferProjection");
   public static ShaderUniformM4 uniform_gbufferProjectionInverse = shaderUniforms.makeM4("gbufferProjectionInverse");
   public static ShaderUniformM4 uniform_gbufferPreviousModelView = shaderUniforms.makeM4("gbufferPreviousModelView");
   public static ShaderUniformM4 uniform_shadowProjection = shaderUniforms.makeM4("shadowProjection");
   public static ShaderUniformM4 uniform_shadowProjectionInverse = shaderUniforms.makeM4("shadowProjectionInverse");
   public static ShaderUniformM4 uniform_shadowModelView = shaderUniforms.makeM4("shadowModelView");
   public static ShaderUniformM4 uniform_shadowModelViewInverse = shaderUniforms.makeM4("shadowModelViewInverse");
   public static ShaderUniform1f uniform_wetness = shaderUniforms.make1f("wetness");
   public static ShaderUniform1f uniform_eyeAltitude = shaderUniforms.make1f("eyeAltitude");
   public static ShaderUniform2i uniform_eyeBrightness = shaderUniforms.make2i("eyeBrightness");
   public static ShaderUniform2i uniform_eyeBrightnessSmooth = shaderUniforms.make2i("eyeBrightnessSmooth");
   public static ShaderUniform2i uniform_terrainTextureSize = shaderUniforms.make2i("terrainTextureSize");
   public static ShaderUniform1i uniform_terrainIconSize = shaderUniforms.make1i("terrainIconSize");
   public static ShaderUniform1i uniform_isEyeInWater = shaderUniforms.make1i("isEyeInWater");
   public static ShaderUniform1f uniform_nightVision = shaderUniforms.make1f("nightVision");
   public static ShaderUniform1f uniform_blindness = shaderUniforms.make1f("blindness");
   public static ShaderUniform1f uniform_screenBrightness = shaderUniforms.make1f("screenBrightness");
   public static ShaderUniform1i uniform_hideGUI = shaderUniforms.make1i("hideGUI");
   public static ShaderUniform1f uniform_centerDepthSmooth = shaderUniforms.make1f("centerDepthSmooth");
   public static ShaderUniform2i uniform_atlasSize = shaderUniforms.make2i("atlasSize");
   public static ShaderUniform4i uniform_blendFunc = shaderUniforms.make4i("blendFunc");
   public static ShaderUniform1i uniform_instanceId = shaderUniforms.make1i("instanceId");
   static double previousCameraPositionX;
   static double previousCameraPositionY;
   static double previousCameraPositionZ;
   static double cameraPositionX;
   static double cameraPositionY;
   static double cameraPositionZ;
   static int cameraOffsetX;
   static int cameraOffsetZ;
   static int shadowPassInterval = 0;
   public static boolean needResizeShadow = false;
   static int shadowMapWidth = 1024;
   static int shadowMapHeight = 1024;
   static int spShadowMapWidth = 1024;
   static int spShadowMapHeight = 1024;
   static float shadowMapFOV = 90.0F;
   static float shadowMapHalfPlane = 160.0F;
   static boolean shadowMapIsOrtho = true;
   static float shadowDistanceRenderMul = -1.0F;
   static int shadowPassCounter = 0;
   static int preShadowPassThirdPersonView;
   public static boolean shouldSkipDefaultShadow = false;
   static boolean waterShadowEnabled = false;
   static int usedColorBuffers = 0;
   static int usedDepthBuffers = 0;
   static int usedShadowColorBuffers = 0;
   static int usedShadowDepthBuffers = 0;
   static int usedColorAttachs = 0;
   static int usedDrawBuffers = 0;
   static int dfb = 0;
   static int sfb = 0;
   private static final int[] gbuffersFormat = new int[8];
   public static boolean[] gbuffersClear = new boolean[8];
   public static Vector4f[] gbuffersClearColor = new Vector4f[8];
   private static final Programs programs = new Programs();
   public static final Program ProgramNone = programs.getProgramNone();
   public static final Program ProgramShadow = programs.makeShadow("shadow", ProgramNone);
   public static final Program ProgramShadowSolid = programs.makeShadow("shadow_solid", ProgramShadow);
   public static final Program ProgramShadowCutout = programs.makeShadow("shadow_cutout", ProgramShadow);
   public static final Program ProgramBasic = programs.makeGbuffers("gbuffers_basic", ProgramNone);
   public static final Program ProgramTextured = programs.makeGbuffers("gbuffers_textured", ProgramBasic);
   public static final Program ProgramTexturedLit = programs.makeGbuffers("gbuffers_textured_lit", ProgramTextured);
   public static final Program ProgramSkyBasic = programs.makeGbuffers("gbuffers_skybasic", ProgramBasic);
   public static final Program ProgramSkyTextured = programs.makeGbuffers("gbuffers_skytextured", ProgramTextured);
   public static final Program ProgramClouds = programs.makeGbuffers("gbuffers_clouds", ProgramTextured);
   public static final Program ProgramTerrain = programs.makeGbuffers("gbuffers_terrain", ProgramTexturedLit);
   public static final Program ProgramTerrainSolid = programs.makeGbuffers("gbuffers_terrain_solid", ProgramTerrain);
   public static final Program ProgramTerrainCutoutMip = programs.makeGbuffers("gbuffers_terrain_cutout_mip", ProgramTerrain);
   public static final Program ProgramTerrainCutout = programs.makeGbuffers("gbuffers_terrain_cutout", ProgramTerrain);
   public static final Program ProgramDamagedBlock = programs.makeGbuffers("gbuffers_damagedblock", ProgramTerrain);
   public static final Program ProgramBlock = programs.makeGbuffers("gbuffers_block", ProgramTerrain);
   public static final Program ProgramBeaconBeam = programs.makeGbuffers("gbuffers_beaconbeam", ProgramTextured);
   public static final Program ProgramItem = programs.makeGbuffers("gbuffers_item", ProgramTexturedLit);
   public static final Program ProgramEntities = programs.makeGbuffers("gbuffers_entities", ProgramTexturedLit);
   public static final Program ProgramEntitiesGlowing = programs.makeGbuffers("gbuffers_entities_glowing", ProgramEntities);
   public static final Program ProgramArmorGlint = programs.makeGbuffers("gbuffers_armor_glint", ProgramTextured);
   public static final Program ProgramSpiderEyes = programs.makeGbuffers("gbuffers_spidereyes", ProgramTextured);
   public static final Program ProgramHand = programs.makeGbuffers("gbuffers_hand", ProgramTexturedLit);
   public static final Program ProgramWeather = programs.makeGbuffers("gbuffers_weather", ProgramTexturedLit);
   public static final Program ProgramDeferredPre = programs.makeVirtual("deferred_pre");
   public static final Program[] ProgramsDeferred = programs.makeDeferreds("deferred", 16);
   public static final Program ProgramDeferred = ProgramsDeferred[0];
   public static final Program ProgramWater = programs.makeGbuffers("gbuffers_water", ProgramTerrain);
   public static final Program ProgramHandWater = programs.makeGbuffers("gbuffers_hand_water", ProgramHand);
   public static final Program ProgramCompositePre = programs.makeVirtual("composite_pre");
   public static final Program[] ProgramsComposite = programs.makeComposites("composite", 16);
   public static final Program ProgramComposite = ProgramsComposite[0];
   public static final Program ProgramFinal = programs.makeComposite("final");
   public static final int ProgramCount = programs.getCount();
   public static final Program[] ProgramsAll = programs.getPrograms();
   public static Program activeProgram = ProgramNone;
   public static int activeProgramID = 0;
   private static final ProgramStack programStack = new ProgramStack();
   private static boolean hasDeferredPrograms = false;
   static IntBuffer activeDrawBuffers = null;
   private static int activeCompositeMipmapSetting = 0;
   public static Properties loadedShaders = null;
   public static Properties shadersConfig = null;
   public static ITextureObject defaultTexture = null;
   public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
   public static boolean[] shadowMipmapEnabled = new boolean[2];
   public static boolean[] shadowFilterNearest = new boolean[2];
   public static boolean[] shadowColorMipmapEnabled = new boolean[8];
   public static boolean[] shadowColorFilterNearest = new boolean[8];
   public static boolean configTweakBlockDamage = false;
   public static boolean configCloudShadow = false;
   public static float configHandDepthMul = 0.125F;
   public static float configRenderResMul = 1.0F;
   public static float configShadowResMul = 1.0F;
   public static int configTexMinFilB = 0;
   public static int configTexMinFilN = 0;
   public static int configTexMinFilS = 0;
   public static int configTexMagFilB = 0;
   public static int configTexMagFilN = 0;
   public static int configTexMagFilS = 0;
   public static boolean configShadowClipFrustrum = true;
   public static boolean configNormalMap = true;
   public static boolean configSpecularMap = true;
   public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
   public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
   public static int configAntialiasingLevel = 0;
   public static final String[] texMinFilDesc = new String[]{"Nearest", "Nearest-Nearest", "Nearest-Linear"};
   public static final String[] texMagFilDesc = new String[]{"Nearest", "Linear"};
   public static final int[] texMinFilValue = new int[]{9728, 9984, 9986};
   public static final int[] texMagFilValue = new int[]{9728, 9729};
   private static IShaderPack shaderPack = null;
   public static boolean shaderPackLoaded = false;
   public static String currentShaderName;
   public static final File shaderPacksDir = new File(Minecraft.getMinecraft().mcDataDir, "shaderpacks");
   static File configFile = new File(Minecraft.getMinecraft().mcDataDir, "optionsshaders.txt");
   private static ShaderOption[] shaderPackOptions = null;
   private static Set<String> shaderPackOptionSliders = null;
   static ShaderProfile[] shaderPackProfiles = null;
   static Map<String, ScreenShaderOptions> shaderPackGuiScreens = null;
   static Map<String, IExpressionBool> shaderPackProgramConditions = new HashMap<>();
   public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
   public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
   public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
   public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
   public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
   public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
   public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
   public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
   public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
   public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
   public static PropertyDefaultTrueFalse shaderPackRainDepth = new PropertyDefaultTrueFalse("rain.depth", "Rain Depth", 0);
   public static PropertyDefaultTrueFalse shaderPackBeaconBeamDepth = new PropertyDefaultTrueFalse("beacon.beam.depth", "Rain Depth", 0);
   public static PropertyDefaultTrueFalse shaderPackSeparateAo = new PropertyDefaultTrueFalse("separateAo", "Separate AO", 0);
   public static PropertyDefaultTrueFalse shaderPackFrustumCulling = new PropertyDefaultTrueFalse("frustum.culling", "Frustum Culling", 0);
   private static Map<String, String> shaderPackResources = new HashMap<>();
   private static World currentWorld = null;
   private static final List<Integer> shaderPackDimensions = new ArrayList<>();
   private static ICustomTexture[] customTexturesGbuffers = null;
   private static ICustomTexture[] customTexturesComposite = null;
   private static ICustomTexture[] customTexturesDeferred = null;
   private static String noiseTexturePath = null;
   private static CustomUniforms customUniforms = null;
   private static final String[] STAGE_NAMES = new String[]{"gbuffers", "composite", "deferred"};
   public static final boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
   public static float blockLightLevel05 = 0.5F;
   public static float blockLightLevel06 = 0.6F;
   public static float blockLightLevel08 = 0.8F;
   public static float aoLevel = -1.0F;
   public static float sunPathRotation = 0.0F;
   public static int fogMode = 0;
   public static float fogDensity = 0.0F;
   public static float fogColorR;
   public static float fogColorG;
   public static float fogColorB;
   public static float shadowIntervalSize = 2.0F;
   public static int terrainIconSize = 16;
   public static int[] terrainTextureSize = new int[2];
   private static ICustomTexture noiseTexture;
   private static boolean noiseTextureEnabled = false;
   private static int noiseTextureResolution = 256;
   static final int[] colorTextureImageUnit = new int[]{0, 1, 2, 3, 7, 8, 9, 10};
   private static final int bigBufferSize = (285 + 8 * ProgramCount) * 4;
   private static final ByteBuffer bigBuffer = BufferUtils.createByteBuffer(bigBufferSize).limit(0);
   static final float[] faProjection = new float[16];
   static final float[] faProjectionInverse = new float[16];
   static final float[] faModelView = new float[16];
   static final float[] faModelViewInverse = new float[16];
   static final float[] faShadowProjection = new float[16];
   static final float[] faShadowProjectionInverse = new float[16];
   static final float[] faShadowModelView = new float[16];
   static final float[] faShadowModelViewInverse = new float[16];
   static final FloatBuffer projection = nextFloatBuffer(16);
   static final FloatBuffer projectionInverse = nextFloatBuffer(16);
   static final FloatBuffer modelView = nextFloatBuffer(16);
   static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
   static final FloatBuffer shadowProjection = nextFloatBuffer(16);
   static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
   static final FloatBuffer shadowModelView = nextFloatBuffer(16);
   static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
   static final FloatBuffer previousProjection = nextFloatBuffer(16);
   static final FloatBuffer previousModelView = nextFloatBuffer(16);
   static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
   static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
   static final IntBuffer dfbColorTextures = nextIntBuffer(16);
   static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
   static final IntBuffer sfbColorTextures = nextIntBuffer(8);
   static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
   static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
   static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
   static final IntBuffer drawBuffersNone = nextIntBuffer(8).limit(0);
   static final IntBuffer drawBuffersColorAtt0 = nextIntBuffer(8).put(36064).position(0).limit(1);
   static final FlipTextures dfbColorTexturesFlip = new FlipTextures(dfbColorTextures, 8);
   static Map<Block, Integer> mapBlockToEntityData;
   private static final String[] formatNames = new String[]{
      "R8",
      "RG8",
      "RGB8",
      "RGBA8",
      "R8_SNORM",
      "RG8_SNORM",
      "RGB8_SNORM",
      "RGBA8_SNORM",
      "R16",
      "RG16",
      "RGB16",
      "RGBA16",
      "R16_SNORM",
      "RG16_SNORM",
      "RGB16_SNORM",
      "RGBA16_SNORM",
      "R16F",
      "RG16F",
      "RGB16F",
      "RGBA16F",
      "R32F",
      "RG32F",
      "RGB32F",
      "RGBA32F",
      "R32I",
      "RG32I",
      "RGB32I",
      "RGBA32I",
      "R32UI",
      "RG32UI",
      "RGB32UI",
      "RGBA32UI",
      "R3_G3_B2",
      "RGB5_A1",
      "RGB10_A2",
      "R11F_G11F_B10F",
      "RGB9_E5"
   };
   private static final int[] formatIds = new int[]{
      33321,
      33323,
      32849,
      32856,
      36756,
      36757,
      36758,
      36759,
      33322,
      33324,
      32852,
      32859,
      36760,
      36761,
      36762,
      36763,
      33325,
      33327,
      34843,
      34842,
      33326,
      33328,
      34837,
      34836,
      33333,
      33339,
      36227,
      36226,
      33334,
      33340,
      36209,
      36208,
      10768,
      32855,
      32857,
      35898,
      35901
   };
   private static final Pattern fj = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*(-?\\d+)\\s*");
   public static int[] entityData = new int[32];
   public static int entityDataIndex = 0;

   private static ByteBuffer nextByteBuffer(int integer) {
      ByteBuffer var1 = bigBuffer;
      int var2 = var1.limit();
      var1.position(var2).limit(var2 + integer);
      return var1.slice();
   }

   public static IntBuffer nextIntBuffer(int integer) {
      ByteBuffer var1 = bigBuffer;
      int var2 = var1.limit();
      var1.position(var2).limit(var2 + integer * 4);
      return var1.asIntBuffer();
   }

   private static FloatBuffer nextFloatBuffer(int integer) {
      ByteBuffer var1 = bigBuffer;
      int var2 = var1.limit();
      var1.position(var2).limit(var2 + integer * 4);
      return var1.asFloatBuffer();
   }

   private static IntBuffer[] nextIntBufferArray(int integer1, int integer2) {
      IntBuffer[] var5 = new IntBuffer[integer1];

      for (int var6 = 0; var6 < integer1; var6++) {
         var5[var6] = nextIntBuffer(integer2);
      }

      return var5;
   }

   public static void loadConfig() {
      SMCLog.info("Load shaders configuration.");

      try {
         if (!shaderPacksDir.exists()) {
            shaderPacksDir.mkdir();
         }
      } catch (Exception var13) {
         SMCLog.severe("Failed to open the shaderpacks directory: " + shaderPacksDir);
      }

      shadersConfig = new PropertiesOrdered();
      shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
      if (configFile.exists()) {
         try {
            FileReader var3 = new FileReader(configFile);
            shadersConfig.load(var3);
            var3.close();
         } catch (Exception var12) {
         }
      }

      if (!configFile.exists()) {
         try {
            storeConfig();
         } catch (Exception var11) {
         }
      }

      for (EnumShaderOption var7 : EnumShaderOption.values()) {
         String var8 = var7.getPropertyKey();
         String var9 = var7.getValueDefault();
         String var10 = shadersConfig.getProperty(var8, var9);
         setEnumShaderOption(var7, var10);
      }

      loadShaderPack();
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   private static void setEnumShaderOption(EnumShaderOption enumShaderOption, String string) {
      if (string == null) {
         string = enumShaderOption.getValueDefault();
      }

      switch (enumShaderOption) {
         case ANTIALIASING:
            configAntialiasingLevel = Config.parseInt(string, 0);
            break;
         case NORMAL_MAP:
            configNormalMap = Config.parseBoolean(string, true);
            break;
         case SPECULAR_MAP:
            configSpecularMap = Config.parseBoolean(string, true);
            break;
         case RENDER_RES_MUL:
            configRenderResMul = Config.parseFloat(string, 1.0F);
            break;
         case SHADOW_RES_MUL:
            configShadowResMul = Config.parseFloat(string, 1.0F);
            break;
         case HAND_DEPTH_MUL:
            configHandDepthMul = Config.parseFloat(string, 0.125F);
            break;
         case CLOUD_SHADOW:
            configCloudShadow = Config.parseBoolean(string, true);
            break;
         case OLD_HAND_LIGHT:
            configOldHandLight.setPropertyValue(string);
            break;
         case OLD_LIGHTING:
            configOldLighting.setPropertyValue(string);
            break;
         case SHADER_PACK:
            currentShaderName = string;
            break;
         case TWEAK_BLOCK_DAMAGE:
            configTweakBlockDamage = Config.parseBoolean(string, true);
            break;
         case SHADOW_CLIP_FRUSTRUM:
            configShadowClipFrustrum = Config.parseBoolean(string, true);
            break;
         case TEX_MIN_FIL_B:
            configTexMinFilB = Config.parseInt(string, 0);
            break;
         case TEX_MIN_FIL_N:
            configTexMinFilN = Config.parseInt(string, 0);
            break;
         case TEX_MIN_FIL_S:
            configTexMinFilS = Config.parseInt(string, 0);
            break;
         case TEX_MAG_FIL_B:
         case TEX_MAG_FIL_S:
         case TEX_MAG_FIL_N:
            configTexMagFilB = Config.parseInt(string, 0);
            break;
         default:
            throw new IllegalArgumentException("Unknown option: " + enumShaderOption);
      }
   }

   public static void storeConfig() {
      SMCLog.info("Save shaders configuration.");
      if (shadersConfig == null) {
         shadersConfig = new PropertiesOrdered();
      }

      for (EnumShaderOption var7 : EnumShaderOption.values()) {
         String var8 = var7.getPropertyKey();
         String var9 = getEnumShaderOption(var7);
         shadersConfig.setProperty(var8, var9);
      }

      try {
         FileWriter var11 = new FileWriter(configFile);
         shadersConfig.store(var11, null);
         var11.close();
      } catch (Exception var10) {
         SMCLog.severe("Error saving configuration: " + var10.getClass().getName() + ": " + var10.getMessage());
      }
   }

   public static String getEnumShaderOption(EnumShaderOption enumShaderOption) {
      switch (enumShaderOption) {
         case ANTIALIASING:
            return Integer.toString(configAntialiasingLevel);
         case NORMAL_MAP:
            return Boolean.toString(configNormalMap);
         case SPECULAR_MAP:
            return Boolean.toString(configSpecularMap);
         case RENDER_RES_MUL:
            return Float.toString(configRenderResMul);
         case SHADOW_RES_MUL:
            return Float.toString(configShadowResMul);
         case HAND_DEPTH_MUL:
            return Float.toString(configHandDepthMul);
         case CLOUD_SHADOW:
            return Boolean.toString(configCloudShadow);
         case OLD_HAND_LIGHT:
            return configOldHandLight.getPropertyValue();
         case OLD_LIGHTING:
            return configOldLighting.getPropertyValue();
         case SHADER_PACK:
            return currentShaderName;
         case TWEAK_BLOCK_DAMAGE:
            return Boolean.toString(configTweakBlockDamage);
         case SHADOW_CLIP_FRUSTRUM:
            return Boolean.toString(configShadowClipFrustrum);
         case TEX_MIN_FIL_B:
            return Integer.toString(configTexMinFilB);
         case TEX_MIN_FIL_N:
            return Integer.toString(configTexMinFilN);
         case TEX_MIN_FIL_S:
            return Integer.toString(configTexMinFilS);
         case TEX_MAG_FIL_B:
         case TEX_MAG_FIL_S:
         case TEX_MAG_FIL_N:
            return Integer.toString(configTexMagFilB);
         default:
            throw new IllegalArgumentException("Unknown option: " + enumShaderOption);
      }
   }

   public static void setShaderPack(String string) {
      currentShaderName = string;
      shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), string);
      loadShaderPack();
   }

   public static void loadShaderPack() {
      boolean var3 = shaderPackLoaded;
      boolean var4 = isOldLighting();
      if (mc.renderGlobal != null) {
         mc.renderGlobal.pauseChunkUpdates();
      }

      shaderPackLoaded = false;
      if (shaderPack != null) {
         shaderPack.close();
         shaderPack = null;
         shaderPackResources.clear();
         shaderPackDimensions.clear();
         shaderPackOptions = null;
         shaderPackOptionSliders = null;
         shaderPackProfiles = null;
         shaderPackGuiScreens = null;
         shaderPackProgramConditions.clear();
         shaderPackClouds.resetValue();
         shaderPackOldHandLight.resetValue();
         shaderPackDynamicHandLight.resetValue();
         shaderPackOldLighting.resetValue();
         resetCustomTextures();
         noiseTexturePath = null;
      }

      boolean var5 = false;
      if (Config.isAntialiasing()) {
         SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
         var5 = true;
      }

      if (Config.isAnisotropicFiltering()) {
         SMCLog.info("Shaders can not be loaded, Anisotropic Filtering is enabled: " + Config.getAnisotropicFilterLevel() + "x");
         var5 = true;
      }

      String var6 = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "(internal)");
      if (!var5) {
         shaderPack = getShaderPack(var6);
         shaderPackLoaded = shaderPack != null;
      }

      if (shaderPackLoaded) {
         SMCLog.info("Loaded shaderpack: " + getShaderPackName());
      } else {
         SMCLog.info("No shaderpack loaded.");
         shaderPack = new ShaderPackNone();
      }

      if (saveFinalShaders) {
         clearDirectory(new File(shaderPacksDir, "debug"));
      }

      loadShaderPackResources();
      loadShaderPackDimensions();
      shaderPackOptions = loadShaderPackOptions();
      loadShaderPackProperties();
      boolean var7 = shaderPackLoaded != var3;
      boolean var8 = isOldLighting() != var4;
      if (var7 || var8) {
         DefaultVertexFormats.updateVertexFormats();
         updateBlockLightLevel();
      }

      if (mc.getResourcePackRepository() != null) {
         CustomBlockLayers.update();
      }

      if (mc.renderGlobal != null) {
         mc.renderGlobal.resumeChunkUpdates();
      }

      if ((var7 || var8) && mc.getResourceManager() != null) {
         mc.scheduleResourcesRefresh();
      }
   }

   public static IShaderPack getShaderPack(String string) {
      if (string == null) {
         return null;
      }

      string = string.trim();
      if (string.isEmpty() || string.equals("OFF")) {
         return null;
      }

      if (string.equals("(internal)")) {
         return new ShaderPackDefault();
      }

      try {
         File var4 = new File(shaderPacksDir, string);
         return var4.isDirectory()
            ? new ShaderPackFolder(string, var4)
            : (var4.isFile() && string.toLowerCase().endsWith(".zip") ? new ShaderPackZip(string, var4) : null);
      } catch (Exception var5) {
         var5.printStackTrace();
         return null;
      }
   }

   public static IShaderPack getShaderPack() {
      return shaderPack;
   }

   private static void loadShaderPackDimensions() {
      shaderPackDimensions.clear();

      for (int var3 = -128; var3 <= 128; var3++) {
         String var4 = "/shaders/world" + var3;
         if (shaderPack.hasDirectory(var4)) {
            shaderPackDimensions.add(var3);
         }
      }

      if (shaderPackDimensions.size() > 0) {
         Config.Method251("[Shaders] Worlds: " + Config.arrayToString((Integer[])shaderPackDimensions.toArray(new Integer[0])));
      }
   }

   private static void loadShaderPackProperties() {
      shaderPackClouds.resetValue();
      shaderPackOldHandLight.resetValue();
      shaderPackDynamicHandLight.resetValue();
      shaderPackOldLighting.resetValue();
      shaderPackShadowTranslucent.resetValue();
      shaderPackUnderwaterOverlay.resetValue();
      shaderPackSun.resetValue();
      shaderPackMoon.resetValue();
      shaderPackVignette.resetValue();
      shaderPackBackFaceSolid.resetValue();
      shaderPackBackFaceCutout.resetValue();
      shaderPackBackFaceCutoutMipped.resetValue();
      shaderPackBackFaceTranslucent.resetValue();
      shaderPackRainDepth.resetValue();
      shaderPackBeaconBeamDepth.resetValue();
      shaderPackSeparateAo.resetValue();
      shaderPackFrustumCulling.resetValue();
      BlockAliases.reset();
      ItemAliases.reset();
      EntityAliases.reset();
      customUniforms = null;

      for (Program var6 : ProgramsAll) {
         var6.resetProperties();
      }

      if (shaderPack != null) {
         ItemAliases.Method550(shaderPack);
         EntityAliases.Method989(shaderPack);
         String var8 = "/shaders/shaders.properties";

         try {
            InputStream var9 = shaderPack.getResourceAsStream(var8);
            if (var9 == null) {
               return;
            }

            var9 = MacroProcessor.process(var9, var8);
            PropertiesOrdered var11 = new PropertiesOrdered();
            var11.load(var9);
            var9.close();
            shaderPackClouds.loadFrom(var11);
            shaderPackOldHandLight.loadFrom(var11);
            shaderPackDynamicHandLight.loadFrom(var11);
            shaderPackOldLighting.loadFrom(var11);
            shaderPackShadowTranslucent.loadFrom(var11);
            shaderPackUnderwaterOverlay.loadFrom(var11);
            shaderPackSun.loadFrom(var11);
            shaderPackVignette.loadFrom(var11);
            shaderPackMoon.loadFrom(var11);
            shaderPackBackFaceSolid.loadFrom(var11);
            shaderPackBackFaceCutout.loadFrom(var11);
            shaderPackBackFaceCutoutMipped.loadFrom(var11);
            shaderPackBackFaceTranslucent.loadFrom(var11);
            shaderPackRainDepth.loadFrom(var11);
            shaderPackBeaconBeamDepth.loadFrom(var11);
            shaderPackSeparateAo.loadFrom(var11);
            shaderPackFrustumCulling.loadFrom(var11);
            shaderPackOptionSliders = ShaderPackParser.parseOptionSliders(var11, shaderPackOptions);
            shaderPackProfiles = ShaderPackParser.parseProfiles(var11, shaderPackOptions);
            shaderPackGuiScreens = ShaderPackParser.parseGuiScreens(var11, shaderPackProfiles, shaderPackOptions);
            shaderPackProgramConditions = ShaderPackParser.parseProgramConditions(var11, shaderPackOptions);
            customTexturesGbuffers = loadCustomTextures(var11, 0);
            customTexturesComposite = loadCustomTextures(var11, 1);
            customTexturesDeferred = loadCustomTextures(var11, 2);
            noiseTexturePath = var11.getProperty("texture.noise");
            if (noiseTexturePath != null) {
               noiseTextureEnabled = true;
            }

            customUniforms = ShaderPackParser.parseCustomUniforms(var11);
            ShaderPackParser.parseAlphaStates(var11);
            ShaderPackParser.parseBlendStates(var11);
            ShaderPackParser.parseRenderScales(var11);
            ShaderPackParser.parseBuffersFlip(var11);
         } catch (IOException var7) {
            Config.Method252("[Shaders] Error reading: " + "/shaders/shaders.properties");
         }
      }
   }

   private static ICustomTexture[] loadCustomTextures(Properties properties, int integer) {
      String var5 = "texture." + STAGE_NAMES[integer] + ".";
      Set var6 = properties.keySet();
      ArrayList<ICustomTexture> var7 = new ArrayList<>();

      for (Object var9Obj : var6) {
         String var9 = (String)var9Obj;
         if (var9.startsWith(var5)) {
            String var10 = StrUtils.removeSuffix(StrUtils.removePrefix(var9, var5), new String[]{".0", ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9"});
            String var11 = properties.getProperty(var9).trim();
            int var12 = getTextureIndex(integer, var10);
            if (var12 < 0) {
               SMCLog.warning("Invalid texture name: " + var9);
            } else {
               ICustomTexture var13 = loadCustomTexture(var12, var11);
               if (var13 != null) {
                  SMCLog.info("Custom texture: " + var9 + " = " + var11);
                  var7.add(var13);
               }
            }
         }
      }

      return var7.size() == 0 ? null : var7.toArray(new ICustomTexture[0]);
   }

   private static ICustomTexture loadCustomTexture(int integer, String string) {
      if (string == null) {
         return null;
      }

      string = string.trim();
      return string.indexOf(58) >= 0
         ? loadCustomTextureLocation(integer, string)
         : (string.indexOf(32) >= 0 ? loadCustomTextureRaw(integer, string) : loadCustomTextureShaders(integer, string));
   }

   private static ICustomTexture loadCustomTextureLocation(int integer, String string) {
      String var5 = string.trim();
      byte var6 = 0;
      if (var5.startsWith("minecraft:textures/")) {
         var5 = StrUtils.addSuffixCheck(var5, ".png");
         if (var5.endsWith("_n.png")) {
            var5 = StrUtils.replaceSuffix(var5, "_n.png", ".png");
            var6 = 1;
         } else if (var5.endsWith("_s.png")) {
            var5 = StrUtils.replaceSuffix(var5, "_s.png", ".png");
            var6 = 2;
         }
      }

      ResourceLocation var7 = new ResourceLocation(var5);
      return new CustomTextureLocation(integer, var7, var6);
   }

   private static ICustomTexture loadCustomTextureRaw(int integer, String string) {
      ConnectedParser var5 = new ConnectedParser("Shaders");
      String[] var6 = Config.tokenize(string, " ");
      ArrayDeque var7 = new ArrayDeque<>(Arrays.asList(var6));
      String var8 = (String)var7.poll();
      TextureType var9 = (TextureType)var5.parseEnum((String)var7.poll(), TextureType.values(), "texture type");
      if (var9 == null) {
         SMCLog.warning("Invalid raw texture type: " + string);
         return null;
      }

      InternalFormat var10 = (InternalFormat)var5.parseEnum((String)var7.poll(), InternalFormat.values(), "internal format");
      if (var10 == null) {
         SMCLog.warning("Invalid raw texture internal format: " + string);
         return null;
      }

      int var12 = 0;
      int var13 = 0;
      int var11;
      switch (var9) {
         case TEXTURE_1D :
            var11 = var5.parseInt((String)var7.poll(), -1);
            break;
         case TEXTURE_2D :
         case TEXTURE_RECTANGLE :
            var11 = var5.parseInt((String)var7.poll(), -1);
            var12 = var5.parseInt((String)var7.poll(), -1);
            break;
         case TEXTURE_3D :
            var11 = var5.parseInt((String)var7.poll(), -1);
            var12 = var5.parseInt((String)var7.poll(), -1);
            var13 = var5.parseInt((String)var7.poll(), -1);
            break;
         default:
            SMCLog.warning("Invalid raw texture type: " + var9);
            return null;
      }

      if (var11 >= 0 && var12 >= 0 && var13 >= 0) {
         PixelFormat var14 = (PixelFormat)var5.parseEnum((String)var7.poll(), PixelFormat.values(), "pixel format");
         if (var14 == null) {
            SMCLog.warning("Invalid raw texture pixel format: " + string);
            return null;
         } else {
            PixelType var15 = (PixelType)var5.parseEnum((String)var7.poll(), PixelType.values(), "pixel type");
            if (var15 == null) {
               SMCLog.warning("Invalid raw texture pixel type: " + string);
               return null;
            } else if (!var7.isEmpty()) {
               SMCLog.warning("Invalid raw texture, too many parameters: " + string);
               return null;
            } else {
               return loadCustomTextureRaw(integer, string, var8, var9, var10, var11, var12, var13, var14, var15);
            }
         }
      } else {
         SMCLog.warning("Invalid raw texture size: " + string);
         return null;
      }
   }

   private static ICustomTexture loadCustomTextureRaw(
      int integer1,
      String string2,
      String string3,
      TextureType textureType,
      InternalFormat internalFormat,
      int integer6,
      int integer7,
      int integer8,
      PixelFormat pixelFormat,
      PixelType pixelType
   ) {
      try {
         String var13 = "shaders/" + StrUtils.removePrefix(string3, "/");
         InputStream var14 = shaderPack.getResourceAsStream(var13);
         if (var14 == null) {
            SMCLog.warning("Raw texture not found: " + string3);
            return null;
         } else {
            byte[] var15 = Config.readAll(var14);
            IOUtils.closeQuietly(var14);
            ByteBuffer var16 = GLAllocation.createDirectByteBuffer(var15.length);
            var16.put(var15);
            var16.flip();
            TextureMetadataSection var17 = SimpleShaderTexture.loadTextureMetadataSection(var13, new TextureMetadataSection(true, true, new ArrayList<>()));
            return new CustomTextureRaw(
               textureType,
               internalFormat,
               integer6,
               integer7,
               integer8,
               pixelFormat,
               pixelType,
               var16,
               integer1,
               var17.getTextureBlur(),
               var17.getTextureClamp()
            );
         }
      } catch (IOException var18) {
         SMCLog.warning("Error loading raw texture: " + string3);
         SMCLog.warning(var18.getClass().getName() + ": " + var18.getMessage());
         return null;
      }
   }

   private static ICustomTexture loadCustomTextureShaders(int integer, String string) {
      string = string.trim();
      if (string.indexOf(46) < 0) {
         string = string + ".png";
      }

      try {
         String var5 = "shaders/" + StrUtils.removePrefix(string, "/");
         InputStream var6 = shaderPack.getResourceAsStream(var5);
         if (var6 == null) {
            SMCLog.warning("Texture not found: " + string);
            return null;
         } else {
            IOUtils.closeQuietly(var6);
            SimpleShaderTexture var7 = new SimpleShaderTexture(var5);
            var7.loadTexture(mc.getResourceManager());
            return new CustomTexture(integer, var5, var7);
         }
      } catch (IOException var8) {
         SMCLog.warning("Error loading texture: " + string);
         SMCLog.warning(var8.getClass().getName() + ": " + var8.getMessage());
         return null;
      }
   }

   private static int getTextureIndex(int integer, String string) {
      if (integer == 0) {
         if (string.equals("texture")) {
            return 0;
         }

         if (string.equals("lightmap")) {
            return 1;
         }

         if (string.equals("normals")) {
            return 2;
         }

         if (string.equals("specular")) {
            return 3;
         }

         if (string.equals("shadowtex0") || string.equals("watershadow")) {
            return 4;
         }

         if (string.equals("shadow")) {
            return waterShadowEnabled ? 5 : 4;
         }

         if (string.equals("shadowtex1")) {
            return 5;
         }

         if (string.equals("depthtex0")) {
            return 6;
         }

         if (string.equals("gaux1")) {
            return 7;
         }

         if (string.equals("gaux2")) {
            return 8;
         }

         if (string.equals("gaux3")) {
            return 9;
         }

         if (string.equals("gaux4")) {
            return 10;
         }

         if (string.equals("depthtex1")) {
            return 12;
         }

         if (string.equals("shadowcolor0") || string.equals("shadowcolor")) {
            return 13;
         }

         if (string.equals("shadowcolor1")) {
            return 14;
         }

         if (string.equals("noisetex")) {
            return 15;
         }
      }

      if (integer == 1 || integer == 2) {
         if (string.equals("colortex0")) {
            return 0;
         }

         if (string.equals("colortex1") || string.equals("gdepth")) {
            return 1;
         }

         if (string.equals("colortex2") || string.equals("gnormal")) {
            return 2;
         }

         if (string.equals("colortex3") || string.equals("composite")) {
            return 3;
         }

         if (string.equals("shadowtex0") || string.equals("watershadow")) {
            return 4;
         }

         if (string.equals("shadow")) {
            return waterShadowEnabled ? 5 : 4;
         }

         if (string.equals("shadowtex1")) {
            return 5;
         }

         if (string.equals("depthtex0") || string.equals("gdepthtex")) {
            return 6;
         }

         if (string.equals("colortex4") || string.equals("gaux1")) {
            return 7;
         }

         if (string.equals("colortex5") || string.equals("gaux2")) {
            return 8;
         }

         if (string.equals("colortex6") || string.equals("gaux3")) {
            return 9;
         }

         if (string.equals("colortex7") || string.equals("gaux4")) {
            return 10;
         }

         if (string.equals("depthtex1")) {
            return 11;
         }

         if (string.equals("depthtex2")) {
            return 12;
         }

         if (string.equals("shadowcolor0") || string.equals("shadowcolor")) {
            return 13;
         }

         if (string.equals("shadowcolor1")) {
            return 14;
         }

         if (string.equals("noisetex")) {
            return 15;
         }
      }

      return -1;
   }

   private static void bindCustomTextures(ICustomTexture[] arr) {
      if (arr != null) {
         for (ICustomTexture var7 : arr) {
            GlStateManager.setActiveTexture(33984 + var7.getTextureUnit());
            int var8 = var7.getTextureId();
            int var9 = var7.getTarget();
            if (var9 == 3553) {
               GlStateManager.bindTexture(var8);
            } else {
               GL11.glBindTexture(var9, var8);
            }
         }
      }
   }

   private static void resetCustomTextures() {
      deleteCustomTextures(customTexturesGbuffers);
      deleteCustomTextures(customTexturesComposite);
      deleteCustomTextures(customTexturesDeferred);
      customTexturesGbuffers = null;
      customTexturesComposite = null;
      customTexturesDeferred = null;
   }

   private static void deleteCustomTextures(ICustomTexture[] arr) {
      if (arr != null) {
         ICustomTexture[] var4 = arr;
         int var5 = var4.length;

         for (int var6 = 0; var6 < var5; var6++) {
            var4[var6].deleteTexture();
         }
      }
   }

   public static ShaderOption[] getShaderPackOptions(String string) {
      ShaderOption[] var4 = (ShaderOption[])shaderPackOptions.clone();
      if (shaderPackGuiScreens == null) {
         if (shaderPackProfiles != null) {
            ShaderOptionProfile var14 = new ShaderOptionProfile(shaderPackProfiles, var4);
            var4 = (ShaderOption[])Config.addObjectToArray(var4, var14, 0);
         }

         return getVisibleOptions(var4);
      } else {
         String var5 = string != null ? "screen." + string : "screen";
         ScreenShaderOptions var6 = shaderPackGuiScreens.get(var5);
         if (var6 == null) {
            return new ShaderOption[0];
         }

         ShaderOption[] var7 = var6.getShaderOptions();
         ArrayList<ShaderOption> var8 = new ArrayList<>();

         for (ShaderOption var12 : var7) {
            if (var12 == null) {
               var8.add(null);
            } else if (var12 instanceof ShaderOptionRest) {
               ShaderOption[] var13 = getShaderOptionsRest(shaderPackGuiScreens, var4);
               var8.addAll(Arrays.asList(var13));
            } else {
               var8.add(var12);
            }
         }

         return var8.toArray(new ShaderOption[0]);
      }
   }

   public static int getShaderPackColumns(String string, int integer) {
      String var5 = string != null ? "screen." + string : "screen";
      if (shaderPackGuiScreens == null) {
         return integer;
      }

      ScreenShaderOptions var6 = shaderPackGuiScreens.get(var5);
      return var6 == null ? integer : var6.getColumns();
   }

   private static ShaderOption[] getShaderOptionsRest(Map<String, ScreenShaderOptions> map, ShaderOption[] arr) {
      HashSet var5 = new HashSet();

      for (String var7 : map.keySet()) {
         for (ShaderOption var13 : ((ScreenShaderOptions)map.get(var7)).getShaderOptions()) {
            if (var13 != null) {
               var5.add(var13.getName());
            }
         }
      }

      ArrayList<ShaderOption> var14 = new ArrayList<>();

      for (ShaderOption var16 : arr) {
         if (var16.isVisible()) {
            String var17 = var16.getName();
            if (!var5.contains(var17)) {
               var14.add(var16);
            }
         }
      }

      return var14.toArray(new ShaderOption[0]);
   }

   public static ShaderOption getShaderOption(String string) {
      return ShaderUtils.getShaderOption(string, shaderPackOptions);
   }

   public static ShaderOption[] getShaderPackOptions() {
      return shaderPackOptions;
   }

   public static boolean isShaderPackOptionSlider(String string) {
      return shaderPackOptionSliders != null && shaderPackOptionSliders.contains(string);
   }

   private static ShaderOption[] getVisibleOptions(ShaderOption[] arr) {
      ArrayList<ShaderOption> var4 = new ArrayList<>();

      for (ShaderOption var8 : arr) {
         if (var8.isVisible()) {
            var4.add(var8);
         }
      }

      return var4.toArray(new ShaderOption[0]);
   }

   public static void saveShaderPackOptions() {
      saveShaderPackOptions(shaderPackOptions, shaderPack);
   }

   private static void saveShaderPackOptions(ShaderOption[] arr, IShaderPack iShaderPack) {
      PropertiesOrdered var5 = new PropertiesOrdered();
      if (shaderPackOptions != null) {
         for (ShaderOption var9 : arr) {
            if (var9.isChanged() && var9.isEnabled()) {
               var5.setProperty(var9.getName(), var9.getValue());
            }
         }
      }

      try {
         saveOptionProperties(iShaderPack, var5);
      } catch (IOException var10) {
         Config.Method252("[Shaders] Error saving configuration for " + shaderPack.getName());
         var10.printStackTrace();
      }
   }

   private static void saveOptionProperties(IShaderPack iShaderPack, Properties properties) throws IOException {
      String var5 = "shaderpacks/" + iShaderPack.getName() + ".txt";
      File var6 = new File(Minecraft.getMinecraft().mcDataDir, var5);
      if (properties.isEmpty()) {
         var6.delete();
      } else {
         FileOutputStream var7 = new FileOutputStream(var6);
         properties.store(var7, null);
         var7.flush();
         var7.close();
      }
   }

   private static ShaderOption[] loadShaderPackOptions() {
      try {
         String[] var3 = programs.getProgramNames();
         ShaderOption[] var4 = ShaderPackParser.parseShaderPackOptions(shaderPack, var3, shaderPackDimensions);
         Properties var5 = loadOptionProperties(shaderPack);

         for (ShaderOption var9 : var4) {
            String var10 = var5.getProperty(var9.getName());
            if (var10 != null) {
               var9.resetValue();
               if (!var9.setValue(var10)) {
                  Config.Method252("[Shaders] Invalid value, option: " + var9.getName() + ", value: " + var10);
               }
            }
         }

         return var4;
      } catch (IOException var11) {
         Config.Method252("[Shaders] Error reading configuration for " + shaderPack.getName());
         var11.printStackTrace();
         return null;
      }
   }

   private static Properties loadOptionProperties(IShaderPack iShaderPack) throws IOException {
      PropertiesOrdered var4 = new PropertiesOrdered();
      String var5 = "shaderpacks/" + iShaderPack.getName() + ".txt";
      File var6 = new File(Minecraft.getMinecraft().mcDataDir, var5);
      if (var6.exists() && var6.isFile() && var6.canRead()) {
         FileInputStream var7 = new FileInputStream(var6);
         var4.load(var7);
         var7.close();
         return var4;
      } else {
         return var4;
      }
   }

   public static ShaderOption[] getChangedOptions(ShaderOption[] arr) {
      ArrayList<ShaderOption> var4 = new ArrayList<>();

      for (ShaderOption var8 : arr) {
         if (var8.isEnabled() && var8.isChanged()) {
            var4.add(var8);
         }
      }

      return var4.toArray(new ShaderOption[0]);
   }

   private static String applyOptions(String string, ShaderOption[] arr) {
      if (arr != null && arr.length > 0) {
         for (ShaderOption var8 : arr) {
            if (var8.matchesLine(string)) {
               string = var8.getSourceLine();
               break;
            }
         }
      }

      return string;
   }

   public static ArrayList listOfShaders() {
      ArrayList var3 = new ArrayList();
      var3.add("OFF");
      var3.add("(internal)");
      int var4 = var3.size();

      try {
         if (!shaderPacksDir.exists()) {
            shaderPacksDir.mkdir();
         }

         for (File var9 : shaderPacksDir.listFiles()) {
            String var10 = var9.getName();
            if (var9.isDirectory()) {
               if (!var10.equals("debug")) {
                  File var11 = new File(var9, "shaders");
                  if (var11.exists() && var11.isDirectory()) {
                     var3.add(var10);
                  }
               }
            } else if (var9.isFile() && var10.toLowerCase().endsWith(".zip")) {
               var3.add(var10);
            }
         }
      } catch (Exception var12) {
      }

      var3.subList(var4, var3.size()).sort(String.CASE_INSENSITIVE_ORDER);
      return var3;
   }

   public static int checkFramebufferStatus(String string) {
      int var4 = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
      if (var4 != 36053) {
         System.err.format("FramebufferStatus 0x%04X at %s\n", var4, string);
      }

      return var4;
   }

   public static int checkGLError(String string) {
      int var4 = GlStateManager.glGetError();
      if (var4 != 0 && GlErrors.isEnabled(var4)) {
         String var5 = Config.getGlErrorString(var4);
         String var6 = getErrorInfo(var4, string);
         String var7 = String.format("OpenGL error: %s (%s)%s, at: %s", var4, var5, var6, string);
         SMCLog.severe(var7);
         if (Config.isShowGlErrors() && TimedEvent.isActive("ShowGlErrorShaders", 10000L)) {
            printChat(I18n.format("of.message.openglError", var4, var5));
         }
      }

      return var4;
   }

   private static String getErrorInfo(int integer, String string) {
      StringBuilder var5 = new StringBuilder();
      if (integer == 1286) {
         int var6 = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
         String var7 = getFramebufferStatusText(var6);
         String var8 = ", fbStatus: " + var6 + " (" + var7 + ")";
         var5.append(var8);
      }

      String var9 = activeProgram.getName();
      if (var9.isEmpty()) {
         var9 = "none";
      }

      var5.append(", program: ").append(var9);
      Program var10 = getProgramById(activeProgramID);
      if (var10 != activeProgram) {
         String var11 = var10.getName();
         if (var11.isEmpty()) {
            var11 = "none";
         }

         var5.append(" (").append(var11).append(")");
      }

      if (string.equals("setDrawBuffers")) {
         var5.append(", drawBuffers: ").append(activeProgram.getDrawBufSettings());
      }

      return var5.toString();
   }

   private static Program getProgramById(int integer) {
      for (Program var7 : ProgramsAll) {
         if (var7.getId() == integer) {
            return var7;
         }
      }

      return ProgramNone;
   }

   private static String getFramebufferStatusText(int integer) {
      switch (integer) {
         case 33305:
            return "Undefined";
         case 36053:
            return "Complete";
         case 36054:
            return "Incomplete attachment";
         case 36055:
            return "Incomplete missing attachment";
         case 36059:
            return "Incomplete draw buffer";
         case 36060:
            return "Incomplete read buffer";
         case 36061:
            return "Unsupported";
         case 36182:
            return "Incomplete multisample";
         case 36264:
            return "Incomplete layer targets";
         default:
            return "Unknown";
      }
   }

   private static void printChat(String string) {
      mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(string));
   }

   private static void printChatAndLogError(String string) {
      SMCLog.severe(string);
      mc.ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(string));
   }

   public static void printIntBuffer(String string, IntBuffer intBuffer) {
      StringBuilder var5 = new StringBuilder(128);
      var5.append(string)
         .append(" [pos ")
         .append(intBuffer.position())
         .append(" lim ")
         .append(intBuffer.limit())
         .append(" cap ")
         .append(intBuffer.capacity())
         .append(" :");
      int var6 = intBuffer.limit();

      for (int var7 = 0; var7 < var6; var7++) {
         var5.append(" ").append(intBuffer.get(var7));
      }

      var5.append("]");
      SMCLog.info(var5.toString());
   }

   public static void Method448(Minecraft minecraft) {
      checkShadersModInstalled();
      mc = minecraft;
      capabilities = GLContext.getCapabilities();
      glVersionString = GL11.glGetString(GL11.GL_VERSION);
      glVendorString = GL11.glGetString(GL11.GL_VENDOR);
      glRendererString = GL11.glGetString(GL11.GL_RENDERER);
      SMCLog.info("OpenGL Version: " + glVersionString);
      SMCLog.info("Vendor:  " + glVendorString);
      SMCLog.info("Renderer: " + glRendererString);
      String var10000 = capabilities.OpenGL20 ? " 2.0 " : " - ";
      String var10001 = capabilities.OpenGL21 ? " 2.1 " : " - ";
      String var10002 = capabilities.OpenGL30 ? " 3.0 " : " - ";
      String var10003 = capabilities.OpenGL32 ? " 3.2 " : " - ";
      String var4 = capabilities.OpenGL40 ? " 4.0 " : " - ";
      String var5 = var10003;
      String var6 = var10002;
      String var7 = var10001;
      String var8 = var10000;
      SMCLog.info("Capabilities: " + var8 + var7 + var6 + var5 + var4);
      SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger(GL20.GL_MAX_DRAW_BUFFERS));
      SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger(GL30.GL_MAX_COLOR_ATTACHMENTS));
      SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger(GL20.GL_MAX_TEXTURE_IMAGE_UNITS));
      hasGlGenMipmap = capabilities.OpenGL30;
      loadConfig();
   }

   public static void updateBlockLightLevel() {
      if (isOldLighting()) {
         blockLightLevel05 = 0.5F;
         blockLightLevel06 = 0.6F;
         blockLightLevel08 = 0.8F;
      } else {
         blockLightLevel05 = 1.0F;
         blockLightLevel06 = 1.0F;
         blockLightLevel08 = 1.0F;
      }
   }

   public static boolean isOldHandLight() {
      return !configOldHandLight.isDefault() ? configOldHandLight.isTrue() : shaderPackOldHandLight.isDefault() || shaderPackOldHandLight.isTrue();
   }

   public static boolean isDynamicHandLight() {
      return shaderPackDynamicHandLight.isDefault() || shaderPackDynamicHandLight.isTrue();
   }

   public static boolean isOldLighting() {
      return !configOldLighting.isDefault() ? configOldLighting.isTrue() : shaderPackOldLighting.isDefault() || shaderPackOldLighting.isTrue();
   }

   public static boolean isRenderShadowTranslucent() {
      return !shaderPackShadowTranslucent.isFalse();
   }

   public static boolean isUnderwaterOverlay() {
      return !shaderPackUnderwaterOverlay.isFalse();
   }

   public static boolean isSun() {
      return !shaderPackSun.isFalse();
   }

   public static boolean isMoon() {
      return !shaderPackMoon.isFalse();
   }

   public static boolean isVignette() {
      return !shaderPackVignette.isFalse();
   }

   public static boolean isRenderBackFace(EnumWorldBlockLayer enumWorldBlockLayer) {
      switch (enumWorldBlockLayer) {
         case SOLID :
            return shaderPackBackFaceSolid.isTrue();
         case CUTOUT :
            return shaderPackBackFaceCutout.isTrue();
         case CUTOUT_MIPPED :
            return shaderPackBackFaceCutoutMipped.isTrue();
         case TRANSLUCENT :
            return shaderPackBackFaceTranslucent.isTrue();
         default:
            return false;
      }
   }

   public static boolean isRainDepth() {
      return shaderPackRainDepth.isTrue();
   }

   public static boolean isBeaconBeamDepth() {
      return shaderPackBeaconBeamDepth.isTrue();
   }

   public static boolean isSeparateAo() {
      return shaderPackSeparateAo.isTrue();
   }

   public static boolean isFrustumCulling() {
      return !shaderPackFrustumCulling.isFalse();
   }

   public static void init() {
      boolean var3;
      if (!isInitializedOnce) {
         isInitializedOnce = true;
         var3 = true;
      } else {
         var3 = false;
      }

      if (!isShaderPackInitialized) {
         checkGLError("Shaders.init pre");
         if (getShaderPackName() != null) {
         }

         if (!capabilities.OpenGL20) {
            printChatAndLogError("No OpenGL 2.0");
         }

         if (!capabilities.GL_EXT_framebuffer_object) {
            printChatAndLogError("No EXT_framebuffer_object");
         }

         dfbDrawBuffers.position(0).limit(8);
         dfbColorTextures.position(0).limit(16);
         dfbDepthTextures.position(0).limit(3);
         sfbDrawBuffers.position(0).limit(8);
         sfbDepthTextures.position(0).limit(2);
         sfbColorTextures.position(0).limit(8);
         usedColorBuffers = 4;
         usedDepthBuffers = 1;
         usedShadowColorBuffers = 0;
         usedShadowDepthBuffers = 0;
         usedColorAttachs = 1;
         usedDrawBuffers = 1;
         Arrays.fill(gbuffersFormat, 6408);
         Arrays.fill(gbuffersClear, true);
         Arrays.fill(gbuffersClearColor, null);
         Arrays.fill(shadowHardwareFilteringEnabled, false);
         Arrays.fill(shadowMipmapEnabled, false);
         Arrays.fill(shadowFilterNearest, false);
         Arrays.fill(shadowColorMipmapEnabled, false);
         Arrays.fill(shadowColorFilterNearest, false);
         centerDepthSmoothEnabled = false;
         noiseTextureEnabled = false;
         sunPathRotation = 0.0F;
         shadowIntervalSize = 2.0F;
         shadowMapWidth = 1024;
         shadowMapHeight = 1024;
         spShadowMapWidth = 1024;
         spShadowMapHeight = 1024;
         shadowMapFOV = 90.0F;
         shadowMapHalfPlane = 160.0F;
         shadowMapIsOrtho = true;
         shadowDistanceRenderMul = -1.0F;
         aoLevel = -1.0F;
         useEntityAttrib = false;
         useMidTexCoordAttrib = false;
         useTangentAttrib = false;
         waterShadowEnabled = false;
         hasGeometryShaders = false;
         updateBlockLightLevel();
         Smoother.resetValues();
         shaderUniforms.reset();
         if (customUniforms != null) {
            customUniforms.reset();
         }

         ShaderProfile var4 = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
         String var5 = "";
         if (currentWorld != null) {
            int var6 = currentWorld.provider.getDimensionId();
            if (shaderPackDimensions.contains(var6)) {
               var5 = "world" + var6 + "/";
            }
         }

         for (Program var9 : ProgramsAll) {
            var9.resetId();
            var9.resetConfiguration();
            if (var9.getProgramStage() != ProgramStage.NONE) {
               String var10 = var9.getName();
               String var11 = var5 + var10;
               boolean var12 = true;
               if (shaderPackProgramConditions.containsKey(var11)) {
                  var12 = true && shaderPackProgramConditions.get(var11).eval();
               }

               if (var4 != null) {
                  var12 = var12 && !var4.isProgramDisabled(var11);
               }

               if (!var12) {
                  SMCLog.info("Program disabled: " + var11);
                  var10 = "<disabled>";
                  var11 = var5 + var10;
               }

               String var13 = "/shaders/" + var11;
               String var14 = var13 + ".vsh";
               String var15 = var13 + ".gsh";
               String var16 = var13 + ".fsh";
               setupProgram(var9, var14, var15, var16);
               int var17 = var9.getId();
               if (var17 > 0) {
                  SMCLog.info("Program loaded: " + var11);
               }

               initDrawBuffers(var9);
               updateToggleBuffers(var9);
            }
         }

         hasDeferredPrograms = false;

         for (Program var27 : ProgramsDeferred) {
            if (var27.getId() != 0) {
               hasDeferredPrograms = true;
               break;
            }
         }

         usedColorAttachs = usedColorBuffers;
         shadowPassInterval = usedShadowDepthBuffers > 0 ? 1 : 0;
         shouldSkipDefaultShadow = usedShadowDepthBuffers > 0;
         SMCLog.info("usedColorBuffers: " + usedColorBuffers);
         SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
         SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
         SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
         SMCLog.info("usedColorAttachs: " + usedColorAttachs);
         SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
         dfbDrawBuffers.position(0).limit(usedDrawBuffers);
         dfbColorTextures.position(0).limit(usedColorBuffers * 2);
         dfbColorTexturesFlip.reset();

         for (int var20 = 0; var20 < usedDrawBuffers; var20++) {
            dfbDrawBuffers.put(var20, 36064 + var20);
         }

         int var21 = GL11.glGetInteger(GL20.GL_MAX_DRAW_BUFFERS);
         if (usedDrawBuffers > var21) {
            printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + var21);
         }

         sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);

         for (int var23 = 0; var23 < usedShadowColorBuffers; var23++) {
            sfbDrawBuffers.put(var23, 36064 + var23);
         }

         for (Program var30 : ProgramsAll) {
            Program var31 = var30;

            while (var31.getId() == 0 && var31.getProgramBackup() != var31) {
               var31 = var31.getProgramBackup();
            }

            if (var31 != var30 && var30 != ProgramShadow) {
               var30.copyFrom(var31);
            }
         }

         resize();
         resizeShadow();
         if (noiseTextureEnabled) {
            setupNoiseTexture();
         }

         if (defaultTexture == null) {
            defaultTexture = ShadersTex.createDefaultTexture();
         }

         GlStateManager.pushMatrix();
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         preCelestialRotate();
         postCelestialRotate();
         GlStateManager.popMatrix();
         isShaderPackInitialized = true;
         loadEntityDataMap();
         resetDisplayLists();
         if (!var3) {
         }

         checkGLError("Shaders.init");
      }
   }

   private static void initDrawBuffers(Program program) {
      int var4 = GL11.glGetInteger(GL20.GL_MAX_DRAW_BUFFERS);
      Arrays.fill(program.getToggleColorTextures(), false);
      if (program == ProgramFinal) {
         program.setDrawBuffers(null);
      } else if (program.getId() == 0) {
         if (program == ProgramShadow) {
            program.setDrawBuffers(drawBuffersNone);
         } else {
            program.setDrawBuffers(drawBuffersColorAtt0);
         }
      } else {
         String var5 = program.getDrawBufSettings();
         if (var5 == null) {
            if (program != ProgramShadow && program != ProgramShadowSolid && program != ProgramShadowCutout) {
               program.setDrawBuffers(dfbDrawBuffers);
               usedDrawBuffers = usedColorBuffers;
               Arrays.fill(program.getToggleColorTextures(), 0, usedColorBuffers, true);
            } else {
               program.setDrawBuffers(sfbDrawBuffers);
            }
         } else {
            IntBuffer var6 = program.getDrawBuffersBuffer();
            int var7 = var5.length();
            usedDrawBuffers = Math.max(usedDrawBuffers, var7);
            var7 = Math.min(var7, var4);
            program.setDrawBuffers(var6);
            var6.limit(var7);

            for (int var8 = 0; var8 < var7; var8++) {
               int var9 = getDrawBuffer(program, var5, var8);
               var6.put(var8, var9);
            }
         }
      }
   }

   private static int getDrawBuffer(Program program, String string, int integer) {
      int var6 = 0;
      if (integer < string.length()) {
         int var7 = string.charAt(integer) - '0';
         if (program == ProgramShadow) {
            if (var7 >= 0 && var7 <= 1) {
               var6 = var7 + 36064;
               usedShadowColorBuffers = Math.max(usedShadowColorBuffers, var7);
            }
         } else if (var7 >= 0 && var7 <= 7) {
            program.getToggleColorTextures()[var7] = true;
            var6 = var7 + 36064;
            usedColorAttachs = Math.max(usedColorAttachs, var7);
            usedColorBuffers = Math.max(usedColorBuffers, var7);
         }
      }

      return var6;
   }

   private static void updateToggleBuffers(Program program) {
      boolean[] var4 = program.getToggleColorTextures();
      Boolean[] var5 = program.getBuffersFlip();

      for (int var6 = 0; var6 < var5.length; var6++) {
         Boolean var7 = var5[var6];
         if (var7 != null) {
            var4[var6] = var7;
         }
      }
   }

   public static void resetDisplayLists() {
      SMCLog.info("Reset model renderers");
      countResetDisplayLists++;
      SMCLog.info("Reset world renderers");
      mc.renderGlobal.loadRenderers();
   }

   private static void setupProgram(Program program, String string2, String string3, String string4) {
      checkGLError("pre setupProgram");
      int var7 = ARBShaderObjects.glCreateProgramObjectARB();
      checkGLError("create");
      if (var7 != 0) {
         progUseEntityAttrib = false;
         progUseMidTexCoordAttrib = false;
         progUseTangentAttrib = false;
         int var8 = createVertShader(program, string2);
         int var9 = createGeomShader(program, string3);
         int var10 = createFragShader(program, string4);
         checkGLError("create");
         if (var8 == 0 && var9 == 0 && var10 == 0) {
            ARBShaderObjects.glDeleteObjectARB(var7);
            program.resetId();
         } else {
            if (var8 != 0) {
               ARBShaderObjects.glAttachObjectARB(var7, var8);
               checkGLError("attach");
            }

            if (var9 != 0) {
               ARBShaderObjects.glAttachObjectARB(var7, var9);
               checkGLError("attach");
               if (progArbGeometryShader4) {
                  ARBGeometryShader4.glProgramParameteriARB(var7, ARBGeometryShader4.GL_GEOMETRY_INPUT_TYPE_ARB, 4);
                  ARBGeometryShader4.glProgramParameteriARB(var7, ARBGeometryShader4.GL_GEOMETRY_OUTPUT_TYPE_ARB, 5);
                  ARBGeometryShader4.glProgramParameteriARB(var7, ARBGeometryShader4.GL_GEOMETRY_VERTICES_OUT_ARB, progMaxVerticesOut);
                  checkGLError("arbGeometryShader4");
               }

               hasGeometryShaders = true;
            }

            if (var10 != 0) {
               ARBShaderObjects.glAttachObjectARB(var7, var10);
               checkGLError("attach");
            }

            if (progUseEntityAttrib) {
               ARBVertexShader.glBindAttribLocationARB(var7, entityAttrib, "mc_Entity");
               checkGLError("mc_Entity");
            }

            if (progUseMidTexCoordAttrib) {
               ARBVertexShader.glBindAttribLocationARB(var7, midTexCoordAttrib, "mc_midTexCoord");
               checkGLError("mc_midTexCoord");
            }

            if (progUseTangentAttrib) {
               ARBVertexShader.glBindAttribLocationARB(var7, tangentAttrib, "at_tangent");
               checkGLError("at_tangent");
            }

            ARBShaderObjects.glLinkProgramARB(var7);
            if (GL20.glGetProgrami(var7, GL20.GL_LINK_STATUS) != 1) {
               SMCLog.severe("Error linking program: " + var7 + " (" + program.getName() + ")");
            }

            printLogInfo(var7, program.getName());
            if (var8 != 0) {
               ARBShaderObjects.glDetachObjectARB(var7, var8);
               ARBShaderObjects.glDeleteObjectARB(var8);
            }

            if (var9 != 0) {
               ARBShaderObjects.glDetachObjectARB(var7, var9);
               ARBShaderObjects.glDeleteObjectARB(var9);
            }

            if (var10 != 0) {
               ARBShaderObjects.glDetachObjectARB(var7, var10);
               ARBShaderObjects.glDeleteObjectARB(var10);
            }

            program.setId(var7);
            program.setRef(var7);
            useProgram(program);
            ARBShaderObjects.glValidateProgramARB(var7);
            useProgram(ProgramNone);
            printLogInfo(var7, program.getName());
            if (GL20.glGetProgrami(var7, GL20.GL_VALIDATE_STATUS) != 1) {
               String var14 = program.getName();
               printChatAndLogError("[Shaders] Error: Invalid program " + "\"" + var14 + "\"");
               ARBShaderObjects.glDeleteObjectARB(var7);
               program.resetId();
            }
         }
      }
   }

   private static int createVertShader(Program program, String string) {
      int var5 = ARBShaderObjects.glCreateShaderObjectARB(GL20.GL_VERTEX_SHADER);
      if (var5 == 0) {
         return 0;
      }

      StringBuilder var6 = new StringBuilder(131072);

      BufferedReader var7;
      try {
         var7 = new BufferedReader(getShaderReader(string));
      } catch (Exception var13) {
         ARBShaderObjects.glDeleteObjectARB(var5);
         return 0;
      }

      ShaderOption[] var8 = getChangedOptions(shaderPackOptions);
      ArrayList var9 = new ArrayList();
      if (var7 != null) {
         try {
            var7 = ShaderPackParser.resolveIncludes(var7, string, shaderPack, 0, var9, 0);
            MacroState var10 = new MacroState();

            while (true) {
               String var11 = var7.readLine();
               if (var11 == null) {
                  var7.close();
                  break;
               }

               var11 = applyOptions(var11, var8);
               var6.append(var11).append('\n');
               if (var10.processLine(var11)) {
                  ShaderLine var12 = ShaderParser.parseLine(var11);
                  if (var12 != null) {
                     if (var12.isAttribute("mc_Entity")) {
                        useEntityAttrib = true;
                        progUseEntityAttrib = true;
                     } else if (var12.isAttribute("mc_midTexCoord")) {
                        useMidTexCoordAttrib = true;
                        progUseMidTexCoordAttrib = true;
                     } else if (var12.isAttribute("at_tangent")) {
                        useTangentAttrib = true;
                        progUseTangentAttrib = true;
                     }

                     if (var12.isConstInt("countInstances")) {
                        program.setCountInstances(var12.getValueInt());
                        SMCLog.info("countInstances: " + program.getCountInstances());
                     }
                  }
               }
            }
         } catch (Exception var14) {
            SMCLog.severe("Couldn't read " + string + "!");
            var14.printStackTrace();
            ARBShaderObjects.glDeleteObjectARB(var5);
            return 0;
         }
      }

      if (saveFinalShaders) {
         saveShader(string, var6.toString());
      }

      ARBShaderObjects.glShaderSourceARB(var5, var6);
      ARBShaderObjects.glCompileShaderARB(var5);
      if (GL20.glGetShaderi(var5, GL20.GL_COMPILE_STATUS) != 1) {
         SMCLog.severe("Error compiling vertex shader: " + string);
      }

      printShaderLogInfo(var5, string, var9);
      return var5;
   }

   private static int createGeomShader(Program program, String string) {
      int var5 = ARBShaderObjects.glCreateShaderObjectARB(GL32.GL_GEOMETRY_SHADER);
      if (var5 == 0) {
         return 0;
      }

      StringBuilder var6 = new StringBuilder(131072);

      BufferedReader var7;
      try {
         var7 = new BufferedReader(getShaderReader(string));
      } catch (Exception var14) {
         ARBShaderObjects.glDeleteObjectARB(var5);
         return 0;
      }

      ShaderOption[] var8 = getChangedOptions(shaderPackOptions);
      ArrayList var9 = new ArrayList();
      progArbGeometryShader4 = false;
      progMaxVerticesOut = 3;
      if (var7 != null) {
         try {
            var7 = ShaderPackParser.resolveIncludes(var7, string, shaderPack, 0, var9, 0);
            MacroState var10 = new MacroState();

            while (true) {
               String var11 = var7.readLine();
               if (var11 == null) {
                  var7.close();
                  break;
               }

               var11 = applyOptions(var11, var8);
               var6.append(var11).append('\n');
               if (var10.processLine(var11)) {
                  ShaderLine var12 = ShaderParser.parseLine(var11);
                  if (var12 != null) {
                     if (var12.isExtension("GL_ARB_geometry_shader4")) {
                        String var13 = Config.normalize(var12.getValue());
                        if (var13.equals("enable") || var13.equals("require") || var13.equals("warn")) {
                           progArbGeometryShader4 = true;
                        }
                     }

                     if (var12.isConstInt("maxVerticesOut")) {
                        progMaxVerticesOut = var12.getValueInt();
                     }
                  }
               }
            }
         } catch (Exception var15) {
            SMCLog.severe("Couldn't read " + string + "!");
            var15.printStackTrace();
            ARBShaderObjects.glDeleteObjectARB(var5);
            return 0;
         }
      }

      if (saveFinalShaders) {
         saveShader(string, var6.toString());
      }

      ARBShaderObjects.glShaderSourceARB(var5, var6);
      ARBShaderObjects.glCompileShaderARB(var5);
      if (GL20.glGetShaderi(var5, GL20.GL_COMPILE_STATUS) != 1) {
         SMCLog.severe("Error compiling geometry shader: " + string);
      }

      printShaderLogInfo(var5, string, var9);
      return var5;
   }

   private static int createFragShader(Program program, String string) {
      int var5 = ARBShaderObjects.glCreateShaderObjectARB(GL20.GL_FRAGMENT_SHADER);
      if (var5 == 0) {
         return 0;
      }

      StringBuilder var6 = new StringBuilder(131072);

      BufferedReader var7;
      try {
         var7 = new BufferedReader(getShaderReader(string));
      } catch (Exception var17) {
         ARBShaderObjects.glDeleteObjectARB(var5);
         return 0;
      }

      ShaderOption[] var8 = getChangedOptions(shaderPackOptions);
      ArrayList var9 = new ArrayList();
      if (var7 != null) {
         try {
            var7 = ShaderPackParser.resolveIncludes(var7, string, shaderPack, 0, var9, 0);
            MacroState var10 = new MacroState();

            while (true) {
               String var11 = var7.readLine();
               if (var11 == null) {
                  var7.close();
                  break;
               }

               var11 = applyOptions(var11, var8);
               var6.append(var11).append('\n');
               if (var10.processLine(var11)) {
                  ShaderLine var12 = ShaderParser.parseLine(var11);
                  if (var12 != null) {
                     if (var12.isUniform()) {
                        String var13 = var12.getName();
                        int var14;
                        if ((var14 = ShaderParser.getShadowDepthIndex(var13)) >= 0) {
                           usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, var14 + 1);
                        } else if ((var14 = ShaderParser.getShadowColorIndex(var13)) >= 0) {
                           usedShadowColorBuffers = Math.max(usedShadowColorBuffers, var14 + 1);
                        } else if ((var14 = ShaderParser.getDepthIndex(var13)) >= 0) {
                           usedDepthBuffers = Math.max(usedDepthBuffers, var14 + 1);
                        } else if (var13.equals("gdepth") && gbuffersFormat[1] == 6408) {
                           gbuffersFormat[1] = 34836;
                        } else if ((var14 = ShaderParser.getColorIndex(var13)) >= 0) {
                           usedColorBuffers = Math.max(usedColorBuffers, var14 + 1);
                        } else if (var13.equals("centerDepthSmooth")) {
                           centerDepthSmoothEnabled = true;
                        }
                     } else if (var12.isConstInt("shadowMapResolution") || var12.isProperty("SHADOWRES")) {
                        spShadowMapWidth = spShadowMapHeight = var12.getValueInt();
                        shadowMapWidth = shadowMapHeight = Math.round(spShadowMapWidth * configShadowResMul);
                        SMCLog.info("Shadow map resolution: " + spShadowMapWidth);
                     } else if (var12.isConstFloat("shadowMapFov") || var12.isProperty("SHADOWFOV")) {
                        shadowMapFOV = var12.getValueFloat();
                        shadowMapIsOrtho = false;
                        SMCLog.info("Shadow map field of view: " + shadowMapFOV);
                     } else if (var12.isConstFloat("shadowDistance") || var12.isProperty("SHADOWHPL")) {
                        shadowMapHalfPlane = var12.getValueFloat();
                        shadowMapIsOrtho = true;
                        SMCLog.info("Shadow map distance: " + shadowMapHalfPlane);
                     } else if (var12.isConstFloat("shadowDistanceRenderMul")) {
                        shadowDistanceRenderMul = var12.getValueFloat();
                        SMCLog.info("Shadow distance render mul: " + shadowDistanceRenderMul);
                     } else if (var12.isConstFloat("shadowIntervalSize")) {
                        shadowIntervalSize = var12.getValueFloat();
                        SMCLog.info("Shadow map interval size: " + shadowIntervalSize);
                     } else if (var12.isConstBool("generateShadowMipmap", true)) {
                        Arrays.fill(shadowMipmapEnabled, true);
                        SMCLog.info("Generate shadow mipmap");
                     } else if (var12.isConstBool("generateShadowColorMipmap", true)) {
                        Arrays.fill(shadowColorMipmapEnabled, true);
                        SMCLog.info("Generate shadow color mipmap");
                     } else if (var12.isConstBool("shadowHardwareFiltering", true)) {
                        Arrays.fill(shadowHardwareFilteringEnabled, true);
                        SMCLog.info("Hardware shadow filtering enabled.");
                     } else if (var12.isConstBool("shadowHardwareFiltering0", true)) {
                        shadowHardwareFilteringEnabled[0] = true;
                        SMCLog.info("shadowHardwareFiltering0");
                     } else if (var12.isConstBool("shadowHardwareFiltering1", true)) {
                        shadowHardwareFilteringEnabled[1] = true;
                        SMCLog.info("shadowHardwareFiltering1");
                     } else if (var12.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", true)) {
                        shadowMipmapEnabled[0] = true;
                        SMCLog.info("shadowtex0Mipmap");
                     } else if (var12.isConstBool("shadowtex1Mipmap", true)) {
                        shadowMipmapEnabled[1] = true;
                        SMCLog.info("shadowtex1Mipmap");
                     } else if (var12.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", true)) {
                        shadowColorMipmapEnabled[0] = true;
                        SMCLog.info("shadowcolor0Mipmap");
                     } else if (var12.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", true)) {
                        shadowColorMipmapEnabled[1] = true;
                        SMCLog.info("shadowcolor1Mipmap");
                     } else if (var12.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", true)) {
                        shadowFilterNearest[0] = true;
                        SMCLog.info("shadowtex0Nearest");
                     } else if (var12.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", true)) {
                        shadowFilterNearest[1] = true;
                        SMCLog.info("shadowtex1Nearest");
                     } else if (var12.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", true)) {
                        shadowColorFilterNearest[0] = true;
                        SMCLog.info("shadowcolor0Nearest");
                     } else if (var12.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", true)) {
                        shadowColorFilterNearest[1] = true;
                        SMCLog.info("shadowcolor1Nearest");
                     } else if (var12.isConstFloat("wetnessHalflife") || var12.isProperty("WETNESSHL")) {
                        wetnessHalfLife = var12.getValueFloat();
                        SMCLog.info("Wetness halflife: " + wetnessHalfLife);
                     } else if (var12.isConstFloat("drynessHalflife") || var12.isProperty("DRYNESSHL")) {
                        drynessHalfLife = var12.getValueFloat();
                        SMCLog.info("Dryness halflife: " + drynessHalfLife);
                     } else if (var12.isConstFloat("eyeBrightnessHalflife")) {
                        eyeBrightnessHalflife = var12.getValueFloat();
                        SMCLog.info("Eye brightness halflife: " + eyeBrightnessHalflife);
                     } else if (var12.isConstFloat("centerDepthHalflife")) {
                        centerDepthSmoothHalflife = var12.getValueFloat();
                        SMCLog.info("Center depth halflife: " + centerDepthSmoothHalflife);
                     } else if (var12.isConstFloat("sunPathRotation")) {
                        sunPathRotation = var12.getValueFloat();
                        SMCLog.info("Sun path rotation: " + sunPathRotation);
                     } else if (var12.isConstFloat("ambientOcclusionLevel")) {
                        aoLevel = Config.Method256(var12.getValueFloat(), 0.0F, 1.0F);
                        SMCLog.info("AO Level: " + aoLevel);
                     } else if (var12.isConstInt("superSamplingLevel")) {
                        int var21 = var12.getValueInt();
                        if (var21 > 1) {
                           SMCLog.info("Super sampling level: " + var21 + "x");
                           superSamplingLevel = var21;
                        } else {
                           superSamplingLevel = 1;
                        }
                     } else if (var12.isConstInt("noiseTextureResolution")) {
                        noiseTextureResolution = var12.getValueInt();
                        noiseTextureEnabled = true;
                        SMCLog.info("Noise texture enabled");
                        SMCLog.info("Noise texture resolution: " + noiseTextureResolution);
                     } else if (var12.isConstIntSuffix("Format")) {
                        String var22 = StrUtils.removeSuffix(var12.getName(), "Format");
                        String var30 = var12.getValue();
                        int var15 = getBufferIndexFromString(var22);
                        int var16 = getTextureFormatFromString(var30);
                        if (var15 >= 0 && var16 != 0) {
                           gbuffersFormat[var15] = var16;
                           SMCLog.info("%s format: %s", var22, var30);
                        }
                     } else if (var12.isConstBoolSuffix("Clear", false)) {
                        if (ShaderParser.isComposite(string) || ShaderParser.isDeferred(string)) {
                           String var23 = StrUtils.removeSuffix(var12.getName(), "Clear");
                           int var31 = getBufferIndexFromString(var23);
                           if (var31 >= 0) {
                              gbuffersClear[var31] = false;
                              SMCLog.info("%s clear disabled", var23);
                           }
                        }
                     } else if (var12.isConstVec4Suffix("ClearColor")) {
                        if (ShaderParser.isComposite(string) || ShaderParser.isDeferred(string)) {
                           String var24 = StrUtils.removeSuffix(var12.getName(), "ClearColor");
                           int var32 = getBufferIndexFromString(var24);
                           if (var32 >= 0) {
                              Vector4f var34 = var12.getValueVec4();
                              if (var34 != null) {
                                 gbuffersClearColor[var32] = var34;
                                 SMCLog.info("%s clear color: %s %s %s %s", var24, var34.getX(), var34.getY(), var34.getZ(), var34.getW());
                              } else {
                                 SMCLog.warning("Invalid color value: " + var12.getValue());
                              }
                           }
                        }
                     } else if (var12.isProperty("GAUX4FORMAT", "RGBA32F")) {
                        gbuffersFormat[7] = 34836;
                        SMCLog.info("gaux4 format : RGB32AF");
                     } else if (var12.isProperty("GAUX4FORMAT", "RGB32F")) {
                        gbuffersFormat[7] = 34837;
                        SMCLog.info("gaux4 format : RGB32F");
                     } else if (var12.isProperty("GAUX4FORMAT", "RGB16")) {
                        gbuffersFormat[7] = 32852;
                        SMCLog.info("gaux4 format : RGB16");
                     } else if (var12.isConstBoolSuffix("MipmapEnabled", true)) {
                        if (ShaderParser.isComposite(string) || ShaderParser.isDeferred(string) || ShaderParser.isFinal(string)) {
                           String var25 = StrUtils.removeSuffix(var12.getName(), "MipmapEnabled");
                           int var33 = getBufferIndexFromString(var25);
                           if (var33 >= 0) {
                              int var35 = program.getCompositeMipmapSetting();
                              var35 |= 1 << var33;
                              program.setCompositeMipmapSetting(var35);
                              SMCLog.info("%s mipmap enabled", var25);
                           }
                        }
                     } else if (var12.isProperty("DRAWBUFFERS")) {
                        String var26 = var12.getValue();
                        if (ShaderParser.isValidDrawBuffers(var26)) {
                           program.setDrawBufSettings(var26);
                        } else {
                           SMCLog.warning("Invalid draw buffers: " + var26);
                        }
                     }
                  }
               }
            }
         } catch (Exception var18) {
            SMCLog.severe("Couldn't read " + string + "!");
            var18.printStackTrace();
            ARBShaderObjects.glDeleteObjectARB(var5);
            return 0;
         }
      }

      if (saveFinalShaders) {
         saveShader(string, var6.toString());
      }

      ARBShaderObjects.glShaderSourceARB(var5, var6);
      ARBShaderObjects.glCompileShaderARB(var5);
      if (GL20.glGetShaderi(var5, GL20.GL_COMPILE_STATUS) != 1) {
         SMCLog.severe("Error compiling fragment shader: " + string);
      }

      printShaderLogInfo(var5, string, var9);
      return var5;
   }

   private static Reader getShaderReader(String string) {
      return new InputStreamReader(shaderPack.getResourceAsStream(string));
   }

   public static void saveShader(String string1, String string2) {
      try {
         File var4 = new File(shaderPacksDir, "debug/" + string1);
         var4.getParentFile().mkdirs();
         Config.Method276(var4, string2);
      } catch (IOException var5) {
         Config.Method252("Error saving: " + string1);
         var5.printStackTrace();
      }
   }

   private static void clearDirectory(File file) {
      if (file.exists() && file.isDirectory()) {
         File[] var4 = file.listFiles();
         if (var4 != null) {
            for (File var8 : var4) {
               if (var8.isDirectory()) {
                  clearDirectory(var8);
               }

               var8.delete();
            }
         }
      }
   }

   private static boolean printLogInfo(int integer, String string) {
      IntBuffer var5 = BufferUtils.createIntBuffer(1);
      ARBShaderObjects.glGetObjectParameterARB(integer, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, var5);
      int var6 = var5.get();
      if (var6 > 1) {
         ByteBuffer var7 = BufferUtils.createByteBuffer(var6);
         var5.flip();
         ARBShaderObjects.glGetInfoLogARB(integer, var5, var7);
         byte[] var8 = new byte[var6];
         var7.get(var8);
         if (var8[var6 - 1] == 0) {
            var8[var6 - 1] = 10;
         }

         String var9 = StrUtils.trim(new String(var8, Charsets.US_ASCII), " \n\r\t");
         SMCLog.info("Info log: " + string + "\n" + var9);
         return false;
      } else {
         return true;
      }
   }

   private static boolean printShaderLogInfo(int integer, String string, List<String> list) {
      BufferUtils.createIntBuffer(1);
      int var7 = GL20.glGetShaderi(integer, GL20.GL_INFO_LOG_LENGTH);
      if (var7 <= 1) {
         return true;
      }

      for (int var8 = 0; var8 < list.size(); var8++) {
         String var9 = (String)list.get(var8);
         SMCLog.info("File: " + (var8 + 1) + " = " + var9);
      }

      String var10 = StrUtils.trim(GL20.glGetShaderInfoLog(integer, var7), " \n\r\t");
      SMCLog.info("Shader info log: " + string + "\n" + var10);
      return false;
   }

   public static void setDrawBuffers(IntBuffer intBuffer) {
      if (intBuffer == null) {
         intBuffer = drawBuffersNone;
      }

      if (activeDrawBuffers != intBuffer) {
         activeDrawBuffers = intBuffer;
         GL20.glDrawBuffers(intBuffer);
         checkGLError("setDrawBuffers");
      }
   }

   public static void useProgram(Program program) {
      checkGLError("pre-useProgram");
      if (isShadowPass) {
         program = ProgramShadow;
      } else if (isEntitiesGlowing) {
         program = ProgramEntitiesGlowing;
      }

      if (activeProgram != program) {
         updateAlphaBlend(activeProgram, program);
         activeProgram = program;
         int var4 = program.getId();
         activeProgramID = var4;
         ARBShaderObjects.glUseProgramObjectARB(var4);
         if (checkGLError("useProgram") != 0) {
            program.setId(0);
            var4 = program.getId();
            activeProgramID = var4;
            ARBShaderObjects.glUseProgramObjectARB(var4);
         }

         shaderUniforms.setProgram(var4);
         if (customUniforms != null) {
            customUniforms.setProgram(var4);
         }

         if (var4 != 0) {
            IntBuffer var5 = program.getDrawBuffers();
            if (isRenderingDfb) {
               setDrawBuffers(var5);
            }

            activeCompositeMipmapSetting = program.getCompositeMipmapSetting();
            switch (program.getProgramStage()) {
               case GBUFFERS :
                  setProgramUniform1i(uniform_texture, 0);
                  setProgramUniform1i(uniform_lightmap, 1);
                  setProgramUniform1i(uniform_normals, 2);
                  setProgramUniform1i(uniform_specular, 3);
                  setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
                  setProgramUniform1i(uniform_watershadow, 4);
                  setProgramUniform1i(uniform_shadowtex0, 4);
                  setProgramUniform1i(uniform_shadowtex1, 5);
                  setProgramUniform1i(uniform_depthtex0, 6);
                  if (customTexturesGbuffers != null || hasDeferredPrograms) {
                     setProgramUniform1i(uniform_gaux1, 7);
                     setProgramUniform1i(uniform_gaux2, 8);
                     setProgramUniform1i(uniform_gaux3, 9);
                     setProgramUniform1i(uniform_gaux4, 10);
                  }

                  setProgramUniform1i(uniform_depthtex1, 11);
                  setProgramUniform1i(uniform_shadowcolor, 13);
                  setProgramUniform1i(uniform_shadowcolor0, 13);
                  setProgramUniform1i(uniform_shadowcolor1, 14);
                  setProgramUniform1i(uniform_noisetex, 15);
                  break;
               case DEFERRED :
               case COMPOSITE :
                  setProgramUniform1i(uniform_gcolor, 0);
                  setProgramUniform1i(uniform_gdepth, 1);
                  setProgramUniform1i(uniform_gnormal, 2);
                  setProgramUniform1i(uniform_composite, 3);
                  setProgramUniform1i(uniform_gaux1, 7);
                  setProgramUniform1i(uniform_gaux2, 8);
                  setProgramUniform1i(uniform_gaux3, 9);
                  setProgramUniform1i(uniform_gaux4, 10);
                  setProgramUniform1i(uniform_colortex0, 0);
                  setProgramUniform1i(uniform_colortex1, 1);
                  setProgramUniform1i(uniform_colortex2, 2);
                  setProgramUniform1i(uniform_colortex3, 3);
                  setProgramUniform1i(uniform_colortex4, 7);
                  setProgramUniform1i(uniform_colortex5, 8);
                  setProgramUniform1i(uniform_colortex6, 9);
                  setProgramUniform1i(uniform_colortex7, 10);
                  setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
                  setProgramUniform1i(uniform_watershadow, 4);
                  setProgramUniform1i(uniform_shadowtex0, 4);
                  setProgramUniform1i(uniform_shadowtex1, 5);
                  setProgramUniform1i(uniform_gdepthtex, 6);
                  setProgramUniform1i(uniform_depthtex0, 6);
                  setProgramUniform1i(uniform_depthtex1, 11);
                  setProgramUniform1i(uniform_depthtex2, 12);
                  setProgramUniform1i(uniform_shadowcolor, 13);
                  setProgramUniform1i(uniform_shadowcolor0, 13);
                  setProgramUniform1i(uniform_shadowcolor1, 14);
                  setProgramUniform1i(uniform_noisetex, 15);
                  break;
               case SHADOW :
                  setProgramUniform1i(uniform_tex, 0);
                  setProgramUniform1i(uniform_texture, 0);
                  setProgramUniform1i(uniform_lightmap, 1);
                  setProgramUniform1i(uniform_normals, 2);
                  setProgramUniform1i(uniform_specular, 3);
                  setProgramUniform1i(uniform_shadow, waterShadowEnabled ? 5 : 4);
                  setProgramUniform1i(uniform_watershadow, 4);
                  setProgramUniform1i(uniform_shadowtex0, 4);
                  setProgramUniform1i(uniform_shadowtex1, 5);
                  if (customTexturesGbuffers != null) {
                     setProgramUniform1i(uniform_gaux1, 7);
                     setProgramUniform1i(uniform_gaux2, 8);
                     setProgramUniform1i(uniform_gaux3, 9);
                     setProgramUniform1i(uniform_gaux4, 10);
                  }

                  setProgramUniform1i(uniform_shadowcolor, 13);
                  setProgramUniform1i(uniform_shadowcolor0, 13);
                  setProgramUniform1i(uniform_shadowcolor1, 14);
                  setProgramUniform1i(uniform_noisetex, 15);
            }

            ItemStack var6 = mc.thePlayer != null ? mc.thePlayer.getHeldItem() : null;
            Item var7 = var6 != null ? var6.getItem() : null;
            int var8 = -1;
            Block var9 = null;
            if (var7 != null) {
               var8 = Item.itemRegistry.getIDForObject(var7);
               var9 = Block.blockRegistry.getObjectById(var8);
               var8 = ItemAliases.getItemAliasId(var8);
            }

            int var10 = var9 != null ? var9.getLightValue() : 0;
            setProgramUniform1i(uniform_heldItemId, var8);
            setProgramUniform1i(uniform_heldBlockLightValue, var10);
            setProgramUniform1i(uniform_fogMode, fogEnabled ? fogMode : 0);
            setProgramUniform1f(uniform_fogDensity, fogEnabled ? fogDensity : 0.0F);
            setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
            setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
            setProgramUniform1i(uniform_worldTime, (int)(worldTime % 24000L));
            setProgramUniform1i(uniform_worldDay, (int)(worldTime / 24000L));
            setProgramUniform1i(uniform_moonPhase, moonPhase);
            setProgramUniform1i(uniform_frameCounter, frameCounter);
            setProgramUniform1f(uniform_frameTime, frameTime);
            setProgramUniform1f(uniform_frameTimeCounter, frameTimeCounter);
            setProgramUniform1f(uniform_sunAngle, sunAngle);
            setProgramUniform1f(uniform_shadowAngle, shadowAngle);
            setProgramUniform1f(uniform_rainStrength, rainStrength);
            setProgramUniform1f(uniform_aspectRatio, (float)renderWidth / renderHeight);
            setProgramUniform1f(uniform_viewWidth, renderWidth);
            setProgramUniform1f(uniform_viewHeight, renderHeight);
            setProgramUniform1f(uniform_near, 0.05F);
            setProgramUniform1f(uniform_far, mc.gameSettings.renderDistanceChunks * 16);
            setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
            setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
            setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
            setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
            setProgramUniform3f(uniform_previousCameraPosition, (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
            setProgramUniform3f(uniform_cameraPosition, (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
            setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
            setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
            setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
            setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
            setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
            setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
            if (usedShadowDepthBuffers > 0) {
               setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
               setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
               setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
               setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
            }

            setProgramUniform1f(uniform_wetness, wetness);
            setProgramUniform1f(uniform_eyeAltitude, eyePosY);
            setProgramUniform2i(uniform_eyeBrightness, eyeBrightness & 65535, eyeBrightness >> 16);
            setProgramUniform2i(uniform_eyeBrightnessSmooth, Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
            setProgramUniform2i(uniform_terrainTextureSize, terrainTextureSize[0], terrainTextureSize[1]);
            setProgramUniform1i(uniform_terrainIconSize, terrainIconSize);
            setProgramUniform1i(uniform_isEyeInWater, isEyeInWater);
            setProgramUniform1f(uniform_nightVision, nightVision);
            setProgramUniform1f(uniform_blindness, blindness);
            setProgramUniform1f(uniform_screenBrightness, mc.gameSettings.gammaSetting);
            setProgramUniform1i(uniform_hideGUI, mc.gameSettings.hideGUI ? 1 : 0);
            setProgramUniform1f(uniform_centerDepthSmooth, centerDepthSmooth);
            setProgramUniform2i(uniform_atlasSize, atlasSizeX, atlasSizeY);
            if (customUniforms != null) {
               customUniforms.update();
            }

            checkGLError("end useProgram");
         }
      }
   }

   private static void updateAlphaBlend(Program program1, Program program2) {
      if (program1.getAlphaState() != null) {
         GlStateManager.unlockAlpha();
      }

      if (program1.getBlendState() != null) {
         GlStateManager.unlockBlend();
      }

      GlAlphaState var5 = program2.getAlphaState();
      if (var5 != null) {
         GlStateManager.lockAlpha(var5);
      }

      GlBlendState var6 = program2.getBlendState();
      if (var6 != null) {
         GlStateManager.lockBlend(var6);
      }
   }

   private static void setProgramUniform1i(ShaderUniform1i shaderUniform1i, int integer) {
      shaderUniform1i.setValue(integer);
   }

   private static void setProgramUniform2i(ShaderUniform2i shaderUniform2i, int integer2, int integer3) {
      shaderUniform2i.setValue(integer2, integer3);
   }

   private static void setProgramUniform1f(ShaderUniform1f shaderUniform1f, float float2) {
      shaderUniform1f.setValue(float2);
   }

   private static void setProgramUniform3f(ShaderUniform3f shaderUniform3f, float float2, float float3, float float4) {
      shaderUniform3f.setValue(float2, float3, float4);
   }

   private static void setProgramUniformMatrix4ARB(ShaderUniformM4 shaderUniformM4, boolean boolean2, FloatBuffer floatBuffer) {
      shaderUniformM4.setValue(boolean2, floatBuffer);
   }

   public static int getBufferIndexFromString(String string) {
      return string.equals("colortex0") || string.equals("gcolor")
         ? 0
         : (
            string.equals("colortex1") || string.equals("gdepth")
               ? 1
               : (
                  string.equals("colortex2") || string.equals("gnormal")
                     ? 2
                     : (
                        string.equals("colortex3") || string.equals("composite")
                           ? 3
                           : (
                              string.equals("colortex4") || string.equals("gaux1")
                                 ? 4
                                 : (
                                    string.equals("colortex5") || string.equals("gaux2")
                                       ? 5
                                       : (
                                          !string.equals("colortex6") && !string.equals("gaux3")
                                             ? (!string.equals("colortex7") && !string.equals("gaux4") ? -1 : 7)
                                             : 6
                                       )
                                 )
                           )
                     )
               )
         );
   }

   private static int getTextureFormatFromString(String string) {
      string = string.trim();

      for (int var4 = 0; var4 < formatNames.length; var4++) {
         String var5 = formatNames[var4];
         if (string.equals(var5)) {
            return formatIds[var4];
         }
      }

      return 0;
   }

   private static void setupNoiseTexture() {
      if (noiseTexture == null && noiseTexturePath != null) {
         noiseTexture = loadCustomTexture(15, noiseTexturePath);
      }

      if (noiseTexture == null) {
         noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
      }
   }

   private static void loadEntityDataMap() {
      mapBlockToEntityData = new IdentityHashMap<>(300);
      if (mapBlockToEntityData.isEmpty()) {
         for (ResourceLocation var4 : Block.blockRegistry.getKeys()) {
            Block var5 = Block.blockRegistry.getObject(var4);
            int var6 = Block.blockRegistry.getIDForObject(var5);
            mapBlockToEntityData.put(var5, var6);
         }
      }

      BufferedReader var13 = null;

      try {
         var13 = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
      } catch (Exception var11) {
      }

      if (var13 != null) {
         String var14;
         try {
            while ((var14 = var13.readLine()) != null) {
               Matcher var15 = fj.matcher(var14);
               if (var15.matches()) {
                  String var16 = var15.group(1);
                  int var8 = Integer.parseInt(var15.group(2));
                  Block var9 = Block.getBlockFromName(var16);
                  if (var9 != null) {
                     mapBlockToEntityData.put(var9, var8);
                  } else {
                     SMCLog.warning("Unknown block name %s", var16);
                  }
               } else {
                  SMCLog.warning("unmatched %s\n", var14);
               }
            }
         } catch (Exception var12) {
            SMCLog.warning("Error parsing mc_Entity_x.txt");
         }
      }

      if (var13 != null) {
         try {
            var13.close();
         } catch (Exception var10) {
         }
      }
   }

   private static IntBuffer fillIntBufferZero(IntBuffer intBuffer) {
      int var4 = intBuffer.limit();

      for (int var5 = intBuffer.position(); var5 < var4; var5++) {
         intBuffer.put(var5, 0);
      }

      return intBuffer;
   }

   public static void uninit() {
      if (isShaderPackInitialized) {
         checkGLError("Shaders.uninit pre");

         for (Program var6 : ProgramsAll) {
            if (var6.getRef() != 0) {
               ARBShaderObjects.glDeleteObjectARB(var6.getRef());
               checkGLError("del programRef");
            }

            var6.setRef(0);
            var6.setId(0);
            var6.setDrawBufSettings(null);
            var6.setDrawBuffers(null);
            var6.setCompositeMipmapSetting(0);
         }

         hasDeferredPrograms = false;
         if (dfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
            dfb = 0;
            checkGLError("del dfb");
         }

         if (sfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
            sfb = 0;
            checkGLError("del sfb");
         }

         if (dfbDepthTextures != null) {
            GlStateManager.deleteTextures(dfbDepthTextures);
            fillIntBufferZero(dfbDepthTextures);
            checkGLError("del dfbDepthTextures");
         }

         if (dfbColorTextures != null) {
            GlStateManager.deleteTextures(dfbColorTextures);
            fillIntBufferZero(dfbColorTextures);
            checkGLError("del dfbTextures");
         }

         if (sfbDepthTextures != null) {
            GlStateManager.deleteTextures(sfbDepthTextures);
            fillIntBufferZero(sfbDepthTextures);
            checkGLError("del shadow depth");
         }

         if (sfbColorTextures != null) {
            GlStateManager.deleteTextures(sfbColorTextures);
            fillIntBufferZero(sfbColorTextures);
            checkGLError("del shadow color");
         }

         if (dfbDrawBuffers != null) {
            fillIntBufferZero(dfbDrawBuffers);
         }

         if (noiseTexture != null) {
            noiseTexture.deleteTexture();
            noiseTexture = null;
         }

         SMCLog.info("Uninit");
         shadowPassInterval = 0;
         shouldSkipDefaultShadow = false;
         isShaderPackInitialized = false;
         checkGLError("Shaders.uninit");
      }
   }

   public static void scheduleResize() {
      renderDisplayHeight = 0;
   }

   public static void scheduleResizeShadow() {
      needResizeShadow = true;
   }

   private static void resize() {
      renderDisplayWidth = mc.displayWidth;
      renderDisplayHeight = mc.displayHeight;
      renderWidth = Math.round(renderDisplayWidth * configRenderResMul);
      renderHeight = Math.round(renderDisplayHeight * configRenderResMul);
      setupFrameBuffer();
   }

   private static void resizeShadow() {
      needResizeShadow = false;
      shadowMapWidth = Math.round(spShadowMapWidth * configShadowResMul);
      shadowMapHeight = Math.round(spShadowMapHeight * configShadowResMul);
      setupShadowFrameBuffer();
   }

   private static void setupFrameBuffer() {
      if (dfb != 0) {
         EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
         GlStateManager.deleteTextures(dfbDepthTextures);
         GlStateManager.deleteTextures(dfbColorTextures);
      }

      dfb = EXTFramebufferObject.glGenFramebuffersEXT();
      GL11.glGenTextures(dfbDepthTextures.clear().limit(usedDepthBuffers));
      GL11.glGenTextures(dfbColorTextures.clear().limit(16));
      dfbDepthTextures.position(0);
      dfbColorTextures.position(0);
      EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, dfb);
      GL20.glDrawBuffers(0);
      GL11.glReadBuffer(GL11.GL_ZERO);

      for (int var3 = 0; var3 < usedDepthBuffers; var3++) {
         GlStateManager.bindTexture(dfbDepthTextures.get(var3));
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_DEPTH_TEXTURE_MODE, GL11.GL_LUMINANCE);
         GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, renderWidth, renderHeight, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer)null);
      }

      EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, GL11.GL_TEXTURE_2D, dfbDepthTextures.get(0), 0);
      GL20.glDrawBuffers(dfbDrawBuffers);
      GL11.glReadBuffer(GL11.GL_ZERO);
      checkGLError("FT d");

      for (int var5 = 0; var5 < usedColorBuffers; var5++) {
         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(var5));
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
         GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, gbuffersFormat[var5], renderWidth, renderHeight, 0, Method449(gbuffersFormat[var5]), GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer)null);
         EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var5, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getA(var5), 0);
         checkGLError("FT c");
      }

      for (int var6 = 0; var6 < usedColorBuffers; var6++) {
         GlStateManager.bindTexture(dfbColorTexturesFlip.getB(var6));
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
         GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, gbuffersFormat[var6], renderWidth, renderHeight, 0, Method449(gbuffersFormat[var6]), GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer)null);
         checkGLError("FT ca");
      }

      int var7 = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
      if (var7 == 36058) {
         printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");

         for (int var4 = 0; var4 < usedColorBuffers; var4++) {
            GlStateManager.bindTexture(dfbColorTexturesFlip.getA(var4));
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, renderWidth, renderHeight, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer)null);
            EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var4, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getA(var4), 0);
            checkGLError("FT c");
         }

         var7 = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
         if (var7 == 36053) {
            SMCLog.info("complete");
         }
      }

      GlStateManager.bindTexture(0);
      if (var7 != 36053) {
         printChatAndLogError("[Shaders] Error: Failed creating framebuffer! (Status " + var7 + ")");
      } else {
         SMCLog.info("Framebuffer created.");
      }
   }

   private static int Method449(int integer) {
      switch (integer) {
         case 33333:
         case 33334:
         case 33339:
         case 33340:
         case 36208:
         case 36209:
         case 36226:
         case 36227:
            return 36251;
         default:
            return 32993;
      }
   }

   private static void setupShadowFrameBuffer() {
      if (usedShadowDepthBuffers != 0) {
         if (sfb != 0) {
            EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
            GlStateManager.deleteTextures(sfbDepthTextures);
            GlStateManager.deleteTextures(sfbColorTextures);
         }

         sfb = EXTFramebufferObject.glGenFramebuffersEXT();
         EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, sfb);
         GL11.glDrawBuffer(0);
         GL11.glReadBuffer(GL11.GL_ZERO);
         GL11.glGenTextures(sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
         GL11.glGenTextures(sfbColorTextures.clear().limit(usedShadowColorBuffers));
         sfbDepthTextures.position(0);
         sfbColorTextures.position(0);

         for (int var3 = 0; var3 < usedShadowDepthBuffers; var3++) {
            GlStateManager.bindTexture(sfbDepthTextures.get(var3));
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            int var4 = shadowFilterNearest[var3] ? 9728 : 9729;
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var4);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var4);
            if (shadowHardwareFilteringEnabled[var3]) {
               GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_COMPARE_MODE, GL14.GL_COMPARE_R_TO_TEXTURE);
            }

            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_DEPTH_COMPONENT, shadowMapWidth, shadowMapHeight, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer)null);
         }

         EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, GL11.GL_TEXTURE_2D, sfbDepthTextures.get(0), 0);
         checkGLError("FT sd");

         for (int var5 = 0; var5 < usedShadowColorBuffers; var5++) {
            GlStateManager.bindTexture(sfbColorTextures.get(var5));
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
            GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            int var7 = shadowColorFilterNearest[var5] ? 9728 : 9729;
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var7);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var7);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, shadowMapWidth, shadowMapHeight, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer)null);
            EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var5, GL11.GL_TEXTURE_2D, sfbColorTextures.get(var5), 0);
            checkGLError("FT sc");
         }

         GlStateManager.bindTexture(0);
         if (usedShadowColorBuffers > 0) {
            GL20.glDrawBuffers(sfbDrawBuffers);
         }

         int var6 = EXTFramebufferObject.glCheckFramebufferStatusEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT);
         if (var6 != 36053) {
            printChatAndLogError("[Shaders] Error: Failed creating shadow framebuffer! (Status " + var6 + ")");
         } else {
            SMCLog.info("Shadow framebuffer created.");
         }
      }
   }

   public static void beginRender(Minecraft minecraft, float float2, long long3) {
      checkGLError("pre beginRender");
      checkWorldChanged(mc.theWorld);
      mc = minecraft;
      mc.mcProfiler.startSection("init");
      entityRenderer = mc.entityRenderer;
      if (!isShaderPackInitialized) {
         try {
            init();
         } catch (IllegalStateException var14) {
            if (Config.normalize(var14.getMessage()).equals("Function is not supported")) {
               printChatAndLogError("[Shaders] Error: " + var14.getMessage());
               var14.printStackTrace();
               setShaderPack("OFF");
               return;
            }
         }
      }

      if (mc.displayWidth != renderDisplayWidth || mc.displayHeight != renderDisplayHeight) {
         resize();
      }

      if (needResizeShadow) {
         resizeShadow();
      }

      worldTime = mc.theWorld.getWorldTime();
      diffWorldTime = (worldTime - lastWorldTime) % 24000L;
      if (diffWorldTime < 0L) {
         diffWorldTime += 24000L;
      }

      lastWorldTime = worldTime;
      moonPhase = mc.theWorld.getMoonPhase();
      frameCounter++;
      if (frameCounter >= 720720) {
         frameCounter = 0;
      }

      systemTime = System.currentTimeMillis();
      if (lastSystemTime == 0L) {
         lastSystemTime = systemTime;
      }

      diffSystemTime = systemTime - lastSystemTime;
      lastSystemTime = systemTime;
      frameTime = (float)diffSystemTime / 1000.0F;
      frameTimeCounter = frameTimeCounter + frameTime;
      frameTimeCounter %= 3600.0F;
      rainStrength = minecraft.theWorld.getRainStrength(float2);
      float var7 = (float)diffSystemTime * 0.01F;
      float var8 = (float)Math.exp(Math.log(0.5) * var7 / (wetness < rainStrength ? drynessHalfLife : wetnessHalfLife));
      wetness = wetness * var8 + rainStrength * (1.0F - var8);
      Entity var9 = mc.getRenderViewEntity();
      if (var9 != null) {
         isSleeping = var9 instanceof EntityLivingBase && ((EntityLivingBase)var9).isPlayerSleeping();
         eyePosY = (float)var9.posY * float2 + (float)var9.lastTickPosY * (1.0F - float2);
         eyeBrightness = var9.getBrightnessForRender(float2);
         var8 = (float)diffSystemTime * 0.01F;
         float var10 = (float)Math.exp(Math.log(0.5) * var8 / eyeBrightnessHalflife);
         eyeBrightnessFadeX = eyeBrightnessFadeX * var10 + (eyeBrightness & 65535) * (1.0F - var10);
         eyeBrightnessFadeY = eyeBrightnessFadeY * var10 + (eyeBrightness >> 16) * (1.0F - var10);
         Material var12 = ActiveRenderInfo.getBlockAtEntityViewpoint(mc.theWorld, var9, float2).getMaterial();
         if (var12 == Material.water) {
            isEyeInWater = 1;
         } else if (var12 == Material.lava) {
            isEyeInWater = 2;
         } else {
            isEyeInWater = 0;
         }

         if (mc.thePlayer != null) {
            nightVision = 0.0F;
            if (mc.thePlayer.isPotionActive(Potion.nightVision)) {
               nightVision = Config.getMinecraft().entityRenderer.getNightVisionBrightness(mc.thePlayer, float2);
            }

            blindness = 0.0F;
            if (mc.thePlayer.isPotionActive(Potion.blindness)) {
               blindness = Config.Method256(mc.thePlayer.getActivePotionEffect(Potion.blindness).getDuration() / 20.0F, 0.0F, 1.0F);
            }
         }

         Vec3 var13 = CustomColors.getWorldSkyColor(mc.theWorld.getSkyColor(var9, float2), currentWorld, var9, float2);
         skyColorR = (float)var13.xCoord;
         skyColorG = (float)var13.yCoord;
         skyColorB = (float)var13.zCoord;
      }

      isRenderingWorld = true;
      isCompositeRendered = false;
      isShadowPass = false;
      isHandRenderedMain = false;
      isHandRenderedOff = false;
      skipRenderHandMain = false;
      skipRenderHandOff = false;
      bindGbuffersTextures();
      previousCameraPositionX = cameraPositionX;
      previousCameraPositionY = cameraPositionY;
      previousCameraPositionZ = cameraPositionZ;
      previousProjection.position(0);
      projection.position(0);
      previousProjection.put(projection);
      previousProjection.position(0);
      projection.position(0);
      previousModelView.position(0);
      modelView.position(0);
      previousModelView.put(modelView);
      previousModelView.position(0);
      modelView.position(0);
      checkGLError("beginRender");
      ShadersRender.renderShadowMap(entityRenderer, 0, float2, long3);
      mc.mcProfiler.endSection();
      EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, dfb);

      for (int var16 = 0; var16 < usedColorBuffers; var16++) {
         EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var16, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getA(var16), 0);
      }

      checkGLError("end beginRender");
   }

   private static void bindGbuffersTextures() {
      if (usedShadowDepthBuffers >= 1) {
         GlStateManager.setActiveTexture(33988);
         GlStateManager.bindTexture(sfbDepthTextures.get(0));
         if (usedShadowDepthBuffers >= 2) {
            GlStateManager.setActiveTexture(33989);
            GlStateManager.bindTexture(sfbDepthTextures.get(1));
         }
      }

      GlStateManager.setActiveTexture(33984);

      for (int var3 = 0; var3 < usedColorBuffers; var3++) {
         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(var3));
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
         GlStateManager.bindTexture(dfbColorTexturesFlip.getB(var3));
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
      }

      GlStateManager.bindTexture(0);

      for (int var4 = 0; var4 < 4 && 4 + var4 < usedColorBuffers; var4++) {
         GlStateManager.setActiveTexture(33991 + var4);
         GlStateManager.bindTexture(dfbColorTexturesFlip.getA(4 + var4));
      }

      GlStateManager.setActiveTexture(33990);
      GlStateManager.bindTexture(dfbDepthTextures.get(0));
      if (usedDepthBuffers >= 2) {
         GlStateManager.setActiveTexture(33995);
         GlStateManager.bindTexture(dfbDepthTextures.get(1));
         if (usedDepthBuffers >= 3) {
            GlStateManager.setActiveTexture(33996);
            GlStateManager.bindTexture(dfbDepthTextures.get(2));
         }
      }

      for (int var5 = 0; var5 < usedShadowColorBuffers; var5++) {
         GlStateManager.setActiveTexture(33997 + var5);
         GlStateManager.bindTexture(sfbColorTextures.get(var5));
      }

      if (noiseTextureEnabled) {
         GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
         GlStateManager.bindTexture(noiseTexture.getTextureId());
      }

      bindCustomTextures(customTexturesGbuffers);
      GlStateManager.setActiveTexture(33984);
   }

   public static void checkWorldChanged(World world) {
      if (currentWorld != world) {
         World var4 = currentWorld;
         currentWorld = world;
         setCameraOffset(mc.getRenderViewEntity());
         int var5 = getDimensionId(var4);
         int var6 = getDimensionId(world);
         if (var6 != var5) {
            boolean var7 = shaderPackDimensions.contains(var5);
            boolean var8 = shaderPackDimensions.contains(var6);
            if (var7 || var8) {
               uninit();
            }
         }

         Smoother.resetValues();
      }
   }

   private static int getDimensionId(World world) {
      return world == null ? Integer.MIN_VALUE : world.provider.getDimensionId();
   }

   public static void beginRenderPass(int integer, float float2, long long3) {
      if (!isShadowPass) {
         EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, dfb);
         GL11.glViewport(0, 0, renderWidth, renderHeight);
         activeDrawBuffers = null;
         ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
         useProgram(ProgramTextured);
         checkGLError("end beginRenderPass");
      }
   }

   public static void setViewport(int integer1, int integer2, int integer3, int integer4) {
      GlStateManager.colorMask(true, true, true, true);
      if (isShadowPass) {
         GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
      } else {
         GL11.glViewport(0, 0, renderWidth, renderHeight);
         EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, dfb);
         isRenderingDfb = true;
         GlStateManager.enableCull();
         GlStateManager.enableDepth();
         setDrawBuffers(drawBuffersNone);
         useProgram(ProgramTextured);
         checkGLError("beginRenderPass");
      }
   }

   public static void setFogMode(int integer) {
      fogMode = integer;
      if (fogEnabled) {
         setProgramUniform1i(uniform_fogMode, integer);
      }
   }

   public static void setFogColor(float float1, float float2, float float3) {
      fogColorR = float1;
      fogColorG = float2;
      fogColorB = float3;
      setProgramUniform3f(uniform_fogColor, fogColorR, fogColorG, fogColorB);
   }

   public static void setClearColor(float float1, float float2, float float3, float float4) {
      GlStateManager.clearColor(float1, float2, float3, float4);
      clearColorR = float1;
      clearColorG = float2;
      clearColorB = float3;
   }

   public static void clearRenderBuffer() {
      if (isShadowPass) {
         checkGLError("shadow clear pre");
         EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, GL11.GL_TEXTURE_2D, sfbDepthTextures.get(0), 0);
         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
         GL20.glDrawBuffers(ProgramShadow.getDrawBuffers());
         checkFramebufferStatus("shadow clear");
         GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
         checkGLError("shadow clear");
      } else {
         checkGLError("clear pre");
         if (gbuffersClear[0]) {
            Vector4f var3 = gbuffersClearColor[0];
            if (var3 != null) {
               GL11.glClearColor(var3.getX(), var3.getY(), var3.getZ(), var3.getW());
            }

            if (dfbColorTexturesFlip.isChanged(0)) {
               EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getB(0), 0);
               GL20.glDrawBuffers(36064);
               GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
               EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getA(0), 0);
            }

            GL20.glDrawBuffers(36064);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
         }

         if (gbuffersClear[1]) {
            GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
            Vector4f var5 = gbuffersClearColor[1];
            if (var5 != null) {
               GL11.glClearColor(var5.getX(), var5.getY(), var5.getZ(), var5.getW());
            }

            if (dfbColorTexturesFlip.isChanged(1)) {
               EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT1_EXT, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getB(1), 0);
               GL20.glDrawBuffers(36065);
               GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
               EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT1_EXT, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getA(1), 0);
            }

            GL20.glDrawBuffers(36065);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
         }

         for (int var6 = 2; var6 < usedColorBuffers; var6++) {
            if (gbuffersClear[var6]) {
               GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
               Vector4f var4 = gbuffersClearColor[var6];
               if (var4 != null) {
                  GL11.glClearColor(var4.getX(), var4.getY(), var4.getZ(), var4.getW());
               }

               if (dfbColorTexturesFlip.isChanged(var6)) {
                  EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var6, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getB(var6), 0);
                  GL20.glDrawBuffers(36064 + var6);
                  GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                  EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var6, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getA(var6), 0);
               }

               GL20.glDrawBuffers(36064 + var6);
               GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            }
         }

         setDrawBuffers(dfbDrawBuffers);
         checkFramebufferStatus("clear");
         checkGLError("clear");
      }
   }

   public static void setCamera(float float1) {
      Entity var3 = mc.getRenderViewEntity();
      double var4 = var3.lastTickPosX + (var3.posX - var3.lastTickPosX) * float1;
      double var6 = var3.lastTickPosY + (var3.posY - var3.lastTickPosY) * float1;
      double var8 = var3.lastTickPosZ + (var3.posZ - var3.lastTickPosZ) * float1;
      updateCameraOffset(var3);
      cameraPositionX = var4 - cameraOffsetX;
      cameraPositionY = var6;
      cameraPositionZ = var8 - cameraOffsetZ;
      GL11.glGetFloat(2983, projection.position(0));
      SMath.invertMat4FBFA(projectionInverse.position(0), projection.position(0), faProjectionInverse, faProjection);
      projection.position(0);
      projectionInverse.position(0);
      GL11.glGetFloat(2982, modelView.position(0));
      SMath.invertMat4FBFA(modelViewInverse.position(0), modelView.position(0), faModelViewInverse, faModelView);
      modelView.position(0);
      modelViewInverse.position(0);
      checkGLError("setCamera");
   }

   private static void updateCameraOffset(Entity entity) {
      double var4 = Math.abs(cameraPositionX - previousCameraPositionX);
      double var6 = Math.abs(cameraPositionZ - previousCameraPositionZ);
      double var8 = Math.abs(cameraPositionX);
      double var10 = Math.abs(cameraPositionZ);
      if (var4 > 1000.0 || var6 > 1000.0 || var8 > 1000000.0 || var10 > 1000000.0) {
         setCameraOffset(entity);
      }
   }

   private static void setCameraOffset(Entity entity) {
      if (entity == null) {
         cameraOffsetX = 0;
         cameraOffsetZ = 0;
      } else {
         cameraOffsetX = (int)entity.posX / 1000 * 1000;
         cameraOffsetZ = (int)entity.posZ / 1000 * 1000;
      }
   }

   public static void setCameraShadow(float float1) {
      Entity var4 = mc.getRenderViewEntity();
      double var5 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * float1;
      double var7 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * float1;
      double var9 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * float1;
      updateCameraOffset(var4);
      cameraPositionX = var5 - cameraOffsetX;
      cameraPositionY = var7;
      cameraPositionZ = var9 - cameraOffsetZ;
      GL11.glGetFloat(2983, projection.position(0));
      SMath.invertMat4FBFA(projectionInverse.position(0), projection.position(0), faProjectionInverse, faProjection);
      projection.position(0);
      projectionInverse.position(0);
      GL11.glGetFloat(2982, modelView.position(0));
      SMath.invertMat4FBFA(modelViewInverse.position(0), modelView.position(0), faModelViewInverse, faModelView);
      modelView.position(0);
      modelViewInverse.position(0);
      GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
      GL11.glMatrixMode(GL11.GL_PROJECTION);
      GL11.glLoadIdentity();
      if (shadowMapIsOrtho) {
         GL11.glOrtho(-shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, shadowMapHalfPlane, 0.05F, 256.0);
      } else {
         GLU.gluPerspective(shadowMapFOV, (float)shadowMapWidth / shadowMapHeight, 0.05F, 256.0F);
      }

      GL11.glMatrixMode(GL11.GL_MODELVIEW);
      GL11.glLoadIdentity();
      GL11.glTranslatef(0.0F, 0.0F, -100.0F);
      GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
      celestialAngle = mc.theWorld.getCelestialAngle(float1);
      sunAngle = celestialAngle < 0.75F ? celestialAngle + 0.25F : celestialAngle - 0.75F;
      float var11 = celestialAngle * -360.0F;
      float var12 = 0 > 0 ? var11 % 0.0F - 0.0F : 0.0F;
      if (sunAngle <= 0.5) {
         GL11.glRotatef(var11 - var12, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
         shadowAngle = sunAngle;
      } else {
         GL11.glRotatef(var11 + 180.0F - var12, 0.0F, 0.0F, 1.0F);
         GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
         shadowAngle = sunAngle - 0.5F;
      }

      if (shadowMapIsOrtho) {
         float var13 = shadowIntervalSize;
         float var14 = var13 / 2.0F;
         GL11.glTranslatef((float)var5 % var13 - var14, (float)var7 % var13 - var14, (float)var9 % var13 - var14);
      }

      float var20 = sunAngle * (float) (Math.PI * 2);
      float var21 = (float)Math.cos(var20);
      float var15 = (float)Math.sin(var20);
      float var16 = sunPathRotation * (float) (Math.PI * 2);
      float var17 = var21;
      float var18 = var15 * (float)Math.cos(var16);
      float var19 = var15 * (float)Math.sin(var16);
      if (sunAngle > 0.5) {
         var17 = -var21;
         var18 = -var18;
         var19 = -var19;
      }

      shadowLightPositionVector[0] = var17;
      shadowLightPositionVector[1] = var18;
      shadowLightPositionVector[2] = var19;
      shadowLightPositionVector[3] = 0.0F;
      GL11.glGetFloat(2983, shadowProjection.position(0));
      SMath.invertMat4FBFA(shadowProjectionInverse.position(0), shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
      shadowProjection.position(0);
      shadowProjectionInverse.position(0);
      GL11.glGetFloat(2982, shadowModelView.position(0));
      SMath.invertMat4FBFA(shadowModelViewInverse.position(0), shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
      shadowModelView.position(0);
      shadowModelViewInverse.position(0);
      setProgramUniformMatrix4ARB(uniform_gbufferProjection, false, projection);
      setProgramUniformMatrix4ARB(uniform_gbufferProjectionInverse, false, projectionInverse);
      setProgramUniformMatrix4ARB(uniform_gbufferPreviousProjection, false, previousProjection);
      setProgramUniformMatrix4ARB(uniform_gbufferModelView, false, modelView);
      setProgramUniformMatrix4ARB(uniform_gbufferModelViewInverse, false, modelViewInverse);
      setProgramUniformMatrix4ARB(uniform_gbufferPreviousModelView, false, previousModelView);
      setProgramUniformMatrix4ARB(uniform_shadowProjection, false, shadowProjection);
      setProgramUniformMatrix4ARB(uniform_shadowProjectionInverse, false, shadowProjectionInverse);
      setProgramUniformMatrix4ARB(uniform_shadowModelView, false, shadowModelView);
      setProgramUniformMatrix4ARB(uniform_shadowModelViewInverse, false, shadowModelViewInverse);
      mc.gameSettings.thirdPersonView = 1;
      checkGLError("setCamera");
   }

   public static void preCelestialRotate() {
      GL11.glRotatef(sunPathRotation, 0.0F, 0.0F, 1.0F);
      checkGLError("preCelestialRotate");
   }

   public static void postCelestialRotate() {
      FloatBuffer var3 = tempMatrixDirectBuffer;
      var3.clear();
      GL11.glGetFloat(2982, var3);
      var3.get(tempMat, 0, 16);
      SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
      SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
      System.arraycopy(shadowAngle == sunAngle ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
      setProgramUniform3f(uniform_sunPosition, sunPosition[0], sunPosition[1], sunPosition[2]);
      setProgramUniform3f(uniform_moonPosition, moonPosition[0], moonPosition[1], moonPosition[2]);
      setProgramUniform3f(uniform_shadowLightPosition, shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
      if (customUniforms != null) {
         customUniforms.update();
      }

      checkGLError("postCelestialRotate");
   }

   public static void setUpPosition() {
      FloatBuffer var3 = tempMatrixDirectBuffer;
      var3.clear();
      GL11.glGetFloat(2982, var3);
      var3.get(tempMat, 0, 16);
      SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
      setProgramUniform3f(uniform_upPosition, upPosition[0], upPosition[1], upPosition[2]);
      if (customUniforms != null) {
         customUniforms.update();
      }
   }

   public static void genCompositeMipmap() {
      if (hasGlGenMipmap) {
         for (int var3 = 0; var3 < usedColorBuffers; var3++) {
            if ((activeCompositeMipmapSetting & 1 << var3) != 0) {
               GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[var3]);
               GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
               GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
            }
         }

         GlStateManager.setActiveTexture(33984);
      }
   }

   public static void drawComposite() {
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      drawCompositeQuad();
      int var3 = activeProgram.getCountInstances();
      if (var3 > 1) {
         for (int var4 = 1; var4 < var3; var4++) {
            uniform_instanceId.setValue(var4);
            drawCompositeQuad();
         }

         uniform_instanceId.setValue(0);
      }
   }

   private static void drawCompositeQuad() {
      if (!Method459()) {
         GL11.glBegin(5);
         GL11.glTexCoord2f(0.0F, 0.0F);
         GL11.glVertex3f(0.0F, 0.0F, 0.0F);
         GL11.glTexCoord2f(1.0F, 0.0F);
         GL11.glVertex3f(1.0F, 0.0F, 0.0F);
         GL11.glTexCoord2f(0.0F, 1.0F);
         GL11.glVertex3f(0.0F, 1.0F, 0.0F);
         GL11.glTexCoord2f(1.0F, 1.0F);
         GL11.glVertex3f(1.0F, 1.0F, 0.0F);
         GL11.glEnd();
      } else {
         GL11.glBegin(7);
         GL11.glTexCoord2f(0.0F, 0.0F);
         GL11.glVertex3f(0.0F, 0.0F, 0.0F);
         GL11.glTexCoord2f(1.0F, 0.0F);
         GL11.glVertex3f(1.0F, 0.0F, 0.0F);
         GL11.glTexCoord2f(1.0F, 1.0F);
         GL11.glVertex3f(1.0F, 1.0F, 0.0F);
         GL11.glTexCoord2f(0.0F, 1.0F);
         GL11.glVertex3f(0.0F, 1.0F, 0.0F);
         GL11.glEnd();
      }
   }

   public static void renderDeferred() {
      if (!isShadowPass) {
         boolean var3 = checkBufferFlip(ProgramDeferredPre);
         if (hasDeferredPrograms) {
            checkGLError("pre-render Deferred");
            renderComposites(ProgramsDeferred, false);
            var3 = true;
         }

         if (var3) {
            bindGbuffersTextures();

            for (int var4 = 0; var4 < usedColorBuffers; var4++) {
               EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var4, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getA(var4), 0);
            }

            if (ProgramWater.getDrawBuffers() != null) {
               setDrawBuffers(ProgramWater.getDrawBuffers());
            } else {
               setDrawBuffers(dfbDrawBuffers);
            }

            GlStateManager.setActiveTexture(33984);
            mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         }
      }
   }

   public static void renderCompositeFinal() {
      if (!isShadowPass) {
         checkBufferFlip(ProgramCompositePre);
         checkGLError("pre-render CompositeFinal");
         renderComposites(ProgramsComposite, true);
      }
   }

   private static boolean checkBufferFlip(Program program) {
      boolean var4 = false;
      Boolean[] var5 = program.getBuffersFlip();

      for (int var6 = 0; var6 < usedColorBuffers; var6++) {
         if (Config.isTrue(var5[var6])) {
            dfbColorTexturesFlip.flip(var6);
            var4 = true;
         }
      }

      return var4;
   }

   private static void renderComposites(Program[] arr, boolean boolean2) {
      if (!isShadowPass) {
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glMatrixMode(GL11.GL_PROJECTION);
         GL11.glPushMatrix();
         GL11.glLoadIdentity();
         GL11.glOrtho(0.0, 1.0, 0.0, 1.0, 0.0, 1.0);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.enableTexture2D();
         GlStateManager.disableAlpha();
         GlStateManager.disableBlend();
         GlStateManager.enableDepth();
         GlStateManager.depthFunc(519);
         GlStateManager.depthMask(false);
         GlStateManager.disableLighting();
         if (usedShadowDepthBuffers >= 1) {
            GlStateManager.setActiveTexture(33988);
            GlStateManager.bindTexture(sfbDepthTextures.get(0));
            if (usedShadowDepthBuffers >= 2) {
               GlStateManager.setActiveTexture(33989);
               GlStateManager.bindTexture(sfbDepthTextures.get(1));
            }
         }

         for (int var5 = 0; var5 < usedColorBuffers; var5++) {
            GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[var5]);
            GlStateManager.bindTexture(dfbColorTexturesFlip.getA(var5));
         }

         GlStateManager.setActiveTexture(33990);
         GlStateManager.bindTexture(dfbDepthTextures.get(0));
         if (usedDepthBuffers >= 2) {
            GlStateManager.setActiveTexture(33995);
            GlStateManager.bindTexture(dfbDepthTextures.get(1));
            if (usedDepthBuffers >= 3) {
               GlStateManager.setActiveTexture(33996);
               GlStateManager.bindTexture(dfbDepthTextures.get(2));
            }
         }

         for (int var10 = 0; var10 < usedShadowColorBuffers; var10++) {
            GlStateManager.setActiveTexture(33997 + var10);
            GlStateManager.bindTexture(sfbColorTextures.get(var10));
         }

         if (noiseTextureEnabled) {
            GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
            GlStateManager.bindTexture(noiseTexture.getTextureId());
         }

         if (boolean2) {
            bindCustomTextures(customTexturesComposite);
         } else {
            bindCustomTextures(customTexturesDeferred);
         }

         GlStateManager.setActiveTexture(33984);

         for (int var11 = 0; var11 < usedColorBuffers; var11++) {
            EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var11, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getB(var11), 0);
         }

         EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, GL11.GL_TEXTURE_2D, dfbDepthTextures.get(0), 0);
         GL20.glDrawBuffers(dfbDrawBuffers);
         checkGLError("pre-composite");

         for (Program var8 : arr) {
            if (var8.getId() != 0) {
               useProgram(var8);
               checkGLError(var8.getName());
               if (activeCompositeMipmapSetting != 0) {
                  genCompositeMipmap();
               }

               preDrawComposite();
               drawComposite();
               postDrawComposite();

               for (int var9 = 0; var9 < usedColorBuffers; var9++) {
                  if (var8.getToggleColorTextures()[var9]) {
                     dfbColorTexturesFlip.flip(var9);
                     GlStateManager.setActiveTexture(33984 + colorTextureImageUnit[var9]);
                     GlStateManager.bindTexture(dfbColorTexturesFlip.getA(var9));
                     EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 36064 + var9, GL11.GL_TEXTURE_2D, dfbColorTexturesFlip.getB(var9), 0);
                  }
               }

               GlStateManager.setActiveTexture(33984);
            }
         }

         checkGLError("composite");
         if (boolean2) {
            renderFinal();
            isCompositeRendered = true;
         }

         GlStateManager.enableLighting();
         GlStateManager.enableTexture2D();
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         GL11.glPopMatrix();
         GL11.glMatrixMode(GL11.GL_MODELVIEW);
         GL11.glPopMatrix();
         useProgram(ProgramNone);
      }
   }

   private static void preDrawComposite() {
      RenderScale var2 = activeProgram.getRenderScale();
      if (var2 != null) {
         int var3 = (int)(renderWidth * var2.getOffsetX());
         int var4 = (int)(renderHeight * var2.getOffsetY());
         int var5 = (int)(renderWidth * var2.getScale());
         int var6 = (int)(renderHeight * var2.getScale());
         GL11.glViewport(var3, var4, var5, var6);
      }
   }

   private static void postDrawComposite() {
      RenderScale var2 = activeProgram.getRenderScale();
      if (var2 != null) {
         GL11.glViewport(0, 0, renderWidth, renderHeight);
      }
   }

   private static void renderFinal() {
      isRenderingDfb = false;
      mc.getFramebuffer().bindFramebuffer(true);
      OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, mc.getFramebuffer().framebufferTexture, 0);
      GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
      if (EntityRenderer.anaglyphEnable) {
         boolean var3 = EntityRenderer.anaglyphField != 0;
         GlStateManager.colorMask(var3, !var3, !var3, true);
      }

      GlStateManager.depthMask(true);
      GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
      GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.disableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.enableDepth();
      GlStateManager.depthFunc(519);
      GlStateManager.depthMask(false);
      checkGLError("pre-final");
      useProgram(ProgramFinal);
      checkGLError("final");
      if (activeCompositeMipmapSetting != 0) {
         genCompositeMipmap();
      }

      drawComposite();
      checkGLError("renderCompositeFinal");
   }

   public static void endRender() {
      if (isShadowPass) {
         checkGLError("shadow endRender");
      } else {
         if (!isCompositeRendered) {
            renderCompositeFinal();
         }

         isRenderingWorld = false;
         GlStateManager.colorMask(true, true, true, true);
         useProgram(ProgramNone);
         RenderHelper.disableStandardItemLighting();
         checkGLError("endRender end");
      }
   }

   public static void beginSky() {
      isRenderingSky = true;
      fogEnabled = true;
      setDrawBuffers(dfbDrawBuffers);
      useProgram(ProgramSkyTextured);
      pushEntity(-2, 0);
   }

   public static void setSkyColor(Vec3 vec3) {
      skyColorR = (float)vec3.xCoord;
      skyColorG = (float)vec3.yCoord;
      skyColorB = (float)vec3.zCoord;
      setProgramUniform3f(uniform_skyColor, skyColorR, skyColorG, skyColorB);
   }

   public static void drawHorizon() {
      WorldRenderer var2 = Tessellator.getInstance().getWorldRenderer();
      float var3 = mc.gameSettings.renderDistanceChunks * 16;
      double var4 = var3 * 0.9238;
      double var6 = var3 * 0.3826;
      double var8 = -var6;
      double var10 = -var4;
      double var14 = -cameraPositionY;
      var2.begin(7, DefaultVertexFormats.POSITION);
      var2.pos(var8, var14, var10).endVertex();
      var2.pos(var8, 16.0, var10).endVertex();
      var2.pos(var10, 16.0, var8).endVertex();
      var2.pos(var10, var14, var8).endVertex();
      var2.pos(var10, var14, var8).endVertex();
      var2.pos(var10, 16.0, var8).endVertex();
      var2.pos(var10, 16.0, var6).endVertex();
      var2.pos(var10, var14, var6).endVertex();
      var2.pos(var10, var14, var6).endVertex();
      var2.pos(var10, 16.0, var6).endVertex();
      var2.pos(var8, 16.0, var4).endVertex();
      var2.pos(var8, var14, var4).endVertex();
      var2.pos(var8, var14, var4).endVertex();
      var2.pos(var8, 16.0, var4).endVertex();
      var2.pos(var6, 16.0, var4).endVertex();
      var2.pos(var6, var14, var4).endVertex();
      var2.pos(var6, var14, var4).endVertex();
      var2.pos(var6, 16.0, var4).endVertex();
      var2.pos(var4, 16.0, var6).endVertex();
      var2.pos(var4, var14, var6).endVertex();
      var2.pos(var4, var14, var6).endVertex();
      var2.pos(var4, 16.0, var6).endVertex();
      var2.pos(var4, 16.0, var8).endVertex();
      var2.pos(var4, var14, var8).endVertex();
      var2.pos(var4, var14, var8).endVertex();
      var2.pos(var4, 16.0, var8).endVertex();
      var2.pos(var6, 16.0, var10).endVertex();
      var2.pos(var6, var14, var10).endVertex();
      var2.pos(var6, var14, var10).endVertex();
      var2.pos(var6, 16.0, var10).endVertex();
      var2.pos(var8, 16.0, var10).endVertex();
      var2.pos(var8, var14, var10).endVertex();
      var2.pos(var10, var14, var10).endVertex();
      var2.pos(var10, var14, var4).endVertex();
      var2.pos(var4, var14, var4).endVertex();
      var2.pos(var4, var14, var10).endVertex();
      Tessellator.getInstance().draw();
   }

   public static void preSkyList() {
      setUpPosition();
      GL11.glColor3f(fogColorR, fogColorG, fogColorB);
      drawHorizon();
      GL11.glColor3f(skyColorR, skyColorG, skyColorB);
   }

   public static void endSky() {
      isRenderingSky = false;
      setDrawBuffers(dfbDrawBuffers);
      useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      popEntity();
   }

   public static void beginUpdateChunks() {
      checkGLError("beginUpdateChunks1");
      checkFramebufferStatus("beginUpdateChunks1");
      if (!isShadowPass) {
         useProgram(ProgramTerrain);
      }

      checkGLError("beginUpdateChunks2");
      checkFramebufferStatus("beginUpdateChunks2");
   }

   public static void endUpdateChunks() {
      checkGLError("endUpdateChunks1");
      checkFramebufferStatus("endUpdateChunks1");
      if (!isShadowPass) {
         useProgram(ProgramTerrain);
      }

      checkGLError("endUpdateChunks2");
      checkFramebufferStatus("endUpdateChunks2");
   }

   public static boolean shouldRenderClouds(GameSettings gameSettings) {
      if (!shaderPackLoaded) {
         return true;
      }

      checkGLError("shouldRenderClouds");
      return isShadowPass ? configCloudShadow : gameSettings.clouds > 0;
   }

   public static void beginClouds() {
      fogEnabled = true;
      pushEntity(-3, 0);
      useProgram(ProgramClouds);
   }

   public static void endClouds() {
      disableFog();
      popEntity();
      useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
   }

   public static void beginEntities() {
      if (isRenderingWorld) {
         useProgram(ProgramEntities);
      }
   }

   public static void nextEntity(Entity entity) {
      if (isRenderingWorld) {
         useProgram(ProgramEntities);
         setEntityId(entity);
      }
   }

   public static void setEntityId(Entity entity) {
      if (uniform_entityId.isDefined()) {
         int var4 = EntityUtils.getEntityIdByClass(entity);
         int var5 = EntityAliases.getEntityAliasId(var4);
         if (var5 >= 0) {
            var4 = var5;
         }

         uniform_entityId.setValue(var4);
      }
   }

   public static void beginSpiderEyes() {
      if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
         useProgram(ProgramSpiderEyes);
         GlStateManager.enableAlpha();
         GlStateManager.alphaFunc(516, 0.0F);
         GlStateManager.blendFunc(770, 771);
      }
   }

   public static void endSpiderEyes() {
      if (isRenderingWorld && ProgramSpiderEyes.getId() != ProgramNone.getId()) {
         useProgram(ProgramEntities);
         GlStateManager.disableAlpha();
      }
   }

   public static void endEntities() {
      if (isRenderingWorld) {
         setEntityId(null);
         useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      }
   }

   public static void Method450() {
      if (isRenderingWorld) {
         isEntitiesGlowing = true;
      }
   }

   public static void Method451() {
      if (isRenderingWorld) {
         isEntitiesGlowing = false;
      }
   }

   public static void setEntityColor(float float1, float float2, float float3, float float4) {
      if (isRenderingWorld && !isShadowPass) {
         uniform_entityColor.setValue(float1, float2, float3, float4);
      }
   }

   public static void beginLivingDamage() {
      if (isRenderingWorld) {
         ShadersTex.bindTexture(defaultTexture);
         if (!isShadowPass) {
            setDrawBuffers(drawBuffersColorAtt0);
         }
      }
   }

   public static void endLivingDamage() {
      if (isRenderingWorld && !isShadowPass) {
         setDrawBuffers(ProgramEntities.getDrawBuffers());
      }
   }

   public static void beginBlockEntities() {
      if (isRenderingWorld) {
         checkGLError("beginBlockEntities");
         useProgram(ProgramBlock);
      }
   }

   public static void nextBlockEntity(TileEntity tileEntity) {
      if (isRenderingWorld) {
         checkGLError("nextBlockEntity");
         useProgram(ProgramBlock);
         setBlockEntityId(tileEntity);
      }
   }

   public static void setBlockEntityId(TileEntity tileEntity) {
      if (uniform_blockEntityId.isDefined()) {
         int var4 = getBlockEntityId(tileEntity);
         uniform_blockEntityId.setValue(var4);
      }
   }

   private static int getBlockEntityId(TileEntity tileEntity) {
      if (tileEntity == null) {
         return -1;
      }

      Block var4 = tileEntity.getBlockType();
      if (var4 == null) {
         return 0;
      }

      int var5 = Block.getIdFromBlock(var4);
      int var6 = tileEntity.getBlockMetadata();
      int var7 = BlockAliases.getBlockAliasId(var5, var6);
      if (var7 >= 0) {
         var5 = var7;
      }

      return var5;
   }

   public static void endBlockEntities() {
      if (isRenderingWorld) {
         checkGLError("endBlockEntities");
         setBlockEntityId(null);
         useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
         ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
      }
   }

   public static void beginLitParticles() {
      useProgram(ProgramTexturedLit);
   }

   public static void beginParticles() {
      useProgram(ProgramTextured);
   }

   public static void endParticles() {
      useProgram(ProgramTexturedLit);
   }

   public static void readCenterDepth() {
      if (!isShadowPass && centerDepthSmoothEnabled) {
         tempDirectFloatBuffer.clear();
         GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, tempDirectFloatBuffer);
         centerDepth = tempDirectFloatBuffer.get(0);
         float var3 = (float)diffSystemTime * 0.01F;
         float var4 = (float)Math.exp(Math.log(0.5) * var3 / centerDepthSmoothHalflife);
         centerDepthSmooth = centerDepthSmooth * var4 + centerDepth * (1.0F - var4);
      }
   }

   public static void beginWeather() {
      if (!isShadowPass) {
         if (usedDepthBuffers >= 3) {
            GlStateManager.setActiveTexture(33996);
            GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, renderWidth, renderHeight);
            GlStateManager.setActiveTexture(33984);
         }

         GlStateManager.enableDepth();
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.enableAlpha();
         useProgram(ProgramWeather);
      }
   }

   public static void endWeather() {
      GlStateManager.disableBlend();
      useProgram(ProgramTexturedLit);
   }

   public static void preWater() {
      if (usedDepthBuffers >= 2) {
         GlStateManager.setActiveTexture(33995);
         checkGLError("pre copy depth");
         GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, renderWidth, renderHeight);
         checkGLError("copy depth");
         GlStateManager.setActiveTexture(33984);
      }

      ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
   }

   public static void beginWater() {
      if (isRenderingWorld) {
         if (!isShadowPass) {
            renderDeferred();
            useProgram(ProgramWater);
            GlStateManager.enableBlend();
            GlStateManager.depthMask(true);
         } else {
            GlStateManager.depthMask(true);
         }
      }
   }

   public static void endWater() {
      if (isRenderingWorld) {
         if (isShadowPass) {
         }

         useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      }
   }

   public static void applyHandDepth() {
      if (configHandDepthMul != 1.0) {
         GL11.glScaled(1.0, 1.0, configHandDepthMul);
      }
   }

   public static void beginHand(boolean boolean1) {
      GL11.glMatrixMode(GL11.GL_MODELVIEW);
      GL11.glPushMatrix();
      GL11.glMatrixMode(GL11.GL_PROJECTION);
      GL11.glPushMatrix();
      GL11.glMatrixMode(GL11.GL_MODELVIEW);
      if (boolean1) {
         useProgram(ProgramHandWater);
      } else {
         useProgram(ProgramHand);
      }

      checkGLError("beginHand");
      checkFramebufferStatus("beginHand");
   }

   public static void endHand() {
      checkGLError("pre endHand");
      checkFramebufferStatus("pre endHand");
      GL11.glMatrixMode(GL11.GL_PROJECTION);
      GL11.glPopMatrix();
      GL11.glMatrixMode(GL11.GL_MODELVIEW);
      GL11.glPopMatrix();
      GlStateManager.blendFunc(770, 771);
      checkGLError("endHand");
   }

   public static void beginFPOverlay() {
      GlStateManager.disableLighting();
      GlStateManager.disableBlend();
   }

   public static void endFPOverlay() {
   }

   public static void Method452(int integer) {
      GL11.glEnable(integer);
      if (integer == 3553) {
         enableTexture2D();
      } else if (integer == 2912) {
         enableFog();
      }
   }

   public static void Method453(int integer) {
      GL11.glDisable(integer);
      if (integer == 3553) {
         an();
      } else if (integer == 2912) {
         disableFog();
      }
   }

   public static void Method454(int integer) {
      GL11.glEnable(integer);
      enableTexture2D();
   }

   public static void Method455(int integer) {
      GL11.glDisable(integer);
      an();
   }

   public static void Method456(int integer) {
      GL11.glEnable(integer);
      enableFog();
   }

   public static void Method457(int integer) {
      GL11.glDisable(integer);
      disableFog();
   }

   public static void enableTexture2D() {
      if (isRenderingSky) {
         useProgram(ProgramSkyTextured);
      } else if (activeProgram == ProgramBasic) {
         useProgram(lightmapEnabled ? ProgramTexturedLit : ProgramTextured);
      }
   }

   public static void an() {
      if (isRenderingSky) {
         useProgram(ProgramSkyBasic);
      } else if (activeProgram == ProgramTextured || activeProgram == ProgramTexturedLit) {
         useProgram(ProgramBasic);
      }
   }

   public static void pushProgram() {
      programStack.push(activeProgram);
   }

   public static void popProgram() {
      useProgram(programStack.pop());
   }

   public static void beginLeash() {
      pushProgram();
      useProgram(ProgramBasic);
   }

   public static void endLeash() {
      popProgram();
   }

   public static void enableFog() {
      fogEnabled = true;
      setProgramUniform1i(uniform_fogMode, fogMode);
      setProgramUniform1f(uniform_fogDensity, fogDensity);
   }

   public static void disableFog() {
      fogEnabled = false;
      setProgramUniform1i(uniform_fogMode, 0);
   }

   public static void setFogDensity(float float1) {
      fogDensity = float1;
      if (fogEnabled) {
         setProgramUniform1f(uniform_fogDensity, float1);
      }
   }

   public static void sglFogi(int integer1, int integer2) {
      GL11.glFogi(integer1, integer2);
      if (integer1 == 2917) {
         fogMode = integer2;
         if (fogEnabled) {
            setProgramUniform1i(uniform_fogMode, fogMode);
         }
      }
   }

   public static void enableLightmap() {
      lightmapEnabled = true;
      if (activeProgram == ProgramTextured) {
         useProgram(ProgramTexturedLit);
      }
   }

   public static void disableLightmap() {
      lightmapEnabled = false;
      if (activeProgram == ProgramTexturedLit) {
         useProgram(ProgramTextured);
      }
   }

   public static int getEntityData() {
      return entityData[entityDataIndex * 2];
   }

   public static int getEntityData2() {
      return entityData[entityDataIndex * 2 + 1];
   }

   public static int setEntityData1(int integer) {
      entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & 65535 | integer << 16;
      return integer;
   }

   public static int setEntityData2(int integer) {
      entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & -65536 | integer & 65535;
      return integer;
   }

   public static void pushEntity(int integer1, int integer2) {
      entityDataIndex++;
      entityData[entityDataIndex * 2] = integer1 & 65535 | integer2 << 16;
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void pushEntity(int integer) {
      entityDataIndex++;
      entityData[entityDataIndex * 2] = integer & 65535;
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void pushEntity(Block block) {
      entityDataIndex++;
      int var3 = block.getRenderType();
      entityData[entityDataIndex * 2] = Block.blockRegistry.getIDForObject(block) & 65535 | var3 << 16;
      entityData[entityDataIndex * 2 + 1] = 0;
   }

   public static void popEntity() {
      entityData[entityDataIndex * 2] = 0;
      entityData[entityDataIndex * 2 + 1] = 0;
      entityDataIndex--;
   }

   public static void mcProfilerEndSection() {
      mc.mcProfiler.endSection();
   }

   public static String getShaderPackName() {
      return shaderPack == null ? null : (shaderPack instanceof ShaderPackNone ? null : shaderPack.getName());
   }

   public static InputStream getShaderPackResourceStream(String string) {
      return shaderPack == null ? null : shaderPack.getResourceAsStream(string);
   }

   public static void nextAntialiasingLevel(boolean boolean1) {
      if (boolean1) {
         configAntialiasingLevel += 2;
         if (configAntialiasingLevel > 4) {
            configAntialiasingLevel = 0;
         }
      } else {
         configAntialiasingLevel -= 2;
         if (configAntialiasingLevel < 0) {
            configAntialiasingLevel = 4;
         }
      }

      configAntialiasingLevel = configAntialiasingLevel / 2 * 2;
      configAntialiasingLevel = Config.Method255(configAntialiasingLevel, 0, 4);
   }

   public static void checkShadersModInstalled() {
      try {
         Class.forName("shadersmod.transform.SMCClassTransformer");
      } catch (Throwable var0) {
         return;
      }

      throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
   }

   public static void resourcesReloaded() {
      loadShaderPackResources();
      if (shaderPackLoaded) {
         BlockAliases.resourcesReloaded();
         ItemAliases.resourcesReloaded();
         EntityAliases.resourcesReloaded();
      }
   }

   private static void loadShaderPackResources() {
      shaderPackResources = new HashMap<>();
      if (shaderPackLoaded) {
         ArrayList<String> var3 = new ArrayList<>();
         String var4 = "/shaders/lang/";
         String var5 = "en_US";
         String var6 = ".lang";
         var3.add(var4 + var5 + var6);
         if (!Config.getGameSettings().language.equals(var5)) {
            var3.add(var4 + Config.getGameSettings().language + var6);
         }

         try {
            for (String var8 : var3) {
               InputStream var9 = shaderPack.getResourceAsStream(var8);
               if (var9 != null) {
                  PropertiesOrdered var10 = new PropertiesOrdered();
                  Lang.loadLocaleData(var9, var10);
                  var9.close();

                  for (Object var12Obj : var10.keySet()) {
                     String var12 = (String)var12Obj;
                     String var13 = var10.getProperty(var12);
                     shaderPackResources.put(var12, var13);
                  }
               }
            }
         } catch (IOException var14) {
            var14.printStackTrace();
         }
      }
   }

   public static String translate(String string1, String string2) {
      String var5 = shaderPackResources.get(string1);
      return var5 == null ? string2 : var5;
   }

   public static boolean isProgramPath(String string) {
      if (string == null) {
         return false;
      }

      if (string.length() == 0) {
         return false;
      }

      int var4 = string.lastIndexOf("/");
      if (var4 >= 0) {
         string = string.substring(var4 + 1);
      }

      Program var5 = getProgram(string);
      return var5 != null;
   }

   public static Program getProgram(String string) {
      return programs.getProgram(string);
   }

   public static void setItemToRenderMain(ItemStack itemStack) {
      itemToRenderMainTranslucent = isTranslucentBlock(itemStack);
   }

   public static void setItemToRenderOff(ItemStack itemStack) {
      itemToRenderOffTranslucent = isTranslucentBlock(itemStack);
   }

   public static boolean isItemToRenderMainTranslucent() {
      return itemToRenderMainTranslucent;
   }

   public static boolean isItemToRenderOffTranslucent() {
      return itemToRenderOffTranslucent;
   }

   public static boolean Method458() {
      return isHandRenderedMain && isHandRenderedOff;
   }

   private static boolean isTranslucentBlock(ItemStack itemStack) {
      if (itemStack == null) {
         return false;
      }

      Item var4 = itemStack.getItem();
      if (var4 == null) {
         return false;
      }

      if (var4 instanceof ItemBlock var5) {
         Block var6 = var5.getBlock();
         if (var6 == null) {
            return false;
         }

         EnumWorldBlockLayer var7 = var6.getBlockLayer();
         return var7 == EnumWorldBlockLayer.TRANSLUCENT;
      } else {
         return false;
      }
   }

   public static boolean isSkipRenderHand() {
      return skipRenderHandMain;
   }

   public static boolean isRenderBothHands() {
      return !skipRenderHandMain && !skipRenderHandOff;
   }

   public static void setSkipRenderHands(boolean boolean1, boolean boolean2) {
      skipRenderHandMain = boolean1;
      skipRenderHandOff = boolean2;
   }

   public static void setHandsRendered(boolean boolean1, boolean boolean2) {
      isHandRenderedMain = boolean1;
      isHandRenderedOff = boolean2;
   }

   public static boolean isHandRenderedMain() {
      return isHandRenderedMain;
   }

   public static boolean isHandRenderedOff() {
      return isHandRenderedOff;
   }

   public static void setRenderingFirstPersonHand(boolean flag) {
      isRenderingFirstPersonHand = flag;
   }

   public static boolean isRenderingFirstPersonHand() {
      return isRenderingFirstPersonHand;
   }

   public static float getShadowRenderDistance() {
      return shadowDistanceRenderMul < 0.0F ? -1.0F : shadowMapHalfPlane * shadowDistanceRenderMul;
   }

   public static void beginBeacon() {
      if (isRenderingWorld) {
         useProgram(ProgramBeaconBeam);
      }
   }

   public static void endBeacon() {
      if (isRenderingWorld) {
         useProgram(ProgramBlock);
      }
   }

   public static World getCurrentWorld() {
      return currentWorld;
   }

   public static BlockPos getCameraPosition() {
      return new BlockPos(cameraPositionX, cameraPositionY, cameraPositionZ);
   }

   public static boolean isCustomUniforms() {
      return customUniforms != null;
   }

   public static boolean Method459() {
      return !hasGeometryShaders || capabilities.GL_NV_geometry_shader4;
   }
}
