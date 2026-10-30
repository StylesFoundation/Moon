package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldType;

public class S07PacketRespawn implements Packet<INetHandlerPlayClient> {
   private int dimensionID;
   private EnumDifficulty difficulty;
   private WorldSettings.GameType gameType;
   private WorldType worldType;

   public S07PacketRespawn() {
   }

   public S07PacketRespawn(int integer, EnumDifficulty enumDifficulty, WorldType worldType, WorldSettings.GameType gameType) {
      this.dimensionID = integer;
      this.difficulty = enumDifficulty;
      this.gameType = gameType;
      this.worldType = worldType;
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handleRespawn(this);
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.dimensionID = buffer.readInt();
      this.difficulty = EnumDifficulty.getDifficultyEnum(buffer.readUnsignedByte());
      this.gameType = WorldSettings.GameType.getByID(buffer.readUnsignedByte());
      this.worldType = WorldType.parseWorldType(buffer.readStringFromBuffer(16));
      if (this.worldType == null) {
         this.worldType = WorldType.DEFAULT;
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeInt(this.dimensionID);
      buffer.writeByte(this.difficulty.getDifficultyId());
      buffer.writeByte(this.gameType.getID());
      buffer.writeString(this.worldType.getWorldTypeName());
   }

   public int getDimensionID() {
      return this.dimensionID;
   }

   public EnumDifficulty getDifficulty() {
      return this.difficulty;
   }

   public WorldSettings.GameType getGameType() {
      return this.gameType;
   }

   public WorldType getWorldType() {
      return this.worldType;
   }
}
