package moom;

import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.OldServerPinger;
import net.minecraft.client.network.OldServerPinger$2$1;

public class l extends ChannelInitializer<Channel> {
   public final ServerAddress a;
   public final ServerData b;
   public final OldServerPinger c;

   public l(OldServerPinger oldServerPinger, ServerAddress serverAddress, ServerData serverData) {
      this.c = oldServerPinger;
      this.a = serverAddress;
      this.b = serverData;
   }

   @Override
   protected void initChannel(Channel channel) throws Exception {
      try {
         channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
      } catch (ChannelException var2) {
      }

      channel.pipeline().addLast(new ChannelHandler[]{new OldServerPinger$2$1(this)});
   }
}
