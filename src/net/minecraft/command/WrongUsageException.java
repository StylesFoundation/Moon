package net.minecraft.command;

public class WrongUsageException extends SyntaxErrorException {
   public WrongUsageException(String string, Object... arr) {
      super(string, arr);
   }
}
