package com.moon.util.microsoft.response;

import com.google.gson.annotations.SerializedName;

public class XstsAuthResponse {
   @SerializedName("Token")
   private String token;
   @SerializedName("DisplayClaims")
   private XstsDisplayClaims displayClaims;

   public String getToken() {
      return this.token;
   }

   public XstsDisplayClaims getDisplayClaims() {
      return this.displayClaims;
   }

   public XstsAuthResponse withToken(String value) {
      this.token = value;
      return this;
   }

   public XstsAuthResponse withDisplayClaims(XstsDisplayClaims value) {
      this.displayClaims = value;
      return this;
   }

   public XstsAuthResponse(String string, XstsDisplayClaims cT) {
      this.token = string;
      this.displayClaims = cT;
   }

   public XstsAuthResponse() {
   }

   public static int zero() {
      return 0;
   }
}
