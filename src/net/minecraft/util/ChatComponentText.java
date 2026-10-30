package net.minecraft.util;

public class ChatComponentText extends ChatComponentStyle {
   private final String text;

   public ChatComponentText(String string) {
      this.text = string;
   }

   public String getChatComponentText_TextValue() {
      return this.text;
   }

   @Override
   public String getUnformattedTextForChat() {
      return this.text;
   }

   public ChatComponentText createCopy() {
      ChatComponentText var4 = new ChatComponentText(this.text);
      var4.setChatStyle(this.getChatStyle().createShallowCopy());

      for (IChatComponent var6 : this.getSiblings()) {
         var4.appendSibling(var6.createCopy());
      }

      return var4;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof ChatComponentText var5) ? false : this.text.equals(var5.getChatComponentText_TextValue()) && super.equals(object);
      }
   }

   @Override
   public String toString() {
      return "TextComponent{text='" + this.text + "', siblings=" + this.siblings + ", style=" + this.getChatStyle() + "}";
   }
}
