package com.moon.util.microsoft.response;

import com.google.gson.annotations.SerializedName;

public class XstsXuiClaim {
   @SerializedName("uhs")
   private String uhs;

   public String getUhs() {
      return this.uhs;
   }

   public XstsXuiClaim withUhs(String value) {
      this.uhs = value;
      return this;
   }

   public XstsXuiClaim(String string) {
      this.uhs = string;
   }

   public XstsXuiClaim() {
   }
}
