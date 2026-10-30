package com.moon.render.vertex;

import com.moon.render.shader.ShaderProgram;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class VertexAttribute {
   private static final TaggedLogger LINK_LOGGER = Logger.tag("attributes/link");
   private static final TaggedLogger SPECIFY_LOGGER = Logger.tag("attributes/specify");
   private final int size;
   private final VertexAttributeType type;
   private String path;
   private ShaderProgram shader;
   private int cachedLocation;
   private int stride;
   private int pointer;
   private VertexAttributeLayout group;
   private int location;

   public void link() {
      if (this.shader == null) {
         LINK_LOGGER.error("Failed to link attribute location, Shader is null! (Did the shader compile successfully?)");
      } else {
         this.location = GL20.glGetAttribLocation(this.shader.getProgramId(), this.path);
         GL20.glEnableVertexAttribArray(this.location);
         int error = GL11.glGetError();
         if (error != 0) {
            LINK_LOGGER.error(
               "Failed to link attribute location as the associated shader program does not contain this attribute or the program did not compile successfully!"
            );
         } else {
            LINK_LOGGER.info("Successfully linked attribute location with path: {}", new Object[]{this.path});
         }
      }
   }

   public void specify() {
      GL20.glVertexAttribPointer(this.location, this.size, this.type.getGlConstant(), false, this.stride, this.pointer);
      int error = GL11.glGetError();
      if (error != 0) {
         switch (error) {
            case 1280:
               SPECIFY_LOGGER.error(
                  "Failed to specify attribute pointer for the attribute with path: {} as type is not an accepted value [type: {}]",
                  new Object[]{this.path, this.type}
               );
               break;
            case 1281:
               SPECIFY_LOGGER.error(
                  "Failed to specify attribute pointer for the attribute with path: {} as the generated index, size or stride was wrongly defined (index higher than GL_MAX_VERTEX_ATTRIBS, type not an accepted value, stride is negative) [type: {}, stride: {}]",
                  new Object[]{this.path, this.type, this.stride}
               );
               break;
            case 1282:
               SPECIFY_LOGGER.error(
                  "Failed to specify attribute pointer for the attribute with path: {} as the size or type is invalid [size: {}, type: {}, stride: {}, pointer: {}]",
                  new Object[]{this.path, this.size, this.type, this.stride, this.pointer}
               );
         }
      }
   }

   VertexAttribute(
      int size,
      VertexAttributeType type,
      String path,
      ShaderProgram shader,
      int cachedLocation,
      int stride,
      int pointer,
      VertexAttributeLayout group,
      int location
   ) {
      this.size = size;
      this.type = type;
      this.path = path;
      this.shader = shader;
      this.cachedLocation = cachedLocation;
      this.stride = stride;
      this.pointer = pointer;
      this.group = group;
      this.location = location;
   }

   public static VertexAttribute.Builder builder() {
      return new VertexAttribute.Builder();
   }

   public int getSize() {
      return this.size;
   }

   public VertexAttributeType getType() {
      return this.type;
   }

   public String getPath() {
      return this.path;
   }

   public ShaderProgram getShader() {
      return this.shader;
   }

   public int getCachedLocation() {
      return this.cachedLocation;
   }

   public int getStride() {
      return this.stride;
   }

   public int getPointer() {
      return this.pointer;
   }

   public VertexAttributeLayout getGroup() {
      return this.group;
   }

   public int getLocation() {
      return this.location;
   }

   public VertexAttribute path(String path) {
      this.path = path;
      return this;
   }

   public VertexAttribute shader(ShaderProgram shader) {
      this.shader = shader;
      return this;
   }

   public VertexAttribute cachedLocation(int cachedLocation) {
      this.cachedLocation = cachedLocation;
      return this;
   }

   public VertexAttribute stride(int stride) {
      this.stride = stride;
      return this;
   }

   public VertexAttribute pointer(int pointer) {
      this.pointer = pointer;
      return this;
   }

   public VertexAttribute group(VertexAttributeLayout group) {
      this.group = group;
      return this;
   }

   public VertexAttribute location(int location) {
      this.location = location;
      return this;
   }

   public static class Builder {
      private int size;
      private VertexAttributeType type;
      private String path;
      private ShaderProgram shader;
      private int cachedLocation;
      private int stride;
      private int pointer;
      private VertexAttributeLayout group;
      private int location;

      Builder() {
      }

      public VertexAttribute.Builder size(int size) {
         this.size = size;
         return this;
      }

      public VertexAttribute.Builder type(VertexAttributeType type) {
         this.type = type;
         return this;
      }

      public VertexAttribute.Builder path(String path) {
         this.path = path;
         return this;
      }

      public VertexAttribute.Builder shader(ShaderProgram shader) {
         this.shader = shader;
         return this;
      }

      public VertexAttribute.Builder cachedLocation(int cachedLocation) {
         this.cachedLocation = cachedLocation;
         return this;
      }

      public VertexAttribute.Builder stride(int stride) {
         this.stride = stride;
         return this;
      }

      public VertexAttribute.Builder pointer(int pointer) {
         this.pointer = pointer;
         return this;
      }

      public VertexAttribute.Builder group(VertexAttributeLayout group) {
         this.group = group;
         return this;
      }

      public VertexAttribute.Builder location(int location) {
         this.location = location;
         return this;
      }

      public VertexAttribute build() {
         return new VertexAttribute(this.size, this.type, this.path, this.shader, this.cachedLocation, this.stride, this.pointer, this.group, this.location);
      }

      @Override
      public String toString() {
         int loc = this.location;
         VertexAttributeLayout layout = this.group;
         int ptr = this.pointer;
         int strideVal = this.stride;
         int cachedLoc = this.cachedLocation;
         ShaderProgram shaderProgram = this.shader;
         String attrPath = this.path;
         VertexAttributeType attrType = this.type;
         int attrSize = this.size;
         return "Attribute.AttributeBuilder(size="
            + attrSize
            + ", type="
            + attrType
            + ", path="
            + attrPath
            + ", shader="
            + shaderProgram
            + ", cachedLocation="
            + cachedLoc
            + ", stride="
            + strideVal
            + ", pointer="
            + ptr
            + ", group="
            + layout
            + ", location="
            + loc
            + ")";
      }
   }
}
