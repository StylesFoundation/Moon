package com.moon.module.impl.visual.esp.projection;

import com.moon.Moon;
import com.moon.events.impl.render.RenderEntityEvent;
import com.moon.module.impl.visual.ESP;
import com.moon.render.WorldRenderTask;
import com.moon.util.render.FrustumUtil;
import com.moon.util.render.LegacyGlUtil;
import com.moon.util.render.ProjectionUtil;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import org.lwjgl.util.vector.Vector4f;

public class EspProjectionTask implements WorldRenderTask {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private ESP esp;

   @Override
   public void run() {
      if (this.esp == null) {
         this.esp = (ESP)Moon.getInstance().getModuleManager().getModuleByName("ESP");
      }

      RenderManager renderManager = mc.getRenderManager();
      if (mc.theWorld != null) {
         for (Entity entity : mc.theWorld.loadedEntityList) {
            if (this.esp.isValidTarget(entity)) {
               float partialTicks = mc.timer.renderPartialTicks;
               double posX = LegacyGlUtil.lerp(entity.posX, entity.lastTickPosX, partialTicks);
               double posY = LegacyGlUtil.lerp(entity.posY, entity.lastTickPosY, partialTicks);
               double posZ = LegacyGlUtil.lerp(entity.posZ, entity.lastTickPosZ, partialTicks);
               float width = entity.width / 1.5F;
               double height = entity.height - (entity.isSneaking() ? 0.2 : 0.0);
               if (entity instanceof EntityPlayer) {
                  RenderEntityEvent event = new RenderEntityEvent(entity, false, false);
                  Moon.getInstance().getEventBus().publish(event);
                  if (event.isChild()) {
                     width *= 0.5F;
                     height *= 0.6F;
                  }
               }

               AxisAlignedBB bb = new AxisAlignedBB(posX - width, posY, posZ - width, posX + width, posY + height, posZ + width);
               if (FrustumUtil.isVisible(bb)) {
                  List<Vec3> corners = bb.Method908();
                  Vector4f screenCoords = null;

                  for (Vec3 corner : corners) {
                     corner = ProjectionUtil.project(
                        (float)(corner.xCoord - renderManager.Method588()), (float)(corner.yCoord - renderManager.Method589()), (float)(corner.zCoord - renderManager.Method590())
                     );
                     if (corner != null && corner.zCoord >= 0.0 && corner.zCoord <= 1.0) {
                        if (screenCoords == null) {
                           screenCoords = new Vector4f((float)corner.xCoord, (float)corner.yCoord, (float)corner.zCoord, (float)corner.yCoord);
                        }

                        screenCoords.x = (float)Math.min(corner.xCoord, screenCoords.x);
                        screenCoords.y = (float)Math.min(corner.yCoord, screenCoords.y);
                        screenCoords.z = (float)Math.max(corner.xCoord, screenCoords.z);
                        screenCoords.w = (float)Math.max(corner.yCoord, screenCoords.w);
                     }
                  }

                  entity.Method941(screenCoords);
               }
            }
         }

         for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
            if (tileEntity instanceof TileEntityChest || tileEntity instanceof TileEntityEnderChest) {
               AxisAlignedBB bb;
               if (tileEntity instanceof TileEntityChest) {
                  bb = this.getChestBoundingBox((TileEntityChest)tileEntity);
               } else {
                  bb = this.getEnderChestBoundingBox((TileEntityEnderChest)tileEntity);
               }

               if (FrustumUtil.isVisible(bb)) {
                  List<Vec3> corners = bb.Method908();
                  Vector4f screenCoords = null;

                  for (Vec3 corner : corners) {
                     corner = ProjectionUtil.project(
                        (float)(corner.xCoord - renderManager.Method588()), (float)(corner.yCoord - renderManager.Method589()), (float)(corner.zCoord - renderManager.Method590())
                     );
                     if (corner != null && corner.zCoord >= 0.0 && corner.zCoord <= 1.0) {
                        if (screenCoords == null) {
                           screenCoords = new Vector4f((float)corner.xCoord, (float)corner.yCoord, (float)corner.zCoord, (float)corner.yCoord);
                        }

                        screenCoords.x = (float)Math.min(corner.xCoord, screenCoords.x);
                        screenCoords.y = (float)Math.min(corner.yCoord, screenCoords.y);
                        screenCoords.z = (float)Math.max(corner.xCoord, screenCoords.z);
                        screenCoords.w = (float)Math.max(corner.yCoord, screenCoords.w);
                     }
                  }

                  tileEntity.Method983(screenCoords);
               }
            }
         }
      }
   }

   private AxisAlignedBB getChestBoundingBox(TileEntityChest chest) {
      BlockPos pos = chest.getPos();
      double x = pos.getX();
      double y = pos.getY();
      double z = pos.getZ();
      AxisAlignedBB bb = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(x, y, z);
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
         bb = bb.union(
            new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(adjacentChest.getPos().getX(), adjacentChest.getPos().getY(), adjacentChest.getPos().getZ())
         );
      }

      return bb;
   }

   private AxisAlignedBB getEnderChestBoundingBox(TileEntityEnderChest enderChest) {
      double x = enderChest.getPos().getX();
      double y = enderChest.getPos().getY();
      double z = enderChest.getPos().getZ();
      return new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.94, 0.875, 0.94).offset(x, y, z);
   }
}
