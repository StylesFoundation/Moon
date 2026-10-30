package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.World;

public class S49PacketUpdateEntityNBT implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private NBTTagCompound tagCompound;

   public S49PacketUpdateEntityNBT() {
   }

   public S49PacketUpdateEntityNBT(int integer, NBTTagCompound nBTTagCompound) {
      this.entityId = integer;
      this.tagCompound = nBTTagCompound;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readVarIntFromBuffer();
      this.tagCompound = buffer.readNBTTagCompoundFromBuffer();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityId);
      buffer.writeNBTTagCompoundToBuffer(this.tagCompound);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEntityNBT(this);
   }

   public NBTTagCompound getTagCompound() {
      return this.tagCompound;
   }

   public Entity getEntity(World world) {
      return world.getEntityByID(this.entityId);
   }
}
