package com.moon.events.subscription;

@FunctionalInterface
public interface Subscription<T> {
   void receive(T object);
}
