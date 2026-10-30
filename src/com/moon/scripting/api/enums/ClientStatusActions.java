package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(value = "client_status_actions", path = "enums/client_status_actions")
@Description("The different types of status actions which can be sent in a client status packet")
public enum ClientStatusActions {
   PERFORM_RESPAWN,
   REQUEST_STATS,
   OPEN_INVENTORY_ACHIEVEMENT;
}
