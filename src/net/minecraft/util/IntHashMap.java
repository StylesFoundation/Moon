package net.minecraft.util;

import java.util.Arrays;
import java.util.Objects;

public class IntHashMap<V> {
   private transient IntHashMap.Entry<V>[] slots = new IntHashMap.Entry[16];
   private transient int count;
   private int threshold = 12;
   private final float growFactor = 0.75F;

   private static int computeHash(int integer) {
      integer = integer ^ integer >>> 20 ^ integer >>> 12;
      return integer ^ integer >>> 7 ^ integer >>> 4;
   }

   private static int getSlotIndex(int integer1, int integer2) {
      return integer1 & integer2 - 1;
   }

   public V lookup(int integer) {
      int var5 = computeHash(integer);

      for (IntHashMap.Entry<V> var6 = this.slots[getSlotIndex(var5, this.slots.length)]; var6 != null; var6 = var6.nextEntry) {
         if (var6.hashEntry == integer) {
            return var6.valueEntry;
         }
      }

      return null;
   }

   public boolean containsItem(int integer) {
      return this.lookupEntry(integer) != null;
   }

   final IntHashMap.Entry<V> lookupEntry(int integer) {
      int var5 = computeHash(integer);

      for (IntHashMap.Entry<V> var6 = this.slots[getSlotIndex(var5, this.slots.length)]; var6 != null; var6 = var6.nextEntry) {
         if (var6.hashEntry == integer) {
            return var6;
         }
      }

      return null;
   }

   public void addKey(int integer, V object) {
      int var6 = computeHash(integer);
      int var7 = getSlotIndex(var6, this.slots.length);

      for (IntHashMap.Entry var8 = this.slots[var7]; var8 != null; var8 = var8.nextEntry) {
         if (var8.hashEntry == integer) {
            var8.valueEntry = (V)object;
            return;
         }
      }

      this.insert(var6, integer, (V)object, var7);
   }

   private void grow(int integer) {
      IntHashMap.Entry[] var5 = this.slots;
      int var6 = var5.length;
      if (var6 == 1073741824) {
         this.threshold = Integer.MAX_VALUE;
      } else {
         IntHashMap.Entry[] var7 = new IntHashMap.Entry[integer];
         this.copyTo(var7);
         this.slots = var7;
         this.threshold = (int)(integer * 0.75F);
      }
   }

   private void copyTo(IntHashMap.Entry<V>[] arr) {
      IntHashMap.Entry[] var5 = this.slots;
      int var6 = arr.length;

      for (int var7 = 0; var7 < var5.length; var7++) {
         IntHashMap.Entry var8 = var5[var7];
         if (var8 != null) {
            var5[var7] = null;

            while (true) {
               IntHashMap.Entry var9 = var8.nextEntry;
               int var10 = getSlotIndex(var8.slotHash, var6);
               var8.nextEntry = arr[var10];
               arr[var10] = var8;
               var8 = var9;
               if (var9 == null) {
                  break;
               }
            }
         }
      }
   }

   public V removeObject(int integer) {
      IntHashMap.Entry<V> var5 = this.removeEntry(integer);
      return var5 == null ? null : var5.valueEntry;
   }

   final IntHashMap.Entry<V> removeEntry(int integer) {
      int var6 = getSlotIndex(computeHash(integer), this.slots.length);
      IntHashMap.Entry<V> var7 = this.slots[var6];
      IntHashMap.Entry<V> var8 = var7;

      while (var8 != null) {
         IntHashMap.Entry<V> var9 = var8.nextEntry;
         if (var8.hashEntry == integer) {
            this.count--;
            if (var7 == var8) {
               this.slots[var6] = var9;
            } else {
               var7.nextEntry = var9;
            }

            return var8;
         }

         var7 = var8;
         var8 = var9;
      }

      return var8;
   }

   public void clearMap() {
      Arrays.fill(this.slots, null);
      this.count = 0;
   }

   private void insert(int integer1, int integer2, V object, int integer4) {
      IntHashMap.Entry<V> var8 = this.slots[integer4];
      this.slots[integer4] = new IntHashMap.Entry<>(integer1, integer2, object, var8);
      if (this.count++ >= this.threshold) {
         this.grow(2 * this.slots.length);
      }
   }

   static class Entry<V> {
      final int hashEntry;
      V valueEntry;
      IntHashMap.Entry<V> nextEntry;
      final int slotHash;

      Entry(int integer1, int integer2, V object, IntHashMap.Entry<V> entry) {
         this.valueEntry = (V)object;
         this.nextEntry = entry;
         this.hashEntry = integer2;
         this.slotHash = integer1;
      }

      public final int getHash() {
         return this.hashEntry;
      }

      public final V getValue() {
         return this.valueEntry;
      }

      @Override
      public final boolean equals(Object object) {
         if (object instanceof IntHashMap.Entry var5) {
            Integer var6 = this.getHash();
            Integer var7 = var5.getHash();
            if (Objects.equals(var6, var7)) {
               Object var8 = this.getValue();
               Object var9 = var5.getValue();
               return Objects.equals(var8, var9);
            }
         }

         return false;
      }

      @Override
      public final int hashCode() {
         return IntHashMap.computeHash(this.hashEntry);
      }

      @Override
      public final String toString() {
         return this.getHash() + "=" + this.getValue();
      }
   }
}
