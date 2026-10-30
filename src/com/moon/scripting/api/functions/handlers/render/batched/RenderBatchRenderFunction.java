package com.moon.scripting.api.functions.handlers.render.batched;

import com.moon.scripting.api.types.render.RenderBatch;
import com.moon.scripting.converter.RenderBatchConverter;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class RenderBatchRenderFunction extends ZeroArgFunction {
   public final RenderBatch batch;
   public final RenderBatchConverter converter;

   public RenderBatchRenderFunction(RenderBatchConverter kb, RenderBatch renderBatch) {
      this.converter = kb;
      this.batch = renderBatch;
   }

   @Override
   public LuaValue call() {
      this.batch.render();
      return NIL;
   }
}
