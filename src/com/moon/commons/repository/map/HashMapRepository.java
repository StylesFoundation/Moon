package com.moon.commons.repository.map;

import java.util.HashMap;

public class HashMapRepository<K, V> implements MapRepository<K, V> {
   protected final HashMap<K, V> map = new HashMap<>();

   @Override
   public void put(K key, V value) {
      this.map.put(key, value);
   }

   @Override
   public void remove(K key, V value) {
      this.map.remove(key, value);
   }

   @Override
   public V get(K key) {
      return this.map.get(key);
   }
}
