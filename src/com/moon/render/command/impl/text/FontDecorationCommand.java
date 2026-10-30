package com.moon.render.command.impl.text;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class FontDecorationCommand implements RenderCommand {
   private final float left;
   private final float top;
   private final float right;
   private final float bottom;
   private final int color;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float normLeft = this.left / screenWidth;
      float normRight = this.right / screenWidth;
      float normTop = this.top / screenHeight;
      float normBottom = this.bottom / screenHeight;
      buffer.appendPosition(normRight, normTop).appendVector2(-1.0F, -1.0F).appendFloat(1.0F).appendFloat(1.0F).appendColor(this.color);
      buffer.appendPosition(normLeft, normTop).appendVector2(-1.0F, -1.0F).appendFloat(1.0F).appendFloat(1.0F).appendColor(this.color);
      buffer.appendPosition(normLeft, normBottom).appendVector2(-1.0F, -1.0F).appendFloat(1.0F).appendFloat(1.0F).appendColor(this.color);
      buffer.appendPosition(normRight, normBottom).appendVector2(-1.0F, -1.0F).appendFloat(1.0F).appendFloat(1.0F).appendColor(this.color);
      return 4;
   }

   @Override
   public int getVertexStride() {
      return 160;
   }

   private FontDecorationCommand(float left, float top, float right, float bottom, int color) {
      this.left = left;
      this.top = top;
      this.right = right;
      this.bottom = bottom;
      this.color = color;
   }

   public static FontDecorationCommand of(float left, float top, float right, float bottom, int color) {
      return new FontDecorationCommand(left, top, right, bottom, color);
   }
}
