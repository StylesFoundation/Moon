package net.minecraft.util;

public class ChatComponentTranslationFormatException extends IllegalArgumentException {
   public ChatComponentTranslationFormatException(ChatComponentTranslation chatComponentTranslation, String string) {
      super(String.format("Error parsing: %s: %s", chatComponentTranslation, string));
   }

   public ChatComponentTranslationFormatException(ChatComponentTranslation chatComponentTranslation, int integer) {
      super(String.format("Invalid index %d requested for %s", integer, chatComponentTranslation));
   }

   public ChatComponentTranslationFormatException(ChatComponentTranslation chatComponentTranslation, Throwable throwable) {
      super(String.format("Error while parsing: %s", chatComponentTranslation), throwable);
   }
}
