package moom;

import net.minecraft.realms.RealmsServerAddress;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.spi.LoggerContext;
import org.apache.logging.log4j.spi.LoggerRegistry;

public class Oi implements LoggerContext {
   private final LoggerRegistry<ExtendedLogger> a = new LoggerRegistry();

   @Override
   public Object getExternalContext() {
      return null;
   }

   @Override
   public ExtendedLogger getLogger(String string) {
      if (!this.a.hasLogger(string)) {
         this.a.putIfAbsent(string, null, new RealmsServerAddress(string));
      }

      return this.a.getLogger(string);
   }

   @Override
   public ExtendedLogger getLogger(String string, MessageFactory messageFactory) {
      if (!this.a.hasLogger(string)) {
         this.a.putIfAbsent(string, null, new RealmsServerAddress(string));
      }

      return this.a.getLogger(string);
   }

   @Override
   public boolean hasLogger(String string) {
      return this.a.hasLogger(string);
   }

   @Override
   public boolean hasLogger(String string, MessageFactory messageFactory) {
      return this.a.hasLogger(string, messageFactory);
   }

   @Override
   public boolean hasLogger(String string, Class<? extends MessageFactory> class2) {
      return this.a.hasLogger(string, class2);
   }
}
