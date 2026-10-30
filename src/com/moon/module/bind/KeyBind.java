package com.moon.module.bind;

import com.moon.module.bind.element.BindAction;
import com.moon.module.bind.element.BindType;

public interface KeyBind {
   int value();

   BindType getType();

   BindAction getAction();

   void trigger();
}
