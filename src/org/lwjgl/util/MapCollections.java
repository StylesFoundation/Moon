package org.lwjgl.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

abstract class MapCollections<K, V> {
   EntrySet mEntrySet;
   KeySet mKeySet;
   ValuesCollection mValues;

   public static <K, V> boolean containsAllHelper(Map<K, V> map, Collection<?> collection) {
      for (Object o : collection) {
         if (!map.containsKey(o)) {
            return false;
         }
      }

      return true;
   }

   public static <K, V> boolean removeAllHelper(Map<K, V> map, Collection<?> collection) {
      int oldSize = map.size();

      for (Object o : collection) {
         map.remove(o);
      }

      return oldSize != map.size();
   }

   public static <K, V> boolean retainAllHelper(Map<K, V> map, Collection<?> collection) {
      int oldSize = map.size();
      map.keySet().removeIf(k -> !collection.contains(k));
      return oldSize != map.size();
   }

   public Object[] toArrayHelper(int offset) {
      int N = this.colGetSize();
      Object[] result = new Object[N];

      for (int i = 0; i < N; i++) {
         result[i] = this.colGetEntry(i, offset);
      }

      return result;
   }

   public <T> T[] toArrayHelper(T[] array, int offset) {
      int N = this.colGetSize();
      if (array.length < N) {
         T[] newArray = (T[])Array.newInstance(array.getClass().getComponentType(), N);
         array = newArray;
      }

      for (int i = 0; i < N; i++) {
         array[i] = (T)this.colGetEntry(i, offset);
      }

      if (array.length > N) {
         array[N] = null;
      }

      return array;
   }

   public static <T> boolean equalsSetHelper(Set<T> set, Object object) {
      if (set == object) {
         return true;
      }

      if (object instanceof Set<?> s) {
         try {
            return set.size() == s.size() && set.containsAll(s);
         } catch (NullPointerException | ClassCastException ignored) {
            return false;
         }
      } else {
         return false;
      }
   }

   public Set<Entry<K, V>> getEntrySet() {
      if (this.mEntrySet == null) {
         this.mEntrySet = new EntrySet();
      }

      return this.mEntrySet;
   }

   public Set<K> getKeySet() {
      if (this.mKeySet == null) {
         this.mKeySet = new KeySet();
      }

      return this.mKeySet;
   }

   public Collection<V> getValues() {
      if (this.mValues == null) {
         this.mValues = new ValuesCollection();
      }

      return this.mValues;
   }

   protected abstract int colGetSize();

   protected abstract Object colGetEntry(int var1, int var2);

   protected abstract int colIndexOfKey(Object var1);

   protected abstract int colIndexOfValue(Object var1);

   protected abstract Map<K, V> colGetMap();

   protected abstract void colPut(K var1, V var2);

   protected abstract V colSetValue(int var1, V var2);

   protected abstract void colRemoveAt(int var1);

   protected abstract void colClear();

   final class ArrayIterator<T> implements Iterator<T> {
      final int mOffset;
      int mSize;
      int mIndex;
      boolean mCanRemove = false;

      ArrayIterator(int offset) {
         this.mOffset = offset;
         this.mSize = MapCollections.this.colGetSize();
      }

      @Override
      public boolean hasNext() {
         return this.mIndex < this.mSize;
      }

      @Override
      public T next() {
         Object res = MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
         this.mIndex++;
         this.mCanRemove = true;
         return (T)res;
      }

      @Override
      public void remove() {
         if (!this.mCanRemove) {
            throw new IllegalStateException();
         }

         this.mIndex--;
         this.mSize--;
         this.mCanRemove = false;
         MapCollections.this.colRemoveAt(this.mIndex);
      }
   }

   final class EntrySet implements Set<Entry<K, V>> {
      public boolean add(Entry<K, V> object) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(Collection<? extends Entry<K, V>> collection) {
         int oldSize = MapCollections.this.colGetSize();

         for (Entry<K, V> entry : collection) {
            MapCollections.this.colPut(entry.getKey(), entry.getValue());
         }

         return oldSize != MapCollections.this.colGetSize();
      }

      @Override
      public void clear() {
         MapCollections.this.colClear();
      }

      @Override
      public boolean contains(Object o) {
         if (o instanceof Entry<?, ?> e) {
            int index = MapCollections.this.colIndexOfKey(e.getKey());
            if (index < 0) {
               return false;
            }

            Object foundVal = MapCollections.this.colGetEntry(index, 1);
            return Objects.equal(foundVal, e.getValue());
         } else {
            return false;
         }
      }

      @Override
      public boolean containsAll(Collection<?> collection) {
         for (Object o : collection) {
            if (!this.contains(o)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public boolean isEmpty() {
         return MapCollections.this.colGetSize() == 0;
      }

      @Override
      public Iterator<Entry<K, V>> iterator() {
         return MapCollections.this.new MapIterator();
      }

      @Override
      public boolean remove(Object object) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean removeAll(Collection<?> collection) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean retainAll(Collection<?> collection) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int size() {
         return MapCollections.this.colGetSize();
      }

      @Override
      public Object[] toArray() {
         throw new UnsupportedOperationException();
      }

      @Override
      public <T> T[] toArray(T[] array) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean equals(Object object) {
         return MapCollections.equalsSetHelper(this, object);
      }

      @Override
      public int hashCode() {
         int result = 0;

         for (int i = MapCollections.this.colGetSize() - 1; i >= 0; i--) {
            Object key = MapCollections.this.colGetEntry(i, 0);
            Object value = MapCollections.this.colGetEntry(i, 1);
            result += (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
         }

         return result;
      }
   }

   final class KeySet implements Set<K> {
      @Override
      public boolean add(K object) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(Collection<? extends K> collection) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void clear() {
         MapCollections.this.colClear();
      }

      @Override
      public boolean contains(Object object) {
         return MapCollections.this.colIndexOfKey(object) >= 0;
      }

      @Override
      public boolean containsAll(Collection<?> collection) {
         return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), collection);
      }

      @Override
      public boolean isEmpty() {
         return MapCollections.this.colGetSize() == 0;
      }

      @Override
      public Iterator<K> iterator() {
         return MapCollections.this.new ArrayIterator<>(0);
      }

      @Override
      public boolean remove(Object object) {
         int index = MapCollections.this.colIndexOfKey(object);
         if (index >= 0) {
            MapCollections.this.colRemoveAt(index);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean removeAll(Collection<?> collection) {
         return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), collection);
      }

      @Override
      public boolean retainAll(Collection<?> collection) {
         return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), collection);
      }

