package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClassInheritanceMultiMap<T> extends AbstractSet<T> {
   private static final Set<Class<?>> field_181158_a = Sets.newHashSet();
   private final Map<Class<?>, List<T>> map = Maps.newHashMap();
   private final Set<Class<?>> knownKeys = Sets.newIdentityHashSet();
   private final Class<T> baseClass;
   private final List<T> values = Lists.newArrayList();

   public ClassInheritanceMultiMap(Class<T> class1) {
      this.baseClass = class1;
      this.knownKeys.add(class1);
      this.map.put(class1, this.values);

      for (Class var6 : field_181158_a) {
         this.createLookup(var6);
      }
   }

   protected void createLookup(Class<?> class1) {
      field_181158_a.add(class1);

      for (Object var6 : this.values) {
         if (class1.isAssignableFrom(var6.getClass())) {
            this.addForClass((T)var6, class1);
         }
      }

      this.knownKeys.add(class1);
   }

   protected Class<?> initializeClassLookup(Class<?> class1) {
      if (this.baseClass.isAssignableFrom(class1)) {
         if (!this.knownKeys.contains(class1)) {
            this.createLookup(class1);
         }

         return class1;
      } else {
         throw new IllegalArgumentException("Don't know how to search for " + class1);
      }
   }

   @Override
   public boolean add(T object) {
      for (Class var6 : this.knownKeys) {
         if (var6.isAssignableFrom(object.getClass())) {
            this.addForClass((T)object, var6);
         }
      }

      return true;
   }

   private void addForClass(T object, Class<?> class2) {
      List var6 = this.map.get(class2);
      if (var6 == null) {
         this.map.put(class2, Lists.newArrayList(object));
      } else {
         var6.add(object);
      }
   }

   @Override
   public boolean remove(Object object) {
      Object var5 = object;
      boolean var6 = false;

      for (Class var8 : this.knownKeys) {
         if (var8.isAssignableFrom(var5.getClass())) {
            List var9 = this.map.get(var8);
            if (var9 != null && var9.remove(var5)) {
               var6 = true;
            }
         }
      }

      return var6;
   }

   @Override
   public boolean contains(Object object) {
      return Iterators.contains(this.getByClass(object.getClass()).iterator(), object);
   }

   public <S> Iterable<S> getByClass(Class<S> class1) {
      return () -> {
         List var5 = this.map.get(this.initializeClassLookup(class1));
         return var5 == null ? Collections.emptyIterator() : Iterators.filter(var5.iterator(), class1);
      };
   }

   @Override
   public Iterator<T> iterator() {
      return this.values.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.values.iterator());
   }

   @Override
   public int size() {
      return this.values.size();
   }
}
