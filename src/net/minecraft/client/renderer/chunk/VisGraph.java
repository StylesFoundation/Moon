package net.minecraft.client.renderer.chunk;

import java.util.ArrayDeque;
import java.util.BitSet;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IntegerCache;

public class VisGraph {
   private static final int field_178616_a = (int)Math.pow(16.0, 0.0);
   private static final int field_178614_b = (int)Math.pow(16.0, 1.0);
   private static final int field_178615_c = (int)Math.pow(16.0, 2.0);
   private final BitSet field_178612_d = new BitSet(4096);
   private static final int[] field_178613_e = new int[1352];
   private int field_178611_f = 4096;

   public void func_178606_a(BlockPos blockPos) {
      this.field_178612_d.set(getIndex(blockPos), true);
      this.field_178611_f--;
   }

   private static int getIndex(BlockPos blockPos) {
      return getIndex(blockPos.getX() & 15, blockPos.getY() & 15, blockPos.getZ() & 15);
   }

   private static int getIndex(int integer1, int integer2, int integer3) {
      return integer1 | integer2 << 8 | integer3 << 4;
   }

   public SetVisibility computeVisibility() {
      SetVisibility var3 = new SetVisibility();
      if (4096 - this.field_178611_f < 256) {
         var3.setAllVisible(true);
      } else if (this.field_178611_f == 0) {
         var3.setAllVisible(false);
      } else {
         for (int var7 : field_178613_e) {
            if (!this.field_178612_d.get(var7)) {
               var3.setManyVisible(this.func_178604_a(var7));
            }
         }
      }

      return var3;
   }

   public Set<EnumFacing> func_178609_b(BlockPos blockPos) {
      return this.func_178604_a(getIndex(blockPos));
   }

   private Set<EnumFacing> func_178604_a(int integer) {
      EnumSet var4 = EnumSet.noneOf(EnumFacing.class);
      ArrayDeque var5 = new ArrayDeque(384);
      var5.add(IntegerCache.getInteger(integer));
      this.field_178612_d.set(integer, true);

      while (!var5.isEmpty()) {
         int var6 = (Integer)var5.poll();
         this.func_178610_a(var6, var4);

         for (EnumFacing var10 : EnumFacing.VALUES) {
            int var11 = this.func_178603_a(var6, var10);
            if (var11 >= 0 && !this.field_178612_d.get(var11)) {
               this.field_178612_d.set(var11, true);
               var5.add(IntegerCache.getInteger(var11));
            }
         }
      }

      return var4;
   }

   private void func_178610_a(int integer, Set<EnumFacing> set) {
      int var5 = integer & 15;
      if (var5 == 0) {
         set.add(EnumFacing.WEST);
      } else if (var5 == 15) {
         set.add(EnumFacing.EAST);
      }

      int var6 = integer >> 8 & 15;
      if (var6 == 0) {
         set.add(EnumFacing.DOWN);
      } else if (var6 == 15) {
         set.add(EnumFacing.UP);
      }

      int var7 = integer >> 4 & 15;
      if (var7 == 0) {
         set.add(EnumFacing.NORTH);
      } else if (var7 == 15) {
         set.add(EnumFacing.SOUTH);
      }
   }

   private int func_178603_a(int integer, EnumFacing enumFacing) {
      switch (enumFacing) {
         case DOWN:
            if ((integer >> 8 & 15) == 0) {
               return -1;
            }

            return integer - field_178615_c;
         case UP:
            if ((integer >> 8 & 15) == 15) {
               return -1;
            }

            return integer + field_178615_c;
         case NORTH:
            if ((integer >> 4 & 15) == 0) {
               return -1;
            }

            return integer - field_178614_b;
         case SOUTH:
            if ((integer >> 4 & 15) == 15) {
               return -1;
            }

            return integer + field_178614_b;
         case WEST:
            if ((integer & 15) == 0) {
               return -1;
            }

            return integer - field_178616_a;
         case EAST:
            if ((integer & 15) == 15) {
               return -1;
            }

            return integer + field_178616_a;
         default:
            return -1;
      }
   }

   static {
      int var15 = 0;

      for (int var16 = 0; var16 < 16; var16++) {
         for (int var17 = 0; var17 < 16; var17++) {
            for (int var18 = 0; var18 < 16; var18++) {
               if (var16 == 0 || var16 == 15 || var17 == 0 || var17 == 15 || var18 == 0 || var18 == 15) {
                  field_178613_e[var15++] = getIndex(var16, var17, var18);
               }
            }
         }
      }
   }
}
