package net.optifine.shaders;

import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.src.Config;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShadersRender {
   private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");

   public static void setFrustrumPosition(ICamera iCamera, double double2, double double3, double double4) {
      iCamera.setPosition(double2, double3, double4);
   }

   public static void setupTerrain(RenderGlobal renderGlobal, Entity entity, double double3, ICamera iCamera, int integer, boolean boolean6) {
      renderGlobal.setupTerrain(entity, double3, iCamera, integer, boolean6);
   }

   public static void beginTerrainSolid() {
      if (Shaders.isRenderingWorld) {
         Shaders.fogEnabled = true;
         Shaders.useProgram(Shaders.ProgramTerrain);
      }
   }

   public static void beginTerrainCutoutMipped() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTerrain);
      }
   }

   public static void beginTerrainCutout() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTerrain);
      }
   }

   public static void endTerrain() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTexturedLit);
      }
   }

   public static void beginTranslucent() {
      if (Shaders.isRenderingWorld) {
         if (Shaders.usedDepthBuffers >= 2) {
            GlStateManager.setActiveTexture(33995);
            Shaders.checkGLError("pre copy depth");
            GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
            Shaders.checkGLError("copy depth");
            GlStateManager.setActiveTexture(33984);
         }

         Shaders.useProgram(Shaders.ProgramWater);
      }
   }

   public static void endTranslucent() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramTexturedLit);
      }
   }

   public static void renderHand0(EntityRenderer entityRenderer, float float2, int integer) {
      if (!Shaders.isShadowPass) {
         boolean var6 = Shaders.isItemToRenderMainTranslucent();
         boolean var7 = Shaders.isItemToRenderOffTranslucent();
         if (!var6 || !var7) {
            Shaders.readCenterDepth();
            Shaders.beginHand(false);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Shaders.setSkipRenderHands(var6, var7);
            entityRenderer.renderHand(float2, integer, true, false, false);
            Shaders.endHand();
            Shaders.setHandsRendered(!var6, !var7);
            Shaders.setSkipRenderHands(false, false);
         }
      }
   }

   public static void renderHand1(EntityRenderer entityRenderer, float float2, int integer) {
      if (!Shaders.isShadowPass && !Shaders.Method458()) {
         Shaders.readCenterDepth();
         GlStateManager.enableBlend();
         Shaders.beginHand(true);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         Shaders.setSkipRenderHands(Shaders.isHandRenderedMain(), Shaders.isHandRenderedOff());
         entityRenderer.renderHand(float2, integer, true, false, true);
         Shaders.endHand();
         Shaders.setHandsRendered(true, true);
         Shaders.setSkipRenderHands(false, false);
      }
   }

   public static void renderItemFP(ItemRenderer itemRenderer, float float2, boolean boolean3) {
      Shaders.setRenderingFirstPersonHand(true);
      GlStateManager.depthMask(true);
      if (boolean3) {
         GlStateManager.depthFunc(519);
         GL11.glPushMatrix();
         IntBuffer var6 = Shaders.activeDrawBuffers;
         Shaders.setDrawBuffers(Shaders.drawBuffersNone);
         Shaders.renderItemKeepDepthMask = true;
         itemRenderer.Method808(float2);
         Shaders.renderItemKeepDepthMask = false;
         Shaders.setDrawBuffers(var6);
         GL11.glPopMatrix();
      }

      GlStateManager.depthFunc(515);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      itemRenderer.Method808(float2);
      Shaders.setRenderingFirstPersonHand(false);
   }

   public static void renderFPOverlay(EntityRenderer entityRenderer, float float2, int integer) {
      if (!Shaders.isShadowPass) {
         Shaders.beginFPOverlay();
         entityRenderer.renderHand(float2, integer, false, true, false);
         Shaders.endFPOverlay();
      }
   }

   public static void beginBlockDamage() {
      if (Shaders.isRenderingWorld) {
         Shaders.useProgram(Shaders.ProgramDamagedBlock);
         if (Shaders.ProgramDamagedBlock.getId() == Shaders.ProgramTerrain.getId()) {
            Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
            GlStateManager.depthMask(false);
         }
      }
   }

   public static void endBlockDamage() {
      if (Shaders.isRenderingWorld) {
         GlStateManager.depthMask(true);
         Shaders.useProgram(Shaders.ProgramTexturedLit);
      }
   }

   public static void renderShadowMap(EntityRenderer entityRenderer, int integer, float float3, long long4) {
      if (Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
         Minecraft var8 = Minecraft.getMinecraft();
         var8.mcProfiler.endStartSection("shadow pass");
         RenderGlobal var9 = var8.renderGlobal;
         Shaders.isShadowPass = true;
         Shaders.shadowPassCounter = Shaders.shadowPassInterval;
         Shaders.preShadowPassThirdPersonView = var8.gameSettings.thirdPersonView;
         var8.gameSettings.thirdPersonView = 1;
         Shaders.checkGLError("pre shadow");
         GL11.glMatrixMode(GL11.GL_PROJECTION);
         GL11.glPushMatrix();
         GL11.glMatrixMode(GL11.GL_MODELVIEW);
         GL11.glPushMatrix();
         var8.mcProfiler.endStartSection("shadow clear");
         EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, Shaders.sfb);
         Shaders.checkGLError("shadow bind sfb");
         var8.mcProfiler.endStartSection("shadow camera");
         entityRenderer.setupCameraTransform(float3, 2);
         Shaders.setCameraShadow(float3);
         Shaders.checkGLError("shadow camera");
         Shaders.useProgram(Shaders.ProgramShadow);
         GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
         Shaders.checkGLError("shadow drawbuffers");
         GL11.glReadBuffer(GL11.GL_ZERO);
         Shaders.checkGLError("shadow readbuffer");
         EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT, GL11.GL_TEXTURE_2D, Shaders.sfbDepthTextures.get(0), 0);
         if (Shaders.usedShadowColorBuffers != 0) {
            EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, Shaders.sfbColorTextures.get(0), 0);
         }

         Shaders.checkFramebufferStatus("shadow fb");
         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glClear(Shaders.usedShadowColorBuffers != 0 ? 16640 : 256);
         Shaders.checkGLError("shadow clear");
         var8.mcProfiler.endStartSection("shadow frustum");
         ClippingHelper var10 = ClippingHelperShadow.getInstance();
         var8.mcProfiler.endStartSection("shadow culling");
         Frustum var11 = new Frustum(var10);
         Entity var12 = var8.getRenderViewEntity();
         double var13 = var12.lastTickPosX + (var12.posX - var12.lastTickPosX) * float3;
         double var15 = var12.lastTickPosY + (var12.posY - var12.lastTickPosY) * float3;
         double var17 = var12.lastTickPosZ + (var12.posZ - var12.lastTickPosZ) * float3;
         var11.setPosition(var13, var15, var17);
         GlStateManager.shadeModel(7425);
         GlStateManager.enableDepth();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         GlStateManager.colorMask(true, true, true, true);
         GlStateManager.disableCull();
         var8.mcProfiler.endStartSection("shadow prepareterrain");
         var8.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         var8.mcProfiler.endStartSection("shadow setupterrain");
         int var19 = entityRenderer.frameCount++;
         var9.setupTerrain(var12, float3, var11, var19, var8.thePlayer.isSpectator());
         var8.mcProfiler.endStartSection("shadow updatechunks");
         var8.mcProfiler.endStartSection("shadow terrain");
         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         GlStateManager.disableAlpha();
         var9.renderBlockLayer(EnumWorldBlockLayer.SOLID, float3, 2, var12);
         Shaders.checkGLError("shadow terrain solid");
         GlStateManager.enableAlpha();
         var9.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, float3, 2, var12);
         Shaders.checkGLError("shadow terrain cutoutmipped");
         var8.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
         var9.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, float3, 2, var12);
         Shaders.checkGLError("shadow terrain cutout");
         var8.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
         GlStateManager.shadeModel(7424);
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         var8.mcProfiler.endStartSection("shadow entities");
         var9.renderEntities(var12, var11, float3);
         Shaders.checkGLError("shadow entities");
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         GlStateManager.disableBlend();
         GlStateManager.enableCull();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.alphaFunc(516, 0.1F);
         if (Shaders.usedShadowDepthBuffers >= 2) {
            GlStateManager.setActiveTexture(33989);
            Shaders.checkGLError("pre copy shadow depth");
            GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
            Shaders.checkGLError("copy shadow depth");
            GlStateManager.setActiveTexture(33984);
         }

         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         var8.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         GlStateManager.shadeModel(7425);
         Shaders.checkGLError("shadow pre-translucent");
         GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
         Shaders.checkGLError("shadow drawbuffers pre-translucent");
         Shaders.checkFramebufferStatus("shadow pre-translucent");
         if (Shaders.isRenderShadowTranslucent()) {
            var8.mcProfiler.endStartSection("shadow translucent");
            var9.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, float3, 2, var12);
            Shaders.checkGLError("shadow translucent");
         }

         GlStateManager.shadeModel(7424);
         GlStateManager.depthMask(true);
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GL11.glFlush();
         Shaders.checkGLError("shadow flush");
         Shaders.isShadowPass = false;
         var8.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
         var8.mcProfiler.endStartSection("shadow postprocess");
         if (Shaders.hasGlGenMipmap) {
            if (Shaders.usedShadowDepthBuffers >= 1) {
               if (Shaders.shadowMipmapEnabled[0]) {
                  GlStateManager.setActiveTexture(33988);
                  GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
                  GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
                  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.shadowFilterNearest[0] ? 9984 : 9987);
               }

               if (Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
                  GlStateManager.setActiveTexture(33989);
                  GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
                  GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
                  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.shadowFilterNearest[1] ? 9984 : 9987);
               }

               GlStateManager.setActiveTexture(33984);
            }

            if (Shaders.usedShadowColorBuffers >= 1) {
               if (Shaders.shadowColorMipmapEnabled[0]) {
                  GlStateManager.setActiveTexture(33997);
                  GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
                  GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
                  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.shadowColorFilterNearest[0] ? 9984 : 9987);
               }

               if (Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
                  GlStateManager.setActiveTexture(33998);
                  GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
                  GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
                  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.shadowColorFilterNearest[1] ? 9984 : 9987);
               }

               GlStateManager.setActiveTexture(33984);
            }
         }

         Shaders.checkGLError("shadow postprocess");
         EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, Shaders.dfb);
         GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
         Shaders.activeDrawBuffers = null;
         var8.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         Shaders.useProgram(Shaders.ProgramTerrain);
         GL11.glMatrixMode(GL11.GL_MODELVIEW);
         GL11.glPopMatrix();
         GL11.glMatrixMode(GL11.GL_PROJECTION);
         GL11.glPopMatrix();
         GL11.glMatrixMode(GL11.GL_MODELVIEW);
         Shaders.checkGLError("shadow end");
      }
   }

   public static void Method523(EnumWorldBlockLayer enumWorldBlockLayer) {
      if (Shaders.isRenderBackFace(enumWorldBlockLayer)) {
         GlStateManager.disableCull();
      }

      if (OpenGlHelper.useVbo()) {
         GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
         GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
         GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
         GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
      }
   }

   public static void Method524(EnumWorldBlockLayer enumWorldBlockLayer) {
      if (OpenGlHelper.useVbo()) {
         GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
         GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
         GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
         GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
      }

      if (Shaders.isRenderBackFace(enumWorldBlockLayer)) {
         GlStateManager.enableCull();
      }
   }

   public static void setupArrayPointersVbo() {
      GL11.glVertexPointer(3, GL11.GL_FLOAT, 56, 0L);
      GL11.glColorPointer(4, 5121, 56, 12L);
      GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 56, 16L);
      OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glTexCoordPointer(2, GL11.GL_SHORT, 56, 24L);
      OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
      GL11.glNormalPointer(GL11.GL_BYTE, 56, 28L);
      GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);
      GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);
      GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
   }

   public static void beaconBeamBegin() {
      Shaders.useProgram(Shaders.ProgramBeaconBeam);
   }

   public static void Method525() {
   }

   public static void Method526() {
   }

   public static void Method527() {
   }

   public static void beaconBeamDraw2() {
      GlStateManager.disableBlend();
   }

   public static void renderEnchantedGlintBegin() {
      Shaders.useProgram(Shaders.ProgramArmorGlint);
   }

   public static void renderEnchantedGlintEnd() {
      if (Shaders.isRenderingWorld) {
         if (Shaders.isRenderingFirstPersonHand() && Shaders.isRenderBothHands()) {
            Shaders.useProgram(Shaders.ProgramHand);
         } else {
            Shaders.useProgram(Shaders.ProgramEntities);
         }
      } else {
         Shaders.useProgram(Shaders.ProgramNone);
      }
   }

   public static boolean renderEndPortal(
      TileEntityEndPortal tileEntityEndPortal, double double2, double double3, double double4, float float5, int integer, float float7
   ) {
      if (!Shaders.isShadowPass && Shaders.activeProgram.getId() == 0) {
         return false;
      }

      GlStateManager.disableLighting();
      Config.getTextureManager().bindTexture(END_PORTAL_TEXTURE);
      Tessellator var13 = Tessellator.getInstance();
      WorldRenderer var14 = var13.getWorldRenderer();
      var14.begin(7, DefaultVertexFormats.BLOCK);
      float var21 = (float)(System.currentTimeMillis() % 100000L) / 100000.0F;
      short var22 = 240;
      var14.pos(double2, double3 + float7, double4 + 1.0).color(0.075F, 0.15F, 0.2F, 1.0F).tex(0.0F + var21, 0.0F + var21).lightmap(var22, var22).endVertex();
      var14.pos(double2 + 1.0, double3 + float7, double4 + 1.0)
         .color(0.075F, 0.15F, 0.2F, 1.0F)
         .tex(0.0F + var21, 0.2F + var21)
         .lightmap(var22, var22)
         .endVertex();
      var14.pos(double2 + 1.0, double3 + float7, double4).color(0.075F, 0.15F, 0.2F, 1.0F).tex(0.2F + var21, 0.2F + var21).lightmap(var22, var22).endVertex();
      var14.pos(double2, double3 + float7, double4).color(0.075F, 0.15F, 0.2F, 1.0F).tex(0.2F + var21, 0.0F + var21).lightmap(var22, var22).endVertex();
      var13.draw();
      GlStateManager.enableLighting();
      return true;
   }
}
