package com.moon.scripting.api.functions;

import com.moon.scripting.api.types.Item;
import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.Parameter;

@com.moon.scripting.documentation.annotation.FunctionContainer(value = "inventory", path = "functions/inventory")
public interface InventoryFunctions {
   @Description("Drops a given slot id from the inventory")
   void drop(
      @Parameter("slot_id") @Description("The slot id to drop") int slotId,
      @Parameter("stack") @Description("Whether to drop the entire stack or a singular item") boolean stack
   );

   @Description("Shifts a slot id in the inventory")
   void shift(@Parameter("slot_id") @Description("The slot id to shift") int slotId);

   @Description("Swaps a given slot id with a given hot-bar slot")
   void swap(
      @Parameter("slot_id") @Description("The slot id to swap with") int slotId,
      @Parameter("hot_bar_slot") @Description("The hot-bar slot to swap with") int hotbarSlot
   );

   @Description("Provides full access to window clicks, used to move around items in inventories")
   void window_click(
      @Parameter("window_id") @Description("The window id") int windowId,
      @Parameter("slot_id") @Description("The slot id") int slotId,
      @Parameter("mouse_button_clicked") @Description("The mouse button clicked") int mouseButton,
      @Parameter("mode") @Description("The clicking mode") int mode
   );

   @Description("Gets an item out of the given window id")
   Item get_item_in_window(@Parameter("window_id") @Description("The window id") int windowId, @Parameter("slot_id") @Description("The slot id") int slotId);

   @Description("Gets an item out of the players inventory")
   Item get_item(@Parameter("slot_id") @Description("The slot id") int slotId);

   @Description("Gets the damage reduction of the given armor item. Returns 0 if the item is not an armor item")
   double get_damage_reduction(@Parameter("slot_id") @Description("The slot id") int slotId);

   @Description("Gets the damage amount of the given item")
   double get_damage_amount(@Parameter("slot_id") @Description("The slot id") int slotId);

   @Description("Gets the harvest level of the given item")
   double get_harvest_level(@Parameter("slot_id") @Description("The slot id") int slotId);

   @Description("Returns the upper chest inventory if a chest gui is currently open")
   int upper_container_id();

   @Description("Returns the lower chest inventory if a chest gui is currently open")
   int lower_container_id();

   @Description("Returns the player's inventory id")
   int player_inventory_id();

   @Description("Returns the current inventory the player is in, returns NIL if player is out of inventory")
   String current_inventory();
}
