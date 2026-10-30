package net.optifine;

import java.util.BitSet;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;

public class SmartAnimations {
   private static boolean active;
   private static final BitSet spritesRendered = new BitSet();
   private static final BitSet texturesRendered = new BitSet();

   public static boolean isActive() {
      return active && !Shaders.isShadowPass;
   }

   public static void update() {
      active = Config.getGameSettings().ofSmartAnimations;
   }

   public static void spriteRendered(int integer) {
      if (integer >= 0) {
         spritesRendered.set(integer);
      }
   }

   public static void spritesRendered(BitSet bitSet) {
      if (bitSet != null) {
         spritesRendered.or(bitSet);
      }
   }

   public static boolean isSpriteRendered(int integer) {
      return integer >= 0 && spritesRendered.get(integer);
   }

   public static void resetSpritesRendered() {
      spritesRendered.clear();
   }

   public static void textureRendered(int integer) {
      if (integer >= 0) {
         texturesRendered.set(integer);
      }
   }

   public static boolean isTextureRendered(int integer) {
      return integer >= 0 && texturesRendered.get(integer);
   }

   public static void resetTexturesRendered() {
      texturesRendered.clear();
   }
}
