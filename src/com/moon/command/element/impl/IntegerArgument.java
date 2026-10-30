package com.moon.command.element.impl;

import com.moon.command.element.Argument;
import com.moon.command.element.ArgumentBuilder;
import com.moon.command.element.ArgumentSuggestionType;

public class IntegerArgument extends Argument<Integer> {
   private int min;
   private int max;

   public static IntegerArgument.Builder builder() {
      return new IntegerArgument.Builder();
   }

   @Override
   public boolean parse(String input) {
      try {
         int parsedValue = Integer.parseInt(input);
         if (parsedValue >= this.min && parsedValue <= this.max) {
            this.value = parsedValue;
            return true;
         } else {
            return false;
         }
      } catch (NumberFormatException e) {
         return false;
      }
   }

   @Override
   public String suggest(String input) {
      return "[<" + this.min + "-" + this.max + ">]";
   }

   @Override
   public ArgumentSuggestionType getSuggestionType() {
      return ArgumentSuggestionType.SPACE;
   }

   @Override
   public boolean isVarArg() {
      return false;
   }

   public IntegerArgument setMin(int min) {
      this.min = min;
      return this;
   }

   public IntegerArgument setMax(int max) {
      this.max = max;
      return this;
   }

   public static class Builder extends ArgumentBuilder<IntegerArgument> {
      public Builder() {
         this.argument = new IntegerArgument();
         this.argument.setType(Integer.class);
      }

      public IntegerArgument.Builder range(int min, int max) {
         this.argument.setMin(min);
         this.argument.setMax(max);
         return this;
      }
   }
}
