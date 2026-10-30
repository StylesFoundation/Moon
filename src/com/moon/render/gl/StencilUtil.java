package com.moon.render.gl;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class StencilUtil {
   public static void beginStencil(boolean renderColor) {
      GL11.glClearStencil(0);
      GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
      GL11.glEnable(GL11.GL_STENCIL_TEST);
      GL11.glStencilFunc(GL11.GL_GEQUAL, 1, 1);
      GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
      if (!renderColor) {
         GlStateManager.colorMask(false, false, false, false);
      }
   }

   public static void renderIntoStencil(boolean inside) {
      GlStateManager.enableBlend();
      GL11.glStencilFunc(inside ? 515 : 517, 1, 1);
      GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
      GlStateManager.colorMask(true, true, true, true);
   }

   public static void endStencil() {
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GL11.glDisable(GL11.GL_STENCIL_TEST);
   }
}
