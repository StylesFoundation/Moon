package com.moon.handlers.entitytrack;

import com.moon.Moon;
import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.StorageESP;
import com.moon.module.impl.visual.esp.indicator.EspIndicatorRenderer;
import com.moon.module.impl.visual.esp.renderer.EspArmorBarRenderer;
import com.moon.module.impl.visual.esp.renderer.EspBoxRenderer;
import com.moon.module.impl.visual.esp.renderer.EspEntityNameRenderer;
import com.moon.module.impl.visual.esp.renderer.EspEntityRenderer;
import com.moon.module.impl.visual.esp.renderer.EspHealthBarRenderer;
import com.moon.module.impl.visual.esp.renderer.EspItemNameRenderer;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.font.FontRenderer;
import com.moon.render.font.impl.MinecraftFontRenderer;
import com.moon.render.font.manager.FontType;
import com.moon.util.render.FrustumUtil;
import com.moon.util.render.LegacyGlUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.util.vector.Vector4f;

public class TwoDRenderPass extends EntityRenderPassBase {
   private final List<EspEntityRenderer> renderers = new ArrayList<>();
   private RenderTask rectTask;
   private RenderTask nameTask;
   private RenderTask gradientTask;
   private RenderTask textTask;
   private StorageESP storageEsp;

   public TwoDRenderPass(ESP esp, StorageESP storageEsp) {
      super(esp);
      this.storageEsp = storageEsp;
      this.registerRenderers();
   }

   @Override
   public void draw() {
      if (this.getEsp().isEnabled() && this.getEsp().getItemEnabled().getValue() && this.getEsp().getItemTexture().getValue()) {
         MinecraftFontRenderer fontRenderer = Moon.getInstance().getBitFontManager().getDefaultRenderer();
         Gui.drawRect(0, 0, 0, 0, 0);
         mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
         mc.entityRenderer.setupOverlayRendering();
         RenderHelper.enableGUIStandardItemLighting();
         GlStateManager.enableDepth();
         GlStateManager.enableRescaleNormal();
         GlStateManager.enableAlpha();
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.scale(0.5F, 0.5F, 1.0F);

         for (Entity entity : mc.theWorld.loadedEntityList) {
            Vector4f bounds = entity.Method940();
            if (bounds != null && this.isValidTarget(entity) && FrustumUtil.isVisible(entity)) {
               float left = bounds.x;
               float top = bounds.y;
               float right = bounds.z;
               float bottom = bounds.w;
               float width = right - left;
               float height = bottom - top;
               if (entity instanceof EntityPlayer player && player.getHeldItem() != null) {
                  ItemStack heldItem = player.getHeldItem();
                  float itemX = (left + width / 2.0F - 4.0F) * 2.0F;
                  float itemY = (top + height + 2.0F + (this.getEsp().getItemName().getValue() ? fontRenderer.getLineHeight() + 2.0F : 0.0F)) * 2.0F
                     + (this.getEsp().getArmorEnabled().getValue() ? 2.5F : 0.0F);
                  this.drawItem(heldItem, itemX + 1.0F, itemY + 1.0F, Integer.MIN_VALUE);
                  this.drawItem(heldItem, itemX, itemY, this.getEsp().getItemColor().getValue());
               }
            }
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.scale(2.0F, 2.0F, 1.0F);
         GlStateManager.disableAlpha();
         GlStateManager.disableRescaleNormal();
         GlStateManager.disableLighting();
         RenderHelper.disableStandardItemLighting();
         mc.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
         GlStateManager.bindTexture(0);
      }

      GlStateManager.enableBlend();
      if (this.rectTask != null) {
         this.rectTask.render();
      }

      if (this.nameTask != null) {
         this.nameTask.render();
      }

      if (this.gradientTask != null) {
         this.gradientTask.render();
      }

      if (this.textTask != null) {
         this.textTask.render();
      }
   }

   @Override
   public void rebuild() {
      FontRenderer fontRenderer = Moon.getInstance().getFontManager().getFont(FontType.VERDANA);
      MinecraftFontRenderer bitFontRenderer = Moon.getInstance().getBitFontManager().getDefaultRenderer();
      if (this.rectTask != null) {
         this.rectTask.getState().releaseGpuResources();
      }

      if (this.nameTask != null) {
         this.nameTask.getState().releaseGpuResources();
      }

      if (this.gradientTask != null) {
         this.gradientTask.getState().releaseGpuResources();
      }

      if (this.textTask != null) {
         this.textTask.getState().releaseGpuResources();
      }

      this.rectTask = RenderTask.builder().withRenderType(RenderType.RECTANGLE).withGpuBuffers().retainGpuBuffer().build();
      this.gradientTask = RenderTask.builder().withRenderType(RenderType.GRADIENT).withGpuBuffers().retainGpuBuffer().build();
      this.nameTask = RenderTask.builder().withRenderType(RenderType.MSDF).withFontRenderer(fontRenderer).withGpuBuffers().retainGpuBuffer().build();
      this.textTask = RenderTask.builder().withRenderType(RenderType.TEXTURE).withMinecraftFontRenderer(bitFontRenderer).withGpuBuffers().retainGpuBuffer().build();
      if (this.getEsp().isEnabled() && this.getEsp().getTwoD().getValue()) {
         for (Entity entity : mc.theWorld.loadedEntityList) {
            if (this.isValidTarget(entity) && FrustumUtil.isVisible(entity) && entity.Method940() != null) {
               for (EspEntityRenderer renderer : this.renderers) {
                  if (renderer.isEnabled(entity)) {
                     renderer.setRectTask(this.rectTask);
                     renderer.setNameTask(this.nameTask);
                     renderer.setGradientTask(this.gradientTask);
                     renderer.setTextTask(this.textTask);
                     renderer.render(entity);
                  }
               }
            }
         }
      }

      if (this.storageEsp.isEnabled() && this.storageEsp.getTwoD().getValue()) {
         for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (FrustumUtil.isVisible(tileEntity.Method981()) && tileEntity.Method982() != null) {
               for (EspEntityRenderer renderer : this.renderers) {
                  if (renderer instanceof EspBoxRenderer boxRenderer) {
                     renderer.setRectTask(this.rectTask);
                     renderer.setNameTask(this.nameTask);
                     renderer.setGradientTask(this.gradientTask);
                     renderer.setTextTask(this.textTask);
                     boxRenderer.renderTileEntity(tileEntity);
                  }
               }
            }
         }
      }
   }

   @Override
   public void renderEntities() {
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   private int getRefreshRate() {
      return Display.getDesktopDisplayMode().getFrequency();
   }

   private void drawItem(ItemStack itemStack, float x, float y, int color) {
      GlStateManager.colorMask(false, false, false, true);
      mc.getRenderItem().Method680(itemStack, x, y);
      GlStateManager.colorMask(true, true, true, true);
      GL11C.glDepthFunc(GL11C.GL_GREATER);
      LegacyGlUtil.drawFilledRect(x - 25.0F, y - 25.0F, 50.0F, 50.0F, color);
      GL11C.glDepthFunc(GL11C.GL_LEQUAL);
   }

   private void registerRenderers() {
      this.renderers.add(new EspBoxRenderer(this.getEsp(), this.storageEsp));
      this.renderers.add(new EspHealthBarRenderer(this.getEsp()));
      this.renderers.add(new EspArmorBarRenderer(this.getEsp()));
      this.renderers.add(new EspIndicatorRenderer(this.getEsp()));
      this.renderers.add(new EspEntityNameRenderer(this.getEsp()));
      this.renderers.add(new EspItemNameRenderer(this.getEsp()));
   }
}
