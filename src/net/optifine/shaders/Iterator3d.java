package net.optifine.shaders;

import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.optifine.BlockPosM;

public class Iterator3d implements Iterator<BlockPos> {
   private final IteratorAxis iteratorAxis;
   private final BlockPosM blockPos = new BlockPosM(0, 0, 0);
   private int axis = 0;
   private final int kX;
   private final int kY;
   private final int kZ;

   public Iterator3d(BlockPos blockPos1, BlockPos blockPos2, int integer3, int integer4) {
      boolean var8 = blockPos1.getX() > blockPos2.getX();
      boolean var9 = blockPos1.getY() > blockPos2.getY();
      boolean var10 = blockPos1.getZ() > blockPos2.getZ();
      blockPos1 = this.reverseCoord(blockPos1, var8, var9, var10);
      blockPos2 = this.reverseCoord(blockPos2, var8, var9, var10);
      this.kX = var8 ? -1 : 1;
      this.kY = var9 ? -1 : 1;
      this.kZ = var10 ? -1 : 1;
      Vec3 var12 = new Vec3(blockPos2.getX() - blockPos1.getX(), blockPos2.getY() - blockPos1.getY(), blockPos2.getZ() - blockPos1.getZ()).normalize();
      Vec3 var13 = new Vec3(1.0, 0.0, 0.0);
      double var16 = Math.abs(var12.dotProduct(var13));
      Vec3 var18 = new Vec3(0.0, 1.0, 0.0);
      double var21 = Math.abs(var12.dotProduct(var18));
      Vec3 var23 = new Vec3(0.0, 0.0, 1.0);
      double var26 = Math.abs(var12.dotProduct(var23));
      if (var26 >= var21 && var26 >= var16) {
         this.axis = 2;
         BlockPos var38 = new BlockPos(blockPos1.getZ(), blockPos1.getY() - integer3, blockPos1.getX() - integer4);
         BlockPos var40 = new BlockPos(blockPos2.getZ(), blockPos1.getY() + integer3 + 1, blockPos1.getX() + integer4 + 1);
         int var42 = blockPos2.getZ() - blockPos1.getZ();
         double var44 = (double)(blockPos2.getY() - blockPos1.getY()) / var42;
         double var46 = (double)(blockPos2.getX() - blockPos1.getX()) / var42;
         this.iteratorAxis = new IteratorAxis(var38, var40, var44, var46);
      } else if (var21 >= var16 && var21 >= var26) {
         this.axis = 1;
         BlockPos var37 = new BlockPos(blockPos1.getY(), blockPos1.getX() - integer3, blockPos1.getZ() - integer4);
         BlockPos var39 = new BlockPos(blockPos2.getY(), blockPos1.getX() + integer3 + 1, blockPos1.getZ() + integer4 + 1);
         int var41 = blockPos2.getY() - blockPos1.getY();
         double var43 = (double)(blockPos2.getX() - blockPos1.getX()) / var41;
         double var45 = (double)(blockPos2.getZ() - blockPos1.getZ()) / var41;
         this.iteratorAxis = new IteratorAxis(var37, var39, var43, var45);
      } else {
         this.axis = 0;
         BlockPos var28 = new BlockPos(blockPos1.getX(), blockPos1.getY() - integer3, blockPos1.getZ() - integer4);
         BlockPos var29 = new BlockPos(blockPos2.getX(), blockPos1.getY() + integer3 + 1, blockPos1.getZ() + integer4 + 1);
         int var30 = blockPos2.getX() - blockPos1.getX();
         double var31 = (double)(blockPos2.getY() - blockPos1.getY()) / var30;
         double var33 = (double)(blockPos2.getZ() - blockPos1.getZ()) / var30;
         this.iteratorAxis = new IteratorAxis(var28, var29, var31, var33);
      }
   }

   private BlockPos reverseCoord(BlockPos blockPos, boolean boolean2, boolean boolean3, boolean boolean4) {
      if (boolean2) {
         blockPos = new BlockPos(-blockPos.getX(), blockPos.getY(), blockPos.getZ());
      }

      if (boolean3) {
         blockPos = new BlockPos(blockPos.getX(), -blockPos.getY(), blockPos.getZ());
      }

      if (boolean4) {
         blockPos = new BlockPos(blockPos.getX(), blockPos.getY(), -blockPos.getZ());
      }

      return blockPos;
   }

   @Override
   public boolean hasNext() {
      return this.iteratorAxis.hasNext();
   }

   public BlockPos next() {
      BlockPos var4 = this.iteratorAxis.next();
      switch (this.axis) {
         case 1:
            this.blockPos.setXyz(var4.getY() * this.kX, var4.getX() * this.kY, var4.getZ() * this.kZ);
            return this.blockPos;
         case 2:
            this.blockPos.setXyz(var4.getZ() * this.kX, var4.getY() * this.kY, var4.getX() * this.kZ);
            return this.blockPos;
         default:
            this.blockPos.setXyz(var4.getX() * this.kX, var4.getY() * this.kY, var4.getZ() * this.kZ);
            return this.blockPos;
      }
   }

   @Override
   public void remove() {
      throw new RuntimeException("Not supported");
   }

   public static void main(String[] arr) {
      BlockPos var4 = new BlockPos(10, 20, 30);
      BlockPos var5 = new BlockPos(30, 40, 20);
      Iterator3d var6 = new Iterator3d(var4, var5, 1, 1);

      while (var6.hasNext()) {
         BlockPos var7 = var6.next();
         System.out.println(var7 + "");
      }
   }
}
