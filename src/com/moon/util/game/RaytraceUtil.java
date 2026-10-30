package com.moon.util.game;

import com.google.common.base.Predicates;
import com.moon.util.math.Vector2f;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.realms.Tezzelator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class RaytraceUtil {
   private static final Minecraft mc = Minecraft.getMinecraft();

   public static boolean isVisible(Vector2f rotations, EnumFacing facing, BlockPos pos) {
      MovingObjectPosition mop = raytraceWithEntities(rotations.getX(), rotations.getY(), 4.5F, 1.0F);
      if (mop == null) {
         return false;
      }

      Vec3 hitVec = mop.hitVec;
      if (hitVec == null) {
         return false;
      }

      BlockPos hitPos = mop.getBlockPos();
      if (hitPos == null) {
         return false;
      }

      boolean posMatches = hitPos.equals(pos);
      boolean facingMatches = mop.sideHit == facing;
      return posMatches && facingMatches;
   }

   public static boolean isVisible(Vec3 start, Vector2f rotations, EnumFacing facing, BlockPos pos) {
      MovingObjectPosition mop = raytraceWithEntities(start, rotations.getX(), rotations.getY(), 4.5F, 1.0F);
      if (mop == null) {
         return false;
      }

      Vec3 hitVec = mop.hitVec;
      if (hitVec == null) {
         return false;
      }

      BlockPos hitPos = mop.getBlockPos();
      if (hitPos == null) {
         return false;
      }

      boolean posMatches = hitPos.equals(pos);
      boolean facingMatches = mop.sideHit == facing;
      return posMatches && facingMatches;
   }

   public static Vec3 getLookVector(float yaw, float pitch) {
      float cosYaw = MathHelper.cos(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float sinYaw = MathHelper.sin(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float cosPitch = -MathHelper.cos(-pitch * (float) (Math.PI / 180.0));
      float sinPitch = MathHelper.sin(-pitch * (float) (Math.PI / 180.0));
      return new Vec3(sinYaw * cosPitch, sinPitch, cosYaw * cosPitch);
   }

   public static MovingObjectPosition raytrace(Vec3 start, float yaw, float pitch, float distance) {
      Vec3 direction = getLookVector(yaw, pitch);
      return raytrace(start, direction, distance);
   }

   public static MovingObjectPosition raytrace(Vec3 start, Vec3 direction, float distance) {
      return raytraceBlocks(start, start.add(direction.normalize().Method759(distance)));
   }

   public static MovingObjectPosition raytraceBlocks(Vec3 start, Vec3 end) {
      return mc.theWorld.rayTraceBlocks(start, end, false);
   }

   public static MovingObjectPosition raytraceWithEntities(Vec3 start, float yaw, float pitch, float distance, float partialTicks) {
      Entity pointedEntity = null;
      MovingObjectPosition raytraceResult = raytrace(start, yaw, pitch, distance);
      double blockHitDist = distance;
      if (raytraceResult != null) {
         blockHitDist = raytraceResult.hitVec.distanceTo(start);
      }

      Vec3 lookVec = getLookVector(yaw, pitch);
      Vec3 reachVec = start.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
      Vec3 hitVec = null;
      List<Entity> entities = mc.theWorld
         .getEntitiesInAABBexcluding(
            mc.thePlayer,
            mc.thePlayer.getEntityBoundingBox().addCoord(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance).expand(1.0, 1.0, 1.0),
            Predicates.and(Tezzelator.d, Entity::canBeCollidedWith)
         );
      double closestEntityDist = blockHitDist;

      for (Entity entity : entities) {
         float borderSize = entity.getCollisionBorderSize();
         AxisAlignedBB entityBB = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
         MovingObjectPosition intercept = entityBB.calculateIntercept(start, reachVec);
         if (entityBB.isVecInside(start)) {
            if (closestEntityDist >= 0.0) {
               pointedEntity = entity;
               hitVec = intercept == null ? start : intercept.hitVec;
               closestEntityDist = 0.0;
            }
         } else if (intercept != null) {
            double distToIntercept = start.distanceTo(intercept.hitVec);
            if (distToIntercept < closestEntityDist || closestEntityDist == 0.0) {
               if (entity == entity.ridingEntity) {
                  if (closestEntityDist == 0.0) {
                     pointedEntity = entity;
                     hitVec = intercept.hitVec;
                  }
               } else {
                  pointedEntity = entity;
                  hitVec = intercept.hitVec;
                  closestEntityDist = distToIntercept;
               }
            }
         }
      }

      if (pointedEntity != null && (closestEntityDist < blockHitDist || raytraceResult == null)) {
         raytraceResult = new MovingObjectPosition(pointedEntity, hitVec);
      }

      return raytraceResult;
   }

   public static MovingObjectPosition raytraceWithEntities(float yaw, float pitch, float distance, float partialTicks) {
      Vec3 eyes = mc.thePlayer.getPositionEyes(partialTicks);
      Entity pointedEntity = null;
      MovingObjectPosition raytraceResult = raytrace(eyes, yaw, pitch, distance);
      double blockHitDist = distance;
      if (raytraceResult != null) {
         blockHitDist = raytraceResult.hitVec.distanceTo(eyes);
      }

      Vec3 lookVec = getLookVector(yaw, pitch);
      Vec3 reachVec = eyes.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
      Vec3 hitVec = null;
      List<Entity> entities = mc.theWorld
         .getEntitiesInAABBexcluding(
            mc.thePlayer,
            mc.thePlayer.getEntityBoundingBox().addCoord(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance).expand(1.0, 1.0, 1.0),
            Predicates.and(Tezzelator.d, Entity::canBeCollidedWith)
         );
      double closestEntityDist = blockHitDist;

      for (Entity entity : entities) {
         float borderSize = entity.getCollisionBorderSize();
         AxisAlignedBB entityBB = entity.getEntityBoundingBox().expand(borderSize, borderSize, borderSize);
         MovingObjectPosition intercept = entityBB.calculateIntercept(eyes, reachVec);
         if (entityBB.isVecInside(eyes)) {
            if (closestEntityDist >= 0.0) {
               pointedEntity = entity;
               hitVec = intercept == null ? eyes : intercept.hitVec;
               closestEntityDist = 0.0;
            }
         } else if (intercept != null) {
            double distToIntercept = eyes.distanceTo(intercept.hitVec);
            if (distToIntercept < closestEntityDist || closestEntityDist == 0.0) {
               if (entity == entity.ridingEntity) {
                  if (closestEntityDist == 0.0) {
                     pointedEntity = entity;
                     hitVec = intercept.hitVec;
                  }
               } else {
                  pointedEntity = entity;
                  hitVec = intercept.hitVec;
                  closestEntityDist = distToIntercept;
               }
            }
         }
      }

      if (pointedEntity != null && (closestEntityDist < blockHitDist || raytraceResult == null)) {
         raytraceResult = new MovingObjectPosition(pointedEntity, hitVec);
      }

      return raytraceResult;
   }
}
