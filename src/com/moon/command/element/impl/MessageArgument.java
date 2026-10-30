package com.moon.command.element.impl;

import com.moon.command.element.Argument;
import com.moon.command.element.ArgumentBuilder;
import com.moon.command.element.ArgumentSuggestionType;

public class MessageArgument extends Argument<String> {
   @Override
   public boolean parse(String input) {
      return false;
   }

   @Override
   public boolean parseArgs(String[] args) {
      this.value = String.join(" ", args);
      return true;
   }

   @Override
   public String suggest(String input) {
      return "[<message>]";
   }

   @Override
   public ArgumentSuggestionType getSuggestionType() {
      return ArgumentSuggestionType.SPACE;
   }

   @Override
   public boolean isVarArg() {
      return true;
   }

   public static MessageArgument.Builder builder() {
      return new MessageArgument.Builder();
   }

   public static class Builder extends ArgumentBuilder<MessageArgument> {
      public Builder() {
         this.argument = new MessageArgument();
         this.argument.setType(String.class);
      }
   }
}
