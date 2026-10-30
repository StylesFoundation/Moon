package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;

public class S41PacketServerDifficulty implements Packet<INetHandlerPlayClient> {
   private EnumDifficulty difficulty;
   private boolean difficultyLocked;

   public S41PacketServerDifficulty() {
   }

   public S41PacketServerDifficulty(EnumDifficulty enumDifficulty, boolean boolean2) {
      this.difficulty = enumDifficulty;
      this.difficultyLocked = boolean2;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleServerDifficulty(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.difficulty = EnumDifficulty.getDifficultyEnum(buffer.readUnsignedByte());
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeByte(this.difficulty.getDifficultyId());
   }

   public boolean isDifficultyLocked() {
      return this.difficultyLocked;
   }

   public EnumDifficulty getDifficulty() {
      return this.difficulty;
   }
}
