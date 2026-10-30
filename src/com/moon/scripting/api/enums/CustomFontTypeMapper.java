package com.moon.scripting.api.enums;

import com.moon.render.font.manager.FontType;

public class CustomFontTypeMapper {
   // $VF: Unable to simplify switch on enum
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static FontType toFontType(CustomFontType customFontType) {
      switch (customFontType) {
         case SF:
            return FontType.SF;
         case TAHOMA:
            return FontType.TAHOMA;
         case VERDANA:
            return FontType.VERDANA;
         case INTER_MEDIUM:
            return FontType.INTER_MEDIUM;
         case PRODUCT_SANS:
            return FontType.PRODUCT_SANS;
         case ROBOTO_MEDIUM:
            return FontType.ROBOTO_MEDIUM;
         case MATERIAL_ICONS:
            return FontType.MATERIAL_ICONS;
         case VOLTE_SEMI_BOLD:
            return FontType.VOLTE_SEMI_BOLD;
         case MATERIAL_SYMBOLS_OUTLINED_REGULAR:
            return FontType.MATERIAL_SYMBOLS_OUTLINED_REGULAR;
         default:
            return FontType.VERDANA;
      }
   }
}
