package net.minecraft.realms;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.tinylog.format.LegacyMessageFormatter;
import org.tinylog.format.MessageFormatter;
import org.tinylog.provider.LoggingProvider;
import org.tinylog.provider.ProviderRegistry;

public class RealmsServerAddress extends AbstractLogger {
   private static final MessageFormatter a = new LegacyMessageFormatter();
   private static final LoggingProvider b = ProviderRegistry.getLoggingProvider();
   private final String host;

   public RealmsServerAddress(String string) {
      this.host = string;
   }

   private static boolean Method643(Level level) {
      return b.getMinimumLevel().ordinal() <= level.intLevel();
   }

   private static boolean Method644(Level level) {
      return b.getMinimumLevel(null).ordinal() <= level.intLevel();
   }

   private static String Method645(Marker marker) {
      return marker == null ? null : marker.getName();
   }

   private static org.tinylog.Level Method646(Level level) {
      return switch (level.getStandardLevel()) {
         case OFF -> org.tinylog.Level.OFF;
         case FATAL, ERROR -> org.tinylog.Level.ERROR;
         case WARN -> org.tinylog.Level.WARN;
         case INFO -> org.tinylog.Level.INFO;
         case DEBUG -> org.tinylog.Level.DEBUG;
         case TRACE, ALL -> org.tinylog.Level.TRACE;
         default -> throw new IncompatibleClassChangeError();
      };
   }

   private static Level Method647(org.tinylog.Level level) {
      return switch (level) {
         case TRACE -> Level.TRACE;
         case DEBUG -> Level.DEBUG;
         case INFO -> Level.INFO;
         case WARN -> Level.WARN;
         case ERROR -> Level.ERROR;
         case OFF -> Level.OFF;
         default -> throw new IncompatibleClassChangeError();
      };
   }

   private static boolean Method648(Level level, Marker marker) {
      String var5 = Method645(marker);
      org.tinylog.Level var6 = Method646(level);
      org.tinylog.Level var7 = b.getMinimumLevel(var5);
      return var6.ordinal() >= var7.ordinal();
   }

   public String getHost() {
      return this.host;
   }

   @Override
   public void logMessage(String string, Level level, Marker marker, Message message, Throwable throwable) {
      Object[] var9 = message.getParameters();
      String var10 = marker == null ? null : marker.getName();
      org.tinylog.Level var11 = Method646(level);
      b.log(9, var10, var11, throwable, a, message.getFormat(), var9);
   }

   @Override
   public Level getLevel() {
      return Method647(b.getMinimumLevel());
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, Message message, Throwable throwable) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, CharSequence charSequence, Throwable throwable) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, Object object, Throwable throwable) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, String string, Throwable throwable) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, String string) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, String string, Object... arr) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, String string, Object object) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5, Object object6) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7, Object object8) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(
      Level level, Marker marker, String string, Object object4, Object object5, Object object6, Object object7, Object object8, Object object9
   ) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(
      Level level,
      Marker marker,
      String string,
      Object object4,
      Object object5,
      Object object6,
      Object object7,
      Object object8,
      Object object9,
      Object object10
   ) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(
      Level level,
      Marker marker,
      String string,
      Object object4,
      Object object5,
      Object object6,
      Object object7,
      Object object8,
      Object object9,
      Object object10,
      Object object11
   ) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(
      Level level,
      Marker marker,
      String string,
      Object object4,
      Object object5,
      Object object6,
      Object object7,
      Object object8,
      Object object9,
      Object object10,
      Object object11,
      Object object12
   ) {
      return Method648(level, marker);
   }

   @Override
   public boolean isEnabled(
      Level level,
      Marker marker,
      String string,
      Object object4,
      Object object5,
      Object object6,
      Object object7,
      Object object8,
      Object object9,
      Object object10,
      Object object11,
      Object object12,
      Object object13
   ) {
      return Method648(level, marker);
   }
}
