package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.ZeroArgFunction;

public class LowerContainerIdHandler extends ZeroArgFunction {
   final InventoryFunctionContainer container;

   public LowerContainerIdHandler(InventoryFunctionContainer jS) {
      this.container = jS;
   }

   @Override
   public LuaValue call() {
      return LuaValue.valueOf(this.container.implementation.lower_container_id());
   }
}
