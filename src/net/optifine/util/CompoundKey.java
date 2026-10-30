package net.optifine.util;

import net.minecraft.src.Config;

public class CompoundKey {
   private final Object[] keys;
   private int hashcode = 0;

   public CompoundKey(Object[] arr) {
      this.keys = (Object[])arr.clone();
   }

   public CompoundKey(Object object1, Object object2) {
      this(new Object[]{object1, object2});
   }

   public CompoundKey(Object object1, Object object2, Object object3) {
      this(new Object[]{object1, object2, object3});
   }

   @Override
   public int hashCode() {
      if (this.hashcode == 0) {
         this.hashcode = 7;

         for (Object var7 : this.keys) {
            if (var7 != null) {
               this.hashcode = 31 * this.hashcode + var7.hashCode();
            }
         }
      }

      return this.hashcode;
   }

   @Override
   public boolean equals(Object object) {
      if (object == null) {
         return false;
      }

      if (object == this) {
         return true;
      }

      if (object instanceof CompoundKey var5) {
         Object[] var6 = var5.getKeys();
         if (var6.length != this.keys.length) {
            return false;
         }

         for (int var7 = 0; var7 < this.keys.length; var7++) {
            if (!compareKeys(this.keys[var7], var6[var7])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean compareKeys(Object object1, Object object2) {
      return object1 == object2 || object1 != null && object1.equals(object2);
   }

   private Object[] getKeys() {
      return this.keys;
   }

   public Object[] getKeysCopy() {
      return (Object[])this.keys.clone();
   }

   @Override
   public String toString() {
      return "[" + Config.arrayToString(this.keys) + "]";
   }
}
