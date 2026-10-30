package com.moon.command.element.impl;

import com.moon.command.element.Argument;
import com.moon.command.element.ArgumentBuilder;
import com.moon.command.element.ArgumentSuggestionType;
import com.moon.command.element.annotations.EnumMapping;
import java.util.Arrays;
import java.util.Comparator;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import net.minecraft.util.Tuple;

public class EnumArgument<T extends Enum<?>> extends Argument<T> {
   private EnumMapping mapping;

   public static <T extends Enum<?>> EnumArgument.Builder<T> builder(Class<T> enumClass) {
      return new EnumArgument.Builder<>(enumClass);
   }

   @Override
   public boolean parse(String input) {
      String[] values = this.mapping.value();
      String lowerInput = input.toLowerCase();

      for (int i = 0; i < values.length; i++) {
         String name = values[i];
         if (name.equalsIgnoreCase(lowerInput)) {
            this.value = this.getType().getEnumConstants()[i];
            return true;
         }
      }

      return false;
   }

   @Override
   public String suggest(String input) {
      String[] values = this.mapping.value();
      String lowerInput = input.toLowerCase();
      Tuple bestMatch = Arrays.stream(values)
         .map(name -> new Tuple<>(name, FuzzySearch.ratio(lowerInput, name.toLowerCase())))
         .max(Comparator.comparing(Tuple::getSecond))
         .orElse(null);
      return bestMatch != null ? (String)bestMatch.getFirst() : "";
   }

   public void validateMapping() {
      if (!this.getType().isAnnotationPresent(EnumMapping.class)) {
         throw new RuntimeException("Missing mapping annotation on type argument " + this.getType().getSimpleName());
      }

      this.mapping = this.getType().getAnnotation(EnumMapping.class);
   }

   @Override
   public ArgumentSuggestionType getSuggestionType() {
      return ArgumentSuggestionType.ALWAYS;
   }

   @Override
   public boolean isVarArg() {
      return false;
   }

   public static class Builder<T extends Enum<?>> extends ArgumentBuilder<EnumArgument<T>> {
      public Builder(Class<T> enumClass) {
         this.argument = new EnumArgument<>();
         this.argument.setType(enumClass);
      }

      public EnumArgument<T> build() {
         this.argument.validateMapping();
         return (EnumArgument<T>)super.build();
      }
   }
}
