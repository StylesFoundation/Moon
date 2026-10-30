package com.moon.scripting.api.functions;

import com.moon.scripting.api.functions.handlers.inventory.CurrentInventoryHandler;
import com.moon.scripting.api.functions.handlers.inventory.DropHandler;
import com.moon.scripting.api.functions.handlers.inventory.GetDamageAmountHandler;
import com.moon.scripting.api.functions.handlers.inventory.GetDamageReductionHandler;
import com.moon.scripting.api.functions.handlers.inventory.GetHarvestLevelHandler;
import com.moon.scripting.api.functions.handlers.inventory.GetItemHandler;
import com.moon.scripting.api.functions.handlers.inventory.GetItemInWindowHandler;
import com.moon.scripting.api.functions.handlers.inventory.LowerContainerIdHandler;
import com.moon.scripting.api.functions.handlers.inventory.PlayerInventoryIdHandler;
import com.moon.scripting.api.functions.handlers.inventory.ShiftHandler;
import com.moon.scripting.api.functions.handlers.inventory.SwapHandler;
import com.moon.scripting.api.functions.handlers.inventory.UpperContainerIdHandler;
import com.moon.scripting.api.functions.handlers.inventory.WindowClickHandler;
import com.moon.scripting.api.functions.impl.InventoryFunctionsImpl;
import org.luaj.vm2.LuaTable;

public class InventoryFunctionContainer extends FunctionContainer {
   public final InventoryFunctionsImpl implementation = new InventoryFunctionsImpl();

   public InventoryFunctionContainer() {
      super("inventory");
   }

   @Override
   public void registerFunctions(LuaTable table) {
      table.set("drop", new DropHandler(this));
      table.set("shift", new ShiftHandler(this));
      table.set("swap", new SwapHandler(this));
      table.set("window_click", new WindowClickHandler(this));
      table.set("get_item_in_window", new GetItemInWindowHandler(this));
      table.set("get_item", new GetItemHandler(this));
      table.set("get_damage_reduction", new GetDamageReductionHandler(this));
      table.set("get_damage_amount", new GetDamageAmountHandler(this));
      table.set("get_harvest_level", new GetHarvestLevelHandler(this));
      table.set("upper_container_id", new UpperContainerIdHandler(this));
      table.set("lower_container_id", new LowerContainerIdHandler(this));
      table.set("player_inventory_id", new PlayerInventoryIdHandler(this));
      table.set("current_inventory", new CurrentInventoryHandler(this));
   }
}
