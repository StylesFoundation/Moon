package com.moon.module.bind;

import java.util.ArrayList;
import java.util.List;

public class BindManager {
   private final List<KeyBind> binds = new ArrayList<>();

   public void add(KeyBind keybind) {
      this.binds.add(keybind);
   }

   public void remove(KeyBind keybind) {
      this.binds.remove(keybind);
   }

   public List<KeyBind> getBinds() {
      return binds;
   }
}
