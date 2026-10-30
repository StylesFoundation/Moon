package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class RenderItemFrame extends Render<EntityItemFrame> {
   private static final ResourceLocation mapBackgroundTextures = new ResourceLocation("textures/map/map_background.png");
   private final Minecraft mc = Minecraft.getMinecraft();
   private final ModelResourceLocation itemFrameModel = new ModelResourceLocation("item_frame", "normal");
   private final ModelResourceLocation mapModel = new ModelResourceLocation("item_frame", "map");
   private final RenderItem itemRenderer;
   private static double itemRenderDistanceSq = 4096.0;

   public RenderItemFrame(RenderManager renderManager, RenderItem renderItem) {
      super(renderManager);
      this.itemRenderer = renderItem;
   }

   public void doRender(EntityItemFrame entityItemFrame, double double2, double double3, double double4, float float5, float float6) {
      if (!entityItemFrame.aC) {
         GlStateManager.pushMatrix();
         BlockPos var10 = entityItemFrame.getHangingPosition();
         double var11 = var10.getX() - entityItemFrame.posX + double2;
         double var13 = var10.getY() - entityItemFrame.posY + double3;
         double var15 = var10.getZ() - entityItemFrame.posZ + double4;
         GlStateManager.translate(var11 + 0.5, var13 + 0.5, var15 + 0.5);
         GlStateManager.rotate(180.0F - entityItemFrame.rotationYaw, 0.0F, 1.0F, 0.0F);
         this.renderManager.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
         BlockRendererDispatcher var17 = this.mc.getBlockRendererDispatcher();
         ModelManager var18 = var17.getBlockModelShapes().getModelManager();
         IBakedModel var19;
         if (entityItemFrame.getDisplayedItem() != null && entityItemFrame.getDisplayedItem().getItem() == Items.filled_map) {
            var19 = var18.getModel(this.mapModel);
         } else {
            var19 = var18.getModel(this.itemFrameModel);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(-0.5F, -0.5F, -0.5F);
         var17.getBlockModelRenderer().renderModelBrightnessColor(var19, 1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.popMatrix();
         GlStateManager.translate(0.0F, 0.0F, 0.4375F);
         this.renderItem(entityItemFrame);
         GlStateManager.popMatrix();
         this.renderName(
            entityItemFrame,
            double2 + entityItemFrame.facingDirection.getFrontOffsetX() * 0.3F,
            double3 - 0.25,
            double4 + entityItemFrame.facingDirection.getFrontOffsetZ() * 0.3F
         );
      }
   }

   protected ResourceLocation getEntityTexture(EntityItemFrame entityItemFrame) {
      return null;
   }

   private void renderItem(EntityItemFrame entityItemFrame) {
      ItemStack var4 = entityItemFrame.getDisplayedItem();
      if (var4 != null) {
         if (!this.isRenderItem(entityItemFrame)) {
            return;
         }

         if (!Config.zoomMode) {
            EntityPlayerSP var5 = this.mc.thePlayer;
            double var6 = entityItemFrame.getDistanceSq(var5.posX, var5.posY, var5.posZ);
            if (var6 > 4096.0) {
               return;
            }
         }

         EntityItem var14 = new EntityItem(entityItemFrame.worldObj, 0.0, 0.0, 0.0, var4);
         Item var15 = var14.Method464().getItem();
         var14.Method464().stackSize = 1;
         var14.hoverStart = 0.0F;
         GlStateManager.pushMatrix();
         GlStateManager.disableLighting();
         int var7 = entityItemFrame.getRotation();
         if (var15 instanceof ItemMap) {
            var7 = var7 % 4 * 2;
         }

         GlStateManager.rotate(var7 * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
         if (var15 instanceof ItemMap) {
            this.renderManager.renderEngine.bindTexture(mapBackgroundTextures);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.scale(0.0078125F, 0.0078125F, 0.0078125F);
            GlStateManager.translate(-64.0F, -64.0F, 0.0F);
            MapData var16 = Items.filled_map.getMapData(var14.Method464(), entityItemFrame.worldObj);
            GlStateManager.translate(0.0F, 0.0F, -1.0F);
            if (var16 != null) {
               this.mc.entityRenderer.getMapItemRenderer().renderMap(var16, true);
            }
         } else {
            TextureAtlasSprite var8 = null;
            if (var15 == Items.compass) {
               var8 = this.mc.getTextureMapBlocks().getAtlasSprite(TextureCompass.locationSprite);
               this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
               if (var8 instanceof TextureCompass var9) {
                  double var10 = var9.currentAngle;
                  double var12 = var9.angleDelta;
                  var9.currentAngle = 0.0;
                  var9.angleDelta = 0.0;
                  var9.updateCompass(
                     entityItemFrame.worldObj,
                     entityItemFrame.posX,
                     entityItemFrame.posZ,
                     MathHelper.wrapAngleTo180_float(180 + entityItemFrame.facingDirection.getHorizontalIndex() * 90),
                     false,
                     true
                  );
                  var9.currentAngle = var10;
                  var9.angleDelta = var12;
               } else {
                  var8 = null;
               }
            }

            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            if (!this.itemRenderer.shouldRenderItemIn3D(var14.Method464()) || var15 instanceof ItemSkull) {
               GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.pushAttrib();
            RenderHelper.enableStandardItemLighting();
            this.itemRenderer.renderItem(var14.Method464(), ItemCameraTransforms.TransformType.FIXED);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.popAttrib();
            if (var8 != null && var8.getFrameCount() > 0) {
               var8.updateAnimation();
            }
         }

         GlStateManager.enableLighting();
         GlStateManager.popMatrix();
      }
   }

   protected void renderName(EntityItemFrame entityItemFrame, double double2, double double3, double double4) {
      if (Minecraft.isGuiEnabled()
         && entityItemFrame.getDisplayedItem() != null
         && entityItemFrame.getDisplayedItem().hasDisplayName()
         && this.renderManager.pointedEntity == entityItemFrame) {
         double var12 = entityItemFrame.getDistanceSqToEntity(this.renderManager.livingPlayer);
         float var14 = entityItemFrame.isSneaking() ? 32.0F : 64.0F;
         if (var12 < var14 * var14) {
            String var15 = entityItemFrame.getDisplayedItem().getDisplayName();
            if (entityItemFrame.isSneaking()) {
               FontRenderer var16 = this.getFontRendererFromRenderManager();
               GlStateManager.pushMatrix();
               GlStateManager.translate((float)double2 + 0.0F, (float)double3 + entityItemFrame.height + 0.5F, (float)double4);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
               GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
               GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
               GlStateManager.disableLighting();
               GlStateManager.translate(0.0F, 9.374999F, 0.0F);
               GlStateManager.depthMask(false);
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(770, 771);
               Tessellator var17 = Tessellator.getInstance();
               WorldRenderer var18 = var17.getWorldRenderer();
               int var19 = var16.getStringWidth(var15) / 2;
               GlStateManager.disableTexture2D();
               var18.begin(7, DefaultVertexFormats.POSITION_COLOR);
               var18.pos(-var19 - 1, -1.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
               var18.pos(-var19 - 1, 8.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
               var18.pos(var19 + 1, 8.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
               var18.pos(var19 + 1, -1.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
               var17.draw();
               GlStateManager.enableTexture2D();
               GlStateManager.depthMask(true);
               var16.drawString(var15, -var16.getStringWidth(var15) / 2, 0.0F, 553648127);
               GlStateManager.enableLighting();
               GlStateManager.disableBlend();
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.popMatrix();
            } else {
               this.renderLivingLabel(entityItemFrame, var15, double2, double3, double4, 64);
            }
         }
      }
   }

   private boolean isRenderItem(EntityItemFrame entityItemFrame) {
      if (Shaders.isShadowPass) {
         return false;
      } else if (!Config.zoomMode) {
         Entity var2 = this.mc.getRenderViewEntity();
         double var3 = entityItemFrame.getDistanceSq(var2.posX, var2.posY, var2.posZ);
         return !(var3 > itemRenderDistanceSq);
      } else {
         return true;
      }
   }

   public static void updateItemRenderDistance() {
      Minecraft var0 = Config.getMinecraft();
      double var1 = Config.Method256(var0.gameSettings.fovSetting, 1.0F, 120.0F);
      double var3 = Math.max(6.0 * var0.displayHeight / var1, 16.0);
      itemRenderDistanceSq = var3 * var3;
   }
}
