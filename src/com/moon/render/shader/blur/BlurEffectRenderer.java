package com.moon.render.shader.blur;

import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public abstract class BlurEffectRenderer {
   protected static final Minecraft mc = Minecraft.getMinecraft();
   protected final Framebuffer mainFramebuffer;
   protected final Framebuffer[] mipFramebuffers;

   public BlurEffectRenderer(Framebuffer mainFramebuffer, Framebuffer[] mipFramebuffers) {
      this.mainFramebuffer = mainFramebuffer;
      this.mipFramebuffers = mipFramebuffers;
   }

   public abstract void render();

   public void prepare() {
   }
}
