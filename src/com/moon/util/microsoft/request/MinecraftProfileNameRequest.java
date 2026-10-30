package com.moon.util.microsoft.request;

import com.google.gson.annotations.SerializedName;

public class MinecraftProfileNameRequest {
   @SerializedName("profileName")
   private String profileName;

   public String getProfileName() {
      return this.profileName;
   }

   public MinecraftProfileNameRequest(String string) {
      this.profileName = string;
   }

   public MinecraftProfileNameRequest() {
   }
}
