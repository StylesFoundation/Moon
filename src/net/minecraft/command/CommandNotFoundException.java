package net.minecraft.command;

public class CommandNotFoundException extends CommandException {
   public CommandNotFoundException() {
      this("commands.generic.notFound");
   }

   public CommandNotFoundException(String string, Object... arr) {
      super(string, arr);
   }
}
