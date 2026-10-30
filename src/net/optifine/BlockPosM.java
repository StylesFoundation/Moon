package net.optifine;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;

public class BlockPosM extends BlockPos {
   private int mx;
   private int my;
   private int mz;
   private final int level;
   private BlockPosM[] facings;
   private boolean needsUpdate;

   public BlockPosM(int integer1, int integer2, int integer3) {
      this(integer1, integer2, integer3, 0);
   }

   public BlockPosM(double double1, double double2, double double3) {
      this(MathHelper.floor_double(double1), MathHelper.floor_double(double2), MathHelper.floor_double(double3));
   }

   public BlockPosM(int integer1, int integer2, int integer3, int integer4) {
      super(0, 0, 0);
      this.mx = integer1;
      this.my = integer2;
      this.mz = integer3;
      this.level = integer4;
   }

   @Override
   public int getX() {
      return this.mx;
   }

   @Override
   public int getY() {
      return this.my;
   }

   @Override
   public int getZ() {
      return this.mz;
   }

   public void setXyz(int integer1, int integer2, int integer3) {
      this.mx = integer1;
      this.my = integer2;
      this.mz = integer3;
      this.needsUpdate = true;
   }

   public void setXyz(double double1, double double2, double double3) {
      this.setXyz(MathHelper.floor_double(double1), MathHelper.floor_double(double2), MathHelper.floor_double(double3));
   }

   public BlockPosM set(Vec3i vec3i) {
      this.setXyz(vec3i.getX(), vec3i.getY(), vec3i.getZ());
      return this;
   }

   public BlockPosM set(int integer1, int integer2, int integer3) {
      this.setXyz(integer1, integer2, integer3);
      return this;
   }

   public BlockPos offsetMutable(EnumFacing enumFacing) {
      return this.offset(enumFacing);
   }

   @Override
   public BlockPos offset(EnumFacing enumFacing) {
      if (this.level <= 0) {
         return super.offset(enumFacing, 1);
      }

      if (this.facings == null) {
         this.facings = new BlockPosM[EnumFacing.VALUES.length];
      }

      if (this.needsUpdate) {
         this.update();
      }

      int var5 = enumFacing.getIndex();
      BlockPosM var6 = this.facings[var5];
      if (var6 == null) {
         int var7 = this.mx + enumFacing.getFrontOffsetX();
         int var8 = this.my + enumFacing.getFrontOffsetY();
         int var9 = this.mz + enumFacing.getFrontOffsetZ();
         var6 = new BlockPosM(var7, var8, var9, this.level - 1);
         this.facings[var5] = var6;
      }

      return var6;
   }

   @Override
   public BlockPos offset(EnumFacing enumFacing, int integer) {
      return integer == 1 ? this.offset(enumFacing) : super.offset(enumFacing, integer);
   }

   private void update() {
      for (int var4 = 0; var4 < 6; var4++) {
         BlockPosM var5 = this.facings[var4];
         if (var5 != null) {
            EnumFacing var6 = EnumFacing.VALUES[var4];
            int var7 = this.mx + var6.getFrontOffsetX();
            int var8 = this.my + var6.getFrontOffsetY();
            int var9 = this.mz + var6.getFrontOffsetZ();
            var5.setXyz(var7, var8, var9);
         }
      }

      this.needsUpdate = false;
   }

   public BlockPos toImmutable() {
      return new BlockPos(this.mx, this.my, this.mz);
   }

   public static Iterable getAllInBoxMutable(BlockPos blockPos1, BlockPos blockPos2) {
      final BlockPos var2 = new BlockPos(
         Math.min(blockPos1.getX(), blockPos2.getX()), Math.min(blockPos1.getY(), blockPos2.getY()), Math.min(blockPos1.getZ(), blockPos2.getZ())
      );
      final BlockPos var3 = new BlockPos(
         Math.max(blockPos1.getX(), blockPos2.getX()), Math.max(blockPos1.getY(), blockPos2.getY()), Math.max(blockPos1.getZ(), blockPos2.getZ())
      );
      return new Iterable() {
         @Override
         public Iterator iterator() {
            return new AbstractIterator() {
               private BlockPosM theBlockPosM = null;

               private BlockPosM computeNext0() {
                  if (this.theBlockPosM == null) {
                     this.theBlockPosM = new BlockPosM(var2.getX(), var2.getY(), var2.getZ(), 3);
                     return this.theBlockPosM;
                  }

                  if (this.theBlockPosM.equals(var3)) {
                     return (BlockPosM)this.endOfData();
                  }

                  int var4 = this.theBlockPosM.getX();
                  int var5 = this.theBlockPosM.getY();
                  int var6 = this.theBlockPosM.getZ();
                  if (var4 < var3.getX()) {
                     var4++;
                  } else if (var5 < var3.getY()) {
                     var4 = var2.getX();
                     var5++;
                  } else if (var6 < var3.getZ()) {
                     var4 = var2.getX();
                     var5 = var2.getY();
                     var6++;
                  }

                  this.theBlockPosM.setXyz(var4, var5, var6);
                  return this.theBlockPosM;
               }

               @Override
               protected Object computeNext() {
                  return this.computeNext0();
               }
            };
         }
      };
   }
}
