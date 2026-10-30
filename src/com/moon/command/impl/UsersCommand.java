package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.connection.Gateway;
import com.moon.connection.protobuf.sync.SynchronizedUser;
import com.moon.util.client.ChatUtil;
import java.util.Map;

public class UsersCommand extends Command {
   public UsersCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      ChatUtil.sendMessage("<red>You are not connected to the backend server!");

      return CommandExecutionResult.builder().success(true).build();
   }
}
