package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class GetHarvestLevelHandler extends OneArgFunction {
   final InventoryFunctionContainer container;

   public GetHarvestLevelHandler(InventoryFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue slotValue) {
      if (!slotValue.isnil() && slotValue.isnumber()) {
         int slotId = slotValue.toint();
         return LuaValue.valueOf(this.container.implementation.get_harvest_level(slotId));
      } else {
         return LuaValue.error("Expected type number at 0, got " + slotValue.typename());
      }
   }
}
