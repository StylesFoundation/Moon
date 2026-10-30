package com.moon.render.vertex;

import com.moon.render.attributes.AttributeFileLoader;
import com.moon.render.shader.ShaderProgram;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.tinylog.Logger;
import org.tinylog.TaggedLogger;

public class VertexAttributeLayout {
   private static final TaggedLogger LOGGER = Logger.tag("attributes/link");
   private final String attributeData;
   private final ShaderProgram shaderProgram;
   private final List<VertexAttribute> attributes = new ArrayList<>();
   private final String path;

   public void loadAttributes() {
      String resourcePath = "/engine/attributes/" + this.attributeData + ".attr";
      InputStream stream = this.getClass().getResourceAsStream(resourcePath);
      if (stream == null) {
         LOGGER.error("Unable to find attribute file {} in the engine package", new Object[]{resourcePath});
      } else {
         VertexAttributeDefinition definition = AttributeFileLoader.loadFromStream(stream, VertexAttributeDefinition.class);
         if (definition == null) {
            LOGGER.error("Unable to parse attribute file {}", new Object[]{resourcePath});
         } else {
            for (VertexAttributeDefinitionPart part : definition.getData()) {
               this.attributes.add(VertexAttribute.builder().path(part.name()).size(part.getSize()).type(part.getType()).build());
            }

            int stride = this.getStride();
            int pointer = 0;

            for (VertexAttribute attribute : this.attributes) {
               attribute.group(this);
               attribute.path(attribute.getPath());
               attribute.shader(this.shaderProgram);
               attribute.stride(stride);
               attribute.pointer(pointer);
               attribute.link();
               pointer += attribute.getSize() * attribute.getType().getByteSize();
            }
         }
      }
   }

   public void disableAttributes() {
      Iterator iterator = this.attributes.iterator();

      while (iterator.hasNext()) {
         ((VertexAttribute)iterator.next()).specify();
      }
   }

   private int getStride() {
      int stride = 0;

      for (VertexAttribute attribute : this.attributes) {
         stride += attribute.getSize() * attribute.getType().getByteSize();
      }

      return stride;
   }

   VertexAttributeLayout(String attributeData, ShaderProgram shaderProgram, String path) {
      this.attributeData = attributeData;
      this.shaderProgram = shaderProgram;
      this.path = path;
   }

   public static VertexAttributeLayout.Builder builder() {
      return new VertexAttributeLayout.Builder();
   }

   public String getAttributeData() {
      return this.attributeData;
   }

   public ShaderProgram getShaderProgram() {
      return this.shaderProgram;
   }

   public List<VertexAttribute> getAttributes() {
      return this.attributes;
   }

   public String getPath() {
      return this.path;
   }

   public static class Builder {
      private String attributeData;
      private ShaderProgram shaderProgram;
      private String path;

      Builder() {
      }

      public VertexAttributeLayout.Builder withAttributeData(String attributeData) {
         this.attributeData = attributeData;
         return this;
      }

      public VertexAttributeLayout.Builder withShaderProgram(ShaderProgram shaderProgram) {
         this.shaderProgram = shaderProgram;
         return this;
      }

      public VertexAttributeLayout.Builder withPath(String path) {
         this.path = path;
         return this;
      }

      public VertexAttributeLayout build() {
         return new VertexAttributeLayout(this.attributeData, this.shaderProgram, this.path);
      }

      @Override
      public String toString() {
         String builderPath = this.path;
         ShaderProgram builderShaderProgram = this.shaderProgram;
         String builderAttributeData = this.attributeData;
         return "AttributeSet.AttributeSetBuilder(data=" + builderAttributeData + ", shader=" + builderShaderProgram + ", path=" + builderPath + ")";
      }
   }
}
