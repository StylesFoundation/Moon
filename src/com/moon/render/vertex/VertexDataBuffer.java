package com.moon.render.vertex;

import com.moon.render.RenderTransformState;
import org.lwjgl.system.MemoryUtil;

public class VertexDataBuffer {
   public long memoryAddress;
   public int position;
   public int capacity;

   public VertexDataBuffer(int capacity) {
      this.memoryAddress = MemoryUtil.nmemAlloc(capacity);
      this.capacity = capacity;
   }

   private void grow() {
      int newCapacity = (int)(this.capacity * 1.5F);
      long newAddress = MemoryUtil.nmemRealloc(this.memoryAddress, newCapacity);
      this.capacity = newCapacity;
      this.memoryAddress = newAddress;
   }

   public void ensureCapacity(int additionalCapacity) {
      int newCapacity = this.capacity + additionalCapacity;
      long newAddress = MemoryUtil.nmemRealloc(this.memoryAddress, newCapacity);
      this.capacity = newCapacity;
      this.memoryAddress = newAddress;
   }

   public void free() {
      MemoryUtil.nmemFree(this.memoryAddress);
   }

   public VertexDataBuffer appendFloat(float value) {
      while (this.capacity - this.position < 4) {
         this.grow();
      }

      return this.putFloat(value);
   }

   public VertexDataBuffer appendPosition(float x, float y) {
      this.appendFloat(x).appendFloat(y);
      return this;
   }

   public VertexDataBuffer appendVector3(float x, float y, float z) {
      this.appendFloat(x).appendFloat(y).appendFloat(z);
      return this;
   }

   public VertexDataBuffer appendVector3(double x, double y, double z) {
      this.appendFloat((float)x).appendFloat((float)y).appendFloat((float)z);
      return this;
   }

   @Deprecated(forRemoval = true)
   public VertexDataBuffer appendNormalizedPosition(float x, float y, float width, float height) {
      this.appendFloat(x / width).appendFloat(y / height);
      return this;
   }

   public VertexDataBuffer appendVector2(float x, float y) {
      this.appendFloat(x).appendFloat(y);
      return this;
   }

   public VertexDataBuffer appendColor(int color) {
      float alphaMultiplier = RenderTransformState.getAlphaMultiplier();
      float red = (color >> 16 & 0xFF) / 255.0F;
      float green = (color >> 8 & 0xFF) / 255.0F;
      float blue = (color & 0xFF) / 255.0F;
      float alpha = (color >> 24 & 0xFF) / 255.0F * alphaMultiplier;
      this.appendFloat(red).appendFloat(green).appendFloat(blue).appendFloat(alpha);
      return this;
   }

   public VertexDataBuffer appendVector4(float x, float y, float z, float w) {
      this.appendFloat(x).appendFloat(y).appendFloat(z).appendFloat(w);
      return this;
   }

   private VertexDataBuffer putFloat(float value) {
      MemoryUtil.memPutFloat(this.memoryAddress + this.position, value);
      this.position += 4;
      return this;
   }

   public long getMemoryAddress() {
      return this.memoryAddress;
   }

   public int getPosition() {
      return this.position;
   }

   public int getCapacity() {
      return this.capacity;
   }

   public VertexDataBuffer setMemoryAddress(long memoryAddress) {
      this.memoryAddress = memoryAddress;
      return this;
   }

   public VertexDataBuffer setPosition(int position) {
      this.position = position;
      return this;
   }

   public VertexDataBuffer setCapacity(int capacity) {
      this.capacity = capacity;
      return this;
   }
}
