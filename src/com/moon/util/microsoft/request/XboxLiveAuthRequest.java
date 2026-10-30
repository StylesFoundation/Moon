package com.moon.util.microsoft.request;

import com.google.gson.annotations.SerializedName;

public class XboxLiveAuthRequest {
   @SerializedName("Properties")
   private XboxLiveAuthProperties properties;
   @SerializedName("RelyingParty")
   private String relyingParty;
   @SerializedName("TokenType")
   private String tokenType;

   public XboxLiveAuthProperties getProperties() {
      return this.properties;
   }

   public String getRelyingParty() {
      return this.relyingParty;
   }

   public String getTokenType() {
      return this.tokenType;
   }

   public XboxLiveAuthRequest withProperties(XboxLiveAuthProperties properties) {
      this.properties = properties;
      return this;
   }

   public XboxLiveAuthRequest withRelyingParty(String relyingParty) {
      this.relyingParty = relyingParty;
      return this;
   }

   public XboxLiveAuthRequest withTokenType(String tokenType) {
      this.tokenType = tokenType;
      return this;
   }

   public XboxLiveAuthRequest(XboxLiveAuthProperties properties, String relyingParty, String tokenType) {
      this.properties = properties;
      this.relyingParty = relyingParty;
      this.tokenType = tokenType;
   }
}
