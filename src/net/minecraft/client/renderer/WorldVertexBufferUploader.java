package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.util.List;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.src.Config;
import net.optifine.shaders.SVertexBuilder;
import org.lwjgl.opengl.GL11;

public class WorldVertexBufferUploader {
   public void draw(WorldRenderer worldRenderer) {
      if (worldRenderer.getVertexCount() > 0) {
         if (worldRenderer.getDrawMode() == 7 && Config.isQuadsToTriangles()) {
            worldRenderer.quadsToTriangles();
         }

         VertexFormat var4 = worldRenderer.getVertexFormat();
         int var5 = var4.getNextOffset();
         ByteBuffer var6 = worldRenderer.getByteBuffer();
         List var7 = var4.getElements();

         for (int var8 = 0; var8 < var7.size(); var8++) {
            VertexFormatElement var9 = (VertexFormatElement)var7.get(var8);
            VertexFormatElement.EnumUsage var10 = var9.getUsage();
            int var11 = var9.getType().getGlConstant();
            int var12 = var9.getIndex();
            var6.position(var4.getOffset(var8));
            switch (var10) {
               case POSITION:
                  GL11.glVertexPointer(var9.getElementCount(), var11, var5, var6);
                  GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
                  break;
               case UV:
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var12);
                  GL11.glTexCoordPointer(var9.getElementCount(), var11, var5, var6);
                  GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                  break;
               case COLOR:
                  GL11.glColorPointer(var9.getElementCount(), var11, var5, var6);
                  GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
                  break;
               case NORMAL:
                  GL11.glNormalPointer(var11, var5, var6);
                  GL11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
            }
         }

         if (worldRenderer.isMultiTexture()) {
            worldRenderer.drawMultiTexture();
         } else if (Config.isShaders()) {
            SVertexBuilder.drawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.getVertexCount(), worldRenderer);
         } else {
            GL11.glDrawArrays(worldRenderer.getDrawMode(), 0, worldRenderer.getVertexCount());
         }

         int var13 = 0;

         for (int var14 = var7.size(); var13 < var14; var13++) {
            VertexFormatElement var15 = (VertexFormatElement)var7.get(var13);
            VertexFormatElement.EnumUsage var16 = var15.getUsage();
            int var17 = var15.getIndex();
            switch (var16) {
               case POSITION:
                  GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
                  break;
               case UV:
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + var17);
                  GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
                  OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                  break;
               case COLOR:
                  GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
                  GlStateManager.resetColor();
                  break;
               case NORMAL:
                  GL11.glDisableClientState(GL11.GL_NORMAL_ARRAY);
            }
         }
      }

      worldRenderer.reset();
   }
}
