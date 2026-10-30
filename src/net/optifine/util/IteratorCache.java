package net.optifine.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class IteratorCache {
   private static final Deque<IteratorCache.IteratorReusable<Object>> dequeIterators = new ArrayDeque<>();

   public static Iterator<Object> getReadOnly(List list) {
      synchronized (dequeIterators) {
         IteratorCache.IteratorReusable var5 = dequeIterators.pollFirst();
         if (var5 == null) {
            var5 = new IteratorCache.IteratorReadOnly();
         }

         var5.setList(list);
         return var5;
      }
   }

   private static void finished(IteratorCache.IteratorReusable<Object> iteratorReusable) {
      synchronized (dequeIterators) {
         if (dequeIterators.size() <= 1000) {
            iteratorReusable.setList(null);
            dequeIterators.addLast(iteratorReusable);
         }
      }
   }

   static {
      for (int var13 = 0; var13 < 1000; var13++) {
         IteratorCache.IteratorReadOnly var14 = new IteratorCache.IteratorReadOnly();
         dequeIterators.add(var14);
      }
   }

   public static class IteratorReadOnly implements IteratorCache.IteratorReusable<Object> {
      private List<Object> list;
      private int index;
      private boolean hasNext;

      @Override
      public void setList(List<Object> list) {
         if (this.hasNext) {
            throw new RuntimeException("Iterator still used, oldList: " + this.list + ", newList: " + list);
         }

         this.list = list;
         this.index = 0;
         this.hasNext = list != null && this.index < list.size();
      }

      @Override
      public Object next() {
         if (!this.hasNext) {
            return null;
         }

         Object var4 = this.list.get(this.index);
         this.index++;
         this.hasNext = this.index < this.list.size();
         return var4;
      }

      @Override
      public boolean hasNext() {
         if (!this.hasNext) {
            IteratorCache.finished(this);
            return false;
         } else {
            return this.hasNext;
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("remove");
      }
   }

   public interface IteratorReusable<E> extends Iterator<E> {
      void setList(List<E> list);
   }
}
