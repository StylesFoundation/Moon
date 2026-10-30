package com.moon.command.element.impl;

import com.moon.Moon;
import com.moon.command.element.Argument;
import com.moon.command.element.ArgumentBuilder;
import com.moon.command.element.ArgumentSuggestionType;
import com.moon.config.Config;
import java.util.Collection;
import java.util.Comparator;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.minecraft.util.Tuple;

public class ConfigArgument extends Argument<String> {
   public static ConfigArgument.Builder builder() {
      return new ConfigArgument.Builder();
   }

   @Override
   public boolean parse(String input) {
      this.value = input;
      return true;
   }

   @Override
   public String suggest(String input) {
      Collection<Config> configs = Moon.getInstance().getConfigManager().getConfigs().values();
      Tuple<Config, Integer> bestMatch = configs.stream()
         .map(config -> new Tuple<>(config, FuzzySearch.ratio(input, config.getName())))
         .max(Comparator.comparing(Tuple::getSecond))
         .orElse(null);
      return bestMatch != null ? bestMatch.getFirst().getName() : "[<text>]";
   }

   @Override
   public ArgumentSuggestionType getSuggestionType() {
      return ArgumentSuggestionType.ALWAYS;
   }

   @Override
   public boolean isVarArg() {
      return false;
   }

   public static class Builder extends ArgumentBuilder<ConfigArgument> {
      public Builder() {
         this.argument = new ConfigArgument();
         this.argument.setType(String.class);
      }
   }
}
