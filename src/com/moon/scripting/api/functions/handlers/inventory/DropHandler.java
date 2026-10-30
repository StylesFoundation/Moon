package com.moon.scripting.api.functions.handlers.inventory;

import com.moon.scripting.api.functions.InventoryFunctionContainer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public class DropHandler extends TwoArgFunction {
   final InventoryFunctionContainer container;

   public DropHandler(InventoryFunctionContainer container) {
      this.container = container;
   }

   @Override
   public LuaValue call(LuaValue slotArg, LuaValue dropAllArg) {
      if (slotArg.isnil() || !slotArg.isnumber()) {
         return LuaValue.error("Expected type number at 0, got " + slotArg.typename());
      } else if (!dropAllArg.isnil() && dropAllArg.isboolean()) {
         int slot = slotArg.toint();
         boolean dropAll = dropAllArg.toboolean();
         this.container.implementation.drop(slot, dropAll);
         return LuaValue.NIL;
      } else {
         return LuaValue.error("Expected type boolean at 1, got " + dropAllArg.typename());
      }
   }
}
