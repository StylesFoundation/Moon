package net.optifine.expr;

public class ParseException extends Exception {
   public ParseException(String string, Throwable throwable) {
      super(string, throwable);
   }

   public ParseException(String string) {
      super(string);
   }
}
