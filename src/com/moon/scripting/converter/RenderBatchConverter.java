package com.moon.scripting.converter;

import com.moon.scripting.api.functions.handlers.render.batched.RenderBatchAddFunction;
import com.moon.scripting.api.functions.handlers.render.batched.RenderBatchRenderFunction;
import com.moon.scripting.api.types.render.RenderBatch;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class RenderBatchConverter extends LuaConverter<RenderBatch> {
   public LuaValue convert(RenderBatch renderBatch) {
      LuaTable table = new LuaTable();
      table.set("add", new RenderBatchAddFunction(this, renderBatch));
      table.set("render", new RenderBatchRenderFunction(this, renderBatch));
      return table;
   }
}
