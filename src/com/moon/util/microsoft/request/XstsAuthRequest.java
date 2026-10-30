package com.moon.util.microsoft.request;

import com.google.gson.annotations.SerializedName;

public class XstsAuthRequest {
   @SerializedName("Properties")
   private XstsAuthProperties properties;
   @SerializedName("RelyingParty")
   private String relyingParty;
   @SerializedName("TokenType")
   private String tokenType;

   public XstsAuthProperties getProperties() {
      return this.properties;
   }

   public String getRelyingParty() {
      return this.relyingParty;
   }

   public String getTokenType() {
      return this.tokenType;
   }

   public XstsAuthRequest withProperties(XstsAuthProperties properties) {
      this.properties = properties;
      return this;
   }

   public XstsAuthRequest withRelyingParty(String relyingParty) {
      this.relyingParty = relyingParty;
      return this;
   }

   public XstsAuthRequest withTokenType(String tokenType) {
      this.tokenType = tokenType;
      return this;
   }

   public XstsAuthRequest(XstsAuthProperties properties, String relyingParty, String tokenType) {
      this.properties = properties;
      this.relyingParty = relyingParty;
      this.tokenType = tokenType;
   }
}
