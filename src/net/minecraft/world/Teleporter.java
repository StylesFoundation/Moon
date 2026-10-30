package net.minecraft.world;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;

public class Teleporter {
   private final WorldServer worldServerInstance;
   private final Random random;
   private final LongHashMap<Teleporter.PortalPosition> destinationCoordinateCache = new LongHashMap<>();
   private final List<Long> destinationCoordinateKeys = Lists.newArrayList();

   public Teleporter(WorldServer worldServer) {
      this.worldServerInstance = worldServer;
      this.random = new Random(worldServer.getSeed());
   }

   public void placeInPortal(Entity entity, float float2) {
      if (this.worldServerInstance.provider.getDimensionId() != 1) {
         if (!this.placeInExistingPortal(entity, float2)) {
            this.makePortal(entity);
            this.placeInExistingPortal(entity, float2);
         }
      } else {
         int var6 = MathHelper.floor_double(entity.posX);
         int var7 = MathHelper.floor_double(entity.posY) - 1;
         int var8 = MathHelper.floor_double(entity.posZ);

         for (int var11 = -2; var11 <= 2; var11++) {
            for (int var12 = -2; var12 <= 2; var12++) {
               for (int var13 = -1; var13 < 3; var13++) {
                  int var14 = var6 + var12 * 1 + var11 * 0;
                  int var15 = var7 + var13;
                  int var16 = var8 + var12 * 0 - var11 * 1;
                  boolean var17 = var13 < 0;
                  this.worldServerInstance
                     .setBlockState(new BlockPos(var14, var15, var16), var17 ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
               }
            }
         }

         entity.setLocationAndAngles(var6, var7, var8, entity.rotationYaw, 0.0F);
         entity.motionX = entity.motionY = entity.motionZ = 0.0;
      }
   }

   public boolean placeInExistingPortal(Entity entity, float float2) {
      double var7 = -1.0;
      int var9 = MathHelper.floor_double(entity.posX);
      int var10 = MathHelper.floor_double(entity.posZ);
      boolean var11 = true;
      BlockPos var12 = BlockPos.ORIGIN;
      long var13 = ChunkCoordIntPair.chunkXZ2Int(var9, var10);
      if (this.destinationCoordinateCache.containsItem(var13)) {
         Teleporter.PortalPosition var15 = this.destinationCoordinateCache.getValueByKey(var13);
         var7 = 0.0;
         var12 = var15;
         var15.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
         var11 = false;
      } else {
         BlockPos var33 = new BlockPos(entity);

         for (int var16 = -128; var16 <= 128; var16++) {
            for (int var18 = -128; var18 <= 128; var18++) {
               BlockPos var19 = var33.add(var16, this.worldServerInstance.getActualHeight() - 1 - var33.getY(), var18);

               while (var19.getY() >= 0) {
                  BlockPos var17 = var19.down();
                  if (this.worldServerInstance.getBlockState(var19).getBlock() == Blocks.portal) {
                     while (this.worldServerInstance.getBlockState(var17 = var19.down()).getBlock() == Blocks.portal) {
                        var19 = var17;
                     }

                     double var20 = var19.distanceSq(var33);
                     if (var7 < 0.0 || var20 < var7) {
                        var7 = var20;
                        var12 = var19;
                     }
                  }

                  var19 = var17;
               }
            }
         }
      }

      if (var7 >= 0.0) {
         if (var11) {
            this.destinationCoordinateCache.add(var13, new Teleporter.PortalPosition(var12, this.worldServerInstance.getTotalWorldTime()));
            this.destinationCoordinateKeys.add(var13);
         }

         double var34 = var12.getX() + 0.5;
         double var10000 = var12.getY() + 0.5;
         double var36 = var12.getZ() + 0.5;
         BlockPattern.PatternHelper var21 = Blocks.portal.func_181089_f(this.worldServerInstance, var12);
         boolean var22 = var21.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE;
         double var23 = var21.getFinger().getAxis() == EnumFacing.Axis.X ? var21.getPos().getZ() : var21.getPos().getX();
         double var35 = var21.getPos().getY() + 1 - entity.func_181014_aG().yCoord * var21.func_181119_e();
         if (var22) {
            var23++;
         }

         if (var21.getFinger().getAxis() == EnumFacing.Axis.X) {
            var36 = var23 + (1.0 - entity.func_181014_aG().xCoord) * var21.func_181118_d() * var21.getFinger().rotateY().getAxisDirection().getOffset();
         } else {
            var34 = var23 + (1.0 - entity.func_181014_aG().xCoord) * var21.func_181118_d() * var21.getFinger().rotateY().getAxisDirection().getOffset();
         }

         float var25 = 0.0F;
         float var26 = 0.0F;
         float var27 = 0.0F;
         float var28 = 0.0F;
         if (var21.getFinger().getOpposite() == entity.getTeleportDirection()) {
            var25 = 1.0F;
            var26 = 1.0F;
         } else if (var21.getFinger().getOpposite() == entity.getTeleportDirection().getOpposite()) {
            var25 = -1.0F;
            var26 = -1.0F;
         } else if (var21.getFinger().getOpposite() == entity.getTeleportDirection().rotateY()) {
            var27 = 1.0F;
            var28 = -1.0F;
         } else {
            var27 = -1.0F;
            var28 = 1.0F;
         }

         double var29 = entity.motionX;
         double var31 = entity.motionZ;
         entity.motionX = var29 * var25 + var31 * var28;
         entity.motionZ = var29 * var27 + var31 * var26;
         entity.rotationYaw = float2 - entity.getTeleportDirection().getOpposite().getHorizontalIndex() * 90 + var21.getFinger().getHorizontalIndex() * 90;
         entity.setLocationAndAngles(var34, var35, var36, entity.rotationYaw, entity.rotationPitch);
         return true;
      } else {
         return false;
      }
   }

   public boolean makePortal(Entity entity) {
      byte var5 = 16;
      double var6 = -1.0;
      int var8 = MathHelper.floor_double(entity.posX);
      int var9 = MathHelper.floor_double(entity.posY);
      int var10 = MathHelper.floor_double(entity.posZ);
      int var11 = var8;
      int var12 = var9;
      int var13 = var10;
      int var14 = 0;
      int var15 = this.random.nextInt(4);
      BlockPos.MutableBlockPos var16 = new BlockPos.MutableBlockPos();

      for (int var17 = var8 - var5; var17 <= var8 + var5; var17++) {
         double var18 = var17 + 0.5 - entity.posX;

         for (int var20 = var10 - var5; var20 <= var10 + var5; var20++) {
            double var21 = var20 + 0.5 - entity.posZ;

            label301:
            for (int var23 = this.worldServerInstance.getActualHeight() - 1; var23 >= 0; var23--) {
               if (this.worldServerInstance.isAirBlock(var16.set(var17, var23, var20))) {
                  while (var23 > 0 && this.worldServerInstance.isAirBlock(var16.set(var17, var23 - 1, var20))) {
                     var23--;
                  }

                  for (int var24 = var15; var24 < var15 + 4; var24++) {
                     int var25 = var24 % 2;
                     int var26 = 1 - var25;
                     if (var24 % 4 >= 2) {
                        var25 = -var25;
                        var26 = -var26;
                     }

                     for (int var27 = 0; var27 < 3; var27++) {
                        for (int var28 = 0; var28 < 4; var28++) {
                           for (int var29 = -1; var29 < 4; var29++) {
                              int var30 = var17 + (var28 - 1) * var25 + var27 * var26;
                              int var31 = var23 + var29;
                              int var32 = var20 + (var28 - 1) * var26 - var27 * var25;
                              var16.set(var30, var31, var32);
                              if (var29 < 0 && !this.worldServerInstance.getBlockState(var16).getBlock().getMaterial().isSolid()
                                 || var29 >= 0 && !this.worldServerInstance.isAirBlock(var16)) {
                                 continue label301;
                              }
                           }
                        }
                     }

                     double var57 = var23 + 0.5 - entity.posY;
                     double var67 = var18 * var18 + var57 * var57 + var21 * var21;
                     if (var6 < 0.0 || var67 < var6) {
                        var6 = var67;
                        var11 = var17;
                        var12 = var23;
                        var13 = var20;
                        var14 = var24 % 4;
                     }
                  }
               }
            }
         }
      }

      if (var6 < 0.0) {
         for (int var33 = var8 - var5; var33 <= var8 + var5; var33++) {
            double var35 = var33 + 0.5 - entity.posX;

            for (int var37 = var10 - var5; var37 <= var10 + var5; var37++) {
               double var39 = var37 + 0.5 - entity.posZ;

               label238:
               for (int var42 = this.worldServerInstance.getActualHeight() - 1; var42 >= 0; var42--) {
                  if (this.worldServerInstance.isAirBlock(var16.set(var33, var42, var37))) {
                     while (var42 > 0 && this.worldServerInstance.isAirBlock(var16.set(var33, var42 - 1, var37))) {
                        var42--;
                     }

                     for (int var45 = var15; var45 < var15 + 2; var45++) {
                        int var49 = var45 % 2;
                        int var53 = 1 - var49;

                        for (int var58 = 0; var58 < 4; var58++) {
                           for (int var63 = -1; var63 < 4; var63++) {
                              int var68 = var33 + (var58 - 1) * var49;
                              int var72 = var42 + var63;
                              int var73 = var37 + (var58 - 1) * var53;
                              var16.set(var68, var72, var73);
                              if (var63 < 0 && !this.worldServerInstance.getBlockState(var16).getBlock().getMaterial().isSolid()
                                 || var63 >= 0 && !this.worldServerInstance.isAirBlock(var16)) {
                                 continue label238;
                              }
                           }
                        }

                        double var59 = var42 + 0.5 - entity.posY;
                        double var69 = var35 * var35 + var59 * var59 + var39 * var39;
                        if (var6 < 0.0 || var69 < var6) {
                           var6 = var69;
                           var11 = var33;
                           var12 = var42;
                           var13 = var37;
                           var14 = var45 % 2;
                        }
                     }
                  }
               }
            }
         }
      }

      int var34 = var11;
      int var36 = var12;
      int var19 = var13;
      int var38 = var14 % 2;
      int var40 = 1 - var38;
      if (var14 % 4 >= 2) {
         var38 = -var38;
         var40 = -var40;
      }

      if (var6 < 0.0) {
         var36 = MathHelper.clamp_int(var12, 70, this.worldServerInstance.getActualHeight() - 10);

         for (int var22 = -1; var22 <= 1; var22++) {
            for (int var43 = 1; var43 < 3; var43++) {
               for (int var46 = -1; var46 < 3; var46++) {
                  int var50 = var34 + (var43 - 1) * var38 + var22 * var40;
                  int var54 = var36 + var46;
                  int var60 = var19 + (var43 - 1) * var40 - var22 * var38;
                  boolean var64 = var46 < 0;
                  this.worldServerInstance
                     .setBlockState(new BlockPos(var50, var54, var60), var64 ? Blocks.obsidian.getDefaultState() : Blocks.air.getDefaultState());
               }
            }
         }
      }

      IBlockState var41 = Blocks.portal.getDefaultState().withProperty(BlockPortal.AXIS, var38 != 0 ? EnumFacing.Axis.X : EnumFacing.Axis.Z);

      for (int var44 = 0; var44 < 4; var44++) {
         for (int var47 = 0; var47 < 4; var47++) {
            for (int var51 = -1; var51 < 4; var51++) {
               int var55 = var34 + (var47 - 1) * var38;
               int var61 = var36 + var51;
               int var65 = var19 + (var47 - 1) * var40;
               boolean var70 = var47 == 0 || var47 == 3 || var51 == -1 || var51 == 3;
               this.worldServerInstance.setBlockState(new BlockPos(var55, var61, var65), var70 ? Blocks.obsidian.getDefaultState() : var41, 2);
            }
         }

         for (int var48 = 0; var48 < 4; var48++) {
            for (int var52 = -1; var52 < 4; var52++) {
               int var56 = var34 + (var48 - 1) * var38;
               int var62 = var36 + var52;
               int var66 = var19 + (var48 - 1) * var40;
               BlockPos var71 = new BlockPos(var56, var62, var66);
               this.worldServerInstance.notifyNeighborsOfStateChange(var71, this.worldServerInstance.getBlockState(var71).getBlock());
            }
         }
      }

      return true;
   }

   public void removeStalePortalLocations(long long1) {
      if (long1 % 100L == 0L) {
         Iterator var6 = this.destinationCoordinateKeys.iterator();
         long var7 = long1 - 300L;

         while (var6.hasNext()) {
            Long var9 = (Long)var6.next();
            Teleporter.PortalPosition var10 = this.destinationCoordinateCache.getValueByKey(var9);
            if (var10 == null || var10.lastUpdateTime < var7) {
               var6.remove();
               this.destinationCoordinateCache.remove(var9);
            }
         }
      }
   }

   public static class PortalPosition extends BlockPos {
      public long lastUpdateTime;

      public PortalPosition(BlockPos blockPos, long long2) {
         super(blockPos.getX(), blockPos.getY(), blockPos.getZ());
         this.lastUpdateTime = long2;
      }
   }
}
