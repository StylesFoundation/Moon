package net.minecraft.client.renderer;

import com.moon.Moon;
import com.moon.events.impl.render.FirstPersonItemRenderEvent;
import com.moon.module.impl.visual.Animations;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.optifine.DynamicLights;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
   private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
   private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");
   private final Minecraft mc;
   private ItemStack itemToRender;
   private float equippedProgress;
   private float prevEquippedProgress;
   private final RenderManager renderManager;
   private final RenderItem itemRenderer;
   private int i = -1;
   private Animations j;

   public ItemRenderer(Minecraft minecraft) {
      this.mc = minecraft;
      this.renderManager = minecraft.getRenderManager();
      this.itemRenderer = minecraft.getRenderItem();
   }

   public void Method807(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
      if (itemStack != null) {
         Block var5 = Block.getBlockFromItem(itemStack.getItem());
         GlStateManager.pushMatrix();
         if (this.itemRenderer.shouldRenderItemIn3D(itemStack)) {
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            if (this.isBlockTranslucent(var5) && (!Config.isShaders() || !Shaders.renderItemKeepDepthMask)) {
               GlStateManager.depthMask(false);
            }
         } else {
            if (this.j == null) {
               this.j = Moon.getInstance().getModuleManager().getModule(Animations.class);
            }

            if (this.j.isEnabled() && transformType == ItemCameraTransforms.TransformType.FIRST_PERSON) {
               float var6 = this.j.getScale().getValue();
               GlStateManager.scale(var6, var6, var6);
            }
         }

         this.itemRenderer.renderItemModelForEntity(itemStack, entityLivingBase, transformType);
         if (this.isBlockTranslucent(var5)) {
            GlStateManager.depthMask(true);
         }

         GlStateManager.popMatrix();
      }
   }

   private boolean isBlockTranslucent(Block block) {
      return block != null && block.getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT;
   }

   private void rotateArroundXAndY(float float1, float float2) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(float1, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(float2, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.popMatrix();
   }

   private void setLightMapFromPlayer(AbstractClientPlayer abstractClientPlayer) {
      int var4 = this.mc
         .theWorld
         .getCombinedLight(
            new BlockPos(abstractClientPlayer.posX, abstractClientPlayer.posY + abstractClientPlayer.getEyeHeight(), abstractClientPlayer.posZ), 0
         );
      if (Config.isDynamicLights()) {
         var4 = DynamicLights.getCombinedLight(this.mc.getRenderViewEntity(), var4);
      }

      float var5 = var4 & 65535;
      float var6 = var4 >> 16;
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var5, var6);
   }

   private void rotateWithPlayerRotations(EntityPlayerSP entityPlayerSP, float float2) {
      float var3 = entityPlayerSP.prevRenderArmPitch + (entityPlayerSP.renderArmPitch - entityPlayerSP.prevRenderArmPitch) * float2;
      float var4 = entityPlayerSP.prevRenderArmYaw + (entityPlayerSP.renderArmYaw - entityPlayerSP.prevRenderArmYaw) * float2;
      GlStateManager.rotate((entityPlayerSP.rotationPitch - var3) * 0.1F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate((entityPlayerSP.rotationYaw - var4) * 0.1F, 0.0F, 1.0F, 0.0F);
   }

   private float getMapAngleFromPitch(float float1) {
      return -MathHelper.cos(MathHelper.clamp_float(1.0F - float1 / 45.0F + 0.1F, 0.0F, 1.0F) * (float) Math.PI) * 0.5F + 0.5F;
   }

   private void renderRightArm(RenderPlayer renderPlayer) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(54.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(64.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-62.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(0.25F, -0.85F, 0.75F);
      renderPlayer.renderRightArm(this.mc.thePlayer);
      GlStateManager.popMatrix();
   }

   private void renderLeftArm(RenderPlayer renderPlayer) {
      GlStateManager.pushMatrix();
      GlStateManager.rotate(92.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(41.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.translate(-0.3F, -1.1F, 0.45F);
      renderPlayer.renderLeftArm(this.mc.thePlayer);
      GlStateManager.popMatrix();
   }

   private void renderPlayerArms(AbstractClientPlayer abstractClientPlayer) {
      this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
      RenderPlayer var3 = (RenderPlayer)this.renderManager.<AbstractClientPlayer>getEntityRenderObject(this.mc.thePlayer);
      if (!abstractClientPlayer.isInvisible()) {
         GlStateManager.disableCull();
         this.renderRightArm(var3);
         this.renderLeftArm(var3);
         GlStateManager.enableCull();
      }
   }

   private void renderItemMap(AbstractClientPlayer abstractClientPlayer, float float2, float float3, float float4) {
      float var7 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(float4) * (float) Math.PI);
      float var8 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(float4) * (float) Math.PI * 2.0F);
      float var9 = -0.2F * MathHelper.sin(float4 * (float) Math.PI);
      GlStateManager.translate(var7, var8, var9);
      float var10 = this.getMapAngleFromPitch(float2);
      GlStateManager.translate(0.0F, 0.04F, -0.72F);
      GlStateManager.translate(0.0F, float3 * -1.2F, 0.0F);
      GlStateManager.translate(0.0F, var10 * -0.5F, 0.0F);
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var10 * -85.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
      this.renderPlayerArms(abstractClientPlayer);
      float var11 = MathHelper.sin(float4 * float4 * (float) Math.PI);
      float var12 = MathHelper.sin(MathHelper.sqrt_float(float4) * (float) Math.PI);
      GlStateManager.rotate(var11 * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var12 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(var12 * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.38F, 0.38F, 0.38F);
      GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(-1.0F, -1.0F, 0.0F);
      GlStateManager.scale(0.015625F, 0.015625F, 0.015625F);
      this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
      Tessellator var13 = Tessellator.getInstance();
      WorldRenderer var14 = var13.getWorldRenderer();
      GL11.glNormal3f(0.0F, 0.0F, -1.0F);
      var14.begin(7, DefaultVertexFormats.POSITION_TEX);
      var14.pos(-7.0, 135.0, 0.0).tex(0.0, 1.0).endVertex();
      var14.pos(135.0, 135.0, 0.0).tex(1.0, 1.0).endVertex();
      var14.pos(135.0, -7.0, 0.0).tex(1.0, 0.0).endVertex();
      var14.pos(-7.0, -7.0, 0.0).tex(0.0, 0.0).endVertex();
      var13.draw();
      MapData var15 = Items.filled_map.getMapData(this.itemToRender, this.mc.theWorld);
      if (var15 != null) {
         this.mc.entityRenderer.getMapItemRenderer().renderMap(var15, false);
      }
   }

   private void renderPlayerArm(AbstractClientPlayer abstractClientPlayer, float float2, float float3) {
      float var4 = -0.3F * MathHelper.sin(MathHelper.sqrt_float(float3) * (float) Math.PI);
      float var5 = 0.4F * MathHelper.sin(MathHelper.sqrt_float(float3) * (float) Math.PI * 2.0F);
      float var6 = -0.4F * MathHelper.sin(float3 * (float) Math.PI);
      GlStateManager.translate(var4, var5, var6);
      GlStateManager.translate(0.64000005F, -0.6F, -0.71999997F);
      GlStateManager.translate(0.0F, float2 * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      float var7 = MathHelper.sin(float3 * float3 * (float) Math.PI);
      GlStateManager.rotate(MathHelper.sin(MathHelper.sqrt_float(float3) * (float) Math.PI) * 70.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var7 * -20.0F, 0.0F, 0.0F, 1.0F);
      this.mc.getTextureManager().bindTexture(abstractClientPlayer.getLocationSkin());
      GlStateManager.translate(-1.0F, 3.6F, 3.5F);
      GlStateManager.rotate(120.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(200.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(1.0F, 1.0F, 1.0F);
      GlStateManager.translate(5.6F, 0.0F, 0.0F);
      Render var9 = this.renderManager.getEntityRenderObject(this.mc.thePlayer);
      GlStateManager.disableCull();
      ((RenderPlayer)var9).renderRightArm(this.mc.thePlayer);
      GlStateManager.enableCull();
   }

   private void doItemUsedTransformations(float float1) {
      float var2 = -0.4F * MathHelper.sin(MathHelper.sqrt_float(float1) * (float) Math.PI);
      float var3 = 0.2F * MathHelper.sin(MathHelper.sqrt_float(float1) * (float) Math.PI * 2.0F);
      float var4 = -0.2F * MathHelper.sin(float1 * (float) Math.PI);
      GlStateManager.translate(var2, var3, var4);
   }

   private void performDrinking(AbstractClientPlayer abstractClientPlayer, float float2) {
      float var3 = abstractClientPlayer.getItemInUseCount() - float2 + 1.0F;
      float var4 = var3 / this.itemToRender.getMaxItemUseDuration();
      float var5 = MathHelper.abs(MathHelper.cos(var3 / 4.0F * (float) Math.PI) * 0.1F);
      if (var4 >= 0.8F) {
         var5 = 0.0F;
      }

      GlStateManager.translate(0.0F, var5, 0.0F);
      float var6 = 1.0F - (float)Math.pow(var4, 27.0);
      GlStateManager.translate(var6 * 0.6F, var6 * -0.5F, var6 * 0.0F);
      GlStateManager.rotate(var6 * 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(var6 * 30.0F, 0.0F, 0.0F, 1.0F);
   }

   private void transformFirstPersonItem(float float1, float float2) {
      GlStateManager.translate(0.56F, -0.52F, -0.71999997F);
      GlStateManager.translate(0.0F, float1 * -0.6F, 0.0F);
      GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
      float var3 = MathHelper.sin(float2 * float2 * (float) Math.PI);
      float var4 = MathHelper.sin(MathHelper.sqrt_float(float2) * (float) Math.PI);
      GlStateManager.rotate(var3 * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(0.4F, 0.4F, 0.4F);
   }

   private void doBowTransformations(float float1, AbstractClientPlayer abstractClientPlayer) {
      GlStateManager.rotate(-18.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.rotate(-12.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-8.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.translate(-0.9F, 0.2F, 0.0F);
      float var3 = this.itemToRender.getMaxItemUseDuration() - (abstractClientPlayer.getItemInUseCount() - float1 + 1.0F);
      float var4 = var3 / 20.0F;
      var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;
      if (var4 > 1.0F) {
         var4 = 1.0F;
      }

      if (var4 > 0.1F) {
         float var5 = MathHelper.sin((var3 - 0.1F) * 1.3F);
         float var6 = var4 - 0.1F;
         float var7 = var5 * var6;
         GlStateManager.translate(var7 * 0.0F, var7 * 0.01F, var7 * 0.0F);
      }

      GlStateManager.translate(var4 * 0.0F, var4 * 0.0F, var4 * 0.1F);
      GlStateManager.scale(1.0F, 1.0F, 1.0F + var4 * 0.2F);
   }

   private void doBlockTransformations() {
      GlStateManager.translate(-0.5F, 0.2F, 0.0F);
      GlStateManager.rotate(30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(-80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.rotate(60.0F, 0.0F, 1.0F, 0.0F);
   }

   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public void Method808(float float1) {
      if (!Config.isShaders() || !Shaders.isSkipRenderHand()) {
         if (this.j == null) {
            this.j = Moon.getInstance().getModuleManager().getModule(Animations.class);
         }

         float var2 = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * float1);
         EntityPlayerSP var3 = this.mc.thePlayer;
         float var4 = var3.getSwingProgress(float1);
         float var5 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * float1;
         float var6 = var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * float1;
         this.rotateArroundXAndY(var5, var6);
         this.setLightMapFromPlayer(var3);
         this.rotateWithPlayerRotations(var3, float1);
         GlStateManager.enableRescaleNormal();
         GlStateManager.pushMatrix();
         if (this.itemToRender != null) {
            FirstPersonItemRenderEvent var7 = new FirstPersonItemRenderEvent();
            Moon.getInstance().getEventBus().publish(var7);
            if (this.j.isEnabled() && this.j.getAlwaysTranslate().getValue()) {
               GlStateManager.translate(this.j.getXPos().getValue(), this.j.getYPos().getValue(), 0.0F);
            }

            if (this.itemToRender.getItem() instanceof ItemMap) {
               this.renderItemMap(var3, var5, var2, var4);
            } else if (var3.getItemInUseCount() <= 0 && !var7.isUsing()) {
               this.doItemUsedTransformations(var4);
               this.transformFirstPersonItem(var2, var4);
            } else {
               EnumAction var8 = this.itemToRender.getItemUseAction();
               switch (var8) {
                  case NONE:
                     this.transformFirstPersonItem(var2, 0.0F);
                     break;
                  case EAT:
                  case DRINK:
                     this.performDrinking(var3, float1);
                     this.transformFirstPersonItem(var2, 0.0F);
                     break;
                  case BLOCK:
                     if (this.j.isEnabled()) {
                        if (!this.j.getAlwaysTranslate().getValue()) {
                           GlStateManager.translate(this.j.getXPos().getValue(), this.j.getYPos().getValue(), 0.0F);
                        }

                        switch ((Animations.Mode)this.j.getMode().getValue()) {
                           case V1_8:
                              this.transformFirstPersonItem(var2, 0.0F);
                              this.doBlockTransformations();
                              break;
                           case V1_7:
                              this.transformFirstPersonItem(var2 - 0.3F, var4);
                              this.doBlockTransformations();
                              break;
                           case SLIDE:
                              this.transformFirstPersonItem(0.0F, 0.0F);
                              this.doBlockTransformations();
                              float var17 = MathHelper.sin((float)(MathHelper.sqrt_float(var4) * Math.PI));
                              GlStateManager.translate(-0.05F, -0.0F, 0.35F);
                              GlStateManager.rotate(-var17 * 60.0F / 2.0F, -15.0F, -0.0F, 9.0F);
                              GlStateManager.rotate(-var17 * 70.0F, 1.0F, -0.4F, -0.0F);
                              break;
                           case XIV:
                              this.transformFirstPersonItem(var2, 0.0F);
                              this.doBlockTransformations();
                              float var16 = MathHelper.sin((float)(var4 * var4 * Math.PI));
                              float var13 = MathHelper.sin((float)(MathHelper.sqrt_float(var4) * Math.PI));
                              GlStateManager.rotate(-var16 * 20.0F, 0.0F, 1.0F, 0.0F);
                              GlStateManager.rotate(-var13 * 20.0F, 0.0F, 0.0F, 1.0F);
                              GlStateManager.rotate(-var13 * 80.0F, 1.0F, 0.0F, 0.0F);
                              break;
                           case EXHIBITION:
                              float var15 = MathHelper.sin(MathHelper.sqrt_float(var4) * (float) Math.PI);
                              GL11.glTranslated(-0.1, 0.15, 0.0);
                              this.transformFirstPersonItem(var2 / 2.0F, 0.0F);
                              GlStateManager.rotate(-var15 * 45.0F / 1.5F, var15 / 1.5F, -0.0F, 9.0F);
                              GlStateManager.rotate(-var15 * 55.0F, 1.0F, var15 / 2.0F, -0.0F);
                              this.doBlockTransformations();
                              break;
                           case OLD_EXHIBITION:
                              float var14 = MathHelper.sin(MathHelper.sqrt_float(var4) * (float) Math.PI);
                              GL11.glTranslated(-0.1, 0.1, 0.0);
                              this.transformFirstPersonItem(var2 / 2.0F, 0.0F);
                              GlStateManager.rotate(-var14 * 40.0F / 2.0F, var14 / 2.0F, -0.0F, 9.0F);
                              GlStateManager.rotate(-var14 * 30.0F, 1.0F, var14 / 2.0F, -0.0F);
                              this.doBlockTransformations();
                              break;
                           case WINGY:
                              float var12 = MathHelper.sin(MathHelper.sqrt_float(var4) * (float) Math.PI);
                              GlStateManager.translate(0.1F, 0.1F, 0.1F);
                              GlStateManager.rotate(-var12 * 30.0F, -8.0F, -0.2F, 9.0F);
                              this.transformFirstPersonItem(var2, var4);
                              this.doBlockTransformations();
                        }
                     } else {
                        this.transformFirstPersonItem(var2, 0.0F);
                        this.doBlockTransformations();
                     }
                     break;
                  case BOW:
                     this.transformFirstPersonItem(var2, 0.0F);
                     this.doBowTransformations(float1, var3);
               }
            }

            this.Method807(var3, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
         } else if (!var3.isInvisible()) {
            this.renderPlayerArm(var3, var2, var4);
         }

         GlStateManager.popMatrix();
         GlStateManager.disableRescaleNormal();
         RenderHelper.disableStandardItemLighting();
      }
   }

   public void renderOverlays(float float1) {
      GlStateManager.disableAlpha();
      if (this.mc.thePlayer.isEntityInsideOpaqueBlock()) {
         IBlockState var4 = this.mc.theWorld.getBlockState(new BlockPos(this.mc.thePlayer));
         new BlockPos(this.mc.thePlayer);
         EntityPlayerSP var6 = this.mc.thePlayer;

         for (int var7 = 0; var7 < 8; var7++) {
            double var8 = var6.posX + (var7 % 2 - 0.5F) * var6.width * 0.8F;
            double var10 = var6.posY + ((var7 >> 1) % 2 - 0.5F) * 0.1F;
            double var12 = var6.posZ + ((var7 >> 2) % 2 - 0.5F) * var6.width * 0.8F;
            BlockPos var14 = new BlockPos(var8, var10 + var6.getEyeHeight(), var12);
            IBlockState var15 = this.mc.theWorld.getBlockState(var14);
            if (var15.getBlock().isVisuallyOpaque()) {
               var4 = var15;
            }
         }

         if (var4.getBlock().getRenderType() != -1) {
            this.renderBlockInHand(float1, this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(var4));
         }
      }

      if (!this.mc.thePlayer.isSpectator()) {
         if (this.mc.thePlayer.isInsideOfMaterial(Material.water)) {
            this.renderWaterOverlayTexture(float1);
         }

         if (this.mc.thePlayer.isBurning()) {
            this.renderFireInFirstPerson(float1);
         }
      }

      GlStateManager.enableAlpha();
   }

   private void renderBlockInHand(float float1, TextureAtlasSprite textureAtlasSprite) {
      this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      GlStateManager.color(0.1F, 0.1F, 0.1F, 0.5F);
      GlStateManager.pushMatrix();
      float var13 = textureAtlasSprite.getMinU();
      float var14 = textureAtlasSprite.getMaxU();
      float var15 = textureAtlasSprite.getMinV();
      float var16 = textureAtlasSprite.getMaxV();
      var6.begin(7, DefaultVertexFormats.POSITION_TEX);
      var6.pos(-1.0, -1.0, -0.5).tex(var14, var16).endVertex();
      var6.pos(1.0, -1.0, -0.5).tex(var13, var16).endVertex();
      var6.pos(1.0, 1.0, -0.5).tex(var13, var15).endVertex();
      var6.pos(-1.0, 1.0, -0.5).tex(var14, var15).endVertex();
      var5.draw();
      GlStateManager.popMatrix();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void renderWaterOverlayTexture(float float1) {
      if (!Config.isShaders() || Shaders.isUnderwaterOverlay()) {
         this.mc.getTextureManager().bindTexture(RES_UNDERWATER_OVERLAY);
         Tessellator var4 = Tessellator.getInstance();
         WorldRenderer var5 = var4.getWorldRenderer();
         float var6 = this.mc.thePlayer.getBrightness(float1);
         GlStateManager.color(var6, var6, var6, 0.5F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.pushMatrix();
         float var13 = -this.mc.thePlayer.rotationYaw / 64.0F;
         float var14 = this.mc.thePlayer.rotationPitch / 64.0F;
         var5.begin(7, DefaultVertexFormats.POSITION_TEX);
         var5.pos(-1.0, -1.0, -0.5).tex(4.0F + var13, 4.0F + var14).endVertex();
         var5.pos(1.0, -1.0, -0.5).tex(0.0F + var13, 4.0F + var14).endVertex();
         var5.pos(1.0, 1.0, -0.5).tex(0.0F + var13, 0.0F + var14).endVertex();
         var5.pos(-1.0, 1.0, -0.5).tex(4.0F + var13, 0.0F + var14).endVertex();
         var4.draw();
         GlStateManager.popMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.disableBlend();
      }
   }

   private void renderFireInFirstPerson(float float1) {
      Tessellator var4 = Tessellator.getInstance();
      WorldRenderer var5 = var4.getWorldRenderer();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
      GlStateManager.depthFunc(519);
      GlStateManager.depthMask(false);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

      for (int var7 = 0; var7 < 2; var7++) {
         GlStateManager.pushMatrix();
         TextureAtlasSprite var8 = this.mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_1");
         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         float var9 = var8.getMinU();
         float var10 = var8.getMaxU();
         float var11 = var8.getMinV();
         float var12 = var8.getMaxV();
         GlStateManager.translate(-(var7 * 2 - 1) * 0.24F, -0.3F, 0.0F);
         GlStateManager.rotate((var7 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
         var5.begin(7, DefaultVertexFormats.POSITION_TEX);
         var5.setSprite(var8);
         var5.pos(-0.5, -0.5, -0.5).tex(var10, var12).endVertex();
         var5.pos(0.5, -0.5, -0.5).tex(var9, var12).endVertex();
         var5.pos(0.5, 0.5, -0.5).tex(var9, var11).endVertex();
         var5.pos(-0.5, 0.5, -0.5).tex(var10, var11).endVertex();
         var4.draw();
         GlStateManager.popMatrix();
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.depthMask(true);
      GlStateManager.depthFunc(515);
   }

   public void updateEquippedItem() {
      this.prevEquippedProgress = this.equippedProgress;
      EntityPlayerSP var1 = this.mc.thePlayer;
      ItemStack var2 = var1.inventory.Method726();
      boolean var3 = false;
      if (this.itemToRender != null && var2 != null) {
         if (!this.itemToRender.getIsItemStackEqual(var2)) {
            var3 = true;
         }
      } else {
         var3 = this.itemToRender != null || var2 != null;
      }

      float var6 = MathHelper.clamp_float((var3 ? 0.0F : 1.0F) - this.equippedProgress, -0.4F, 0.4F);
      this.equippedProgress += var6;
      if (this.equippedProgress < 0.1F) {
         this.itemToRender = var2;
         this.i = var1.inventory.currentItem;
         if (Config.isShaders()) {
            Shaders.setItemToRenderMain(var2);
         }
      }
   }

   public void Method809() {
      this.equippedProgress = 0.0F;
   }

   public void Method810() {
      this.equippedProgress = 0.0F;
   }
}
