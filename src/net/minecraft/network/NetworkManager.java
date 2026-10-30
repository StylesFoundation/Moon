package net.minecraft.network;

import com.google.common.collect.Queues;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.moon.Moon;
import com.moon.events.impl.network.PacketEvent;
import com.moon.viamcp.MoonUserConnection;
import com.moon.viamcp.ViaMCP;
import com.moon.viamcp.handler.CompressionReorderEvent;
import com.viaversion.viaversion.protocol.ProtocolPipelineImpl;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.local.LocalChannel;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.local.LocalServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.crypto.SecretKey;
import com.moon.viamcp.handler.PipelineReorderHandler;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CryptManager;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.LazyLoadBase;
import net.minecraft.util.MessageDeserializer;
import net.minecraft.util.MessageDeserializer2;
import net.minecraft.util.MessageSerializer;
import net.minecraft.util.MessageSerializer2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class NetworkManager extends SimpleChannelInboundHandler<Packet> {
   public static final Marker logMarkerNetwork = MarkerManager.getMarker("NETWORK");
   public static final Marker logMarkerPackets = MarkerManager.getMarker("NETWORK_PACKETS", logMarkerNetwork);
   public static final AttributeKey<EnumConnectionState> attrKeyConnectionState = AttributeKey.valueOf("protocol");
   public static final LazyLoadBase<NioEventLoopGroup> CLIENT_NIO_EVENTLOOP = new LazyLoadBase<NioEventLoopGroup>() {
      protected NioEventLoopGroup load() {
         return new NioEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Client IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase<EpollEventLoopGroup> CLIENT_EPOLL_EVENTLOOP = new LazyLoadBase<EpollEventLoopGroup>() {
      protected EpollEventLoopGroup load() {
         return new EpollEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Epoll Client IO #%d").setDaemon(true).build());
      }
   };
   public static final LazyLoadBase<LocalEventLoopGroup> CLIENT_LOCAL_EVENTLOOP = new LazyLoadBase<LocalEventLoopGroup>() {
      protected LocalEventLoopGroup load() {
         return new LocalEventLoopGroup(0, new ThreadFactoryBuilder().setNameFormat("Netty Local Client IO #%d").setDaemon(true).build());
      }
   };
   private static final Logger logger = LogManager.getLogger();
   private final EnumPacketDirection direction;
   private final Queue<NetworkManager.InboundHandlerTuplePacketListener> outboundPacketsQueue = Queues.newConcurrentLinkedQueue();
   private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
   public Channel channel;
   private SocketAddress socketAddress;
   private INetHandler packetListener;
   private IChatComponent terminationReason;
   private boolean isEncrypted;
   private boolean disconnected;

   public NetworkManager(EnumPacketDirection enumPacketDirection) {
      this.direction = enumPacketDirection;
   }

   public static NetworkManager createNetworkManagerAndConnect(InetAddress inetAddress, int integer, boolean boolean3) {
      final NetworkManager var6 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      Class<? extends SocketChannel> var7;
      LazyLoadBase var8;
      if (Epoll.isAvailable() && boolean3) {
         var7 = EpollSocketChannel.class;
         var8 = CLIENT_EPOLL_EVENTLOOP;
      } else {
         var7 = NioSocketChannel.class;
         var8 = CLIENT_NIO_EVENTLOOP;
      }

      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)var8.getValue()))
               .handler(
                  new ChannelInitializer<Channel>() {
                     @Override
                     protected void initChannel(Channel channel) {
                        try {
                           channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.TRUE);
                        } catch (ChannelException var6x) {
                        }

                        channel.pipeline()
                           .addLast("timeout", new ReadTimeoutHandler(30))
                           .addLast("splitter", new MessageDeserializer2())
                           .addLast("decoder", new MessageDeserializer(EnumPacketDirection.CLIENTBOUND))
                           .addLast("prepender", new MessageSerializer2())
                           .addLast("encoder", new MessageSerializer(EnumPacketDirection.SERVERBOUND))
                           .addLast("packet_handler", var6);
                        if (channel instanceof SocketChannel && ViaMCP.getInstance().getVersion() != ViaMCP.getInstance().getProtocolVersion()) {
                           MoonUserConnection var5 = new MoonUserConnection(channel, true);
                           new ProtocolPipelineImpl(var5);
                           channel.pipeline().addLast(new ChannelHandler[]{new PipelineReorderHandler(var5) {
                              @Override
                              public String getDecoderInjectPoint() {
                                 return "decoder";
                              }

                              @Override
                              public String getEncoderInjectPoint() {
                                 return "encoder";
                              }

                              @Override
                              public String getDecoderReorderAnchor() {
                                 return "decompress";
                              }

                              @Override
                              public String getEncoderReorderAnchor() {
                                 return "compress";
                              }
                           }});
                        }
                     }
                  }
               ))
            .channel(var7))
         .connect(inetAddress, integer)
         .syncUninterruptibly();
      return var6;
   }

   public static NetworkManager provideLocalClient(SocketAddress socketAddress) {
      final NetworkManager var1 = new NetworkManager(EnumPacketDirection.CLIENTBOUND);
      ((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group((EventLoopGroup)CLIENT_LOCAL_EVENTLOOP.getValue())).handler(new ChannelInitializer<Channel>() {
         @Override
         protected void initChannel(Channel channel) throws Exception {
            channel.pipeline().addLast("packet_handler", var1);
         }
      })).channel(LocalChannel.class)).connect(socketAddress).syncUninterruptibly();
      return var1;
   }

   @Override
   public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
      super.channelActive(channelHandlerContext);
      this.channel = channelHandlerContext.channel();
      this.socketAddress = this.channel.remoteAddress();

      try {
         this.setConnectionState(EnumConnectionState.HANDSHAKING);
      } catch (Throwable var3) {
         logger.fatal(var3);
      }
   }

   public void setConnectionState(EnumConnectionState enumConnectionState) {
      this.channel.attr(attrKeyConnectionState).set(enumConnectionState);
      this.channel.config().setAutoRead(true);
      logger.debug("Enabled auto read");
   }

   @Override
   public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
      this.closeChannel(new ChatComponentTranslation("disconnect.endOfStream"));
   }

   @Override
   public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) throws Exception {
      ChatComponentTranslation var6;
      if (throwable instanceof TimeoutException) {
         var6 = new ChatComponentTranslation("disconnect.timeout");
      } else {
         var6 = new ChatComponentTranslation("disconnect.genericReason", "Internal Exception: " + throwable);
      }

      this.closeChannel(var6);
   }

   protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) throws Exception {
      PacketEvent var6 = new PacketEvent(false, packet);
      Moon.getInstance().getEventBus().publish(var6);
      if (!var6.isCancelled() && this.channel.isOpen()) {
         try {
            packet.processPacket(this.packetListener);
         } catch (ThreadQuickExitException var7) {
         }
      }
   }

   public void sendPacket(Packet<?> packet) {
      if (this.isChannelOpen()) {
         this.flushOutboundQueue();
         this.dispatchPacket(packet, null);
      } else {
         this.readWriteLock.writeLock().lock();

         try {
            this.outboundPacketsQueue
               .add(new NetworkManager.InboundHandlerTuplePacketListener(packet, (GenericFutureListener<? extends Future<? super Void>>[])null));
         } finally {
            this.readWriteLock.writeLock().unlock();
         }
      }
   }

   public void sendPacket(
      Packet<?> packet,
      GenericFutureListener<? extends Future<? super Void>> genericFutureListener,
      GenericFutureListener<? extends Future<? super Void>>... arr
   ) {
      if (this.isChannelOpen()) {
         this.flushOutboundQueue();
         this.dispatchPacket(packet, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add(arr, 0, genericFutureListener));
      } else {
         this.readWriteLock.writeLock().lock();

         try {
            this.outboundPacketsQueue
               .add(
                  new NetworkManager.InboundHandlerTuplePacketListener(
                     packet, (GenericFutureListener<? extends Future<? super Void>>[])ArrayUtils.add(arr, 0, genericFutureListener)
                  )
               );
         } finally {
            this.readWriteLock.writeLock().unlock();
         }
      }
   }

   private void dispatchPacket(Packet packet, GenericFutureListener<? extends Future<? super Void>>[] arr) {
      EnumConnectionState var6 = EnumConnectionState.getFromPacket(packet);
      EnumConnectionState var7 = (EnumConnectionState)this.channel.attr(attrKeyConnectionState).get();
      if (var7 != var6) {
         logger.debug("Disabled auto read");
         this.channel.config().setAutoRead(false);
      }

      if (this.channel.eventLoop().inEventLoop()) {
         if (var6 != var7) {
            this.setConnectionState(var6);
         }

         ChannelFuture var8 = this.channel.writeAndFlush(packet);
         if (arr != null) {
            var8.addListeners(arr);
         }

         var8.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
      } else {
         this.channel.eventLoop().execute(() -> {
            if (var6 != var7) {
               this.setConnectionState(var6);
            }

            ChannelFuture var8 = this.channel.writeAndFlush(packet);
            if (arr != null) {
               var8.addListeners(arr);
            }

            var8.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
         });
      }
   }

   private void flushOutboundQueue() {
      if (this.channel != null && this.channel.isOpen()) {
         this.readWriteLock.readLock().lock();

         try {
            while (!this.outboundPacketsQueue.isEmpty()) {
               NetworkManager.InboundHandlerTuplePacketListener var4 = this.outboundPacketsQueue.poll();
               this.dispatchPacket(var4.packet, var4.futureListeners);
            }
         } finally {
            this.readWriteLock.readLock().unlock();
         }
      }
   }

   public void processReceivedPackets() {
      this.flushOutboundQueue();
      if (this.packetListener instanceof ITickable) {
         ((ITickable)this.packetListener).update();
      }

      this.channel.flush();
   }

   public SocketAddress getRemoteAddress() {
      return this.socketAddress;
   }

   public void closeChannel(IChatComponent iChatComponent) {
      if (this.channel.isOpen()) {
         this.channel.close().awaitUninterruptibly();
         this.terminationReason = iChatComponent;
      }
   }

   public boolean isLocalChannel() {
      return this.channel instanceof LocalChannel || this.channel instanceof LocalServerChannel;
   }

   public void enableEncryption(SecretKey secretKey) {
      this.isEncrypted = true;
      this.channel.pipeline().addBefore("splitter", "decrypt", new NettyEncryptingDecoder(CryptManager.createNetCipherInstance(2, secretKey)));
      this.channel.pipeline().addBefore("prepender", "encrypt", new NettyEncryptingEncoder(CryptManager.createNetCipherInstance(1, secretKey)));
   }

   public boolean getIsencrypted() {
      return this.isEncrypted;
   }

   public boolean isChannelOpen() {
      return this.channel != null && this.channel.isOpen();
   }

   public boolean hasNoChannel() {
      return this.channel == null;
   }

   public INetHandler getNetHandler() {
      return this.packetListener;
   }

   public void setNetHandler(INetHandler iNetHandler) {
      Validate.notNull(iNetHandler, "packetListener", new Object[0]);
      logger.debug("Set listener of {} to {}", this, iNetHandler);
      this.packetListener = iNetHandler;
   }

   public IChatComponent getExitMessage() {
      return this.terminationReason;
   }

   public void disableAutoRead() {
      this.channel.config().setAutoRead(false);
   }

   public void setCompressionTreshold(int integer) {
      if (integer >= 0) {
         if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            ((NettyCompressionDecoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(integer);
         } else {
            this.channel.pipeline().addBefore("decoder", "decompress", new NettyCompressionDecoder(integer));
         }

         if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            ((NettyCompressionEncoder)this.channel.pipeline().get("decompress")).setCompressionTreshold(integer);
         } else {
            this.channel.pipeline().addBefore("encoder", "compress", new NettyCompressionEncoder(integer));
         }
      } else {
         if (this.channel.pipeline().get("decompress") instanceof NettyCompressionDecoder) {
            this.channel.pipeline().remove("decompress");
         }

         if (this.channel.pipeline().get("compress") instanceof NettyCompressionEncoder) {
            this.channel.pipeline().remove("compress");
         }
      }

      this.channel.pipeline().fireUserEventTriggered(new CompressionReorderEvent());
   }

   public void checkDisconnected() {
      if (this.channel != null && !this.channel.isOpen()) {
         if (!this.disconnected) {
            this.disconnected = true;
            if (this.getExitMessage() != null) {
               this.getNetHandler().onDisconnect(this.getExitMessage());
            } else if (this.getNetHandler() != null) {
               this.getNetHandler().onDisconnect(new ChatComponentText("Disconnected"));
            }
         } else {
            logger.warn("handleDisconnection() called twice");
         }
      }
   }

   public Channel Method714() {
      return this.channel;
   }

   static class InboundHandlerTuplePacketListener {
      private final Packet packet;
      private final GenericFutureListener<? extends Future<? super Void>>[] futureListeners;

      public InboundHandlerTuplePacketListener(Packet packet, GenericFutureListener<? extends Future<? super Void>>... arr) {
         this.packet = packet;
         this.futureListeners = arr;
      }
   }
}
