package com.moon.handlers.entitytrack;

import com.moon.Moon;
import com.moon.module.impl.combat.KillAura;
import com.moon.module.impl.visual.ESP;
import com.moon.module.impl.visual.StorageESP;
import com.moon.module.setting.impl.FloatSetting;
import com.moon.render.RenderTask;
import com.moon.render.RenderType;
import com.moon.render.color.RenderColor;
import com.moon.render.command.impl.world.WorldBoxCommand;
import com.moon.util.render.FrustumUtil;
import com.moon.util.render.LegacyGlUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class Box3dRenderPass extends EntityRenderPassBase {
   private RenderTask lineTask;
   private RenderTask fillTask;
   private KillAura killAura;
   private StorageESP storageEsp;

   public Box3dRenderPass(ESP esp, StorageESP storageEsp) {
      super(esp);
      this.storageEsp = storageEsp;
   }

   @Override
   public void draw() {
      GlStateManager.enableCull();
      GlStateManager.disableDepth();
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glLineWidth(0.5F);
      if (this.lineTask != null) {
         this.lineTask.render();
      }

      if ((this.getEsp().getBox3dOutline().getValue() || this.storageEsp.getBox3dOutline().getValue()) && this.fillTask != null) {
         this.fillTask.render();
      }

      GlStateManager.enableDepth();
   }

   @Override
   public void rebuild() {
      this.lineTask = this.buildBoxTask(this.lineTask, false);
      if (this.getEsp().getBox3dOutline().getValue() || this.storageEsp.getBox3dOutline().getValue()) {
         this.fillTask = this.buildBoxTask(this.fillTask, true);
      }
   }

   @Override
   public void renderEntities() {
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

   private RenderTask buildBoxTask(RenderTask task, boolean fill) {
      this.ensureKillAura();
      if (task != null) {
         task.getState().releaseGpuResources();
      }

      task = RenderTask.builder()
         .withDrawMode(fill ? 1 : 7)
         .withRenderType(RenderType.WORLD)
         .withActiveRenderMatrices()
         .retainGpuBuffer()
         .withGpuBuffers()
         .build();
      if (this.storageEsp.isEnabled() && this.storageEsp.getBox3d().getValue() && (!fill || this.storageEsp.getBox3dOutline().getValue())) {
         for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (FrustumUtil.isVisible(tileEntity.Method981()) && (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest)) {
               double renderX = mc.getRenderManager().Method588();
               double renderY = mc.getRenderManager().Method589();
               double renderZ = mc.getRenderManager().Method590();
               double posX = tileEntity.getPos().getX() - renderX;
               double posY = tileEntity.getPos().getY() - renderY;
               double posZ = tileEntity.getPos().getZ() - renderZ;
               AxisAlignedBB box = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(posX, posY, posZ);
               if (tileEntity instanceof TileEntityChest chest) {
                  TileEntityChest adjacentChest = null;
                  if (chest.adjacentChestXNeg != null) {
                     adjacentChest = chest.adjacentChestXNeg;
                  } else if (chest.adjacentChestXPos != null) {
                     adjacentChest = chest.adjacentChestXPos;
                  } else if (chest.adjacentChestZNeg != null) {
                     adjacentChest = chest.adjacentChestZNeg;
                  } else if (chest.adjacentChestZPos != null) {
                     adjacentChest = chest.adjacentChestZPos;
                  }

                  if (adjacentChest != null) {
                     box = box.union(
                        new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94)
                           .offset(adjacentChest.getPos().getX() - renderX, adjacentChest.getPos().getY() - renderY, adjacentChest.getPos().getZ() - renderZ)
                     );
                  }
               }

               task.addCommand(
                  WorldBoxCommand.of(
                     box,
                     new RenderColor(this.getTileEntityColor(tileEntity)).withAlphaFraction(fill ? 1.0F : this.storageEsp.getBox3dOpacity().getValue()).getRGB()
                  )
               );
            }
         }
      }

      if (this.getEsp().isEnabled() && this.getEsp().getBox3d().getValue() && (!fill || this.getEsp().getBox3dOutline().getValue())) {
         RenderColor normalColor = new RenderColor(this.getEsp().getBox3dNormalColor().getValue())
            .withAlphaFraction(fill ? 1.0F : this.getEsp().getBox3dOpacity().getValue());
         RenderColor targetColor = new RenderColor(this.getEsp().getBox3dTargetColor().getValue())
            .withAlphaFraction(fill ? 1.0F : this.getEsp().getBox3dOpacity().getValue());

         for (Entity entity : mc.theWorld.loadedEntityList) {
            if (this.isValidTarget(entity) && FrustumUtil.isVisible(entity)) {
               float partialTicks = mc.timer.renderPartialTicks;
               double interpolatedX = LegacyGlUtil.lerp(entity.posX, entity.lastTickPosX, partialTicks);
               double interpolatedY = LegacyGlUtil.lerp(entity.posY, entity.lastTickPosY, partialTicks);
               double interpolatedZ = LegacyGlUtil.lerp(entity.posZ, entity.lastTickPosZ, partialTicks);
               float entityWidth = entity.width;
               float entityHeight = entity.height + 0.1F;
               double renderManagerX = mc.getRenderManager().Method588();
               double renderManagerY = mc.getRenderManager().Method589();
               double renderManagerZ = mc.getRenderManager().Method590();
               FloatSetting widthExpansion = this.getEsp().getBox3dWidthExpansion();
               FloatSetting heightExpansion = this.getEsp().getBox3dHeightExpansion();
               AxisAlignedBB entityBox = new AxisAlignedBB(
                  interpolatedX - renderManagerX - entityWidth + 0.25 - widthExpansion.getValue().floatValue(),
                  interpolatedY - renderManagerY - heightExpansion.getValue().floatValue(),
                  interpolatedZ - renderManagerZ - entityWidth + 0.25 - widthExpansion.getValue().floatValue(),
                  interpolatedX - renderManagerX + entityWidth - 0.25 + widthExpansion.getValue().floatValue(),
                  interpolatedY - renderManagerY + entityHeight + heightExpansion.getValue().floatValue(),
                  interpolatedZ - renderManagerZ + entityWidth - 0.25 + widthExpansion.getValue().floatValue()
               );
               task.addCommand(
                  WorldBoxCommand.of(entityBox, (this.killAura.isEnabled() && this.killAura.getTargetSelector().getTarget() == entity ? targetColor : normalColor).getRGB())
               );
            }
         }
      }

      return task;
   }

   private void ensureKillAura() {
      if (this.killAura == null) {
         this.killAura = Moon.getInstance().getModuleManager().getModule(KillAura.class);
      }
   }

   public int getTileEntityColor(TileEntity tileEntity) {
      if (tileEntity instanceof TileEntityChest chest) {
         return chest.getChestType() == 1 ? this.storageEsp.getBox3dRedstoneColor().getValue() : this.storageEsp.getBox3dNormalColor().getValue();
      } else {
         return tileEntity instanceof TileEntityEnderChest ? this.storageEsp.getBox3dEnderColor().getValue() : -1;
      }
   }
}
