package net.optifine.shaders;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.LayeredTexture;
import net.minecraft.client.renderer.texture.Stitcher;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class ShadersTex {
   public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
   public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
   public static int[] intArray = new int[1048576];
   public static Map<Integer, MultiTexID> multiTexMap = new HashMap<>();

   public static IntBuffer getIntBuffer(int integer) {
      if (intBuffer.capacity() < integer) {
         byteBuffer = BufferUtils.createByteBuffer(roundUpPOT(integer) * 4);
         intBuffer = byteBuffer.asIntBuffer();
      }

      return intBuffer;
   }

   public static int[] getIntArray(int integer) {
      if (intArray == null) {
         intArray = new int[1048576];
      }

      if (intArray.length < integer) {
         intArray = new int[roundUpPOT(integer)];
      }

      return intArray;
   }

   public static int roundUpPOT(int integer) {
      int var3 = integer - 1;
      var3 |= var3 >> 1;
      var3 |= var3 >> 2;
      var3 |= var3 >> 4;
      var3 |= var3 >> 8;
      return (var3 | var3 >> 16) + 1;
   }

   public static int Method1025(int integer) {
      int var4 = 0;
      if ((integer & -65536) != 0) {
         var4 += 16;
         integer >>= 16;
      }

      if ((integer & 0xFF00) != 0) {
         var4 += 8;
         integer >>= 8;
      }

      if ((integer & 240) != 0) {
         var4 += 4;
         integer >>= 4;
      }

      if ((integer & 6) != 0) {
         var4 += 2;
         integer >>= 2;
      }

      if ((integer & 2) != 0) {
         var4++;
      }

      return var4;
   }

   public static IntBuffer fillIntBuffer(int integer1, int integer2) {
      getIntArray(integer1);
      getIntBuffer(integer1);
      Arrays.fill(intArray, 0, integer1, integer2);
      intBuffer.put(intArray, 0, integer1);
      return intBuffer;
   }

   public static int[] createAIntImage(int integer) {
      int[] var3 = new int[integer * 3];
      Arrays.fill(var3, 0, integer, 0);
      Arrays.fill(var3, integer, integer * 2, -8421377);
      Arrays.fill(var3, integer * 2, integer * 3, 0);
      return var3;
   }

   public static int[] createAIntImage(int integer1, int integer2) {
      int[] var4 = new int[integer1 * 3];
      Arrays.fill(var4, 0, integer1, integer2);
      Arrays.fill(var4, integer1, integer1 * 2, -8421377);
      Arrays.fill(var4, integer1 * 2, integer1 * 3, 0);
      return var4;
   }

   public static MultiTexID getMultiTexID(AbstractTexture abstractTexture) {
      MultiTexID var4 = abstractTexture.multiTex;
      if (var4 == null) {
         int var5 = abstractTexture.getGlTextureId();
         var4 = multiTexMap.get(var5);
         if (var4 == null) {
            var4 = new MultiTexID(var5, GL11.glGenTextures(), GL11.glGenTextures());
            multiTexMap.put(var5, var4);
         }

         abstractTexture.multiTex = var4;
      }

      return var4;
   }

   public static void deleteTextures(AbstractTexture abstractTexture, int integer) {
      MultiTexID var5 = abstractTexture.multiTex;
      if (var5 != null) {
         abstractTexture.multiTex = null;
         multiTexMap.remove(var5.base);
         GlStateManager.deleteTexture(var5.norm);
         GlStateManager.deleteTexture(var5.spec);
         if (var5.base != integer) {
            SMCLog.warning("Error : MultiTexID.base mismatch: " + var5.base + ", texid: " + integer);
            GlStateManager.deleteTexture(var5.base);
         }
      }
   }

   public static void bindNSTextures(int integer1, int integer2) {
      if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
         GlStateManager.setActiveTexture(33986);
         GlStateManager.bindTexture(integer1);
         GlStateManager.setActiveTexture(33987);
         GlStateManager.bindTexture(integer2);
         GlStateManager.setActiveTexture(33984);
      }
   }

   public static void bindNSTextures(MultiTexID multiTexID) {
      bindNSTextures(multiTexID.norm, multiTexID.spec);
   }

   public static void bindTextures(int integer1, int integer2, int integer3) {
      if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
         GlStateManager.setActiveTexture(33986);
         GlStateManager.bindTexture(integer2);
         GlStateManager.setActiveTexture(33987);
         GlStateManager.bindTexture(integer3);
         GlStateManager.setActiveTexture(33984);
      }

      GlStateManager.bindTexture(integer1);
   }

   public static void bindTextures(MultiTexID multiTexID) {
      if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
         if (Shaders.configNormalMap) {
            GlStateManager.setActiveTexture(33986);
            GlStateManager.bindTexture(multiTexID.norm);
         }

         if (Shaders.configSpecularMap) {
            GlStateManager.setActiveTexture(33987);
            GlStateManager.bindTexture(multiTexID.spec);
         }

         GlStateManager.setActiveTexture(33984);
      }

      GlStateManager.bindTexture(multiTexID.base);
   }

   public static void bindTexture(ITextureObject iTextureObject) {
      iTextureObject.getGlTextureId();
      bindTextures(iTextureObject.getMultiTexID());
      if (GlStateManager.getActiveTextureUnit() == 33984) {
         int var5 = Shaders.atlasSizeX;
         int var6 = Shaders.atlasSizeY;
         if (iTextureObject instanceof TextureMap) {
            Shaders.atlasSizeX = ((TextureMap)iTextureObject).atlasWidth;
            Shaders.atlasSizeY = ((TextureMap)iTextureObject).atlasHeight;
         } else {
            Shaders.atlasSizeX = 0;
            Shaders.atlasSizeY = 0;
         }

         if (Shaders.atlasSizeX != var5 || Shaders.atlasSizeY != var6) {
            Shaders.uniform_atlasSize.setValue(Shaders.atlasSizeX, Shaders.atlasSizeY);
         }
      }
   }

   public static void bindTextures(int integer) {
      bindTextures(multiTexMap.get(integer));
   }

   public static void initDynamicTexture(int integer1, int integer2, int integer3, DynamicTexture dynamicTexture) {
      MultiTexID var6 = dynamicTexture.getMultiTexID();
      int[] var7 = dynamicTexture.getTextureData();
      int var8 = integer2 * integer3;
      Arrays.fill(var7, var8, var8 * 2, -8421377);
      Arrays.fill(var7, var8 * 2, var8 * 3, 0);
      TextureUtil.allocateTexture(var6.base, integer2, integer3);
      TextureUtil.setTextureBlurMipmap(false, false);
      TextureUtil.setTextureClamped(false);
      TextureUtil.allocateTexture(var6.norm, integer2, integer3);
      TextureUtil.setTextureBlurMipmap(false, false);
      TextureUtil.setTextureClamped(false);
      TextureUtil.allocateTexture(var6.spec, integer2, integer3);
      TextureUtil.setTextureBlurMipmap(false, false);
      TextureUtil.setTextureClamped(false);
      GlStateManager.bindTexture(var6.base);
   }

   public static void updateDynamicTexture(int integer1, int[] arr, int integer3, int integer4, DynamicTexture dynamicTexture) {
      MultiTexID var5 = dynamicTexture.getMultiTexID();
      GlStateManager.bindTexture(var5.base);
      updateDynTexSubImage1(arr, integer3, integer4, 0, 0, 0);
      GlStateManager.bindTexture(var5.norm);
      updateDynTexSubImage1(arr, integer3, integer4, 0, 0, 1);
      GlStateManager.bindTexture(var5.spec);
      updateDynTexSubImage1(arr, integer3, integer4, 0, 0, 2);
      GlStateManager.bindTexture(var5.base);
   }

   public static void updateDynTexSubImage1(int[] arr, int integer2, int integer3, int integer4, int integer5, int integer6) {
      int var9 = integer2 * integer3;
      IntBuffer var10 = getIntBuffer(var9);
      var10.clear();
      int var11 = integer6 * var9;
      if (arr.length >= var11 + var9) {
         var10.put(arr, var11, var9).position(0).limit(var9);
         GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, integer4, integer5, integer2, integer3, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var10);
         var10.clear();
      }
   }

   public static ITextureObject createDefaultTexture() {
      DynamicTexture var0 = new DynamicTexture(1, 1);
      var0.getTextureData()[0] = -1;
      var0.updateDynamicTexture();
      return var0;
   }

   public static void allocateTextureMap(int integer1, int integer2, int integer3, int integer4, Stitcher stitcher, TextureMap textureMap) {
      int var10 = integer4;
      int var11 = integer3;
      int var12 = integer2;
      SMCLog.info("allocateTextureMap " + var12 + " " + var11 + " " + var10 + " ");
      textureMap.atlasWidth = integer3;
      textureMap.atlasHeight = integer4;
      MultiTexID var9 = getMultiTexID(textureMap);
      TextureUtil.Method460(var9.base, integer2, integer3, integer4);
      if (Shaders.configNormalMap) {
         TextureUtil.Method460(var9.norm, integer2, integer3, integer4);
      }

      if (Shaders.configSpecularMap) {
         TextureUtil.Method460(var9.spec, integer2, integer3, integer4);
      }

      GlStateManager.bindTexture(integer1);
   }

   public static void uploadTexSubForLoadAtlas(
      TextureMap textureMap, String string, int[][] arr, int integer4, int integer5, int integer6, int integer7, boolean boolean8, boolean boolean9
   ) {
      MultiTexID var12 = textureMap.multiTex;
      TextureUtil.uploadTextureMipmap(arr, integer4, integer5, integer6, integer7, boolean8, boolean9);
      if (Shaders.configNormalMap) {
         int[][] var14 = readImageAndMipmaps(textureMap, string + "_n", integer4, integer5, arr.length, false, -8421377);
         GlStateManager.bindTexture(var12.norm);
         TextureUtil.uploadTextureMipmap(var14, integer4, integer5, integer6, integer7, boolean8, boolean9);
      }

      if (Shaders.configSpecularMap) {
         int[][] var15 = readImageAndMipmaps(textureMap, string + "_s", integer4, integer5, arr.length, false, 0);
         GlStateManager.bindTexture(var12.spec);
         TextureUtil.uploadTextureMipmap(var15, integer4, integer5, integer6, integer7, boolean8, boolean9);
      }

      GlStateManager.bindTexture(var12.base);
   }

   public static int[][] readImageAndMipmaps(TextureMap textureMap, String string, int integer3, int integer4, int integer5, boolean boolean6, int integer7) {
      MultiTexID var10 = textureMap.multiTex;
      int[][] var11 = new int[integer5][];
      int[] var12;
      var11[0] = var12 = new int[integer3 * integer4];
      boolean var13 = false;
      BufferedImage var14 = readImage(textureMap.completeResourceLocation(new ResourceLocation(string)));
      if (var14 != null) {
         int var15 = var14.getWidth();
         var14.getHeight();
         if (var15 + (boolean6 ? 16 : 0) == integer3) {
            var13 = true;
            var14.getRGB(0, 0, var15, var15, var12, 0, var15);
         }
      }

      if (!var13) {
         Arrays.fill(var12, integer7);
      }

      GlStateManager.bindTexture(var10.spec);
      return genMipmapsSimple(var11.length - 1, integer3, var11);
   }

   public static BufferedImage readImage(ResourceLocation resourceLocation) {
      try {
         if (!Config.hasResource(resourceLocation)) {
            return null;
         }

         InputStream var4 = Config.getResourceStream(resourceLocation);
         if (var4 == null) {
            return null;
         }

         BufferedImage var5 = ImageIO.read(var4);
         var4.close();
         return var5;
      } catch (IOException var6) {
         return null;
      }
   }

   public static int[][] genMipmapsSimple(int integer1, int integer2, int[][] arr) {
      for (int var6 = 1; var6 <= integer1; var6++) {
         if (arr[var6] == null) {
            int var7 = integer2 >> var6;
            int var8 = var7 * 2;
            int[] var9 = arr[var6 - 1];
            int[] var10 = arr[var6] = new int[var7 * var7];

            for (int var11 = 0; var11 < var7; var11++) {
               for (int var12 = 0; var12 < var7; var12++) {
                  int var13 = var11 * 2 * var8 + var12 * 2;
                  var10[var11 * var7 + var12] = blend4Simple(var9[var13], var9[var13 + 1], var9[var13 + var8], var9[var13 + var8 + 1]);
               }
            }
         }
      }

      return arr;
   }

   public static void uploadTexSub1(int[][] arr, int integer2, int integer3, int integer4, int integer5, int integer6) {
      IntBuffer var10 = getIntBuffer(integer2 * integer3);
      int var11 = arr.length;
      int var12 = 0;
      int var13 = integer2;
      int var14 = integer3;
      int var15 = integer4;
      int var16 = integer5;

      while (var13 > 0 && var14 > 0 && var12 < var11) {
         int var17 = var13 * var14;
         int[] var18 = arr[var12];
         var10.clear();
         if (var18.length >= var17 * (integer6 + 1)) {
            var10.put(var18, var17 * integer6, var17).position(0).limit(var17);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, var12, var15, var16, var13, var14, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var10);
         }

         var13 >>= 1;
         var14 >>= 1;
         var15 >>= 1;
         var16 >>= 1;
         var12++;
      }

      var10.clear();
   }

   public static int Method1026(int integer1, int integer2, int integer3, int integer4) {
      int var7 = integer1 >>> 24 & 0xFF;
      int var8 = integer2 >>> 24 & 0xFF;
      int var9 = integer3 >>> 24 & 0xFF;
      int var10 = integer4 >>> 24 & 0xFF;
      int var11 = var7 + var8 + var9 + var10;
      int var12 = (var11 + 2) / 4;
      int var13;
      if (var11 != 0) {
         var13 = var11;
      } else {
         var13 = 4;
         var7 = 1;
         var8 = 1;
         var9 = 1;
         var10 = 1;
      }

      int var14 = (var13 + 1) / 2;
      return var12 << 24
         | ((integer1 >>> 16 & 0xFF) * var7 + (integer2 >>> 16 & 0xFF) * var8 + (integer3 >>> 16 & 0xFF) * var9 + (integer4 >>> 16 & 0xFF) * var10 + var14)
               / var13
            << 16
         | ((integer1 >>> 8 & 0xFF) * var7 + (integer2 >>> 8 & 0xFF) * var8 + (integer3 >>> 8 & 0xFF) * var9 + (integer4 >>> 8 & 0xFF) * var10 + var14) / var13
            << 8
         | ((integer1 & 0xFF) * var7 + (integer2 & 0xFF) * var8 + (integer3 & 0xFF) * var9 + (integer4 & 0xFF) * var10 + var14) / var13;
   }

   public static int blend4Simple(int integer1, int integer2, int integer3, int integer4) {
      return ((integer1 >>> 24 & 0xFF) + (integer2 >>> 24 & 0xFF) + (integer3 >>> 24 & 0xFF) + (integer4 >>> 24 & 0xFF) + 2) / 4 << 24
         | ((integer1 >>> 16 & 0xFF) + (integer2 >>> 16 & 0xFF) + (integer3 >>> 16 & 0xFF) + (integer4 >>> 16 & 0xFF) + 2) / 4 << 16
         | ((integer1 >>> 8 & 0xFF) + (integer2 >>> 8 & 0xFF) + (integer3 >>> 8 & 0xFF) + (integer4 >>> 8 & 0xFF) + 2) / 4 << 8
         | ((integer1 & 0xFF) + (integer2 & 0xFF) + (integer3 & 0xFF) + (integer4 & 0xFF) + 2) / 4;
   }

   public static void genMipmapAlpha(int[] arr, int integer2, int integer3, int integer4) {
      Math.min(integer3, integer4);
      int var7 = integer2;
      int var8 = integer3;
      int var9 = integer4;
      int var10 = 0;
      int var11 = 0;
      int var13 = 0;

      while (var8 > 1 && var9 > 1) {
         var10 = var7 + var8 * var9;
         var11 = var8 / 2;
         int var12 = var9 / 2;

         for (int var14 = 0; var14 < var12; var14++) {
            int var15 = var10 + var14 * var11;
            int var16 = var7 + var14 * 2 * var8;

            for (int var17 = 0; var17 < var11; var17++) {
               arr[var15 + var17] = Method1026(
                  arr[var16 + var17 * 2], arr[var16 + var17 * 2 + 1], arr[var16 + var8 + var17 * 2], arr[var16 + var8 + var17 * 2 + 1]
               );
            }
         }

         var13++;
         var8 = var11;
         var9 = var12;
         var7 = var10;
      }

      while (var13 > 0) {
         var8 = integer3 >> --var13;
         var9 = integer4 >> var13;
         var7 = var10 - var8 * var9;
         int var21 = var7;

         for (int var22 = 0; var22 < var9; var22++) {
            for (int var23 = 0; var23 < var8; var23++) {
               if (arr[var21] == 0) {
                  arr[var21] = arr[var10 + var22 / 2 * var11 + var23 / 2] & 16777215;
               }

               var21++;
            }
         }

         var10 = var7;
         var11 = var8;
      }
   }

   public static void genMipmapSimple(int[] arr, int integer2, int integer3, int integer4) {
      Math.min(integer3, integer4);
      int var7 = integer2;
      int var8 = integer3;
      int var9 = integer4;
      int var10 = 0;
      int var11 = 0;
      int var13 = 0;

      while (var8 > 1 && var9 > 1) {
         var10 = var7 + var8 * var9;
         var11 = var8 / 2;
         int var12 = var9 / 2;

         for (int var14 = 0; var14 < var12; var14++) {
            int var15 = var10 + var14 * var11;
            int var16 = var7 + var14 * 2 * var8;

            for (int var17 = 0; var17 < var11; var17++) {
               arr[var15 + var17] = blend4Simple(
                  arr[var16 + var17 * 2], arr[var16 + var17 * 2 + 1], arr[var16 + var8 + var17 * 2], arr[var16 + var8 + var17 * 2 + 1]
               );
            }
         }

         var13++;
         var8 = var11;
         var9 = var12;
         var7 = var10;
      }

      while (var13 > 0) {
         var8 = integer3 >> --var13;
         var9 = integer4 >> var13;
         var7 = var10 - var8 * var9;
         int var21 = var7;

         for (int var22 = 0; var22 < var9; var22++) {
            for (int var23 = 0; var23 < var8; var23++) {
               if (arr[var21] == 0) {
                  arr[var21] = arr[var10 + var22 / 2 * var11 + var23 / 2] & 16777215;
               }

               var21++;
            }
         }

         var10 = var7;
         var11 = var8;
      }
   }

   public static boolean isSemiTransparent(int[] arr, int integer2, int integer3) {
      int var6 = integer2 * integer3;
      if (arr[0] >>> 24 == 255 && arr[var6 - 1] == 0) {
         return true;
      }

      for (int var7 = 0; var7 < var6; var7++) {
         int var8 = arr[var7] >>> 24;
         if (var8 != 0 && var8 != 255) {
            return true;
         }
      }

      return false;
   }

   public static void Method1027(int[] arr, int integer2, int integer3, int integer4, int integer5) {
      int var8 = 0;
      int var9 = integer2;
      int var10 = integer3;
      int var11 = integer4;

      for (int var12 = integer5; var9 > 0 && var10 > 0; var12 /= 2) {
         GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, var8, var11, var12, 0, 0, var9, var10);
         var8++;
         var9 /= 2;
         var10 /= 2;
         var11 /= 2;
      }
   }

   public static void setupTexture(MultiTexID multiTexID, int[] arr, int integer3, int integer4, boolean boolean5, boolean boolean6) {
      int var9 = boolean5 ? 9729 : 9728;
      int var10 = boolean6 ? 33071 : 10497;
      int var11 = integer3 * integer4;
      IntBuffer var12 = getIntBuffer(var11);
      var12.clear();
      var12.put(arr, 0, var11).position(0).limit(var11);
      GlStateManager.bindTexture(multiTexID.base);
      GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, integer3, integer4, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var12);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var9);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var9);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, var10);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, var10);
      var12.put(arr, var11, var11).position(0).limit(var11);
      GlStateManager.bindTexture(multiTexID.norm);
      GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, integer3, integer4, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var12);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var9);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var9);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, var10);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, var10);
      var12.put(arr, var11 * 2, var11).position(0).limit(var11);
      GlStateManager.bindTexture(multiTexID.spec);
      GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, integer3, integer4, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var12);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, var9);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, var9);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, var10);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, var10);
      GlStateManager.bindTexture(multiTexID.base);
   }

   public static void updateSubImage(
      MultiTexID multiTexID, int[] arr, int integer3, int integer4, int integer5, int integer6, boolean boolean7, boolean boolean8
   ) {
      int var11 = integer3 * integer4;
      IntBuffer var12 = getIntBuffer(var11);
      var12.clear();
      var12.put(arr, 0, var11);
      var12.position(0).limit(var11);
      GlStateManager.bindTexture(multiTexID.base);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
      GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, integer5, integer6, integer3, integer4, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var12);
      if (arr.length == var11 * 3) {
         var12.clear();
         var12.put(arr, var11, var11).position(0);
         var12.position(0).limit(var11);
      }

      GlStateManager.bindTexture(multiTexID.norm);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
      GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, integer5, integer6, integer3, integer4, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var12);
      if (arr.length == var11 * 3) {
         var12.clear();
         var12.put(arr, var11 * 2, var11);
         var12.position(0).limit(var11);
      }

      GlStateManager.bindTexture(multiTexID.spec);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
      GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
      GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, integer5, integer6, integer3, integer4, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, var12);
      GlStateManager.setActiveTexture(33984);
   }

   public static ResourceLocation getNSMapLocation(ResourceLocation resourceLocation, String string) {
      if (resourceLocation == null) {
         return null;
      }

      String var7 = resourceLocation.getResourcePath().split(".png")[0];
      return new ResourceLocation(resourceLocation.getResourceDomain(), var7 + "_" + string + ".png");
   }

   public static void loadNSMap(IResourceManager iResourceManager, ResourceLocation resourceLocation, int integer3, int integer4, int[] arr) {
      if (Shaders.configNormalMap) {
         loadNSMap1(iResourceManager, getNSMapLocation(resourceLocation, "n"), integer3, integer4, arr, integer3 * integer4, -8421377);
      }

      if (Shaders.configSpecularMap) {
         loadNSMap1(iResourceManager, getNSMapLocation(resourceLocation, "s"), integer3, integer4, arr, integer3 * integer4 * 2, 0);
      }
   }

   private static void loadNSMap1(
      IResourceManager iResourceManager, ResourceLocation resourceLocation, int integer3, int integer4, int[] arr, int integer6, int integer7
   ) {
      if (!loadNSMapFile(iResourceManager, resourceLocation, integer3, integer4, arr, integer6)) {
         Arrays.fill(arr, integer6, integer6 + integer3 * integer4, integer7);
      }
   }

   private static boolean loadNSMapFile(
      IResourceManager iResourceManager, ResourceLocation resourceLocation, int integer3, int integer4, int[] arr, int integer6
   ) {
      if (resourceLocation == null) {
         return false;
      }

      try {
         BufferedImage var10 = ImageIO.read(iResourceManager.getResource(resourceLocation).getInputStream());
         if (var10 == null) {
            return false;
         } else if (var10.getWidth() == integer3 && var10.getHeight() == integer4) {
            var10.getRGB(0, 0, integer3, integer4, arr, integer6, integer3);
            return true;
         } else {
            return false;
         }
      } catch (IOException var11) {
         return false;
      }
   }

   public static int loadSimpleTexture(
      int integer,
      BufferedImage bufferedImage,
      boolean boolean3,
      boolean boolean4,
      IResourceManager iResourceManager,
      ResourceLocation resourceLocation,
      MultiTexID multiTexID
   ) {
      int var7 = bufferedImage.getWidth();
      int var8 = bufferedImage.getHeight();
      int[] var10 = getIntArray(var7 * var8 * 3);
      bufferedImage.getRGB(0, 0, var7, var8, var10, 0, var7);
      loadNSMap(iResourceManager, resourceLocation, var7, var8, var10);
      setupTexture(multiTexID, var10, var7, var8, boolean3, boolean4);
      return integer;
   }

   public static void mergeImage(int[] arr, int integer2, int integer3, int integer4) {
   }

   public static int blendColor(int integer1, int integer2, int integer3) {
      int var5 = 255 - integer3;
      return ((integer1 >>> 24 & 0xFF) * integer3 + (integer2 >>> 24 & 0xFF) * var5) / 255 << 24
         | ((integer1 >>> 16 & 0xFF) * integer3 + (integer2 >>> 16 & 0xFF) * var5) / 255 << 16
         | ((integer1 >>> 8 & 0xFF) * integer3 + (integer2 >>> 8 & 0xFF) * var5) / 255 << 8
         | ((integer1 & 0xFF) * integer3 + (integer2 & 0xFF) * var5) / 255;
   }

   public static void loadLayeredTexture(LayeredTexture layeredTexture, IResourceManager iResourceManager, List<String> list) {
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;
      int[] var9 = null;

      for (String var11 : list) {
         if (var11 != null) {
            try {
               ResourceLocation var12 = new ResourceLocation(var11);
               BufferedImage var14 = ImageIO.read(iResourceManager.getResource(var12).getInputStream());
               if (var8 == 0) {
                  var6 = var14.getWidth();
                  var7 = var14.getHeight();
                  var8 = var6 * var7;
                  var9 = createAIntImage(var8, 0);
               }

               int[] var15 = getIntArray(var8 * 3);
               var14.getRGB(0, 0, var6, var7, var15, 0, var6);
               loadNSMap(iResourceManager, var12, var6, var7, var15);

               for (int var16 = 0; var16 < var8; var16++) {
                  int var17 = var15[var16] >>> 24 & 0xFF;
                  var9[var16] = blendColor(var15[var16], var9[var16], var17);
                  var9[var8 + var16] = blendColor(var15[var8 + var16], var9[var8 + var16], var17);
                  var9[var8 * 2 + var16] = blendColor(var15[var8 * 2 + var16], var9[var8 * 2 + var16], var17);
               }
            } catch (IOException var18) {
               var18.printStackTrace();
            }
         }
      }

      setupTexture(layeredTexture.getMultiTexID(), var9, var6, var7, false, false);
   }

   public static void updateTextureMinMagFilter() {
      TextureManager var3 = Minecraft.getMinecraft().getTextureManager();
      ITextureObject var4 = var3.getTexture(TextureMap.locationBlocksTexture);
      if (var4 != null) {
         MultiTexID var5 = var4.getMultiTexID();
         GlStateManager.bindTexture(var5.base);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
         GlStateManager.bindTexture(var5.norm);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
         GlStateManager.bindTexture(var5.spec);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
         GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
         GlStateManager.bindTexture(0);
      }
   }

   public static int[][] getFrameTexData(int[][] arr, int integer2, int integer3, int integer4) {
      int var7 = arr.length;
      int[][] var8 = new int[var7][];

      for (int var9 = 0; var9 < var7; var9++) {
         int[] var10 = arr[var9];
         if (var10 != null) {
            int var11 = (integer2 >> var9) * (integer3 >> var9);
            int[] var12 = new int[var11 * 3];
            var8[var9] = var12;
            int var13 = var10.length / 3;
            int var14 = var11 * integer4;
            System.arraycopy(var10, var14, var12, 0, var11);
            var14 += var13;
            int var15 = 0 + var11;
            System.arraycopy(var10, var14, var12, var15, var11);
            var14 += var13;
            var15 += var11;
            System.arraycopy(var10, var14, var12, var15, var11);
         }
      }

      return var8;
   }

   public static int[][] prepareAF(TextureAtlasSprite textureAtlasSprite, int[][] arr, int integer3, int integer4) {
      return arr;
   }

   public static void fixTransparentColor(TextureAtlasSprite textureAtlasSprite, int[] arr) {
   }
}
