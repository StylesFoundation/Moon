package com.moon.render.vertex;

import java.util.Arrays;

public class VertexAttributeDefinition {
   private String path;
   private VertexAttributeDefinitionPart[] data;

   public String getPath() {
      return this.path;
   }

   public VertexAttributeDefinitionPart[] getData() {
      return this.data;
   }

   public VertexAttributeDefinition path(String path) {
      this.path = path;
      return this;
   }

   public VertexAttributeDefinition parts(VertexAttributeDefinitionPart[] parts) {
      this.data = parts;
      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof VertexAttributeDefinition other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         String thisPath = this.getPath();
         String otherPath = other.getPath();
         return (thisPath == null ? otherPath == null : thisPath.equals(otherPath)) ? Arrays.deepEquals(this.getData(), other.getData()) : false;
      }
   }

   protected boolean canEqual(Object object) {
      return object instanceof VertexAttributeDefinition;
   }

   @Override
   public int hashCode() {
      String pathStr = this.getPath();
      return (59 + (pathStr == null ? 43 : pathStr.hashCode())) * 59 + Arrays.deepHashCode(this.getData());
   }

   @Override
   public String toString() {
      return "AttributeData(path=" + this.getPath() + ", data=" + Arrays.deepToString(this.getData()) + ")";
   }
}
