package com.moon.scripting.api.functions.render;

import com.moon.scripting.api.functions.FunctionContainer;
import com.moon.scripting.api.functions.handlers.render.state.BlendFuncHandler;
import com.moon.scripting.api.functions.handlers.render.state.BlendFuncSeparateHandler;
import com.moon.scripting.api.functions.handlers.render.state.DisableBlendHandler;
import com.moon.scripting.api.functions.handlers.render.state.DisableDepthHandler;
import com.moon.scripting.api.functions.handlers.render.state.EnableBlendHandler;
import com.moon.scripting.api.functions.handlers.render.state.EnableDepthHandler;
import com.moon.scripting.api.functions.render.impl.StatesRenderFunctionsImpl;
import org.luaj.vm2.LuaTable;

public class StatesRenderFunctionContainer extends FunctionContainer {
   public final StatesRenderFunctionsImpl implementation = new StatesRenderFunctionsImpl();

   public StatesRenderFunctionContainer() {
      super("render.state");
   }

   @Override
   public void registerFunctions(LuaTable table) {
      table.set("enable_depth", new EnableDepthHandler(this));
      table.set("disable_depth", new DisableDepthHandler(this));
      table.set("enable_blend", new EnableBlendHandler(this));
      table.set("disable_blend", new DisableBlendHandler(this));
      table.set("blend_func", new BlendFuncHandler(this));
      table.set("blend_func_separate", new BlendFuncSeparateHandler(this));
   }
}
