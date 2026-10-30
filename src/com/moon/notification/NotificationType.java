package com.moon.notification;

public enum NotificationType {
   SUCCESS("\ue876"),
   ERROR("\ue5cd"),
   INFO("\ue7f4");

   private final String icon;

   public String icon() {
      return this.icon;
   }

   NotificationType(String icon) {
      this.icon = icon;
   }
}
