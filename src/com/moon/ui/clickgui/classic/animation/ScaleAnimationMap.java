package com.moon.ui.clickgui.classic.animation;

import com.moon.ui.clickgui.classic.ClassicClickGui;
import com.moon.util.animation.Animation;
import com.moon.util.animation.Easing;
import java.util.HashMap;

public class ScaleAnimationMap extends HashMap<Easing, Animation> {
   final ClassicClickGui screen;

   public ScaleAnimationMap(ClassicClickGui screen) {
      this.screen = screen;
      this.put(Easing.LINEAR, new Animation(200L, Easing.LINEAR));
      this.put(Easing.EASE_OUT_BACK, new Animation(400L, Easing.EASE_OUT_BACK));
      this.put(Easing.EASE_IN_BACK, new Animation(400L, Easing.EASE_IN_BACK));
   }
}
