package com.moon.scripting.api.functions.handlers.font;

import com.moon.scripting.api.functions.FontFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class MinecraftWidthHandler extends OneArgFunction {
   final FontFunctionContainer container;

   public MinecraftWidthHandler(FontFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue textArg) {
      if (!textArg.isstring()) {
         return LuaValue.error("Expected type string at 0, got " + textArg.typename());
      }

      String text = textArg.tojstring();
      return LuaValue.valueOf(this.container.implementation.minecraft_width(text));
   }
}
