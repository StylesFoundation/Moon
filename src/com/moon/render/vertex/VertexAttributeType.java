package com.moon.render.vertex;

public enum VertexAttributeType {
   BYTE(5120, 1),
   UNSIGNED_BYTE(5121, 1),
   SHORT(5122, 2),
   UNSIGNED_SHORT(5123, 2),
   INT(5124, 4),
   UNSIGNED_INT(5125, 4),
   FLOAT(5126, 4),
   DOUBLE(5130, 8);

   private final int glConstant;
   private final int byteSize;

   public int getGlConstant() {
      return this.glConstant;
   }

   public int getByteSize() {
      return this.byteSize;
   }

   VertexAttributeType(int glConstant, int byteSize) {
      this.glConstant = glConstant;
      this.byteSize = byteSize;
   }
}
