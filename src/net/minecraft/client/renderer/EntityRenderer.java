package net.minecraft.client.renderer;

import com.google.common.base.Predicates;
import com.google.gson.JsonSyntaxException;
import com.moon.Moon;
import com.moon.events.impl.player.CameraPositionEvent;
import com.moon.events.impl.player.PickEvent;
import com.moon.events.impl.player.RaytraceHitBoxEvent;
import com.moon.events.impl.render.HurtCameraEvent;
import com.moon.events.impl.render.PostFrameEvent;
import com.moon.events.impl.render.RenderFogEvent;
import com.moon.events.impl.render.RenderFrameEvent;
import com.moon.events.impl.render.WorldRenderEvent;
import com.moon.handlers.EntityCullingHandler;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.realms.Tezzelator;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.src.Config;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.optifine.CustomColors;
import net.optifine.GlErrors;
import net.optifine.Lagometer;
import net.optifine.RandomEntities;
import net.optifine.gui.GuiChatOF;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.MemoryMonitor;
import net.optifine.util.TextureUtils;
import net.optifine.util.TimedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.NVFogDistance;
import org.lwjgl.util.glu.Project;

public class EntityRenderer implements IResourceManagerReloadListener {
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation locationRainPng = new ResourceLocation("textures/environment/rain.png");
   private static final ResourceLocation locationSnowPng = new ResourceLocation("textures/environment/snow.png");
   private static final ResourceLocation[] shaderResourceLocations = new ResourceLocation[]{
      new ResourceLocation("shaders/post/notch.json"),
      new ResourceLocation("shaders/post/fxaa.json"),
      new ResourceLocation("shaders/post/art.json"),
      new ResourceLocation("shaders/post/bumpy.json"),
      new ResourceLocation("shaders/post/blobs2.json"),
      new ResourceLocation("shaders/post/pencil.json"),
      new ResourceLocation("shaders/post/color_convolve.json"),
      new ResourceLocation("shaders/post/deconverge.json"),
      new ResourceLocation("shaders/post/flip.json"),
      new ResourceLocation("shaders/post/invert.json"),
      new ResourceLocation("shaders/post/ntsc.json"),
      new ResourceLocation("shaders/post/outline.json"),
      new ResourceLocation("shaders/post/phosphor.json"),
      new ResourceLocation("shaders/post/scan_pincushion.json"),
      new ResourceLocation("shaders/post/sobel.json"),
      new ResourceLocation("shaders/post/bits.json"),
      new ResourceLocation("shaders/post/desaturate.json"),
      new ResourceLocation("shaders/post/green.json"),
      new ResourceLocation("shaders/post/blur.json"),
      new ResourceLocation("shaders/post/wobble.json"),
      new ResourceLocation("shaders/post/blobs.json"),
      new ResourceLocation("shaders/post/antialias.json"),
      new ResourceLocation("shaders/post/creeper.json"),
      new ResourceLocation("shaders/post/spider.json")
   };
   public static final int shaderCount = shaderResourceLocations.length;
   public static boolean anaglyphEnable;
   public static int anaglyphField;
   private final Minecraft mc;
   private final IResourceManager resourceManager;
   private final Random random = new Random();
   private final MapItemRenderer theMapItemRenderer;
   private final float l = 4.0F;
   private final boolean m = true;
   private final boolean n = true;
   private final DynamicTexture lightmapTexture;
   private final int[] lightmapColors;
   private final ResourceLocation locationLightMap;
   private final float[] rainXCoords = new float[1024];
   private final float[] rainYCoords = new float[1024];
   private final FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
   private final int u = 0;
   private final boolean v = false;
   private final double w = 1.0;
   private final boolean x = false;
   private final ShaderGroup[] fxaaShaders = new ShaderGroup[10];
   public ItemRenderer itemRenderer;
   public float fogColorRed;
   public float fogColorGreen;
   public float fogColorBlue;
   public int frameCount;
   public boolean fogStandard = false;
   private float farPlaneDistance;
   private int rendererUpdateCount;
   private Entity pointedEntity;
   private MouseFilter mouseFilterXAxis = new MouseFilter();
   private MouseFilter mouseFilterYAxis = new MouseFilter();
   private float thirdPersonDistanceTemp = 4.0F;
   private float smoothCamYaw;
   private float smoothCamPitch;
   private float smoothCamFilterX;
   private float smoothCamFilterY;
   private float smoothCamPartialTicks;
   private float fovModifierHand;
   private float fovModifierHandPrev;
   private float bossColorModifier;
   private float bossColorModifierPrev;
   private boolean cloudFog;
   private long prevFrameTime = Minecraft.getSystemTime();
   private long renderEndNanoTime;
   private boolean lightmapUpdateNeeded;
   private float torchFlickerX;
   private float torchFlickerDX;
   private int rainSoundCounter;
   private float fogColor2;
   private float fogColor1;
   private double cameraYaw;
   private double cameraPitch;
   private ShaderGroup theShaderGroup;
   private int shaderIndex;
   private boolean useShader;
   private boolean initialized = false;
   private World updatedWorld = null;
   private float clipDistance = 128.0F;
   private long lastServerTime = 0L;
   private int lastServerTicks = 0;
   private int serverWaitTime = 0;
   private int serverWaitTimeCurrent = 0;
   private float avgServerTimeDiff = 0.0F;
   private float avgServerTickDiff = 0.0F;
   private boolean loadVisibleChunks = false;

   public EntityRenderer(Minecraft minecraft, IResourceManager iResourceManager) {
      this.shaderIndex = shaderCount;
      this.useShader = false;
      this.frameCount = 0;
      this.mc = minecraft;
      this.resourceManager = iResourceManager;
      this.itemRenderer = minecraft.getItemRenderer();
      this.theMapItemRenderer = new MapItemRenderer(minecraft.getTextureManager());
      this.lightmapTexture = new DynamicTexture(16, 16);
      this.locationLightMap = minecraft.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture);
      this.lightmapColors = this.lightmapTexture.getTextureData();
      this.theShaderGroup = null;

