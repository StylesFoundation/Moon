package com.moon.scripting.converter;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

public class C00PacketKeepAliveConverter extends PacketConverter<C00PacketKeepAlive> {
   public C00PacketKeepAliveConverter(Class<C00PacketKeepAlive> packetClass) {
      super(packetClass);
   }

   public LuaTable toLuaTable(C00PacketKeepAlive c00PacketKeepAlive) {
      LuaTable table = new LuaTable();
      table.set("key", LuaValue.valueOf(c00PacketKeepAlive.Method657()));
      return table;
   }

   public void applyFromTable(LuaTable luaTable, C00PacketKeepAlive c00PacketKeepAlive) {
      int key = luaTable.get("key").toint();
      c00PacketKeepAlive.Method658(key);
   }
}
