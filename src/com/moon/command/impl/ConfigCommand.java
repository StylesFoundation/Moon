package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.annotations.EnumMapping;
import com.moon.command.element.impl.ConfigArgument;
import com.moon.command.element.impl.EnumArgument;
import com.moon.config.Config;
import com.moon.config.ConfigManager;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.util.client.ChatUtil;
import java.awt.Desktop;
import java.io.IOException;
import java.util.Map;

public class ConfigCommand extends Command {
   private final EnumArgument<ConfigCommand.Operations> action = EnumArgument.builder(ConfigCommand.Operations.class)
      .value("action")
      .description("The action you want to execute, either save config, load config or list all configs.")
      .required()
      .build();
   private final ConfigArgument config = ConfigArgument.builder()
      .value("name")
      .description("The config name to load or save. This is required when loading or saving a config even tho it is marked as optional.")
      .build();

   public ConfigCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      ConfigManager configManager = Moon.getInstance().getConfigManager();
      String configName = this.config.getValue();
      switch ((ConfigCommand.Operations)this.action.getValue()) {
         case FOLDER:
            try {
               Desktop.getDesktop().open(configManager.getDirectory());
            } catch (IOException e) {
            }
            break;
         case LIST:
            StringBuilder builder = new StringBuilder();
            Map<String, Config> configs = configManager.getConfigs();
            builder.append("<aqua>Available Configs <gray>(").append(configs.size()).append("):");

            for (Config cfg : configs.values()) {
               builder.append("\n <gray>- <aqua>").append(cfg.getName());
            }

            ChatUtil.sendMessage(builder.toString());
            break;
         case LOAD:
            boolean hasLoadName = configName != null;
            if (!hasLoadName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Configs")
                        .message("Make sure to provide a config name when loading a config!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            Config loadedConfig = configManager.getConfig(configName);
            if (loadedConfig == null) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Configs")
                        .message("The provided config could not be found!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            loadedConfig.load(configManager.getDirectory());
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Configs")
                     .message("The config " + configName + " was loaded successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case SAVE:
            boolean hasSaveName = configName != null;
            if (!hasSaveName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Configs")
                        .message("Make sure to provide a config name when loading a config!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            configManager.save(configName);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Configs")
                     .message("The config " + configName + " was saved successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case DELETE:
            boolean hasDeleteName = configName != null;
            if (!hasDeleteName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Configs")
                        .message("Make sure to provide a config name when deleting a config!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            configManager.delete(configName);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Configs")
                     .message("The config " + configName + " was deleted successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
      }

      return CommandExecutionResult.builder().success(true).build();
   }

   @EnumMapping({"load", "save", "delete", "list", "folder"})
   public enum Operations {
      LOAD,
      SAVE,
      DELETE,
      LIST,
      FOLDER;
   }
}
