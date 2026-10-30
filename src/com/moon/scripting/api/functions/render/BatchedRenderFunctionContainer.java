package com.moon.scripting.api.functions.render;

import com.moon.scripting.api.functions.FunctionContainer;
import com.moon.scripting.api.functions.handlers.render.batched.PrepareBatchHandler;
import com.moon.scripting.api.functions.handlers.render.batched.RenderBatchHandler;
import com.moon.scripting.api.functions.render.impl.BatchedRenderFunctionsImpl;
import org.luaj.vm2.LuaTable;

public class BatchedRenderFunctionContainer extends FunctionContainer {
   public final BatchedRenderFunctionsImpl implementation = new BatchedRenderFunctionsImpl();

   public BatchedRenderFunctionContainer() {
      super("render.batched");
   }

   @Override
   public void registerFunctions(LuaTable table) {
      table.set("prepare_batch", new PrepareBatchHandler(this));
      table.set("render_batch", new RenderBatchHandler(this));
   }
}
