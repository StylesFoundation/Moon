package net.minecraft.entity.ai;

import java.util.Random;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RandomPositionGenerator {
   private static Vec3 staticVector = new Vec3(0.0, 0.0, 0.0);

   public static Vec3 findRandomTarget(EntityCreature entityCreature, int integer2, int integer3) {
      return findRandomTargetBlock(entityCreature, integer2, integer3, null);
   }

   public static Vec3 findRandomTargetBlockTowards(EntityCreature entityCreature, int integer2, int integer3, Vec3 vec3) {
      staticVector = vec3.subtract(entityCreature.posX, entityCreature.posY, entityCreature.posZ);
      return findRandomTargetBlock(entityCreature, integer2, integer3, staticVector);
   }

   public static Vec3 findRandomTargetBlockAwayFrom(EntityCreature entityCreature, int integer2, int integer3, Vec3 vec3) {
      staticVector = new Vec3(entityCreature.posX, entityCreature.posY, entityCreature.posZ).subtract(vec3);
      return findRandomTargetBlock(entityCreature, integer2, integer3, staticVector);
   }

   private static Vec3 findRandomTargetBlock(EntityCreature entityCreature, int integer2, int integer3, Vec3 vec3) {
      Random var7 = entityCreature.getRNG();
      boolean var8 = false;
      int var9 = 0;
      int var10 = 0;
      int var11 = 0;
      float var12 = -99999.0F;
      boolean var13;
      if (entityCreature.hasHome()) {
         double var14 = entityCreature.getHomePosition()
               .distanceSq(
                  MathHelper.floor_double(entityCreature.posX), MathHelper.floor_double(entityCreature.posY), MathHelper.floor_double(entityCreature.posZ)
               )
            + 4.0;
         double var16 = entityCreature.getMaximumHomeDistance() + integer2;
         var13 = var14 < var16 * var16;
      } else {
         var13 = false;
      }

      for (int var20 = 0; var20 < 10; var20++) {
         int var15 = var7.nextInt(2 * integer2 + 1) - integer2;
         int var22 = var7.nextInt(2 * integer3 + 1) - integer3;
         int var17 = var7.nextInt(2 * integer2 + 1) - integer2;
         if (vec3 == null || var15 * vec3.xCoord + var17 * vec3.zCoord >= 0.0) {
            if (entityCreature.hasHome() && integer2 > 1) {
               BlockPos var18 = entityCreature.getHomePosition();
               if (entityCreature.posX > var18.getX()) {
                  var15 -= var7.nextInt(integer2 / 2);
               } else {
                  var15 += var7.nextInt(integer2 / 2);
               }

               if (entityCreature.posZ > var18.getZ()) {
                  var17 -= var7.nextInt(integer2 / 2);
               } else {
                  var17 += var7.nextInt(integer2 / 2);
               }
            }

            var15 += MathHelper.floor_double(entityCreature.posX);
            var22 += MathHelper.floor_double(entityCreature.posY);
            var17 += MathHelper.floor_double(entityCreature.posZ);
            BlockPos var25 = new BlockPos(var15, var22, var17);
            if (!var13 || entityCreature.isWithinHomeDistanceFromPosition(var25)) {
               float var19 = entityCreature.getBlockPathWeight(var25);
               if (var19 > var12) {
                  var12 = var19;
                  var9 = var15;
                  var10 = var22;
                  var11 = var17;
                  var8 = true;
               }
            }
         }
      }

      return var8 ? new Vec3(var9, var10, var11) : null;
   }
}
