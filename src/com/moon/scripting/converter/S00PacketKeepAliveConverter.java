package com.moon.scripting.converter;

import net.minecraft.network.play.server.S00PacketKeepAlive;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class S00PacketKeepAliveConverter extends PacketConverter<S00PacketKeepAlive> {
   public S00PacketKeepAliveConverter(Class<S00PacketKeepAlive> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(S00PacketKeepAlive s00PacketKeepAlive) {
      LuaTable table = new LuaTable();
      table.set("id", LuaValue.valueOf(s00PacketKeepAlive.Method894()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, S00PacketKeepAlive s00PacketKeepAlive) {
      s00PacketKeepAlive.Method895(luaTable.get("id").toint());
   }
}
