package com.moon.render.dispatcher;

import com.moon.render.RenderType;
import com.moon.render.dispatcher.impl.ColoredTextureDispatcher;
import com.moon.render.dispatcher.impl.EntityOutlineDispatcher;
import com.moon.render.dispatcher.impl.GlowDownDispatcher;
import com.moon.render.dispatcher.impl.GlowUpDispatcher;
import com.moon.render.dispatcher.impl.GradientDispatcher;
import com.moon.render.dispatcher.impl.JumpFloodOutlineBaseDispatcher;
import com.moon.render.dispatcher.impl.JumpFloodOutlineDispatcher;
import com.moon.render.dispatcher.impl.KawaseDownDispatcher;
import com.moon.render.dispatcher.impl.KawaseFogDispatcher;
import com.moon.render.dispatcher.impl.KawaseStencilDispatcher;
import com.moon.render.dispatcher.impl.KawaseUpDispatcher;
import com.moon.render.dispatcher.impl.MsdfDispatcher;
import com.moon.render.dispatcher.impl.OutlineRoundedRectangleDispatcher;
import com.moon.render.dispatcher.impl.RectangleDispatcher;
import com.moon.render.dispatcher.impl.RoundedRectangleDispatcher;
import com.moon.render.dispatcher.impl.RoundedTextureDispatcher;
import com.moon.render.dispatcher.impl.ShadowDownDispatcher;
import com.moon.render.dispatcher.impl.ShadowUpDispatcher;
import com.moon.render.dispatcher.impl.SmoothingDispatcher;
import com.moon.render.dispatcher.impl.TextureDispatcher;
import com.moon.render.dispatcher.impl.TriangleDispatcher;
import com.moon.render.dispatcher.impl.WorldDispatcher;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RenderTaskDispatcherRegistry {
   private static final Map<RenderType, RenderTaskDispatcher> dispatchers = new HashMap<>();

   public static RenderTaskDispatcher getDispatcher(RenderType type) {
      return dispatchers.get(type);
   }

   private static void initialize() {
      dispatchers.put(RenderType.RECTANGLE, new RectangleDispatcher());
      dispatchers.put(RenderType.ROUNDED_RECTANGLE, new RoundedRectangleDispatcher());
      dispatchers.put(RenderType.TEXTURE, new TextureDispatcher());
      dispatchers.put(RenderType.OUTLINE_ROUNDED_RECTANGLE, new OutlineRoundedRectangleDispatcher());
      dispatchers.put(RenderType.GRADIENT, new GradientDispatcher());
      dispatchers.put(RenderType.WORLD, new WorldDispatcher());
      dispatchers.put(RenderType.KAWASE_DOWN, new KawaseDownDispatcher());
      dispatchers.put(RenderType.KAWASE_UP, new KawaseUpDispatcher());
      dispatchers.put(RenderType.SHADOW_DOWN, new ShadowDownDispatcher());
      dispatchers.put(RenderType.SHADOW_UP, new ShadowUpDispatcher());
      dispatchers.put(RenderType.ENTITY_OUTLINE, new EntityOutlineDispatcher());
      dispatchers.put(RenderType.GLOW_DOWN, new GlowDownDispatcher());
      dispatchers.put(RenderType.GLOW_UP, new GlowUpDispatcher());
      dispatchers.put(RenderType.MSDF, new MsdfDispatcher());
      dispatchers.put(RenderType.TEXTURE_COL, new ColoredTextureDispatcher());
      dispatchers.put(RenderType.TEXTURE_ROUND, new RoundedTextureDispatcher());
      dispatchers.put(RenderType.TRIANGLE, new TriangleDispatcher());
      dispatchers.put(RenderType.JUMP_FLOOD_OUTLINE, new JumpFloodOutlineDispatcher());
      dispatchers.put(RenderType.JUMP_FLOOD_OUTLINE_BASE, new JumpFloodOutlineBaseDispatcher());
      dispatchers.put(RenderType.SMOOTHING, new SmoothingDispatcher());
      dispatchers.put(RenderType.KAWASE_FOG, new KawaseFogDispatcher());
      dispatchers.put(RenderType.KAWASE_STENCIL, new KawaseStencilDispatcher());
      Iterator<RenderTaskDispatcher> iterator = dispatchers.values().iterator();

      while (iterator.hasNext()) {
         iterator.next().initialize();
      }
   }

   static {
      initialize();
   }
}
