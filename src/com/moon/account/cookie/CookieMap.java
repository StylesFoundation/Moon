package com.moon.account.cookie;

import java.util.HashMap;
import java.util.Map;

public class CookieMap {
   private final Map<String, String> cookies = new HashMap<>();

   public String serialize() {
      StringBuilder sb = new StringBuilder();
      this.cookies.forEach((name, value) -> sb.append(name).append("=").append(value).append("; "));
      return sb.toString().trim();
   }

   public Map<String, String> getCookies() {
      return this.cookies;
   }
}
