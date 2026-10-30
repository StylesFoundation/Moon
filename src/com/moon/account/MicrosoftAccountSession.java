package com.moon.account;

import com.google.gson.annotations.SerializedName;

public class MicrosoftAccountSession extends AccountSession {
   @SerializedName("refreshToken")
   private String refreshToken;
   @SerializedName("accessToken")
   private String accessToken;

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public String getAccessToken() {
      return this.accessToken;
   }

   public MicrosoftAccountSession setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
      return this;
   }

   public MicrosoftAccountSession setAccessToken(String accessToken) {
      this.accessToken = accessToken;
      return this;
   }

   public MicrosoftAccountSession() {
   }

   public MicrosoftAccountSession(String refreshToken, String accessToken) {
      this.refreshToken = refreshToken;
      this.accessToken = accessToken;
   }
}
