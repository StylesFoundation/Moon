package com.moon.commons.repository.map;

public interface MapRepository<K, V> {
   void put(K key, V value);

   void remove(K key, V value);

   V get(K key);
}
