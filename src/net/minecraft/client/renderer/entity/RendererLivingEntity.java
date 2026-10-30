package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import com.moon.Moon;
import com.moon.events.impl.render.RenderEntityEvent;
import com.moon.events.impl.render.RenderEntityLayerEvent;
import com.moon.events.impl.render.RenderEntityModelEvent;
import com.moon.events.impl.render.RenderEntityNameTagEvent;
import java.nio.FloatBuffer;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.src.Config;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.optifine.EmissiveTextures;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.shaders.Shaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public abstract class RendererLivingEntity<T extends EntityLivingBase> extends Render<T> {
   public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");
   private static final Logger logger = LogManager.getLogger();
   private static final DynamicTexture textureBrightness = new DynamicTexture(16, 16);
   public ModelBase mainModel;
   public EntityLivingBase renderEntity;
   public float renderLimbSwing;
   public float renderLimbSwingAmount;
   public float renderAgeInTicks;
   public float renderHeadYaw;
   public float renderHeadPitch;
   public float renderScaleFactor;
   public float renderPartialTicks;
   protected FloatBuffer brightnessBuffer = GLAllocation.createDirectFloatBuffer(4);
   protected List<LayerRenderer<T>> layerRenderers = Lists.newArrayList();
   protected boolean renderOutlines = false;
   private boolean renderModelPushMatrix;
   private boolean renderLayersPushMatrix;

   public RendererLivingEntity(RenderManager renderManager, ModelBase modelBase, float float3) {
      super(renderManager);
      this.mainModel = modelBase;
      this.shadowSize = float3;
      this.renderModelPushMatrix = this.mainModel instanceof ModelSpider;
   }

   public <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean addLayer(U layerRenderer) {
      return this.layerRenderers.add((LayerRenderer<T>)layerRenderer);
   }

   protected <V extends EntityLivingBase, U extends LayerRenderer<V>> boolean removeLayer(U layerRenderer) {
      return this.layerRenderers.remove(layerRenderer);
   }

   public ModelBase getMainModel() {
      return this.mainModel;
   }

   protected float interpolateRotation(float float1, float float2, float float3) {
      float var4 = float2 - float1;

      while (var4 < -180.0F) {
         var4 += 360.0F;
      }

      while (var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return float1 + float3 * var4;
   }

   public void transformHeldFull3DItemLayer() {
   }

   public void doRender(T entityLivingBase, double double2, double double3, double double4, float float5, float float6) {
      RenderEntityEvent var10 = new RenderEntityEvent(entityLivingBase, entityLivingBase.isRiding(), entityLivingBase.isChild());
      Moon.getInstance().getEventBus().publish(var10);
      if (!var10.isCancelled()) {
         if (animateModelLiving) {
            entityLivingBase.limbSwingAmount = 1.0F;
         }

         GlStateManager.pushMatrix();
         GlStateManager.disableCull();
         this.mainModel.swingProgress = this.getSwingProgress((T)entityLivingBase, float6);
         this.mainModel.isRiding = var10.isRiding();
         this.mainModel.isChild = var10.isChild();

         try {
            float var11 = this.interpolateRotation(entityLivingBase.prevRenderYawOffset, entityLivingBase.renderYawOffset, float6);
            float var12 = this.interpolateRotation(entityLivingBase.prevRotationYawHead, entityLivingBase.rotationYawHead, float6);
            float var13 = var12 - var11;
            if (this.mainModel.isRiding && entityLivingBase.ridingEntity instanceof EntityLivingBase var14) {
               var11 = this.interpolateRotation(var14.prevRenderYawOffset, var14.renderYawOffset, float6);
               float var24 = MathHelper.wrapAngleTo180_float(var12 - var11);
               if (var24 < -85.0F) {
                  var24 = -85.0F;
               }

               if (var24 >= 85.0F) {
                  var24 = 85.0F;
               }

               var11 = var12 - var24;
               if (var24 * var24 > 2500.0F) {
                  var11 += var24 * 0.2F;
               }

               var13 = var12 - var11;
            }

            float var23 = entityLivingBase.prevRotationPitch + (entityLivingBase.rotationPitch - entityLivingBase.prevRotationPitch) * float6;
            if (entityLivingBase == Minecraft.getMinecraft().thePlayer) {
               var23 = Minecraft.getMinecraft().thePlayer.cL + (Minecraft.getMinecraft().thePlayer.cK - Minecraft.getMinecraft().thePlayer.cL) * float6;
            }

            this.renderLivingAt((T)entityLivingBase, double2, double3, double4);
            float var25 = this.handleRotationFloat((T)entityLivingBase, float6);
            this.rotateCorpse((T)entityLivingBase, var25, var11, float6);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback((T)entityLivingBase, float6);
            GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
            float var17 = entityLivingBase.prevLimbSwingAmount + (entityLivingBase.limbSwingAmount - entityLivingBase.prevLimbSwingAmount) * float6;
            float var18 = entityLivingBase.limbSwing - entityLivingBase.limbSwingAmount * (1.0F - float6);
            if (entityLivingBase.isChild()) {
               var18 *= 3.0F;
            }

            if (var17 > 1.0F) {
               var17 = 1.0F;
            }

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(entityLivingBase, var18, var17, float6);
            this.mainModel.setRotationAngles(var18, var17, var25, var13, var23, 0.0625F, entityLivingBase);
            if (CustomEntityModels.isActive()) {
               this.renderEntity = entityLivingBase;
               this.renderLimbSwing = var18;
               this.renderLimbSwingAmount = var17;
               this.renderAgeInTicks = var25;
               this.renderHeadYaw = var13;
               this.renderHeadPitch = var23;
               this.renderScaleFactor = 0.0625F;
               this.renderPartialTicks = float6;
            }

            if (this.renderOutlines) {
               boolean var26 = this.setScoreTeamColor((T)entityLivingBase);
               this.renderModel((T)entityLivingBase, var18, var17, var25, var13, var23, 0.0625F);
               if (var26) {
                  this.unsetScoreTeamColor();
               }
            } else {
               boolean var19 = this.setDoRenderBrightness((T)entityLivingBase, float6);
               if (EmissiveTextures.isActive()) {
                  EmissiveTextures.beginRender();
               }

               if (this.renderModelPushMatrix) {
                  GlStateManager.pushMatrix();
               }

               this.renderModel((T)entityLivingBase, var18, var17, var25, var13, var23, 0.0625F);
               if (this.renderModelPushMatrix) {
                  GlStateManager.popMatrix();
               }

               if (EmissiveTextures.isActive()) {
                  if (EmissiveTextures.hasEmissive()) {
                     this.renderModelPushMatrix = true;
                     EmissiveTextures.beginRenderEmissive();
                     GlStateManager.pushMatrix();
                     this.renderModel((T)entityLivingBase, var18, var17, var25, var13, var23, 0.0625F);
                     GlStateManager.popMatrix();
                     EmissiveTextures.endRenderEmissive();
                  }

                  EmissiveTextures.endRender();
               }

               if (var19) {
                  this.unsetBrightness();
               }

               GlStateManager.depthMask(true);
               RenderEntityLayerEvent var20 = new RenderEntityLayerEvent(entityLivingBase);
               Moon.getInstance().getEventBus().publish(var20);
               if (!var20.isCancelled() && (!(entityLivingBase instanceof EntityPlayer) || !((EntityPlayer)entityLivingBase).isSpectator())) {
                  this.renderLayers((T)entityLivingBase, var18, var17, float6, var25, var13, var23, 0.0625F);
               }
            }

            if (CustomEntityModels.isActive()) {
               this.renderEntity = null;
            }

            GlStateManager.disableRescaleNormal();
         } catch (Exception var21) {
            logger.error("Couldn't render entity", var21);
         }

         GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GlStateManager.enableTexture2D();
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
         if (!this.renderOutlines) {
            super.doRender((T)entityLivingBase, double2, double3, double4, float5, float6);
         }
      }
   }

   protected boolean setScoreTeamColor(T entityLivingBase) {
      int var4 = 16777215;
      if (entityLivingBase instanceof EntityPlayer) {
         ScorePlayerTeam var5 = (ScorePlayerTeam)entityLivingBase.getTeam();
         if (var5 != null) {
            String var6 = FontRenderer.getFormatFromString(var5.getColorPrefix());
            if (var6.length() >= 2) {
               var4 = this.getFontRendererFromRenderManager().getColorCode(var6.charAt(1));
            }
         }
      }

      float var8 = (var4 >> 16 & 0xFF) / 255.0F;
      float var9 = (var4 >> 8 & 0xFF) / 255.0F;
      float var7 = (var4 & 0xFF) / 255.0F;
      GlStateManager.disableLighting();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.color(var8, var9, var7, 1.0F);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      return true;
   }

   protected void unsetScoreTeamColor() {
      GlStateManager.enableLighting();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.enableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   protected void renderModel(T entityLivingBase, float float2, float float3, float float4, float float5, float float6, float float7) {
      boolean var10 = !entityLivingBase.isInvisible();
      boolean var11 = !var10 && !entityLivingBase.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);
      if (var10 || var11) {
         if (!this.bindEntityTexture((T)entityLivingBase)) {
            return;
         }

         if (var11) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.alphaFunc(516, 0.003921569F);
         }

         RenderEntityModelEvent var12 = new RenderEntityModelEvent(entityLivingBase, this.mainModel, float2, float3, float4, float5, float6, float7, true);
         Moon.getInstance().getEventBus().publish(var12);
         if (!var12.isCancelled()) {
            this.mainModel.render(entityLivingBase, float2, float3, float4, float5, float6, float7);
         }

         RenderEntityModelEvent var13 = new RenderEntityModelEvent(entityLivingBase, this.mainModel, float2, float3, float4, float5, float6, float7, false);
         Moon.getInstance().getEventBus().publish(var13);
         if (var11) {
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
         }
      }
   }

   protected boolean setDoRenderBrightness(T entityLivingBase, float float2) {
      return this.setBrightness((T)entityLivingBase, float2, true);
   }

   protected boolean setBrightness(T entityLivingBase, float float2, boolean boolean3) {
      float var6 = entityLivingBase.getBrightness(float2);
      int var7 = this.getColorMultiplier((T)entityLivingBase, var6, float2);
      boolean var8 = (var7 >> 24 & 0xFF) > 0;
      boolean var9 = entityLivingBase.hurtTime > 0 && !Minecraft.getMinecraft().getRenderManager().Method591()
         || entityLivingBase.deathTime > 0 && !Minecraft.getMinecraft().getRenderManager().Method591();
      if (!var8 && !var9) {
         return false;
      }

      if (!var8 && !boolean3) {
         return false;
      }

      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableTexture2D();
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_RGB, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_ALPHA, GL11.GL_REPLACE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.enableTexture2D();
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_RGB, OpenGlHelper.GL_INTERPOLATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_CONSTANT);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE2_RGB, OpenGlHelper.GL_CONSTANT);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND2_RGB, GL11.GL_SRC_ALPHA);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_ALPHA, GL11.GL_REPLACE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
      this.brightnessBuffer.position(0);
      if (var9) {
         this.brightnessBuffer.put(1.0F);
         this.brightnessBuffer.put(0.0F);
         this.brightnessBuffer.put(0.0F);
         this.brightnessBuffer.put(0.3F);
         if (Config.isShaders()) {
            Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
         }
      } else {
         float var10 = (var7 >> 24 & 0xFF) / 255.0F;
         float var11 = (var7 >> 16 & 0xFF) / 255.0F;
         float var12 = (var7 >> 8 & 0xFF) / 255.0F;
         float var13 = (var7 & 0xFF) / 255.0F;
         this.brightnessBuffer.put(var11);
         this.brightnessBuffer.put(var12);
         this.brightnessBuffer.put(var13);
         this.brightnessBuffer.put(1.0F - var10);
         if (Config.isShaders()) {
            Shaders.setEntityColor(var11, var12, var13, 1.0F - var10);
         }
      }

      this.brightnessBuffer.flip();
      GL11.glTexEnv(8960, 8705, this.brightnessBuffer);
      GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
      GlStateManager.enableTexture2D();
      GlStateManager.bindTexture(textureBrightness.getGlTextureId());
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_RGB, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.GL_PREVIOUS);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.lightmapTexUnit);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_ALPHA, GL11.GL_REPLACE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.GL_PREVIOUS);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      return true;
   }

   protected void unsetBrightness() {
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableTexture2D();
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_RGB, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_ALPHA, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND1_ALPHA, GL11.GL_SRC_ALPHA);
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_RGB, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_RGB, GL11.GL_TEXTURE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_ALPHA, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_ALPHA, GL11.GL_TEXTURE);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
      GlStateManager.disableTexture2D();
      GlStateManager.bindTexture(0);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_RGB, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_RGB, GL11.GL_TEXTURE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_COMBINE_ALPHA, GL11.GL_MODULATE);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
      GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, OpenGlHelper.GL_SOURCE0_ALPHA, GL11.GL_TEXTURE);
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if (Config.isShaders()) {
         Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
      }
   }

   protected void renderLivingAt(T entityLivingBase, double double2, double double3, double double4) {
      GlStateManager.translate((float)double2, (float)double3, (float)double4);
   }

   protected void rotateCorpse(T entityLivingBase, float float2, float float3, float float4) {
      GlStateManager.rotate(180.0F - float3, 0.0F, 1.0F, 0.0F);
      if (entityLivingBase.deathTime > 0) {
         float var5 = MathHelper.sqrt_float((entityLivingBase.deathTime + float4 - 1.0F) / 20.0F * 1.6F);
         if (var5 > 1.0F) {
            var5 = 1.0F;
         }

         GlStateManager.rotate(var5 * this.getDeathMaxRotation((T)entityLivingBase), 0.0F, 0.0F, 1.0F);
      } else {
         String var6 = EnumChatFormatting.getTextWithoutFormattingCodes(entityLivingBase.getName());
         if (var6 != null
            && (var6.equals("Dinnerbone") || var6.equals("Grumm"))
            && (!(entityLivingBase instanceof EntityPlayer) || ((EntityPlayer)entityLivingBase).isWearing(EnumPlayerModelParts.CAPE))) {
            GlStateManager.translate(0.0F, entityLivingBase.height + 0.1F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         }
      }
   }

   protected float getSwingProgress(T entityLivingBase, float float2) {
      return entityLivingBase.getSwingProgress(float2);
   }

   protected float handleRotationFloat(T entityLivingBase, float float2) {
      return entityLivingBase.ticksExisted + float2;
   }

   protected void renderLayers(T entityLivingBase, float float2, float float3, float float4, float float5, float float6, float float7, float float8) {
      for (LayerRenderer var10 : this.layerRenderers) {
         boolean var11 = this.setBrightness((T)entityLivingBase, float4, var10.shouldCombineTextures());
         if (EmissiveTextures.isActive()) {
            EmissiveTextures.beginRender();
         }

         if (this.renderLayersPushMatrix) {
            GlStateManager.pushMatrix();
         }

         var10.doRenderLayer(entityLivingBase, float2, float3, float4, float5, float6, float7, float8);
         if (this.renderLayersPushMatrix) {
            GlStateManager.popMatrix();
         }

         if (EmissiveTextures.isActive()) {
            if (EmissiveTextures.hasEmissive()) {
               this.renderLayersPushMatrix = true;
               EmissiveTextures.beginRenderEmissive();
               GlStateManager.pushMatrix();
               var10.doRenderLayer(entityLivingBase, float2, float3, float4, float5, float6, float7, float8);
               GlStateManager.popMatrix();
               EmissiveTextures.endRenderEmissive();
            }

            EmissiveTextures.endRender();
         }

         if (var11) {
            this.unsetBrightness();
         }
      }
   }

   protected float getDeathMaxRotation(T entityLivingBase) {
      return 90.0F;
   }

   protected int getColorMultiplier(T entityLivingBase, float float2, float float3) {
      return 0;
   }

   protected void preRenderCallback(T entityLivingBase, float float2) {
   }

   public void renderName(T entityLivingBase, double double2, double double3, double double4) {
      if (Minecraft.getMinecraft().getRenderManager().Method594()) {
         RenderEntityNameTagEvent var10 = new RenderEntityNameTagEvent(entityLivingBase);
         Moon.getInstance().getEventBus().publish(var10);
         if (!var10.isCancelled()) {
            if (this.canRenderName((T)entityLivingBase)) {
               double var11 = entityLivingBase.getDistanceSqToEntity(this.renderManager.livingPlayer);
               float var13 = entityLivingBase.isSneaking() ? 32.0F : 64.0F;
               if (var11 < var13 * var13) {
                  String var14 = entityLivingBase.getDisplayName().getFormattedText();
                  GlStateManager.alphaFunc(516, 0.1F);
                  if (entityLivingBase.isSneaking()) {
                     FontRenderer var16 = this.getFontRendererFromRenderManager();
                     GlStateManager.pushMatrix();
                     GlStateManager.translate(
                        (float)double2,
                        (float)double3 + entityLivingBase.height + 0.5F - (entityLivingBase.isChild() ? entityLivingBase.height / 2.0F : 0.0F),
                        (float)double4
                     );
                     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                     GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                     GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
                     GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                     GlStateManager.disableLighting();
                     GlStateManager.depthMask(false);
                     GlStateManager.enableBlend();
                     GlStateManager.disableTexture2D();
                     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                     int var17 = var16.getStringWidth(var14) / 2;
                     Tessellator var18 = Tessellator.getInstance();
                     WorldRenderer var19 = var18.getWorldRenderer();
                     var19.begin(7, DefaultVertexFormats.POSITION_COLOR);
                     var19.pos(-var17 - 1, -1.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                     var19.pos(-var17 - 1, 8.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                     var19.pos(var17 + 1, 8.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                     var19.pos(var17 + 1, -1.0, 0.0).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                     var18.draw();
                     GlStateManager.enableTexture2D();
                     GlStateManager.depthMask(true);
                     var16.drawString(var14, -var16.getStringWidth(var14) / 2, 0.0F, 553648127);
                     GlStateManager.enableLighting();
                     GlStateManager.disableBlend();
                     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                     GlStateManager.popMatrix();
                  } else {
                     this.renderOffsetLivingLabel(
                        (T)entityLivingBase,
                        double2,
                        double3 - (entityLivingBase.isChild() ? entityLivingBase.height / 2.0F : 0.0),
                        double4,
                        var14,
                        0.02666667F,
                        var11
                     );
                  }
               }
            }
         }
      }
   }

   protected boolean canRenderName(T entityLivingBase) {
      EntityPlayerSP var2 = Minecraft.getMinecraft().thePlayer;
      if (entityLivingBase instanceof EntityPlayer && entityLivingBase != var2) {
         Team var3 = entityLivingBase.getTeam();
         Team var4 = var2.getTeam();
         if (var3 != null) {
            Team.EnumVisible var5 = var3.getNameTagVisibility();
            switch (var5) {
               case NEVER:
                  return false;
               case HIDE_FOR_OTHER_TEAMS:
                  return var4 == null || var3.isSameTeam(var4);
               case HIDE_FOR_OWN_TEAM:
                  return var4 == null || !var3.isSameTeam(var4);
               default:
                  return true;
            }
         }
      }

      return Minecraft.isGuiEnabled()
         && entityLivingBase != this.renderManager.livingPlayer
         && !entityLivingBase.isInvisibleToPlayer(var2)
         && entityLivingBase.riddenByEntity == null;
   }

   public void setRenderOutlines(boolean boolean1) {
      this.renderOutlines = boolean1;
   }

   public List<LayerRenderer<T>> getLayerRenderers() {
      return this.layerRenderers;
   }

   static {
      int[] var13 = textureBrightness.getTextureData();

      for (int var14 = 0; var14 < 256; var14++) {
         var13[var14] = -1;
      }

      textureBrightness.updateDynamicTexture();
   }
}
