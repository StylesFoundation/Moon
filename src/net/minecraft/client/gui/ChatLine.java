package net.minecraft.client.gui;

import net.minecraft.util.IChatComponent;

public class ChatLine {
   private final int updateCounterCreated;
   private final IChatComponent lineString;
   private final int chatLineID;

   public ChatLine(int integer1, IChatComponent iChatComponent, int integer3) {
      this.lineString = iChatComponent;
      this.updateCounterCreated = integer1;
      this.chatLineID = integer3;
   }

   public IChatComponent getChatComponent() {
      return this.lineString;
   }

   public int getUpdatedCounter() {
      return this.updateCounterCreated;
   }

   public int getChatLineID() {
      return this.chatLineID;
   }
}
