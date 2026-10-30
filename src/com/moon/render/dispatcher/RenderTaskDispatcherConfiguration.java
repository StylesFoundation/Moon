package com.moon.render.dispatcher;

import com.moon.render.command.RenderCommand;

public class RenderTaskDispatcherConfiguration {
   private Class<? extends RenderCommand> commandType;
   private String attributeLayout;
   private String fragmentShader;
   private String vertexShader;
   private int drawMode;
   private boolean optionalColorBuffer;

   RenderTaskDispatcherConfiguration(
      Class<? extends RenderCommand> commandType, String attributeLayout, String fragmentShader, String vertexShader, int drawMode, boolean optionalColorBuffer
   ) {
      this.commandType = commandType;
      this.attributeLayout = attributeLayout;
      this.fragmentShader = fragmentShader;
      this.vertexShader = vertexShader;
      this.drawMode = drawMode;
      this.optionalColorBuffer = optionalColorBuffer;
   }

   public static RenderTaskDispatcherConfiguration.Builder builder() {
      return new RenderTaskDispatcherConfiguration.Builder();
   }

   public Class<? extends RenderCommand> getCommandType() {
      return this.commandType;
   }

   public String getAttributeLayout() {
      return this.attributeLayout;
   }

   public String getFragmentShader() {
      return this.fragmentShader;
   }

   public String getVertexShader() {
      return this.vertexShader;
   }

   public int getDrawMode() {
      return this.drawMode;
   }

   public boolean usesOptionalColorBuffer() {
      return this.optionalColorBuffer;
   }

   public static class Builder {
      private Class<? extends RenderCommand> commandType;
      private String attributeLayout;
      private String fragmentShader;
      private String vertexShader;
      private int drawMode;
      private boolean optionalColorBuffer;

      Builder() {
      }

      public RenderTaskDispatcherConfiguration.Builder withCommandType(Class<? extends RenderCommand> commandType) {
         this.commandType = commandType;
         return this;
      }

      public RenderTaskDispatcherConfiguration.Builder withAttributeLayout(String attributeLayout) {
         this.attributeLayout = attributeLayout;
         return this;
      }

      public RenderTaskDispatcherConfiguration.Builder withFragmentShader(String fragmentShader) {
         this.fragmentShader = fragmentShader;
         return this;
      }

      public RenderTaskDispatcherConfiguration.Builder withVertexShader(String vertexShader) {
         this.vertexShader = vertexShader;
         return this;
      }

      public RenderTaskDispatcherConfiguration.Builder withDrawMode(int drawMode) {
         this.drawMode = drawMode;
         return this;
      }

      public RenderTaskDispatcherConfiguration.Builder withOptionalColorBuffer(boolean optionalColorBuffer) {
         this.optionalColorBuffer = optionalColorBuffer;
         return this;
      }

      public RenderTaskDispatcherConfiguration build() {
         return new RenderTaskDispatcherConfiguration(
            this.commandType, this.attributeLayout, this.fragmentShader, this.vertexShader, this.drawMode, this.optionalColorBuffer
         );
      }

      @Override
      public String toString() {
         boolean optionalBuffer = this.optionalColorBuffer;
         int mode = this.drawMode;
         String vertex = this.vertexShader;
         String fragment = this.fragmentShader;
         String attributes = this.attributeLayout;
         Class allowed = this.commandType;
         return "TaskDispatcherConfiguration.TaskDispatcherConfigurationBuilder(allowed="
            + allowed
            + ", attributes="
            + attributes
            + ", fragment="
            + fragment
            + ", vertex="
            + vertex
            + ", mode="
            + mode
            + ", optionalBuffer="
            + optionalBuffer
            + ")";
      }
   }
}
