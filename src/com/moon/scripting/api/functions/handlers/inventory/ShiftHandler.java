package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;

public class ShiftHandler extends OneArgFunction {
   final InventoryFunctionContainer container;

   public ShiftHandler(InventoryFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue slotValue) {
      if (!slotValue.isnil() && slotValue.isnumber()) {
         int slotId = slotValue.toint();
         this.container.implementation.shift(slotId);
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type number at 0, got " + slotValue.typename());
      }
   }
}
