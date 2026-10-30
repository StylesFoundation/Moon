package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S0CPacketSpawnPlayer implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private UUID playerId;
   private int x;
   private int y;
   private int z;
   private byte yaw;
   private byte pitch;
   private int currentItem;
   private DataWatcher watcher;
   private List<DataWatcher.WatchableObject> field_148958_j;

   public S0CPacketSpawnPlayer() {
   }

   public S0CPacketSpawnPlayer(EntityPlayer entityPlayer) {
      this.entityId = entityPlayer.getEntityId();
      this.playerId = entityPlayer.getGameProfile().getId();
      this.x = MathHelper.floor_double(entityPlayer.posX * 32.0);
      this.y = MathHelper.floor_double(entityPlayer.posY * 32.0);
      this.z = MathHelper.floor_double(entityPlayer.posZ * 32.0);
      this.yaw = (byte)(entityPlayer.rotationYaw * 256.0F / 360.0F);
      this.pitch = (byte)(entityPlayer.rotationPitch * 256.0F / 360.0F);
      ItemStack var5 = entityPlayer.inventory.getCurrentItem();
      this.currentItem = var5 == null ? 0 : Item.getIdFromItem(var5.getItem());
      this.watcher = entityPlayer.getDataWatcher();
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.playerId = buffer.readUuid();
      this.x = buffer.readInt();
      this.y = buffer.readInt();
      this.z = buffer.readInt();
      this.yaw = buffer.readByte();
      this.pitch = buffer.readByte();
      this.currentItem = buffer.readShort();
      this.field_148958_j = DataWatcher.readWatchedListFromPacketBuffer(buffer);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeUuid(this.playerId);
      buffer.writeInt(this.x);
      buffer.writeInt(this.y);
      buffer.writeInt(this.z);
      buffer.writeByte(this.yaw);
      buffer.writeByte(this.pitch);
      buffer.writeShort(this.currentItem);
      this.watcher.writeTo(buffer);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSpawnPlayer(this);
   }

   public List<DataWatcher.WatchableObject> func_148944_c() {
      if (this.field_148958_j == null) {
         this.field_148958_j = this.watcher.getAllWatched();
      }

      return this.field_148958_j;
   }

   public int getEntityID() {
      return this.entityId;
   }

   public UUID getPlayer() {
      return this.playerId;
   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getZ() {
      return this.z;
   }

   public byte getYaw() {
      return this.yaw;
   }

   public byte getPitch() {
      return this.pitch;
   }

   public int getCurrentItemID() {
      return this.currentItem;
   }
}
