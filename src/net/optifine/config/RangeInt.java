package net.optifine.config;

public class RangeInt {
   private final int min;
   private final int max;

   public RangeInt(int integer1, int integer2) {
      this.min = Math.min(integer1, integer2);
      this.max = Math.max(integer1, integer2);
   }

   public boolean isInRange(int integer) {
      return integer >= this.min && integer <= this.max;
   }

   public int getMin() {
      return this.min;
   }

   public int getMax() {
      return this.max;
   }

   @Override
   public String toString() {
      return "min: " + this.min + ", max: " + this.max;
   }
}
