package com.moon.ui.account;

public class MicrosoftAuthData {
   private String microsoftAccessToken;
   private String minecraftAccessToken;
   private String refreshToken;

   public String getMicrosoftAccessToken() {
      return this.microsoftAccessToken;
   }

   public String getMinecraftAccessToken() {
      return this.minecraftAccessToken;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public MicrosoftAuthData setMicrosoftAccessToken(String microsoftAccessToken) {
      this.microsoftAccessToken = microsoftAccessToken;
      return this;
   }

   public MicrosoftAuthData setMinecraftAccessToken(String minecraftAccessToken) {
      this.minecraftAccessToken = minecraftAccessToken;
      return this;
   }

   public MicrosoftAuthData setRefreshToken(String refreshToken) {
      this.refreshToken = refreshToken;
      return this;
   }

   public MicrosoftAuthData(String microsoftAccessToken, String minecraftAccessToken, String refreshToken) {
      this.microsoftAccessToken = microsoftAccessToken;
      this.minecraftAccessToken = minecraftAccessToken;
      this.refreshToken = refreshToken;
   }

   public static boolean alwaysFalse() {
      return false;
   }

   static {
      if (alwaysFalse()) {
      }
   }
}
