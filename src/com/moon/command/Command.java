package com.moon.command;

import com.moon.command.element.Argument;
import com.moon.command.element.CommandExecutionResult;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public abstract class Command {
   protected static final Minecraft minecraft = Minecraft.getMinecraft();
   private static final TaggedLogger logger = Logger.tag("command");
   private final String label;
   private final String description;
   private final String[] handles;
   private final List<Argument<?>> arguments = new ArrayList<>();

   public abstract CommandExecutionResult onRun();

   public void scanArguments() {
      for (Field field : this.getClass().getDeclaredFields()) {
         try {
            field.setAccessible(true);
            if (field.get(this) instanceof Argument argument) {
               this.arguments.add(argument);
            }
         } catch (IllegalAccessException e) {
            logger.error("Error while resolving arguments in object " + this, new Object[]{e});
         }
      }
   }

   public List<Argument<?>> getArguments() {
      return this.arguments.stream().filter(Argument::isRequired).collect(Collectors.toList());
   }

   public String getLabel() {
      return this.label;
   }

   public String getDescription() {
      return this.description;
   }

   public String[] getHandles() {
      return this.handles;
   }

   public Command(String label, String description, String[] handles) {
      this.label = label;
      this.description = description;
      this.handles = handles;
   }

   public List<Argument<?>> getAllArguments() {
      return this.arguments;
   }
}
