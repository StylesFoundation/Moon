package net.minecraft.network.play.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.WorldSettings;

public class S38PacketPlayerListItem implements Packet<INetHandlerPlayClient> {
   private S38PacketPlayerListItem.Action action;
   private final List<S38PacketPlayerListItem.AddPlayerData> players = Lists.newArrayList();

   public S38PacketPlayerListItem() {
   }

   public S38PacketPlayerListItem(S38PacketPlayerListItem.Action action, EntityPlayerMP... arr) {
      this.action = action;

      for (EntityPlayerMP var9 : arr) {
         this.players
            .add(
               new S38PacketPlayerListItem.AddPlayerData(
                  var9.getGameProfile(), var9.ping, var9.theItemInWorldManager.getGameType(), var9.getTabListDisplayName()
               )
            );
      }
   }

   public S38PacketPlayerListItem(S38PacketPlayerListItem.Action action, Iterable<EntityPlayerMP> iterable) {
      this.action = action;

      for (EntityPlayerMP var7 : iterable) {
         this.players
            .add(
               new S38PacketPlayerListItem.AddPlayerData(
                  var7.getGameProfile(), var7.ping, var7.theItemInWorldManager.getGameType(), var7.getTabListDisplayName()
               )
            );
      }
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.action = buffer.readEnumValue(S38PacketPlayerListItem.Action.class);
      int var5 = buffer.readVarIntFromBuffer();

      for (int var6 = 0; var6 < var5; var6++) {
         GameProfile var7 = null;
         int var8 = 0;
         WorldSettings.GameType var9 = null;
         IChatComponent var10 = null;
         switch (this.action) {
            case ADD_PLAYER:
               var7 = new GameProfile(buffer.readUuid(), buffer.readStringFromBuffer(16));
               int var11 = buffer.readVarIntFromBuffer();
               int var12 = 0;

               for (; var12 < var11; var12++) {
                  String var13 = buffer.readStringFromBuffer(32767);
                  String var14 = buffer.readStringFromBuffer(32767);
                  if (buffer.readBoolean()) {
                     var7.getProperties().put(var13, new Property(var13, var14, buffer.readStringFromBuffer(32767)));
                  } else {
                     var7.getProperties().put(var13, new Property(var13, var14));
                  }
               }

               var9 = WorldSettings.GameType.getByID(buffer.readVarIntFromBuffer());
               var8 = buffer.readVarIntFromBuffer();
               if (buffer.readBoolean()) {
                  var10 = buffer.readChatComponent();
               }
               break;
            case UPDATE_GAME_MODE:
               var7 = new GameProfile(buffer.readUuid(), null);
               var9 = WorldSettings.GameType.getByID(buffer.readVarIntFromBuffer());
               break;
            case UPDATE_LATENCY:
               var7 = new GameProfile(buffer.readUuid(), null);
               var8 = buffer.readVarIntFromBuffer();
               break;
            case UPDATE_DISPLAY_NAME:
               var7 = new GameProfile(buffer.readUuid(), null);
               if (buffer.readBoolean()) {
                  var10 = buffer.readChatComponent();
               }
               break;
            case REMOVE_PLAYER:
               var7 = new GameProfile(buffer.readUuid(), null);
         }

         this.players.add(new S38PacketPlayerListItem.AddPlayerData(var7, var8, var9, var10));
      }
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeEnumValue(this.action);
      buffer.writeVarIntToBuffer(this.players.size());

      for (S38PacketPlayerListItem.AddPlayerData var6 : this.players) {
         switch (this.action) {
            case ADD_PLAYER:
               buffer.writeUuid(var6.getProfile().getId());
               buffer.writeString(var6.getProfile().getName());
               buffer.writeVarIntToBuffer(var6.getProfile().getProperties().size());

               for (Property var8 : var6.getProfile().getProperties().values()) {
                  buffer.writeString(var8.getName());
                  buffer.writeString(var8.getValue());
                  if (var8.hasSignature()) {
                     buffer.writeBoolean(true);
                     buffer.writeString(var8.getSignature());
                  } else {
                     buffer.writeBoolean(false);
                  }
               }

               buffer.writeVarIntToBuffer(var6.getGameMode().getID());
               buffer.writeVarIntToBuffer(var6.getPing());
               if (var6.getDisplayName() == null) {
                  buffer.writeBoolean(false);
               } else {
                  buffer.writeBoolean(true);
                  buffer.writeChatComponent(var6.getDisplayName());
               }
               break;
            case UPDATE_GAME_MODE:
               buffer.writeUuid(var6.getProfile().getId());
               buffer.writeVarIntToBuffer(var6.getGameMode().getID());
               break;
            case UPDATE_LATENCY:
               buffer.writeUuid(var6.getProfile().getId());
               buffer.writeVarIntToBuffer(var6.getPing());
               break;
            case UPDATE_DISPLAY_NAME:
               buffer.writeUuid(var6.getProfile().getId());
               if (var6.getDisplayName() == null) {
                  buffer.writeBoolean(false);
               } else {
                  buffer.writeBoolean(true);
                  buffer.writeChatComponent(var6.getDisplayName());
               }
               break;
            case REMOVE_PLAYER:
               buffer.writeUuid(var6.getProfile().getId());
         }
      }
   }

   public void processPacket(INetHandlerPlayClient handler) {
      handler.handlePlayerListItem(this);
   }

   public List<S38PacketPlayerListItem.AddPlayerData> getEntries() {
      return this.players;
   }

   public S38PacketPlayerListItem.Action getAction() {
      return this.action;
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("action", this.action).add("entries", this.players).toString();
   }

   public enum Action {
      ADD_PLAYER,
      UPDATE_GAME_MODE,
      UPDATE_LATENCY,
      UPDATE_DISPLAY_NAME,
      REMOVE_PLAYER;

      private static S38PacketPlayerListItem.Action[] Method515() {
         return new S38PacketPlayerListItem.Action[]{ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER};
      }
   }

   public static class AddPlayerData {
      private final int ping;
      private final WorldSettings.GameType gamemode;
      private final GameProfile profile;
      private final IChatComponent displayName;

      public AddPlayerData(GameProfile gameProfile, int integer, WorldSettings.GameType gameType, IChatComponent iChatComponent) {
         this.profile = gameProfile;
         this.ping = integer;
         this.gamemode = gameType;
         this.displayName = iChatComponent;
      }

      public GameProfile getProfile() {
         return this.profile;
      }

      public int getPing() {
         return this.ping;
      }

      public WorldSettings.GameType getGameMode() {
         return this.gamemode;
      }

      public IChatComponent getDisplayName() {
         return this.displayName;
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this)
            .add("latency", this.ping)
            .add("gameMode", this.gamemode)
            .add("profile", this.profile)
            .add("displayName", this.displayName == null ? null : IChatComponent.Serializer.componentToJson(this.displayName))
            .toString();
      }
   }
}
