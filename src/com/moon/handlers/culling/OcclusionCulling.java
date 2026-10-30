package com.moon.handlers.culling;

import com.moon.util.math.Position;
import java.util.Arrays;
import java.util.BitSet;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class OcclusionCulling {
   private static final Minecraft mc = Minecraft.getMinecraft();
   private final int cacheRadius;
   private final double blockExpand;
   private final OcclusionCache cache;
   private final BitSet resolvedBlocks = new BitSet();
   private final Position[] samplePoints = new Position[15];
   private final Position blockCorner = new Position(0.0, 0.0, 0.0);
   private final int[] cameraBlockPos = new int[3];
   private final boolean[] activeSamplePoints = new boolean[14];
   private boolean hasLastHit = false;
   private final int[] lastHitBlockPos = new int[3];

   public OcclusionCulling(int cacheRadius) {
      this(cacheRadius, new PackedOcclusionCache(cacheRadius), 0.1);
   }

   public OcclusionCulling(int cacheRadius, OcclusionCache cache, double blockExpand) {
      this.cacheRadius = cacheRadius;
      this.cache = cache;
      this.blockExpand = blockExpand;

      for (int i = 0; i < this.samplePoints.length; i++) {
         this.samplePoints[i] = new Position(0.0, 0.0, 0.0);
      }
   }

   public boolean isOccluded(Position min, Position max, Position camera) {
      try {
         int maxX = MathHelper.floor_double(max.x + this.blockExpand);
         int maxY = MathHelper.floor_double(max.y + this.blockExpand);
         int maxZ = MathHelper.floor_double(max.z + this.blockExpand);
         int minX = MathHelper.floor_double(min.x - this.blockExpand);
         int minY = MathHelper.floor_double(min.y - this.blockExpand);
         int minZ = MathHelper.floor_double(min.z - this.blockExpand);
         this.cameraBlockPos[0] = MathHelper.floor_double(camera.x);
         this.cameraBlockPos[1] = MathHelper.floor_double(camera.y);
         this.cameraBlockPos[2] = MathHelper.floor_double(camera.z);
         IntervalRelation relationX = IntervalRelation.classify(minX, maxX, this.cameraBlockPos[0]);
         IntervalRelation relationY = IntervalRelation.classify(minY, maxY, this.cameraBlockPos[1]);
         IntervalRelation relationZ = IntervalRelation.classify(minZ, maxZ, this.cameraBlockPos[2]);
         if (relationX == IntervalRelation.INSIDE && relationY == IntervalRelation.INSIDE && relationZ == IntervalRelation.INSIDE) {
            return true;
         }

         this.resolvedBlocks.clear();
         int blockIndex = 0;

         for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
               for (int z = minZ; z <= maxZ; z++) {
                  int cacheState = this.getCacheState(x, y, z);
                  if (cacheState == 1) {
                     return true;
                  }

                  if (cacheState != 0) {
                     this.resolvedBlocks.set(blockIndex);
                  }

                  blockIndex++;
               }
            }
         }

         this.hasLastHit = false;
         blockIndex = 0;

         for (int x = minX; x <= maxX; x++) {
            byte activeEdgesX = 0;
            byte edgeMaskX = (byte)(0 | (x == minX ? 1 : 0));
            edgeMaskX = (byte)(edgeMaskX | (x == maxX ? 2 : 0));
            activeEdgesX = (byte)(activeEdgesX | (x == minX && relationX == IntervalRelation.POSITIVE ? 1 : 0));
            activeEdgesX = (byte)(activeEdgesX | (x == maxX && relationX == IntervalRelation.NEGATIVE ? 2 : 0));

            for (int y = minY; y <= maxY; y++) {
               byte edgeMaskXY = edgeMaskX;
               byte activeEdgesXY = activeEdgesX;
               edgeMaskXY = (byte)(edgeMaskXY | (y == minY ? 4 : 0));
               edgeMaskXY = (byte)(edgeMaskXY | (y == maxY ? 8 : 0));
               activeEdgesXY = (byte)(activeEdgesXY | (y == minY && relationY == IntervalRelation.POSITIVE ? 4 : 0));
               activeEdgesXY = (byte)(activeEdgesXY | (y == maxY && relationY == IntervalRelation.NEGATIVE ? 8 : 0));

               for (int z = minZ; z <= maxZ; z++) {
                  byte edgeMask = edgeMaskXY;
                  byte activeEdges = activeEdgesXY;
                  edgeMask = (byte)(edgeMask | (z == minZ ? 16 : 0));
                  edgeMask = (byte)(edgeMask | (z == maxZ ? 32 : 0));
                  activeEdges = (byte)(activeEdges | (z == minZ && relationZ == IntervalRelation.POSITIVE ? 16 : 0));
                  activeEdges = (byte)(activeEdges | (z == maxZ && relationZ == IntervalRelation.NEGATIVE ? 32 : 0));
                  if (this.resolvedBlocks.get(blockIndex)) {
                     blockIndex++;
                  } else {
                     if (activeEdges != 0) {
                        this.blockCorner.set(x, y, z);
                        if (this.checkEdgeSamplePoints(camera, this.blockCorner, edgeMask, activeEdges)) {
                           return true;
                        }
                     }

                     blockIndex++;
                  }
               }
            }
         }

         return false;
      } catch (Throwable t) {
         t.printStackTrace();
         return true;
      }
   }

   private boolean checkEdgeSamplePoints(Position camera, Position blockCorner, byte edgeMask, byte activeEdges) {
      int sampleCount = 0;
      Arrays.fill(this.activeSamplePoints, false);
      if ((activeEdges & 1) == 1) {
         this.activeSamplePoints[0] = true;
         if ((edgeMask & -2) != 0) {
            this.activeSamplePoints[1] = true;
            this.activeSamplePoints[4] = true;
            this.activeSamplePoints[5] = true;
         }

         this.activeSamplePoints[8] = true;
      }

      if ((activeEdges & 4) == 4) {
         this.activeSamplePoints[0] = true;
         if ((edgeMask & -5) != 0) {
            this.activeSamplePoints[3] = true;
            this.activeSamplePoints[4] = true;
            this.activeSamplePoints[7] = true;
         }

         this.activeSamplePoints[9] = true;
      }

      if ((activeEdges & 16) == 16) {
         this.activeSamplePoints[0] = true;
         if ((edgeMask & -17) != 0) {
            this.activeSamplePoints[1] = true;
            this.activeSamplePoints[4] = true;
            this.activeSamplePoints[5] = true;
         }

         this.activeSamplePoints[10] = true;
      }

      if ((activeEdges & 2) == 2) {
         this.activeSamplePoints[4] = true;
         if ((edgeMask & -3) != 0) {
            this.activeSamplePoints[5] = true;
            this.activeSamplePoints[6] = true;
            this.activeSamplePoints[7] = true;
         }

         this.activeSamplePoints[11] = true;
      }

      if ((activeEdges & 8) == 8) {
         this.activeSamplePoints[1] = true;
         if ((edgeMask & -9) != 0) {
            this.activeSamplePoints[2] = true;
            this.activeSamplePoints[5] = true;
            this.activeSamplePoints[6] = true;
         }

         this.activeSamplePoints[12] = true;
      }

      if ((activeEdges & 32) == 32) {
         this.activeSamplePoints[2] = true;
         if ((edgeMask & -33) != 0) {
            this.activeSamplePoints[3] = true;
            this.activeSamplePoints[6] = true;
            this.activeSamplePoints[7] = true;
         }

         this.activeSamplePoints[13] = true;
      }

      if (this.activeSamplePoints[0]) {
         sampleCount++;
         this.samplePoints[0].setFromOffset(blockCorner, 0.05, 0.05, 0.05);
      }

      if (this.activeSamplePoints[1]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.05, 0.95, 0.05);
      }

      if (this.activeSamplePoints[2]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.05, 0.95, 0.95);
      }

      if (this.activeSamplePoints[3]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.05, 0.05, 0.95);
      }

      if (this.activeSamplePoints[4]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.95, 0.05, 0.05);
      }

      if (this.activeSamplePoints[5]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.95, 0.95, 0.05);
      }

      if (this.activeSamplePoints[6]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.95, 0.95, 0.95);
      }

      if (this.activeSamplePoints[7]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.95, 0.05, 0.95);
      }

      if (this.activeSamplePoints[8]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.05, 0.5, 0.5);
      }

      if (this.activeSamplePoints[9]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.5, 0.05, 0.5);
      }

      if (this.activeSamplePoints[10]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.5, 0.5, 0.05);
      }

      if (this.activeSamplePoints[11]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.95, 0.5, 0.5);
      }

      if (this.activeSamplePoints[12]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.5, 0.95, 0.5);
      }

      if (this.activeSamplePoints[13]) {
         this.samplePoints[sampleCount++].setFromOffset(blockCorner, 0.5, 0.5, 0.95);
      }

      return this.castRays(camera, this.samplePoints, sampleCount);
   }

   private boolean rayIntersectsBlock(int[] blockPos, Position rayOrigin, Position rayDirection) {
      Position invDir = new Position(1.0, 1.0, 1.0).divide(rayDirection);
      double tMinX = (blockPos[0] - rayOrigin.x) * invDir.x;
      double tMaxX = (blockPos[0] + 1 - rayOrigin.x) * invDir.x;
      double tMinY = (blockPos[1] - rayOrigin.y) * invDir.y;
      double tMaxY = (blockPos[1] + 1 - rayOrigin.y) * invDir.y;
      double tMinZ = (blockPos[2] - rayOrigin.z) * invDir.z;
      double tMaxZ = (blockPos[2] + 1 - rayOrigin.z) * invDir.z;
      double tNear = Math.max(Math.max(Math.min(tMinX, tMaxX), Math.min(tMinY, tMaxY)), Math.min(tMinZ, tMaxZ));
      double tFar = Math.min(Math.min(Math.max(tMinX, tMaxX), Math.max(tMinY, tMaxY)), Math.max(tMinZ, tMaxZ));
      return tFar > 0.0 ? false : !(tNear > tFar);
   }

   private boolean castRays(Position camera, Position[] samplePoints, int sampleCount) {
      int camBlockX = this.cameraBlockPos[0];
      int camBlockY = this.cameraBlockPos[1];
      int camBlockZ = this.cameraBlockPos[2];

      for (int i = 0; i < sampleCount; i++) {
         Position samplePoint = samplePoints[i];
         double dx = camera.x - samplePoint.getX();
         double dy = camera.y - samplePoint.getY();
         double dz = camera.z - samplePoint.getZ();
         if (!this.hasLastHit || !this.rayIntersectsBlock(this.lastHitBlockPos, camera, new Position(dx, dy, dz).normalize())) {
            double absDx = Math.abs(dx);
            double absDy = Math.abs(dy);
            double absDz = Math.abs(dz);
            double invDx = 1.0 / absDx;
            double invDy = 1.0 / absDy;
            double invDz = 1.0 / absDz;
            int steps = 1;
            byte stepX;
            double nextX;
            if (absDx == 0.0) {
               stepX = 0;
               nextX = invDx;
            } else if (samplePoint.x > camera.x) {
               stepX = 1;
               steps = 1 + (MathHelper.floor_double(samplePoint.x) - camBlockX);
               nextX = (float)((camBlockX + 1 - camera.x) * invDx);
            } else {
               stepX = -1;
               steps += camBlockX - MathHelper.floor_double(samplePoint.x);
               nextX = (float)((camera.x - camBlockX) * invDx);
            }

            byte stepY;
            double nextY;
            if (absDy == 0.0) {
               stepY = 0;
               nextY = invDy;
            } else if (samplePoint.y > camera.y) {
               stepY = 1;
               steps += MathHelper.floor_double(samplePoint.y) - camBlockY;
               nextY = (float)((camBlockY + 1 - camera.y) * invDy);
            } else {
               stepY = -1;
               steps += camBlockY - MathHelper.floor_double(samplePoint.y);
               nextY = (float)((camera.y - camBlockY) * invDy);
            }

            byte stepZ;
            double nextZ;
            if (absDz == 0.0) {
               stepZ = 0;
               nextZ = invDz;
            } else if (samplePoint.z > camera.z) {
               stepZ = 1;
               steps += MathHelper.floor_double(samplePoint.z) - camBlockZ;
               nextZ = (float)((camBlockZ + 1 - camera.z) * invDz);
            } else {
               stepZ = -1;
               steps += camBlockZ - MathHelper.floor_double(samplePoint.z);
               nextZ = (float)((camera.z - camBlockZ) * invDz);
            }

            boolean visible = this.traceRay(camera, camBlockX, camBlockY, camBlockZ, invDx, invDy, invDz, steps, stepX, stepY, stepZ, nextY, nextX, nextZ);
            if (visible) {
               this.markPosition(samplePoints[0], true);
               return true;
            }

            this.hasLastHit = true;
         }
      }

      this.markPosition(samplePoints[0], false);
      return false;
   }

   private boolean traceRay(
      Position camera,
      int blockX,
      int blockY,
      int blockZ,
      double invDx,
      double invDy,
      double invDz,
      int steps,
      int stepX,
      int stepY,
      int stepZ,
      double nextY,
      double nextX,
      double nextZ
   ) {
      while (steps > 1) {
         int cacheState = this.getCacheState(blockX, blockY, blockZ);
         if (cacheState == 2) {
            this.lastHitBlockPos[0] = blockX;
            this.lastHitBlockPos[1] = blockY;
            this.lastHitBlockPos[2] = blockZ;
            return false;
         }

         if (cacheState == 0) {
            int chunkCoord = blockX >> 4;
            chunkCoord = blockZ >> 4;
            if (mc.theWorld == null) {
               return false;
            }

            BlockPos pos = new BlockPos(blockX, blockY, blockZ);
            if (mc.theWorld.getBlockState(pos).getBlock().isOpaqueCube()) {
               this.cache.repeatOpaque();
               this.lastHitBlockPos[0] = blockX;
               this.lastHitBlockPos[1] = blockY;
               this.lastHitBlockPos[2] = blockZ;
               return false;
            }

            this.cache.repeatVisible();
         }

         if (nextY < nextX && nextY < nextZ) {
            blockY += stepY;
            nextY += invDy;
         } else if (nextX < nextY && nextX < nextZ) {
            blockX += stepX;
            nextX += invDx;
         } else {
            blockZ += stepZ;
            nextZ += invDz;
         }

         steps--;
      }

      return true;
   }

   private int getCacheState(int x, int y, int z) {
      x -= this.cameraBlockPos[0];
      y -= this.cameraBlockPos[1];
      z -= this.cameraBlockPos[2];
      return Math.abs(x) <= this.cacheRadius - 2 && Math.abs(y) <= this.cacheRadius - 2 && Math.abs(z) <= this.cacheRadius - 2
         ? this.cache.getState(x + this.cacheRadius, y + this.cacheRadius, z + this.cacheRadius)
         : -1;
   }

   private void setBlockState(int x, int y, int z, boolean visible) {
      int cacheX = x - this.cameraBlockPos[0] + this.cacheRadius;
      int cacheY = y - this.cameraBlockPos[1] + this.cacheRadius;
      int cacheZ = z - this.cameraBlockPos[2] + this.cacheRadius;
      if (visible) {
         this.cache.markVisible(cacheX, cacheY, cacheZ);
      } else {
         this.cache.markOpaque(cacheX, cacheY, cacheZ);
      }
   }

   private void markPosition(Position position, boolean visible) {
      int cacheX = MathHelper.floor_double(position.x) - this.cameraBlockPos[0] + this.cacheRadius;
      int cacheY = MathHelper.floor_double(position.y) - this.cameraBlockPos[1] + this.cacheRadius;
      int cacheZ = MathHelper.floor_double(position.z) - this.cameraBlockPos[2] + this.cacheRadius;
      if (visible) {
         this.cache.markVisible(cacheX, cacheY, cacheZ);
      } else {
         this.cache.markOpaque(cacheX, cacheY, cacheZ);
      }
   }

   public void resetCache() {
      this.cache.clear();
   }
}
