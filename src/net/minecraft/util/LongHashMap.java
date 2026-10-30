package net.minecraft.util;

import java.util.Objects;

public class LongHashMap<V> {
   private transient LongHashMap.Entry<V>[] hashArray = new LongHashMap.Entry[4096];
   private transient int numHashElements;
   private int mask;
   private int capacity = 3072;
   private final float percentUseable = 0.75F;
   private transient volatile int modCount;

   public LongHashMap() {
      this.mask = this.hashArray.length - 1;
   }

   private static int getHashedKey(long long1) {
      return (int)(long1 ^ long1 >>> 27);
   }

   private static int hash(int integer) {
      integer = integer ^ integer >>> 20 ^ integer >>> 12;
      return integer ^ integer >>> 7 ^ integer >>> 4;
   }

   private static int getHashIndex(int integer1, int integer2) {
      return integer1 & integer2;
   }

   public int getNumHashElements() {
      return this.numHashElements;
   }

   public V getValueByKey(long long1) {
      int var6 = getHashedKey(long1);

      for (LongHashMap.Entry<V> var7 = this.hashArray[getHashIndex(var6, this.mask)]; var7 != null; var7 = var7.nextEntry) {
         if (var7.key == long1) {
            return
                    var7.value;
         }
      }

      return null;
   }

   public boolean containsItem(long long1) {
      return this.getEntry(long1) != null;
   }

   final LongHashMap.Entry<V> getEntry(long long1) {
      int var6 = getHashedKey(long1);

      for (LongHashMap.Entry<V> var7 = this.hashArray[getHashIndex(var6, this.mask)]; var7 != null; var7 = var7.nextEntry) {
         if (var7.key == long1) {
            return var7;
         }
      }

      return null;
   }

   public void add(long long1, V object) {
      int var7 = getHashedKey(long1);
      int var8 = getHashIndex(var7, this.mask);

      for (LongHashMap.Entry var9 = this.hashArray[var8]; var9 != null; var9 = var9.nextEntry) {
         if (var9.key == long1) {
            var9.value = (V)object;
            return;
         }
      }

      this.modCount++;
      this.createKey(var7, long1, (V)object, var8);
   }

   private void resizeTable(int integer) {
      LongHashMap.Entry[] var5 = this.hashArray;
      int var6 = var5.length;
      if (var6 == 1073741824) {
         this.capacity = Integer.MAX_VALUE;
      } else {
         LongHashMap.Entry[] var7 = new LongHashMap.Entry[integer];
         this.copyHashTableTo(var7);
         this.hashArray = var7;
         this.mask = this.hashArray.length - 1;
         float var8 = integer;
         this.getClass();
         this.capacity = (int)(var8 * 0.75F);
      }
   }

   private void copyHashTableTo(LongHashMap.Entry<V>[] arr) {
      LongHashMap.Entry[] var5 = this.hashArray;
      int var6 = arr.length;

      for (int var7 = 0; var7 < var5.length; var7++) {
         LongHashMap.Entry var8 = var5[var7];
         if (var8 != null) {
            var5[var7] = null;

            while (true) {
               LongHashMap.Entry var9 = var8.nextEntry;
               int var10 = getHashIndex(var8.hash, var6 - 1);
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

   public V remove(long long1) {
      LongHashMap.Entry<V> var6 = this.removeKey(long1);
      return var6 == null ? null : var6.value;
   }

   final LongHashMap.Entry<V> removeKey(long long1) {
      int var6 = getHashedKey(long1);
      int var7 = getHashIndex(var6, this.mask);
      LongHashMap.Entry<V> var8 = this.hashArray[var7];
      LongHashMap.Entry<V> var9 = var8;

      while (var9 != null) {
         LongHashMap.Entry<V> var10 = var9.nextEntry;
         if (var9.key == long1) {
            this.modCount++;
            this.numHashElements--;
            if (var8 == var9) {
               this.hashArray[var7] = var10;
            } else {
               var8.nextEntry = var10;
            }

            return var9;
         }

         var8 = var9;
         var9 = var10;
      }

      return var9;
   }

   private void createKey(int integer1, long long2, V object, int integer4) {
      LongHashMap.Entry<V> var9 = this.hashArray[integer4];
      this.hashArray[integer4] = new LongHashMap.Entry<>(integer1, long2, object, var9);
      if (this.numHashElements++ >= this.capacity) {
         this.resizeTable(2 * this.hashArray.length);
      }
   }

   public double getKeyDistribution() {
      int var4 = 0;

      for (LongHashMap.Entry var8 : this.hashArray) {
         if (var8 != null) {
            var4++;
         }
      }

      return (double)var4 / this.numHashElements;
   }

   static class Entry<V> {
      final long key;
      V value;
      LongHashMap.Entry<V> nextEntry;
      final int hash;

      Entry(int integer, long long2, V object, LongHashMap.Entry<V> entry) {
         this.value = (V)object;
         this.nextEntry = entry;
         this.key = long2;
         this.hash = integer;
      }

      public final long getKey() {
         return this.key;
      }

      public final V getValue() {
         return this.value;
      }

      @Override
      public final boolean equals(Object object) {
         if (object instanceof LongHashMap.Entry var5) {
            Long var6 = this.getKey();
            Long var7 = var5.getKey();
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
         return LongHashMap.getHashedKey(this.key);
      }

      @Override
      public final String toString() {
         return this.getKey() + "=" + this.getValue();
      }
   }
}
