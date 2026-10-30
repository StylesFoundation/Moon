package net.minecraft.client.network;

import com.google.common.util.concurrent.FutureCallback;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;

class NetHandlerPlayClient$3$1$1 implements FutureCallback<Object> {
   final String a;
   final NetHandlerPlayClient b;

   NetHandlerPlayClient$3$1$1(NetHandlerPlayClient netHandlerPlayClient, String string) {
      this.b = netHandlerPlayClient;
      this.a = string;
   }

   @Override
   public void onSuccess(Object object) {
      this.b.getNetworkManager().sendPacket(new C19PacketResourcePackStatus(this.a, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
   }

   @Override
   public void onFailure(Throwable throwable) {
      this.b.getNetworkManager().sendPacket(new C19PacketResourcePackStatus(this.a, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
   }
}
