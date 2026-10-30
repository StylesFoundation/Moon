package net.minecraft.util;

import com.google.common.collect.AbstractIterator;
import java.util.Iterator;
import net.minecraft.entity.Entity;

public class BlockPos extends Vec3i {
   public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);
   private static final int NUM_X_BITS = 1 + MathHelper.calculateLogBaseTwo(MathHelper.roundUpToPowerOfTwo(30000000));
   private static final int NUM_Z_BITS = NUM_X_BITS;
   private static final int NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS;
   private static final int Y_SHIFT = NUM_Z_BITS;
   private static final int X_SHIFT = Y_SHIFT + NUM_Y_BITS;
   private static final long X_MASK = (1L << NUM_X_BITS) - 1L;
   private static final long Y_MASK = (1L << NUM_Y_BITS) - 1L;
   private static final long Z_MASK = (1L << NUM_Z_BITS) - 1L;

   public BlockPos(int integer1, int integer2, int integer3) {
      super(integer1, integer2, integer3);
   }

   public BlockPos(double double1, double double2, double double3) {
      super(double1, double2, double3);
   }

   public BlockPos(Entity entity) {
      this(entity.posX, entity.posY, entity.posZ);
   }

   public BlockPos(Vec3 vec3) {
      this(vec3.xCoord, vec3.yCoord, vec3.zCoord);
   }

   public BlockPos(Vec3i vec3i) {
      this(vec3i.getX(), vec3i.getY(), vec3i.getZ());
   }

   public BlockPos add(double double1, double double2, double double3) {
      return double1 == 0.0 && double2 == 0.0 && double3 == 0.0 ? this : new BlockPos(this.getX() + double1, this.getY() + double2, this.getZ() + double3);
   }

   public BlockPos add(int integer1, int integer2, int integer3) {
      return integer1 == 0 && integer2 == 0 && integer3 == 0 ? this : new BlockPos(this.getX() + integer1, this.getY() + integer2, this.getZ() + integer3);
   }

   public BlockPos add(Vec3i vec3i) {
      return vec3i.getX() == 0 && vec3i.getY() == 0 && vec3i.getZ() == 0
         ? this
         : new BlockPos(this.getX() + vec3i.getX(), this.getY() + vec3i.getY(), this.getZ() + vec3i.getZ());
   }

   public BlockPos subtract(Vec3i vec3i) {
      return vec3i.getX() == 0 && vec3i.getY() == 0 && vec3i.getZ() == 0
         ? this
         : new BlockPos(this.getX() - vec3i.getX(), this.getY() - vec3i.getY(), this.getZ() - vec3i.getZ());
   }

   public BlockPos up() {
      return this.up(1);
   }

   public BlockPos up(int integer) {
      return this.offset(EnumFacing.UP, integer);
   }

   public BlockPos down() {
      return this.down(1);
   }

   public BlockPos down(int integer) {
      return this.offset(EnumFacing.DOWN, integer);
   }

   public BlockPos north() {
      return this.north(1);
   }

   public BlockPos north(int integer) {
      return this.offset(EnumFacing.NORTH, integer);
   }

   public BlockPos south() {
      return this.south(1);
   }

   public BlockPos south(int integer) {
      return this.offset(EnumFacing.SOUTH, integer);
   }

   public BlockPos west() {
      return this.west(1);
   }

   public BlockPos west(int integer) {
      return this.offset(EnumFacing.WEST, integer);
   }

   public BlockPos east() {
      return this.east(1);
   }

   public BlockPos east(int integer) {
      return this.offset(EnumFacing.EAST, integer);
   }

   public BlockPos offset(EnumFacing enumFacing) {
      return this.offset(enumFacing, 1);
   }

   public BlockPos offset(EnumFacing enumFacing, int integer) {
      return integer == 0
         ? this
         : new BlockPos(
            this.getX() + enumFacing.getFrontOffsetX() * integer,
            this.getY() + enumFacing.getFrontOffsetY() * integer,
            this.getZ() + enumFacing.getFrontOffsetZ() * integer
         );
   }

   public BlockPos crossProduct(Vec3i vec3i) {
      return new BlockPos(
         this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(),
         this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(),
         this.getX() * vec3i.getY() - this.getY() * vec3i.getX()
      );
   }

   public long toLong() {
      return (this.getX() & X_MASK) << X_SHIFT | (this.getY() & Y_MASK) << Y_SHIFT | this.getZ() & Z_MASK;
   }

   public static BlockPos fromLong(long long1) {
      int var4 = (int)(long1 << 64 - X_SHIFT - NUM_X_BITS >> 64 - NUM_X_BITS);
      int var5 = (int)(long1 << 64 - Y_SHIFT - NUM_Y_BITS >> 64 - NUM_Y_BITS);
      int var6 = (int)(long1 << 64 - NUM_Z_BITS >> 64 - NUM_Z_BITS);
      return new BlockPos(var4, var5, var6);
   }

   public static Iterable<BlockPos> getAllInBox(BlockPos blockPos1, BlockPos blockPos2) {
      final BlockPos var2 = new BlockPos(
         Math.min(blockPos1.getX(), blockPos2.getX()), Math.min(blockPos1.getY(), blockPos2.getY()), Math.min(blockPos1.getZ(), blockPos2.getZ())
      );
      final BlockPos var3 = new BlockPos(
         Math.max(blockPos1.getX(), blockPos2.getX()), Math.max(blockPos1.getY(), blockPos2.getY()), Math.max(blockPos1.getZ(), blockPos2.getZ())
      );
      return new Iterable<BlockPos>() {
         @Override
         public Iterator<BlockPos> iterator() {
            return new AbstractIterator<BlockPos>() {
               private BlockPos lastReturned = null;

               protected BlockPos computeNext() {
                  if (this.lastReturned == null) {
                     this.lastReturned = var2;
                     return this.lastReturned;
                  }

                  if (this.lastReturned.equals(var3)) {
                     return this.endOfData();
                  }

                  int var4 = this.lastReturned.getX();
                  int var5 = this.lastReturned.getY();
                  int var6 = this.lastReturned.getZ();
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

                  this.lastReturned = new BlockPos(var4, var5, var6);
                  return this.lastReturned;
               }
            };
         }
      };
   }

   public static Iterable<BlockPos.MutableBlockPos> getAllInBoxMutable(BlockPos blockPos1, BlockPos blockPos2) {
      final BlockPos var2 = new BlockPos(
         Math.min(blockPos1.getX(), blockPos2.getX()), Math.min(blockPos1.getY(), blockPos2.getY()), Math.min(blockPos1.getZ(), blockPos2.getZ())
      );
      final BlockPos var3 = new BlockPos(
         Math.max(blockPos1.getX(), blockPos2.getX()), Math.max(blockPos1.getY(), blockPos2.getY()), Math.max(blockPos1.getZ(), blockPos2.getZ())
      );
      return new Iterable<BlockPos.MutableBlockPos>() {
         @Override
         public Iterator<BlockPos.MutableBlockPos> iterator() {
            return new AbstractIterator<BlockPos.MutableBlockPos>() {
               private BlockPos.MutableBlockPos theBlockPos = null;

               protected BlockPos.MutableBlockPos computeNext() {
                  if (this.theBlockPos == null) {
                     this.theBlockPos = new BlockPos.MutableBlockPos(var2.getX(), var2.getY(), var2.getZ());
                     return this.theBlockPos;
                  }

                  if (this.theBlockPos.equals(var3)) {
                     return this.endOfData();
                  }

                  int var4 = this.theBlockPos.getX();
                  int var5 = this.theBlockPos.getY();
                  int var6 = this.theBlockPos.getZ();
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

                  this.theBlockPos.x = var4;
                  this.theBlockPos.y = var5;
                  this.theBlockPos.z = var6;
                  return this.theBlockPos;
               }
            };
         }
      };
   }

   public static final class MutableBlockPos extends BlockPos {
      private int x;
      private int y;
      private int z;

      public MutableBlockPos() {
         this(0, 0, 0);
      }

      public MutableBlockPos(int integer1, int integer2, int integer3) {
         super(0, 0, 0);
         this.x = integer1;
         this.y = integer2;
         this.z = integer3;
      }

      @Override
      public int getX() {
         return this.x;
      }

      @Override
      public int getY() {
         return this.y;
      }

      @Override
      public int getZ() {
         return this.z;
      }

      public BlockPos.MutableBlockPos set(int integer1, int integer2, int integer3) {
         this.x = integer1;
         this.y = integer2;
         this.z = integer3;
         return this;
      }
   }
}
