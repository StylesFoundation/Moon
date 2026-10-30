package net.minecraft.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.Iterator;
import java.util.Map;

public class RegistryNamespaced<K, V> extends RegistrySimple<K, V> implements IObjectIntIterable<V> {
   protected final ObjectIntIdentityMap<V> underlyingIntegerMap = new ObjectIntIdentityMap<>();
   protected final Map<V, K> inverseObjectRegistry = ((BiMap)this.registryObjects).inverse();

   public void register(int integer, K object2, V object3) {
      this.underlyingIntegerMap.put((V)object3, integer);
      this.putObject((K)object2, (V)object3);
   }

   @Override
   protected Map<K, V> createUnderlyingMap() {
      return HashBiMap.create();
   }

   @Override
   public V getObject(K object) {
      return super.getObject((K)object);
   }

   public K getNameForObject(V object) {
      return this.inverseObjectRegistry.get(object);
   }

   @Override
   public boolean containsKey(K object) {
      return super.containsKey((K)object);
   }

   public int getIDForObject(V object) {
      return this.underlyingIntegerMap.get((V)object);
   }

   public V getObjectById(int integer) {
      return this.underlyingIntegerMap.getByValue(integer);
   }

   @Override
   public Iterator<V> iterator() {
      return this.underlyingIntegerMap.iterator();
   }
}
