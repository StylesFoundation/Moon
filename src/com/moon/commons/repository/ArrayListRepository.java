package com.moon.commons.repository;

import java.util.ArrayList;

public class ArrayListRepository<T> implements Repository<T> {
   protected final ArrayList<T> list = new ArrayList<>();

   @Override
   public void add(T type) {
      this.list.add(type);
   }

   @Override
   public void remove(T type) {
      this.list.remove(type);
   }
}
