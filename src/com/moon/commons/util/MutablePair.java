package com.moon.commons.util;

public class MutablePair<K, V> {
   private K first;
   private V second;

   public MutablePair(K first, V second) {
      this.first = first;
      this.second = second;
   }

   public K first() {
      return this.first;
   }

   public void first(K first) {
      this.first = first;
   }

   public V second() {
      return this.second;
   }

   public void second(V second) {
      this.second = second;
   }
}
