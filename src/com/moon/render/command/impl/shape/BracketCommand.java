package com.moon.render.command.impl.shape;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class BracketCommand implements RenderCommand {
   private final float left;
   private final float top;
   private final float right;
   private final float bottom;
   private final int outerColor;
   private final int innerColor;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float normLeft = this.left / screenWidth;
      float normRight = this.right / screenWidth;
      float normTop = this.top / screenHeight;
      float normBottom = this.bottom / screenHeight;
      buffer.appendPosition(normLeft, normTop).appendColor(this.outerColor);
      buffer.appendPosition(normLeft, normBottom).appendColor(this.outerColor);
      buffer.appendPosition(normLeft + 1.5F / screenWidth, normBottom).appendColor(this.outerColor);
      buffer.appendPosition(normLeft + 1.5F / screenWidth, normTop).appendColor(this.outerColor);
      buffer.appendPosition(normRight - 1.5F / screenWidth, normTop).appendColor(this.outerColor);
      buffer.appendPosition(normRight - 1.5F / screenWidth, normBottom).appendColor(this.outerColor);
      buffer.appendPosition(normRight, normBottom).appendColor(this.outerColor);
      buffer.appendPosition(normRight, normTop).appendColor(this.outerColor);
      buffer.appendPosition(normLeft + 1.5F / screenWidth, normTop).appendColor(this.outerColor);
      buffer.appendPosition(normLeft + 1.5F / screenWidth, normTop + 1.5F / screenHeight).appendColor(this.outerColor);
      buffer.appendPosition(normRight - 1.5F / screenWidth, normTop + 1.5F / screenHeight).appendColor(this.outerColor);
      buffer.appendPosition(normRight - 1.5F / screenWidth, normTop).appendColor(this.outerColor);
      buffer.appendPosition(normLeft + 1.5F / screenWidth, normBottom - 1.5F / screenHeight).appendColor(this.outerColor);
      buffer.appendPosition(normLeft + 1.5F / screenWidth, normBottom).appendColor(this.outerColor);
      buffer.appendPosition(normRight - 1.5F / screenWidth, normBottom).appendColor(this.outerColor);
      buffer.appendPosition(normRight - 1.5F / screenWidth, normBottom - 1.5F / screenHeight).appendColor(this.outerColor);
      buffer.appendPosition(normLeft + 0.5F / screenWidth, normTop + 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normLeft + 0.5F / screenWidth, normBottom - 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normLeft + 1.0F / screenWidth, normBottom - 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normLeft + 1.0F / screenWidth, normTop + 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normRight - 1.0F / screenWidth, normTop + 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normRight - 1.0F / screenWidth, normBottom - 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normRight - 0.5F / screenWidth, normBottom - 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normRight - 0.5F / screenWidth, normTop + 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normLeft + 0.5F / screenWidth, normTop + 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normLeft + 0.5F / screenWidth, normTop + 1.0F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normRight - 0.5F / screenWidth, normTop + 1.0F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normRight - 0.5F / screenWidth, normTop + 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normLeft + 0.5F / screenWidth, normBottom - 1.0F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normLeft + 0.5F / screenWidth, normBottom - 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normRight - 0.5F / screenWidth, normBottom - 0.5F / screenHeight).appendColor(this.innerColor);
      buffer.appendPosition(normRight - 0.5F / screenWidth, normBottom - 1.0F / screenHeight).appendColor(this.innerColor);
      return 32;
   }

   @Override
   public int getVertexStride() {
      return 768;
   }

   private BracketCommand(float left, float top, float right, float bottom, int outerColor, int innerColor) {
      this.left = left;
      this.top = top;
      this.right = right;
      this.bottom = bottom;
      this.outerColor = outerColor;
      this.innerColor = innerColor;
   }

   public static BracketCommand of(float left, float top, float right, float bottom, int outerColor, int innerColor) {
      return new BracketCommand(left, top, right, bottom, outerColor, innerColor);
   }
}
