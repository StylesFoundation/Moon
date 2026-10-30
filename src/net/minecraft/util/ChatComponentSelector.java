package net.minecraft.util;

public class ChatComponentSelector extends ChatComponentStyle {
   private final String selector;

   public ChatComponentSelector(String string) {
      this.selector = string;
   }

   public String getSelector() {
      return this.selector;
   }

   @Override
   public String getUnformattedTextForChat() {
      return this.selector;
   }

   public ChatComponentSelector createCopy() {
      ChatComponentSelector var4 = new ChatComponentSelector(this.selector);
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
         return !(object instanceof ChatComponentSelector var5) ? false : this.selector.equals(var5.selector) && super.equals(object);
      }
   }

   @Override
   public String toString() {
      return "SelectorComponent{pattern='" + this.selector + "', siblings=" + this.siblings + ", style=" + this.getChatStyle() + "}";
   }
}
