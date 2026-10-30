package com.moon.util.microsoft.response;

import com.google.gson.annotations.SerializedName;

public class OAuthTokenResponse {
   @SerializedName("access_token")
   private String accessToken;
   @SerializedName("refresh_token")
   private String refreshToken;

   public String getAccessToken() {
      return this.accessToken;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public OAuthTokenResponse withAccessToken(String value) {
      this.accessToken = value;
      return this;
   }

   public OAuthTokenResponse withRefreshToken(String value) {
      this.refreshToken = value;
      return this;
   }

   public OAuthTokenResponse(String accessToken, String refreshToken) {
      this.accessToken = accessToken;
      this.refreshToken = refreshToken;
   }

   public OAuthTokenResponse() {
   }
}
