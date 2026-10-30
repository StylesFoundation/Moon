package com.moon.notification;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationManager {
   private final List<Notification> a = new CopyOnWriteArrayList<>();

   public void pop(Notification hA) {
      hA.initialize();
      this.a.add(hA);
   }

   public void Method757(Notification hA) {
      this.a.remove(hA);
   }

   public List<Notification> Method758() {
      return this.a;
   }
}
