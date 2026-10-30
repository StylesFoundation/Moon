package com.moon.handlers.culling;

enum IntervalRelation {
   INSIDE,
   POSITIVE,
   NEGATIVE;

   public static IntervalRelation classify(int min, int max, int value) {
      if (max > value && min > value) {
         return POSITIVE;
      } else {
         return min < value && max < value ? NEGATIVE : INSIDE;
      }
   }

   private static IntervalRelation[] createValues() {
      return new IntervalRelation[]{INSIDE, POSITIVE, NEGATIVE};
   }
}
