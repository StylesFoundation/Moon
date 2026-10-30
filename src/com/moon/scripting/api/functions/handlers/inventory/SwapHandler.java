package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class SwapHandler extends TwoArgFunction {
   final InventoryFunctionContainer container;

   public SwapHandler(InventoryFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue slotValue, LuaValue hotbarSlotValue) {
      if (slotValue.isnil() || !slotValue.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + slotValue.typename());
      } else if (!hotbarSlotValue.isnil() && hotbarSlotValue.isnumber()) {
         int slotId = slotValue.toint();
         int hotbarSlot = hotbarSlotValue.toint();
         this.container.implementation.swap(slotId, hotbarSlot);
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 1, got " + hotbarSlotValue.typename());
      }
   }
}
