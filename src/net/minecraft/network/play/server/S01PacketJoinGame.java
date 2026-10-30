package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class S01PacketJoinGame implements Packet<INetHandlerPlayClient> {
   private int entityId;
   private boolean hardcoreMode;
   private WorldSettings.GameType gameType;
   private int dimension;
   private EnumDifficulty difficulty;
   private int maxPlayers;
   private WorldType worldType;
   private boolean reducedDebugInfo;

   public S01PacketJoinGame() {
   }

   public S01PacketJoinGame(
      int integer1,
      WorldSettings.GameType gameType,
      boolean boolean3,
      int integer4,
      EnumDifficulty enumDifficulty,
      int integer6,
      WorldType worldType,
      boolean boolean8
   ) {
      this.entityId = integer1;
      this.dimension = integer4;
      this.difficulty = enumDifficulty;
      this.gameType = gameType;
      this.maxPlayers = integer6;
      this.hardcoreMode = boolean3;
      this.worldType = worldType;
      this.reducedDebugInfo = boolean8;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.entityId = buffer.readInt();
      int var5 = buffer.readUnsignedByte();
      this.hardcoreMode = (var5 & 8) == 8;
      var5 &= -9;
      this.gameType = WorldSettings.GameType.getByID(var5);
      this.dimension = buffer.readByte();
      this.difficulty = EnumDifficulty.getDifficultyEnum(buffer.readUnsignedByte());
      this.maxPlayers = buffer.readUnsignedByte();
      this.worldType = WorldType.parseWorldType(buffer.readStringFromBuffer(16));
      if (this.worldType == null) {
         this.worldType = WorldType.DEFAULT;
      }

      this.reducedDebugInfo = buffer.readBoolean();
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeInt(this.entityId);
      int var5 = this.gameType.getID();
      if (this.hardcoreMode) {
         var5 |= 8;
      }

      buffer.writeByte(var5);
      buffer.writeByte(this.dimension);
      buffer.writeByte(this.difficulty.getDifficultyId());
      buffer.writeByte(this.maxPlayers);
      buffer.writeString(this.worldType.getWorldTypeName());
      buffer.writeBoolean(this.reducedDebugInfo);
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleJoinGame(this);
   }

   public int getEntityId() {
      return this.entityId;
   }

   public boolean isHardcoreMode() {
      return this.hardcoreMode;
   }

   public WorldSettings.GameType getGameType() {
      return this.gameType;
   }

   public int getDimension() {
      return this.dimension;
   }

   public EnumDifficulty getDifficulty() {
      return this.difficulty;
   }

   public int getMaxPlayers() {
      return this.maxPlayers;
   }

   public WorldType getWorldType() {
      return this.worldType;
   }

   public boolean isReducedDebugInfo() {
      return this.reducedDebugInfo;
   }
}
