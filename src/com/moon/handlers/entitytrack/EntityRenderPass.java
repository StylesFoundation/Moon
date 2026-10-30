package com.moon.handlers.entitytrack;

import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.StorageESP;
import com.moon.render.gl.CustomFramebuffer;
import com.moon.util.render.EntityColorUtil;
import com.moon.util.render.FrustumUtil;
import java.awt.Color;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;

public class EntityRenderPass extends EntityRenderPassBase {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final CustomFramebuffer framebuffer = new CustomFramebuffer(1, 1, false);
   private final Predicate<Entity> entityFilter;
   private final StorageESP storageEsp;

   public EntityRenderPass(ESP esp, StorageESP storageEsp, Predicate<Entity> entityFilter) {
      super(esp);
      this.storageEsp = storageEsp;
      this.entityFilter = entityFilter;
   }

   @Override
   public void draw() {
   }

   @Override
   public void rebuild() {
   }

   @Override
   public void renderEntities() {
      this.renderEntitiesToFramebuffer();
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   public void renderEntitiesToFramebuffer() {
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      Minecraft.getMinecraft().entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 0);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
      boolean originalShadows = mc.gameSettings.entityShadows;
      mc.gameSettings.entityShadows = false;
      mc.getRenderManager().Method596(true);
      mc.getRenderManager().Method597(false);
      mc.getRenderManager().Method598(false);
      mc.getRenderManager().Method599(false);
      mc.getRenderManager().Method600(true);
      if (this.framebuffer.resize(mc.displayWidth, mc.displayHeight)) {
         this.framebuffer.setTextureFilter(9728);
      }

      this.framebuffer.bindFramebuffer(false);
      this.framebuffer.clear();
      Color currentColor = null;
      EntityColorUtil.reset();
      if (this.storageEsp.isEnabled() && (this.storageEsp.getGlow().getValue() || this.storageEsp.getOutline().getValue())) {
         for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (FrustumUtil.isVisible(tileEntity.Method981()) && (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest)) {
               Color tileColor = new Color(this.getTileEntityColor(tileEntity));
               if (!tileColor.equals(currentColor)) {
                  EntityColorUtil.setEntityColor(tileColor);
                  currentColor = tileColor;
               }

               TileEntityRendererDispatcher.instance.renderTileEntity(tileEntity, mc.timer.renderPartialTicks, -1);
            }
         }
      }

      if (this.getEsp().isEnabled() && (this.getEsp().getGlow().getValue() || this.getEsp().getOutline().getValue())) {
         for (Entity entity : mc.theWorld.loadedEntityList) {
            if (this.entityFilter.test(entity) && FrustumUtil.isVisible(entity)) {
               Color entityColor = new Color(this.getEntityColor(entity));
               if (!entityColor.equals(currentColor)) {
                  EntityColorUtil.setEntityColor(entityColor);
                  currentColor = entityColor;
               }

               mc.getRenderManager().renderEntitySimple(entity, mc.timer.renderPartialTicks);
            }
         }
      }

      EntityColorUtil.reset();
      GlStateManager.enableDepth();
      RenderHelper.disableStandardItemLighting();
      mc.entityRenderer.disableLightmap();
      mc.getRenderManager().Method596(false);
      mc.getRenderManager().Method597(true);
      mc.getRenderManager().Method598(true);
      mc.getRenderManager().Method599(true);
      mc.getRenderManager().Method600(false);
      mc.gameSettings.entityShadows = originalShadows;
      mc.getFramebuffer().bindFramebuffer(true);
   }

   public int getEntityColor(Entity entity) {
      if (entity instanceof EntityLivingBase livingBase && livingBase.hurtTime > 0) {
         return this.getEsp().getHurtTimeColor().getValue();
      } else if (entity instanceof EntityMob) {
         return this.getEsp().getMobColor().getValue();
      } else if (entity instanceof EntityAnimal) {
         return this.getEsp().getAnimalColor().getValue();
      } else {
         return entity instanceof EntityPlayer ? this.getEsp().getPlayerColor().getValue() : -1;
      }
   }

   public int getTileEntityColor(TileEntity tileEntity) {
      if (tileEntity instanceof TileEntityChest chest) {
         return chest.getChestType() == 1 ? this.storageEsp.getGlowRedstoneColor().getValue() : this.storageEsp.getGlowNormalColor().getValue();
      } else {
         return tileEntity instanceof TileEntityEnderChest ? this.storageEsp.getGlowEnderColor().getValue() : -1;
      }
   }

   public CustomFramebuffer getFramebuffer() {
      return this.framebuffer;
   }

   public Predicate<Entity> getEntityFilter() {
      return this.entityFilter;
   }

   public StorageESP getStorageEsp() {
      return this.storageEsp;
   }
}
