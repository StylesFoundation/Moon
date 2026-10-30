package net.minecraft.network.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import moom.yQ;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;

public class S00PacketServerInfo implements Packet<INetHandlerStatusClient> {
   private static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(
         ServerStatusResponse.MinecraftProtocolVersionIdentifier.class, new ServerStatusResponse.MinecraftProtocolVersionIdentifier.Serializer()
      )
      .registerTypeAdapter(ServerStatusResponse.PlayerCountData.class, new yQ())
      .registerTypeAdapter(ServerStatusResponse.class, new ServerStatusResponse.Serializer())
      .registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer())
      .registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer())
      .registerTypeAdapterFactory(new EnumTypeAdapterFactory())
      .create();
   private ServerStatusResponse response;

   public S00PacketServerInfo() {
   }

   public S00PacketServerInfo(ServerStatusResponse serverStatusResponse) {
      this.response = serverStatusResponse;
   }

   @Override
   public void readPacketData(PacketBuffer buffer) throws IOException {
      this.response = (ServerStatusResponse)GSON.fromJson(buffer.readStringFromBuffer(32767), ServerStatusResponse.class);
   }

   @Override
   public void writePacketData(PacketBuffer buffer) throws IOException {
      buffer.writeString(GSON.toJson(this.response));
   }

   public void processPacket(INetHandlerStatusClient handler) {
      handler.handleServerInfo(this);
   }

   public ServerStatusResponse getResponse() {
      return this.response;
   }
}
