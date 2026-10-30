package org.lwjgl.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class ArrayMap<K, V> implements Map<K, V> {
   private static final boolean DEBUG = false;
   private static final String TAG = "ArrayMap";
   private static final int BASE_SIZE = 4;
   private static final int CACHE_SIZE = 10;
   static final int[] EMPTY_IMMUTABLE_INTS = new int[0];
   public static final ArrayMap EMPTY = new ArrayMap(true);
   static Object[] mBaseCache;
   static int mBaseCacheSize;
   static Object[] mTwiceBaseCache;
   static int mTwiceBaseCacheSize;
   int[] mHashes;
   Object[] mArray;
   int mSize;
   MapCollections<K, V> mCollections;

   int indexOf(Object key, int hash) {
      int N = this.mSize;
      if (N == 0) {
         return -1;
      }

      int index = ContainerHelpers.binarySearch(this.mHashes, N, hash);
      if (index < 0) {
         return index;
      }

      if (key.equals(this.mArray[index << 1])) {
         return index;
      }

      int end;
      for (end = index + 1; end < N && this.mHashes[end] == hash; end++) {
         if (key.equals(this.mArray[end << 1])) {
            return end;
         }
      }

      for (int i = index - 1; i >= 0 && this.mHashes[i] == hash; i--) {
         if (key.equals(this.mArray[i << 1])) {
            return i;
         }
      }

      return ~end;
   }

   int indexOfNull() {
      int N = this.mSize;
      if (N == 0) {
         return -1;
      }

      int index = ContainerHelpers.binarySearch(this.mHashes, N, 0);
      if (index < 0) {
         return index;
      }

      if (null == this.mArray[index << 1]) {
         return index;
      }

      int end;
      for (end = index + 1; end < N && this.mHashes[end] == 0; end++) {
         if (null == this.mArray[end << 1]) {
            return end;
         }
      }

      for (int i = index - 1; i >= 0 && this.mHashes[i] == 0; i--) {
         if (null == this.mArray[i << 1]) {
            return i;
         }
      }

      return ~end;
   }

   private void allocArrays(int size) {
      if (this.mHashes == EMPTY_IMMUTABLE_INTS) {
         throw new UnsupportedOperationException("ArrayMap is immutable");
      }

      if (size == 8) {
         synchronized (ArrayMap.class) {
            if (mTwiceBaseCache != null) {
               Object[] array = mTwiceBaseCache;
               this.mArray = array;
               mTwiceBaseCache = (Object[])array[0];
               this.mHashes = (int[])array[1];
               array[0] = array[1] = null;
               mTwiceBaseCacheSize--;
               return;
            }
         }
      } else if (size == 4) {
         synchronized (ArrayMap.class) {
            if (mBaseCache != null) {
               Object[] array = mBaseCache;
               this.mArray = array;
               mBaseCache = (Object[])array[0];
               this.mHashes = (int[])array[1];
               array[0] = array[1] = null;
               mBaseCacheSize--;
               return;
            }
         }
      }

      this.mHashes = new int[size];
      this.mArray = new Object[size << 1];
   }

   private static void freeArrays(int[] hashes, Object[] array, int size) {
      if (hashes.length == 8) {
         synchronized (ArrayMap.class) {
            if (mTwiceBaseCacheSize < 10) {
               array[0] = mTwiceBaseCache;
               array[1] = hashes;

               for (int i = (size << 1) - 1; i >= 2; i--) {
                  array[i] = null;
               }

               mTwiceBaseCache = array;
               mTwiceBaseCacheSize++;
            }
         }
      } else if (hashes.length == 4) {
         synchronized (ArrayMap.class) {
            if (mBaseCacheSize < 10) {
               array[0] = mBaseCache;
               array[1] = hashes;

               for (int i = (size << 1) - 1; i >= 2; i--) {
                  array[i] = null;
               }

               mBaseCache = array;
               mBaseCacheSize++;
            }
         }
      }
   }

   public ArrayMap() {
      this.mHashes = EmptyArray.INT;
      this.mArray = EmptyArray.OBJECT;
      this.mSize = 0;
   }

   public ArrayMap(int capacity) {
      if (capacity == 0) {
         this.mHashes = EmptyArray.INT;
         this.mArray = EmptyArray.OBJECT;
      } else {
         this.allocArrays(capacity);
      }

      this.mSize = 0;
   }

   private ArrayMap(boolean immutable) {
      this.mHashes = immutable ? EMPTY_IMMUTABLE_INTS : EmptyArray.INT;
      this.mArray = EmptyArray.OBJECT;
      this.mSize = 0;
   }

   public ArrayMap(ArrayMap<K, V> map) {
      this();
      if (map != null) {
         this.putAll(map);
      }
   }

   @Override
   public void clear() {
      if (this.mSize > 0) {
         freeArrays(this.mHashes, this.mArray, this.mSize);
         this.mHashes = EmptyArray.INT;
         this.mArray = EmptyArray.OBJECT;
         this.mSize = 0;
      }
   }

   public void erase() {
      if (this.mSize > 0) {
         int N = this.mSize << 1;
         Object[] array = this.mArray;

         for (int i = 0; i < N; i++) {
            array[i] = null;
         }

         this.mSize = 0;
      }
   }

   public void ensureCapacity(int minimumCapacity) {
      if (this.mHashes.length < minimumCapacity) {
         int[] ohashes = this.mHashes;
         Object[] oarray = this.mArray;
         this.allocArrays(minimumCapacity);
         if (this.mSize > 0) {
            System.arraycopy(ohashes, 0, this.mHashes, 0, this.mSize);
            System.arraycopy(oarray, 0, this.mArray, 0, this.mSize << 1);
         }

         freeArrays(ohashes, oarray, this.mSize);
      }
   }

   @Override
   public boolean containsKey(Object key) {
      return this.indexOfKey(key) >= 0;
   }

   public int indexOfKey(Object key) {
      return key == null ? this.indexOfNull() : this.indexOf(key, key.hashCode());
   }

   int indexOfValue(Object value) {
      int N = this.mSize * 2;
      Object[] array = this.mArray;
      if (value == null) {
         for (int i = 1; i < N; i += 2) {
            if (array[i] == null) {
               return i >> 1;
            }
         }
      } else {
         for (int i = 1; i < N; i += 2) {
            if (value.equals(array[i])) {
               return i >> 1;
            }
         }
      }

      return -1;
   }

   @Override
   public boolean containsValue(Object value) {
      return this.indexOfValue(value) >= 0;
   }

   @Override
   public V get(Object key) {
      int index = this.indexOfKey(key);
      return (V)(index >= 0 ? this.mArray[(index << 1) + 1] : null);
   }

   public K keyAt(int index) {
      return (K)this.mArray[index << 1];
   }

   public V valueAt(int index) {
      return (V)this.mArray[(index << 1) + 1];
   }

   public V setValueAt(int index, V value) {
      index = (index << 1) + 1;
      V old = (V)this.mArray[index];
      this.mArray[index] = value;
      return old;
   }

   @Override
   public boolean isEmpty() {
      return this.mSize <= 0;
   }

   @Override
   public V put(K key, V value) {
      int hash;
      int index;
      if (key == null) {
         hash = 0;
         index = this.indexOfNull();
      } else {
         hash = key.hashCode();
         index = this.indexOf(key, hash);
      }

      if (index >= 0) {
         index = (index << 1) + 1;
         V old = (V)this.mArray[index];
         this.mArray[index] = value;
         return old;
      }

      index = ~index;
      if (this.mSize >= this.mHashes.length) {
         int n = this.mSize >= 8 ? this.mSize + (this.mSize >> 1) : (this.mSize >= 4 ? 8 : 4);
         int[] ohashes = this.mHashes;
         Object[] oarray = this.mArray;
         this.allocArrays(n);
         if (this.mHashes.length > 0) {
            System.arraycopy(ohashes, 0, this.mHashes, 0, ohashes.length);
            System.arraycopy(oarray, 0, this.mArray, 0, oarray.length);
         }

         freeArrays(ohashes, oarray, this.mSize);
      }

      if (index < this.mSize) {
         System.arraycopy(this.mHashes, index, this.mHashes, index + 1, this.mSize - index);
         System.arraycopy(this.mArray, index << 1, this.mArray, index + 1 << 1, this.mSize - index << 1);
      }

      this.mHashes[index] = hash;
      this.mArray[index << 1] = key;
      this.mArray[(index << 1) + 1] = value;
      this.mSize++;
      return null;
   }

   public void append(K key, V value) {
      int index = this.mSize;
      int hash = key == null ? 0 : key.hashCode();
      if (index >= this.mHashes.length) {
         throw new IllegalStateException("Array is full");
      }

      if (index > 0 && this.mHashes[index - 1] > hash) {
         RuntimeException e = new RuntimeException("here");
         e.fillInStackTrace();
         System.out.println("New hash " + hash + " is before end of array hash " + this.mHashes[index - 1] + " at index " + index + " key " + key);
         e.printStackTrace();
         this.put(key, value);
      } else {
         this.mSize = index + 1;
         this.mHashes[index] = hash;
         index <<= 1;
         this.mArray[index] = key;
         this.mArray[index + 1] = value;
      }
   }

   public void validate() {
      int N = this.mSize;
      if (N > 1) {
         int basehash = this.mHashes[0];
         int basei = 0;

         for (int i = 1; i < N; i++) {
            int hash = this.mHashes[i];
            if (hash != basehash) {
               basehash = hash;
               basei = i;
            } else {
               Object cur = this.mArray[i << 1];

               for (int j = i - 1; j >= basei; j--) {
                  Object prev = this.mArray[j << 1];
                  if (cur == prev) {
                     throw new IllegalArgumentException("Duplicate key in ArrayMap: " + cur);
                  }

                  if (cur != null && cur.equals(prev)) {
                     throw new IllegalArgumentException("Duplicate key in ArrayMap: " + cur);
                  }
               }
            }
         }
      }
   }

   public void putAll(ArrayMap<? extends K, ? extends V> array) {
      int N = array.mSize;
      this.ensureCapacity(this.mSize + N);
      if (this.mSize == 0) {
         if (N > 0) {
            System.arraycopy(array.mHashes, 0, this.mHashes, 0, N);
            System.arraycopy(array.mArray, 0, this.mArray, 0, N << 1);
            this.mSize = N;
         }
      } else {
         for (int i = 0; i < N; i++) {
            this.put((K)array.keyAt(i), (V)array.valueAt(i));
         }
      }
   }

   @Override
   public V remove(Object key) {
      int index = this.indexOfKey(key);
      return index >= 0 ? this.removeAt(index) : null;
   }

   public V removeAt(int index) {
      Object old = this.mArray[(index << 1) + 1];
      if (this.mSize <= 1) {
         freeArrays(this.mHashes, this.mArray, this.mSize);
         this.mHashes = EmptyArray.INT;
         this.mArray = EmptyArray.OBJECT;
         this.mSize = 0;
      } else if (this.mHashes.length > 8 && this.mSize < this.mHashes.length / 3) {
         int n = this.mSize > 8 ? this.mSize + (this.mSize >> 1) : 8;
         int[] ohashes = this.mHashes;
         Object[] oarray = this.mArray;
         this.allocArrays(n);
         this.mSize--;
         if (index > 0) {
            System.arraycopy(ohashes, 0, this.mHashes, 0, index);
            System.arraycopy(oarray, 0, this.mArray, 0, index << 1);
         }

         if (index < this.mSize) {
            System.arraycopy(ohashes, index + 1, this.mHashes, index, this.mSize - index);
            System.arraycopy(oarray, index + 1 << 1, this.mArray, index << 1, this.mSize - index << 1);
         }
      } else {
         this.mSize--;
         if (index < this.mSize) {
            System.arraycopy(this.mHashes, index + 1, this.mHashes, index, this.mSize - index);
            System.arraycopy(this.mArray, index + 1 << 1, this.mArray, index << 1, this.mSize - index << 1);
         }

         this.mArray[this.mSize << 1] = null;
         this.mArray[(this.mSize << 1) + 1] = null;
      }

      return (V)old;
   }

   @Override
   public int size() {
      return this.mSize;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (!(object instanceof Map<?, ?> map)) {
         return false;
      } else {
         if (this.size() != map.size()) {
            return false;
         }

         try {
            for (int i = 0; i < this.mSize; i++) {
               K key = this.keyAt(i);
               V mine = this.valueAt(i);
               Object theirs = map.get(key);
               if (mine == null) {
                  if (theirs != null || !map.containsKey(key)) {
                     return false;
                  }
               } else if (!mine.equals(theirs)) {
                  return false;
               }
            }

            return true;
         } catch (NullPointerException | ClassCastException ignored) {
            return false;
         }
      }
   }

   @Override
   public int hashCode() {
      int[] hashes = this.mHashes;
      Object[] array = this.mArray;
      int result = 0;
      int i = 0;
      int v = 1;

      for (int s = this.mSize; i < s; v += 2) {
         Object value = array[v];
         result += hashes[i] ^ (value == null ? 0 : value.hashCode());
         i++;
      }

      return result;
   }

   @Override
   public String toString() {
      if (this.isEmpty()) {
         return "{}";
      }

      StringBuilder buffer = new StringBuilder(this.mSize * 28);
      buffer.append('{');

      for (int i = 0; i < this.mSize; i++) {
         if (i > 0) {
            buffer.append(", ");
         }

         Object key = this.keyAt(i);
         if (key != this) {
            buffer.append(key);
         } else {
            buffer.append("(this Map)");
         }

         buffer.append('=');
         Object value = this.valueAt(i);
         if (value != this) {
            buffer.append(value);
         } else {
            buffer.append("(this Map)");
         }
      }

      buffer.append('}');
      return buffer.toString();
   }

   private MapCollections<K, V> getCollection() {
      if (this.mCollections == null) {
         this.mCollections = new MapCollections<K, V>() {
            @Override
            protected int colGetSize() {
               return ArrayMap.this.mSize;
            }

            @Override
            protected Object colGetEntry(int index, int offset) {
               return ArrayMap.this.mArray[(index << 1) + offset];
            }

            @Override
            protected int colIndexOfKey(Object key) {
               return ArrayMap.this.indexOfKey(key);
            }

            @Override
            protected int colIndexOfValue(Object value) {
               return ArrayMap.this.indexOfValue(value);
            }

            @Override
            protected Map<K, V> colGetMap() {
               return ArrayMap.this;
            }

            @Override
            protected void colPut(K key, V value) {
               ArrayMap.this.put(key, value);
            }

            @Override
            protected V colSetValue(int index, V value) {
               return (V)ArrayMap.this.setValueAt(index, value);
            }

            @Override
            protected void colRemoveAt(int index) {
               ArrayMap.this.removeAt(index);
            }

            @Override
            protected void colClear() {
               ArrayMap.this.clear();
            }
         };
      }

      return this.mCollections;
   }

   public boolean containsAll(Collection<?> collection) {
      return MapCollections.containsAllHelper(this, collection);
   }

   @Override
   public void putAll(Map<? extends K, ? extends V> map) {
      this.ensureCapacity(this.mSize + map.size());

      for (Entry<? extends K, ? extends V> entry : map.entrySet()) {
         this.put((K)entry.getKey(), (V)entry.getValue());
      }
   }

   public boolean removeAll(Collection<?> collection) {
      return MapCollections.removeAllHelper(this, collection);
   }

   public boolean retainAll(Collection<?> collection) {
      return MapCollections.retainAllHelper(this, collection);
   }

   @Override
   public Set<Entry<K, V>> entrySet() {
      return this.getCollection().getEntrySet();
   }

   @Override
   public Set<K> keySet() {
      return this.getCollection().getKeySet();
   }

   @Override
   public Collection<V> values() {
      return this.getCollection().getValues();
   }
}
