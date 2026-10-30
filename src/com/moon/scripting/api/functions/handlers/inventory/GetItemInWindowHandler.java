package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import com.moon.scripting.converter.LuaConverterRegistry;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class GetItemInWindowHandler extends TwoArgFunction {
   final InventoryFunctionContainer container;

   public GetItemInWindowHandler(InventoryFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue windowIdValue, LuaValue slotIdValue) {
      if (windowIdValue.isnil() || !windowIdValue.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + windowIdValue.typename());
      } else if (!slotIdValue.isnil() && slotIdValue.isnumber()) {
         int windowId = windowIdValue.toint();
         int slotId = slotIdValue.toint();
         return LuaConverterRegistry.toLuaValue(this.container.implementation.get_item_in_window(windowId, slotId));
      } else {
         return LuaValue.error("Expected type number at 1, got " + slotIdValue.typename());
      }
   }
}
