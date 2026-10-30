package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C0EPacketClickWindow implements Packet<INetHandlerPlayServer> {
   private int windowId;
   private int slotId;
   private int usedButton;
   private short actionNumber;
   private ItemStack clickedItem;
   private int mode;

   public C0EPacketClickWindow() {
   }

   public C0EPacketClickWindow(int integer1, int integer2, int integer3, int integer4, ItemStack itemStack, short short6) {
      this.windowId = integer1;
      this.slotId = integer2;
      this.usedButton = integer3;
      this.clickedItem = itemStack != null ? itemStack.copy() : null;
      this.actionNumber = short6;
      this.mode = integer4;
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processClickWindow(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readByte();
      this.slotId = buffer.readShort();
      this.usedButton = buffer.readByte();
      this.actionNumber = buffer.readShort();
      this.mode = buffer.readByte();
      this.clickedItem = buffer.readItemStackFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
      buffer.writeShort(this.slotId);
      buffer.writeByte(this.usedButton);
      buffer.writeShort(this.actionNumber);
      buffer.writeByte(this.mode);
      buffer.writeItemStackToBuffer(this.clickedItem);
   }

   public int getWindowId() {
      return this.windowId;
   }

   public int getSlotId() {
      return this.slotId;
   }

   public int getUsedButton() {
      return this.usedButton;
   }

   public short getActionNumber() {
      return this.actionNumber;
   }

   public ItemStack getClickedItem() {
      return this.clickedItem;
   }

   public int getMode() {
      return this.mode;
   }

   public int Method631() {
      return this.windowId;
   }

   public int Method632() {
      return this.slotId;
   }

   public int Method633() {
      return this.usedButton;
   }

   public short Method634() {
      return this.actionNumber;
   }

   public ItemStack Method635() {
      return this.clickedItem;
   }

   public int Method636() {
      return this.mode;
   }

   public C0EPacketClickWindow Method637(int integer) {
      this.windowId = integer;
      return this;
   }

   public C0EPacketClickWindow Method638(int integer) {
      this.slotId = integer;
      return this;
   }

   public C0EPacketClickWindow Method639(int integer) {
      this.usedButton = integer;
      return this;
   }

   public C0EPacketClickWindow Method640(short short1) {
      this.actionNumber = short1;
      return this;
   }

   public C0EPacketClickWindow Method641(ItemStack itemStack) {
      this.clickedItem = itemStack;
      return this;
   }

   public C0EPacketClickWindow Method642(int integer) {
      this.mode = integer;
      return this;
   }
}
