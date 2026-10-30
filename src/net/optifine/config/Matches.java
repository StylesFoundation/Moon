package net.optifine.config;

import net.minecraft.block.state.BlockStateBase;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.biome.BiomeGenBase;

public class Matches {
   public static boolean block(BlockStateBase blockStateBase, MatchBlock[] arr) {
      if (arr == null) {
         return true;
      }

      for (MatchBlock var8 : arr) {
         if (var8.matches(blockStateBase)) {
            return true;
         }
      }

      return false;
   }

   public static boolean block(int integer1, int integer2, MatchBlock[] arr) {
      if (arr == null) {
         return true;
      }

      for (MatchBlock var9 : arr) {
         if (var9.matches(integer1, integer2)) {
            return true;
         }
      }

      return false;
   }

   public static boolean blockId(int integer, MatchBlock[] arr) {
      if (arr == null) {
         return true;
      }

      for (MatchBlock var8 : arr) {
         if (var8.getBlockId() == integer) {
            return true;
         }
      }

      return false;
   }

   public static boolean metadata(int integer, int[] arr) {
      if (arr == null) {
         return true;
      }

      for (int var8 : arr) {
         if (var8 == integer) {
            return true;
         }
      }

      return false;
   }

   public static boolean sprite(TextureAtlasSprite textureAtlasSprite, TextureAtlasSprite[] arr) {
      if (arr == null) {
         return true;
      }

      for (TextureAtlasSprite var8 : arr) {
         if (var8 == textureAtlasSprite) {
            return true;
         }
      }

      return false;
   }

   public static boolean biome(BiomeGenBase biomeGenBase, BiomeGenBase[] arr) {
      if (arr == null) {
         return true;
      }

      for (BiomeGenBase var8 : arr) {
         if (var8 == biomeGenBase) {
            return true;
         }
      }

      return false;
   }
}
