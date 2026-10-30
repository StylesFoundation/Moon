package com.moon.viamcp.loader;

import com.moon.viamcp.ViaMCP;
import de.gerrygames.viarewind.api.ViaRewindConfigImpl;
import de.gerrygames.viarewind.api.ViaRewindPlatform;
import java.io.File;
import java.util.logging.Logger;

public class MCPRewindLoader implements ViaRewindPlatform {
   public MCPRewindLoader(File file) {
    //  ViaRewindConfigImpl config = new ViaRewindConfigImpl(file.toPath().resolve("Rewind").resolve("config.yml").toFile());
    //  config.reloadConfig();
  //    this.init(config);
   }

   @Override
   public Logger getLogger() {
      return ViaMCP.getInstance().getjLogger();
   }
}
