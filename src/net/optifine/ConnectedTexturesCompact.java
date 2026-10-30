package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.optifine.render.RenderEnv;

public class ConnectedTexturesCompact {
   public static BakedQuad[] getConnectedTextureCtmCompact(
      int integer1, ConnectedProperties connectedProperties, int integer3, BakedQuad bakedQuad, RenderEnv renderEnv
   ) {
      if (connectedProperties.ctmTileIndexes != null && integer1 >= 0 && integer1 < connectedProperties.ctmTileIndexes.length) {
         int var8 = connectedProperties.ctmTileIndexes[integer1];
         if (var8 >= 0 && var8 <= connectedProperties.tileIcons.length) {
            return getQuadsCompact(var8, connectedProperties.tileIcons, bakedQuad, renderEnv);
         }
      }

      switch (integer1) {
         case 1:
            return getQuadsCompactH(0, 3, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 2:
            return getQuadsCompact(3, connectedProperties.tileIcons, bakedQuad, renderEnv);
         case 3:
            return getQuadsCompactH(3, 0, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 4:
            return getQuadsCompact4(0, 3, 2, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 5:
            return getQuadsCompact4(3, 0, 4, 2, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 6:
            return getQuadsCompact4(2, 4, 2, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 7:
            return getQuadsCompact4(3, 3, 4, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 8:
            return getQuadsCompact4(4, 1, 4, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 9:
            return getQuadsCompact4(4, 4, 4, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 10:
            return getQuadsCompact4(1, 4, 1, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 11:
            return getQuadsCompact4(1, 1, 4, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 12:
            return getQuadsCompactV(0, 2, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 13:
            return getQuadsCompact4(0, 3, 2, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 14:
            return getQuadsCompactV(3, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 15:
            return getQuadsCompact4(3, 0, 1, 2, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 16:
            return getQuadsCompact4(2, 4, 0, 3, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 17:
            return getQuadsCompact4(4, 2, 3, 0, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 18:
            return getQuadsCompact4(4, 4, 3, 3, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 19:
            return getQuadsCompact4(4, 2, 4, 2, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 20:
            return getQuadsCompact4(1, 4, 4, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 21:
            return getQuadsCompact4(4, 4, 1, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 22:
            return getQuadsCompact4(4, 4, 1, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 23:
            return getQuadsCompact4(4, 1, 4, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 24:
            return getQuadsCompact(2, connectedProperties.tileIcons, bakedQuad, renderEnv);
         case 25:
            return getQuadsCompactH(2, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 26:
            return getQuadsCompact(1, connectedProperties.tileIcons, bakedQuad, renderEnv);
         case 27:
            return getQuadsCompactH(1, 2, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 28:
            return getQuadsCompact4(2, 4, 2, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 29:
            return getQuadsCompact4(3, 3, 1, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 30:
            return getQuadsCompact4(2, 1, 2, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 31:
            return getQuadsCompact4(3, 3, 4, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 32:
            return getQuadsCompact4(1, 1, 1, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 33:
            return getQuadsCompact4(1, 1, 4, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 34:
            return getQuadsCompact4(4, 1, 1, 4, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 35:
            return getQuadsCompact4(1, 4, 4, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 36:
            return getQuadsCompactV(2, 0, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 37:
            return getQuadsCompact4(2, 1, 0, 3, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 38:
            return getQuadsCompactV(1, 3, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 39:
            return getQuadsCompact4(1, 2, 3, 0, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 40:
            return getQuadsCompact4(4, 1, 3, 3, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 41:
            return getQuadsCompact4(1, 2, 4, 2, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 42:
            return getQuadsCompact4(1, 4, 3, 3, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 43:
            return getQuadsCompact4(4, 2, 1, 2, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 44:
            return getQuadsCompact4(1, 4, 1, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 45:
            return getQuadsCompact4(4, 1, 1, 1, connectedProperties.tileIcons, integer3, bakedQuad, renderEnv);
         case 46:
            return getQuadsCompact(4, connectedProperties.tileIcons, bakedQuad, renderEnv);
         default:
            return getQuadsCompact(0, connectedProperties.tileIcons, bakedQuad, renderEnv);
      }
   }

   private static BakedQuad[] getQuadsCompactH(int integer1, int integer2, TextureAtlasSprite[] arr, int integer4, BakedQuad bakedQuad, RenderEnv renderEnv) {
      return getQuadsCompact(ConnectedTexturesCompact.Dir.LEFT, integer1, ConnectedTexturesCompact.Dir.RIGHT, integer2, arr, integer4, bakedQuad, renderEnv);
   }

   private static BakedQuad[] getQuadsCompactV(int integer1, int integer2, TextureAtlasSprite[] arr, int integer4, BakedQuad bakedQuad, RenderEnv renderEnv) {
      return getQuadsCompact(ConnectedTexturesCompact.Dir.UP, integer1, ConnectedTexturesCompact.Dir.DOWN, integer2, arr, integer4, bakedQuad, renderEnv);
   }

   private static BakedQuad[] getQuadsCompact4(
      int integer1, int integer2, int integer3, int integer4, TextureAtlasSprite[] arr, int integer6, BakedQuad bakedQuad, RenderEnv renderEnv
   ) {
      return integer1 == integer2
         ? (
            integer3 == integer4
               ? getQuadsCompact(ConnectedTexturesCompact.Dir.UP, integer1, ConnectedTexturesCompact.Dir.DOWN, integer3, arr, integer6, bakedQuad, renderEnv)
               : getQuadsCompact(
                  ConnectedTexturesCompact.Dir.UP,
                  integer1,
                  ConnectedTexturesCompact.Dir.DOWN_LEFT,
                  integer3,
                  ConnectedTexturesCompact.Dir.DOWN_RIGHT,
                  integer4,
                  arr,
                  integer6,
                  bakedQuad,
                  renderEnv
               )
         )
         : (
            integer3 == integer4
               ? getQuadsCompact(
                  ConnectedTexturesCompact.Dir.UP_LEFT,
                  integer1,
                  ConnectedTexturesCompact.Dir.UP_RIGHT,
                  integer2,
                  ConnectedTexturesCompact.Dir.DOWN,
                  integer3,
                  arr,
                  integer6,
                  bakedQuad,
                  renderEnv
               )
               : (
                  integer1 == integer3
                     ? (
                        integer2 == integer4
                           ? getQuadsCompact(
                              ConnectedTexturesCompact.Dir.LEFT, integer1, ConnectedTexturesCompact.Dir.RIGHT, integer2, arr, integer6, bakedQuad, renderEnv
                           )
                           : getQuadsCompact(
                              ConnectedTexturesCompact.Dir.LEFT,
                              integer1,
                              ConnectedTexturesCompact.Dir.UP_RIGHT,
                              integer2,
                              ConnectedTexturesCompact.Dir.DOWN_RIGHT,
                              integer4,
                              arr,
                              integer6,
                              bakedQuad,
                              renderEnv
                           )
                     )
                     : (
                        integer2 == integer4
                           ? getQuadsCompact(
                              ConnectedTexturesCompact.Dir.UP_LEFT,
                              integer1,
                              ConnectedTexturesCompact.Dir.DOWN_LEFT,
                              integer3,
                              ConnectedTexturesCompact.Dir.RIGHT,
                              integer2,
                              arr,
                              integer6,
                              bakedQuad,
                              renderEnv
                           )
                           : getQuadsCompact(
                              ConnectedTexturesCompact.Dir.UP_LEFT,
                              integer1,
                              ConnectedTexturesCompact.Dir.UP_RIGHT,
                              integer2,
                              ConnectedTexturesCompact.Dir.DOWN_LEFT,
                              integer3,
                              ConnectedTexturesCompact.Dir.DOWN_RIGHT,
                              integer4,
                              arr,
                              integer6,
                              bakedQuad,
                              renderEnv
                           )
                     )
               )
         );
   }

   private static BakedQuad[] getQuadsCompact(int integer, TextureAtlasSprite[] arr, BakedQuad bakedQuad, RenderEnv renderEnv) {
      return ConnectedTextures.getQuads(arr[integer], bakedQuad, renderEnv);
   }

   private static BakedQuad[] getQuadsCompact(
      ConnectedTexturesCompact.Dir dir1,
      int integer2,
      ConnectedTexturesCompact.Dir dir3,
      int integer4,
      TextureAtlasSprite[] arr,
      int integer6,
      BakedQuad bakedQuad,
      RenderEnv renderEnv
   ) {
      BakedQuad var8 = getQuadCompact(arr[integer2], dir1, integer6, bakedQuad, renderEnv);
      BakedQuad var9 = getQuadCompact(arr[integer4], dir3, integer6, bakedQuad, renderEnv);
      return renderEnv.getArrayQuadsCtm(var8, var9);
   }

   private static BakedQuad[] getQuadsCompact(
      ConnectedTexturesCompact.Dir dir1,
      int integer2,
      ConnectedTexturesCompact.Dir dir3,
      int integer4,
      ConnectedTexturesCompact.Dir dir5,
      int integer6,
      TextureAtlasSprite[] arr,
      int integer8,
      BakedQuad bakedQuad,
      RenderEnv renderEnv
   ) {
      BakedQuad var10 = getQuadCompact(arr[integer2], dir1, integer8, bakedQuad, renderEnv);
      BakedQuad var11 = getQuadCompact(arr[integer4], dir3, integer8, bakedQuad, renderEnv);
      BakedQuad var12 = getQuadCompact(arr[integer6], dir5, integer8, bakedQuad, renderEnv);
      return renderEnv.getArrayQuadsCtm(var10, var11, var12);
   }

   private static BakedQuad[] getQuadsCompact(
      ConnectedTexturesCompact.Dir dir1,
      int integer2,
      ConnectedTexturesCompact.Dir dir3,
      int integer4,
      ConnectedTexturesCompact.Dir dir5,
      int integer6,
      ConnectedTexturesCompact.Dir dir7,
      int integer8,
      TextureAtlasSprite[] arr,
      int integer10,
      BakedQuad bakedQuad,
      RenderEnv renderEnv
   ) {
      BakedQuad var12 = getQuadCompact(arr[integer2], dir1, integer10, bakedQuad, renderEnv);
      BakedQuad var13 = getQuadCompact(arr[integer4], dir3, integer10, bakedQuad, renderEnv);
      BakedQuad var14 = getQuadCompact(arr[integer6], dir5, integer10, bakedQuad, renderEnv);
      BakedQuad var15 = getQuadCompact(arr[integer8], dir7, integer10, bakedQuad, renderEnv);
      return renderEnv.getArrayQuadsCtm(var12, var13, var14, var15);
   }

   private static BakedQuad getQuadCompact(
      TextureAtlasSprite textureAtlasSprite, ConnectedTexturesCompact.Dir dir, int integer, BakedQuad bakedQuad, RenderEnv renderEnv
   ) {
      switch (dir) {
         case UP:
            return getQuadCompact(textureAtlasSprite, dir, 0, 0, 16, 8, integer, bakedQuad, renderEnv);
         case UP_RIGHT:
            return getQuadCompact(textureAtlasSprite, dir, 8, 0, 16, 8, integer, bakedQuad, renderEnv);
         case RIGHT:
            return getQuadCompact(textureAtlasSprite, dir, 8, 0, 16, 16, integer, bakedQuad, renderEnv);
         case DOWN_RIGHT:
            return getQuadCompact(textureAtlasSprite, dir, 8, 8, 16, 16, integer, bakedQuad, renderEnv);
         case DOWN:
            return getQuadCompact(textureAtlasSprite, dir, 0, 8, 16, 16, integer, bakedQuad, renderEnv);
         case DOWN_LEFT:
            return getQuadCompact(textureAtlasSprite, dir, 0, 8, 8, 16, integer, bakedQuad, renderEnv);
         case LEFT:
            return getQuadCompact(textureAtlasSprite, dir, 0, 0, 8, 16, integer, bakedQuad, renderEnv);
         case UP_LEFT:
            return getQuadCompact(textureAtlasSprite, dir, 0, 0, 8, 8, integer, bakedQuad, renderEnv);
         default:
            return bakedQuad;
      }
   }

   private static BakedQuad getQuadCompact(
      TextureAtlasSprite textureAtlasSprite,
      ConnectedTexturesCompact.Dir dir,
      int integer3,
      int integer4,
      int integer5,
      int integer6,
      int integer7,
      BakedQuad bakedQuad,
      RenderEnv renderEnv
   ) {
      Map[][] var12 = ConnectedTextures.getSpriteQuadCompactMaps();
      if (var12 == null) {
         return bakedQuad;
      }

      int var13 = textureAtlasSprite.getIndexInMap();
      if (var13 >= 0 && var13 < var12.length) {
         Map[] var14 = var12[var13];
         if (var14 == null) {
            var14 = new Map[ConnectedTexturesCompact.Dir.VALUES.length];
            var12[var13] = var14;
         }

         Map var15 = var14[dir.ordinal()];
         if (var15 == null) {
            var15 = new IdentityHashMap(1);
            var14[dir.ordinal()] = var15;
         }

         BakedQuad var16 = (BakedQuad)var15.get(bakedQuad);
         if (var16 == null) {
            var16 = makeSpriteQuadCompact(bakedQuad, textureAtlasSprite, integer7, integer3, integer4, integer5, integer6);
            var15.put(bakedQuad, var16);
         }

         return var16;
      } else {
         return bakedQuad;
      }
   }

   private static BakedQuad makeSpriteQuadCompact(
      BakedQuad bakedQuad, TextureAtlasSprite textureAtlasSprite, int integer3, int integer4, int integer5, int integer6, int integer7
   ) {
      int[] var10 = (int[])bakedQuad.getVertexData().clone();
      TextureAtlasSprite var11 = bakedQuad.getSprite();

      for (int var12 = 0; var12 < 4; var12++) {
         fixVertexCompact(var10, var12, var11, textureAtlasSprite, integer3, integer4, integer5, integer6, integer7);
      }

      return new BakedQuad(var10, bakedQuad.getTintIndex(), bakedQuad.getFace(), textureAtlasSprite);
   }

   private static void fixVertexCompact(
      int[] arr,
      int integer2,
      TextureAtlasSprite textureAtlasSprite3,
      TextureAtlasSprite textureAtlasSprite4,
      int integer5,
      int integer6,
      int integer7,
      int integer8,
      int integer9
   ) {
      int var13 = arr.length / 4 * integer2;
      float var14 = Float.intBitsToFloat(arr[var13 + 4]);
      float var15 = Float.intBitsToFloat(arr[var13 + 4 + 1]);
      double var16 = textureAtlasSprite3.getSpriteU16(var14);
      double var18 = textureAtlasSprite3.getSpriteV16(var15);
      float var20 = Float.intBitsToFloat(arr[var13]);
      float var21 = Float.intBitsToFloat(arr[var13 + 1]);
      float var22 = Float.intBitsToFloat(arr[var13 + 2]);
      float var23;
      float var24;
      switch (integer5) {
         case 0:
            var23 = var20;
            var24 = 1.0F - var22;
            break;
         case 1:
            var23 = var20;
            var24 = var22;
            break;
         case 2:
            var23 = 1.0F - var20;
            var24 = 1.0F - var21;
            break;
         case 3:
            var23 = var20;
            var24 = 1.0F - var21;
            break;
         case 4:
            var23 = var22;
            var24 = 1.0F - var21;
            break;
         case 5:
            var23 = 1.0F - var22;
            var24 = 1.0F - var21;
            break;
         default:
            return;
      }

      if (var16 < integer6) {
         var23 = (float)(var23 + (integer6 - var16) / 15.968F);
         var16 = integer6;
      }

      if (var16 > integer8) {
         var23 = (float)(var23 - (var16 - integer8) / 15.968F);
         var16 = integer8;
      }

      if (var18 < integer7) {
         var24 = (float)(var24 + (integer7 - var18) / 15.968F);
         var18 = integer7;
      }

      if (var18 > integer9) {
         var24 = (float)(var24 - (var18 - integer9) / 15.968F);
         var18 = integer9;
      }

      switch (integer5) {
         case 0:
            var20 = var23;
            var22 = 1.0F - var24;
            break;
         case 1:
            var20 = var23;
            var22 = var24;
            break;
         case 2:
            var20 = 1.0F - var23;
            var21 = 1.0F - var24;
            break;
         case 3:
            var20 = var23;
            var21 = 1.0F - var24;
            break;
         case 4:
            var22 = var23;
            var21 = 1.0F - var24;
            break;
         case 5:
            var22 = 1.0F - var23;
            var21 = 1.0F - var24;
            break;
         default:
            return;
      }

      arr[var13 + 4] = Float.floatToRawIntBits(textureAtlasSprite4.getInterpolatedU(var16));
      arr[var13 + 4 + 1] = Float.floatToRawIntBits(textureAtlasSprite4.getInterpolatedV(var18));
      arr[var13] = Float.floatToRawIntBits(var20);
      arr[var13 + 1] = Float.floatToRawIntBits(var21);
      arr[var13 + 2] = Float.floatToRawIntBits(var22);
   }

   enum Dir {
      UP,
      UP_RIGHT,
      RIGHT,
      DOWN_RIGHT,
      DOWN,
      DOWN_LEFT,
      LEFT,
      UP_LEFT;

      public static final ConnectedTexturesCompact.Dir[] VALUES = values();

      private static ConnectedTexturesCompact.Dir[] Method613() {
         return new ConnectedTexturesCompact.Dir[]{UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT};
      }
   }
}
