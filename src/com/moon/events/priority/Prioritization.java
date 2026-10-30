package com.moon.events.priority;

public class Prioritization<T> {
   private final T item;
   private final Priority priority;

   public Prioritization(T item, Priority priority) {
      this.item = item;
      this.priority = priority;
   }

   public T item() {
      return this.item;
   }

   public Priority priority() {
      return this.priority;
   }
}
