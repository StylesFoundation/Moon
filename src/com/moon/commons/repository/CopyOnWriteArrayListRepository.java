package com.moon.commons.repository;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListRepository<T> implements Repository<T> {
   protected final CopyOnWriteArrayList<T> list = new CopyOnWriteArrayList<>();

   @Override
   public void add(T type) {
      this.list.add(type);
   }

   @Override
   public void remove(T type) {
      this.list.remove(type);
   }
}
