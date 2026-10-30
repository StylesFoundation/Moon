package com.moon.render.gl;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL30;

public class CustomFramebuffer {
   public int width;
   public int height;
   public int framebufferWidth;
   public int framebufferHeight;
   public boolean useDepth;
   public int framebufferId;
   public int textureId;
   public int depthBufferId;
   public int textureFilter;

   public CustomFramebuffer(int width, int height, boolean useDepth) {
      this.useDepth = useDepth;
      this.framebufferId = -1;
      this.textureId = -1;
      this.depthBufferId = -1;
      this.initFramebuffer(width, height);
   }

   public void initFramebuffer(int width, int height) {
      if (!OpenGlHelper.isFramebufferEnabled()) {
         this.framebufferWidth = width;
         this.framebufferHeight = height;
      } else {
         GlStateManager.enableDepth();
         if (this.framebufferId >= 0) {
            this.delete();
         }

         this.create(width, height);
         this.checkStatus();
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
      }
   }

   public void delete() {
      if (OpenGlHelper.isFramebufferEnabled()) {
         GlStateManager.bindTexture(0);
         this.unbindFramebuffer();
         if (this.depthBufferId > -1) {
            OpenGlHelper.glDeleteRenderbuffers(this.depthBufferId);
            this.depthBufferId = -1;
         }

         if (this.textureId > -1) {
            TextureUtil.deleteTexture(this.textureId);
            this.textureId = -1;
         }

         if (this.framebufferId > -1) {
            OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
            OpenGlHelper.glDeleteFramebuffers(this.framebufferId);
            this.framebufferId = -1;
         }
      }
   }

   public void create(int width, int height) {
      this.framebufferWidth = width;
      this.framebufferHeight = height;
      this.width = width;
      this.height = height;
      if (!OpenGlHelper.isFramebufferEnabled()) {
         this.clear();
      } else {
         this.framebufferId = OpenGlHelper.glGenFramebuffers();
         this.textureId = TextureUtil.glGenTextures();
         if (this.useDepth) {
            this.depthBufferId = OpenGlHelper.glGenRenderbuffers();
         }

         this.setTextureFilter(9728);
         GlStateManager.bindTexture(this.textureId);
         GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA16, this.width, this.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer)null);
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferId);
         GlStateManager.bindTexture(this.textureId);
         GL32.glFramebufferTexture(OpenGlHelper.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, this.textureId, 0);
         if (this.useDepth) {
            OpenGlHelper.glBindRenderbuffer(OpenGlHelper.GL_RENDERBUFFER, this.depthBufferId);
            OpenGlHelper.glRenderbufferStorage(OpenGlHelper.GL_RENDERBUFFER, 33190, this.width, this.height);
            OpenGlHelper.glFramebufferRenderbuffer(
               OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_DEPTH_ATTACHMENT, OpenGlHelper.GL_RENDERBUFFER, this.depthBufferId
            );
         }

         this.clear();
         GlStateManager.bindTexture(0);
         this.checkStatus();
      }
   }

   public void setTextureFilter(int filter) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         this.textureFilter = filter;
         GlStateManager.bindTexture(this.textureId);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, filter);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, filter);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
         GlStateManager.bindTexture(0);
      }
   }

   public void setTextureWrap(int wrap) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         GlStateManager.bindTexture(this.textureId);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrap);
         GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrap);
         GlStateManager.bindTexture(0);
      }
   }

   public void checkStatus() {
      int status = OpenGlHelper.glCheckFramebufferStatus(OpenGlHelper.GL_FRAMEBUFFER);

      try {
         if (status != OpenGlHelper.GL_FRAMEBUFFER_COMPLETE) {
            if (status == OpenGlHelper.GL_FB_INCOMPLETE_ATTACHMENT) {
               throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
            }

            if (status == OpenGlHelper.GL_FB_INCOMPLETE_MISS_ATTACH) {
               throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
            }

            if (status == OpenGlHelper.GL_FB_INCOMPLETE_DRAW_BUFFER) {
               throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
            }

            if (status == OpenGlHelper.GL_FB_INCOMPLETE_READ_BUFFER) {
               throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
            }

            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + status);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public void bindTexture() {
      if (OpenGlHelper.isFramebufferEnabled()) {
         GlStateManager.bindTexture(this.textureId);
      }
   }

   public void bindFramebuffer(boolean updateViewport) {
      if (OpenGlHelper.isFramebufferEnabled()) {
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, this.framebufferId);
         if (updateViewport) {
            GlStateManager.viewport(0, 0, this.framebufferWidth, this.framebufferHeight);
         }
      }
   }

   public void unbindFramebuffer() {
      if (OpenGlHelper.isFramebufferEnabled()) {
         OpenGlHelper.glBindFramebuffer(OpenGlHelper.GL_FRAMEBUFFER, 0);
      }
   }

   public void clear() {
      GlStateManager.clearColor(0.0F, 0.0F, 0.0F, 0.0F);
      short mask = 16384;
      if (this.useDepth) {
         GlStateManager.clearDepth(1.0);
         mask = 16640;
      }

      GlStateManager.clear(mask);
   }

   public boolean resize(int width, int height) {
      if (width <= 1 || height <= 1) {
         return false;
      }

      if (width == this.framebufferWidth && height == this.framebufferHeight) {
         return false;
      }

      this.delete();
      this.create(width, height);
      return true;
   }
}
