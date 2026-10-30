package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.impl.ModuleArgument;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;

public class ToggleCommand extends Command {
   private final ModuleArgument module = ModuleArgument.builder().value("module").description("The module you want to toggle").required().build();

   public ToggleCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      this.module.getValue().toggle();
      Moon.getInstance()
         .getNotificationManager()
         .pop(
            Notification.builder()
               .title("Toggle")
               .message("Toggled the module " + this.module.getValue().getName() + "!")
               .type(NotificationType.SUCCESS)
               .duration(3000L)
               .build()
         );
      return CommandExecutionResult.builder().success(true).build();
   }
}
