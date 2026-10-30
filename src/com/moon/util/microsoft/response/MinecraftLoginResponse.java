package com.moon.util.microsoft.response;

import com.google.gson.annotations.SerializedName;

public class MinecraftLoginResponse {
   @SerializedName("access_token")
   private String accessToken;

   public String getAccessToken() {
      return this.accessToken;
   }

   public MinecraftLoginResponse withAccessToken(String value) {
      this.accessToken = value;
      return this;
   }

   public MinecraftLoginResponse(String string) {
      this.accessToken = string;
   }

   public MinecraftLoginResponse() {
   }
}
