package com.moon.scripting.api.functions.impl;

import com.moon.Moon;
import com.moon.render.font.manager.FontType;
import com.moon.scripting.api.enums.CustomFontType;
import com.moon.scripting.api.enums.CustomFontTypeMapper;
import com.moon.scripting.api.functions.FontFunctions;
import net.minecraft.client.Minecraft;

public class FontFunctionsImpl implements FontFunctions {
   private static final Minecraft mc = Minecraft.getMinecraft();

   @Override
   public float minecraft_width(String text) {
      return mc.fontRendererObj.getStringWidth(text);
   }

   @Override
   public float minecraft_height() {
      return mc.fontRendererObj.FONT_HEIGHT;
   }

   @Override
   public float width(CustomFontType customFontType, float size, String text) {
      FontType fontType = CustomFontTypeMapper.toFontType(customFontType);
      return Moon.getInstance().getFontManager().getFont(fontType).getTextWidth(text, size);
   }

   @Override
   public float height(CustomFontType customFontType, float size, String text) {
      FontType fontType = CustomFontTypeMapper.toFontType(customFontType);
      return Moon.getInstance().getFontManager().getFont(fontType).getTextHeight(text, size);
   }
}
