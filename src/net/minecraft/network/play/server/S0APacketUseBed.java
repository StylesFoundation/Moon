package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class S0APacketUseBed implements Packet<INetHandlerPlayClient> {
   private int playerID;
   private BlockPos bedPos;

   public S0APacketUseBed() {
   }

   public S0APacketUseBed(EntityPlayer entityPlayer, BlockPos blockPos) {
      this.playerID = entityPlayer.getEntityId();
      this.bedPos = blockPos;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.playerID = buffer.readVarIntFromBuffer();
      this.bedPos = buffer.readBlockPos();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeVarIntToBuffer(this.playerID);
      buffer.writeBlockPos(this.bedPos);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleUseBed(this);
   }

   public EntityPlayer getPlayer(World world) {
      return (EntityPlayer)world.getEntityByID(this.playerID);
   }

   public BlockPos getBedPosition() {
      return this.bedPos;
   }
}
