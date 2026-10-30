package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(value = "hittype", path = "enums/hittype")
@Description("The different hit types a [ray-cast](../types/raycastresult.md) can have")
public enum HitType {
   @Description("Not hitting anything")
   MISS,
   @Description("Hitting block")
   BLOCK,
   @Description("Hitting entity")
   ENITTY;
}
