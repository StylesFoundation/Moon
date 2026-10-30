package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.impl.EnumArgument;
import com.moon.command.element.impl.ModuleArgument;
import com.moon.command.element.impl.TextArgument;
import com.moon.module.Module;
import com.moon.module.bind.element.BindAction;
import com.moon.module.bind.element.BindType;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import org.lwjgl.input.Keyboard;

public class BindCommand extends Command {
   private final ModuleArgument module = ModuleArgument.builder().value("module").description("The module you want to bind").required().build();
   private final TextArgument key = TextArgument.builder().value("key").description("The key / button you want to bind the module to").required().build();
   private final EnumArgument<BindAction> action = EnumArgument.builder(BindAction.class)
      .value("action")
      .description("The action type you want to bind the module with, either hold or press.")
      .build();

   public BindCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      Module targetModule = this.module.getValue();
      String keyName = this.key.getValue();
      boolean isMouse = keyName.startsWith("MOUSE");
      targetModule.setKey(isMouse ? Integer.parseInt(keyName.substring(5)) : Keyboard.getKeyIndex(keyName.toUpperCase()));
      targetModule.setBindType(isMouse ? BindType.MOUSE : BindType.KEYBOARD);
      if (this.action.getValue() != null) {
         targetModule.setBindAction(this.action.getValue());
      }

      Moon.getInstance()
         .getNotificationManager()
         .pop(
            Notification.builder()
               .title("Binds")
               .message("Successfully bound module " + targetModule.getName() + "!")
               .type(NotificationType.SUCCESS)
               .duration(3000L)
               .build()
         );
      return CommandExecutionResult.builder().success(true).build();
   }
}
