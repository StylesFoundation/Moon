package net.minecraft.util;

public class RegistryDefaulted<K, V> extends RegistrySimple<K, V> {
   private final V defaultObject;

   public RegistryDefaulted(V object) {
      this.defaultObject = (V)object;
   }

   @Override
   public V getObject(K object) {
      Object var5 = super.getObject((K)object);
      return (V)(var5 == null ? this.defaultObject : var5);
   }
}
