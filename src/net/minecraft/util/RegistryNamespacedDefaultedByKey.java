package net.minecraft.util;

import org.apache.commons.lang3.Validate;

public class RegistryNamespacedDefaultedByKey<K, V> extends RegistryNamespaced<K, V> {
   private final K defaultValueKey;
   private V defaultValue;

   public RegistryNamespacedDefaultedByKey(K object) {
      this.defaultValueKey = (K)object;
   }

   @Override
   public void register(int integer, K object2, V object3) {
      if (this.defaultValueKey.equals(object2)) {
         this.defaultValue = (V)object3;
      }

      super.register(integer, (K)object2, (V)object3);
   }

   public void validateKey() {
      Validate.notNull(this.defaultValueKey);
   }

   @Override
   public V getObject(K object) {
      Object var4 = super.getObject((K)object);
      return (V)(var4 == null ? this.defaultValue : var4);
   }

   @Override
   public V getObjectById(int integer) {
      Object var5 = super.getObjectById(integer);
      return (V)(var5 == null ? this.defaultValue : var5);
   }
}
