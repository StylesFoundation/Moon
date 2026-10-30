package org.lwjgl.system;

import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;

public abstract class StructBuffer<T extends Struct, SELF extends StructBuffer<T, SELF>> extends CustomBuffer<SELF> implements Iterable<T> {
   protected StructBuffer(ByteBuffer container, int remaining) {
      super(MemoryUtil.memAddress(container), container, -1, 0, remaining, remaining);
   }

   protected StructBuffer(long address, @Nullable ByteBuffer container, int mark, int position, int limit, int capacity) {
      super(address, container, mark, position, limit, capacity);
   }

   @Override
   public int sizeof() {
      return this.getElementFactory().sizeof();
   }

   public T get() {
      return this.getElementFactory().wrap(this.address, this.nextGetIndex(), this.container);
   }

   public SELF get(T value) {
      int sizeof = this.getElementFactory().sizeof();
      MemoryUtil.memCopy(this.address + Integer.toUnsignedLong(this.nextGetIndex()) * sizeof, value.address(), sizeof);
      return this.self();
   }

   public SELF put(T value) {
      int sizeof = this.getElementFactory().sizeof();
      MemoryUtil.memCopy(value.address(), this.address + Integer.toUnsignedLong(this.nextPutIndex()) * sizeof, sizeof);
      return this.self();
   }

   public T get(int index) {
      return this.getElementFactory().wrap(this.address, check(index, this.limit), this.container);
   }

   public SELF get(int index, T value) {
      int sizeof = this.getElementFactory().sizeof();
      MemoryUtil.memCopy(this.address + Checks.check(index, this.limit) * sizeof, value.address(), sizeof);
      return this.self();
   }

   public SELF put(int index, T value) {
      int sizeof = this.getElementFactory().sizeof();
      MemoryUtil.memCopy(value.address(), this.address + Checks.check(index, this.limit) * sizeof, sizeof);
      return this.self();
   }

   public SELF apply(Consumer<T> consumer) {
      consumer.accept(this.get());
      return this.self();
   }

   public SELF apply(int index, Consumer<T> consumer) {
      consumer.accept(this.get(index));
      return this.self();
   }

   @Override
   public Iterator<T> iterator() {
      return new StructBuffer.StructIterator<>(this.address, this.container, this.getElementFactory(), this.position, this.limit);
   }

   @Override
   public void forEach(Consumer<? super T> action) {
      Objects.requireNonNull(action);
      T factory = this.getElementFactory();
      int i = this.position;

      for (int fence = this.limit; i < fence; i++) {
         action.accept(factory.wrap(this.address, i, this.container));
      }
   }

   @Override
   public Spliterator<T> spliterator() {
      return new StructBuffer.StructSpliterator<>(this.address, this.container, this.getElementFactory(), this.position, this.limit);
   }

   public Stream<T> stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   public Stream<T> parallelStream() {
      return StreamSupport.stream(this.spliterator(), true);
   }

   protected abstract T getElementFactory();

   private static int check(int index, int length) {
      if (!Checks.CHECKS || index >= 0 && length > index) {
         return index;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   private static class StructIterator<T extends Struct, SELF extends StructBuffer<T, SELF>> implements Iterator<T> {
      private long address;
      @Nullable
      private ByteBuffer container;
      private T factory;
      private int index;
      private int fence;

      StructIterator(long address, @Nullable ByteBuffer container, T factory, int position, int limit) {
         this.address = address;
         this.container = container;
         this.factory = factory;
         this.index = position;
         this.fence = limit;
      }

      @Override
      public boolean hasNext() {
         return this.index < this.fence;
      }

      public T next() {
         if (Checks.CHECKS && this.fence <= this.index) {
            throw new NoSuchElementException();
         } else {
            return this.factory.wrap(this.address, this.index++, this.container);
         }
      }

      @Override
      public void forEachRemaining(Consumer<? super T> action) {
         Objects.requireNonNull(action);
         int i = this.index;

         try {
            while (i < this.fence) {
               action.accept(this.factory.wrap(this.address, i, this.container));
               i++;
            }
         } finally {
            this.index = i;
         }
      }
   }

   private static class StructSpliterator<T extends Struct, SELF extends StructBuffer<T, SELF>> implements Spliterator<T> {
      private long address;
      @Nullable
      private ByteBuffer container;
      private T factory;
      private int index;
      private int fence;

      StructSpliterator(long address, @Nullable ByteBuffer container, T factory, int position, int limit) {
         this.address = address;
         this.container = container;
         this.factory = factory;
         this.index = position;
         this.fence = limit;
      }

      @Override
      public boolean tryAdvance(Consumer<? super T> action) {
         Objects.requireNonNull(action);
         if (this.index < this.fence) {
            action.accept(this.factory.wrap(this.address, this.index++, this.container));
            return true;
         } else {
            return false;
         }
      }

      @Nullable
      @Override
      public Spliterator<T> trySplit() {
         int lo = this.index;
         int mid = lo + this.fence >>> 1;
         return lo < mid ? new StructBuffer.StructSpliterator<>(this.address, this.container, this.factory, lo, this.index = mid) : null;
      }

      @Override
      public long estimateSize() {
         return this.fence - this.index;
      }

      @Override
      public int characteristics() {
         return 17744;
      }

      @Override
      public void forEachRemaining(Consumer<? super T> action) {
         Objects.requireNonNull(action);
         int i = this.index;

         try {
            while (i < this.fence) {
               action.accept(this.factory.wrap(this.address, i, this.container));
               i++;
            }
         } finally {
            this.index = i;
         }
      }

      @Override
      public Comparator<? super T> getComparator() {
         throw new IllegalStateException();
      }
   }
}
