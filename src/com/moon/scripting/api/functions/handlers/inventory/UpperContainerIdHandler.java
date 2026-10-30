package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class UpperContainerIdHandler extends ZeroArgFunction {
   final InventoryFunctionContainer container;

   public UpperContainerIdHandler(InventoryFunctionContainer jS) {
      this.container = jS;
   }

   @Override
   public LuaValue call() {
      return LuaValue.valueOf(this.container.implementation.upper_container_id());
   }
}
