package com.moon.render.command.impl.world;

import com.moon.render.command.RenderCommand;
import com.moon.render.vertex.VertexDataBuffer;
import net.minecraft.util.AxisAlignedBB;

public class WorldBoxCommand implements RenderCommand {
   private final AxisAlignedBB boundingBox;
   private final int color;

   @Override
   public int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight) {
      double minX = this.boundingBox.minX;
      double maxX = this.boundingBox.maxX;
      double minY = this.boundingBox.minY;
      double maxY = this.boundingBox.maxY;
      double minZ = this.boundingBox.minZ;
      double maxZ = this.boundingBox.maxZ;
      buffer.appendVector3(minX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, maxZ).appendColor(this.color);
      buffer.appendVector3(minX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(minX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, minZ).appendColor(this.color);
      buffer.appendVector3(maxX, maxY, maxZ).appendColor(this.color);
      buffer.appendVector3(maxX, minY, maxZ).appendColor(this.color);
      return 48;
   }

   @Override
   public int getVertexStride() {
      return 1344;
   }

   public AxisAlignedBB getBoundingBox() {
      return this.boundingBox;
   }

   public int getColor() {
      return this.color;
   }

   private WorldBoxCommand(AxisAlignedBB boundingBox, int color) {
      this.boundingBox = boundingBox;
      this.color = color;
   }

   public static WorldBoxCommand of(AxisAlignedBB boundingBox, int color) {
      return new WorldBoxCommand(boundingBox, color);
   }
}
