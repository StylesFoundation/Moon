package com.moon.command.element.impl;

import com.moon.command.element.Argument;
import com.moon.command.element.ArgumentBuilder;
import com.moon.command.element.ArgumentSuggestionType;

public class FloatArgument extends Argument<Float> {
   private float min;
   private float max;

   @Override
   public boolean parse(String input) {
      try {
         float parsedValue = Float.parseFloat(input);
         if (!(parsedValue < this.min) && !(parsedValue > this.max)) {
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

   public static FloatArgument.Builder builder() {
      return new FloatArgument.Builder();
   }

   public FloatArgument setMin(float min) {
      this.min = min;
      return this;
   }

   public FloatArgument setMax(float max) {
      this.max = max;
      return this;
   }

   public static class Builder extends ArgumentBuilder<FloatArgument> {
      public Builder() {
         this.argument = new FloatArgument();
         this.argument.setType(Float.class);
      }

      public FloatArgument.Builder range(float min, float max) {
         this.argument.setMin(min);
         this.argument.setMax(max);
         return this;
      }
   }
}
