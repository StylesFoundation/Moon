package net.minecraft.util;

import java.lang.reflect.Array;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeBoundList<T> {
   private final T[] field_152759_a;
   private final Class<? extends T> field_152760_b;
   private final ReadWriteLock field_152761_c = new ReentrantReadWriteLock();
   private int field_152762_d;
   private int field_152763_e;

   public ThreadSafeBoundList(Class<? extends T> class1, int integer) {
      this.field_152760_b = class1;
      this.field_152759_a = (T[])((Object[])Array.newInstance(class1, integer));
   }

   public T func_152757_a(T object) {
      this.field_152761_c.writeLock().lock();
      this.field_152759_a[this.field_152763_e] = (T)object;
      this.field_152763_e = (this.field_152763_e + 1) % this.func_152758_b();
      if (this.field_152762_d < this.func_152758_b()) {
         this.field_152762_d++;
      }

      this.field_152761_c.writeLock().unlock();
      return (T)object;
   }

   public int func_152758_b() {
      this.field_152761_c.readLock().lock();
      int var1 = this.field_152759_a.length;
      this.field_152761_c.readLock().unlock();
      return var1;
   }

   public T[] Method441() {
      Object[] var4 = (Object[])Array.newInstance(this.field_152760_b, this.field_152762_d);
      this.field_152761_c.readLock().lock();

      for (int var5 = 0; var5 < this.field_152762_d; var5++) {
         int var6 = (this.field_152763_e - this.field_152762_d + var5) % this.func_152758_b();
         if (var6 < 0) {
            var6 += this.func_152758_b();
         }

         var4[var5] = this.field_152759_a[var6];
      }

      this.field_152761_c.readLock().unlock();
      return (T[])var4;
   }
}
