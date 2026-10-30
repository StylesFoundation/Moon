package net.minecraft.item;

import net.minecraft.block.material.MapColor;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IStringSerializable;

public enum EnumDyeColor implements IStringSerializable {
   WHITE(0, 15, "white", "white", MapColor.snowColor, EnumChatFormatting.WHITE),
   ORANGE(1, 14, "orange", "orange", MapColor.adobeColor, EnumChatFormatting.GOLD),
   MAGENTA(2, 13, "magenta", "magenta", MapColor.magentaColor, EnumChatFormatting.AQUA),
   LIGHT_BLUE(3, 12, "light_blue", "lightBlue", MapColor.lightBlueColor, EnumChatFormatting.BLUE),
   YELLOW(4, 11, "yellow", "yellow", MapColor.yellowColor, EnumChatFormatting.YELLOW),
   LIME(5, 10, "lime", "lime", MapColor.limeColor, EnumChatFormatting.GREEN),
   PINK(6, 9, "pink", "pink", MapColor.pinkColor, EnumChatFormatting.LIGHT_PURPLE),
   GRAY(7, 8, "gray", "gray", MapColor.grayColor, EnumChatFormatting.DARK_GRAY),
   SILVER(8, 7, "silver", "silver", MapColor.silverColor, EnumChatFormatting.GRAY),
   CYAN(9, 6, "cyan", "cyan", MapColor.cyanColor, EnumChatFormatting.DARK_AQUA),
   PURPLE(10, 5, "purple", "purple", MapColor.purpleColor, EnumChatFormatting.DARK_PURPLE),
   BLUE(11, 4, "blue", "blue", MapColor.blueColor, EnumChatFormatting.DARK_BLUE),
   BROWN(12, 3, "brown", "brown", MapColor.brownColor, EnumChatFormatting.GOLD),
   GREEN(13, 2, "green", "green", MapColor.greenColor, EnumChatFormatting.DARK_GREEN),
   RED(14, 1, "red", "red", MapColor.redColor, EnumChatFormatting.DARK_RED),
   BLACK(15, 0, "black", "black", MapColor.blackColor, EnumChatFormatting.BLACK);

   private static final EnumDyeColor[] META_LOOKUP = new EnumDyeColor[values().length];
   private static final EnumDyeColor[] DYE_DMG_LOOKUP = new EnumDyeColor[values().length];
   private final int meta;
   private final int dyeDamage;
   private final String name;
   private final String unlocalizedName;
   private final MapColor mapColor;
   private final EnumChatFormatting chatColor;

   EnumDyeColor(int integer3, int integer4, String string5, String string6, MapColor mapColor, EnumChatFormatting enumChatFormatting) {
      this.meta = integer3;
      this.dyeDamage = integer4;
      this.name = string5;
      this.unlocalizedName = string6;
      this.mapColor = mapColor;
      this.chatColor = enumChatFormatting;
   }

   public int getMetadata() {
      return this.meta;
   }

   public int getDyeDamage() {
      return this.dyeDamage;
   }

   public String getUnlocalizedName() {
      return this.unlocalizedName;
   }

   public MapColor getMapColor() {
      return this.mapColor;
   }

   public static EnumDyeColor byDyeDamage(int integer) {
      if (integer < 0 || integer >= DYE_DMG_LOOKUP.length) {
         integer = 0;
      }

      return DYE_DMG_LOOKUP[integer];
   }

   public static EnumDyeColor byMetadata(int integer) {
      if (integer < 0 || integer >= META_LOOKUP.length) {
         integer = 0;
      }

      return META_LOOKUP[integer];
   }

   @Override
   public String toString() {
      return this.unlocalizedName;
   }

   @Override
   public String getName() {
      return this.name;
   }

   static {
      for (EnumDyeColor var16 : values()) {
         META_LOOKUP[var16.getMetadata()] = var16;
         DYE_DMG_LOOKUP[var16.getDyeDamage()] = var16;
      }
   }
}
