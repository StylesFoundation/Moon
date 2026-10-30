package com.moon.handlers.culling;

import com.moon.util.math.Position;
import java.util.ConcurrentModificationException;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class EntityVisibilityTask implements Runnable {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final OcclusionCulling occlusionCulling = new OcclusionCulling(128);

   @Override
   public void run() {
      try {
         Thread.sleep(10L);
         if (mc.theWorld != null && mc.thePlayer != null) {
            this.occlusionCulling.resetCache();
            this.cullEntities();
            this.cullTileEntities();
         }
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   private void cullEntities() {
      boolean isSpectator = mc.thePlayer.isSpectator();

      for (Entity entity : mc.theWorld.loadedEntityList) {
         try {
            ;
         } catch (NullPointerException | ConcurrentModificationException e) {
            break;
         }

         if (entity != null) {
            if (!isSpectator && !(entity instanceof EntityArmorStand)) {
               if (entity.getDistanceToEntity(mc.thePlayer) > 128.0F) {
                  entity.aC = false;
               } else {
                  AxisAlignedBB bb = entity.getEntityBoundingBox();
                  if (!(bb.maxX - bb.minX > 50.0) && !(bb.maxY - bb.minY > 50.0) && !(bb.maxZ - bb.minZ > 50.0)) {
                     Position min = new Position(bb.minX, bb.minY, bb.minZ);
                     Position max = new Position(bb.maxX, bb.maxY, bb.maxZ);
                     boolean occluded = this.occlusionCulling
                        .isOccluded(
                           min,
                           max,
                           new Position(
                              mc.getRenderManager().Method588(),
                              mc.getRenderManager().Method589() + mc.thePlayer.getEyeHeight(),
                              mc.getRenderManager().Method590()
                           )
                        );
                     entity.aC = !occluded;
                  } else {
                     entity.aC = false;
                  }
               }
            } else {
               entity.aC = false;
            }
         }
      }
   }

   private void cullTileEntities() {
      boolean isSpectator = mc.thePlayer.isSpectator();

      for (TileEntity tileEntity : mc.theWorld.loadedTileEntityList) {
         try {
            ;
         } catch (NullPointerException | ConcurrentModificationException e) {
            break;
         }

         if (tileEntity != null && tileEntity != null) {
            if (isSpectator) {
               tileEntity.i = false;
            } else if (tileEntity.getDistanceSq(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ) > 128.0) {
               tileEntity.i = false;
            } else {
               BlockPos pos = tileEntity.getPos();
               AxisAlignedBB bb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ());
               if (!(bb.maxX - bb.minX > 50.0) && !(bb.maxY - bb.minY > 50.0) && !(bb.maxZ - bb.minZ > 50.0)) {
                  Position min = new Position(bb.minX, bb.minY, bb.minZ);
                  Position max = new Position(bb.maxX, bb.maxY, bb.maxZ);
                  boolean occluded = this.occlusionCulling
                     .isOccluded(
                        min,
                        max,
                        new Position(
                           mc.getRenderManager().Method588(),
                           mc.getRenderManager().Method589() + mc.thePlayer.getEyeHeight(),
                           mc.getRenderManager().Method590()
                        )
                     );
                  tileEntity.i = !occluded;
               } else {
                  tileEntity.i = false;
               }
            }
         }
      }
   }
}
