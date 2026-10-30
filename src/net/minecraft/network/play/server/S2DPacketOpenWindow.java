package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;

public class S2DPacketOpenWindow implements Packet<INetHandlerPlayClient> {
   private int windowId;
   private String inventoryType;
   private IChatComponent windowTitle;
   private int slotCount;
   private int entityId;

   public S2DPacketOpenWindow() {
   }

   public S2DPacketOpenWindow(int integer, String string, IChatComponent iChatComponent) {
      this(integer, string, iChatComponent, 0);
   }

   public S2DPacketOpenWindow(int integer1, String string, IChatComponent iChatComponent, int integer4) {
      this.windowId = integer1;
      this.inventoryType = string;
      this.windowTitle = iChatComponent;
      this.slotCount = integer4;
   }

   public S2DPacketOpenWindow(int integer1, String string, IChatComponent iChatComponent, int integer4, int integer5) {
      this(integer1, string, iChatComponent, integer4);
      this.entityId = integer5;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleOpenWindow(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readUnsignedByte();
      this.inventoryType = buffer.readStringFromBuffer(32);
      this.windowTitle = buffer.readChatComponent();
      this.slotCount = buffer.readUnsignedByte();
      if (this.inventoryType.equals("EntityHorse")) {
         this.entityId = buffer.readInt();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
      buffer.writeString(this.inventoryType);
      buffer.writeChatComponent(this.windowTitle);
      buffer.writeByte(this.slotCount);
      if (this.inventoryType.equals("EntityHorse")) {
         buffer.writeInt(this.entityId);
      }
   }

   public int getWindowId() {
      return this.windowId;
   }

   public String getGuiId() {
      return this.inventoryType;
   }

   public IChatComponent getWindowTitle() {
      return this.windowTitle;
   }

   public int getSlotCount() {
      return this.slotCount;
   }

   public int getEntityId() {
      return this.entityId;
   }

   public boolean hasSlots() {
      return this.slotCount > 0;
   }
}
