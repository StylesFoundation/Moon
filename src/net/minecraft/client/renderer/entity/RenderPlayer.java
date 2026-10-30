package net.minecraft.client.renderer.entity;

import com.moon.Moon;
import com.moon.events.impl.render.RenderHeldItemEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerArrow;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ResourceLocation;

public class RenderPlayer extends RendererLivingEntity<AbstractClientPlayer> {
   private final boolean smallArms;

   public RenderPlayer(RenderManager renderManager) {
      this(renderManager, false);
   }

   public RenderPlayer(RenderManager renderManager, boolean boolean2) {
      super(renderManager, new ModelPlayer(0.0F, boolean2), 0.5F);
      this.smallArms = boolean2;
      this.addLayer(new LayerBipedArmor(this));
      this.addLayer(new LayerHeldItem(this));
      this.addLayer(new LayerArrow(this));
      this.addLayer(new LayerDeadmau5Head(this));
      this.addLayer(new LayerCape(this));
      this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
   }

   public ModelPlayer getMainModel() {
      return (ModelPlayer)super.getMainModel();
   }

   public void doRender(AbstractClientPlayer abstractClientPlayer, double double2, double double3, double double4, float float5, float float6) {
      if (!abstractClientPlayer.isUser() || this.renderManager.livingPlayer == abstractClientPlayer) {
         double var10 = double3;
         if (abstractClientPlayer.isSneaking() && !(abstractClientPlayer instanceof EntityPlayerSP)) {
            var10 = double3 - 0.125;
         }

         this.setModelVisibilities(abstractClientPlayer);
         super.doRender(abstractClientPlayer, double2, var10, double4, float5, float6);
      }
   }

   private void setModelVisibilities(AbstractClientPlayer abstractClientPlayer) {
      ModelPlayer var4 = this.getMainModel();
      if (abstractClientPlayer.isSpectator()) {
         var4.setInvisible(false);
         var4.bipedHead.showModel = true;
         var4.bipedHeadwear.showModel = true;
      } else {
         ItemStack var5 = abstractClientPlayer.inventory.getCurrentItem();
         var4.setInvisible(true);
         var4.bipedHeadwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.HAT);
         var4.bipedBodyWear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.JACKET);
         var4.bipedLeftLegwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
         var4.bipedRightLegwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
         var4.bipedLeftArmwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
         var4.bipedRightArmwear.showModel = abstractClientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
         var4.heldItemLeft = 0;
         var4.aimedBow = false;
         var4.isSneak = abstractClientPlayer.isSneaking();
         int var6 = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
         RenderHeldItemEvent var7 = new RenderHeldItemEvent(var6);
         Moon.getInstance().getEventBus().publish(var7);
         if (var5 != null && var6 == var7.getSlot()) {
            var4.heldItemRight = 1;
            if (abstractClientPlayer.getItemInUseCount() > 0) {
               EnumAction var8 = var5.getItemUseAction();
               if (var8 == EnumAction.BLOCK) {
                  var4.heldItemRight = 3;
               } else if (var8 == EnumAction.BOW) {
                  var4.aimedBow = true;
               }
            }
         } else {
            var4.heldItemRight = 0;
         }
      }
   }

   protected ResourceLocation getEntityTexture(AbstractClientPlayer abstractClientPlayer) {
      return abstractClientPlayer.getLocationSkin();
   }

   @Override
   public void transformHeldFull3DItemLayer() {
      GlStateManager.translate(0.0F, 0.1875F, 0.0F);
   }

   protected void preRenderCallback(AbstractClientPlayer abstractClientPlayer, float float2) {
      GlStateManager.scale(0.9375F, 0.9375F, 0.9375F);
   }

   protected void renderOffsetLivingLabel(
      AbstractClientPlayer abstractClientPlayer, double double2, double double3, double double4, String string, float float6, double double7
   ) {
      if (double7 < 100.0) {
         Scoreboard var14 = abstractClientPlayer.getWorldScoreboard();
         ScoreObjective var15 = var14.getObjectiveInDisplaySlot(2);
         if (var15 != null) {
            Score var16 = var14.Method281(abstractClientPlayer.getName(), var15);
            this.renderLivingLabel(abstractClientPlayer, var16.getScorePoints() + " " + var15.getDisplayName(), double2, double3, double4, 64);
            double3 += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * float6;
         }
      }

      super.renderOffsetLivingLabel(abstractClientPlayer, double2, double3, double4, string, float6, double7);
   }

   public void renderRightArm(AbstractClientPlayer abstractClientPlayer) {
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      ModelPlayer var3 = this.getMainModel();
      this.setModelVisibilities(abstractClientPlayer);
      var3.swingProgress = 0.0F;
      var3.isSneak = false;
      var3.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, abstractClientPlayer);
      var3.renderRightArm();
   }

   public void renderLeftArm(AbstractClientPlayer abstractClientPlayer) {
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      ModelPlayer var3 = this.getMainModel();
      this.setModelVisibilities(abstractClientPlayer);
      var3.isSneak = false;
      var3.swingProgress = 0.0F;
      var3.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, abstractClientPlayer);
      var3.renderLeftArm();
   }

   protected void renderLivingAt(AbstractClientPlayer abstractClientPlayer, double double2, double double3, double double4) {
      if (abstractClientPlayer.isEntityAlive() && abstractClientPlayer.isPlayerSleeping()) {
         super.renderLivingAt(
            abstractClientPlayer,
            double2 + abstractClientPlayer.renderOffsetX,
            double3 + abstractClientPlayer.renderOffsetY,
            double4 + abstractClientPlayer.renderOffsetZ
         );
      } else {
         super.renderLivingAt(abstractClientPlayer, double2, double3, double4);
      }
   }

   protected void rotateCorpse(AbstractClientPlayer abstractClientPlayer, float float2, float float3, float float4) {
      if (abstractClientPlayer.isEntityAlive() && abstractClientPlayer.isPlayerSleeping()) {
         GlStateManager.rotate(abstractClientPlayer.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(this.getDeathMaxRotation(abstractClientPlayer), 0.0F, 0.0F, 1.0F);
         GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
      } else {
         super.rotateCorpse(abstractClientPlayer, float2, float3, float4);
      }
   }
}
