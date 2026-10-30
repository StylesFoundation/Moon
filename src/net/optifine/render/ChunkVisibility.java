package net.optifine.render;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class ChunkVisibility {
   public static final EnumFacing[][] enumFacingArrays = makeEnumFacingArrays(false);
   public static final EnumFacing[][] enumFacingOppositeArrays = makeEnumFacingArrays(true);
   private static int counter = 0;
   private static int iMaxStatic = -1;
   private static int iMaxStaticFinal = 16;
   private static World worldLast = null;
   private static int pcxLast = Integer.MIN_VALUE;
   private static int pczLast = Integer.MIN_VALUE;

   public static int getMaxChunkY(World world, Entity entity, int integer) {
      int var6 = MathHelper.floor_double(entity.posX) >> 4;
      int var7 = MathHelper.floor_double(entity.posY) >> 4;
      int var8 = MathHelper.floor_double(entity.posZ) >> 4;
      Chunk var9 = world.getChunkFromChunkCoords(var6, var8);
      int var10 = var6 - integer;
      int var11 = var6 + integer;
      int var12 = var8 - integer;
      int var13 = var8 + integer;
      if (world != worldLast || var6 != pcxLast || var8 != pczLast) {
         counter = 0;
         iMaxStaticFinal = 16;
         worldLast = world;
         pcxLast = var6;
         pczLast = var8;
      }

      if (counter == 0) {
         iMaxStatic = -1;
      }

      int var14 = iMaxStatic;
      switch (counter) {
         case 0:
            var11 = var6;
            var13 = var8;
            break;
         case 1:
            var10 = var6;
            var13 = var8;
            break;
         case 2:
            var11 = var6;
            var12 = var8;
            break;
         case 3:
            var10 = var6;
            var12 = var8;
      }

      for (int var15 = var10; var15 < var11; var15++) {
         for (int var16 = var12; var16 < var13; var16++) {
            Chunk var17 = world.getChunkFromChunkCoords(var15, var16);
            if (!var17.isEmpty()) {
               ExtendedBlockStorage[] var18 = var17.getBlockStorageArray();

               for (int var19 = var18.length - 1; var19 > var14; var19--) {
                  ExtendedBlockStorage var20 = var18[var19];
                  if (var20 != null && !var20.isEmpty()) {
                     if (var19 > var14) {
                        var14 = var19;
                     }
                     break;
                  }
               }

               try {
                  Map var24 = var17.getTileEntityMap();
                  if (!var24.isEmpty()) {
                     Iterator var26 = var24.keySet().iterator();

                     while (var26.hasNext()) {
                        int var22 = ((BlockPos)var26.next()).getY() >> 4;
                        if (var22 > var14) {
                           var14 = var22;
                        }
                     }
                  }
               } catch (ConcurrentModificationException var23) {
               }

               ClassInheritanceMultiMap[] var25 = var17.getEntityLists();

               for (int var27 = var25.length - 1; var27 > var14; var27--) {
                  ClassInheritanceMultiMap var21 = var25[var27];
                  if (!var21.isEmpty() && (var17 != var9 || var27 != var7 || var21.size() != 1)) {
                     if (var27 > var14) {
                        var14 = var27;
                     }
                     break;
                  }
               }
            }
         }
      }

      if (counter < 3) {
         iMaxStatic = var14;
         var14 = iMaxStaticFinal;
      } else {
         iMaxStaticFinal = var14;
         iMaxStatic = -1;
      }

      counter = (counter + 1) % 4;
      return var14 << 4;
   }

   public static boolean isFinished() {
      return counter == 0;
   }

   private static EnumFacing[][] makeEnumFacingArrays(boolean boolean1) {
      byte var3 = 64;
      EnumFacing[][] var4 = new EnumFacing[var3][];

      for (int var5 = 0; var5 < var3; var5++) {
         ArrayList<EnumFacing> var6 = new ArrayList<EnumFacing>();

         for (int var7 = 0; var7 < EnumFacing.VALUES.length; var7++) {
            EnumFacing var8 = EnumFacing.VALUES[var7];
            EnumFacing var9 = boolean1 ? var8.getOpposite() : var8;
            int var10 = 1 << var9.ordinal();
            if ((var5 & var10) != 0) {
               var6.add(var8);
            }
         }

         EnumFacing[] var11 = var6.toArray(new EnumFacing[0]);
         var4[var5] = var11;
      }

      return var4;
   }

   public static EnumFacing[] getFacingsNotOpposite(int integer) {
      int var3 = ~integer & 63;
      return enumFacingOppositeArrays[var3];
   }

   public static void reset() {
      worldLast = null;
   }
}
