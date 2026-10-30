package com.moon.render.command.impl.shape;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class GradientCommand implements RenderCommand {
   private final float x;
   private final float y;
   private final float width;
   private final float height;
   private final int startColor;
   private final int endColor;
   private final boolean vertical;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float normLeft = this.x / screenWidth;
      float normRight = (this.x + this.width) / screenWidth;
      float normTop = this.y / screenHeight;
      float normBottom = (this.y + this.height) / screenHeight;
      float verticalFlag = this.vertical ? 1.0F : 0.0F;
      buffer.appendPosition(normRight, normTop)
         .appendVector2(this.width, this.height)
         .appendPosition(this.x, this.y)
         .appendVector2(screenWidth, screenHeight)
         .appendFloat(verticalFlag)
         .appendColor(this.startColor)
         .appendColor(this.endColor);
      buffer.appendPosition(normLeft, normTop)
         .appendVector2(this.width, this.height)
         .appendPosition(this.x, this.y)
         .appendVector2(screenWidth, screenHeight)
         .appendFloat(verticalFlag)
         .appendColor(this.startColor)
         .appendColor(this.endColor);
      buffer.appendPosition(normLeft, normBottom)
         .appendVector2(this.width, this.height)
         .appendPosition(this.x, this.y)
         .appendVector2(screenWidth, screenHeight)
         .appendFloat(verticalFlag)
         .appendColor(this.startColor)
         .appendColor(this.endColor);
      buffer.appendPosition(normRight, normBottom)
         .appendVector2(this.width, this.height)
         .appendPosition(this.x, this.y)
         .appendVector2(screenWidth, screenHeight)
         .appendFloat(verticalFlag)
         .appendColor(this.startColor)
         .appendColor(this.endColor);
      return 4;
   }

   @Override
   public int getVertexStride() {
      return 272;
   }

   private GradientCommand(float x, float y, float width, float height, int startColor, int endColor, boolean vertical) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.startColor = startColor;
      this.endColor = endColor;
      this.vertical = vertical;
   }

   public static GradientCommand of(float x, float y, float width, float height, int startColor, int endColor, boolean vertical) {
      return new GradientCommand(x, y, width, height, startColor, endColor, vertical);
   }
}
