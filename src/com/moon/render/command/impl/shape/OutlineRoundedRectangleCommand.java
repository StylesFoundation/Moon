package com.moon.render.command.impl.shape;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class OutlineRoundedRectangleCommand implements RenderCommand {
   private final float x;
   private final float y;
   private final float width;
   private final float height;
   private final float radius;
   private final float outlineThickness;
   private final int color;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float normLeft = this.x / screenWidth;
      float normRight = (this.x + this.width) / screenWidth;
      float normTop = this.y / screenHeight;
      float normBottom = (this.y + this.height) / screenHeight;
      buffer.appendPosition(normRight, normTop)
         .appendVector2(this.width, this.height)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(this.x, this.y)
         .appendFloat(this.radius)
         .appendFloat(this.outlineThickness)
         .appendColor(this.color);
      buffer.appendPosition(normLeft, normTop)
         .appendVector2(this.width, this.height)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(this.x, this.y)
         .appendFloat(this.radius)
         .appendFloat(this.outlineThickness)
         .appendColor(this.color);
      buffer.appendPosition(normLeft, normBottom)
         .appendVector2(this.width, this.height)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(this.x, this.y)
         .appendFloat(this.radius)
         .appendFloat(this.outlineThickness)
         .appendColor(this.color);
      buffer.appendPosition(normRight, normBottom)
         .appendVector2(this.width, this.height)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(this.x, this.y)
         .appendFloat(this.radius)
         .appendFloat(this.outlineThickness)
         .appendColor(this.color);
      return 4;
   }

   @Override
   public int getVertexStride() {
      return 224;
   }

   private OutlineRoundedRectangleCommand(float x, float y, float width, float height, float radius, float outlineThickness, int color) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.radius = radius;
      this.outlineThickness = outlineThickness;
      this.color = color;
   }

   public static OutlineRoundedRectangleCommand of(float x, float y, float width, float height, float radius, float outlineThickness, int color) {
      return new OutlineRoundedRectangleCommand(x, y, width, height, radius, outlineThickness, color);
   }
}
