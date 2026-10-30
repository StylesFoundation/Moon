package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.src.Config;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class NaturalProperties {
   public int rotation = 1;
   public boolean flip = false;
   private final Map[] quadMaps = new Map[8];

   public NaturalProperties(String string) {
      if (string.equals("4")) {
         this.rotation = 4;
      } else if (string.equals("2")) {
         this.rotation = 2;
      } else if (string.equals("F")) {
         this.flip = true;
      } else if (string.equals("4F")) {
         this.rotation = 4;
         this.flip = true;
      } else if (string.equals("2F")) {
         this.rotation = 2;
         this.flip = true;
      } else {
         Config.Method252("NaturalTextures: Unknown type: " + string);
      }
   }

   public boolean isValid() {
      return this.rotation == 2 || this.rotation == 4 || this.flip;
   }

   public synchronized BakedQuad getQuad(BakedQuad bakedQuad, int integer, boolean boolean3) {
      int var7 = integer;
      if (boolean3) {
         var7 = integer | 4;
      }

      if (var7 > 0 && var7 < this.quadMaps.length) {
         Map var8 = this.quadMaps[var7];
         if (var8 == null) {
            var8 = new IdentityHashMap(1);
            this.quadMaps[var7] = var8;
         }

         BakedQuad var9 = (BakedQuad)var8.get(bakedQuad);
         if (var9 == null) {
            var9 = this.makeQuad(bakedQuad, integer, boolean3);
            var8.put(bakedQuad, var9);
         }

         return var9;
      } else {
         return bakedQuad;
      }
   }

   private BakedQuad makeQuad(BakedQuad bakedQuad, int integer, boolean boolean3) {
      int[] var7 = bakedQuad.getVertexData();
      int var8 = bakedQuad.getTintIndex();
      EnumFacing var9 = bakedQuad.getFace();
      TextureAtlasSprite var10 = bakedQuad.getSprite();
      if (!this.isFullSprite(bakedQuad)) {
         integer = 0;
      }

      var7 = this.transformVertexData(var7, integer, boolean3);
      return new BakedQuad(var7, var8, var9, var10);
   }

   private int[] transformVertexData(int[] arr, int integer, boolean boolean3) {
      int[] var7 = (int[])arr.clone();
      int var8 = 4 - integer;
      if (boolean3) {
         var8 += 3;
      }

      var8 %= 4;
      int var9 = var7.length / 4;

      for (int var10 = 0; var10 < 4; var10++) {
         int var11 = var10 * var9;
         int var12 = var8 * var9;
         var7[var12 + 4] = arr[var11 + 4];
         var7[var12 + 4 + 1] = arr[var11 + 4 + 1];
         if (boolean3) {
            if (--var8 < 0) {
               var8 = 3;
            }
         } else if (++var8 > 3) {
            var8 = 0;
         }
      }

      return var7;
   }

   private boolean isFullSprite(BakedQuad bakedQuad) {
      TextureAtlasSprite var5 = bakedQuad.getSprite();
      float var6 = var5.getMinU();
      float var7 = var5.getMaxU();
      float var9 = (var7 - var6) / 256.0F;
      float var10 = var5.getMinV();
      float var11 = var5.getMaxV();
      float var13 = (var11 - var10) / 256.0F;
      int[] var14 = bakedQuad.getVertexData();
      int var15 = var14.length / 4;

      for (int var16 = 0; var16 < 4; var16++) {
         int var17 = var16 * var15;
         float var18 = Float.intBitsToFloat(var14[var17 + 4]);
         float var19 = Float.intBitsToFloat(var14[var17 + 4 + 1]);
         if (!this.equalsDelta(var18, var6, var9) && !this.equalsDelta(var18, var7, var9)) {
            return false;
         }

         if (!this.equalsDelta(var19, var10, var13) && !this.equalsDelta(var19, var11, var13)) {
            return false;
         }
      }

      return true;
   }

   private boolean equalsDelta(float float1, float float2, float float3) {
      float var7 = MathHelper.abs(float1 - float2);
      return var7 < float3;
   }
}
