package com.moon.handlers;

import com.moon.command.Command;
import com.moon.command.element.Argument;
import com.moon.command.element.CommandExecutionResult;
import com.moon.events.impl.network.ChatEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.util.client.ChatUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandHandler {
   private final Map<String, Command> commands;

   @Subscribe
   public void sendCommandUsage(ChatEvent event) {
      String message = event.getMessage();
      if (message.startsWith(".")) {
         event.setCancelled(true);
         String[] parts = message.substring(1).split(" ");
         String label = parts[0];
         Command command = this.commands.get(label.toLowerCase());
         if (command == null) {
            ChatUtil.sendMessage("<red>This command does not exist!");
            return;
         }

         for (Argument arg : command.getAllArguments()) {
            arg.setValue(null);
         }

         List allArgs = command.getAllArguments();
         List args = command.getArguments();
         if (args.size() > parts.length - 1) {
            this.onChatEvent(command);
            return;
         }

         if (parts.length > 1) {
            String[] inputArgs = Arrays.copyOfRange(parts, 1, parts.length);

            for (int i = 0; i < inputArgs.length; i++) {
               String argStr = inputArgs[i];
               if (allArgs.size() - 1 >= i) {
                  Argument targetArg = (Argument)allArgs.get(i);
                  boolean isVarArg = targetArg.isVarArg();
                  if (isVarArg) {
                     if (!targetArg.parseArgs(Arrays.copyOfRange(inputArgs, i, inputArgs.length))) {
                        String expectedType = targetArg.getType().getSimpleName();
                        String foundVal = argStr;
                        int argIndex = i;
                        ChatUtil.sendMessage("<red>Failed to parse argument at " + argIndex + " found " + foundVal + " expected " + expectedType + "</red>");
                        this.onChatEvent(command);
                        return;
                     }
                     break;
                  }

                  if (!targetArg.parse(argStr)) {
                     String expectedType = targetArg.getType().getSimpleName();
                     String foundVal = argStr;
                     int argIndex = i;
                     ChatUtil.sendMessage("<red>Failed to parse argument at " + argIndex + " found " + foundVal + " expected " + expectedType + "</red>");
                     this.onChatEvent(command);
                     return;
                  }
               }
            }
         }

         CommandExecutionResult result = command.onRun();
         if (!result.isSuccess()) {
            if (result.getArgument() != null) {
               ChatUtil.sendMessage("<red>Failed to parse argument " + result.getArgument().getName() + "</red>");
               this.onChatEvent(command);
            }

            if (result.getInfo() != null) {
               ChatUtil.sendMessage("<gray>Useful Information: " + result.getInfo());
            }
         }
      }
   }

   private void onChatEvent(Command command) {
      StringBuilder builder = new StringBuilder();
      StringBuilder syntax = new StringBuilder();
      builder.append(String.format("<aqua>%s</aqua><gray> Command (<aqua>%s arguments<gray>):\n", command.getLabel(), command.getAllArguments().size()));

      for (Argument argument : command.getAllArguments()) {
         builder.append(String.format("<aqua>%s <gray>- %s (<red>%s<gray>)\n", argument.getName(), argument.getDescription(), argument.getType().getSimpleName()));
         if (argument.isRequired()) {
            syntax.append(argument.suggest("")).append(" ");
         } else {
            syntax.append("(").append(argument.suggest("")).append(")").append(" ");
         }
      }

      builder.append(String.format("\n<gray>Example Syntax: <red>.%s %s", command.getLabel(), syntax));
      ChatUtil.sendMessage(builder.toString());
   }

   public CommandHandler(Map<String, Command> commands) {
      this.commands = commands;
   }
}
