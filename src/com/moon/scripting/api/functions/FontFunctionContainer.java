package com.moon.scripting.api.functions;

import com.moon.scripting.api.functions.handlers.font.HeightHandler;
import com.moon.scripting.api.functions.handlers.font.MinecraftHeightHandler;
import com.moon.scripting.api.functions.handlers.font.MinecraftWidthHandler;
import com.moon.scripting.api.functions.handlers.font.WidthHandler;
import com.moon.scripting.api.functions.impl.FontFunctionsImpl;
import org.luaj.vm2.LuaTable;

public class FontFunctionContainer extends FunctionContainer {
   public final FontFunctionsImpl implementation = new FontFunctionsImpl();

   public FontFunctionContainer() {
      super("font");
   }

   @Override
   public void registerFunctions(LuaTable table) {
      table.set("minecraft_width", new MinecraftWidthHandler(this));
      table.set("minecraft_height", new MinecraftHeightHandler(this));
      table.set("width", new WidthHandler(this));
      table.set("height", new HeightHandler(this));
   }
}
