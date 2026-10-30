package com.moon.render.font.msdf;

import com.google.gson.annotations.SerializedName;

public class MsdfAtlasMetadata {
   @SerializedName("type")
   private String type;
   @SerializedName("distanceRange")
   private float distanceRange;
   @SerializedName("size")
   private float size;
   @SerializedName("width")
   private float width;
   @SerializedName("height")
   private float height;
   @SerializedName("yOrigin")
   private String yOrigin;

   public String getType() {
      return this.type;
   }

   public float getDistanceRange() {
      return this.distanceRange;
   }

   public float getSize() {
      return this.size;
   }

   public float getWidth() {
      return this.width;
   }

   public float getHeight() {
      return this.height;
   }

   public String getYOrigin() {
      return this.yOrigin;
   }

   public MsdfAtlasMetadata setType(String type) {
      this.type = type;
      return this;
   }

   public MsdfAtlasMetadata setDistanceRange(float distanceRange) {
      this.distanceRange = distanceRange;
      return this;
   }

   public MsdfAtlasMetadata setSize(float size) {
      this.size = size;
      return this;
   }

   public MsdfAtlasMetadata setWidth(float width) {
      this.width = width;
      return this;
   }

   public MsdfAtlasMetadata setHeight(float height) {
      this.height = height;
      return this;
   }

   public MsdfAtlasMetadata setYOrigin(String yOrigin) {
      this.yOrigin = yOrigin;
      return this;
   }
}
