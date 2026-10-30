package com.moon.ui.clickgui.classic.overlay;

import com.moon.util.math.Vector2f;
import java.util.function.Consumer;

public class BezierControlPoint {
   private final Consumer<Vector2f> callback;
   private final boolean smoothHandle;
   private float x;
   private float y;

   public void apply() {
      this.callback.accept(new Vector2f(this.x, this.y));
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return this.isPointInBounds(mouseX, mouseY, this.x - 10.0F, this.y - 10.0F, 20.0F, 20.0F);
   }

   private boolean isPointInBounds(int mouseX, int mouseY, float minX, float minY, float width, float height) {
      return mouseX >= minX && mouseX <= minX + width && mouseY >= minY && mouseY <= minY + height;
   }

   public Consumer<Vector2f> getCallback() {
      return this.callback;
   }

   public boolean isSmoothHandle() {
      return this.smoothHandle;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public BezierControlPoint setX(float x) {
      this.x = x;
      return this;
   }

   public BezierControlPoint setY(float y) {
      this.y = y;
      return this;
   }

   public BezierControlPoint(Consumer<Vector2f> callback, boolean smoothHandle) {
      this.callback = callback;
      this.smoothHandle = smoothHandle;
   }
}
