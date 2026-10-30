package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;

public class ObjectIntIdentityMap<T> implements IObjectIntIterable<T> {
   private final IdentityHashMap<T, Integer> identityMap = new IdentityHashMap<>(512);
   private final List<T> objectList = Lists.newArrayList();

   public void put(T object, int integer) {
      this.identityMap.put((T)object, integer);

      while (this.objectList.size() <= integer) {
         this.objectList.add(null);
      }

      this.objectList.set(integer, (T)object);
   }

   public int get(T object) {
      Integer var4 = this.identityMap.get(object);
      return var4 == null ? -1 : var4;
   }

   public final T getByValue(int integer) {
      return integer >= 0 && integer < this.objectList.size() ? this.objectList.get(integer) : null;
   }

   @Override
   public Iterator<T> iterator() {
      return Iterators.filter(this.objectList.iterator(), Predicates.notNull());
   }
}
