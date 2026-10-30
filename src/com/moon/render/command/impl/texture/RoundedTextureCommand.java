package com.moon.render.command.impl.texture;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class RoundedTextureCommand implements RenderCommand {
   private final float x;
   private final float y;
   private final float right;
   private final float bottom;
   private final float u0;
   private final float v0;
   private final float u1;
   private final float v1;
   private final float radius;
   private final int color;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float minX = this.x / screenWidth;
      float maxX = this.right / screenWidth;
      float minY = this.y / screenHeight;
      float maxY = this.bottom / screenHeight;
      float width = this.right - this.x;
      float height = this.bottom - this.y;
      buffer.appendPosition(maxX, minY)
         .appendVector2(this.u1, this.v0)
         .appendVector2(width, height)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(this.x, this.y)
         .appendFloat(this.radius)
         .appendColor(this.color);
      buffer.appendPosition(minX, minY)
         .appendVector2(this.u0, this.v0)
         .appendVector2(width, height)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(this.x, this.y)
         .appendFloat(this.radius)
         .appendColor(this.color);
      buffer.appendPosition(minX, maxY)
         .appendVector2(this.u0, this.v1)
         .appendVector2(width, height)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(this.x, this.y)
         .appendFloat(this.radius)
         .appendColor(this.color);
      buffer.appendPosition(maxX, maxY)
         .appendVector2(this.u1, this.v1)
         .appendVector2(width, height)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(this.x, this.y)
         .appendFloat(this.radius)
         .appendColor(this.color);
      return 4;
   }

   @Override
   public int getVertexStride() {
      return 240;
   }

   private RoundedTextureCommand(float x, float y, float right, float bottom, float u0, float v0, float u1, float v1, float radius, int color) {
      this.x = x;
      this.y = y;
      this.right = right;
      this.bottom = bottom;
      this.u0 = u0;
      this.v0 = v0;
      this.u1 = u1;
      this.v1 = v1;
      this.radius = radius;
      this.color = color;
   }

   public static RoundedTextureCommand of(float x, float y, float right, float bottom, float u0, float v0, float u1, float v1, float radius, int color) {
      return new RoundedTextureCommand(x, y, right, bottom, u0, v0, u1, v1, radius, color);
   }
}
