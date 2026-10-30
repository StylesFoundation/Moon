package com.moon.storage.dataset;

import com.google.gson.annotations.SerializedName;
import net.minecraft.entity.player.EntityPlayer;

public class Target {
   @SerializedName("name")
   private final String name;

   public boolean matches(EntityPlayer player) {
      return player.getName().equals(this.name);
   }

   public String getName() {
      return this.name;
   }

   public Target(String name) {
      this.name = name;
   }
}
