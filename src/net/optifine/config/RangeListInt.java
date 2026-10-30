package net.optifine.config;

import net.minecraft.src.Config;

public class RangeListInt {
   private RangeInt[] ranges = new RangeInt[0];

   public RangeListInt() {
   }

   public RangeListInt(RangeInt rangeInt) {
      this.addRange(rangeInt);
   }

   public void addRange(RangeInt rangeInt) {
      this.ranges = (RangeInt[])Config.addObjectToArray(this.ranges, rangeInt);
   }

   public boolean isInRange(int integer) {
      for (RangeInt var8 : this.ranges) {
         if (var8.isInRange(integer)) {
            return true;
         }
      }

      return false;
   }

   public int getCountRanges() {
      return this.ranges.length;
   }

   public RangeInt getRange(int integer) {
      return this.ranges[integer];
   }

   @Override
   public String toString() {
      StringBuilder var4 = new StringBuilder();
      var4.append("[");

      for (int var5 = 0; var5 < this.ranges.length; var5++) {
         RangeInt var6 = this.ranges[var5];
         if (var5 > 0) {
            var4.append(", ");
         }

         var4.append(var6.toString());
      }

      var4.append("]");
      return var4.toString();
   }
}
