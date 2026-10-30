package com.moon.render.shader;

import java.nio.FloatBuffer;
import java.util.function.Supplier;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class ShaderUniform {
   private static final TaggedLogger LOGGER = Logger.tag("engine/shaders");
   private final String name;
   private final Supplier<ShaderProgram> programSupplier;
   private int location;

   public void link() {
      if (this.programSupplier.get() == null) {
         LOGGER.error("Failed to link uniform location, Shader is null! (Did the shader compile successfully?)");
      } else {
         this.location = GL20.glGetUniformLocation(this.programSupplier.get().getProgramId(), this.name);
         int error = GL11.glGetError();
         if (error != 0) {
            switch (error) {
               case 1281:
                  LOGGER.error("Failed to link uniform location as the associated shader program is not known by OpenGL");
                  break;
               case 1282:
                  LOGGER.error(
                     "Failed to link uniform location as the associated shader program does not contain this uniform or the program did not compile successfully!"
                  );
            }
         } else {
            LOGGER.info("Successfully linked uniform location with name: {}", new Object[]{this.name});
         }
      }
   }

   public void setMatrix4(boolean transpose, FloatBuffer buffer) {
      GL30.glUniformMatrix4fv(this.location, transpose, buffer);
   }

   public void setFloatArray(float[] values) {
      GL30.glUniform1fv(this.location, values);
   }

   public void setVector4(float x, float y, float z, float w) {
      GL30.glUniform4f(this.location, x, y, z, w);
   }

   public void setVector2(float x, float y) {
      GL30.glUniform2f(this.location, x, y);
   }

   public void setFloat(float value) {
      GL30.glUniform1f(this.location, value);
   }

   public void setInt(int value) {
      GL30.glUniform1i(this.location, value);
   }

   public void setBoolean(boolean value) {
      GL30.glUniform1i(this.location, value ? 1 : 0);
   }

   ShaderUniform(String name, Supplier<ShaderProgram> programSupplier, int location) {
      this.name = name;
      this.programSupplier = programSupplier;
      this.location = location;
   }

   public static ShaderUniform.Builder builder() {
      return new ShaderUniform.Builder();
   }

   public String getName() {
      return this.name;
   }

   public Supplier<ShaderProgram> getProgramSupplier() {
      return this.programSupplier;
   }

   public int getLocation() {
      return this.location;
   }

   public ShaderUniform setLocation(int location) {
      this.location = location;
      return this;
   }

   public static class Builder {
      private String name;
      private Supplier<ShaderProgram> programSupplier;
      private int location;

      Builder() {
      }

      public ShaderUniform.Builder withName(String name) {
         this.name = name;
         return this;
      }

      public ShaderUniform.Builder withProgramSupplier(Supplier<ShaderProgram> programSupplier) {
         this.programSupplier = programSupplier;
         return this;
      }

      public ShaderUniform.Builder withLocation(int location) {
         this.location = location;
         return this;
      }

      public ShaderUniform build() {
         return new ShaderUniform(this.name, this.programSupplier, this.location);
      }

      @Override
      public String toString() {
         int loc = this.location;
         Supplier<ShaderProgram> shader = this.programSupplier;
         String uniformName = this.name;
         return "Uniform.UniformBuilder(name=" + uniformName + ", shader=" + shader + ", location=" + loc + ")";
      }
   }
}
