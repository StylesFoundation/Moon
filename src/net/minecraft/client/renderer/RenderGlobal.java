package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.multiplayer.ChunkProviderClient;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.dispenser.IBehaviorDispenseItem$1;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Matrix4f;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.optifine.CustomColors;
import net.optifine.CustomSky;
import net.optifine.DynamicLights;
import net.optifine.Lagometer;
import net.optifine.RandomEntities;
import net.optifine.SmartAnimations;
import net.optifine.model.BlockModelUtils;
import net.optifine.render.ChunkVisibility;
import net.optifine.render.CloudRenderer;
import net.optifine.render.RenderEnv;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.shaders.ShadowUtils;
import net.optifine.shaders.gui.GuiShaderOptions;
import net.optifine.util.RenderChunkUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener {
   private static final Logger logger = LogManager.getLogger();
   private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation("textures/environment/moon_phases.png");
   private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
   private static final ResourceLocation locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
   private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
   private static final Set g = Set.of(EnumFacing.VALUES);
   private static int renderEntitiesCounter = 0;
   public final Minecraft mc;
   public final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.newHashMap();
   private final TextureManager renderEngine;
   private final RenderManager renderManager;
   private final Set<TileEntity> setTileEntities = Sets.newHashSet();
   private final VertexFormat vertexBufferFormat;
   private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
   private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
   private final ChunkRenderDispatcher renderDispatcher = new ChunkRenderDispatcher();
   private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
   private final Vector3d debugTerrainFrustumPosition = new Vector3d();
   private final CloudRenderer cloudRenderer;
   private final Deque visibilityDeque = new ArrayDeque();
   private final List renderInfosNormal = new ArrayList(1024);
   private final List renderInfosEntitiesNormal = new ArrayList(1024);
   private final List renderInfosTileEntitiesNormal = new ArrayList(1024);
   private final List renderInfosShadow = new ArrayList(1024);
   private final List renderInfosEntitiesShadow = new ArrayList(1024);
   private final List renderInfosTileEntitiesShadow = new ArrayList(1024);
   private final RenderEnv renderEnv = new RenderEnv(Blocks.air.getDefaultState(), new BlockPos(0, 0, 0));
   public boolean displayListEntitiesDirty = true;
   public Entity renderedEntity;
   public Set chunksToResortTransparency = new LinkedHashSet();
   public Set chunksToUpdateForced = new LinkedHashSet();
   public boolean renderOverlayDamaged = false;
   public boolean renderOverlayEyes = false;
   IRenderChunkFactory renderChunkFactory;
   private WorldClient theWorld;
   private Set<RenderChunk> chunksToUpdate = Sets.newLinkedHashSet();
   private List<RenderGlobal.ContainerLocalRenderInformation> renderInfos = Lists.newArrayListWithCapacity(69696);
   private ViewFrustum viewFrustum;
   private int N = -1;
   private int O = -1;
   private int P = -1;
   private VertexBuffer Q;
   private VertexBuffer R;
   private VertexBuffer S;
   private int cloudTickCounter;
   private Framebuffer entityOutlineFramebuffer;
   private ShaderGroup entityOutlineShader;
   private double frustumUpdatePosX = Double.MIN_VALUE;
   private double frustumUpdatePosY = Double.MIN_VALUE;
   private double frustumUpdatePosZ = Double.MIN_VALUE;
   private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
   private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
   private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
   private double lastViewEntityX = Double.MIN_VALUE;
   private double lastViewEntityY = Double.MIN_VALUE;
   private double lastViewEntityZ = Double.MIN_VALUE;
   private double lastViewEntityPitch = Double.MIN_VALUE;
   private double lastViewEntityYaw = Double.MIN_VALUE;
   private ChunkRenderContainer renderContainer;
   private int renderDistanceChunks = -1;
   private int renderEntitiesStartupCounter = 2;
   private int countEntitiesTotal;
   private int countEntitiesRendered;
   private int countEntitiesHidden;
   private boolean debugFixTerrainFrustum = false;
   private ClippingHelper debugFixedClippingHelper;
   private boolean vboEnabled = false;
   private double prevRenderSortX;
   private double prevRenderSortY;
   private double prevRenderSortZ;
   private List<RenderGlobal.ContainerLocalRenderInformation> at = new ArrayList<>(1024);
   private List renderInfosTileEntities = new ArrayList(1024);
   private int renderDistance = 0;
   private int renderDistanceSq = 0;
   private int countTileEntitiesRendered;
   private IChunkProvider worldChunkProvider = null;
   private LongHashMap worldChunkProviderMap = null;
   private int countLoadedChunksPrev = 0;
   private boolean firstWorldLoad = false;

   public RenderGlobal(Minecraft minecraft) {
      this.cloudRenderer = new CloudRenderer(minecraft);
      this.mc = minecraft;
      this.renderManager = minecraft.getRenderManager();
      this.renderEngine = minecraft.getTextureManager();
      this.renderEngine.bindTexture(locationForcefieldPng);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
      GlStateManager.bindTexture(0);
      this.Method716();
      this.vboEnabled = OpenGlHelper.useVbo();
      if (this.vboEnabled) {
         this.renderContainer = new VboRenderList();
         this.renderChunkFactory = new VboChunkFactory();
      } else {
         this.renderContainer = new RenderList();
         this.renderChunkFactory = new ListChunkFactory();
      }

      this.vertexBufferFormat = new VertexFormat();
      this.vertexBufferFormat.addElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
      this.Method719();
      this.Method718();
      this.Method717();
   }

   public static void drawSelectionBoundingBox(AxisAlignedBB axisAlignedBB) {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      var2.begin(3, DefaultVertexFormats.POSITION);
      var2.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
      var2.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
      var2.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
      var2.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
      var2.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
      var1.draw();
      var2.begin(3, DefaultVertexFormats.POSITION);
      var2.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
      var2.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
      var2.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
      var2.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
      var2.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
      var1.draw();
      var2.begin(1, DefaultVertexFormats.POSITION);
      var2.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
      var2.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
      var2.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).endVertex();
      var2.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).endVertex();
      var2.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
      var2.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
      var2.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).endVertex();
      var2.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).endVertex();
      var1.draw();
   }

   public static void drawOutlinedBoundingBox(AxisAlignedBB axisAlignedBB, int integer2, int integer3, int integer4, int integer5) {
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      var6.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var6.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var5.draw();
      var6.begin(3, DefaultVertexFormats.POSITION_COLOR);
      var6.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var5.draw();
      var6.begin(1, DefaultVertexFormats.POSITION_COLOR);
      var6.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).color(integer2, integer3, integer4, integer5).endVertex();
      var6.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).color(integer2, integer3, integer4, integer5).endVertex();
      var5.draw();
   }

   @Override
   public void onResourceManagerReload(IResourceManager iResourceManager) {
      this.Method716();
   }

   private void Method716() {
      TextureMap var1 = this.mc.getTextureMapBlocks();

      for (int var2 = 0; var2 < this.destroyBlockIcons.length; var2++) {
         this.destroyBlockIcons[var2] = var1.getAtlasSprite("minecraft:blocks/destroy_stage_" + var2);
      }
   }

   public void makeEntityOutlineShader() {
      if (OpenGlHelper.shadersSupported) {
         if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
         }

         ResourceLocation var1 = new ResourceLocation("shaders/post/entity_outline.json");

         try {
            this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), var1);
            this.entityOutlineShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
            this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
         } catch (IOException | JsonSyntaxException var3) {
            logger.warn("Failed to load shader: " + var1, var3);
            this.entityOutlineShader = null;
            this.entityOutlineFramebuffer = null;
         }
      } else {
         this.entityOutlineShader = null;
         this.entityOutlineFramebuffer = null;
      }
   }

   public void renderEntityOutlineFramebuffer() {
      if (this.isRenderEntityOutlines()) {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
         this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
         GlStateManager.disableBlend();
      }
   }

   protected boolean isRenderEntityOutlines() {
      return !Config.isShaders()
         && !Config.isAntialiasing()
         && this.entityOutlineFramebuffer != null
         && this.entityOutlineShader != null
         && this.mc.thePlayer != null
         && this.mc.thePlayer.isSpectator()
         && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown();
   }

   private void Method717() {
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      if (this.S != null) {
         this.S.deleteGlBuffers();
      }

      if (this.P >= 0) {
         GLAllocation.deleteDisplayLists(this.P);
         this.P = -1;
      }

      if (this.vboEnabled) {
         this.S = new VertexBuffer(this.vertexBufferFormat);
         this.renderSky(var4, -16.0F, true);
         var4.finishDrawing();
         var4.reset();
         this.S.bufferData(var4.getByteBuffer());
      } else {
         this.P = GLAllocation.generateDisplayLists(1);
         GL11.glNewList(this.P, GL11.GL_COMPILE);
         this.renderSky(var4, -16.0F, true);
         var3.draw();
         GL11.glEndList();
      }
   }

   private void Method718() {
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      if (this.R != null) {
         this.R.deleteGlBuffers();
      }

      if (this.O >= 0) {
         GLAllocation.deleteDisplayLists(this.O);
         this.O = -1;
      }

      if (this.vboEnabled) {
         this.R = new VertexBuffer(this.vertexBufferFormat);
         this.renderSky(var4, 16.0F, false);
         var4.finishDrawing();
         var4.reset();
         this.R.bufferData(var4.getByteBuffer());
      } else {
         this.O = GLAllocation.generateDisplayLists(1);
         GL11.glNewList(this.O, GL11.GL_COMPILE);
         this.renderSky(var4, 16.0F, false);
         var3.draw();
         GL11.glEndList();
      }
   }

   private void renderSky(WorldRenderer worldRenderer, float float2, boolean boolean3) {
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);
      int var8 = (this.renderDistance / 64 + 1) * 64 + 64;

      for (int var9 = -var8; var9 <= var8; var9 += 64) {
         for (int var10 = -var8; var10 <= var8; var10 += 64) {
            float var11 = var9;
            float var12 = var9 + 64;
            if (boolean3) {
               var12 = var9;
               var11 = var9 + 64;
            }

            worldRenderer.pos(var11, float2, var10).endVertex();
            worldRenderer.pos(var12, float2, var10).endVertex();
            worldRenderer.pos(var12, float2, var10 + 64).endVertex();
            worldRenderer.pos(var11, float2, var10 + 64).endVertex();
         }
      }
   }

   private void Method719() {
      Tessellator var3 = Tessellator.getInstance();
      WorldRenderer var4 = var3.getWorldRenderer();
      if (this.Q != null) {
         this.Q.deleteGlBuffers();
      }

      if (this.N >= 0) {
         GLAllocation.deleteDisplayLists(this.N);
         this.N = -1;
      }

      if (this.vboEnabled) {
         this.Q = new VertexBuffer(this.vertexBufferFormat);
         this.renderStars(var4);
         var4.finishDrawing();
         var4.reset();
         this.Q.bufferData(var4.getByteBuffer());
      } else {
         this.N = GLAllocation.generateDisplayLists(1);
         GlStateManager.pushMatrix();
         GL11.glNewList(this.N, GL11.GL_COMPILE);
         this.renderStars(var4);
         var3.draw();
         GL11.glEndList();
         GlStateManager.popMatrix();
      }
   }

   private void renderStars(WorldRenderer worldRenderer) {
      Random var4 = new Random(10842L);
      worldRenderer.begin(7, DefaultVertexFormats.POSITION);

      for (int var5 = 0; var5 < 1500; var5++) {
         double var6 = var4.nextFloat() * 2.0F - 1.0F;
         double var8 = var4.nextFloat() * 2.0F - 1.0F;
         double var10 = var4.nextFloat() * 2.0F - 1.0F;
         double var12 = 0.15F + var4.nextFloat() * 0.1F;
         double var14 = var6 * var6 + var8 * var8 + var10 * var10;
         if (var14 < 1.0 && var14 > 0.01) {
            var14 = 1.0 / Math.sqrt(var14);
            var6 *= var14;
            var8 *= var14;
            var10 *= var14;
            double var16 = var6 * 100.0;
            double var18 = var8 * 100.0;
            double var20 = var10 * 100.0;
            double var22 = Math.atan2(var6, var10);
            double var24 = Math.sin(var22);
            double var26 = Math.cos(var22);
            double var28 = Math.atan2(Math.sqrt(var6 * var6 + var10 * var10), var8);
            double var30 = Math.sin(var28);
            double var32 = Math.cos(var28);
            double var34 = var4.nextDouble() * Math.PI * 2.0;
            double var36 = Math.sin(var34);
            double var38 = Math.cos(var34);

            for (int var40 = 0; var40 < 4; var40++) {
               double var43 = ((var40 & 2) - 1) * var12;
               double var45 = ((var40 + 1 & 2) - 1) * var12;
               double var49 = var43 * var38 - var45 * var36;
               double var51 = var45 * var38 + var43 * var36;
               double var53 = var49 * var30 + 0.0 * var32;
               double var55 = 0.0 * var30 - var49 * var32;
               double var57 = var55 * var24 - var51 * var26;
               double var59 = var51 * var24 + var55 * var26;
               worldRenderer.pos(var16 + var57, var18 + var53, var20 + var59).endVertex();
            }
         }
      }
   }

   public void setWorldAndLoadRenderers(WorldClient worldClient) {
      if (this.theWorld != null) {
         this.theWorld.removeWorldAccess(this);
      }

      this.frustumUpdatePosX = Double.MIN_VALUE;
      this.frustumUpdatePosY = Double.MIN_VALUE;
      this.frustumUpdatePosZ = Double.MIN_VALUE;
      this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
      this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
      this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
      this.renderManager.set(worldClient);
      this.theWorld = worldClient;
      if (Config.isDynamicLights()) {
         DynamicLights.clear();
      }

      ChunkVisibility.reset();
      this.worldChunkProvider = null;
      this.worldChunkProviderMap = null;
      this.renderEnv.reset(null, null);
      Shaders.checkWorldChanged(this.theWorld);
      if (worldClient != null) {
         worldClient.addWorldAccess(this);
         this.loadRenderers();
      } else {
         this.chunksToUpdate.clear();
         this.clearRenderInfos();
         if (this.viewFrustum != null) {
            this.viewFrustum.deleteGlResources();
         }

         this.viewFrustum = null;
      }
   }

   public void loadRenderers() {
      if (this.theWorld != null) {
         this.displayListEntitiesDirty = true;
         Blocks.leaves.setGraphicsLevel(Config.isTreesFancy());
         Blocks.leaves2.setGraphicsLevel(Config.isTreesFancy());
         BlockModelRenderer.updateAoLightValue();
         if (Config.isDynamicLights()) {
            DynamicLights.clear();
         }

         SmartAnimations.update();
         this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
         this.renderDistance = this.renderDistanceChunks * 16;
         this.renderDistanceSq = this.renderDistance * this.renderDistance;
         boolean var3 = this.vboEnabled;
         this.vboEnabled = OpenGlHelper.useVbo();
         if (var3 && !this.vboEnabled) {
            this.renderContainer = new RenderList();
            this.renderChunkFactory = new ListChunkFactory();
         } else if (!var3 && this.vboEnabled) {
            this.renderContainer = new VboRenderList();
            this.renderChunkFactory = new VboChunkFactory();
         }

         this.Method719();
         this.Method718();
         this.Method717();
         if (this.viewFrustum != null) {
            this.viewFrustum.deleteGlResources();
         }

         this.stopChunkUpdates();
         synchronized (this.setTileEntities) {
            this.setTileEntities.clear();
         }

         this.viewFrustum = new ViewFrustum(this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
         if (this.theWorld != null) {
            Entity var7 = this.mc.getRenderViewEntity();
            if (var7 != null) {
               this.viewFrustum.updateChunkPositions(var7.posX, var7.posZ);
            }
         }

         this.renderEntitiesStartupCounter = 2;
      }

      if (this.mc.thePlayer == null) {
         this.firstWorldLoad = true;
      }
   }

   protected void stopChunkUpdates() {
      this.chunksToUpdate.clear();
      this.renderDispatcher.stopChunkUpdates();
   }

   public void createBindEntityOutlineFbs(int integer1, int integer2) {
      if (OpenGlHelper.shadersSupported && this.entityOutlineShader != null) {
         this.entityOutlineShader.createBindFramebuffers(integer1, integer2);
      }
   }

   public void renderEntities(Entity entity, ICamera iCamera, float float3) {
      if (this.renderEntitiesStartupCounter > 0) {
         if (0 > 0) {
            return;
         }

         this.renderEntitiesStartupCounter--;
      } else {
         double var7 = entity.prevPosX + (entity.posX - entity.prevPosX) * float3;
         double var9 = entity.prevPosY + (entity.posY - entity.prevPosY) * float3;
         double var11 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * float3;
         this.theWorld.theProfiler.startSection("prepare");
         TileEntityRendererDispatcher.instance
            .cacheActiveRenderInfo(this.theWorld, this.mc.getTextureManager(), this.mc.fontRendererObj, this.mc.getRenderViewEntity(), float3);
         this.renderManager
            .cacheActiveRenderInfo(this.theWorld, this.mc.fontRendererObj, this.mc.getRenderViewEntity(), this.mc.pointedEntity, this.mc.gameSettings, float3);
         renderEntitiesCounter++;
         if (true) {
            this.countEntitiesTotal = 0;
            this.countEntitiesRendered = 0;
            this.countEntitiesHidden = 0;
            this.countTileEntitiesRendered = 0;
         }

         Entity var13 = this.mc.getRenderViewEntity();
         double var14 = var13.lastTickPosX + (var13.posX - var13.lastTickPosX) * float3;
         double var16 = var13.lastTickPosY + (var13.posY - var13.lastTickPosY) * float3;
         double var18 = var13.lastTickPosZ + (var13.posZ - var13.lastTickPosZ) * float3;
         TileEntityRendererDispatcher.staticPlayerX = var14;
         TileEntityRendererDispatcher.staticPlayerY = var16;
         TileEntityRendererDispatcher.staticPlayerZ = var18;
         this.renderManager.setRenderPosition(var14, var16, var18);
         this.mc.entityRenderer.enableLightmap();
         this.theWorld.theProfiler.endStartSection("global");
         List<Entity> var20 = this.theWorld.getLoadedEntityList();
         if (true) {
            this.countEntitiesTotal = var20.size();
         }

         if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GlStateManager.disableFog();
         }

         for (int var21 = 0; var21 < this.theWorld.weatherEffects.size(); var21++) {
            Entity var22 = this.theWorld.weatherEffects.get(var21);
            this.countEntitiesRendered++;
            if (var22.isInRangeToRender3d(var7, var9, var11)) {
               this.renderManager.renderEntitySimple(var22, float3);
            }
         }

         if (this.isRenderEntityOutlines()) {
            GlStateManager.depthFunc(519);
            GlStateManager.disableFog();
            this.entityOutlineFramebuffer.framebufferClear();
            this.entityOutlineFramebuffer.bindFramebuffer(false);
            this.theWorld.theProfiler.endStartSection("entityOutlines");
            RenderHelper.disableStandardItemLighting();
            this.renderManager.setRenderOutlines(true);

            for (Entity var36 : var20) {
               boolean var23 = this.mc.getRenderViewEntity() instanceof EntityLivingBase
                  && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping();
               boolean var24 = var36.isInRangeToRender3d(var7, var9, var11)
                  && (var36.ignoreFrustumCheck || iCamera.isBoundingBoxInFrustum(var36.getEntityBoundingBox()) || var36.riddenByEntity == this.mc.thePlayer)
                  && var36 instanceof EntityPlayer;
               if ((var36 != this.mc.getRenderViewEntity() || this.mc.gameSettings.thirdPersonView != 0 || var23) && var24) {
                  this.renderManager.renderEntitySimple(var36, float3);
               }
            }

            this.renderManager.setRenderOutlines(false);
            RenderHelper.enableStandardItemLighting();
            GlStateManager.depthMask(false);
            this.entityOutlineShader.loadShaderGroup(float3);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            this.mc.getFramebuffer().bindFramebuffer(false);
            GlStateManager.enableFog();
            GlStateManager.enableBlend();
            GlStateManager.enableColorMaterial();
            GlStateManager.depthFunc(515);
            GlStateManager.enableDepth();
            GlStateManager.enableAlpha();
         }

         this.theWorld.theProfiler.endStartSection("entities");
         boolean var35 = Config.isShaders();
         if (var35) {
            Shaders.beginEntities();
         }

         RenderItemFrame.updateItemRenderDistance();
         boolean var37 = this.mc.gameSettings.fancyGraphics;
         this.mc.gameSettings.fancyGraphics = Config.isDroppedItemsFancy();
         boolean var38 = Shaders.isShadowPass && !this.mc.thePlayer.isSpectator();

         for (RenderGlobal.ContainerLocalRenderInformation var25 : this.at) {
            ClassInheritanceMultiMap<Entity> var27 = var25.renderChunk.getChunk().getEntityLists()[var25.renderChunk.getPosition().getY() / 16];
            if (!var27.isEmpty()) {
               for (Entity var29 : var27) {
                  boolean var30 = this.renderManager.shouldRender(var29, iCamera, var7, var9, var11) || var29.riddenByEntity == this.mc.thePlayer;
                  if (var30) {
                     boolean var31 = this.mc.getRenderViewEntity() instanceof EntityLivingBase
                        && ((EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping();
                     if (var29 == this.mc.getRenderViewEntity() && !var38 && this.mc.gameSettings.thirdPersonView == 0 && !var31
                        || !(var29.posY < 0.0) && !(var29.posY >= 256.0) && !this.theWorld.Method322(new BlockPos(var29))) {
                        continue;
                     }

                     this.countEntitiesRendered++;
                     this.renderedEntity = var29;
                     if (var35) {
                        Shaders.nextEntity(var29);
                     }

                     this.renderManager.renderEntitySimple(var29, float3);
                     this.renderedEntity = null;
                  }

                  if (!var30 && var29 instanceof EntityWitherSkull) {
                     this.renderedEntity = var29;
                     if (var35) {
                        Shaders.nextEntity(var29);
                     }

                     this.mc.getRenderManager().renderWitherSkull(var29, float3);
                     this.renderedEntity = null;
                  }
               }
            }
         }

         this.mc.gameSettings.fancyGraphics = var37;
         if (var35) {
            Shaders.endEntities();
            Shaders.beginBlockEntities();
         }

         this.theWorld.theProfiler.endStartSection("blockentities");
         RenderHelper.enableStandardItemLighting();
         TileEntitySignRenderer.updateTextRenderDistance();
         Iterator var40 = this.renderInfos.iterator();

         while (var40.hasNext()) {
            List<TileEntity> var26 = ((RenderGlobal.ContainerLocalRenderInformation)var40.next()).renderChunk.getCompiledChunk().getTileEntities();
            if (!var26.isEmpty()) {
               for (TileEntity var49 : var26) {
                  AxisAlignedBB var52 = var49.Method981();
                  if (var52 == null || iCamera.isBoundingBoxInFrustum(var52)) {
                     if (var35) {
                        Shaders.nextBlockEntity(var49);
                     }

                     TileEntityRendererDispatcher.instance.renderTileEntity(var49, float3, -1);
                     this.countTileEntitiesRendered++;
                  }
               }
            }
         }

         synchronized (this.setTileEntities) {
            for (TileEntity var45 : this.setTileEntities) {
               if (var35) {
                  Shaders.nextBlockEntity(var45);
               }

               TileEntityRendererDispatcher.instance.renderTileEntity(var45, float3, -1);
            }
         }

         this.renderOverlayDamaged = true;
         this.preRenderDamagedBlocks();

         for (DestroyBlockProgress var44 : this.damagedBlocks.values()) {
            BlockPos var46 = var44.getPosition();
            TileEntity var48 = this.theWorld.getTileEntity(var46);
            if (var48 instanceof TileEntityChest var50) {
               if (var50.adjacentChestXNeg != null) {
                  var46 = var46.offset(EnumFacing.WEST);
                  var48 = this.theWorld.getTileEntity(var46);
               } else if (var50.adjacentChestZNeg != null) {
                  var46 = var46.offset(EnumFacing.NORTH);
                  var48 = this.theWorld.getTileEntity(var46);
               }
            }

            Block var51 = this.theWorld.getBlockState(var46).getBlock();
            boolean var53 = var48 != null
               && (var51 instanceof BlockChest || var51 instanceof BlockEnderChest || var51 instanceof BlockSign || var51 instanceof BlockSkull);
            if (var53) {
               if (var35) {
                  Shaders.nextBlockEntity(var48);
               }

               TileEntityRendererDispatcher.instance.renderTileEntity(var48, float3, var44.getPartialBlockDamage());
            }
         }

         this.postRenderDamagedBlocks();
         this.renderOverlayDamaged = false;
         if (var35) {
            Shaders.endBlockEntities();
         }

         renderEntitiesCounter--;
         this.mc.entityRenderer.disableLightmap();
         this.mc.mcProfiler.endSection();
      }
   }

   public String getDebugInfoRenders() {
      int var1 = this.viewFrustum.renderChunks.length;
      int var2 = 0;
      Iterator var3 = this.renderInfos.iterator();

      while (var3.hasNext()) {
         CompiledChunk var5 = ((RenderGlobal.ContainerLocalRenderInformation)var3.next()).renderChunk.compiledChunk;
         if (var5 != CompiledChunk.DUMMY && !var5.isEmpty()) {
            var2++;
         }
      }

      return String.format(
         "C: %d/%d %sD: %d, %s", var2, var1, this.mc.renderChunksMany ? "(s) " : "", this.renderDistanceChunks, this.renderDispatcher.getDebugInfo()
      );
   }

   public String Method720() {
      return "E: "
         + this.countEntitiesRendered
         + "/"
         + this.countEntitiesTotal
         + ", B: "
         + this.countEntitiesHidden
         + ", I: "
         + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered)
         + ", "
         + Config.getVersionDebug();
   }

   public void setupTerrain(Entity entity, double double2, ICamera iCamera, int integer, boolean boolean5) {
      if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
         this.loadRenderers();
      }

      this.theWorld.theProfiler.startSection("camera");
      double var9 = entity.posX - this.frustumUpdatePosX;
      double var11 = entity.posY - this.frustumUpdatePosY;
      double var13 = entity.posZ - this.frustumUpdatePosZ;
      if (this.frustumUpdatePosChunkX != entity.W
         || this.frustumUpdatePosChunkY != entity.chunkCoordY
         || this.frustumUpdatePosChunkZ != entity.Y
         || var9 * var9 + var11 * var11 + var13 * var13 > 16.0) {
         this.frustumUpdatePosX = entity.posX;
         this.frustumUpdatePosY = entity.posY;
         this.frustumUpdatePosZ = entity.posZ;
         this.frustumUpdatePosChunkX = entity.W;
         this.frustumUpdatePosChunkY = entity.chunkCoordY;
         this.frustumUpdatePosChunkZ = entity.Y;
         this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
      }

      if (Config.isDynamicLights()) {
         DynamicLights.update(this);
      }

      this.theWorld.theProfiler.endStartSection("renderlistcamera");
      double var15 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * double2;
      double var17 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * double2;
      double var19 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * double2;
      this.renderContainer.initialize(var15, var17, var19);
      this.theWorld.theProfiler.endStartSection("cull");
      if (this.debugFixedClippingHelper != null) {
         Frustum var21 = new Frustum(this.debugFixedClippingHelper);
         var21.setPosition(this.debugTerrainFrustumPosition.x, this.debugTerrainFrustumPosition.y, this.debugTerrainFrustumPosition.z);
         iCamera = var21;
      }

      this.mc.mcProfiler.endStartSection("culling");
      BlockPos var41 = new BlockPos(var15, var17 + entity.getEyeHeight(), var19);
      RenderChunk var22 = this.viewFrustum.getRenderChunk(var41);
      new BlockPos(MathHelper.floor_double(var15 / 16.0) * 16, MathHelper.floor_double(var17 / 16.0) * 16, MathHelper.floor_double(var19 / 16.0) * 16);
      this.displayListEntitiesDirty = this.displayListEntitiesDirty
         || !this.chunksToUpdate.isEmpty()
         || entity.posX != this.lastViewEntityX
         || entity.posY != this.lastViewEntityY
         || entity.posZ != this.lastViewEntityZ
         || entity.rotationPitch != this.lastViewEntityPitch
         || entity.rotationYaw != this.lastViewEntityYaw;
      this.lastViewEntityX = entity.posX;
      this.lastViewEntityY = entity.posY;
      this.lastViewEntityZ = entity.posZ;
      this.lastViewEntityPitch = entity.rotationPitch;
      this.lastViewEntityYaw = entity.rotationYaw;
      boolean var23 = this.debugFixedClippingHelper != null;
      this.mc.mcProfiler.endStartSection("update");
      Lagometer.timerVisibility.start();
      int var24 = this.getCountLoadedChunks();
      if (var24 != this.countLoadedChunksPrev) {
         this.countLoadedChunksPrev = var24;
         this.displayListEntitiesDirty = true;
      }

      int var25 = 256;
      if (!ChunkVisibility.isFinished()) {
         this.displayListEntitiesDirty = true;
      }

      if (!var23 && this.displayListEntitiesDirty && Config.isIntegratedServerRunning()) {
         var25 = ChunkVisibility.getMaxChunkY(this.theWorld, entity, this.renderDistanceChunks);
      }

      RenderChunk var26 = this.viewFrustum.getRenderChunk(new BlockPos(entity.posX, entity.posY, entity.posZ));
      if (Shaders.isShadowPass) {
         this.renderInfos = this.renderInfosShadow;
         this.at = this.renderInfosEntitiesShadow;
         this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
         if (!var23 && this.displayListEntitiesDirty) {
            this.clearRenderInfos();
            if (var26 != null && var26.getPosition().getY() > var25) {
               this.at.add(var26.getRenderInfo());
            }

            Iterator var27 = ShadowUtils.makeShadowChunkIterator(this.theWorld, double2, entity, this.renderDistanceChunks, this.viewFrustum);

            while (var27.hasNext()) {
               RenderChunk var28 = (RenderChunk)var27.next();
               if (var28 != null && var28.getPosition().getY() <= var25) {
                  RenderGlobal.ContainerLocalRenderInformation var29 = var28.getRenderInfo();
                  if (!var28.compiledChunk.isEmpty() || var28.isNeedsUpdate()) {
                     this.renderInfos.add(var29);
                  }

                  if (IBehaviorDispenseItem$1.Method367(var28.getChunk())) {
                     this.at.add(var29);
                  }

                  if (var28.getCompiledChunk().getTileEntities().size() > 0) {
                     this.renderInfosTileEntities.add(var29);
                  }
               }
            }
         }
      } else {
         this.renderInfos = this.renderInfosNormal;
         this.at = this.renderInfosEntitiesNormal;
         this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
      }

      if (!var23 && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
         this.displayListEntitiesDirty = false;
         this.clearRenderInfos();
         this.visibilityDeque.clear();
         Deque var42 = this.visibilityDeque;
         boolean var44 = this.mc.renderChunksMany;
         if (var22 != null && var22.getPosition().getY() <= var25) {
            boolean var47 = false;
            RenderGlobal.ContainerLocalRenderInformation var49 = new RenderGlobal.ContainerLocalRenderInformation(var22, null, 0);
            Set var52 = g;
            if (var52.size() == 1) {
               Vector3f var55 = this.getViewVector(entity, double2);
               EnumFacing var58 = EnumFacing.getFacingFromVector(var55.x, var55.y, var55.z).getOpposite();
               if (var58 == null) {
                  var58 = EnumFacing.NORTH;
               }

               var52.remove(var58);
            }

            if (var52.isEmpty()) {
               var47 = true;
            }

            if (var47 && !boolean5) {
               this.renderInfos.add(var49);
            } else {
               if (boolean5 && this.theWorld.getBlockState(var41).getBlock().isOpaqueCube()) {
                  var44 = false;
               }

               var22.setFrameIndex(integer);
               var42.add(var49);
            }
         } else {
            int var46 = var41.getY() > 0 ? Math.min(var25, 248) : 8;
            if (var26 != null) {
               this.at.add(var26.getRenderInfo());
            }

            for (int var30 = -this.renderDistanceChunks; var30 <= this.renderDistanceChunks; var30++) {
               for (int var31 = -this.renderDistanceChunks; var31 <= this.renderDistanceChunks; var31++) {
                  RenderChunk var32 = this.viewFrustum.getRenderChunk(new BlockPos((var30 << 4) + 8, var46, (var31 << 4) + 8));
                  if (var32 != null && var32.isBoundingBoxInFrustum(iCamera, integer)) {
                     var32.setFrameIndex(integer);
                     RenderGlobal.ContainerLocalRenderInformation var33 = var32.getRenderInfo();
                     var33.initialize(null, 0);
                     var42.add(var33);
                  }
               }
            }
         }

         this.mc.mcProfiler.startSection("iteration");
         boolean var48 = Config.isFogOn();

         while (!var42.isEmpty()) {
            RenderGlobal.ContainerLocalRenderInformation var50 = (RenderGlobal.ContainerLocalRenderInformation)var42.poll();
            RenderChunk var53 = var50.renderChunk;
            EnumFacing var56 = var50.facing;
            CompiledChunk var59 = var53.compiledChunk;
            if (!var59.isEmpty() || var53.isNeedsUpdate()) {
               this.renderInfos.add(var50);
            }

            if (IBehaviorDispenseItem$1.Method367(var53.getChunk())) {
               this.at.add(var50);
            }

            if (var59.getTileEntities().size() > 0) {
               this.renderInfosTileEntities.add(var50);
            }

            for (EnumFacing var37 : var44 ? ChunkVisibility.getFacingsNotOpposite(var50.setFacing) : EnumFacing.VALUES) {
               if (!var44 || var56 == null || var59.isVisible(var56.getOpposite(), var37)) {
                  RenderChunk var38 = this.getRenderChunkOffset(var41, var53, var37, var48, var25);
                  if (var38 != null && var38.setFrameIndex(integer) && var38.isBoundingBoxInFrustum(iCamera, integer)) {
                     int var39 = var50.setFacing | 1 << var37.ordinal();
                     RenderGlobal.ContainerLocalRenderInformation var40 = var38.getRenderInfo();
                     var40.initialize(var37, var39);
                     var42.add(var40);
                  }
               }
            }
         }

         this.mc.mcProfiler.endSection();
      }

      this.mc.mcProfiler.endStartSection("captureFrustum");
      if (this.debugFixTerrainFrustum) {
         this.fixTerrainFrustum(var15, var17, var19);
         this.debugFixTerrainFrustum = false;
      }

      Lagometer.timerVisibility.end();
      if (Shaders.isShadowPass) {
         Shaders.mcProfilerEndSection();
      } else {
         this.mc.mcProfiler.endStartSection("rebuildNear");
         this.renderDispatcher.clearChunkUpdates();
         Set var43 = this.chunksToUpdate;
         this.chunksToUpdate = Sets.newLinkedHashSet();
         Lagometer.timerChunkUpdate.start();
         Iterator var45 = this.renderInfos.iterator();

         while (var45.hasNext()) {
            RenderChunk var51 = ((RenderGlobal.ContainerLocalRenderInformation)var45.next()).renderChunk;
            if (var51.isNeedsUpdate() || var43.contains(var51)) {
               this.displayListEntitiesDirty = true;
               BlockPos var54 = var51.getPosition();
               boolean var57 = var41.distanceSq(var54.getX() + 8, var54.getY() + 8, var54.getZ() + 8) < 768.0;
               if (!var57) {
                  this.chunksToUpdate.add(var51);
               } else if (!var51.isPlayerUpdate()) {
                  this.chunksToUpdateForced.add(var51);
               } else {
                  this.mc.mcProfiler.startSection("build near");
                  this.renderDispatcher.updateChunkNow(var51);
                  var51.setNeedsUpdate(false);
                  this.mc.mcProfiler.endSection();
               }
            }
         }

         Lagometer.timerChunkUpdate.end();
         this.chunksToUpdate.addAll(var43);
         this.mc.mcProfiler.endSection();
      }
   }

   private boolean isPositionInRenderChunk(BlockPos blockPos, RenderChunk renderChunk) {
      BlockPos var5 = renderChunk.getPosition();
      return MathHelper.abs_int(blockPos.getX() - var5.getX()) <= 16
         && MathHelper.abs_int(blockPos.getY() - var5.getY()) <= 16
         && MathHelper.abs_int(blockPos.getZ() - var5.getZ()) <= 16;
   }

   private Set<EnumFacing> getVisibleFacings(BlockPos blockPos) {
      VisGraph var4 = new VisGraph();
      BlockPos var5 = new BlockPos(blockPos.getX() >> 4 << 4, blockPos.getY() >> 4 << 4, blockPos.getZ() >> 4 << 4);
      Chunk var6 = this.theWorld.getChunkFromBlockCoords(var5);

      for (BlockPos.MutableBlockPos var8 : BlockPos.getAllInBoxMutable(var5, var5.add(15, 15, 15))) {
         if (var6.getBlock(var8).isOpaqueCube()) {
            var4.func_178606_a(var8);
         }
      }

      return var4.func_178609_b(blockPos);
   }

   private RenderChunk getRenderChunkOffset(BlockPos blockPos, RenderChunk renderChunk, EnumFacing enumFacing, boolean boolean4, int integer) {
      RenderChunk var6 = renderChunk.getRenderChunkNeighbour(enumFacing);
      if (var6 == null) {
         return null;
      }

      if (var6.getPosition().getY() > integer) {
         return null;
      }

      if (boolean4) {
         BlockPos var7 = var6.getPosition();
         int var8 = blockPos.getX() - var7.getX();
         int var9 = blockPos.getZ() - var7.getZ();
         int var10 = var8 * var8 + var9 * var9;
         if (var10 > this.renderDistanceSq) {
            return null;
         }
      }

      return var6;
   }

   private void fixTerrainFrustum(double double1, double double2, double double3) {
      this.debugFixedClippingHelper = new ClippingHelperImpl();
      ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
      Matrix4f var9 = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
      var9.transpose();
      Matrix4f var10 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
      var10.transpose();
      Matrix4f var11 = new Matrix4f();
      Matrix4f.mul(var10, var9, var11);
      var11.invert();
      this.debugTerrainFrustumPosition.x = double1;
      this.debugTerrainFrustumPosition.y = double2;
      this.debugTerrainFrustumPosition.z = double3;
      this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
      this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

      for (int var12 = 0; var12 < 8; var12++) {
         Matrix4f.transform(var11, this.debugTerrainMatrix[var12], this.debugTerrainMatrix[var12]);
         this.debugTerrainMatrix[var12].x = this.debugTerrainMatrix[var12].x / this.debugTerrainMatrix[var12].w;
         this.debugTerrainMatrix[var12].y = this.debugTerrainMatrix[var12].y / this.debugTerrainMatrix[var12].w;
         this.debugTerrainMatrix[var12].z = this.debugTerrainMatrix[var12].z / this.debugTerrainMatrix[var12].w;
         this.debugTerrainMatrix[var12].w = 1.0F;
      }
   }

   protected Vector3f getViewVector(Entity entity, double double2) {
      float var4 = (float)(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * double2);
      float var5 = (float)(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * double2);
      if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
         var4 += 180.0F;
      }

      float var6 = MathHelper.cos(-var5 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var7 = MathHelper.sin(-var5 * (float) (Math.PI / 180.0) - (float) Math.PI);
      float var8 = -MathHelper.cos(-var4 * (float) (Math.PI / 180.0));
      float var9 = MathHelper.sin(-var4 * (float) (Math.PI / 180.0));
      return new Vector3f(var7 * var8, var9, var6 * var8);
   }

   public int renderBlockLayer(EnumWorldBlockLayer enumWorldBlockLayer, double double2, int integer, Entity entity) {
      RenderHelper.disableStandardItemLighting();
      if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT && !Shaders.isShadowPass) {
         this.mc.mcProfiler.startSection("translucent_sort");
         double var8 = entity.posX - this.prevRenderSortX;
         double var10 = entity.posY - this.prevRenderSortY;
         double var12 = entity.posZ - this.prevRenderSortZ;
         if (var8 * var8 + var10 * var10 + var12 * var12 > 1.0) {
            this.prevRenderSortX = entity.posX;
            this.prevRenderSortY = entity.posY;
            this.prevRenderSortZ = entity.posZ;
            int var14 = 0;
            this.chunksToResortTransparency.clear();

            for (RenderGlobal.ContainerLocalRenderInformation var16 : this.renderInfos) {
               if (var16.renderChunk.compiledChunk.isLayerStarted(enumWorldBlockLayer) && var14++ < 15) {
                  this.chunksToResortTransparency.add(var16.renderChunk);
               }
            }
         }

         this.mc.mcProfiler.endSection();
      }

      this.mc.mcProfiler.startSection("filterempty");
      int var17 = 0;
      boolean var9 = enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT;
      int var18 = var9 ? this.renderInfos.size() - 1 : 0;
      int var11 = var9 ? -1 : this.renderInfos.size();
      int var19 = var9 ? -1 : 1;

      for (int var13 = var18; var13 != var11; var13 += var19) {
         RenderChunk var20 = this.renderInfos.get(var13).renderChunk;
         if (!var20.getCompiledChunk().isLayerEmpty(enumWorldBlockLayer)) {
            var17++;
            this.renderContainer.addRenderChunk(var20, enumWorldBlockLayer);
         }
      }

      if (var17 != 0) {
         if (Config.isFogOff() && this.mc.entityRenderer.fogStandard) {
            GlStateManager.disableFog();
         }

         this.mc.mcProfiler.endStartSection("render_" + enumWorldBlockLayer);
         this.renderBlockLayer(enumWorldBlockLayer);
      }

      this.mc.mcProfiler.endSection();
      return var17;
   }

   private void renderBlockLayer(EnumWorldBlockLayer enumWorldBlockLayer) {
      this.mc.entityRenderer.enableLightmap();
      if (OpenGlHelper.useVbo()) {
         GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
         GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
         GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
         GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
      }

      if (Config.isShaders()) {
         ShadersRender.Method523(enumWorldBlockLayer);
      }

      this.renderContainer.renderChunkLayer(enumWorldBlockLayer);
      if (Config.isShaders()) {
         ShadersRender.Method524(enumWorldBlockLayer);
      }

      if (OpenGlHelper.useVbo()) {
         for (VertexFormatElement var5 : DefaultVertexFormats.BLOCK.getElements()) {
            VertexFormatElement.EnumUsage var6 = var5.getUsage();
            int var7 = var5.getIndex();
            switch (var6) {
               case POSITION:
                  GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
                  break;
               case UV:
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var7);
                  GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                  break;
               case COLOR:
                  GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
                  GlStateManager.resetColor();
            }
         }
      }

      this.mc.entityRenderer.disableLightmap();
   }

   private void cleanupDamagedBlocks(Iterator<DestroyBlockProgress> iterator) {
      while (iterator.hasNext()) {
         int var5 = ((DestroyBlockProgress)iterator.next()).getCreationCloudUpdateTick();
         if (this.cloudTickCounter - var5 > 400) {
            iterator.remove();
         }
      }
   }

   public void updateClouds() {
      if (Config.isShaders()) {
         if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(24)) {
            GuiShaderOptions var3 = new GuiShaderOptions(null, Config.getGameSettings());
            Config.getMinecraft().displayGuiScreen(var3);
         }

         if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(19)) {
            Shaders.uninit();
            Shaders.loadShaderPack();
         }
      }

      this.cloudTickCounter++;
      if (this.cloudTickCounter % 20 == 0) {
         this.cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
      }
   }

   private void renderSkyEnd() {
      if (Config.isSkyEnabled()) {
         GlStateManager.disableFog();
         GlStateManager.disableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.depthMask(false);
         this.renderEngine.bindTexture(locationEndSkyPng);
         Tessellator var3 = Tessellator.getInstance();
         WorldRenderer var4 = var3.getWorldRenderer();

         for (int var5 = 0; var5 < 6; var5++) {
            GlStateManager.pushMatrix();
            if (var5 == 1) {
               GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var5 == 2) {
               GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var5 == 3) {
               GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
            }

            if (var5 == 4) {
               GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            }

            if (var5 == 5) {
               GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
            }

            var4.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
            int var6 = 40;
            int var7 = 40;
            int var8 = 40;
            if (Config.isCustomColors()) {
               Vec3 var9 = CustomColors.getWorldSkyColor(
                  new Vec3(0.1568627450980392, 0.1568627450980392, 0.1568627450980392), this.theWorld, this.mc.getRenderViewEntity(), 0.0F
               );
               var6 = (int)(var9.xCoord * 255.0);
               var7 = (int)(var9.yCoord * 255.0);
               var8 = (int)(var9.zCoord * 255.0);
            }

            var4.pos(-100.0, -100.0, -100.0).tex(0.0, 0.0).color(var6, var7, var8, 255).endVertex();
            var4.pos(-100.0, -100.0, 100.0).tex(0.0, 16.0).color(var6, var7, var8, 255).endVertex();
            var4.pos(100.0, -100.0, 100.0).tex(16.0, 16.0).color(var6, var7, var8, 255).endVertex();
            var4.pos(100.0, -100.0, -100.0).tex(16.0, 0.0).color(var6, var7, var8, 255).endVertex();
            var3.draw();
            GlStateManager.popMatrix();
         }

         GlStateManager.depthMask(true);
         GlStateManager.enableTexture2D();
         GlStateManager.enableAlpha();
         GlStateManager.disableBlend();
      }
   }

   public void renderSky(float float1, int integer) {
      if (this.mc.theWorld.provider.getDimensionId() == 1) {
         this.renderSkyEnd();
      } else if (this.mc.theWorld.provider.isSurfaceWorld()) {
         GlStateManager.disableTexture2D();
         boolean var5 = Config.isShaders();
         if (var5) {
            Shaders.an();
         }

         Vec3 var6 = CustomColors.getSkyColor(
            this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), float1),
            this.mc.theWorld,
            this.mc.getRenderViewEntity().posX,
            this.mc.getRenderViewEntity().posY + 1.0,
            this.mc.getRenderViewEntity().posZ
         );
         if (var5) {
            Shaders.setSkyColor(var6);
         }

         float var7 = (float)var6.xCoord;
         float var8 = (float)var6.yCoord;
         float var9 = (float)var6.zCoord;
         if (integer != 2) {
            float var10 = (var7 * 30.0F + var8 * 59.0F + var9 * 11.0F) / 100.0F;
            float var11 = (var7 * 30.0F + var8 * 70.0F) / 100.0F;
            float var12 = (var7 * 30.0F + var9 * 70.0F) / 100.0F;
            var7 = var10;
            var8 = var11;
            var9 = var12;
         }

         GlStateManager.color(var7, var8, var9);
         Tessellator var22 = Tessellator.getInstance();
         WorldRenderer var23 = var22.getWorldRenderer();
         GlStateManager.depthMask(false);
         GlStateManager.enableFog();
         if (var5) {
            Shaders.enableFog();
         }

         GlStateManager.color(var7, var8, var9);
         if (var5) {
            Shaders.preSkyList();
         }

         if (Config.isSkyEnabled()) {
            if (this.vboEnabled) {
               this.R.bindBuffer();
               GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
               GL11.glVertexPointer(3, GL11.GL_FLOAT, 12, 0L);
               this.R.drawArrays(7);
               this.R.unbindBuffer();
               GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            } else {
               GlStateManager.callList(this.O);
            }
         }

         GlStateManager.disableFog();
         if (var5) {
            Shaders.disableFog();
         }

         GlStateManager.disableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.disableStandardItemLighting();
         float[] var24 = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(float1), float1);
         if (var24 != null && Config.isSunMoonEnabled()) {
            GlStateManager.disableTexture2D();
            if (var5) {
               Shaders.an();
            }

            GlStateManager.shadeModel(7425);
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(MathHelper.sin(this.theWorld.getCelestialAngleRadians(float1)) < 0.0F ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            float var13 = var24[0];
            float var14 = var24[1];
            float var15 = var24[2];
            if (integer != 2) {
               float var16 = (var13 * 30.0F + var14 * 59.0F + var15 * 11.0F) / 100.0F;
               float var17 = (var13 * 30.0F + var14 * 70.0F) / 100.0F;
               float var18 = (var13 * 30.0F + var15 * 70.0F) / 100.0F;
               var13 = var16;
               var14 = var17;
               var15 = var18;
            }

            var23.begin(6, DefaultVertexFormats.POSITION_COLOR);
            var23.pos(0.0, 100.0, 0.0).color(var13, var14, var15, var24[3]).endVertex();

            for (int var30 = 0; var30 <= 16; var30++) {
               float var32 = var30 * (float) Math.PI * 2.0F / 16.0F;
               float var19 = MathHelper.sin(var32);
               float var20 = MathHelper.cos(var32);
               var23.pos(var19 * 120.0F, var20 * 120.0F, -var20 * 40.0F * var24[3]).color(var24[0], var24[1], var24[2], 0.0F).endVertex();
            }

            var22.draw();
            GlStateManager.popMatrix();
            GlStateManager.shadeModel(7424);
         }

         GlStateManager.enableTexture2D();
         if (var5) {
            Shaders.enableTexture2D();
         }

         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         GlStateManager.pushMatrix();
         float var25 = 1.0F - this.theWorld.getRainStrength(float1);
         GlStateManager.color(1.0F, 1.0F, 1.0F, var25);
         GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
         CustomSky.renderSky(this.theWorld, this.renderEngine, float1);
         if (var5) {
            Shaders.preCelestialRotate();
         }

         GlStateManager.rotate(this.theWorld.getCelestialAngle(float1) * 360.0F, 1.0F, 0.0F, 0.0F);
         if (var5) {
            Shaders.postCelestialRotate();
         }

         if (Config.isSunTexture()) {
            this.renderEngine.bindTexture(locationSunPng);
            var23.begin(7, DefaultVertexFormats.POSITION_TEX);
            var23.pos(-30.0, 100.0, -30.0).tex(0.0, 0.0).endVertex();
            var23.pos(30.0, 100.0, -30.0).tex(1.0, 0.0).endVertex();
            var23.pos(30.0, 100.0, 30.0).tex(1.0, 1.0).endVertex();
            var23.pos(-30.0, 100.0, 30.0).tex(0.0, 1.0).endVertex();
            var22.draw();
         }

         if (Config.isMoonTexture()) {
            this.renderEngine.bindTexture(locationMoonPhasesPng);
            int var26 = this.theWorld.getMoonPhase();
            int var28 = var26 % 4;
            int var31 = var26 / 4 % 2;
            float var33 = var28 / 4.0F;
            float var34 = var31 / 2.0F;
            float var36 = (var28 + 1) / 4.0F;
            float var21 = (var31 + 1) / 2.0F;
            var23.begin(7, DefaultVertexFormats.POSITION_TEX);
            var23.pos(-20.0, -100.0, 20.0).tex(var36, var21).endVertex();
            var23.pos(20.0, -100.0, 20.0).tex(var33, var21).endVertex();
            var23.pos(20.0, -100.0, -20.0).tex(var33, var34).endVertex();
            var23.pos(-20.0, -100.0, -20.0).tex(var36, var34).endVertex();
            var22.draw();
         }

         GlStateManager.disableTexture2D();
         if (var5) {
            Shaders.an();
         }

         float var27 = this.theWorld.getStarBrightness(float1) * var25;
         if (var27 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers(this.theWorld)) {
            GlStateManager.color(var27, var27, var27, var27);
            if (this.vboEnabled) {
               this.Q.bindBuffer();
               GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
               GL11.glVertexPointer(3, GL11.GL_FLOAT, 12, 0L);
               this.Q.drawArrays(7);
               this.Q.unbindBuffer();
               GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            } else {
               GlStateManager.callList(this.N);
            }
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
         GlStateManager.enableAlpha();
         GlStateManager.enableFog();
         if (var5) {
            Shaders.enableFog();
         }

         GlStateManager.popMatrix();
         GlStateManager.disableTexture2D();
         if (var5) {
            Shaders.an();
         }

         GlStateManager.color(0.0F, 0.0F, 0.0F);
         double var29 = this.mc.thePlayer.getPositionEyes(float1).yCoord - this.theWorld.getHorizon();
         if (var29 < 0.0) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 12.0F, 0.0F);
            if (this.vboEnabled) {
               this.S.bindBuffer();
               GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
               GL11.glVertexPointer(3, GL11.GL_FLOAT, 12, 0L);
               this.S.drawArrays(7);
               this.S.unbindBuffer();
               GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
            } else {
               GlStateManager.callList(this.P);
            }

            GlStateManager.popMatrix();
            float var35 = -((float)(var29 + 65.0));
            var23.begin(7, DefaultVertexFormats.POSITION_COLOR);
            var23.pos(-1.0, var35, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, var35, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, var35, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, var35, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, var35, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, var35, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, var35, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, var35, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(-1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, -1.0, 1.0).color(0, 0, 0, 255).endVertex();
            var23.pos(1.0, -1.0, -1.0).color(0, 0, 0, 255).endVertex();
            var22.draw();
         }

         if (this.theWorld.provider.isSkyColored()) {
            GlStateManager.color(var7 * 0.2F + 0.04F, var8 * 0.2F + 0.04F, var9 * 0.6F + 0.1F);
         } else {
            GlStateManager.color(var7, var8, var9);
         }

         if (this.mc.gameSettings.renderDistanceChunks <= 4) {
            GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(0.0F, -((float)(var29 - 16.0)), 0.0F);
         if (Config.isSkyEnabled()) {
            if (this.vboEnabled) {
               this.S.bindBuffer();
               GlStateManager.glEnableClientState(32884);
               GlStateManager.glVertexPointer(3, 5126, 12, 0);
               this.S.drawArrays(7);
               this.S.unbindBuffer();
               GlStateManager.glDisableClientState(32884);
            } else {
               GlStateManager.callList(this.P);
            }
         }

         GlStateManager.popMatrix();
         GlStateManager.enableTexture2D();
         if (var5) {
            Shaders.enableTexture2D();
         }

         GlStateManager.depthMask(true);
      }
   }

   public void renderClouds(float float1, int integer) {
      if (!Config.isCloudsOff() && this.mc.theWorld.provider.isSurfaceWorld()) {
         if (Config.isShaders()) {
            Shaders.beginClouds();
         }

         if (Config.isCloudsFancy()) {
            this.renderCloudsFancy(float1, integer);
         } else {
            Vec3 var5 = this.theWorld.Method330(float1);
            this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, float1, var5);
            GlStateManager.disableCull();
            float var6 = (float)(
               this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * 0.0
            );
            Tessellator var7 = Tessellator.getInstance();
            WorldRenderer var8 = var7.getWorldRenderer();
            this.renderEngine.bindTexture(locationCloudsPng);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if (this.cloudRenderer.shouldUpdateGlList()) {
               this.cloudRenderer.startUpdateGlList();
               float var9 = (float)var5.xCoord;
               float var10 = (float)var5.yCoord;
               float var11 = (float)var5.zCoord;
               if (integer != 2) {
                  float var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
                  float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
                  float var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
                  var9 = var12;
                  var10 = var13;
                  var11 = var14;
               }

               double var25 = this.cloudTickCounter + 0.0F;
               double var26 = this.mc.getRenderViewEntity().prevPosX
                  + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * 0.0
                  + var25 * 0.03F;
               double var16 = this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * 0.0;
               int var18 = MathHelper.floor_double(var26 / 2048.0);
               int var19 = MathHelper.floor_double(var16 / 2048.0);
               var26 -= var18 * 2048;
               var16 -= var19 * 2048;
               float var20 = this.theWorld.provider.getCloudHeight() - var6 + 0.33F + this.mc.gameSettings.ofCloudsHeight * 128.0F;
               float var21 = (float)(var26 * 4.8828125E-4);
               float var22 = (float)(var16 * 4.8828125E-4);
               var8.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

               for (short var23 = -256; var23 < 256; var23 += 32) {
                  for (short var24 = -256; var24 < 256; var24 += 32) {
                     var8.pos(var23, var20, var24 + 32)
                        .tex(var23 * 4.8828125E-4F + var21, (var24 + 32) * 4.8828125E-4F + var22)
                        .color(var9, var10, var11, 0.8F)
                        .endVertex();
                     var8.pos(var23 + 32, var20, var24 + 32)
                        .tex((var23 + 32) * 4.8828125E-4F + var21, (var24 + 32) * 4.8828125E-4F + var22)
                        .color(var9, var10, var11, 0.8F)
                        .endVertex();
                     var8.pos(var23 + 32, var20, var24)
                        .tex((var23 + 32) * 4.8828125E-4F + var21, var24 * 4.8828125E-4F + var22)
                        .color(var9, var10, var11, 0.8F)
                        .endVertex();
                     var8.pos(var23, var20, var24)
                        .tex(var23 * 4.8828125E-4F + var21, var24 * 4.8828125E-4F + var22)
                        .color(var9, var10, var11, 0.8F)
                        .endVertex();
                  }
               }

               var7.draw();
               this.cloudRenderer.endUpdateGlList();
            }

            this.cloudRenderer.renderGlList();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.enableCull();
         }

         if (Config.isShaders()) {
            Shaders.endClouds();
         }
      }
   }

   public boolean hasCloudFog(double double1, double double2, double double3, float float4) {
      return false;
   }

   private void renderCloudsFancy(float float1, int integer) {
      Vec3 var5 = this.theWorld.Method330(float1);
      this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, float1, var5);
      GlStateManager.disableCull();
      float var6 = (float)(this.mc.getRenderViewEntity().lastTickPosY + (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY) * 0.0);
      Tessellator var7 = Tessellator.getInstance();
      WorldRenderer var8 = var7.getWorldRenderer();
      double var9 = this.cloudTickCounter + 0.0F;
      double var11 = (
            this.mc.getRenderViewEntity().prevPosX + (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * 0.0 + var9 * 0.03F
         )
         / 12.0;
      double var13 = (this.mc.getRenderViewEntity().prevPosZ + (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * 0.0) / 12.0
         + 0.33F;
      float var15 = this.theWorld.provider.getCloudHeight() - var6 + 0.33F + this.mc.gameSettings.ofCloudsHeight * 128.0F;
      int var16 = MathHelper.floor_double(var11 / 2048.0);
      int var17 = MathHelper.floor_double(var13 / 2048.0);
      var11 -= var16 * 2048;
      var13 -= var17 * 2048;
      this.renderEngine.bindTexture(locationCloudsPng);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      float var18 = (float)var5.xCoord;
      float var19 = (float)var5.yCoord;
      float var20 = (float)var5.zCoord;
      if (integer != 2) {
         float var21 = (var18 * 30.0F + var19 * 59.0F + var20 * 11.0F) / 100.0F;
         float var22 = (var18 * 30.0F + var19 * 70.0F) / 100.0F;
         float var23 = (var18 * 30.0F + var20 * 70.0F) / 100.0F;
         var18 = var21;
         var19 = var22;
         var20 = var23;
      }

      float var47 = var18 * 0.9F;
      float var48 = var19 * 0.9F;
      float var49 = var20 * 0.9F;
      float var24 = var18 * 0.7F;
      float var25 = var19 * 0.7F;
      float var26 = var20 * 0.7F;
      float var27 = var18 * 0.8F;
      float var28 = var19 * 0.8F;
      float var29 = var20 * 0.8F;
      float var31 = MathHelper.floor_double(var11) * 0.00390625F;
      float var32 = MathHelper.floor_double(var13) * 0.00390625F;
      float var33 = (float)(var11 - MathHelper.floor_double(var11));
      float var34 = (float)(var13 - MathHelper.floor_double(var13));
      GlStateManager.scale(12.0F, 1.0F, 12.0F);

      for (int var38 = 0; var38 < 2; var38++) {
         if (var38 == 0) {
            GlStateManager.colorMask(false, false, false, false);
         } else {
            switch (integer) {
               case 0:
                  GlStateManager.colorMask(false, true, true, true);
                  break;
               case 1:
                  GlStateManager.colorMask(true, false, false, true);
                  break;
               case 2:
                  GlStateManager.colorMask(true, true, true, true);
            }
         }

         this.cloudRenderer.renderGlList();
      }

      if (this.cloudRenderer.shouldUpdateGlList()) {
         this.cloudRenderer.startUpdateGlList();

         for (int var50 = -3; var50 <= 4; var50++) {
            for (int var39 = -3; var39 <= 4; var39++) {
               var8.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
               float var40 = var50 * 8;
               float var41 = var39 * 8;
               float var42 = var40 - var33;
               float var43 = var41 - var34;
               if (var15 > -5.0F) {
                  var8.pos(var42 + 0.0F, var15 + 0.0F, var43 + 8.0F)
                     .tex((var40 + 0.0F) * 0.00390625F + var31, (var41 + 8.0F) * 0.00390625F + var32)
                     .color(var24, var25, var26, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .endVertex();
                  var8.pos(var42 + 8.0F, var15 + 0.0F, var43 + 8.0F)
                     .tex((var40 + 8.0F) * 0.00390625F + var31, (var41 + 8.0F) * 0.00390625F + var32)
                     .color(var24, var25, var26, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .endVertex();
                  var8.pos(var42 + 8.0F, var15 + 0.0F, var43 + 0.0F)
                     .tex((var40 + 8.0F) * 0.00390625F + var31, (var41 + 0.0F) * 0.00390625F + var32)
                     .color(var24, var25, var26, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .endVertex();
                  var8.pos(var42 + 0.0F, var15 + 0.0F, var43 + 0.0F)
                     .tex((var40 + 0.0F) * 0.00390625F + var31, (var41 + 0.0F) * 0.00390625F + var32)
                     .color(var24, var25, var26, 0.8F)
                     .normal(0.0F, -1.0F, 0.0F)
                     .endVertex();
               }

               if (var15 <= 5.0F) {
                  var8.pos(var42 + 0.0F, var15 + 4.0F - 9.765625E-4F, var43 + 8.0F)
                     .tex((var40 + 0.0F) * 0.00390625F + var31, (var41 + 8.0F) * 0.00390625F + var32)
                     .color(var18, var19, var20, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .endVertex();
                  var8.pos(var42 + 8.0F, var15 + 4.0F - 9.765625E-4F, var43 + 8.0F)
                     .tex((var40 + 8.0F) * 0.00390625F + var31, (var41 + 8.0F) * 0.00390625F + var32)
                     .color(var18, var19, var20, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .endVertex();
                  var8.pos(var42 + 8.0F, var15 + 4.0F - 9.765625E-4F, var43 + 0.0F)
                     .tex((var40 + 8.0F) * 0.00390625F + var31, (var41 + 0.0F) * 0.00390625F + var32)
                     .color(var18, var19, var20, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .endVertex();
                  var8.pos(var42 + 0.0F, var15 + 4.0F - 9.765625E-4F, var43 + 0.0F)
                     .tex((var40 + 0.0F) * 0.00390625F + var31, (var41 + 0.0F) * 0.00390625F + var32)
                     .color(var18, var19, var20, 0.8F)
                     .normal(0.0F, 1.0F, 0.0F)
                     .endVertex();
               }

               if (var50 > -1) {
                  for (int var44 = 0; var44 < 8; var44++) {
                     var8.pos(var42 + var44 + 0.0F, var15 + 0.0F, var43 + 8.0F)
                        .tex((var40 + var44 + 0.5F) * 0.00390625F + var31, (var41 + 8.0F) * 0.00390625F + var32)
                        .color(var47, var48, var49, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .endVertex();
                     var8.pos(var42 + var44 + 0.0F, var15 + 4.0F, var43 + 8.0F)
                        .tex((var40 + var44 + 0.5F) * 0.00390625F + var31, (var41 + 8.0F) * 0.00390625F + var32)
                        .color(var47, var48, var49, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .endVertex();
                     var8.pos(var42 + var44 + 0.0F, var15 + 4.0F, var43 + 0.0F)
                        .tex((var40 + var44 + 0.5F) * 0.00390625F + var31, (var41 + 0.0F) * 0.00390625F + var32)
                        .color(var47, var48, var49, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .endVertex();
                     var8.pos(var42 + var44 + 0.0F, var15 + 0.0F, var43 + 0.0F)
                        .tex((var40 + var44 + 0.5F) * 0.00390625F + var31, (var41 + 0.0F) * 0.00390625F + var32)
                        .color(var47, var48, var49, 0.8F)
                        .normal(-1.0F, 0.0F, 0.0F)
                        .endVertex();
                  }
               }

               if (var50 <= 1) {
                  for (int var51 = 0; var51 < 8; var51++) {
                     var8.pos(var42 + var51 + 1.0F - 9.765625E-4F, var15 + 0.0F, var43 + 8.0F)
                        .tex((var40 + var51 + 0.5F) * 0.00390625F + var31, (var41 + 8.0F) * 0.00390625F + var32)
                        .color(var47, var48, var49, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .endVertex();
                     var8.pos(var42 + var51 + 1.0F - 9.765625E-4F, var15 + 4.0F, var43 + 8.0F)
                        .tex((var40 + var51 + 0.5F) * 0.00390625F + var31, (var41 + 8.0F) * 0.00390625F + var32)
                        .color(var47, var48, var49, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .endVertex();
                     var8.pos(var42 + var51 + 1.0F - 9.765625E-4F, var15 + 4.0F, var43 + 0.0F)
                        .tex((var40 + var51 + 0.5F) * 0.00390625F + var31, (var41 + 0.0F) * 0.00390625F + var32)
                        .color(var47, var48, var49, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .endVertex();
                     var8.pos(var42 + var51 + 1.0F - 9.765625E-4F, var15 + 0.0F, var43 + 0.0F)
                        .tex((var40 + var51 + 0.5F) * 0.00390625F + var31, (var41 + 0.0F) * 0.00390625F + var32)
                        .color(var47, var48, var49, 0.8F)
                        .normal(1.0F, 0.0F, 0.0F)
                        .endVertex();
                  }
               }

               if (var39 > -1) {
                  for (int var52 = 0; var52 < 8; var52++) {
                     var8.pos(var42 + 0.0F, var15 + 4.0F, var43 + var52 + 0.0F)
                        .tex((var40 + 0.0F) * 0.00390625F + var31, (var41 + var52 + 0.5F) * 0.00390625F + var32)
                        .color(var27, var28, var29, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .endVertex();
                     var8.pos(var42 + 8.0F, var15 + 4.0F, var43 + var52 + 0.0F)
                        .tex((var40 + 8.0F) * 0.00390625F + var31, (var41 + var52 + 0.5F) * 0.00390625F + var32)
                        .color(var27, var28, var29, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .endVertex();
                     var8.pos(var42 + 8.0F, var15 + 0.0F, var43 + var52 + 0.0F)
                        .tex((var40 + 8.0F) * 0.00390625F + var31, (var41 + var52 + 0.5F) * 0.00390625F + var32)
                        .color(var27, var28, var29, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .endVertex();
                     var8.pos(var42 + 0.0F, var15 + 0.0F, var43 + var52 + 0.0F)
                        .tex((var40 + 0.0F) * 0.00390625F + var31, (var41 + var52 + 0.5F) * 0.00390625F + var32)
                        .color(var27, var28, var29, 0.8F)
                        .normal(0.0F, 0.0F, -1.0F)
                        .endVertex();
                  }
               }

               if (var39 <= 1) {
                  for (int var53 = 0; var53 < 8; var53++) {
                     var8.pos(var42 + 0.0F, var15 + 4.0F, var43 + var53 + 1.0F - 9.765625E-4F)
                        .tex((var40 + 0.0F) * 0.00390625F + var31, (var41 + var53 + 0.5F) * 0.00390625F + var32)
                        .color(var27, var28, var29, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .endVertex();
                     var8.pos(var42 + 8.0F, var15 + 4.0F, var43 + var53 + 1.0F - 9.765625E-4F)
                        .tex((var40 + 8.0F) * 0.00390625F + var31, (var41 + var53 + 0.5F) * 0.00390625F + var32)
                        .color(var27, var28, var29, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .endVertex();
                     var8.pos(var42 + 8.0F, var15 + 0.0F, var43 + var53 + 1.0F - 9.765625E-4F)
                        .tex((var40 + 8.0F) * 0.00390625F + var31, (var41 + var53 + 0.5F) * 0.00390625F + var32)
                        .color(var27, var28, var29, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .endVertex();
                     var8.pos(var42 + 0.0F, var15 + 0.0F, var43 + var53 + 1.0F - 9.765625E-4F)
                        .tex((var40 + 0.0F) * 0.00390625F + var31, (var41 + var53 + 0.5F) * 0.00390625F + var32)
                        .color(var27, var28, var29, 0.8F)
                        .normal(0.0F, 0.0F, 1.0F)
                        .endVertex();
                  }
               }

               var7.draw();
            }
         }

         this.cloudRenderer.endUpdateGlList();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.enableCull();
   }

   public void updateChunks(long long1) {
      long1 = (long)(long1 + 1.0E8);
      this.displayListEntitiesDirty = this.displayListEntitiesDirty | this.renderDispatcher.runChunkUploads(long1);
      if (this.chunksToUpdateForced.size() > 0) {
         Iterator var3 = this.chunksToUpdateForced.iterator();

         while (var3.hasNext()) {
            RenderChunk var4 = (RenderChunk)var3.next();
            if (!this.renderDispatcher.updateChunkLater(var4)) {
               break;
            }

            var4.setNeedsUpdate(false);
            var3.remove();
            this.chunksToUpdate.remove(var4);
            this.chunksToResortTransparency.remove(var4);
         }
      }

      if (this.chunksToResortTransparency.size() > 0) {
         Iterator var13 = this.chunksToResortTransparency.iterator();
         if (var13.hasNext()) {
            RenderChunk var15 = (RenderChunk)var13.next();
            if (this.renderDispatcher.updateTransparencyLater(var15)) {
               var13.remove();
            }
         }
      }

      double var14 = 0.0;
      int var5 = Config.getUpdatesPerFrame();
      if (!this.chunksToUpdate.isEmpty()) {
         Iterator var6 = this.chunksToUpdate.iterator();

         while (var6.hasNext()) {
            RenderChunk var7 = (RenderChunk)var6.next();
            boolean var8 = var7.isChunkRegionEmpty();
            boolean var9;
            if (var8) {
               var9 = this.renderDispatcher.updateChunkNow(var7);
            } else {
               var9 = this.renderDispatcher.updateChunkLater(var7);
            }

            if (!var9) {
               break;
            }

            var7.setNeedsUpdate(false);
            var6.remove();
            if (!var8) {
               double var10 = 2.0 * RenderChunkUtils.getRelativeBufferSize(var7);
               var14 += var10;
               if (var14 > var5) {
                  break;
               }
            }
         }
      }
   }

   public void renderWorldBorder(Entity entity, float float2) {
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      WorldBorder var7 = this.theWorld.getWorldBorder();
      double var8 = this.mc.gameSettings.renderDistanceChunks * 16;
      if (entity.posX >= var7.maxX() - var8 || entity.posX <= var7.minX() + var8 || entity.posZ >= var7.maxZ() - var8 || entity.posZ <= var7.minZ() + var8) {
         if (Config.isShaders()) {
            Shaders.pushProgram();
            Shaders.useProgram(Shaders.ProgramTexturedLit);
         }

         double var10 = 1.0 - var7.getClosestDistance(entity) / var8;
         var10 = Math.pow(var10, 4.0);
         double var12 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * float2;
         double var14 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * float2;
         double var16 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * float2;
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
         this.renderEngine.bindTexture(locationForcefieldPng);
         GlStateManager.depthMask(false);
         GlStateManager.pushMatrix();
         int var18 = var7.getStatus().getID();
         float var19 = (var18 >> 16 & 0xFF) / 255.0F;
         float var20 = (var18 >> 8 & 0xFF) / 255.0F;
         float var21 = (var18 & 0xFF) / 255.0F;
         GlStateManager.color(var19, var20, var21, (float)var10);
         GlStateManager.doPolygonOffset(-3.0F, -3.0F);
         GlStateManager.enablePolygonOffset();
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.enableAlpha();
         GlStateManager.disableCull();
         float var22 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F;
         var6.begin(7, DefaultVertexFormats.POSITION_TEX);
         var6.setTranslation(-var12, -var14, -var16);
         double var26 = Math.max(MathHelper.floor_double(var16 - var8), var7.minZ());
         double var28 = Math.min(MathHelper.ceiling_double_int(var16 + var8), var7.maxZ());
         if (var12 > var7.maxX() - var8) {
            float var30 = 0.0F;

            for (double var31 = var26; var31 < var28; var30 += 0.5F) {
               double var33 = Math.min(1.0, var28 - var31);
               float var35 = (float)var33 * 0.5F;
               var6.pos(var7.maxX(), 256.0, var31).tex(var22 + var30, var22 + 0.0F).endVertex();
               var6.pos(var7.maxX(), 256.0, var31 + var33).tex(var22 + var35 + var30, var22 + 0.0F).endVertex();
               var6.pos(var7.maxX(), 0.0, var31 + var33).tex(var22 + var35 + var30, var22 + 128.0F).endVertex();
               var6.pos(var7.maxX(), 0.0, var31).tex(var22 + var30, var22 + 128.0F).endVertex();
               var31++;
            }
         }

         if (var12 < var7.minX() + var8) {
            float var39 = 0.0F;

            for (double var42 = var26; var42 < var28; var39 += 0.5F) {
               double var45 = Math.min(1.0, var28 - var42);
               float var48 = (float)var45 * 0.5F;
               var6.pos(var7.minX(), 256.0, var42).tex(var22 + var39, var22 + 0.0F).endVertex();
               var6.pos(var7.minX(), 256.0, var42 + var45).tex(var22 + var48 + var39, var22 + 0.0F).endVertex();
               var6.pos(var7.minX(), 0.0, var42 + var45).tex(var22 + var48 + var39, var22 + 128.0F).endVertex();
               var6.pos(var7.minX(), 0.0, var42).tex(var22 + var39, var22 + 128.0F).endVertex();
               var42++;
            }
         }

         var26 = Math.max(MathHelper.floor_double(var12 - var8), var7.minX());
         var28 = Math.min(MathHelper.ceiling_double_int(var12 + var8), var7.maxX());
         if (var16 > var7.maxZ() - var8) {
            float var40 = 0.0F;

            for (double var43 = var26; var43 < var28; var40 += 0.5F) {
               double var46 = Math.min(1.0, var28 - var43);
               float var49 = (float)var46 * 0.5F;
               var6.pos(var43, 256.0, var7.maxZ()).tex(var22 + var40, var22 + 0.0F).endVertex();
               var6.pos(var43 + var46, 256.0, var7.maxZ()).tex(var22 + var49 + var40, var22 + 0.0F).endVertex();
               var6.pos(var43 + var46, 0.0, var7.maxZ()).tex(var22 + var49 + var40, var22 + 128.0F).endVertex();
               var6.pos(var43, 0.0, var7.maxZ()).tex(var22 + var40, var22 + 128.0F).endVertex();
               var43++;
            }
         }

         if (var16 < var7.minZ() + var8) {
            float var41 = 0.0F;

            for (double var44 = var26; var44 < var28; var41 += 0.5F) {
               double var47 = Math.min(1.0, var28 - var44);
               float var50 = (float)var47 * 0.5F;
               var6.pos(var44, 256.0, var7.minZ()).tex(var22 + var41, var22 + 0.0F).endVertex();
               var6.pos(var44 + var47, 256.0, var7.minZ()).tex(var22 + var50 + var41, var22 + 0.0F).endVertex();
               var6.pos(var44 + var47, 0.0, var7.minZ()).tex(var22 + var50 + var41, var22 + 128.0F).endVertex();
               var6.pos(var44, 0.0, var7.minZ()).tex(var22 + var41, var22 + 128.0F).endVertex();
               var44++;
            }
         }

         var5.draw();
         var6.setTranslation(0.0, 0.0, 0.0);
         GlStateManager.enableCull();
         GlStateManager.disableAlpha();
         GlStateManager.doPolygonOffset(0.0F, 0.0F);
         GlStateManager.disablePolygonOffset();
         GlStateManager.enableAlpha();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         if (Config.isShaders()) {
            Shaders.popProgram();
         }
      }
   }

   private void preRenderDamagedBlocks() {
      GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
      GlStateManager.enableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
      GlStateManager.doPolygonOffset(-1.0F, -10.0F);
      GlStateManager.enablePolygonOffset();
      GlStateManager.alphaFunc(516, 0.1F);
      GlStateManager.enableAlpha();
      GlStateManager.pushMatrix();
      if (Config.isShaders()) {
         ShadersRender.beginBlockDamage();
      }
   }

   private void postRenderDamagedBlocks() {
      GlStateManager.disableAlpha();
      GlStateManager.doPolygonOffset(0.0F, 0.0F);
      GlStateManager.disablePolygonOffset();
      GlStateManager.enableAlpha();
      GlStateManager.depthMask(true);
      GlStateManager.popMatrix();
      if (Config.isShaders()) {
         ShadersRender.endBlockDamage();
      }
   }

   public void drawBlockDamageTexture(Tessellator tessellator, WorldRenderer worldRenderer, Entity entity, float float4) {
      double var7 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * float4;
      double var9 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * float4;
      double var11 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * float4;
      if (!this.damagedBlocks.isEmpty()) {
         this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
         this.preRenderDamagedBlocks();
         worldRenderer.begin(7, DefaultVertexFormats.BLOCK);
         worldRenderer.setTranslation(-var7, -var9, -var11);
         worldRenderer.noColor();
         Iterator var13 = this.damagedBlocks.values().iterator();

         while (var13.hasNext()) {
            DestroyBlockProgress var14 = (DestroyBlockProgress)var13.next();
            BlockPos var15 = var14.getPosition();
            double var16 = var15.getX() - var7;
            double var18 = var15.getY() - var9;
            double var20 = var15.getZ() - var11;
            Block var22 = this.theWorld.getBlockState(var15).getBlock();
            boolean var23 = !(var22 instanceof BlockChest)
               && !(var22 instanceof BlockEnderChest)
               && !(var22 instanceof BlockSign)
               && !(var22 instanceof BlockSkull);
            if (var23) {
               if (var16 * var16 + var18 * var18 + var20 * var20 > 1024.0) {
                  var13.remove();
               } else {
                  IBlockState var24 = this.theWorld.getBlockState(var15);
                  if (var24.getBlock().getMaterial() != Material.air) {
                     int var25 = var14.getPartialBlockDamage();
                     TextureAtlasSprite var26 = this.destroyBlockIcons[var25];
                     BlockRendererDispatcher var27 = this.mc.getBlockRendererDispatcher();
                     var27.renderBlockDamage(var24, var15, var26, this.theWorld);
                  }
               }
            }
         }

         tessellator.draw();
         worldRenderer.setTranslation(0.0, 0.0, 0.0);
         this.postRenderDamagedBlocks();
      }
   }

   public void drawSelectionBox(EntityPlayer entityPlayer, MovingObjectPosition movingObjectPosition, int integer, float float4) {
      if (integer == 0 && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
         GL11.glLineWidth(2.0F);
         GlStateManager.disableTexture2D();
         if (Config.isShaders()) {
            Shaders.an();
         }

         GlStateManager.depthMask(false);
         BlockPos var8 = movingObjectPosition.getBlockPos();
         Block var9 = this.theWorld.getBlockState(var8).getBlock();
         if (var9.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(var8)) {
            var9.setBlockBoundsBasedOnState(this.theWorld, var8);
            double var10 = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * float4;
            double var12 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * float4;
            double var14 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * float4;
            AxisAlignedBB var16 = var9.getSelectedBoundingBox(this.theWorld, var8);
            Block.EnumOffsetType var17 = var9.getOffsetType();
            if (var17 != Block.EnumOffsetType.NONE) {
               var16 = BlockModelUtils.getOffsetBoundingBox(var16, var17, var8);
            }

            drawSelectionBoundingBox(var16.expand(0.002F, 0.002F, 0.002F).offset(-var10, -var12, -var14));
         }

         GlStateManager.depthMask(true);
         GlStateManager.enableTexture2D();
         if (Config.isShaders()) {
            Shaders.enableTexture2D();
         }

         GlStateManager.disableBlend();
      }
   }

   private void markBlocksForUpdate(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      this.viewFrustum.markBlocksForUpdate(integer1, integer2, integer3, integer4, integer5, integer6);
   }

   @Override
   public void markBlockForUpdate(BlockPos blockPos) {
      int var2 = blockPos.getX();
      int var3 = blockPos.getY();
      int var4 = blockPos.getZ();
      this.markBlocksForUpdate(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
   }

   @Override
   public void notifyLightSet(BlockPos blockPos) {
      int var2 = blockPos.getX();
      int var3 = blockPos.getY();
      int var4 = blockPos.getZ();
      this.markBlocksForUpdate(var2 - 1, var3 - 1, var4 - 1, var2 + 1, var3 + 1, var4 + 1);
   }

   @Override
   public void markBlockRangeForRenderUpdate(int integer1, int integer2, int integer3, int integer4, int integer5, int integer6) {
      this.markBlocksForUpdate(integer1 - 1, integer2 - 1, integer3 - 1, integer4 + 1, integer5 + 1, integer6 + 1);
   }

   @Override
   public void playRecord(String string, BlockPos blockPos) {
      ISound var3 = this.mapSoundPositions.get(blockPos);
      if (var3 != null) {
         this.mc.getSoundHandler().stopSound(var3);
         this.mapSoundPositions.remove(blockPos);
      }

      if (string != null) {
         ItemRecord var4 = ItemRecord.getRecord(string);
         if (var4 != null) {
            this.mc.ingameGUI.setRecordPlayingMessage(var4.getRecordNameLocal());
         }

         PositionedSoundRecord var5 = PositionedSoundRecord.create(new ResourceLocation(string), blockPos.getX(), blockPos.getY(), blockPos.getZ());
         this.mapSoundPositions.put(blockPos, var5);
         this.mc.getSoundHandler().playSound(var5);
      }
   }

   @Override
   public void playSound(String string, double double2, double double3, double double4, float float5, float float6) {
   }

   @Override
   public void playSoundToNearExcept(EntityPlayer entityPlayer, String string, double double3, double double4, double double5, float float6, float float7) {
   }

   @Override
   public void spawnParticle(
      int integer, boolean boolean2, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
   ) {
      try {
         this.spawnEntityFX(integer, boolean2, double3, double4, double5, double6, double7, double8, arr);
      } catch (Throwable var19) {
         CrashReport var17 = CrashReport.makeCrashReport(var19, "Exception while adding particle");
         CrashReportCategory var18 = var17.makeCategory("Particle being added");
         var18.addCrashSection("ID", integer);
         if (arr != null) {
            var18.addCrashSection("Parameters", arr);
         }

         var18.addCrashSectionCallable("Position", () -> CrashReportCategory.getCoordinateInfo(double3, double4, double5));
         throw new ReportedException(var17);
      }
   }

   private void spawnParticle(
      EnumParticleTypes enumParticleTypes, double double2, double double3, double double4, double double5, double double6, double double7, int... arr
   ) {
      this.spawnParticle(enumParticleTypes.getParticleID(), enumParticleTypes.getShouldIgnoreRange(), double2, double3, double4, double5, double6, double7, arr);
   }

   private EntityFX spawnEntityFX(
      int integer, boolean boolean2, double double3, double double4, double double5, double double6, double double7, double double8, int... arr
   ) {
      if (this.mc != null && this.mc.getRenderViewEntity() != null && this.mc.effectRenderer != null) {
         int var16 = this.mc.gameSettings.particleSetting;
         if (var16 == 1 && this.theWorld.rand.nextInt(3) == 0) {
            var16 = 2;
         }

         double var17 = this.mc.getRenderViewEntity().posX - double3;
         double var19 = this.mc.getRenderViewEntity().posY - double4;
         double var21 = this.mc.getRenderViewEntity().posZ - double5;
         if (integer == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
         }

         if (integer == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
         }

         if (integer == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion()) {
            return null;
         }

         if (integer == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles()) {
            return null;
         }

         if (integer == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles()) {
            return null;
         }

         if (integer == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke()) {
            return null;
         }

         if (integer == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke()) {
            return null;
         }

         if (integer == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles()) {
            return null;
         }

         if (integer == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles()) {
            return null;
         }

         if (integer == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles()) {
            return null;
         }

         if (integer == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles()) {
            return null;
         }

         if (integer == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles()) {
            return null;
         }

         if (integer == EnumParticleTypes.PORTAL.getParticleID() && !Config.isPortalParticles()) {
            return null;
         }

         if (integer == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame()) {
            return null;
         }

         if (integer == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone()) {
            return null;
         }

         if (integer == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava()) {
            return null;
         }

         if (integer == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava()) {
            return null;
         }

         if (integer == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles()) {
            return null;
         }

         if (!boolean2) {
            double var23 = 256.0;
            if (integer == EnumParticleTypes.CRIT.getParticleID()) {
               var23 = 38416.0;
            }

            if (var17 * var17 + var19 * var19 + var21 * var21 > var23) {
               return null;
            }

            if (var16 > 1) {
               return null;
            }
         }

         EntityFX var25 = this.mc.effectRenderer.spawnEffectParticle(integer, double3, double4, double5, double6, double7, double8, arr);
         if (integer == EnumParticleTypes.WATER_BUBBLE.getParticleID()) {
            CustomColors.updateWaterFX(var25, this.theWorld, double3, double4, double5, this.renderEnv);
         }

         if (integer == EnumParticleTypes.WATER_SPLASH.getParticleID()) {
            CustomColors.updateWaterFX(var25, this.theWorld, double3, double4, double5, this.renderEnv);
         }

         if (integer == EnumParticleTypes.WATER_DROP.getParticleID()) {
            CustomColors.updateWaterFX(var25, this.theWorld, double3, double4, double5, this.renderEnv);
         }

         if (integer == EnumParticleTypes.TOWN_AURA.getParticleID()) {
            CustomColors.updateMyceliumFX(var25);
         }

         if (integer == EnumParticleTypes.PORTAL.getParticleID()) {
            CustomColors.updatePortalFX(var25);
         }

         if (integer == EnumParticleTypes.REDSTONE.getParticleID()) {
            CustomColors.updateReddustFX(var25, this.theWorld, double3, double4, double5);
         }

         return var25;
      } else {
         return null;
      }
   }

   @Override
   public void onEntityAdded(Entity entity) {
      RandomEntities.entityLoaded(entity, this.theWorld);
      if (Config.isDynamicLights()) {
         DynamicLights.entityAdded(entity, this);
      }
   }

   @Override
   public void onEntityRemoved(Entity entity) {
      RandomEntities.entityUnloaded(entity, this.theWorld);
      if (Config.isDynamicLights()) {
         DynamicLights.entityRemoved(entity, this);
      }
   }

   public void deleteAllDisplayLists() {
   }

   @Override
   public void broadcastSound(int integer1, BlockPos blockPos, int integer3) {
      switch (integer1) {
         case 1013:
         case 1018:
            if (this.mc.getRenderViewEntity() != null) {
               double var6 = blockPos.getX() - this.mc.getRenderViewEntity().posX;
               double var8 = blockPos.getY() - this.mc.getRenderViewEntity().posY;
               double var10 = blockPos.getZ() - this.mc.getRenderViewEntity().posZ;
               double var12 = Math.sqrt(var6 * var6 + var8 * var8 + var10 * var10);
               double var14 = this.mc.getRenderViewEntity().posX;
               double var16 = this.mc.getRenderViewEntity().posY;
               double var18 = this.mc.getRenderViewEntity().posZ;
               if (var12 > 0.0) {
                  var14 += var6 / var12 * 2.0;
                  var16 += var8 / var12 * 2.0;
                  var18 += var10 / var12 * 2.0;
               }

               if (integer1 == 1013) {
                  this.theWorld.playSound(var14, var16, var18, "mob.wither.spawn", 1.0F, 1.0F, false);
               } else {
                  this.theWorld.playSound(var14, var16, var18, "mob.enderdragon.end", 5.0F, 1.0F, false);
               }
            }
      }
   }

   @Override
   public void playAuxSFX(EntityPlayer entityPlayer, int integer2, BlockPos blockPos, int integer4) {
      Random var7 = this.theWorld.rand;
      switch (integer2) {
         case 1000:
            this.theWorld.playSoundAtPos(blockPos, "random.click", 1.0F, 1.0F, false);
            break;
         case 1001:
            this.theWorld.playSoundAtPos(blockPos, "random.click", 1.0F, 1.2F, false);
            break;
         case 1002:
            this.theWorld.playSoundAtPos(blockPos, "random.bow", 1.0F, 1.2F, false);
            break;
         case 1003:
            this.theWorld.playSoundAtPos(blockPos, "random.door_open", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1004:
            this.theWorld.playSoundAtPos(blockPos, "random.fizz", 0.5F, 2.6F + (var7.nextFloat() - var7.nextFloat()) * 0.8F, false);
            break;
         case 1005:
            if (Item.getItemById(integer4) instanceof ItemRecord) {
               this.theWorld.playRecord(blockPos, "records." + ((ItemRecord)Item.getItemById(integer4)).recordName);
            } else {
               this.theWorld.playRecord(blockPos, null);
            }
            break;
         case 1006:
            this.theWorld.playSoundAtPos(blockPos, "random.door_close", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1007:
            this.theWorld.playSoundAtPos(blockPos, "mob.ghast.charge", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1008:
            this.theWorld.playSoundAtPos(blockPos, "mob.ghast.fireball", 10.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1009:
            this.theWorld.playSoundAtPos(blockPos, "mob.ghast.fireball", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1010:
            this.theWorld.playSoundAtPos(blockPos, "mob.zombie.wood", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1011:
            this.theWorld.playSoundAtPos(blockPos, "mob.zombie.metal", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1012:
            this.theWorld.playSoundAtPos(blockPos, "mob.zombie.woodbreak", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1014:
            this.theWorld.playSoundAtPos(blockPos, "mob.wither.shoot", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1015:
            this.theWorld.playSoundAtPos(blockPos, "mob.bat.takeoff", 0.05F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1016:
            this.theWorld.playSoundAtPos(blockPos, "mob.zombie.infect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1017:
            this.theWorld.playSoundAtPos(blockPos, "mob.zombie.unfect", 2.0F, (var7.nextFloat() - var7.nextFloat()) * 0.2F + 1.0F, false);
            break;
         case 1020:
            this.theWorld.playSoundAtPos(blockPos, "random.anvil_break", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1021:
            this.theWorld.playSoundAtPos(blockPos, "random.anvil_use", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 1022:
            this.theWorld.playSoundAtPos(blockPos, "random.anvil_land", 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 2000:
            int var8 = integer4 % 3 - 1;
            int var9 = integer4 / 3 % 3 - 1;
            double var10 = blockPos.getX() + var8 * 0.6 + 0.5;
            double var12 = blockPos.getY() + 0.5;
            double var14 = blockPos.getZ() + var9 * 0.6 + 0.5;

            for (int var41 = 0; var41 < 10; var41++) {
               double var42 = var7.nextDouble() * 0.2 + 0.01;
               double var43 = var10 + var8 * 0.01 + (var7.nextDouble() - 0.5) * var9 * 0.5;
               double var44 = var12 + (var7.nextDouble() - 0.5) * 0.5;
               double var46 = var14 + var9 * 0.01 + (var7.nextDouble() - 0.5) * var8 * 0.5;
               double var47 = var8 * var42 + var7.nextGaussian() * 0.01;
               double var48 = -0.03 + var7.nextGaussian() * 0.01;
               double var50 = var9 * var42 + var7.nextGaussian() * 0.01;
               this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var43, var44, var46, var47, var48, var50);
            }

            return;
         case 2001:
            Block var16 = Block.getBlockById(integer4 & 4095);
            if (var16.getMaterial() != Material.air) {
               this.mc
                  .getSoundHandler()
                  .playSound(
                     new PositionedSoundRecord(
                        new ResourceLocation(var16.stepSound.getBreakSound()),
                        (var16.stepSound.getVolume() + 1.0F) / 2.0F,
                        var16.stepSound.getFrequency() * 0.8F,
                        blockPos.getX() + 0.5F,
                        blockPos.getY() + 0.5F,
                        blockPos.getZ() + 0.5F
                     )
                  );
            }

            this.mc.effectRenderer.addBlockDestroyEffects(blockPos, var16.getStateFromMeta(integer4 >> 12 & 0xFF));
            break;
         case 2002:
            double var17 = blockPos.getX();
            double var19 = blockPos.getY();
            double var21 = blockPos.getZ();

            for (int var23 = 0; var23 < 8; var23++) {
               this.spawnParticle(
                  EnumParticleTypes.ITEM_CRACK,
                  var17,
                  var19,
                  var21,
                  var7.nextGaussian() * 0.15,
                  var7.nextDouble() * 0.2,
                  var7.nextGaussian() * 0.15,
                  Item.getIdFromItem(Items.potionitem),
                  integer4
               );
            }

            int var45 = Items.potionitem.getColorFromDamage(integer4);
            float var24 = (var45 >> 16 & 0xFF) / 255.0F;
            float var25 = (var45 >> 8 & 0xFF) / 255.0F;
            float var26 = (var45 & 0xFF) / 255.0F;
            EnumParticleTypes var27 = EnumParticleTypes.SPELL;
            if (Items.potionitem.isEffectInstant(integer4)) {
               var27 = EnumParticleTypes.SPELL_INSTANT;
            }

            for (int var49 = 0; var49 < 100; var49++) {
               double var29 = var7.nextDouble() * 4.0;
               double var31 = var7.nextDouble() * Math.PI * 2.0;
               double var33 = Math.cos(var31) * var29;
               double var53 = 0.01 + var7.nextDouble() * 0.5;
               double var54 = Math.sin(var31) * var29;
               EntityFX var55 = this.spawnEntityFX(
                  var27.getParticleID(), var27.getShouldIgnoreRange(), var17 + var33 * 0.1, var19 + 0.3, var21 + var54 * 0.1, var33, var53, var54
               );
               if (var55 != null) {
                  float var40 = 0.75F + var7.nextFloat() * 0.25F;
                  var55.setRBGColorF(var24 * var40, var25 * var40, var26 * var40);
                  var55.multiplyVelocity((float)var29);
               }
            }

            this.theWorld.playSoundAtPos(blockPos, "game.potion.smash", 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
            break;
         case 2003:
            double var28 = blockPos.getX() + 0.5;
            double var30 = blockPos.getY();
            double var32 = blockPos.getZ() + 0.5;

            for (int var51 = 0; var51 < 8; var51++) {
               this.spawnParticle(
                  EnumParticleTypes.ITEM_CRACK,
                  var28,
                  var30,
                  var32,
                  var7.nextGaussian() * 0.15,
                  var7.nextDouble() * 0.2,
                  var7.nextGaussian() * 0.15,
                  Item.getIdFromItem(Items.ender_eye)
               );
            }

            for (double var52 = 0.0; var52 < Math.PI * 2; var52 += Math.PI / 20) {
               this.spawnParticle(
                  EnumParticleTypes.PORTAL,
                  var28 + Math.cos(var52) * 5.0,
                  var30 - 0.4,
                  var32 + Math.sin(var52) * 5.0,
                  Math.cos(var52) * -5.0,
                  0.0,
                  Math.sin(var52) * -5.0
               );
               this.spawnParticle(
                  EnumParticleTypes.PORTAL,
                  var28 + Math.cos(var52) * 5.0,
                  var30 - 0.4,
                  var32 + Math.sin(var52) * 5.0,
                  Math.cos(var52) * -7.0,
                  0.0,
                  Math.sin(var52) * -7.0
               );
            }

            return;
         case 2004:
            for (int var34 = 0; var34 < 20; var34++) {
               double var35 = blockPos.getX() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
               double var37 = blockPos.getY() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
               double var39 = blockPos.getZ() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
               this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var35, var37, var39, 0.0, 0.0, 0.0);
               this.theWorld.spawnParticle(EnumParticleTypes.FLAME, var35, var37, var39, 0.0, 0.0, 0.0);
            }

            return;
         case 2005:
            ItemDye.spawnBonemealParticles(this.theWorld, blockPos, integer4);
      }
   }

   @Override
   public void sendBlockBreakProgress(int integer1, BlockPos blockPos, int integer3) {
      if (integer3 >= 0 && integer3 < 10) {
         DestroyBlockProgress var6 = this.damagedBlocks.get(integer1);
         if (var6 == null
            || var6.getPosition().getX() != blockPos.getX()
            || var6.getPosition().getY() != blockPos.getY()
            || var6.getPosition().getZ() != blockPos.getZ()) {
            var6 = new DestroyBlockProgress(integer1, blockPos);
            this.damagedBlocks.put(integer1, var6);
         }

         var6.setPartialBlockDamage(integer3);
         var6.setCloudUpdateTick(this.cloudTickCounter);
      } else {
         this.damagedBlocks.remove(integer1);
      }
   }

   public void setDisplayListEntitiesDirty() {
      this.displayListEntitiesDirty = true;
   }

   public boolean hasNoChunkUpdates() {
      return this.chunksToUpdate.isEmpty() && this.renderDispatcher.hasChunkUpdates();
   }

   public void resetClouds() {
      this.cloudRenderer.reset();
   }

   public int getCountRenderers() {
      return this.viewFrustum.renderChunks.length;
   }

   public int getCountActiveRenderers() {
      return this.renderInfos.size();
   }

   public int getCountEntitiesRendered() {
      return this.countEntitiesRendered;
   }

   public int getCountTileEntitiesRendered() {
      return this.countTileEntitiesRendered;
   }

   public int getCountLoadedChunks() {
      if (this.theWorld == null) {
         return 0;
      }

      IChunkProvider var1 = this.theWorld.getChunkProvider();
      if (var1 == null) {
         return 0;
      }

      if (var1 != this.worldChunkProvider) {
         this.worldChunkProvider = var1;
         ChunkProviderClient var2 = (ChunkProviderClient)this.worldChunkProvider;
         this.worldChunkProviderMap = var2.chunkMapping;
      }

      return this.worldChunkProviderMap == null ? 0 : this.worldChunkProviderMap.getNumHashElements();
   }

   public int getCountChunksToUpdate() {
      return this.chunksToUpdate.size();
   }

   public RenderChunk getRenderChunk(BlockPos blockPos) {
      return this.viewFrustum.getRenderChunk(blockPos);
   }

   public WorldClient getWorld() {
      return this.theWorld;
   }

   private void clearRenderInfos() {
      if (renderEntitiesCounter > 0) {
         this.renderInfos = new ArrayList<>(this.renderInfos.size() + 16);
         this.at = new ArrayList<>(this.at.size() + 16);
         this.renderInfosTileEntities = new ArrayList(this.renderInfosTileEntities.size() + 16);
      } else {
         this.renderInfos.clear();
         this.at.clear();
         this.renderInfosTileEntities.clear();
      }
   }

   public void onPlayerPositionSet() {
      if (this.firstWorldLoad) {
         this.loadRenderers();
         this.firstWorldLoad = false;
      }
   }

   public void pauseChunkUpdates() {
      if (this.renderDispatcher != null) {
         this.renderDispatcher.pauseChunkUpdates();
      }
   }

   public void resumeChunkUpdates() {
      if (this.renderDispatcher != null) {
         this.renderDispatcher.resumeChunkUpdates();
      }
   }

   public void updateTileEntities(Collection<TileEntity> collection1, Collection<TileEntity> collection2) {
      synchronized (this.setTileEntities) {
         this.setTileEntities.removeAll(collection1);
         this.setTileEntities.addAll(collection2);
      }
   }

   public static class ContainerLocalRenderInformation {
      final RenderChunk renderChunk;
      EnumFacing facing;
      int setFacing;

      public ContainerLocalRenderInformation(RenderChunk renderChunk, EnumFacing enumFacing, int integer) {
         this.renderChunk = renderChunk;
         this.facing = enumFacing;
         this.setFacing = integer;
      }

      public void setFacingBit(byte byte1, EnumFacing enumFacing) {
         this.setFacing = this.setFacing | byte1 | 1 << enumFacing.ordinal();
      }

      public boolean isFacingBit(EnumFacing enumFacing) {
         return (this.setFacing & 1 << enumFacing.ordinal()) > 0;
      }

      private void initialize(EnumFacing enumFacing, int integer) {
         this.facing = enumFacing;
         this.setFacing = integer;
      }
   }
}
