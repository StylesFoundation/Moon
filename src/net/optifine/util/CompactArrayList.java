package net.optifine.util;

import java.util.ArrayList;

public class CompactArrayList {
   private ArrayList list = null;
   private int initialCapacity = 0;
   private float loadFactor = 1.0F;
   private int countValid = 0;

   public CompactArrayList() {
      this(10, 0.75F);
   }

   public CompactArrayList(int integer) {
      this(integer, 0.75F);
   }

   public CompactArrayList(int integer, float float2) {
      this.list = new ArrayList(integer);
      this.initialCapacity = integer;
      this.loadFactor = float2;
   }

   public void add(int integer, Object object) {
      if (object != null) {
         this.countValid++;
      }

      this.list.add(integer, object);
   }

   public boolean add(Object object) {
      if (object != null) {
         this.countValid++;
      }

      return this.list.add(object);
   }

   public Object set(int integer, Object object) {
      Object var6 = this.list.set(integer, object);
      if (object != var6) {
         if (var6 == null) {
            this.countValid++;
         }

         if (object == null) {
            this.countValid--;
         }
      }

      return var6;
   }

   public Object remove(int integer) {
      Object var5 = this.list.remove(integer);
      if (var5 != null) {
         this.countValid--;
      }

      return var5;
   }

   public void clear() {
      this.list.clear();
      this.countValid = 0;
   }

   public void compact() {
      if (this.countValid <= 0 && this.list.size() == 0) {
         this.clear();
      } else if (this.list.size() > this.initialCapacity) {
         float var4 = (float)this.countValid / this.list.size();
         if (var4 <= this.loadFactor) {
            int var5 = 0;

            for (int var6 = 0; var6 < this.list.size(); var6++) {
               Object var7 = this.list.get(var6);
               if (var7 != null) {
                  if (var6 != var5) {
                     this.list.set(var5, var7);
                  }

                  var5++;
               }
            }

            if (this.list.size() > var5) {
               this.list.subList(var5, this.list.size()).clear();
            }
         }
      }
   }

   public boolean contains(Object object) {
      return this.list.contains(object);
   }

   public Object get(int integer) {
      return this.list.get(integer);
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public int size() {
      return this.list.size();
   }

   public int getCountValid() {
      return this.countValid;
   }
}
