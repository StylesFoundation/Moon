package net.minecraft.util;

public interface IRegistry<K, V> extends Iterable<V> {
   V getObject(K object);

   void putObject(K object1, V object2);
}
