package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(value = "facing", path = "enums/facing")
@Description("The different face values used in several functions")
public enum Facing {
   DOWN,
   UP,
   NORTH,
   SOUTH,
   WEST,
   EAST;
}
