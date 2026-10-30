package com.moon.module;

public enum ModuleCategory implements LabelEnum {
   COMBAT("Combat", "\uea2c"),
   MOVEMENT("Movement", "\ue566"),
   PLAYER("Player", "\ue7fd"),
   EXPLOIT("Exploit", "\ue868"),
   VISUAL("Visual", "\ue8f4"),
   SCRIPTS("Scripts", "\ue86f");

   private final String name;
   private final String icon;

   @Override
   public String label() {
      return this.name;
   }

   public String getName() {
      return this.name;
   }

   public String getIcon() {
      return this.icon;
   }

   ModuleCategory(String name, String icon) {
      this.name = name;
      this.icon = icon;
   }
}
