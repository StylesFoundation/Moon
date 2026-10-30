package net.minecraft.command;

public class NumberInvalidException extends CommandException {
   public NumberInvalidException() {
      this("commands.generic.num.invalid");
   }

   public NumberInvalidException(String string, Object... arr) {
      super(string, arr);
   }
}
