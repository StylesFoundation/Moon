package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;

public class S30PacketWindowItems implements Packet<INetHandlerPlayClient> {
   private int windowId;
   private ItemStack[] itemStacks;

   public S30PacketWindowItems() {
   }

   public S30PacketWindowItems(int integer, List<ItemStack> list) {
      this.windowId = integer;
      this.itemStacks = new ItemStack[list.size()];

      for (int var6 = 0; var6 < this.itemStacks.length; var6++) {
         ItemStack var7 = (ItemStack)list.get(var6);
         this.itemStacks[var6] = var7 == null ? null : var7.copy();
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.windowId = buffer.readUnsignedByte();
      short var5 = buffer.readShort();
      this.itemStacks = new ItemStack[var5];

      for (int var6 = 0; var6 < var5; var6++) {
         this.itemStacks[var6] = buffer.readItemStackFromBuffer();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.windowId);
      buffer.writeShort(this.itemStacks.length);

      for (ItemStack var8 : this.itemStacks) {
         buffer.writeItemStackToBuffer(var8);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleWindowItems(this);
   }

   public int func_148911_c() {
      return this.windowId;
   }

   public ItemStack[] getItemStacks() {
      return this.itemStacks;
   }
}
