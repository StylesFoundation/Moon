package net.minecraft.util;

import java.io.OutputStream;
import java.io.PrintStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingPrintStream extends PrintStream {
   private static final Logger LOGGER = LogManager.getLogger();
   private final String domain;

   public LoggingPrintStream(String string, OutputStream outputStream) {
      super(outputStream);
      this.domain = string;
   }

   @Override
   public void println(String string) {
      this.logString(string);
   }

   @Override
   public void println(Object object) {
      this.logString(String.valueOf(object));
   }

   private void logString(String string) {
      StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
      StackTraceElement var3 = var2[Math.min(3, var2.length)];
      LOGGER.info("[{}]@.({}:{}): {}", this.domain, var3.getFileName(), var3.getLineNumber(), string);
   }
}
