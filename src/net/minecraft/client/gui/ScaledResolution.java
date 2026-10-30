package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class ScaledResolution {
   private final double scaledWidthD;
   private final double scaledHeightD;
   private int c;
   private int d;
   private int e;
   private int f;
   private int scaleFactor;

   public ScaledResolution(Minecraft minecraft) {
      this.c = minecraft.displayWidth;
      this.e = minecraft.displayHeight;
      this.scaleFactor = 1;
      boolean var4 = minecraft.isUnicode();
      int var5 = minecraft.gameSettings.guiScale;
      if (var5 == 0) {
         var5 = 1000;
      }

      while (this.scaleFactor < var5 && this.c / (this.scaleFactor + 1) >= 320 && this.e / (this.scaleFactor + 1) >= 240) {
         this.scaleFactor++;
      }

      if (var4 && this.scaleFactor % 2 != 0 && this.scaleFactor != 1) {
         this.scaleFactor--;
      }

      this.scaledWidthD = (double)this.c / this.scaleFactor;
      this.scaledHeightD = (double)this.e / this.scaleFactor;
      this.c = MathHelper.ceiling_double_int(this.scaledWidthD);
      this.e = MathHelper.ceiling_double_int(this.scaledHeightD);
      this.f = this.e;
      this.d = this.c;
   }

   public void Method235(float float1) {
      this.c = (int)(this.c * float1);
      this.e = (int)(this.e * float1);
   }

   public void Method236() {
      this.c = this.d;
      this.e = this.f;
   }

   public int Method237() {
      return this.c;
   }

   public int Method238() {
      return this.e;
   }

   public double getScaledWidth_double() {
      return this.scaledWidthD;
   }

   public double getScaledHeight_double() {
      return this.scaledHeightD;
   }

   public int getScaleFactor() {
      return this.scaleFactor;
   }
}
