package com.moon.events.priority;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class PrioritySet<T> implements Set<T> {
   private final Map<Priority, Set<T>> prioritizedSets = new EnumMap<>(Priority.class);

   @Override
   public int size() {
      return this.prioritizedSets.values().stream().mapToInt(Set::size).sum();
   }

   @Override
   public boolean isEmpty() {
      return this.size() == 0;
   }

   @Override
   public boolean contains(Object o) {
      for (Set<T> set : this.prioritizedSets.values()) {
         if (set.contains(o)) {
            return true;
         }
      }

      return false;
   }

   public boolean containsPrioritized(Object o) {
      if (o instanceof Prioritization<?> prioritization) {
         Priority priority = prioritization.priority();
         if (this.prioritizedSets.containsKey(priority)) {
            return this.prioritizedSets.get(priority).contains(prioritization.item());
         }
      }

      return false;
   }

   @NotNull
   @Override
   public Iterator<T> iterator() {
      return this.prioritizedSets.entrySet().stream().flatMap(e -> e.getValue().stream()).iterator();
   }

   @NotNull
   @Override
   public Object[] toArray() {
      return this.prioritizedSets.entrySet().stream().flatMap(e -> e.getValue().stream()).toArray();
   }

   @NotNull
   @Override
   public <U> U[] toArray(@NotNull U[] a) {
      U[] array = (U[])Array.newInstance(a.getClass().getComponentType(), this.size());
      int index = 0;

      for (T t : this) {
         array[index++] = (U)t;
      }

      return array;
   }

   @Override
   public boolean add(T t) {
      return this.prioritizedSets.computeIfAbsent(Priority.NORMAL, k -> new HashSet<>()).add(t);
   }

   public boolean addPrioritized(Prioritization<T> item) {
      return this.prioritizedSets.computeIfAbsent(item.priority(), k -> new HashSet<>()).add(item.item());
   }

   @Override
   public boolean remove(Object o) {
      boolean modified = false;

      for (Set<T> set : this.prioritizedSets.values()) {
         modified |= set.remove(o);
      }

      return modified;
   }

   public boolean removePrioritized(Object o) {
      if (o instanceof Prioritization<?> prioritization) {
         Priority priority = prioritization.priority();
         if (this.prioritizedSets.containsKey(priority)) {
            return this.prioritizedSets.get(priority).remove(prioritization.item());
         }
      }

      return false;
   }

   @Override
   public boolean containsAll(@NotNull Collection<?> c) {
      for (Object item : c) {
         if (!this.contains(item)) {
            return false;
         }
      }

      return true;
   }

   public boolean containsAllPrioritized(@NotNull Collection<?> c) {
      for (Object item : c) {
         if (!this.containsPrioritized(item)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean addAll(@NotNull Collection<? extends T> c) {
      boolean modified = false;

      for (T item : c) {
         modified |= this.add(item);
      }

      return modified;
   }

   public boolean addAllPrioritized(@NotNull Collection<? extends Prioritization<T>> c) {
      boolean modified = false;

      for (Prioritization<T> item : c) {
         modified |= this.addPrioritized(item);
      }

      return modified;
   }

   @Override
   public boolean retainAll(@NotNull Collection<?> c) {
      boolean modified = false;

      for (Set<T> set : this.prioritizedSets.values()) {
         modified |= set.retainAll(c);
      }

      return modified;
   }

   @Override
   public boolean removeAll(@NotNull Collection<?> c) {
      boolean modified = false;

      for (Object item : c) {
         modified |= this.remove(item);
      }

      return modified;
   }

   public boolean removeAllPrioritized(@NotNull Collection<? extends Prioritization<T>> c) {
      boolean modified = false;

      for (Prioritization<T> item : c) {
         modified |= this.removePrioritized(item);
      }

      return modified;
   }

   @Override
   public void clear() {
      this.prioritizedSets.clear();
   }
}
