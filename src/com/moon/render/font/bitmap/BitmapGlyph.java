package com.moon.render.font.bitmap;

import com.google.gson.annotations.SerializedName;

public class BitmapGlyph {
   @SerializedName("id")
   private int id;
   @SerializedName("x")
   private int x;
   @SerializedName("y")
   private int y;
   @SerializedName("w")
   private int width;
   @SerializedName("h")
   private int height;

   public int getId() {
      return this.id;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   public BitmapGlyph withId(int id) {
      this.id = id;
      return this;
   }

   public BitmapGlyph withX(int x) {
      this.x = x;
      return this;
   }

   public BitmapGlyph withY(int y) {
      this.y = y;
      return this;
   }

   public BitmapGlyph withWidth(int width) {
      this.width = width;
      return this;
   }

   public BitmapGlyph withHeight(int height) {
      this.height = height;
      return this;
   }
}
