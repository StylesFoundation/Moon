package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class C12PacketUpdateSign implements Packet<INetHandlerPlayServer> {
   private BlockPos pos;
   private IChatComponent[] lines;

   public C12PacketUpdateSign() {
   }

   public C12PacketUpdateSign(BlockPos blockPos, IChatComponent[] arr) {
      this.pos = blockPos;
      this.lines = new IChatComponent[]{arr[0], arr[1], arr[2], arr[3]};
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.pos = buffer.readBlockPos();
      this.lines = new IChatComponent[4];

      for (int var5 = 0; var5 < 4; var5++) {
         IChatComponent var7 = IChatComponent.Serializer.jsonToComponent(buffer.readStringFromBuffer(384));
         this.lines[var5] = var7;
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeBlockPos(this.pos);

      for (int var5 = 0; var5 < 4; var5++) {
         String var7 = IChatComponent.Serializer.componentToJson(this.lines[var5]);
         buffer.writeString(var7);
      }
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processUpdateSign(this);
   }

   public BlockPos getPosition() {
      return this.pos;
   }

   public IChatComponent[] getLines() {
      return this.lines;
   }
}
