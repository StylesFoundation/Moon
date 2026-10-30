package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

public class IgnCommand extends Command {
   public IgnCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      String playerName = minecraft.thePlayer.getName();
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(playerName), null);
      Moon.getInstance()
         .getNotificationManager()
         .pop(Notification.builder().title("IGN").message("Copied IGN to your clipboard!").type(NotificationType.SUCCESS).duration(3000L).build());
      return CommandExecutionResult.builder().success(true).build();
   }
}
