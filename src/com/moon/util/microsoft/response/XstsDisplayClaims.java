package com.moon.util.microsoft.response;

import com.google.gson.annotations.SerializedName;

public class XstsDisplayClaims {
   @SerializedName("xui")
   private XstsXuiClaim[] xui;

   public XstsXuiClaim[] getXui() {
      return this.xui;
   }

   public XstsDisplayClaims withXui(XstsXuiClaim[] value) {
      this.xui = value;
      return this;
   }

   public XstsDisplayClaims(XstsXuiClaim[] arr) {
      this.xui = arr;
   }

   public XstsDisplayClaims() {
   }
}
