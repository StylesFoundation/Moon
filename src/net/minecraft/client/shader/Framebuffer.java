package net.minecraft.client.shader;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class Framebuffer {
   public int framebufferTextureWidth;
   public int framebufferTextureHeight;
   public int framebufferWidth;
   public int framebufferHeight;
   public boolean useDepth;
   public int framebufferObject;
   public int framebufferTexture;
   public int depthBuffer;
   public float[] framebufferColor;
   public int framebufferFilter;

   public Framebuffer(int integer1, int integer2, boolean boolean3) {
      this.useDepth = boolean3;
      this.framebufferObject = -1;
      this.framebufferTexture = -1;
      this.depthBuffer = -1;
      this.framebufferColor = new float[4];
      this.framebufferColor[0] = 1.0F;
      this.framebufferColor[1] = 1.0F;
      this.framebufferColor[2] = 1.0F;
      this.framebufferColor[3] = 0.0F;
      this.createBindFramebuffer(integer1, integer2);
   }

   public void createBindFramebuffer(int integer1, int integer2) {
      if (!OpenGlHelper.isFramebufferEnabled()) {
         this.framebufferWidth = integer1;
         this.framebufferHeight = integer2;
      } else {
         GlStateManager.enableDepth();
         if (this.framebufferObject >= 0) {
            this.deleteFramebuffer();
         }

         this.createFramebuffer(integer1, integer2);
         this.checkFramebufferComplete();
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
      }
   }

   public void deleteFramebuffer() {
      if (OpenGlHelper.isFramebufferEnabled()) {
         this.unbindFramebufferTexture();
         this.unbindFramebuffer();
         if (this.depthBuffer > -1) {
            TextureUtil.deleteTexture(this.depthBuffer);
            this.depthBuffer = -1;
         }

         if (this.framebufferTexture > -1) {
            TextureUtil.deleteTexture(this.framebufferTexture);
            this.framebufferTexture = -1;
         }

         if (this.framebufferObject > -1) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
            OpenGlHelper.glDeleteFramebuffers(this.framebufferObject);
            this.framebufferObject = -1;
         }
      }
   }

   public void createFramebuffer(int integer1, int integer2) {
      if (!OpenGlHelper.isFramebufferEnabled()) {
         this.framebufferWidth = integer1;
         this.framebufferHeight = integer2;
      } else {
         this.framebufferWidth = integer1;
         this.framebufferHeight = integer2;
         this.framebufferTextureWidth = integer1;
         this.framebufferTextureHeight = integer2;
         this.framebufferObject = OpenGlHelper.glGenFramebuffers();
         this.framebufferTexture = TextureUtil.glGenTextures();
         if (this.useDepth) {
            this.depthBuffer = TextureUtil.glGenTextures();
            GlStateManager.bindTexture(this.depthBuffer);
            GlStateManager.glTexParameteri(3553, 10240, 9728);
            GlStateManager.glTexParameteri(3553, 10241, 9728);
            GlStateManager.glTexParameteri(3553, 34892, 0);
            GlStateManager.glTexParameteri(3553, 10242, 33071);
            GlStateManager.glTexParameteri(3553, 10243, 33071);
            GlStateManager.glTexImage2D(3553, 0, 34041, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, 6402, 5126, null);
         }

         this.setFramebufferFilter(9728);
         GlStateManager.bindTexture(this.framebufferTexture);
         GlStateManager.glTexParameteri(3553, 10242, 33071);
         GlStateManager.glTexParameteri(3553, 10243, 33071);
         GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, this.framebufferTextureWidth, this.framebufferTextureHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)null);
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
         OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, this.framebufferTexture, 0);
         if (this.useDepth) {
            OpenGlHelper.glFramebufferTexture2D(36160, 33306, 3553, this.depthBuffer, 0);
         }

         this.unbindFramebufferTexture();
      }
   }

   public void setFramebufferFilter(int integer) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         this.framebufferFilter = integer;
         GlStateManager.bindTexture(this.framebufferTexture);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, integer);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, integer);
         GlStateManager.bindTexture(0);
      }
   }

   public void Method369(int integer) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         GlStateManager.bindTexture(this.framebufferTexture);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, integer);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, integer);
         GlStateManager.bindTexture(0);
      }
   }

   public void checkFramebufferComplete() {
      int var1 = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);

      try {
         if (var1 != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
            if (var1 == OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT) {
               throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
            }

            if (var1 == OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH) {
               throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
            }

            if (var1 == OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER) {
               throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
            }

            if (var1 == OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER) {
               throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
            }

            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + var1);
         }
      } catch (Exception var2) {
      }
   }

   public void bindFramebufferTexture() {
      if (OpenGlHelper.isFramebufferEnabled()) {
         GlStateManager.bindTexture(this.framebufferTexture);
      }
   }

   public void unbindFramebufferTexture() {
      if (OpenGlHelper.isFramebufferEnabled()) {
         GlStateManager.bindTexture(0);
      }
   }

   public void bindFramebuffer(boolean boolean1) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferObject);
         if (boolean1) {
            GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
         }
      }
   }

   public void unbindFramebuffer() {
      if (OpenGlHelper.isFramebufferEnabled()) {
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
      }
   }

   public void setFramebufferColor(float float1, float float2, float float3, float float4) {
      this.framebufferColor[0] = float1;
      this.framebufferColor[1] = float2;
      this.framebufferColor[2] = float3;
      this.framebufferColor[3] = float4;
   }

   public void framebufferRender(int integer1, int integer2) {
      this.framebufferRenderExt(integer1, integer2, true);
   }

   public void framebufferRenderExt(int integer1, int integer2, boolean boolean3) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         GlStateManager.colorMask(true, true, true, false);
         GlStateManager.disableDepth();
         GlStateManager.depthMask(false);
         GlStateManager.matrixMode(5889);
         GlStateManager.loadIdentity();
         GlStateManager.ortho(0.0, integer1, integer2, 0.0, 1000.0, 3000.0);
         GlStateManager.matrixMode(5888);
         GlStateManager.loadIdentity();
         GlStateManager.translate(0.0F, 0.0F, -2000.0F);
         GlStateManager.viewport(0, 0, integer1, integer2);
         GlStateManager.enableTexture2D();
         GlStateManager.disableLighting();
         GlStateManager.disableAlpha();
         if (boolean3) {
            GlStateManager.disableBlend();
            GlStateManager.enableColorMaterial();
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
         this.bindFramebufferTexture();
         float var6 = integer1;
         float var7 = integer2;
         float var8 = (float)this.framebufferWidth / this.framebufferTextureWidth;
         float var9 = (float)this.framebufferHeight / this.framebufferTextureHeight;
         Tessellator var10 = Tessellator.getInstance();
         WorldRenderer var11 = var10.getWorldRenderer();
         var11.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
         var11.pos(0.0, var7, 0.0).tex(0.0, 0.0).color(255, 255, 255, 255).endVertex();
         var11.pos(var6, var7, 0.0).tex(var8, 0.0).color(255, 255, 255, 255).endVertex();
         var11.pos(var6, 0.0, 0.0).tex(var8, var9).color(255, 255, 255, 255).endVertex();
         var11.pos(0.0, 0.0, 0.0).tex(0.0, var9).color(255, 255, 255, 255).endVertex();
         var10.draw();
         GlStateManager.depthMask(true);
         GlStateManager.colorMask(true, true, true, true);
         this.unbindFramebufferTexture();
      }
   }

   public void framebufferClear() {
      this.bindFramebuffer(true);
      GlStateManager.clearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
      short var3 = 16384;
      if (this.useDepth) {
         GlStateManager.clearDepth(1.0);
         var3 = 16640;
      }

      GlStateManager.clear(var3);
      this.unbindFramebuffer();
   }

   public void Method370() {
      GlStateManager.clearColor(this.framebufferColor[0], this.framebufferColor[1], this.framebufferColor[2], this.framebufferColor[3]);
      short var3 = 16384;
      if (this.useDepth) {
         GlStateManager.clearDepth(1.0);
         var3 = 16640;
      }

      GlStateManager.clear(var3);
   }

   public boolean Method371(int integer1, int integer2) {
      if (integer1 <= 1 || integer2 <= 1) {
         return false;
      }

      if (integer1 == this.framebufferWidth && integer2 == this.framebufferHeight) {
         return false;
      }

      this.deleteFramebuffer();
      this.createFramebuffer(integer1, integer2);
      return true;
   }
}
