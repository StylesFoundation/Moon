package com.moon.viamcp.loader;

import com.moon.viamcp.ViaMCP;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.protocols.base.BaseVersionProvider;

class MCPVersionProvider extends BaseVersionProvider {
   final MCPViaLoader loader;

   MCPVersionProvider(MCPViaLoader o_) {
      this.loader = o_;
   }

   @Override
   public int getClosestServerProtocol(UserConnection userConnection) throws Exception {
      return userConnection.isClientSide() ? ViaMCP.getInstance().getVersion() : super.getClosestServerProtocol(userConnection);
   }
}
