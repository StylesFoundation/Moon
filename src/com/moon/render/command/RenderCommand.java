package com.moon.render.command;

import com.moon.render.vertex.VertexDataBuffer;

public interface RenderCommand {
   int appendVertices(VertexDataBuffer buffer, float screenWidth, float screenHeight);

   int getVertexStride();
}
