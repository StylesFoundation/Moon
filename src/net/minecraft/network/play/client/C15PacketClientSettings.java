package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C15PacketClientSettings implements Packet<INetHandlerPlayServer> {
   private String lang;
   private int view;
   private EntityPlayer.EnumChatVisibility chatVisibility;
   private boolean enableColors;
   private int modelPartFlags;

   public C15PacketClientSettings() {
   }

   public C15PacketClientSettings(String string, int integer2, EntityPlayer.EnumChatVisibility enumChatVisibility, boolean boolean4, int integer5) {
      this.lang = string;
      this.view = integer2;
      this.chatVisibility = enumChatVisibility;
      this.enableColors = boolean4;
      this.modelPartFlags = integer5;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.lang = buffer.readStringFromBuffer(7);
      this.view = buffer.readByte();
      this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(buffer.readByte());
      this.enableColors = buffer.readBoolean();
      this.modelPartFlags = buffer.readUnsignedByte();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(this.lang);
      buffer.writeByte(this.view);
      buffer.writeByte(this.chatVisibility.getChatVisibility());
      buffer.writeBoolean(this.enableColors);
      buffer.writeByte(this.modelPartFlags);
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processClientSettings(this);
   }

   public String getLang() {
      return this.lang;
   }

   public EntityPlayer.EnumChatVisibility getChatVisibility() {
      return this.chatVisibility;
   }

   public boolean isColorsEnabled() {
      return this.enableColors;
   }

   public int getModelPartFlags() {
      return this.modelPartFlags;
   }
}
