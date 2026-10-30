package com.moon.render.gl;

import com.moon.render.vertex.VertexDataBuffer;
import org.lwjgl.opengl.GL15;

public class GpuBuffer {
   private final int id = GL15.glGenBuffers();

   public void bind(int target) {
      GL15.glBindBuffer(target, this.id);
   }

   public void allocate(int target, long size, int usage) {
      GL15.glBufferData(target, size, usage);
   }

   public void upload(int target, long offset, VertexDataBuffer buffer) {
      GL15.nglBufferSubData(target, offset, buffer.capacity, buffer.memoryAddress);
   }

   public void delete() {
      GL15.glDeleteBuffers(this.id);
   }

   public int getId() {
      return this.id;
   }
}
