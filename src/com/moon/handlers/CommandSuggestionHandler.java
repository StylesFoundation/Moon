package com.moon.handlers;

import com.moon.command.Command;
import com.moon.command.element.Argument;
import com.moon.command.element.ArgumentSuggestionType;
import com.moon.events.impl.module.CommandSuggestionEvent;
import com.moon.events.subscription.Subscribe;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.minecraft.util.Tuple;

public class CommandSuggestionHandler {
   private Map<String, Command> commandMap;
   private String lastSuggestion;
   private boolean suggestionAccepted;
   private String lastText;

   public CommandSuggestionHandler(Map<String, Command> commandMap) {
      this.commandMap = commandMap;
   }

   @Subscribe
   public void onSuggest(CommandSuggestionEvent event) {
      String text = event.getText().toLowerCase();
      if (text.startsWith(".")) {
         String[] parts = text.substring(1).split(" ");
         if (this.lastText.equals(text)) {
            event.setSuggestion(this.lastSuggestion);
            event.setAccepted(this.suggestionAccepted);
            return;
         }

         if (parts.length == 0) {
            this.clearSuggestion(event, text);
            return;
         }

         boolean isCommandOnly = parts.length == 1 && !text.endsWith(" ");
         String commandName = parts[0];
         if (isCommandOnly) {
            Tuple<Command, Integer> bestMatch = this.commandMap.values().stream().map(command -> {
               int ratio = FuzzySearch.ratio(commandName, command.getLabel().toLowerCase());

               for (String handle : command.getHandles()) {
                  int handleRatio = FuzzySearch.ratio(commandName, handle.toLowerCase());
                  if (ratio > handleRatio) {
                     ratio = handleRatio;
                  }
               }

               return new Tuple<>(command, ratio);
            }).max(Comparator.comparing(Tuple::getSecond)).orElse(null);
            if (bestMatch == null || bestMatch.getSecond() <= 0) {
               this.clearSuggestion(event, text);
               return;
            }

            String suggestion = "." + bestMatch.getFirst().getLabel().toLowerCase();
            event.setSuggestion(suggestion);
            event.setAccepted(true);
            this.lastSuggestion = suggestion;
            this.suggestionAccepted = true;
         } else {
            Command command = this.commandMap.get(commandName);
            if (command == null) {
               this.clearSuggestion(event, text);
               return;
            }

            int argIndex = parts.length - (text.endsWith(" ") ? 1 : 2);
            List allArgs = command.getAllArguments();
            if (allArgs.size() - 1 < argIndex) {
               this.clearSuggestion(event, text);
               return;
            }

            Argument argument = (Argument)allArgs.get(argIndex);
            if (argument.getSuggestionType() == ArgumentSuggestionType.ALWAYS) {
               String[] currentArgs = Arrays.copyOfRange(parts, 0, argIndex + 1);
               event.setSuggestion(this.createSuggestion(argument, currentArgs, parts));
               event.setAccepted(true);
            } else if (text.endsWith(" ")) {
               event.setSuggestion(this.createSuggestion(argument, parts, parts));
               event.setAccepted(false);
            } else {
               this.clearSuggestion(event, text);
            }
         }
      }

      this.lastText = text;
   }

   private void clearSuggestion(CommandSuggestionEvent event, String text) {
      event.setSuggestion("");
      this.lastSuggestion = "";
      this.suggestionAccepted = false;
      this.lastText = text;
   }

   private String createSuggestion(Argument<?> argument, String[] arguments, String[] allArguments) {
      String joinedArgs = String.join(" ", arguments);
      String lastArg = allArguments[allArguments.length - 1];
      String suggestion = "." + joinedArgs + " " + argument.suggest(lastArg);
      this.lastSuggestion = suggestion;
      this.suggestionAccepted = argument.getSuggestionType() == ArgumentSuggestionType.ALWAYS;
      return suggestion;
   }
}
