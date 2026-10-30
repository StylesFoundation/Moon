package com.moon.scripting.api.functions.handlers.font;

import com.moon.scripting.api.functions.FontFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class MinecraftHeightHandler extends ZeroArgFunction {
   final FontFunctionContainer container;

   public MinecraftHeightHandler(FontFunctionContainer jQ) {
      this.container = jQ;
   }

   @Override
   public LuaValue call() {
      return LuaValue.valueOf(this.container.implementation.minecraft_height());
   }
}
