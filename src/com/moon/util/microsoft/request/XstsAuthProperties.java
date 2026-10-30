package com.moon.util.microsoft.request;

import com.google.gson.annotations.SerializedName;

public class XstsAuthProperties {
   @SerializedName("UserTokens")
   private String[] userTokens;
   @SerializedName("SandboxId")
   private String sandboxId;

   public String[] getUserTokens() {
      return this.userTokens;
   }

   public String getSandboxId() {
      return this.sandboxId;
   }

   public XstsAuthProperties withUserTokens(String[] userTokens) {
      this.userTokens = userTokens;
      return this;
   }

   public XstsAuthProperties withSandboxId(String sandboxId) {
      this.sandboxId = sandboxId;
      return this;
   }

   public XstsAuthProperties(String[] arr, String string) {
      this.userTokens = arr;
      this.sandboxId = string;
   }

   public static boolean verify() {
      return true;
   }

   static {
      if (!verify()) {
      }
   }
}
