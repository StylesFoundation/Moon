package com.moon.render;

public class RenderTransformState {
   private static float scaleX = 1.0F;
   private static float scaleY = 1.0F;
   private static float scaleZ = 1.0F;
   private static float translateX;
   private static float translateY;
   private static float translateZ;
   private static float rotationAngle;
   private static float rotationAxisX;
   private static float rotationAxisY;
   private static float rotationAxisZ;
   private static float alphaMultiplier = 1.0F;

   public static void setScale(float x, float y, float z) {
      scaleX = x;
      scaleY = y;
      scaleZ = z;
   }

   public static void setTranslation(float x, float y, float z) {
      translateX = x;
      translateY = y;
      translateZ = z;
   }

   public static void setRotation(float angle, float axisX, float axisY, float axisZ) {
      rotationAngle = angle;
      rotationAxisX = axisX;
      rotationAxisY = axisY;
      rotationAxisZ = axisZ;
   }

   public static void setAlphaMultiplier(float alphaMultiplier) {
      RenderTransformState.alphaMultiplier = alphaMultiplier;
   }

   public static void resetScale() {
      scaleZ = 1.0F;
      scaleY = 1.0F;
      scaleX = 1.0F;
   }

   public static void resetTranslation() {
      translateZ = 0.0F;
      translateY = 0.0F;
      translateX = 0.0F;
   }

   public static void resetRotation() {
      rotationAngle = 0.0F;
      rotationAxisZ = 0.0F;
      rotationAxisY = 0.0F;
      rotationAxisX = 0.0F;
   }

   public static void resetAlphaMultiplier() {
      alphaMultiplier = 1.0F;
   }

   public static boolean hasScale() {
      return scaleX != 1.0F || scaleY != 1.0F || scaleZ != 1.0F;
   }

   public static boolean hasTranslation() {
      return translateX != 0.0F || translateY != 0.0F || translateZ != 0.0F;
   }

   public static boolean hasRotation() {
      return rotationAngle != 0.0F || rotationAxisX != 0.0F || rotationAxisY != 0.0F || rotationAxisZ != 0.0F;
   }

   public static float getScaleX() {
      return scaleX;
   }

   public static float getScaleY() {
      return scaleY;
   }

   public static float getScaleZ() {
      return scaleZ;
   }

   public static void setScaleX(float scaleX) {
      RenderTransformState.scaleX = scaleX;
   }

   public static void setScaleY(float scaleY) {
      RenderTransformState.scaleY = scaleY;
   }

   public static void setScaleZ(float scaleZ) {
      RenderTransformState.scaleZ = scaleZ;
   }

   public static float getTranslateX() {
      return translateX;
   }

   public static float getTranslateY() {
      return translateY;
   }

   public static float getTranslateZ() {
      return translateZ;
   }

   public static void setTranslateX(float translateX) {
      RenderTransformState.translateX = translateX;
   }

   public static void setTranslateY(float translateY) {
      RenderTransformState.translateY = translateY;
   }

   public static void setTranslateZ(float translateZ) {
      RenderTransformState.translateZ = translateZ;
   }

   public static float getRotationAngle() {
      return rotationAngle;
   }

   public static float getRotationAxisX() {
      return rotationAxisX;
   }

   public static float getRotationAxisY() {
      return rotationAxisY;
   }

   public static float getRotationAxisZ() {
      return rotationAxisZ;
   }

   public static void setRotationAngle(float angle) {
      rotationAngle = angle;
   }

   public static void setRotationAxisX(float axisX) {
      rotationAxisX = axisX;
   }

   public static void setRotationAxisY(float axisY) {
      rotationAxisY = axisY;
   }

   public static void setRotationAxisZ(float axisZ) {
      rotationAxisZ = axisZ;
   }

   public static float getAlphaMultiplier() {
      return alphaMultiplier;
   }
}
