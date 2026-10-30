package net.minecraft.client.renderer.vertex;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.optifine.render.VboRange;
import net.optifine.render.VboRegion;
import org.lwjgl.opengl.GL11;

public class VertexBuffer {
   private int glBufferId;
   private final VertexFormat vertexFormat;
   private int count;
   private VboRegion vboRegion;
   private VboRange vboRange;
   private int drawMode;

   public VertexBuffer(VertexFormat vertexFormat) {
      this.vertexFormat = vertexFormat;
      this.glBufferId = OpenGlHelper.glGenBuffers();
   }

   public void bindBuffer() {
      OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
   }

   public void bufferData(ByteBuffer byteBuffer) {
      if (this.vboRegion != null) {
         this.vboRegion.bufferData(byteBuffer, this.vboRange);
      } else {
         this.bindBuffer();
         OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, byteBuffer, 35044);
         this.unbindBuffer();
         this.count = byteBuffer.limit() / this.vertexFormat.getNextOffset();
      }
   }

   public void drawArrays(int integer) {
      if (this.drawMode > 0) {
         integer = this.drawMode;
      }

      if (this.vboRegion != null) {
         this.vboRegion.drawArrays(integer, this.vboRange);
      } else {
         GL11.glDrawArrays(integer, 0, this.count);
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

   public void setVboRegion(VboRegion vboRegion) {
      if (vboRegion != null) {
         this.deleteGlBuffers();
         this.vboRegion = vboRegion;
         this.vboRange = new VboRange();
      }
   }

   public VboRegion getVboRegion() {
      return this.vboRegion;
   }

   public VboRange getVboRange() {
      return this.vboRange;
   }

   public int getDrawMode() {
      return this.drawMode;
   }

   public void setDrawMode(int integer) {
      this.drawMode = integer;
   }
}