      @Override
      public int size() {
         return MapCollections.this.colGetSize();
      }

      @Override
      public Object[] toArray() {
         return MapCollections.this.toArrayHelper(0);
      }

      @Override
      public <T> T[] toArray(T[] array) {
         return (T[])MapCollections.this.toArrayHelper(array, 0);
      }

      @Override
      public boolean equals(Object object) {
         return MapCollections.equalsSetHelper(this, object);
      }

      @Override
      public int hashCode() {
         int result = 0;

         for (int i = MapCollections.this.colGetSize() - 1; i >= 0; i--) {
            Object obj = MapCollections.this.colGetEntry(i, 0);
            result += obj == null ? 0 : obj.hashCode();
         }

         return result;
      }
   }

   final class MapIterator implements Iterator<Entry<K, V>>, Entry<K, V> {
      int mEnd;
      int mIndex;
      boolean mEntryValid = false;

      MapIterator() {
         this.mEnd = MapCollections.this.colGetSize() - 1;
         this.mIndex = -1;
      }

      @Override
      public boolean hasNext() {
         return this.mIndex < this.mEnd;
      }

      public Entry<K, V> next() {
         this.mIndex++;
         this.mEntryValid = true;
         return this;
      }

      @Override
      public void remove() {
         if (!this.mEntryValid) {
            throw new IllegalStateException();
         }

         MapCollections.this.colRemoveAt(this.mIndex);
         this.mIndex--;
         this.mEnd--;
         this.mEntryValid = false;
      }

      @Override
      public K getKey() {
         if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         } else {
            return (K)MapCollections.this.colGetEntry(this.mIndex, 0);
         }
      }

      @Override
      public V getValue() {
         if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         } else {
            return (V)MapCollections.this.colGetEntry(this.mIndex, 1);
         }
      }

      @Override
      public V setValue(V object) {
         if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         } else {
            return MapCollections.this.colSetValue(this.mIndex, object);
         }
      }

      @Override
      public boolean equals(Object o) {
         if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         } else {
            return !(o instanceof Entry<?, ?> e)
               ? false
               : Objects.equal(e.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0))
                  && Objects.equal(e.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1));
         }
      }

      @Override
      public int hashCode() {
         if (!this.mEntryValid) {
            throw new IllegalStateException("This container does not support retaining Map.Entry objects");
         }

         Object key = MapCollections.this.colGetEntry(this.mIndex, 0);
         Object value = MapCollections.this.colGetEntry(this.mIndex, 1);
         return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
      }

      @Override
      public String toString() {
         return this.getKey() + "=" + this.getValue();
      }
   }

   final class ValuesCollection implements Collection<V> {
      @Override
      public boolean add(V object) {
         throw new UnsupportedOperationException();
      }

      @Override
      public boolean addAll(Collection<? extends V> collection) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void clear() {
         MapCollections.this.colClear();
      }

      @Override
      public boolean contains(Object object) {
         return MapCollections.this.colIndexOfValue(object) >= 0;
      }

      @Override
      public boolean containsAll(Collection<?> collection) {
         for (Object o : collection) {
            if (!this.contains(o)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public boolean isEmpty() {
         return MapCollections.this.colGetSize() == 0;
      }

      @Override
      public Iterator<V> iterator() {
         return MapCollections.this.new ArrayIterator<>(1);
      }

      @Override
      public boolean remove(Object object) {
         int index = MapCollections.this.colIndexOfValue(object);
         if (index >= 0) {
            MapCollections.this.colRemoveAt(index);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean removeAll(Collection<?> collection) {
         int N = MapCollections.this.colGetSize();
         boolean changed = false;

         for (int i = 0; i < N; i++) {
            Object cur = MapCollections.this.colGetEntry(i, 1);
            if (collection.contains(cur)) {
               MapCollections.this.colRemoveAt(i);
               i--;
               N--;
               changed = true;
            }
         }

         return changed;
      }

      @Override
      public boolean retainAll(Collection<?> collection) {
         int N = MapCollections.this.colGetSize();
         boolean changed = false;

         for (int i = 0; i < N; i++) {
            Object cur = MapCollections.this.colGetEntry(i, 1);
            if (!collection.contains(cur)) {
               MapCollections.this.colRemoveAt(i);
               i--;
               N--;
               changed = true;
            }
         }

         return changed;
      }

      @Override
      public int size() {
         return MapCollections.this.colGetSize();
      }

      @Override
      public Object[] toArray() {
         return MapCollections.this.toArrayHelper(1);
      }

      @Override
      public <T> T[] toArray(T[] array) {
         return (T[])MapCollections.this.toArrayHelper(array, 1);
      }
   }
}
