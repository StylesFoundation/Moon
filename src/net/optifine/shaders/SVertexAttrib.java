package net.optifine.shaders;

import net.minecraft.client.renderer.vertex.VertexFormatElement;

public class SVertexAttrib {
   public int index;
   public int count;
   public VertexFormatElement.EnumType type;

   public SVertexAttrib(int integer1, int integer2, VertexFormatElement.EnumType enumType) {
      this.index = integer1;
      this.count = integer2;
      this.type = enumType;
   }
}
