package net.minecraft.command;

public class EntityNotFoundException extends CommandException {
   public EntityNotFoundException() {
      this("commands.generic.entity.notFound");
   }

   public EntityNotFoundException(String string, Object... arr) {
      super(string, arr);
   }
}
