package com.moon.commons.consumer;

import java.util.Objects;

@FunctionalInterface
public interface TupleConsumer<K, V> {
   void accept(K k, V v);

   default TupleConsumer<K, V> andThen(TupleConsumer<? super K, ? super V> after) {
      Objects.requireNonNull(after);
      return (k, v) -> {
         this.accept(k, v);
         after.accept(k, v);
      };
   }
}
