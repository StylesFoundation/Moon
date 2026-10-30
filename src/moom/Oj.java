package moom;

import java.net.URI;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerContextFactory;
import org.apache.logging.log4j.status.StatusLogger;

public class Oj implements LoggerContextFactory {
   private static final StatusLogger a = StatusLogger.getLogger();
   private static final LoggerContext b = new Oi();

   @Override
   public void shutdown(String string, ClassLoader classLoader, boolean boolean3, boolean boolean4) {
   }

   @Override
   public boolean hasContext(String string, ClassLoader classLoader, boolean boolean3) {
      return true;
   }

   @Override
   public LoggerContext getContext(String string, ClassLoader classLoader, Object object, boolean boolean4) {
      return b;
   }

   @Override
   public LoggerContext getContext(String string1, ClassLoader classLoader, Object object, boolean boolean4, URI uRI, String string6) {
      return b;
   }

   @Override
   public void removeContext(LoggerContext loggerContext) {
   }
}
