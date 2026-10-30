package com.moon.scripting.api.functions.render;

import com.moon.scripting.api.functions.FunctionContainer;
import com.moon.scripting.api.functions.handlers.render.immediate.GradientRectHandler;
import com.moon.scripting.api.functions.handlers.render.immediate.MinecraftTextHandler;
import com.moon.scripting.api.functions.handlers.render.immediate.OutlineRoundedRectHandler;
import com.moon.scripting.api.functions.handlers.render.immediate.RectHandler;
import com.moon.scripting.api.functions.handlers.render.immediate.RoundedRectHandler;
import com.moon.scripting.api.functions.handlers.render.immediate.TextHandler;
import com.moon.scripting.api.functions.handlers.render.immediate.TriangleHandler;
import com.moon.scripting.api.functions.render.impl.ImmediateRenderFunctionsImpl;
import org.luaj.vm2.LuaTable;

public class ImmediateRenderFunctionContainer extends FunctionContainer {
   public final ImmediateRenderFunctionsImpl implementation = new ImmediateRenderFunctionsImpl();

   public ImmediateRenderFunctionContainer() {
      super("render.immediate");
   }

   @Override
   public void registerFunctions(LuaTable table) {
      table.set("rect", new RectHandler(this));
      table.set("gradient_rect", new GradientRectHandler(this));
      table.set("rounded_rect", new RoundedRectHandler(this));
      table.set("outline_rounded_rect", new OutlineRoundedRectHandler(this));
      table.set("triangle", new TriangleHandler(this));
      table.set("minecraft_text", new MinecraftTextHandler(this));
      table.set("text", new TextHandler(this));
   }
}
