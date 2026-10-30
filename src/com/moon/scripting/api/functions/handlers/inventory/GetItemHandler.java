package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetItemHandler extends OneArgFunction {
   final InventoryFunctionContainer container;

   public GetItemHandler(InventoryFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue slotValue) {
      if (!slotValue.isnil() && slotValue.isnumber()) {
         int slotId = slotValue.toint();
         return LuaConverterRegistry.toLuaValue(this.container.implementation.get_item(slotId));
      } else {
         return LuaValue.error("Expected type number at 0, got " + slotValue.typename());
      }
   }
}
