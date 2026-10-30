package com.moon.command.element.impl;

import com.moon.Moon;
import com.moon.command.element.Argument;
import com.moon.command.element.ArgumentBuilder;
import com.moon.command.element.ArgumentSuggestionType;
import com.moon.module.Module;
import java.util.Collection;
import java.util.Comparator;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.minecraft.util.Tuple;

public class ModuleArgument extends Argument<Module> {
   public static ModuleArgument.Builder builder() {
      return new ModuleArgument.Builder();
   }

   @Override
   public boolean parse(String input) {
      Module module = Moon.getInstance().getModuleManager().getModuleByName(input.toLowerCase());
      if (module == null) {
         return false;
      }

      this.value = module;
      return true;
   }

   @Override
   public String suggest(String input) {
      Collection<Module> modules = Moon.getInstance().getModuleManager().getRegistry().getModuleNameMap().values();
      String lowerInput = input.toLowerCase();
      Tuple<Module, Integer> bestMatch = modules.stream()
         .map(module -> new Tuple<>(module, FuzzySearch.ratio(lowerInput, module.getName().toLowerCase())))
         .max(Comparator.comparing(Tuple::getSecond))
         .orElse(null);
      return bestMatch != null ? bestMatch.getFirst().getName().toLowerCase() : null;
   }

   @Override
   public ArgumentSuggestionType getSuggestionType() {
      return ArgumentSuggestionType.ALWAYS;
   }

   @Override
   public boolean isVarArg() {
      return false;
   }

   public static class Builder extends ArgumentBuilder<ModuleArgument> {
      public Builder() {
         this.argument = new ModuleArgument();
         this.argument.setType(Module.class);
      }
   }
}
