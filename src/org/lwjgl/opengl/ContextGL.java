package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.PointerBuffer;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFW;

final class ContextGL implements Context {
   private static final ThreadLocal<ContextGL> current_context_local = new ThreadLocal<>();
   private final ByteBuffer handle;
   private final PeerInfo peer_info;
   private final ContextAttribs contextAttribs;
   private final boolean forwardCompatible;
   private boolean destroyed;
   private boolean destroy_requested;
   private Thread thread;
   private boolean isCurrent;

   PeerInfo getPeerInfo() {
      return this.peer_info;
   }

   ContextAttribs getContextAttribs() {
      return this.contextAttribs;
   }

   static ContextGL getCurrentContext() {
      return current_context_local.get();
   }

   ContextGL(PeerInfo peer_info, ContextAttribs attribs, ContextGL shared_context) throws LWJGLException {
      synchronized (shared_context != null ? shared_context : this) {
         if (shared_context != null && shared_context.destroyed) {
            throw new IllegalArgumentException("Shared context is destroyed");
         }

         this.peer_info = peer_info;
         this.contextAttribs = attribs;
         this.forwardCompatible = false;
         this.handle = null;
      }
   }

   @Override
   public void releaseCurrent() throws LWJGLException {
      ContextGL current_context = getCurrentContext();
      if (current_context != null) {
         GLFW.glfwMakeContextCurrent(0L);
         this.isCurrent = false;
         current_context_local.set(null);
         synchronized (current_context) {
            current_context.thread = null;
            current_context.checkDestroy();
         }
      }
   }

   @Override
   public synchronized void releaseDrawable() throws LWJGLException {
      if (this.destroyed) {
         throw new IllegalStateException("Context is destroyed");
      }
   }

   public synchronized void update() {
      if (this.destroyed) {
         throw new IllegalStateException("Context is destroyed");
      }
   }

   public static void swapBuffers() throws LWJGLException {
      Display.swapBuffers();
   }

   private boolean canAccess() {
      return this.thread == null || Thread.currentThread() == this.thread;
   }

   private void checkAccess() {
      if (!this.canAccess()) {
         throw new IllegalStateException("From thread " + Thread.currentThread() + ": " + this.thread + " already has the context current");
      }
   }

   @Override
   public synchronized void makeCurrent() throws LWJGLException {
      this.checkAccess();
      if (this.destroyed) {
         throw new IllegalStateException("Context is destroyed");
      }

      this.thread = Thread.currentThread();
      current_context_local.set(this);
      GLFW.glfwMakeContextCurrent(Display.Window.handle);
      this.isCurrent = true;
   }

   ByteBuffer getHandle() {
      return this.handle;
   }

   @Override
   public synchronized boolean isCurrent() throws LWJGLException {
      if (this.destroyed) {
         throw new IllegalStateException("Context is destroyed");
      } else {
         return this.isCurrent;
      }
   }

   private void checkDestroy() {
      if (!this.destroyed && this.destroy_requested) {
         try {
            this.releaseDrawable();
            this.destroyed = true;
            this.thread = null;
            Display.destroy();
         } catch (LWJGLException e) {
            LWJGLUtil.log("Exception occurred while destroying context: " + e);
         }
      }
   }

   public static void setSwapInterval(int value) {
      GLFW.glfwSwapInterval(value);
   }

   public synchronized void forceDestroy() throws LWJGLException {
      this.checkAccess();
      this.destroy();
   }

   public synchronized void destroy() throws LWJGLException {
      if (!this.destroyed) {
         this.destroy_requested = true;
         boolean was_current = this.isCurrent();
         int error = 0;
         if (was_current) {
            if (GLContext.getCapabilities() != null && GLContext.getCapabilities().OpenGL11) {
               error = GL11.glGetError();
            }

            this.releaseCurrent();
         }

         this.checkDestroy();
         if (was_current && error != 0) {
            throw new OpenGLException(error);
         }
      }
   }

   public synchronized void setCLSharingProperties(PointerBuffer properties) throws LWJGLException {
      ByteBuffer peer_handle = this.peer_info.lockAndGetHandle();
   }

   static {
      Sys.initialize();
   }
}
