package com.moon.viamcp.loader;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.protocol.version.VersionProvider;
import com.viaversion.viaversion.bungee.providers.BungeeMovementTransmitter;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.providers.MovementTransmitterProvider;

public class MCPViaLoader implements ViaPlatformLoader {
   @Override
   public void load() {
      Via.getManager().getProviders().use(MovementTransmitterProvider.class, new BungeeMovementTransmitter());
      Via.getManager().getProviders().use(VersionProvider.class, new MCPVersionProvider(this));
   }

   @Override
   public void unload() {
   }
}