      for (int var5 = 0; var5 < 32; var5++) {
         for (int var6 = 0; var6 < 32; var6++) {
            float var7 = var6 - 16;
            float var8 = var5 - 16;
            float var9 = MathHelper.sqrt_float(var7 * var7 + var8 * var8);
            this.rainXCoords[var5 << 5 | var6] = -var8 / var9;
            this.rainYCoords[var5 << 5 | var6] = var7 / var9;
         }
      }
   }

   public boolean isShaderActive() {
      return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
   }

   public void stopUseShader() {
      if (this.theShaderGroup != null) {
         this.theShaderGroup.deleteShaderGroup();
      }

      this.theShaderGroup = null;
      this.shaderIndex = shaderCount;
   }

   public void switchUseShader() {
      this.useShader = !this.useShader;
   }

   public void loadEntityShader(Entity entity) {
      if (OpenGlHelper.shadersSupported) {
         if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
         }

         this.theShaderGroup = null;
         if (entity instanceof EntityCreeper) {
            this.Method733(new ResourceLocation("shaders/post/creeper.json"));
         } else if (entity instanceof EntitySpider) {
            this.Method733(new ResourceLocation("shaders/post/spider.json"));
         } else if (entity instanceof EntityEnderman) {
            this.Method733(new ResourceLocation("shaders/post/invert.json"));
         }
      }
   }

   public void activateNextShader() {
      if (OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
         if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
         }

         this.shaderIndex = (this.shaderIndex + 1) % (shaderResourceLocations.length + 1);
         if (this.shaderIndex != shaderCount) {
            this.Method733(shaderResourceLocations[this.shaderIndex]);
         } else {
            this.theShaderGroup = null;
         }
      }
   }

   private void Method733(ResourceLocation resourceLocation) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         try {
            this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, this.mc.getFramebuffer(), resourceLocation);
            this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.useShader = true;
         } catch (IOException | JsonSyntaxException var3) {
            logger.warn("Failed to load shader: " + resourceLocation, var3);
            this.shaderIndex = shaderCount;
            this.useShader = false;
         }
      }
   }

   @Override
   public void onResourceManagerReload(IResourceManager iResourceManager) {
      if (this.theShaderGroup != null) {
         this.theShaderGroup.deleteShaderGroup();
      }

      this.theShaderGroup = null;
      if (this.shaderIndex != shaderCount) {
         this.Method733(shaderResourceLocations[this.shaderIndex]);
      } else {
         this.loadEntityShader(this.mc.getRenderViewEntity());
      }
   }

   public void updateRenderer() {
      if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
         ShaderLinkHelper.setNewStaticShaderLinkHelper();
      }

      this.updateFovModifierHand();
      this.updateTorchFlicker();
      this.fogColor2 = this.fogColor1;
      this.thirdPersonDistanceTemp = 4.0F;
      if (this.mc.gameSettings.smoothCamera) {
         float var1 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float var2 = var1 * var1 * var1 * 8.0F;
         this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * var2);
         this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * var2);
         this.smoothCamPartialTicks = 0.0F;
         this.smoothCamYaw = 0.0F;
         this.smoothCamPitch = 0.0F;
      } else {
         this.smoothCamFilterX = 0.0F;
         this.smoothCamFilterY = 0.0F;
         this.mouseFilterXAxis.reset();
         this.mouseFilterYAxis.reset();
      }

      if (this.mc.getRenderViewEntity() == null) {
         this.mc.setRenderViewEntity(this.mc.thePlayer);
      }

      Entity var11 = this.mc.getRenderViewEntity();
      double var12 = var11.posX;
      double var4 = var11.posY + var11.getEyeHeight();
      double var6 = var11.posZ;
      float var8 = this.mc.theWorld.getLightBrightness(new BlockPos(var12, var4, var6));
      float var9 = MathHelper.clamp_float(this.mc.gameSettings.renderDistanceChunks / 16.0F, 0.0F, 1.0F);
      float var10 = var8 * (1.0F - var9) + var9;
      this.fogColor1 = this.fogColor1 + (var10 - this.fogColor1) * 0.1F;
      this.rendererUpdateCount++;
      this.itemRenderer.updateEquippedItem();
      this.addRainParticles();
      this.bossColorModifierPrev = this.bossColorModifier;
      if (BossStatus.hasColorModifier) {
         this.bossColorModifier += 0.05F;
         if (this.bossColorModifier > 1.0F) {
            this.bossColorModifier = 1.0F;
         }

         BossStatus.hasColorModifier = false;
      } else if (this.bossColorModifier > 0.0F) {
         this.bossColorModifier -= 0.0125F;
      }
   }

   public ShaderGroup getShaderGroup() {
      return this.theShaderGroup;
   }

   public void updateShaderGroupSize(int integer1, int integer2) {
      if (OpenGlHelper.shadersSupported) {
         if (this.theShaderGroup != null) {
            this.theShaderGroup.createBindFramebuffers(integer1, integer2);
         }

         this.mc.renderGlobal.createBindEntityOutlineFbs(integer1, integer2);
      }
   }

   public void getMouseOver(float partialTicks) {
      Entity entity = this.mc.getRenderViewEntity();
      if (entity != null && this.mc.theWorld != null) {
         this.mc.mcProfiler.startSection("pick");
         this.mc.pointedEntity = null;
         double blockReach = this.mc.playerController.getBlockReachDistance();
         this.mc.objectMouseOver = entity.rayTrace(blockReach, partialTicks);
         double blockReachCpy = blockReach;
         Vec3 vec3 = entity.getPositionEyes(partialTicks);
         boolean flag = false;
         if (this.mc.playerController.extendedReach()) {
            blockReach = 6.0;
            blockReachCpy = 6.0;
         } else if (blockReach > 3.0) {
            flag = true;
         }

         PickEvent var12 = new PickEvent(blockReach, this.pointedEntity);
         Moon.getInstance().getEventBus().publish(var12);
         if (var12.getRange() > 4.5) {
            blockReach = 6.0;
         }

         if (this.mc.objectMouseOver != null) {
            blockReachCpy = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
         }

         Vec3 var13 = entity.getLook(partialTicks);
         Vec3 var14 = vec3.addVector(var13.xCoord * blockReach, var13.yCoord * blockReach, var13.zCoord * blockReach);
         this.pointedEntity = null;
         Vec3 var15 = null;
         float f = 1.0F;
         List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(var13.xCoord * blockReach, var13.yCoord * blockReach, var13.zCoord * blockReach).expand(f, f, f), Predicates.and(Tezzelator.d, Entity::canBeCollidedWith));
         double var18 = blockReachCpy;

         for (Entity var21 : list) {
            float var22 = var21.getCollisionBorderSize();
            RaytraceHitBoxEvent var23 = new RaytraceHitBoxEvent(var22);
            Moon.getInstance().getEventBus().publish(var23);
            var22 = var23.getSize();
            AxisAlignedBB var24 = var21.getEntityBoundingBox().expand(var22, var22, var22);
            MovingObjectPosition movingObjectPosition = var24.calculateIntercept(vec3, var14);
            if (var24.isVecInside(vec3)) {
               if (var18 >= 0.0) {
                  this.pointedEntity = var21;
                  var15 = movingObjectPosition == null ? vec3 : movingObjectPosition.hitVec;
                  var18 = 0.0;
               }
            } else if (movingObjectPosition != null) {
               double var26 = vec3.distanceTo(movingObjectPosition.hitVec);
               if (var26 < var18 || var18 == 0.0) {
                  if (var21 != entity.ridingEntity) {
                     this.pointedEntity = var21;
                     var15 = movingObjectPosition.hitVec;
                     var18 = var26;
                  } else if (var18 == 0.0) {
                     this.pointedEntity = var21;
                     var15 = movingObjectPosition.hitVec;
                  }
               }
            }
         }

         PickEvent var28 = new PickEvent(3.0, this.pointedEntity);
         Moon.getInstance().getEventBus().publish(var28);
         if (this.pointedEntity != null && flag && vec3.distanceTo(var15) > var28.getRange()) {
            this.pointedEntity = null;
            this.mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, var15, null, new BlockPos(var15));
         }

         if (this.pointedEntity != null && (var18 < blockReachCpy || this.mc.objectMouseOver == null)) {
            this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, var15);
            if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
               this.mc.pointedEntity = this.pointedEntity;
            }
         }

         this.mc.mcProfiler.endSection();
      }
   }

   private void updateFovModifierHand() {
      float var1 = 1.0F;
      Entity var3 = this.mc.getRenderViewEntity();
      if (var3 instanceof AbstractClientPlayer) {
         var1 = ((AbstractClientPlayer)var3).getFovModifier();
      }

      this.fovModifierHandPrev = this.fovModifierHand;
      this.fovModifierHand = this.fovModifierHand + (var1 - this.fovModifierHand) * 0.5F;
      if (this.fovModifierHand > 1.5F) {
         this.fovModifierHand = 1.5F;
      }

      if (this.fovModifierHand < 0.1F) {
         this.fovModifierHand = 0.1F;
      }
   }

   private float getFOVModifier(float float1, boolean boolean2) {
      Objects.requireNonNull(this);
      Entity var3 = this.mc.getRenderViewEntity();
      float var4 = 70.0F;
      if (boolean2) {
         var4 = this.mc.gameSettings.fovSetting;
         if (Config.isDynamicFov()) {
            var4 *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * float1;
         }
      }

      boolean var5 = false;
      if (this.mc.currentScreen == null) {
         var5 = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
      }

      if (var5) {
         if (!Config.zoomMode) {
            Config.zoomMode = true;
            Config.zoomSmoothCamera = this.mc.gameSettings.smoothCamera;
            this.mc.gameSettings.smoothCamera = true;
            this.mc.renderGlobal.displayListEntitiesDirty = true;
         }

         if (Config.zoomMode) {
            var4 /= 4.0F;
         }
      } else if (Config.zoomMode) {
         Config.zoomMode = false;
         this.mc.gameSettings.smoothCamera = Config.zoomSmoothCamera;
         this.mouseFilterXAxis = new MouseFilter();
         this.mouseFilterYAxis = new MouseFilter();
         this.mc.renderGlobal.displayListEntitiesDirty = true;
      }

      if (var3 instanceof EntityLivingBase && ((EntityLivingBase)var3).getHealth() <= 0.0F) {
         float var6 = ((EntityLivingBase)var3).deathTime + float1;
         var4 /= (1.0F - 500.0F / (var6 + 500.0F)) * 2.0F + 1.0F;
      }

      if (ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var3, float1).getMaterial() == Material.water) {
         var4 = var4 * 60.0F / 70.0F;
      }

      return var4;
   }

   private void hurtCameraEffect(float float1) {
      HurtCameraEvent var2 = new HurtCameraEvent();
      Moon.getInstance().getEventBus().publish(var2);
      if (!var2.isCancelled()) {
         if (this.mc.getRenderViewEntity() instanceof EntityLivingBase var3) {
            float var6 = var3.hurtTime - float1;
            if (var3.getHealth() <= 0.0F) {
               float var5 = var3.deathTime + float1;
               GlStateManager.rotate(40.0F - 8000.0F / (var5 + 200.0F), 0.0F, 0.0F, 1.0F);
            }

            if (var6 < 0.0F) {
               return;
            }

            var6 /= var3.maxHurtTime;
            var6 = MathHelper.sin(var6 * var6 * var6 * var6 * (float) Math.PI);
            float var9 = var3.attackedAtYaw;
            GlStateManager.rotate(-var9, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-var6 * 14.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(var9, 0.0F, 1.0F, 0.0F);
         }
      }
   }

   private void setupViewBobbing(float float1) {
      if (this.mc.getRenderViewEntity() instanceof EntityPlayer var2) {
         float var7 = var2.distanceWalkedModified - var2.prevDistanceWalkedModified;
         float var4 = -(var2.distanceWalkedModified + var7 * float1);
         float var5 = var2.prevCameraYaw + (var2.cameraYaw - var2.prevCameraYaw) * float1;
         float var6 = var2.prevCameraPitch + (var2.cameraPitch - var2.prevCameraPitch) * float1;
         GlStateManager.translate(MathHelper.sin(var4 * (float) Math.PI) * var5 * 0.5F, -Math.abs(MathHelper.cos(var4 * (float) Math.PI) * var5), 0.0F);
         GlStateManager.rotate(MathHelper.sin(var4 * (float) Math.PI) * var5 * 3.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(Math.abs(MathHelper.cos(var4 * (float) Math.PI - 0.2F) * var5) * 5.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.rotate(var6, 1.0F, 0.0F, 0.0F);
      }
   }

   public void orientCamera(float float1) {
      Entity var4 = this.mc.getRenderViewEntity();
      float var5 = var4.getEyeHeight();
      double var6 = var4.prevPosX + (var4.posX - var4.prevPosX) * float1;
      double var8 = var4.prevPosY + (var4.posY - var4.prevPosY) * float1 + var5;
      double var10 = var4.prevPosZ + (var4.posZ - var4.prevPosZ) * float1;
      if (var4 instanceof EntityLivingBase && ((EntityLivingBase)var4).isPlayerSleeping()) {
         var5 = (float)(var5 + 1.0);
         GlStateManager.translate(0.0F, 0.3F, 0.0F);
         if (!this.mc.gameSettings.debugCamEnable) {
            BlockPos var32 = new BlockPos(var4);
            IBlockState var13 = this.mc.theWorld.getBlockState(var32);
            if (var13.getBlock() == Blocks.bed) {
               GlStateManager.rotate(var13.getValue(BlockBed.FACING).getHorizontalIndex() * 90, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotate(var4.prevRotationYaw + (var4.rotationYaw - var4.prevRotationYaw) * float1 + 180.0F, 0.0F, -1.0F, 0.0F);
            GlStateManager.rotate(var4.prevRotationPitch + (var4.rotationPitch - var4.prevRotationPitch) * float1, -1.0F, 0.0F, 0.0F);
         }
      } else if (this.mc.gameSettings.thirdPersonView > 0) {
         double var12 = this.thirdPersonDistanceTemp + (4.0F - this.thirdPersonDistanceTemp) * float1;
         if (this.mc.gameSettings.debugCamEnable) {
            GlStateManager.translate(0.0F, 0.0F, (float)(-var12));
         } else {
            float var14 = var4.rotationYaw;
            float var15 = var4.rotationPitch;
            if (this.mc.gameSettings.thirdPersonView == 2) {
               var15 += 180.0F;
            }

            double var16 = -MathHelper.sin(var14 / 180.0F * (float) Math.PI) * MathHelper.cos(var15 / 180.0F * (float) Math.PI) * var12;
            double var18 = MathHelper.cos(var14 / 180.0F * (float) Math.PI) * MathHelper.cos(var15 / 180.0F * (float) Math.PI) * var12;
            double var20 = -MathHelper.sin(var15 / 180.0F * (float) Math.PI) * var12;

            for (int var22 = 0; var22 < 8; var22++) {
               float var23 = (var22 & 1) * 2 - 1;
               float var24 = (var22 >> 1 & 1) * 2 - 1;
               float var25 = (var22 >> 2 & 1) * 2 - 1;
               var23 *= 0.1F;
               var24 *= 0.1F;
               var25 *= 0.1F;
               MovingObjectPosition var26 = this.mc
                  .theWorld
                  .rayTraceBlocks(
                     new Vec3(var6 + var23, var8 + var24, var10 + var25), new Vec3(var6 - var16 + var23 + var25, var8 - var20 + var24, var10 - var18 + var25)
                  );
               if (var26 != null) {
                  double var27 = var26.hitVec.distanceTo(new Vec3(var6, var8, var10));
                  if (var27 < var12) {
                     var12 = var27;
                  }
               }
            }

            if (this.mc.gameSettings.thirdPersonView == 2) {
               GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.rotate(var4.rotationPitch - var15, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(var4.rotationYaw - var14, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, 0.0F, (float)(-var12));
            GlStateManager.rotate(var14 - var4.rotationYaw, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(var15 - var4.rotationPitch, 1.0F, 0.0F, 0.0F);
         }
      } else {
         GlStateManager.translate(0.0F, 0.0F, -0.1F);
      }

      if (!this.mc.gameSettings.debugCamEnable) {
         GlStateManager.rotate(var4.prevRotationPitch + (var4.rotationPitch - var4.prevRotationPitch) * float1, 1.0F, 0.0F, 0.0F);
         if (var4 instanceof EntityAnimal var33) {
            GlStateManager.rotate(var33.prevRotationYawHead + (var33.rotationYawHead - var33.prevRotationYawHead) * float1 + 180.0F, 0.0F, 1.0F, 0.0F);
         } else {
            GlStateManager.rotate(var4.prevRotationYaw + (var4.rotationYaw - var4.prevRotationYaw) * float1 + 180.0F, 0.0F, 1.0F, 0.0F);
         }
      }

      GlStateManager.translate(0.0F, -var5, 0.0F);
      var6 = var4.prevPosX + (var4.posX - var4.prevPosX) * float1;
      var8 = var4.prevPosY + (var4.posY - var4.prevPosY) * float1 + var5;
      var10 = var4.prevPosZ + (var4.posZ - var4.prevPosZ) * float1;
      this.cloudFog = this.mc.renderGlobal.hasCloudFog(var6, var8, var10, float1);
   }

   public void setupCameraTransform(float float1, int integer) {
      this.farPlaneDistance = this.mc.gameSettings.renderDistanceChunks * 16;
      if (Config.isFogFancy()) {
         this.farPlaneDistance *= 0.95F;
      }

      if (Config.isFogFast()) {
         this.farPlaneDistance *= 0.83F;
      }

      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      if (this.mc.gameSettings.anaglyph) {
         GlStateManager.translate(-(integer * 2 - 1) * 0.07F, 0.0F, 0.0F);
      }

      this.clipDistance = this.farPlaneDistance * 2.0F;
      if (this.clipDistance < 173.0F) {
         this.clipDistance = 173.0F;
      }

      if (false) {
         GlStateManager.translate((float)this.cameraYaw, (float)(-this.cameraPitch), 0.0F);
         GlStateManager.scale(1.0, 1.0, 1.0);
      }

      Project.gluPerspective(this.getFOVModifier(float1, true), (float)this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      if (this.mc.gameSettings.anaglyph) {
         GlStateManager.translate((integer * 2 - 1) * 0.1F, 0.0F, 0.0F);
      }

      this.hurtCameraEffect(float1);
      if (this.mc.gameSettings.viewBobbing) {
         this.setupViewBobbing(float1);
      }

      float var6 = this.mc.thePlayer.prevTimeInPortal + (this.mc.thePlayer.timeInPortal - this.mc.thePlayer.prevTimeInPortal) * float1;
      if (var6 > 0.0F) {
         byte var7 = 20;
         if (this.mc.thePlayer.isPotionActive(Potion.confusion)) {
            var7 = 7;
         }

         float var8 = 5.0F / (var6 * var6 + 5.0F) - var6 * 0.04F;
         var8 *= var8;
         GlStateManager.rotate((this.rendererUpdateCount + float1) * var7, 0.0F, 1.0F, 1.0F);
         GlStateManager.scale(1.0F / var8, 1.0F, 1.0F);
         GlStateManager.rotate(-(this.rendererUpdateCount + float1) * var7, 0.0F, 1.0F, 1.0F);
      }

      this.orientCamera(float1);
      Objects.requireNonNull(this);
   }

   private void renderHand(float float1, int integer) {
      this.renderHand(float1, integer, true, true, false);
   }

   public void renderHand(float float1, int integer, boolean boolean3, boolean boolean4, boolean boolean5) {
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      if (this.mc.gameSettings.anaglyph) {
         GlStateManager.translate(-(integer * 2 - 1) * 0.07F, 0.0F, 0.0F);
      }

      if (Config.isShaders()) {
         Shaders.applyHandDepth();
      }

      Project.gluPerspective(this.getFOVModifier(float1, false), (float)this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      if (this.mc.gameSettings.anaglyph) {
         GlStateManager.translate((integer * 2 - 1) * 0.1F, 0.0F, 0.0F);
      }

      boolean var9 = false;
      if (boolean3) {
         GlStateManager.pushMatrix();
         this.hurtCameraEffect(float1);
         if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(float1);
         }

         var9 = this.mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping();
         if (this.mc.gameSettings.thirdPersonView == 0 && !var9 && !this.mc.gameSettings.hideGUI && !this.mc.playerController.isSpectator()) {
            this.enableLightmap();
            int var10 = 3000000;
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(0.0F, -3000000.0F);
            if (Config.isShaders()) {
               ShadersRender.renderItemFP(this.itemRenderer, float1, boolean5);
            } else {
               this.itemRenderer.Method808(float1);
            }

            GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(0.0F, 3000000.0F);
            this.disableLightmap();
         }

         GlStateManager.popMatrix();
      }

      if (boolean4) {
         this.disableLightmap();
         if (this.mc.gameSettings.thirdPersonView == 0 && !var9) {
            this.itemRenderer.renderOverlays(float1);
            this.hurtCameraEffect(float1);
         }

         if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(float1);
         }

         GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
      }
   }

   public void disableLightmap() {
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if (Config.isShaders()) {
         Shaders.disableLightmap();
      }
   }

   public void enableLightmap() {
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.matrixMode(5890);
      GlStateManager.loadIdentity();
      GlStateManager.scale(0.00390625F, 0.00390625F, 0.00390625F);
      GlStateManager.translate(8.0F, 8.0F, 8.0F);
      GlStateManager.matrixMode(5888);
      this.mc.getTextureManager().bindTexture(this.locationLightMap);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if (Config.isShaders()) {
         Shaders.enableLightmap();
      }
   }

   private void updateTorchFlicker() {
      this.torchFlickerDX = (float)(this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
      this.torchFlickerDX = (float)(this.torchFlickerDX * 0.9);
      this.torchFlickerX = this.torchFlickerX + (this.torchFlickerDX - this.torchFlickerX);
      this.lightmapUpdateNeeded = true;
   }

   private void updateLightmap(float float1) {
      if (this.lightmapUpdateNeeded) {
         this.mc.mcProfiler.startSection("lightTex");
         WorldClient var4 = this.mc.theWorld;
         if (var4 != null) {
            if (Config.isCustomColors()
               && CustomColors.updateLightmap(var4, this.torchFlickerX, this.lightmapColors, this.mc.thePlayer.isPotionActive(Potion.nightVision), float1)) {
               this.lightmapTexture.updateDynamicTexture();
               this.lightmapUpdateNeeded = false;
               this.mc.mcProfiler.endSection();
               return;
            }

            float var5 = var4.getSunBrightness(1.0F);
            float var6 = var5 * 0.95F + 0.05F;

            for (int var7 = 0; var7 < 256; var7++) {
               float var8 = var4.provider.getLightBrightnessTable()[var7 / 16] * var6;
               float var9 = var4.provider.getLightBrightnessTable()[var7 % 16] * (this.torchFlickerX * 0.1F + 1.5F);
               if (var4.getLastLightningBolt() > 0) {
                  var8 = var4.provider.getLightBrightnessTable()[var7 / 16];
               }

               float var10 = var8 * (var5 * 0.65F + 0.35F);
               float var11 = var8 * (var5 * 0.65F + 0.35F);
               float var12 = var9 * ((var9 * 0.6F + 0.4F) * 0.6F + 0.4F);
               float var13 = var9 * (var9 * var9 * 0.6F + 0.4F);
               float var14 = var10 + var9;
               float var15 = var11 + var12;
               float var16 = var8 + var13;
               var14 = var14 * 0.96F + 0.03F;
               var15 = var15 * 0.96F + 0.03F;
               var16 = var16 * 0.96F + 0.03F;
               if (this.bossColorModifier > 0.0F) {
                  float var17 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * float1;
                  var14 = var14 * (1.0F - var17) + var14 * 0.7F * var17;
                  var15 = var15 * (1.0F - var17) + var15 * 0.6F * var17;
                  var16 = var16 * (1.0F - var17) + var16 * 0.6F * var17;
               }

               if (var4.provider.getDimensionId() == 1) {
                  var14 = 0.22F + var9 * 0.75F;
                  var15 = 0.28F + var12 * 0.75F;
                  var16 = 0.25F + var13 * 0.75F;
               }

               if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                  float var34 = this.getNightVisionBrightness(this.mc.thePlayer, float1);
                  float var18 = 1.0F / var14;
                  if (var18 > 1.0F / var15) {
                     var18 = 1.0F / var15;
                  }

                  if (var18 > 1.0F / var16) {
                     var18 = 1.0F / var16;
                  }

                  var14 = var14 * (1.0F - var34) + var14 * var18 * var34;
                  var15 = var15 * (1.0F - var34) + var15 * var18 * var34;
                  var16 = var16 * (1.0F - var34) + var16 * var18 * var34;
               }

               if (var14 > 1.0F) {
                  var14 = 1.0F;
               }

               if (var15 > 1.0F) {
                  var15 = 1.0F;
               }

               if (var16 > 1.0F) {
                  var16 = 1.0F;
               }

               float var35 = this.mc.gameSettings.gammaSetting;
               float var36 = 1.0F - var14;
               float var19 = 1.0F - var15;
               float var20 = 1.0F - var16;
               var36 = 1.0F - var36 * var36 * var36 * var36;
               var19 = 1.0F - var19 * var19 * var19 * var19;
               var20 = 1.0F - var20 * var20 * var20 * var20;
               var14 = var14 * (1.0F - var35) + var36 * var35;
               var15 = var15 * (1.0F - var35) + var19 * var35;
               var16 = var16 * (1.0F - var35) + var20 * var35;
               var14 = var14 * 0.96F + 0.03F;
               var15 = var15 * 0.96F + 0.03F;
               var16 = var16 * 0.96F + 0.03F;
               if (var14 > 1.0F) {
                  var14 = 1.0F;
               }

               if (var15 > 1.0F) {
                  var15 = 1.0F;
               }

               if (var16 > 1.0F) {
                  var16 = 1.0F;
               }

               if (var14 < 0.0F) {
                  var14 = 0.0F;
               }

               if (var15 < 0.0F) {
                  var15 = 0.0F;
               }

               if (var16 < 0.0F) {
                  var16 = 0.0F;
               }

               short var21 = 255;
               int var22 = (int)(var14 * 255.0F);
               int var23 = (int)(var15 * 255.0F);
               int var24 = (int)(var16 * 255.0F);
               this.lightmapColors[var7] = 0xFF000000 | var22 << 16 | var23 << 8 | var24;
            }

            this.lightmapTexture.updateDynamicTexture();
            this.lightmapUpdateNeeded = false;
            this.mc.mcProfiler.endSection();
         }
      }
   }

   public float getNightVisionBrightness(EntityLivingBase entityLivingBase, float float2) {
      int var5 = entityLivingBase.getActivePotionEffect(Potion.nightVision).getDuration();
      return var5 > 200 ? 1.0F : 0.7F + MathHelper.sin((var5 - float2) * (float) Math.PI * 0.2F) * 0.3F;
   }

   public void updateCameraAndRender(float float1, long long2) {
      Config.renderPartialTicks = float1;
      this.frameInit();
      boolean var6 = Display.isActive();
      if (!var6 && this.mc.gameSettings.pauseOnLostFocus && (!this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
         if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
            this.mc.displayInGameMenu();
         }
      } else {
         this.prevFrameTime = Minecraft.getSystemTime();
      }

      this.mc.mcProfiler.startSection("mouse");
      if (var6 && Minecraft.isRunningOnMac && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
         Mouse.setGrabbed(false);
         Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
         Mouse.setGrabbed(true);
      }

      if (this.mc.inGameHasFocus && var6) {
         this.mc.mouseHelper.Method893();
         float var7 = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
         float var8 = var7 * var7 * var7 * 8.0F;
         float var9 = this.mc.mouseHelper.deltaX * var8;
         float var10 = this.mc.mouseHelper.deltaY * var8;
         byte var11 = 1;
         if (this.mc.gameSettings.invertMouse) {
            var11 = -1;
         }

         if (this.mc.gameSettings.smoothCamera) {
            this.smoothCamYaw += var9;
            this.smoothCamPitch += var10;
            float var12 = float1 - this.smoothCamPartialTicks;
            this.smoothCamPartialTicks = float1;
            var9 = this.smoothCamFilterX * var12;
            var10 = this.smoothCamFilterY * var12;
            this.mc.thePlayer.setAngles(var9, var10 * var11);
         } else {
            this.smoothCamYaw = 0.0F;
            this.smoothCamPitch = 0.0F;
            this.mc.thePlayer.setAngles(var9, var10 * var11);
         }
      }

      this.mc.mcProfiler.endSection();
      if (!this.mc.skipRenderWorld) {
         Moon.getInstance().getEventBus().publish(new RenderFrameEvent());
         anaglyphEnable = this.mc.gameSettings.anaglyph;
         ScaledResolution var19 = new ScaledResolution(this.mc);
         int var20 = var19.Method237();
         int var22 = var19.Method238();
         int var24 = Mouse.getX() * var20 / this.mc.displayWidth;
         int var25 = var22 - Mouse.getY() * var22 / this.mc.displayHeight - 1;
         int var26 = this.mc.gameSettings.limitFramerate;
         if (this.mc.theWorld == null) {
            GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            this.setupOverlayRendering();
            this.renderEndNanoTime = System.nanoTime();
            TileEntityRendererDispatcher.instance.renderEngine = this.mc.getTextureManager();
            TileEntityRendererDispatcher.instance.fontRenderer = this.mc.fontRendererObj;
         } else {
            this.mc.mcProfiler.startSection("level");
            int var13 = Math.max(Math.min(Minecraft.getDebugFPS(), var26), 60);
            long var14 = System.nanoTime() - long2;
            long var16 = Math.max(1000000000 / var13 / 4 - var14, 0L);
            this.renderWorld(float1, System.nanoTime() + var16);
            this.renderEndNanoTime = System.nanoTime();
            this.mc.mcProfiler.endStartSection("gui");
            if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
               GlStateManager.alphaFunc(516, 0.1F);
               this.mc.ingameGUI.renderGameOverlay(float1);
               if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
                  Config.drawFps();
               }

               if (this.mc.gameSettings.showDebugInfo) {
                  Lagometer.showLagometer(var19);
               }
            }

            this.mc.mcProfiler.endSection();
         }

         if (this.mc.currentScreen != null) {
            GlStateManager.clear(256);

            try {
               this.mc.currentScreen.onDrawScreen(var24, var25, float1);
            } catch (Throwable var18) {
               CrashReport var27 = CrashReport.makeCrashReport(var18, "Rendering screen");
               CrashReportCategory var15 = var27.makeCategory("Screen render details");
               var15.addCrashSectionCallable("Screen name", () -> this.mc.currentScreen.getClass().getCanonicalName());
               var15.addCrashSectionCallable(
                  "Mouse location", () -> String.format("Scaled: (%d, %d). Absolute: (%d, %d)", var24, var25, Mouse.getX(), Mouse.getY())
               );
               var15.addCrashSectionCallable(
                  "Screen size",
                  () -> String.format(
                     "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d",
                     var19.Method237(),
                     var19.Method238(),
                     this.mc.displayWidth,
                     this.mc.displayHeight,
                     var19.getScaleFactor()
                  )
               );
               throw new ReportedException(var27);
            }
         }

         Moon.getInstance().getEventBus().publish(new PostFrameEvent());
      }

      this.frameFinish();
      this.waitForServerThread();
      MemoryMonitor.update();
      Lagometer.updateLagometer();
      if (this.mc.gameSettings.ofProfiler) {
         this.mc.gameSettings.showDebugProfilerChart = true;
      }
   }

   private boolean isDrawBlockOutline() {
      Objects.requireNonNull(this);
      Entity var1 = this.mc.getRenderViewEntity();
      boolean var2 = var1 instanceof EntityPlayer && !this.mc.gameSettings.hideGUI;
      if (var2 && !((EntityPlayer)var1).capabilities.allowEdit) {
         ItemStack var3 = ((EntityPlayer)var1).getCurrentEquippedItem();
         if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            BlockPos var4 = this.mc.objectMouseOver.getBlockPos();
            IBlockState var5 = this.mc.theWorld.getBlockState(var4);
            Block var6 = var5.getBlock();
            if (this.mc.playerController.getCurrentGameType() == WorldSettings.GameType.SPECTATOR) {
               var2 = var5.getBlock().hasTileEntity() && this.mc.theWorld.getTileEntity(var4) instanceof IInventory;
            } else {
               var2 = var3 != null && (var3.canDestroy(var6) || var3.canPlaceOn(var6));
            }
         }
      }

      return var2;
   }

   private void renderWorldDirections(float float1) {
      if (this.mc.gameSettings.showDebugInfo && !this.mc.gameSettings.hideGUI && !this.mc.thePlayer.hasReducedDebug() && !this.mc.gameSettings.reducedDebugInfo
         )
       {
         Entity var4 = this.mc.getRenderViewEntity();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GL11.glLineWidth(1.0F);
         GlStateManager.disableTexture2D();
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         this.orientCamera(float1);
         GlStateManager.translate(0.0F, var4.getEyeHeight(), 0.0F);
         RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), 255, 0, 0, 255);
         RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), 0, 0, 255, 255);
         RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), 0, 255, 0, 255);
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         GlStateManager.enableTexture2D();
         GlStateManager.disableBlend();
      }
   }

   public void renderWorld(float float1, long long2) {
      this.updateLightmap(float1);
      if (this.mc.getRenderViewEntity() == null) {
         this.mc.setRenderViewEntity(this.mc.thePlayer);
      }

      double var6 = this.mc.getRenderViewEntity().posX;
      double var8 = this.mc.getRenderViewEntity().posY;
      double var10 = this.mc.getRenderViewEntity().posZ;
      CameraPositionEvent var12 = new CameraPositionEvent(var6, var8, var10);
      Moon.getInstance().getEventBus().publish(var12);
      this.mc.getRenderViewEntity().posX = var12.getX();
      this.mc.getRenderViewEntity().posY = var12.getY();
      this.mc.getRenderViewEntity().posZ = var12.getZ();
      this.getMouseOver(float1);
      if (Config.isShaders()) {
         Shaders.beginRender(this.mc, float1, long2);
      }

      GlStateManager.enableDepth();
      GlStateManager.enableAlpha();
      GlStateManager.alphaFunc(516, 0.1F);
      this.mc.mcProfiler.startSection("center");
      if (this.mc.gameSettings.anaglyph) {
         anaglyphField = 0;
         GlStateManager.colorMask(false, true, true, false);
         this.renderWorldPass(0, float1, long2);
         anaglyphField = 1;
         GlStateManager.colorMask(true, false, false, false);
         this.renderWorldPass(1, float1, long2);
         GlStateManager.colorMask(true, true, true, false);
      } else {
         this.renderWorldPass(2, float1, long2);
      }

      this.mc.mcProfiler.endSection();
   }

   private void renderWorldPass(int integer, float float2, long long3) {
      boolean var7 = Config.isShaders();
      if (var7) {
         Shaders.beginRenderPass(integer, float2, long3);
      }

      RenderGlobal var8 = this.mc.renderGlobal;
      EffectRenderer var9 = this.mc.effectRenderer;
      boolean var10 = this.isDrawBlockOutline();
      GlStateManager.enableCull();
      this.mc.mcProfiler.endStartSection("clear");
      if (var7) {
         Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      } else {
         GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
      }

      this.updateFogColor(float2);
      GlStateManager.clear(16640);
      if (var7) {
         Shaders.clearRenderBuffer();
      }

      this.mc.mcProfiler.endStartSection("camera");
      this.setupCameraTransform(float2, integer);
      if (var7) {
         Shaders.setCamera(float2);
      }

      ActiveRenderInfo.updateRenderInfo(this.mc.thePlayer, this.mc.gameSettings.thirdPersonView == 2);
      this.mc.mcProfiler.endStartSection("frustum");
      ClippingHelper var11 = ClippingHelperImpl.getInstance();
      this.mc.mcProfiler.endStartSection("culling");
      var11.disabled = Config.isShaders() && !Shaders.isFrustumCulling();
      Frustum var12 = new Frustum(var11);
      Entity var13 = this.mc.getRenderViewEntity();
      double var14 = var13.lastTickPosX + (var13.posX - var13.lastTickPosX) * float2;
      double var16 = var13.lastTickPosY + (var13.posY - var13.lastTickPosY) * float2;
      double var18 = var13.lastTickPosZ + (var13.posZ - var13.lastTickPosZ) * float2;
      if (var7) {
         ShadersRender.setFrustrumPosition(var12, var14, var16, var18);
      } else {
         var12.setPosition(var14, var16, var18);
      }

      if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
         this.setupFog(-1, float2);
         this.mc.mcProfiler.endStartSection("sky");
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(float2, true), (float)this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
         if (var7) {
            Shaders.beginSky();
         }

         var8.renderSky(float2, integer);
         if (var7) {
            Shaders.endSky();
         }

         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(float2, true), (float)this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
      } else {
         GlStateManager.disableBlend();
      }

      this.setupFog(0, float2);
      GlStateManager.shadeModel(7425);
      if (var13.posY + var13.getEyeHeight() < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0F) {
         this.renderCloudsCheck(var8, float2, integer);
      }

      this.mc.mcProfiler.endStartSection("prepareterrain");
      this.setupFog(0, float2);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      RenderHelper.disableStandardItemLighting();
      this.mc.mcProfiler.endStartSection("terrain_setup");
      this.checkLoadVisibleChunks(var13, float2, var12, this.mc.thePlayer.isSpectator());
      if (var7) {
         ShadersRender.setupTerrain(var8, var13, float2, var12, this.frameCount++, this.mc.thePlayer.isSpectator());
      } else {
         var8.setupTerrain(var13, float2, var12, this.frameCount++, this.mc.thePlayer.isSpectator());
      }

      if (integer == 0 || integer == 2) {
         this.mc.mcProfiler.endStartSection("updatechunks");
         Lagometer.timerChunkUpload.start();
         this.mc.renderGlobal.updateChunks(long3);
         Lagometer.timerChunkUpload.end();
      }

      this.mc.mcProfiler.endStartSection("terrain");
      Lagometer.timerTerrain.start();
      if (this.mc.gameSettings.ofSmoothFps && integer > 0) {
         this.mc.mcProfiler.endStartSection("finish");
         GL11.glFinish();
         this.mc.mcProfiler.endStartSection("terrain");
      }

      GlStateManager.matrixMode(5888);
      GlStateManager.pushMatrix();
      GlStateManager.disableAlpha();
      if (var7) {
         ShadersRender.beginTerrainSolid();
      }

      var8.renderBlockLayer(EnumWorldBlockLayer.SOLID, float2, integer, var13);
      GlStateManager.enableAlpha();
      if (var7) {
         ShadersRender.beginTerrainCutoutMipped();
      }

      this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, this.mc.gameSettings.mipmapLevels > 0);
      var8.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, float2, integer, var13);
      this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
      this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
      if (var7) {
         ShadersRender.beginTerrainCutout();
      }

      var8.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, float2, integer, var13);
      this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
      if (var7) {
         ShadersRender.endTerrain();
      }

      Lagometer.timerTerrain.end();
      GlStateManager.shadeModel(7424);
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      RenderHelper.enableStandardItemLighting();
      this.mc.mcProfiler.endStartSection("entities");
      EntityCullingHandler.enabled = true;
      var8.renderEntities(var13, var12, float2);
      EntityCullingHandler.enabled = false;
      RenderHelper.disableStandardItemLighting();
      this.disableLightmap();
      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      GlStateManager.pushMatrix();
      if (this.mc.objectMouseOver != null && var13.isInsideOfMaterial(Material.water) && var10) {
         EntityPlayer var20 = (EntityPlayer)var13;
         GlStateManager.disableAlpha();
         this.mc.mcProfiler.endStartSection("outline");
         var8.drawSelectionBox(var20, this.mc.objectMouseOver, 0, float2);
         GlStateManager.enableAlpha();
      }

      GlStateManager.matrixMode(5888);
      GlStateManager.popMatrix();
      if (var10 && this.mc.objectMouseOver != null && !var13.isInsideOfMaterial(Material.water)) {
         EntityPlayer var21 = (EntityPlayer)var13;
         GlStateManager.disableAlpha();
         this.mc.mcProfiler.endStartSection("outline");
         if (!this.mc.gameSettings.showDebugInfo) {
            var8.drawSelectionBox(var21, this.mc.objectMouseOver, 0, float2);
         }

         GlStateManager.enableAlpha();
      }

      if (!var8.damagedBlocks.isEmpty()) {
         this.mc.mcProfiler.endStartSection("destroyProgress");
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
         var8.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getWorldRenderer(), var13, float2);
         this.mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
         GlStateManager.disableBlend();
      }

      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.disableBlend();
      Objects.requireNonNull(this);
      this.enableLightmap();
      this.mc.mcProfiler.endStartSection("litParticles");
      if (var7) {
         Shaders.beginLitParticles();
      }

      var9.renderLitParticles(var13, float2);
      RenderHelper.disableStandardItemLighting();
      this.setupFog(0, float2);
      this.mc.mcProfiler.endStartSection("particles");
      if (var7) {
         Shaders.beginParticles();
      }

      var9.renderParticles(var13, float2);
      if (var7) {
         Shaders.endParticles();
      }

      this.disableLightmap();
      GlStateManager.depthMask(false);
      if (Config.isShaders()) {
         GlStateManager.depthMask(Shaders.isRainDepth());
      }

      GlStateManager.enableCull();
      this.mc.mcProfiler.endStartSection("weather");
      if (var7) {
         Shaders.beginWeather();
      }

      this.renderRainSnow(float2);
      if (var7) {
         Shaders.endWeather();
      }

      GlStateManager.depthMask(true);
      var8.renderWorldBorder(var13, float2);
      if (var7) {
         ShadersRender.renderHand0(this, float2, integer);
         Shaders.preWater();
      }

      GlStateManager.disableBlend();
      GlStateManager.enableCull();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.alphaFunc(516, 0.1F);
      this.setupFog(0, float2);
      GlStateManager.enableBlend();
      GlStateManager.depthMask(false);
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      GlStateManager.shadeModel(7425);
      this.mc.mcProfiler.endStartSection("translucent");
      if (var7) {
         Shaders.beginWater();
      }

      var8.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, float2, integer, var13);
      if (var7) {
         Shaders.endWater();
      }

      GlStateManager.shadeModel(7424);
      GlStateManager.depthMask(true);
      GlStateManager.enableCull();
      GlStateManager.disableBlend();
      GlStateManager.disableFog();
      if (var13.posY + var13.getEyeHeight() >= 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0F) {
         this.mc.mcProfiler.endStartSection("aboveClouds");
         this.renderCloudsCheck(var8, float2, integer);
      }

      this.mc.mcProfiler.endStartSection("hand");
      Moon.getInstance().getEventBus().publish(new WorldRenderEvent(WorldRenderEvent.Sequence.PRE));
      Objects.requireNonNull(this);
      if (!Shaders.isShadowPass) {
         if (var7) {
            ShadersRender.renderHand1(this, float2, integer);
            Shaders.renderCompositeFinal();
         }

         if (var7) {
            ShadersRender.renderFPOverlay(this, float2, integer);
         } else {
            this.renderHand(float2, integer);
         }

         this.renderWorldDirections(float2);
      }

      if (var7) {
         Shaders.endRender();
      }

      Moon.getInstance().getEventBus().publish(new WorldRenderEvent(WorldRenderEvent.Sequence.POST));
   }

   private void renderCloudsCheck(RenderGlobal renderGlobal, float float2, int integer) {
      if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
         this.mc.mcProfiler.endStartSection("clouds");
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(float2, true), (float)this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         this.setupFog(0, float2);
         renderGlobal.renderClouds(float2, integer);
         GlStateManager.disableFog();
         GlStateManager.popMatrix();
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         Project.gluPerspective(this.getFOVModifier(float2, true), (float)this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
         GlStateManager.matrixMode(5888);
      }
   }

   private void addRainParticles() {
      float var3 = this.mc.theWorld.getRainStrength(1.0F);
      if (!Config.isRainFancy()) {
         var3 /= 2.0F;
      }

      if (var3 != 0.0F && Config.isRainSplash()) {
         this.random.setSeed(this.rendererUpdateCount * 312987231L);
         Entity var4 = this.mc.getRenderViewEntity();
         WorldClient var5 = this.mc.theWorld;
         BlockPos var6 = new BlockPos(var4);
         byte var7 = 10;
         double var8 = 0.0;
         double var10 = 0.0;
         double var12 = 0.0;
         int var14 = 0;
         int var15 = (int)(100.0F * var3 * var3);
         if (this.mc.gameSettings.particleSetting == 1) {
            var15 >>= 1;
         } else if (this.mc.gameSettings.particleSetting == 2) {
            var15 = 0;
         }

         for (int var16 = 0; var16 < var15; var16++) {
            BlockPos var17 = var5.getPrecipitationHeight(
               var6.add(this.random.nextInt(var7) - this.random.nextInt(var7), 0, this.random.nextInt(var7) - this.random.nextInt(var7))
            );
            BiomeGenBase var18 = var5.getBiomeGenForCoords(var17);
            BlockPos var19 = var17.down();
            Block var20 = var5.getBlockState(var19).getBlock();
            if (var17.getY() <= var6.getY() + var7 && var17.getY() >= var6.getY() - var7 && var18.canRain() && var18.getFloatTemperature(var17) >= 0.15F) {
               double var21 = this.random.nextDouble();
               double var23 = this.random.nextDouble();
               if (var20.getMaterial() == Material.lava) {
                  this.mc
                     .theWorld
                     .spawnParticle(
                        EnumParticleTypes.SMOKE_NORMAL,
                        var17.getX() + var21,
                        var17.getY() + 0.1F - var20.getBlockBoundsMinY(),
                        var17.getZ() + var23,
                        0.0,
                        0.0,
                        0.0
                     );
               } else if (var20.getMaterial() != Material.air) {
                  var20.setBlockBoundsBasedOnState(var5, var19);
                  if (this.random.nextInt(++var14) == 0) {
                     var8 = var19.getX() + var21;
                     var10 = var19.getY() + 0.1F + var20.getBlockBoundsMaxY() - 1.0;
                     var12 = var19.getZ() + var23;
                  }

                  this.mc
                     .theWorld
                     .spawnParticle(
                        EnumParticleTypes.WATER_DROP,
                        var19.getX() + var21,
                        var19.getY() + 0.1F + var20.getBlockBoundsMaxY(),
                        var19.getZ() + var23,
                        0.0,
                        0.0,
                        0.0
                     );
               }
            }
         }

         if (var14 > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
            this.rainSoundCounter = 0;
            if (var10 > var6.getY() + 1 && var5.getPrecipitationHeight(var6).getY() > MathHelper.floor_float(var6.getY())) {
               this.mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.1F, 0.5F, false);
            } else {
               this.mc.theWorld.playSound(var8, var10, var12, "ambient.weather.rain", 0.2F, 1.0F, false);
            }
         }
      }
   }

   protected void renderRainSnow(float float1) {
      float var4 = this.mc.theWorld.getRainStrength(float1);
      if (var4 > 0.0F) {
         if (Config.isRainOff()) {
            return;
         }

         this.enableLightmap();
         Entity var5 = this.mc.getRenderViewEntity();
         WorldClient var6 = this.mc.theWorld;
         int var7 = MathHelper.floor_double(var5.posX);
         int var8 = MathHelper.floor_double(var5.posY);
         int var9 = MathHelper.floor_double(var5.posZ);
         Tessellator var10 = Tessellator.getInstance();
         WorldRenderer var11 = var10.getWorldRenderer();
         GlStateManager.disableCull();
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.alphaFunc(516, 0.1F);
         double var12 = var5.lastTickPosX + (var5.posX - var5.lastTickPosX) * float1;
         double var14 = var5.lastTickPosY + (var5.posY - var5.lastTickPosY) * float1;
         double var16 = var5.lastTickPosZ + (var5.posZ - var5.lastTickPosZ) * float1;
         int var18 = MathHelper.floor_double(var14);
         byte var19 = 5;
         if (Config.isRainFancy()) {
            var19 = 10;
         }

         byte var20 = -1;
         float var21 = this.rendererUpdateCount + float1;
         var11.setTranslation(-var12, -var14, -var16);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         BlockPos.MutableBlockPos var22 = new BlockPos.MutableBlockPos();

         for (int var23 = var9 - var19; var23 <= var9 + var19; var23++) {
            for (int var24 = var7 - var19; var24 <= var7 + var19; var24++) {
               int var25 = (var23 - var9 + 16) * 32 + var24 - var7 + 16;
               double var26 = this.rainXCoords[var25] * 0.5;
               double var28 = this.rainYCoords[var25] * 0.5;
               var22.set(var24, 0, var23);
               BiomeGenBase var30 = var6.getBiomeGenForCoords(var22);
               if (var30.canRain() || var30.getEnableSnow()) {
                  int var31 = var6.getPrecipitationHeight(var22).getY();
                  int var32 = var8 - var19;
                  int var33 = var8 + var19;
                  if (var32 < var31) {
                     var32 = var31;
                  }

                  if (var33 < var31) {
                     var33 = var31;
                  }

                  int var34 = Math.max(var31, var18);
                  if (var32 != var33) {
                     this.random.setSeed((long)var24 * var24 * 3121L + var24 * 45238971L ^ (long)var23 * var23 * 418711L + var23 * 13761L);
                     var22.set(var24, var32, var23);
                     float var35 = var30.getFloatTemperature(var22);
                     if (var6.getWorldChunkManager().getTemperatureAtHeight(var35, var31) >= 0.15F) {
                        if (var20 != 0) {
                           if (var20 >= 0) {
                              var10.draw();
                           }

                           var20 = 0;
                           this.mc.getTextureManager().bindTexture(locationRainPng);
                           var11.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }

                        double var36 = (
                              (double)(this.rendererUpdateCount + var24 * var24 * 3121 + var24 * 45238971 + var23 * var23 * 418711 + var23 * 13761 & 31)
                                 + float1
                           )
                           / 32.0
                           * (3.0 + this.random.nextDouble());
                        double var38 = var24 + 0.5F - var5.posX;
                        double var40 = var23 + 0.5F - var5.posZ;
                        float var42 = MathHelper.sqrt_double(var38 * var38 + var40 * var40) / var19;
                        float var43 = ((1.0F - var42 * var42) * 0.5F + 0.5F) * var4;
                        var22.set(var24, var34, var23);
                        int var44 = var6.getCombinedLight(var22, 0);
                        int var45 = var44 >> 16 & 65535;
                        int var46 = var44 & 65535;
                        var11.pos(var24 - var26 + 0.5, var32, var23 - var28 + 0.5)
                           .tex(0.0, var32 * 0.25 + var36)
                           .color(1.0F, 1.0F, 1.0F, var43)
                           .lightmap(var45, var46)
                           .endVertex();
                        var11.pos(var24 + var26 + 0.5, var32, var23 + var28 + 0.5)
                           .tex(1.0, var32 * 0.25 + var36)
                           .color(1.0F, 1.0F, 1.0F, var43)
                           .lightmap(var45, var46)
                           .endVertex();
                        var11.pos(var24 + var26 + 0.5, var33, var23 + var28 + 0.5)
                           .tex(1.0, var33 * 0.25 + var36)
                           .color(1.0F, 1.0F, 1.0F, var43)
                           .lightmap(var45, var46)
                           .endVertex();
                        var11.pos(var24 - var26 + 0.5, var33, var23 - var28 + 0.5)
                           .tex(0.0, var33 * 0.25 + var36)
                           .color(1.0F, 1.0F, 1.0F, var43)
                           .lightmap(var45, var46)
                           .endVertex();
                     } else {
                        if (var20 != 1) {
                           if (var20 == 0) {
                              var10.draw();
                           }

                           var20 = 1;
                           this.mc.getTextureManager().bindTexture(locationSnowPng);
                           var11.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                        }

                        double var51 = ((this.rendererUpdateCount & 511) + float1) / 512.0F;
                        double var52 = this.random.nextDouble() + var21 * 0.01 * (float)this.random.nextGaussian();
                        double var53 = this.random.nextDouble() + var21 * (float)this.random.nextGaussian() * 0.001;
                        double var54 = var24 + 0.5F - var5.posX;
                        double var55 = var23 + 0.5F - var5.posZ;
                        float var56 = MathHelper.sqrt_double(var54 * var54 + var55 * var55) / var19;
                        float var47 = ((1.0F - var56 * var56) * 0.3F + 0.5F) * var4;
                        var22.set(var24, var34, var23);
                        int var48 = (var6.getCombinedLight(var22, 0) * 3 + 15728880) / 4;
                        int var49 = var48 >> 16 & 65535;
                        int var50 = var48 & 65535;
                        var11.pos(var24 - var26 + 0.5, var32, var23 - var28 + 0.5)
                           .tex(0.0 + var52, var32 * 0.25 + var51 + var53)
                           .color(1.0F, 1.0F, 1.0F, var47)
                           .lightmap(var49, var50)
                           .endVertex();
                        var11.pos(var24 + var26 + 0.5, var32, var23 + var28 + 0.5)
                           .tex(1.0 + var52, var32 * 0.25 + var51 + var53)
                           .color(1.0F, 1.0F, 1.0F, var47)
                           .lightmap(var49, var50)
                           .endVertex();
                        var11.pos(var24 + var26 + 0.5, var33, var23 + var28 + 0.5)
                           .tex(1.0 + var52, var33 * 0.25 + var51 + var53)
                           .color(1.0F, 1.0F, 1.0F, var47)
                           .lightmap(var49, var50)
                           .endVertex();
                        var11.pos(var24 - var26 + 0.5, var33, var23 - var28 + 0.5)
                           .tex(0.0 + var52, var33 * 0.25 + var51 + var53)
                           .color(1.0F, 1.0F, 1.0F, var47)
                           .lightmap(var49, var50)
                           .endVertex();
                     }
                  }
               }
            }
         }

         if (var20 >= 0) {
            var10.draw();
         }

         var11.setTranslation(0.0, 0.0, 0.0);
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GlStateManager.alphaFunc(516, 0.1F);
         this.disableLightmap();
      }
   }

   public void setupOverlayRendering() {
      ScaledResolution var3 = new ScaledResolution(this.mc);
      GlStateManager.clear(256);
      GlStateManager.matrixMode(5889);
      GlStateManager.loadIdentity();
      GlStateManager.ortho(0.0, var3.getScaledWidth_double(), var3.getScaledHeight_double(), 0.0, 1000.0, 3000.0);
      GlStateManager.matrixMode(5888);
      GlStateManager.loadIdentity();
      GlStateManager.translate(0.0F, 0.0F, -2000.0F);
   }

   private void updateFogColor(float float1) {
      WorldClient var4 = this.mc.theWorld;
      Entity var5 = this.mc.getRenderViewEntity();
      float var6 = 0.25F + 0.75F * this.mc.gameSettings.renderDistanceChunks / 32.0F;
      var6 = 1.0F - (float)Math.pow(var6, 0.25);
      Vec3 var7 = var4.getSkyColor(this.mc.getRenderViewEntity(), float1);
      var7 = CustomColors.getWorldSkyColor(var7, var4, this.mc.getRenderViewEntity(), float1);
      float var8 = (float)var7.xCoord;
      float var9 = (float)var7.yCoord;
      float var10 = (float)var7.zCoord;
      Vec3 var11 = var4.getFogColor(float1);
      var11 = CustomColors.getWorldFogColor(var11, var4, this.mc.getRenderViewEntity(), float1);
      this.fogColorRed = (float)var11.xCoord;
      this.fogColorGreen = (float)var11.yCoord;
      this.fogColorBlue = (float)var11.zCoord;
      if (this.mc.gameSettings.renderDistanceChunks >= 4) {
         Vec3 var14 = MathHelper.sin(var4.getCelestialAngleRadians(float1)) > 0.0F ? new Vec3(-1.0, 0.0, 0.0) : new Vec3(1.0, 0.0, 0.0);
         float var15 = (float)var5.getLook(float1).dotProduct(var14);
         if (var15 < 0.0F) {
            var15 = 0.0F;
         }

         if (var15 > 0.0F) {
            float[] var16 = var4.provider.calcSunriseSunsetColors(var4.getCelestialAngle(float1), float1);
            if (var16 != null) {
               var15 *= var16[3];
               this.fogColorRed = this.fogColorRed * (1.0F - var15) + var16[0] * var15;
               this.fogColorGreen = this.fogColorGreen * (1.0F - var15) + var16[1] * var15;
               this.fogColorBlue = this.fogColorBlue * (1.0F - var15) + var16[2] * var15;
            }
         }
      }

      this.fogColorRed = this.fogColorRed + (var8 - this.fogColorRed) * var6;
      this.fogColorGreen = this.fogColorGreen + (var9 - this.fogColorGreen) * var6;
      this.fogColorBlue = this.fogColorBlue + (var10 - this.fogColorBlue) * var6;
      float var12 = var4.getRainStrength(float1);
      if (var12 > 0.0F) {
         float var13 = 1.0F - var12 * 0.5F;
         float var25 = 1.0F - var12 * 0.4F;
         this.fogColorRed *= var13;
         this.fogColorGreen *= var13;
         this.fogColorBlue *= var25;
      }

      float var24 = var4.getThunderStrength(float1);
      if (var24 > 0.0F) {
         float var26 = 1.0F - var24 * 0.5F;
         this.fogColorRed *= var26;
         this.fogColorGreen *= var26;
         this.fogColorBlue *= var26;
      }

      Block var27 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var5, float1);
      if (this.cloudFog) {
         Vec3 var29 = var4.Method330(float1);
         this.fogColorRed = (float)var29.xCoord;
         this.fogColorGreen = (float)var29.yCoord;
         this.fogColorBlue = (float)var29.zCoord;
      } else if (var27.getMaterial() == Material.water) {
         float var30 = Config.Method256(EnchantmentHelper.getRespiration(var5) * 0.2F, 0.0F, 0.6F);
         if (var5 instanceof EntityLivingBase && ((EntityLivingBase)var5).isPotionActive(Potion.waterBreathing)) {
            var30 = var30 * 0.3F + 0.6F;
         }

         this.fogColorRed = 0.02F + var30;
         this.fogColorGreen = 0.02F + var30;
         this.fogColorBlue = 0.2F + var30;
         Vec3 var33 = CustomColors.getUnderwaterColor(
            this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0, this.mc.getRenderViewEntity().posZ
         );
         if (var33 != null) {
            this.fogColorRed = (float)var33.xCoord;
            this.fogColorGreen = (float)var33.yCoord;
            this.fogColorBlue = (float)var33.zCoord;
         }
      } else if (var27.getMaterial() == Material.lava) {
         this.fogColorRed = 0.6F;
         this.fogColorGreen = 0.1F;
         this.fogColorBlue = 0.0F;
         Vec3 var31 = CustomColors.getUnderlavaColor(
            this.mc.theWorld, this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().posY + 1.0, this.mc.getRenderViewEntity().posZ
         );
         if (var31 != null) {
            this.fogColorRed = (float)var31.xCoord;
            this.fogColorGreen = (float)var31.yCoord;
            this.fogColorBlue = (float)var31.zCoord;
         }
      }

      float var32 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * float1;
      this.fogColorRed *= var32;
      this.fogColorGreen *= var32;
      this.fogColorBlue *= var32;
      double var34 = (var5.lastTickPosY + (var5.posY - var5.lastTickPosY) * float1) * var4.provider.getVoidFogYFactor();
      if (var5 instanceof EntityLivingBase && ((EntityLivingBase)var5).isPotionActive(Potion.blindness)) {
         int var18 = ((EntityLivingBase)var5).getActivePotionEffect(Potion.blindness).getDuration();
         if (var18 < 20) {
            var34 *= 1.0F - var18 / 20.0F;
         } else {
            var34 = 0.0;
         }
      }

      if (var34 < 1.0) {
         if (var34 < 0.0) {
            var34 = 0.0;
         }

         var34 *= var34;
         this.fogColorRed = (float)(this.fogColorRed * var34);
         this.fogColorGreen = (float)(this.fogColorGreen * var34);
         this.fogColorBlue = (float)(this.fogColorBlue * var34);
      }

      if (this.bossColorModifier > 0.0F) {
         float var36 = this.bossColorModifierPrev + (this.bossColorModifier - this.bossColorModifierPrev) * float1;
         this.fogColorRed = this.fogColorRed * (1.0F - var36) + this.fogColorRed * 0.7F * var36;
         this.fogColorGreen = this.fogColorGreen * (1.0F - var36) + this.fogColorGreen * 0.6F * var36;
         this.fogColorBlue = this.fogColorBlue * (1.0F - var36) + this.fogColorBlue * 0.6F * var36;
      }

      if (var5 instanceof EntityLivingBase && ((EntityLivingBase)var5).isPotionActive(Potion.nightVision)) {
         float var37 = this.getNightVisionBrightness((EntityLivingBase)var5, float1);
         float var19 = 1.0F / this.fogColorRed;
         if (var19 > 1.0F / this.fogColorGreen) {
            var19 = 1.0F / this.fogColorGreen;
         }

         if (var19 > 1.0F / this.fogColorBlue) {
            var19 = 1.0F / this.fogColorBlue;
         }

         if (Float.isInfinite(var19)) {
            var19 = Math.nextAfter(var19, 0.0);
         }

         this.fogColorRed = this.fogColorRed * (1.0F - var37) + this.fogColorRed * var19 * var37;
         this.fogColorGreen = this.fogColorGreen * (1.0F - var37) + this.fogColorGreen * var19 * var37;
         this.fogColorBlue = this.fogColorBlue * (1.0F - var37) + this.fogColorBlue * var19 * var37;
      }

      if (this.mc.gameSettings.anaglyph) {
         float var38 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
         float var39 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
         float var20 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
         this.fogColorRed = var38;
         this.fogColorGreen = var39;
         this.fogColorBlue = var20;
      }

      Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
   }

   private void setupFog(int integer, float float2) {
      this.fogStandard = false;
      Entity var5 = this.mc.getRenderViewEntity();
      if (var5 instanceof EntityPlayer) {
         ;
      }

      GL11.glFog(2918, this.setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
      GL11.glNormal3f(0.0F, -1.0F, 0.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      Block var7 = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, var5, float2);
      if (-1 >= 0) {
         GlStateManager.setFogDensity(-1.0F);
      } else if (var5 instanceof EntityLivingBase && ((EntityLivingBase)var5).isPotionActive(Potion.blindness)) {
         RenderFogEvent var13 = new RenderFogEvent();
         Moon.getInstance().getEventBus().publish(var13);
         if (!var13.isCancelled()) {
            float var10 = 5.0F;
            int var11 = ((EntityLivingBase)var5).getActivePotionEffect(Potion.blindness).getDuration();
            if (var11 < 20) {
               var10 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - var11 / 20.0F);
            }

            GlStateManager.setFog(9729);
            if (integer == -1) {
               GlStateManager.setFogStart(0.0F);
               GlStateManager.setFogEnd(var10 * 0.8F);
            } else {
               GlStateManager.setFogStart(var10 * 0.25F);
               GlStateManager.setFogEnd(var10);
            }

            if (GLContext.getCapabilities().GL_NV_fog_distance && Config.isFogFancy()) {
               GL11.glFogi(NVFogDistance.GL_FOG_DISTANCE_MODE_NV, 34139);
            }
         }
      } else if (this.cloudFog) {
         GlStateManager.setFog(2048);
         GlStateManager.setFogDensity(0.1F);
      } else if (var7.getMaterial() == Material.water) {
         GlStateManager.setFog(2048);
         float var9 = Config.isClearWater() ? 0.02F : 0.1F;
         if (var5 instanceof EntityLivingBase && ((EntityLivingBase)var5).isPotionActive(Potion.waterBreathing)) {
            GlStateManager.setFogDensity(0.01F);
         } else {
            GlStateManager.setFogDensity(Config.Method256(0.1F - EnchantmentHelper.getRespiration(var5) * 0.03F, 0.0F, var9));
         }
      } else if (var7.getMaterial() == Material.lava) {
         GlStateManager.setFog(2048);
         GlStateManager.setFogDensity(2.0F);
      } else {
         float var12 = this.farPlaneDistance;
         this.fogStandard = true;
         GlStateManager.setFog(9729);
         if (integer == -1) {
            GlStateManager.setFogStart(0.0F);
            GlStateManager.setFogEnd(var12);
         } else {
            GlStateManager.setFogStart(var12 * Config.getFogStart());
            GlStateManager.setFogEnd(var12);
         }

         if (GLContext.getCapabilities().GL_NV_fog_distance) {
            if (Config.isFogFancy()) {
               GL11.glFogi(NVFogDistance.GL_FOG_DISTANCE_MODE_NV, 34139);
            }

            if (Config.isFogFast()) {
               GL11.glFogi(NVFogDistance.GL_FOG_DISTANCE_MODE_NV, 34140);
            }
         }

         if (this.mc.theWorld.provider.doesXZShowFog((int)var5.posX, (int)var5.posZ)) {
            GlStateManager.setFogStart(var12 * 0.05F);
            GlStateManager.setFogEnd(var12);
         }
      }

      GlStateManager.enableColorMaterial();
      GlStateManager.enableFog();
      GlStateManager.colorMaterial(1028, 4608);
   }

   private FloatBuffer setFogColorBuffer(float float1, float float2, float float3, float float4) {
      if (Config.isShaders()) {
         Shaders.setFogColor(float1, float2, float3);
      }

      this.fogColorBuffer.clear();
      this.fogColorBuffer.put(float1).put(float2).put(float3).put(float4);
      this.fogColorBuffer.flip();
      return this.fogColorBuffer;
   }

   public MapItemRenderer getMapItemRenderer() {
      return this.theMapItemRenderer;
   }

   private void waitForServerThread() {
      this.serverWaitTimeCurrent = 0;
      if (!Config.isSmoothWorld() || !Config.isSingleProcessor()) {
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
      } else if (this.mc.isIntegratedServerRunning()) {
         IntegratedServer var3 = this.mc.getIntegratedServer();
         if (var3 != null) {
            boolean var4 = this.mc.isGamePaused();
            if (!var4 && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
               if (this.serverWaitTime > 0) {
                  Lagometer.timerServer.start();
                  Config.sleep(this.serverWaitTime);
                  Lagometer.timerServer.end();
                  this.serverWaitTimeCurrent = this.serverWaitTime;
               }

               long var5 = System.nanoTime() / 1000000L;
               if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
                  long var7 = var5 - this.lastServerTime;
                  if (var7 < 0L) {
                     this.lastServerTime = var5;
                     var7 = 0L;
                  }

                  if (var7 >= 50L) {
                     this.lastServerTime = var5;
                     int var9 = var3.getTickCounter();
                     int var10 = var9 - this.lastServerTicks;
                     if (var10 < 0) {
                        this.lastServerTicks = var9;
                        var10 = 0;
                     }

                     if (var10 < 1 && this.serverWaitTime < 100) {
                        this.serverWaitTime += 2;
                     }

                     if (var10 > 1 && this.serverWaitTime > 0) {
                        this.serverWaitTime--;
                     }

                     this.lastServerTicks = var9;
                  }
               } else {
                  this.lastServerTime = var5;
                  this.lastServerTicks = var3.getTickCounter();
                  this.avgServerTickDiff = 1.0F;
                  this.avgServerTimeDiff = 50.0F;
               }
            } else {
               if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
                  Config.sleep(20L);
               }

               this.lastServerTime = 0L;
               this.lastServerTicks = 0;
            }
         }
      }
   }

   private void frameInit() {
      GlErrors.frameStart();
      if (!this.initialized) {
         TextureUtils.registerResourceListener();
         if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
            Config.setNotify64BitJava(true);
         }

         this.initialized = true;
      }

      Config.checkDisplayMode();
      WorldClient var3 = this.mc.theWorld;
      if (var3 != null) {
         if (Config.getNewRelease() != null) {
            String var5 = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light") + " " + Config.getNewRelease();
            ChatComponentText var6 = new ChatComponentText(I18n.format("of.message.newVersion", "§n" + var5 + "§r"));
            var6.setChatStyle(new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://optifine.net/downloads")));
            this.mc.ingameGUI.getChatGUI().printChatMessage(var6);
            Config.setNewRelease((String)null);
         }

         ChatComponentText var4 = new ChatComponentText(I18n.format("of.message.java64Bit"));
         // TODO
         //this.mc.ingameGUI.getChatGUI().printChatMessage(var4);
      }

      if (this.mc.currentScreen instanceof GuiMainMenu) {
         this.Method735((GuiMainMenu)this.mc.currentScreen);
      }

      if (this.updatedWorld != var3) {
         RandomEntities.worldChanged(this.updatedWorld, var3);
         Config.updateThreadPriorities();
         this.lastServerTime = 0L;
         this.lastServerTicks = 0;
         this.updatedWorld = var3;
      }

      if (!this.Method736(Shaders.configAntialiasingLevel)) {
         Shaders.configAntialiasingLevel = 0;
      }

      if (this.mc.currentScreen != null && this.mc.currentScreen.getClass() == GuiChat.class) {
         this.mc.displayGuiScreen(new GuiChatOF((GuiChat)this.mc.currentScreen));
      }
   }

   private void frameFinish() {
      if (this.mc.theWorld != null && Config.isShowGlErrors() && TimedEvent.isActive("CheckGlErrorFrameFinish", 10000L)) {
         int var3 = GlStateManager.glGetError();
         if (var3 != 0 && GlErrors.isEnabled(var3)) {
            String var4 = Config.getGlErrorString(var3);
            ChatComponentText var5 = new ChatComponentText(I18n.format("of.message.openglError", var3, var4));
            this.mc.ingameGUI.getChatGUI().printChatMessage(var5);
         }
      }
   }

   private void Method735(GuiMainMenu guiMainMenu) {
      try {
         String var4 = null;
         Calendar var5 = Calendar.getInstance();
         var5.setTime(new Date());
         int var6 = var5.get(5);
         int var7 = var5.get(2) + 1;
         if (var6 == 8 && var7 == 4) {
            var4 = "Happy birthday, OptiFine!";
         }

         if (var6 == 14 && var7 == 8) {
            var4 = "Happy birthday, sp614x!";
         }

         if (var4 == null) {
            return;
         }

         guiMainMenu.splashText = var4;
      } catch (Throwable var8) {
      }
   }

   public boolean Method736(int integer) {
      if (!OpenGlHelper.isFramebufferEnabled()) {
         return false;
      }

      if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && this.theShaderGroup != this.fxaaShaders[4]) {
         return true;
      }

      if (integer != 2 && integer != 4) {
         if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
            this.theShaderGroup = null;
         }

         return true;
      } else {
         if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[integer]) {
            return true;
         }

         if (this.mc.theWorld == null) {
            return true;
         }

         this.Method733(new ResourceLocation("shaders/post/fxaa_of_" + integer + "x.json"));
         this.fxaaShaders[integer] = this.theShaderGroup;
         return this.useShader;
      }
   }

   private void checkLoadVisibleChunks(Entity entity, float float2, ICamera iCamera, boolean boolean4) {
      int var7 = 201435902;
      if (this.loadVisibleChunks) {
         this.loadVisibleChunks = false;
         this.loadAllVisibleChunks(entity, float2, iCamera, boolean4);
         this.mc.ingameGUI.getChatGUI().deleteChatLine(var7);
      }

      if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(38)) {
         if (this.mc.currentScreen != null) {
            return;
         }

         this.loadVisibleChunks = true;
         ChatComponentText var8 = new ChatComponentText(I18n.format("of.message.loadingVisibleChunks"));
         this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(var8, var7);
      }
   }

   private void loadAllVisibleChunks(Entity entity, double double2, ICamera iCamera, boolean boolean4) {
      int var8 = this.mc.gameSettings.ofChunkUpdates;
      boolean var9 = this.mc.gameSettings.ofLazyChunkLoading;

      try {
         this.mc.gameSettings.ofChunkUpdates = 1000;
         this.mc.gameSettings.ofLazyChunkLoading = false;
         RenderGlobal var10 = Config.getRenderGlobal();
         int var11 = var10.getCountLoadedChunks();
         System.currentTimeMillis();
         Config.Method251("Loading visible chunks");
         long var14 = System.currentTimeMillis() + 5000L;
         int var16 = 0;

         boolean var17;
         do {
            var17 = false;

            for (int var18 = 0; var18 < 100; var18++) {
               var10.displayListEntitiesDirty = true;
               var10.setupTerrain(entity, double2, iCamera, this.frameCount++, boolean4);
               if (!var10.hasNoChunkUpdates()) {
                  var17 = true;
               }

               var16 += var10.getCountChunksToUpdate();

               while (!var10.hasNoChunkUpdates()) {
                  var10.updateChunks(System.nanoTime() + 1000000000L);
               }

               var16 -= var10.getCountChunksToUpdate();
               if (!var17) {
                  break;
               }
            }

            if (var10.getCountLoadedChunks() != var11) {
               var17 = true;
               var11 = var10.getCountLoadedChunks();
            }

            if (System.currentTimeMillis() > var14) {
               Config.log("Chunks loaded: " + var16);
               var14 = System.currentTimeMillis() + 5000L;
            }
         } while (var17);

         Config.log("Chunks loaded: " + var16);
         Config.log("Finished loading visible chunks");
         RenderChunk.renderChunksUpdated = 0;
      } finally {
         this.mc.gameSettings.ofChunkUpdates = var8;
         this.mc.gameSettings.ofLazyChunkLoading = var9;
      }
   }

   public ResourceLocation Method737() {
      return this.locationLightMap;
   }
}
