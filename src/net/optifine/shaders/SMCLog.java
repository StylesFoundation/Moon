package net.optifine.shaders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class SMCLog {
   private static final Logger a = LogManager.getLogger();

   public static void severe(String string) {
      a.error("[Shaders] " + string);
   }

   public static void warning(String string) {
      a.warn("[Shaders] " + string);
   }

   public static void info(String string) {
      a.info("[Shaders] " + string);
   }

   public static void fine(String string) {
      a.debug("[Shaders] " + string);
   }

   public static void Method978(String string, Object... arr) {
      String var4 = String.format(string, arr);
      a.error("[Shaders] " + var4);
   }

   public static void warning(String string, Object... arr) {
      String var4 = String.format(string, arr);
      a.warn("[Shaders] " + var4);
   }

   public static void info(String string, Object... arr) {
      String var4 = String.format(string, arr);
      a.info("[Shaders] " + var4);
   }

   public static void Method979(String string, Object... arr) {
      String var4 = String.format(string, arr);
      a.debug("[Shaders] " + var4);
   }
}
