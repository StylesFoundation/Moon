package com.moon.module.bind.element;

import com.moon.command.element.annotations.EnumMapping;

@EnumMapping({"press", "hold"})
public enum BindAction {
   PRESS,
   HOLD;
}
