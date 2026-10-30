package net.optifine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final boolean logDetail = System.getProperty("log.detail", "false").equals("true");

   public static void detail(String string) {
      if (logDetail) {
         LOGGER.info("[OptiFine] " + string);
      }
   }

   public static void Method333(String string) {
      LOGGER.info("[OptiFine] " + string);
   }

   public static void Method334(String string) {
      LOGGER.warn("[OptiFine] " + string);
   }

   public static void Method335(String string, Throwable throwable) {
      LOGGER.warn("[OptiFine] " + string, throwable);
   }

   public static void error(String string) {
      LOGGER.error("[OptiFine] " + string);
   }

   public static void Method336(String string, Throwable throwable) {
      LOGGER.error("[OptiFine] " + string, throwable);
   }

   public static void log(String string) {
      Method333(string);
   }
}
