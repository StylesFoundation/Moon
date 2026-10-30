package net.minecraft.command;

public class CommandException extends Exception {
   private final Object[] errorObjects;

   public CommandException(String string, Object... arr) {
      super(string);
      this.errorObjects = arr;
   }

   public Object[] getErrorObjects() {
      return this.errorObjects;
   }

   public static boolean Method917() {
      return true;
   }

   static {
      if (!Method917()) {
      }
   }
}
