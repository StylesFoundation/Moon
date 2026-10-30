package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S04PacketEntityEquipment implements Packet<INetHandlerPlayClient> {
   private int entityID;
   private int equipmentSlot;
   private ItemStack itemStack;

   public S04PacketEntityEquipment() {
   }

   public S04PacketEntityEquipment(int integer1, int integer2, ItemStack itemStack) {
      this.entityID = integer1;
      this.equipmentSlot = integer2;
      this.itemStack = itemStack == null ? null : itemStack.copy();
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityID = buffer.readVarIntFromBuffer();
      this.equipmentSlot = buffer.readShort();
      this.itemStack = buffer.readItemStackFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityID);
      buffer.writeShort(this.equipmentSlot);
      buffer.writeItemStackToBuffer(this.itemStack);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityEquipment(this);
   }

   public ItemStack getItemStack() {
      return this.itemStack;
   }

   public int getEntityID() {
      return this.entityID;
   }

   public int getEquipmentSlot() {
      return this.equipmentSlot;
   }
}
