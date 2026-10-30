package com.moon.render.vertex;

public class VertexAttributeDefinitionPart {
   private String _name;
   private int size;
   private VertexAttributeType dataType;

   public String name() {
      return this._name;
   }

   public String getName() {
      return this._name;
   }

   public int getSize() {
      return this.size;
   }

   public VertexAttributeType getType() {
      return this.dataType;
   }

   public VertexAttributeType getDataType() {
      return this.dataType;
   }

   public VertexAttributeDefinitionPart name(String name) {
      this._name = name;
      return this;
   }

   public VertexAttributeDefinitionPart size(int size) {
      this.size = size;
      return this;
   }

   public VertexAttributeDefinitionPart type(VertexAttributeType type) {
      this.dataType = type;
      return this;
   }

   public VertexAttributeDefinitionPart dataType(VertexAttributeType dataType) {
      this.dataType = dataType;
      return this;
   }

   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof VertexAttributeDefinitionPart other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.getSize() != other.getSize()) {
         return false;
      } else {
         String thisName = this.getName();
         String otherName = other.getName();
         if (thisName == null ? otherName == null : thisName.equals(otherName)) {
            VertexAttributeType thisType = this.getType();
            VertexAttributeType otherType = other.getType();
            return thisType == null ? otherType == null : thisType.equals(otherType);
         } else {
            return false;
         }
      }
   }

   protected boolean canEqual(Object object) {
      return object instanceof VertexAttributeDefinitionPart;
   }

   @Override
   public int hashCode() {
      int result = 59 + this.getSize();
      String name = this.getName();
      result = result * 59 + (name == null ? 43 : name.hashCode());
      VertexAttributeType type = this.getType();
      return result * 59 + (type == null ? 43 : type.hashCode());
   }

   @Override
   public String toString() {
      String name = this.getName();
      VertexAttributeType type = this.getType();
      int partSize = this.getSize();
      String nameStr = name;
      return "AttributeDataPart(_name=" + nameStr + ", size=" + partSize + ", dataType=" + type + ")";
   }
}
