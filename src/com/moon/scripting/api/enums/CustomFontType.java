package com.moon.scripting.api.enums;

import com.moon.scripting.documentation.annotation.Description;
import com.moon.scripting.documentation.annotation.EnumContainer;

@EnumContainer(value = "custom_font_type", path = "enums/custom_font_type")
@Description("The different custom font types which can be used to render text")
public enum CustomFontType {
   @Description("Roboto Medium")
   ROBOTO_MEDIUM,
   @Description("Inter Medium")
   INTER_MEDIUM,
   @Description("Tahoma")
   TAHOMA,
   @Description("Verdana")
   VERDANA,
   @Description("San Francisco")
   SF,
   @Description("Product Sans")
   PRODUCT_SANS,
   @Description("Material Icons (https://fonts.google.com/icons for more information)")
   MATERIAL_ICONS,
   @Description("Material Symbols Outlined Regular (https://fonts.google.com/icons for more information)")
   MATERIAL_SYMBOLS_OUTLINED_REGULAR,
   @Description("Volte Semi Bold")
   VOLTE_SEMI_BOLD;
}
