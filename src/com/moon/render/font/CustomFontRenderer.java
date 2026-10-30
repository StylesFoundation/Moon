package com.moon.render.font;

import com.moon.render.RenderTask;
import org.lwjgl.util.vector.Matrix4f;

public abstract class CustomFontRenderer {
   public abstract void draw(RenderTask context, String text, float x, float y, int color, boolean shadow);

   public abstract void begin(Matrix4f matrix);

   public abstract void end(Matrix4f matrix);

   public abstract float getStringWidth(String text);

   public abstract float getLineHeightRatio();
}
