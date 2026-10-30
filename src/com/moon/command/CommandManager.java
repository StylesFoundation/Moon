package com.moon.command;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
   private final Map<String, Command> commands = new HashMap<>();

   public void registerCommand(Command command) {
      command.scanArguments();
      this.commands.put(command.getLabel().toLowerCase(), command);
      if (command.getHandles() != null) {
         for (String handle : command.getHandles()) {
            this.commands.put(handle.toLowerCase(), command);
         }
      }
   }

   public Map<String, Command> getCommands() {
      return this.commands;
   }
}
