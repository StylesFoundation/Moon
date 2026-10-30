package com.moon.command.impl;

import com.moon.Moon;
import com.moon.command.Command;
import com.moon.command.element.CommandExecutionResult;
import com.moon.command.element.annotations.EnumMapping;
import com.moon.command.element.impl.EnumArgument;
import com.moon.command.element.impl.TextArgument;
import com.moon.module.Module;
import com.moon.module.ModuleCategory;
import com.moon.module.ModuleManager;
import com.moon.module.impl.visual.ClickGUI;
import com.moon.notification.Notification;
import com.moon.notification.NotificationType;
import com.moon.scripting.Script;
import com.moon.scripting.ScriptManager;
import com.moon.ui.clickgui.classic.component.CategoryPanel;
import com.moon.util.client.ChatUtil;
import java.awt.Desktop;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;

public class ScriptCommand extends Command {
   private final EnumArgument<ScriptCommand.Operations> action = EnumArgument.builder(ScriptCommand.Operations.class)
      .value("action")
      .description("The action you want to execute: list, load, unload or reload.")
      .required()
      .build();
   private final TextArgument name = TextArgument.builder()
      .value("name")
      .description(
         "The script name to load, unload or reload. This is required when loading, unloading or reloading a script even tho it is marked as optional."
      )
      .build();

   public ScriptCommand(String label, String description, String[] handles) {
      super(label, description, handles);
   }

   @Override
   public CommandExecutionResult onRun() {
      ScriptManager scriptManager = Moon.getInstance().getScriptManager();
      String scriptName = this.name.getValue();
      switch ((ScriptCommand.Operations)this.action.getValue()) {
         case FOLDER:
            try {
               Desktop.getDesktop().open(scriptManager.getScriptsDirectory());
            } catch (IOException e) {
            }
            break;
         case LIST:
            StringBuilder sb = new StringBuilder();
            Map<String, Script> scripts = scriptManager.getScripts();
            sb.append("<aqua>Available Scripts <gray>(").append(scripts.size()).append("):");

            for (Script script : scripts.values()) {
               sb.append("\n <gray>- <aqua>").append(script.getName());
            }

            ChatUtil.sendMessage(sb.toString());
            break;
         case LOAD:
            boolean hasLoadName = scriptName != null;
            if (!hasLoadName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("Make sure to provide a script name when loading a script!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            Script scriptToLoad = scriptManager.findScript(scriptName);
            if (scriptToLoad == null) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("The script " + scriptName + " could not be found!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            if (scriptToLoad.isLoaded()) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("The script " + scriptName + " is loaded already!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            this.loadScript(scriptToLoad);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Scripts")
                     .message("The script " + scriptName + " was loaded successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case UNLOAD:
            boolean hasUnloadName = scriptName != null;
            if (!hasUnloadName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("Make sure to provide a script name when unloading a script!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            Script scriptToUnload = scriptManager.findScript(scriptName);
            if (scriptToUnload == null) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("The script " + scriptName + " could not be found!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            if (!scriptToUnload.isLoaded()) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("The script " + scriptName + " is not loaded!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            this.unloadScript(scriptToUnload);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Scripts")
                     .message("The script " + scriptName + " was unloaded successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case RELOAD:
            boolean hasReloadName = scriptName != null;
            if (!hasReloadName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("Make sure to provide a script name when reloading a script!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            Script scriptToReload = scriptManager.findScript(scriptName);
            if (scriptToReload == null) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("The script " + scriptName + " could not be found!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            if (!scriptToReload.isLoaded()) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("The script " + scriptName + " is not loaded!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            this.unloadScript(scriptToReload);
            this.loadScript(scriptToReload);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Scripts")
                     .message("The script " + scriptName + " was reloaded successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
            break;
         case DELETE:
            boolean hasDeleteName = scriptName != null;
            if (!hasDeleteName) {
               Moon.getInstance()
                  .getNotificationManager()
                  .pop(
                     Notification.builder()
                        .title("Scripts")
                        .message("Make sure to provide a script name when deleting a script!")
                        .type(NotificationType.ERROR)
                        .duration(3000L)
                        .build()
                  );
               return CommandExecutionResult.builder().success(true).build();
            }

            scriptManager.unload(scriptName);
            Moon.getInstance()
               .getNotificationManager()
               .pop(
                  Notification.builder()
                     .title("Scripts")
                     .message("The script " + scriptName + " was deleted successfully!")
                     .type(NotificationType.SUCCESS)
                     .duration(3000L)
                     .build()
               );
      }

      return CommandExecutionResult.builder().success(true).build();
   }

   private void loadScript(Script script) {
      ScriptManager scriptManager = Moon.getInstance().getScriptManager();
      scriptManager.reloadSource(script);
      scriptManager.getScriptLoader().load(script);
   }

   private void unloadScript(Script script) {
      for (Module module : script.getModules()) {
         if (module.isEnabled()) {
            module.setEnabled(false);
         }

         ModuleManager moduleManager = Moon.getInstance().getModuleManager();
         Minecraft.getMinecraft().addScheduledTask(() -> {
            moduleManager.unregisterModule(module);
            Moon.getInstance().getBindManager().remove(module);
         });
      }

      Minecraft.getMinecraft().addScheduledTask(() -> {
         ModuleManager moduleManager = Moon.getInstance().getModuleManager();
         ClickGUI clickGui = Moon.getInstance().getModuleManager().getModule(ClickGUI.class);
         if (clickGui.getClassicScreen() != null) {
            List scriptModules = moduleManager.getModulesByCategory(ModuleCategory.SCRIPTS);
            CategoryPanel scriptsPanel = clickGui.getClassicScreen().getScriptsPanel();
            scriptsPanel.getModules().clear();
            scriptsPanel.getModules().addAll(scriptModules);
            scriptsPanel.init();
         }
      });
      script.getModules().clear();
      script.setLoaded(false);
   }

   @EnumMapping({"load", "unload", "reload", "delete", "list", "folder"})
   public enum Operations {
      LOAD,
      UNLOAD,
      RELOAD,
      DELETE,
      LIST,
      FOLDER;
   }
}
