package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.MathHelper;

public class S11PacketSpawnExperienceOrb implements Packet<INetHandlerPlayClient> {
   private int entityID;
   private int posX;
   private int posY;
   private int posZ;
   private int xpValue;

   public S11PacketSpawnExperienceOrb() {
   }

   public S11PacketSpawnExperienceOrb(EntityXPOrb entityXPOrb) {
      this.entityID = entityXPOrb.getEntityId();
      this.posX = MathHelper.floor_double(entityXPOrb.posX * 32.0);
      this.posY = MathHelper.floor_double(entityXPOrb.posY * 32.0);
      this.posZ = MathHelper.floor_double(entityXPOrb.posZ * 32.0);
      this.xpValue = entityXPOrb.getXpValue();
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityID = buffer.readVarIntFromBuffer();
      this.posX = buffer.readInt();
      this.posY = buffer.readInt();
      this.posZ = buffer.readInt();
      this.xpValue = buffer.readShort();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.entityID);
      buffer.writeInt(this.posX);
      buffer.writeInt(this.posY);
      buffer.writeInt(this.posZ);
      buffer.writeShort(this.xpValue);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleSpawnExperienceOrb(this);
   }

   public int getEntityID() {
      return this.entityID;
   }

   public int getX() {
      return this.posX;
   }

   public int getY() {
      return this.posY;
   }

   public int getZ() {
      return this.posZ;
   }

   public int getXPValue() {
      return this.xpValue;
   }
}
