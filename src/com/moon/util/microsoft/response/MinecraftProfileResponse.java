package com.moon.util.microsoft.response;

import com.google.gson.annotations.SerializedName;

public class MinecraftProfileResponse {
   @SerializedName("id")
   private String id;
   @SerializedName("name")
   private String name;

   public String getId() {
      return this.id;
   }

   public String getName() {
      return this.name;
   }

   public MinecraftProfileResponse(String id, String name) {
      this.id = id;
      this.name = name;
   }

   public MinecraftProfileResponse() {
   }
}
