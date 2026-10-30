package com.moon.render.command.impl.text;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;

public class MsdfGlyphCommand implements RenderCommand {
   private final float left;
   private final float top;
   private final float right;
   private final float bottom;
   private final float u0;
   private final float v0;
   private final float u1;
   private final float v1;
   private final float weight;
   private final float outlineOffset;
   private final float slant;
   private final int color;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      float minX = this.left / screenWidth;
      float maxX = this.right / screenWidth;
      float minY = this.top / screenHeight;
      float maxY = this.bottom / screenHeight;
      float slantOffset = this.slant / screenWidth;
      buffer.appendPosition(maxX + slantOffset, minY)
         .appendVector2(this.u1, this.v0)
         .appendFloat(this.weight)
         .appendFloat(this.outlineOffset)
         .appendColor(this.color);
      buffer.appendPosition(minX + slantOffset, minY)
         .appendVector2(this.u0, this.v0)
         .appendFloat(this.weight)
         .appendFloat(this.outlineOffset)
         .appendColor(this.color);
      buffer.appendPosition(minX - slantOffset, maxY)
         .appendVector2(this.u0, this.v1)
         .appendFloat(this.weight)
         .appendFloat(this.outlineOffset)
         .appendColor(this.color);
      buffer.appendPosition(maxX - slantOffset, maxY)
         .appendVector2(this.u1, this.v1)
         .appendFloat(this.weight)
         .appendFloat(this.outlineOffset)
         .appendColor(this.color);
      return 4;
   }

   @Override
   public int getVertexStride() {
      return 160;
   }

   private MsdfGlyphCommand(
      float left, float top, float right, float bottom, float u0, float v0, float u1, float v1, float weight, float outlineOffset, float slant, int color
   ) {
      this.left = left;
      this.top = top;
      this.right = right;
      this.bottom = bottom;
      this.u0 = u0;
      this.v0 = v0;
      this.u1 = u1;
      this.v1 = v1;
      this.weight = weight;
      this.outlineOffset = outlineOffset;
      this.slant = slant;
      this.color = color;
   }

   public static MsdfGlyphCommand of(
      float left, float top, float right, float bottom, float u0, float v0, float u1, float v1, float weight, float outlineOffset, float slant, int color
   ) {
      return new MsdfGlyphCommand(left, top, right, bottom, u0, v0, u1, v1, weight, outlineOffset, slant, color);
   }
}
