package com.moon.util.microsoft.request;

import com.google.gson.annotations.SerializedName;

public class XboxLiveAuthProperties {
   @SerializedName("AuthMethod")
   private String authMethod;
   @SerializedName("SiteName")
   private String siteName;
   @SerializedName("RpsTicket")
   private String rpsTicket;

   public String getAuthMethod() {
      return this.authMethod;
   }

   public String getSiteName() {
      return this.siteName;
   }

   public String getRpsTicket() {
      return this.rpsTicket;
   }

   public XboxLiveAuthProperties withAuthMethod(String authMethod) {
      this.authMethod = authMethod;
      return this;
   }

   public XboxLiveAuthProperties withSiteName(String siteName) {
      this.siteName = siteName;
      return this;
   }

   public XboxLiveAuthProperties withRpsTicket(String rpsTicket) {
      this.rpsTicket = rpsTicket;
      return this;
   }

   public XboxLiveAuthProperties(String authMethod, String siteName, String rpsTicket) {
      this.authMethod = authMethod;
      this.siteName = siteName;
      this.rpsTicket = rpsTicket;
   }
}
