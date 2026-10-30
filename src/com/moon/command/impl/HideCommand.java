package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.impl.ModuleArgument;
import com.moon.module.Module;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;

public class HideCommand extends Command {
   private final ModuleArgument module = ModuleArgument.builder().value("module").description("The module you want to hide or unhide").required().build();

   public HideCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      Module targetModule = this.module.getValue();
      targetModule.setHidden(!targetModule.isHidden());
      Moon.getInstance()
         .getNotificationManager()
         .pop(
            Notification.builder()
               .title("Hiding")
               .message("Toggled the hiding state of the module " + targetModule.getName() + "!")
               .type(NotificationType.SUCCESS)
               .duration(3000L)
               .build()
         );
      return CommandExecutionResult.builder().success(true).build();
   }
}
