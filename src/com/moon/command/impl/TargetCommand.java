package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.annotations.EnumMapping;
import com.moon.command.element.impl.EnumArgument;
import com.moon.command.element.impl.TextArgument;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.storage.TargetStorage;
import com.moon.storage.dataset.Target;
import com.moon.util.client.ChatUtil;
import java.util.Map;

public class TargetCommand extends Command {
   private final EnumArgument<TargetCommand.Operation> action = EnumArgument.builder(TargetCommand.Operation.class)
      .value("action")
      .description("The action you want to execute, either add, remove a target or list all targets.")
      .required()
      .build();
   private final TextArgument name = TextArgument.builder()
      .value("name")
      .description("The name of the target you want to add or remove. (Required when adding or removing targets)")
      .build();

   public TargetCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      TargetStorage targetStorage = Moon.getInstance().getTargetStorage();
      String targetName = this.name.getValue();
      switch ((TargetCommand.Operation)this.action.getValue()) {
         case LIST:
            StringBuilder sb = new StringBuilder();
            Map<String, Target> targets = targetStorage.getTargetsByName();
            sb.append("<aqua>Targets <gray>(").append(targets.size()).append("):");

            for (Target target : targets.values()) {
               sb.append("\n <gray>- <aqua>").append(target.getName());
            }

            ChatUtil.sendMessage(sb.toString());
            break;
         case ADD:
            boolean targetProvided = targetName != null;
            if (!targetProvided) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Toggle")
                        .message("Make sure to provide a player name when adding a target!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            targetStorage.addTarget(targetName);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Targets")
                     .message("The target " + targetName + " was added successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case CLEAR:
            targetStorage.getTargetsByName().clear();
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Targets")
                     .message("Your targets were cleared successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case REMOVE:
            boolean targetSpecified = targetName != null;
            if (!targetSpecified) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Toggle")
                        .message("Make sure to provide a player name when removing a target!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            targetStorage.removeTarget(targetName);
            ChatUtil.sendMessage("<aqua>The target " + targetName + " was removed successfully!");
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Targets")
                     .message("The target " + targetName + " was removed successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
      }

      return CommandExecutionResult.builder().success(true).build();
   }

   @EnumMapping({"add", "remove", "clear", "list"})
   public enum Operation {
      ADD,
      REMOVE,
      CLEAR,
      LIST;

      public static int getMaxTargets() {
         return 20;
      }
   }
}
