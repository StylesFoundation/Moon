package com.moon.command.element;

public class ArgumentBuilder<T extends Argument<?>> {
   protected T argument;

   public ArgumentBuilder<T> value(String name) {
      this.argument.setName(name);
      return this;
   }

   public ArgumentBuilder<T> description(String description) {
      this.argument.setDescription(description);
      return this;
   }

   public ArgumentBuilder<T> required() {
      this.argument.setRequired(true);
      return this;
   }

   public T build() {
      return this.argument;
   }
}
