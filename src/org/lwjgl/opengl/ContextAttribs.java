package org.lwjgl.opengl;

public final class ContextAttribs {
   public ContextAttribs() {
   }

   public ContextAttribs(int majorVersion, int minorVersion) {
   }

   public int getMajorVersion() {
      return 0;
   }

   public int getMinorVersion() {
      return 0;
   }

   public int getLayerPlane() {
      return 0;
   }

   public boolean isDebug() {
      return false;
   }

   public boolean isForwardCompatible() {
      return false;
   }

   public boolean isProfileCore() {
      return false;
   }

   public boolean isProfileCompatibility() {
      return false;
   }

   public boolean isProfileES() {
      return false;
   }

   public ContextAttribs withLayer(int layerPlane) {
      return null;
   }

   public ContextAttribs withDebug(boolean debug) {
      return null;
   }

   public ContextAttribs withForwardCompatible(boolean forwardCompatible) {
      return null;
   }

   public ContextAttribs withProfileCore(boolean profileCore) {
      return null;
   }

   public ContextAttribs withProfileCompatibility(boolean profileCompatibility) {
      return null;
   }

   public ContextAttribs withProfileES(boolean profileES) {
      return null;
   }

   public ContextAttribs withLoseContextOnReset(boolean loseContextOnReset) {
      return null;
   }

   public ContextAttribs withContextResetIsolation(boolean contextResetIsolation) {
      return null;
   }

   @Override
   public String toString() {
      return null;
   }
}
