package com.moon.commons.consumer;

import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, V, K> {
   void accept(T t, V v, K k);

   default TriConsumer<T, V, K> andThen(TriConsumer<? super T, ? super V, ? super K> after) {
      Objects.requireNonNull(after);
      return (t, v, k) -> {
         this.accept(t, v, k);
         after.accept(t, v, k);
      };
   }
}
