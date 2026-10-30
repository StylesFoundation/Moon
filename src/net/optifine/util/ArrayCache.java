package net.optifine.util;

import java.lang.reflect.Array;
import java.util.ArrayDeque;

public class ArrayCache {
   private Class elementClass = null;
   private int maxCacheSize = 0;
   private final ArrayDeque cache = new ArrayDeque();

   public ArrayCache(Class class1, int integer) {
      this.elementClass = class1;
      this.maxCacheSize = integer;
   }

   public synchronized Object allocate(int integer) {
      Object var5 = this.cache.pollLast();
      if (var5 == null || Array.getLength(var5) < integer) {
         var5 = Array.newInstance(this.elementClass, integer);
      }

      return var5;
   }

   public synchronized void free(Object object) {
      if (object != null) {
         Class var5 = object.getClass();
         if (var5.getComponentType() != this.elementClass) {
            throw new IllegalArgumentException("Wrong component type");
         }

         if (this.cache.size() < this.maxCacheSize) {
            this.cache.add(object);
         }
      }
   }
}
