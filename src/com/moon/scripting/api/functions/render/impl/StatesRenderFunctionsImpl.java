package com.moon.scripting.api.functions.render.impl;

import com.moon.scripting.api.functions.render.StatesRenderFunctions;
import net.minecraft.client.renderer.GlStateManager;

public class StatesRenderFunctionsImpl implements StatesRenderFunctions {
   @Override
   public void enable_depth() {
      GlStateManager.enableDepth();
   }

   @Override
   public void disable_depth() {
      GlStateManager.disableDepth();
   }

   @Override
   public void enable_blend() {
      GlStateManager.enableBlend();
   }

   @Override
   public void disable_blend() {
      GlStateManager.disableBlend();
   }

   @Override
   public void blend_func(int srcFactor, int dstFactor) {
      GlStateManager.blendFunc(srcFactor, dstFactor);
   }

   @Override
   public void blend_func_separate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
      GlStateManager.tryBlendFuncSeparate(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
   }
}
