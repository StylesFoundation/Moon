package com.moon.render.command.impl.shape;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class TriangleCommand implements RenderCommand {
   private final float x1;
   private final float y1;
   private final float x2;
   private final float y2;
   private final float x3;
   private final float y3;
   private final int color;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      buffer.appendPosition(this.x1 / screenWidth, this.y1 / screenHeight).appendColor(this.color);
      buffer.appendPosition(this.x2 / screenWidth, this.y2 / screenHeight).appendColor(this.color);
      buffer.appendPosition(this.x3 / screenWidth, this.y3 / screenHeight).appendColor(this.color);
      return 3;
   }

   @Override
   public int getVertexStride() {
      return 72;
   }

   private TriangleCommand(float x1, float y1, float x2, float y2, float x3, float y3, int color) {
      this.x1 = x1;
      this.y1 = y1;
      this.x2 = x2;
      this.y2 = y2;
      this.x3 = x3;
      this.y3 = y3;
      this.color = color;
   }

   public static TriangleCommand of(float x1, float y1, float x2, float y2, float x3, float y3, int color) {
      return new TriangleCommand(x1, y1, x2, y2, x3, y3, color);
   }
}
