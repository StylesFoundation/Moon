package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.util.BlockPos;
import org.apache.commons.lang3.StringUtils;

public class C14PacketTabComplete implements Packet<INetHandlerPlayServer> {
   private String message;
   private BlockPos targetBlock;

   public C14PacketTabComplete() {
   }

   public C14PacketTabComplete(String string) {
      this(string, null);
   }

   public C14PacketTabComplete(String string, BlockPos blockPos) {
      this.message = string;
      this.targetBlock = blockPos;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.message = buffer.readStringFromBuffer(32767);
      boolean var4 = buffer.readBoolean();
      if (var4) {
         this.targetBlock = buffer.readBlockPos();
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(StringUtils.substring(this.message, 0, 32767));
      boolean var5 = this.targetBlock != null;
      buffer.writeBoolean(var5);
      if (var5) {
         buffer.writeBlockPos(this.targetBlock);
      }
   }

   public void processPacket(INetHandlerPlayServer handler) {
      handler.processTabComplete(this);
   }

   public String getMessage() {
      return this.message;
   }

   public BlockPos getTargetBlock() {
      return this.targetBlock;
   }

   public C14PacketTabComplete Method688(String string) {
      this.message = string;
      return this;
   }

   public C14PacketTabComplete Method689(BlockPos blockPos) {
      this.targetBlock = blockPos;
      return this;
   }

   public String Method690() {
      return this.message;
   }

   public BlockPos Method691() {
      return this.targetBlock;
   }
}
