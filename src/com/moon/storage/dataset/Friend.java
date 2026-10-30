package com.moon.storage.dataset;

import com.google.gson.annotations.SerializedName;
import net.minecraft.entity.player.EntityPlayer;

public class Friend {
   @SerializedName("name")
   private final String name;
   @SerializedName("alias")
   private String alias = "";

   public boolean matches(EntityPlayer player) {
      return player.getName().equals(this.name);
   }

   public Friend setAlias(String alias) {
      this.alias = alias;
      return this;
   }

   public String getName() {
      return this.name;
   }

   public String getAlias() {
      return this.alias;
   }

   public Friend(String name) {
      this.name = name;
   }
}
