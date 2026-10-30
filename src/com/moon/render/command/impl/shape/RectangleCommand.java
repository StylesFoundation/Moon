package com.moon.render.command.impl.shape;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class RectangleCommand implements RenderCommand {
   private final float x;
   private final float y;
   private final float width;
   private final float height;
   private final int color;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float normLeft = this.x / screenWidth;
      float normRight = (this.x + this.width) / screenWidth;
      float normTop = this.y / screenHeight;
      float normBottom = (this.y + this.height) / screenHeight;
      buffer.appendPosition(normRight, normTop).appendColor(this.color);
      buffer.appendPosition(normLeft, normTop).appendColor(this.color);
      buffer.appendPosition(normLeft, normBottom).appendColor(this.color);
      buffer.appendPosition(normRight, normBottom).appendColor(this.color);
      return 4;
   }

   @Override
   public int getVertexStride() {
      return 96;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }

   public int getColor() {
      return this.color;
   }

   private RectangleCommand(float x, float y, float width, float height, int color) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.color = color;
   }

   public static RectangleCommand of(float x, float y, float width, float height, int color) {
      return new RectangleCommand(x, y, width, height, color);
   }
}
