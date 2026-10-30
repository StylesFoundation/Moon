package net.minecraft.client.resources;

import java.util.Map;

public class I18n {
   private static Locale i18nLocale;

   static void setLocale(Locale locale) {
      i18nLocale = locale;
   }

   public static String format(String string, Object... arr) {
      return i18nLocale.Method980(string, arr);
   }

   public static Map getLocaleProperties() {
      return i18nLocale.properties;
   }
}
