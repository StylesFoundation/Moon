package net.minecraft.util;

import java.util.Collection;
import java.util.Random;

public class WeightedRandom {
   public static int getTotalWeight(Collection<? extends WeightedRandom.Item> collection) {
      int var4 = 0;

      for (WeightedRandom.Item var6 : collection) {
         var4 += var6.itemWeight;
      }

      return var4;
   }

   public static <T extends WeightedRandom.Item> T getRandomItem(Random random, Collection<T> collection, int integer) {
      if (integer <= 0) {
         throw new IllegalArgumentException();
      }

      int var6 = random.nextInt(integer);
      return getRandomItem(collection, var6);
   }

   public static <T extends WeightedRandom.Item> T getRandomItem(Collection<T> collection, int integer) {
      for (WeightedRandom.Item var6 : collection) {
         integer -= var6.itemWeight;
         if (integer < 0) {
            return (T)var6;
         }
      }

      return null;
   }

   public static <T extends WeightedRandom.Item> T getRandomItem(Random random, Collection<T> collection) {
      return getRandomItem(random, collection, getTotalWeight(collection));
   }

   public static class Item {
      protected int itemWeight;

      public Item(int integer) {
         this.itemWeight = integer;
      }
   }
}
