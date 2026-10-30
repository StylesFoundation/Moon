package com.moon.commons.repository;

public interface Repository<T> {
   void add(T object);

   void remove(T object);
}
