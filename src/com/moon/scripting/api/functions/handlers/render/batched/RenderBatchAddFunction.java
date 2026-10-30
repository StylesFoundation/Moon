package com.moon.scripting.api.functions.handlers.render.batched;

import com.moon.scripting.api.types.render.RenderBatch;
import com.moon.scripting.api.types.templates.RenderTemplate;
import com.moon.scripting.converter.RenderBatchConverter;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

public class RenderBatchAddFunction extends OneArgFunction {
   public final RenderBatch batch;
   public final RenderBatchConverter converter;

   public RenderBatchAddFunction(RenderBatchConverter converter, RenderBatch renderBatch) {
      this.converter = converter;
      this.batch = renderBatch;
   }

   @Override
   public LuaValue call(LuaValue luaValue) {
      if (luaValue.isnil()) {
         return LuaValue.error("Expected type render_template extension at 0, got " + luaValue.typename());
      } else if (CoerceLuaToJava.coerce(luaValue, Object.class) instanceof RenderTemplate renderTemplate) {
         this.batch.add(renderTemplate);
         return NIL;
      } else {
         return LuaValue.error("Expected type render_template extension at 0, got " + luaValue.typename());
      }
   }
}
