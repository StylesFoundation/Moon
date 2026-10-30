package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class S33PacketUpdateSign implements Packet<INetHandlerPlayClient> {
   private World world;
   private BlockPos blockPos;
   private IChatComponent[] lines;

   public S33PacketUpdateSign() {
   }

   public S33PacketUpdateSign(World world, BlockPos blockPos, IChatComponent[] arr) {
      this.world = world;
      this.blockPos = blockPos;
      this.lines = new IChatComponent[]{arr[0], arr[1], arr[2], arr[3]};
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.blockPos = buffer.readBlockPos();
      this.lines = new IChatComponent[4];

      for (int var5 = 0; var5 < 4; var5++) {
         this.lines[var5] = buffer.readChatComponent();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBlockPos(this.blockPos);

      for (int var5 = 0; var5 < 4; var5++) {
         buffer.writeChatComponent(this.lines[var5]);
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleUpdateSign(this);
   }

   public BlockPos getPos() {
      return this.blockPos;
   }

   public IChatComponent[] getLines() {
      return this.lines;
   }
}
