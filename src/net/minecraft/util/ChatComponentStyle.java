package net.minecraft.util;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public abstract class ChatComponentStyle implements IChatComponent {
   protected List<IChatComponent> siblings = Lists.newArrayList();
   private ChatStyle style;

   @Override
   public IChatComponent appendSibling(IChatComponent iChatComponent) {
      iChatComponent.getChatStyle().setParentStyle(this.getChatStyle());
      this.siblings.add(iChatComponent);
      return this;
   }

   @Override
   public List<IChatComponent> getSiblings() {
      return this.siblings;
   }

   @Override
   public IChatComponent appendText(String string) {
      return this.appendSibling(new ChatComponentText(string));
   }

   @Override
   public IChatComponent setChatStyle(ChatStyle chatStyle) {
      this.style = chatStyle;
      Iterator var5 = this.siblings.iterator();

      while (var5.hasNext()) {
         ((IChatComponent)var5.next()).getChatStyle().setParentStyle(this.getChatStyle());
      }

      return this;
   }

   @Override
   public ChatStyle getChatStyle() {
      if (this.style == null) {
         this.style = new ChatStyle();
         Iterator var3 = this.siblings.iterator();

         while (var3.hasNext()) {
            ((IChatComponent)var3.next()).getChatStyle().setParentStyle(this.style);
         }
      }

      return this.style;
   }

   @Override
   public Iterator<IChatComponent> iterator() {
      return Iterators.concat(Iterators.forArray(new ChatComponentStyle[]{this}), createDeepCopyIterator(this.siblings));
   }

   @Override
   public final String getUnformattedText() {
      StringBuilder var4 = new StringBuilder();

      for (IChatComponent var6 : this) {
         var4.append(var6.getUnformattedTextForChat());
      }

      return var4.toString();
   }

   @Override
   public final String getFormattedText() {
      StringBuilder var4 = new StringBuilder();

      for (IChatComponent var6 : this) {
         var4.append(var6.getChatStyle().getFormattingCode());
         var4.append(var6.getUnformattedTextForChat());
         var4.append(EnumChatFormatting.RESET);
      }

      return var4.toString();
   }

   public static Iterator<IChatComponent> createDeepCopyIterator(Iterable<IChatComponent> iterable) {
      return Iterators.transform(Iterators.concat(Iterators.transform(iterable.iterator(), Iterable::iterator)), iChatComponent -> {
         IChatComponent var1 = iChatComponent.createCopy();
         var1.setChatStyle(var1.getChatStyle().createDeepCopy());
         return var1;
      });
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof ChatComponentStyle var5) ? false : this.siblings.equals(var5.siblings) && this.getChatStyle().equals(var5.getChatStyle());
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.style.hashCode() + this.siblings.hashCode();
   }

   @Override
   public String toString() {
      return "BaseComponent{style=" + this.style + ", siblings=" + this.siblings + "}";
   }
}
