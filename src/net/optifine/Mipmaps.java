package net.optifine;

import java.awt.Dimension;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.src.Config;
import net.optifine.util.TextureUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class Mipmaps {
   private final String iconName;
   private final int width;
   private final int height;
   private final int[] data;
   private final boolean direct;
   private final int[][] mipmapDatas;
   private IntBuffer[] mipmapBuffers;
   private final Dimension[] mipmapDimensions;

   public Mipmaps(String string, int integer2, int integer3, int[] arr, boolean boolean5) {
      this.iconName = string;
      this.width = integer2;
      this.height = integer3;
      this.data = arr;
      this.direct = boolean5;
      this.mipmapDimensions = makeMipmapDimensions(integer2, integer3, string);
      this.mipmapDatas = generateMipMapData(arr, integer2, integer3, this.mipmapDimensions);
      if (boolean5) {
         this.mipmapBuffers = makeMipmapBuffers(this.mipmapDimensions, this.mipmapDatas);
      }
   }

   public static Dimension[] makeMipmapDimensions(int integer1, int integer2, String string) {
      int var6 = TextureUtils.ceilPowerOfTwo(integer1);
      int var7 = TextureUtils.ceilPowerOfTwo(integer2);
      if (var6 == integer1 && var7 == integer2) {
         ArrayList<Dimension> var8 = new ArrayList<Dimension>();
         int var9 = var6;
         int var10 = var7;

         while (true) {
            var9 /= 2;
            var10 /= 2;
            if (var9 <= 0 && var10 <= 0) {
               return var8.toArray(new Dimension[0]);
            }

            if (var9 <= 0) {
               var9 = 1;
            }

            if (var10 <= 0) {
               var10 = 1;
            }

            int var10000 = var9 * var10 * 4;
            Dimension var12 = new Dimension(var9, var10);
            var8.add(var12);
         }
      } else {
         int var13 = integer2;
         int var14 = integer1;
         String var15 = string;
         Config.Method252("Mipmaps not possible (power of 2 dimensions needed), texture: " + var15 + ", dim: " + var14 + "x" + var13);
         return new Dimension[0];
      }
   }

   public static int[][] generateMipMapData(int[] data, int integer2, int integer3, Dimension[] mipmapDimensions) {
      int[] var7 = data;
      int var8 = integer2;
      boolean var9 = true;
      int[][] var10 = new int[mipmapDimensions.length][];

      for (int var11 = 0; var11 < mipmapDimensions.length; var11++) {
         Dimension var12 = mipmapDimensions[var11];
         int var13 = var12.width;
         int var14 = var12.height;
         int[] var15 = new int[var13 * var14];
         var10[var11] = var15;
         int var10000 = var11 + 1;
         if (var9) {
            for (int var17 = 0; var17 < var13; var17++) {
               for (int var18 = 0; var18 < var14; var18++) {
                  int var19 = var7[var17 * 2 + var18 * 2 * var8];
                  int var20 = var7[var17 * 2 + 1 + var18 * 2 * var8];
                  int var21 = var7[var17 * 2 + 1 + (var18 * 2 + 1) * var8];
                  int var22 = var7[var17 * 2 + (var18 * 2 + 1) * var8];
                  int var23 = alphaBlend(var19, var20, var21, var22);
                  var15[var17 + var18 * var13] = var23;
               }
            }
         }

         var7 = var15;
         var8 = var13;
         if (var13 <= 1 || var14 <= 1) {
            var9 = false;
         }
      }

      return var10;
   }

   public static int alphaBlend(int integer1, int integer2, int integer3, int integer4) {
      int var4 = alphaBlend(integer1, integer2);
      int var5 = alphaBlend(integer3, integer4);
      return alphaBlend(var4, var5);
   }

   private static int alphaBlend(int integer1, int integer2) {
      int var5 = (integer1 & 0xFF000000) >> 24 & 0xFF;
      int var6 = (integer2 & 0xFF000000) >> 24 & 0xFF;
      int var7 = (var5 + var6) / 2;
      if (var5 == 0 && var6 == 0) {
         var5 = 1;
         var6 = 1;
      } else {
         if (var5 == 0) {
            integer1 = integer2;
            var7 /= 2;
         }

         if (var6 == 0) {
            integer2 = integer1;
            var7 /= 2;
         }
      }

      int var8 = (integer1 >> 16 & 0xFF) * var5;
      int var9 = (integer1 >> 8 & 0xFF) * var5;
      int var10 = (integer1 & 0xFF) * var5;
      int var11 = (integer2 >> 16 & 0xFF) * var6;
      int var12 = (integer2 >> 8 & 0xFF) * var6;
      int var13 = (integer2 & 0xFF) * var6;
      int var14 = (var8 + var11) / (var5 + var6);
      int var15 = (var9 + var12) / (var5 + var6);
      int var16 = (var10 + var13) / (var5 + var6);
      return var7 << 24 | var14 << 16 | var15 << 8 | var16;
   }

   private int averageColor(int integer1, int integer2) {
      int var5 = (integer1 & 0xFF000000) >> 24 & 0xFF;
      int var6 = (integer2 & 0xFF000000) >> 24 & 0xFF;
      return (var5 + integer2 >> 1 << 24) + ((var5 & 16711422) + (var6 & 16711422) >> 1);
   }

   public static IntBuffer[] makeMipmapBuffers(Dimension[] mipmapDimensions, int[][] mipmapDatas) {
      if (mipmapDimensions == null) {
         return null;
      }

      IntBuffer[] var5 = new IntBuffer[mipmapDimensions.length];

      for (int var6 = 0; var6 < mipmapDimensions.length; var6++) {
         Dimension var7 = mipmapDimensions[var6];
         IntBuffer var9 = GLAllocation.createDirectIntBuffer(var7.width * var7.height);
         int[] var10 = mipmapDatas[var6];
         var9.clear();
         var9.put(var10);
         var9.clear();
         var5[var6] = var9;
      }

      return var5;
   }

   public static void allocateMipmapTextures(int integer1, int integer2, String string) {
      Dimension[] var6 = makeMipmapDimensions(integer1, integer2, string);

      for (int var7 = 0; var7 < var6.length; var7++) {
         Dimension var8 = var6[var7];
         int var9 = var8.width;
         int var10 = var8.height;
         int var11 = var7 + 1;
         GL11.glTexImage2D(GL11.GL_TEXTURE_2D, var11, GL11.GL_RGBA, var9, var10, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, (ByteBuffer)null);
      }
   }
}
