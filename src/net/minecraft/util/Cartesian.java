package net.minecraft.util;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.UnmodifiableIterator;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class Cartesian {
   public static <T> Iterable<T[]> cartesianProduct(Class<T> class1, Iterable<? extends Iterable<? extends T>> iterable) {
      return new Cartesian.Product<>(class1, toArray(Iterable.class, iterable));
   }

   public static <T> Iterable<List<T>> cartesianProduct(Iterable<? extends Iterable<? extends T>> iterable) {
      return arraysAsLists(cartesianProduct(Object.class, iterable));
   }

   private static <T> Iterable<List<T>> arraysAsLists(Iterable<Object[]> iterable) {
      return Iterables.transform(iterable, new Cartesian.GetList());
   }

   private static <T> T[] toArray(Class<? super T> class1, Iterable<? extends T> iterable) {
      ArrayList var5 = Lists.newArrayList();

      for (Object var7 : iterable) {
         var5.add(var7);
      }

      return (T[])var5.toArray(createArray(class1, var5.size()));
   }

   private static <T> T[] createArray(Class<? super T> class1, int integer) {
      return (T[])((Object[])Array.newInstance(class1, integer));
   }

   static class GetList<T> implements Function<Object[], List<T>> {
      private GetList() {
      }

      public List<T> apply(Object[] arr) {
         return Arrays.asList((T[])arr);
      }
   }

   static class Product<T> implements Iterable<T[]> {
      private final Class<T> clazz;
      private final Iterable<? extends T>[] iterables;

      private Product(Class<T> class1, Iterable<? extends T>[] arr) {
         this.clazz = class1;
         this.iterables = arr;
      }

      @Override
      public Iterator<T[]> iterator() {
         return this.iterables.length == 0
            ? Collections.singletonList((T[])Cartesian.createArray(this.clazz, 0)).iterator()
            : new Cartesian.Product.ProductIterator<>(this.clazz, this.iterables);
      }

      static class ProductIterator<T> extends UnmodifiableIterator<T[]> {
         private int index = -2;
         private final Iterable<? extends T>[] iterables;
         private final Iterator<? extends T>[] iterators;
         private final T[] results;

         private ProductIterator(Class<T> class1, Iterable<? extends T>[] arr) {
            this.iterables = arr;
            this.iterators = Cartesian.createArray(Iterator.class, this.iterables.length);

            for (int var6 = 0; var6 < this.iterables.length; var6++) {
               this.iterators[var6] = arr[var6].iterator();
            }

            this.results = (T[])Cartesian.createArray(class1, this.iterators.length);
         }

         private void endOfData() {
            this.index = -1;
            Arrays.fill(this.iterators, null);
            Arrays.fill(this.results, null);
         }

         @Override
         public boolean hasNext() {
            if (this.index == -2) {
               this.index = 0;

               for (Iterator var7 : this.iterators) {
                  if (!var7.hasNext()) {
                     this.endOfData();
                     break;
                  }
               }

               return true;
            } else {
               if (this.index >= this.iterators.length) {
                  for (this.index = this.iterators.length - 1; this.index >= 0; this.index--) {
                     Iterator var4 = this.iterators[this.index];
                     if (var4.hasNext()) {
                        break;
                     }

                     if (this.index == 0) {
                        this.endOfData();
                        break;
                     }

                     var4 = this.iterables[this.index].iterator();
                     this.iterators[this.index] = var4;
                     if (!var4.hasNext()) {
                        this.endOfData();
                        break;
                     }
                  }
               }

               return this.index >= 0;
            }
         }

         public T[] next() {
            if (!this.hasNext()) {
               throw new NoSuchElementException();
            }

            while (this.index < this.iterators.length) {
               this.results[this.index] = (T)this.iterators[this.index].next();
               this.index++;
            }

            return (T[])((Object[])this.results.clone());
         }
      }
   }
}
