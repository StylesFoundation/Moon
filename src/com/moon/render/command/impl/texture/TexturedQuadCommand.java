package com.moon.render.command.impl.texture;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class TexturedQuadCommand implements RenderCommand {
   private final float left;
   private final float top;
   private final float right;
   private final float bottom;
   private final float u0;
   private final float v0;
   private final float u1;
   private final float v1;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float minX = this.left / screenWidth;
      float maxX = this.right / screenWidth;
      float minY = this.top / screenHeight;
      float maxY = this.bottom / screenHeight;
      buffer.appendPosition(maxX, minY).appendVector2(this.u1, this.v0);
      buffer.appendPosition(minX, minY).appendVector2(this.u0, this.v0);
      buffer.appendPosition(minX, maxY).appendVector2(this.u0, this.v1);
      buffer.appendPosition(maxX, maxY).appendVector2(this.u1, this.v1);
      return 4;
   }

   @Override
   public int getVertexStride() {
      return 64;
   }

   private TexturedQuadCommand(float left, float top, float right, float bottom, float u0, float v0, float u1, float v1) {
      this.left = left;
      this.top = top;
      this.right = right;
      this.bottom = bottom;
      this.u0 = u0;
      this.v0 = v0;
      this.u1 = u1;
      this.v1 = v1;
   }

   public static TexturedQuadCommand of(float left, float top, float right, float bottom, float u0, float v0, float u1, float v1) {
      return new TexturedQuadCommand(left, top, right, bottom, u0, v0, u1, v1);
   }
}
