package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;

public class WindowClickHandler extends VarArgFunction {
   final InventoryFunctionContainer container;

   public WindowClickHandler(InventoryFunctionContainer container) {
      this.container = container;
   }

   @Override
   public Varargs invoke(Varargs varargs) {
      LuaValue windowIdArg = varargs.arg(1);
      LuaValue slotIdArg = varargs.arg(2);
      LuaValue mouseButtonArg = varargs.arg(3);
      LuaValue modeArg = varargs.arg(4);
      if (windowIdArg.isnil() || !windowIdArg.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + windowIdArg.typename());
      } else if (slotIdArg.isnil() || !slotIdArg.isnumber()) {
         return LuaValue.error("Expected type number at 1, got " + slotIdArg.typename());
      } else if (mouseButtonArg.isnil() || !mouseButtonArg.isnumber()) {
         return LuaValue.error("Expected type number at 2, got " + mouseButtonArg.typename());
      } else if (!modeArg.isnil() && modeArg.isnumber()) {
         int windowId = windowIdArg.toint();
         int slotId = slotIdArg.toint();
         int mouseButtonClicked = mouseButtonArg.toint();
         int mode = modeArg.toint();
         this.container.implementation.window_click(windowId, slotId, mouseButtonClicked, mode);
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 3, got " + modeArg.typename());
      }
   }
}
