package net.minecraft.util;

public class StatCollector {
   private static final StringTranslate localizedName = StringTranslate.getInstance();
   private static final StringTranslate fallbackTranslator = new StringTranslate();

   public static String translateToLocal(String string) {
      return localizedName.translateKey(string);
   }

   public static String translateToLocalFormatted(String string, Object... arr) {
      return localizedName.Method902(string, arr);
   }

   public static String translateToFallback(String string) {
      return fallbackTranslator.translateKey(string);
   }

   public static boolean canTranslate(String string) {
      return localizedName.isKeyTranslated(string);
   }

   public static long getLastTranslationUpdateTimeInMilliseconds() {
      return localizedName.getLastUpdateTimeInMilliseconds();
   }
}
