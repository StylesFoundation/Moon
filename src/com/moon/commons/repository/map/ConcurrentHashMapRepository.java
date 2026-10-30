package com.moon.commons.repository.map;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapRepository<K, V> implements MapRepository<K, V> {
   protected final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();

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
