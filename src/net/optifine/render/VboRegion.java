package net.optifine.render;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.VboRenderList;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.src.Config;
import net.minecraft.util.EnumWorldBlockLayer;
import net.optifine.util.LinkedList;

public class VboRegion {
   private EnumWorldBlockLayer layer = null;
   private int glBufferId = OpenGlHelper.glGenBuffers();
   private int capacity = 4096;
   private int positionTop = 0;
   private int sizeUsed;
   private final LinkedList<VboRange> rangeList = new LinkedList<>();
   private VboRange compactRangeLast = null;
   private IntBuffer bufferIndexVertex = Config.createDirectIntBuffer(this.capacity);
   private IntBuffer bufferCountVertex = Config.createDirectIntBuffer(this.capacity);
   private int drawMode = 7;
   private final int vertexBytes = DefaultVertexFormats.BLOCK.getNextOffset();

   public VboRegion(EnumWorldBlockLayer enumWorldBlockLayer) {
      this.layer = enumWorldBlockLayer;
      this.bindBuffer();
      long var4 = this.toBytes(this.capacity);
      OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, var4, OpenGlHelper.GL_STATIC_DRAW);
      this.unbindBuffer();
   }

   public void bufferData(ByteBuffer byteBuffer, VboRange vboRange) {
      int var6 = vboRange.getPosition();
      int var7 = vboRange.getSize();
      int var8 = this.toVertex(byteBuffer.limit());
      if (var8 <= 0) {
         if (var6 >= 0) {
            vboRange.setPosition(-1);
            vboRange.setSize(0);
            this.rangeList.remove(vboRange.getNode());
            this.sizeUsed -= var7;
         }
      } else {
         if (var8 > var7) {
            vboRange.setPosition(this.positionTop);
            vboRange.setSize(var8);
            this.positionTop += var8;
            if (var6 >= 0) {
               this.rangeList.remove(vboRange.getNode());
            }

            this.rangeList.addLast(vboRange.getNode());
         }

         vboRange.setSize(var8);
         this.sizeUsed += var8 - var7;
         this.checkVboSize(vboRange.getPositionNext());
         long var9 = this.toBytes(vboRange.getPosition());
         this.bindBuffer();
         OpenGlHelper.glBufferSubData(OpenGlHelper.GL_ARRAY_BUFFER, var9, byteBuffer);
         this.unbindBuffer();
         if (this.positionTop > this.sizeUsed * 11 / 10) {
            this.compactRanges(1);
         }
      }
   }

   private void compactRanges(int integer) {
      if (!this.rangeList.isEmpty()) {
         VboRange var5 = this.compactRangeLast;
         if (var5 == null || !this.rangeList.Method394(var5.getNode())) {
            var5 = this.rangeList.getFirst().getItem();
         }

         var5.getPosition();
         VboRange var7 = var5.getPrev();
         int var6;
         if (var7 == null) {
            var6 = 0;
         } else {
            var6 = var7.getPositionNext();
         }

         int var8 = 0;

         while (var5 != null && var8 < integer) {
            var8++;
            if (var5.getPosition() == var6) {
               var6 += var5.getSize();
               var5 = var5.getNext();
            } else {
               int var9 = var5.getPosition() - var6;
               if (var5.getSize() <= var9) {
                  this.copyVboData(var5.getPosition(), var6, var5.getSize());
                  var5.setPosition(var6);
                  var6 += var5.getSize();
                  var5 = var5.getNext();
               } else {
                  this.checkVboSize(this.positionTop + var5.getSize());
                  this.copyVboData(var5.getPosition(), this.positionTop, var5.getSize());
                  var5.setPosition(this.positionTop);
                  this.positionTop = this.positionTop + var5.getSize();
                  VboRange var10 = var5.getNext();
                  this.rangeList.remove(var5.getNode());
                  this.rangeList.addLast(var5.getNode());
                  var5 = var10;
               }
            }
         }

         if (var5 == null) {
            this.positionTop = this.rangeList.getLast().getItem().getPositionNext();
         }

         this.compactRangeLast = var5;
      }
   }

   private void checkRanges() {
      int var4 = 0;
      int var5 = 0;

      for (VboRange var6 = this.rangeList.getFirst().getItem(); var6 != null; var6 = var6.getNext()) {
         var4++;
         var5 += var6.getSize();
         if (var6.getPosition() < 0 || var6.getSize() <= 0 || var6.getPositionNext() > this.positionTop) {
            throw new RuntimeException("Invalid range: " + var6);
         }

         VboRange var7 = var6.getPrev();
         if (var7 != null && var6.getPosition() < var7.getPositionNext()) {
            throw new RuntimeException("Invalid range: " + var6);
         }

         VboRange var8 = var6.getNext();
         if (var8 != null && var6.getPositionNext() > var8.getPosition()) {
            throw new RuntimeException("Invalid range: " + var6);
         }
      }

      if (var4 != this.rangeList.getSize()) {
         throw new RuntimeException("Invalid count: " + var4 + " <> " + this.rangeList.getSize());
      }

      if (var5 != this.sizeUsed) {
         throw new RuntimeException("Invalid size: " + var5 + " <> " + this.sizeUsed);
      }
   }

   private void checkVboSize(int integer) {
      if (this.capacity < integer) {
         this.expandVbo(integer);
      }
   }

   private void copyVboData(int integer1, int integer2, int integer3) {
      long var6 = this.toBytes(integer1);
      long var8 = this.toBytes(integer2);
      long var10 = this.toBytes(integer3);
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, this.glBufferId);
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, this.glBufferId);
      OpenGlHelper.glCopyBufferSubData(OpenGlHelper.GL_COPY_READ_BUFFER, OpenGlHelper.GL_COPY_WRITE_BUFFER, var6, var8, var10);
      Config.checkGlError("Copy VBO range");
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, 0);
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, 0);
   }

   private void expandVbo(int integer) {
      int var5 = this.capacity * 6 / 4;

      while (var5 < integer) {
         var5 = var5 * 6 / 4;
      }

      long var6 = this.toBytes(this.capacity);
      long var8 = this.toBytes(var5);
      int var10 = OpenGlHelper.glGenBuffers();
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, var10);
      OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, var8, OpenGlHelper.GL_STATIC_DRAW);
      Config.checkGlError("Expand VBO");
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, this.glBufferId);
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, var10);
      OpenGlHelper.glCopyBufferSubData(OpenGlHelper.GL_COPY_READ_BUFFER, OpenGlHelper.GL_COPY_WRITE_BUFFER, 0L, 0L, var6);
      Config.checkGlError("Copy VBO: " + var8);
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_READ_BUFFER, 0);
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_COPY_WRITE_BUFFER, 0);
      OpenGlHelper.glDeleteBuffers(this.glBufferId);
      this.bufferIndexVertex = Config.createDirectIntBuffer(var5);
      this.bufferCountVertex = Config.createDirectIntBuffer(var5);
      this.glBufferId = var10;
      this.capacity = var5;
   }

   public void bindBuffer() {
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
   }

   public void drawArrays(int integer, VboRange vboRange) {
      if (this.drawMode != integer) {
         if (this.bufferIndexVertex.position() > 0) {
            throw new IllegalArgumentException("Mixed region draw modes: " + this.drawMode + " != " + integer);
         }

         this.drawMode = integer;
      }

      this.bufferIndexVertex.put(vboRange.getPosition());
      this.bufferCountVertex.put(vboRange.getSize());
   }

   public void finishDraw(VboRenderList vboRenderList) {
      this.bindBuffer();
      vboRenderList.setupArrayPointers();
      this.bufferIndexVertex.flip();
      this.bufferCountVertex.flip();
      GlStateManager.glMultiDrawArrays(this.drawMode, this.bufferIndexVertex, this.bufferCountVertex);
      this.bufferIndexVertex.limit(this.bufferIndexVertex.capacity());
      this.bufferCountVertex.limit(this.bufferCountVertex.capacity());
      if (this.positionTop > this.sizeUsed * 11 / 10) {
         this.compactRanges(1);
      }
   }

   public void unbindBuffer() {
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
   }

   public void deleteGlBuffers() {
      if (this.glBufferId >= 0) {
         OpenGlHelper.glDeleteBuffers(this.glBufferId);
         this.glBufferId = -1;
      }
   }

   private long toBytes(int integer) {
      return (long)integer * this.vertexBytes;
   }

   private int toVertex(long long1) {
      return (int)(long1 / this.vertexBytes);
   }

   public int getPositionTop() {
      return this.positionTop;
   }
}
