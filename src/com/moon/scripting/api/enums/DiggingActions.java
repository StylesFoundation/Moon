package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(value = "digging_action", path = "enums/digging_action")
@Description("The different digging actions which can be sent in a 0x07 packet")
public enum DiggingActions {
   @Description("Starts destroying a block")
   START_DESTROY_BLOCK,
   @Description("Aborts destroying a block")
   ABORT_DESTROY_BLOCK,
   @Description("Stops destroying a block, indicating its broken")
   STOP_DESTROY_BLOCK,
   @Description("Drops all items")
   DROP_ALL_ITEMS,
   @Description("Drops one item")
   DROP_ITEM,
   @Description("Releases the current item")
   RELEASE_USE_ITEM;
}
