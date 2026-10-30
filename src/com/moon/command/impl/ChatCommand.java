package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.impl.MessageArgument;

public class ChatCommand extends Command {
   private final MessageArgument message = MessageArgument.builder()
      .value("message")
      .description("The message you want to send in the moon user chat.")
      .required()
      .build();

   public ChatCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      return CommandExecutionResult.builder().success(true).build();
   }
}
