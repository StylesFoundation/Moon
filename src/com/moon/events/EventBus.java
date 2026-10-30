package com.moon.events;

import com.moon.commons.consumer.TriConsumer;
import com.moon.events.priority.Prioritization;
import com.moon.events.priority.Priority;
import com.moon.events.priority.PrioritySet;
import com.moon.events.subscription.Subscribe;
import com.moon.events.subscription.Subscription;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventBus<T> {
   protected final Map<Type, PrioritySet<Subscription<T>>> subscriptions;
   protected final Map<Object, Map<Type, Set<Subscription<T>>>> instanceSubscriptionsMap = new HashMap<>();

   public EventBus() {
      this.subscriptions = new HashMap<>();
   }

   public void subscribe(Object o) {
      this.changeSubscriptionState(o, (type, subscription, priority) -> {
         this.subscriptions.computeIfAbsent(type, k -> new PrioritySet<>()).addPrioritized(new Prioritization<>(subscription, priority));
         this.instanceSubscriptionsMap.computeIfAbsent(o, k -> new HashMap<>()).computeIfAbsent(type, k -> new HashSet<>()).add(subscription);
      });
   }

   public void unsubscribe(Object o) {
      Map<Type, Set<Subscription<T>>> subscriptions = this.instanceSubscriptionsMap.remove(o);
      if (subscriptions != null) {
         subscriptions.forEach((type, subscriptionSet) -> this.subscriptions.get(type).removeAll(subscriptionSet));
      }
   }

   protected void changeSubscriptionState(Object o, TriConsumer<Type, Subscription<T>, Priority> changer) {
      Class<?> type = o.getClass();

      for (Method method : type.getDeclaredMethods()) {
         if (method.isAnnotationPresent(Subscribe.class)) {
            Class<?>[] params = method.getParameterTypes();
            if (params.length == 1) {
               Priority priority = method.getDeclaredAnnotation(Subscribe.class).priority();
               Subscription<T> subscription = this.createSubscriptionFrom(method, o);
               if (subscription != null) {
                  changer.accept(params[0], subscription, priority);
               }
            }
         }
      }
   }

   private Subscription<T> createSubscriptionFrom(Method method, Object owner) {
      int modifiers = method.getModifiers();
      if (!Modifier.isPublic(modifiers)) {
         System.err.println("FATAL: Method " + method.getName() + " must be public!");
         return null;
      }

      Class<?> eventClazz = method.getParameterTypes()[0];

      try {
         MethodHandle handle = MethodHandles.lookup().unreflect(method);
         CallSite callSite = LambdaMetafactory.metafactory(
            MethodHandles.lookup(),
            "receive",
            MethodType.methodType(Subscription.class, owner.getClass()),
            MethodType.methodType(void.class, Object.class),
            handle,
            MethodType.methodType(void.class, eventClazz)
         );
         return (Subscription)callSite.getTarget().invoke((Object)owner);
      } catch (Throwable e) {
         System.err.println("FATAL: Could not construct subscription from method");
         e.printStackTrace();
         return null;
      }
   }

   public void publish(T event) {
      Type eventType = event.getClass();
      if (this.subscriptions.containsKey(eventType)) {
         for (Subscription<T> subscription : new java.util.ArrayList<>(this.subscriptions.get(eventType))) {
            subscription.receive(event);
         }
      }
   }
}
