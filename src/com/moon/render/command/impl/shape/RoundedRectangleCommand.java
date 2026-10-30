package com.moon.render.command.impl.shape;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class RoundedRectangleCommand implements RenderCommand {
   private final float x;
   private final float y;
   private final float width;
   private final float height;
   private final float radius;
   private final int color;
   private boolean roundTopLeft = true;
   private boolean roundTopRight = true;
   private boolean roundBottomRight = true;
   private boolean roundBottomLeft = true;
   private boolean pixelAligned = true;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float alignedX = this.x + (this.pixelAligned ? 0.0F : 0.5F);
      float alignedY = this.y + (this.pixelAligned ? 0.0F : 0.5F);
      float alignedHeight = this.height - (this.pixelAligned ? 0.0F : 1.0F);
      float alignedWidth = this.width - (this.pixelAligned ? 0.0F : 1.0F);
      float normLeft = alignedX / screenWidth;
      float normRight = (alignedX + alignedWidth) / screenWidth;
      float normTop = alignedY / screenHeight;
      float normBottom = (alignedY + alignedHeight) / screenHeight;
      byte cornerFlags = 0;
      if (!this.roundTopLeft) {
         cornerFlags = 1;
      }

      if (!this.roundTopRight) {
         cornerFlags |= 2;
      }

      if (!this.roundBottomRight) {
         cornerFlags |= 4;
      }

      if (!this.roundBottomLeft) {
         cornerFlags |= 8;
      }

      buffer.appendPosition(normRight, normTop)
         .appendVector2(alignedWidth, alignedHeight)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(alignedX, alignedY)
         .appendFloat(this.radius)
         .appendFloat(cornerFlags)
         .appendFloat(this.pixelAligned ? 1.0F : 0.0F)
         .appendColor(this.color);
      buffer.appendPosition(normLeft, normTop)
         .appendVector2(alignedWidth, alignedHeight)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(alignedX, alignedY)
         .appendFloat(this.radius)
         .appendFloat(cornerFlags)
         .appendFloat(this.pixelAligned ? 1.0F : 0.0F)
         .appendColor(this.color);
      buffer.appendPosition(normLeft, normBottom)
         .appendVector2(alignedWidth, alignedHeight)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(alignedX, alignedY)
         .appendFloat(this.radius)
         .appendFloat(cornerFlags)
         .appendFloat(this.pixelAligned ? 1.0F : 0.0F)
         .appendColor(this.color);
      buffer.appendPosition(normRight, normBottom)
         .appendVector2(alignedWidth, alignedHeight)
         .appendVector2(screenWidth, screenHeight)
         .appendPosition(alignedX, alignedY)
         .appendFloat(this.radius)
         .appendFloat(cornerFlags)
         .appendFloat(this.pixelAligned ? 1.0F : 0.0F)
         .appendColor(this.color);
      return 4;
   }

   @Override
   public int getVertexStride() {
      return 240;
   }

   private RoundedRectangleCommand(float x, float y, float width, float height, float radius, int color) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.radius = radius;
      this.color = color;
   }

   public static RoundedRectangleCommand of(float x, float y, float width, float height, float radius, int color) {
      return new RoundedRectangleCommand(x, y, width, height, radius, color);
   }

   public RoundedRectangleCommand setRoundTopLeft(boolean roundTopLeft) {
      this.roundTopLeft = roundTopLeft;
      return this;
   }

   public RoundedRectangleCommand setRoundTopRight(boolean roundTopRight) {
      this.roundTopRight = roundTopRight;
      return this;
   }

   public RoundedRectangleCommand setRoundBottomRight(boolean roundBottomRight) {
      this.roundBottomRight = roundBottomRight;
      return this;
   }

   public RoundedRectangleCommand setRoundBottomLeft(boolean roundBottomLeft) {
      this.roundBottomLeft = roundBottomLeft;
      return this;
   }

   public RoundedRectangleCommand setPixelAligned(boolean pixelAligned) {
      this.pixelAligned = pixelAligned;
      return this;
   }
}
