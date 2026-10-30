package net.optifine.util;

public class IntArray {
   private int[] array = null;
   private int position = 0;
   private int limit = 0;

   public IntArray(int integer) {
      this.array = new int[integer];
   }

   public void put(int integer) {
      this.array[this.position] = integer;
      this.position++;
      if (this.limit < this.position) {
         this.limit = this.position;
      }
   }

   public void put(int integer1, int integer2) {
      this.array[integer1] = integer2;
      if (this.limit < integer1) {
         this.limit = integer1;
      }
   }

   public void position(int integer) {
      this.position = integer;
   }

   public void put(int[] arr) {
      int var10000 = arr.length;

      for (int var9 : arr) {
         this.array[this.position] = var9;
         this.position++;
      }

      if (this.limit < this.position) {
         this.limit = this.position;
      }
   }

   public int get(int integer) {
      return this.array[integer];
   }

   public int[] getArray() {
      return this.array;
   }

   public void clear() {
      this.position = 0;
      this.limit = 0;
   }

   public int getLimit() {
      return this.limit;
   }

   public int getPosition() {
      return this.position;
   }
}
