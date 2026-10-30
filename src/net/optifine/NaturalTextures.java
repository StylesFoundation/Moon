package net.optifine;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.src.Config;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.optifine.util.TextureUtils;

public class NaturalTextures {
   private static NaturalProperties[] propertiesByIndex = new NaturalProperties[0];

   public static void update() {
      propertiesByIndex = new NaturalProperties[0];
      if (Config.isNaturalTextures()) {
         String var3 = "optifine/natural.properties";

         try {
            ResourceLocation var4 = new ResourceLocation(var3);
            if (!Config.hasResource(var4)) {
               Config.Method251("NaturalTextures: configuration \"" + "optifine/natural.properties" + "\" not found");
               return;
            }

            boolean var5 = Config.isFromDefaultResourcePack(var4);
            InputStream var6 = Config.getResourceStream(var4);
            ArrayList<NaturalProperties> var7 = new ArrayList<NaturalProperties>(256);
            String var8 = Config.readInputStream(var6);
            var6.close();
            String[] var9 = Config.tokenize(var8, "\n\r");
            if (var5) {
               Config.Method251("Natural Textures: Parsing default configuration \"" + "optifine/natural.properties" + "\"");
               Config.Method251("Natural Textures: Valid only for textures from default resource pack");
            } else {
               Config.Method251("Natural Textures: Parsing configuration \"" + "optifine/natural.properties" + "\"");
            }

            TextureMap var10 = TextureUtils.getTextureMapBlocks();
            String[] var11 = var9;
            int var12 = var11.length;

            for (int var13 = 0; var13 < var12; var13++) {
               String var15 = var11[var13].trim();
               if (!var15.startsWith("#")) {
                  String[] var16 = Config.tokenize(var15, "=");
                  if (var16.length != 2) {
                     Config.Method252("Natural Textures: Invalid \"" + var3 + "\" line: " + var15);
                  } else {
                     String var17 = var16[0].trim();
                     String var18 = var16[1].trim();
                     TextureAtlasSprite var19 = var10.getSpriteSafe("minecraft:blocks/" + var17);
                     if (var19 == null) {
                        Config.Method252("Natural Textures: Texture not found: \"" + var3 + "\" line: " + var15);
                     } else {
                        int var20 = var19.getIndexInMap();
                        if (var20 < 0) {
                           Config.Method252("Natural Textures: Invalid \"" + var3 + "\" line: " + var15);
                        } else {
                           if (var5 && !Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/" + var17 + ".png"))) {
                              return;
                           }

                           NaturalProperties var21 = new NaturalProperties(var18);
                           if (var21.isValid()) {
                              while (var7.size() <= var20) {
                                 var7.add(null);
                              }

                              var7.set(var20, var21);
                              Config.Method251("NaturalTextures: " + var17 + " = " + var18);
                           }
                        }
                     }
                  }
               }
            }

            propertiesByIndex = var7.toArray(new NaturalProperties[0]);
         } catch (FileNotFoundException var22) {
            Config.Method252("NaturalTextures: configuration \"" + "optifine/natural.properties" + "\" not found");
         } catch (Exception var23) {
            var23.printStackTrace();
         }
      }
   }

   public static BakedQuad getNaturalTexture(BlockPos blockPos, BakedQuad bakedQuad) {
      TextureAtlasSprite var5 = bakedQuad.getSprite();
      if (var5 == null) {
         return bakedQuad;
      }

      NaturalProperties var6 = getNaturalProperties(var5);
      if (var6 == null) {
         return bakedQuad;
      }

      int var7 = ConnectedTextures.getSide(bakedQuad.getFace());
      int var8 = Config.getRandom(blockPos, var7);
      int var9 = 0;
      boolean var10 = false;
      if (var6.rotation > 1) {
         var9 = var8 & 3;
      }

      if (var6.rotation == 2) {
         var9 = var9 / 2 * 2;
      }

      if (var6.flip) {
         var10 = (var8 & 4) != 0;
      }

      return var6.getQuad(bakedQuad, var9, var10);
   }

   public static NaturalProperties getNaturalProperties(TextureAtlasSprite textureAtlasSprite) {
      if (!(textureAtlasSprite instanceof TextureAtlasSprite)) {
         return null;
      }

      int var4 = textureAtlasSprite.getIndexInMap();
      return var4 >= 0 && var4 < propertiesByIndex.length ? propertiesByIndex[var4] : null;
   }
}
