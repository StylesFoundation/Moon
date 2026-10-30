package com.moon.command.element.impl;

import com.moon.command.element.Argument;
import com.moon.command.element.ArgumentBuilder;
import com.moon.command.element.ArgumentSuggestionType;

public class TextArgument extends Argument<String> {
   public static TextArgument.Builder builder() {
      return new TextArgument.Builder();
   }

   @Override
   public boolean parse(String input) {
      this.value = input;
      return true;
   }

   @Override
   public String suggest(String input) {
      return "[<text>]";
   }

   @Override
   public ArgumentSuggestionType getSuggestionType() {
      return ArgumentSuggestionType.SPACE;
   }

   @Override
   public boolean isVarArg() {
      return false;
   }

   public static class Builder extends ArgumentBuilder<TextArgument> {
      public Builder() {
         this.argument = new TextArgument();
         this.argument.setType(String.class);
      }
   }
}
