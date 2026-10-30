package net.optifine.shaders;

import java.util.Iterator;
import java.util.NoSuchElementException;
import net.minecraft.util.BlockPos;
import net.optifine.BlockPosM;

public class IteratorAxis implements Iterator<BlockPos> {
   private final double yDelta;
   private final double zDelta;
   private final int xStart;
   private final int xEnd;
   private double yStart;
   private double yEnd;
   private double zStart;
   private double zEnd;
   private int xNext;
   private double yNext;
   private double zNext;
   private final BlockPosM pos = new BlockPosM(0, 0, 0);
   private boolean hasNext = false;

   public IteratorAxis(BlockPos blockPos1, BlockPos blockPos2, double double3, double double4) {
      this.yDelta = double3;
      this.zDelta = double4;
      this.xStart = blockPos1.getX();
      this.xEnd = blockPos2.getX();
      this.yStart = blockPos1.getY();
      this.yEnd = blockPos2.getY() - 0.5;
      this.zStart = blockPos1.getZ();
      this.zEnd = blockPos2.getZ() - 0.5;
      this.xNext = this.xStart;
      this.yNext = this.yStart;
      this.zNext = this.zStart;
      this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
   }

   @Override
   public boolean hasNext() {
      return this.hasNext;
   }

   public BlockPos next() {
      if (!this.hasNext) {
         throw new NoSuchElementException();
      }

      this.pos.setXyz(this.xNext, this.yNext, this.zNext);
      this.nextPos();
      this.hasNext = this.xNext < this.xEnd && this.yNext < this.yEnd && this.zNext < this.zEnd;
      return this.pos;
   }

   private void nextPos() {
      this.zNext++;
      if (this.zNext >= this.zEnd) {
         this.zNext = this.zStart;
         this.yNext++;
         if (this.yNext >= this.yEnd) {
            this.yNext = this.yStart;
            this.yStart = this.yStart + this.yDelta;
            this.yEnd = this.yEnd + this.yDelta;
            this.yNext = this.yStart;
            this.zStart = this.zStart + this.zDelta;
            this.zEnd = this.zEnd + this.zDelta;
            this.zNext = this.zStart;
            this.xNext++;
            if (this.xNext >= this.xEnd) {
            }
         }
      }
   }

   @Override
   public void remove() {
      throw new RuntimeException("Not implemented");
   }

   public static void main(String[] arr) throws Exception {
      BlockPos var4 = new BlockPos(-2, 10, 20);
      BlockPos var5 = new BlockPos(2, 12, 22);
      IteratorAxis var10 = new IteratorAxis(var4, var5, -0.5, 0.5);
      BlockPos var16 = var5;
      BlockPos var17 = var4;
      System.out.println("Start: " + var17 + ", end: " + var16 + ", yDelta: " + -0.5 + ", zDelta: " + 0.5);

      while (var10.hasNext()) {
         BlockPos var11 = var10.next();
         System.out.println(var11 + "");
      }
   }
}
