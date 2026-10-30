package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.BlockPos;

public class S28PacketEffect implements Packet<INetHandlerPlayClient> {
   private int soundType;
   private BlockPos soundPos;
   private int soundData;
   private boolean serverWide;

   public S28PacketEffect() {
   }

   public S28PacketEffect(int integer1, BlockPos blockPos, int integer3, boolean boolean4) {
      this.soundType = integer1;
      this.soundPos = blockPos;
      this.soundData = integer3;
      this.serverWide = boolean4;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.soundType = buffer.readInt();
      this.soundPos = buffer.readBlockPos();
      this.soundData = buffer.readInt();
      this.serverWide = buffer.readBoolean();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeInt(this.soundType);
      buffer.writeBlockPos(this.soundPos);
      buffer.writeInt(this.soundData);
      buffer.writeBoolean(this.serverWide);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleEffect(this);
   }

   public boolean isSoundServerwide() {
      return this.serverWide;
   }

   public int getSoundType() {
      return this.soundType;
   }

   public int getSoundData() {
      return this.soundData;
   }

   public BlockPos getSoundPos() {
      return this.soundPos;
   }
}
