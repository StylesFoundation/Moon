package org.lwjgl.opengl;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;

public class AWTGLCanvas extends Canvas implements Drawable, ComponentListener, HierarchyListener {
   private static final long serialVersionUID = 1L;
   private ContextGL mContextGL;
   private PixelFormatLWJGL mPixelFormat;
   private ContextAttribs mCtxAttrs;

   public void setPixelFormat(PixelFormatLWJGL pf) throws LWJGLException {
      this.mPixelFormat = pf;
   }

   public void setPixelFormat(PixelFormatLWJGL pf, ContextAttribs attribs) throws LWJGLException {
      this.mPixelFormat = pf;
      this.mCtxAttrs = attribs;
   }

   public PixelFormatLWJGL getPixelFormat() {
      return this.mPixelFormat;
   }

   public ContextGL getContext() {
      return this.mContextGL;
   }

   public ContextGL createSharedContext() throws LWJGLException {
      this.mContextGL = new ContextGL(this.getContext().getPeerInfo(), this.mCtxAttrs, null);
      return this.mContextGL;
   }

   public void checkGLError() {
   }

   public void initContext(float r, float g, float b) {
      Display.setInitialBackground(r, g, b);
   }

   public AWTGLCanvas() throws LWJGLException {
      Display.create();
   }

   public AWTGLCanvas(PixelFormat pixel_format) throws LWJGLException {
      Display.create(pixel_format);
   }

   public AWTGLCanvas(GraphicsDevice device, PixelFormat pixel_format) throws LWJGLException {
      this(pixel_format);
   }

   public AWTGLCanvas(GraphicsDevice device, PixelFormat pixel_format, Drawable drawable) throws LWJGLException {
      this(pixel_format);
   }

   public AWTGLCanvas(GraphicsDevice device, PixelFormat pixel_format, Drawable drawable, ContextAttribs attribs) throws LWJGLException {
      this(pixel_format);
   }

   @Override
   public void addNotify() {
   }

   @Override
   public void removeNotify() {
   }

   public void setSwapInterval(int swap_interval) {
      ContextGL.setSwapInterval(swap_interval);
   }

   public void setVSyncEnabled(boolean enabled) {
      ContextGL.setSwapInterval(enabled ? 1 : 0);
   }

   public void swapBuffers() throws LWJGLException {
      ContextGL.swapBuffers();
   }

   @Override
   public boolean isCurrent() throws LWJGLException {
      return this.mContextGL.isCurrent();
   }

   @Override
   public void makeCurrent() throws LWJGLException {
      this.mContextGL.makeCurrent();
   }

   @Override
   public void releaseContext() throws LWJGLException {
      this.mContextGL.releaseCurrent();
   }

   @Override
   public final void destroy() {
      try {
         this.mContextGL.destroy();
      } catch (LWJGLException e) {
         throw new RuntimeException(e);
      }
   }

   @Override
   public final void setCLSharingProperties(PointerBuffer properties) throws LWJGLException {
      this.mContextGL.setCLSharingProperties(properties);
   }

   protected void initGL() {
   }

   protected void paintGL() {
   }

   @Override
   public final void paint(Graphics g) {
   }

   protected void exceptionOccurred(LWJGLException exception) {
   }

   @Override
   public void update(Graphics g) {
   }

   @Override
   public void componentShown(ComponentEvent e) {
   }

   @Override
   public void componentHidden(ComponentEvent e) {
   }

   @Override
   public void componentResized(ComponentEvent e) {
   }

   @Override
   public void componentMoved(ComponentEvent e) {
   }

   @Override
   public void setLocation(int x, int y) {
   }

   @Override
   public void setLocation(Point p) {
   }

   @Override
   public void setSize(Dimension d) {
   }

   @Override
   public void setSize(int width, int height) {
   }

   @Override
   public void setBounds(int x, int y, int width, int height) {
   }

   @Override
   public void hierarchyChanged(HierarchyEvent e) {
   }
}
