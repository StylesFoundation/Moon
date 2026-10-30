package net.minecraft.client.settings;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.moon.Moon;
import com.moon.events.impl.input.KeyStateEvent;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IntHashMap;

public class KeyBinding implements Comparable<KeyBinding> {
   private static final List<KeyBinding> keybindArray = Lists.newArrayList();
   private static final IntHashMap<KeyBinding> hash = new IntHashMap<>();
   private static final Set<String> keybindSet = Sets.newHashSet();
   private final String keyDescription;
   private final int keyCodeDefault;
   private final String keyCategory;
   private int keyCode;
   private boolean pressed;
   private int pressTime;

   public static void onTick(int integer) {
      if (integer != 0) {
         KeyBinding var4 = hash.lookup(integer);
         if (var4 != null) {
            var4.pressTime++;
         }
      }
   }

   public static boolean Method390(int integer) {
      return hash.lookup(integer).pressed;
   }

   public static void setKeyBindState(int integer, boolean boolean2) {
      if (integer != 0) {
         KeyBinding var4 = hash.lookup(integer);
         if (var4 != null) {
            KeyStateEvent var5 = new KeyStateEvent(integer, boolean2);
            Moon.getInstance().getEventBus().publish(var5);
            if (!var5.isCancelled()) {
               var4.pressed = var5.isPressed();
            }
         }
      }
   }

   public static void Method391(int integer, boolean boolean2) {
      if (integer != 0) {
         KeyBinding var5 = hash.lookup(integer);
         if (var5 != null) {
            var5.pressed = boolean2;
         }
      }
   }

   public static void unPressAllKeys() {
      Iterator var3 = keybindArray.iterator();

      while (var3.hasNext()) {
         ((KeyBinding)var3.next()).unpressKey();
      }
   }

   public static void resetKeyBindingArrayAndHash() {
      hash.clearMap();

      for (KeyBinding var4 : keybindArray) {
         hash.addKey(var4.keyCode, var4);
      }
   }

   public static Set<String> getKeybinds() {
      return keybindSet;
   }

   public KeyBinding(String string1, int integer, String string3) {
      this.keyDescription = string1;
      this.keyCode = integer;
      this.keyCodeDefault = integer;
      this.keyCategory = string3;
      keybindArray.add(this);
      hash.addKey(integer, this);
      keybindSet.add(string3);
   }

   public boolean isKeyDown() {
      return this.pressed;
   }

   public String getKeyCategory() {
      return this.keyCategory;
   }

   public boolean isPressed() {
      if (this.pressTime == 0) {
         return false;
      }

      this.pressTime--;
      return true;
   }

   private void unpressKey() {
      this.pressTime = 0;
      this.pressed = false;
   }

   public String getKeyDescription() {
      return this.keyDescription;
   }

   public int getKeyCodeDefault() {
      return this.keyCodeDefault;
   }

   public int getKeyCode() {
      return this.keyCode;
   }

   public void setKeyCode(int integer) {
      this.keyCode = integer;
   }

   public int compareTo(KeyBinding keyBinding) {
      int var5 = I18n.format(this.keyCategory).compareTo(I18n.format(keyBinding.keyCategory));
      if (var5 == 0) {
         var5 = I18n.format(this.keyDescription).compareTo(I18n.format(keyBinding.keyDescription));
      }

      return var5;
   }
}
