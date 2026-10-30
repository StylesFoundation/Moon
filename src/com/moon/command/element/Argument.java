package com.moon.command.element;

public abstract class Argument<T> {
   private String name;
   private String description;
   private Class<T> type;
   private boolean required;
   protected T value;

   public abstract boolean parse(String input);

   public boolean parseArgs(String[] args) {
      return false;
   }

   public abstract String suggest(String input);

   public abstract ArgumentSuggestionType getSuggestionType();

   public abstract boolean isVarArg();

   public String getName() {
      return this.name;
   }

   public String getDescription() {
      return this.description;
   }

   public Class<T> getType() {
      return this.type;
   }

   public boolean isRequired() {
      return this.required;
   }

   public T getValue() {
      return this.value;
   }

   public Argument<T> setName(String name) {
      this.name = name;
      return this;
   }

   public Argument<T> setDescription(String description) {
      this.description = description;
      return this;
   }

   public Argument<T> setType(Class<T> type) {
      this.type = type;
      return this;
   }

   public Argument<T> setRequired(boolean required) {
      this.required = required;
      return this;
   }

   public Argument<T> setValue(T value) {
      this.value = (T)value;
      return this;
   }
}
