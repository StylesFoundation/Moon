package net.minecraft.util;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RegistrySimple<K, V> implements IRegistry<K, V> {
   private static final Logger logger = LogManager.getLogger();
   protected final Map<K, V> registryObjects = this.createUnderlyingMap();

   protected Map<K, V> createUnderlyingMap() {
      return Maps.newHashMap();
   }

   @Override
   public V getObject(K object) {
      return this.registryObjects.get(object);
   }

   @Override
   public void putObject(K object1, V object2) {
      Validate.notNull(object1);
      Validate.notNull(object2);
      if (this.registryObjects.containsKey(object1)) {
         logger.debug("Adding duplicate key '" + object1 + "' to registry");
      }

      this.registryObjects.put((K)object1, (V)object2);
   }

   public Set<K> getKeys() {
      return Collections.unmodifiableSet(this.registryObjects.keySet());
   }

   public boolean containsKey(K object) {
      return this.registryObjects.containsKey(object);
   }

   @Override
   public Iterator<V> iterator() {
      return this.registryObjects.values().iterator();
   }
}
