package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Maps;
import com.moon.Moon;
import com.moon.events.impl.render.TileEntityRenderEvent;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.optifine.EmissiveTextures;

public class TileEntityRendererDispatcher {
   public Map<Class, TileEntitySpecialRenderer> mapSpecialRenderers = Maps.newHashMap();
   public static TileEntityRendererDispatcher instance = new TileEntityRendererDispatcher();
   public FontRenderer fontRenderer;
   public static double staticPlayerX;
   public static double staticPlayerY;
   public static double staticPlayerZ;
   public TextureManager renderEngine;
   public World worldObj;
   public Entity entity;
   public float entityYaw;
   public float entityPitch;
   public double entityX;
   public double entityY;
   public double entityZ;
   public TileEntity tileEntityRendered;
   private final Tessellator batchBuffer = new Tessellator(2097152);
   private boolean drawingBatch = false;

   private TileEntityRendererDispatcher() {
      this.mapSpecialRenderers.put(TileEntitySign.class, new TileEntitySignRenderer());
      this.mapSpecialRenderers.put(TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
      this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
      this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
      this.mapSpecialRenderers.put(TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
      this.mapSpecialRenderers.put(TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
      this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
      this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
      this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
      this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());
      Iterator var3 = this.mapSpecialRenderers.values().iterator();

      while (var3.hasNext()) {
         ((TileEntitySpecialRenderer)var3.next()).setRendererDispatcher(this);
      }
   }

   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRendererByClass(Class<? extends TileEntity> class1) {
      TileEntitySpecialRenderer var2 = this.mapSpecialRenderers.get(class1);
      if (var2 == null && class1 != TileEntity.class) {
         var2 = this.getSpecialRendererByClass((Class<? extends TileEntity>)class1.getSuperclass());
         this.mapSpecialRenderers.put(class1, var2);
      }

      return (TileEntitySpecialRenderer<T>)var2;
   }

   public <T extends TileEntity> TileEntitySpecialRenderer<T> getSpecialRenderer(TileEntity tileEntity) {
      return tileEntity != null && !tileEntity.isInvalid() ? this.getSpecialRendererByClass((Class<? extends TileEntity>)tileEntity.getClass()) : null;
   }

   public void cacheActiveRenderInfo(World world, TextureManager textureManager, FontRenderer fontRenderer, Entity entity, float float5) {
      if (this.worldObj != world) {
         this.setWorld(world);
      }

      this.renderEngine = textureManager;
      this.entity = entity;
      this.fontRenderer = fontRenderer;
      this.entityYaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * float5;
      this.entityPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * float5;
      this.entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * float5;
      this.entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * float5;
      this.entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * float5;
   }

   public void renderTileEntity(TileEntity tileEntity, float float2, int integer) {
      if (tileEntity.getDistanceSq(this.entityX, this.entityY, this.entityZ) < tileEntity.getMaxRenderDistanceSquared()) {
         if (true) {
            RenderHelper.enableStandardItemLighting();
            int var7 = this.worldObj.getCombinedLight(tileEntity.getPos(), 0);
            int var8 = var7 % 65536;
            int var9 = var7 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8, var9);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         }

         BlockPos var10 = tileEntity.getPos();
         if (!this.worldObj.Method323(var10, false)) {
            return;
         }

         if (EmissiveTextures.isActive()) {
            EmissiveTextures.beginRender();
         }

         this.renderTileEntityAt(tileEntity, var10.getX() - staticPlayerX, var10.getY() - staticPlayerY, var10.getZ() - staticPlayerZ, float2, integer);
         if (EmissiveTextures.isActive()) {
            if (EmissiveTextures.hasEmissive()) {
               EmissiveTextures.beginRenderEmissive();
               this.renderTileEntityAt(tileEntity, var10.getX() - staticPlayerX, var10.getY() - staticPlayerY, var10.getZ() - staticPlayerZ, float2, integer);
               EmissiveTextures.endRenderEmissive();
            }

            EmissiveTextures.endRender();
         }
      }
   }

   public void renderTileEntityAt(TileEntity tileEntity, double double2, double double3, double double4, float float5) {
      this.renderTileEntityAt(tileEntity, double2, double3, double4, float5, -1);
   }

   public void renderTileEntityAt(TileEntity tileEntity, double double2, double double3, double double4, float float5, int integer) {
      TileEntityRenderEvent var10 = new TileEntityRenderEvent(tileEntity);
      Moon.getInstance().getEventBus().publish(var10);
      if (!var10.isCancelled()) {
         TileEntitySpecialRenderer var11 = this.getSpecialRenderer(tileEntity);
         if (var11 != null) {
            try {
               this.tileEntityRendered = tileEntity;
               if (this.drawingBatch) {
                  var11.renderTileEntityFast(tileEntity, double2, double3, double4, float5, integer, this.batchBuffer.getWorldRenderer());
               } else {
                  var11.renderTileEntityAt(tileEntity, double2, double3, double4, float5, integer);
               }

               this.tileEntityRendered = null;
            } catch (Throwable var15) {
               CrashReport var13 = CrashReport.makeCrashReport(var15, "Rendering Block Entity");
               CrashReportCategory var14 = var13.makeCategory("Block Entity Details");
               tileEntity.addInfoToCrashReport(var14);
               throw new ReportedException(var13);
            }
         }
      }
   }

   public void setWorld(World world) {
      this.worldObj = world;
   }

   public FontRenderer getFontRenderer() {
      return this.fontRenderer;
   }

   public void preDrawBatch() {
      this.batchBuffer.getWorldRenderer().begin(7, DefaultVertexFormats.BLOCK);
      this.drawingBatch = true;
   }

   public void drawBatch(int integer) {
      this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
      RenderHelper.disableStandardItemLighting();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.enableBlend();
      GlStateManager.disableCull();
      if (Minecraft.isAmbientOcclusionEnabled()) {
         GlStateManager.shadeModel(7425);
      } else {
         GlStateManager.shadeModel(7424);
      }

      if (integer > 0) {
         this.batchBuffer.getWorldRenderer().sortVertexData((float)staticPlayerX, (float)staticPlayerY, (float)staticPlayerZ);
      }

      this.batchBuffer.draw();
      RenderHelper.enableStandardItemLighting();
      this.drawingBatch = false;
   }
}
