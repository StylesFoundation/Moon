package org.lwjgl.openal;

public final class EFXUtil {
   private static final int EFFECT = 1111;
   private static final int FILTER = 2222;

   private EFXUtil() {
   }

   public static boolean isEfxSupported() {
      return ALC.createCapabilities(AL.alcDevice.device).ALC_EXT_EFX;
   }

   public static boolean isEffectSupported(int effectType) {
      switch (effectType) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 9:
         case 10:
         case 11:
         case 12:
         case 32768:
            return testSupportGeneric(1111, effectType);
         default:
            throw new IllegalArgumentException("Unknown or invalid effect type: " + effectType);
      }
   }

   public static boolean isFilterSupported(int filterType) {
      switch (filterType) {
         case 0:
         case 1:
         case 2:
         case 3:
            return testSupportGeneric(2222, filterType);
         default:
            throw new IllegalArgumentException("Unknown or invalid filter type: " + filterType);
      }
   }

   private static boolean testSupportGeneric(int objectType, int typeValue) {
      switch (objectType) {
         case 1111:
         case 2222:
            boolean supported = false;
            if (isEfxSupported()) {
               AL10.alGetError();
               int testObject = 0;

               int genError;
               try {
                  testObject = switch (objectType) {
                     case 1111 -> EXTEfx.alGenEffects();
                     case 2222 -> EXTEfx.alGenFilters();
                     default -> throw new IllegalArgumentException("Invalid objectType: " + objectType);
                  };
                  genError = AL10.alGetError();
               } catch (OpenALException debugBuildException) {
                  if (debugBuildException.getMessage().contains("AL_OUT_OF_MEMORY")) {
                     genError = 40965;
                  } else {
                     genError = 40964;
                  }
               }

               if (genError == 0) {
                  AL10.alGetError();

                  int setError;
                  try {
                     switch (objectType) {
                        case 1111:
                           EXTEfx.alEffecti(testObject, 32769, typeValue);
                           break;
                        case 2222:
                           EXTEfx.alFilteri(testObject, 32769, typeValue);
                           break;
                        default:
                           throw new IllegalArgumentException("Invalid objectType: " + objectType);
                     }

                     setError = AL10.alGetError();
                  } catch (OpenALException debugBuildException) {
                     setError = 40963;
                  }

                  if (setError == 0) {
                     supported = true;
                  }

                  try {
                     switch (objectType) {
                        case 1111:
                           EXTEfx.alDeleteEffects(testObject);
                           break;
                        case 2222:
                           EXTEfx.alDeleteFilters(testObject);
                           break;
                        default:
                           throw new IllegalArgumentException("Invalid objectType: " + objectType);
                     }
                  } catch (OpenALException var7) {
                  }
               } else if (genError == 40965) {
                  throw new OpenALException(AL10.alGetString(genError));
               }
            }

            return supported;
         default:
            throw new IllegalArgumentException("Invalid objectType: " + objectType);
      }
   }
}
