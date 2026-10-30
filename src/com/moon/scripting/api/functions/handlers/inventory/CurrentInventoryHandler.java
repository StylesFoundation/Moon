package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class CurrentInventoryHandler extends ZeroArgFunction {
   final InventoryFunctionContainer container;

   public CurrentInventoryHandler(InventoryFunctionContainer jS) {
      this.container = jS;
   }

   @Override
   public LuaValue call() {
      return LuaValue.valueOf(this.container.implementation.current_inventory());
   }
}
