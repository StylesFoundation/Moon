package com.moon.command.element;

public class CommandExecutionResult {
   private boolean success;
   private Argument<?> argument;
   private String info;

   CommandExecutionResult(boolean success, Argument<?> argument, String info) {
      this.success = success;
      this.argument = argument;
      this.info = info;
   }

   public static CommandExecutionResult.Builder builder() {
      return new CommandExecutionResult.Builder();
   }

   public boolean isSuccess() {
      return this.success;
   }

   public Argument<?> getArgument() {
      return this.argument;
   }

   public String getInfo() {
      return this.info;
   }

   public static class Builder {
      private boolean success;
      private Argument<?> argument;
      private String info;

      Builder() {
      }

      public CommandExecutionResult.Builder success(boolean success) {
         this.success = success;
         return this;
      }

      public CommandExecutionResult.Builder argument(Argument<?> argument) {
         this.argument = argument;
         return this;
      }

      public CommandExecutionResult.Builder info(String info) {
         this.info = info;
         return this;
      }

      public CommandExecutionResult build() {
         return new CommandExecutionResult(this.success, this.argument, this.info);
      }

      @Override
      public String toString() {
         String info = this.info;
         Argument argument = this.argument;
         boolean success = this.success;
         return "CommandExecutionResult.CommandExecutionResultBuilder(success=" + success + ", argument=" + argument + ", info=" + info + ")";
      }
   }
}
