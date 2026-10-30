package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(value = "entity_action", path = "enums/entity_action")
@Description("The different entity actions which can be sent in a 0x0B packet")
public enum EntityAction {
   @Description("Starts sneaking on the server")
   START_SNEAKING,
   @Description("Stops sneaking on the server")
   STOP_SNEAKING,
   @Description("Stops sleeping on the server")
   STOP_SLEEPING,
   @Description("Starts sprinting on the server")
   START_SPRINTING,
   @Description("Stops sprinting on the server")
   STOP_SPRINTING,
   @Description("Executes a riding jump on the server")
   RIDING_JUMP,
   @Description("Executed an open inventory request on the server")
   OPEN_INVENTORY;
}
