package com.moon.util.microsoft.response;

import com.google.gson.annotations.SerializedName;

public class XboxLiveAuthResponse {
   @SerializedName("Token")
   private String token;

   public String getToken() {
      return this.token;
   }

   public XboxLiveAuthResponse withToken(String value) {
      this.token = value;
      return this;
   }

   public XboxLiveAuthResponse(String string) {
      this.token = string;
   }

   public XboxLiveAuthResponse() {
   }
}
