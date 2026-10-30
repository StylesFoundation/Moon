package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.src.Config;
import net.minecraft.util.EnumWorldBlockLayer;
import net.optifine.render.VboRegion;
import net.optifine.shaders.ShadersRender;
import org.lwjgl.opengl.GL11;

public class VboRenderList extends ChunkRenderContainer {
   private double viewEntityX;
   private double viewEntityY;
   private double viewEntityZ;

   @Override
   public void renderChunkLayer(EnumWorldBlockLayer enumWorldBlockLayer) {
      if (this.initialized) {
         if (!Config.isRenderRegions()) {
            for (RenderChunk var12 : this.renderChunks) {
               VertexBuffer var13 = var12.getVertexBufferByLayer(enumWorldBlockLayer.ordinal());
               GlStateManager.pushMatrix();
               this.preRenderChunk(var12);
               var12.multModelviewMatrix();
               var13.bindBuffer();
               this.setupArrayPointers();
               var13.drawArrays(7);
               GlStateManager.popMatrix();
            }
         } else {
            int var4 = Integer.MIN_VALUE;
            int var5 = Integer.MIN_VALUE;
            VboRegion var6 = null;

            for (RenderChunk var8 : this.renderChunks) {
               VertexBuffer var9 = var8.getVertexBufferByLayer(enumWorldBlockLayer.ordinal());
               VboRegion var10 = var9.getVboRegion();
               if (var10 != var6 || var4 != var8.regionX || var5 != var8.regionZ) {
                  if (var6 != null) {
                     this.drawRegion(var4, var5, var6);
                  }

                  var4 = var8.regionX;
                  var5 = var8.regionZ;
                  var6 = var10;
               }

               var9.drawArrays(7);
            }

            if (var6 != null) {
               this.drawRegion(var4, var5, var6);
            }
         }

         OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
         GlStateManager.resetColor();
         this.renderChunks.clear();
      }
   }

   public void setupArrayPointers() {
      if (Config.isShaders()) {
         ShadersRender.setupArrayPointersVbo();
      } else {
         GL11.glVertexPointer(3, GL11.GL_FLOAT, 28, 0L);
         GL11.glColorPointer(4, 5121, 28, 12L);
         GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 28, 16L);
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
         GL11.glTexCoordPointer(2, GL11.GL_SHORT, 28, 24L);
         OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
      }
   }

   @Override
   public void initialize(double double1, double double2, double double3) {
      this.viewEntityX = double1;
      this.viewEntityY = double2;
      this.viewEntityZ = double3;
      super.initialize(double1, double2, double3);
   }

   private void drawRegion(int integer1, int integer2, VboRegion vboRegion) {
      GlStateManager.pushMatrix();
      this.preRenderRegion(integer1, 0, integer2);
      vboRegion.finishDraw(this);
      GlStateManager.popMatrix();
   }

   public void preRenderRegion(int integer1, int integer2, int integer3) {
      GlStateManager.translate((float)(integer1 - this.viewEntityX), (float)(integer2 - this.viewEntityY), (float)(integer3 - this.viewEntityZ));
   }
}
