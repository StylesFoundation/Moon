package net.optifine.util;

public class CounterInt {
   private final int startValue;
   private int value;

   public CounterInt(int integer) {
      this.startValue = integer;
      this.value = integer;
   }

   public synchronized int nextValue() {
      return this.value++;
   }

   public synchronized void reset() {
      this.value = this.startValue;
   }

   public int getValue() {
      return this.value;
   }
}
