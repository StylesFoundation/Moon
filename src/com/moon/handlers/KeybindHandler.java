package com.moon.handlers;

import com.moon.events.impl.input.KeyPressEvent;
import com.moon.events.impl.input.MouseEvent;
import com.moon.events.subscription.Subscribe;
import com.moon.module.bind.KeyBind;
import com.moon.module.bind.element.BindAction;
import com.moon.module.bind.element.BindType;
import java.util.List;

public class KeybindHandler {
   private final List<KeyBind> keyBinds;

   @Subscribe
   public void onKeyPress(KeyPressEvent event) {
      for (KeyBind keyBind : this.keyBinds) {
         boolean matches = keyBind.getType() == BindType.KEYBOARD && keyBind.value() == event.getKey() && (event.isPressed() || keyBind.getAction() == BindAction.HOLD);
         if (matches) {
            keyBind.trigger();
         }
      }
   }

   @Subscribe
   public void onMouseEvent(MouseEvent event) {
      for (KeyBind keyBind : this.keyBinds) {
         boolean matches = keyBind.getType() == BindType.MOUSE && keyBind.value() == event.getButton() && (event.isPressed() || keyBind.getAction() == BindAction.HOLD);
         if (matches) {
            keyBind.trigger();
         }
      }
   }

   public KeybindHandler(List<KeyBind> keyBinds) {
      this.keyBinds = keyBinds;
   }
}
