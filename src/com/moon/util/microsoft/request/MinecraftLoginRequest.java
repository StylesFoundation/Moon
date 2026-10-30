package com.moon.util.microsoft.request;

import com.google.gson.annotations.SerializedName;

public class MinecraftLoginRequest {
   @SerializedName("identityToken")
   private String identityToken;

   public String getIdentityToken() {
      return this.identityToken;
   }

   public MinecraftLoginRequest withIdentityToken(String identityToken) {
      this.identityToken = identityToken;
      return this;
   }

   public MinecraftLoginRequest(String string) {
      this.identityToken = string;
   }
}
