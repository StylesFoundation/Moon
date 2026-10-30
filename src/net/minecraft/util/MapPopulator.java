package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class MapPopulator {
   public static <K, V> Map<K, V> createMap(Iterable<K> iterable1, Iterable<V> iterable2) {
      return populateMap(iterable1, iterable2, Maps.newLinkedHashMap());
   }

   public static <K, V> Map<K, V> populateMap(Iterable<K> iterable1, Iterable<V> iterable2, Map<K, V> map) {
      Iterator<V> var6 = iterable2.iterator();

      for (K var8 : iterable1) {
         map.put(var8, var6.next());
      }

      if (var6.hasNext()) {
         throw new NoSuchElementException();
      } else {
         return map;
      }
   }
}
