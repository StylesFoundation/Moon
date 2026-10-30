package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(value = "interact_actions", path = "enums/interact_actions")
@Description("The different entity interact actions which can be sent in a 0x02 packet")
public enum InteractActions {
   @Description("Interacts with the target entity")
   INTERACT,
   @Description("Attacks the target entity")
   ATTACK,
   @Description("Interacts with the entity at the given hit vector")
   INTERACT_AT;
}
