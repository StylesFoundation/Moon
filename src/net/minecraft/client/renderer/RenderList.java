package net.minecraft.client.renderer;

import java.nio.IntBuffer;
import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.src.Config;
import net.minecraft.util.EnumWorldBlockLayer;
import org.lwjgl.opengl.GL11;

public class RenderList extends ChunkRenderContainer {
   private double viewEntityX;
   private double viewEntityY;
   private double viewEntityZ;
   IntBuffer bufferLists = GLAllocation.createDirectIntBuffer(16);

   @Override
   public void renderChunkLayer(EnumWorldBlockLayer enumWorldBlockLayer) {
      if (this.initialized) {
         if (!Config.isRenderRegions()) {
            for (RenderChunk var11 : this.renderChunks) {
               ListedRenderChunk var12 = (ListedRenderChunk)var11;
               GlStateManager.pushMatrix();
               this.preRenderChunk(var11);
               GL11.glCallList(var12.getDisplayList(enumWorldBlockLayer, var12.getCompiledChunk()));
               GlStateManager.popMatrix();
            }
         } else {
            int var4 = Integer.MIN_VALUE;
            int var5 = Integer.MIN_VALUE;

            for (RenderChunk var7 : this.renderChunks) {
               ListedRenderChunk var8 = (ListedRenderChunk)var7;
               if (var4 != var7.regionX || var5 != var7.regionZ) {
                  if (this.bufferLists.position() > 0) {
                     this.drawRegion(var4, var5, this.bufferLists);
                  }

                  var4 = var7.regionX;
                  var5 = var7.regionZ;
               }

               if (this.bufferLists.position() >= this.bufferLists.capacity()) {
                  IntBuffer var9 = GLAllocation.createDirectIntBuffer(this.bufferLists.capacity() * 2);
                  this.bufferLists.flip();
                  var9.put(this.bufferLists);
                  this.bufferLists = var9;
               }

               this.bufferLists.put(var8.getDisplayList(enumWorldBlockLayer, var8.getCompiledChunk()));
            }

            if (this.bufferLists.position() > 0) {
               this.drawRegion(var4, var5, this.bufferLists);
            }
         }

         if (Config.isMultiTexture()) {
            GlStateManager.bindCurrentTexture();
         }

         GlStateManager.resetColor();
         this.renderChunks.clear();
      }
   }

   @Override
   public void initialize(double double1, double double2, double double3) {
      this.viewEntityX = double1;
      this.viewEntityY = double2;
      this.viewEntityZ = double3;
      super.initialize(double1, double2, double3);
   }

   private void drawRegion(int integer1, int integer2, IntBuffer intBuffer) {
      GlStateManager.pushMatrix();
      this.preRenderRegion(integer1, 0, integer2);
      intBuffer.flip();
      GlStateManager.callLists(intBuffer);
      intBuffer.clear();
      GlStateManager.popMatrix();
   }

   public void preRenderRegion(int integer1, int integer2, int integer3) {
      GlStateManager.translate((float)(integer1 - this.viewEntityX), (float)(integer2 - this.viewEntityY), (float)(integer3 - this.viewEntityZ));
   }
}
