package com.moon.viamcp.utils;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class JLoggerToLog4j extends Logger {
   private final org.apache.logging.log4j.Logger base;

   public JLoggerToLog4j(org.apache.logging.log4j.Logger logger) {
      super("Moon-Protocol", null);
      this.base = logger;
   }

   @Override
   public void log(LogRecord logRecord) {
      this.log(logRecord.getLevel(), logRecord.getMessage());
   }

   @Override
   public void log(Level level, String string) {
      if (level == Level.FINE) {
         this.base.debug(string);
      } else if (level == Level.WARNING) {
         this.base.warn(string);
      } else if (level == Level.SEVERE) {
         this.base.error(string);
      } else if (level == Level.INFO) {
         this.base.info(string);
      } else {
         this.base.trace(string);
      }
   }

   @Override
   public void log(Level level, String string, Object object) {
      if (level == Level.FINE) {
         this.base.debug(string, object);
      } else if (level == Level.WARNING) {
         this.base.warn(string, object);
      } else if (level == Level.SEVERE) {
         this.base.error(string, object);
      } else if (level == Level.INFO) {
         this.base.info(string, object);
      } else {
         this.base.trace(string, object);
      }
   }

   @Override
   public void log(Level level, String string, Object[] arr) {
      this.log(level, MessageFormat.format(string, arr));
   }

   @Override
   public void log(Level level, String string, Throwable throwable) {
      if (level == Level.FINE) {
         this.base.debug(string, throwable);
      } else if (level == Level.WARNING) {
         this.base.warn(string, throwable);
      } else if (level == Level.SEVERE) {
         this.base.error(string, throwable);
      } else if (level == Level.INFO) {
         this.base.info(string, throwable);
      } else {
         this.base.trace(string, throwable);
      }
   }
}
