package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.annotations.EnumMapping;
import com.moon.command.element.impl.EnumArgument;
import com.moon.command.element.impl.TextArgument;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.storage.FriendStorage;
import com.moon.storage.dataset.Friend;
import com.moon.util.client.ChatUtil;
import java.util.Map;
import java.util.Objects;

public class FriendCommand extends Command {
   private final EnumArgument<FriendCommand.Operation> action = EnumArgument.builder(FriendCommand.Operation.class)
      .value("action")
      .description("The action you want to execute, either add, remove a friend or list all friends.")
      .required()
      .build();
   private final TextArgument name = TextArgument.builder()
      .value("name")
      .description("The name of the friend you want to add or remove. (Required when adding or removing friends)")
      .build();
   private final TextArgument alias = TextArgument.builder().value("alias").description("The alias of the friend you want to add. (Optional)").build();

   public FriendCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      FriendStorage friendStorage = Moon.getInstance().getFriendStorage();
      String friendName = this.name.getValue();
      String friendAlias = Objects.requireNonNullElse(this.alias.getValue(), "");
      switch ((FriendCommand.Operation)this.action.getValue()) {
         case LIST:
            StringBuilder builder = new StringBuilder();
            Map friendsByName = friendStorage.getFriendsByName();
            builder.append("<aqua>Friends <gray>(").append(friendsByName.size()).append("):");

            for (Friend friend : (Iterable<Friend>) friendsByName.values()) {
               builder.append("\n <gray>- <aqua>").append(friend.getName());
               if (!friend.getAlias().isEmpty()) {
                  builder.append(" <gray>(").append(friend.getAlias()).append(")");
               }
            }

            ChatUtil.sendMessage(builder.toString());
            break;
         case ADD:
            boolean hasAddName = friendName != null;
            if (!hasAddName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Friends")
                        .message("Make sure to provide a player name when adding a friend!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            friendStorage.addFriend(friendName, friendAlias);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Friends")
                     .message("The friend " + friendName + " was added successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case CLEAR:
            friendStorage.getFriendsByName().clear();
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Friends")
                     .message("Your friends were cleared successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case REMOVE:
            boolean hasRemoveName = friendName != null;
            if (!hasRemoveName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Friends")
                        .message("Make sure to provide a player name when removing a friend!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            friendStorage.removeFriend(friendName);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Friends")
                     .message("The friend " + friendName + " was removed successfully!")
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
   }
}
