package net.minecraft.client.renderer.entity;

import com.moon.Moon;
import com.moon.events.impl.render.RenderEntityEvent;
import com.moon.events.impl.render.RenderEntityShadowEvent;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.Config;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public abstract class Render<T extends Entity> implements IEntityRenderer {
   private static final ResourceLocation shadowTextures = new ResourceLocation("textures/misc/shadow.png");
   protected final RenderManager renderManager;
   public float shadowSize;
   protected float shadowOpaque = 1.0F;
   private Class entityClass = null;
   private ResourceLocation locationTextureCustom = null;

   protected Render(RenderManager renderManager) {
      this.renderManager = renderManager;
   }

   public static void renderOffsetAABB(AxisAlignedBB axisAlignedBB, double double2, double double3, double double4) {
      GlStateManager.disableTexture2D();
      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      var10.setTranslation(double2, double3, double4);
      var10.begin(7, DefaultVertexFormats.POSITION_NORMAL);
      var10.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.minX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.maxY, axisAlignedBB.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
      var10.pos(axisAlignedBB.maxX, axisAlignedBB.minY, axisAlignedBB.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
      var9.draw();
      var10.setTranslation(0.0, 0.0, 0.0);
      GlStateManager.enableTexture2D();
   }

   public static void setModelBipedMain(RenderBiped renderBiped, ModelBiped modelBiped) {
      renderBiped.modelBipedMain = modelBiped;
   }

   public boolean shouldRender(T entity, ICamera iCamera, double double3, double double4, double double5) {
      AxisAlignedBB var9 = entity.getEntityBoundingBox();
      if (var9.hasNaN() || var9.getAverageEdgeLength() == 0.0) {
         var9 = new AxisAlignedBB(entity.posX - 2.0, entity.posY - 2.0, entity.posZ - 2.0, entity.posX + 2.0, entity.posY + 2.0, entity.posZ + 2.0);
      }

      return entity.isInRangeToRender3d(double3, double4, double5) && (entity.ignoreFrustumCheck || iCamera.isBoundingBoxInFrustum(var9));
   }

   public void doRender(T entity, double double2, double double3, double double4, float float5, float float6) {
      this.renderName((T)entity, double2, double3, double4);
   }

   protected void renderName(T entity, double double2, double double3, double double4) {
      if (this.canRenderName((T)entity)) {
         this.renderLivingLabel((T)entity, entity.getDisplayName().getFormattedText(), double2, double3, double4, 64);
      }
   }

   protected boolean canRenderName(T entity) {
      return entity.getAlwaysRenderNameTagForRender() && entity.hasCustomName();
   }

   protected void renderOffsetLivingLabel(T entity, double double2, double double3, double double4, String string, float float6, double double7) {
      this.renderLivingLabel((T)entity, string, double2, double3, double4, 64);
   }

   protected abstract ResourceLocation getEntityTexture(T entity);

   protected boolean bindEntityTexture(T entity) {
      ResourceLocation var2 = this.getEntityTexture((T)entity);
      if (this.locationTextureCustom != null) {
         var2 = this.locationTextureCustom;
      }

      if (var2 == null) {
         return false;
      }

      this.bindTexture(var2);
      return true;
   }

   public void bindTexture(ResourceLocation resourceLocation) {
      this.renderManager.renderEngine.bindTexture(resourceLocation);
   }

   private void renderEntityOnFire(Entity entity, double double2, double double3, double double4, float float5) {
      GlStateManager.disableLighting();
      TextureMap var11 = Minecraft.getMinecraft().getTextureMapBlocks();
      TextureAtlasSprite var12 = var11.getAtlasSprite("minecraft:blocks/fire_layer_0");
      TextureAtlasSprite var13 = var11.getAtlasSprite("minecraft:blocks/fire_layer_1");
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
      float var14 = entity.width;
      if (entity instanceof EntityPlayer) {
         RenderEntityEvent var15 = new RenderEntityEvent(entity, false, false);
         Moon.getInstance().getEventBus().publish(var15);
         if (var15.isChild()) {
            var14 *= 0.5F;
         }
      }

      float var31 = var14 * 1.4F;
      GlStateManager.scale(var31, var31, var31);
      Tessellator var16 = Tessellator.getInstance();
      WorldRenderer var17 = var16.getWorldRenderer();
      float var18 = 0.5F;
      float var20 = entity.height / var31;
      float var21 = (float)(entity.posY - entity.getEntityBoundingBox().minY);
      GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.translate(0.0F, 0.0F, -0.3F + (int)var20 * 0.02F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      float var22 = 0.0F;
      int var23 = 0;
      boolean var24 = Config.isMultiTexture();
      if (var24) {
         var17.setBlockLayer(EnumWorldBlockLayer.SOLID);
      }

      var17.begin(7, DefaultVertexFormats.POSITION_TEX);

      while (var20 > 0.0F) {
         TextureAtlasSprite var25 = var23 % 2 == 0 ? var12 : var13;
         var17.setSprite(var25);
         this.bindTexture(TextureMap.locationBlocksTexture);
         float var26 = var25.getMinU();
         float var27 = var25.getMinV();
         float var28 = var25.getMaxU();
         float var29 = var25.getMaxV();
         if (var23 / 2 % 2 == 0) {
            float var30 = var28;
            var28 = var26;
            var26 = var30;
         }

         var17.pos(var18 - 0.0F, 0.0F - var21, var22).tex(var28, var29).endVertex();
         var17.pos(-var18 - 0.0F, 0.0F - var21, var22).tex(var26, var29).endVertex();
         var17.pos(-var18 - 0.0F, 1.4F - var21, var22).tex(var26, var27).endVertex();
         var17.pos(var18 - 0.0F, 1.4F - var21, var22).tex(var28, var27).endVertex();
         var20 -= 0.45F;
         var21 -= 0.45F;
         var18 *= 0.9F;
         var22 += 0.03F;
         var23++;
      }

      var16.draw();
      if (var24) {
         var17.setBlockLayer(null);
         GlStateManager.bindCurrentTexture();
      }

      GlStateManager.popMatrix();
      GlStateManager.enableLighting();
   }

   private void renderShadow(Entity entity, double double2, double double3, double double4, float float5, float float6) {
      RenderEntityShadowEvent var12 = new RenderEntityShadowEvent(entity);
      Moon.getInstance().getEventBus().publish(var12);
      if (!var12.isCancelled()) {
         if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            this.renderManager.renderEngine.bindTexture(shadowTextures);
            World var13 = this.getWorldFromRenderManager();
            GlStateManager.depthMask(false);
            float var14 = this.shadowSize;
            if (entity instanceof EntityLiving var15) {
               var14 *= var15.getRenderSizeModifier();
               if (var15.isChild()) {
                  var14 *= 0.5F;
               }
            }

            double var38 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * float6;
            double var17 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * float6;
            double var19 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * float6;
            int var21 = MathHelper.floor_double(var38 - var14);
            int var22 = MathHelper.floor_double(var38 + var14);
            int var23 = MathHelper.floor_double(var17 - var14);
            int var24 = MathHelper.floor_double(var17);
            int var25 = MathHelper.floor_double(var19 - var14);
            int var26 = MathHelper.floor_double(var19 + var14);
            double var27 = double2 - var38;
            double var29 = double3 - var17;
            double var31 = double4 - var19;
            Tessellator var33 = Tessellator.getInstance();
            var33.getWorldRenderer().begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

            for (BlockPos var36 : BlockPos.getAllInBoxMutable(new BlockPos(var21, var23, var25), new BlockPos(var22, var24, var26))) {
               Block var37 = var13.getBlockState(var36.down()).getBlock();
               if (var37.getRenderType() != -1 && var13.getLightFromNeighbors(var36) > 3) {
                  this.renderShadowBlock(var37, double2, double3, double4, var36, float5, var14, var27, var29, var31);
               }
            }

            var33.draw();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableBlend();
            GlStateManager.depthMask(true);
         }
      }
   }

   private World getWorldFromRenderManager() {
      return this.renderManager.worldObj;
   }

   private void renderShadowBlock(
      Block block,
      double double2,
      double double3,
      double double4,
      BlockPos blockPos,
      float float6,
      float float7,
      double double8,
      double double9,
      double double10
   ) {
      if (block.isFullCube()) {
         WorldRenderer var18 = Tessellator.getInstance().getWorldRenderer();
         double var19 = (float6 - (double3 - (blockPos.getY() + double9)) / 2.0) * 0.5 * this.getWorldFromRenderManager().getLightBrightness(blockPos);
         if (var19 >= 0.0) {
            if (var19 > 1.0) {
               var19 = 1.0;
            }

            double var21 = blockPos.getX() + block.getBlockBoundsMinX() + double8;
            double var23 = blockPos.getX() + block.getBlockBoundsMaxX() + double8;
            double var25 = blockPos.getY() + block.getBlockBoundsMinY() + double9 + 0.015625;
            double var27 = blockPos.getZ() + block.getBlockBoundsMinZ() + double10;
            double var29 = blockPos.getZ() + block.getBlockBoundsMaxZ() + double10;
            float var31 = (float)((double2 - var21) / 2.0 / float7 + 0.5);
            float var32 = (float)((double2 - var23) / 2.0 / float7 + 0.5);
            float var33 = (float)((double4 - var27) / 2.0 / float7 + 0.5);
            float var34 = (float)((double4 - var29) / 2.0 / float7 + 0.5);
            var18.pos(var21, var25, var27).tex(var31, var33).color(1.0F, 1.0F, 1.0F, (float)var19).endVertex();
            var18.pos(var21, var25, var29).tex(var31, var34).color(1.0F, 1.0F, 1.0F, (float)var19).endVertex();
            var18.pos(var23, var25, var29).tex(var32, var34).color(1.0F, 1.0F, 1.0F, (float)var19).endVertex();
            var18.pos(var23, var25, var27).tex(var32, var33).color(1.0F, 1.0F, 1.0F, (float)var19).endVertex();
         }
      }
   }

   public void doRenderShadowAndFire(Entity entity, double double2, double double3, double double4, float float5, float float6) {
      if (this.renderManager.options != null) {
         if (this.renderManager.options.entityShadows && this.shadowSize > 0.0F && !entity.isInvisible() && this.renderManager.isRenderShadow()) {
            double var10 = this.renderManager.getDistanceToCamera(entity.posX, entity.posY, entity.posZ);
            float var12 = (float)((1.0 - var10 / 256.0) * this.shadowOpaque);
            if (var12 > 0.0F) {
               this.renderShadow(entity, double2, double3, double4, var12, float6);
            }
         }

         if (entity.canRenderOnFire() && (!(entity instanceof EntityPlayer) || !((EntityPlayer)entity).isSpectator())) {
            this.renderEntityOnFire(entity, double2, double3, double4, float6);
         }
      }
   }

   public FontRenderer getFontRendererFromRenderManager() {
      return this.renderManager.getFontRenderer();
   }

   protected void renderLivingLabel(T entity, String string, double double3, double double4, double double5, int integer) {
      if (entity.getDistanceSqToEntity(this.renderManager.livingPlayer) <= integer * integer) {
         FontRenderer var14 = this.getFontRendererFromRenderManager();
         GlStateManager.pushMatrix();
         GlStateManager.translate((float)double3 + 0.0F, (float)double4 + entity.height + 0.5F, (float)double5);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
         GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
         GlStateManager.disableLighting();
         GlStateManager.depthMask(false);
         GlStateManager.disableDepth();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         Tessellator var17 = Tessellator.getInstance();
         WorldRenderer var18 = var17.getWorldRenderer();
         byte var19 = 0;
         if (string.equals("deadmau5")) {
            var19 = -10;
         }

         int var20 = var14.getStringWidth(string) / 2;
         GlStateManager.disableTexture2D();
         var18.begin(7, DefaultVertexFormats.POSITION_COLOR);
         var18.pos(-var20 - 1, -1 + var19, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
         var18.pos(-var20 - 1, 8 + var19, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
         var18.pos(var20 + 1, 8 + var19, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
         var18.pos(var20 + 1, -1 + var19, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
         var17.draw();
         GlStateManager.enableTexture2D();
         var14.drawString(string, -var14.getStringWidth(string) / 2, var19, 553648127);
         GlStateManager.enableDepth();
         GlStateManager.depthMask(true);
         var14.drawString(string, -var14.getStringWidth(string) / 2, var19, -1);
         GlStateManager.enableLighting();
         GlStateManager.disableBlend();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
      }
   }

   public RenderManager getRenderManager() {
      return this.renderManager;
   }

   public boolean isMultipass() {
      return false;
   }

   public void renderMultipass(T entity, double double2, double double3, double double4, float float5, float float6) {
   }

   @Override
   public Class getEntityClass() {
      return this.entityClass;
   }

   @Override
   public void setEntityClass(Class class1) {
      this.entityClass = class1;
   }

   @Override
   public ResourceLocation getLocationTextureCustom() {
      return this.locationTextureCustom;
   }

   @Override
   public void setLocationTextureCustom(ResourceLocation resourceLocation) {
      this.locationTextureCustom = resourceLocation;
   }
}
